package cn.crtech.cooperop.hospital_common.service.dict;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.dict.DiagnosisDao;

public class DiagnosisService extends BaseService{

	public Result queryin(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DiagnosisDao().queryin(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public void init(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			start();
			//2.查询未匹配药品top100，并写入中间表（存储过程完成）
			new DiagnosisDao().calltop100(new HashMap<String, Object>());
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}

	public Result querysys(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DiagnosisDao().querysys(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public void updateMapping(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			start();
			new DiagnosisDao().updateMapping(params);
			commit();
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}

	public void direptUpdateSys(Map<String, Object> params) throws Exception {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String sys_p_key = (String)params.get("diagnosis_code")+"|"+(String)params.get("diagnosis_name");
			map.put("sys_p_key", sys_p_key);
			map.put("is_plcl", "是");
			map.put("is_plcl_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
			map.put("his_p_key", params.remove("p_key"));
			params.put("p_key", sys_p_key);
			connect("hospital_common");
			start();
			new DiagnosisDao().insertSys(params);
			new DiagnosisDao().updateMapping(map);
			commit();
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}

	public void updateSureMapping(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			start();
			new DiagnosisDao().updateSureMapping(params);
			commit();
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}

}
