package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.VersionControlClientDao;

public class VersionControlClientService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new VersionControlClientDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new VersionControlClientDao().insert(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void updateByIp(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new VersionControlClientDao().updateByIp(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result query_mx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new VersionControlClientDao().query_mx(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void insert_mx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new VersionControlClientDao().insert_mx(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void updateByPkey_mx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new VersionControlClientDao().updateByPkey_mx(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Result queryVersionAll(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new VersionControlClientDao().queryVersionAll(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public void capture(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new VersionControlClientDao().capture(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	
}
