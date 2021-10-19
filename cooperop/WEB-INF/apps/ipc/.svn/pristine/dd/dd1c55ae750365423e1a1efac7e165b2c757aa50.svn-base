package cn.crtech.precheck.ipc.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class ThirdpartyDataDao extends BaseDao {
	public Result queryDiagnosisForHLYY(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.* ");
		sql.append("from auto_audit_diagnosis(nolock) p  ");
		sql.append(" where p.auto_audit_id=:auto_audit_id ");
		return executeQuery(sql.toString(), params);
	}
	
	public void insertDiagnosis(Map<String, Object> params) throws Exception {
		executeInsert("auto_audit_diagnosis", params);
	}
	
	public Result queryOpsInfoForHLYY(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select mx.*,                                           ");
		sql.append("isnull(CONVERT(varchar(19), mx.start_date_time, 20),'')"); 
		sql.append("as start_date_time2,                                    ");
		sql.append("isnull(CONVERT(varchar(19), mx.end_date_time, 20),'')  ");
		sql.append("as end_date_time2                                       ");
		sql.append(" from auto_audit_oper(nolock) mx ");
		sql.append(" where mx.auto_audit_id=:auto_audit_id");
		return executeQuery(sql.toString(), params);
	}
	
	public void insertOpsInfoName(Map<String, Object> params) throws Exception {
		executeInsert("auto_audit_operation_name", params);
	}
	
	public void insertOpsInfoMaster(Map<String, Object> params) throws Exception {
		executeInsert("auto_audit_operation_master", params);
	}
	
	public Result queryAllergyInfoForHLYY(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.* ");
		sql.append("from auto_audit_PhysiologyInfo(nolock) p  ");
		sql.append(" where p.auto_audit_id=:auto_audit_id ");
		return executeQuery(sql.toString(), params);
	}
	
	public void insertAllergyInfo(Map<String, Object> params) throws Exception {
		executeInsert("auto_audit_PhysiologyInfo", params);
	}
	
	public Result queryPhysiologyInfoForHLYY(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.* ");
		sql.append("from auto_audit_PhysiologyInfo(nolock) p  ");
		sql.append(" where p.auto_audit_id=:auto_audit_id ");
		return executeQuery(sql.toString(), params);
	}
	
	public void insertPhysiologyInfo(Map<String, Object> params) throws Exception {
		executeInsert("auto_audit_PhysiologyInfo", params);
	}
	
	public void insertPatient(Map<String, Object> params) throws Exception {
		executeInsert("auto_audit_patient", params);
	}

	public void execDealData(Record params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("exec deal_data :auto_audit_id");
		Record ins = new Record();
		ins.put("auto_audit_id", params.get("auto_audit_id"));
		execute(sql.toString(), ins);
	}
	
	/**
	 * 自定义审查获取审查医嘱数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Result get_audit_def_oredrs(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ori.* from v_audit_def_oredrs(nolock) ori ");
		sql.append(" where ori.auto_audit_id=:auto_audit_id ");
		return executeQuery(sql.toString(), params);
	}
	
	public Record get_audit_def_patient(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from v_audit_def_patient (nolock) ");
		sql.append(" where auto_audit_id = :auto_audit_id ");
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public Result orgResultsToMap(Map<String, Object> map) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select                                                      ");
		sql.append("stuff((                                                     ");
		sql.append("select ','+p_key_th from auto_audit_orders (nolock)         ");
		sql.append("where auto_audit_id = :auto_audit_id                        ");
		sql.append("and charindex(p_key+',',b.order_id+',') > 0                 ");
		sql.append("for xml path('')                                            ");
		sql.append("),1,1,'') as order_p_key,                                   ");
		sql.append("b.level as level,                                           ");
		sql.append("b.check_result_state as lj_status,                          ");
		sql.append("d.sort_name as sort_name,                                   ");
		sql.append("b.description as info,                                      ");
		sql.append("b.reference as ref                                          ");
		sql.append("from                                                        ");
		sql.append("check_result (nolock) a                                     ");
		sql.append("inner join check_result_info (nolock) b                     ");
		sql.append("on a.id = b.parent_id and b.auto_audit_id = :auto_audit_id  ");
		sql.append("left join hospital_common..map_common_regulation (nolock) c ");
		sql.append("on a.keyword = c.thirdt_code and a.type = c.check_type      ");
		sql.append("and c.product_code = 'ipc'                                  ");
		sql.append("left join hospital_common..sys_common_regulation (nolock) d ");
		sql.append("on d.p_key = c.sys_p_key                                    ");
		sql.append("where a.auto_audit_id = :auto_audit_id                      ");
		return executeQuery(sql.toString(), map);
	}
	
}
