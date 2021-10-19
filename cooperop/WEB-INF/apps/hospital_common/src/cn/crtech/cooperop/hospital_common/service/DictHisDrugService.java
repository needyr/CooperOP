package cn.crtech.cooperop.hospital_common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.DictHisDrugDao;
import cn.crtech.cooperop.hospital_common.util.Util;

public class DictHisDrugService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictHisDrugDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public void updateByCode(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new DictHisDrugDao().updateByCode(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void updateSysByCodeShuoms(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new DictHisDrugDao().updateSysByCodeShuoms(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record getShuoms_file(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictHisDrugDao().getShuoms_file(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Map<String, Object> searchByDrugTag(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("drug", new DictHisDrugDao().searchByDrugTag(params).getResultset());
			return result;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result queryKJYPropertyToxi(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictHisDrugDao().queryKJYPropertyToxi(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void updateTag(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("drug_code"))) {
				List<String> list = new ArrayList<String>();
				if(params.get("drug_code") instanceof String) {
					list.add((String)params.get("drug_code"));
				}else {
					String[] s = (String[])params.get("drug_code");
					for (String string : s) {
						list.add(string);
					}
				}
				params.put("drug_code", list);
				new DictHisDrugDao().updateTag(params);
			}else {
				params.put("drug_code", null);
				new DictHisDrugDao().updateTag(params);
			}
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Result queryAttrTree(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictHisDrugDao().queryAttrTree(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public void disposeAttr(Map<String, Object> params) throws Exception {
		List<Map<String, Object>> list = CommonFun.json2Object((String)params.get("data"), List.class);
		if(list.size() > 0) {
			List<Record> ins_data = new ArrayList<Record>();
			String drug_code = (String) params.get("drug_code");
			int i = 1;
			for (Map<String, Object> map : list) {
				Record r = new Record();
				r.put("drug_code", drug_code);
				r.put("xmid", map.get("xmid"));
				r.put("displayorder", i);
				r.put("xiangm", map.get("xmmch"));
				r.put("value", map.get("attr_value"));
				r.put("dw", map.get("xmdw"));
				ins_data.add(r);
				i++;
			}
			try {
				connect("hospital_common");
				start();
				new DictHisDrugDao().deleteAttrByDrugCode(params);
				Util.executeBatchInsert("dict_his_drug_mx", ins_data, this.getConnection());
				commit();
			} catch (Exception e) {
				rollback();
				throw e;
			}finally {
				disconnect();
			}
		}
	}
}
