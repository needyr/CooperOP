package cn.crtech.cooperop.crdc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class SavetbDao extends BaseDao{
	private final static String TABLE_NAME = "cr_djsavetb";
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}

	public int delete(String viewflagid, String tbname) throws Exception {
		Record conditions = new Record();
		conditions.put("viewflagid", viewflagid);
		conditions.put("tbname", tbname);
		return executeDelete(TABLE_NAME, conditions);
	}
	public int deleteByDJ(String viewflagid,String system_product_code,String type) throws Exception {
		Record conditions = new Record();
		conditions.put("viewflagid", viewflagid);
		conditions.put("system_product_code", system_product_code);
		conditions.put("viewtype", type);
		return executeDelete(TABLE_NAME, conditions);
	}
}
