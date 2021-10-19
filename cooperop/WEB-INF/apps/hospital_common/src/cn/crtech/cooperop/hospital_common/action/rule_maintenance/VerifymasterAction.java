package cn.crtech.cooperop.hospital_common.action.rule_maintenance;

import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.rule_maintenance.VerifymasterService;

public class VerifymasterAction extends BaseAction{
	public Result query(Map<String, Object> params) throws Exception {
		return new VerifymasterService().query(params);
	}
	
	
}
