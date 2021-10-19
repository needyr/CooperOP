package cn.crtech.cooperop.core.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.core.domain.RequestTask;
import cn.crtech.cooperop.core.domain.RunResult;
import cn.crtech.cooperop.core.service.AbstractExecuteTask;
import cn.crtech.precheck.EngineInterface;

/**
 * 本地初始化任务
 * @author chenjunhong
 * 2021年1月28日
 */
public class InitMqc extends AbstractExecuteTask {

	@Override
	public RunResult execute(RequestTask task) {
		RunResult retVal = new RunResult();
		Map<String, Object> tjMap = new HashMap<String, Object>();
		tjMap.put("doctor_no", task.getIn().get("doctor_no"));
		tjMap.put("dept_code", task.getIn().get("dept_code"));
		tjMap.put("patient_id", task.getIn().get("patient_id"));
		tjMap.put("visit_id", task.getIn().get("visit_id"));
		tjMap.put("name", task.getIn().get("name"));
		Calendar cal = Calendar.getInstance();
		tjMap.put("months", cal.get(Calendar.MONTH )+1);
		tjMap.put("years", cal.get(Calendar.YEAR));
		//判断是否应该打开质控界面:是否装配该产品->是否有存在需要显示的质控项目 ->返回弹出标志和访问链接
		int iswork = 0;
		try {
			iswork = EngineInterface.isMQCWarn(tjMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//返回内容
		StringBuffer respUrl = new StringBuffer();
		if(iswork == 1) {
			//respUrl.append("/w/hospital_mqc/earlywarn/mqc.html");	ygz.2020-10-19 修改为多页签页面
			respUrl.append("/w/hospital_common/patient/pinfos.html");
			respUrl.append("?doctor_no=" + tjMap.get("doctor_no"));
			respUrl.append("&dept_code=" + tjMap.get("dept_code"));
			respUrl.append("&patient_id=" + tjMap.get("patient_id"));
			respUrl.append("&visit_id=" + tjMap.get("visit_id"));
			respUrl.append("&patient_name=" + tjMap.get("name"));
			respUrl.append("&months=" + tjMap.get("months"));
			respUrl.append("&years=" + tjMap.get("years"));
		}else {
			respUrl.append("N");
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
		//返回值
		retVal.put("respUrl", respUrl.toString());
		log(" #InitMqc retVal -> " + retVal);
		return retVal;
	}

}
