package cn.crtech.cooperop.hospital_common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.hospital_common.dao.ManageDao;
import cn.crtech.cooperop.bus.rdms.Result;

public class ManageService extends BaseService {
	
	/**
	 * 插入
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int insert(Map<String, Object> params) throws Exception{
		try{
			connect();
			int i = new ManageDao().insert(params);
			return i;
		}catch(Exception ex){
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	/**
	 * drop表结构
	 */
	public int dropTable(Map<String,Object> params) throws Exception{
		try{
			connect();
			return new ManageDao().dropTable(params); 
		}catch(Exception ex){
			rollback();
			throw ex;
		}finally{
			disconnect();
		}
	}
	
	/**
	 * 查询表结构
	 */
	public List<Record> query(Map<String, Object> params) throws Exception{
		try{
			connect();
			Result rs = new ManageDao().query(params);
			return  rs.getResultset();
		}catch(Exception ex){
			return new ArrayList<Record>();
		}finally{
			disconnect();
		}
	}
	
	// +++
	public String getAlias(String table_name, String field_name) throws Exception {
		try {
			connect();
			return new ManageDao().getAlias(table_name, field_name);
		} finally {
			disconnect();
		}
	}
	
	public int modify(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new ManageDao().modify(params);
		} finally {
			disconnect();
		}
	}
	// +++
}
