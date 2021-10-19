package cn.crtech.cooperop.setting.dao;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;

public class WeixinDao extends BaseDao{
	private final static String TABLE_NAME = "[system_config]";
	

	public void insert(Map<String, Object> params) throws Exception {
		params.remove("id");
		executeInsert(TABLE_NAME, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("code", params.remove("code"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	
	public Record get(String code) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", code);
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+TABLE_NAME+"(nolock) ");
		sql.append(" where 1 = 1 ");
		setParameter(params, "code", " and code=:code", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("code", params.remove("code"));
		return executeUpdate(TABLE_NAME, params,r);
	}
}
