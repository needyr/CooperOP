package cn.crtech.cooperop.hospital_common.service.auditset;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.auditset.MapchecklevelDao;
import cn.crtech.cooperop.hospital_common.dao.auditset.CheckLevelDao;

public class MapchecklevelService extends BaseService {

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new MapchecklevelDao().query(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}

	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new MapchecklevelDao().insert(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}

	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new MapchecklevelDao().update(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}

	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new MapchecklevelDao().delete(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			if(CommonFun.isNe(params.get("m_p_key"))){
				return null;
			}
			return new MapchecklevelDao().get(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}
	
	public Result queryAllLevel(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new CheckLevelDao().query(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}
	
}
