package cn.crtech.cooperop.oa.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

/**
 * @Description:
 * @author: wangyu
 * @date: 2019年11月8日
 */
public class CalendarEventDao extends BaseDao {
    private final static String TABLE_NAME = "calendar_event";

    public Result query(Map<String, Object> params) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("select t.* ");
	sql.append(" from " + TABLE_NAME + "(nolock) t ");
	sql.append(" where 1=1 ");
	if("MYSQL".equals(conn.getDbType().toUpperCase())){
		setParameter(params, "begin_date", " and convert(t.create_time,datetime) >= :begin_date", sql);
		setParameter(params, "end_date", " and convert(t.create_time,datetime) <= :end_date", sql);
		setParameter(params, "event_date", " and convert(t.create_time,datetime) = :event_date", sql);
	}else{
		setParameter(params, "begin_date", " and convert(varchar(10),t.create_time,120) >= :begin_date", sql);
		setParameter(params, "end_date", " and convert(varchar(10),t.create_time,120) <= :end_date", sql);
		setParameter(params, "event_date", " and convert(varchar(10),t.create_time,120) = :event_date", sql);
	}
	setParameter(params, "system_user_id", " and t.system_user_id = :system_user_id", sql);
	setParameter(params, "dl", " and t.dl=:dl ", sql);
	setParameter(params, "id", " and t.id=:id ", sql);
	if (CommonFun.isNe(params.get("sort"))) {
	    params.put("sort", "dl_level, expire_time");
	}
	return executeQuery(sql.toString(), params);
    }

    public Record get(Map<String, Object> params) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("select top 1 t.* ");
	sql.append(" from " + TABLE_NAME + "(nolock) t ");
	sql.append(" where 1=1 ");
	setParameter(params, "id", " and t.id=:id", sql);
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
