package cn.crtech.cooperop.hospital_common.dao.report;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class Diagnosis_kjyDao extends BaseDao {
	public Result research_query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		sql.append("select a.*, ");
		sql.append("a.kjy_order_datas AS cf                                       ");
		sql.append("from report_kjy_patient (nolock) a  ");
		sql.append("where kjy_kind_num>0 ");
		sql.append("and a.DISCHARGE_DATETIME                            ");
		sql.append("between :start_time + ' 00:00:00'                   ");
		sql.append("and :end_time + ' 23:59:59'                         ");

		sql2.append("select count(1) ");
		sql2.append("from report_kjy_patient (nolock) a  ");
		sql2.append("where kjy_kind_num>0 ");
		sql2.append("and a.DISCHARGE_DATETIME                            ");
		sql2.append("between :start_time + ' 00:00:00'                   ");
		sql2.append("and :end_time + ' 23:59:59'                         ");

		String fiter = getFifter(params, "a");

		Result executeQuery = executeQueryLimit(sql.toString() + fiter, params, sql2.toString() + fiter);
		return executeQuery;
	}

	public Result cost_comparison_query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();

		sql.append(" select                                          ");
		sql.append(" a.ATTENDING_DOCTOR_code,                        ");
		sql.append(" a.ATTENDING_DOCTOR,                             ");
		sql.append(" dhd.dept_NAME as doctor_dept,                   ");
		sql.append(" count(1) as zl_patient_num,                     ");
		sql.append(" sum(a.all_money) as all_money,                  ");
		sql.append(" sum(a.drug_money) as drug_money,                ");
		sql.append(" sum(cast(REPLACE(a.ts, '天', '') as int)) as ts,    ");
		sql.append(" sum(case when kjy_kind_num>0                    ");
		sql.append(" then 1 else 0 end) as kjy_patient_num,          ");
		sql.append(" sum(a.kjy_money) as kjy_money,                  ");
		sql.append(" sum(a.kjy_kind_num) as kjy_kind_num,            ");
		sql.append(" sum(case when sys_DISCHARGE_DISPOSITION ='治愈' ");
		sql.append(" then 1 else 0 end) as result_zy_num,            ");
		sql.append(" sum(case when sys_DISCHARGE_DISPOSITION ='好转' ");
		sql.append(" then 1 else 0 end) as result_hz_num,            ");
		sql.append(" sum(case when sys_DISCHARGE_DISPOSITION ='未愈' ");
		sql.append(" then 1 else 0 end) as result_wy_num,            ");
		sql.append(" sum(case when sys_DISCHARGE_DISPOSITION ='死亡' ");
		sql.append(" then 1 else 0 end) as result_sw_num,            ");
		sql.append(" sum(case when sys_DISCHARGE_DISPOSITION         ");
		sql.append(" not in ('治愈','好转','未愈','死亡')            ");
		sql.append(" then 1 else 0 end) as result_qt_num             ");
		sql.append(" from report_kjy_patient (nolock) a              ");
		sql.append(" inner join dict_his_users (nolock) dhu          ");
		sql.append(" on a.ATTENDING_DOCTOR_code = dhu.USER_ID        ");
		sql.append(" inner join dict_his_deptment (nolock) dhd       ");
		sql.append(" on dhu.USER_DEPT = dhd.dept_CODE                ");
		sql.append(" where                                           ");
		sql.append(" a.DISCHARGE_DATETIME                            ");
		sql.append(" between :start_time + ' 00:00:00'               ");
		sql.append(" and :end_time + ' 23:59:59'                     ");

		sql2.append("select count(1) from (select count(1) count ");
		sql2.append("from report_kjy_patient (nolock) a  ");
		sql2.append("where  ");
		sql2.append("a.DISCHARGE_DATETIME                            ");
		sql2.append("between :start_time + ' 00:00:00'                   ");
		sql2.append("and :end_time + ' 23:59:59'                         ");

		String fiter = getFifter(params, "a");

		Result executeQuery = executeQueryLimit(sql.toString() + fiter +
				" group by a.ATTENDING_DOCTOR_code,a.ATTENDING_DOCTOR,dhd.dept_NAME " +
				" having sum( CASE WHEN a.kjy_kind_num > 0 THEN 1 ELSE 0 END ) > 0 ",
				params,
				sql2.toString() + fiter +
				" group by a.ATTENDING_DOCTOR_code,a.ATTENDING_DOCTOR "+
				" having sum( CASE WHEN a.kjy_kind_num > 0 THEN 1 ELSE 0 END ) > 0) a");
		return executeQuery;
	}

	public Result sum_query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();

		sql.append("select ");
		sql.append(" count(1) as zl_patient_num,					 ");
		sql.append(" sum(case when sys_DISCHARGE_DISPOSITION ='治愈' ");
		sql.append(" then 1 else 0 end) as result_zy_num,            ");
		sql.append(" sum(cast(REPLACE(a.ts, '天', '') as int)) as ts,    ");
		sql.append(" sum(a.all_money) as all_money,                  ");
		sql.append(" sum(a.drug_money) as drug_money,                ");
		sql.append(" sum(case when kjy_kind_num>0                    ");
		sql.append(" then 1 else 0 end) as kjy_patient_num,          ");
		sql.append(" sum(a.kjy_money) as kjy_money,                  ");
		sql.append(" sum(case when kjy_kind_num>0                    ");
		sql.append(" then a.all_money else 0 end) as kjy_all_money   ");

		sql.append("from report_kjy_patient (nolock) a  ");
		sql.append("where ");
		sql.append(" a.DISCHARGE_DATETIME                            ");
		sql.append(" between :start_time + ' 00:00:00'                   ");
		sql.append(" and :end_time + ' 23:59:59'                         ");


		String fiter = getFifter(params, "a");

		Result executeQuery = executeQuery(sql.toString() + fiter, params);
		return executeQuery;
	}

	public String getFifter(Map<String, Object> map, String a) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.putAll(map);
		StringBuffer sql = new StringBuffer();

		if(!user().isSupperUser()) {
			//增加表值函数, 查看权限信息
			//sql.append(" and exists (select 1 from user_dept_permission('"+user().getId()+"') bb where "+a+".dept_code=bb.dept_code ) ");
			//sql.append(" and exists (select 1 from user_doctor_permission('"+user().getId()+"') bb where "+a+".ATTENDING_DOCTOR_code=bb.no ) ");
		}

		if(!CommonFun.isNe(params.get("dept_code"))) {
			sql.append(" and exists (select 1 from split(:dept_code,',') ");
			sql.append(" sp where sp.col= "+a+".dept_code) ");
		}

		if(!CommonFun.isNe(params.get("doctor_name"))) {
			sql.append(" and exists (select 1 from split(:doctor_name,',') ");
			sql.append(" sp where sp.col= "+a+".attending_doctor) ");
		}

		//if(!CommonFun.isNe(params.get("diagnosis_name"))) {
			sql.append(" and EXISTS(select 1 from ");
			sql.append("his_in_diagnosis zd(nolock) ");
			sql.append("inner join dict_his_diagnosisclass his_class(nolock) ");
			sql.append("on zd.DIAGNOSIS_TYPE=his_class.diagnosisclass_CODE ");
			sql.append("inner join dict_sys_diagnosisclass sys_class(nolock) ");
			sql.append("on sys_class.P_KEY=his_class.SYS_P_KEY ");
			sql.append("where zd.patient_id="+a+".patient_id ");
			sql.append("and zd.visit_id="+a+".visit_id ");
			sql.append("and sys_class.p_key='2' ");
			//if(!CommonFun.isNe(params.get("diagnosis_name"))) {
				sql.append(" and EXISTS (select 1 from (  ");
				sql.append("select col from split(:diagnosis_name,',')) sp ");
				sql.append("where zd.DIAGNOSIS_DESC like '%'+sp.col+'%' ) ");
			//}
			sql.append(" ) ");
		//}

		return sql.toString();
	}
}
