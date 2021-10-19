package cn.crtech.cooperop.hospital_common.action.dict;

import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.DictdrugService;
import cn.crtech.cooperop.hospital_common.service.dict.SysDrugTagService;

public class SysdrugtagAction extends BaseAction{
	
	public Result query(Map<String, Object> params) throws Exception {
		return new SysDrugTagService().query(params);
	}
	
	public Result parenttag(Map<String, Object> req) throws Exception{
		return new SysDrugTagService().parenttag(req);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return new SysDrugTagService().insert(params);
	}

	public int update(Map<String, Object> params) throws Exception {
		return new SysDrugTagService().update(params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new SysDrugTagService().delete(params);
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("drugtagid"))){
			return new SysDrugTagService().get(params);
		}else {
			return null;
		}
	}
	
	public List<Record> queryTree(Map<String, Object> req) throws Exception{
		return new SysDrugTagService().queryTree(req).getResultset();
	}
	
}
