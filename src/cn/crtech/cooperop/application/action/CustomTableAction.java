package cn.crtech.cooperop.application.action;

import java.util.Map;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.application.service.DataTableService;
import cn.crtech.cooperop.bus.cache.MemoryCache;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;

@DisValidPermission
public class CustomTableAction extends BaseAction {

	private final static String AREA = "custom:setting";
	private final static String AREA_DATATABLE = AREA + ":datatable";

	private String getKey(Record req) throws Exception {
		StringBuffer key = new StringBuffer();
		key.append(req.getString("system_user_id"));
		key.append(":");
		if (!CommonFun.isNe(req.getString("company_id"))) {
			key.append(req.getString("company_id"));
			key.append(":");
		}
		key.append(req.getString("pageid"));
		key.append(":");
		key.append(req.getString("tableid"));
		return key.toString();
	}

	public Map<String, Object> get(Map<String, Object> req) throws Exception {
		if (user() == null) return null;
		req.put("system_user_id", user().getId());
		Record param = new Record(req);
		String key = getKey(param);
		Map<String, Object> rtn = null;
		rtn = CommonFun.json2Object((String)MemoryCache.get(AREA_DATATABLE,key), Map.class);
		if (rtn == null) {
			rtn = new DataTableService().getSetting(param);
			if (rtn != null) {
				MemoryCache.put(AREA_DATATABLE,key, CommonFun.object2Json(rtn));
			}
		}
		return rtn;
	}

	public Map<String, Object> save(Map<String, Object> params) throws Exception {
		Map<String, Object> req = CommonFun.json2Object((String)params.get("data"), Map.class);
		Record rtn = new Record();
		if (user() == null) {
			return rtn;
		}
		Record param = new Record(req);
		param.put("system_user_id", user().getId());

		String key = getKey(param);
		DataTableService dts = new DataTableService();
		dts.saveSetting(param);
		MemoryCache.put(AREA_DATATABLE,key, CommonFun.object2Json(dts.getSetting(param)));
		rtn.put("result", "success");
		return rtn;
	}
}
