package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.WeblogDao;

public class WeblogService extends BaseService {

	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect();
			int r = new WeblogDao().insert(params);
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
			int r = new WeblogDao().delete(params);
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
			int r = new WeblogDao().update(params);
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
			Result r = new WeblogDao().query(params);
			for (Record rd: r.getResultset()) {
				switch (Integer.parseInt((String)rd.remove("state"))) {
					case 0: rd.put("state", "待交易"); break;
					case 1: rd.put("state", "已发送"); break;
					case 2: rd.put("state", " 已返回"); break;
					case 3: rd.put("state", "已写入临时表"); break;
					case 4: rd.put("state", "存储过程执行完成(完成)"); break;
					case -1: rd.put("state", "异常"); break;
				}
			}
			return r;
		} finally {
			disconnect();
		}
	}
	public Result queryDB(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new WeblogDao().queryDB(params);
		} finally {
			disconnect();
		}
	}
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			if (CommonFun.isNe(params.get("id")))
				return new Record();
			return new WeblogDao().get(params);
		} finally {
			disconnect();
		}
	}
}
