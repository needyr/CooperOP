package cn.crtech.cooperop.hospital_common.dao.report;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class Drug_use_intensionDao extends BaseDao {
	public Result query_dui(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer pa = new StringBuffer();
		pa.append("select sum(dd.ts) all_patient_ts from (                        ");
		pa.append("select vhia.patient_id,                                        ");
		pa.append("vhia.visit_id,                                                 ");
		pa.append("vhia.ADMISSION_DATETIME,                                       ");
		pa.append("vhia.DISCHARGE_DATETIME,                                       ");
		pa.append("case when DATEDIFF(DAY,vhia.ADMISSION_DATETIME,vhia.DISCHARGE_DATETIME) = 1 then 1 ");
		pa.append("else DATEDIFF(DAY,vhia.ADMISSION_DATETIME,vhia.DISCHARGE_DATETIME) end ts ");
		pa.append("from V_his_in_patientvisit_all(nolock) vhia                    ");
		pa.append("where vhia.DISCHARGE_DATETIME between :start_time +' 00:00:00' and :end_time +' 23:59:59' ");
		pa.append("and vhia.DISCHARGE_DATETIME is not null ");
		pa.append("and vhia.d_type = '1' ");
		pa.append("group by vhia.patient_id,                                      ");
		pa.append("vhia.visit_id,vhia.ADMISSION_DATETIME,                         ");
		pa.append("vhia.DISCHARGE_DATETIME) dd                                    ");
		Record re = executeQuerySingleRecord(pa.toString(),params);

		sql.append("select a.*, ");
		sql.append("b.Usejlgg,                                                     ");
		sql.append("b.Use_dw,                                                      ");
		sql.append("isnull(c.DRUG_YLFL_NAME,'') as property_toxi,                  ");
		sql.append("b.jixing,                                                      ");
		sql.append("b.druggg,                                                      ");
		sql.append("case when b.ANTI_LEVEL='1' then '非限制级' when b.ANTI_LEVEL='2' then '限制级' when b.ANTI_LEVEL='3' then '特殊级' else '' end as ANTI_LEVEL,");
		sql.append("b.ddd_value,                                                   ");
		sql.append("Round((case  ");
		/*sql.append("when a.AMOUNT_UNITS = b.DRUG_unit and isnull(b.DDD_VALUE,0)<>0 then ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");*/
		sql.append("when a.AMOUNT_UNITS = b.PACK_UNIT and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)*b.PACK_ConvertNum/b.DDD_VALUE end ");
		sql.append("when a.AMOUNT_UNITS = b.Use_dw and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE*b.Usejlgg end ");
		sql.append("when isnull(a.AMOUNT_UNITS,'')<>'' and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");
		sql.append("else 0 end),2) as DDDS                                                ");
		sql.append(" into #ttt from report_DDD (nolock) a");
		sql.append(" left join dict_his_drug (nolock) b on a.drug_code=b.drug_code");
		sql.append(" left join DICT_SYS_YLFL(nolock) c on c.DRUG_YLFL_CODE=b.DRUG_YLFL ");
		sql.append(" inner join v_his_in_patientvisit_all (nolock) vhipas ");
		sql.append(" on vhipas.patient_id=a.patient_id ");
		sql.append(" and vhipas.visit_id=a.visit_id and vhipas.discharge_datetime is not null and vhipas.d_type = '1' ");
		sql.append(" where 1=1 ");
		sql.append(getFilter(params, false, false));

		sql.append(" select property_toxi,count(1) num,sum(CHARGES) CHARGES into #ttt2 from #ttt a where exists (select 1 from dict_his_drug (nolock) where a.drug_code=drug_code ) group by property_toxi ");

		sql.append(" select sum(a.CHARGES) CHARGES into #ttt3 from #ttt a where exists (select 1 from dict_his_drug (nolock) where a.drug_code=drug_code ) ");

		sql.append(" select sum(a.CHARGES) CHARGES into #ttt4 from #ttt a where exists (select 1 from dict_his_drug (nolock) where a.drug_code=drug_code ) ");

		sql.append("select                                                         ");
		sql.append("a.property_toxi,a.drug_name,a.jixing,                          ");
		sql.append("a.druggg,a.ANTI_LEVEL,                                    ");
		sql.append("sum(a.AMOUNT) num,                                             ");
		sql.append("sum(a.CHARGES) all_money,                                      ");
		sql.append("b.num kind_drug_num,           ");
		sql.append("b.CHARGES kind_drug_money, ");
		sql.append("sum(a.sylc) sylc, ");
		sql.append("sum(a.DDDS) ddds,                                              ");
		sql.append("(select top 1 CHARGES from #ttt4) all_drug_money,                ");
		sql.append((CommonFun.isNe(re.get("all_patient_ts"))?0:re.get("all_patient_ts"))+" as  all_patient_ts ");
		sql.append("from #ttt a                                                    ");
		sql.append("inner join dict_his_drug (nolock) dhd on a.drug_code=dhd.drug_code ");
		sql.append("left join #ttt2 b on a.property_toxi=b.property_toxi           ");
		sql.append("group by a.property_toxi,a.drug_name,a.jixing,                 ");
		sql.append("a.druggg,a.ANTI_LEVEL,a.Usejlgg,b.num,b.CHARGES         ");
		if(!CommonFun.isNe(params.get("pm"))) {
			sql.append("order by "+("1".equals(params.get("pm"))?"a.property_toxi":"sum(a.DDDS)")+" desc ");
		}

		sql.append(" drop table #ttt  ");
		sql.append(" drop table #ttt2  ");
		sql.append(" drop table #ttt3  ");
		sql.append(" drop table #ttt4  ");
		return executeQuery(sql.toString(), params);
	}

	public Result query_expend_dept_zj(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*, ");
		sql.append("b.Usejlgg,                                                     ");
		sql.append("b.Use_dw,                                                      ");
		sql.append("isnull(c.DRUG_YLFL_NAME,'') as property_toxi,                  ");
		sql.append("b.jixing,                                                      ");
		sql.append("b.druggg,                                                      ");
		sql.append("b.IS_ZSJ,                                                      ");
		sql.append("b.IS_KFJ,                                                      ");
		sql.append("case when b.ANTI_LEVEL='1' then '非限制级' when b.ANTI_LEVEL='2' then '限制级' when b.ANTI_LEVEL='3' then '特殊级' else '' end as ANTI_LEVEL,");
		sql.append("b.ddd_value,                                                   ");
		sql.append("Round((case  ");
		/*sql.append("when a.AMOUNT_UNITS = b.DRUG_unit and isnull(b.DDD_VALUE,0)<>0 then ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");*/
		sql.append("when a.AMOUNT_UNITS = b.PACK_UNIT and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)*b.PACK_ConvertNum/b.DDD_VALUE end ");
		sql.append("when a.AMOUNT_UNITS = b.Use_dw and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE*b.Usejlgg end ");
		sql.append("when isnull(a.AMOUNT_UNITS,'')<>'' and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");
		sql.append("else 0 end),2) as DDDS                                                ");
		sql.append(" into #ttt from report_DDD (nolock) a");
		sql.append(" left join dict_his_drug (nolock) b on a.drug_code=b.drug_code");
		sql.append(" left join DICT_SYS_YLFL(nolock) c on c.DRUG_YLFL_CODE=b.DRUG_YLFL ");
		sql.append(" inner join v_his_in_patientvisit_all (nolock) vhipas ");
		sql.append(" on vhipas.patient_id=a.patient_id ");
		sql.append(" and vhipas.visit_id=a.visit_id and vhipas.discharge_datetime is not null ");
		sql.append(" where 1=1 ");
		sql.append(getFilter(params, false, false));

		sql.append(" select dept_CODE,property_toxi,count(1) num,sum(CHARGES) CHARGES into #property_toxi from #ttt a where exists (select 1 from dict_his_drug (nolock) where a.drug_code=drug_code )  group by property_toxi,dept_CODE ");

		sql.append("select                                                         ");
		sql.append("a.dept_CODE,a.dept_name,                 ");
		sql.append("(select sum(num) from #property_toxi pt where pt.dept_code=a.dept_code ) as kind_drug_num ");
		sql.append("into #ttt2                                                     ");
		sql.append("from #ttt a                                                    ");
		sql.append("group by a.dept_CODE,a.dept_name                    ");

		sql.append("select                                                         ");
		sql.append("a.dept_CODE,a.dept_name,a.kind_drug_num,                       ");
		sql.append("(select sum(ddds) from #ttt where a.dept_CODE=dept_CODE) as ddds, ");
		sql.append("(select sum(ddds) from #ttt where a.dept_CODE=dept_CODE and isnull(IS_ZSJ,'0')='1') as ZSJ_ddds, ");
		sql.append("(select sum(ddds) from #ttt where a.dept_CODE=dept_CODE and isnull(IS_KFJ,'0')='1') as KFJ_ddds, ");
		sql.append("(select sum(ddds) from #ttt where a.dept_CODE=dept_CODE and isnull(IS_ZSJ,'0')<>'1' and isnull(IS_KFJ,'0')<>'1') as oth_ddds, ");
		sql.append("(select sum(ddds) from #ttt where a.dept_CODE=dept_CODE and isnull(ANTI_LEVEL,'')='3' ) as ts_kjy_ddds, ");
		sql.append("(select sum(CHARGES) from #ttt where a.dept_CODE=dept_CODE) as all_money, ");
		sql.append("(select sum(CHARGES) from #ttt where a.dept_CODE=dept_CODE and isnull(IS_ZSJ,'0')='1') as ZSJ_money, ");
		sql.append("(select sum(CHARGES) from #ttt where a.dept_CODE=dept_CODE and isnull(IS_KFJ,'0')='1') as KFJ_money, ");
		sql.append("(select sum(CHARGES) from #ttt where a.dept_CODE=dept_CODE and isnull(IS_ZSJ,'0')<>'1' and isnull(IS_KFJ,'0')<>'1') as oth_money, ");
		sql.append("(select sum(CHARGES) from #ttt where a.dept_CODE=dept_CODE and isnull(ANTI_LEVEL,'')='3' ) as ts_kjy_money ");
		sql.append("from #ttt2 a                                                   ");

		if(!CommonFun.isNe(params.get("pm"))) {
			//sql.append("order by "+("1".equals(params.get("pm"))?"a.property_toxi":"sum(a.DDDS)")+" desc ");
		}

		sql.append("drop table #ttt                                                ");
		sql.append("drop table #ttt2                                               ");
		sql.append("drop table #property_toxi                                      ");
		return executeQuery(sql.toString(), params);
	}

	public Result query_expend_doctor_zj(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*, ");
		sql.append("b.Usejlgg,                                                     ");
		sql.append("b.Use_dw,                                                      ");
		sql.append("isnull(c.DRUG_YLFL_NAME,'') as property_toxi,                  ");
		sql.append("b.jixing,                                                      ");
		sql.append("b.druggg,                                                      ");
		sql.append("b.IS_ZSJ,                                                      ");
		sql.append("b.IS_KFJ,                                                      ");
		sql.append("case when b.ANTI_LEVEL='1' then '非限制级' when b.ANTI_LEVEL='2' then '限制级' when b.ANTI_LEVEL='3' then '特殊级' else '' end as ANTI_LEVEL,");
		sql.append("b.ddd_value,                                                   ");
		sql.append("Round((case  ");
		/*sql.append("when a.AMOUNT_UNITS = b.DRUG_unit and isnull(b.DDD_VALUE,0)<>0 then ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");*/
		sql.append("when a.AMOUNT_UNITS = b.PACK_UNIT and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)*b.PACK_ConvertNum/b.DDD_VALUE end ");
		sql.append("when a.AMOUNT_UNITS = b.Use_dw and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE*b.Usejlgg end ");
		sql.append("when isnull(a.AMOUNT_UNITS,'')<>'' and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");
		sql.append("else 0 end),2) as DDDS                                                ");
		sql.append(" into #ttt from report_DDD (nolock) a");
		sql.append(" left join dict_his_drug (nolock) b on a.drug_code=b.drug_code");
		sql.append(" left join DICT_SYS_YLFL(nolock) c on c.DRUG_YLFL_CODE=b.DRUG_YLFL ");
		sql.append(" inner join v_his_in_patientvisit_all (nolock) vhipas ");
		sql.append(" on vhipas.patient_id=a.patient_id ");
		sql.append(" and vhipas.visit_id=a.visit_id and vhipas.discharge_datetime is not null ");
		sql.append(" where 1=1 ");
		sql.append(getFilter(params, false, false));

		sql.append(" select doctor_code,property_toxi,count(1) num,sum(CHARGES) CHARGES into #property_toxi from #ttt a where exists (select 1 from dict_his_drug (nolock) where a.drug_code=drug_code )  group by property_toxi,doctor_code ");

		sql.append("select                                                         ");
		sql.append("dhd.dept_name doc_dept_name,a.doctor_name,a.doctor_code DOCTOR_NO, ");
		sql.append("(select sum(num) from #property_toxi pt where pt.doctor_code=a.doctor_code ) as kind_drug_num ");
		sql.append("into #ttt2                                                     ");
		sql.append("from #ttt a                                                    ");
		sql.append("left join dict_his_users (nolock) dhu on a.doctor_code=dhu.USER_ID ");
		sql.append("left join dict_his_deptment (nolock) dhd                       ");
		sql.append("on dhu.USER_DEPT = dhd.dept_CODE                               ");
		sql.append("group by dhd.dept_name,a.doctor_name,a.doctor_code      ");


		sql.append("select                                                         ");
		sql.append("a.doctor_name,a.DOCTOR_NO,a.doc_dept_name,a.kind_drug_num,     ");
		sql.append("(select sum(ddds) from #ttt where a.DOCTOR_NO=doctor_code) as ddds, ");
		sql.append("(select sum(ddds) from #ttt where a.DOCTOR_NO=doctor_code and isnull(IS_ZSJ,'0')='1') as ZSJ_ddds, ");
		sql.append("(select sum(ddds) from #ttt where a.DOCTOR_NO=doctor_code and isnull(IS_KFJ,'0')='1') as KFJ_ddds, ");
		sql.append("(select sum(ddds) from #ttt where a.DOCTOR_NO=doctor_code and isnull(IS_ZSJ,'0')<>'1' and isnull(IS_KFJ,'0')<>'1') as oth_ddds, ");
		sql.append("(select sum(ddds) from #ttt where a.DOCTOR_NO=doctor_code and isnull(ANTI_LEVEL,'')='3' ) as ts_kjy_ddds, ");
		sql.append("(select sum(CHARGES) from #ttt where a.DOCTOR_NO=doctor_code) as all_money, ");
		sql.append("(select sum(CHARGES) from #ttt where a.DOCTOR_NO=doctor_code and isnull(IS_ZSJ,'0')='1') as ZSJ_money, ");
		sql.append("(select sum(CHARGES) from #ttt where a.DOCTOR_NO=doctor_code and isnull(IS_KFJ,'0')='1') as KFJ_money, ");
		sql.append("(select sum(CHARGES) from #ttt where a.DOCTOR_NO=doctor_code and isnull(IS_ZSJ,'0')<>'1' and isnull(IS_KFJ,'0')<>'1') as oth_money, ");
		sql.append("(select sum(CHARGES) from #ttt where a.DOCTOR_NO=doctor_code and isnull(ANTI_LEVEL,'')='3' ) as ts_kjy_money ");
		sql.append("from #ttt2 a                                                   ");

		if(!CommonFun.isNe(params.get("pm"))) {
			//sql.append("order by "+("1".equals(params.get("pm"))?"a.property_toxi":"sum(a.DDDS)")+" desc ");
		}
		sql.append("drop table #ttt            ");
		sql.append("drop table #ttt2           ");
		sql.append("drop table #property_toxi  ");
		return executeQuery(sql.toString(), params);
	}

	public Result query_expend_stats(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*, ");
		sql.append("b.Usejlgg,                                                     ");
		sql.append("b.Use_dw,                                                      ");
		sql.append("isnull(c.DRUG_YLFL_NAME,'') as property_toxi,                  ");
		sql.append("b.jixing,                                                      ");
		sql.append("b.druggg,                                                      ");
		sql.append("b.IS_ZSJ,                                                      ");
		sql.append("b.IS_KFJ,                                                      ");
		sql.append("b.IS_ANTI_DRUG,                                                ");
		sql.append("case when b.ANTI_LEVEL='1' then '非限制级' when b.ANTI_LEVEL='2' then '限制级' when b.ANTI_LEVEL='3' then '特殊级' else '' end as ANTI_LEVEL,");
		sql.append("b.ddd_value,                                                   ");
		sql.append("Round((case  ");
		/*sql.append("when a.AMOUNT_UNITS = b.DRUG_unit and isnull(b.DDD_VALUE,0)<>0 then ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");*/
		sql.append("when a.AMOUNT_UNITS = b.PACK_UNIT and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)*b.PACK_ConvertNum/b.DDD_VALUE end ");
		sql.append("when a.AMOUNT_UNITS = b.Use_dw and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE*b.Usejlgg end ");
		sql.append("when isnull(a.AMOUNT_UNITS,'')<>'' and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");
		sql.append("else 0 end),2) as DDDS                                                ");
		sql.append(" into #ttt from report_DDD (nolock) a");
		sql.append(" left join dict_his_drug (nolock) b on a.drug_code=b.drug_code");
		sql.append(" left join DICT_SYS_YLFL(nolock) c on c.DRUG_YLFL_CODE=b.DRUG_YLFL ");
		sql.append(" inner join v_his_in_patientvisit_all (nolock) vhipas ");
		sql.append(" on vhipas.patient_id=a.patient_id ");
		sql.append(" and vhipas.visit_id=a.visit_id and vhipas.discharge_datetime is not null ");
		sql.append(" where 1=1 ");
		sql.append(getFilter(params, false, false));

		sql.append("select                                                         ");
		sql.append("a.property_toxi,a.drug_name,a.jixing,                          ");
		sql.append("a.druggg,a.IS_ANTI_DRUG,a.ANTI_LEVEL,                          ");
		sql.append("a.IS_ZSJ,                                                      ");
		sql.append("a.IS_KFJ,                                                      ");
		sql.append("cast(sum(a.AMOUNT) as varchar)+max(a.AMOUNT_UNITS) num,        ");
		sql.append("sum(a.CHARGES) all_money,                                      ");
		sql.append("sum(a.DDDS) ddds                                               ");
		sql.append("from #ttt a                                                    ");
		sql.append("inner join dict_his_drug (nolock) dhd on a.drug_code=dhd.drug_code ");
		sql.append("group by a.property_toxi,a.drug_name,a.jixing,a.ANTI_LEVEL,    ");
		sql.append("a.druggg,a.IS_ANTI_DRUG,a.Usejlgg,a.IS_ZSJ,a.IS_KFJ            ");
		if(!CommonFun.isNe(params.get("pm"))) {
			sql.append("order by "+("1".equals(params.get("pm"))?"a.property_toxi":"2".equals(params.get("pm"))?"sum(a.DDDS)":"sum(a.CHARGES)")+" desc ");
		}
		sql.append("                                                               ");
		sql.append("drop table #ttt                                                ");
		return executeQuery(sql.toString(), params);
	}

	public Result query_dept_dui(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();

		sql.append("select sum(dd.ts) all_patient_ts,dd.DEPT_DISCHARGE as dept_CODE into #all_patient_ts from (  ");
		sql.append("select vhia.patient_id,                                        ");
		sql.append("vhia.visit_id,                                                 ");
		sql.append("vhia.ADMISSION_DATETIME,                                       ");
		sql.append("vhia.DISCHARGE_DATETIME,vhia.DEPT_DISCHARGE,                   ");
		sql.append("case when DATEDIFF(DAY,vhia.ADMISSION_DATETIME,vhia.DISCHARGE_DATETIME) = 1 then 1 ");
		sql.append("else DATEDIFF(DAY,vhia.ADMISSION_DATETIME,vhia.DISCHARGE_DATETIME) end ts ");
		sql.append("from V_his_in_patientvisit_all(nolock) vhia                    ");
		sql.append("where vhia.DISCHARGE_DATETIME between :start_time +' 00:00:00' and :end_time +' 23:59:59' ");
		sql.append("and vhia.DISCHARGE_DATETIME is not null ");
		sql.append("and vhia.d_type = '1' ");
		sql.append("group by vhia.patient_id,                                      ");
		sql.append("vhia.visit_id,vhia.ADMISSION_DATETIME,                         ");
		sql.append("vhia.DISCHARGE_DATETIME,vhia.DEPT_DISCHARGE) dd group by dd.DEPT_DISCHARGE  ");

		sql.append("select count(1) as all_patient,a.DEPT_DISCHARGE as dept_CODE into #all_patient from (select patient_id,visit_id,vhia.DEPT_DISCHARGE  ");
		sql.append("from V_his_in_patientvisit_all(nolock) vhia                    ");
		sql.append("where vhia.DISCHARGE_DATETIME between :start_time +' 00:00:00' and :end_time +' 23:59:59' ");
		sql.append("and vhia.DISCHARGE_DATETIME is not null ");
		sql.append("and vhia.d_type = '1' ");
		sql.append("group by vhia.patient_id,vhia.visit_id,vhia.DEPT_DISCHARGE) a group by a.DEPT_DISCHARGE  ");

		sql.append("select a.*, ");
		sql.append("b.Usejlgg,                                                     ");
		sql.append("b.Use_dw,                                                      ");
		sql.append("isnull(c.DRUG_YLFL_NAME,'') as property_toxi,                  ");
		sql.append("b.jixing,                                                      ");
		sql.append("b.druggg,                                                      ");
		sql.append("b.IS_ZSJ,                                                      ");
		sql.append("b.IS_KFJ,                                                      ");
		sql.append("case when b.ANTI_LEVEL='1' then '非限制级' when b.ANTI_LEVEL='2' then '限制级' when b.ANTI_LEVEL='3' then '特殊级' else '' end as ANTI_LEVEL,");
		sql.append("b.ddd_value,                                                   ");
		sql.append("Round((case  ");
		/*sql.append("when a.AMOUNT_UNITS = b.DRUG_unit and isnull(b.DDD_VALUE,0)<>0 then ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");*/
		sql.append("when a.AMOUNT_UNITS = b.PACK_UNIT and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)*b.PACK_ConvertNum/b.DDD_VALUE end ");
		sql.append("when a.AMOUNT_UNITS = b.Use_dw and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE*b.Usejlgg end ");
		sql.append("when isnull(a.AMOUNT_UNITS,'')<>'' and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");
		sql.append("else 0 end),2) as DDDS                                                ");
		sql.append(" into #ttt from report_DDD (nolock) a");
		sql.append(" left join dict_his_drug (nolock) b on a.drug_code=b.drug_code");
		sql.append(" left join DICT_SYS_YLFL(nolock) c on c.DRUG_YLFL_CODE=b.DRUG_YLFL ");
		sql.append(" inner join v_his_in_patientvisit_all (nolock) vhipas ");
		sql.append(" on vhipas.patient_id=a.patient_id ");
		sql.append(" and vhipas.visit_id=a.visit_id and vhipas.discharge_datetime is not null and vhipas.d_type = '1' ");
		sql.append(" where 1=1 ");
		sql.append(getFilter(params, false, false));

		sql.append("select                                                         ");
		sql.append("a.dept_CODE,a.dept_name,                                   ");
		sql.append("isnull(ap.all_patient,0) as all_patient, ");
		sql.append("isnull(apt.all_patient_ts,0) as  all_patient_ts ");
		sql.append("into #ttt2                                                     ");
		sql.append("from #ttt a                                                    ");
		sql.append("left join dict_his_deptment (nolock) dhd                       ");
		sql.append("on a.dept_CODE = dhd.dept_CODE                             ");
		sql.append("left join #all_patient_ts apt on apt.dept_CODE=a.dept_CODE ");
		sql.append("left join #all_patient ap on ap.dept_CODE=a.dept_CODE ");
		sql.append("group by a.dept_CODE,a.dept_name,apt.all_patient_ts,ap.all_patient  ");

		sql.append(" select b.dept_CODE,b.dept_name,a.property_toxi,count(1) num,sum(a.CHARGES) CHARGES into #property_toxi ");
		sql.append("from #ttt2 b                                                   ");
		sql.append("left join #ttt a on b.dept_code=a.dept_code ");
		sql.append("where exists (select 1 from dict_his_drug (nolock) where a.drug_code=drug_code ) ");
		sql.append("group by a.property_toxi,a.CHARGES,b.dept_CODE,b.dept_name ");

		sql.append(" select sum(a.CHARGES) CHARGES into #ttt3 from #ttt a where exists (select 1 from dict_his_drug (nolock) where a.drug_code=drug_code ) ");

		sql.append("select b.*,                                                    ");
		sql.append("a.property_toxi,a.drug_name,a.jixing,                          ");
		sql.append("a.druggg,a.ANTI_LEVEL,                                         ");
		sql.append("sum(a.AMOUNT) num,                                             ");
		sql.append("sum(a.CHARGES) all_money,                                      ");
		sql.append("(select sum(num) from #property_toxi pt where pt.dept_code=b.dept_code ) as kind_drug_num, ");
		sql.append("sum(a.sylc) as sylc,                                          ");
		sql.append("sum(a.DDDS) ddds,                                              ");
		sql.append("(select top 1 CHARGES from #ttt3) all_drug_money               ");
		sql.append("from #ttt2 b                                                   ");
		sql.append("inner join #ttt a on b.dept_code=a.dept_code                    ");
		sql.append("inner join dict_his_drug (nolock) dhd on a.drug_code=dhd.drug_code ");
		sql.append("group by a.property_toxi,a.drug_name,a.jixing,                 ");
		sql.append("a.druggg,a.ANTI_LEVEL,a.Usejlgg,b.dept_code,b.dept_name,       ");
		sql.append("b.all_patient,b.all_patient_ts                           ");
		sql.append("order by b.dept_code,"+("1".equals(params.get("pm"))?"a.property_toxi":"sum(a.DDDS)")+" desc ");

		sql.append("drop table #ttt ");
		sql.append("drop table #ttt2 ");
		sql.append("drop table #ttt3 ");
		sql.append("drop table #property_toxi ");
		sql.append("drop table #all_patient_ts ");
		sql.append("drop table #all_patient ");
		return executeQuery(sql.toString(), params);
	}

	public Result query_expend_dept_dui(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(dd.ts) all_patient_ts,dd.DEPT_DISCHARGE as dept_CODE into #all_patient_ts from (  ");
		sql.append("select vhia.patient_id,                                        ");
		sql.append("vhia.visit_id,                                                 ");
		sql.append("vhia.ADMISSION_DATETIME,                                       ");
		sql.append("vhia.DISCHARGE_DATETIME,vhia.DEPT_DISCHARGE,                   ");
		sql.append("case when DATEDIFF(DAY,vhia.ADMISSION_DATETIME,vhia.DISCHARGE_DATETIME) = 1 then 1 ");
		sql.append("else DATEDIFF(DAY,vhia.ADMISSION_DATETIME,vhia.DISCHARGE_DATETIME) end ts ");
		sql.append("from V_his_in_patientvisit_all(nolock) vhia                    ");
		sql.append("where vhia.DISCHARGE_DATETIME between :start_time +' 00:00:00' and :end_time +' 23:59:59' ");
		sql.append("and vhia.DISCHARGE_DATETIME is not null ");
		sql.append("group by vhia.patient_id,                                      ");
		sql.append("vhia.visit_id,vhia.ADMISSION_DATETIME,                         ");
		sql.append("vhia.DISCHARGE_DATETIME,vhia.DEPT_DISCHARGE) dd group by dd.DEPT_DISCHARGE  ");

		sql.append("select count(1) as all_patient,a.DEPT_DISCHARGE as dept_CODE into #all_patient from (select patient_id,visit_id,vhia.DEPT_DISCHARGE  ");
		sql.append("from V_his_in_patientvisit_all(nolock) vhia                    ");
		sql.append("where vhia.DISCHARGE_DATETIME between :start_time +' 00:00:00' and :end_time +' 23:59:59' ");
		sql.append("and vhia.DISCHARGE_DATETIME is not null ");
		sql.append("group by vhia.patient_id,vhia.visit_id,vhia.DEPT_DISCHARGE) a group by a.DEPT_DISCHARGE  ");

		sql.append("select a.*, ");
		sql.append("b.Usejlgg,                                                     ");
		sql.append("b.Use_dw,                                                      ");
		sql.append("isnull(c.DRUG_YLFL_NAME,'') as property_toxi,                  ");
		sql.append("b.jixing,                                                      ");
		sql.append("b.druggg,                                                      ");
		sql.append("b.IS_ZSJ,                                                      ");
		sql.append("b.IS_KFJ,                                                      ");
		sql.append("case when b.ANTI_LEVEL='1' then '非限制级' when b.ANTI_LEVEL='2' then '限制级' when b.ANTI_LEVEL='3' then '特殊级' else '' end as ANTI_LEVEL,");
		sql.append("b.ddd_value,                                                   ");
		sql.append("Round((case  ");
		/*sql.append("when a.AMOUNT_UNITS = b.DRUG_unit and isnull(b.DDD_VALUE,0)<>0 then ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");*/
		sql.append("when a.AMOUNT_UNITS = b.PACK_UNIT and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)*b.PACK_ConvertNum/b.DDD_VALUE end ");
		sql.append("when a.AMOUNT_UNITS = b.Use_dw and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE*b.Usejlgg end ");
		sql.append("when isnull(a.AMOUNT_UNITS,'')<>'' and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");
		sql.append("else 0 end),2) as DDDS                                                ");
		sql.append(" into #ttt from report_DDD (nolock) a");
		sql.append(" left join dict_his_drug (nolock) b on a.drug_code=b.drug_code");
		sql.append(" left join DICT_SYS_YLFL(nolock) c on c.DRUG_YLFL_CODE=b.DRUG_YLFL ");
		sql.append(" inner join v_his_in_patientvisit_all (nolock) vhipas ");
		sql.append(" on vhipas.patient_id=a.patient_id ");
		sql.append(" and vhipas.visit_id=a.visit_id and vhipas.discharge_datetime is not null ");
		sql.append(" where 1=1 ");
		sql.append(getFilter(params, false, false));

		sql.append("select                                                         ");
		sql.append("dhd.dept_CODE,dhd.dept_name,                                   ");
		sql.append("isnull(ap.all_patient,0) as all_patient, ");
		sql.append("isnull(apt.all_patient_ts,0) as  all_patient_ts ");
		sql.append("into #ttt2                                                     ");
		sql.append("from #ttt a                                                    ");
		sql.append("left join dict_his_deptment (nolock) dhd                       ");
		sql.append("on a.dept_CODE = dhd.dept_CODE                                 ");
		sql.append("left join #all_patient_ts apt on apt.dept_CODE=a.dept_CODE ");
		sql.append("left join #all_patient ap on ap.dept_CODE=a.dept_CODE ");
		sql.append("group by dhd.dept_CODE,dhd.dept_name,apt.all_patient_ts,ap.all_patient ");

		sql.append("select b.*,                                                    ");
		sql.append("a.property_toxi,a.drug_name,a.jixing,                          ");
		sql.append("a.druggg,a.ANTI_LEVEL,                                         ");
		sql.append("sum(a.AMOUNT) num,                                             ");
		sql.append("sum(a.CHARGES) all_money,                                      ");
		sql.append("sum(a.DDDS) ddds,                                              ");
		sql.append("(select sum(DDDS) from #ttt where b.dept_code=dept_code) as ddds_zong, ");
		sql.append("(select sum(CHARGES) from #ttt where b.dept_code=dept_code) as all_money_zong, ");
		sql.append("(select sum(CHARGES) from #ttt) all_drug_money                 ");
		sql.append("from #ttt2 b                                                   ");
		sql.append("inner join #ttt a on b.dept_code=a.dept_code                ");
		sql.append("inner join dict_his_drug (nolock) dhd on a.drug_code=dhd.drug_code ");
		sql.append("group by a.property_toxi,a.drug_name,a.jixing,                 ");
		sql.append("a.druggg,a.ANTI_LEVEL,a.Usejlgg,b.dept_code,b.dept_name,       ");
		sql.append("b.all_patient,b.all_patient_ts                                 ");
		if("1".equals(params.get("pm"))) {
			sql.append("order by all_money_zong desc,sum(a.CHARGES) desc ");
		}else if("2".equals(params.get("pm"))) {
			sql.append("order by sum(a.CHARGES) desc,all_money_zong desc ");
		}else if("3".equals(params.get("pm"))) {
			sql.append("order by ddds_zong desc,sum(a.DDDS) desc ");
		}else if("4".equals(params.get("pm"))) {
			sql.append("order by sum(a.DDDS) desc,ddds_zong desc ");
		}

		sql.append("drop table #ttt                                                ");
		sql.append("drop table #ttt2                                               ");
		sql.append("drop table #all_patient_ts ");
		sql.append("drop table #all_patient ");
		return executeQuery(sql.toString(), params);
	}

	public Result query_expend_doctor_dui(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(dd.ts) all_patient_ts,dd.DEPT_DISCHARGE as dept_CODE into #all_patient_ts from (  ");
		sql.append("select vhia.patient_id,                                        ");
		sql.append("vhia.visit_id,                                                 ");
		sql.append("vhia.ADMISSION_DATETIME,                                       ");
		sql.append("vhia.DISCHARGE_DATETIME,vhia.DEPT_DISCHARGE,                   ");
		sql.append("case when DATEDIFF(DAY,vhia.ADMISSION_DATETIME,vhia.DISCHARGE_DATETIME) = 1 then 1 ");
		sql.append("else DATEDIFF(DAY,vhia.ADMISSION_DATETIME,vhia.DISCHARGE_DATETIME) end ts ");
		sql.append("from V_his_in_patientvisit_all(nolock) vhia                    ");
		sql.append("where vhia.DISCHARGE_DATETIME between :start_time +' 00:00:00' and :end_time +' 23:59:59' ");
		sql.append("and vhia.DISCHARGE_DATETIME is not null ");
		sql.append("group by vhia.patient_id,                                      ");
		sql.append("vhia.visit_id,vhia.ADMISSION_DATETIME,                         ");
		sql.append("vhia.DISCHARGE_DATETIME,vhia.DEPT_DISCHARGE) dd group by dd.DEPT_DISCHARGE  ");

		sql.append("select count(1) as all_patient,a.DEPT_DISCHARGE as dept_CODE into #all_patient from (select patient_id,visit_id,vhia.DEPT_DISCHARGE  ");
		sql.append("from V_his_in_patientvisit_all(nolock) vhia                    ");
		sql.append("where vhia.DISCHARGE_DATETIME between :start_time +' 00:00:00' and :end_time +' 23:59:59' ");
		sql.append("and vhia.DISCHARGE_DATETIME is not null ");
		sql.append("group by vhia.patient_id,vhia.visit_id,vhia.DEPT_DISCHARGE) a group by a.DEPT_DISCHARGE  ");

		sql.append("select a.*, ");
		sql.append("b.Usejlgg,                                                     ");
		sql.append("b.Use_dw,                                                      ");
		sql.append("isnull(c.DRUG_YLFL_NAME,'') as property_toxi,                  ");
		sql.append("b.jixing,                                                      ");
		sql.append("b.druggg,                                                      ");
		sql.append("b.IS_ZSJ,                                                      ");
		sql.append("b.IS_KFJ,                                                      ");
		sql.append("case when b.ANTI_LEVEL='1' then '非限制级' when b.ANTI_LEVEL='2' then '限制级' when b.ANTI_LEVEL='3' then '特殊级' else '' end as ANTI_LEVEL,");
		sql.append("b.ddd_value,                                                   ");
		sql.append("Round((case  ");
		/*sql.append("when a.AMOUNT_UNITS = b.DRUG_unit and isnull(b.DDD_VALUE,0)<>0 then ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");*/
		sql.append("when a.AMOUNT_UNITS = b.PACK_UNIT and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)*b.PACK_ConvertNum/b.DDD_VALUE end ");
		sql.append("when a.AMOUNT_UNITS = b.Use_dw and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE*b.Usejlgg end ");
		sql.append("when isnull(a.AMOUNT_UNITS,'')<>'' and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");
		sql.append("else 0 end),2) as DDDS                                                ");
		sql.append(" into #ttt from report_DDD (nolock) a");
		sql.append(" left join dict_his_drug (nolock) b on a.drug_code=b.drug_code");
		sql.append(" left join DICT_SYS_YLFL(nolock) c on c.DRUG_YLFL_CODE=b.DRUG_YLFL ");
		sql.append(" inner join v_his_in_patientvisit_all (nolock) vhipas ");
		sql.append(" on vhipas.patient_id=a.patient_id ");
		sql.append(" and vhipas.visit_id=a.visit_id and vhipas.discharge_datetime is not null ");
		sql.append(" where 1=1 ");
		sql.append(getFilter(params, false, false));

		sql.append("select                                                         ");
		sql.append("dhd.dept_name doc_dept_name,a.doctor_name doctor_name,a.doctor_code DOCTOR_NO, ");
		sql.append("isnull(ap.all_patient,0) as all_patient, ");
		sql.append("isnull(apt.all_patient_ts,0) as  all_patient_ts ");
		sql.append("into #ttt2                                                     ");
		sql.append("from #ttt a                                                    ");
		sql.append("left join dict_his_users (nolock) dhu on a.doctor_code=dhu.USER_ID ");
		sql.append("left join dict_his_deptment (nolock) dhd                       ");
		sql.append("on dhu.USER_DEPT = dhd.dept_CODE                               ");
		sql.append("left join #all_patient_ts apt on apt.dept_CODE=a.dept_CODE ");
		sql.append("left join #all_patient ap on ap.dept_CODE=a.dept_CODE ");
		sql.append("group by dhd.dept_name,a.doctor_name,a.doctor_code,apt.all_patient_ts,ap.all_patient ");

		sql.append("select b.*,                                                    ");
		sql.append("a.property_toxi,a.drug_name,a.jixing,                          ");
		sql.append("a.druggg,a.ANTI_LEVEL,                                         ");
		sql.append("sum(a.AMOUNT) num,                                             ");
		sql.append("sum(a.CHARGES) all_money,                                      ");
		sql.append("sum(a.DDDS) ddds,                                              ");
		sql.append("(select sum(DDDS) from #ttt where b.doctor_no=doctor_code) as ddds_zong, ");
		sql.append("(select sum(CHARGES) from #ttt where b.doctor_no=doctor_code) as all_money_zong, ");
		sql.append("(select sum(CHARGES) from #ttt) all_drug_money                 ");
		sql.append("from #ttt2 b                                                   ");
		sql.append("inner join #ttt a on b.doctor_no=a.doctor_code                  ");
		sql.append("inner join dict_his_drug (nolock) dhd on a.drug_code=dhd.drug_code ");
		sql.append("group by a.property_toxi,a.drug_name,a.jixing,                 ");
		sql.append("a.druggg,a.ANTI_LEVEL,a.Usejlgg,b.doctor_no,b.doctor_name,     ");
		sql.append("b.all_patient,b.all_patient_ts,b.doc_dept_name                 ");
		if("1".equals(params.get("pm"))) {
			sql.append("order by all_money_zong desc,sum(a.CHARGES) desc ");
		}else if("2".equals(params.get("pm"))) {
			sql.append("order by sum(a.CHARGES) desc,all_money_zong desc ");
		}else if("3".equals(params.get("pm"))) {
			sql.append("order by ddds_zong desc,sum(a.DDDS) desc ");
		}else if("4".equals(params.get("pm"))) {
			sql.append("order by sum(a.DDDS) desc,ddds_zong desc ");
		}

		sql.append("drop table #ttt                                                ");
		sql.append("drop table #ttt2                                               ");
		sql.append("drop table #all_patient_ts ");
		sql.append("drop table #all_patient ");
		return executeQuery(sql.toString(), params);
	}

	public Result query_dept_zj(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(dd.ts) all_patient_ts,dd.DEPT_DISCHARGE as dept_CODE into #all_patient_ts from (  ");
		sql.append("select vhia.patient_id,                                        ");
		sql.append("vhia.visit_id,                                                 ");
		sql.append("vhia.ADMISSION_DATETIME,                                       ");
		sql.append("vhia.DISCHARGE_DATETIME,vhia.DEPT_DISCHARGE,                   ");
		sql.append("case when DATEDIFF(DAY,vhia.ADMISSION_DATETIME,vhia.DISCHARGE_DATETIME) = 1 then 1 ");
		sql.append("else DATEDIFF(DAY,vhia.ADMISSION_DATETIME,vhia.DISCHARGE_DATETIME) end ts ");
		sql.append("from V_his_in_patientvisit_all(nolock) vhia                    ");
		sql.append("where vhia.DISCHARGE_DATETIME between :start_time +' 00:00:00' and :end_time +' 23:59:59' ");
		sql.append("and vhia.DISCHARGE_DATETIME is not null ");
		sql.append("and vhia.d_type = '1' ");
		sql.append("group by vhia.patient_id,                                      ");
		sql.append("vhia.visit_id,vhia.ADMISSION_DATETIME,                         ");
		sql.append("vhia.DISCHARGE_DATETIME,vhia.DEPT_DISCHARGE) dd group by dd.DEPT_DISCHARGE  ");

		sql.append("select count(1) as all_patient,a.DEPT_DISCHARGE as dept_CODE into #all_patient from (select patient_id,visit_id,vhia.DEPT_DISCHARGE  ");
		sql.append("from V_his_in_patientvisit_all(nolock) vhia                    ");
		sql.append("where vhia.DISCHARGE_DATETIME between :start_time +' 00:00:00' and :end_time +' 23:59:59' ");
		sql.append("and vhia.DISCHARGE_DATETIME is not null ");
		sql.append("and vhia.d_type = '1' ");
		sql.append("group by vhia.patient_id,vhia.visit_id,vhia.DEPT_DISCHARGE) a group by a.DEPT_DISCHARGE  ");

		sql.append("select a.*, ");
		sql.append("b.Usejlgg,                                                     ");
		sql.append("b.Use_dw,                                                      ");
		sql.append("isnull(c.DRUG_YLFL_NAME,'') as property_toxi,                  ");
		sql.append("b.jixing,                                                      ");
		sql.append("b.druggg,                                                      ");
		sql.append("b.IS_ZSJ,                                                      ");
		sql.append("b.IS_KFJ,                                                      ");
		sql.append("case when b.ANTI_LEVEL='1' then '非限制级' when b.ANTI_LEVEL='2' then '限制级' when b.ANTI_LEVEL='3' then '特殊级' else '' end as ANTI_LEVEL,");
		sql.append("b.ddd_value,                                                   ");
		sql.append("Round((case  ");
		/*sql.append("when a.AMOUNT_UNITS = b.DRUG_unit and isnull(b.DDD_VALUE,0)<>0 then ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");*/
		sql.append("when a.AMOUNT_UNITS = b.PACK_UNIT and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)*b.PACK_ConvertNum/b.DDD_VALUE end ");
		sql.append("when a.AMOUNT_UNITS = b.Use_dw and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE*b.Usejlgg end ");
		sql.append("when isnull(a.AMOUNT_UNITS,'')<>'' and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");
		sql.append("else 0 end),2) as DDDS                                                ");
		sql.append(" into #ttt from report_DDD (nolock) a");
		sql.append(" left join dict_his_drug (nolock) b on a.drug_code=b.drug_code");
		sql.append(" left join DICT_SYS_YLFL(nolock) c on c.DRUG_YLFL_CODE=b.DRUG_YLFL ");
		sql.append(" inner join v_his_in_patientvisit_all (nolock) vhipas ");
		sql.append(" on vhipas.patient_id=a.patient_id ");
		sql.append(" and vhipas.visit_id=a.visit_id and vhipas.discharge_datetime is not null and vhipas.d_type='1' ");
		sql.append(" where 1=1 ");
		sql.append(getFilter(params, false, false));

		sql.append(" select count(1) drug_all_patient,dept_code,dept_name into #drug_all_patient  ");
		sql.append(" from (select a.patient_id,a.visit_id,a.dept_code,a.dept_name from #ttt a ");
		sql.append(" inner join dict_his_drug (nolock) b on a.drug_code=b.drug_code ");
		sql.append(" group by patient_id,visit_id,dept_code,dept_name) a group by dept_code,dept_name ");

		sql.append("select                                                         ");
		sql.append("dhd.dept_CODE,dhd.dept_name,                                   ");
		sql.append("dap.drug_all_patient as drug_all_patient, ");
		sql.append("sum(a.sylc) sylc,                                                        ");
		sql.append("isnull(ap.all_patient,0) as all_patient, ");
		sql.append("isnull(apt.all_patient_ts,0) as  all_patient_ts ");
		sql.append("into #ttt2                                                     ");
		sql.append("from #ttt a                                                    ");
		sql.append("left join dict_his_deptment (nolock) dhd                       ");
		sql.append("on a.dept_CODE = dhd.dept_CODE                             ");
		sql.append("left join #all_patient_ts apt on apt.dept_CODE=a.dept_CODE ");
		sql.append("left join #all_patient ap on ap.dept_CODE=a.dept_CODE ");
		sql.append("left join #drug_all_patient dap on dap.dept_CODE=a.dept_CODE ");
		sql.append("group by dhd.dept_CODE,dhd.dept_name,apt.all_patient_ts,ap.all_patient,dap.drug_all_patient ");

		sql.append(" select property_toxi,dept_CODE,dept_name,count(1) num,sum(CHARGES) CHARGES into #property_toxi from #ttt a where exists (select 1 from dict_his_drug (nolock) where a.drug_code=drug_code )  group by property_toxi,dept_CODE,dept_name ");

		sql.append("select b.dept_code,b.dept_name,b.all_patient,b.all_patient_ts,b.drug_all_patient,");
		sql.append("sum(a.CHARGES) all_money,                                      ");
		sql.append("(select sum(num) from #property_toxi pt where pt.dept_code=b.dept_code ) as kind_drug_num, ");
		sql.append("sum(b.sylc) sylc,                                              ");
		sql.append("sum(a.DDDS) ddds,                                              ");
		sql.append("(select sum(CHARGES) from #ttt) all_drug_money                 ");
		sql.append("from #ttt2 b                                                   ");
		sql.append("inner join #ttt a on b.dept_code=a.dept_code                    ");
		sql.append("inner join dict_his_drug (nolock) dhd on a.drug_code=dhd.drug_code ");
		sql.append("group by b.dept_code,b.dept_name,                              ");
		sql.append("b.all_patient,b.all_patient_ts,b.drug_all_patient       ");
		sql.append("order by b.dept_code,sum(a.DDDS) desc ");

		sql.append("drop table #ttt                                                ");
		sql.append("drop table #ttt2                                               ");
		sql.append("drop table #all_patient_ts ");
		sql.append("drop table #all_patient ");
		sql.append("drop table #drug_all_patient ");
		sql.append("drop table #property_toxi ");
		return executeQuery(sql.toString(), params);
	}

	public Result query_dti(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer pa = new StringBuffer();
		pa.append("select sum(dd.ts) all_patient_ts from (                        ");
		pa.append("select vhia.patient_id,                                        ");
		pa.append("vhia.visit_id,                                                 ");
		pa.append("vhia.ADMISSION_DATETIME,                                       ");
		pa.append("vhia.DISCHARGE_DATETIME,                                       ");
		pa.append("case when DATEDIFF(DAY,vhia.ADMISSION_DATETIME,vhia.DISCHARGE_DATETIME) = 1 then 1 ");
		pa.append("else DATEDIFF(DAY,vhia.ADMISSION_DATETIME,vhia.DISCHARGE_DATETIME) end ts ");
		pa.append("from V_his_in_patientvisit_all(nolock) vhia                    ");
		pa.append("where vhia.DISCHARGE_DATETIME between :start_time +' 00:00:00' and :end_time +' 23:59:59' ");
		pa.append("and vhia.DISCHARGE_DATETIME is not null ");
		pa.append("and vhia.d_type='1' ");
		pa.append("group by vhia.patient_id,                                      ");
		pa.append("vhia.visit_id,vhia.ADMISSION_DATETIME,                         ");
		pa.append("vhia.DISCHARGE_DATETIME) dd                                    ");
		Record re = executeQuerySingleRecord(pa.toString(),params);

		sql.append("select a.*, ");
		sql.append("b.Usejlgg,                                                     ");
		sql.append("b.Use_dw,                                                      ");
		sql.append("isnull(c.DRUG_YLFL_NAME,'') as property_toxi,                  ");
		sql.append("b.jixing,                                                      ");
		sql.append("b.druggg,                                                      ");
		sql.append("b.IS_ZSJ,                                                      ");
		sql.append("b.IS_KFJ,                                                      ");
		sql.append("case when b.ANTI_LEVEL='1' then '非限制级' when b.ANTI_LEVEL='2' then '限制级' when b.ANTI_LEVEL='3' then '特殊级' else '' end as ANTI_LEVEL,");
		sql.append("b.ddd_value,                                                   ");
		sql.append("Round((case  ");
		/*sql.append("when a.AMOUNT_UNITS = b.DRUG_unit and isnull(b.DDD_VALUE,0)<>0 then ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");*/
		sql.append("when a.AMOUNT_UNITS = b.PACK_UNIT and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)*b.PACK_ConvertNum/b.DDD_VALUE end ");
		sql.append("when a.AMOUNT_UNITS = b.Use_dw and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE*b.Usejlgg end ");
		sql.append("when isnull(a.AMOUNT_UNITS,'')<>'' and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("case when isnull(b.DDD_VALUE,0)=0 then 0 else a.AMOUNT*isnull(b.DDD_Conversion,1)/b.DDD_VALUE end ");
		sql.append("else 0 end),2) as DDDS                                                ");
		sql.append(" into #ttt from report_DDD (nolock) a");
		sql.append(" left join dict_his_drug (nolock) b on a.drug_code=b.drug_code");
		sql.append(" left join DICT_SYS_YLFL(nolock) c on c.DRUG_YLFL_CODE=b.DRUG_YLFL ");
		sql.append(" inner join v_his_in_patientvisit_all (nolock) vhipas ");
		sql.append(" on vhipas.patient_id=a.patient_id ");
		sql.append(" and vhipas.visit_id=a.visit_id and vhipas.discharge_datetime is not null and vhipas.d_type='1' ");
		sql.append(" where 1=1 ");
		sql.append(getFilter(params, false, false));

		sql.append(" select SUM(CHARGES) CHARGES into #CHARGES from #ttt ");

		sql.append("select sum(ddds) ddds,property_toxi into #ddd    ");
		sql.append("from #ttt ");
		sql.append("group by property_toxi ");

		sql.append("SELECT                                                ");
		sql.append("a.property_toxi,                                      ");
		sql.append("SUM(a.CHARGES) all_money,                             ");
		sql.append("sum(a.sylc) sylc,                                     ");
		sql.append("b.ddds ddds,               ");
		sql.append("(SELECT top 1 CHARGES FROM #CHARGES) all_drug_money,       ");
		sql.append((CommonFun.isNe(re.get("all_patient_ts"))?0:re.get("all_patient_ts"))+" as  all_patient_ts ");
		sql.append("FROM #ttt a ");
		sql.append("inner join dict_his_drug (nolock) dhd on a.drug_code=dhd.drug_code ");
		sql.append("left join #ddd b on a.property_toxi=b.property_toxi   ");
		sql.append("GROUP BY a.property_toxi,b.ddds      ");
		if(!CommonFun.isNe(params.get("pm"))) {
			sql.append("order by "+("1".equals(params.get("pm"))?"a.property_toxi":"sum(a.DDDS)")+" desc ");
		}
		sql.append("DROP TABLE #ttt ");
		sql.append("DROP TABLE #CHARGES  ");
		sql.append("DROP TABLE ##ddd  ");
		return executeQuery(sql.toString(), params);
	}

	public String getFilter(Map<String, Object> params,boolean is_dept_permission,boolean is_doc_permission) {
		StringBuffer sql = new StringBuffer();
		sql.append(" and vhipas.DISCHARGE_DATETIME between :start_time +' 00:00:00' and :end_time +' 23:59:59' ");
		Object d_type = params.get("d_type");
		if(!CommonFun.isNe(d_type)){
			if(d_type instanceof String) {
				sql.append(" and vhipas.d_type = '"+d_type+"'");
			}else{
				sql.append(" and vhipas.d_type in (:d_type) ");
			}
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
		if(!CommonFun.isNe(params.get("drug_type")) ||
				!CommonFun.isNe(params.get("jixing")) ||
				!CommonFun.isNe(params.get("kjy_drug_type")) ||
				!CommonFun.isNe(params.get("kjy_drug_level")) ||
				!CommonFun.isNe(params.get("yllb_code"))) {
			sql.append("and exists (select 1 from dict_his_drug(nolock) dhd ");
			sql.append("where dhd.drug_code=a.drug_code                     ");
			if(!CommonFun.isNe(params.get("drug_type"))) {
				sql.append(" and exists (select 1 from split(:drug_type,',') ");
				sql.append(" sp where sp.col= b.property_toxi) ");
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
				sql.append(" and exists (select 1 from split(:kjy_drug_level,',') ");
				sql.append(" sp where sp.col= b.anti_level) ");
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
