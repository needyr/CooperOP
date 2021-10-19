package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ImicCustomreService;
@DisLoggedIn
public class ImiccustomreAction extends BaseAction{
	
	public Result queryimicsc(Map<String, Object> params) throws Exception {
		return new ImicCustomreService().queryimicsc(params);
	}
	
	public Result querynotimicsc(Map<String, Object> params) throws Exception {
		return new ImicCustomreService().querynotimicsc(params);
	}
	
	//切换项目信息
	public Record getforinsurvsprice(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("item_code"))) {
			return new ImicCustomreService().getforinsurvsprice(params);
		}else {
			return null;
		}
	}
	
	public Result dictYBType(Map<String, Object> params) throws Exception {
		return new ImicCustomreService().dictYBType(params);
	}
	
}
