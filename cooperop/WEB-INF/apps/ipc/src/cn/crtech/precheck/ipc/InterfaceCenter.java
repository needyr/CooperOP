package cn.crtech.precheck.ipc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.ipc.service.AuditResultService;
import cn.crtech.cooperop.ipc.service.AutoAuditService;
import cn.crtech.cooperop.ipc.service.CheckaAndCommentService;
import cn.crtech.cooperop.ipc.service.SearchRResultService;
import cn.crtech.precheck.ipc.audit_def.impl.TPNImpl;
import cn.crtech.precheck.ipc.dao.DataDao;
import cn.crtech.precheck.ipc.service.DataService;
import cn.crtech.precheck.ipc.service.impl.HYTDllAuditServiceImpl;
import cn.crtech.precheck.ipc.ws.client.Client;
import cn.crtech.precheck.ipc.ws.client.Connection;
import cn.crtech.ylz.ylz;
public class InterfaceCenter {
	
	/** 合理用药审查最大等到时间 */
	private static int maxtime = 10 * Integer.parseInt(SystemConfig.getSystemConfigValue("ipc", "time_waitsckeck", "6")) + 1; 
	
	/** 是否提交第三方审查引擎 */
	public static Boolean IS_SUBMIT =  Boolean .parseBoolean(GlobalVar.getSystemProperty("ipc.post.Tengine", "true"));
	
	/**
	 * 合理用药审查
	 */
	public static Map<String, Object> audit(Map<String, Object> req){
		log.debug("**********合理用药审查开始**********");
		Map<String, Object> params = (Map<String, Object>) req.get("req");
		Long start = System.currentTimeMillis();
		String common_id = (String) req.get("common_id");
		
		
		String id = "0";
		String state = "HL_Y";
		Object request = new Object();
		String doctor_no = null;
		Object patient_id = null;
		Object visit_id = null;
		Map<String, Object> audit_def_data = new HashMap<String, Object>();
		try {
			//拼装审查参数
			//Map<String, Object> reMap = new DataService().save_hyt(params,common_id);
			HYTDllAuditServiceImpl hytDllAuditServiceImpl = new HYTDllAuditServiceImpl();
			Map<String, Object> reMap = hytDllAuditServiceImpl.joinParams(params, common_id);
			id = (String)reMap.get("auto_audit_id");
			ylz.recordMsg("mmid_" + common_id, "ipc合理用药审查auto_audit_id="+id);
			request = reMap.get("request");
			//获取自定义审查需要的数据, 注意: 需要处理好数据后再去拿数据
			audit_def_data = hytDllAuditServiceImpl.get_audit_def_data(id);
			patient_id = reMap.get("patient_id");
			visit_id = reMap.get("visit_id");
			doctor_no = reMap.get("doctor_no").toString();
		} catch (Exception e1) {
			log.error(e1);
		}
		
		try {
			//调用自动审查接口
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("auto_audit_id", id);
			m.put("common_id", common_id);
			m.put("is_after", params.get("is_after"));
			params.put("auto_audit_id", id);
			state = audit_hlyy(request, m, audit_def_data);//new EngineHYT().execMethod(request, m);
		} catch (Exception e) {
			log.error(e);
			//TODO 调用卓远失败保存日志
		}
		
		//返回结果
		//STATE Y/N 推荐保存医嘱/不保存
		//FLAG 0/1/2/3/4 在审查结果页面使用
		//FLAG：0 标识自动审查通过，1自动审查不通过，取消医嘱;2自动审查不通过、强行用药人工审查超时；3自动审查不通过、强行用药人工审查通过；4自动审查不通过，强行用药人工审查不通过）
		Map<String, Object> map = new HashMap<String, Object>();
		if("HL_N".equals(state)){
			map.clear();
			map.put("id", id);
			map.put("state", state);
			map.put("use_flag", "0");
			map.put("flag", "8");
		}else{
			//调用人工审查接口
			map.clear();
			map.put("id", id);
			map.put("patient_id", patient_id);
			map.put("visit_id", visit_id);
			try {
				new DataService().updateForYXK(map);
				if("1".equals(SystemConfig.getSystemConfigValue("ipc", "pass_toyun","0"))) {
					Client.sendPrescription(doctor_no, map);
				}
			} catch (Exception e) {
				log.error(e);
			}
			map.clear();
			map.put("id", id);
			map.put("state", state);
			map.put("use_flag", "1");
			map.put("flag", state.equals("HL_Y")?"0":"7");
		}
		map.put("flag_name", "ipcflag");
		log.debug("===["+id+"]合理用药["+state+"],耗时:[" + ( System.currentTimeMillis()- start) + "]===");
		return map;
	}
	

