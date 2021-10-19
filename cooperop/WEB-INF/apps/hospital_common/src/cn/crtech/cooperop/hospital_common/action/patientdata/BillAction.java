package cn.crtech.cooperop.hospital_common.action.patientdata;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.hospital_common.service.patientdata.BillService;

public class BillAction extends BaseAction {
	
	public Map<String, Object> is_permit(Map<String, Object> params) throws Exception {
		return new BillService().is_permit(params);
	}
	
	public String visit_insert(Map<String, Object> params) throws Exception {
		return new BillService().visit_insert(params);
	}
	
	
	public int update_log(Map<String, Object> params) throws Exception {
		return new BillService().update_log(params);
	}
	
	
}
