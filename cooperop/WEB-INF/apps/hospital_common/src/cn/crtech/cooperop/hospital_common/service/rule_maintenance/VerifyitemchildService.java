package cn.crtech.cooperop.hospital_common.service.rule_maintenance;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.rule_maintenance.VerifyitemchildDao;

public class VerifyitemchildService extends BaseService{

    public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("guide");
			return new VerifyitemchildDao().query(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
    
    public void insert(Map<String, Object> params) throws Exception {
    	try {
			connect("guide");
			start();
			new VerifyitemchildDao().insert(params);
			commit();
		} catch (Exception e) {
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}
    
    public void delete(Map<String, Object> params) throws Exception {
    	try {
			connect("guide");
			start();
			new VerifyitemchildDao().delete(params);
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
			return new VerifyitemchildDao().findbyid(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
    
    public void update(Map<String, Object> params) throws Exception{
    	try {
			connect("guide");
			start();
			new VerifyitemchildDao().update(params);
			commit();
		} catch (Exception e) {
			rollback();
			throw e;
		} finally {
			disconnect();
		}
    }
    
}
