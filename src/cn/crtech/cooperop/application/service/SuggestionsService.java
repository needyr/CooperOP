package cn.crtech.cooperop.application.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.dao.SuggestionsDao;
import cn.crtech.cooperop.application.dao.TaskDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;


public class SuggestionsService extends BaseService {
	public Result querySuggestions(Map<String, Object> params) throws Exception {
		try {
			connect("base");
			TaskDao td = new TaskDao();
			if(CommonFun.isNe(params.get("order_id"))){
				Record r = td.getOrder(null,(String)params.get("djbh"));
				if(r != null){
					params.put("order_id", r.get("id"));
				}else{
					Record r1 = td.getHistOrder(null,(String)params.get("djbh"));
					params.put("order_id", r1.get("id"));;
				}
			}
			SuggestionsDao sd = new SuggestionsDao();
			return sd.querySuggestions(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Result queryMine(Map<String, Object> params) throws Exception {
		try {
			connect();
			SuggestionsDao td = new SuggestionsDao();
			return td.queryMine(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryActors(Map<String, Object> params) throws Exception {
		try {
			connect();
			SuggestionsDao td = new SuggestionsDao();
			return td.queryActors(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("base");
			SuggestionsDao sd = new SuggestionsDao();
			TaskDao td = new TaskDao();
			if(CommonFun.isNe(params.get("order_id"))){
				Record r = td.getOrder(null,(String)params.get("djbh"));
				if(r != null){
					params.put("order_id", r.get("id"));
				}else{
					Record r1 = td.getHistOrder(null,(String)params.get("djbh"));
					params.put("order_id", r1.get("id"));;
				}
			}
			Record rtn = sd.getCCOrder(params);
			if(rtn != null){
				List<Record> res = sd.queryActors(params).getResultset();
				Object[] s = new Object[res.size()];
				for(int i = 0; i<res.size(); i++){
					s[i] = res.get(i).get("actor_id");
				}
				rtn.put("hchryv", s);
				rtn.put("hchry", res);
				if (CommonFun.isNe(rtn.get("finish_Time"))) {
					//sd.saveRead(rtn.getInt("suggestions_id"));
					sd.saveRead((String)params.get("order_id"));
				}
				return rtn;
			}else{
				return new Record();
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void saveRead(Map<String, Object> params) throws Exception {
		try {
			connect();
			SuggestionsDao td = new SuggestionsDao();
			if(!CommonFun.isNe(params.get("order_id"))){
				td.saveRead((String)params.get("order_id"));
			}else{
				td.saveRead(Integer.parseInt((String)params.get("id")));
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public int querySuggestionsCount(Map<String, Object> params) throws Exception {
		try {
			connect();
			SuggestionsDao td = new SuggestionsDao();
			return td.querySuggestionsCount(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void save(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			TaskDao t = new TaskDao();
			String djbh = (String)params.remove("djbh");
			if(CommonFun.isNe(params.get("order_id"))){
				Record r = t.getOrder(null,djbh);
				if(r != null){
					params.put("order_id", r.get("id"));
				}else{
					Record r1 = t.getHistOrder(null,djbh);
					params.put("order_id", r1.get("id"));;
				}
			}
			if(CommonFun.isNe(params.get("order_id"))){
				return;
			}
			List<Map<String, Object>> list = (List<Map<String, Object>>) params.remove("hchry");
			SuggestionsDao td = new SuggestionsDao();
			params.put("creator", user().getId());
			params.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			if(CommonFun.isNe(params.get("info_bill"))){
				Record r = new TaskDao().getProcess((String)params.get("order_id")).getResultset(0);
				params.put("pageid", r.get("info_bill"));
				params.remove("info_bill");
			}else{
				params.put("pageid", params.remove("info_bill"));
			}
			int suggestions_id = td.save(params);
			
			if(list != null){
				for(int i=0; i<list.size(); i++){
					list.get(i);
					Map<String, Object> cs = new HashMap<String , Object>();
					cs.put("actor_id", list.get(i).get("actor_id"));
					cs.put("order_id", params.get("order_id"));
					cs.put("creator", user().getId());
					cs.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					cs.put("suggestions_id", suggestions_id);
					td.saveCCOrder(cs);
				}
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
}
