package cn.crtech.cooperop.ipc.dao;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;

import java.util.Map;
import cn.crtech.cooperop.bus.util.CommonFun;


public class SearchRResultDao extends BaseDao {
	private final static String YC_SEARCH_RECORD_ROWS = "yc_search_record_rows";
	
	private final static String YC_SEARCH_RECORD_RESULT = "yc_search_record_result";
	
	private final static String PROC = "syc_search_cm_proc";
	
	public String insert(Map<String, Object> params) throws Exception{
		String id = CommonFun.getITEMID();
		params.put("id", id);
		executeInsert(YC_SEARCH_RECORD_ROWS, params);
		return id;
	}
	
	public Result queryResult(Map<String, Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(" select row_pkey, use_flag from " + YC_SEARCH_RECORD_RESULT + " (nolock) where 1=1 ");
		if(!CommonFun.isNe(params.get("search_id"))) {
			sql.append(" and search_id = :search_id ");
		}
		return executeQuery(sql.toString(), params);
	}
	
	public Result queryQMsg(Map<String, Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append("   b.tag, b.order_text, b.doctor, b.repeat_indicator, ");
		sql.append("   b.start_date_time, b.order_status, ");
		sql.append("   b.dosage, b.dosage_units, ");
		sql.append("   b.administration, b.frequency, ");
		sql.append(" 	a.use_flag,a.msg,last_auto_audit_id, last_check_result_info_id ");
		sql.append(" from ");
		sql.append(" 	yc_search_record_result a ( nolock ) ");
		sql.append(" 	left join yc_search_record_rows b ( nolock ) ");
		sql.append(" 	on a.search_id = b.search_id ");
		sql.append(" 	and a.row_pkey = b.row_pkey ");
		sql.append(" where ");
		sql.append(" 	a.search_id = :search_id and a.use_flag = 0 ");
		return executeQuery(sql.toString(), params);
	}
	
	public String execComputerProc(Map<String, Object> params) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append(" exec " + PROC + " :search_id ");
		return execute(sql.toString(), params);
	}
	
}
