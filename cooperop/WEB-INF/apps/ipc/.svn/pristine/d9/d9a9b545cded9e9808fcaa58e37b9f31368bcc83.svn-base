package cn.crtech.cooperop.ipc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DoctorDao extends BaseDao{
	
	private final static String TABLE_NAME = "system_users";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select su.*,suc.no,suc.name,suc.password,suc.gender")
		   .append(",suc.telephone,suc.birthday,suc.avatar,suc.state,suc.id sid,sd.name deptname ")  
           .append(" from "+TABLE_NAME+" (nolock) su")  
		   .append(" inner join system_user_cooperop (nolock) suc ")  
		   .append(" on su.cooperop_sys_no = suc.no ")  
		   .append(" left join 	system_department (nolock) sd on sd.id = suc.department")
		   .append(" where su.type = '1' ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
			sql.append(" and (suc.name like :key or suc.telephone like :key)");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result querySync(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select  ");
		sql.append(" su.type,rtrim(su.doctor_no) as doctor_no,su.order_type,su.position,su.description,"
				+ "su.create_time,"
				+ "su.lastModifyTime,rtrim(suc.no) as no,suc.name,suc.password,suc.gender, ");
		sql.append(" suc.telephone,suc.birthday,suc.avatar,                             ");
		sql.append(" suc.state ")
           .append(" from "+TABLE_NAME+" (nolock) su")  
		   .append(" inner join system_user_cooperop (nolock) suc ")  
		   .append(" on rtrim(su.cooperop_sys_no) = rtrim(suc.no) ")  
		   .append(" left join 	system_department (nolock) sd on sd.id = suc.department")
		   .append(" where su.type = '1' or su.type = '2' ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
			sql.append(" and (suc.name like :key or suc.telephone like :key)");
		}
		return executeQuery(sql.toString(), params);
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
		sql.append("select su.*,suc.no,suc.name,suc.password,suc.gender")
		   .append(",suc.telephone,suc.birthday,suc.avatar,suc.state,suc.id sid,suc.department,sd.name dept_name ")  
           .append(" from "+TABLE_NAME+" (nolock) su")  
		   .append(" inner join system_user_cooperop (nolock) suc ")  
		   .append(" on su.cooperop_sys_no = suc.no ")  
		   .append(" left join 	system_department (nolock) sd on sd.id = suc.department")
		   .append(" where su.type = '1' ")
		   .append(" and su.id = :id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getcdoctor(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from  dict_his_users where 1=1");
		sql.append("select dhu.*,dhd.dept_name from dict_his_users (nolock) dhu")
		   .append(" left join dict_his_deptment (nolock) dhd ")
		   .append(" on dhu.user_dept = dhd.dept_code ")
		   .append(" where user_id not in  ")
		   .append(" (select distinct doctor_no from system_users (nolock) where doctor_no is not null ) ");
		sql.append("and user_id = :user_id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result querydoctor(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		//sql.append("select * from dict_his_users where user_id not in (select doctor_no from system_users)");
		sql.append("select dhu.*,dhd.dept_name from dict_his_users (nolock) dhu")
		   .append(" left join dict_his_deptment  (nolock)dhd ")
		   .append(" on dhu.user_dept = dhd.dept_code ")
		   .append(" where user_id not in  ")
		   .append(" (select distinct doctor_no from system_users (nolock) where doctor_no is not null ) ");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
			sql.append(" and dhu.user_name like :key ");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record getByDeptCode(String code) throws Exception{
		StringBuffer sql = new StringBuffer();
		Record r = new Record();
		r.put("dept_code", code);
		sql.append("select * from dict_his_deptment (nolock) where dept_code = :dept_code");
		return executeQuerySingleRecord(sql.toString(), r);
	}
	
	public Result queryBeCommentN(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select id from v_system_user (nolock) where no in (  ");
		sql.append(" select cso.doctor_no from comment_sample_orders cso (nolock)  ");
		sql.append(" left join comment_sample_patients cs (nolock) on cso.sample_id = cs.sample_id  ");
		if(CommonFun.isNe(params.get("djbh"))) {
			sql.append(" where sample_id =  :sample_id group by cso.doctor_no ");
		}else {
			sql.append(" where cs.djbh =  :djbh group by cso.doctor_no ");
		}
		sql.append(" ) ");
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryBeCommentNBySampleid(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select id from v_system_user (nolock) where no in (                   ");
		sql.append(" 		select cso.doctor_no from comment_sample_orders cso  (nolock) ");
		sql.append(" 		left join comment_sample cs (nolock) on cso.sample_id = cs.id  ");
		sql.append(" 	where sample_id =  :sample_id                                    ");
		sql.append(" 		)                                                     ");
		return executeQuery(sql.toString(), params);
	}
	
	public int insertMsg(Map<String, Object> params) throws Exception {
		return executeInsert("system_message", params);
	}
}
