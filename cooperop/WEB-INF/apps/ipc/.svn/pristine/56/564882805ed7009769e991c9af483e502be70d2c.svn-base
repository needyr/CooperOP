package cn.crtech.cooperop.ipc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class PharmacistDao extends BaseDao{
	
	private final static String TABLE_NAME = "system_users";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select su.*,suc.no ,suc.name ,suc.gender,suc.mobile,suc.birthday,suc.state,suc.id sid,sd.name deptname  ")
           .append(" from "+TABLE_NAME+" (nolock) su")  
		   .append(" inner join system_user_cooperop (nolock) suc ")  
		   .append(" on su.cooperop_sys_no = suc.no ")  
		   .append(" left join 	system_department (nolock) sd on sd.id = suc.department ")
		   .append(" where su.type = '2' ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
			sql.append(" and (suc.name like :key or suc.mobile like :key)");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryHas(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select no from system_user_cooperop (nolock) where no = :no ");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int insertCsysUser(Map<String, Object> params) throws Exception {
		return executeInsert("system_user_cooperop", params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	
	public int updateCsysUser(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		return executeUpdate("system_user_cooperop", params, r);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
	
	public int deleteCsysUser(Map<String, Object> params) throws Exception {
		return executeDelete("system_user_cooperop", params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select su.*,suc.no ,suc.name,suc.gender,")
		   .append(" suc.mobile,suc.telephone,suc.weixin,suc.email,")
		   .append(" suc.qq,suc.idcard_type,suc.idcard_no,suc.state,")
		   .append(" suc.birthday,suc.password,suc.user_state,suc.id sid,suc.department,sd.name dept_name ")
           .append(" from "+TABLE_NAME+" (nolock) su ")  
		   .append(" inner join system_user_cooperop (nolock) suc ")  
		   .append(" on su.cooperop_sys_no = suc.no ")  
		   .append(" left join 	system_department (nolock) sd on sd.id = suc.department ")
		   .append(" where su.type = '2' ")
		   .append(" and su.id = :id");
		Record record = executeQuerySingleRecord(sql.toString(), params);
		return record;
	}
	
	/*public Result queryDept(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record getcdoctor(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from  dict_his_users where 1=1");
		sql.append("select dhu.*,dhd.dept_name from dict_his_users dhu")
		   .append(" left join dict_his_deptment dhd ")
		   .append(" on dhu.user_dept = dhd.dept_code ")
		   .append(" where user_id not in (select doctor_no from system_users ) ");
		sql.append("and user_id = :user_id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result querydoctor(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from dict_his_users where user_id not in (select doctor_no from system_users)");
		sql.append("select dhu.*,dhd.dept_name from dict_his_users dhu")
		   .append(" left join dict_his_deptment dhd ")
		   .append(" on dhu.user_dept = dhd.dept_code ")
		   .append(" where user_id not in (select doctor_no from system_users ) ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
			sql.append(" and dhu.user_name like :key ");
		}
		return executeQueryLimit(sql.toString(), params);
	}*/

}
