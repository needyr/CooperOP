package cn.crtech.cooperop.application.service;

import java.util.Map;

import cn.crtech.cooperop.application.dao.DataTableDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DataTableService extends BaseService {
	public Map<String, Object> getSetting(Record req) throws Exception {
		try {
			connect();
			DataTableDao dtd = new DataTableDao();
			Record t = dtd.getSetting(req);
			if (t == null) return null;
			return CommonFun.json2Object(t.getString("setting"), Map.class);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public int saveSetting(Record req) throws Exception {
		try {
			connect();
			DataTableDao dtd = new DataTableDao();
			req.put("setting", CommonFun.object2Json(req.get("setting")));
			int i = dtd.updateSetting(req);
			if (i == 0) {
				i = dtd.insertSetting(req);
			}
			return i;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
