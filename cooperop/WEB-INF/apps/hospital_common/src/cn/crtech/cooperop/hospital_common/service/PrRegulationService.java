package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.PrRegulationDao;

public class PrRegulationService extends BaseService {

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new PrRegulationDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new PrRegulationDao().delete(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Map<String, Object> get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(CommonFun.isNe(params.get("id"))) {
				return null;
			}
			return new PrRegulationDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int save(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Record map = new PrRegulationDao().getQuestion(params);
			params.remove("type_p_key");
			params.put("thirdt_code", map.get("thirdt_code"));
			params.put("check_type", map.get("check_type"));
			params.put("product_code", map.get("product_code"));
			if(CommonFun.isNe(params.get("id"))) {
				new PrRegulationDao().insert(params);
			}else {
				new PrRegulationDao().update(params);
			}
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Result queryAuditType(Map<String, Object> params) throws Exception  {
		try {
			connect("hospital_common");
			return new PrRegulationDao().queryAuditType(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
