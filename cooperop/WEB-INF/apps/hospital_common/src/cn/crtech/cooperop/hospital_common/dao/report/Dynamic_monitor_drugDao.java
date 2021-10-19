package cn.crtech.cooperop.hospital_common.dao.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class Dynamic_monitor_drugDao extends BaseDao {
	
	public Result query_stats(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #list from ( ");
		sql.append(getTypeStr(params,false,false));
		sql.append(") a ");
		
		sql.append("select                                    ");
		if("0".equals(params.get("time_unit"))) {
			sql.append("a.create_time,                        ");
		}else {
			sql.append("convert(varchar(4),a.create_time,120) create_time,");
		}
		sql.append("a.drug_code,                              ");
		sql.append("max(a.drug_name) drug_name,               ");
		sql.append("dhd.Jixing,                               ");
		sql.append("dhd.druggg,                               ");
		sql.append("sum("+
		("0".equals(params.get("bhqk")) ? "a.drug_amount" : 
		"1".equals(params.get("bhqk"))?"a.drug_number":"a.DDDS")
		+") as money               ");
		sql.append("into #ttt                                 ");
		sql.append("from #list (nolock) a                     ");
		sql.append("left join dict_his_drug(nolock) dhd       ");
		sql.append("on a.drug_code=dhd.drug_code              ");
		sql.append("group by                                  ");
		if("0".equals(params.get("time_unit"))) {
			sql.append("a.create_time,                        ");
		}else {
			sql.append("convert(varchar(4),a.create_time,120),");
		}
		sql.append("a.drug_code,dhd.Jixing,dhd.druggg         ");
		
		sql.append("select a.*,                               "); 
		sql.append("(select b.val from (                      "); 
		sql.append("select                                    "); 
		sql.append("case when isnull(money,0)<>0 then         "); 
		sql.append("(a.money-money)/money                     "); 
		sql.append("else 0 end as val                         "); 
		sql.append("from #ttt                                 "); 
		sql.append("where a.drug_code=drug_code               "); 
		if("0".equals(params.get("time_unit"))) {
			sql.append("and DATEDIFF(mm,                          "); 
			sql.append("convert(varchar(7),create_time,120) + '-01 00:00:00',        "); 
			sql.append("convert(varchar(7),a.create_time,120) + '-01 00:00:00') = 1  "); 
		}else {
			sql.append("and DATEDIFF(yy,                          "); 
			sql.append("convert(varchar(4),create_time,120) + '-01-01 00:00:00',        "); 
			sql.append("convert(varchar(4),a.create_time,120) + '-01-01 00:00:00') = 1  ");
		}
		sql.append(") b) as fdl                               "); 
		sql.append("into #ttt2                                "); 
		sql.append("from #ttt a                               "); 
		
		sql.append("select                                                             ");
		sql.append("IDENTITY(INT, 1, 1) rowid,                                         ");
		sql.append("ROW_NUMBER() OVER                                                  ");
		sql.append("(partition BY a.create_time ORDER BY a.money desc) as rowid_money, ");
		sql.append("ROW_NUMBER() OVER                                                  ");
		sql.append("(partition BY a.create_time ORDER BY a.fdl desc) as rowid_fdl,     ");
		sql.append("a.*                                                                ");
		if(!CommonFun.isNe(params.get("sj"))) {
			sql.append(",(case when a.fdl >= "+params.get("sj")+" then 1 else 0 end) as is_warn ");
		}
		sql.append("into #ttt3                                                         ");
		sql.append("from #ttt2 a                                                       ");
		sql.append("order by a.create_time,a.money desc                                ");
		
		sql.append("select                                                      "); 
		sql.append("a.drug_code,                                                "); 
		sql.append("max(a.drug_name) drug_name,                                 "); 
		sql.append("max(a.Jixing) jixing,                                       "); 
		sql.append("max(a.druggg) druggg,                                       "); 
		sql.append("max(dhd.shengccj) shengccj,                                 "); 
		if(!CommonFun.isNe(params.get("sj"))) {
			sql.append("max(a.is_warn) is_warn, "); 
		}
		sql.append("stuff(                                                      "); 
		sql.append("(select '^{\"date\":\"'+create_time+                            "); 
		sql.append("'\",\"rowid_money\":\"'+cast(isnull(rowid_money,0) as varchar)+ "); 
		sql.append("'\",\"rowid_fdl\":\"'+cast(isnull(rowid_fdl,0) as varchar)+     "); 
		sql.append("'\",\"money\":\"'+cast(isnull(money,0) as varchar)+             "); 
		sql.append("'\",\"fdl\":\"'+cast(isnull(fdl,0) as varchar)+'\"}'            "); 
		sql.append("from #ttt3                                                  "); 
		sql.append("where a.drug_code=drug_code                                 "); 
		sql.append("order by create_time for xml path(''))                      "); 
		sql.append(",1,1,'') msg                                                "); 
		sql.append("from #ttt3 a                                                "); 
		sql.append("left join dict_his_drug(nolock) dhd on                      "); 
		sql.append("dhd.drug_code=a.drug_code                                   "); 
		sql.append("group by a.drug_code                                        "); 
		sql.append("order by min(rowid)                                         "); 
		
		sql.append("drop table #list ");
		sql.append("drop table #ttt  ");
		sql.append("drop table #ttt2 ");
		sql.append("drop table #ttt3 ");
		
		return executeQuery(sql.toString(),params);
	}
	
	public Result query_dept(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #list from ( ");
		sql.append(getTypeStr(params,false,false));
		sql.append(") a ");
		
		sql.append("select                                    ");
		if("0".equals(params.get("time_unit"))) {
			sql.append("a.create_time,                        ");
		}else {
			sql.append("convert(varchar(4),a.create_time,120) create_time,");
		}
		sql.append("a.dept_code,                              ");
		sql.append("max(a.dept_name) dept_name,               ");
		sql.append("sum("+
				("0".equals(params.get("bhqk")) ? "a.drug_amount" : 
					"1".equals(params.get("bhqk"))?"a.drug_number":"a.DDDS")
				+") as money               ");
		sql.append("into #ttt                                 ");
		sql.append("from #list (nolock) a                     ");
		sql.append("group by                                  ");
		if("0".equals(params.get("time_unit"))) {
			sql.append("a.create_time,                        ");
		}else {
			sql.append("convert(varchar(4),a.create_time,120),");
		}
		sql.append("a.dept_code        ");
		
		sql.append("select a.*,                               "); 
		sql.append("(select b.val from (                      "); 
		sql.append("select                                    "); 
		sql.append("case when isnull(money,0)<>0 then         "); 
		sql.append("(a.money-money)/money                     "); 
		sql.append("else 0 end as val                         "); 
		sql.append("from #ttt                                 "); 
		sql.append("where a.dept_code=dept_code               "); 
		if("0".equals(params.get("time_unit"))) {
			sql.append("and DATEDIFF(mm,                          "); 
			sql.append("convert(varchar(7),create_time,120) + '-01 00:00:00',        "); 
			sql.append("convert(varchar(7),a.create_time,120) + '-01 00:00:00') = 1  "); 
		}else {
			sql.append("and DATEDIFF(yy,                          "); 
			sql.append("convert(varchar(4),create_time,120) + '-01-01 00:00:00',        "); 
			sql.append("convert(varchar(4),a.create_time,120) + '-01-01 00:00:00') = 1  ");
		}
		sql.append(") b) as fdl                               "); 
		sql.append("into #ttt2                                "); 
		sql.append("from #ttt a                               "); 
		
		sql.append("select                                                             ");
		sql.append("IDENTITY(INT, 1, 1) rowid,                                         ");
		sql.append("ROW_NUMBER() OVER                                                  ");
		sql.append("(partition BY a.create_time ORDER BY a.money desc) as rowid_money, ");
		sql.append("ROW_NUMBER() OVER                                                  ");
		sql.append("(partition BY a.create_time ORDER BY a.fdl desc) as rowid_fdl,     ");
		sql.append("a.*                                                                ");
		if(!CommonFun.isNe(params.get("sj"))) {
			sql.append(",(case when a.fdl >= "+params.get("sj")+" then 1 else 0 end) as is_warn ");
		}
		sql.append("into #ttt3                                                         ");
		sql.append("from #ttt2 a                                                       ");
		sql.append("order by a.create_time,a.money desc                                ");
		
		sql.append("select                                                      "); 
		sql.append("a.dept_code,                                                "); 
		sql.append("max(a.dept_name) dept_name,                                 "); 
		if(!CommonFun.isNe(params.get("sj"))) {
			sql.append("max(a.is_warn) is_warn, "); 
		}
		sql.append("stuff(                                                      "); 
		sql.append("(select '^{\"date\":\"'+create_time+                            "); 
		sql.append("'\",\"rowid_money\":\"'+cast(isnull(rowid_money,0) as varchar)+ "); 
		sql.append("'\",\"rowid_fdl\":\"'+cast(isnull(rowid_fdl,0) as varchar)+     "); 
		sql.append("'\",\"money\":\"'+cast(isnull(money,0) as varchar)+             "); 
		sql.append("'\",\"fdl\":\"'+cast(isnull(fdl,0) as varchar)+'\"}'            "); 
		sql.append("from #ttt3                                                  "); 
		sql.append("where a.dept_code=dept_code                                 "); 
		sql.append("order by create_time for xml path(''))                      "); 
		sql.append(",1,1,'') msg                                                "); 
		sql.append("from #ttt3 a                                                "); 
		sql.append("group by a.dept_code                                        "); 
		sql.append("order by min(rowid)                                         "); 
		
		sql.append("drop table #list ");
		sql.append("drop table #ttt  ");
		sql.append("drop table #ttt2 ");
		sql.append("drop table #ttt3 ");
		
		return executeQuery(sql.toString(),params);
	}
	
	public Result query_doctor(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * into #list from ( ");
		sql.append(getTypeStr(params,false,false));
		sql.append(") a ");
		
		sql.append("select                                    ");
		if("0".equals(params.get("time_unit"))) {
			sql.append("a.create_time,                        ");
		}else {
			sql.append("convert(varchar(4),a.create_time,120) create_time,");
		}
		sql.append("a.doctor_code,                            ");
		sql.append("max(a.doctor_name) doctor_name,           ");
		sql.append("max(a.doctor_dept_name) doctor_dept_name, ");
		sql.append("sum("+
				("0".equals(params.get("bhqk")) ? "a.drug_amount" : 
					"1".equals(params.get("bhqk"))?"a.drug_number":"a.DDDS")
				+") as money               ");
		sql.append("into #ttt                                 ");
		sql.append("from #list (nolock) a                     ");
		sql.append("group by                                  ");
		if("0".equals(params.get("time_unit"))) {
			sql.append("a.create_time,                        ");
		}else {
			sql.append("convert(varchar(4),a.create_time,120),");
		}
		sql.append("a.doctor_code        ");
		
		sql.append("select a.*,                               "); 
		sql.append("(select b.val from (                      "); 
		sql.append("select                                    "); 
		sql.append("case when isnull(money,0)<>0 then         "); 
		sql.append("(a.money-money)/money                     "); 
		sql.append("else 0 end as val                         "); 
		sql.append("from #ttt                                 "); 
		sql.append("where a.doctor_code=doctor_code           "); 
		if("0".equals(params.get("time_unit"))) {
			sql.append("and DATEDIFF(mm,                          "); 
			sql.append("convert(varchar(7),create_time,120) + '-01 00:00:00',        "); 
			sql.append("convert(varchar(7),a.create_time,120) + '-01 00:00:00') = 1  "); 
		}else {
			sql.append("and DATEDIFF(yy,                          "); 
			sql.append("convert(varchar(4),create_time,120) + '-01-01 00:00:00',        "); 
			sql.append("convert(varchar(4),a.create_time,120) + '-01-01 00:00:00') = 1  ");
		}
		sql.append(") b) as fdl                               "); 
		sql.append("into #ttt2                                "); 
		sql.append("from #ttt a                               "); 
		
		sql.append("select                                                             ");
		sql.append("IDENTITY(INT, 1, 1) rowid,                                         ");
		sql.append("ROW_NUMBER() OVER                                                  ");
		sql.append("(partition BY a.create_time ORDER BY a.money desc) as rowid_money, ");
		sql.append("ROW_NUMBER() OVER                                                  ");
		sql.append("(partition BY a.create_time ORDER BY a.fdl desc) as rowid_fdl,     ");
		sql.append("a.*                                                                ");
		if(!CommonFun.isNe(params.get("sj"))) {
			sql.append(",(case when a.fdl >= "+params.get("sj")+" then 1 else 0 end) as is_warn ");
		}
		sql.append("into #ttt3                                                         ");
		sql.append("from #ttt2 a                                                       ");
		sql.append("order by a.create_time,a.money desc                                ");
		
		sql.append("select                                                      "); 
		sql.append("a.doctor_code,                                              "); 
		sql.append("max(a.doctor_name) doctor_name,                             "); 
		sql.append("max(a.doctor_dept_name) doctor_dept_name,                   "); 
		if(!CommonFun.isNe(params.get("sj"))) {
			sql.append("max(a.is_warn) is_warn, "); 
		}
		sql.append("stuff(                                                      "); 
		sql.append("(select '^{\"date\":\"'+create_time+                            "); 
		sql.append("'\",\"rowid_money\":\"'+cast(isnull(rowid_money,0) as varchar)+ "); 
		sql.append("'\",\"rowid_fdl\":\"'+cast(isnull(rowid_fdl,0) as varchar)+     "); 
		sql.append("'\",\"money\":\"'+cast(isnull(money,0) as varchar)+             "); 
		sql.append("'\",\"fdl\":\"'+cast(isnull(fdl,0) as varchar)+'\"}'            "); 
		sql.append("from #ttt3                                                  "); 
		sql.append("where a.doctor_code=doctor_code                             "); 
		sql.append("order by create_time for xml path(''))                      "); 
		sql.append(",1,1,'') msg                                                "); 
		sql.append("from #ttt3 a                                                "); 
		sql.append("group by a.doctor_code                                      "); 
		sql.append("order by min(rowid)                                         "); 
		
		sql.append("drop table #list ");
		sql.append("drop table #ttt  ");
		sql.append("drop table #ttt2 ");
		sql.append("drop table #ttt3 ");
		
		return executeQuery(sql.toString(),params);
	}
	
	public String getTypeStr(Map<String, Object> params,boolean is_dept_permission, boolean is_doc_permission) {
		List<String> list = new ArrayList<String>();
		String re = "";
		Object d_type = params.get("d_type");
		String filter = getFilter(params,is_dept_permission,is_doc_permission);
		String fields = getFields();
		if(CommonFun.isNe(d_type)) {
			StringBuffer sql = new StringBuffer();
			sql.append("select "+fields);
			sql.append("from report_month_drug_use_in(nolock) a            "); 
			sql.append("where                                              "); 
			sql.append(filter);
			list.add(sql.toString());
			sql = new StringBuffer();
			sql.append("select "+fields); 
			sql.append("from report_month_drug_use_outp(nolock) a          "); 
			sql.append("where                                              "); 
			sql.append(filter);
			list.add(sql.toString());
			sql = new StringBuffer();
			sql.append("select "+fields); 
			sql.append("from report_month_drug_use_emer(nolock) a          "); 
			sql.append("where                                              "); 
			sql.append(filter);
			list.add(sql.toString());
		}else {
			if(d_type instanceof String) {
				if("1".equals(d_type)) {
					StringBuffer sql = new StringBuffer();
					sql.append("select "+fields); 
					sql.append("from report_month_drug_use_in(nolock) a            "); 
					sql.append("where                                              "); 
					sql.append(filter);
					list.add(sql.toString());
				}else if("2".equals(d_type)) {
					StringBuffer sql = new StringBuffer();
					sql.append("select "+fields); 
					sql.append("from report_month_drug_use_outp(nolock) a          "); 
					sql.append("where                                              "); 
					sql.append(filter);
					list.add(sql.toString());
				}else if("3".equals(d_type)) {
					StringBuffer sql = new StringBuffer();
					sql.append("select "+fields); 
					sql.append("from report_month_drug_use_emer(nolock) a          "); 
					sql.append("where                                              "); 
					sql.append(filter);
					list.add(sql.toString());
				}
			}else {
				String[] d_type_li = (String[])d_type;
				for (String string : d_type_li) {
					if("1".equals(string)) {
						StringBuffer sql = new StringBuffer();
						sql.append("select "+fields); 
						sql.append("from report_month_drug_use_in(nolock) a            "); 
						sql.append("where                                              "); 
						sql.append(filter);
						list.add(sql.toString());
					}else if("2".equals(string)) {
						StringBuffer sql = new StringBuffer();
						sql.append("select "+fields); 
						sql.append("from report_month_drug_use_outp(nolock) a          "); 
						sql.append("where                                              "); 
						sql.append(filter);
						list.add(sql.toString());
					}else if("3".equals(string)) {
						StringBuffer sql = new StringBuffer();
						sql.append("select "+fields); 
						sql.append("from report_month_drug_use_emer(nolock) a          "); 
						sql.append("where                                              "); 
						sql.append(filter);
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
		sql.append("a.drug_code,       ");
		sql.append("a.drug_name,       ");
		sql.append("a.drug_amount,     ");
		sql.append("a.drug_number,     ");
		sql.append("a.number_unit,     ");
		sql.append("a.DDDS,            ");
		sql.append("a.dept_code,       ");
		sql.append("a.dept_name,       ");
		sql.append("a.doctor_code,     ");
		sql.append("a.doctor_name,     ");
		sql.append("a.doctor_dept_name,");
		sql.append("a.create_time      ");
		return sql.toString();
	}
	
	public String getFilter(Map<String, Object> params,boolean is_dept_permission, boolean is_doc_permission) {
		StringBuffer sql = new StringBuffer();
		if("0".equals(params.get("time_unit"))) {
			sql.append(" a.create_time between :start_time and :end_time ");
		}else {
			sql.append(" convert(varchar(4),a.create_time,120) between :start_time and :end_time ");
		}
		if(!user().isSupperUser()) {
			//增加表值函数, 查看权限信息
			if(is_dept_permission) {
				sql.append(" and exists (select 1 from user_dept_permission('"+user().getId()+"') bb where a.dept_code=bb.dept_code ) ");
			}
			if(is_doc_permission) {
				sql.append(" and exists (select 1 from user_doctor_permission('"+user().getId()+"') bb where a.doctor_code=bb.no ) ");
			}
		}
		if(!CommonFun.isNe(params.get("dept_code"))) {
			sql.append(" and exists (select 1 from split(:dept_code,',') ");
			sql.append(" sp where sp.col= a.dept_code) ");
		}
		if(!CommonFun.isNe(params.get("drug_code"))) {
			sql.append(" and exists (select 1 from split(:drug_code,',') sp ");
			sql.append(" where sp.col=a.drug_code) ");
		}
		if(!CommonFun.isNe(params.get("doctor_code"))) {
			sql.append(" and exists (select 1 from split(:doctor_code,',') sp ");
			sql.append(" where sp.col=a.doctor_code) ");
		}
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
