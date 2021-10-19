package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DictHisFeiBieDao extends BaseDao{
	public Result search(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select feibie_name,feibie_code,input_code   ");
		sql.append(" from dict_his_feibie   (nolock)    where 1=1        ");
		if(!CommonFun.isNe(params.get("data"))){
			params.put("key", "%"+params.get("data")+"%");
			sql.append(" and (feibie_name like :key or feibie_code = :data) ");
		}
		sql.append(" order by feibie_code                       ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result searchCheck(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select feibie_name,feibie_code,input_code   ");
		sql.append(" from dict_his_feibie  (nolock)     where 1=1        ");
		sql.append(" and feibie_code in(:code) ");
		sql.append(" order by feibie_code                       ");
		return executeQuery(sql.toString(), params);
	}
}
