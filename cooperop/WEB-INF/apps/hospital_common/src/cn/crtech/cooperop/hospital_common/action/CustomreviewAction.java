package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.hospital_common.service.DictdrugService;

public class CustomreviewAction extends BaseAction{

	public Record cz_all_mx(Map<String, Object> params) throws Exception {
		return new DictdrugService().cz_all_mx(params);
	}
}
