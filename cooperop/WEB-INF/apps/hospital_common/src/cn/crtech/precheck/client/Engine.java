package cn.crtech.precheck.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import cn.crtech.cooperop.bus.cache.MemoryCache;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;
import cn.crtech.precheck.client.ClientScheduleJob;
import cn.crtech.precheck.client.Engine;
import cn.crtech.precheck.client.service.ClientService;

public class Engine {
	public String CLIENT_CODE ;
	public static final String CONFIG_PATH = "WEB-INF/config/client";
	public static final String DEFAULT_GROUP = "PRECHECK_SCHEDULER_";
	public static final String PARAM_METHOD_NAME = "CLIENT_METHOD";
	public static final String PARAM_ENGINE_NAME = "CLIENT_ENGINE";
	private Scheduler schedule;
	public static Map<String, Engine> engines = new HashMap<String, Engine>();
	
	// +++
	public static Map<String, Object> exceptionMark = new HashMap<String, Object>();
	// +++

	public static void schedule(String code, String method) throws Exception {
		if (engines.get(code) != null)
			engines.get(code).scheduleMethod(method);
	}

	public static boolean containsSchedule(String code, String method) throws Exception {
		if (engines.get(code) != null)
			return engines.get(code).containsScheduleMethod(method);
		return false;
	}

	public static void removeSchedule(String code, String method) throws Exception {
		if (engines.get(code) != null)
			engines.get(code).removeScheduleMethod(method);
	}

	public void init(String client_code) throws ServletException {
		try {
			this.CLIENT_CODE = client_code;
			schedule();
			engines.put(CLIENT_CODE, this);
		} catch (Exception exc) {
			log.error("启动" + CLIENT_CODE + "定时服务异常", exc);
			throw new ServletException(exc);
		}

	}
	
	public void schedule() throws Exception {
		schedule = installScheduler();
		GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(DEFAULT_GROUP + CLIENT_CODE);
		Set<JobKey> jobs = schedule.getJobKeys(matcher);
		for (JobKey job : jobs) {
			schedule.deleteJob(job);
		}
		Record client = new ClientService().getClient(CLIENT_CODE);
		if(client!=null) {
			@SuppressWarnings("unchecked")
			List<Record> methods = (List<Record>) client.get("methods");
			for (Record method : methods) {
				// if (!"jgykkpmx_ywjh".equals(method.getString("code")))
				// continue;
				scheduleMethod(method.getString("code"));
			}
		}
		schedule.start();
	}

	public void scheduleMethod(String method) throws Exception {
		// +++
		exceptionMark.remove("scheduleException#" + method);
		// +++
		Record m =  new ClientService().getClientMethod(CLIENT_CODE, method);
		String id = m.getString("data_webservice_code") + "." + m.getString("code");
		String jobName = m.getString("name") + "[" + id + "]";
		removeScheduleMethod(method);
		try {
			String triggerCron = m.getString("cycle_cron");
			if (CommonFun.isNe(triggerCron)) {
				return;
				//throw new Exception("cycle_cron不得为空");
			}
			JobDetail job = JobBuilder.newJob(ClientScheduleJob.class)
					.withIdentity(jobName, DEFAULT_GROUP + CLIENT_CODE).build();
			job.getJobDataMap().put(PARAM_METHOD_NAME, method);
			job.getJobDataMap().put(PARAM_ENGINE_NAME, CLIENT_CODE);
			CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger()
					.withIdentity("trigger[" + id + "]", "trigger_" + DEFAULT_GROUP + CLIENT_CODE)
					.withSchedule(CronScheduleBuilder.cronSchedule(triggerCron)).build();
			schedule.scheduleJob(job, trigger);
			log.release("register schedule task success, " + jobName + "[cron=" + triggerCron + "]");
		} catch (Exception e) {
			// +++
			exceptionMark.put("scheduleException#" + method, e.getMessage());
			// +++
			log.error("register schedule task failed, " + jobName, e);
		}
	}