	/**
	 * @param params
	 * @return 
	 * @author yan
	 * @time 2020年3月26日
	 * @function search orders check result 获取医嘱审查结果（用于外部接口，此为处理逻辑）
	 */
	public static Map<String, Object> searchOCResult(Map<String, Object> params) throws Exception{
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		String state = "Y" ; // 默认全通过
		rtnMap.put("msg", "run ok !");
		String search_id = CommonFun.getITEMID();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> patient = (Map<String, Object>) params.get("patient");
		rtnMap.put("id", search_id);
		Map<String, Object> rows = (Map<String, Object>) params.get("rows");
		if(rows.get("row") instanceof java.util.List) {
			List<Map<String, Object>> rowlist = (List<Map<String, Object>>) rows.get("row");
			for (Map<String, Object> row : rowlist) {
				Map<String, Object> tmp = new HashMap<String, Object>();
				tmp.put("search_id", search_id);
				tmp.put("patient_id", patient.get("id"));
				tmp.put("visit_id", patient.get("visitid"));
				tmp.put("row_pkey", row.get("p_key"));
				tmp.put("group_id", row.get("group_id"));
				tmp.put("order_no", row.get("order_no"));
				tmp.put("order_no", row.get("order_no"));
				tmp.put("order_sub_no", row.get("order_sub_no"));
				tmp.put("repeat_indicator", row.get("repeat_indicator"));
				tmp.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				//2021年4月6日13:38:02  雷健 添加
				tmp.put("order_text",row.get("order_text"));
				tmp.put("dosage",row.get("dosage"));
				tmp.put("administration",row.get("administration"));
				tmp.put("frequency",row.get("frequency"));
				tmp.put("start_date_time",row.get("start_date_time"));
				tmp.put("doctor",row.get("doctor"));
				tmp.put("order_status",row.get("order_status"));
				tmp.put("dosage_units",row.get("dosage_units"));
				list.add(tmp);
			}
		}else if(rows.get("row") instanceof java.util.Map) {
			Map<String, Object> row = (Map<String, Object>) rows.get("row");
			Map<String, Object> tmp = new HashMap<String, Object>();
			tmp.put("search_id", search_id);
			tmp.put("patient_id", patient.get("id"));
			tmp.put("visit_id", patient.get("visitid"));
			tmp.put("row_pkey", row.get("p_key"));
			tmp.put("group_id", row.get("group_id"));
			tmp.put("order_no", row.get("order_no"));
			tmp.put("order_sub_no", row.get("order_sub_no"));
			tmp.put("repeat_indicator", row.get("repeat_indicator"));
			tmp.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			//2021年4月6日13:38:02  雷健 添加
			tmp.put("order_text",row.get("order_text"));
			tmp.put("dosage",row.get("dosage"));
			tmp.put("administration",row.get("administration"));
			tmp.put("frequency",row.get("frequency"));
			tmp.put("start_date_time",row.get("start_date_time"));
			tmp.put("doctor",row.get("doctor"));
			tmp.put("order_status",row.get("order_status"));
			tmp.put("dosage_units",row.get("dosage_units"));
			list.add(tmp);
		}
		if(list.size() > 0) {
			Map<String, Object> insMap = new HashMap<String, Object>();
			insMap.put("rows", list);
			insMap.put("search_id", search_id);
			String msg = new SearchRResultService().insertAll(insMap);
			if("1".equals(msg)) {
				insMap.remove("rows");
				//调用存储过程
				String success = new SearchRResultService().execComputerProc(insMap);
				// 查询结果明细,计算结果
				Result result = new SearchRResultService().queryResult(insMap);
				if(!CommonFun.isNe(result) && result.getCount() > 0) {
					List<Record> reslist = result.getResultset();
					List<String> pkeys = new ArrayList<String>();
					Map<String, Object> pkeysMap = new HashMap<String, Object>();
					for (Record record : reslist) {
						Map<String, Object> fortmp = new HashMap<String, Object>();
						if("0".equals(record.get("use_flag"))) {
							pkeys.add((String) record.get("row_pkey"));
							state = "Q";
						}
					}
					pkeysMap.put("p_key", pkeys);
					rtnMap.put("rows", pkeysMap);
				}
			}else {
				rtnMap.put("msg", msg);
			}
		}
		rtnMap.put("state", state);
		return rtnMap;
	}
	
