package cn.crtech.cooperop.hospital_common.action.dict;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.SysdeptService;

public class SysdeptAction extends BaseAction {
	
	public List<Record> queryTree(Map<String, Object> req) throws Exception{
		return new SysdeptService().queryTree(req).getResultset();
	}
	
	public Result queryTreeRe(Map<String, Object> req) throws Exception{
		Result queryTree = new SysdeptService().queryTree(req);
		return queryTree;
	}
}
