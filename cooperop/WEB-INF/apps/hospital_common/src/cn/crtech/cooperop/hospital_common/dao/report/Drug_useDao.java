package cn.crtech.cooperop.hospital_common.dao.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class Drug_useDao extends BaseDao {

	public Record query_stats(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #ttt from ( ");
		sql.append(getTypeStr(params,false,false));
		sql.append(") a ");

		sql.append("select                                 ");
		sql.append("(select sum(a.all_money)               ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append(") total,                               ");
		sql.append("(select sum(a.drug_money)              ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append(") total_drug,                          ");
		sql.append("(select sum(a.xi_drug_money)           ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append(") xi_drug,                             ");
		sql.append("(select sum(a.zongc_drug_money)        ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append(") zongc_drug,                          ");
		sql.append("(select sum(a.zongy_drug_money)        ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append(") zongy_drug,                          ");
		sql.append("(select sum(a.swzj_drug_money)         ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append(") swzj_drug,                           ");
		sql.append("(select sum(a.kjy_money)               ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append(") kj_drug,                             ");
		sql.append("(select count(1) from (select          ");
		sql.append("a.patient_id,a.visit_id                ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append("group by a.patient_id,a.visit_id) a    ");
		sql.append(") patient_count                        ");
		sql.append("drop table #ttt                        ");
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public Result query_dept(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #ttt from ( ");
		sql.append(getTypeStr(params,false,false));
		sql.append(") a ");

		sql.append("select isnull(dept_code,'') dept_code,dept_name    ");
		sql.append("into #ttt2 from #ttt          ");
		sql.append("group by dept_code,dept_name  ");

		sql.append("select dept_code,dept_name,            ");
		sql.append("(select sum(a.all_money)               ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append("where a.dept_code=dd.dept_code         ");
		sql.append(") total,                               ");
		sql.append("(select sum(a.drug_money)              ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append("where a.dept_code=dd.dept_code         ");
		sql.append(") total_drug,                          ");
		sql.append("(select sum(a.xi_drug_money)           ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append("where a.dept_code=dd.dept_code         ");
		sql.append(") xi_drug,                             ");
		sql.append("(select sum(a.zongc_drug_money)        ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append("where a.dept_code=dd.dept_code         ");
		sql.append(") zongc_drug,                          ");
		sql.append("(select sum(a.zongy_drug_money)        ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append("where a.dept_code=dd.dept_code         ");
		sql.append(") zongy_drug,                          ");
		sql.append("(select sum(a.swzj_drug_money)         ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append("where a.dept_code=dd.dept_code         ");
		sql.append(") swzj_drug,                           ");
		sql.append("(select sum(a.kjy_money)               ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append("where a.dept_code=dd.dept_code         ");
		sql.append(") kj_drug,                             ");
		sql.append("(select count(1) from (select          ");
		sql.append("a.patient_id,a.visit_id                ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append("where a.dept_code=dd.dept_code         ");
		sql.append("group by a.patient_id,a.visit_id) a    ");
		sql.append(") patient_count                        ");
		sql.append("from #ttt2 dd                          ");
		if(!CommonFun.isNe(params.get("sort"))) {
			sql.append(" order by "+params.remove("sort")+" ");
		}
		sql.append("drop table #ttt                        ");
		sql.append("drop table #ttt2                       ");
		return executeQuery(sql.toString(), params);
	}

	public Result query_doctor(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #ttt from ( ");
		sql.append(getTypeStr(params,false,false));
		sql.append(") a ");

		sql.append("select isnull(ATTENDING_DOCTOR_code,'') ATTENDING_DOCTOR_code, ATTENDING_DOCTOR ");
		sql.append("into #ttt2 from #ttt          ");
		sql.append("group by ATTENDING_DOCTOR_code,ATTENDING_DOCTOR    ");

		sql.append("select ATTENDING_DOCTOR_code,ATTENDING_DOCTOR, ");
		sql.append("(select sum(a.all_money)               ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append("where a.ATTENDING_DOCTOR_code=dd.ATTENDING_DOCTOR_code ");
		sql.append(") total,                               ");
		sql.append("(select sum(a.drug_money)              ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append("where a.ATTENDING_DOCTOR_code=dd.ATTENDING_DOCTOR_code ");
		sql.append(") total_drug,                          ");
		sql.append("(select sum(a.xi_drug_money)           ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append("where a.ATTENDING_DOCTOR_code=dd.ATTENDING_DOCTOR_code ");
		sql.append(") xi_drug,                             ");
		sql.append("(select sum(a.zongc_drug_money)       ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append("where a.ATTENDING_DOCTOR_code=dd.ATTENDING_DOCTOR_code ");
		sql.append(") zongc_drug,                          ");
		sql.append("(select sum(a.zongy_drug_money)        ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append("where a.ATTENDING_DOCTOR_code=dd.ATTENDING_DOCTOR_code ");
		sql.append(") zongy_drug,                          ");
		sql.append("(select sum(a.swzj_drug_money)         ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append("where a.ATTENDING_DOCTOR_code=dd.ATTENDING_DOCTOR_code ");
		sql.append(") swzj_drug,                           ");
		sql.append("(select sum(a.kjy_money)               ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append("where a.ATTENDING_DOCTOR_code=dd.ATTENDING_DOCTOR_code ");
		sql.append(") kj_drug,                             ");
		sql.append("(select count(1) from (select          ");
		sql.append("a.patient_id,a.visit_id                ");
		sql.append("from #ttt (nolock) a                   ");
		sql.append("where a.ATTENDING_DOCTOR_code=dd.ATTENDING_DOCTOR_code ");
		sql.append("group by a.patient_id,a.visit_id) a    ");
		sql.append(") patient_count                        ");
		sql.append("from #ttt2 dd  ");
		if(!CommonFun.isNe(params.get("sort"))) {
			sql.append(" order by "+params.remove("sort")+" ");
		}
		sql.append("drop table #ttt                        ");
		sql.append("drop table #ttt2                       ");
		return executeQuery(sql.toString(), params);
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
					sql.append("a.has_recipe            ");
					sql.append("from report_kjy_patient (nolock) a                      ");
					sql.append("where a.DISCHARGE_DATETIME                              ");
					sql.append("between :start_time + ' 00:00:00' and :end_time + ' 23:59:59' ");
					sql.append(" and not exists (select 1 from his_in_pats (nolock) where ");
					sql.append(" a.patient_id=patient_id and a.visit_id=visit_id) ");
					if(!CommonFun.isNe(params.get("dept_code"))) {
						sql.append(" and exists (select 1 from split(:dept_code,',') ");
						sql.append(" sp where sp.col= a.dept_code) ");
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
					if(!CommonFun.isNe(params.get("doctor_code"))) {
						sql.append(" and exists (select 1 from split(:doctor_code,',') ");
						sql.append(" sp where sp.col= a.ATTENDING_DOCTOR_code) ");
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
					sql.append("a.has_recipe            ");
					sql.append("from report_kjy_outp_patient (nolock) a   ");
					sql.append("where a.DISCHARGE_DATETIME                       ");
					sql.append("between :start_time + ' 00:00:00' and :end_time + ' 23:59:59' ");
					sql.append("and d_type='2' ");
					if("1".equals(params.get("d_type2"))) {
						sql.append("and a.drug_money > 0 ");
					}else if("2".equals(params.get("d_type2"))) {
						sql.append("and a.drug_money > 0 ");
						sql.append("and a.has_recipe = '1' ");
					}
					if(!CommonFun.isNe(params.get("dept_code"))) {
						sql.append(" and exists (select 1 from split(:dept_code,',') ");
						sql.append(" sp where sp.col= a.dept_code) ");
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
					if(!CommonFun.isNe(params.get("doctor_code"))) {
						sql.append(" and exists (select 1 from split(:doctor_code,',') ");
						sql.append(" sp where sp.col= a.ATTENDING_DOCTOR_code) ");
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
					sql.append("a.has_recipe            ");
					sql.append("from report_kjy_outp_patient (nolock) a   ");
					sql.append("where a.DISCHARGE_DATETIME                       ");
					sql.append("between :start_time + ' 00:00:00' and :end_time + ' 23:59:59' ");
					sql.append("and d_type='3' ");
					if("1".equals(params.get("d_type2"))) {
						sql.append("and a.drug_money > 0 ");
					}else if("2".equals(params.get("d_type2"))) {
						sql.append("and a.drug_money > 0 ");
						sql.append("and a.has_recipe = '1' ");
					}
					if(!CommonFun.isNe(params.get("dept_code"))) {
						sql.append(" and exists (select 1 from split(:dept_code,',') ");
						sql.append(" sp where sp.col= a.dept_code) ");
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
					if(!CommonFun.isNe(params.get("doctor_code"))) {
						sql.append(" and exists (select 1 from split(:doctor_code,',') ");
						sql.append(" sp where sp.col= a.ATTENDING_DOCTOR_code) ");
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
			sql.append("a.has_recipe            ");
			sql.append("from report_kjy_patient (nolock) a                      ");
			sql.append("where a.DISCHARGE_DATETIME                              ");
			sql.append("between :start_time + ' 00:00:00' and :end_time + ' 23:59:59' ");
			sql.append(" and not exists (select 1 from his_in_pats (nolock) where ");
			sql.append(" a.patient_id=patient_id and a.visit_id=visit_id) ");
			if(!CommonFun.isNe(params.get("dept_code"))) {
				sql.append(" and exists (select 1 from split(:dept_code,',') ");
				sql.append(" sp where sp.col= a.dept_code) ");
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
			if(!CommonFun.isNe(params.get("doctor_code"))) {
				sql.append(" and exists (select 1 from split(:doctor_code,',') ");
				sql.append(" sp where sp.col= a.ATTENDING_DOCTOR_code) ");
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
			sql.append("a.has_recipe            ");
			sql.append("from report_kjy_outp_patient (nolock) a   ");
			sql.append("where a.DISCHARGE_DATETIME                       ");
			sql.append("between :start_time + ' 00:00:00' and :end_time + ' 23:59:59' ");
			if(!CommonFun.isNe(params.get("dept_code"))) {
				sql.append(" and exists (select 1 from split(:dept_code,',') ");
				sql.append(" sp where sp.col= a.dept_code) ");
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
			if(!CommonFun.isNe(params.get("doctor_code"))) {
				sql.append(" and exists (select 1 from split(:doctor_code,',') ");
				sql.append(" sp where sp.col= a.ATTENDING_DOCTOR_code) ");
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
