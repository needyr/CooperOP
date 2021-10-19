package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.InstructionConfigService;

public class InstructionconfigAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		return new InstructionConfigService().query(params);
	}
	
	public Result queryBrief(Map<String, Object> params) throws Exception {
		return new InstructionConfigService().queryBrief(params);
	}
	
	public Result queryHas(Map<String, Object> params) throws Exception {
		return new InstructionConfigService().queryHas(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return new InstructionConfigService().insert(params);
	}
	
	public int moveUp(Map<String, Object> params) throws Exception {
		return new InstructionConfigService().moveUp(params);
	}
	
	public int moveDown(Map<String, Object> params) throws Exception {
		return new InstructionConfigService().moveDown(params);
	}
	
	public int updateShow(Map<String, Object> params) throws Exception {
		return new InstructionConfigService().updateShow(params);
	}
	
	public int moveUpBrief(Map<String, Object> params) throws Exception {
		return new InstructionConfigService().moveUpBrief(params);
	}
	
	public int moveDownBrief(Map<String, Object> params) throws Exception {
		return new InstructionConfigService().moveDownBrief(params);
	}
	
	public int updateShowBrief(Map<String, Object> params) throws Exception {
		return new InstructionConfigService().updateShowBrief(params);
	}
	
	public int updateTitle(Map<String, Object> params) throws Exception {
		return new InstructionConfigService().updateTitle(params);
	}

	
}
