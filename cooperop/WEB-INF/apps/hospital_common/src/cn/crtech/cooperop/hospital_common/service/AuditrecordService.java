package cn.crtech.cooperop.hospital_common.service;

import java.util.HashMap;
import java.util.Map;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.AutoCommonDao;
import cn.crtech.precheck.EngineInterface;

public class AuditrecordService extends BaseService{

	public Result queryAudit(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			//如果不是超级管理用户，则进行多机构判断
			if(!user().isSupperUser()){
				//登录职工ID
				String jigou_id=user().getJigid();
				if (!CommonFun.isNe(jigou_id)){
					params.put("jigou_id",jigou_id);
				}
			}
			Result result = new AutoCommonDao().queryImic(params);
			return result;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Result queryAuditByDept(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Record dept = new AutoCommonDao().getDept(params);
			params.put("dept_code", dept.get("code"));
			//如果不是超级管理用户，则进行多机构判断
			if(!user().isSupperUser()){
				//登录职工ID
				String jigou_id=user().getJigid();
				if (!CommonFun.isNe(jigou_id)){
					params.put("jigou_id",jigou_id);
				}
			}
			Result result = new AutoCommonDao().queryImicByDept(params);
			return result;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	/** 审查列表 */
	public Result queryAuditAll(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new AutoCommonDao().queryAuditAll(params);
		} catch (Exception e) { throw e; }
		finally { disconnect(); }
	}
	
	public Record getById(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new AutoCommonDao().get(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	/**
	 * 
	 * @param params common_id
	 * @return
	 * @throws Exception
	 * @function 审查详情
	 * @author yankangkang 2019年6月2日
	 */
	public Map<String, Object> get(Map<String, Object> params) throws Exception {
		try {
			Map<String, Object> resp = new HashMap<String, Object>();
			connect("hospital_common");
			AutoCommonDao autoCommonDao = new AutoCommonDao();
			Record common = autoCommonDao.getAutoCommonByID(params);
			//auto_common状态+总耗时
			resp.put("common", common);
			String demo_resp = (String) common.get("demo_resp");
			if(!CommonFun.isNe(demo_resp)) {
				Map<String, Object> rtnStr = CommonFun.json2Object(demo_resp, Map.class);
				String flag = (String) rtnStr.get("flag");
				//合理用药
				if(flag.indexOf("a") >= 0) {
					Map<String, Object> ipc = EngineInterface.getAuditTime(params);
					resp.put("ipcdata", ipc);
				}
			}
			//医保审查数据
			/*connect("hospital_imic");
			AutoCommonDao autoCommonDao2 = new AutoCommonDao();
			Record imicAuditByCommonID = autoCommonDao2.getImicAuditByCommonID(params);
			if(!CommonFun.isNe(imicAuditByCommonID)) {
				req.put("imicAudit", imicAuditByCommonID);
			}
			disconnect();*/
			return resp;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
}
