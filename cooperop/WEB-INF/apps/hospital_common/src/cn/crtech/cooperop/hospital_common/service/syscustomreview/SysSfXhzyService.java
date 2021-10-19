package cn.crtech.cooperop.hospital_common.service.syscustomreview;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.syscustomreview.SysSfXhzyDao;

public class SysSfXhzyService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysSfXhzyDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new SysSfXhzyDao().insert(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(CommonFun.isNe(params.get("is_lhsc"))) {
				params.put("is_lhsc", "否");
			}
			if(CommonFun.isNe(params.get("is_qb"))) {
				params.put("is_qb", "否");
			}
			int res=new SysSfXhzyDao().update(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new SysSfXhzyDao().delete(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysSfXhzyDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
