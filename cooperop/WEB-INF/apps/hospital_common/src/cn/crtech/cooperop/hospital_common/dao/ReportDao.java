package cn.crtech.cooperop.hospital_common.dao;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class ReportDao extends BaseDao {

	public Result queryKjyOutpUse(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		sql.append("select a.*, ");
		sql.append("a.kjy_order_datas AS cf ");
		sql.append("from report_kjy_patient (nolock) a  ");
		sql.append(" where drug_kind_num>0 ");
		sql.append(" and a.DISCHARGE_DATETIME between :start_time + ' 00:00:00'");
		sql.append(" and :end_time + ' 23:59:59'");

		sql2.append("select count(1) from report_kjy_patient (nolock) a where drug_kind_num>0 ");
		sql2.append(" and a.DISCHARGE_DATETIME between :start_time + ' 00:00:00'");
		sql2.append(" and :end_time + ' 23:59:59'");

		String fiter = getFifter(params, "a");

		Result executeQuery = executeQueryLimit(sql.toString() + fiter, params, sql2.toString() + fiter);
		return executeQuery;
	}

	public Result querykjyoutpuse_dept(Map<String, Object> params) throws Exception {
		return executeQuery(get_queryKjyOutpUseStr(params, "dept_code", "dept_name"), params);
	}

	public Result querykjyoutpuse_doctor(Map<String, Object> params) throws Exception {
		return executeQuery(get_queryKjyOutpUseStr(params, "doctor_code", "doctor_name"), params);
	}

	public Result querykjyoper(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		sql.append("select a.*, ");
		sql.append("a.kjy_order_datas AS cf,                            ");
		
		sql.append("(select sum(DATEDIFF(day,                           ");
		sql.append("b.start_date_time,b.stop_date_time)+1)              ");
		sql.append("FROM his_in_orders (nolock) b                       ");
		sql.append("INNER JOIN dict_his_drug (nolock) drug              ");
		sql.append("ON b.ORDER_CODE= drug.drug_code                     ");
		sql.append("WHERE a.patient_id= b.patient_id                    ");
		sql.append("AND a.visit_id= b.visit_id                          ");
		sql.append("AND isnull( drug.IS_ANTI_DRUG,'0')<>'0'             ");
		sql.append("and exists(                                         ");
		sql.append("select 1 from his_in_patientvisit (nolock) hip          ");
		sql.append("where hip.patient_id=b.patient_id                   ");
		sql.append("and hip.visit_id=b.visit_id                         ");
		sql.append("and hip.MEDICATE_CAUSE ='1'                      ");
		sql.append(") ) as yf_use_drug_day                               ");
		
		sql.append("from report_kjy_patient (nolock) a  ");
		
		sql.append(" where a.DISCHARGE_DATETIME between :start_time + ' 00:00:00'");
		sql.append(" and :end_time + ' 23:59:59'");
		sql.append(" and isnull(a.oper_message,'')<>'' ");

		sql2.append("select count(1) from report_kjy_patient (nolock) a where ");
		sql2.append(" a.DISCHARGE_DATETIME between :start_time + ' 00:00:00'");
		sql2.append(" and :end_time + ' 23:59:59'");
		sql2.append(" and isnull(a.oper_message,'')<>'' ");

		String fiter = getFifter(params, "a");

		Result executeQuery = executeQueryLimit(sql.toString() + fiter, params, sql2.toString() + fiter);
		return executeQuery;
	}

	/*
	 * public Result querykjyoper_dept(Map<String, Object> params) throws
	 * Exception { StringBuffer sql = new StringBuffer(); StringBuffer sql2 =
	 * new StringBuffer(); sql.
	 * append("select *                                                       "
	 * ); sql.
	 * append("into #tt                                                       "
	 * ); sql.
	 * append("from report_kjy_patient (nolock) a                             "
	 * ); sql.
	 * append("where                                                          "
	 * ); sql.
	 * append("exists (select 1 from report_kjy_patient (nolock) b where      "
	 * ); sql.append("b.DISCHARGE_DATETIME between '"+params.get("start_time")
	 * +"' + ' 00:00:00' "); sql.append("and '"+params.get("end_time")
	 * +"' + ' 23:59:59'                 "); sql.
	 * append("and isnull(a.oper_message,'')<>''                              "
	 * ); sql.
	 * append("and b.patient_id=a.patient_id and b.visit_id=a.visit_id)       "
	 * ); String fiter = getFifter(params, "a"); sql.append(fiter);
	 * 
	 * sql.
	 * append("select                                                         "
	 * ); sql.
	 * append("a.dept_code,a.dept_name,                                       "
	 * ); sql.
	 * append("sum(a.oper_num) as oper_patient_num,                                  "
	 * ); sql.
	 * append("(select count(1) from                                          "
	 * ); sql.
	 * append("(select tt.patient_id,tt.visit_id from #tt                     "
	 * ); sql.
	 * append("tt where exists(                                               "
	 * ); sql.
	 * append("select 1 from his_in_orders (nolock) b                         "
	 * ); sql.
	 * append("INNER JOIN dict_his_drug (nolock) drug                         "
	 * ); sql.
	 * append("ON b.ORDER_CODE= drug.drug_code                                "
	 * ); sql.
	 * append("WHERE tt.patient_id= b.patient_id                              "
	 * ); sql.
	 * append("AND tt.visit_id= b.visit_id                                    "
	 * ); sql.
	 * append("AND isnull(drug.IS_ANTI_DRUG,'0')<>'0'                         "
	 * ); sql.
	 * append("and exists (select 1 from his_in_diagnosis(nolock) hid         "
	 * ); sql.
	 * append("inner join dict_his_diagnosis(nolock) dhd                      "
	 * ); sql.
	 * append("on hid.DIAGNOSIS_CODE=dhd.diagnosis_CODE                       "
	 * ); sql.
	 * append("where hid.patient_id=tt.patient_id                             "
	 * ); sql.
	 * append("and hid.visit_id=tt.visit_id                                   "
	 * ); sql.
	 * append("and hid.DIAGNOSIS_DATE                                         "
	 * ); sql.
	 * append("< b.stop_date_time                                             "
	 * ); sql.
	 * append("and dhd.ganrlx='0'))                                           "
	 * ); sql.
	 * append("and tt.dept_code=a.dept_code                                   "
	 * ); sql.
	 * append("GROUP BY tt.patient_id,tt.visit_id) aa                         "
	 * ); sql.
	 * append(") as yf_kjy_patient_num,                                       "
	 * ); sql.
	 * append("                                                               "
	 * ); sql.
	 * append("(select count(1) from (select                                  "
	 * ); sql.
	 * append("tt.patient_id,tt.visit_id from #tt tt                          "
	 * ); sql.
	 * append("where tt.dept_code=a.dept_code                                 "
	 * ); sql.
	 * append("and tt.oper_before_drug = '0.5-2h'                             "
	 * ); sql.
	 * append("GROUP BY tt.patient_id,tt.visit_id) aa                         "
	 * ); sql.
	 * append(") as time_num,                                                 "
	 * ); sql.
	 * append("                                                               "
	 * ); sql.
	 * append("(                                                              "
	 * ); sql.
	 * append("select sum(DATEDIFF(day,b.start_date_time,                     "
	 * ); sql.
	 * append("b.stop_date_time)+1) from #tt tt                               "
	 * ); sql.
	 * append("inner join his_in_orders (nolock) b                            "
	 * ); sql.
	 * append("on tt.patient_id=b.patient_id                                  "
	 * ); sql.
	 * append("and tt.visit_id=b.visit_id                                     "
	 * ); sql.
	 * append("INNER JOIN dict_his_drug (nolock) drug                         "
	 * ); sql.
	 * append("ON b.ORDER_CODE= drug.drug_code                                "
	 * ); sql.
	 * append("AND isnull(drug.IS_ANTI_DRUG,'0')<>'0'                         "
	 * ); sql.
	 * append("where tt.dept_code=a.dept_code                                 "
	 * ); sql.
	 * append("and exists (select 1 from his_in_diagnosis(nolock) hid         "
	 * ); sql.
	 * append("inner join dict_his_diagnosis(nolock) dhd                      "
	 * ); sql.
	 * append("on hid.DIAGNOSIS_CODE=dhd.diagnosis_CODE                       "
	 * ); sql.
	 * append("where hid.patient_id=tt.patient_id                             "
	 * ); sql.
	 * append("and hid.visit_id=tt.visit_id                                   "
	 * ); sql.
	 * append("and hid.DIAGNOSIS_DATE                                         "
	 * ); sql.
	 * append("< b.stop_date_time                                             "
	 * ); sql.
	 * append("and dhd.ganrlx='0')                                            "
	 * ); sql.
	 * append(") as use_time,                                                 "
	 * ); sql.
	 * append("                                                               "
	 * ); sql.
	 * append("(select count(1) from                                          "
	 * ); sql.
	 * append("(select tt.patient_id,tt.visit_id from #tt tt                  "
	 * ); sql.
	 * append("inner join his_in_orders (nolock) b                            "
	 * ); sql.
	 * append("on tt.patient_id=b.patient_id                                  "
	 * ); sql.
	 * append("and tt.visit_id=b.visit_id                                     "
	 * ); sql.
	 * append("INNER JOIN dict_his_drug (nolock) drug                         "
	 * ); sql.
	 * append("ON b.ORDER_CODE= drug.drug_code                                "
	 * ); sql.
	 * append("AND isnull(drug.IS_ANTI_DRUG,'0')<>'0'                         "
	 * ); sql.
	 * append("where tt.dept_code=a.dept_code                                 "
	 * ); sql.
	 * append("and exists (select 1 from his_in_diagnosis(nolock) hid         "
	 * ); sql.
	 * append("inner join dict_his_diagnosis(nolock) dhd                      "
	 * ); sql.
	 * append("on hid.DIAGNOSIS_CODE=dhd.diagnosis_CODE                       "
	 * ); sql.
	 * append("where hid.patient_id=tt.patient_id                             "
	 * ); sql.
	 * append("and hid.visit_id=tt.visit_id                                   "
	 * ); sql.
	 * append("and hid.DIAGNOSIS_DATE                                         "
	 * ); sql.
	 * append("< b.stop_date_time                                             "
	 * ); sql.
	 * append("and dhd.ganrlx='0')                                            "
	 * ); sql.
	 * append("and DATEDIFF(hh,b.start_date_time,                             "
	 * ); sql.
	 * append("b.stop_date_time) > 24                                         "
	 * ); sql.
	 * append("group by tt.patient_id,tt.visit_id) aa                         "
	 * ); sql.
	 * append(") as not24_num                                                 "
	 * ); sql.
	 * append("                                                               "
	 * ); sql.
	 * append("from #tt a                                                     "
	 * ); sql.
	 * append("group by a.dept_code,a.dept_name                               "
	 * ); sql.
	 * append("                                                               "
	 * ); sql.
	 * append("drop table #tt                                                 "
	 * );
	 * 
	 * Result executeQuery = executeQuery(sql.toString(), params); return
	 * executeQuery; }
	 */

	public Result querykjyoper_dept(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		// 手术病人
		sql.append(" set nocount on                               ");
		sql.append(" SELECT                                        ");
		sql.append("   *                                           ");
		sql.append(" INTO #tt                                      ");
		sql.append(" FROM                                          ");
		sql.append("   report_kjy_patient (nolock) a               ");
		sql.append(" WHERE                                         ");
		sql.append(" a.kjy_kind_num >0     and                         ");
		sql.append(" a.DISCHARGE_DATETIME between '" + params.get("start_time") + "' + ' 00:00:00' ");
		sql.append(" and '" + params.get("end_time") + "' + ' 23:59:59'                 ");

		String fiter = getFifter(params, "a");
		sql.append(fiter);

		// 预防用抗菌药
		sql.append(" SELECT                                        ");
		sql.append(" a.*                                           ");
		sql.append(" INTO #yf_kjy                                  ");
		sql.append(" FROM #tt  (nolock) a                          ");
		sql.append(" INNER JOIN his_in_patientvisit (nolock) b     ");
		sql.append(" ON a.PATIENT_ID =b.PATIENT_ID                 ");
		sql.append(" AND a.VISIT_ID =b.VISIT_ID                    ");
		sql.append(" WHERE b.MEDICATE_CAUSE = '1'                  ");
		// 手术例数
		sql.append(" SELECT                              ");
		sql.append(" dept_code,                          ");
		sql.append(" dept_name,                          ");
		sql.append(" sum(oper_num) as oper_patient_num   ");
		sql.append(" INTO #oper_patient_num              ");
		sql.append(" FROM #tt                            ");
		sql.append(" GROUP BY dept_code,dept_name        ");
		// 预防用抗菌药物手术例数
		sql.append(" SELECT                                ");
		sql.append(" dept_code,                            ");
		sql.append(" dept_name,                            ");
		sql.append(" sum(oper_num) as yf_kjy_patient_num   ");
		sql.append(" INTO #yf_kjy_patient_num              ");
		sql.append(" FROM #yf_kjy  (nolock)                ");
		sql.append(" GROUP BY dept_code,dept_name          ");
		// 手术前0.5- 2.0小时的给药例数
		sql.append(" SELECT                             ");
		sql.append(" dept_code,                         ");
		sql.append(" dept_name,                         ");
		sql.append(" COUNT(1) time_num                  ");
		sql.append(" INTO #time_num                     ");
		sql.append(" FROM #tt  (nolock)                 ");
		sql.append(" WHERE oper_before_drug = '0.5-2h'  ");
		sql.append(" GROUP BY dept_code,dept_name       ");
		// 预防用抗菌药物总天数
		sql.append(" select                                                             ");
		sql.append(" a.dept_code,                                                       ");
		sql.append(" a.dept_name,                                                       ");
		sql.append(" sum(DATEDIFF(day,b.start_date_time,b.stop_date_time)+1) use_time   ");
		sql.append(" INTO #use_time                                                     ");
		sql.append(" from #yf_kjy (nolock) a                                            ");
		sql.append(" inner join his_in_orders (nolock) b                                ");
		sql.append(" on a.patient_id=b.patient_id                                       ");
		sql.append(" and a.visit_id=b.visit_id                                          ");
		sql.append(" INNER JOIN dict_his_drug (nolock) c                                ");
		sql.append(" ON b.ORDER_CODE= c.drug_code                                       ");
		sql.append(" AND isnull(c.IS_ANTI_DRUG,'0') in ('1','2','4')                    ");
		sql.append(" GROUP BY a.dept_code,a.dept_name                                   ");
		// 预防使用抗菌药物时间不超过24小时手术例数
		sql.append(" select                                                      ");
		sql.append(" a.dept_code,                                                ");
		sql.append(" a.dept_name,                                                ");
		sql.append(" sum(a.oper_num) not24_num                                   ");
		sql.append(" INTO #not24_num                                             ");
		sql.append(" from #yf_kjy (nolock) a                                     ");
		sql.append(" inner join his_in_orders (nolock) b                         ");
		sql.append(" on a.patient_id=b.patient_id                                ");
		sql.append(" and a.visit_id=b.visit_id                                   ");
		sql.append(" INNER JOIN dict_his_drug (nolock) c                         ");
		sql.append(" ON b.ORDER_CODE= c.drug_code                                ");
		sql.append(" AND isnull(c.IS_ANTI_DRUG,'0') in ('1','2','4')             ");
		sql.append(" AND DATEDIFF(hh,b.start_date_time, b.stop_date_time) > 24   ");
		sql.append(" GROUP BY a.dept_code,a.dept_name                            ");

		sql.append(" SELECT                          ");
		sql.append(" dept_code,                      ");
		sql.append(" dept_name                       ");
		sql.append(" into #tt2                       ");
		sql.append(" FROM #tt                        ");
		sql.append(" GROUP BY dept_code,dept_name    ");

		// 组织查询
		sql.append(" SELECT            ");
		sql.append(" a.dept_code,                                                                ");
		sql.append(" a.dept_name,                                                                ");
		sql.append(" b.oper_patient_num,                                                         ");
		sql.append(" c.yf_kjy_patient_num,                                                       ");
		sql.append(" d.use_time,                                                                 ");
		sql.append(" e.time_num,                                                                 ");
		sql.append(" f.not24_num                                                                 ");
		sql.append(" FROM #tt2  (nolock) a                                                       ");
		sql.append(" LEFT JOIN #oper_patient_num (nolock) b     ON  a.dept_code = b.dept_code    ");
		sql.append(" LEFT JOIN #yf_kjy_patient_num (nolock) c   ON  a.dept_code = c.dept_code    ");
		sql.append(" LEFT JOIN #use_time (nolock) d             ON  a.dept_code = d.use_time     ");
		sql.append(" LEFT JOIN #time_num (nolock) e             ON  a.dept_code = e.dept_code    ");
		sql.append(" LEFT JOIN #not24_num (nolock) f            ON  a.dept_code = f.dept_code    ");

		sql.append(" DROP TABLE  #tt                 ");
		sql.append(" DROP TABLE  #tt2                ");
		sql.append(" DROP TABLE  #yf_kjy             ");
		sql.append(" DROP TABLE  #oper_patient_num   ");
		sql.append(" DROP TABLE  #yf_kjy_patient_num ");
		sql.append(" DROP TABLE  #use_time           ");
		sql.append(" DROP TABLE  #time_num           ");
		sql.append(" DROP TABLE  #not24_num          ");

		return executeQuery(sql.toString(), params);
	}

	public Result querykjyoper_doctor(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		// 手术病人
		sql.append(" set nocount on                               ");
		sql.append(" SELECT                                        ");
		sql.append("   *                                           ");
		sql.append(" INTO #tt                                      ");
		sql.append(" FROM                                          ");
		sql.append("   report_kjy_patient (nolock) a               ");
		sql.append(" WHERE                                         ");
		sql.append(" a.kjy_kind_num >0     and                         ");
		sql.append(" a.DISCHARGE_DATETIME between '" + params.get("start_time") + "' + ' 00:00:00' ");
		sql.append(" and '" + params.get("end_time") + "' + ' 23:59:59'                 ");

		String fiter = getFifter(params, "a");
		sql.append(fiter);

		// 预防用抗菌药
		sql.append(" SELECT                                        ");
		sql.append(" a.*                                           ");
		sql.append(" INTO #yf_kjy                                  ");
		sql.append(" FROM #tt  (nolock) a                          ");
		sql.append(" INNER JOIN his_in_patientvisit (nolock) b     ");
		sql.append(" ON a.PATIENT_ID =b.PATIENT_ID                 ");
		sql.append(" AND a.VISIT_ID =b.VISIT_ID                    ");
		sql.append(" WHERE b.MEDICATE_CAUSE = '1'                  ");
		// 手术例数
		sql.append(" SELECT                              ");
		sql.append(" attending_doctor_code,                          ");
		sql.append(" attending_doctor,                          ");
		sql.append(" sum(oper_num) as oper_patient_num   ");
		sql.append(" INTO #oper_patient_num              ");
		sql.append(" FROM #tt                            ");
		sql.append(" GROUP BY attending_doctor_code,attending_doctor        ");
		// 预防用抗菌药物手术例数
		sql.append(" SELECT                                ");
		sql.append(" attending_doctor_code,                            ");
		sql.append(" attending_doctor,                            ");
		sql.append(" sum(oper_num) as yf_kjy_patient_num   ");
		sql.append(" INTO #yf_kjy_patient_num              ");
		sql.append(" FROM #yf_kjy  (nolock)                ");
		sql.append(" GROUP BY attending_doctor_code,attending_doctor          ");
		// 手术前0.5- 2.0小时的给药例数
		sql.append(" SELECT                             ");
		sql.append(" attending_doctor_code,                         ");
		sql.append(" attending_doctor,                         ");
		sql.append(" COUNT(1) time_num                  ");
		sql.append(" INTO #time_num                     ");
		sql.append(" FROM #tt  (nolock)                 ");
		sql.append(" WHERE oper_before_drug = '0.5-2h'  ");
		sql.append(" GROUP BY attending_doctor_code,attending_doctor       ");
		// 预防用抗菌药物总天数
		sql.append(" select                                                             ");
		sql.append(" a.attending_doctor_code,                                                       ");
		sql.append(" a.attending_doctor,                                                       ");
		sql.append(" sum(DATEDIFF(day,b.start_date_time,b.stop_date_time)+1) use_time   ");
		sql.append(" INTO #use_time                                                     ");
		sql.append(" from #yf_kjy (nolock) a                                            ");
		sql.append(" inner join his_in_orders (nolock) b                                ");
		sql.append(" on a.patient_id=b.patient_id                                       ");
		sql.append(" and a.visit_id=b.visit_id                                          ");
		sql.append(" INNER JOIN dict_his_drug (nolock) c                                ");
		sql.append(" ON b.ORDER_CODE= c.drug_code                                       ");
		sql.append(" AND isnull(c.IS_ANTI_DRUG,'0') in ('1','2','4')                    ");
		sql.append(" GROUP BY a.attending_doctor_code,a.attending_doctor                                   ");
		// 预防使用抗菌药物时间不超过24小时手术例数
		sql.append(" select                                                      ");
		sql.append(" a.attending_doctor_code,                                                ");
		sql.append(" a.attending_doctor,                                                ");
		sql.append(" sum(a.oper_num) not24_num                                   ");
		sql.append(" INTO #not24_num                                             ");
		sql.append(" from #yf_kjy (nolock) a                                     ");
		sql.append(" inner join his_in_orders (nolock) b                         ");
		sql.append(" on a.patient_id=b.patient_id                                ");
		sql.append(" and a.visit_id=b.visit_id                                   ");
		sql.append(" INNER JOIN dict_his_drug (nolock) c                         ");
		sql.append(" ON b.ORDER_CODE= c.drug_code                                ");
		sql.append(" AND isnull(c.IS_ANTI_DRUG,'0') in ('1','2','4')             ");
		sql.append(" AND DATEDIFF(hh,b.start_date_time, b.stop_date_time) > 24   ");
		sql.append(" GROUP BY a.attending_doctor_code,a.attending_doctor                            ");

		sql.append(" SELECT                          ");
		sql.append(" attending_doctor_code,                      ");
		sql.append(" attending_doctor                       ");
		sql.append(" into #tt2                       ");
		sql.append(" FROM #tt                        ");
		sql.append(" GROUP BY attending_doctor_code,attending_doctor    ");

		// 组织查询
		sql.append(" SELECT            ");
		sql.append(" a.attending_doctor_code,                                                                ");
		sql.append(" a.attending_doctor,                                                                ");
		sql.append(" b.oper_patient_num,                                                         ");
		sql.append(" c.yf_kjy_patient_num,                                                       ");
		sql.append(" d.use_time,                                                                 ");
		sql.append(" e.time_num,                                                                 ");
		sql.append(" f.not24_num,                                                                 ");
		sql.append(" h.dept_name                                                              ");
		sql.append(" FROM #tt2  (nolock) a                                                       ");
		sql.append(" LEFT JOIN #oper_patient_num (nolock) b     ON  a.attending_doctor_code = b.attending_doctor_code    ");
		sql.append(" LEFT JOIN #yf_kjy_patient_num (nolock) c   ON  a.attending_doctor_code = c.attending_doctor_code    ");
		sql.append(" LEFT JOIN #use_time (nolock) d             ON  a.attending_doctor_code = d.use_time     ");
		sql.append(" LEFT JOIN #time_num (nolock) e             ON  a.attending_doctor_code = e.attending_doctor_code    ");
		sql.append(" LEFT JOIN #not24_num (nolock) f            ON  a.attending_doctor_code = f.attending_doctor_code    ");
		sql.append(" LEFT JOIN dict_his_users (nolock) g            ON  a.attending_doctor_code = g.user_id    ");
		sql.append(" LEFT JOIN dict_his_deptment (nolock) h            ON  g.user_dept = h.dept_code    ");
		
		
		sql.append(" DROP TABLE  #tt                 ");
		sql.append(" DROP TABLE  #tt2                ");
		sql.append(" DROP TABLE  #yf_kjy             ");
		sql.append(" DROP TABLE  #oper_patient_num   ");
		sql.append(" DROP TABLE  #yf_kjy_patient_num ");
		sql.append(" DROP TABLE  #use_time           ");
		sql.append(" DROP TABLE  #time_num           ");
		sql.append(" DROP TABLE  #not24_num          ");

		return executeQuery(sql.toString(), params);
	}

	/*
	 * public Result querykjyoper_doctor(Map<String, Object> params) throws
	 * Exception { StringBuffer sql = new StringBuffer(); StringBuffer sql2 =
	 * new StringBuffer(); sql.
	 * append("select *                                                       "
	 * ); sql.
	 * append("into #tt                                                       "
	 * ); sql.
	 * append("from report_kjy_patient (nolock) a                             "
	 * ); sql.
	 * append("where                                                          "
	 * ); sql.
	 * append("exists (select 1 from report_kjy_patient (nolock) b where      "
	 * ); sql.append("b.DISCHARGE_DATETIME between '" + params.get("start_time")
	 * + "' + ' 00:00:00' "); sql.append("and '" + params.get("end_time") +
	 * "' + ' 23:59:59'                 "); sql.
	 * append("and isnull(a.oper_message,'')<>''                              "
	 * ); sql.
	 * append("and b.patient_id=a.patient_id and b.visit_id=a.visit_id)       "
	 * ); String fiter = getFifter(params, "a"); sql.append(fiter);
	 * 
	 * sql.
	 * append("select                                                         "
	 * ); sql.
	 * append("a.dept_code,a.dept_name,a.ATTENDING_DOCTOR_code,a.ATTENDING_DOCTOR, "
	 * ); sql.
	 * append("sum(oper_num) as oper_patient_num,                                  "
	 * ); sql.
	 * append("(select count(1) from                                          "
	 * ); sql.
	 * append("(select tt.patient_id,tt.visit_id from #tt                     "
	 * ); sql.
	 * append("tt where exists(                                               "
	 * ); sql.
	 * append("select 1 from his_in_orders (nolock) b                         "
	 * ); sql.
	 * append("INNER JOIN dict_his_drug (nolock) drug                         "
	 * ); sql.
	 * append("ON b.ORDER_CODE= drug.drug_code                                "
	 * ); sql.
	 * append("WHERE tt.patient_id= b.patient_id                              "
	 * ); sql.
	 * append("AND tt.visit_id= b.visit_id                                    "
	 * ); sql.
	 * append("AND isnull(drug.IS_ANTI_DRUG,'0')<>'0'                         "
	 * ); sql.
	 * append("and exists (select 1 from his_in_diagnosis(nolock) hid         "
	 * ); sql.
	 * append("inner join dict_his_diagnosis(nolock) dhd                      "
	 * ); sql.
	 * append("on hid.DIAGNOSIS_CODE=dhd.diagnosis_CODE                       "
	 * ); sql.
	 * append("where hid.patient_id=tt.patient_id                             "
	 * ); sql.
	 * append("and hid.visit_id=tt.visit_id                                   "
	 * ); sql.
	 * append("and hid.DIAGNOSIS_DATE                                         "
	 * ); sql.
	 * append("< b.stop_date_time                                             "
	 * ); sql.
	 * append("and dhd.ganrlx='0'))                                           "
	 * ); sql.
	 * append("and tt.ATTENDING_DOCTOR_code=a.ATTENDING_DOCTOR_code           "
	 * ); sql.
	 * append("GROUP BY tt.patient_id,tt.visit_id) aa                         "
	 * ); sql.
	 * append(") as yf_kjy_patient_num,                                       "
	 * ); sql.
	 * append("                                                               "
	 * ); sql.
	 * append("(select count(1) from (select                                  "
	 * ); sql.
	 * append("tt.patient_id,tt.visit_id from #tt tt                          "
	 * ); sql.
	 * append("where tt.ATTENDING_DOCTOR_code=a.ATTENDING_DOCTOR_code         "
	 * ); sql.
	 * append("and tt.oper_before_drug = '0.5-2h'                             "
	 * ); sql.
	 * append("GROUP BY tt.patient_id,tt.visit_id) aa                         "
	 * ); sql.
	 * append(") as time_num,                                                 "
	 * ); sql.
	 * append("                                                               "
	 * ); sql.
	 * append("(                                                              "
	 * ); sql.
	 * append("select sum(DATEDIFF(day,b.start_date_time,                     "
	 * ); sql.
	 * append("b.stop_date_time)+1) from #tt tt                               "
	 * ); sql.
	 * append("inner join his_in_orders (nolock) b                            "
	 * ); sql.
	 * append("on tt.patient_id=b.patient_id                                  "
	 * ); sql.
	 * append("and tt.visit_id=b.visit_id                                     "
	 * ); sql.
	 * append("INNER JOIN dict_his_drug (nolock) drug                         "
	 * ); sql.
	 * append("ON b.ORDER_CODE= drug.drug_code                                "
	 * ); sql.
	 * append("AND isnull(drug.IS_ANTI_DRUG,'0')<>'0'                         "
	 * ); sql.
	 * append("where tt.ATTENDING_DOCTOR_code=a.ATTENDING_DOCTOR_code         "
	 * ); sql.
	 * append("and exists (select 1 from his_in_diagnosis(nolock) hid         "
	 * ); sql.
	 * append("inner join dict_his_diagnosis(nolock) dhd                      "
	 * ); sql.
	 * append("on hid.DIAGNOSIS_CODE=dhd.diagnosis_CODE                       "
	 * ); sql.
	 * append("where hid.patient_id=tt.patient_id                             "
	 * ); sql.
	 * append("and hid.visit_id=tt.visit_id                                   "
	 * ); sql.
	 * append("and hid.DIAGNOSIS_DATE                                         "
	 * ); sql.
	 * append("< b.stop_date_time                                             "
	 * ); sql.
	 * append("and dhd.ganrlx='0')                                            "
	 * ); sql.
	 * append(") as use_time,                                                 "
	 * ); sql.
	 * append("                                                               "
	 * ); sql.
	 * append("(select count(1) from                                          "
	 * ); sql.
	 * append("(select tt.patient_id,tt.visit_id from #tt tt                  "
	 * ); sql.
	 * append("inner join his_in_orders (nolock) b                            "
	 * ); sql.
	 * append("on tt.patient_id=b.patient_id                                  "
	 * ); sql.
	 * append("and tt.visit_id=b.visit_id                                     "
	 * ); sql.
	 * append("INNER JOIN dict_his_drug (nolock) drug                         "
	 * ); sql.
	 * append("ON b.ORDER_CODE= drug.drug_code                                "
	 * ); sql.
	 * append("AND isnull(drug.IS_ANTI_DRUG,'0')<>'0'                         "
	 * ); sql.
	 * append("where tt.ATTENDING_DOCTOR_code=a.ATTENDING_DOCTOR_code         "
	 * ); sql.
	 * append("and exists (select 1 from his_in_diagnosis(nolock) hid         "
	 * ); sql.
	 * append("inner join dict_his_diagnosis(nolock) dhd                      "
	 * ); sql.
	 * append("on hid.DIAGNOSIS_CODE=dhd.diagnosis_CODE                       "
	 * ); sql.
	 * append("where hid.patient_id=tt.patient_id                             "
	 * ); sql.
	 * append("and hid.visit_id=tt.visit_id                                   "
	 * ); sql.
	 * append("and hid.DIAGNOSIS_DATE                                         "
	 * ); sql.
	 * append("< b.stop_date_time                                             "
	 * ); sql.
	 * append("and dhd.ganrlx='0')                                            "
	 * ); sql.
	 * append("and DATEDIFF(hh,b.start_date_time,                             "
	 * ); sql.
	 * append("b.stop_date_time) > 24                                         "
	 * ); sql.
	 * append("group by tt.patient_id,tt.visit_id) aa                         "
	 * ); sql.
	 * append(") as not24_num                                                 "
	 * ); sql.
	 * append("                                                               "
	 * ); sql.
	 * append("from #tt a                                                     "
	 * ); sql.
	 * append("group by a.dept_code,a.dept_name,a.ATTENDING_DOCTOR_code,a.ATTENDING_DOCTOR "
	 * ); sql.
	 * append("                                                               "
	 * ); sql.
	 * append("drop table #tt                                                 "
	 * );
	 * 
	 * 
	 * Result executeQuery = executeQuery(sql.toString(), params); return
	 * executeQuery; }
	 */

	public Result querykjymj(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		sql.append("select a.*, ");
		sql.append("a.kjy_order_datas AS cf                                   ");
		sql.append("from report_prescription (nolock) a where drug_kind_num>0 ");
		sql.append(" and a.DISCHARGE_DATETIME between :start_time + ' 00:00:00'");
		sql.append(" and :end_time + ' 23:59:59' and a.group_id is not null ");

		sql2.append("select count(1) from report_prescription (nolock) a where drug_kind_num>0 ");
		sql2.append(" and a.DISCHARGE_DATETIME between :start_time + ' 00:00:00'");
		sql2.append(" and :end_time + ' 23:59:59' and a.group_id is not null ");

		String fiter = getFifter(params, "a");
		Result executeQuery = executeQueryLimit(sql.toString() + fiter, params, sql2.toString() + fiter);
		return executeQuery;
	}

	public Result querykjymj_dept(Map<String, Object> params) throws Exception {
		return executeQuery(get_querykjymjStr(params, "dept_code", "dept_name"), params);
	}

	public Result querykjyomj_doctor(Map<String, Object> params) throws Exception {
		return executeQuery(get_querykjymjStr(params, "doctor_code", "doctor_name"), params);
	}

	public Result query_dui(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                                                         ");
		sql.append("b.Usejlgg,                                                     ");
		sql.append("b.Use_dw,                                                      ");
		sql.append("b.drug_code,                                                   ");
		sql.append("b.property_toxi,                                               ");
		sql.append("b.drug_name,                                                   ");
		sql.append("b.jixing,                                                      ");
		sql.append("b.druggg,                                                      ");
		sql.append("b.ANTI_LEVEL,                                                  ");
		sql.append("b.ddd_value,                                                   ");
		sql.append("(case when a.UNITS = b.DRUG_unit and isnull(b.DDD_VALUE,0)<>0 then ");
		sql.append("a.AMOUNT/b.DDD_VALUE                                               ");
		sql.append("when a.UNITS = b.PACK_UNIT and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("a.AMOUNT*b.DDD_Conversion/b.DDD_VALUE                              ");
		sql.append("else 0 end) as DDDS,                                               ");
		sql.append("a.AMOUNT,                                                      ");
		sql.append("a.CHARGES,                                                     ");
		sql.append("a.dw,                                                          ");
		sql.append("a.PATIENT_ID,                                                  ");
		sql.append("a.visit_id                                                     ");
		sql.append("into #ttt                                                      ");
		sql.append("from V_his_in_drug_bill_detail_ALL (nolock) a                  ");
		sql.append("inner join dict_his_drug(nolock) b                             ");
		sql.append("on a.ITEM_CODE=b.drug_code                                     ");
		sql.append("where a.BILLING_DATE_TIME                                      ");
		sql.append("between :start_time +' 00:00:00' and :end_time +' 23:59:59'    ");
		sql.append("and a.AMOUNT>0 and CHARGES>0                                   ");
		sql.append("and isnull(b.IS_ANTI_DRUG,'')='1'                              ");
		if (!user().isSupperUser()) {
			// 增加表值函数, 查看权限信息
			sql.append(" and exists (select 1 from user_dept_permission('" + user().getId() + "') bb where a.ordering_dept=bb.dept_code ) ");
			sql.append(" and exists (select 1 from user_doctor_permission('" + user().getId() + "') bb where a.DOCTOR_NO=bb.no ) ");
		}
		if (!CommonFun.isNe(params.get("dept_code"))) {
			sql.append(" and exists (select 1 from split(:dept_code,',') ");
			sql.append(" sp where sp.col= a.ordering_dept) ");
		}
		if (!CommonFun.isNe(params.get("drug_type"))) {
			sql.append(" and exists (select 1 from split(:drug_type,',') ");
			sql.append(" sp where sp.col= b.property_toxi) ");
		}
		if (!CommonFun.isNe(params.get("kjy_drug_level"))) {
			sql.append(" and exists (select 1 from split(:kjy_drug_level,',') ");
			sql.append(" sp where sp.col= b.anti_level) ");
		}
		sql.append("                                                               ");
		sql.append("select                                                         ");
		sql.append("a.property_toxi,a.drug_name,a.jixing,                          ");
		sql.append("a.druggg,a.ANTI_LEVEL,                                    ");
		sql.append("sum(a.AMOUNT) num,                                             ");
		sql.append("sum(a.CHARGES) all_money,                                      ");
		sql.append("(select count(1) from (select drug_name,                       ");
		sql.append("jixing,druggg from #ttt                                        ");
		sql.append("where a.property_toxi=property_toxi                            ");
		sql.append("group by drug_name,jixing,druggg) dd) kind_drug_num,           ");
		sql.append("(select count(1) from (                                        ");
		sql.append("select patient_id,visit_id from #ttt                           ");
		sql.append("where a.drug_name=drug_name                                    ");
		sql.append("and a.jixing=jixing                                            ");
		sql.append("and a.druggg=druggg                                            ");
		sql.append("and a.property_toxi=property_toxi                              ");
		sql.append("group by patient_id,visit_id) dd                               ");
		sql.append(") sylc,                                                        ");
		// sql.append("a.ddd_value*sum(a.AMOUNT) ddds, ");
		sql.append("sum(a.DDDS) ddds,                                              ");
		sql.append("(select sum(CHARGES) from #ttt) all_drug_money,                ");
		sql.append("(select sum(dd.ts) from (                                      ");
		sql.append("select vhia.patient_id,                                        ");
		sql.append("vhia.visit_id,                                                 ");
		sql.append("vhia.ADMISSION_DATETIME,                                       ");
		sql.append("vhia.DISCHARGE_DATETIME,                                       ");
		sql.append("DATEDIFF(DAY,vhia.ADMISSION_DATETIME,                          ");
		sql.append("case when isnull(vhia.DISCHARGE_DATETIME,'')=''                ");
		sql.append("then getdate()                                                 ");
		sql.append("else vhia.DISCHARGE_DATETIME end)+1 ts                         ");
		sql.append("from #ttt tt                                                   ");
		sql.append("inner join V_his_in_patientvisit_all(nolock) vhia              ");
		sql.append("on vhia.patient_id=tt.patient_id                               ");
		sql.append("and vhia.visit_id=tt.visit_id                                  ");
		sql.append("group by vhia.patient_id,                                      ");
		sql.append("vhia.visit_id,vhia.ADMISSION_DATETIME,                         ");
		sql.append("vhia.DISCHARGE_DATETIME) dd                                    ");
		sql.append(") all_patient_ts                                               ");
		sql.append("from #ttt a                                                    ");
		sql.append("group by a.property_toxi,a.drug_name,a.jixing,                 ");
		sql.append("a.druggg,a.ANTI_LEVEL,a.Usejlgg                                ");
		sql.append("                                                               ");
		sql.append("drop table #ttt                                                ");
		return executeQuery(sql.toString(), params);
	}

	public Result query_dti(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT                                                ");
		sql.append("b.Usejlgg,                                            ");
		sql.append("b.Use_dw,                                             ");
		sql.append("b.drug_code,                                          ");
		sql.append("b.property_toxi,                                      ");
		sql.append("b.drug_name,                                          ");
		sql.append("b.jixing,                                             ");
		sql.append("b.druggg,                                             ");
		sql.append("b.ANTI_LEVEL,                                         ");
		sql.append("b.ddd_value,                                          ");
		sql.append("(case when a.UNITS = b.DRUG_unit and isnull(b.DDD_VALUE,0)<>0 then ");
		sql.append("a.AMOUNT/b.DDD_VALUE                                               ");
		sql.append("when a.UNITS = b.PACK_UNIT and isnull(b.DDD_VALUE,0)<>0 then       ");
		sql.append("a.AMOUNT*b.DDD_Conversion/b.DDD_VALUE                              ");
		sql.append("else 0 end) as DDDS,                                               ");
		sql.append("a.AMOUNT,                                             ");
		sql.append("a.CHARGES,                                            ");
		sql.append("a.dw,                                                 ");
		sql.append("a.PATIENT_ID,                                         ");
		sql.append("a.visit_id INTO #ttt                                  ");
		sql.append("FROM                                                  ");
		sql.append("V_his_in_drug_bill_detail_ALL (nolock) a              ");
		sql.append("INNER JOIN dict_his_drug (nolock) b                   ");
		sql.append("ON a.ITEM_CODE= b.drug_code                           ");
		sql.append("WHERE                                                 ");
		sql.append("a.BILLING_DATE_TIME                                   ");
		sql.append("BETWEEN :start_time + ' 00:00:00'                     ");
		sql.append("AND :end_time + ' 23:59:59'                           ");
		sql.append("AND a.AMOUNT> 0 AND CHARGES > 0                       ");
		sql.append("AND isnull( b.IS_ANTI_DRUG, '' ) = '1'                ");
		if (!user().isSupperUser()) {
			// 增加表值函数, 查看权限信息
			sql.append(" and exists (select 1 from user_dept_permission('" + user().getId() + "') bb where a.ordering_dept=bb.dept_code ) ");
			sql.append(" and exists (select 1 from user_doctor_permission('" + user().getId() + "') bb where a.DOCTOR_NO=bb.no ) ");
		}
		if (!CommonFun.isNe(params.get("dept_code"))) {
			sql.append(" and exists (select 1 from split(:dept_code,',') ");
			sql.append(" sp where sp.col= a.ordering_dept) ");
		}
		if (!CommonFun.isNe(params.get("drug_type"))) {
			sql.append(" and exists (select 1 from split(:drug_type,',') ");
			sql.append(" sp where sp.col= b.property_toxi) ");
		}
		if (!CommonFun.isNe(params.get("kjy_drug_level"))) {
			sql.append(" and exists (select 1 from split(:kjy_drug_level,',') ");
			sql.append(" sp where sp.col= b.anti_level) ");
		}
		sql.append("                                                      ");
		sql.append("SELECT                                                ");
		sql.append("a.property_toxi,                                      ");
		sql.append("SUM(a.CHARGES) all_money,                             ");
		sql.append("(SELECT COUNT(1) FROM                                 ");
		sql.append("(SELECT patient_id,visit_id FROM                      ");
		sql.append("#ttt WHERE                                            ");
		sql.append("a.property_toxi= property_toxi                        ");
		sql.append("GROUP BY                                              ");
		sql.append("patient_id,                                           ");
		sql.append("visit_id) dd) sylc,                                   ");
		sql.append("(select sum(dd.ddds) from (select sum(ddds) ddds      ");
		sql.append("from #ttt where a.property_toxi= property_toxi        ");
		sql.append("group by property_toxi,drug_name,jixing,              ");
		sql.append("druggg,dw,ANTI_LEVEL,Usejlgg) dd) ddds,               ");
		sql.append("(SELECT SUM(CHARGES) FROM #ttt) all_drug_money,       ");
		sql.append("(SELECT SUM(dd.ts) FROM                               ");
		sql.append("(SELECT                                               ");
		sql.append("vhia.patient_id,                                      ");
		sql.append("vhia.visit_id,                                        ");
		sql.append("vhia.ADMISSION_DATETIME,                              ");
		sql.append("vhia.DISCHARGE_DATETIME,                              ");
		sql.append("DATEDIFF(DAY, vhia.ADMISSION_DATETIME,                ");
		sql.append("CASE WHEN isnull( vhia.DISCHARGE_DATETIME, '' )=''    ");
		sql.append("THEN getdate() ELSE vhia.DISCHARGE_DATETIME END)+1 ts ");
		sql.append("FROM                                                  ");
		sql.append("#ttt tt                                               ");
		sql.append("INNER JOIN V_his_in_patientvisit_all (nolock) vhia    ");
		sql.append("ON vhia.patient_id= tt.patient_id                     ");
		sql.append("AND vhia.visit_id= tt.visit_id                        ");
		sql.append("GROUP BY                                              ");
		sql.append("vhia.patient_id,                                      ");
		sql.append("vhia.visit_id,                                        ");
		sql.append("vhia.ADMISSION_DATETIME,                              ");
		sql.append("vhia.DISCHARGE_DATETIME) dd) all_patient_ts           ");
		sql.append("FROM                                                  ");
		sql.append("#ttt a                                                ");
		sql.append("GROUP BY                                              ");
		sql.append("a.property_toxi                                       ");
		sql.append("                                                      ");
		sql.append("DROP TABLE #ttt                                       ");
		return executeQuery(sql.toString(), params);
	}

	public String get_queryKjyOutpUseStr(Map<String, Object> params, String doc_dept_code, String doc_dept_name) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,a.ATTENDING_DOCTOR_code as doctor_code,a.ATTENDING_DOCTOR as doctor_name,c.dept_name as doctor_dept_name into #ttt ");
		sql.append(" from report_kjy_patient (nolock) a  ");
		sql.append("left join dict_his_users b(nolock) on a.ATTENDING_DOCTOR_code=b.user_id ");
		sql.append("left join dict_his_deptment c(nolock) on b.user_dept=c.dept_code ");
		sql.append(" where a.DISCHARGE_DATETIME between :start_time + ' 00:00:00'");
		sql.append(" and :end_time + ' 23:59:59'");
		sql.append(getFifter(params, "a"));

		sql.append(" select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as patient_num into #patient_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id ");
		sql.append(" from #ttt a  ");
		sql.append(" where a.drug_kind_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as kjy_yizhu_num into #kjy_yizhu_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id ");
		sql.append(" from #ttt a  ");
		sql.append(" where drug_kind_num>0 and kjy_kind_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append(" select a." + doc_dept_code + ",a." + doc_dept_name + ",sum(a.all_money) patient_use_kjy_money into #patient_use_kjy_money from #ttt (nolock) a  ");
		sql.append(" where drug_kind_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as one_kjy_num into #one_kjy_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.one_drug_union_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as two_drug_union_num into #two_drug_union_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.two_drug_union_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as three_drug_union_num into #three_drug_union_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.three_drug_union_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as other_drug_union_num into #other_drug_union_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.other_drug_union_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as yufang_kjy_num into #yufang_kjy_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.yufang_kjy_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as zhiliao_kjy_num into #zhiliao_kjy_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.zhiliao_kjy_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as kjy_ts_yizhu_num into #kjy_ts_yizhu_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.ts_kjy_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as kjy_xz_yizhu_num into #kjy_xz_yizhu_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.kjy_fxz_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as kjy_fxz_yizhu_num into #kjy_fxz_yizhu_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.kjy_xz_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as kjy_jmzs_yizhu_num into #kjy_jmzs_yizhu_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.kjy_jmzs_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		/* ===================使用抗菌药物===================== */
		// 使用抗菌药物的患者数
		sql.append("  SELECT     a." + doc_dept_code + ",a." + doc_dept_name + ",     ");
		sql.append(" 	COUNT ( 1 ) AS kjy_patient_num                        ");
		sql.append(" 	INTO #kjy_patient 	                                  ");
		sql.append("     FROM (                                               ");
		sql.append(" SELECT  " + doc_dept_code + "," + doc_dept_name + ",             ");
		sql.append(" 	patient_id,                                           ");
		sql.append(" 	visit_id	                                          ");
		sql.append(" FROM                                                     ");
		sql.append(" 	#ttt ( nolock )                                       ");
		sql.append(" WHERE                                                    ");
		sql.append("  kjy_kind_num > 0                                        ");
		sql.append(" GROUP BY                                                 ");
		sql.append(" 	patient_id,                                           ");
		sql.append(" 	visit_id,                                             ");
		sql.append(" 	 " + doc_dept_code + "," + doc_dept_name + "  ) a             ");
		sql.append(" GROUP BY       a." + doc_dept_code + ",a." + doc_dept_name + "");

		// 病原学送检患者
		sql.append("  SELECT                                                              ");
		sql.append("  a.patient_id,                                                       ");
		sql.append("  a.visit_id                                                          ");
		sql.append("  INTO #byx_patient                                                   ");
		sql.append("  FROM HIS_IN_LAB_TEST_ITEMS  ( nolock ) a                           ");
		sql.append("  INNER  JOIN  #ttt  ( nolock ) b                                     ");
		sql.append("  ON a.patient_id = b.patient_id AND a.visit_id = b.visit_id          ");
		sql.append("	INNER JOIN dict_his_lab_test_items  (nolock)  c                 ");
		sql.append("	ON a.ITEM_CODE = c.ITEM_CODE                                    ");
		sql.append("  WHERE  c.is_byxjc = '1'                                           ");
		sql.append("  GROUP BY                                                            ");
		sql.append("  	a.patient_id,                                                     ");
		sql.append("  	a.visit_id                                                        ");

		// 使用抗菌药物且有病原学送检的病人数
		sql.append("  	SELECT  aa." + doc_dept_code + ",aa." + doc_dept_name + " ,                ");
		sql.append("  COUNT ( 1 ) AS kjy_byx_patient_num                                   ");
		sql.append("  INTO #kjy_byx_patient                                                ");
		sql.append("  FROM (                                                               ");
		sql.append("  SELECT   a." + doc_dept_code + ",a." + doc_dept_name + " ,               ");
		sql.append("  	a.patient_id,                                                      ");
		sql.append("  	a.visit_id	                                                       ");
		sql.append("  FROM                                                                 ");
		sql.append("  	#ttt ( nolock ) a                                                  ");
		sql.append("  	INNER JOIN  #byx_patient  ( nolock ) b                             ");
		sql.append("  	ON a.patient_id = b.patient_id AND a.visit_id = b.visit_id         ");
		sql.append("  WHERE                                                                ");
		sql.append("   a.kjy_kind_num > 0                                                  ");
		sql.append("  GROUP BY                                                             ");
		sql.append("  	a.patient_id,                                                      ");
		sql.append("  	a.visit_id,                                                        ");
		sql.append("  	a." + doc_dept_code + ",a." + doc_dept_name + "  ) aa                    ");
		sql.append("  GROUP BY  aa." + doc_dept_code + ",aa." + doc_dept_name + "                  ");

		// 使用抗菌药物的手术患者数
		sql.append(" SELECT  b." + doc_dept_code + ",b." + doc_dept_name + ",");
		sql.append(" 	COUNT ( 1 ) AS kjy_oper_patient_num            ");
		sql.append(" 	INTO #kjy_oper_patient 	                       ");
		sql.append("    FROM (                                         ");
		sql.append(" 	SELECT a." + doc_dept_code + ",a." + doc_dept_name + ",");
		sql.append(" 	a.patient_id,                                  ");
		sql.append(" 	a.visit_id                                     ");
		sql.append(" FROM                                              ");
		sql.append(" 	#ttt ( nolock ) a                              ");
		sql.append(" WHERE                                             ");
		sql.append("     kjy_kind_num > 0                              ");
		sql.append(" 	AND oper_num > 0                               ");
		sql.append(" GROUP BY                                          ");
		sql.append(" 	a.patient_id,                                  ");
		sql.append(" 	a.visit_id,                                    ");
		sql.append(" 	a." + doc_dept_code + ",a." + doc_dept_name + " ) b  ");
		sql.append(" GROUP BY b." + doc_dept_code + ",b." + doc_dept_name + " ");

		// 使用抗菌药物且有病原学送检的手术患者数
		sql.append("SELECT  aa." + doc_dept_code + ",aa." + doc_dept_name + ",                           ");
		sql.append("	COUNT ( 1 ) AS kjy_byx_oper_patient_num                             ");
		sql.append("	into #kjy_byx_oper_patient                                          ");
		sql.append("FROM (SELECT                                                            ");
		sql.append("	a." + doc_dept_code + ",a." + doc_dept_name + ",                          ");
		sql.append("	a.patient_id,                                                       ");
		sql.append("	a.visit_id                                                          ");
		sql.append("FROM                                                                    ");
		sql.append("	#ttt  ( nolock ) a                                                  ");
		sql.append("	INNER JOIN  #byx_patient  ( nolock ) b                              ");
		sql.append("	ON a.patient_id = b.patient_id AND a.visit_id = b.visit_id          ");
		sql.append("WHERE                                                                   ");
		sql.append("   a.kjy_kind_num > 0                                                   ");
		sql.append("	AND a.oper_num > 0                                                  ");
		sql.append("GROUP BY                                                                ");
		sql.append("	a.patient_id,                                                       ");
		sql.append("	a.visit_id,                                                         ");
		sql.append("	a." + doc_dept_code + ",a." + doc_dept_name + " )  aa                    ");
		sql.append("GROUP BY  aa." + doc_dept_code + ",aa." + doc_dept_name + "                     ");

		/* ===================使用抗菌药物END===================== */

		/* ===================使用限制抗菌药物===================== */
		// 使用限制抗菌药物的患者数
		sql.append("  SELECT     a." + doc_dept_code + ",a." + doc_dept_name + ",     ");
		sql.append(" 	COUNT ( 1 ) AS xz_kjy_patient_num                        ");
		sql.append(" 	INTO #xz_kjy_patient 	                                  ");
		sql.append("     FROM (                                               ");
		sql.append(" SELECT  " + doc_dept_code + "," + doc_dept_name + ",             ");
		sql.append(" 	patient_id,                                           ");
		sql.append(" 	visit_id	                                          ");
		sql.append(" FROM                                                     ");
		sql.append(" 	#ttt ( nolock )                                       ");
		sql.append(" WHERE                                                    ");
		sql.append("  kjy_xz_num > 0                                        ");
		sql.append(" GROUP BY                                                 ");
		sql.append(" 	patient_id,                                           ");
		sql.append(" 	visit_id,                                             ");
		sql.append(" 	 " + doc_dept_code + "," + doc_dept_name + "  ) a             ");
		sql.append(" GROUP BY       a." + doc_dept_code + ",a." + doc_dept_name + "");

		// 使用限制抗菌药物且有病原学送检的病人数
		sql.append("  	SELECT  aa." + doc_dept_code + ",aa." + doc_dept_name + " ,                ");
		sql.append("  COUNT ( 1 ) AS xz_kjy_byx_patient_num                                   ");
		sql.append("  INTO #xz_kjy_byx_patient                                                ");
		sql.append("  FROM (                                                               ");
		sql.append("  SELECT   a." + doc_dept_code + ",a." + doc_dept_name + " ,               ");
		sql.append("  	a.patient_id,                                                      ");
		sql.append("  	a.visit_id	                                                       ");
		sql.append("  FROM                                                                 ");
		sql.append("  	#ttt ( nolock ) a                                                  ");
		sql.append("  	INNER JOIN  #byx_patient  ( nolock ) b                             ");
		sql.append("  	ON a.patient_id = b.patient_id AND a.visit_id = b.visit_id         ");
		sql.append("  WHERE                                                                ");
		sql.append("   a.kjy_xz_num > 0                                                  ");
		sql.append("  GROUP BY                                                             ");
		sql.append("  	a.patient_id,                                                      ");
		sql.append("  	a.visit_id,                                                        ");
		sql.append("  	a." + doc_dept_code + ",a." + doc_dept_name + "  ) aa                    ");
		sql.append("  GROUP BY  aa." + doc_dept_code + ",aa." + doc_dept_name + "                  ");

		// 使用限制抗菌药物的手术患者数
		sql.append(" SELECT  b." + doc_dept_code + ",b." + doc_dept_name + ",");
		sql.append(" 	COUNT ( 1 ) AS xz_kjy_oper_patient_num            ");
		sql.append(" 	INTO #xz_kjy_oper_patient 	                       ");
		sql.append("    FROM (                                         ");
		sql.append(" 	SELECT a." + doc_dept_code + ",a." + doc_dept_name + ",");
		sql.append(" 	a.patient_id,                                  ");
		sql.append(" 	a.visit_id                                     ");
		sql.append(" FROM                                              ");
		sql.append(" 	#ttt ( nolock ) a                              ");
		sql.append(" WHERE                                             ");
		sql.append("     kjy_xz_num > 0                              ");
		sql.append(" 	AND oper_num > 0                               ");
		sql.append(" GROUP BY                                          ");
		sql.append(" 	a.patient_id,                                  ");
		sql.append(" 	a.visit_id,                                    ");
		sql.append(" 	a." + doc_dept_code + ",a." + doc_dept_name + " ) b  ");
		sql.append(" GROUP BY b." + doc_dept_code + ",b." + doc_dept_name + " ");

		// 使用限制抗菌药物且有病原学送检的手术患者数
		sql.append("SELECT  aa." + doc_dept_code + ",aa." + doc_dept_name + ",                       ");
		sql.append("	COUNT ( 1 ) AS xz_kjy_byx_oper_patient_num                          ");
		sql.append("	into #xz_kjy_byx_oper_patient                                       ");
		sql.append("FROM (SELECT                                                            ");
		sql.append("	a." + doc_dept_code + ",a." + doc_dept_name + ",                          ");
		sql.append("	a.patient_id,                                                       ");
		sql.append("	a.visit_id                                                          ");
		sql.append("FROM                                                                    ");
		sql.append("	#ttt  ( nolock ) a                                                  ");
		sql.append("	INNER JOIN  #byx_patient  ( nolock ) b                              ");
		sql.append("	ON a.patient_id = b.patient_id AND a.visit_id = b.visit_id          ");
		sql.append("WHERE                                                                   ");
		sql.append("   a.kjy_xz_num > 0                                                   ");
		sql.append("	AND a.oper_num > 0                                                  ");
		sql.append("GROUP BY                                                                ");
		sql.append("	a.patient_id,                                                       ");
		sql.append("	a.visit_id,                                                         ");
		sql.append("	a." + doc_dept_code + ",a." + doc_dept_name + " )  aa                    ");
		sql.append("GROUP BY  aa." + doc_dept_code + ",aa." + doc_dept_name + "                     ");

		/* ===================使用限制抗菌药物END===================== */

		/* ===================使用特殊抗菌药物===================== */
		// 使用特殊抗菌药物的患者数
		sql.append("  SELECT     a." + doc_dept_code + ",a." + doc_dept_name + ",     ");
		sql.append(" 	COUNT ( 1 ) AS ts_kjy_patient_num                        ");
		sql.append(" 	INTO #ts_kjy_patient 	                                  ");
		sql.append("     FROM (                                               ");
		sql.append(" SELECT  " + doc_dept_code + "," + doc_dept_name + ",             ");
		sql.append(" 	patient_id,                                           ");
		sql.append(" 	visit_id	                                          ");
		sql.append(" FROM                                                     ");
		sql.append(" 	#ttt ( nolock )                                       ");
		sql.append(" WHERE                                                    ");
		sql.append("  ts_kjy_num > 0                                        ");
		sql.append(" GROUP BY                                                 ");
		sql.append(" 	patient_id,                                           ");
		sql.append(" 	visit_id,                                             ");
		sql.append(" 	 " + doc_dept_code + "," + doc_dept_name + "  ) a             ");
		sql.append(" GROUP BY       a." + doc_dept_code + ",a." + doc_dept_name + "");

		// 使用限制抗菌药物且有病原学送检的病人数
		sql.append("  	SELECT  aa." + doc_dept_code + ",aa." + doc_dept_name + " ,                ");
		sql.append("  COUNT ( 1 ) AS ts_kjy_byx_patient_num                                   ");
		sql.append("  INTO #ts_kjy_byx_patient                                                ");
		sql.append("  FROM (                                                               ");
		sql.append("  SELECT   a." + doc_dept_code + ",a." + doc_dept_name + " ,               ");
		sql.append("  	a.patient_id,                                                      ");
		sql.append("  	a.visit_id	                                                       ");
		sql.append("  FROM                                                                 ");
		sql.append("  	#ttt ( nolock ) a                                                  ");
		sql.append("  	INNER JOIN  #byx_patient  ( nolock ) b                             ");
		sql.append("  	ON a.patient_id = b.patient_id AND a.visit_id = b.visit_id         ");
		sql.append("  WHERE                                                                ");
		sql.append("   a.ts_kjy_num > 0                                                  ");
		sql.append("  GROUP BY                                                             ");
		sql.append("  	a.patient_id,                                                      ");
		sql.append("  	a.visit_id,                                                        ");
		sql.append("  	a." + doc_dept_code + ",a." + doc_dept_name + "  ) aa                    ");
		sql.append("  GROUP BY  aa." + doc_dept_code + ",aa." + doc_dept_name + "                  ");

		// 使用限制抗菌药物的手术患者数
		sql.append(" SELECT  b." + doc_dept_code + ",b." + doc_dept_name + ",");
		sql.append(" 	COUNT ( 1 ) AS ts_kjy_oper_patient_num            ");
		sql.append(" 	INTO #ts_kjy_oper_patient 	                       ");
		sql.append("    FROM (                                         ");
		sql.append(" 	SELECT a." + doc_dept_code + ",a." + doc_dept_name + ",");
		sql.append(" 	a.patient_id,                                  ");
		sql.append(" 	a.visit_id                                     ");
		sql.append(" FROM                                              ");
		sql.append(" 	#ttt ( nolock ) a                              ");
		sql.append(" WHERE                                             ");
		sql.append("     ts_kjy_num > 0                              ");
		sql.append(" 	AND oper_num > 0                               ");
		sql.append(" GROUP BY                                          ");
		sql.append(" 	a.patient_id,                                  ");
		sql.append(" 	a.visit_id,                                    ");
		sql.append(" 	a." + doc_dept_code + ",a." + doc_dept_name + " ) b  ");
		sql.append(" GROUP BY b." + doc_dept_code + ",b." + doc_dept_name + " ");

		// 使用限制抗菌药物且有病原学送检的手术患者数
		sql.append("SELECT  aa." + doc_dept_code + ",aa." + doc_dept_name + ",                           ");
		sql.append("	COUNT ( 1 ) AS ts_kjy_byx_oper_patient_num                             ");
		sql.append("	into #ts_kjy_byx_oper_patient                                          ");
		sql.append("FROM (SELECT                                                            ");
		sql.append("	a." + doc_dept_code + ",a." + doc_dept_name + ",                          ");
		sql.append("	a.patient_id,                                                       ");
		sql.append("	a.visit_id                                                          ");
		sql.append("FROM                                                                    ");
		sql.append("	#ttt  ( nolock ) a                                                  ");
		sql.append("	INNER JOIN  #byx_patient  ( nolock ) b                              ");
		sql.append("	ON a.patient_id = b.patient_id AND a.visit_id = b.visit_id          ");
		sql.append("WHERE                                                                   ");
		sql.append("   a.ts_kjy_num > 0                                                   ");
		sql.append("	AND a.oper_num > 0                                                  ");
		sql.append("GROUP BY                                                                ");
		sql.append("	a.patient_id,                                                       ");
		sql.append("	a.visit_id,                                                         ");
		sql.append("	a." + doc_dept_code + ",a." + doc_dept_name + " )  aa                    ");
		sql.append("GROUP BY  aa." + doc_dept_code + ",aa." + doc_dept_name + "                     ");

		/* ===================使用限制抗菌药物END===================== */

		/* ======================组织数据查询========================== */
		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ", ");
		sql.append("sum(a.drug_kind_num) as all_drug_pz_num, ");
		sql.append("b.patient_num,");
		sql.append("sum(a.kjy_kind_num) as all_drug_kjy_pz_num, ");
		sql.append("c.kjy_yizhu_num, ");
		sql.append("sum(a.drug_money) as all_drug_money, ");
		sql.append("sum(a.kjy_money) as kjy_all_drug_money,");
		sql.append("d.patient_use_kjy_money,");
		sql.append("e.one_kjy_num,");
		sql.append("f.two_drug_union_num,");
		sql.append("g.three_drug_union_num,");
		sql.append("h.other_drug_union_num,");
		sql.append("i.yufang_kjy_num,");
		sql.append("j.zhiliao_kjy_num,");
		sql.append("sum(a.yufang_kjy_money) as yufang_kjy_money,");
		sql.append("sum(a.zhiliao_kjy_money) as zhiliao_kjy_money,");
		sql.append("a1.kjy_ts_yizhu_num,");
		sql.append("a2.kjy_xz_yizhu_num,");
		sql.append("a3.kjy_fxz_yizhu_num,");
		sql.append("a4.kjy_jmzs_yizhu_num, ");

		sql.append(" by1.kjy_patient_num,             ");
		sql.append(" by2.kjy_byx_patient_num,         ");
		sql.append(" by3.kjy_oper_patient_num,        ");
		sql.append(" by4.kjy_byx_oper_patient_num ,    ");

		sql.append(" by_xz1.xz_kjy_patient_num,             ");
		sql.append(" by_xz2.xz_kjy_byx_patient_num,         ");
		sql.append(" by_xz3.xz_kjy_oper_patient_num,        ");
		sql.append(" by_xz4.xz_kjy_byx_oper_patient_num ,    ");

		sql.append(" by_ts1.ts_kjy_patient_num,             ");
		sql.append(" by_ts2.ts_kjy_byx_patient_num,         ");
		sql.append(" by_ts3.ts_kjy_oper_patient_num,        ");
		sql.append(" by_ts4.ts_kjy_byx_oper_patient_num     ");

		sql.append("from #ttt a  ");
		sql.append("left join #patient_num b on a." + doc_dept_code + "=b." + doc_dept_code + " ");
		sql.append("left join #kjy_yizhu_num c on a." + doc_dept_code + "=c." + doc_dept_code + " ");
		sql.append("left join #patient_use_kjy_money d on a." + doc_dept_code + "=d." + doc_dept_code + " ");
		sql.append("left join #one_kjy_num e on a." + doc_dept_code + "=e." + doc_dept_code + " ");
		sql.append("left join #two_drug_union_num f on a." + doc_dept_code + "=f." + doc_dept_code + " ");
		sql.append("left join #three_drug_union_num g on a." + doc_dept_code + "=g." + doc_dept_code + " ");
		sql.append("left join #other_drug_union_num h on a." + doc_dept_code + "=h." + doc_dept_code + " ");
		sql.append("left join #yufang_kjy_num i on a." + doc_dept_code + "=i." + doc_dept_code + " ");
		sql.append("left join #zhiliao_kjy_num j on a." + doc_dept_code + "=j." + doc_dept_code + " ");
		sql.append("left join #kjy_ts_yizhu_num a1 on a." + doc_dept_code + "=a1." + doc_dept_code + " ");
		sql.append("left join #kjy_xz_yizhu_num a2 on a." + doc_dept_code + "=a2." + doc_dept_code + " ");
		sql.append("left join #kjy_fxz_yizhu_num a3 on a." + doc_dept_code + "=a3." + doc_dept_code + " ");
		sql.append("left join #kjy_jmzs_yizhu_num a4 on a." + doc_dept_code + "=a4." + doc_dept_code + " ");

		sql.append(" LEFT JOIN #kjy_patient           by1    ON a." + doc_dept_code + "= by1." + doc_dept_code + "   ");
		sql.append(" LEFT JOIN #kjy_byx_patient       by2    ON a." + doc_dept_code + "= by2." + doc_dept_code + "   ");
		sql.append(" LEFT JOIN #kjy_oper_patient      by3    ON a." + doc_dept_code + "= by3." + doc_dept_code + "   ");
		sql.append(" LEFT JOIN #kjy_byx_oper_patient  by4    ON a." + doc_dept_code + "= by4." + doc_dept_code + "   ");

		sql.append(" LEFT JOIN #xz_kjy_patient           by_xz1    ON a." + doc_dept_code + "= by_xz1." + doc_dept_code + "   ");
		sql.append(" LEFT JOIN #xz_kjy_byx_patient       by_xz2    ON a." + doc_dept_code + "= by_xz2." + doc_dept_code + "   ");
		sql.append(" LEFT JOIN #xz_kjy_oper_patient      by_xz3    ON a." + doc_dept_code + "= by_xz3." + doc_dept_code + "   ");
		sql.append(" LEFT JOIN #xz_kjy_byx_oper_patient  by_xz4    ON a." + doc_dept_code + "= by_xz4." + doc_dept_code + "   ");

		sql.append(" LEFT JOIN #ts_kjy_patient           by_ts1    ON a." + doc_dept_code + "= by_ts1." + doc_dept_code + "   ");
		sql.append(" LEFT JOIN #ts_kjy_byx_patient       by_ts2    ON a." + doc_dept_code + "= by_ts2." + doc_dept_code + "   ");
		sql.append(" LEFT JOIN #ts_kjy_oper_patient      by_ts3    ON a." + doc_dept_code + "= by_ts3." + doc_dept_code + "   ");
		sql.append(" LEFT JOIN #ts_kjy_byx_oper_patient  by_ts4    ON a." + doc_dept_code + "= by_ts4." + doc_dept_code + "   ");

		sql.append(" where drug_kind_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",b.patient_num," + "c.kjy_yizhu_num,d.patient_use_kjy_money,e.one_kjy_num,"
				+ "f.two_drug_union_num,g.three_drug_union_num,h.other_drug_union_num," + "i.yufang_kjy_num,j.zhiliao_kjy_num,a1.kjy_ts_yizhu_num,"
				+ "a2.kjy_xz_yizhu_num,a3.kjy_fxz_yizhu_num,a4.kjy_jmzs_yizhu_num ,");

		sql.append(" by1.kjy_patient_num,             ");
		sql.append(" by2.kjy_byx_patient_num,         ");
		sql.append(" by3.kjy_oper_patient_num,        ");
		sql.append(" by4.kjy_byx_oper_patient_num,    ");

		sql.append(" by_xz1.xz_kjy_patient_num,             ");
		sql.append(" by_xz2.xz_kjy_byx_patient_num,         ");
		sql.append(" by_xz3.xz_kjy_oper_patient_num,        ");
		sql.append(" by_xz4.xz_kjy_byx_oper_patient_num,     ");

		sql.append(" by_ts1.ts_kjy_patient_num,             ");
		sql.append(" by_ts2.ts_kjy_byx_patient_num,         ");
		sql.append(" by_ts3.ts_kjy_oper_patient_num,        ");
		sql.append(" by_ts4.ts_kjy_byx_oper_patient_num     ");

		sql.append(" drop table #ttt ");
		sql.append(" drop table #kjy_yizhu_num ");
		sql.append(" drop table #patient_use_kjy_money ");
		sql.append(" drop table #one_kjy_num ");
		sql.append(" drop table #two_drug_union_num ");
		sql.append(" drop table #three_drug_union_num ");
		sql.append(" drop table #other_drug_union_num ");
		sql.append(" drop table #yufang_kjy_num ");
		sql.append(" drop table #zhiliao_kjy_num ");
		sql.append(" drop table #kjy_ts_yizhu_num ");
		sql.append(" drop table #kjy_xz_yizhu_num ");
		sql.append(" drop table #kjy_fxz_yizhu_num ");
		sql.append(" drop table #kjy_jmzs_yizhu_num ");
		sql.append(" DROP TABLE  #patient_num             ");
		sql.append(" DROP TABLE  #byx_patient              ");

		sql.append(" DROP TABLE  #kjy_patient             ");
		sql.append(" DROP TABLE  #kjy_byx_patient         ");
		sql.append(" DROP TABLE  #kjy_oper_patient        ");
		sql.append(" DROP TABLE  #kjy_byx_oper_patient    ");

		sql.append(" DROP TABLE  #xz_kjy_patient             ");
		sql.append(" DROP TABLE  #xz_kjy_byx_patient         ");
		sql.append(" DROP TABLE  #xz_kjy_oper_patient        ");
		sql.append(" DROP TABLE  #xz_kjy_byx_oper_patient    ");

		sql.append(" DROP TABLE  #ts_kjy_patient             ");
		sql.append(" DROP TABLE  #ts_kjy_byx_patient         ");
		sql.append(" DROP TABLE  #ts_kjy_oper_patient        ");
		sql.append(" DROP TABLE  #ts_kjy_byx_oper_patient    ");

		System.out.println(sql.toString());
		return sql.toString();
	}

	public String get_querykjymjStr(Map<String, Object> params, String doc_dept_code, String doc_dept_name) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,a.ATTENDING_DOCTOR_code as doctor_code,a.ATTENDING_DOCTOR as doctor_name,c.dept_name as doctor_dept_name into #ttt ");
		sql.append("from report_prescription a (nolock) ");
		sql.append("left join dict_his_users b(nolock) on a.ATTENDING_DOCTOR_code=b.user_id ");
		sql.append("left join dict_his_deptment c(nolock) on b.user_dept=c.dept_code ");
		sql.append(" where drug_kind_num>0 ");
		sql.append(" and a.DISCHARGE_DATETIME between :start_time + ' 00:00:00'");
		sql.append(" and :end_time + ' 23:59:59' and a.group_id is not null ");
		sql.append(getFifter(params, "a"));

		sql.append(" select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as patient_num into #patient_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id,a.group_id ");
		sql.append(" from #ttt a  ");
		sql.append(" where a.drug_kind_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id,a.group_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as kjy_yizhu_num into #kjy_yizhu_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id,a.group_id ");
		sql.append(" from #ttt a  ");
		sql.append(" where drug_kind_num>0 and kjy_kind_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id,a.group_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append(" select a." + doc_dept_code + ",a." + doc_dept_name + ",sum(a.all_money) patient_use_kjy_money into #patient_use_kjy_money from #ttt (nolock) a  ");
		sql.append(" where drug_kind_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as one_kjy_num into #one_kjy_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id,a.group_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.one_drug_union_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id,a.group_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as two_drug_union_num into #two_drug_union_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id,a.group_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.two_drug_union_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id,a.group_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as three_drug_union_num into #three_drug_union_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id,a.group_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.three_drug_union_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id,a.group_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as other_drug_union_num into #other_drug_union_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id,a.group_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.other_drug_union_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id,a.group_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as yufang_kjy_num into #yufang_kjy_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id,a.group_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.yufang_kjy_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id,a.group_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as zhiliao_kjy_num into #zhiliao_kjy_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id,a.group_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.zhiliao_kjy_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id,a.group_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as kjy_ts_yizhu_num into #kjy_ts_yizhu_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id,a.group_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.ts_kjy_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id,a.group_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as kjy_xz_yizhu_num into #kjy_xz_yizhu_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id,a.group_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.kjy_fxz_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id,a.group_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as kjy_fxz_yizhu_num into #kjy_fxz_yizhu_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id,a.group_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.kjy_xz_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id,a.group_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ",count(1) as kjy_jmzs_yizhu_num into #kjy_jmzs_yizhu_num from (select a." + doc_dept_code + ",a." + doc_dept_name
				+ ",a.patient_id,a.visit_id,a.group_id ");
		sql.append(" from #ttt (nolock) a  ");
		sql.append(" where a.kjy_jmzs_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",a.patient_id,a.visit_id,a.group_id) a ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + " ");

		sql.append("select a." + doc_dept_code + ",a." + doc_dept_name + ", ");
		sql.append("sum(a.drug_kind_num) as all_drug_pz_num, ");
		sql.append("b.patient_num,");
		sql.append("sum(a.kjy_kind_num) as all_drug_kjy_pz_num, ");
		sql.append("c.kjy_yizhu_num, ");
		sql.append("sum(a.drug_money) as all_drug_money, ");
		sql.append("sum(a.kjy_money) as kjy_all_drug_money,");
		sql.append("d.patient_use_kjy_money,");
		sql.append("e.one_kjy_num,");
		sql.append("f.two_drug_union_num,");
		sql.append("g.three_drug_union_num,");
		sql.append("h.other_drug_union_num,");
		sql.append("i.yufang_kjy_num,");
		sql.append("j.zhiliao_kjy_num,");
		sql.append("sum(a.yufang_kjy_money) as yufang_kjy_money,");
		sql.append("sum(a.zhiliao_kjy_money) as zhiliao_kjy_money,");
		sql.append("a1.kjy_ts_yizhu_num,");
		sql.append("a2.kjy_xz_yizhu_num,");
		sql.append("a3.kjy_fxz_yizhu_num,");
		sql.append("a4.kjy_jmzs_yizhu_num ");
		sql.append("from #ttt a  ");
		sql.append("left join #patient_num b on a." + doc_dept_code + "=b." + doc_dept_code + " ");
		sql.append("left join #kjy_yizhu_num c on a." + doc_dept_code + "=c." + doc_dept_code + " ");
		sql.append("left join #patient_use_kjy_money d on a." + doc_dept_code + "=d." + doc_dept_code + " ");
		sql.append("left join #one_kjy_num e on a." + doc_dept_code + "=e." + doc_dept_code + " ");
		sql.append("left join #two_drug_union_num f on a." + doc_dept_code + "=f." + doc_dept_code + " ");
		sql.append("left join #three_drug_union_num g on a." + doc_dept_code + "=g." + doc_dept_code + " ");
		sql.append("left join #other_drug_union_num h on a." + doc_dept_code + "=h." + doc_dept_code + " ");
		sql.append("left join #yufang_kjy_num i on a." + doc_dept_code + "=i." + doc_dept_code + " ");
		sql.append("left join #zhiliao_kjy_num j on a." + doc_dept_code + "=j." + doc_dept_code + " ");
		sql.append("left join #kjy_ts_yizhu_num a1 on a." + doc_dept_code + "=a1." + doc_dept_code + " ");
		sql.append("left join #kjy_xz_yizhu_num a2 on a." + doc_dept_code + "=a2." + doc_dept_code + " ");
		sql.append("left join #kjy_fxz_yizhu_num a3 on a." + doc_dept_code + "=a3." + doc_dept_code + " ");
		sql.append("left join #kjy_jmzs_yizhu_num a4 on a." + doc_dept_code + "=a4." + doc_dept_code + " ");
		sql.append(" where drug_kind_num>0 ");
		sql.append(" group by a." + doc_dept_code + ",a." + doc_dept_name + ",b.patient_num," + "c.kjy_yizhu_num,d.patient_use_kjy_money,e.one_kjy_num,"
				+ "f.two_drug_union_num,g.three_drug_union_num,h.other_drug_union_num," + "i.yufang_kjy_num,j.zhiliao_kjy_num,a1.kjy_ts_yizhu_num,"
				+ "a2.kjy_xz_yizhu_num,a3.kjy_fxz_yizhu_num,a4.kjy_jmzs_yizhu_num ");

		sql.append(" drop table #ttt ");
		sql.append(" drop table #kjy_yizhu_num ");
		sql.append(" drop table #patient_use_kjy_money ");
		sql.append(" drop table #one_kjy_num ");
		sql.append(" drop table #two_drug_union_num ");
		sql.append(" drop table #three_drug_union_num ");
		sql.append(" drop table #other_drug_union_num ");
		sql.append(" drop table #yufang_kjy_num ");
		sql.append(" drop table #zhiliao_kjy_num ");
		sql.append(" drop table #kjy_ts_yizhu_num ");
		sql.append(" drop table #kjy_xz_yizhu_num ");
		sql.append(" drop table #kjy_fxz_yizhu_num ");
		sql.append(" drop table #kjy_jmzs_yizhu_num ");
		return sql.toString();
	}

	public String getFifter(Map<String, Object> map, String a) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.putAll(map);
		StringBuffer sql = new StringBuffer();

		if (!user().isSupperUser()) {
			// 增加表值函数, 查看权限信息
			// sql.append(" and exists (select 1 from
			// user_dept_permission('"+user().getId()+"') bb where
			// "+a+".dept_code=bb.dept_code ) ");
			// sql.append(" and exists (select 1 from
			// user_doctor_permission('"+user().getId()+"') bb where
			// "+a+".ATTENDING_DOCTOR_code=bb.no ) ");
		}

		if (!CommonFun.isNe(params.get("dept_code"))) {
			sql.append(" and exists (select 1 from split(:dept_code,',') ");
			sql.append(" sp where sp.col= " + a + ".dept_code) ");
		}
		if (!CommonFun.isNe(params.get("doctor_name"))) {
			sql.append(" and exists (select 1 from split(:doctor_name,',') ");
			sql.append(" sp where sp.col= " + a + ".attending_doctor) ");
		}
		if (!CommonFun.isNe(params.get("diagnosis_name"))) {
			sql.append(" and EXISTS(select 1 from ");
			sql.append("his_in_diagnosis zd(nolock) ");
			sql.append("where zd.patient_id=" + a + ".patient_id ");
			sql.append("and zd.visit_id=" + a + ".visit_id ");
			if (!CommonFun.isNe(params.get("diagnosis_name"))) {
				sql.append(" and EXISTS (select 1 from (  ");
				sql.append("select col from split(:diagnosis_name,',')) sp ");
				sql.append("where zd.DIAGNOSIS_DESC like '%'+sp.col+'%' ) ");
			}
			sql.append(" ) ");
		}

		if (!CommonFun.isNe(params.get("oper_code"))) {
			sql.append(" and EXISTS(select 1 from ");
			sql.append("his_in_operation oper(nolock) ");
			sql.append("where oper.patient_id=" + a + ".patient_id ");
			sql.append("and oper.visit_id=" + a + ".visit_id ");
			if (!CommonFun.isNe(params.get("oper_code"))) {
				sql.append(" and EXISTS (select 1 from (  ");
				sql.append("select col from split(:oper_code,',')) sp ");
				sql.append("where oper.OPERATION_NO = sp.col ) ");
			}
			sql.append(" ) ");
		}

		boolean check = false;
		if (!CommonFun.isNe(params.get("incision_type")) && ((String) params.get("incision_type")).contains("0")) {
			check = true;
			String incision_type = (String) params.get("incision_type");
			params.put("incision_type", incision_type.replace("0,", "").replace("0", ""));
			sql.append(" and (not EXISTS(SELECT top 1 1 FROM ");
			sql.append("his_in_operation (nolock) shoushu ");
			sql.append("WHERE shoushu.PATIENT_ID = " + a + ".PATIENT_ID ");
			sql.append("and shoushu.visit_id = " + a + ".visit_id ");
			sql.append(") ");
		} else if (!CommonFun.isNe(params.get("incision_type")) && !((String) params.get("incision_type")).contains("0")) {
			sql.append(" and ( 1=0 ");
		}

		if (!CommonFun.isNe(params.get("incision_type")) || !CommonFun.isNe(params.get("oper_name"))) {

			sql.append(" or EXISTS(SELECT top 1 1 FROM ");
			sql.append("his_in_operation (nolock) shoushu ");
			sql.append("WHERE shoushu.PATIENT_ID = " + a + ".PATIENT_ID ");
			sql.append("and shoushu.visit_id = " + a + ".visit_id ");
			if (!CommonFun.isNe(params.get("oper_name"))) {
				sql.append(" and EXISTS (select 1 from ");
				sql.append("(select col from split(:oper_name,',')) spl ");
				sql.append("where spl.col = shoushu.OPERATION_DESC ");
				sql.append(") ");
			}
			if (!CommonFun.isNe(params.get("incision_type"))) {
				sql.append(" and EXISTS (select 1 from ");
				sql.append("(select col from split(:incision_type,',')) spl ");
				sql.append("where spl.col = shoushu.wound_grade ");
				sql.append(") ");
			}
			sql.append(") ");
			sql.append(") ");
		} else if (check && CommonFun.isNe(params.get("incision_type"))) {
			sql.append(" or  1=0) ");
		}

		return sql.toString();
	}

}
