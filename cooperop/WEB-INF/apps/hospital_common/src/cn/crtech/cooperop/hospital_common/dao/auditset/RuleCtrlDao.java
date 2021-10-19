package cn.crtech.cooperop.hospital_common.dao.auditset;

import java.util.Date;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class RuleCtrlDao extends BaseDao{

	private static final String TABLE = "sys_common_regulation";
	
	private static final String DETAIL_TABLE = "hospital_imic..audit_rule_ctrl";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select                              ");
		sql.append(" 	b.id, a.sort_id, a.sort_name,       ");
		sql.append(" 	b.audit_sur, b.rejected_level,      ");   
		sql.append(" 	b.intercept_level, b.prompt_level,  ");     
		sql.append(" 	a.state z_state, b.state,           ");
		sql.append(" 	a.product_code, a.sort_num,         ");
		sql.append(" 	a.audit_explain,a.description       ");
		sql.append(" from "+ DETAIL_TABLE +" (nolock) b         ");
		sql.append(" left join "+ TABLE +" (nolock) a on a.p_key = b.sys_p_key  ");
		if(!CommonFun.isNe(params.get("sys_p_key"))) {
			sql.append(" where b.sys_p_key = :sys_p_key ");
		}
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from " + DETAIL_TABLE );
		sql.append(" where 1 = 1 ");
		if(!CommonFun.isNe(params.get("id"))) {
			sql.append(" and id = :id ");
		}
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record getByCondition(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from " + DETAIL_TABLE );
		sql.append(" where 1 = 1 ");
		sql.append(" and audit_sur = :audit_sur and sys_p_key =:sys_p_key ");
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(DETAIL_TABLE, params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Record condition = new Record();
		params.put("update_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		condition.put("id", params.remove("id"));
		return executeUpdate(DETAIL_TABLE, params, condition);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return executeDelete(DETAIL_TABLE, params);
	}
	
}
