package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.WebtablerefDao;
import cn.crtech.cooperop.bus.rdms.Result;

public class WebtablerefService extends BaseService{


	public int insert(Map<String, Object> params) throws Exception{
		try{
			connect();
			int i = new WebtablerefDao().insert(params);
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
			int i = new WebtablerefDao().delete(params);
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
			int i = new WebtablerefDao().update(params);
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
			return new WebtablerefDao().query(params);
		}finally{
			disconnect();
		}
	}
	
	public Record get(Map<String,Object> params) throws Exception{
		try{
			connect();
			if (CommonFun.isNe(params.get("data_webservice_code")) ||
					CommonFun.isNe(params.get("data_webservice_method_code")) ||
					CommonFun.isNe(params.get("table_name")))
				return new Record();
			return new WebtablerefDao().get(params);
		}finally{
			disconnect();
		}
	}
}
