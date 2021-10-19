package cn.crtech.cooperop.hospital_common.dao.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class Drug_kindDao extends BaseDao {

	public Result query_stats(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #ttt from ( ");
		sql.append(getTypeStr(params, false, false));
		sql.append(") a ");

		sql.append("select                                                         ");
		sql.append("(select count(1) from (select common_name from #ttt            ");
		sql.append("group by common_name) a                                        ");
		sql.append(") zong,                                                        ");
		sql.append("(select count(1) from (select a.common_name from #ttt a        ");
		sql.append("inner join dict_his_drug b (nolock) on a.drug_code=b.drug_code ");
		sql.append("and isnull(b.IS_JBYW,'0') = '1'                                ");
		sql.append("group by a.common_name) a                                      ");
		sql.append(") jbyw,                                                        ");
		sql.append("(select count(1) from (select a.common_name from #ttt a        ");
		sql.append("inner join dict_his_drug b (nolock) on a.drug_code=b.drug_code ");
		sql.append("and isnull(b.IS_ANTI_DRUG,'0') in ('1','2','4')                ");
		sql.append("group by a.common_name) a                                      ");
		sql.append(") kjy,                                                         ");
		sql.append("(select count(1) from (select a.common_name from #ttt a        ");
		sql.append("inner join dict_his_drug b (nolock) on a.drug_code=b.drug_code ");
		sql.append("and isnull(b.IS_ANTI_DRUG,'0') in ('1','2','4')                ");
		sql.append("and isnull(b.ANTI_LEVEL,'') in ('','1')                        ");
		sql.append("group by a.common_name) a                                      ");
		sql.append(") kjy1,                                                        ");
		sql.append("(select count(1) from (select a.common_name from #ttt a        ");
		sql.append("inner join dict_his_drug b (nolock) on a.drug_code=b.drug_code ");
		sql.append("and isnull(b.IS_ANTI_DRUG,'0') in ('1','2','4')                ");
		sql.append("and b.ANTI_LEVEL = '2'                                         ");
		sql.append("group by a.common_name) a                                      ");
		sql.append(") kjy2,                                                        ");
		sql.append("(select count(1) from (select a.common_name from #ttt a        ");
		sql.append("inner join dict_his_drug b (nolock) on a.drug_code=b.drug_code ");
		sql.append("and isnull(b.IS_ANTI_DRUG,'0') in ('1','2','4')                ");
		sql.append("and b.ANTI_LEVEL = '3'                                         ");
		sql.append("group by a.common_name) a                                      ");
		sql.append(") kjy3,                                                        ");
		sql.append("(select count(1) from (select a.common_name from #ttt a        ");
		sql.append("inner join dict_his_drug b (nolock) on a.drug_code=b.drug_code ");
		sql.append("and isnull(b.IS_ANTI_DRUG,'0') = '3'                           ");
		sql.append("group by a.common_name) a                                      ");
		sql.append(") kjy_kjh                                                      ");

		sql.append("drop table #ttt ");
		return executeQuery(sql.toString(), params);
	}

	public Result query_dept(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #ttt from ( ");
		sql.append(getTypeStr(params, false, false));
		sql.append(") a ");

		sql.append("select dept_code,dept_name into #ttt2 from ");
		sql.append("#ttt group by dept_code,dept_name          ");

		sql.append("select dept_code,dept_name,                                    ");
		sql.append("(select count(1) from (select common_name from #ttt            ");
		sql.append("where cc.dept_code = dept_code                                 ");
		sql.append("group by common_name) a                                        ");
		sql.append(") zong,                                                        ");
		sql.append("(select count(1) from (select a.common_name from #ttt a        ");
		sql.append("inner join dict_his_drug b (nolock) on a.drug_code=b.drug_code ");
		sql.append("and isnull(b.IS_JBYW,'0') = '1'                                ");
		sql.append("where cc.dept_code = a.dept_code                               ");
		sql.append("group by a.common_name) a                                      ");
		sql.append(") jbyw,                                                        ");
		sql.append("(select count(1) from (select a.common_name from #ttt a        ");
		sql.append("inner join dict_his_drug b (nolock) on a.drug_code=b.drug_code ");
		sql.append("and isnull(b.IS_ANTI_DRUG,'0') in ('1','2','4')                ");
		sql.append("where cc.dept_code = a.dept_code                               ");
		sql.append("group by a.common_name) a                                      ");
		sql.append(") kjy,                                                         ");
		sql.append("(select count(1) from (select a.common_name from #ttt a        ");
		sql.append("inner join dict_his_drug b (nolock) on a.drug_code=b.drug_code ");
		sql.append("and isnull(b.IS_ANTI_DRUG,'0') in ('1','2','4')                ");
		sql.append("and isnull(b.ANTI_LEVEL,'') in ('','1')                        ");
		sql.append("where cc.dept_code = a.dept_code                               ");
		sql.append("group by a.common_name) a                                      ");
		sql.append(") kjy1,                                                        ");
		sql.append("(select count(1) from (select a.common_name from #ttt a        ");
		sql.append("inner join dict_his_drug b (nolock) on a.drug_code=b.drug_code ");
		sql.append("and isnull(b.IS_ANTI_DRUG,'0') in ('1','2','4')                ");
		sql.append("and b.ANTI_LEVEL = '2'                                         ");
		sql.append("where cc.dept_code = a.dept_code                               ");
		sql.append("group by a.common_name) a                                      ");
		sql.append(") kjy2,                                                        ");
		sql.append("(select count(1) from (select a.common_name from #ttt a        ");
		sql.append("inner join dict_his_drug b (nolock) on a.drug_code=b.drug_code ");
		sql.append("and isnull(b.IS_ANTI_DRUG,'0') in ('1','2','4')                ");
		sql.append("and b.ANTI_LEVEL = '3'                                         ");
		sql.append("where cc.dept_code = a.dept_code                               ");
		sql.append("group by a.common_name) a                                      ");
		sql.append(") kjy3                                                         ");
		sql.append("from #ttt2 cc                                                  ");

		sql.append("drop table #ttt ");
		sql.append("drop table #ttt2 ");
		return executeQuery(sql.toString(), params);
	}

	public Result query_doctor(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #ttt from ( ");
		sql.append(getTypeStr(params, false, false));
		sql.append(") a ");

		sql.append("select doctor_code,doctor_name into #ttt2 from ");
		sql.append("#ttt group by doctor_code,doctor_name          ");

		sql.append("select doctor_code,doctor_name,                                ");
		sql.append("(select count(1) from (select common_name from #ttt            ");
		sql.append("where cc.doctor_code = doctor_code                             ");
		sql.append("group by common_name) a                                        ");
		sql.append(") zong,                                                        ");
		sql.append("(select count(1) from (select a.common_name from #ttt a        ");
		sql.append("inner join dict_his_drug b (nolock) on a.drug_code=b.drug_code ");
		sql.append("and isnull(b.IS_JBYW,'0') = '1'                                ");
		sql.append("where cc.doctor_code = a.doctor_code                           ");
		sql.append("group by a.common_name) a                                      ");
		sql.append(") jbyw,                                                        ");
		sql.append("(select count(1) from (select a.common_name from #ttt a        ");
		sql.append("inner join dict_his_drug b (nolock) on a.drug_code=b.drug_code ");
		sql.append("and isnull(b.IS_ANTI_DRUG,'0') in ('1','2','4')                           ");
		sql.append("where cc.doctor_code = a.doctor_code                           ");
		sql.append("group by a.common_name) a                                      ");
		sql.append(") kjy,                                                         ");
		sql.append("(select count(1) from (select a.common_name from #ttt a        ");
		sql.append("inner join dict_his_drug b (nolock) on a.drug_code=b.drug_code ");
		sql.append("and isnull(b.IS_ANTI_DRUG,'0')  in ('1','2','4')               ");
		sql.append("and isnull(b.ANTI_LEVEL,'') in ('','1')                        ");
		sql.append("where cc.doctor_code = a.doctor_code                           ");
		sql.append("group by a.common_name) a                                      ");
		sql.append(") kjy1,                                                        ");
		sql.append("(select count(1) from (select a.common_name from #ttt a        ");
		sql.append("inner join dict_his_drug b (nolock) on a.drug_code=b.drug_code ");
		sql.append("and isnull(b.IS_ANTI_DRUG,'0')  in ('1','2','4')               ");
		sql.append("and b.ANTI_LEVEL = '2'                                         ");
		sql.append("where cc.doctor_code = a.doctor_code                           ");
		sql.append("group by a.common_name) a                                      ");
		sql.append(") kjy2,                                                        ");
		sql.append("(select count(1) from (select a.common_name from #ttt a        ");
		sql.append("inner join dict_his_drug b (nolock) on a.drug_code=b.drug_code ");
		sql.append("and isnull(b.IS_ANTI_DRUG,'0')  in ('1','2','4')               ");
		sql.append("and b.ANTI_LEVEL = '3'                                         ");
		sql.append("where cc.doctor_code = a.doctor_code                           ");
		sql.append("group by a.common_name) a                                      ");
		sql.append(") kjy3                                                         ");
		sql.append("from #ttt2 cc                                                  ");

		sql.append("drop table #ttt ");
		sql.append("drop table #ttt2 ");
		return executeQuery(sql.toString(), params);
	}

	public String getTypeStr(Map<String, Object> params, boolean is_dept_permission, boolean is_doc_permission) {
		List<String> list = new ArrayList<String>();
		String re = "";
		Object d_type = params.get("d_type");
		String filter = getFilter(params, is_dept_permission, is_doc_permission);
		String fields = getFields();
		if (CommonFun.isNe(d_type)) {
			StringBuffer sql = new StringBuffer();
			sql.append("select " + fields);
			sql.append("from report_DDD (nolock) a                     ");
			sql.append("inner join dict_his_drug (nolock) dhd on dhd.drug_code=a.drug_code ");
			sql.append("where                                              ");
			sql.append(filter);
			list.add(sql.toString());
		} else {
			if (d_type instanceof String) {
				StringBuffer sql = new StringBuffer();
				sql.append("select " + fields);
				sql.append("from report_DDD(nolock) a                     ");
				sql.append("left join dict_his_drug (nolock) dhd on dhd.drug_code=a.drug_code ");
				sql.append("where                                              ");
				sql.append(filter);
				sql.append(" and a.d_type= '" + d_type + "'");
				list.add(sql.toString());
			} else {
				String[] d_type_li = (String[]) params.get("d_type");
				for (String string : d_type_li) {
					StringBuffer sql = new StringBuffer();
					sql.append("select " + fields);
					sql.append("from report_DDD(nolock) a                     ");
					sql.append("left join dict_his_drug (nolock) dhd on dhd.drug_code=a.drug_code ");
					sql.append("where                                              ");
					sql.append(filter);
					sql.append(" and a.d_type= '" + string + "'");
					list.add(sql.toString());
				}
			}
		}
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				re = re + list.get(i);
			} else {
				re = re + " union all " + list.get(i);
			}
		}
		return re;
	}

	public String getFields() {
		StringBuffer sql = new StringBuffer();
		sql.append("a.dept_code,       ");
		sql.append("a.dept_name,       ");
		sql.append("a.doctor_code,     ");
		sql.append("a.doctor_name,     ");
		sql.append("a.drug_code,       ");
		sql.append("a.drug_name,       ");
		sql.append("a.sylc,            ");
		sql.append("a.d_type,          ");
		sql.append("a.billing_date,    ");
		sql.append("a.patient_id,      ");
		sql.append("a.visit_id,    ");
		sql.append("a.amount AS drug_num,          ");
		sql.append("a.amount_units AS drug_unit,    ");
		sql.append("a.charges AS drug_money,         ");
		sql.append("dhd.druggg,        ");
		sql.append("dhd.Jixing,   ");
		sql.append("dhd.common_name   ");
		return sql.toString();
	}

	public String getFilter(Map<String, Object> params, boolean is_dept_permission, boolean is_doc_permission) {
		StringBuffer sql = new StringBuffer();
		sql.append(" a.billing_date between :start_time and :end_time ");
		if (!user().isSupperUser()) {
			// 增加表值函数, 查看权限信息
			if (is_dept_permission) {
				sql.append(" and exists (select 1 from user_dept_permission('" + user().getId() + "') bb where a.dept_code=bb.dept_code ) ");
			}
			if (is_doc_permission) {
				sql.append(" and exists (select 1 from user_doctor_permission('" + user().getId() + "') bb where a.ATTENDING_DOCTOR_code=bb.no ) ");
			}
		}
		if (!CommonFun.isNe(params.get("dept_code"))) {
			sql.append(" and exists (select 1 from split(:dept_code,',') ");
			sql.append(" sp where sp.col= a.dept_code) ");
		}

		if (!CommonFun.isNe(params.get("doctor_code"))) {
			sql.append(" and exists (select 1 from split(:doctor_code,',') ");
			sql.append(" sp where sp.col= a.attending_doctor_code) ");
		}

		if (!CommonFun.isNe(params.get("jixing")) || !CommonFun.isNe(params.get("yllb_code"))) {
			sql.append("and exists (select 1 from dict_his_drug(nolock) dhd ");
			sql.append("where dhd.drug_code=a.drug_code                     ");
			if (!CommonFun.isNe(params.get("jixing"))) {
				if (params.get("jixing") instanceof String) {
					if ("1".equals(params.get("jixing"))) {
						sql.append("and isnull(dhd.IS_KFJ,'0') = '1' ");
					} else if ("2".equals(params.get("jixing"))) {
						sql.append("and isnull(dhd.IS_ZSJ,'0') = '1' ");
					} else {
						sql.append("and isnull(dhd.IS_KFJ,'0') <> '1' ");
						sql.append("and isnull(dhd.IS_ZSJ,'0') <> '1' ");
					}
				} else {
					String[] jixing = (String[]) params.get("jixing");
					sql.append(" and ( ");
					for (int j = 0; j < jixing.length; j++) {
						if ("1".equals(jixing[j])) {
							if (j != 0) {
								sql.append(" or ");
							}
							sql.append(" isnull(dhd.IS_KFJ,'0') = '1' ");
						} else if ("2".equals(jixing[j])) {
							if (j != 0) {
								sql.append(" or ");
							}
							sql.append(" isnull(dhd.IS_ZSJ,'0') = '1' ");
						} else {
							if (j != 0) {
								sql.append(" or ");
							}
							sql.append(" (isnull(dhd.IS_KFJ,'0') <> '1' and isnull(dhd.IS_ZSJ,'0') <> '1') ");
						}
					}
					sql.append(" ) ");
				}
			}
			if (!CommonFun.isNe(params.get("yllb_code"))) {
				sql.append(" and exists (select 1 from split(:yllb_code,',') sp ");
				sql.append(" where sp.col=dhd.DRUG_YLFL) ");
			}
			sql.append(" ) ");
		}
		return sql.toString();
	}

}
