package cn.crtech.cooperop.hospital_common.service.version_message;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.version_message.YcmoduledictDao;

public class YcmoduledictService extends BaseService{
	public Result query(Map<String, Object> params) throws Exception{
		try {
			connect("hospital_common");
			return new YcmoduledictDao().query(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public void delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new YcmoduledictDao().delete(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public void insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new YcmoduledictDao().insert(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> getByModuleid(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new YcmoduledictDao().getByModuleid(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public void update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new YcmoduledictDao().update(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			disconnect();
		}
	}
	
    public Result queryMmodulename(Map<String, Object> params) throws Exception {
    	try {
			connect("hospital_common");
			return new YcmoduledictDao().queryMmodulename(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			disconnect();
		}
	}
}
