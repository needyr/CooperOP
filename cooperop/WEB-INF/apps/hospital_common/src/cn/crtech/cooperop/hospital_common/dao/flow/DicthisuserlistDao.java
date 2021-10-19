package cn.crtech.cooperop.hospital_common.dao.flow;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DicthisuserlistDao extends BaseDao{
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT                             ");
		sql.append("pkey_id,                           ");
		sql.append("p_key,                             ");
		sql.append("db_user,                           ");
		sql.append("user_id,                           ");
		sql.append("user_name,                         ");
		sql.append("user_dept,                         ");
		sql.append("input_code,                        ");
		sql.append("mobilephone,                       ");
		sql.append("sex,                               ");
		sql.append("job_role,                          ");
		sql.append("anti_level,                        ");
		sql.append("job_level,                         ");
		sql.append("is_doctor                          ");
		sql.append("FROM dict_his_users                ");
		sql.append("WHERE 1=1                          ");
		if (!CommonFun.isNe(params.get("sousuo"))) {
			params.put("sousuo", "%"+params.get("sousuo")+"%");
			sql.append(" and user_name like :sousuo");
		}
		return executeQueryLimit(sql.toString(), params);
	}
}
