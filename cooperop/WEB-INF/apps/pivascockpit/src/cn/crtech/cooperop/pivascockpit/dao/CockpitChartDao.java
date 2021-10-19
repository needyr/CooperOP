package cn.crtech.cooperop.pivascockpit.dao;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.model.BaseDao;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;

public class CockpitChartDao extends BaseDao {

	public static final String TABLE_NAME = "pcp_cockpit_chart";
	
	public Result query(int cockpit_id) throws Exception {
		Record params = new Record();
		params.put("cockpit_id", cockpit_id);
		
		StringBuffer sql = new StringBuffer();
		
		sql.append("select c.cockpit_id, c.cockpit_tab_order_no, d.code, d.name, d.refresh_time, d.childcode, dc.name as childname, c.xaxis, c.yaxis, c.width, c.height ");
		sql.append("  from " + TABLE_NAME + " c ");
		sql.append(" inner join " + CockpitDao.TABLE_NAME + " cd on cd.id = c.cockpit_id ");
		sql.append(" inner join " + CockpitTabDao.TABLE_NAME + " t on t.cockpit_id = cd.id and t.order_no = c.cockpit_tab_order_no ");
		sql.append(" inner join " + ChartDao.TABLE_NAME + " d on d.code = c.chart_code ");
		sql.append("  left join " + ChartDao.TABLE_NAME + " dc on dc.code = d.childcode ");
		sql.append(" where c.cockpit_id = :cockpit_id ");
		
		return executeQuery(sql.toString(), params);
	}

	public int deleteCockpitCharts(int cockpit_id) throws Exception {
		Record conditions = new Record();
		conditions.put("cockpit_id", cockpit_id);
		return executeDelete(TABLE_NAME, conditions);
	}

	public int insert(int cockpit_id, int cockpit_tab_order_no, Map<String, Object> chart) throws Exception {
		chart.put("cockpit_id", cockpit_id);
		chart.put("cockpit_tab_order_no", cockpit_tab_order_no);
		return executeInsert(TABLE_NAME, chart);
	}

}
