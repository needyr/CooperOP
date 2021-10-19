package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DictHisOperDao extends BaseDao {

	public Result searchCheck(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select operation_code,operation_name,input_code   ");
		sql.append(" from dict_his_operation  (nolock) where 1=1        ");
		sql.append(" and operation_code in(:code)                      ");
		sql.append(" order by operation_code  ");
		return executeQuery(sql.toString(), params);
	}

	public Result search(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  operation_code,operation_name,input_code   ");
		sql.append(" from dict_his_operation   (nolock)    where 1=1        ");
		if (!CommonFun.isNe(params.get("data"))) {
			params.put("key", "%" + params.get("data") + "%");
			sql.append(" and (operation_name like :key or operation_code = :data or input_code like :key) ");
		}
		sql.append(" order by operation_code                       ");
		return executeQuery(sql.toString(), params);
	}

	// 手术等级
	public Result queryOperTypeCheck(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select operationscale_CODE,operationscale_NAME,input_code   ");
		sql.append(" from dict_his_operationscale  (nolock) where 1=1        ");
		sql.append(" and operationscale_CODE in(:code) ");
		sql.append(" order by operationscale_CODE  ");
		return executeQuery(sql.toString(), params);
	}

	public Result queryOperType(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select operationscale_CODE,operationscale_NAME,input_code   ");
		sql.append(" from dict_his_operationscale  (nolock) where 1=1        ");
		if (!CommonFun.isNe(params.get("data"))) {
			params.put("key", "%" + params.get("data") + "%");
			sql.append(" and (operation_name like :key or operation_code = :data or input_code like :key) ");
		}
		sql.append(" order by operationscale_CODE  ");
		return executeQuery(sql.toString(), params);
	}

	

}
