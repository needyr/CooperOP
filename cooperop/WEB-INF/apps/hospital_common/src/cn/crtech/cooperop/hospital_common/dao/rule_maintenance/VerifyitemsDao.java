package cn.crtech.cooperop.hospital_common.dao.rule_maintenance;

import java.util.Map;


import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class VerifyitemsDao extends BaseDao {
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT                       ");
		sql.append("id,                          ");
		sql.append("field,                       ");
		sql.append("table_name,                  ");
		sql.append("field_type,                  ");
		sql.append("time_format,                 ");
		sql.append("float_num,                   ");
		sql.append("is_unique,                   ");
		sql.append("is_null,                     ");
		sql.append("parent_bh,                   ");
		sql.append("is_union,                    ");
		sql.append("product                      ");
		sql.append("from verify_items(nolock)    ");
		sql.append("where 1=1                    ");
		sql.append(" and parent_bh = :parent_bh  ");
		if (!CommonFun.isNe(params.get("sousuo"))) {
			params.put("sousuo", "%"+params.get("sousuo")+"%");
			sql.append(" and (id like :sousuo or table_name like :sousuo)");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		executeInsert("verify_items", params);
	}
	
    public void delete(Map<String, Object> params) throws Exception {
		executeDelete("verify_items", params);
	}
	
	public Record findbyid(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * from verify_items(nolock) WHERE id = :id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public void update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("id", params.remove("id"));
		executeUpdate("verify_items", params, r);
	}
}
