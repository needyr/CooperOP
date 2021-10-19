package cn.crtech.precheck.domain.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.core.domain.RequestTask;
import cn.crtech.cooperop.core.domain.RunResult;
import cn.crtech.cooperop.core.service.AbstractExecuteTask;
import cn.crtech.precheck.EngineInterface;
import cn.crtech.ylz.YlzPost;
import cn.crtech.ylz.ylz;

/**
 * 核心审核任务，包括：合理用药、医保审核，需要转发请求到核心审核服务中执行
 * @author chenjunhong
 * 2021年2月3日
 */
public class AuditCore extends AbstractExecuteTask {

	public static String YC_AUDIT_ENGINE = SystemConfig.getSystemConfigValue("hospital_common", "yc.audit.engine", "http://127.0.0.1:9272/optionifs");
	
	@SuppressWarnings("unchecked")
	public RunResult execute(RequestTask task) {
		//原始请求参数
		Map<String, Object> reqParams = task.getOrigin();
		reqParams.put("req_ip", task.getControl().get("ip"));
		//核心审核服务原执行过程 - 先保留
		RunResult retVal = new RunResult();
		retVal.put("connerr", false);
    	try {
    		//注：这里的审查服务，在部署时需要设置好连接超时时间，所以这里启用了新的线程呢，是为了控制响应时间
    		String rtnJson = YlzPost.post(YC_AUDIT_ENGINE, reqParams);
    		Map<String, Object> map = CommonFun.json2Object(rtnJson, Map.class);
    		if(!CommonFun.isNe(map)) {
    			//建立药师端连接，发送前置数据
    			if(map.containsKey("ipc") && !CommonFun.isNe(map.get("ipc"))) {
    				Map<String, Object> ipcInfo = (Map<String, Object>) map.remove("ipc");
    				// 发送数据到药师端
    				if("Y".equals(ipcInfo.get("ipctoyun"))) {
    					EngineInterface.sendToYun(ipcInfo);
    				}
    			}
    			retVal.putAll(map);
    		}
		} catch (Throwable e) {
			ylz.p("yc audit engine http post connect error ... [" + YC_AUDIT_ENGINE + "]");
			retVal.put("connerr", true);	//连接失败
			e.printStackTrace();
		}
    	/*
    	原超时处理，待定
    	retVal.remove("connerr");
		if((System.currentTimeMillis() - task.getAction())/100 >= task.getTimeout()) {
			Map<String, Object> info = new HashMap<String, Object>();
			info.put("ip", ip);
			info.put("start_time", task.getAction());
			info.put("params", CommonFun.json2Xml((String)reqParams.get("json")));
			writeLog(info);
		}
		ylz.p("final_control /4 : aduit and listen then return right now (use yc audit core)" + "[max-wait=" + task.getTimeout()/1000 + "s]");
		*/
		log("#AuditCore retVal -> " + retVal);
		return retVal;
	}
	
	protected void writeLog(Map<String, Object> info) {
		// 记录超时
		StringBuffer winfo = new StringBuffer();
		winfo.append(CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + ": "+ "用户ip：" + info.get("ip"));
		winfo.append(",审查耗时较长，耗时[" + (System.currentTimeMillis() - (long)info.get("start_time")) + "]ms, 参数： ");
		winfo.append("\n");
		winfo.append(info.get("params"));
		winfo.append("==== \n");
		String path=GlobalVar.getWorkPath()+"\\WEB-INF\\apps\\hospital_common\\auditlog\\" + CommonFun.formatDate(new Date(), "yyyy-MM-dd")  + ".txt";
		File file=new File(path);
    	try {
    		 if(!file.exists()) {
	        	 file.createNewFile();
	        	 new FileOutputStream(file,true).write(winfo.toString().getBytes("utf-8") );
	         }else {
	        	 FileWriter writer = new FileWriter(path, true);
	        		writer.write(winfo.toString());
	        		writer.close();
	         }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
