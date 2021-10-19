package cn.crtech.cooperop.hospital_common.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class ServicelogDao extends BaseDao {

	// 0 完成   1 未完成
	public final static String TABLE_NAME = "dbo.data_service_log";
	public final static String TABLE_NAME_DONE = "dbo.data_service_log_bak";
	
	public int insert(Map<String, Object> params) throws Exception {
		return executeInsert(getTableName(params), params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", params.get("id"));
		return executeDelete(getTableName(params), conditions);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("id", params.get("id"));
		return executeUpdate(getTableName(params), params, conditions);
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select id, convert(varchar(30), begin_time, 121) as begin_time"
				+ ", convert(varchar(30), end_time, 121) as end_time "
				+ ", state, error_message, appId, request, result, data_service_method_code,"
				+ "data_service_code "
				+ " from " + getTableName(params) + "(nolock) ");
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
		log.debug(beginTime + " " + endTime);
		if (beginTime.length() > 0 && endTime.length() > 0) {
			sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			if (sdf.parse(beginTime.toString()).getTime() > 
					sdf.parse(endTime.toString()).getTime()) {
				StringBuffer tp = new StringBuffer();
				tp = beginTime;
				beginTime = endTime;
				endTime = tp;
			}
			sql.append(" and (begin_time >= convert(datetime, :beginTime, 120)"
					+ " and begin_time <= convert(datetime, :endTime, 120))");
			params.put("beginTime", beginTime.toString());
			params.put("endTime", endTime.toString());
		}
		setParameter(params, "app_id", " and appid=:app_id", sql);
	//	if (!CommonFun.isNe(params.get("key"))) {
	//		params.put("key", "%" + params.get("key") + "%");
	//		setParameter(params, "key", " and (data_service_method_code like :key "
	//				+ " or data_service_code like :key)", sql);
	//	}
		setParameter(params,"data_service_method_code", 
				" and data_service_method_code=:data_service_method_code", sql);
		setParameter(params,"data_service_code", 
				" and data_service_code=:data_service_code", sql);
		
		return executeQueryLimit(sql.toString(), params);
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from " + getTableName(params) + "(nolock) ");
		sql.append(" where id=:id");
		return executeQuerySingleRecord(sql.toString(), params);
	}

	public static String getTableName(Map<String, Object> params) {
		if (!CommonFun.isNe(params.get("case")) && 
				Integer.parseInt((String)params.get("case")) == 1) {
			return TABLE_NAME;
		} else {
			return TABLE_NAME_DONE;
		}
	}
}
