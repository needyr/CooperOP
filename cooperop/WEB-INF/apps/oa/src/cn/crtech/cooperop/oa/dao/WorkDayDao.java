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
public class WorkDayDao extends BaseDao {
    private final static String TABLE_NAME = "system_workday";

    public Result query(Map<String, Object> params) throws Exception {
    	StringBuffer sql = new StringBuffer();
    	if("MYSQL".equals(conn.getDbType().toUpperCase())){
			sql.append(" select sw.*,gl.* ");
			sql.append(" ,(select count(1) from calendar_event  where convert(expire_time,datetime) = gl.gongli  ");
			setParameter(params, "system_user_id", " and system_user_id = :system_user_id", sql);
			sql.append(" and dl='task' ) as task_num ");
			sql.append(" ,(select count(1) from calendar_event  where convert(expire_time,datetime) = gl.gongli ");
			setParameter(params, "system_user_id", " and system_user_id = :system_user_id", sql);
			sql.append(" and dl='remind' ) as remind_num ");
			sql.append(" ,(select count(1) from calendar_event  where convert(expire_time,datetime) = gl.gongli ");
			setParameter(params, "system_user_id", " and system_user_id = :system_user_id", sql);
			sql.append(" and dl='note' ) as memo_num ");
			sql.append(" ,ifnull(sw1.is_work,sw.is_work) as workday,ifnull(gl.gongli,sw.set_date) as work_set_day ");
			sql.append(" ,sw1.id as ev_id,sw1.type as ev_type,sw1.is_legal AS ev_islegal,sw1.remark as ev_remark  ");
			sql.append(" from "+TABLE_NAME+"  sw ");
			sql.append(" right join  ");
			sql.append(" 	( select *,DATE_FORMAT(gongli,'%w')+1 as weeks from gnldzb   ");
			sql.append(" 		where gongli>=:sdate and gongli <=:edate                                  ");
			sql.append(" 	) gl on gl.weeks=sw.set_date                                      ");
			sql.append(" left join "+TABLE_NAME+"(nolock) sw1 on sw1.set_date=gl.gongli and sw1.type=2 ");
			sql.append(" where sw.type=1  ");
		}else{
			sql.append(" select sw.*,gl.* ");
			sql.append(" ,(select count(1) from calendar_event(nolock) where convert(varchar(10),expire_time,120) = gl.gongli  ");
			setParameter(params, "system_user_id", " and system_user_id = :system_user_id", sql);
			sql.append(" and dl='task' ) as task_num ");
			sql.append(" ,(select count(1) from calendar_event(nolock) where convert(varchar(10),expire_time,120) = gl.gongli ");
			setParameter(params, "system_user_id", " and system_user_id = :system_user_id", sql);
			sql.append(" and dl='remind' ) as remind_num ");
			sql.append(" ,(select count(1) from calendar_event(nolock) where convert(varchar(10),expire_time,120) = gl.gongli ");
			setParameter(params, "system_user_id", " and system_user_id = :system_user_id", sql);
			sql.append(" and dl='note' ) as memo_num ");
			sql.append(" ,isnull(sw1.is_work,sw.is_work) as workday,isnull(gl.gongli,sw.set_date) as work_set_day ");
			sql.append(" ,sw1.id as ev_id,sw1.type as ev_type,sw1.is_legal AS ev_islegal,sw1.remark as ev_remark  ");
			sql.append(" from "+TABLE_NAME+"(nolock) sw ");
			sql.append(" right join  ");
			sql.append(" 	( select *,DATEPART(weekday, gongli) as weeks from gnldzb(nolock)    ");
			sql.append(" 		where gongli>=:sdate and gongli <=:edate                                  ");
			sql.append(" 	) gl on gl.weeks=sw.set_date                                      ");
			sql.append(" left join "+TABLE_NAME+"(nolock) sw1 on sw1.set_date=gl.gongli and sw1.type=2 ");
			sql.append(" where sw.type=1  ");
		}
		return executeQuery(sql.toString(), params);
    }

    public Record get(Map<String, Object> params) throws Exception {
	StringBuffer sql = new StringBuffer();
	if("MYSQL".equals(conn.getDbType().toUpperCase())){
		sql.append(" select top 1 sw.*,gl.* ");
		sql.append(" ,isnull(sw1.is_work,sw.is_work) as workday,isnull(gl.gongli,sw.set_date) as work_set_day ");
		sql.append(" ,sw1.id as ev_id,sw1.type as ev_type,sw1.is_legal AS ev_islegal,sw1.remark as ev_remark  ");
		sql.append(" from "+TABLE_NAME+"(nolock) sw                                                       ");
		sql.append(" right join  ");
		sql.append(" 	( select *,DATE_FORMAT(gongli,'%w')+1 as weeks from gnldzb(nolock)  )  ");
		sql.append(" 	AS gl on gl.weeks=sw.set_date                                      ");
		sql.append(" left join "+TABLE_NAME+"(nolock) sw1 on sw1.set_date=gl.gongli and sw1.type=2 ");
	}else{
		sql.append(" select top 1 sw.*,gl.* ");
		sql.append(" ,isnull(sw1.is_work,sw.is_work) as workday,isnull(gl.gongli,sw.set_date) as work_set_day ");
		sql.append(" ,sw1.id as ev_id,sw1.type as ev_type,sw1.is_legal AS ev_islegal,sw1.remark as ev_remark  ");
		sql.append(" from "+TABLE_NAME+"(nolock) sw                                                       ");
		sql.append(" right join  ");
		sql.append(" 	( select *,DATEPART(weekday, gongli) as weeks from gnldzb(nolock)  )  ");
		sql.append(" 	AS gl on gl.weeks=sw.set_date                                      ");
		sql.append(" left join "+TABLE_NAME+"(nolock) sw1 on sw1.set_date=gl.gongli and sw1.type=2 ");
	}
	sql.append(" where sw.type=1  ");
	setParameter(params, "id", " and sw1.id=:id", sql);
	setParameter(params, "mdate", " and gl.gongli=:mdate", sql);
	setParameter(params, "set_date", " and sw.set_date=:set_date", sql);
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
