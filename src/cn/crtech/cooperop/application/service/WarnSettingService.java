package cn.crtech.cooperop.application.service;

import java.lang.reflect.Method;
import java.util.Map;

import cn.crtech.cooperop.application.dao.WarnSettingDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class WarnSettingService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new WarnSettingDao().query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new WarnSettingDao().get(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void save(Map<String, Object> params) throws Exception {
		try {
			connect();
			new WarnSettingDao().insert(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void update(Map<String, Object> params) throws Exception {
		try {
			connect();
			new WarnSettingDao().update(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void delete(Map<String, Object> params) throws Exception {
		try {
			connect();
			new WarnSettingDao().delete(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record initWarn(Map<String, Object> params) throws Exception {
		try {
			connect();
			Record rtn = new Record();
			rtn.put("sorts", new WarnSettingDao().querySort(params));
			rtn.put("warns", new WarnSettingDao().queryByRule(params));
			return rtn;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result initWarnContent(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("action"))){
			String action = (String)params.get("action");
			String cla = action.substring(0, action.lastIndexOf("."));
			String methed = action.substring(action.lastIndexOf(".") + 1);
			Class<?> c = Class.forName(cla);
			Method m = c.getDeclaredMethod(methed, String.class);
			Object rtn = m.invoke(c.newInstance());
			return (Result)rtn;
		}else if(!CommonFun.isNe(params.get("scheme"))){
			try {
				connect((String)params.get("system_product_code"));
				Record r = new Record();
				r.put("jigid", user().getJigid());
				r.put("userid", user().getId());
				return new WarnSettingDao().executeCallQuery("{call "+ params.get("scheme") +"(:jigid, :userid)}", r);
			} catch (Exception ex) {
				throw ex;
			} finally {
				disconnect();
			}
		}
		return new Result();
	}
	
	
	public Result querySort(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new WarnSettingDao().querySort(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record getSort(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new WarnSettingDao().getSort(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void saveSort(Map<String, Object> params) throws Exception {
		try {
			connect();
			new WarnSettingDao().insertSort(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void updateSort(Map<String, Object> params) throws Exception {
		try {
			connect();
			new WarnSettingDao().updateSort(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void deleteSort(Map<String, Object> params) throws Exception {
		try {
			connect();
			new WarnSettingDao().deleteSort(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
