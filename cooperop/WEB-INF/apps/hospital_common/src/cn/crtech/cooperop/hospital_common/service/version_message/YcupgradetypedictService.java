package cn.crtech.cooperop.hospital_common.service.version_message;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.version_message.YcupgradetypedictDao;

public class YcupgradetypedictService extends BaseService{
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new YcupgradetypedictDao().query(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new YcupgradetypedictDao().delete(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}finally {
			disconnect();
		}
	}

	public void insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new YcupgradetypedictDao().insert(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Map<String, Object> getByTypeid(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new YcupgradetypedictDao().getByTypeid(params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void update(Map<String, Object> params) throws Exception{
		try {
			connect("hospital_common");
			new YcupgradetypedictDao().update(params);;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}finally {
			disconnect();
		}
	}
}
