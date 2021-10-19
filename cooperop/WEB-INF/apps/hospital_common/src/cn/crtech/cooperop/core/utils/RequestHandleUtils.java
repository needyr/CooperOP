package cn.crtech.cooperop.core.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.core.domain.RequestTask;
import cn.crtech.cooperop.core.domain.RunFuture;
import cn.crtech.cooperop.core.domain.RunResult;
import cn.crtech.cooperop.core.domain.pojo.RunConfig;
import cn.crtech.cooperop.core.service.BlankService;
import cn.crtech.cooperop.core.service.ExecuteService;
import cn.crtech.cooperop.core.service.ExecuteTask;

/**
 * 请求处理工具类 - 统一处理请求的参数
 * @author chenjunhong
 * 2021年1月25日
 */
public class RequestHandleUtils {

	//预处理 - 封装请求参数
	@SuppressWarnings("unchecked")
	public static RequestTask pretreatment(HttpServletRequest request,HttpServletResponse response,String position) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		//记录开始时间
		long start = System.currentTimeMillis();
		//1-提取原始请求参数
		//1.1 事前请求JSON串 - json
		//1.2 事后审查唯一标识 - audit_source_fk
		Map<String, Object> origin = CommonFun.requestMap(request);
		//2-创建请求任务对象
		RequestTask task = new RequestTask(request,response,origin,position);
		task.setStart(start);
		//3-处理JSON串中的特殊符号，并转换为MAP对象
		String reqJson = (String)origin.get("json");
		reqJson = reqJson.replaceAll("[\\t\\n\\r]", "");
		reqJson = reqJson.replaceAll("\\\\", "/");
		Map<String, Object> jsonMap = CommonFun.json2Object(reqJson, Map.class);
		//4-更多参数转换处理
		Map<String, Object> params = (Map<String, Object>)jsonMap.get("request");
		Map<String, Object> patient = (Map<String, Object>)params.get("patient");
		task.setPatKey(buildPatKey(patient));
		//请求节点#request
		//task.getParams().putAll(params);
		task.setParams(params);
		//用于控制管理的参数
		task.getControl().put("ip", getIpAddr(request));
		//用于过滤判断的参数
		task.getFilter().put("h_type", params.get("h_type"));
		task.getFilter().put("p_type", params.get("p_type"));
		task.getFilter().put("d_type", params.get("d_type"));
		task.getFilter().put("is_after", params.get("is_after"));
		if(patient!=null) {
			task.getFilter().put("dept_code", patient.get("departcode"));
		}
		//5-构建默认返回值
		task.setRtnJson("N");
		System.out.println("#封装请求 - " + (System.currentTimeMillis() - start) + "ms");
		return task;
	}
	
	private static String buildPatKey(Map<String, Object> patient) {
		return "p_"+patient.get("id")+"_"+patient.get("visitid");
	}
	
	//匹配服务 - 找到符合本次请求任务限定条件的执行服务
	public static ExecuteService findMatchService(RequestTask task,List<ExecuteService> services) {
		List<ExecuteService> matches = new ArrayList<>();
		for(ExecuteService es:services) {
			matchService(task.getFilter(), es, matches);
		}
		if(matches.isEmpty()&&!services.isEmpty()) {
			matches.add(services.get(0));
		}else {
			matches.add(new BlankService());
		}
		return matches.get(0);
	}
	
	public static void matchService(Map<String, Object> filter,ExecuteService es,List<ExecuteService> matches) {
		//匹配过程 - 待完成
		//matches.add(0, es);
		matches.add(es);
	}
	
	public static boolean filterRunService(Map<String, Object> filter,RunConfig run,List<ExecuteService> matches) {
		//匹配实现 - 待完成
		boolean flag = false;
		return flag;
	}
	
	public static Map<String, Object> buildDefaultInitResponse() {
		//构建默认的审查结果
		Map<String, Object> respMap = new HashMap<String, Object>();
		respMap.put("state", "Y");
		respMap.put("use_flag", 1);
		respMap.put("flag", 1);
		respMap.put("id", "A");
		respMap.put("finish", false);
		return respMap;
	}
	
	//获取请求IP
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	//任务执行方式
	public static void execute(int runMode,int waitting,ExecuteTask task,
			ExecutorService threadPools,Map<String,Future<RunResult>> futures){
		Future<RunResult> future = null;
		if(ConstantUtils.checkRunSync(runMode)) {
			RunResult r = null;
			try {
				//同步执行 - 直接调用call方法
				r = task.call();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//手工构建同步执行的结果状态对象
				future = new RunFuture(r,true,true);
			}
		}else {
			//异步执行 - 提交到线程池执行，间接调用call方法
			future = threadPools.submit(task);
		}
		//是否加入监听结果集中
		if(ConstantUtils.checkWaitting(waitting)) {
			futures.put(task.getConfig().getCode(),future);
		}
	}
	
	/**
	 * 定时轮询监控，直到监控范围内的所有维度都执行完毕
	 * @param futureList	监控集合
	 * @param intervalTime	轮询时间
	 */
	public static void checkFutures(RequestTask task,List<Future<RunResult>> futures,
			long intervalTime,long timeout){
		//1-定时轮询过程，直到数个过程都执行完毕为止
		//2-加入整体超时监控处理
		for(Future<RunResult> fut : futures){
			try {
				while(true){
					//轮询时间 - 每N秒
					Thread.sleep(intervalTime);
					//执行完毕或者退出执行
					if(fut.isDone()||fut.isCancelled()){
						//System.out.println("#--------------------------------------------#");
						//get() - 似乎有阻塞效果，同时后续再确定返回结果
						//ylz.p("  #任务[I]=" + fut.get() + "执行完成#" + new Date());
						//这一步待定
						task.getOut().putAll(fut.get());
						break;
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
