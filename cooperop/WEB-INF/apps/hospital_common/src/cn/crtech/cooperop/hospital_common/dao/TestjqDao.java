package cn.crtech.cooperop.hospital_common.dao;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class TestjqDao extends BaseDao {
	
	public static final String TABLE_NAME = "test_result";
	
	public int insert(Map<String, Object> params) throws Exception {
		params.put("id", CommonFun.getITEMID());
		return executeInsert(TABLE_NAME, params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("code", params.get("code"));
		return executeDelete(TABLE_NAME, conditions);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("code", params.get("code"));
		return executeUpdate(TABLE_NAME, params, conditions);
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,b.state as sh_jieguo from " + TABLE_NAME + " (nolock) a ");
		sql.append(" inner join hospital_autopa..auto_audit b (nolock) on a.test_id = b.common_id   ");
		sql.append(" where 1=1 ");
		setParameter(params, "type", " and type=:type", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result jsexesql(Map<String, Object> params) throws Exception {
		return executeQuery((String)params.get("sql"), params);
	}

	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME + "(nolock) ");
		sql.append(" where id=:id");
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public Result queryInfo(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select max(cost_time) as max_time,min(cost_time) as min_time,avg(cost_time) as avg_time, ");  
		sql.append("(select count(id)+0.00  from test_result (nolock) where type = :type and state = 1)/");
		sql.append("(select count(id)+0.00  from test_result (nolock) where type = :type)*100 as complete, ");
		sql.append("((select count(a.id)+0.00                                                   ");
		sql.append("from test_result a (nolock)                                                  ");
		sql.append("inner join hospital_autopa..auto_audit b (nolock) on a.test_id = b.common_id   ");
		sql.append("where type = :type  and b.state = 'HL_Y')/                                ");
		sql.append("(select count(a.id)+0.00                                                   ");
		sql.append("from test_result a (nolock)                                                  ");
		sql.append("inner join hospital_autopa..auto_audit b (nolock) on a.test_id = b.common_id   ");
		sql.append("where type = :type))*100                                                  ");
		sql.append("as pass                                                              ");
		sql.append(" from test_result (nolock) where type = :type   "); 
		return executeQuery(sql.toString(),params);
	}
}
