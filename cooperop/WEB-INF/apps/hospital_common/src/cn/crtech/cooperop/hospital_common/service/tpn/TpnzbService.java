package cn.crtech.cooperop.hospital_common.service.tpn;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.tpn.TpnzbDao;

public class TpnzbService extends BaseService{
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new TpnzbDao().query(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}

	public Result queryTpnzbMX(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new TpnzbDao().queryTpnzbMX(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	//插入信息
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new TpnzbDao().insert(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}
	//插入信息
		public int insertZbmx(Map<String, Object> params) throws Exception {
			try {
				connect("hospital_common");
				return new TpnzbDao().insertZbmx(params);
			} catch (Exception e) {
				throw e;
			} finally {
				disconnect();
			}

		}
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new TpnzbDao().update(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	public int updateZbmx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new TpnzbDao().updateZbmx(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new TpnzbDao().delete(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}
	
	public int deleteZbmx(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new TpnzbDao().deleteZbmx(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}

	}

	public Record edit(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new TpnzbDao().edit(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Record zbmxEdit(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new TpnzbDao().zbmxEdit(params);
		} catch (Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	

}
