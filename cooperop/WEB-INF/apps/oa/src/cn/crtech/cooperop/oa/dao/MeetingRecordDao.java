package cn.crtech.cooperop.oa.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

/**
 * @author 作者 wangyu
 * @version 创建时间：2019年9月5日 类说明 处理oa_meeting_record表的DAO
 */
public class MeetingRecordDao extends BaseDao {
    private final static String TABLE_NAME = "oa_meeting_record";

    public Result query(Map<String, Object> params) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("select t.*,mr.name meeting_name ,su.name AS moderator_name,vsu.name AS creator_name ");
	sql.append(" from " + TABLE_NAME + "(nolock) t ");
	sql.append(" left join oa_meeting_room mr on mr.id=t.meeting_id ");
	sql.append(" left join v_system_user_hr su on su.id=t.moderator ");
	sql.append(" left join v_system_user vsu on vsu.id=t.creator ");
	sql.append(" left join v_system_user_hr vsuh on vsuh.id=t.meeting_summary_user ");
	sql.append(" where t.state=1 ");
	setParameter(params, "id", " and t.id=:id ", sql);
	setParameter(params, "meeting_id", " and t.meeting_id=:meeting_id ", sql);
	return executeQueryLimit(sql.toString(), params);
    }

    /**
     * 加载只查两条记录
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public Result queryTopTwo(Map<String, Object> params) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("select top 2 t.*,mr.name meeting_name ,su.name  moderator_name ");
	// 查询数量
	sql.append(" ,(select count(mr.id) FROM [oa_meeting_record](nolock) as mr  ");
	sql.append(" 		where mr.state=1 ");
	setParameter(params, "meeting_id", " and mr.meeting_id=:meeting_id ", sql);
	setParameter(params, "after", " and mr.end_time > CURRENT_TIMESTAMP ", sql);
	setParameter(params, "before", " and mr.end_time < CURRENT_TIMESTAMP ", sql);
	setParameter(params, "filter", " and mr.creator=:filter ", sql);
	sql.append(" ) AS number ");
	sql.append(" from " + TABLE_NAME + "(nolock) t ");
	sql.append(" left join oa_meeting_room mr on mr.id=t.meeting_id ");
	sql.append(" left join v_system_user_hr su on su.id=t.moderator ");
	sql.append(" left join v_system_user_hr vsuh on vsuh.id=t.meeting_summary_user ");
	sql.append(" where t.state=1 ");
	setParameter(params, "meeting_id", " and t.meeting_id=:meeting_id ", sql);
	// 时间筛选
	setParameter(params, "after", " and t.end_time > CURRENT_TIMESTAMP ", sql);
	setParameter(params, "before", " and t.end_time < CURRENT_TIMESTAMP ", sql);
	// 创建者或者主持人
	setParameter(params, "filter", " and t.creator=:filter ", sql);
	sql.append(" order by t.start_time ");
	return executeQueryLimit(sql.toString(), params);
    }

    /**
     * 查询新增申请的会议室是否在使用中
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public Record queryExists(Map<String, Object> params) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("select top 1 t.* ");
	sql.append(" from " + TABLE_NAME + "(nolock) t ");
	sql.append(" where t.state=1 ");
	setParameter(params, "meeting_id", " and t.meeting_id=:meeting_id ", sql);
	setParameter(params, "start_time", "and (:start_time between t.start_time and t.end_time or  ", sql);
	setParameter(params, "end_time", " :end_time between t.start_time and t.end_time)", sql);
	return executeQuerySingleRecord(sql.toString(), params);
    }

    /**
     * 查询用户用于 会议主持人/纪要人
     * 
     * @param params
     * @return
     * @throws Exception
     */
    public Result queryUser(Map<String, Object> params) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("select vsuh.id ,vsuh.name ");
	sql.append(" from v_system_user_hr(nolock) vsuh ");
	sql.append(" where exists (select 1 from system_user_type(nolock) where in_select=1 and type=vsuh.type) and state=1 and user_state != 0");
	return executeQueryLimit(sql.toString(), params);
    }

    public Record get(Map<String, Object> params) throws Exception {
	StringBuffer sql = new StringBuffer();
	sql.append("select top 1 t.* ");
	sql.append(" ,mr.name meeting_name ");
	sql.append(" ,su.name  moderator_name ");
	sql.append(" ,vsuh.name meeting_summary_user_name");
	sql.append(" from " + TABLE_NAME + "(nolock) t ");
	sql.append(" left join oa_meeting_room mr on mr.id=t.meeting_id ");
	sql.append(" left join v_system_user_hr su on su.id=t.moderator ");
	sql.append(" left join v_system_user_hr vsuh on vsuh.id=t.meeting_summary_user ");
	sql.append(" where t.state=1 ");
	setParameter(params, "id", " and t.id=:id ", sql);
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
