package cn.crtech.cooperop.pivascockpit.service;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.pivascockpit.action.PivascockpitAction.STATISTICS_TYPE;
import cn.crtech.cooperop.pivascockpit.dao.ChartDao;
import cn.crtech.cooperop.pivascockpit.dao.CockpitDao;

public class ChartService extends BaseService {

	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			ChartDao cd = new ChartDao();
			
			return cd.query(params);
		} finally {
			disconnect();
		}
	}

	public Record get(String code) throws Exception {
		try {
			connect();
			ChartDao cd = new ChartDao();
			
			Record c = cd.get(code);
			
			if (c == null) throw new Exception("数据未找到");
			
			return c;
		} finally {
			disconnect();
		}
	}

	public int save(String code, Map<String, Object> params) throws Exception {
		try {
			connect();
			ChartDao cd = new ChartDao();

			start();
			int i = cd.update(code, params);
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
