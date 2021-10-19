package cn.crtech.cooperop.pivascockpit.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class CockpitTabDao extends BaseDao {

	public static final String TABLE_NAME = "pcp_cockpit_tab";
	
	public Result query(int cockpit_id) throws Exception {
		Record params = new Record();
		params.put("cockpit_id", cockpit_id);
		params.put("sort", "order_no asc");
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select cockpit_id, order_no, name ");
		sql.append("  from " + TABLE_NAME );
		sql.append(" where cockpit_id = :cockpit_id ");
		
		return executeQuery(sql.toString(), params);
	}

	public int deleteCockpitTabs(int cockpit_id) throws Exception {
		Record conditions = new Record();
		conditions.put("cockpit_id", cockpit_id);
		return executeDelete(TABLE_NAME, conditions);
	}

	public int insert(int cockpit_id, Map<String, Object> tab) throws Exception {
		tab.put("cockpit_id", cockpit_id);
		return executeInsert(TABLE_NAME, tab);
	}

}
