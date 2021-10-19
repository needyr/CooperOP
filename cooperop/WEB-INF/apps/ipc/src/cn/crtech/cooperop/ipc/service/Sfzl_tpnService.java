package cn.crtech.cooperop.ipc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.ipc.dao.Sfzl_tpnDao;
import cn.crtech.cooperop.ipc.util.Util;

public class Sfzl_tpnService extends BaseService{
	
	public Result query(Record parmas) throws Exception {
		try {
			connect("hospital_common");
			return new Sfzl_tpnDao().query(parmas);
		}catch(Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Result queryDrugMx(Record parmas) throws Exception {
		try {
			connect("hospital_common");
			return new Sfzl_tpnDao().queryDrugMx(parmas);
		}catch(Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	//处理公式映射关系的数据
	public Map<String, String> getformulMapping(Record params) throws Exception{
		try {
			connect("hospital_common");
			List<Record> list = new Sfzl_tpnDao().getformulMapping(params).getResultset();
			HashMap<String,String> map = new HashMap<String, String>(); 
			for (Record r : list) {
				map.put(r.getString("code"), r.getString("name"));
			}
			return map;
		}catch(Exception e) {
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public void insertRadarChart(List<Record> parmas) throws Exception {
		try {
			connect("ipc");
			start();
			Util.executeBatchInsert("cr_tmp_fenxitux_radarchart", parmas, this.getConnection());
			commit();
		}catch(Exception e) {
			rollback();
			throw e;
		} finally {
			disconnect();
		}
	}
	
	public Record getDrugByOrderCode(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new Sfzl_tpnDao().getDrugByOrderCode(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}

