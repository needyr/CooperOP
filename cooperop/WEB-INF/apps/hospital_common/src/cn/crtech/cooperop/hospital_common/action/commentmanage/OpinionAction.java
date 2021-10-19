package cn.crtech.cooperop.hospital_common.action.commentmanage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.DictSysCommentService;
import cn.crtech.cooperop.hospital_common.service.PrRegulationService;

public class OpinionAction extends BaseAction{
	
	public Result query(Map<String, Object> params) throws Exception {
		return new PrRegulationService().query(params);
	}
	
	public void delete(Map<String, Object> params) throws Exception {
		new PrRegulationService().delete(params);
	}
	
	public Map<String, Object> edit(Map<String, Object> params) throws Exception {
		return new PrRegulationService().get(params);
	}
	
	public int save(Map<String, Object> params) throws Exception {
		return new PrRegulationService().save(params);
	}
	
	public Result queryAuditType(Map<String, Object> params) throws Exception {
		return new PrRegulationService().queryAuditType(params);
	}
	
	public Map<String, Object> commentlist(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Record> resultset = new DictSysCommentService().queryAll(params).getResultset();
		map.put("comment", resultset);
		return map;
	}
}
