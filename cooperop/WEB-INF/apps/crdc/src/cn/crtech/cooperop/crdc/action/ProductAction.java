package cn.crtech.cooperop.crdc.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.crdc.service.ProductService;

public class ProductAction extends BaseAction {

	public Result query(Map<String, Object> req) throws Exception{
		return new ProductService().query(req);
	}
	
	public Record get(Map<String, Object> req) throws Exception{
		return new ProductService().get((String)req.get("code"));
	}
	
	public Record modify(Map<String, Object> req) throws Exception{
		return new ProductService().get((String)req.get("code"));
	}
	
	public int insert(Map<String, Object> req) throws Exception{
		return new ProductService().insert(req);
	}
	
	public int update(Map<String, Object> req) throws Exception{
		return new ProductService().update((String)req.remove("code"), req);
	}
	
	public int delete(Map<String, Object> req) throws Exception{
		return new ProductService().delete((String)req.get("code"));
	}
	
	public void redeployAll(Map<String, Object> req) throws Exception{
		new ProductService().redeployAll((String)req.get("code"));
	}
	
}
