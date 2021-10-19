package cn.crtech.cooperop.hospital_common.dao.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class Drug_amount_exceedDao extends BaseDao {

	public Result query_stats(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ( ");
		sql.append(getTypeStr(params,false,false));
		sql.append(") a ");
		sql.append(" where a.drug_money >= :range ");
		return executeQuery(sql.toString(),params);
	}

	public Result query_dept(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #ttt from ( ");
		sql.append(getTypeStr(params,false,false));
		sql.append(") a ");

		sql.append("select a.dept_code,a.dept_name into #ttt2  ");
		sql.append("from #ttt a group by a.dept_code,a.dept_name ");

		sql.append("select a.dept_code,a.dept_name,    ");
		sql.append("(select count(1) from #ttt where a.dept_code=dept_code) as zong_count, ");
		sql.append("(select count(1) from #ttt where a.dept_code=dept_code and drug_money >= :range) as range_count ");
		sql.append("from #ttt2 a                       ");

		sql.append("drop table #ttt ");
		sql.append("drop table #ttt2 ");
		return executeQuery(sql.toString(),params);
	}

	public Result query_doctor(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #ttt from ( ");
		sql.append(getTypeStr(params,false,true));
		sql.append(") a ");

		sql.append("select a.ATTENDING_DOCTOR_code,a.ATTENDING_DOCTOR,c.dept_name as doctor_dept into #ttt2  ");
		sql.append("from #ttt a  ");
		sql.append("left join dict_his_users (nolock) b on b.user_id=a.ATTENDING_DOCTOR_code ");
	    sql.append("left join dict_his_deptment (nolock) c on b.USER_DEPT = c.dept_code      ");
		sql.append("group by a.ATTENDING_DOCTOR_code,a.ATTENDING_DOCTOR,c.dept_name          ");

		sql.append("select a.ATTENDING_DOCTOR_code,a.ATTENDING_DOCTOR,a.doctor_dept, ");
		sql.append("(select count(1) from #ttt where a.ATTENDING_DOCTOR_code=ATTENDING_DOCTOR_code) as zong_count, ");
		sql.append("(select count(1) from #ttt where a.ATTENDING_DOCTOR_code=ATTENDING_DOCTOR_code and drug_money >= :range) as range_count ");
		sql.append("from #ttt2 a                       ");

		sql.append("drop table #ttt ");
		sql.append("drop table #ttt2 ");
		return executeQuery(sql.toString(),params);
	}

	public String getTypeStr(Map<String, Object> params,boolean is_dept_permission,boolean is_doc_permission) {
		List<String> list = new ArrayList<String>();
		String re = "";
		String d_type = params.get("d_type").toString();
		String filter = getFilter(params,is_dept_permission,is_doc_permission);
		String fields = getFields();
		if(CommonFun.isNe(d_type)) {
			StringBuffer sql = new StringBuffer();
			sql.append("select "+fields);
			sql.append("from report_prescription(nolock) a                 ");
			sql.append("where                                              ");
			sql.append(filter);
			list.add(sql.toString());
		}else {
			String[] d_type_li = d_type.split(",");
				for (String string : d_type_li) {
					if("2".equals(string)) {
						StringBuffer sql = new StringBuffer();
						sql.append("select "+fields);
						sql.append("from report_prescription(nolock) a                 ");
						sql.append("where                                              ");
						sql.append(filter);
						sql.append("and a.d_type='2' ");
						list.add(sql.toString());
					}else if("3".equals(string)) {
						StringBuffer sql = new StringBuffer();
						sql.append("select "+fields);
						sql.append("from report_prescription(nolock) a                 ");
						sql.append("where                                              ");
						sql.append(filter);
						sql.append("and a.d_type='3' ");
						list.add(sql.toString());
					}
				}

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

/*	public String getTypeStr(Map<String, Object> params,boolean is_dept_permission,boolean is_doc_permission) {
		List<String> list = new ArrayList<String>();
		String re = "";
		Object d_type = params.get("d_type");
		String filter = getFilter(params,is_dept_permission,is_doc_permission);
		String fields = getFields();
		if(CommonFun.isNe(d_type)) {
			StringBuffer sql = new StringBuffer();
			sql.append("select "+fields);
			sql.append("from report_prescription(nolock) a                 ");
			sql.append("where                                              ");
			sql.append(filter);
			list.add(sql.toString());
		}else {
			if(d_type instanceof String) {
				if("2".equals(d_type)) {
					StringBuffer sql = new StringBuffer();
					sql.append("select "+fields);
					sql.append("from report_prescription(nolock) a                 ");
					sql.append("where                                              ");
					sql.append(filter);
					sql.append("and a.d_type='2' ");
					list.add(sql.toString());
				}else if("3".equals(d_type)) {
					StringBuffer sql = new StringBuffer();
					sql.append("select "+fields);
					sql.append("from report_prescription(nolock) a                 ");
					sql.append("where                                              ");
					sql.append(filter);
					sql.append("and a.d_type='3' ");
					list.add(sql.toString());
				}
			}else {
				String[] d_type_li = (String[])d_type;
				for (String string : d_type_li) {
					if("2".equals(string)) {
						StringBuffer sql = new StringBuffer();
						sql.append("select "+fields);
						sql.append("from report_prescription(nolock) a                 ");
						sql.append("where                                              ");
						sql.append(filter);
						sql.append("and a.d_type='2' ");
						list.add(sql.toString());
					}else if("3".equals(string)) {
						StringBuffer sql = new StringBuffer();
						sql.append("select "+fields);
						sql.append("from report_prescription(nolock) a                 ");
						sql.append("where                                              ");
						sql.append(filter);
						sql.append("and a.d_type='3' ");
						list.add(sql.toString());
					}
				}
			}
		}
		for (int i = 0; i < list.size(); i++) {
			if(i == 0) {
				re = re + list.get(i);
			}else {
				re = re + " union all " + list.get(i);
			}
		}
		return re;
	}*/

	public String getFields() {
		StringBuffer sql = new StringBuffer();
		sql.append("a.id,                  ");
		sql.append("a.patient_no,          ");
		sql.append("a.patient_id,          ");
		sql.append("a.visit_id,            ");
		sql.append("a.patient_name,        ");
		sql.append("a.age,                 ");
		sql.append("a.sex,                 ");
		sql.append("a.ADMISSION_DATETIME,  ");
		sql.append("a.DISCHARGE_DATETIME,  ");
		sql.append("a.ts,                  ");
		sql.append("a.ATTENDING_DOCTOR_code,");
		sql.append("a.ATTENDING_DOCTOR,    ");
		sql.append("a.dept_name,           ");
		sql.append("a.dept_code,           ");
		sql.append("a.in_diagnosis,        ");
		sql.append("a.out_diagnosis,       ");
		sql.append("a.oper_message,        ");
		sql.append("a.drug_kind_num,       ");
		sql.append("a.kjy_kind_num,        ");
		sql.append("a.all_money,           ");
		sql.append("a.drug_money,          ");
		sql.append("a.kjy_money,           ");
		sql.append("a.one_drug_union_num,  ");
		sql.append("a.two_drug_union_num,  ");
		sql.append("a.three_drug_union_num,");
		sql.append("a.other_drug_union_num,");
		sql.append("a.ddd_value,           ");
		sql.append("a.transfuse_num,       ");
		sql.append("a.d_type,              ");
		sql.append("a.xi_drug_money,       ");
		sql.append("a.zongc_drug_money,    ");
		sql.append("a.zongy_drug_money,    ");
		sql.append("a.swzj_drug_money,     ");
		sql.append("a.has_recipe,          ");
		sql.append("a.jb_drug_money,       ");
		sql.append("a.jb_drug_kind_num,    ");
		sql.append("a.group_id             ");
		return sql.toString();
	}

	public String getFilter(Map<String, Object> params,boolean is_dept_permission,boolean is_doc_permission) {
		StringBuffer sql = new StringBuffer();
		sql.append(" convert(varchar(10),a.ADMISSION_DATETIME,120) between :start_time and :end_time ");
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
}
