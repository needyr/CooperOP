package cn.crtech.cooperop.hospital_common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.TPNDao;

public class TPNService extends BaseService{
	
	public String queryTpn(Map<String, Object> params) throws Exception {
		try {
			Result result = new Result();
			Map<String, Object> rtn = new HashMap<String, Object>();
			//pie图
			/*try {
				connect("ipc");
				TPNDao tpnPieDao = new TPNDao();
				result = tpnPieDao.queryTpnPie(params);
			} catch (Exception ex) {
				throw ex;
			} finally {
				disconnect();
			}
			if (result.getCount()>0) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				List<Record> resultset = result.getResultset();
				for (Record record : resultset) {
					Map<String, Object> map = new HashMap<String, Object>();
					double parseDouble = Double.parseDouble((String)record.get("value"));
					map.put("name", record.get("fdname"));
					map.put("y", parseDouble);
					list.add(map);
				}
				rtn.put("pie",list);
			}*/
			//-------------------
			//line图
			/*try {
				connect("ipc");
				TPNDao tpn = new TPNDao();
				result = tpn.queryTpnLine(params);
				params.remove("sort");
			} catch (Exception ex) {
				throw ex;
			} finally {
				disconnect();
			}
			if (result.getCount()>0) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				List<Record> resultset = result.getResultset();
				Map<String, Object> map2 = new HashMap<String, Object>();
				Map<String, Object> current_map = new HashMap<String, Object>();
				for (Record record : resultset) {
					Map<String, Object> map = new HashMap<String, Object>();
					double x_value = Double.parseDouble((String)record.get("x_value"));
					double y_value = Double.parseDouble((String)record.get("y_value"));
					double current_x_value = Double.parseDouble((String)record.get("current_x_value"));
					double current_y_value = Double.parseDouble((String)record.get("current_y_value"));
					map.put("x", x_value);
					map.put("y", y_value);
					list.add(map);
					if (!map2.containsKey("current")) {
						current_map.put("x", current_x_value);
						current_map.put("y", current_y_value);
						map2.put("current", current_map);
					}
				}
				map2.put("value", list);
				rtn.put("line",map2);
			}*/
			//-------------------
			//雷达图
			try {
				connect("ipc");
				TPNDao tpn = new TPNDao();
				result = tpn.queryTpnRadar(params);
			} catch (Exception ex) {
				throw ex;
			} finally {
				disconnect();
			}
			if (result.getCount()>0) {
				List<Map<String, Double>> max_list = new ArrayList<Map<String, Double>>();
				List<Map<String, Double>> min_list = new ArrayList<Map<String, Double>>();
				List<Map<String, Double>> current_list = new ArrayList<Map<String, Double>>();
				List<String> x_name = new ArrayList<String>();
				Map<String, Object> rtn_map = new HashMap<String, Object>();
				List<Record> resultset = result.getResultset();
				for (Record record : resultset) {
					Map<String, Double> max_map = new HashMap<String, Double>();
					Map<String, Double> min_map = new HashMap<String, Double>();
					Map<String, Double> current_map = new HashMap<String, Double>();
					double value_current = Double.parseDouble((String)record.get("value_current"));
					double value_highest = Double.parseDouble((String)record.get("value_highest"));
					double value_lowest = Double.parseDouble((String)record.get("value_lowest"));
					double show_value_current = Double.parseDouble((String)record.get("show_value_current"));
					double show_value_highest = Double.parseDouble((String)record.get("show_value_highest"));
					double show_value_lowest = Double.parseDouble((String)record.get("show_value_lowest"));
					max_map.put("real_y", value_highest);
					max_map.put("y", show_value_highest);
					max_list.add(max_map);
					min_map.put("real_y", value_lowest);
					min_map.put("y", show_value_lowest);
					min_list.add(min_map);
					current_map.put("real_y", value_current);
					current_map.put("y", show_value_current);
					current_list.add(current_map);
					x_name.add(record.getString("show_fdname"));
				}
				rtn_map.put("x_name", x_name);
				rtn_map.put("show_max_list", max_list);
				rtn_map.put("show_min_list", min_list);
				rtn_map.put("show_current_list", current_list);
				rtn_map.put("table", resultset);
				rtn.put("radar",rtn_map);
			}
			//-------------------
			return CommonFun.object2Json(rtn);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Map<String, Object> getTPNData(Map<String, Object> map) throws Exception {
		try {
			connect("ipc");
			Map<String, Object> result = new HashMap<String, Object>();
			if(CommonFun.isNe(map.get("history"))) {
				result = new TPNDao().getTPNData(map);
			}else {
				map.remove("history");
				result = new TPNDao().getTPNDataHistory(map);
			}
			return result;
		}catch(Exception e){
			throw e;
		} finally {
			disconnect();
		}
	}
	
}
