//package cn.crtech.precheck.domain.service;
//
//import cn.crtech.cooperop.core.domain.RequestTask;
//import cn.crtech.cooperop.core.domain.RunResult;
//import cn.crtech.cooperop.core.domain.RunStatus;
//import cn.crtech.cooperop.core.domain.pojo.RunConfig;
//import cn.crtech.cooperop.core.service.BlankTask;
//import cn.crtech.cooperop.core.service.ExecuteTask;
//
///**
// * 默认的执行任务实现类 - 空实现或者默认实现
// * @author chenjunhong
// * 2021年1月21日
// */
//public abstract class AbstractExecuteTask implements ExecuteTask {
//
//	private RunConfig config;
//	private RequestTask task;
//	
//	@Override
//	public void init(RunConfig config) {
//		//执行任务基础参数初始化
//		this.config = config;
//	}
//	
//	@Override
//	public RunConfig getConfig() {
//		return this.config;
//	}
//
//	@Override
//	public ExecuteTask process(RunConfig config,RequestTask task) {
//		//1-反射创建新的执行任务，避免冲突
//		AbstractExecuteTask et = getInterfaceImplObj(AbstractExecuteTask.class, this.getConfig().getClassz());
//		if(et!=null) {
//			//2-传入执行参数
//			AbstractExecuteService.log(" #执行任务：" + et.toString());
//			et.init(config);
//			//3-传入请求任务对象，并转换构建执行任务所需的数据对象
//			et.action(task.transform(config.getCode()));
//		}
//		return et;
//	}
//	
//	private void action(RequestTask task) {
//		task.setAction(System.currentTimeMillis());			//记录启动时间
//		task.setTimeout(this.getConfig().getTimeout());		//默认超时时间，3000毫秒 - 3秒
//		task.setStatus(RunStatus.ACTION);
//		this.setTask(task);
//		prepare(task);
//	}
//	
//	/** 准备 - 任务执行前的准备工作，如果需要，则在子类中进行复写 */
//	public void prepare(RequestTask task) {
//		//预留：可能较少需要
//	}
//	
//	/**
//	 * 过滤 - 是否排除不符合条件的任务，如果需要，则在子类中进行复写
//	 * @param task
//	 * @return true - 排除，false - 执行
//	 */
//	public boolean filter(RequestTask task) {
//		return false;
//	}
//	
//	/** 执行 - 具体的执行任务内容，在子类中实现处理 */
//	public abstract RunResult execute(RequestTask task);
//	
//	/**
//	 * 任务的实际执行入口 - 采用模板方法管理执行流程
//	 */
//	public final RunResult call() throws Exception {
//		RunResult retVal = new RunResult();
//		//过滤
//		if(!filter(this.getTask())) {
//			//执行任务
//			AbstractExecuteService.log("执行任务线程：" + Thread.currentThread().getName());
//			retVal = execute(this.getTask());
//		}
//		//记录任务的完成时间
//		this.getTask().finish();
//		return retVal;
//	}
//	
//	@SuppressWarnings("unchecked")
//	private <T> T getInterfaceImplObj(Class<T> clazz,String className){
//		Class<?> t = null;
//		Object obj = null;
//		if(className!=null) {
//			try {
//				t = Class.forName(className);
//				obj = t.newInstance();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		if(obj==null) {
//			obj = new BlankTask();
//		}
//		return (T)obj;
//	}
//	
//	public RequestTask getTask() {
//		return task;
//	}
//	
//	public void setTask(RequestTask task) {
//		this.task = task;
//	}
//	
//}
