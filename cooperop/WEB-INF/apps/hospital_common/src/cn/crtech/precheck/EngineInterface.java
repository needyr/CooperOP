package cn.crtech.precheck;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.hospital_common.service.ProductmanagetService;
import cn.crtech.cooperop.hospital_common.service.auditset.CheckDeptService;
import cn.crtech.cooperop.hospital_common.service.dict.ProductResultService;
import cn.crtech.cooperop.hospital_common.util.GetAuditClass;
import cn.crtech.cooperop.hospital_common.util.Util;
import cn.crtech.precheck.server.OptionIFSServlet;
import cn.crtech.ylz.YlzPost;
import cn.crtech.ylz.ylz;

public class EngineInterface {

	/** 所有装配产品[包含审查服务产品] */
	public static Map<String, String> allProduct = new ConcurrentHashMap<String, String>();
	/** 审查服务产品 */
	public static Map<String, String> interfaces = new ConcurrentHashMap<String, String>();
	/** 产品下参与审查的部门 */
	public static Map<String, Object> depts = new ConcurrentHashMap<String, Object>();
	/** 产品所有审查结果字典【key：产品code+结果状态 value:当前状态字典对象】 */
	public static Map<String, Object> mangerL = new ConcurrentHashMap<String, Object>();

	private static final String DTYPEJOINS = SystemConfig.getSystemConfigValue("hospital_imic", "d_type.join.audit", "");

	public static int LISTEN_TIME = Integer.parseInt(SystemConfig.getSystemConfigValue("hospital_common", "common.listen.time", "10")) * 10;

	/** 是否调用远程参与审核的部门同步方法 */
	private static boolean CALL_SYNC = true;

	/** 合理用药客户端版本 */
	/*
	public static Map<String, Object> version = new ConcurrentHashMap<String, Object>();//升级
	public static Map<String, Object> version_mx = new ConcurrentHashMap<String, Object>();//是否升级
	 */
	public static void init(){
		try {
			CALL_SYNC = false;
			//加载审查产品,部门,产品审查结果字典
			loadCheckProduct();
			loadCheckDept();
			loadProductResultDicts();
		} catch (Exception e) {e.printStackTrace();}
		try {
			IMUPCache.loadVersion();
			IMUPCache.loadVersionMx();
		} catch (Exception e) {e.printStackTrace();}
		//判断pdc,ptc是否启动
		String pdc = GlobalVar.getSystemProperty("pdc.autostart","0");
		String dtc = GlobalVar.getSystemProperty("dtc.autostart","0");
		String auditCore = GlobalVar.getSystemProperty("auditcore.autostart","0");
		boolean is_kill_cmd = false;
		try {
			if ("1".equals(pdc) && !Util.existsPort("9100")){
				String path = Util.getServerPath("a-pdc-server");
				Util.execCMD(path+"\\jdk1.8.0_51",path,"cmd /c start "+path+"\\bin\\startup.bat");
				is_kill_cmd = true;
				Thread.sleep(4000);
			}
		} catch (Exception e) {e.printStackTrace();}
		try {
			if ("1".equals(dtc) && !Util.existsPort("9000")){
				String path = Util.getServerPath("a-dtc-server");
				Util.execCMD(path+"\\jdk1.8.0_51",path,"cmd /c start "+path+"\\bin\\startup.bat");
				is_kill_cmd = true;
				Thread.sleep(4000);
			}
		} catch (Exception e) {e.printStackTrace();}
		try {
			if ("1".equals(auditCore) && !Util.existsPort("9272")){
				String path = Util.getServerPath("a-audit-core");
				Util.execCMD(path+"\\jdk1.8.0_51",path,"cmd /c start "+path+"\\bin\\startup.bat");
				is_kill_cmd = true;
				Thread.sleep(4000);
			}
		} catch (Exception e) {e.printStackTrace();}finally {
			if(is_kill_cmd){
				new Util().killProcess();
			}
		}
		//--------------------
	}

