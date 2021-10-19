package cn.crtech.cooperop.application.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Result;

public class ChartDao extends BaseDao {
	
	public Result queryCharts(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  *                                                         ");
		sql.append(" from    system_charts" + "(nolock)");
		sql.append(" where   view_id=:view_id and view_flag=:view_flag and system_product_code=:system_product_code");
		setParameter(params, "chart_flag", " and flag= :chart_flag", sql);
	    return executeQuery(sql.toString(), params);
	}
	
	public Result queryChartSchemes(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  *                                                         ");
		sql.append(" from    system_charts_scheme" + "(nolock)");
		sql.append(" where   system_charts_id=:system_charts_id");
	    return executeQuery(sql.toString(), params);
	}
	
	public Result queryChartPlot(Map<String, Object> params) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  *                                                         ");
		sql.append(" from    system_charts_plot" + "(nolock)");
		sql.append(" where   system_charts_id=:system_charts_id");
	    return executeQuery(sql.toString(), params);
	}
}
