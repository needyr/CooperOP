package cn.crtech.cooperop.hospital_common.action.auditset;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.auditset.MapCheckSortService;


public class MapchecksortAction extends BaseAction {

	public Result query(Map<String, Object> params) throws Exception {
		return new MapCheckSortService().query(params);

	}

	public int insert(Map<String, Object> params) throws Exception {
		 String user=user().getName();
		 params.put("oper_user",user);
		return new MapCheckSortService().insert(params);

	}

	public int update(Map<String, Object> params) throws Exception {
		 String user=user().getName();
		 params.put("oper_user",user);
		return new MapCheckSortService().update(params);

	}

	public int delete(Map<String, Object> params) throws Exception {
		return new MapCheckSortService().delete(params);

	}

	public Record edit(Map<String, Object> params) throws Exception {
		return new MapCheckSortService().get(params);

	}

}
