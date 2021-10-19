package cn.crtech.cooperop.hospital_common.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.UseReasonService;
import cn.crtech.cooperop.hospital_common.service.auditset.CheckSortService;

public class UsereasonAction extends BaseAction{
	public Map<String, List<Record>> query(Map<String, Object> params) throws Exception {
		Map<String, List<Record>> map = new UseReasonService().query(params);
		return map;
	}
	
	public Result queryPro(Map<String, Object> params) throws Exception {
		Result result = new UseReasonService().queryPro(params);
		return result;
	}
	
	public Result queryCheck(Map<String, Object> params) throws Exception {
		Result result = new UseReasonService().queryCheck(params);
		return result;
	}
	
	public int save(Map<String, Object> params) throws Exception {
		boolean b=CommonFun.isNe(params.get("id"));
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("sort_id", params.get("apa_check_sorts_id"));
		map.put("product_code", params.get("sys_product_code"));
		Record record = new CheckSortService().getBySortId(map);
		params.put("apa_check_sorts_name", record.remove("sort_name"));
		if(b){
			return new UseReasonService().insert(params);
		}else {
			return new UseReasonService().update(params);
		}
	}
	
	public Map<String, Object> edit(Map<String, Object> params) throws Exception {
		Record r = new Record();
		if(!CommonFun.isNe(params.get("id"))){
			r = new UseReasonService().get(params);
		}
		r.put("usereason_product_id", params.get("usereason_product_id"));
		return r;
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		int result = new UseReasonService().delete(params);
		return result;
	}
	
	public Result queryDetail(Map<String, Object> params) throws Exception {
		Result result = new UseReasonService().queryDetail(params);
		return result;
	}
	
	public Result queryType(Map<String, Object> params) throws Exception {
		Result result = new UseReasonService().queryType(params);
		return result;
	}
	
	public Map<String, Object> addreason(Map<String, Object> params) throws Exception {
		Record r = new Record();
		if(!CommonFun.isNe(params.get("id"))){
			r = new UseReasonService().getDetail(params);
		}
		r.put("parent_name", params.get("parent_name"));
		return r;
	}
	
	public int saveDetail(Map<String, Object> params) throws Exception {
		boolean b=CommonFun.isNe(params.get("id"));
		if(b){
			return new UseReasonService().insertDetail(params);
		}else {
			return new UseReasonService().updateDetail(params);
		}
	}
	
	public int deleteDetail(Map<String, Object> params) throws Exception {
		int result = new UseReasonService().deleteDetail(params);
		return result;
	}
	
}
