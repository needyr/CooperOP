package cn.crtech.cooperop.bus.im.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DepartmentDao extends BaseDao {

	public final static String TABLE_NAME = "system_department";

	public Record get(String id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ");
		sql.append(TABLE_NAME + "(nolock) ");
		sql.append("where id = :id");
		Record params = new Record();
		params.put("id", id);
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public int insert(Map<String, Object> params) throws Exception {
		System.out.println(params);
		return executeInsert(TABLE_NAME, params);
	}

	public int update(Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, conditions);
	}

	public int delete(String id) throws Exception {
		Record conditions = new Record();
		conditions.put("id", id);
		return executeDelete(TABLE_NAME, conditions);
	}

}
