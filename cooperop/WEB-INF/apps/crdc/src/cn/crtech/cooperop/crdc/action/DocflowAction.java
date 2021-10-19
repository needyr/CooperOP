package cn.crtech.cooperop.crdc.action;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.mvc.view.ViewCreater;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.crdc.service.DesignerService;
import cn.crtech.cooperop.crdc.service.DocFlowService;
import cn.crtech.cooperop.crdc.service.WorkFlowDesignerService;

public class DocflowAction extends BaseAction {

	public Result query(Map<String, Object> req) throws Exception{
		req.put("type", "document");
		return new DesignerService().query(req);
	}
	
	public Map<String, Object> get(Map<String, Object> req) throws Exception{
		return new WorkFlowDesignerService().get((String)req.get("system_product_code"), (String)req.get("id"));
	}
	
	public void save(Map<String, Object> req) throws Exception{
		Map<String, Object> params = CommonFun.json2Object((String)req.get("wfjson"), Map.class);
		params.put("type", "document");
		new WorkFlowDesignerService().save(params);
	}
	
	public Map<String, Object> delete(Map<String, Object> req) throws Exception{
		String type = (String)req.get("type");
		String flag = (String)req.get("flag");
		String scheme = (String)req.get("id");
		String system_product_code = (String)req.get("system_product_code");
		new DesignerService().delete(type, scheme, flag,system_product_code);
		new WorkFlowDesignerService().delete((String)req.get("system_product_code"), (String)req.get("id"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("return", true);
		return map;
	}
	public Map<String, Object> deploy(Map<String, Object> req) throws Exception{
		DocFlowService dfs = new DocFlowService();
		Result r = dfs.queryDocFlowCount(new HashMap<String, Object>());
		String docflow_num = "999";//License.getParams().get("docflow_num");
		if(CommonFun.isNe(docflow_num)){
			throw new Exception("没有文档流设计权限，请联系管理员购买【文档流设计】的权限");
		}
		if(r != null && r.getResultset().size() >= Integer.parseInt(docflow_num)){
			throw new Exception("您购买的文档流数量为："+docflow_num + "，如果需要添加更多的文档流，请联系管理员进行购买！");
		}
		dfs.deploy(req);
		
		Map<String, Object> m = CommonFun.json2Object((String) req.get("jdata"), Map.class);
		new DesignerService().createHZTable(m);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("return", true);
		return map;
	}
	public void redeployAll(Map<String, Object> req) throws Exception{
		
	}
	public void undeploy(Map<String, Object> req) throws Exception{
		new WorkFlowDesignerService().undeploy((String)req.get("system_product_code"), (String)req.get("id"));
	}
	public Result selectbill(Map<String, Object> req) throws Exception{
		if(CommonFun.isNe(req.get("system_product_code"))){
			return new Result();
		}
		//req.put("type", "bill");
		return new WorkFlowDesignerService().selectbill(req);
	}
}
