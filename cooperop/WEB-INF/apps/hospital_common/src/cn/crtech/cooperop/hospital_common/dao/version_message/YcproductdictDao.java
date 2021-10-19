package cn.crtech.cooperop.hospital_common.dao.version_message;

import java.util.Map;


import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class YcproductdictDao extends BaseDao{
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                 ");
		sql.append("product_ID,            ");
		sql.append("pro_name               ");
		sql.append("from                   ");
		sql.append("dbo.YC_product_dict(nolock)  ");
		sql.append("where 1=1              ");
		if(!CommonFun.isNe(params.get("sousuo"))){
			params.put("sousuo", "%"+params.get("sousuo")+"%");
			sql.append(" and (product_ID like :sousuo or pro_name like :sousuo)");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public void delete(Map<String, Object> params) throws Exception {
		executeDelete("YC_product_dict", params);
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		executeInsert("YC_product_dict", params);
	}
	
	public Record getById(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("product_id"))){
			StringBuffer sql = new StringBuffer();
			sql.append("select                             ");
			sql.append("product_ID,                        ");
			sql.append("pro_name                           ");
			sql.append("from YC_product_dict(nolock)       ");
			sql.append("where product_ID = :product_id     ");
			
			return executeQuerySingleRecord(sql.toString(), params);
		}else {
			return null;
		}
	}
	
	public void update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("product_id", params.remove("product_id"));
		executeUpdate("YC_product_dict", params, r);
	}
	
	public Record getByName(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                             ");
		sql.append("product_ID,                        ");
		sql.append("pro_name                           ");
		sql.append("from YC_product_dict(nolock)       ");
		sql.append("where pro_name = :pro_name     ");
		
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
