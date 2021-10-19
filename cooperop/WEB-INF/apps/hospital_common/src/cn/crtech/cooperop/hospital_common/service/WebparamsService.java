package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.WebparamsDao;

public class WebparamsService extends BaseService {

	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect();
			int r = new WebparamsDao().insert(params);
			return r;
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect();
			int r = new WebparamsDao().delete(params);
			return r;
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect();
			int r = new WebparamsDao().update(params);
			return r;
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new WebparamsDao().query(params);
		} finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			if (CommonFun.isNe(params.get("data_webservice_quene_id")) ||
					CommonFun.isNe(params.get("order_no")))
				return new Record();
			return new WebparamsDao().get(params);
		} finally {
			disconnect();
		}
	}
}
