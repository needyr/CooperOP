package cn.crtech.precheck.ipc.thirdparty;

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
import cn.crtech.cooperop.ipc.service.SearchRResultService;
import cn.crtech.precheck.ipc.audit_def.impl.TPNImpl;
import cn.crtech.precheck.ipc.dao.DataDao;
import cn.crtech.precheck.ipc.service.impl.HYTDllAuditServiceITHmpl;
import cn.crtech.precheck.ipc.service.impl.HYTDllAuditServiceImpl;
import cn.crtech.precheck.ipc.ws.client.Client;
import cn.crtech.ylz.ylz;
public class InterfaceCenter {
	
	/** 合理用药审查最大等到时间 */
	private static int maxtime = 10 * Integer.parseInt(SystemConfig.getSystemConfigValue("ipc", "time_waitsckeck", "6")) + 1; 
	
	/** 是否提交第三方审查引擎 */
	public static Boolean IS_SUBMIT =  Boolean .parseBoolean(GlobalVar.getSystemProperty("ipc.post.Tengine", "true"));
	
	/**
	 * 合理用药审查
	 */
	public static Map<String, Object> audit(Map<String, Object> req) throws Exception {
		log.debug("**********合理用药审查开始**********");
		Map<String, Object> audit_hlyy = new HashMap<String, Object>();
		String id = "0";
		Object request = new Object();
		Map<String, Object> audit_def_data = new HashMap<String, Object>();
		try {
			//拼装审查参数
			//Map<String, Object> reMap = new DataService().save_hyt(params,common_id);
			HYTDllAuditServiceITHmpl hytDllAuditServiceImpl = new HYTDllAuditServiceITHmpl();
			Map<String, Object> reMap = hytDllAuditServiceImpl.joinParams(req,null);
			id = (String)reMap.get("auto_audit_id");
			request = reMap.get("request");
			//获取自定义审查需要的数据, 注意: 需要处理好数据后再去拿数据
			audit_def_data = hytDllAuditServiceImpl.get_audit_def_data(id);
		} catch (Exception e1) {
			log.error(e1);
			throw e1;
		}
		
		try {
			//调用自动审查接口
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("auto_audit_id", id);
			audit_hlyy = audit_hlyy(request, m, audit_def_data);//new EngineHYT().execMethod(request, m);
		} catch (Exception e) {
			log.error(e);
			throw e;
			//TODO 调用卓远失败保存日志
		}
		return audit_hlyy;
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
	
	public static Map<String, Object> audit_hlyy(Object reqStr, Map<String, Object> map, Map<String, Object> audit_def_data) throws Exception {
		try {
			// 调用存储过程创建其他存储过程的运行环境
			//TODO 需要完成系统自定义存储过程的更改
			int success_sys = new AuditResultService().audit_sys_procBefore(map);
			int success = new AuditResultService().audit_zdy_procBefore(map);
			
			// --test yan
			//map.put("tt3", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
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
			
			String audit_flag = "HL_F";
			/*int o_time = maxtime;
			if("1".equals(map.get("is_after"))) {
				o_time = 4*maxtime;
			}
			long listen_time_st = System.currentTimeMillis();*/
			while(true){//1200,可根据可以需要调整
				if(rtnMap.size() == 12){
					audit_flag = "Y";
					break;
				}
				//if((System.currentTimeMillis() - listen_time_st) >= o_time*100) {break;}
				Thread.sleep(800);
			}
			AuditResultService auditResultService = new AuditResultService();
			auditResultService.audit_hlyy(audit_flag, map);//根据各个审查结果和审查规则，分析出审查的综合结果
			//审查完毕,返回组织结果数据
			Map<String, Object> re = auditResultService.orgResultsToMap(map);
			return re;
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
			try {
				new TPNImpl().call(audit_def_data, null);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				reString = "Y";
				//synchronized (rtnMap) {
					rtnMap.put(proc, 1);
				//}
			}
		}
	}
}
