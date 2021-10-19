package cn.crtech.cooperop.hospital_common.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DepartmentDao extends BaseDao{

	private final static String TABLE_NAME = "system_department";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("select * from "+TABLE_NAME+" (nolock) where 1=1");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
		}
		setParameter(params, "filter", " and (advicename like :key )", sql);
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
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(TABLE_NAME, params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from "+TABLE_NAME+" (nolock) where 1 = 1");
		setParameter(params, "id", " and id =:id ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getHisDept(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from  dict_his_deptment (nolock) where 1=1");
		sql.append("and dept_code = :dept_code");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Result queryHisDept(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from  dict_his_deptment (nolock) where 1=1");
		if(!CommonFun.isNe(params.get("filter"))){
			params.put("key", "%"+params.get("filter")+"%");
			sql.append(" and dept_name like :key ");
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public Result querySearch(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                         ");
		sql.append("a.dept_NAME,                     ");
		sql.append("a.dept_CODE,a.input_code,          ");
		sql.append("(case a.outp_or_inp              ");
		sql.append("when 1 then '住院'       ");
		sql.append("when '2' then '门诊'       ");
		sql.append("when '3' then '急诊'       ");
		sql.append("else '其他' end) outp_or_inp   ");
		sql.append("from dict_his_deptment (nolock) a ");
		sql.append("where  1=1     ");
		if(!user().isSupperUser() && "1".equals(params.get("is_permission"))) {
			//增加表值函数, 查看权限信息
			sql.append(" and exists (select 1 from user_dept_permission('"+user().getId()+"') b where a.dept_CODE=b.dept_code ) ");
		}
		if (!CommonFun.isNe(params.get("datasouce"))) {
			if(params.get("datasouce") instanceof String) {
				sql.append(" and a.outp_or_inp = :datasouce ");
			}else {
				String[] ss = (String[])params.get("datasouce");
				sql.append(" and a.outp_or_inp in ( ");
				int i= 1;
				for (String string : ss) {
					if(i == 1) {
						sql.append("'"+string+"'");
						i++;
					}else {
						sql.append(",'"+string+"'");
					}
				}
				sql.append(" ) ");
			}
		}
		if(!CommonFun.isNe(params.get("data"))){
			params.put("key", "%"+params.get("data")+"%");
			sql.append(" and (a.dept_name like :key or a.dept_code = :data or a.input_code like :key) ");
		}
		sql.append("order by a.dept_CODE         ");
		return executeQuery(sql.toString(), params);
	}

	public Result searchCheck(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                         ");
		sql.append("a.dept_NAME,                     ");
		sql.append("a.dept_CODE,a.input_code,          ");
		sql.append("(case a.outp_or_inp              ");
		sql.append("when 1 then '急诊，门诊'       ");
		sql.append("when '-1' then '急诊，门诊'       ");
		sql.append("else '住院' end) outp_or_inp   ");
		sql.append("from dict_his_deptment  (nolock) a  where  1 = 1 ");
		if(!user().isSupperUser() && "1".equals(params.get("is_permission"))) {
			//增加表值函数, 查看权限信息
			sql.append(" and exists (select 1 from user_dept_permission('"+user().getId()+"') b where a.dept_CODE=b.dept_code ) ");
		}
		//药控无datasouce
		if(!CommonFun.isNe(params.get("datasouce"))){
			sql.append("and a.outp_or_inp = (CASE     ");
			sql.append("WHEN 1=:datasouce THEN 0       ");
			sql.append("ELSE 1                         ");
			sql.append("END)                           ");
		}
		sql.append(" and a.dept_code in(:code) ");
		sql.append("order by a.dept_CODE             ");
		return executeQuery(sql.toString(), params);
	}
}
