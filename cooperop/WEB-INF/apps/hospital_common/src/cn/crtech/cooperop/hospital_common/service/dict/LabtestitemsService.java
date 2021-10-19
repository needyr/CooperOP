package cn.crtech.cooperop.hospital_common.service.dict;

import java.util.Map;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.dao.dict.LabtestitemsDao;

public class LabtestitemsService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new LabtestitemsDao().query(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	
	public Result queryLabReportItem(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new LabtestitemsDao().queryLabReportItem(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	

	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new LabtestitemsDao().insert(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}

	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new LabtestitemsDao().update(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new LabtestitemsDao().delete(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}

	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new LabtestitemsDao().get(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}


}
