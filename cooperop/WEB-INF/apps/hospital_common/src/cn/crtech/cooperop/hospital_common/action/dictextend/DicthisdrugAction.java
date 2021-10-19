package cn.crtech.cooperop.hospital_common.action.dictextend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.DictHisDrugService;
import cn.crtech.cooperop.hospital_common.service.DictSysYlflService;
import cn.crtech.cooperop.hospital_common.service.DictSysYpflService;
import cn.crtech.cooperop.hospital_common.service.DictdrugService;

public class DicthisdrugAction extends BaseAction {
	public Result query(Map<String, Object> params) throws Exception {
		Result result = new DictHisDrugService().query(params);
		return result;
	}
	
	public void updateByCode(Map<String, Object> params) throws Exception {
		new DictHisDrugService().updateByCode(params);
	}
	
	public void updateSysByCodeShuoms(Map<String, Object> params) throws Exception {
		new DictHisDrugService().updateSysByCodeShuoms(params);
	}
	
	public Map<String, Object> list(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ypfl", new DictSysYpflService().query(params).getResultset());
		map.put("types", new DictdrugService().queryDrugType(params).getResultset());
		return map;
	}
	
	public List<Record> query_ylfl(Map<String, Object> params) throws Exception {
		return new DictSysYlflService().query(params).getResultset();
	}
	
	public Map<String, Object> delete_ylfl(Map<String, Object> req) throws Exception{
		new DictSysYlflService().delete(req);
		req.put("result", "success");
		return req;
	}
	
	public Map<String, Object> save_ylfl(Map<String, Object> req) throws Exception{
		DictSysYlflService p = new DictSysYlflService();
		if(!CommonFun.isNe(req.get("id"))){
			p.update(req);
		}else{
			p.save(req);
		}
		req.put("result", "success");
		return req;
	}
	
	public Map<String, Object> ylfl_add(Map<String, Object> req) throws Exception{
		Record r = new Record();
		if(!CommonFun.isNe(req.get("id"))){
			r = new DictSysYlflService().get(req);
		}else{
			r.put("parent_id", req.get("parent_id"));
		}
		return r;
	}
	
	public Map<String, Object> download(Map<String, Object> params) throws Exception{
		Record r = new Record();
		if(!CommonFun.isNe(params.get("drug_code"))){
			r = new DictHisDrugService().getShuoms_file(params);
		}
		return r;
	}
	
	/**
	 * 根据标签获取药品
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object>  searchByDrugTag(Map<String, Object> params) throws Exception {
		return new DictHisDrugService().searchByDrugTag(params);
	}
	
	/**
	 * 升级多个药品为某个标签
	 * @param params
	 * @throws Exception
	 */
	public void  updateTag(Map<String, Object> params) throws Exception {
		new DictHisDrugService().updateTag(params);
	}
	
	/**
	 * 查询药品属性字典
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryAttrTree(Map<String, Object> params) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("attr", new DictHisDrugService().queryAttrTree(params).getResultset());
		return map;
	}
	
	/**
	 * 处理保存的药品属性
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public void disposeAttr(Map<String, Object> params) throws Exception {
		new DictHisDrugService().disposeAttr(params);
	}
	
}
