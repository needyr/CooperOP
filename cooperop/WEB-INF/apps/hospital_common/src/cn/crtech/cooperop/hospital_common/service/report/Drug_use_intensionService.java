package cn.crtech.cooperop.hospital_common.service.report;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.report.Drug_use_intensionDao;

public class Drug_use_intensionService extends BaseService {
	public Result query_dui(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}
			if(!CommonFun.isNe(params.get("drug_type")) && params.get("drug_type") instanceof String[]) {
				params.put("drug_type", getString((String[]) params.get("drug_type")));
			}
			if(!CommonFun.isNe(params.get("kjy_drug_level")) && params.get("kjy_drug_level") instanceof String[]) {
				params.put("kjy_drug_level", getString((String[]) params.get("kjy_drug_level")));
			}
			return new Drug_use_intensionDao().query_dui(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	public Result query_expend_dept_zj(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}
			if(!CommonFun.isNe(params.get("drug_type")) && params.get("drug_type") instanceof String[]) {
				params.put("drug_type", getString((String[]) params.get("drug_type")));
			}
			if(!CommonFun.isNe(params.get("kjy_drug_level")) && params.get("kjy_drug_level") instanceof String[]) {
				params.put("kjy_drug_level", getString((String[]) params.get("kjy_drug_level")));
			}
			return new Drug_use_intensionDao().query_expend_dept_zj(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	public Result query_expend_doctor_zj(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}
			if(!CommonFun.isNe(params.get("drug_type")) && params.get("drug_type") instanceof String[]) {
				params.put("drug_type", getString((String[]) params.get("drug_type")));
			}
			if(!CommonFun.isNe(params.get("kjy_drug_level")) && params.get("kjy_drug_level") instanceof String[]) {
				params.put("kjy_drug_level", getString((String[]) params.get("kjy_drug_level")));
			}
			return new Drug_use_intensionDao().query_expend_doctor_zj(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	public Result query_expend_stats(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}
			if(!CommonFun.isNe(params.get("drug_type")) && params.get("drug_type") instanceof String[]) {
				params.put("drug_type", getString((String[]) params.get("drug_type")));
			}
			if(!CommonFun.isNe(params.get("kjy_drug_level")) && params.get("kjy_drug_level") instanceof String[]) {
				params.put("kjy_drug_level", getString((String[]) params.get("kjy_drug_level")));
			}
			return new Drug_use_intensionDao().query_expend_stats(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	public Result query_dept_dui(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}
			if(!CommonFun.isNe(params.get("drug_type")) && params.get("drug_type") instanceof String[]) {
				params.put("drug_type", getString((String[]) params.get("drug_type")));
			}
			if(!CommonFun.isNe(params.get("kjy_drug_level")) && params.get("kjy_drug_level") instanceof String[]) {
				params.put("kjy_drug_level", getString((String[]) params.get("kjy_drug_level")));
			}
			return new Drug_use_intensionDao().query_dept_dui(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	public Result query_expend_dept_dui(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}
			if(!CommonFun.isNe(params.get("drug_type")) && params.get("drug_type") instanceof String[]) {
				params.put("drug_type", getString((String[]) params.get("drug_type")));
			}
			if(!CommonFun.isNe(params.get("kjy_drug_level")) && params.get("kjy_drug_level") instanceof String[]) {
				params.put("kjy_drug_level", getString((String[]) params.get("kjy_drug_level")));
			}
			return new Drug_use_intensionDao().query_expend_dept_dui(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	public Result query_expend_doctor_dui(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}
			if(!CommonFun.isNe(params.get("drug_type")) && params.get("drug_type") instanceof String[]) {
				params.put("drug_type", getString((String[]) params.get("drug_type")));
			}
			if(!CommonFun.isNe(params.get("kjy_drug_level")) && params.get("kjy_drug_level") instanceof String[]) {
				params.put("kjy_drug_level", getString((String[]) params.get("kjy_drug_level")));
			}
			return new Drug_use_intensionDao().query_expend_doctor_dui(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	public Result query_dept_zj(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}
			if(!CommonFun.isNe(params.get("drug_type")) && params.get("drug_type") instanceof String[]) {
				params.put("drug_type", getString((String[]) params.get("drug_type")));
			}
			if(!CommonFun.isNe(params.get("kjy_drug_level")) && params.get("kjy_drug_level") instanceof String[]) {
				params.put("kjy_drug_level", getString((String[]) params.get("kjy_drug_level")));
			}
			return new Drug_use_intensionDao().query_dept_zj(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	public Result query_dti(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("incision_type")) && params.get("incision_type") instanceof String[]) {
				params.put("incision_type", getString((String[]) params.get("incision_type")));
			}
			if(!CommonFun.isNe(params.get("drug_type")) && params.get("drug_type") instanceof String[]) {
				params.put("drug_type", getString((String[]) params.get("drug_type")));
			}
			if(!CommonFun.isNe(params.get("kjy_drug_level")) && params.get("kjy_drug_level") instanceof String[]) {
				params.put("kjy_drug_level", getString((String[]) params.get("kjy_drug_level")));
			}
			return new Drug_use_intensionDao().query_dti(params);
		} catch (Exception e) {
			throw e;
		}
		finally {
			disconnect();
		}
	}
	
	public String getString(String[] str) {
		String new_str = "";
		for (int i=0;i<str.length;i++) {
			if(i == 0) {
				new_str = str[i];
			}else {
				new_str = new_str + "," + str[i];
			}
		}
		return new_str;
	}
}
