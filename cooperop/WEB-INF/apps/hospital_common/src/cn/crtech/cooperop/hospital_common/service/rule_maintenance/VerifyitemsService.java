package cn.crtech.cooperop.hospital_common.service.rule_maintenance;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.rule_maintenance.VerifyitemchildDao;
import cn.crtech.cooperop.hospital_common.dao.rule_maintenance.VerifyitemsDao;

public class VerifyitemsService extends BaseService{
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			return new VerifyitemsDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			start();
			new VerifyitemsDao().insert(params);
			commit();
		} catch (Exception e) {
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> findbyid(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			return new VerifyitemsDao().findbyid(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
		
	}
	
    public void delete(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			start();
			Map<String, Object> map = new HashedMap();
			map.put("parent_id", params.get("id"));
			new VerifyitemchildDao().delete(map);
			new VerifyitemsDao().delete(params);
			commit();
		} catch (Exception e) {
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public void update(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			start();
			new VerifyitemsDao().update(params);
			commit();
		} catch (Exception e) {
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}
}
