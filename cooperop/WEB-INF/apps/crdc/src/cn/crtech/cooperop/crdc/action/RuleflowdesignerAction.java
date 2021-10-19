package cn.crtech.cooperop.crdc.action;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.crdc.service.RuleFlowDesignerService;

public class RuleflowdesignerAction extends BaseAction {

	public Result query(Map<String, Object> req) throws Exception{
		return new RuleFlowDesignerService().query(req);
	}
	
	public Map<String, Object> get(Map<String, Object> req) throws Exception{
		return new RuleFlowDesignerService().get((String)req.get("system_product_code"), (String)req.get("id"));
	}
	
	public void save(Map<String, Object> req) throws Exception{
		Map<String, Object> params = CommonFun.json2Object((String)req.get("wfjson"), Map.class);
		params.put("type", "rule");
		new RuleFlowDesignerService().save(params);
	}
	
	public void delete(Map<String, Object> req) throws Exception{
		new RuleFlowDesignerService().delete((String)req.get("system_product_code"), (String)req.get("id"));
	}

	public void deploy(Map<String, Object> req) throws Exception{
		Map<String, Object> params = CommonFun.json2Object((String)req.get("wfjson"), Map.class);
		
		RuleFlowDesignerService wfd = new RuleFlowDesignerService();
		Result r = wfd.queryFlowCount(new HashMap<String, Object>());
		String flow_num = "999";//License.getParams().get("flow_num");
		if(CommonFun.isNe(flow_num)){
			throw new Exception("没有流程设计权限，请联系管理员购买【流程设计】的权限");
		}
		if(r != null && r.getResultset().size() >= Integer.parseInt(flow_num)){
			throw new Exception("您购买的流程数量为："+flow_num + "，如果需要添加更多的流程，请联系管理员进行购买！");
		}
		wfd.deploy(params);
	}
	public void undeploy(Map<String, Object> req) throws Exception{
		new RuleFlowDesignerService().undeploy((String)req.get("system_product_code"), (String)req.get("id"));
	}
	public void redeployAll(Map<String, Object> req) throws Exception{
		
	}
	public Result selectbill(Map<String, Object> req) throws Exception{
		if(CommonFun.isNe(req.get("system_product_code"))){
			return new Result();
		}
		//req.put("type", "bill");
		return new RuleFlowDesignerService().selectbill(req);
	}
}
