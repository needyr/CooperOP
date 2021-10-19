package cn.crtech.cooperop.hospital_common.service.syscustomreview;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.syscustomreview.SysSfccrDao;

public class SysSfccrService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysSfccrDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new SysSfccrDao().insert(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new SysSfccrDao().update(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			int res=new SysSfccrDao().delete(params);
			return res;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysSfccrDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
