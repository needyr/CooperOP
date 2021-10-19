package cn.crtech.cooperop.pivascockpit.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class CockpitDao extends BaseDao {

	public static final String TABLE_NAME = "pcp_cockpit_def";
	public static final String VIEW_NAME_USER = "v_system_user";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select c.id, c.name, c.template, c.last_modifier, c.last_modify_time, u.name as last_modifier_name, c.state, c.remark, c.preview ");
		sql.append("  from " + TABLE_NAME + " c ");
		sql.append("  left join " + VIEW_NAME_USER + " u on u.id = c.last_modifier ");
		sql.append(" where c.state >= 0 ");
		
		setParameter(params, "filter", " and UPPER (c.name+','+isnull(c.remark,'')+dbo.fun_getPY(c.name)+','+dbo.fun_getPY(isnull(c.remark,''))) LIKE UPPER ('%'+:filter+'%')", sql);
		setParameter(params, "state", " and c.state = :state", sql);
		
		if (CommonFun.isNe(params.get("sort"))) {
			params.put("sort", "name asc");
		}
		
		return executeQueryLimit(sql.toString(), params);
	}

	public Record get(int id) throws Exception {
		Record params = new Record();
		params.put("id", id);
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select c.id, c.name, c.template, c.state, c.remark");
		sql.append("  from " + TABLE_NAME + " c ");
		sql.append(" where state >= 0 and id = :id");
		
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public int updateState(int id, int state) throws Exception {
		Record conditions = new Record();
		conditions.put("id", id);
		Record params = new Record();
		params.put("state", state);
		params.put("last_modifier", user().getId());
		params.put("last_modify_time", "sysdate");
		return executeUpdate(TABLE_NAME, params, conditions);
	}

	public int delete(int id) throws Exception {
		Record conditions = new Record();
		conditions.put("id", id);
		Record params = new Record();
		params.put("state", -1);
		params.put("last_modifier", user().getId());
		params.put("last_modify_time", "sysdate");
		return executeUpdate(TABLE_NAME, params, conditions);
	}

	public int insert(Map<String, Object> cockpit) throws Exception {
		int id = Integer.parseInt(getSeqNextVal(TABLE_NAME));
		cockpit.put("id", id);
		cockpit.put("state", 1);
		cockpit.put("last_modifier", user().getId());
		cockpit.put("last_modify_time", "sysdate");
		executeInsert(TABLE_NAME, cockpit);
		return id;
	}

	public int update(int id, Map<String, Object> cockpit) throws Exception {
		Record conditions = new Record();
		conditions.put("id", id);
		Record params = new Record(cockpit);
		params.remove("id");
		params.put("last_modifier", user().getId());
		params.put("last_modify_time", "sysdate");
		return executeUpdate(TABLE_NAME, params, conditions);
	}

}
