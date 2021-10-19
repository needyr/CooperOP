package cn.crtech.cooperop.oa.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

/**
 * @author 作者 wangyu
 * @version 创建时间：2019年9月5日 
 * 类说明 处理oa_meeting_facility表的DAO
 */
public class MeetingFacilityDao extends BaseDao {
    private final static String TABLE_NAME = "oa_meeting_facility";

    public Result query(Map<String, Object> params) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("select t.* ");
	sql.append(" from " + TABLE_NAME + "(nolock) t ");
	sql.append(" where state=1 ");
	setParameter(params, "meeting_id", " and t.meeting_id=:meeting_id ", sql);
	setParameter(params, "id", " and t.id=:id ", sql);
	return executeQueryLimit(sql.toString(), params);
    }

    public Result queryFacilityDistinct(Map<String, Object> params) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("select DISTINCT(t.name) ");
	sql.append(" from " + TABLE_NAME + "(nolock) t ");
	sql.append(" where 1=1 ");
	return executeQueryLimit(sql.toString(), params);
    }

    public Record get(Map<String, Object> params) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("select top 1 t.* ");
	sql.append(" from " + TABLE_NAME + "(nolock) t ");
	sql.append(" where t.state=1 ");
	setParameter(params, "id", " and t.id=:id", sql);
	setParameter(params, "meeting_id", " and t.meeting_id=:meeting_id ", sql);
	setParameter(params, "name", " and t.name=:name ", sql);
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
