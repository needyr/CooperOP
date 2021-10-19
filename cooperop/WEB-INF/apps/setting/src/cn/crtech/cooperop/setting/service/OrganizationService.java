package cn.crtech.cooperop.setting.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.setting.dao.OrganizationDao;

public class OrganizationService extends BaseService{

	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new OrganizationDao().insert(params);
		} finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect();
			Object state = params.get("state");
			if (!CommonFun.isNe(state)) {
				int se = Integer.parseInt(state.toString());
				if (se != 0 && se != 1) {
					throw new Exception("state 取值不正确.");
				}
			}
			return new OrganizationDao().update((String)params.remove("id"), params);
		} finally {
			disconnect();
		}
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new OrganizationDao().query(params);
		} finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new OrganizationDao().get((String)params.get("id"), (String)params.get("jigid"));
		} finally {
			disconnect();
		}
	}
	
}
