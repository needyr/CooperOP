package cn.crtech.cooperop.hospital_common.dao.tpn;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;

public class XiangmuwhDao extends BaseDao {
	
	public Result getXm(Map<String, Object> params) throws Exception {
		if(params.get("class_code")==null) {
			params.put("class_code", "01");
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT XMID,XMMCH,xmdw,fdtype FROM ");
		sql.append(" dict_sys_drug_XM(nolock) where class_code= :class_code "); 
		return executeQueryLimit(sql.toString(), params);
	}

	public Result edit(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from DICT_SYS_DRUG_XM_CLASS(nolock) ");
		return executeQuery(sql.toString());
	}

	public Result getXmValue(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from DICT_SYS_DRUG_XM_VALUE(nolock) where xmid= :xmid");
		return executeQueryLimit(sql.toString(), params);
	}

	public Integer deleteXm(Map<String, Object> params) throws Exception {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("xmid", params.get("xmid"));
		int i = executeDelete("dict_sys_drug_XM", map);
		return i;
	}
	public Integer deleteXmValue(Map<String, Object> params) throws Exception {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("xmid", params.get("xmid"));
		int i = executeDelete("DICT_SYS_DRUG_XM_VALUE", map);
		return i;
	}
	
	public Integer deleteXmValueById(Map<String, Object> params) throws Exception {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", params.get("id"));
		int i = executeDelete("DICT_SYS_DRUG_XM_VALUE", map);
		return i;
	}

	public Integer addXmValue(Map<String, Object> params) throws Exception {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("xmmch", params.get("xmmch"));
		map.put("value", params.get("value"));
		map.put("xmid", params.get("xmid"));
		return executeInsert("DICT_SYS_DRUG_XM_VALUE", params);
	}

	public Integer addXm(Map<String, Object> params) throws Exception {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("xmmch", params.get("xmmch"));
		map.put("xmdw", params.get("xmdw"));
		map.put("fdtype", params.get("fdtype"));
		map.put("class_code", params.get("class_code"));
		return executeInsert("dict_sys_drug_XM", params);
	}

	public Integer addClass(Map<String, Object> params) throws Exception {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("class_name", params.get("classname"));
		return executeInsert("DICT_SYS_DRUG_XM_CLASS", map);
	}

	public Integer deleteClass(Map<String, Object> params) throws Exception {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("class_code", params.get("code"));
		return executeDelete("DICT_SYS_DRUG_XM_CLASS", map);
	}
}
