package cn.crtech.cooperop.hospital_common.action;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.hospital_common.service.DictHisOperService;

public class DicthisoperAction extends BaseAction {
	public Map<String, Object> search(Map<String, Object> params) throws Exception {
		return new DictHisOperService().search(params);
	}

	public Map<String, Object> searchCheck(Map<String, Object> params) throws Exception {
		return new DictHisOperService().searchCheck(params);
	}

	/* 手术等级 */
	public List<Record> queryOperType(Map<String, Object> params) throws Exception {

		return new DictHisOperService().queryOperType(params);
	}

	public List<Record> queryOperTypeCheck(Map<String, Object> params) throws Exception {
		return new DictHisOperService().queryOperTypeCheck(params);
	}
	

	/* 手术名称 */
	public List<Record> queryOperName(Map<String, Object> params) throws Exception {

		return new DictHisOperService().queryOperName(params);
	}

	public List<Record> queryOperNameCheck(Map<String, Object> params) throws Exception {
		return new DictHisOperService().queryOperNameCheck(params);
	}

}
