package cn.crtech.cooperop.oa.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

/**
 * @author 作者 wangyu
 * @version 创建时间：2019年9月5日 类说明 操作oa_meeting_room表的DAO
 */
public class MeetingRoomDao extends BaseDao {
	private final static String TABLE_NAME = "oa_meeting_room";

	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from  (");
		sql.append("select t.* ");
		// 查询有没有正在进行的会议
		sql.append(" ,(select 1 from [oa_meeting_record](nolock) as mr where mr.meeting_id=t.id and mr.state=1 and CURRENT_TIMESTAMP between mr.start_time and mr.end_time) as online");
		sql.append(" ,(SELECT STUFF((SELECT ','+mt.name FROM oa_meeting_tags AS mt where  t.id=mt.meeting_room_id for xml path('')),1,1,'')) AS tags ");
		sql.append(" from " + TABLE_NAME + "(nolock) t ");
		sql.append(" where t.state=1 ");
		setParameter(params, "id", " and t.id=:id ", sql);
		setParameter(params, "tablefilter", " and t.name+','+isnull(t.address,'') like '%'+:tablefilter+'%' ", sql);
		sql.append(" ) as tab ");
		sql.append(" where 1=1 ");
		if (!CommonFun.isNe(params.get("tags"))) {
			String tags = params.get("tags").toString();
			List<String> tagsList = Arrays.asList(tags.split(","));
			for (String tag : tagsList) {
				tag = ',' + tag + ',';
				sql.append(" and charindex('" + tag + "',','+tab.tags+',')>0 ");
			}
		}
		return executeQueryLimit(sql.toString(), params);
	}

	public Result queryAddressDistinct(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select DISTINCT(t.address) ");
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
		setParameter(params, "filter", " and t.name+t.address =:filter", sql);
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
