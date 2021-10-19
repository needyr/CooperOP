package cn.crtech.cooperop.hospital_common.dao.rule_maintenance;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class VerifyitemchildDao extends BaseDao{
	
    public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT                             ");
		sql.append("id,                                ");
		sql.append("field,                             ");
		sql.append("table_name,                        ");
		sql.append("parent_id,                         ");
		sql.append("product                            ");
		sql.append("from verify_item_child(nolock)     ");
		sql.append("WHERE 1=1                          ");
		String p_id = (String) params.get("p_id");
		sql.append(" and parent_id =             ");
		sql.append(p_id);
		if (!CommonFun.isNe(params.get("sousuo"))) {
			params.put("sousuo", "%"+params.get("sousuo")+"%");
			sql.append(" and (id like :sousuo or table_name like :sousuo)");
		}
		return executeQueryLimit(sql.toString(), params);
	}
    
    public void insert(Map<String, Object> params) throws Exception {
		executeInsert("verify_item_child", params);
	}
    
    public void delete(Map<String, Object> params) throws Exception {
		executeDelete("verify_item_child", params);
	}
    
    public Record findbyid(Map<String, Object> params) throws Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT * from verify_item_child(nolock) where id = :id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
    
    public void update(Map<String, Object> params) throws Exception {
    	Record r = new Record();
    	r.put("id", params.remove("id"));
		executeUpdate("verify_item_child", params, r);
	}
    
}
