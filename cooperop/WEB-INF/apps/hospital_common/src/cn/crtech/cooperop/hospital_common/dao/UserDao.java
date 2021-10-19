package cn.crtech.cooperop.hospital_common.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class UserDao extends BaseDao{
	public Result search(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.no,a.name,department_name             ");
		sql.append(" from IADSCP..v_system_user a (nolock)                                                 ");
		sql.append(" inner join hospital_common..system_users b (nolock) on a.no=b.doctor_no ");
		sql.append(" inner join hospital_common..dict_his_users c (nolock) on a.no=c.user_id ");
		sql.append(" where 1=1 ");
		if(!user().isSupperUser() && "1".equals(params.get("is_permission"))) {
			//增加表值函数, 查看权限信息
			sql.append(" and exists (select 1 from user_doctor_permission('"+user().getId()+"') b where a.no=b.no ) ");
		}
		if(!CommonFun.isNe(params.get("data"))){
			params.put("key", "%"+params.get("data")+"%");
			sql.append(" and (a.name like :key or a.no = :data or c.input_code like :key) ");
		}
		if(!CommonFun.isNe(params.get("dept"))) {
			sql.append(" and (department_name like '%'+'"+params.get("dept")+"'+'%' or departments+',' like '"+params.get("dept")+",%')");
		}
		sql.append(" order by a.no                                                                 ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result searchCheck(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.no,a.name,department_name             ");
		sql.append(" from IADSCP..v_system_user a (nolock)                                                 ");
		sql.append(" inner join hospital_common..system_users b (nolock) on a.no=b.doctor_no ");
		sql.append(" and no in(:code)");
		if(!user().isSupperUser() && "1".equals(params.get("is_permission"))) {
			//增加表值函数, 查看权限信息
			sql.append(" and exists (select 1 from user_doctor_permission('"+user().getId()+"') b where a.no=b.no ) ");
		}
		sql.append(" order by a.no                                                                 ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryYaoshi(Map<String, Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select vsc.*,vsc.id as comment_user from IADSCP..v_system_user (nolock) vsc ");
		sql.append(" inner join system_users (nolock) ss on vsc.no = ss.cooperop_sys_no ");
		sql.append(" where ss.type=2 ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "filter", " and (name like :key or no=:filter)", sql);
		return executeQueryLimit(sql.toString(), params);
	}

	public Result queryUsers(Record params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.id, a.no  ");
		sql.append(" from IADSCP..v_system_user a (nolock)                                                 ");
		sql.append(" inner join hospital_common..system_users b (nolock) on a.no=b.doctor_no ");
		sql.append(" inner join hospital_common..dict_his_users c (nolock) on a.no=c.user_id ");
		sql.append(" where 1=1 ");
		return executeQuery(sql.toString(), params);
	}
}
