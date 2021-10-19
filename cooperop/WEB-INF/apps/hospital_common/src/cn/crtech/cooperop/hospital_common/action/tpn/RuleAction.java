package cn.crtech.cooperop.hospital_common.action.tpn;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.tpn.RuleService;

public class RuleAction extends BaseAction {
	//查询TPN规则
	public Result query(Map<String, Object> params) throws Exception {
		return new RuleService().query(params);

	}
	
	public Record add(Map<String, Object> params) throws Exception {
		return new RuleService().get(params);
	}

	public Result queryTpnzlRule(Map<String, Object> params) throws Exception {
		return new RuleService().queryTpnzlRule(params);

	}
	
	public Result getXmmch(Map<String, Object> params) throws Exception {
		return new RuleService().getXmmch(params);
		
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return new RuleService().insert(params);
	}
	
	
	public int insertTpnMX(Map<String, Object> params) throws Exception {
		return new RuleService().insertTpnMX(params);
	}
	//TPN规则：停用，启用
	public int updateByState(Map<String, Object> params) throws Exception {
		return new RuleService().updateByState(params);

	}
	
	public int updateById(Map<String, Object> params) throws Exception {
		return new RuleService().updateByState(params);
		
	}
	//TPN规则明细：停用，启用
		public int updateByState2(Map<String, Object> params) throws Exception {
			return new RuleService().updateByState2(params);

		}
	//删除TPN规则
	public int delete(Map<String, Object> params) throws Exception {
		return new RuleService().delete(params);

	}
	
	//删除TPN规则明细
	public int deleteRuleMX(Map<String, Object> params) throws Exception {
		return new RuleService().deleteRuleMX(params);

	}
	
}
