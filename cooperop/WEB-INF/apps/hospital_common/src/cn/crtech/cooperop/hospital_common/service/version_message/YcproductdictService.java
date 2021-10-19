package cn.crtech.cooperop.hospital_common.service.version_message;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.version_message.YcproductdictDao;

public class YcproductdictService extends BaseService{
	
//	private YcproductdictDao ycDao = new YcproductdictDao();
	
	public Result query(Map<String, Object> params) throws Exception {
		
		try {
			connect("hospital_common");
			return new YcproductdictDao().query(params);
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
			new YcproductdictDao().delete(params);
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
			new YcproductdictDao().insert(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> getById(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new YcproductdictDao().getById(params);
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
			new YcproductdictDao().update(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}finally {
			disconnect();
		}
	}
}
