package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.Pharmacist_check_passService;

public class Pharmacist_check_passAction extends BaseAction{
	
	public Result query(Map<String, Object> params) throws Exception {
		Result result = new Pharmacist_check_passService().query(params);
		return result;
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		Record result = new Pharmacist_check_passService().get(params);
		return result;
	}
	
	public void update(Map<String, Object> params) throws Exception {
		new Pharmacist_check_passService().update(params);
	}
	
	public void delete(Map<String, Object> params) throws Exception {
		new Pharmacist_check_passService().delete(params);
	}
}
