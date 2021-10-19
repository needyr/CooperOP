package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.WebmethodDao;

public class WebmethodService extends BaseService{

	public int insert(Map<String, Object> params) throws Exception{
		try{
			connect();
			int i =  new WebmethodDao().insert(params);
			return i;
		}catch(Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	public int delete(Map<String,Object> params) throws Exception{
		try{
			connect();
			int i = new WebmethodDao().delete(params);
			return i;
		}catch(Exception ex){
			throw ex;
		}finally{
			disconnect();
		}	
	}
	
	public int update(Map<String,Object> params) throws Exception{
		try{
			connect();
			int i = new WebmethodDao().update(params);
			return i;
		}catch(Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}

	public Result query(Map<String,Object> params) throws Exception{
		try{
			connect();
			return new WebmethodDao().query(params);
		} finally{
			disconnect();
		}
	}
	
	public Record get(Map<String,Object> params) throws Exception{
		try{
			connect();
			if (CommonFun.isNe("data_webservice_code") ||
					CommonFun.isNe("code"))
				return new Record();
			return new WebmethodDao().get(params);
		}finally{
			disconnect();
		}
	}
	
}
