package cn.crtech.cooperop.bus.schedule.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

/**
 * 
 * @author FORWAY R&D fuhong.chen
 * @version xx 1.0
 * @createTime 2010-11-1 下午01:38:11
 */
public class TaskDao extends BaseDao {

	// 查询需要执行的任务
	public Result queryTask(String mac) throws Exception {
		// 查询所有本机需要运行的正常任务
		String sql = "select * from schedule(nolock) where mac = :mac and state = 1";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mac", mac);
		return executeQuery(sql, params);
	}

	// 记录执行日志
	public int writeLog(TaskLog log) throws Exception {
		String sql = "insert into schedule_log(id,schedule_instanc,start_time,end_time,computer,result) " +
				"values(seq_schedule_log.nextval,:schedule_instanc,:start_time,:end_time,:computer,:result)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("schedule_instanc", log.getInstanceId());
		params.put("start_time", log.getStartTime());
		params.put("end_time", log.getEndTime());
		params.put("computer", log.getComputer());
		params.put("result", log.getResult());
		return executeUpdate(sql, params);
	}
}
