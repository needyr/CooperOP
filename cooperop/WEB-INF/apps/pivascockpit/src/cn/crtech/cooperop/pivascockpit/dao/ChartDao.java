package cn.crtech.cooperop.pivascockpit.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class ChartDao extends BaseDao {

	public static final String TABLE_NAME = "pcp_chart_def";

	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select c.code, c.name, c.refresh_time, c.childcode, dc.name as childname, c.iscockpit ");
		sql.append("  from " + TABLE_NAME + " c ");
		sql.append("  left join " + ChartDao.TABLE_NAME + " dc on dc.code = c.childcode ");
		sql.append(" where 1 = 1 ");
		
		setParameter(params, "filter", " and UPPER (c.code+','+c.name+dbo.fun_getPY(c.name)) LIKE UPPER ('%'+:filter+'%')", sql);
		setParameter(params, "iscockpit", " and c.iscockpit = :iscockpit", sql);
		
		if (CommonFun.isNe(params.get("sort"))) {
			params.put("sort", "name asc");
		}
		
		return executeQueryLimit(sql.toString(), params);
	}

	public Record get(String code) throws Exception {
		Record params = new Record();
		params.put("code", code);
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select c.code, c.name, c.refresh_time, c.childcode, dc.name as childname, c.cockpitsql, c.cockpitsort, c.cockpitlimit, c.fullsql, c.fullsort, c.fulllimit, c.iscockpit ");
		sql.append("  from " + TABLE_NAME + " c ");
		sql.append("  left join " + ChartDao.TABLE_NAME + " dc on dc.code = c.childcode ");
		sql.append(" where c.code = :code ");
		
		return executeQuerySingleRecord(sql.toString(), params);
	}


	public int update(String code, Map<String, Object> params) throws Exception {
		Record conditions = new Record();
		conditions.put("code", code);
		return executeUpdate(TABLE_NAME, params, conditions);
	}

}
