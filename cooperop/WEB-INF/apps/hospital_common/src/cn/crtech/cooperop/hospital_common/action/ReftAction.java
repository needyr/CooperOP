package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ReftService;

public class ReftAction extends BaseAction {
	
	private static String table_name = null;
	private static String type = null;
	
	public int insert(Map<String, Object> params) throws Exception {
		return new ReftService().insert(params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new ReftService().delete(params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		return new ReftService().update(params);
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		return new ReftService().query(params);
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		Record r = new ReftService().get(params);
		table_name = (String)r.get("table_name");
		type = (String)r.get("type");
		return r;
	}
	
	public Result queryExceptColumn(Map<String, Object> params) throws Exception {
		params.put("_exceptColumn_", table_name);
		params.put("_exceptType_", type);
		return query(params);
	}
}
