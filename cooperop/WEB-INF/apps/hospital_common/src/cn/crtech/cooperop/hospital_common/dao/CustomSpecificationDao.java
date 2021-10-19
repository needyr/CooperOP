package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class CustomSpecificationDao extends BaseDao {
	
	private final static String TABLE_NAME = "spzl_shuoms_zdy";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT * FROM " + TABLE_NAME);
		sql.append(" (nolock) where 1=1 ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
			sql.append("and  (name_cn like :key or name_en like :key or manufacturer like :key or manufacturer_short_name like :key)");
		}
		setParameter(params, "sys_drug_code", " and sys_drug_code =:sys_drug_code", sql);
		setParameter(params, "approval_number", " and approval_number =:approval_number", sql);
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*,dhd.drug_code as his_drug_code from " + TABLE_NAME+" (nolock) a ");
		sql.append(" left join dict_sys_drug dsd (nolock) on dsd.drug_code = a.sys_drug_code");
		sql.append(" left join dict_his_drug dhd (nolock) on dhd.sys_p_key = dsd.p_key");
		setParameter(params, "sys_drug_code"," where a.sys_drug_code = :sys_drug_code", sql);
		Record record = executeQuerySingleRecord(sql.toString(), params);
		return record;
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}

	public int update(Map<String, Object> params) throws Exception {
		Record record = new Record();
		record.put("sys_drug_code", params.remove("sys_drug_code"));
		return executeUpdate(TABLE_NAME, params, record);
	}
	
	public Result isHas(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select sys_drug_code from " + TABLE_NAME + " (nolock) where sys_drug_code = :sys_drug_code ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}

}
