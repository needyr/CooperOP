package cn.crtech.cooperop.oa.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

/**
 * @Description:
 * @author: wangyu
 * @date: 2019年10月25日
 */
public class WorkdayTimeplanDao extends BaseDao {
    private final static String TABLE_NAME = "system_workday_timeplan";

    public Result query(Map<String, Object> params) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("select top 3 t.*,sw.type as week_type,sw.set_date,sw.remark ");
	sql.append(" from " + TABLE_NAME + "(nolock) t ");
	sql.append("left join system_workday(nolock) sw on sw.id=t.workday_id ");
	sql.append(" where 1=1 ");
	setParameter(params, "workday_id", " and t.workday_id=:workday_id ", sql);
	return executeQueryLimit(sql.toString(), params);
    }

    public Record get(Map<String, Object> params) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("select top 1 t.* ");
	sql.append(" from " + TABLE_NAME + "(nolock) t ");
	sql.append(" where 1=1 ");
	setParameter(params, "id", " and t.id=:id", sql);
	setParameter(params, "workday_id", " and t.workday_id=:workday_id ", sql);
	setParameter(params, "type", " and t.type=:type ", sql);
	return executeQuerySingleRecord(sql.toString(), params);
    }

    public int insert(Map<String, Object> params) throws Exception {
	return executeInsert(TABLE_NAME, params);
    }

    public int update(Map<String, Object> params, Map<String, Object> condition) throws Exception {
	return executeUpdate(TABLE_NAME, params, condition);
    }

    public int delete(Map<String, Object> condition) throws Exception {
	return executeDelete(TABLE_NAME, condition);
    }
}
