package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.WebparamsService;


public class WebparamsAction {
	
	public int insert(Map<String, Object> params) throws Exception {
		return new WebparamsService().insert(params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new WebparamsService().delete(params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		return new WebparamsService().update(params);
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		return new WebparamsService().query(params);
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		return new WebparamsService().get(params);
	}
}
