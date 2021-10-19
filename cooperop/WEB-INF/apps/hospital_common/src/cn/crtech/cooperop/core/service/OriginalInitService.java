package cn.crtech.cooperop.core.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.HttpClient;
import cn.crtech.cooperop.core.domain.RequestTask;
import cn.crtech.precheck.EngineInterface;
import cn.crtech.precheck.client.ws.Engine;
import cn.crtech.precheck.server.PrescripionInitServlet;
import cn.crtech.ylz.ylz;

/**
 * 原初始化业务实现服务 - 保留原有的业务实现流程，通过配置实现新旧服务的切换，备用
 * @author chenjunhong
 * 2021年1月29日
 */
public class OriginalInitService extends AbstractExecuteService {

	private final String DATAINIT = SystemConfig.getSystemConfigValue("hospital_common", "data_pdc_url", "http://127.0.0.1:9100") + "/patupdatasync";
	private final String DTYPEJOINS = SystemConfig.getSystemConfigValue("hospital_common", "d_type.join.audit", "");
	
	public void prepare(RequestTask task) {
	}

	@Override
	public boolean filter(RequestTask task) {
		return false;
	}
	
	protected void execute(RequestTask task) {
		//保留原有的初始化执行流程 - PrescripionInitServlet
		/*
		HttpServletRequest req = task.getRequest();
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		long st_time = System.currentTimeMillis();
		HashMap<String, Object> m = CommonFun.requestMap(req);
		
		// --- start 处理xml串中值的回车符号
		String req_json = (String)m.get("json");
		req_json = req_json.replaceAll("[\\t\\n\\r]", "");
		req_json = req_json.replaceAll("\\\\", "/");
		Map<String, Object> p = CommonFun.json2Object(req_json, Map.class);
		// --- end 处理xml串中值的回车符号
		*/
		// 完整的参数
		Map<String, Object> params = task.getParams();
		
		// 实际使用参数
		@SuppressWarnings("unchecked")
		HashMap<String, Object> map = (HashMap<String, Object>) params.get("patient");
		map.put("p_type", params.get("p_type"));
		map.put("d_type", params.get("d_type"));
		map.put("doctor_no", ((Map<String, String>)params.get("doctor")).get("code"));
		map.put("patient_id", (String)map.get("id"));
		map.put("visit_id", (String)map.get("visitid"));
		map.put("user_ip", getIpAddr(task.getRequest()));
		String respUrl = "";
		if(!CommonFun.isNe(map.get("doctor_no"))) {
			Record pat = new Record();
			pat.put("patient_id", map.get("id"));
			pat.put("visit_id",  map.get("visitid"));
			PrescripionInitServlet.DOCTORMPAT.put((String)map.get("doctor_no"), pat);
		}
		if(CommonFun.isNe(map.get("patient_id"))){
			map.put("result", "err");
			map.put("message", "必须病人id【patient_id】");
		}else {
			respUrl = patInitData(map, task.getStart());
		}
		task.setRtnJson(respUrl);
	}
	