	/**
	 * @param params 审查id
	 * @return 当前产品的审查结果展示数据
	 * @author yan
	 * @time 2018年11月27日
	 * @function 获取审查结果
	 */
	public static Map<String, Object> getShowData(Map<String, Object> params) throws Exception{
		return new AuditResultService().detail_view(params);
	}
	
	/**
	 * @param params 审查id
	 * @return 当前产品的审查结果展示数据
	 * @author yan
	 * @time 2020年02月01日 
	 * @function 获取审查结果[仅用于事后审查]
	 */
	public static Map<String, Object> getAfterShowData(Map<String, Object> params) throws Exception{
		return new AuditResultService().detail(params);
	}
	
	/**
	 * @param params auto_common 表id ：common_id
	 * @author yan
	 * @throws Exception 
	 * @time 2018年11月27日
	 * @function 返回调整或者其他仅改变产品审查主表状态的操作
	 */
	public static void backAdjust(Map<String, Object> params) throws Exception{
		Record record = new AuditResultService().getIdByCmid((String)params.remove("common_id"));
		if(!CommonFun.isNe(record)) {
			params.put("id", record.get("id"));
			new AutoAuditService().update(params);
		}
	}
	
	/**
	 * @param params
	 * @return 药师审查结果
	 * @author yan
	 * @time 2018年11月27日
	 * @function 获取药师审查结果 
	 */
	public static Map<String, Object> getPCResult(Map<String, Object> params){
		Object id = params.get("id");
		Object doctor_no = params.get("doctor_no");
		params.put("id", (String)params.get("id"));
		Connection c = Client.conns.get(doctor_no);
		if(!CommonFun.isNe(c)){
			c.reMap.remove("return_J_"+id);
		}
		//组织数据
		Map<String, Object> rtnMap = null;
		try {
			//配置时间
			//int wait_time = Integer.parseInt(GlobalVar.getSystemProperty("wait.timeout", "0"));
			int wait_time = Integer.parseInt(SystemConfig.getSystemConfigValue("ipc", "wait_time2", "30"));
			if(wait_time == -1) {
				while (true) {
					Thread.sleep(1000);
					c = Client.conns.get(doctor_no);
					if(!CommonFun.isNe(c.reMap.get("return_A_"+id))){
						Object o = c.reMap.remove("return_A_"+id);
						rtnMap = (Map<String, Object>) o;				
						break;
					}
				}
			}else {
				for(int i = 0 ;i< wait_time; i++){
					Thread.sleep(1000);
					c = Client.conns.get(doctor_no);
					if(!CommonFun.isNe(c.reMap.get("return_A_"+id))){
						Object o = c.reMap.remove("return_A_"+id);
						rtnMap = (Map<String, Object>) o;				
						break;
					}
				}
			}
		} catch (Throwable e) {
			log.error(e);
		}
		//记录人工审方的结果标识
		if(rtnMap == null){
			rtnMap = new HashMap<String, Object>();
			rtnMap.put("state", "W");
			rtnMap.put("yxk_advice", "人工审方异常：没有空闲药师审查该单业务或者网络异常！");
		}
		return rtnMap;
	}
	
