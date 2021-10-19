package cn.crtech.cooperop.hospital_common.service.auditset;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.auditset.CheckDeptDao;
import cn.crtech.precheck.EngineInterface;

public class CheckDeptService extends BaseService{
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			CheckDeptDao cdd = new CheckDeptDao();
			if(!CommonFun.isNe(params.get("product_code")) && "1".equals(params.get("ref"))) {
				cdd.updateCI(params);
			}
			return cdd.query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryDeptAll(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckDeptDao().queryDeptAll(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryToDeptType(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckDeptDao().queryToDeptType(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result querydept(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckDeptDao().querydept(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			new CheckDeptDao().insert(params);
			//loadCheckDept(params);
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("update_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			new CheckDeptDao().update(params);
			//loadCheckDept(params);
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update_state(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("update_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			Object remove = params.remove("type");
			if("open".equals(remove)) {
				params.put("state", "1");
			}else if("close".equals(remove)) {
				params.put("state", "0");
			}
			new CheckDeptDao().update_state(params);
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new CheckDeptDao().delete(params);
			//loadCheckDept(params);
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckDeptDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delUpdate(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("state", -1);
			new CheckDeptDao().update(params);
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Map<String, Object> loadCheckDept(Map<String, Object> params) throws Exception {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		try {
			connect("hospital_common");
			CheckDeptDao checkdept = new CheckDeptDao();
			//获取产品
			Iterator<String> iter = EngineInterface.interfaces.keySet().iterator();
			while(iter.hasNext()) {
				String code = iter.next();
				Map<String, Object> pduMap = new HashMap<String, Object>();
				pduMap.put("product_code", code);
				pduMap.put("state", 1);
				Result result = checkdept.queryLoad(pduMap);
				if(!CommonFun.isNe(result)) {
					rtnMap.put(code + "_check_dept", result.getResultset());
					log.debug(" -- loading "+ code +" dept success , all " + result.getResultset().size() + " ... ");
				}
			}
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
		return rtnMap;
	}
}
