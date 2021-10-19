package cn.crtech.cooperop.hospital_common.dao.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class Drug_amount_patientDao extends BaseDao {
	
	public Result query_stats(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #ttt from ( ");
		sql.append(getTypeStr(params));
		sql.append(") a ");

		sql.append("select "); 
		sql.append("a.drug_code,max(a.drug_name) drug_name,max(a.druggg) druggg,max(a.Jixing) Jixing,max(a.drug_unit) drug_unit,   "); 
		sql.append("(select count(1) from (                                  ");
		sql.append("select patient_id,visit_id from #ttt                     ");
		sql.append("where a.drug_code=drug_code                              ");
		//sql.append("and a.druggg=druggg                                      ");
		sql.append("group by patient_id,visit_id) d) patient_count           ");
		sql.append("into #aaa from #ttt a                                    "); 
		sql.append("group by a.drug_code                        "); 
		//sql.append("a.druggg,a.Jixing,a.drug_unit                            "); 
		sql.append("having sum(a.drug_money) > 0                             "); 
		
		sql.append("select top "+params.get("drug_pm"));
		sql.append(" ROW_NUMBER() OVER               ");
		sql.append("(ORDER BY a.patient_count        ");
		sql.append("desc) as rowid,a.*               ");
		sql.append("from #aaa a                      ");
		sql.append("order by a.patient_count desc    ");

		sql.append("drop table #ttt      ");
		sql.append("drop table #aaa      ");
		return executeQuery(sql.toString(),params);
	}
	
	public Result query_dept(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #ttt1 from ( ");
		sql.append(getTypeStr(params));
		sql.append(") a ");
		
		sql.append("select                       ");
		sql.append("a.drug_code,                 ");
		sql.append("max(a.drug_name) drug_name,  ");
		sql.append("max(a.druggg) druggg,        ");
		sql.append("max(a.Jixing) Jixing,        ");
		sql.append("max(a.drug_unit) drug_unit,  ");
		sql.append("a.dept_code,a.dept_name,     ");
		sql.append("(select count(1) from (                                  ");
		sql.append("select patient_id,visit_id from #ttt1                    ");
		sql.append("where a.drug_code=drug_code                              ");
		//sql.append("and a.druggg=druggg                                      ");
		sql.append("and	a.dept_code = dept_code                              ");
		sql.append("group by patient_id,visit_id) d) patient_count           ");
		sql.append("into #ttt2                   ");
		sql.append("from #ttt1(nolock) a         ");
		sql.append("group by a.drug_code,");
		sql.append("a.dept_code,a.dept_name,a.druggg ");
		sql.append("having sum(a.drug_money) > 0 ");
		//sql.append("order by patient_count desc  ");
		
		sql.append("select a.*,                  ");
		sql.append("b.dept_code,b.dept_name,     ");
		sql.append("b.patient_count as dept_patient_count  ");
		sql.append("into #ttt3                   ");
		sql.append("from                         ");
		sql.append("(select top "+params.get("drug_pm"));
		sql.append(" sum(a.patient_count) patient_count, ");
		sql.append(" a.drug_code,                 ");
		sql.append("max(a.drug_name) drug_name,  ");
		sql.append("max(a.druggg) druggg,        ");
		sql.append("max(a.Jixing) Jixing,        ");
		sql.append("max(a.drug_unit) drug_unit   ");
		sql.append("from #ttt2 a                 ");
		sql.append("group by a.drug_code ");
		sql.append("order by sum(a.patient_count) desc) a ");
		sql.append("inner join #ttt2 b           ");
		sql.append("on a.drug_code=b.drug_code   ");
		//sql.append("order by a.drug_code         ");
		
		sql.append("select a.rowmsg,                                             ");
		sql.append("a.drug_code,                                                 ");
		sql.append("a.dept_patient_count,                                        ");
		sql.append("a.patient_count,                                             ");
		sql.append("a.drug_name,                                                 ");
		sql.append("a.druggg,                                                    ");
		sql.append("a.drug_unit,                                                 ");
		sql.append("a.Jixing,                                                    ");
		sql.append("a.dept_code,                                                 ");
		sql.append("a.dept_name+'('+cast(dept_patient_count as varchar)+'例)' dept_name ");
		sql.append("into #ttt4 from (                                            ");
		sql.append("select                                                       ");
		sql.append("ROW_NUMBER() OVER                                            ");
		sql.append("(partition BY drug_code ORDER BY dept_patient_count desc) as rowid,  ");
		sql.append("'科室'+cast(ROW_NUMBER() OVER                                 ");
		sql.append("(partition BY drug_code                                      ");
		sql.append("ORDER BY dept_patient_count desc) as varchar) rowmsg,        ");
		sql.append("* from #ttt3 a) a                                            ");
		sql.append("where rowid<= "+params.get("dept_pm") + " ");
		
		sql.append("select  ");
		sql.append("ROW_NUMBER() OVER                                        "); 
		sql.append("(ORDER BY a.patient_count desc) as rowid, "); 
		sql.append("a.drug_code,a.drug_name,a.druggg,a.Jixing,a.patient_count,   ");
		sql.append("stuff(                                                       ");
		sql.append("(select ','+rowmsg+':'+dept_name from #ttt4                  ");
		sql.append("where a.drug_code=drug_code for xml path(''))                ");
		sql.append(",1,1,''                                                      ");
		sql.append(") dept_rank                                                  ");
		sql.append("from #ttt4 a                                                 ");
		sql.append("group by a.drug_code,a.drug_name,a.druggg,a.Jixing,a.patient_count  ");
		sql.append("order by a.patient_count desc ");
		
		sql.append("drop table #ttt1 ");
		sql.append("drop table #ttt2 ");
		sql.append("drop table #ttt3 ");
		sql.append("drop table #ttt4 ");
		
		return executeQuery(sql.toString(),params);
	}
	
	public Result query_doctor(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #ttt1 from ( ");
		sql.append(getTypeStr(params));
		sql.append(") a ");
		
		sql.append("select                       ");
		sql.append("sum(a.drug_money) money,     ");
		sql.append("sum(a.drug_num) drug_num,    ");
		sql.append("a.drug_code,                 ");
		sql.append("max(a.drug_name) drug_name,  ");
		sql.append("max(a.druggg) druggg,        ");
		sql.append("max(a.Jixing) Jixing,        ");
		sql.append("max(a.drug_unit) drug_unit,  ");
		sql.append("a.doctor_code,a.doctor_name, ");
		sql.append("(select count(1) from (                                  ");
		sql.append("select patient_id,visit_id from #ttt1                    ");
		sql.append("where a.drug_code=drug_code                              ");
		//sql.append("and a.druggg=druggg                                      ");
		sql.append("and	a.doctor_code = doctor_code                          ");
		sql.append("group by patient_id,visit_id) d) patient_count           ");
		sql.append("into #ttt2                   ");
		sql.append("from #ttt1(nolock) a         ");
		sql.append("group by a.drug_code,        ");
		sql.append("a.doctor_code,a.doctor_name  ");
		sql.append("having sum(a.drug_money) > 0 ");
		//sql.append("order by money desc          ");

		sql.append("select a.*,                  ");
		sql.append("b.doctor_code,b.doctor_name, ");
		sql.append("b.patient_count as doctor_patient_count  ");
		sql.append("into #ttt3                   ");
		sql.append("from                         ");
		sql.append("(select top "+params.get("drug_pm"));
		sql.append(" sum(a.patient_count) patient_count, ");
		sql.append("a.drug_code,                 ");
		sql.append("max(a.drug_name) drug_name,  ");
		sql.append("max(a.druggg) druggg,        ");
		sql.append("max(a.Jixing) Jixing,        ");
		sql.append("max(a.drug_unit) drug_unit   ");
		sql.append("from #ttt2 a                 ");
		sql.append("group by a.drug_code         ");
		sql.append("order by sum(a.patient_count) desc) a ");
		sql.append("inner join #ttt2 b           ");
		sql.append("on a.drug_code=b.drug_code   ");
		//sql.append("order by a.drug_code         ");

		sql.append("select a.rowmsg,                                             ");
		sql.append("a.drug_code,                                                 ");
		sql.append("a.patient_count,                                             ");
		sql.append("a.drug_name,                                                 ");
		sql.append("a.druggg,                                                    ");
		sql.append("a.drug_unit,                                                 ");
		sql.append("a.Jixing,                                                    ");
		sql.append("a.doctor_code,                                                 ");
		sql.append("a.doctor_name+'('+cast(doctor_patient_count as varchar)+'例)' doctor_name ");
		sql.append("into #ttt4 from (                                            ");
		sql.append("select                                                       ");
		sql.append("ROW_NUMBER() OVER                                            ");
		sql.append("(partition BY drug_code ORDER BY doctor_patient_count desc) as rowid,  ");
		sql.append("'科室'+cast(ROW_NUMBER() OVER                                 ");
		sql.append("(partition BY drug_code                                      ");
		sql.append("ORDER BY doctor_patient_count desc) as varchar) rowmsg,                ");
		sql.append("* from #ttt3 a) a                                            ");
		sql.append("where rowid<= "+params.get("dept_pm") + " ");

		sql.append("select  ");
		sql.append("ROW_NUMBER() OVER                                        "); 
		sql.append("(ORDER BY a.patient_count desc) as rowid, "); 
		sql.append("a.drug_code,a.drug_name,a.druggg,a.Jixing,a.patient_count,");
		sql.append("stuff(                                                       ");
		sql.append("(select ','+rowmsg+':'+doctor_name from #ttt4                  ");
		sql.append("where a.drug_code=drug_code for xml path(''))                ");
		sql.append(",1,1,''                                                      ");
		sql.append(") doctor_rank                                                  ");
		sql.append("from #ttt4 a                                                 ");
		sql.append("group by a.drug_code,a.drug_name,a.druggg,a.Jixing,a.patient_count  ");
		sql.append("order by a.patient_count desc ");

		sql.append("drop table #ttt1 ");
		sql.append("drop table #ttt2 ");
		sql.append("drop table #ttt3 ");
		sql.append("drop table #ttt4 ");

		return executeQuery(sql.toString(),params);
	}
	
	
	public String getTypeStr(Map<String, Object> params) {
		List<String> list = new ArrayList<String>();
		String re = "";
		Object d_type = params.get("d_type");
		String filter = getFilter(params);
		String fields = getFields();
		if(CommonFun.isNe(d_type)) {
			StringBuffer sql = new StringBuffer();
			sql.append("select "+fields);
			sql.append("from report_kjy_bill(nolock) a                     "); 
			sql.append("where                                              "); 
			sql.append(filter);
			sql.append(" and not exists (select 1 from his_in_pats (nolock) where ");
			sql.append(" a.patient_id=patient_id and a.visit_id=visit_id) ");
			list.add(sql.toString());
			sql = new StringBuffer();
			sql.append("select "+fields); 
			sql.append("from report_kjy_outp_bill(nolock) a                "); 
			sql.append("where                                              "); 
			sql.append(filter);
			list.add(sql.toString());
		}else {
			if(d_type instanceof String) {
				if("1".equals(d_type)) {
					StringBuffer sql = new StringBuffer();
					sql.append("select "+fields); 
					sql.append("from report_kjy_bill(nolock) a                     "); 
					sql.append("where                                              "); 
					sql.append(filter);
					sql.append(" and not exists (select 1 from his_in_pats (nolock) where ");
					sql.append(" a.patient_id=patient_id and a.visit_id=visit_id) ");
					list.add(sql.toString());
				}else if("2".equals(d_type)) {
					StringBuffer sql = new StringBuffer();
					sql.append("select "+fields); 
					sql.append("from report_kjy_outp_bill(nolock) a                "); 
					sql.append("where                                              "); 
					sql.append(filter);
					sql.append("and a.d_type='2' ");
					list.add(sql.toString());
				}else if("3".equals(d_type)) {
					StringBuffer sql = new StringBuffer();
					sql.append("select "+fields); 
					sql.append("from report_kjy_outp_bill(nolock) a                "); 
					sql.append("where                                              "); 
					sql.append(filter);
					sql.append("and a.d_type='3' ");
					list.add(sql.toString());
				}
			}else {
				String[] d_type_li = (String[])d_type;
				for (String string : d_type_li) {
					if("1".equals(string)) {
						StringBuffer sql = new StringBuffer();
						sql.append("select "+fields); 
						sql.append("from report_kjy_bill(nolock) a                     "); 
						sql.append("where                                              "); 
						sql.append(filter);
						sql.append(" and not exists (select 1 from his_in_pats (nolock) where ");
						sql.append(" a.patient_id=patient_id and a.visit_id=visit_id) ");
						list.add(sql.toString());
					}else if("2".equals(string)) {
						StringBuffer sql = new StringBuffer();
						sql.append("select "+fields); 
						sql.append("from report_kjy_outp_bill(nolock) a                "); 
						sql.append("where                                              "); 
						sql.append(filter);
						sql.append("and a.d_type='2' ");
						list.add(sql.toString());
					}else if("3".equals(string)) {
						StringBuffer sql = new StringBuffer();
						sql.append("select "+fields); 
						sql.append("from report_kjy_outp_bill(nolock) a                "); 
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
	}
	
	public String getFields() {
		StringBuffer sql = new StringBuffer();
		sql.append("a.create_time,      ");
		sql.append("a.dept_code,        ");
		sql.append("a.dept_name,        ");
		sql.append("a.drug_code,        ");
		sql.append("a.drug_name,        ");
		sql.append("a.druggg,           ");
		sql.append("a.sys_drug_name,    ");
		sql.append("a.drug_money,       ");
		sql.append("a.drug_num,         ");
		sql.append("a.ddd_value,        ");
		sql.append("a.kjy_level,        ");
		sql.append("a.drug_unit,        ");
		sql.append("a.use_purp,         ");
		sql.append("a.id,               ");
		sql.append("a.doctor_code,      ");
		sql.append("a.doctor_name,      ");
		sql.append("a.patient_id,       ");
		sql.append("a.visit_id,         ");
		sql.append("a.group_id,         ");
		sql.append("a.order_no,         ");
		sql.append("a.order_sub_no,     ");
		sql.append("a.doctor_dept_code, ");
		sql.append("a.doctor_dept_name, ");
		sql.append("a.Jixing,           ");
		sql.append("a.DDD_UNIT,         ");
		sql.append("a.DDD_Conversion,   ");
		sql.append("a.PROPERTY_TOXI,    ");
		sql.append("a.parent_id,        ");
		sql.append("a.d_type            ");
		return sql.toString();
	}
	
	public String getFilter(Map<String, Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append(" a.create_time between :start_time and :end_time ");
		if(!CommonFun.isNe(params.get("drug_type")) ||
				!CommonFun.isNe(params.get("jixing")) ||
				!CommonFun.isNe(params.get("kjy_drug_type")) ||
				!CommonFun.isNe(params.get("kjy_drug_level")) ||
				!CommonFun.isNe(params.get("is_jbyw")) ||
				!CommonFun.isNe(params.get("yllb_code"))) {
			sql.append("and exists (select 1 from dict_his_drug(nolock) dhd ");
			sql.append("where dhd.drug_code=a.drug_code                     ");
			if(!CommonFun.isNe(params.get("drug_type"))) {
				if(params.get("drug_type") instanceof String) {
					sql.append("and dhd.DRUG_YPFL = :drug_type   ");
				}else {
					sql.append("and dhd.DRUG_YPFL in (:drug_type)  ");
				}
			}
			if(!CommonFun.isNe(params.get("jixing"))) {
				if(params.get("jixing") instanceof String) {
					if("1".equals(params.get("jixing"))) {
						sql.append("and isnull(dhd.IS_KFJ,'0') = '1' ");
					}else if("2".equals(params.get("jixing"))) {
						sql.append("and isnull(dhd.IS_ZSJ,'0') = '1' ");
					}else {
						sql.append("and isnull(dhd.IS_KFJ,'0') <> '1' ");
						sql.append("and isnull(dhd.IS_ZSJ,'0') <> '1' ");
					}
				}else {
					String[] jixing = (String[]) params.get("jixing");
					sql.append(" and ( ");
					for (int j = 0;j < jixing.length;j++) {
						if("1".equals(jixing[j])) {
							if(j != 0) {
								sql.append(" or ");
							}
							sql.append(" isnull(dhd.IS_KFJ,'0') = '1' ");
						}else if("2".equals(jixing[j])) {
							if(j != 0) {
								sql.append(" or ");
							}
							sql.append(" isnull(dhd.IS_ZSJ,'0') = '1' ");
						}else {
							if(j != 0) {
								sql.append(" or ");
							}
							sql.append(" (isnull(dhd.IS_KFJ,'0') <> '1' and isnull(dhd.IS_ZSJ,'0') <> '1') ");
						}
					}
					sql.append(" ) ");
				}
			}
			if(!CommonFun.isNe(params.get("kjy_drug_type"))) {
				if(params.get("kjy_drug_type") instanceof String) {
					sql.append("and dhd.IS_ANTI_DRUG = :kjy_drug_type   ");
				}else {
					sql.append("and dhd.IS_ANTI_DRUG in (:kjy_drug_type)  ");
				}
			}
			if(!CommonFun.isNe(params.get("kjy_drug_level"))) {
				if(params.get("kjy_drug_level") instanceof String) {
					sql.append("and dhd.ANTI_LEVEL = :kjy_drug_level   ");
				}else {
					sql.append("and dhd.ANTI_LEVEL in (:kjy_drug_level)  ");
				}
			}
			if(!CommonFun.isNe(params.get("is_jbyw"))) {
				if(params.get("is_jbyw") instanceof String) {
					if("1".equals(params.get("is_jbyw"))) {
						sql.append("and isnull(dhd.IS_JBYW,'0') = '1' ");
					}else {
						sql.append("and isnull(dhd.IS_JBYW,'0') = '0' ");
					}
				}
			}
			if(!CommonFun.isNe(params.get("yllb_code"))) {
				sql.append(" and exists (select 1 from split(:yllb_code,',') sp ");
				sql.append(" where sp.col=dhd.DRUG_YLFL) ");
			}
			sql.append(" ) ");
		}
		return sql.toString();
	}
}
