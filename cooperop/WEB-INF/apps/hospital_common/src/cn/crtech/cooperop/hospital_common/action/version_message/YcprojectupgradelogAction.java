package cn.crtech.cooperop.hospital_common.action.version_message;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.version_message.YcmoduledictService;
import cn.crtech.cooperop.hospital_common.service.version_message.YcproductdictService;
import cn.crtech.cooperop.hospital_common.service.version_message.YcprojectupgradelogService;
import cn.crtech.cooperop.hospital_common.service.version_message.YcupgradetypedictService;

public class YcprojectupgradelogAction extends BaseAction{
	public Result query(Map<String, Object> params) throws Exception {
//		System.out.println(JSON.toJSONString(params, true));
		Result result = new YcprojectupgradelogService().query(params);
		return result;
	}
	
	public Result queryVersion(Map<String, Object> params) throws Exception {
		return new YcprojectupgradelogService().queryVersion(params);
	}

	public Result queryMmodulename(Map<String, Object> params) throws Exception {
		return new YcmoduledictService().query(params);
	}
	
	public Result queryPproname(Map<String, Object> params) throws Exception {
		return new YcproductdictService().query(params);
	}
	
	public Result queryTtypename(Map<String, Object> params) throws Exception {
		Result result = new YcupgradetypedictService().query(params);
		return result;
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		new YcprojectupgradelogService().insert(params);
	}
	
	public Map<String,Object> edit(Map<String, Object> params) throws Exception {
		if (params.get("upgrade_id") != null) {
			Map<String, Object> map = new YcprojectupgradelogService().getById(params);
			if (params.get("msg") != null) {
				map.put("msg", params.get("msg"));
			}
			return map;
		}else {
			return null;
		}
	}
	
	public void update(Map<String, Object> params) throws Exception {
//		System.out.println(JSON.toJSONString(params, true));
		new YcprojectupgradelogService().update(params);
	}
	
	
}
