package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class Pharmacist_check_passDao extends BaseDao {
	private final static String TABLE_NAME = "pharmacist_check_pass";

	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select sort_name,b.level_name as change_level_name,                        ");
		sql.append("c.level_name as level_name,a.drug_P_KEY_1 drug_codes,                         ");
		sql.append("related_drugs_show,a.description,reference,                                   ");
		sql.append("shenc_pass_time,shenc_pass_ren,shenc_pass_gnmch,                              ");
		sql.append("shenc_pass_pharmacist_advice,id,a.beactive,                                   ");
		sql.append("pharmacist_Todoctor_advice,a.auto_audit_id,a.check_result_info_id             ");
		sql.append("from "+TABLE_NAME+" a(nolock) inner join sys_check_level b(nolock) "); 
		sql.append("on a.shenc_change_level=b.level_code and b.product_code='ipc'                  ");
		sql.append(" inner join sys_check_level c(nolock) ");
		sql.append("on a.level=c.level_code and c.product_code='ipc'                  ");
		sql.append("where 1=1                                                         ");
		setParameter(params, "drug_name", " and related_drugs_show like '%"+params.get("drug_name")+"%' ", sql);
		setParameter(params, "s_time", " and shenc_pass_time >= :s_time ", sql);
		setParameter(params, "e_time", " and shenc_pass_time <= :e_time ", sql);
		return executeQueryLimit(sql.toString(), params);
	}

	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from "+TABLE_NAME+" (nolock) ");
		sql.append("where id=:id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public void update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		executeUpdate(TABLE_NAME, params, r);
	}

	public void delete(Map<String, Object> params) throws Exception {
		executeDelete(TABLE_NAME, params);
	}
	
}
