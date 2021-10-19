package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class YkRoleDao extends BaseDao{
		
	public Result search(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		                                                                                                
		sql.append("SELECT id , name FROM system_role (nolock)  ");
		sql.append("where system_product_code='hospital_common'     ");
		if(!CommonFun.isNe(params.get("data"))){
			params.put("name", "%"+params.get("data")+"%");
			sql.append(" and name like :name  ");
		}
		sql.append("order by id  ");
		return executeQuery(sql.toString(), params);
	}

	public Result searchCheck(Map<String, Object> params) throws Exception {
		String code = "'"+params.get("code").toString()+"'";
		params.put("code", code.replaceAll(",", "','"));
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id , name FROM system_role (nolock)  ");
		sql.append("where system_product_code='hospital_common'     ");
		if(!CommonFun.isNe(params.get("data"))){
			params.put("name", "%"+params.get("data")+"%");
			sql.append(" and name like :name  ");
		}
		sql.append(" and id in("+params.get("code")+") ");
		sql.append("order by id  ");
		return executeQuery(sql.toString(), params);
	}
}
