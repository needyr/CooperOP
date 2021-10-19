package cn.crtech.cooperop.hospital_common.dao.auditset;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class CheckDeptDao extends BaseDao{

	public static final String TABLE_NAME = "sys_dept_check";
	public static final String DICT_HIS_DEPTMENT = "dict_his_deptment";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from " + TABLE_NAME );
		sql.append("(nolock) where 1 = 1 ");
		if(!CommonFun.isNe(params.get("condition"))) {
			params.put("condition", "%" + params.get("condition") +"%");
			sql.append(" and (dept_code like :condition or dept_name like :condition) ");
		}
		if(!CommonFun.isNe(params.get("product_code"))) {
			sql.append(" and product_code = :product_code ");
		}
		if(!CommonFun.isNe(params.get("state"))) {
			sql.append(" and state = :state ");
		}
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryLoad(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select dept_code, dept_name from " + TABLE_NAME );
		sql.append("(nolock) where 1 = 1 ");
		if(!CommonFun.isNe(params.get("product_code"))) {
			sql.append(" and product_code = :product_code ");
		}
		if(!CommonFun.isNe(params.get("state"))) {
			sql.append(" and state = :state ");
		}
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryDeptAll(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select dept_code,dept_name  from dict_his_deptment " );
		sql.append("(nolock) where 1 = 1 ");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("condition", "%" + params.get("filter") +"%");
			sql.append(" and dept_code like :condition or dept_name like :condition ");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result querydept(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select *,  ");
		sql.append(" (case OUTP_OR_INP when 1 then '住院科室' ");
		sql.append(" when 2 then '门诊科室'                   ");
		sql.append(" when 3 then '急诊科室'                   ");
		sql.append(" else '其他科室'                   ");
		sql.append(" end) as dept_type                        ");
		sql.append(" from dict_his_deptment (nolock) ");
		sql.append("where not exists(select dept_code from sys_dept_check where dept_code = dict_his_deptment.dept_code and product_code = :product_code) ");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("condition", "%" + params.get("filter") +"%");
			sql.append(" and (dept_code like :condition or dept_name like :condition) ");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Result queryToDeptType(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append(" select *,  ");
		sql.append(" (case cast(OUTP_OR_INP as varchar) when '1' then '住院科室' ");
		sql.append(" when '2' then '门诊科室'                   ");
		sql.append(" when 3 then '急诊科室'                   ");
		sql.append(" else '其他科室'                   ");
		sql.append(" end) as dept_type                        ");
		sql.append(" from dict_his_deptment (nolock) ");
		sql.append("where 1=1 ");
		if(!CommonFun.isNe(params.get("filter"))) {
			params.put("condition", "%" + params.get("filter") +"%");
			sql.append(" and (cast(dept_code as varchar) like :condition or dept_name like :condition) ");
		}
		params.remove("sort");
		return executeQueryLimit(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record r=new Record();
		r.put("id", params.remove("id"));
		return executeUpdate(TABLE_NAME, params, r);
	}
	
	public void update_state(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update a set a.state = :state,            ");
		sql.append("a.update_time=:update_time                ");
		sql.append("from sys_dept_check a                   ");
		sql.append("inner join dict_his_deptment(nolock) b  ");
		sql.append("on a.dept_code=b.dept_code  ");
		sql.append("where a.product_code = :product_code  ");
		if(!"all".equals(params.get("dept"))) {
			sql.append("and b.OUTP_OR_INP = :dept ");
		}
		execute(sql.toString(), params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME);
		sql.append(" (nolock) where  id = :id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public String updateCI(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" update sys_dept_check set client_install = 1 ");
		sql.append(" where product_code = :product_code ");
		sql.append(" and dept_code in ( ");
		sql.append(" select dept_code from auto_common (nolock) ");
		sql.append(" where dept_code is not null and is_after is null group by dept_code ");
		sql.append(" ) ");
		return execute(sql.toString(), params);
	}
}