	public String patInitData(Map<String, Object> map, long st_time) {
		StringBuffer respUrl = new StringBuffer();
		if("".equals(DTYPEJOINS) || DTYPEJOINS.indexOf((String)map.get("d_type")) >= 0) {
			//if(patMap.size() <= QUEUE_NUM) {
				//patMap.put((String)map.get("patient_id"), "1");
				Map<String, Object> postdata = new HashMap<String, Object>();
				// --- 数据初始化
				postdata.put("pat_data_type", "init");
				postdata.put("patient_id", map.get("patient_id"));
				postdata.put("visit_id", map.get("visit_id"));
				String code = GlobalVar.getSystemProperty("webservice.client");
				new Thread(new Runnable() {
				    @Override
				    public void run() {
				    	System.out.println("当前线程：" + Thread.currentThread().getName());
				    	try {
				    		//先屏蔽初始化的实时数据同步处理
							HttpClient.post(DATAINIT, postdata);
						} catch (Throwable e1) {
							e1.printStackTrace();
						}
				    	try {
				    		Engine.engines.get(code).execMethod(map);//立即更新数据一次
				    		//调用初始化存储过程
				    		//new PatService().hisInTmpProc(postdata);
				    	} catch (Throwable e1) {
				    		log.error(e1);
							e1.printStackTrace();
						}
				    }
				}).start();
				//patMap.remove((String)map.get("patient_id"));
				//ylz.p("患者初始化：队列[" + patMap.size() + "/"+ QUEUE_NUM +"]");
			//}else {
				//ylz.p("患者初始化：超过控制并发数["+ QUEUE_NUM +"]，不进行数据同步");
			//}
		}else {
			ylz.p("患者初始化：d_type不在审查范围内，不进行数据同步");
		}
		long th_time = System.currentTimeMillis() - st_time;
		map.put("result", "success");
		//判断是否应该打开质控界面:是否装配该产品->是否有存在需要显示的质控项目 ->返回弹出标志和访问链接
		Map<String, Object> tjMap = new HashMap<String, Object>();
		tjMap.put("doctor_no", map.get("doctor_no"));
		tjMap.put("dept_code", map.get("departcode"));
		tjMap.put("patient_id", map.get("id"));
		tjMap.put("visit_id", map.get("visitid"));
		Calendar cal = Calendar.getInstance();
		tjMap.put("months", cal.get(Calendar.MONTH )+1);
		tjMap.put("years", cal.get(Calendar.YEAR));
		int iswork = 0;
		try {
			iswork = EngineInterface.isMQCWarn(tjMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(iswork == 1) {
			//respUrl.append("/w/hospital_mqc/earlywarn/mqc.html"); ygz.2020-10-19 修改为多页签页面
			respUrl.append("/w/hospital_common/patient/pinfos.html");
			respUrl.append("?doctor_no=" + tjMap.get("doctor_no"));
			respUrl.append("&dept_code=" + tjMap.get("dept_code"));
			respUrl.append("&patient_id=" + tjMap.get("patient_id"));
			respUrl.append("&visit_id=" + tjMap.get("visit_id"));
			respUrl.append("&patient_name=" + map.get("name"));
			respUrl.append("&months=" + tjMap.get("months"));
			respUrl.append("&years=" + tjMap.get("years"));
		}else {
			respUrl.append("N");
		}
		
		try {
			// 开启线程超时记录日志
			if(System.currentTimeMillis() - st_time >= 100) {
				StringBuffer winfo = new StringBuffer();
				winfo.append(CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + ": "+ "用户ip：" + map.get("user_ip"));
				winfo.append(",初始化耗时较长，耗时[" + (System.currentTimeMillis() - st_time) + "]ms,");
				//winfo.append("启动线程耗时：" + th_time + "ms," + "队列["+ patMap.size() +"/" + QUEUE_NUM +"],");
				winfo.append("参数：\n" + map.toString() + "\r\n");
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
		return respUrl.toString();
	}
	
	@Override
	public void decorate(RequestTask task) {
		//组织返回数据
		String rtnJson = task.getRtnJson();
		HttpServletResponse resp = task.getResponse();
		resp.setContentType("text/html; charset=UTF-8");
		// bs 项目跨域
		if(!CommonFun.isNe(task.getOrigin().get("isbs"))) {
			resp.setHeader("Access-Control-Allow-Origin", "*");
			Map<String, Object> bsRtn = new HashMap<String, Object>();
			if(CommonFun.isNe(rtnJson) || "N".equals(rtnJson.toString())) {
				bsRtn.put("isopen", "0");
			}else {
				bsRtn.put("isopen", "1");
			}
			bsRtn.put("url", rtnJson);
			task.setRtnJson(CommonFun.object2Json(bsRtn));
		}
	}

	public String getIpAddr(HttpServletRequest request) {
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
}
