package cn.crtech.cooperop.pivascockpit.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.pivascockpit.action.PivascockpitAction.STATISTICS_TYPE;
import cn.crtech.cooperop.pivascockpit.dao.ChartDao;
import cn.crtech.cooperop.pivascockpit.dao.CockpitChartDao;
import cn.crtech.cooperop.pivascockpit.dao.CockpitDao;
import cn.crtech.cooperop.pivascockpit.dao.CockpitTabDao;

public class CockpitService extends BaseService {

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			CockpitDao cd = new CockpitDao();

			return cd.query(params);
		} finally {
			disconnect();
		}
	}

	public Record get(int id) throws Exception {
		try {
			connect();
			CockpitDao cd = new CockpitDao();
			CockpitTabDao ctd = new CockpitTabDao();
			CockpitChartDao ccd = new CockpitChartDao();

			Record c = cd.get(id);

			if (c == null)
				throw new Exception("数据未找到");

			c.put("supporter", SystemConfig.getSystemConfigValue("pivascockpit", "supporter"));
			c.put("hotline", SystemConfig.getSystemConfigValue("pivascockpit", "hotline"));
			c.put("weixin", SystemConfig.getSystemConfigValue("pivascockpit", "weixin"));
			c.put("website", SystemConfig.getSystemConfigValue("pivascockpit", "website"));

			List<Record> tabs = ctd.query(c.getInt("id")).getResultset();

			Map<String, List<Record>> tcm = new HashMap<String, List<Record>>();
			List<Record> tcs = null;
			for (Record tab : tabs) {
				tcs = new ArrayList<Record>();
				tab.put("charts", tcs);
				tcm.put("T_" + tab.getInt("order_no"), tcs);
			}

			List<Record> charts = ccd.query(c.getInt("id")).getResultset();

			Record chart = null;
			while (charts.size() > 0) {
				chart = charts.remove(0);
				tcm.get("T_" + chart.getInt("cockpit_tab_order_no")).add(chart);
			}

			c.put("tabs", tabs);

			c.put("json", CommonFun.object2Json(c));

			return c;
		} finally {
			disconnect();
		}
	}

	public Result statistics(String chart_code, STATISTICS_TYPE type, Map<String, Object> params) throws Exception {
		try {
			connect();
			ChartDao cd = new ChartDao();
			
			Record chart = cd.get(chart_code);
			
			if (chart == null) throw new Exception("错误的请求");

			String sql = null;
			if (params == null) params = new Record();
			String sort = (String) params.get("sort");
			int limit = params.get("limit") == null ? 0 : (int) params.get("limit");
			params.put("limit", limit);
			switch (type) {
			case cockpit:
				sql = chart.getString("cockpitsql");
				
				if (CommonFun.isNe(sort) && !CommonFun.isNe(chart.get("cockpitsort"))) {
					params.put("sort", chart.get("cockpitsort"));
				}
				if (limit <= 0 && chart.getInt("cockpitlimit") > 0) {
					params.put("limit", chart.get("cockpitlimit"));
				}
				break;
			case full:
				sql = chart.getString("fullsql");
				if (CommonFun.isNe(sort) && !CommonFun.isNe(chart.get("fullsort"))) {
					params.put("sort", chart.get("fullsort"));
				}
				if (limit <= 0 && chart.getInt("fulllimit") > 0) {
					params.put("limit", chart.get("fulllimit"));
				}
			}
			return cd.executeQueryLimit(sql, params == null ? new Record() : params);
		} finally {
			disconnect();
		}
	}

	public int save(Map<String, Object> cockpit) throws Exception {
		try {
			List<Map<String, Object>> tabs = CommonFun.json2Object((String) cockpit.remove("tabs"), List.class) ;
			
			connect();
			CockpitDao cd = new CockpitDao();
			CockpitTabDao ctd = new CockpitTabDao();
			CockpitChartDao ccd = new CockpitChartDao();

			start();
			int id = 0;
			if (CommonFun.isNe(cockpit.get("id"))) {
				id = cd.insert(cockpit);
			} else {
				id = Integer.parseInt((String)cockpit.get("id"));
				cd.update(id, cockpit);
			}

			ctd.deleteCockpitTabs(id);
			ccd.deleteCockpitCharts(id);
			
			List<Map<String, Object>> charts;
			for (Map<String, Object> tab : tabs) {
				charts = (List<Map<String, Object>>) tab.remove("charts");
				ctd.insert(id, tab);
				for (Map<String, Object> chart : charts) {
					ccd.insert(id, (int)tab.get("order_no"), chart);
				}
			}
			
			commit();
			return 1;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int updateState(int id, int state) throws Exception {
		try {
			connect();
			CockpitDao cd = new CockpitDao();

			start();
			
			int i = cd.updateState(id, state);
			if (i == 0)
				throw new Exception("数据未找到");

			commit();
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int delete(int id) throws Exception {
		try {
			connect();
			CockpitDao cd = new CockpitDao();

			start();
			
			int i = cd.delete(id);
			if (i == 0)
				throw new Exception("数据未找到");

			commit();
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

}