	/**
	 * @param req 医院传入的审查 请求参数
	 * @return 各个产品的综合审查结果
	 * @author yan
	 * @time 2018年11月27日
	 * @function  前置审方
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> audit(Map<String, Object> req){
		ylz.recordMsg("mmid_" + req.get("common_id"), "开始校验是否需要进行审核");
		Iterator<String> iter = interfaces.keySet().iterator();
		Map<String, Object> reMap = new HashMap<String, Object>();
		String d_type = ((Map<String, Object>)req.get("req")).get("d_type").toString();
		String is_after = "";
		while(iter.hasNext()){
			String code = iter.next();
			boolean is_check = isDeptjoinCheck(code + "_check_dept", (String)req.get("dept_code"));//该部门是否允许审查
			is_after = (String) ((Map<String, Object>)req.get("req")).get("is_after");//是否事后审查
			if("1".equals(is_after)) {//如果为事后审查，则不论科室是否参与审查，都执行审查
				String prods = (String) ((Map<String, Object>)req.get("req")).get("products");
				// 事后审查产品不为空，且不包含该产品时，不进行该产品审查
				if(CommonFun.isNe(prods) || prods.indexOf(code) >= 0) {
					is_check = true;
				}else {
					is_check = false;
				}
			}
			// 绵竹 医生开出院需要结算审查，要做开科控制
			if("5".equals(d_type)) {
				is_check = true;
			}

			if(!"".equals(DTYPEJOINS) && DTYPEJOINS.indexOf(d_type) < 0) {
				is_check = false;
			}
			ylz.recordMsg("mmid_" + req.get("common_id"), "结束校验，结果为:" + is_check);
			if(is_check) {
				new Thread(new Runnable() {
				    @Override
				    public void run() {
				    	try {
							/*Class<?> c = Class.forName("cn.crtech.precheck."+interfaces.get(code)+".InterfaceCenter");
							Method m = c.getMethod("audit",  Map.class);*/
				    		Method m = null;
				    		if("ipc".equals(interfaces.get(code))) {
				    			m = GetAuditClass.getInstance().ipc_audit_method;
				    		}
				    		if("hospital_imic".equals(interfaces.get(code))) {
				    			m = GetAuditClass.getInstance().imic_audit_method;
				    		}
							ylz.recordMsg("mmid_" + req.get("common_id"), "开启"+code+"审核线程,"+(m==null?"调用方法为空":""));
							if(m!=null) {
								reMap.put(code, m.invoke(null, req));
							}
						} catch (Exception e) {e.printStackTrace();}
				    }
				}).start();
			}else {
				Map<String, Object> nocheck = new HashMap<String, Object>();
				nocheck.put("state", "NOCHECK");
				reMap.put(code,nocheck);
				ylz.p("--- 未开科室或事后审查不包含该产品，不审查 ---");
			}
		}
		//监听审查是否结束
		Map<String, Object> finalRtn = new HashMap<String, Object>();
		int wts = LISTEN_TIME;
		if("5".equals(d_type) || "1".equals(is_after)) {
			wts = 4* wts;
		}
		ylz.recordMsg("mmid_" + req.get("common_id"), "common开始监听审查是否全部结束，时间为"+ wts/10 + "s");
		long listen_time_st = System.currentTimeMillis();
		try {
			for(int a = 0; a< wts; a++){
				if(reMap.size() == interfaces.size()) {break;}
				if((System.currentTimeMillis() - listen_time_st)/100 >= wts) {break;}
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ylz.recordMsg("mmid_" + req.get("common_id"), "common监听结束,开始结算最终审查结果");
		//---审查结果综合start
		finalRtn.put("state", "Y");
		finalRtn.put("use_flag", "1");
		String flag = "0" ;
		int Finalevel = 0 ;
		Map<String,Object> dictObj = null;
		try {
			Iterator<String> rtnIter = reMap.keySet().iterator();
			while(rtnIter.hasNext()) {
				String product = rtnIter.next();
				Map<String, Object> rtm = (Map<String, Object>) reMap.get(product);
				String state_tmp =  (String) rtm.get("state");
				Map<String,Object> dictObjTmp = (Map<String, Object>) mangerL.get(product+state_tmp);//审查状态对应的临时字典对象
				//审查结果优先级
				int level_tmp = (int) dictObjTmp.get("priority");
				if(Finalevel < level_tmp) {
					Finalevel = level_tmp;
					dictObj = dictObjTmp;
				}
				if("0".equals(flag)) {
			    	flag = (String) dictObjTmp.get("flag");
			    }else {
			    	flag = flag + (String) dictObjTmp.get("flag");
			    }
				if(!CommonFun.isNe(dictObj)) {
					finalRtn.put("use_flag", dictObj.get("use_flag"));
				    finalRtn.put("state", dictObj.get("final_state"));
				}
			}
		}catch(Exception ext){
			ext.printStackTrace();
		}
		ylz.recordMsg("mmid_" + req.get("common_id"), "comm结算最终审查结果，为" + finalRtn.get("state"));
		finalRtn.put("flag", flag);
		return finalRtn;
	}


	/**
	 * @param params auto_common 表id ：common_id
	 * @author yan 各个产品的审查结果展示数据
	 * @time 2018年11月27日
	 * @function 获取各个产品的审查结果展示数据
	 */
	public static Map<String, Object> getShowData(Map<String, Object> params){
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> initSt = new HashMap<String, Object>();
		List<String> prs = new ArrayList<String>();
		Iterator<String> iter = interfaces.keySet().iterator();
		while(iter.hasNext()){
			String code = iter.next();
			prs.add(code);
			Class<?> c;
			try {
				c = Class.forName("cn.crtech.precheck."+interfaces.get(code)+".InterfaceCenter");
				Method m = c.getMethod("getShowData",  Map.class);
				rtnMap.put(code, m.invoke(null, params));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (String p : prs) {
			try{
				initSt.put(p, ((Map<String, Object>)rtnMap.get(p)).get("state"));
			}catch(Exception e){
				continue;
			}

		}
		rtnMap.put("prts", CommonFun.object2Json(prs));
		rtnMap.put("initst", CommonFun.object2Json(initSt));
		rtnMap.put("mangerlv", CommonFun.object2Json(mangerL));
		return rtnMap;
	}

	/**
	 * @param params auto_common 表id ：common_id
	 * @author yan 各个产品的审查结果展示数据
	 * @time 2018年11月27日
	 * @function 获取各个产品的审查结果展示数据[仅用于事后审查]
	 */
	public static Map<String, Object> getAfterShowData(Map<String, Object> params){
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> initSt = new HashMap<String, Object>();
		List<String> prs = new ArrayList<String>();
		Iterator<String> iter = interfaces.keySet().iterator();
		while(iter.hasNext()){
			String code = iter.next();
			prs.add(code);
			Class<?> c;
			try {
				c = Class.forName("cn.crtech.precheck."+interfaces.get(code)+".InterfaceCenter");
				Method m = c.getMethod("getAfterShowData",  Map.class);
				rtnMap.put(code, m.invoke(null, params));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (String p : prs) {
			try{
				initSt.put(p, ((Map<String, Object>)rtnMap.get(p)).get("state"));
			}catch(Exception e){
				continue;
			}

		}
		rtnMap.put("prts", CommonFun.object2Json(prs));
		rtnMap.put("initst", CommonFun.object2Json(initSt));
		rtnMap.put("mangerlv", CommonFun.object2Json(mangerL));
		return rtnMap;
	}

	/**
	 * @param params
	 * @return
	 * @author yan
	 * @throws Exception
	 * @time 2018年11月27日
	 * @function 强制使用，将医嘱发送给药师审查
	 */
	public static Map<String, Object> mustDo(Map<String, Object> params) throws Exception{
		Iterator<String> iter = interfaces.keySet().iterator();//已开启审查的产品
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> req_tmp = new HashMap<String, Object>();
		while(iter.hasNext()){
			req_tmp.clear();
			req_tmp.putAll(params);
			String code = iter.next();
			new Thread(new Runnable() {
			    @Override
			    public void run() {
			    	try {
			    		Class<?> c = Class.forName("cn.crtech.precheck."+interfaces.get(code)+".InterfaceCenter");
						Method m = c.getMethod("mustDo",  Map.class);
						rtnMap.put(code, m.invoke(null, req_tmp));
					} catch (Exception e) {
						e.printStackTrace();
					}
			    }
			}).start();
		}
		Object object = params.get("d_type");
		int wait_time_ys = 0;
		int wait_time =0;
		if("1".equals(object)) {
			wait_time_ys = Integer.parseInt(SystemConfig.getSystemConfigValue("ipc", "wait_timeout_zy", "30"));
		}else if("2".equals(object)) {
			wait_time_ys = Integer.parseInt(SystemConfig.getSystemConfigValue("ipc", "wait_timeout_mz", "30"));
		}else if("3".equals(object)) {
			wait_time_ys = Integer.parseInt(SystemConfig.getSystemConfigValue("ipc", "wait_timeout_jz", "30"));
		}
		wait_time = wait_time_ys + 1;
		if (wait_time_ys == -1) {
			while(true) {
				Thread.sleep(1000);
				if(!CommonFun.isNe(rtnMap.get("ipc"))) {
					return rtnMap;
				}
			}
		}else {
			for (int i = 0; i < wait_time; i++) {
				Thread.sleep(1000);
				if(!CommonFun.isNe(rtnMap.get("ipc"))) {
					return rtnMap;
				}
			}
		}
		return rtnMap;
	}

	/**
	 * @param params
	 * @return
	 * @author yan
	 * @time 2018年11月27日
	 * @function 获取药师审查结果
	 */
	public static Map<String, Object> getPCResult(Map<String, Object> params){
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> finalRtn = new HashMap<String, Object>();
		Class<?> c;
		try {
			c = Class.forName("cn.crtech.precheck.ipc.InterfaceCenter");
			Method m = c.getMethod("getPCResult",  Map.class);
			rtnMap.put("ipc", m.invoke(null, params));
		} catch (Exception e) {
			e.printStackTrace();
		}
		finalRtn = (Map<String, Object>) rtnMap.get("ipc");
		return finalRtn;
	}

	/**
	 * @param params
	 * @return
	 * @author yan
	 * @time 2018年11月28日
	 * @function 获取患者审查历史 getPatientCheckHistory
	 */
	public static Map<String, Object> getPCHistory(Map<String, Object> params){
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> finalRtn = new HashMap<String, Object>();
		Class<?> c;
		try {
			c = Class.forName("cn.crtech.precheck.ipc.InterfaceCenter");
			Method m = c.getMethod("getPCHistory",  Map.class);
			rtnMap.put("ipc", m.invoke(null, params));
		} catch (Exception e) {
			e.printStackTrace();
		}
		finalRtn = (Map<String, Object>) rtnMap.get("ipc");
		return finalRtn;
	}

	/**
	 * @param params
	 * @return
	 * @author yan
	 * @time 2020年3月26日
	 * @function search orders check result 获取医嘱审查结果（用于外部接口，此为处理逻辑）
	 */
	public static Map<String, Object> searchOCResult(Map<String, Object> params){
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> finalRtn = new HashMap<String, Object>();
		Class<?> c;
		try {
			c = Class.forName("cn.crtech.precheck.ipc.InterfaceCenter");
			Method m = c.getMethod("searchOCResult",  Map.class);
			rtnMap.put("ipc", m.invoke(null, params));
		} catch (Exception e) {
			e.printStackTrace();
		}
		finalRtn = (Map<String, Object>) rtnMap.get("ipc");
		return finalRtn;
	}

	/**
	 * @param params mic_result_info_id info表id,doctor_choose 医生选择
	 * @author yanguozhi
	 * @throws Exception
	 * @time 2018年12月19日
	 * @function 记录可疑时 医生的选择
	 */
	public static void iMicChoose(Map<String, Object> params) throws Exception{
		invokes("hospital_imic", "hospital_imic", "iMicChoose", params);
	}

	/**
	 * @param params Map (patient_id ,visit_id, dept_code, doctor_no)
	 * @return isWarn 1弹窗 0不弹窗
	 * @throws Exception
	 * @author yanguozhi
	 * @time 2018年12月24日
	 * @function 判断是否应该弹出质控预警
	 */
	public static int isMQCWarn(Map<String, Object> params) {
		int iswarn= 0;
		if(!CommonFun.isNe(allProduct.get("hospital_mqc"))) {
			iswarn = 1;
			/* ygz.2020-10-19 注释（可删除）  修改为直接打开页面，页面改为多页签形式
			 * try { Map<String, Object> rtnMap = invokes("hospital_mqc", "hospital_mqc",
			 * "isMQCWarn", params); if(!CommonFun.isNe(rtnMap.get("hospital_mqc"))) {
			 * iswarn = (int) rtnMap.get("hospital_mqc"); } } catch (Exception e) {
			 * e.printStackTrace(); }
			 */
		}
		return iswarn;
	}

	/**
	 * @param params
	 * @return
	 * @author yan
	 * @time 2018年11月27日
	 * @function 医生双签名用药[药师返回医生决定后，医生选择确认用药]
	 */
	public static void sureAgain(Map<String, Object> params){
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> finalRtn = new HashMap<String, Object>();
		Class<?> c;
		try {
			c = Class.forName("cn.crtech.precheck.ipc.InterfaceCenter");
			Method m = c.getMethod("sureAgain",  Map.class);
			rtnMap.put("ipc", m.invoke(null, params));
		} catch (Exception e) {
			e.printStackTrace();
		}
		finalRtn = (Map<String, Object>) rtnMap.get("ipc");
	}

	// 发送数据到药师端 （审查相关数据）
	public static void sendToYun(Map<String, Object> params){
		try {
			Class<?> c = Class.forName("cn.crtech.precheck.ipc.InterfaceCenter");
			Method m = c.getMethod("sendToYun",  Map.class);
			m.invoke(null, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param params
	 * @return
	 * @author yan
	 * @time 2018年11月28日
	 * @function 获取该患者的点评结果数据
	 */
	public static Map<String, Object> getCommentResult(Map<String, Object> params){
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> finalRtn = new HashMap<String, Object>();
		Class<?> c;
		try {
			c = Class.forName("cn.crtech.precheck.ipc.InterfaceCenter");
			Method m = c.getMethod("getCommentResult",  Map.class);
			rtnMap.put("ipc", m.invoke(null, params));
		} catch (Exception e) {
			e.printStackTrace();
		}
		finalRtn = (Map<String, Object>) rtnMap.get("ipc");
		return finalRtn;
	}

	/**
	 * @param params
	 * @return
	 * @author yan
	 * @time 2018年11月28日
	 * @function 获取该患者的医嘱及点评信息
	 */
	public static Map<String, Object> getCommentOrders(Map<String, Object> params){
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> finalRtn = new HashMap<String, Object>();
		Class<?> c;
		try {
			c = Class.forName("cn.crtech.precheck.ipc.InterfaceCenter");
			Method m = c.getMethod("getCommentOrders",  Map.class);
			rtnMap.put("ipc", m.invoke(null, params));
		} catch (Exception e) {
			e.printStackTrace();
		}
		finalRtn = (Map<String, Object>) rtnMap.get("ipc");
		return finalRtn;
	}

	public static Map<String, Object> queryImicType(Map<String, Object> params) throws Exception {
		return invokes("hospital_imic", "hospital_imic", "queryImicType", params);
	}

	public static Map<String, Object> queryImicDtl(Map<String, Object> params) throws Exception {
		return (Map<String, Object>) invokes("hospital_imic", "hospital_imic", "queryImicDtl", params).get("hospital_imic");
	}

	/**
	 * @param params auto_common 表id ：common_id
	 * @author yan
	 * @throws Exception
	 * @time 2018年11月27日
	 * @function 返回调整或者其他仅改变产品审查主表状态的操作
	 */
	public static void backAdjust(Map<String, Object> params) throws Exception{
		invokes(interfaces, "backAdjust", params);
	}

	/**
	* 加载审查产品
	*/
	public static void loadCheckProduct() throws Exception {
		Map<String, Object> pmap =  new ProductmanagetService().loadCheckProduct(new HashMap<String, Object>());
		@SuppressWarnings("unchecked")
		List<Record> product_list = (List<Record>) pmap.get("check_product");
		interfaces.clear();
		allProduct.clear();
		if(!CommonFun.isNe(product_list)) {
			for (Record p : product_list) {
				if("1".equals(p.get("is_check_server"))) {
					interfaces.put(p.getString("code"), p.getString("interface_url"));
				}
				if( p.get("interface_url") != null) {
					allProduct.put(p.getString("code"), p.getString("interface_url"));
				}
			}
		}
	}

	/**
	* 加载参与审查的部门
	*/
	public static void loadCheckDept() throws Exception {
		Map<String, Object> rtnMap = new CheckDeptService().loadCheckDept(null);
		depts.clear();
		depts.putAll(rtnMap);
		try {
			if(OptionIFSServlet.final_control == 4) {
				if(CALL_SYNC){
					String YC_AUDIT_ENGINE = SystemConfig.getSystemConfigValue("hospital_common",
							"hyt_dll_audit_address", "http://127.0.0.1:9272");
					YlzPost.post(YC_AUDIT_ENGINE+"/loadcheckdept", null);
				}
				CALL_SYNC = true;
			}
		} catch (Throwable e) {
			e.printStackTrace();
			log.info("加载审查服务参与审查的部门错误!可能为服务访问异常");
		}
	}

	/**
	* 加载唯一IP客户端程序版本号
	*/
	/*public static void loadVersion() throws Exception {
		List<Record> resultset = new VersionControlClientService().query(null).getResultset();
		version.clear();
		for (Record record : resultset) {
			version.put(record.getString("ip_address"),record);
		}
	}

	*//**
	* 加载所有客户端程序版本号
	*//*
	public static void loadVersionMx() throws Exception {
		List<Record> resultset = new VersionControlClientService().query_mx(null).getResultset();
		version_mx.clear();
		for (Record record : resultset) {
			version_mx.put(record.getString("p_key"),record);
		}
	}

	*//**
	* 增加唯一IP客户端程序升级内容
	*//*
	public static void addVersion(Map<String, Object> params) throws Exception {
		version.put((String)params.get("ip_address"), params);
	}

	*//**
	* 增加所有客户端程序升级内容
	*//*
	public static void addVersionMx(Map<String, Object> params) throws Exception {
		version_mx.put((String)params.get("p_key"), params);
	}

	*//**
	* 更新唯一IP客户端程序升级内容
	*//*
	public static void updateVersion(Map<String, Object> params) throws Exception {
		Object object = version.get((String)params.get("ip_address"));
		Map<String, Object> map = new HashMap<String, Object>();
		if(object != null) {
			Map<String, Object> new_version = (Map<String, Object>) object;
			Iterator<Entry<String, Object>> entries = params.entrySet().iterator();
			while(entries.hasNext()){
			    Entry<String, Object> entry = entries.next();
			    String key = entry.getKey();
			    String value = (String) entry.getValue();
			    new_version.put(key, value);
			}
			version.put((String)params.get("ip_address"), new_version);
		}
	}

	*//**
	* 更新所有客户端程序升级内容
	*//*
	public static void updateVersionMx(Map<String, Object> params) throws Exception {
		Object object = version_mx.get((String)params.get("p_key"));
		Map<String, Object> map = new HashMap<String, Object>();
		if(object != null) {
			Map<String, Object> new_version = (Map<String, Object>) object;
			Iterator<Entry<String, Object>> entries = params.entrySet().iterator();
			while(entries.hasNext()){
			    Entry<String, Object> entry = entries.next();
			    String key = entry.getKey();
			    String value = (String) entry.getValue();
			    new_version.put(key, value);
			}
			version_mx.put((String)params.get("p_key"), new_version);
		}
	}*/

	/** 加载产品审查结果字典 */
	public static void loadProductResultDicts() throws Exception {
		Map<String,Object> m = new HashMap<>();
		List<Record> rtnList = new ProductResultService().query(m).getResultset();//各产品审查结果等级
		mangerL.clear();
		for(Record obj : rtnList) {//循环加载每个状态的字典数据
			Map<String,Object> info = new HashMap<>();
			info.put("priority", obj.getInt("priority"));//结果优先级
			info.put("final_state", obj.getString("final_state"));//最终状态
			info.put("use_flag", obj.getString("use_flag"));//能否用药
			info.put("sql_record_flag", obj.getString("sql_record_flag"));//数据库记录flag
			info.put("flag", obj.getString("flag"));//审查说明flag
			info.put("remark", obj.getString("remark"));//审查说明
			//加载产品审查字典 key:产品名+管理状态 value: 详情map
			mangerL.put(obj.getString("product_code")+obj.getString("manage_state"), info);
		}
	}
	//单个线程访问
	public static Map<String, Object> invokes(Map<String, String> product, String method, Map<String, Object> req) throws Exception {
		Iterator<String> iter = product.keySet().iterator();
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> req_tmp = new HashMap<String, Object>();
		while(iter.hasNext()){
			req_tmp.clear();
			req_tmp.putAll(req);
			String code = iter.next();
			Class<?> c;
			try {
				c = Class.forName("cn.crtech.precheck."+product.get(code)+".InterfaceCenter");
				Method m = c.getMethod(method,  Map.class);
				rtnMap.put(code, m.invoke(null, req_tmp));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return rtnMap;
	}

	/**
	 * @param product 产品 (必须)
	 * @param method 方法名 (必须)
	 * @param req 请求参数 (必须)
	 * @return Map 返回值(各个产品综合)
	 * @throws Exception
	 * @author yanguozhi
	 * @time 2018年12月13日
	 * @function 访问产品接口通用方法
	 */
	public static Map<String, Object> invokes2(Map<String, String> product, String method, Map<String, Object> req) throws Exception {
		Iterator<String> iter = product.keySet().iterator();
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> req_tmp = new HashMap<String, Object>();
		while(iter.hasNext()){
			req_tmp.clear();
			req_tmp.putAll(req);
			String code = iter.next();
			new Thread(new Runnable() {
			    @Override
			    public void run() {
			    	try {
			    		Class<?> c = Class.forName("cn.crtech.precheck."+product.get(code)+".InterfaceCenter");
						Method m = c.getMethod(method,  Map.class);
						rtnMap.put(code, m.invoke(null, req_tmp));
					} catch (Exception e) {
						e.printStackTrace();
					}
			    }
			}).start();
		}
		return rtnMap;
	}

	public static Map<String, Object> invokes(String product,String readurl, String method, Map<String, Object> req) throws Exception {
		Map<String, String> singleP = new HashMap<String, String>();
		singleP.put(product, readurl);
		return invokes(singleP, method, req);
	}

	/**
	 * @param product 产品code
	 * @param dept 部门
	 * @return boolean 是否参与审查
	 * @author yan
	 * @time 2018年11月26日
	 * @function 判断该部门是否参与该产品的审查
	 */
	public static boolean isDeptjoinCheck (String product, String dept) {
		boolean is_check = false ;
		try {
			@SuppressWarnings("unchecked")
			List<Record> deptlist = (List<Record>) depts.get(product);
			for (Record dept_tmp : deptlist) {
				if(dept.equals(dept_tmp.get("dept_code"))) {
					is_check =  true;
					break;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return is_check;
	}

	//获取单次审查相关时间
	public static Map<String, Object> getAuditTime(Map<String, Object> params){
		Map<String, Object> resp= new HashMap<String, Object>();
		try {
			Class<?> c = Class.forName("cn.crtech.precheck.ipc.InterfaceCenter");
			Method m = c.getMethod("auditTimeInfo",  Map.class);
			resp.put("audit", m.invoke(null, params));
		} catch (Exception e) {e.printStackTrace();}
		return resp;
	}

}
