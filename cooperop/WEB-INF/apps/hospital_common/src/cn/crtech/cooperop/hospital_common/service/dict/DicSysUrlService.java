package cn.crtech.cooperop.hospital_common.service.dict;

import java.util.Date;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.dict.DictSysUrlDao;

public class DicSysUrlService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictSysUrlDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("create_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			new DictSysUrlDao().insert(params);
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("update_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			new DictSysUrlDao().update(params);
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delUpdate(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			params.put("state", -1);
			new DictSysUrlDao().update(params);
			return 1;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new DictSysUrlDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

}
