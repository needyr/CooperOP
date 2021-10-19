package cn.crtech.cooperop.oa.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class NoticeDao extends BaseDao{
	private final static String TABLE_NAME = "notice";
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.id,t.subject,t.author,t.begin_time,t.end_time,t.state,t.creator,t.create_time,t.last_modifier,t.last_modify_time,t.release_time ");
		if(!CommonFun.isNe(params.get("system_user_id"))){
			sql.append(" ,(select 1 from notice_read(nolock) where system_user_id=:system_user_id and notice_id=t.id) as is_read ");
		}
		sql.append(" from "+TABLE_NAME+"(nolock) t ");
		sql.append(" where t.state!=-1 ");
		sql.append(" and getdate() between t.begin_time and t.end_time ");
		setParameter(params, "id", " and t.id=:id ", sql);
		setParameter(params, "filter", " and t.subject+','+t.author like '%'+:filter+'%' ", sql);
		setParameter(params, "state", " and t.state=:state ", sql);
		setParameter(params, "exclude_state", " and t.state!=:exclude_state ", sql);
//		setParameter(params, "system_user_id", " and state=1 and exists (select 1 from notice_sendto(nolock) where notice_id=t.id "
//				+ "and (target='A' or (target='U' and send_to=:system_user_id) or (target='D' and send_to=(select department from v_system_user where id=:system_user_id)) )) ", sql);
		setParameter(params, "is_release", " and t.release_time is not null ", sql);
		if(CommonFun.isNe(params.get("sort"))){
			if(!CommonFun.isNe(params.get("system_user_id"))){
				params.put("sort", "is_read,release_time desc");
			}else{
				params.put("sort", "release_time desc");
			}
		}
		return executeQueryLimit(sql.toString(), params);
	}
	public Record getMyNoticenum(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) as num ");
		sql.append(" from "+TABLE_NAME+"(nolock) t ");
		sql.append("  left join notice_read(nolock) nr on nr.notice_id=t.id and nr.system_user_id=:system_user_id  ");
		sql.append(" where t.state!=-1 and t.state!=:exclude_state ");
		sql.append(" and nr.notice_id is null ");
//		setParameter(params, "system_user_id", "  exists (select 1 from notice_sendto(nolock) where notice_id=t.id "
//				+ "and (target='A' or (target='U' and send_to=:system_user_id) or (target='D' and send_to=(select department from v_system_user where id=:system_user_id)) )) ", sql);
		return executeQuerySingleRecord(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select top 1 t.* ");
		sql.append(" from "+TABLE_NAME+"(nolock) t ");
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
