package cn.crtech.cooperop.crdc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.crdc.dao.DdictionaryDao;

public class DdictionaryService extends BaseService {
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			DdictionaryDao dd = new DdictionaryDao();
			return dd.query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Result queryColumns(Map<String,Object> params) throws Exception{
		try {
			connect();
			DdictionaryDao dd = new DdictionaryDao();
			return dd.queryColumns(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Result queryDistinct(Map<String,Object> params) throws Exception{
		try {
			connect();
			DdictionaryDao dd = new DdictionaryDao();
			return dd.queryDistinct(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public int synchroOnline(Map<String,Object> params) throws Exception{
		try{
			connect();
			DdictionaryDao dd = new DdictionaryDao();
			dd.synchroOnline(params);
			return 1;
		} catch (Exception ex){
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public int deleteTableRows(Map<String,Object> params) throws Exception{
		try{
			connect();
			DdictionaryDao dd = new DdictionaryDao();
			dd.deleteTableRows(params);
			return 1;
		} catch (Exception ex){
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public int deleteTablesRows(Map<String,Object> params) throws Exception{
		try{
			connect();
			DdictionaryDao dd = new DdictionaryDao();
			params.put("del_rows", 1);
			List<Record> list=dd.query(params).getResultset();
			for(Record r:list){
				Map<String,Object> m=new HashMap<String,Object>();
				String tablename=null;
				if(Integer.parseInt(r.getString("is_del"))==1){
					m.clear();
					tablename=r.getString("databasename")+".dbo."+r.getString("table_name");
					m.put("tablename", tablename);
					dd.deleteTableRows(m);
				}
			}
			return 1;
		} catch (Exception ex){
			throw ex;
		} finally {
			disconnect();
		}
	}
	
}
