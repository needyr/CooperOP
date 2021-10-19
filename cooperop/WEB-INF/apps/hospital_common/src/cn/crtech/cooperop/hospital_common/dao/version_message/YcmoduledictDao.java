package cn.crtech.cooperop.hospital_common.dao.version_message;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class YcmoduledictDao extends BaseDao{
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                   ");
		sql.append("module_ID,               ");
		sql.append("module_project,          ");
		sql.append("module_name              ");
		sql.append("from                     ");
		sql.append("dbo.YC_module_dict(nolock)  ");
		sql.append("where 1=1              ");
		if(!CommonFun.isNe(params.get("sousuo"))){
			params.put("sousuo", "%"+params.get("sousuo")+"%");
			sql.append(" and (module_ID like :sousuo or module_project like :sousuo or module_name like :sousuo)");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public void delete(Map<String, Object> params) throws Exception {
		executeDelete("YC_module_dict", params);
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		executeInsert("YC_module_dict", params);
	}
	
	public Record getByModuleid(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("module_id"))){
			StringBuffer sql = new StringBuffer();
			sql.append("select                             ");
			sql.append("module_ID,                         ");
			sql.append("module_project,                    ");
			sql.append("module_name                        ");
			sql.append("from YC_module_dict(nolock)        ");
			sql.append("where module_ID = :module_id       ");
			
			return executeQuerySingleRecord(sql.toString(), params);
		}else {
			return null;
		}
	}
	
	public void update(Map<String, Object> params) throws Exception {
		Record r = new Record();
		r.put("module_id", params.remove("module_id"));
		executeUpdate("YC_module_dict", params, r);
	}
	
	public Result queryMmodulename(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct module_name from YC_module_dict(nolock) where 1=1 ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record getByName(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                             ");
		sql.append("module_ID,                         ");
		sql.append("module_project,                    ");
		sql.append("module_name                        ");
		sql.append("from YC_module_dict(nolock)        ");
		sql.append("where module_name = :module_name       ");
		
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
