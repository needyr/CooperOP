package cn.crtech.precheck.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.hospital_common.service.AuditDataReadyService;
import cn.crtech.cooperop.hospital_common.service.AutoCommonService;
import cn.crtech.precheck.EngineInterface;
import cn.crtech.ylz.YlzPost;
import cn.crtech.ylz.ylz;

public class OptionIFSServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	// 提交审核线程池
	ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	private Record myProduct = new Record();

	/**
	 * 1.审查后结束返回
	 * 2.开启审查线程后返回（不等待审查完成）
	 * 3.开启线程后，等待一段时间后返回
	 * 4.与3类似，但调用审查引擎审查
	 * other ： 直接返回
	 */
	public static short final_control = Short.parseShort(SystemConfig.getSystemConfigValue("hospital_common",
			"final_control", "4"));
	//public static int final_control = 4;

	private static String YC_AUDIT_CORE = SystemConfig.getSystemConfigValue("hospital_common",
			"yc.audit.engine", "http://127.0.0.1:9272/optionifs");

	public void init() throws ServletException {
		if( cachedThreadPool == null) {
			cachedThreadPool = Executors.newCachedThreadPool();
		}
	}

	private HashMap<String, Object> putDefaults(){
		HashMap<String, Object> def = new HashMap<String, Object>();
		def.put("state", "Y");
		def.put("use_flag", 1);
		def.put("flag", 1);
		def.put("id", "A");
		def.put("finish", false);
		return def;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		long req_start_time = System.currentTimeMillis();
		// 审查结果容器初始化
		Map<String, Object> respMap = putDefaults();
		// 提取请求参数
		HashMap<String, Object> reqParams = CommonFun.requestMap(req);
		reqParams.put("req_ip", getIp(req));
		if (final_control == 4){
			Map<String, Object> data = fmtParams(reqParams);
			data.put("ip", reqParams.get("req_ip"));
			Record params = new Record(data);
			Record ret ;
			switch (data.get("d_type").toString()){
				case "51":
					// DRGs 审核
					ret = callMethod("cn.crtech.cooperop.hospital_drgs.core.DrgCommon",
							"audit", params);
					if (ret != null){
						respMap.putAll(ret);
					}else {
						respMap.put("flag", "-9");
					}
					break;
				case "52":
					// 病案质控审核
					ret = callMethod("cn.crtech.cooperop.hospital_bazk.core.BAZK",
							"audit", params);
					ret.put("id", "bazk_"+ ret.get("id"));
					respMap.putAll(ret);
					break;
				default:
					respMap.put("connerr", false);
					cachedThreadPool.execute(
							()->{
								// reqParams.put("req_ip", getIpAddr(req));
								try {
									String jsonStr = YlzPost.post(YC_AUDIT_CORE, reqParams);
									Map<String, Object> map = CommonFun.json2Object(jsonStr, Map.class);
									if(!CommonFun.isNe(map)) {
										// ?建立药师端连接，发送前置数据?
										if(map.containsKey("ipc") && !CommonFun.isNe(map.get("ipc"))) {
											Map<String, Object> ipcInfo = (Map<String, Object>) map.remove("ipc");
											// 发送数据到药师端
											if("Y".equals(ipcInfo.get("ipctoyun"))) {
												EngineInterface.sendToYun(ipcInfo);
											}
										}
										respMap.putAll(map);
									}
								} catch (Throwable e) {
									respMap.put("connerr", true);
									log.error("调用远程服务失败", e);
								}
							}
					);
					// 等待审查完成
					long audit_start_time = System.currentTimeMillis();
					int wait = EngineInterface.LISTEN_TIME;
					for(int i = 0; i <= wait; i++) {
						try {
							if((Boolean) respMap.get("finish")) break;
							if((Boolean) respMap.get("connerr")) {
								ylz.p("yc audit core http post connect error ... [" + YC_AUDIT_CORE + "]");
								break;
							}
							if((System.currentTimeMillis() - audit_start_time)/100 >= wait) {
								ylz.p("audit not finish, but time out");
								break;
							}
							Thread.sleep(100);
						} catch (InterruptedException e) {
							log.error("访问审核中心出错！", e);
						}
					}
					respMap.remove("connerr");
					if((System.currentTimeMillis() - audit_start_time)/100 >= wait) {
						Map<String, Object> info = new HashMap<String, Object>();
						info.put("ip", reqParams.get("req_ip"));
						info.put("start_time", audit_start_time);
						String req_json = (String)reqParams.get("json");
						req_json = req_json.replaceAll("[\\t\\n\\r]", "");
						req_json = req_json.replaceAll("\\\\", "/");
						@SuppressWarnings("unchecked")
						Map<String, Object> p = CommonFun.json2Object(req_json, Map.class);
						p.remove("audit_source_fk");
						info.put("params", CommonFun.object2Xml(p));
						writeLog(info);
					}
					ylz.p("final_control /4 : audit and listen then return right now (use yc audit core)"
							+ "[max-wait=" + wait/10 + "s]");
			}
		}else {
			otherControl(final_control, reqParams, respMap);
		}
		respMap.remove("finish");
		ylz.p("final_control /" + final_control + " ：request in server for [" +
				(System.currentTimeMillis() - req_start_time) + "ms]，return :" + CommonFun.object2Json(respMap));
		back(resp, respMap);
	}

	private void back(HttpServletResponse resp, Map<String, Object> out){
		try {
			resp.setContentType("text/html; charset=UTF-8");
			resp.setHeader("Access-Control-Allow-Origin", "*");
			resp.getWriter().write(CommonFun.object2Json(out));
			resp.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 返回用IP地址
	private String getIp(HttpServletRequest request) {
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

	// 格式化请求参数
	private Map<String, Object> fmtParams(Map<String, Object> reqParams) {
		String req_json = (String)reqParams.get("json");
		req_json = req_json.replaceAll("[\\t\\n\\r]", "");
		req_json = req_json.replaceAll("\\\\", "/");
		@SuppressWarnings("unchecked")
		Map<String, Object> p = CommonFun.json2Object(req_json, Map.class);
		Map<String, Object> params = (Map<String, Object>) p.get("request");
		return params;
	}

	// 调用app 的方法
	private Record callMethod(String myClass, String myMethod, Record params) {
		Class<?> callerClass = null;
		Record data = null;
		try {
			callerClass = Class.forName(myClass);
			//  invoke(callerClass.getConstructor().newInstance(), params); 非静态需要实例
			data = (Record) callerClass.getMethod(myMethod, Record.class).invoke(null, params);
		} catch (Exception e) {
			log.error("common调用app方法异常", e);
		}
		return data;
	}

	private void writeLog(Map<String, Object> info) {
		// 记录超时
		StringBuffer sb = new StringBuffer();
		sb.append(CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + ": "+ "用户ip：" + info.get("ip"));
		sb.append(",审查耗时较长，耗时[" + (System.currentTimeMillis() - (long)info.get("start_time")) + "]ms, 参数： ");
		sb.append("\n");
		sb.append(info.get("params"));
		sb.append("==== \n");
		String path=GlobalVar.getWorkPath()+"\\WEB-INF\\apps\\hospital_common\\auditlog\\" +
				CommonFun.formatDate(new Date(), "yyyy-MM-dd")  + ".txt";
		File file=new File(path);
		try {
			if(!file.exists()) {
				file.createNewFile();
				new FileOutputStream(file,true).write(sb.toString().getBytes("utf-8") );
			}else {
				FileWriter writer = new FileWriter(path, true);
				writer.write(sb.toString());
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 审查
	private void ifsAudit(Map<String, Object> reqParams, Map<String, Object> respMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
		Long start = System.currentTimeMillis();
		String common_id = "";
		// 参数校验
		boolean pvpass = true;
		Map<String, Object> resultMap=new HashMap<String, Object>();
		try {
			// 参数校验 -----start
			// 判断是否空项目提交审查  start
			Map<String, Object> hmap = reqParams;
			// 去除请求串特殊字符，防止类型转换失败
			String req_json = (String)reqParams.get("json");
			req_json = req_json.replaceAll("[\\t\\n\\r]", "");
			req_json = req_json.replaceAll("\\\\", "/");
			@SuppressWarnings("unchecked")
			Map<String, Object> p = CommonFun.json2Object(req_json, Map.class);
			// 事后审查唯一标识（事前无该字段）
			Object audit_source_fk = p.remove("audit_source_fk");
			String reqXml = CommonFun.object2Xml(p);
			@SuppressWarnings("unchecked")
			Map<String, Object> params = (Map<String, Object>) p.get("request");
			// 事后审查标识
			Object is_after = params.get("is_after");
			Map<String, Object> rows = null;
			if(!CommonFun.isNe(params.get("rows"))) {
				rows = (Map<String, Object>) params.get("rows");
				if(rows.get("row") instanceof java.util.LinkedHashMap) {
					//在青浦医院中的空提交为只有一个row 它的内容为空
					Map<String, Object> row =  (Map<String, Object>) rows.get("row");
					if(CommonFun.isNe(row.get("p_key"))) {
						pvpass = false;
						ylz.p("判定为未开具医嘱空提交,不审查");
					}
				}
			}else {
				if(!"1".equals(is_after)) {
					pvpass = false;
					ylz.p("判定为未开具医嘱空提交,不审查");
				}
			}
			// 医保结算审查标识d_type = 5
			if("5".equals(params.get("d_type"))) {
				pvpass = true;
				ylz.p("判定为：来自医保结算的提交审查，空提交也审查，数据由存储过程提供");
			}
			// 参数校验 end
			if(pvpass) {
				Map<String, Object> info = CommonFun.object2Map(params.get("patient"));
				map.put("patient_id", info.get("id"));
				map.put("visit_id", info.get("visitid"));
				Map<String, Object> doctor_info = CommonFun.object2Map(params.get("doctor"));
				map.put("doctor_no", doctor_info.get("code"));
				//事后审查外键
				map.put("audit_source_fk", audit_source_fk);
				//产生公共主表审查记录
				//map.put("his_req", his_req);
				map.put("ip", reqParams.get("req_ip"));
				//++客户端程序版本号
				Object v = hmap.get("version");
				if(v != null) {
					map.put("im_version", v);
				}
				map.put("d_type", params.get("d_type"));
				map.put("dept_code", info.get("departcode"));
				map.put("is_after", is_after);
				Long start_insert_common = System.currentTimeMillis();
				AutoCommonService autoCommonService = new AutoCommonService();
				common_id = autoCommonService.insert(map);
				ylz.recordMsg(true, "mmid_" + common_id, "产生common_id, (在此之前消耗 "
						+ (System.currentTimeMillis() - start) + " ms)");
				ylz.recordMsg("mmid_" + common_id, "插入auto_common, (消耗 "
						+ (System.currentTimeMillis() - start_insert_common) + " ms)");
				/*插入诊断信息*/
				/*new InsertDiagnosisService().insert(params);*/
				map.put("common_id", common_id);
				map.put("req", params);
				resultMap.put("id", common_id);
				//调用审查
				map.put("dept_code", (String)info.get("departcode"));
				//2019-08-27 wang修改,增加数据准备方法
				new AuditDataReadyService().ready(map);
				ylz.recordMsg("mmid_" + common_id, "存储common患者信息，诊断信息");
				Map<String, Object> audit_result = new HashMap<String, Object>();
				if (!CommonFun.isNe(params.get("settlement_last_year"))){
					map.put("settlement_last_year", params.get("settlement_last_year"));
				}
				if (!CommonFun.isNe(params.get("bill_start_time"))){
					map.put("bill_start_time", params.get("bill_start_time"));
				}
				if (!CommonFun.isNe(params.get("bill_end_time"))){
					map.put("bill_end_time", params.get("bill_end_time"));
				}
				audit_result = EngineInterface.audit(map);
				map.clear();
				//此id用于demo访问结果页面传入参数 为common_id
				audit_result.put("id", common_id);
				map.put("common_id", common_id);
				map.put("state", audit_result.get("state"));
				map.put("end_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
				map.put("cost_time", System.currentTimeMillis() - start );
				map.put("demo_resp", CommonFun.object2Json(audit_result));
				map.put("clog", ylz.removeMsg("mmid_" + common_id));
				map.put("his_req", reqXml);
				autoCommonService.insertMx(map);
				if(!CommonFun.isNe(audit_result)) {
					respMap.putAll(audit_result);
				}
			}
		} catch (Throwable e) {
			log.error(e);
		} finally {
			respMap.put("finish", true);
		}
	}

	// 除了4 以外的其他控制方法
	private void otherControl(Short ctrlType, Map<String, Object> params, Map<String, Object> ret){
		if(ctrlType == 1) {
			ylz.p("final_control /1 : wait audit over then return result ");
			ifsAudit(params, ret);
		}else if(ctrlType == 2) {
			ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
			cachedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					// reqParams.put("req_ip", getIpAddr(req));
					ifsAudit(params, ret);
				}
			});
			ylz.p("final_control /2 : audit but return right now");
		}else if(ctrlType == 3) {
			ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
			cachedThreadPool.execute(new Runnable() {
				public void run() {
					// reqParams.put("req_ip", getIpAddr(req));
					ifsAudit(params, ret);
				}
			});
			// 等待审查完成
			long audit_start_time = System.currentTimeMillis();
			int wait = EngineInterface.LISTEN_TIME;
			for(int i = 0; i <= wait; i++) {
				try {
					if((Boolean)params.get("finish")) break;
					if((System.currentTimeMillis() - audit_start_time)/100 >= wait) {
						ylz.p("audit not finish, but time out");
						break;
					}
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if((System.currentTimeMillis() - audit_start_time)/100 >= wait) {
				Map<String, Object> info = new HashMap<String, Object>();
				info.put("ip", params.get("req_ip"));
				info.put("start_time", audit_start_time);
				info.put("params", CommonFun.json2Xml((String)params.get("json")));
				writeLog(info);
			}
			ylz.p("final_control /3 : audit and listen then return right now " + "[max-wait=" + wait/10 + "s]");
		}
	}
}