	/**
	 * @param params
	 * @author yan
	 * @time 2018年11月27日
	 * @function 医生双签名用药[药师返回医生决定后，医生选择确认用药]
	 */
	public static void sureAgain(Map<String, Object> params){
		Object doctor_no = params.get("doctor_no");
		params.put("hospital_id", SystemConfig.getSystemConfigValue("hospital_common", "hospital_id"));
		try {
			Client.sendDoctorDeal((String)doctor_no,  params);
			new DataService().update(params);
		} catch (Throwable e1) {
			log.error(e1);
		}
	}
	
	/**
	 * @param params patient_id  visit_id
	 * @return map 获取该患者的医嘱及点评信息
	 * @author yan
	 * @throws Exception 
	 * @time 2018年11月28日
	 * @function 获取该患者的医嘱及点评信息
	 */
	public static Map<String, Object> getCommentOrders(Map<String, Object> params) throws Exception{
		return new CheckaAndCommentService().queryCommentAndYizu(params);
	}
	
	/**
	 * @param params 
	 * @return 
	 * @author yan
	 * @time 2018年11月27日
	 * @function 强制用药，将医嘱发送给药师审查
	 */
	public static Map<String, Object> mustDo(Map<String, Object> params){
	/*	Map<String, Object> rtnMap = new HashMap<String, Object>();
		Map<String, Object> finalRtn = new HashMap<String, Object>();*/
		try {
			Record idByCmid = new AuditResultService().getIdByCmid((String)params.get("common_id"));
			if("HL_Y".equals(idByCmid.get("state")) || "HL_T".equals(idByCmid.get("state"))) {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object id = params.get("id");
		Object doctor_no = params.get("doctor_no");
		params.put("id", (String)params.get("id"));
		Connection c = Client.conns.get(doctor_no);
		if(!CommonFun.isNe(c)){
			c.reMap.remove("return_J_"+id);
		}
		//组织数据
		Map<String, Object> result = null;
		try {
			DataService ds = new DataService();
			ds.saveDoctorAdvices(params);//保存医生的用药意见
			//调用云平台审方接口
			params.put("state", "DQ");
			if("false".equals(params.get("is_work"))) {
				params.put("state", "DQ1");
			}
			ds.updateForYXK(params);
			if("0".equals(params.get("to_pharmacist")) || "false".equals(params.get("is_work"))) {
				return null;
			}else {
				Client.sendPrescription((String)doctor_no, params);
				Object object = params.get("d_type");
				int wait_time =0;
				if("1".equals(object)) {
					wait_time = Integer.parseInt(SystemConfig.getSystemConfigValue("ipc", "wait_timeout_zy", "30"));
				}else if("2".equals(object)) {
					wait_time = Integer.parseInt(SystemConfig.getSystemConfigValue("ipc", "wait_timeout_mz", "30"));
				}else if("3".equals(object)) {
					wait_time = Integer.parseInt(SystemConfig.getSystemConfigValue("ipc", "wait_timeout_jz", "30"));
				}
				if(wait_time == -1) {
					while (true) {
						Thread.sleep(1000);
						c = Client.conns.get(doctor_no);
						if(!CommonFun.isNe(c.reMap.get("return_J_"+id))){
							Object o = c.reMap.remove("return_J_"+id);
							result = (Map<String, Object>) o;				
							break;
						}else if(!CommonFun.isNe(c.reMap.get("return_A_"+id))){
							Object o = c.reMap.remove("return_A_"+id);
							result = (Map<String, Object>) o;				
							break;
						}
					}
				}else {
					for(int i = 0; i <= wait_time; i++){
						Thread.sleep(1000);
						c = Client.conns.get(doctor_no);
						if(!CommonFun.isNe(c.reMap.get("return_J_"+id))){
							Object o = c.reMap.remove("return_J_"+id);
							result = (Map<String, Object>) o;				
							break;
						}else if(!CommonFun.isNe(c.reMap.get("return_A_"+id))){
							Object o = c.reMap.remove("return_A_"+id);
							result = (Map<String, Object>) o;				
							break;
						}
					}
				}
			}
		} catch (Exception e2) {
			log.error(e2);
		}
		if(result == null){
			result = new HashMap<String, Object>();
			result.put("state", "W");
			result.put("yxk_advice", "人工审方异常：没有空闲药师审查该单业务或者网络异常！");
		}
		return result;
	}
	
	/**
	 * @param params
	 * @return 
	 * @author yan
	 * @throws Exception 
	 * @time 2018年11月28日
	 * @function 获取点评结果数据
	 */
	public static Map<String, Object> getCommentResult(Map<String, Object> params) throws Exception{
		return new AuditResultService().queryComment(params);
	}
	
	/**
	 * @param params
	 * @return 
	 * @author yan
	 * @throws Exception 
	 * @time 2018年11月28日
	 * @function 获取患者审查历史 getPatientCheckHistory
	 */
	public static Map<String, Object> getPCHistory(Map<String, Object> params) throws Exception{
		return new CheckaAndCommentService().queryCheckAndCommentInfo(params);
	}
	
	public static String audit_hlyy(Object reqStr, Map<String, Object> map, Map<String, Object> audit_def_data) throws Exception {
		try {
			//common_id
			String common_id = (String)map.get("common_id");
			 // 自定义审查开始时间
			long st = 0; 
			// 是否已经记录自定义审查耗时
			int is_record = 0; 
			// 调用存储过程创建其他存储过程的运行环境
			// -- test yan
			//map.put("tt2", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
			long procBefore_time = System.currentTimeMillis();
			//
			int success_sys = new AuditResultService().audit_sys_procBefore(map);
			int success = new AuditResultService().audit_zdy_procBefore(map);
			
			// --test yan
			//map.put("tt3", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
			ylz.recordMsg("mmid_" + common_id, "ipc调用自定义审查前置准备[SYS_SHENGFANG_EXEPROC,SCR_SHENGFANG_EXEPROC]时间, (消耗 " + (System.currentTimeMillis() - procBefore_time) + " ms)");
			// --
			Map<String, Object> rtnMap = new ConcurrentHashMap<String, Object>();
			//Map<String, Object> rtnMap = new HashMap<String, Object>();
			HYTAuditHLYY b = new HYTAuditHLYY(map, reqStr, null, rtnMap);
			ZDYAuditHLYY a1 = new ZDYAuditHLYY(map, reqStr, DataDao.proc1, rtnMap);
			ZDYAuditHLYY a2 = new ZDYAuditHLYY(map, reqStr, DataDao.proc2, rtnMap);
			ZDYAuditHLYY a3 = new ZDYAuditHLYY(map, reqStr, DataDao.proc3, rtnMap);
			ZDYAuditHLYY a4 = new ZDYAuditHLYY(map, reqStr, DataDao.proc4, rtnMap);
			ZDYAuditHLYY a5 = new ZDYAuditHLYY(map, reqStr, DataDao.proc5, rtnMap);
			ZDYAuditHLYY_new a6 = new ZDYAuditHLYY_new(map, reqStr, "TPN", rtnMap, audit_def_data);
			//ZDYAuditHLYY a7 = new ZDYAuditHLYY(map, reqStr, DataDao.proc7, rtnMap);
			ZDYAuditHLYY s1 = new ZDYAuditHLYY(map, reqStr, DataDao.sproc1, rtnMap);
			ZDYAuditHLYY s2 = new ZDYAuditHLYY(map, reqStr, DataDao.sproc2, rtnMap);
			ZDYAuditHLYY s3 = new ZDYAuditHLYY(map, reqStr, DataDao.sproc3, rtnMap);
			ZDYAuditHLYY s4 = new ZDYAuditHLYY(map, reqStr, DataDao.sproc4, rtnMap);
			ZDYAuditHLYY s5 = new ZDYAuditHLYY(map, reqStr, DataDao.sproc5, rtnMap);
			b.start();
			st = System.currentTimeMillis();
			if(success == 1) {
				a1.start();
				a2.start();
				a3.start();
				a4.start();
				a5.start();
				a6.start();
				log.debug("------------------------zdy");
				//a7.start();
			}
			if(success_sys == 1) {
				s1.start();
				s2.start();
				s3.start();
				s4.start();
				s5.start();
			}
			
			long for_time = System.currentTimeMillis();
			String audit_flag = "HL_F";
			Map<String, Object> params = new HashMap<String, Object>();
			int o_time = maxtime;
			if("1".equals(map.get("is_after"))) {
				o_time = 4*maxtime;
			}
			long listen_time_st = System.currentTimeMillis();
			for(int i=0; i<o_time; i++){//1200,可根据可以需要调整
				if(rtnMap.size() == 11 && CommonFun.isNe(rtnMap.get("hyt"))) {
					if(is_record == 0) {
						params.put("zdy_cost_time", System.currentTimeMillis()-st);
						params.put("id", map.get("auto_audit_id"));
						long pharmacist_audit_result_time = System.currentTimeMillis();
						new AutoAuditService().update(params);
						ylz.recordMsg("mmid_" + common_id, "ipc调用pharmacist_audit_result时间, (消耗 " + (System.currentTimeMillis() - pharmacist_audit_result_time) + " ms)");
						is_record = 1;
					}
				}
				if(rtnMap.size() == 12){
					audit_flag = "Y";
					if(is_record == 0) {
						params.put("zdy_cost_time", System.currentTimeMillis()-st);
						params.put("id", map.get("auto_audit_id"));
						long pharmacist_audit_result_time = System.currentTimeMillis();
						new AutoAuditService().update(params);
						ylz.recordMsg("mmid_" + common_id, "ipc调用pharmacist_audit_result时间, (消耗 " + (System.currentTimeMillis() - pharmacist_audit_result_time) + " ms)");
						is_record = 1;
					}
					break;
				}
				if((System.currentTimeMillis() - listen_time_st) >= o_time*100) {break;}
				Thread.sleep(100);
			}
			// --test yan
			//map.put("tt4", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
			ylz.recordMsg("mmid_" + common_id, "ipc调用审查整体时间, (消耗 " + (System.currentTimeMillis() - for_time) + " ms)");
			// --
			String zdy_result = "HL_Y";
			
			zdy_result = new AuditResultService().audit_hlyy(audit_flag, map);//根据各个审查结果和审查规则，分析出审查的综合结果
			return zdy_result;
		}catch(Exception e){
			throw e;
		}finally {
			
		}
	}
	
	//合理用药审查线程
	public static class HYTAuditHLYY extends Thread{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> rtnMap = null;
		String reString = "N";
		Object request = new Object();
		String proc = "";
		public HYTAuditHLYY(Map<String, Object> map,Object request, String proc, Map<String, Object> rtnMap){
			this.map.putAll(map);
			this.request = request;
			this.proc = proc;
			this.rtnMap = rtnMap;
		}
		@Override
		public void run() {
			//map.put("tt4", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
			//wrh, get_time
			long HYTAuditHLYY_time = System.currentTimeMillis();
			try {
				if(IS_SUBMIT) {
					new HYTDllAuditServiceImpl().callAudit(map, request);
				}
			} catch (Throwable e) {
				e.printStackTrace();
				Client.sendHLYYError("admin_jq", new HashMap<String, Object>());
			}finally {
				reString = "Y";
				//synchronized (rtnMap) {
					rtnMap.put("hyt", 1);
				//}
				ylz.recordMsg("mmid_" + map.get("common_id"), "ipc调用审查引擎审查时间, (消耗 " + (System.currentTimeMillis() - HYTAuditHLYY_time) + " ms)");
			}
		}
	}
	public static class ZDYAuditHLYY extends Thread{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> rtnMap = null;
		String reString = "N";
		Object request = new Object();
		String proc = "";
		public ZDYAuditHLYY(Map<String, Object> map, Object request, String proc, Map<String, Object> rtnMap){
			this.map.putAll(map);
			this.request = request;
			this.proc = proc;
			this.rtnMap = rtnMap;
		}
		@Override
		public void run() {
			long ZDYAuditHLYY_time = System.currentTimeMillis();
			try {
				reString = new AuditResultService().audit_zdy(map, proc);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				reString = "Y";
				//synchronized (rtnMap) {
					rtnMap.put(proc, 1);
				//}
				ylz.recordMsg("mmid_" + map.get("common_id"), "ipc调用自定义审查引擎["+proc+"]时间, (消耗 " + (System.currentTimeMillis() - ZDYAuditHLYY_time) + " ms)");
			}
		}
	}
	
	public static class ZDYAuditHLYY_new extends Thread{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> rtnMap = null;
		Map<String, Object> audit_def_data = null;
		String reString = "N";
		Object request = new Object();
		String proc = "";
		public ZDYAuditHLYY_new(Map<String, Object> map, Object request, String proc, Map<String, Object> rtnMap,Map<String, Object> audit_def_data){
			this.map.putAll(map);
			this.request = request;
			this.proc = proc;
			this.rtnMap = rtnMap;
			this.audit_def_data = audit_def_data;
		}
		@Override
		public void run() {
			long ZDYAuditHLYY_time = System.currentTimeMillis();
			try {
				new TPNImpl().call(audit_def_data, null);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				reString = "Y";
				//synchronized (rtnMap) {
					rtnMap.put(proc, 1);
				//}
				ylz.recordMsg("mmid_" + map.get("common_id"), "ipc调用自定义审查引擎["+proc+"]时间, (消耗 " + (System.currentTimeMillis() - ZDYAuditHLYY_time) + " ms)");
			}
		}
	}
	
	//合理用药审查---系列时间
	public static Map<String, Object> auditTimeInfo(Map<String, Object> params) throws Exception{
		return new AutoAuditService().auditTimeInfo(params);
	}
	
	// 发送审查数据到药师端
	public static void sendToYun(Map<String, Object> params) throws Exception{
		params.put("id", params.get("auto_audit_id"));
		new DataService().updateForYXK(params);
		Client.sendPrescription((String)params.get("doctor_no"), params);
	}
	
	/**
	 * 点评审查
	 * @param req
	 * @return
	 */
	public static Map<String, Object> pr_audit(Map<String, Object> req){
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		Object request = req.get("xml");
		if(request != null) {
			Map<String, Object> re = (Map<String, Object>) request;
			if(!CommonFun.isNe(re)) {
				try {
					//调用自动审查接口
					Map<String, Object> audit_result = pr_audit_hlyy(re.get("request"),map);
					result.putAll(audit_result);
				} catch (Exception e) {
					log.error(e);
				}
			}
		}
		
		return result;
	}
	
	
	public static Map<String, Object> pr_audit_hlyy(Object reqStr, Map<String, Object> map) throws Exception {
		try {
			// 调用存储过程创建其他存储过程的运行环境
			//int success_sys = new AuditResultService().audit_sys_procBefore(map);
			//int success = new AuditResultService().audit_zdy_procBefore(map);
			Map<String, Object> rtnMap = new ConcurrentHashMap<String, Object>();
			Map<String, Object> result = new HashMap<String, Object>();
			pr_HYTAuditHLYY b = new pr_HYTAuditHLYY(map, reqStr, null, rtnMap);
			pr_ZDYAuditHLYY a1 = new pr_ZDYAuditHLYY(map, reqStr, DataDao.pr_proc1, rtnMap);
			pr_ZDYAuditHLYY a2 = new pr_ZDYAuditHLYY(map, reqStr, DataDao.pr_proc2, rtnMap);
			pr_ZDYAuditHLYY a3 = new pr_ZDYAuditHLYY(map, reqStr, DataDao.pr_proc3, rtnMap);
			pr_ZDYAuditHLYY a4 = new pr_ZDYAuditHLYY(map, reqStr, DataDao.pr_proc4, rtnMap);
			pr_ZDYAuditHLYY a5 = new pr_ZDYAuditHLYY(map, reqStr, DataDao.pr_proc5, rtnMap);
			//ZDYAuditHLYY a7 = new ZDYAuditHLYY(map, reqStr, DataDao.proc7, rtnMap);
			pr_ZDYAuditHLYY s1 = new pr_ZDYAuditHLYY(map, reqStr, DataDao.pr_sproc1, rtnMap);
			pr_ZDYAuditHLYY s2 = new pr_ZDYAuditHLYY(map, reqStr, DataDao.pr_sproc2, rtnMap);
			pr_ZDYAuditHLYY s3 = new pr_ZDYAuditHLYY(map, reqStr, DataDao.pr_sproc3, rtnMap);
			pr_ZDYAuditHLYY s4 = new pr_ZDYAuditHLYY(map, reqStr, DataDao.pr_sproc4, rtnMap);
			pr_ZDYAuditHLYY s5 = new pr_ZDYAuditHLYY(map, reqStr, DataDao.pr_sproc5, rtnMap);
			b.start();
			//if(success == 1) {
				a1.start();
				a2.start();
				a3.start();
				a4.start();
				a5.start();
				//a7.start();
			//}
			//if(success_sys == 1) {
				s1.start();
				s2.start();
				s3.start();
				s4.start();
				s5.start();
			//}
			
			int o_time = maxtime;
			if("1".equals(map.get("is_after"))) {
				o_time = 4*maxtime;
			}
			for(int i=0; i<o_time; i++){//1200,可根据可以需要调整
				if(rtnMap.size() == 11){
					result.putAll(rtnMap);
					break;
				}
				Thread.sleep(200);
			}
			return result;
		}catch(Exception e){
			throw e;
		}finally {
			
		}
	}
	
	//合理用药审查线程
	public static class pr_HYTAuditHLYY extends Thread{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> rtnMap = null;
		Object request = new Object();
		String proc = "";
		public pr_HYTAuditHLYY(Map<String, Object> map,Object request, String proc, Map<String, Object> rtnMap){
			this.map.putAll(map);
			this.request = request;
			this.proc = proc;
			this.rtnMap = rtnMap;
		}
		@Override
		public void run() {
			String result = "";
			try {
				if(IS_SUBMIT) {
					result = new HYTDllAuditServiceImpl().pr_callAudit(request);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				////synchronized (rtnMap) {
					rtnMap.put("hlyy_result", result);
				//}
				//rtnMap.put("hyt", 1);
			}
		}
	}
	
	public static class pr_ZDYAuditHLYY extends Thread{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> rtnMap = null;
		Object request = new Object();
		String proc = "";
		public pr_ZDYAuditHLYY(Map<String, Object> map, Object request, String proc, Map<String, Object> rtnMap){
			this.map.putAll(map);
			this.request = request;
			this.proc = proc;
			this.rtnMap = rtnMap;
		}
		@Override
		public void run() {
			String result = "";
			try {
				//result = new AuditResultService().pr_audit_zdy(map, proc);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				//synchronized (rtnMap) {
					rtnMap.put("zdy_"+proc, result);
				//}
				//rtnMap.put(proc, 1);
			}
		}
	}
}
