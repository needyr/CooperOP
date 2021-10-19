package cn.crtech.cooperop.hospital_common.dao.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class Drug_expend_monDao extends BaseDao {

	public Result query_dept(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #ttt from ( ");
		sql.append(getTypeStr(params, false, false));
		sql.append(") a ");

		sql.append("select  a.dept_code,a.dept_name,                      ");
		sql.append("stuff((                                               ");
		sql.append("select ',date_'+times+':'+cast(dept_money as varchar) ");
		sql.append("from #ttt                                             ");
		sql.append("where a.dept_code=dept_code                           ");
		sql.append("for xml path('')                                      ");
		sql.append("),1,1,'') dept_msg                                    ");
		sql.append("from #ttt a                                           ");
		sql.append("group by a.dept_code,a.dept_name                      ");
		sql.append("drop table #ttt ");
		return executeQuery(sql.toString(), params);
	}

	public String getTypeStr(Map<String, Object> params, boolean is_dept_permission, boolean is_doc_permission) {
		List<String> list = new ArrayList<String>();
		String re = "";
		Object d_type = params.get("d_type");
		String filter = getFilter(params, false, false);
		String fields = getFields();
		if (CommonFun.isNe(d_type)) {
			StringBuffer sql = new StringBuffer();
			sql.append("select " + fields);
			sql.append("from report_DDD (nolock) a                     ");
			sql.append("where                                              ");
			sql.append(filter);
			sql.append(" group by convert(varchar(7),a.billing_date,120),   ");
			sql.append("a.dept_code,a.dept_name                            ");
			list.add(sql.toString());
		} else {
			if (d_type instanceof String) {
				StringBuffer sql = new StringBuffer();
				sql.append("select " + fields);
				sql.append("from report_DDD (nolock) a                     ");
				sql.append("where                                              ");
				sql.append(filter);
				sql.append(" and a.d_type= '" + d_type + "'");
				sql.append(" group by convert(varchar(7),a.billing_date,120),   ");
				sql.append(" a.dept_code,a.dept_name                            ");
				list.add(sql.toString());
			} else {
				String[] d_type_li = (String[]) params.get("d_type");
				for (String string : d_type_li) {
					StringBuffer sql = new StringBuffer();
					sql.append("select " + fields);
					sql.append("from report_DDD (nolock) a                     ");
					sql.append("where                                              ");
					sql.append(filter);
					sql.append(" and a.d_type= '" + string + "'");
					sql.append(" group by convert(varchar(7),a.billing_date,120),   ");
					sql.append(" a.dept_code,a.dept_name                            ");
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
		sql.append("convert(varchar(7),a.billing_date,120) times, ");
		sql.append("a.dept_code,       ");
		sql.append("a.dept_name,       ");
		sql.append("sum(a.charges) dept_money                 ");
		return sql.toString();
	}

	public String getFilter(Map<String, Object> params, boolean is_dept_permission, boolean is_doc_permission) {
		StringBuffer sql = new StringBuffer();
		sql.append(" convert(varchar(7),a.billing_date,120) between :start_time and :end_time ");
		sql.append(" AND NOT EXISTS ( SELECT 1 FROM his_in_pats ( nolock ) WHERE a.patient_id= patient_id AND a.visit_id= visit_id ) 	 ");
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
		if (!CommonFun.isNe(params.get("drug_code"))) {
			sql.append(" and exists (select 1 from split(:drug_code,',') sp ");
			sql.append(" where sp.col=a.drug_code) ");
		}
		if (!CommonFun.isNe(params.get("drug_type")) || !CommonFun.isNe(params.get("jixing")) || !CommonFun.isNe(params.get("kjy_drug_type")) || !CommonFun.isNe(params.get("kjy_drug_level")) || !CommonFun.isNe(params.get("yllb_code"))) {
			sql.append("and exists (select 1 from dict_his_drug(nolock) dhd ");
			sql.append("where dhd.drug_code=a.drug_code                     ");
			if (!CommonFun.isNe(params.get("drug_type"))) {
				if (params.get("drug_type") instanceof String) {
					sql.append("and dhd.DRUG_YPFL = :drug_type   ");
				} else {
					sql.append("and dhd.DRUG_YPFL in (:drug_type)  ");
				}
			}
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
			if (!CommonFun.isNe(params.get("kjy_drug_type"))) {
				if (params.get("kjy_drug_type") instanceof String) {
					sql.append("and dhd.IS_ANTI_DRUG = :kjy_drug_type   ");
				} else {
					sql.append("and dhd.IS_ANTI_DRUG in (:kjy_drug_type)  ");
				}
			}
			if (!CommonFun.isNe(params.get("kjy_drug_level"))) {
				if (params.get("kjy_drug_level") instanceof String) {
					sql.append("and dhd.ANTI_LEVEL = :kjy_drug_level   ");
				} else {
					sql.append("and dhd.ANTI_LEVEL in (:kjy_drug_level)  ");
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
