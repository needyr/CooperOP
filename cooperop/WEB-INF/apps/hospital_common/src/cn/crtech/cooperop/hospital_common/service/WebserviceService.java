package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.WebserviceDao;

public class WebserviceService extends BaseService{

	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect();
			int i = new WebserviceDao().insert(params);
			return i;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect();
			int i = new WebserviceDao().delete(params);
			return i;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public int update(Map<String,Object> params) throws Exception{
		try{
			connect();
			int i = new WebserviceDao().update(params);
			return i;
		}catch(Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new WebserviceDao().query(params);
		} finally {
			disconnect();
		}
	}
	
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			if (CommonFun.isNe(params.get("code")))
				return new Record();
			return new WebserviceDao().get(params);
		} finally {
			disconnect();
		}
	}
	

}
