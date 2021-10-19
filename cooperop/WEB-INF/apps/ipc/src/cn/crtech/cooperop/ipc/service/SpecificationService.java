package cn.crtech.cooperop.ipc.service;

import java.util.Date;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.dao.SpecificationDao;

public class SpecificationService extends BaseService {
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new SpecificationDao().query(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			Record record=new SpecificationDao().get(params);
			return record;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			params.put("revision_date", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			return new SpecificationDao().insert(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			params.put("revision_date", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			return new SpecificationDao().update(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Result isHas(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new SpecificationDao().isHas(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new SpecificationDao().delete(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}

}
