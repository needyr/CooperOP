package cn.crtech.cooperop.core.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.HttpClient;
import cn.crtech.cooperop.core.domain.RequestTask;
import cn.crtech.cooperop.core.domain.RunResult;
import cn.crtech.cooperop.core.service.AbstractExecuteTask;
import cn.crtech.precheck.client.ws.Engine;
import cn.crtech.ylz.ylz;

/**
 * 初始化任务
 * @author chenjunhong
 * 2021年1月28日
 */
public class InitPdc extends AbstractExecuteTask {

	private final String URL = SystemConfig.getSystemConfigValue("hospital_common", "data_pdc_url", "http://127.0.0.1:9100") + "/patupdatasync";

	@Override
	public boolean filter(RequestTask task) {
		boolean flag = false;
		String dType = (String)task.getFilter().get("d_type");
		String joins = (String)task.getFilter().get("d_type_joins");
		//是否执行患者数据初始化
		if("".equals(joins) || joins.indexOf(dType) >= 0) {
			flag = false;
		}else {
			flag = true;
			ylz.p("患者初始化：d_type不在审查范围内，不进行数据同步");
		}
		return flag;
	}

	@Override
	public RunResult execute(RequestTask task) {
		RunResult retVal = new RunResult();
		Map<String, Object> postdata = new HashMap<String, Object>();
		postdata.put("pat_data_type", "init");
		postdata.put("patient_id", task.getIn().get("patient_id"));
		postdata.put("visit_id", task.getIn().get("visit_id"));
		String code = GlobalVar.getSystemProperty("webservice.client");
		//实时同步任务 - pdc服务
		try {
			//HttpClient.post(URL, postdata);
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
		//本地更新任务
    	try {
    		//立即更新数据一次 - 目前已停用
    		Engine.engines.get(code).execMethod(task.getIn());
    		//调用初始化存储过程
    		//new PatService().hisInTmpProc(postdata);
    	} catch (Throwable e1) {
    		log.error(e1);
			e1.printStackTrace();
		}
		try {
			// 开启线程超时记录日志
			if(System.currentTimeMillis() - task.getAction() >= task.getTimeout()) {
				StringBuffer winfo = new StringBuffer();
				winfo.append(CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + ": "+ "用户ip：" + task.getIn().get("user_ip"));
				winfo.append(",初始化耗时较长，耗时[" + (System.currentTimeMillis() - task.getAction()) + "]ms,");
				//winfo.append("启动线程耗时：" + th_time + "ms," + "队列["+ patMap.size() +"/" + QUEUE_NUM +"],");
				winfo.append("参数：\n" + task.getIn().toString() + "\r\n");
				winfo.append("===============================================================================\n");
				String path = GlobalVar.getWorkPath()+"\\WEB-INF\\apps\\hospital_common\\ylzlog\\" + CommonFun.formatDate(new Date(), "yyyy-MM-dd")  + ".txt";
				File file = new File(path);
		         if(!file.exists()) {
		        	 file.createNewFile();
		        	 new FileOutputStream(file, true).write(winfo.toString().getBytes("utf-8") );
		         }else {
		        	 FileWriter writer = new FileWriter(path, true);
		        		writer.write(winfo.toString());
		        		writer.close();
		         }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" #InitPdc retVal -> " + retVal);
		return retVal;
	}
	
}
