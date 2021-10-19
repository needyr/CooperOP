package cn.crtech.cooperop.hospital_common.dao.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class Jb_drug_useDao extends BaseDao {
	public Result query_stats(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #ttt from ( ");
		sql.append(getTypeStr(params,false,false));
		sql.append(") a ");
		sql.append("declare @jb_patient_num bigint  ");
		sql.append("select @jb_patient_num = count(1) from #ttt where jb_drug_kind_num > 0  ");

		sql.append("select *,@jb_patient_num jb_patient_num from #ttt ");

		sql.append("drop table #ttt ");

		return executeQuery(sql.toString(),params);
	}

	public Result query_dept(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #ttt from ( ");
		sql.append(getTypeStr(params,false,false));
		sql.append(") a ");

		sql.append("select a.dept_code,a.dept_name into #ttt2  ");
		sql.append("from #ttt a group by a.dept_code,a.dept_name ");

		sql.append("select a.dept_code,a.dept_name,                                                          ");
		sql.append("(select count(1) from (                                                                  ");
		sql.append("select patient_id,visit_id from #ttt                                                     ");
		sql.append("where a.dept_code=dept_code group by patient_id,visit_id) b) as patient_num,             ");
		sql.append("(select count(1) from (                                                                  ");
		sql.append("select patient_id,visit_id from #ttt                                                     ");
		sql.append("where a.dept_code=dept_code                                                              ");
		sql.append("and jb_drug_kind_num > 0 group by patient_id,visit_id) b) as jb_patient_num,             ");
		sql.append("(select sum(cast(drug_kind_num as float)) from #ttt where a.dept_code=dept_code) as drug_kind_num,      ");
		sql.append("(select sum(cast(jb_drug_kind_num as float)) from #ttt where a.dept_code=dept_code) as jb_drug_kind_num,");
		sql.append("(select sum(cast(drug_money as float)) from #ttt where a.dept_code=dept_code) as drug_money,            ");
		sql.append("(select sum(cast(jb_drug_money as float)) from #ttt where a.dept_code=dept_code) as jb_drug_money       ");
		sql.append("from #ttt2 a                                                                             ");

		sql.append("drop table #ttt ");
		sql.append("drop table #ttt2 ");

		return executeQuery(sql.toString(),params);
	}

	public Result query_doctor(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #ttt from ( ");
		sql.append(getTypeStr(params,false,false));
		sql.append(") a ");

		sql.append("select a.ATTENDING_DOCTOR_code,a.ATTENDING_DOCTOR into #ttt2  ");
		sql.append("from #ttt a group by a.ATTENDING_DOCTOR_code,a.ATTENDING_DOCTOR ");

		sql.append("select a.ATTENDING_DOCTOR_code,a.ATTENDING_DOCTOR,dhd.dept_NAME as attending_doctor_dept,  ");
		sql.append("(select sum(cast(drug_kind_num as float)) from #ttt where a.ATTENDING_DOCTOR_code=ATTENDING_DOCTOR_code) as drug_kind_num,      ");
		sql.append("(select sum(cast(jb_drug_kind_num as float)) from #ttt where a.ATTENDING_DOCTOR_code=ATTENDING_DOCTOR_code) as jb_drug_kind_num,");
		sql.append("(select sum(cast(drug_money as float)) from #ttt where a.ATTENDING_DOCTOR_code=ATTENDING_DOCTOR_code) as drug_money,            ");
		sql.append("(select sum(cast(jb_drug_money as float)) from #ttt where a.ATTENDING_DOCTOR_code=ATTENDING_DOCTOR_code) as jb_drug_money       ");
		sql.append("from #ttt2 a                                                                             ");
		sql.append("left join dict_his_users(nolock) dhu on dhu.USER_ID=a.ATTENDING_DOCTOR_code ");
		sql.append("left join dict_his_deptment(nolock) dhd on dhu.USER_DEPT=dhd.dept_CODE ");

		sql.append("drop table #ttt ");
		sql.append("drop table #ttt2 ");

		return executeQuery(sql.toString(),params);
	}


	public String getTypeStr(Map<String, Object> params,boolean is_dept_permission,boolean is_doc_permission) {
		List<String> list = new ArrayList<String>();
		String re = "";
		if(!CommonFun.isNe(params.get("d_type"))) {
			String[] split = ((String)params.get("d_type")).split(",");
			for (String string : split) {
				if("1".equals(string)) {
					StringBuffer sql = new StringBuffer();
					sql.append("select ");
					sql.append("a.id,                   ");
					sql.append("a.patient_no,           ");
					sql.append("a.patient_id,           ");
					sql.append("a.visit_id,             ");
					sql.append("a.patient_name,         ");
					sql.append("a.age,                  ");
					sql.append("a.sex,                  ");
					sql.append("a.ADMISSION_DATETIME,   ");
					sql.append("a.DISCHARGE_DATETIME,   ");
					sql.append("a.ts,                   ");
					sql.append("a.ATTENDING_DOCTOR_code,");
					sql.append("a.ATTENDING_DOCTOR,     ");
					sql.append("a.dept_name,            ");
					sql.append("a.dept_code,            ");
					sql.append("a.in_diagnosis,         ");
					sql.append("a.out_diagnosis,        ");
					sql.append("a.oper_message,         ");
					sql.append("a.drug_kind_num,        ");
					sql.append("a.kjy_kind_num,         ");
					sql.append("a.all_money,            ");
					sql.append("a.drug_money,           ");
					sql.append("a.kjy_money,            ");
					sql.append("a.one_drug_union_num,   ");
					sql.append("a.two_drug_union_num,   ");
					sql.append("a.three_drug_union_num, ");
					sql.append("a.other_drug_union_num, ");
					sql.append("a.ddd_value,            ");
					sql.append("a.transfuse_num,        ");
					sql.append("a.d_type,               ");
					sql.append("a.xi_drug_money,        ");
					sql.append("a.zongc_drug_money,     ");
					sql.append("a.zongy_drug_money,     ");
					sql.append("a.swzj_drug_money,      ");
					sql.append("a.has_recipe,           ");
					sql.append("a.jb_drug_money,        ");
					sql.append("a.jb_drug_kind_num      ");
					sql.append("from report_kjy_patient (nolock) a                      ");
					sql.append("where a.DISCHARGE_DATETIME                              ");
					sql.append("between :start_time + ' 00:00:00' and :end_time + ' 23:59:59' ");
					sql.append(" and not exists (select 1 from his_in_pats (nolock) where ");
					sql.append(" a.patient_id=patient_id and a.visit_id=visit_id) ");
					if("1".equals(params.get("obj"))) {
						sql.append("and a.drug_money > 0 ");
					}else if("2".equals(params.get("obj"))) {
						sql.append("and a.drug_money > 0 ");
						sql.append("and isnull(a.has_recipe,'0') = '1' ");
					}
					if(!user().isSupperUser()) {
						//增加表值函数, 查看权限信息
						if(is_dept_permission) {
							sql.append(" and exists (select 1 from user_dept_permission('"+user().getId()+"') bb where a.dept_code=bb.dept_code ) ");
						}
						if(is_doc_permission) {
							sql.append(" and exists (select 1 from user_doctor_permission('"+user().getId()+"') bb where a.ATTENDING_DOCTOR_code=bb.no ) ");
						}
					}
					list.add(sql.toString());
				}else if("2".equals(string)) {
					StringBuffer sql = new StringBuffer();
					sql.append("select ");
					sql.append("a.id,                   ");
					sql.append("a.patient_no,           ");
					sql.append("a.patient_id,           ");
					sql.append("a.visit_id,             ");
					sql.append("a.patient_name,         ");
					sql.append("a.age,                  ");
					sql.append("a.sex,                  ");
					sql.append("a.ADMISSION_DATETIME,   ");
					sql.append("a.DISCHARGE_DATETIME,   ");
					sql.append("a.ts,                   ");
					sql.append("a.ATTENDING_DOCTOR_code,");
					sql.append("a.ATTENDING_DOCTOR,     ");
					sql.append("a.dept_name,            ");
					sql.append("a.dept_code,            ");
					sql.append("a.in_diagnosis,         ");
					sql.append("a.out_diagnosis,        ");
					sql.append("a.oper_message,         ");
					sql.append("a.drug_kind_num,        ");
					sql.append("a.kjy_kind_num,         ");
					sql.append("a.all_money,            ");
					sql.append("a.drug_money,           ");
					sql.append("a.kjy_money,            ");
					sql.append("a.one_drug_union_num,   ");
					sql.append("a.two_drug_union_num,   ");
					sql.append("a.three_drug_union_num, ");
					sql.append("a.other_drug_union_num, ");
					sql.append("a.ddd_value,            ");
					sql.append("a.transfuse_num,        ");
					sql.append("a.d_type,               ");
					sql.append("a.xi_drug_money,        ");
					sql.append("a.zongc_drug_money,     ");
					sql.append("a.zongy_drug_money,     ");
					sql.append("a.swzj_drug_money,      ");
					sql.append("a.has_recipe,           ");
					sql.append("a.jb_drug_money,        ");
					sql.append("a.jb_drug_kind_num      ");
					sql.append("from report_kjy_outp_patient (nolock) a   ");
					sql.append("where a.DISCHARGE_DATETIME                       ");
					sql.append("between :start_time + ' 00:00:00' and :end_time + ' 23:59:59' ");
					sql.append("and d_type='2' ");
					if("1".equals(params.get("obj"))) {
						sql.append("and a.drug_money > 0 ");
					}else if("2".equals(params.get("obj"))) {
						sql.append("and a.drug_money > 0 ");
						sql.append("and isnull(a.has_recipe,'0') = '1' ");
					}
					if(!user().isSupperUser()) {
						//增加表值函数, 查看权限信息
						if(is_dept_permission) {
							sql.append(" and exists (select 1 from user_dept_permission('"+user().getId()+"') bb where a.dept_code=bb.dept_code ) ");
						}
						if(is_doc_permission) {
							sql.append(" and exists (select 1 from user_doctor_permission('"+user().getId()+"') bb where a.ATTENDING_DOCTOR_code=bb.no ) ");
						}
					}
					list.add(sql.toString());
				}else if("3".equals(string)) {
					StringBuffer sql = new StringBuffer();
					sql.append("select ");
					sql.append("a.id,                   ");
					sql.append("a.patient_no,           ");
					sql.append("a.patient_id,           ");
					sql.append("a.visit_id,             ");
					sql.append("a.patient_name,         ");
					sql.append("a.age,                  ");
					sql.append("a.sex,                  ");
					sql.append("a.ADMISSION_DATETIME,   ");
					sql.append("a.DISCHARGE_DATETIME,   ");
					sql.append("a.ts,                   ");
					sql.append("a.ATTENDING_DOCTOR_code,");
					sql.append("a.ATTENDING_DOCTOR,     ");
					sql.append("a.dept_name,            ");
					sql.append("a.dept_code,            ");
					sql.append("a.in_diagnosis,         ");
					sql.append("a.out_diagnosis,        ");
					sql.append("a.oper_message,         ");
					sql.append("a.drug_kind_num,        ");
					sql.append("a.kjy_kind_num,         ");
					sql.append("a.all_money,            ");
					sql.append("a.drug_money,           ");
					sql.append("a.kjy_money,            ");
					sql.append("a.one_drug_union_num,   ");
					sql.append("a.two_drug_union_num,   ");
					sql.append("a.three_drug_union_num, ");
					sql.append("a.other_drug_union_num, ");
					sql.append("a.ddd_value,            ");
					sql.append("a.transfuse_num,        ");
					sql.append("a.d_type,               ");
					sql.append("a.xi_drug_money,        ");
					sql.append("a.zongc_drug_money,     ");
					sql.append("a.zongy_drug_money,     ");
					sql.append("a.swzj_drug_money,      ");
					sql.append("a.has_recipe,           ");
					sql.append("a.jb_drug_money,        ");
					sql.append("a.jb_drug_kind_num      ");
					sql.append("from report_kjy_outp_patient (nolock) a   ");
					sql.append("where a.DISCHARGE_DATETIME                       ");
					sql.append("between :start_time + ' 00:00:00' and :end_time + ' 23:59:59' ");
					sql.append("and d_type='3' ");
					if("1".equals(params.get("obj"))) {
						sql.append("and a.drug_money > 0 ");
					}else if("2".equals(params.get("obj"))) {
						sql.append("and a.drug_money > 0 ");
						sql.append("and isnull(a.has_recipe,'0') = '1' ");
					}
					if(!user().isSupperUser()) {
						//增加表值函数, 查看权限信息
						if(is_dept_permission) {
							sql.append(" and exists (select 1 from user_dept_permission('"+user().getId()+"') bb where a.dept_code=bb.dept_code ) ");
						}
						if(is_doc_permission) {
							sql.append(" and exists (select 1 from user_doctor_permission('"+user().getId()+"') bb where a.ATTENDING_DOCTOR_code=bb.no ) ");
						}
					}
					list.add(sql.toString());
				}
			}
		}else {
			StringBuffer sql = new StringBuffer();
			sql.append("select  ");
			sql.append("a.id,                   ");
			sql.append("a.patient_no,           ");
			sql.append("a.patient_id,           ");
			sql.append("a.visit_id,             ");
			sql.append("a.patient_name,         ");
			sql.append("a.age,                  ");
			sql.append("a.sex,                  ");
			sql.append("a.ADMISSION_DATETIME,   ");
			sql.append("a.DISCHARGE_DATETIME,   ");
			sql.append("a.ts,                   ");
			sql.append("a.ATTENDING_DOCTOR_code,");
			sql.append("a.ATTENDING_DOCTOR,     ");
			sql.append("a.dept_name,            ");
			sql.append("a.dept_code,            ");
			sql.append("a.in_diagnosis,         ");
			sql.append("a.out_diagnosis,        ");
			sql.append("a.oper_message,         ");
			sql.append("a.drug_kind_num,        ");
			sql.append("a.kjy_kind_num,         ");
			sql.append("a.all_money,            ");
			sql.append("a.drug_money,           ");
			sql.append("a.kjy_money,            ");
			sql.append("a.one_drug_union_num,   ");
			sql.append("a.two_drug_union_num,   ");
			sql.append("a.three_drug_union_num, ");
			sql.append("a.other_drug_union_num, ");
			sql.append("a.ddd_value,            ");
			sql.append("a.transfuse_num,        ");
			sql.append("a.d_type,               ");
			sql.append("a.xi_drug_money,        ");
			sql.append("a.zongc_drug_money,     ");
			sql.append("a.zongy_drug_money,     ");
			sql.append("a.swzj_drug_money,      ");
			sql.append("a.has_recipe,           ");
			sql.append("a.jb_drug_money,        ");
			sql.append("a.jb_drug_kind_num      ");
			sql.append("from report_kjy_patient (nolock) a                      ");
			sql.append("where a.DISCHARGE_DATETIME                              ");
			sql.append("between :start_time + ' 00:00:00' and :end_time + ' 23:59:59' ");
			sql.append(" and not exists (select 1 from his_in_pats (nolock) where ");
			sql.append(" a.patient_id=patient_id and a.visit_id=visit_id) ");
			if("1".equals(params.get("obj"))) {
				sql.append("and a.drug_money > 0 ");
			}else if("2".equals(params.get("obj"))) {
				sql.append("and a.drug_money > 0 ");
				sql.append("and isnull(a.has_recipe,'0') = '1' ");
			}
			if(!user().isSupperUser()) {
				//增加表值函数, 查看权限信息
				if(is_dept_permission) {
					sql.append(" and exists (select 1 from user_dept_permission('"+user().getId()+"') bb where a.dept_code=bb.dept_code ) ");
				}
				if(is_doc_permission) {
					sql.append(" and exists (select 1 from user_doctor_permission('"+user().getId()+"') bb where a.ATTENDING_DOCTOR_code=bb.no ) ");
				}
			}

			sql.append(" union all ");

			sql.append("select ");
			sql.append("a.id,                   ");
			sql.append("a.patient_no,           ");
			sql.append("a.patient_id,           ");
			sql.append("a.visit_id,             ");
			sql.append("a.patient_name,         ");
			sql.append("a.age,                  ");
			sql.append("a.sex,                  ");
			sql.append("a.ADMISSION_DATETIME,   ");
			sql.append("a.DISCHARGE_DATETIME,   ");
			sql.append("a.ts,                   ");
			sql.append("a.ATTENDING_DOCTOR_code,");
			sql.append("a.ATTENDING_DOCTOR,     ");
			sql.append("a.dept_name,            ");
			sql.append("a.dept_code,            ");
			sql.append("a.in_diagnosis,         ");
			sql.append("a.out_diagnosis,        ");
			sql.append("a.oper_message,         ");
			sql.append("a.drug_kind_num,        ");
			sql.append("a.kjy_kind_num,         ");
			sql.append("a.all_money,            ");
			sql.append("a.drug_money,           ");
			sql.append("a.kjy_money,            ");
			sql.append("a.one_drug_union_num,   ");
			sql.append("a.two_drug_union_num,   ");
			sql.append("a.three_drug_union_num, ");
			sql.append("a.other_drug_union_num, ");
			sql.append("a.ddd_value,            ");
			sql.append("a.transfuse_num,        ");
			sql.append("a.d_type,               ");
			sql.append("a.xi_drug_money,        ");
			sql.append("a.zongc_drug_money,     ");
			sql.append("a.zongy_drug_money,     ");
			sql.append("a.swzj_drug_money,      ");
			sql.append("a.has_recipe,           ");
			sql.append("a.jb_drug_money,        ");
			sql.append("a.jb_drug_kind_num      ");
			sql.append("from report_kjy_outp_patient (nolock) a   ");
			sql.append("where a.DISCHARGE_DATETIME                       ");
			sql.append("between :start_time + ' 00:00:00' and :end_time + ' 23:59:59' ");
			if("1".equals(params.get("obj"))) {
				sql.append("and a.drug_money > 0 ");
			}else if("2".equals(params.get("obj"))) {
				sql.append("and a.drug_money > 0 ");
				sql.append("and isnull(a.has_recipe,'0') = '1' ");
			}
			if(!user().isSupperUser()) {
				//增加表值函数, 查看权限信息
				if(is_dept_permission) {
					sql.append(" and exists (select 1 from user_dept_permission('"+user().getId()+"') bb where a.dept_code=bb.dept_code ) ");
				}
				if(is_doc_permission) {
					sql.append(" and exists (select 1 from user_doctor_permission('"+user().getId()+"') bb where a.ATTENDING_DOCTOR_code=bb.no ) ");
				}
			}
			return sql.toString();
		}

		for (int i = 0; i < list.size(); i++) {
			if(i == 0) {
				re = re + list.get(i);
			}else {
				re = re + " union all " + list.get(i);
			}
		}

		return re;
	}
}
