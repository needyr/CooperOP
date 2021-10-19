package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class AbaseDao extends BaseDao{

	public Result queryDept(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from dict_his_deptment (nolock) where 1 = 1 ");
		if(!CommonFun.isNe(params.get("f_key"))) {
			params.put("f_key", "%" + params.get("f_key") + "%");
			sql.append(" and (dept_code like :f_key or dept_name like :f_key or input_code like :f_key) ");
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public Result searchField(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select cast(a1.name as varchar) field, ");
		sql.append("cast(b.value as varchar) beiz,cast(c.name as varchar) as typename ");
		sql.append("from sysobjects a ");
		sql.append("left join  sys.columns a1 on a.id = a1.object_id ");
		sql.append("left join sys.extended_properties b ");
		sql.append("on b.major_id = a.id and b.minor_id = a1.column_id ");
		sql.append("left join sys.types c on c.system_type_id=a1.system_type_id ");
		sql.append("where a.name=:table_name ");
		if(!CommonFun.isNe(params.get("code"))) {
			sql.append(" and cast(a1.name as varchar) in (:code)");
		}
		return executeQuery(sql.toString(), params);
	}
	
	//用户
	public Result queryUsers(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT id,no,name                               ");
		sql.append(" FROM IADSCP..v_system_user (nolock)              ");
		sql.append(" where 1=1                                       ");
		if(!CommonFun.isNe(params.get("data"))){
			params.put("key", "%"+params.get("data")+"%");
			sql.append(" and (id like :key or no like :key   or name like :key ) ");
		}
		sql.append(" order by id                       ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryUsersCheck(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  id,no,name               ");
		sql.append(" from IADSCP..v_system_user (nolock)                            ");               
		sql.append(" where id in (:code)                              ");
		sql.append(" order by id                                      ");
		return executeQuery(sql.toString(), params);
	}
	
	
	
}
