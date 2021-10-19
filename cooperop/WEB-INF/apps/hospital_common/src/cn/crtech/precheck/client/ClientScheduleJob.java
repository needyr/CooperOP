package cn.crtech.precheck.client;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.precheck.client.Engine;
import cn.crtech.precheck.client.EngineClient;
import cn.crtech.precheck.client.service.ClientService;

public class ClientScheduleJob implements Job {
	
	private static HashMap<String, Long> runnings = new HashMap<String, Long>();

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String jobName = arg0.getJobDetail().getKey().getName();
		Record logs = null;
		String result = "[正常]";
		boolean remove = true;
		long overtime = Long.parseLong(GlobalVar.getSystemProperty("trade.busy.maxtime", "10"));
		try {
			log.debug("Schedule Job[" + jobName + "]: overtime = " + overtime);
			if (runnings.containsKey(jobName) && System.currentTimeMillis() - runnings.get(jobName) < overtime * 60 * 1000) {
				remove = false;
				throw new Exception("the last job is busy.");
			}
			runnings.put(jobName, System.currentTimeMillis());
			
			String data_webservice_code = (String) arg0.getMergedJobDataMap().get(EngineClient.PARAM_ENGINE_NAME);
			String method = (String) arg0.getMergedJobDataMap().get(EngineClient.PARAM_METHOD_NAME);
			if (method != null) {
				logs = new Record();
				logs.put("data_webservice_code", data_webservice_code);
				logs.put("data_webservice_method_code", method);
				logs.put("start_time", new Date());
			} else {
				throw new Exception("method data is null.");
			}
			//method.getString("name") + "[" + method.getString("data_webservice_code") + "_"
			//		+ method.getString("code") + "]";
			log.debug("Schedule Job[" + jobName + "]: begin");

			ClientService cs = new ClientService();
			Record m = cs.getClientMethod(data_webservice_code, method);
			cs.executeParamProcedure(m);
			List<Record> trading = cs.listClientTrading(m.getString("data_webservice_code"), m.getString("code"));
			for (Record trade : trading) {
				try {
				//	String json = cs.getTradeJSON(trade);
					String request = cs.getTradeJSON(trade);
					log.info(request);
				//	log.debug(json);
//					@SuppressWarnings("unchecked")
				//	List<Map<String, String>> arglist = CommonFun.json2Object(json, List.class);
					Object rtn = Engine.engines.get(data_webservice_code).invokeMethod(trade.getString("data_webservice_method_code"), request/*arglist.toArray()*/);
					if (rtn instanceof Map) {
						@SuppressWarnings("unchecked")
						Record t = new Record((Map<String, Object>)rtn);
						if (t.containsKey("error") && t.getBoolean("error")) {
							throw new Exception(t.getString("errmsg"));
						}
					}
					cs.callback(trade, rtn);
				} catch (Throwable ex) {
					log.error("WebService自动轮循服务执行交易异常, " + trade.getString("data_webservice_code") + "."
							+ trade.getString("data_webservice_method_code") + "[id=" + trade.getLong("id") + "]",
							ex);
					cs.saveError(trade, ex.getClass() + ": " + ex.getMessage());
				}
			}

			log.debug("Schedule Job[" + jobName + "]: end");
		} catch (Throwable ex) {
			result = "[异常]" + ex.getMessage();
			log.error("Schedule Job[" + jobName + "]: error, ", ex);
		} finally {
			// 仅仅记录错误运行日志
			//if (logs != null && !result.equals("[正常]")) {
				logs.put("end_time", new Date());
				logs.put("result", result);
				ClientService service = new ClientService();
				try {
					service.writeLog(logs);
				} catch (Exception e) {
					log.error("Schedule Job[" + jobName + "]: save schudle error log error, ", e);
				}
				logs = null;
			//}
			if (remove) {
				runnings.remove(jobName);
			}
		}
	}
}
