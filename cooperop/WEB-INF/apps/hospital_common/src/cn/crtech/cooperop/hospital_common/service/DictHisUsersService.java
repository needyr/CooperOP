package cn.crtech.cooperop.hospital_common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.DictHisDrugDao;
import cn.crtech.cooperop.hospital_common.dao.DictHisUsersDao;

public class DictHisUsersService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictHisUsersDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void updateByCode(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new DictHisUsersDao().updateByCode(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Map<String, Object> searchByUserTag(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("doctor", new DictHisUsersDao().searchByUserTag(params).getResultset());
			return result;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void updateTag(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(!CommonFun.isNe(params.get("user_id"))) {
				List<String> list = new ArrayList<String>();
				if(params.get("user_id") instanceof String) {
					list.add((String)params.get("user_id"));
				}else {
					String[] s = (String[])params.get("user_id");
					for (String string : s) {
						list.add(string);
					}
				}
				params.put("user_id", list);
				new DictHisUsersDao().updateTag(params);
			}else{
				params.put("user_id", null);
				new DictHisUsersDao().updateTag(params);
			}
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
