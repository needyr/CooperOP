package cn.crtech.cooperop.oa.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

/**
 * @author 作者 wangyu
 * @version 创建时间：2019年9月9日 
 * 类说明 操作oa_memo表的DAO
 */
public class MemoDao extends BaseDao {
    private final static String TABLE_NAME = "oa_memo";

    public Result query(Map<String, Object> params) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("select t.*, ");
	sql.append(" vsu.name creator_name ");
	sql.append(" from " + TABLE_NAME + "(nolock) t ");
	sql.append(" left join v_system_user vsu on vsu.id=t.creator ");
	sql.append(" where 1=1 ");
	setParameter(params, "id", " and t.id=:id ", sql);
	setParameter(params, "creator", " and t.creator=:creator ", sql);
	setParameter(params, "begin_date", " and t.memo_time >= :begin_date ", sql);
	setParameter(params, "end_date", " and t.memo_time <= :end_date ", sql);
	return executeQueryLimit(sql.toString(), params);
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