	public void removeScheduleMethod(String method) throws Exception {
		Record m =  new ClientService().getClientMethod(CLIENT_CODE, method);
		String id = m.getString("data_webservice_code") + "." + m.getString("code");
		String jobName = m.getString("name") + "[" + id + "]";
		GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(DEFAULT_GROUP + CLIENT_CODE);
		Set<JobKey> jobs = schedule.getJobKeys(matcher);
		for (JobKey job : jobs) {
			if (job.getName().equals(jobName)) {
				schedule.deleteJob(job);
				log.release("delete schedule task success, " + jobName);
			}
		}
	}

	public boolean containsScheduleMethod(String method) throws Exception {
		Record m =  new ClientService().getClientMethod(CLIENT_CODE, method);
		String id = m.getString("data_webservice_code") + "." + m.getString("code");
		String jobName = m.getString("name") + "[" + id + "]";
		GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(DEFAULT_GROUP + CLIENT_CODE);
		Set<JobKey> jobs = schedule.getJobKeys(matcher);
		
		for (JobKey job : jobs) {
			if (job.getName().equals(jobName)) {
				return true;
			}
		}
		return false;
	}

	public Object invokeMethod(String method, String request) throws Exception {
		return null;
	}
	public void execMethod(Map<String, Object> params){
		
	}
	public void execMethod(String method, Map<String, Object> params) throws Exception {
	}
	// +++
	public static String test(Map<String, Object> req) {
		Object rtn = null;
//		Map<String, Object> params = null;
//		Object body = null;
		String respose = "";
		String request = ((String)req.get("request")).trim();
			
		log.info(request);
//		if (request.startsWith("<Request>")) {
//			params = CommonFun.xml2Object((String)req.get("request"), Map.class);
//			body = ((Map<String, Object>)params.get("Request")).get("Body");
//		} else {
//			if (request.startsWith("[[") && request.endsWith("]]"))
//				request = request.substring(1, request.length() - 1);
//			if (request.startsWith("[")) {
//				body = CommonFun.json2Object(request, List.class);
//			} else if (request.startsWith("{")) {
//				body = CommonFun.json2Object(request, Map.class);
//			}
//		}
		
//		String json = "[{\"OutpatientEncounterStartedRt\":[{\"UpdateDate\":\"7\",\"PATPatientID\":\"1\",\"UpdateTime\":\"8\",\"UpdateUserCode\":\"6\",\"PAADMOPTime\":\"5\",\"PAADMOPDocCode\":\"4\",\"PAADMVisitNumber\":\"2\",\"PAADMOPDeptCode\":\"3\"}]}]";
//		List<Map<String, String>> arglist = CommonFun.json2Object(json, List.class);
		try {
		rtn = engines.get(
				req.get("data_webservice_code")).invokeMethod(
						(String)req.get("code"), request);
		
		Record clientService = 
				new ClientService().getClient((String)req.get("data_webservice_code"));
		
		respose = engines.get(req.get("data_webservice_code"))
				.getResponse(
						(String)(String)req.get("code"),
						(String)clientService.get("header"), rtn);
		} catch (Exception ex) {
			respose = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
		}
		
		return respose;
	}
	public String getRequest(String method, String header, Object... args) {
		return null;
	}
	
	public String getResponse(String method, String header, Object rtn) {
		return null;
	}
	
	public  Scheduler installScheduler() throws SchedulerException {
		StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Properties props = new Properties();
		props.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_ID, "cr_dxp_" + CLIENT_CODE);
		props.put(StdSchedulerFactory.PROP_SCHED_INSTANCE_NAME, "cr_dxp_" + CLIENT_CODE);
		props.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS, "org.quartz.simpl.SimpleThreadPool");
		props.put("org.quartz.threadPool.threadCount", "100");// 必填
		schedulerFactory.initialize(props);
		
		return schedulerFactory.getScheduler();
	}
	
	public void uninstallScheduler() throws SchedulerException {
		schedule.shutdown(true);
	}
	public void destroy(){
		
	}
	public static void load(String code) throws Exception {
		if (engines.get(code) != null) {
			engines.get(code).init(code);
			
		}
	}
	public static void remove(String code) throws Exception {
		if (engines.get(code) != null) {
			engines.get(code).uninstallScheduler();
			engines.remove(code);
		}
	}
	
	// +++
}
