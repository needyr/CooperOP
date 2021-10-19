package cn.crtech.cooperop.setting.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.setting.service.WeixinService;

public class WeixinAction extends BaseAction{
	public Map<String, Object> save(Map<String, Object> req) throws Exception{
		new WeixinService().save(req);
		return req;
	}
	public Map<String, Object> add(Map<String, Object> req) throws Exception{
		return new WeixinService().get(req);
	}
}
