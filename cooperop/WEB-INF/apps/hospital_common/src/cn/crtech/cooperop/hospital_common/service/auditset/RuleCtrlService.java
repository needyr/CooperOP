package cn.crtech.cooperop.hospital_common.service.auditset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.auditset.CheckLevelDao;
import cn.crtech.cooperop.hospital_common.dao.auditset.RuleCtrlDao;

public class RuleCtrlService extends BaseService{

	public Result query(Map<String, Object> params) throws Exception{
		try {
			connect("hospital_common");
			return new RuleCtrlDao().query(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void insert(Map<String, Object> params) throws Exception{
		try {
			connect("hospital_common");
			new RuleCtrlDao().insert(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public void update(Map<String, Object> params) throws Exception{
		try {
			connect("hospital_common");
			new RuleCtrlDao().update(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Map<String, Object> get(Map<String, Object> params) throws Exception{
		try {
			connect("hospital_common");
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			rtnMap.put("level_list", new CheckLevelDao().queryAllLevel(params).getResultset());
			if(!CommonFun.isNe(params.get("id"))) {
				Record info = new RuleCtrlDao().get(params);
				if(!CommonFun.isNe(info)) {
					rtnMap.putAll(info);
				}
			}
			return rtnMap;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record getByCondition(Map<String, Object> params) throws Exception{
		try {
			connect("hospital_common");
			return new RuleCtrlDao().getByCondition(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception{
		try {
			connect("hospital_common");
			return new RuleCtrlDao().delete(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Map<String, Object> queryLevel(Map<String, Object> params) throws Exception{
		try {
			connect("hospital_common");
			Map<String, Object> rtnMap = new HashMap<String, Object>();
			List<Record> list = new CheckLevelService().queryAllLevel(params).getResultset();
			for (Record record : list) {
				 rtnMap.put("lv" + record.getString("level_code"), record);
			}
			String json = CommonFun.object2Json(rtnMap);
			rtnMap.clear();
			rtnMap.put("lvs", json);
			rtnMap.put("lvs_arr", CommonFun.object2Json(list));
			return rtnMap;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			disconnect();
		}
	}
	

}
