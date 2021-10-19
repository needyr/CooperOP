package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.ScheduleService;


public class ScheduleAction {
	
	public int insert(Map<String, Object> params) throws Exception {
		return new ScheduleService().insert(params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new ScheduleService().delete(params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		return new ScheduleService().update(params);
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		return new ScheduleService().query(params);
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		return new ScheduleService().get(params);
	}
}
