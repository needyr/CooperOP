package cn.crtech.cooperop.hospital_common.service.trade;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.trade.ConfigDao;

public class ConfigService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("data_transfer");
			return new ConfigDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("data_transfer");
			//params.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			new ConfigDao().insert(params);
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("data_transfer");
			//params.put("update_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			new ConfigDao().update(params);
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("data_transfer");
			new ConfigDao().delete(params);
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("data_transfer");
			return new ConfigDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

}
