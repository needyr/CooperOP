package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.MethodDao;

public class MethodService extends BaseService {

	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect();
			int r = new MethodDao().insert(params);
			return r;
		} catch(Exception e){
			rollback();
			throw e;
		} finally {
			disconnect(); 
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect();
			int r = new MethodDao().delete(params);
			return r;
		} catch(Exception e){
			throw e;
		} finally {
			disconnect(); 
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect();
			int r = new MethodDao().update(params);
			return r;
		} catch(Exception e){
			throw e;
		} finally {
			disconnect(); 
		}
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new MethodDao().query(params);
		} finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			if (CommonFun.isNe(params.get("code")) || CommonFun.isNe("data_service_code"))
				return new Record();
			return new MethodDao().get(params);
		} finally {
			disconnect();
		}
	}
}
