package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class SystemAssistQueryDao extends BaseDao{
	
	public static final String TABLE_NAME = "system_assist_query";
	
	public static final String TABLE_DICT_SYS_URL = "dict_sys_url";

	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select a.*, b.address from " + TABLE_NAME + " (nolock) a");
		sql.append(" left join " + TABLE_DICT_SYS_URL + " (nolock) b");
		sql.append(" on a.dict_sys_url_id = b.id ");
		sql.append(" where b.state = 1 ");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("filter", "%" + params.get("filter") +"%");
			sql.append("and a.fz_title like :filter or b.address like :filter ");
		}
		setParameter(params, "assist", "and a.state = 1", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*, b.address from " + TABLE_NAME + " a");
		sql.append(" left join " + TABLE_DICT_SYS_URL + " (nolock) b");
		sql.append(" on a.dict_sys_url_id = b.id ");
		sql.append(" where b.state = 1 ");
		sql.append(" and a.id = :id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
