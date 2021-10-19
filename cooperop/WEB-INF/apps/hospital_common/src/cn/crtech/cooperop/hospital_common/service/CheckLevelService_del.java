package cn.crtech.cooperop.hospital_common.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.CheckLevelDao_del;

public class CheckLevelService_del extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckLevelDao_del().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			if (CommonFun.isNe(params.get("sys_check_level"))) {
				return null;
			}
			return new CheckLevelDao_del().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public int insert(Map<String, Object> params) throws Exception {
		int i=0;
		Map<String, Object> sysMap=new HashMap<String, Object>();
		sysMap.put("sys_check_level", params.get("sys_check_level"));
		sysMap.put("system_product_code", params.remove("system_product_code"));
		sysMap.put("star_level", params.remove("star_level"));
		sysMap.put("sys_check_level_name", params.remove("sys_check_level_name"));
		sysMap.put("is_active", params.remove("is_active"));
		try {
			connect();
			start();
			CheckLevelDao_del checkleveldao = new CheckLevelDao_del();
			if ((checkleveldao.isExist(sysMap))==null) {
				i= checkleveldao.insertSys(sysMap);
				i+= checkleveldao.insert(params);
			}
			commit();
			return i;
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}

	public int update(Map<String, Object> params) throws Exception {
		int i=0;
		Map<String, Object> sysMap=new HashMap<String, Object>();
		sysMap.put("sys_check_level", params.get("sys_check_level"));
		sysMap.put("system_product_code", params.remove("system_product_code"));
		sysMap.put("star_level", params.remove("star_level"));
		sysMap.put("sys_check_level_name", params.remove("sys_check_level_name"));
		sysMap.put("is_active", params.remove("is_active"));
		sysMap.put("old_sys_check_level", params.remove("old_sys_check_level"));
		try {
			connect();
			start();
			CheckLevelDao_del checkleveldao = new CheckLevelDao_del();
			i= checkleveldao.updateSys(sysMap);
			i+= checkleveldao.update(params);
			commit();
			return i;
		} catch (Exception e) {
			rollback();
			throw e;
		}finally {
			disconnect();
		}
	}

	public int updateActive(Map<String, Object> params) throws Exception {
		try {
			connect();
			String string = (String) params.get("is_active");
			if (string.equals("1")) {
				string = "0";
				params.put("is_active", string);
			}else{
				string = "1";
				params.put("is_active", string);
			}
			int i = new CheckLevelDao_del().updateSys(params);
			return i;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public int delete(Map<String, Object> params) throws Exception{
		Map<String, Object> sysMap=new HashMap<String, Object>();
		int i=0;
		sysMap.put("sys_check_level",params.remove("sys_check_level"));
		sysMap.put("system_product_code", params.remove("system_product_code"));
		try {
			connect();
			CheckLevelDao_del checkleveldao = new CheckLevelDao_del();
			i+=checkleveldao.deleteSys(sysMap);
			i+=checkleveldao.delete(params);
			return i;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
}
