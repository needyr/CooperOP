package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.CheckLevelService_del;

public class ChecklevelAction_del extends BaseAction{
	public Result query(Map<String, Object> params) throws Exception {
		return new CheckLevelService_del().query(params);
	}
	
	
	public int insert(Map<String, Object> params) throws Exception {
		return new CheckLevelService_del().insert(params);
	}
	
	//关闭修改功能
	public int update(Map<String, Object> params) throws Exception {
		return new CheckLevelService_del().update(params);
	}
	
	public Record edits(Map<String, Object> params) throws Exception {
		return new CheckLevelService_del().get(params);
	}
	
	/*public int updateActive(Map<String, Object> params) throws Exception {
		return new CheckLevelService().updateActive(params);
	}*/
	
	/*public int delete(Map<String, Object> params) throws Exception {
		return new CheckLevelService().delete(params);
	}*/
}
