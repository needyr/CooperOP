package cn.crtech.cooperop.hospital_common.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class ScheduleDao extends BaseDao {

	private static String TABLE_NAME = "dbo.data_webservice_schedule_log";
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(TABLE_NAME, params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", params.get("id"));
		return executeDelete(TABLE_NAME, conditions);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", params.get("id"));
		return executeUpdate(TABLE_NAME, params, conditions);
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select id, convert(varchar(30), start_time, 121) as start_time"
				+ ", convert(varchar(30), end_time, 121) as end_time "
				+ ", result, data_webservice_code, data_webservice_method_code "
				+ " from " + TABLE_NAME + "(nolock) ");
		sql.append(" where 1=1 ");
		StringBuffer beginTime = new StringBuffer();
		StringBuffer endTime = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		if (!CommonFun.isNe(params.get("begin_date")))
			beginTime.append(params.get("begin_date"));
		if (!CommonFun.isNe(params.get("begin_time"))) {
			if (!(beginTime.length() > 0))
				beginTime.append(sdf.format(new Date().getTime()));
			beginTime.append(" ");
			beginTime.append(params.get("begin_time"));
		}  else {
			if (beginTime.length() > 0)
				beginTime.append(" 00:00");
		}
		if (!CommonFun.isNe(params.get("end_date")))
			endTime.append(params.get("end_date"));
		if (!CommonFun.isNe(params.get("end_time"))) {
			if (!(endTime.length() > 0))
				endTime.append(sdf.format(new Date().getTime()));
			endTime.append(" ");
			endTime.append(params.get("end_time"));
		} else {
			if (endTime.length() > 0)
				endTime.append(" 23:59");
		}
		if (beginTime.length() > 0 && endTime.length() > 0) {
			sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			if (sdf.parse(beginTime.toString()).getTime() > 
					sdf.parse(endTime.toString()).getTime()) {
				StringBuffer tp = new StringBuffer();
				tp = beginTime;
				beginTime = endTime;
				endTime = tp;
			}
			sql.append(" and (start_time >= convert(datetime, :beginTime, 120)"
					+ " and end_time <= convert(datetime, :endTime, 120))");
			params.put("beginTime", beginTime.toString());
			params.put("endTime", endTime.toString());
		}
		setParameter(params, "data_webservice_code", " and"
				+ " data_webservice_code=:data_webservice_code", sql);
		setParameter(params, "data_webservice_method_code", ""
				+ " and data_webservice_method_code=:data_webservice_method_code", sql);
		
		return executeQueryLimit(sql.toString(), params);
	}

	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + TABLE_NAME + "(nolock) ");
		sql.append(" where id=:id");
		return executeQuerySingleRecord(sql.toString(), params);
	}
}
