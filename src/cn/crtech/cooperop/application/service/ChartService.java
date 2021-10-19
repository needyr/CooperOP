package cn.crtech.cooperop.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.dao.BillDao;
import cn.crtech.cooperop.application.dao.ChartDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;


public class ChartService extends BaseService {
	private Map<String, Object> splitPage(String pageid) {
		String[] t = pageid.split("\\.");
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("system_product_code", t[0]);
		rtn.put("type", t[1]);
		rtn.put("view_flag", t[2]);
		rtn.put("view_id", t[3]);
		return rtn;
	}
	public Map<String, Object> initChart(Map<String, Object> params) throws Exception {
		try {
			connect();
			Map<String, Object> rtn = new HashMap<String, Object>();
			List<Map<String, Object>> charts = new ArrayList<Map<String, Object>>();
			ChartDao cd1 = new ChartDao();
			Map<String, Object> pageinfo = splitPage((String) params.get("pageid"));
			params.putAll(pageinfo);
			if(user() != null){
				setUserParams(params);
			}
			Result res  = cd1.queryCharts(params);
			if(res !=null){
				for(Record chartr :res.getResultset()){
					Map<String, Object> chart = new HashMap<String, Object>();
					chart.put("flag", chartr.get("flag"));
					chart.put("title", chartr.get("title"));
					chart.put("subtitle", chartr.get("subtitle"));
					chart.put("subtitle_href", chartr.get("subtitle_href"));
					chart.put("chart_width", chartr.get("chart_width"));
					chart.put("chart_height", chartr.get("chart_height"));
					chart.put("g_name", chartr.get("g_name"));
					chart.put("group_field", chartr.get("group_field"));
					chart.put("drill_chart", chartr.get("drill_chart"));
					chart.put("color", chartr.get("color"));
					chart.put("drill_chart_name", chartr.get("drill_chart_name"));
					chart.put("stacking", chartr.get("stacking"));
					chart.put("chart_type", chartr.get("chart_type"));
					if("3".equals(chartr.get("chart_type"))){
						Record r = new Record();
						r.put("system_charts_id", chartr.get("id"));
						Result p = cd1.queryChartPlot(r);
						disconnect();
						connect((String) pageinfo.get("system_product_code"));
						BillDao bd = new BillDao();
						Result xres = bd.executeQuery(bd.relpaceParams(chartr.getString("yaxis_sql"), params), params);
						chart.put("min_num", xres.getResultset(0).get(chartr.getString("min_num")));
						chart.put("max_num", xres.getResultset(0).get(chartr.getString("max_num")));
						chart.put("default_num", getResultString(xres, chartr.getString("default_num")));
						chart.put("autorefresh", chartr.get("autorefresh"));
						chart.put("num_unit", chartr.get("num_unit"));
						chart.put("refresh_time", chartr.get("refresh_time"));
						chart.put("plots", p.getResultset());
					}else if("2".equals(chartr.get("chart_type"))){
						chart.put("span_head", chartr.get("span_head"));
						chart.put("span_content", chartr.get("span_content"));
						chart.put("span_footer", chartr.get("span_footer"));
						chart.put("subtitle", chartr.get("subtitle"));
						chart.put("subtitle_href", chartr.get("subtitle_href"));
						disconnect();
						connect((String)pageinfo.get("system_product_code"));
						BillDao cd = new BillDao();
						if(!CommonFun.isNe(chartr.get("yaxis_sql"))){
							String sqll = cd.relpaceParams(chartr.getString("yaxis_sql"), params);
							Result yres = cd.executeQuery(cd.relpaceParams(sqll, params), params);
							if(yres!= null && yres.getResultset().size()> 0){
								chart.put("countMap", yres.getResultset(0));
							}
						}
					}else if("4".equals(chartr.get("chart_type"))){
						chart.put("x_name", chartr.get("x_name"));
						chart.put("y_name", chartr.get("y_name"));
						chart.put("plotline_xname", chartr.get("plotline_xname"));
						chart.put("plotline_xvalue", chartr.get("plotline_xvalue"));
						chart.put("plotline_yname", chartr.get("plotline_yname"));
						chart.put("plotline_yvalue", chartr.get("plotline_yvalue"));
						disconnect();
						
						connect((String)pageinfo.get("system_product_code"));
						BillDao cd = new BillDao();
						if(!CommonFun.isNe(chartr.getString("xaxis_sql"))){
							Result xres = cd.executeQuery(cd.relpaceParams(chartr.getString("xaxis_sql"), params), params);
							chart.put("xaxis", xres.getResultset());
						}
						if(!CommonFun.isNe(chartr.get("yaxis_sql"))){
							String sqll = cd.relpaceParams(chartr.getString("yaxis_sql"), params);
							Result yres = cd.executeQueryScheme(sqll, null, params);
							chart.put("yaxis", yres.getResultset());
							chart.put("plotline_xvalue",  yres.getResultset(0).get(chartr.get("plotline_xvalue")));
							chart.put("plotline_yvalue",  yres.getResultset(0).get(chartr.get("plotline_yvalue")));
						}
						
					}else{
						Record r = new Record();
						r.put("system_charts_id", chartr.get("id"));
						Result schemeres = cd1.queryChartSchemes(r);
						disconnect();
						
						connect((String)pageinfo.get("system_product_code"));
						BillDao cd = new BillDao();
						Result xres = cd.executeQuery(cd.relpaceParams(chartr.getString("xaxis_sql"), params), params);
						
						Result yres = new Result();
						if(!CommonFun.isNe(chartr.get("yaxis_sql"))){
							//yres = cd.executeQuery(chartr.getString("yaxis_sql"), params);
							String sqll = cd.relpaceParams(chartr.getString("yaxis_sql"), params);
							yres = cd.executeQueryScheme(sqll, null, params);
						}
						List<Map<String, Object>> yaxis = new ArrayList<Map<String, Object>>();
						
						for(Record scheme : schemeres.getResultset()){
							if("1".equals(scheme.get("nosql"))){
								if(CommonFun.isNe(chart.get("group_field"))){
									scheme.put("series", yres.getResultset());
								}else{
									List<Map<String, Object>> groupf = new ArrayList<Map<String, Object>>();
									Object o = chart.get("group_field");
									for(Record gr : yres.getResultset()){
										boolean b = true;
										for(Map<String, Object> m : groupf){
											if(gr.get(o).equals(m.get("g_field"))){
												((List<Map<String, Object>>)m.get("series")).add(gr);
												b = false;
												break;
											}
										}
										
										if(b){
											Map<String, Object> map = new HashMap<String, Object>(); 
											map.put("g_field", gr.get(o));
											List<Map<String ,Object>> l = new ArrayList<Map<String ,Object>>();
											l.add(gr);
											map.put("series", l);
											groupf.add(map);
										}
									}
									scheme.put("groupf", groupf);
								}
							}else{
								scheme.put("series", cd.executeQuery(cd.relpaceParams(scheme.getString("yaxis_sql"), params), params));
							}
							yaxis.add(scheme);
						}
						chart.put("xaxis", xres.getResultset());
						chart.put("yaxis", yaxis);
					}
					charts.add(chart);
				}
				rtn.put("charts", charts);
			}
			return rtn;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Map<String, Object> queryPlotNum(Map<String, Object> params) throws Exception {
		try {
			connect();
			Map<String, Object> rtn = new HashMap<String, Object>();
			ChartDao cd = new ChartDao();
			Map<String, Object> pageinfo = splitPage((String) params.get("pageid"));
			params.putAll(pageinfo);
			Result res  = cd.queryCharts(params);
			Record chart = res.getResultset(0);
			disconnect();
			connect((String)pageinfo.get("system_product_code"));
			BillDao bd = new BillDao();
			if("3".equals(chart.get("chart_type"))){
				Result xres = bd.executeQuery(bd.relpaceParams(chart.getString("xaxis_sql"), params), params);
				rtn.put("plot", getResultString(xres, chart.getString("default_num")));
			}else if ("2".equals(chart.get("chart_type"))){
				Result xres = bd.executeQuery(bd.relpaceParams(chart.getString("xaxis_sql"), params), params);
				if(xres != null & xres.getResultset().size()> 0){
					rtn = xres.getResultset(0);
				}
			}
			return rtn;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public String getResultString(Result rs, String def) throws Exception {
		if (rs.getCount() > 0) {
			Iterator<String> keys = rs.getResultset(0).keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				if (!Result.ROWNO_NAME.equals(key)) {
					String s = rs.getResultset(0).getString(key);
					if(s != null){
						return s;
					}
				}
			}
		}
		return def;
	}

	public void setUserParams(Map<String, Object> req){
		req.put("userid", user().getId());
		req.put("userno", user().getNo());
		req.put("uname", user().getName());
		req.put("depcode", CommonFun.isNe(user().getBaseDepCode())?0:user().getBaseDepCode());
		req.put("depname", user().getBaseDepName());
		req.put("depid", user().getBmid());
		req.put("jigid", CommonFun.isNe(user().getJigid())?"000":user().getJigid());
		req.put("jigname", user().getJigname());
		req.put("positionid", CommonFun.isNe(user().getPosition())?0:user().getPosition());
		// req.put("positionname", user().getPositionName());
		// req.put("postid", CommonFun.isNe(user().getPost())?0:user().getPost());
		req.put("postname", user().getPostName());
		req.put("businessid", CommonFun.isNe(user().getBusinessId())?0:user().getBusinessId());
		//req.put("ontime", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
	}
}
