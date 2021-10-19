package cn.crtech.cooperop.hospital_common.service.tpn;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.tpn.RuleDao;


public class RuleService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new RuleDao().query(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(CommonFun.isNe(params.get("tpnzl_id"))) {
				return null;
			}
			return new RuleDao().get(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
		
	}
	
	public Result queryTpnzlRule(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new RuleDao().queryTpnzlRule(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Result getXmmch(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new RuleDao().getXmmch(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new RuleDao().insert(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}
	public int insertTpnMX(Map<String, Object> params) throws Exception{
		try {
			connect("hospital_common");
			return new RuleDao().insertTpnMX(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public int updateByState(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new RuleDao().updateByState(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public int updateByState2(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new RuleDao().updateByState2(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new RuleDao().delete(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}
	
	public int deleteRuleMX(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new RuleDao().deleteRuleMX(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}
	

	

	


	
}
