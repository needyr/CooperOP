package cn.crtech.cooperop.crdc.service.scheme;

import java.util.Map;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.crdc.action.scheme.SchemeUtil;
import cn.crtech.cooperop.crdc.dao.DesignerDao;
import cn.crtech.cooperop.crdc.dao.scheme.DataretrievalDao;

public class DataretrievalService extends BaseService {
	public void save(Map<String, Object> map) throws Exception {
		try {
			connect();
			new DataretrievalDao().save(map);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Record querySingle(Map<String, Object> map) throws Exception {
		try {
			connect();
			Record record = new DataretrievalDao().querySingle(map);
			SchemeUtil.inToString(record);
			return record;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void update(Map<String, Object> map) throws Exception {
		try {
			connect();
			new DataretrievalDao().update(map);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Result query(Map<String, Object> map) throws Exception {
		try {
			connect();
			return new DataretrievalDao().query(map);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void delete(Map<String, Object> map) throws Exception {
		try {
			connect();
			new DataretrievalDao().delete(map);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
