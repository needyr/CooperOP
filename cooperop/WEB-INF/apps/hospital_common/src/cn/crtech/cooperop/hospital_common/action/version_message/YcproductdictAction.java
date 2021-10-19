package cn.crtech.cooperop.hospital_common.action.version_message;

import java.util.Map;


import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.version_message.YcproductdictService;

public class YcproductdictAction extends BaseAction{
	
//	private YcproductdictService ycService = new YcproductdictService();
	
	public Result query(Map<String, Object> params) throws Exception {
		Result result = new YcproductdictService().query(params);
		return result;
	}
	
	public void delete(Map<String, Object> params) throws Exception {
		new YcproductdictService().delete(params);
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		new YcproductdictService().insert(params);
	}
	
	public Map<String, Object> edit(Map<String, Object> params) throws Exception {
		return new YcproductdictService().getById(params);
	}
	
	public void update(Map<String, Object> params) throws Exception {
//		System.out.println("进来了");
		new YcproductdictService().update(params);
	}
}
