package cn.crtech.cooperop.pivascockpit.action;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.application.authenticate.DisValidPermission;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.pivascockpit.service.ChartService;
import cn.crtech.cooperop.pivascockpit.service.CockpitService;

public class PivascockpitAction extends BaseAction {
	
//	private static ConcurrentHashMap<String, Record> charts = new ConcurrentHashMap<String, Record>();
//	
//	public static Record getChart(String code) {
//		//TODO: 修改chart定义时需要调用putChart重新载入
//		if (charts.size() == 0) {
//			try {
//				Record params = new Record();
//				List<Record> cs = new ChartService().query(params).getResultset();
//				for (Record c : cs) {
//					charts.put(c.getString("code"), c);
//				}
//			} catch (Exception e) {
//				log.error("载入图表缓存信息失败", e);
//			}
//		}
//		return charts.get(code);
//	}
//	public static void putChart(Record chart) {
//		charts.put(chart.getString("code"), new Record(chart));
//	}
//	public static Record removeChart(String code) {
//		return charts.remove(code);
//	}
	
	public enum STATISTICS_TYPE {
		cockpit, full;
		public static STATISTICS_TYPE get(String str) {
			for (STATISTICS_TYPE st : STATISTICS_TYPE.values()) {
				if (st.toString().equals(str)) return st;
			}
			return null;
		}
	}
	
	@DisLoggedIn
	public Record index(Map<String, Object> req) throws Exception {
		//TODO: 这里需要查询的是授权后的驾驶舱菜单8888000000下的子菜单项，现在是开发用临时使用的。
		Record params = new Record();
		params.put("state", 1);
		params.put("limit", -1);
		List<Record> cockpits = new CockpitService().query(params).getResultset();
		params.clear();
		params.put("cockpits", cockpits);
		return params;
	}

	@DisLoggedIn
	public Record cockpit(Map<String, Object> req) throws Exception {
		if (CommonFun.isNe(req.get("id"))) {
			throw new Exception("错误的请求");
		}
		return new CockpitService().get(new Record(req).getInt("id"));
	}

	@DisLoggedIn
	public Result statistics(Map<String, Object> req) throws Exception {
		STATISTICS_TYPE type = STATISTICS_TYPE.get((String)req.get("type"));
		String code = (String)req.get("code");
		if (CommonFun.isNe("code") || type == null) {
			throw new Exception("错误的请求");
		}
		Map<String, Object> params = null;
		try {
			String json = (String) req.get("params");
			params = CommonFun.json2Object(json, Map.class);
		}catch(Exception e) {}
		return new CockpitService().statistics(code, type, params);
	}
}
