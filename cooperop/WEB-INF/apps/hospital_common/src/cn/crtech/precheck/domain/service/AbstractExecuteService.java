//package cn.crtech.precheck.domain.service;
//
//import java.io.IOException;
//import java.lang.Thread.State;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Future;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.TimeUnit;
//
//import javax.servlet.http.HttpServletResponse;
//
//import cn.crtech.cooperop.core.domain.RequestTask;
//import cn.crtech.cooperop.core.domain.RunFuture;
//import cn.crtech.cooperop.core.domain.RunResult;
//import cn.crtech.cooperop.core.domain.RunStatus;
//import cn.crtech.cooperop.core.domain.pojo.RunConfig;
//import cn.crtech.cooperop.core.service.ExecuteService;
//import cn.crtech.cooperop.core.service.ExecuteTask;
//import cn.crtech.precheck.utils.ConstantUtils;
//import cn.crtech.precheck.utils.ThreadPoolUtils;
//import cn.crtech.ylz.ylz;
//
///**
// * 默认的执行服务实现类 - 空实现或者默认实现
// * @author chenjunhong
// * 2021年1月21日
// */
//public abstract class AbstractExecuteService implements ExecuteService{
//
//	/** 请求任务并发管理线程池 - 在请求入口处进行定义，外部传入 */
//	private ExecutorService requestPool;
//	/** 执行任务并发管理线程池 - 在服务内部通过配置参数进行定义 */
//	private ExecutorService executePool;
//	/** 执行任务的监测结果集 */
//	private Map<String,Future<RunResult>> futures;
//	
//	/** 服务运行的基础参数 */
//	private RunConfig config;
//	/** 服务配置的执行任务（1-N项） */
//	private List<ExecuteTask> tasks;
//	
//	/** 服务运行管理模式：1-普通模式（即：直接执行），2-队列模式（即：排队执行） */
//	private RunPattern pattern = RunPattern.NORMAL;
//	/** 执行队列 */
//	private BlockingQueue<RequestTask> blockQueue;
//	/** 监听线程 */
//	private Thread monitor;
//	/** 执行名单，记录正在执行处理的患者名单，执行完成后移除 */
//	private Map<String, Long> patStaff;
//	
//	
//	private static boolean outputLog = true;
//	
//	public static void log(Object message) {
//		if(outputLog) {
//			System.out.println(message);
//		}
//	}
//	
//	public enum RunPattern {
//		NORMAL("P1","普通模式"),
//		QUEUE("P2","队列模式");
//		
//		private String code;
//		private String desc;
//		
//		private RunPattern(String code, String desc) {
//			this.code = code;
//			this.desc = desc;
//		}
//		public String getCode() {
//			return code;
//		}
//		public void setCode(String code) {
//			this.code = code;
//		}
//		public String getDesc() {
//			return desc;
//		}
//		public void setDesc(String desc) {
//			this.desc = desc;
//		}
//		public void print() {
//			System.out.println(this.getCode() + "#" + this.getDesc());
//		}
//	}
//	
//	@Override
//	public void init(RunConfig run){
//		//执行服务的初始化过程
//		this.setConfig(run);
//		this.setFutures(new LinkedHashMap<String,Future<RunResult>>());
//		this.setPatStaff(new ConcurrentHashMap<String, Long>());
//		//初始化服务下的队列和执行任务线程池
//		if(run.getQueue() > 0) {
//			initQueue(run.getQueue());
//			initMonitor();
//			//队列模式
//			this.setPattern(RunPattern.QUEUE);
//			//启用固定数目的线程池，线程数和队列数遵循1比2的原则
//			this.setExecutePool(ThreadPoolUtils.loadFixedThreadPool(run.getCapacity()));
//		}else {
//			//普通模式
//			this.setPattern(RunPattern.NORMAL);
//			//启用缓存型线程池，异步执行请求任务
//			this.setExecutePool(ThreadPoolUtils.loadCachedThreadPool());
//		}
//	}
//	
//	private void initQueue(int queue) {
//		//设置有界阻塞队列
//		this.setBlockQueue(new LinkedBlockingQueue<RequestTask>(queue));
//	}
//	
//	private void initMonitor() {
//		//设置队列监听线程 - 排队获取任务
//		Thread m = new Thread(() -> {
//			RequestTask task = null;
//			while(true) {
//				try {
//					//阻塞出队 - 出队可以一直等待，不限时间
//					task = this.getBlockQueue().take();
//					//执行任务 - 排队执行，通过队列模式限流（限制条件：队列数目#2 - 执行线程数目#1）
//					execute(task);
//					//间隔时间 - 降低占用
//					Thread.sleep(100);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		this.setMonitor(m);
//		//启动队列监听线程 -> 不在初始化时启动，调整为在action时启动
//		//startMonitor(m);
//	}
//	
//	@Override
//	public void initTasks(List<ExecuteTask> tasks) {
//		this.setTasks(tasks);
//	}
//
//	@Override
//	public void initRequestPool(ExecutorService pool) {
//		this.setRequestPool(pool);
//	}
//	
//	protected void action(RequestTask task) {
//		task.setAction(System.currentTimeMillis());			//记录启动时间
//		task.setTimeout(this.getConfig().getTimeout());		//默认超时时间，5000毫秒 - 5秒
//		task.setStatus(RunStatus.ACTION);
//		//启动队列监听线程
//		startMonitor();
//		//应用参数准备过程 - 在子类中实现
//		prepare(task);
//	}
//	
//	private void startMonitor() {
//		//启动监听线程，全程只执行一次
//		Thread m = this.getMonitor();
//		if(m!=null&&State.NEW==m.getState()) {
//			m.start();
//		}
//	}
//	
//	/**
//	 * 在子类中实现 - 构建实际的应用参数
//	 * @param task
//	 */
//	public abstract void prepare(RequestTask task);
//
//	/**
//	 * 在子类中实现 - 过滤处理：排除无效的请求任务
//	 * @param task
//	 * @return true - 排除任务，false - 接收任务 
//	 */
//	public abstract boolean filter(RequestTask task);
//	
//	/**
//	 * 在子类中实现 - 组织正式的返回结果，JSON格式字符串
//	 * @param task
//	 */
//	public abstract void decorate(RequestTask task);
//
//	/**
//	 * 在子类中复写 - 是否需要记录执行名单，并且清理执行名单
//	 * @param task
//	 */
//	public void clean(RequestTask task) {
//		log("#Clean - " + task.getPatKey());
//	}
//	
//	/** 服务的标准执行过程 */
//	public final void process(RequestTask task){
//		log("#Process - " + task.getPosition());
//		//启动
//		action(task);
//		//过滤
//		if(!filter(task)) {
//			//将任务加入执行队列 - 自动进行处理
//			joinQueue(task);
//		}
//		//测试#输出执行状态
//		task.getStatus().print();
//		//完成
//		complete(task);
//	}
//	
//	/** 请求任务的标准入队过程 */
//	protected final void joinQueue(RequestTask task) {
//		log("#JoinQueue - " + task.getPosition());
//		switch(this.getPattern()) {
//		case NORMAL:
//			//普通模式 - 不入队列，直接执行
//			System.out.print("#直接执行 -> ");
//			execute(task);
//			break;
//		case QUEUE:
//			//队列模式 - 加入队列，间接执行
//			System.out.print("#排队执行 -> ");
//			offer(task);
//			//其他扩展 - 待定
//			break;
//		}
//	}
//	
//	/** 请求任务加入队列的处理过程 - 入队有超时限制 */
//	protected final void offer(RequestTask task) {
//		try {
//			//入队等待时间 - 暂定：2秒 - 注：排队等待时间需要开放设置，不同的服务设定的时间是不同的
//			log("#开始入队 -> " + task.getPosition());
//			long timeout = this.getConfig().getQueueTime();
//			if(this.getBlockQueue().offer(task, timeout, TimeUnit.MILLISECONDS)) {
//				//成功入队 - 排队执行
//				task.setStatus(RunStatus.ASYNC);
//				//是否需要调整超时时间 - 待进一步确认
//				task.setTimeout(task.getTimeout() - (timeout/2));
//				log(" #入队成功 -> 当前队列数：" + this.getBlockQueue().size());
//			}else {
//				//入队超时 - 超时返回
//				task.setStatus(RunStatus.OVERTIME);
//				log(" #排队超时 -> 当前队列数：" + this.getBlockQueue().size());
//				//超时处理策略
//				timeoutPolicy(task);
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	/**
//	 * 在子类中进行复写 - 请求任务超时后的处理策略，如：是否记录请求记录（auto_common、auto_common_mx）
//	 * @param task
//	 */
//	protected void timeoutPolicy(RequestTask task) {
//		log(" #超时患者 -> " + task.getPatKey());
//	}
//	
//	protected void execute(RequestTask task) {
//		//运行模式：1-同步，2-异步
//		int runMode = this.getConfig().getRunMode();
//		if(ConstantUtils.checkRunSync(runMode)) {
//			//同步执行
//			log(" #同步执行 - " + task.getPosition());
//			task.setStatus(RunStatus.SYNC);
//			execute(task, this.getTasks());
//		}else {
//			//异步执行 - 请求任务并发管理线程池
//			log(" #异步执行 - " + task.getPosition());
//			task.setStatus(RunStatus.ASYNC);
//			this.getRequestPool().execute(() -> {
//				execute(task, this.getTasks());
//			});
//		}
//	}
//	
//	private void execute(RequestTask task,List<ExecuteTask> tasks) {
//		/*
//		 * 如何执行服务下配置的任务（1-N）
//		 * 1、大部分时候将服务配置为单任务模式 - 不好，希望是配置为多任务，然后顺序执行或者并发执行
//		 * 2、具体任务的执行方式也可以单独设置
//		 */
//		log("#请求任务线程：" + Thread.currentThread().getName());
//		if(tasks!=null&&!tasks.isEmpty()) {
//			//注1：task是在服务初始化时创建的，之后就一直保持不变了
//			//注2：因为对于请求任务是采用的多线程执行方式，所以要避免同一个task的冲突，每次执行时需要new一个新的
//			//注3：new的时候应该创建的是哪一个执行服务呢，这里需要明确，或者使用clone方法
//			RunConfig config = null;
//			for(ExecuteTask et : tasks) {
//				log(" #调用任务：" + et.toString());
//				config = et.getConfig();
//				//执行任务 - 任务并发管理线程池，采用线程并发执行
//				execute(config.getRunMode(), config.getWaitting(), config.getCode(),
//						et.process(config, task), this.getExecutePool(), this.getFutures());
//			}
//		}
//	}
//	
//	//任务执行方式
//	private void execute(int runMode,int waitting,String code,ExecuteTask task,
//			ExecutorService threadPools,Map<String,Future<RunResult>> futures){
//		if(task!=null) {
//			Future<RunResult> future = null;
//			if(ConstantUtils.checkRunSync(runMode)) {
//				RunResult r = null;
//				try {
//					//同步执行 - 直接调用call方法
//					log(" #同步执行 - " + code);
//					r = task.call();
//				} catch (Exception e) {
//					e.printStackTrace();
//				} finally {
//					//构建同步执行的结果对象
//					future = new RunFuture(r,true,true);
//				}
//			}else {
//				//异步执行 - 提交到线程池执行，间接调用call方法
//				log(" #异步执行 - " + code);
//				future = threadPools.submit(task);
//			}
//			//是否监听任务执行情况
//			if(ConstantUtils.checkWaitting(waitting)) {
//				log(" #执行监听 - " + code);
//				futures.put(code,future);
//			}else {
//				log(" #排除监听 - " + code);
//			}
//		}
//	}
//	
//	/** 在子类中实现#完成 - 等待执行完成或超时，并返回响应结果，结束整个请求过程 */
//	protected final void complete(RequestTask task) {
//		//服务是否需要等待返回
//		log("#Complete - " + task.getPosition());
//		int waitting = this.getConfig().getWaitting();
//		if(ConstantUtils.checkWaitting(waitting)) {
//			//获取请求任务的执行状态
//			RunStatus status = task.getStatus();
//			switch(status) {
//			case COMPLETE:
//			case OVERTIME:
//			case ERROR:
//				//执行完成
//				finishExecute(task);
//				break;
//			case EMPTY:
//			case SYNC:
//			case ASYNC:
//				//执行中
//				monitorExecute(task);
//				break;
//			default:
//				//其他状态
//				finishExecute(task);
//			}
//		}else {
//			//不需等待时，直接返回结果
//			finishExecute(task);
//		}
//	}
//	
//	//监听执行
//	private void monitorExecute(RequestTask task) {
//		/*
//		 * 1-如何实现监听处理
//		 * 2-是否需要设为静态工具方法 - RequestHandleUtils.checkFutures
//		 */
//		log(" #MonitorExecute - " + task.getPosition());
//		Map<String,Object> rtn = checkFutures(task.getAction(), task.getTimeout(), 
//				ConstantUtils.INTERVAL_WAIT_TIME, this.getFutures());
//		if(!rtn.isEmpty()) {
//			//结果处理 - 待定
//			task.getOut().putAll(rtn);
//			//task.setRtnJson(CommonFun.object2Json(task.getOut()));
//		}
//		//监听完成后返回
//		finishExecute(task);
//	}
//	
//	//结束执行
//	private void finishExecute(RequestTask task) {
//		log(" #FinishExecute - " + task.getPosition());
//		//组织返回结果 - 在子类中实现
//		decorate(task);
//		//记录完成时间
//		task.finish();
//		//清理执行记录
//		clean(task);
//		try {
//			//将返回结果统一存放在task的rtnJson中，json格式的字符串
//			log(" #返回结果 - " + task.getRtnJson());
//			HttpServletResponse resp = task.getResponse();
//			resp.setContentType("text/html; charset=UTF-8");
//			//返回结果
//			resp.getWriter().write(task.getRtnJson());
//			resp.getWriter().flush();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	private Map<String,Object> checkFutures(long st,long timeout,long interval,
//			Map<String,Future<RunResult>> futures){
//		//返回结果集合
//		Map<String,Object> rtn = new HashMap<>();
//		//总监听任务数
//		int total = futures.size();
//		//已完成任务数
//		Map<String,Integer> fillMap = new HashMap<>();
//		//是否有监听任务
//		if(total > 0) {
//			//st - 执行开始时间，时间单位统一为毫秒
//			//long st = System.currentTimeMillis();
//			//long interval	间隔时间，如：100毫秒
//			//long timeout	超时时间，如：5000毫秒
//			//执行频次：5000/100 = 50
//			long freq= timeout/interval;
//			Future<RunResult> fut = null;
//			//监测情况：1-执行完成，2-执行超时，3-轮询终止
//			for(int i = 0; i <= freq; i++) {
//				//轮询判断
//				if(checkStatus(fillMap.size(), total, st, timeout)) {
//					break;
//				}
//				try {
//					//查看任务是否执行完毕，并获取执行结果
//					for(String key : futures.keySet()) {
//						if(fillMap.get(key)==null) {
//							fut = futures.get(key);
//							if(fut.isDone()||fut.isCancelled()){
//								log(" #任务[" + key + "]执行完成.");
//								fillMap.put(key, 1);
//								rtn.putAll(fut.get());	//在任务中记录执行时间
//							}
//						}
//					}
//					//设置轮询间隔
//					Thread.sleep(interval);		
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return rtn;
//	}
//	
//	//是否执行完成或执行超时，true - 是，false - 否
//	private boolean checkStatus(int number,int total,long st,long timeout) {
//		boolean flag = false;
//		//完成
//		if(number==total) {
//			flag = true;
//		}
//		if(!flag) {
//			//超时
//			if((System.currentTimeMillis()-st) > timeout) {
//				flag = true;
//				ylz.p("execute timeout");
//			}
//		}
//		return flag;
//	}
//	
//	public List<ExecuteTask> getTasks() {
//		return tasks;
//	}
//	public void setTasks(List<ExecuteTask> tasks) {
//		this.tasks = tasks;
//	}
//	public RunConfig getConfig() {
//		return config;
//	}
//	public void setConfig(RunConfig config) {
//		this.config = config;
//	}
//	public Map<String, Future<RunResult>> getFutures() {
//		return futures;
//	}
//	public void setFutures(Map<String, Future<RunResult>> futures) {
//		this.futures = futures;
//	}
//	public BlockingQueue<RequestTask> getBlockQueue() {
//		return blockQueue;
//	}
//	public void setBlockQueue(BlockingQueue<RequestTask> blockQueue) {
//		this.blockQueue = blockQueue;
//	}
//	public Thread getMonitor() {
//		return monitor;
//	}
//	public void setMonitor(Thread monitor) {
//		this.monitor = monitor;
//	}
//	public RunPattern getPattern() {
//		return pattern;
//	}
//	public void setPattern(RunPattern pattern) {
//		this.pattern = pattern;
//	}
//	public ExecutorService getRequestPool() {
//		return requestPool;
//	}
//	public void setRequestPool(ExecutorService requestPool) {
//		this.requestPool = requestPool;
//	}
//	public ExecutorService getExecutePool() {
//		return executePool;
//	}
//	public void setExecutePool(ExecutorService executePool) {
//		this.executePool = executePool;
//	}
//	public Map<String, Long> getPatStaff() {
//		return patStaff;
//	}
//	public void setPatStaff(Map<String, Long> patStaff) {
//		this.patStaff = patStaff;
//	}
//	
//}
