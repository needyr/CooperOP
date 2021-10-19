package cn.crtech.cooperop.hospital_common.dao.version_message;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class YcupgradetypedictDao extends BaseDao{
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                 ");
		sql.append("type_ID,               ");
		sql.append("type_name              ");
		sql.append("from                   ");
		sql.append("dbo.YC_upgrade_type_dict(nolock)  ");
		sql.append("where 1=1              ");
		if(!CommonFun.isNe(params.get("sousuo"))){
			params.put("sousuo", "%"+params.get("sousuo")+"%");
			sql.append(" and (type_ID like :sousuo or type_name like :sousuo)");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public void delete(Map<String, Object> params) throws Exception {
		executeDelete("YC_upgrade_type_dict", params);
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		executeInsert("YC_upgrade_type_dict", params);
	}
	
	public Record getByTypeid(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("type_id"))){
			StringBuffer sql = new StringBuffer();
			sql.append("select                             ");
			sql.append("type_ID,                           ");
			sql.append("type_name                          ");
			sql.append("from YC_upgrade_type_dict(nolock)  ");
			sql.append("where type_ID = :type_id           ");
			
			return executeQuerySingleRecord(sql.toString(), params);
		}else {
			return null;
		}
	}
	
	public void update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("type_id", params.remove("type_id"));
		executeUpdate("YC_upgrade_type_dict", params, r);
	}
	
	public Record getByname(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                             ");
		sql.append("type_ID,                           ");
		sql.append("type_name                          ");
		sql.append("from YC_upgrade_type_dict(nolock)  ");
		sql.append("where type_name = :type_name           ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
