package cn.crtech.cooperop.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.dao.TaskDao;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.LocalThreadMap;
import cn.crtech.cooperop.bus.workflow.workflow;
import cn.crtech.cooperop.bus.workflow.core.WorkFlowEngine;


public class TaskService extends BaseService {
	public Result queryMine(Map<String, Object> params) throws Exception {
		try {
			connect();
			TaskDao td = new TaskDao();
			return td.queryMine(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result listProcess(Map<String, Object> params) throws Exception {
		try {
			connect();
			TaskDao td = new TaskDao();
			return td.listProcess(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record queryTaskCount(Map<String, Object> params) throws Exception {
		try {
			connect();
			TaskDao td = new TaskDao();
			return td.queryTaskCount(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryTasking(Map<String, Object> params) throws Exception {
		try {
			connect();
			TaskDao td = new TaskDao();
			return td.queryTasking(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryHistory(Map<String, Object> params) throws Exception {
		try {
			connect();
			TaskDao td = new TaskDao();
			return td.queryHistory(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void autoSubmitFromCS() throws Exception {
		try {
			connect("base");
			TaskDao td = new TaskDao();
			Result res = td.queryTaskFromCS();
			disconnect();
			for(int i=0;i<res.getResultset().size();i++){
				Record r = res.getResultset(i);
				LocalThreadMap.put("_wf_creator", r.getString("operator"));
				System.out.println("djbh====="+r.getString("djbh")+"==i=="+i);
				try{
					if(!CommonFun.isNe(r.get("processname"))){
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("processname", r.getString("processname"));
						workflow.start(r.getString("system_product_code"), r.getString("pageid"), r.getString("djbh"), 
								(CommonFun.isNe(r.get("bmid"))? user().getBaseDepCode() :r.getString("bmid")), m);
					}else{
						workflow.start(r.getString("system_product_code"), r.getString("pageid"), r.getString("djbh"), 
								(CommonFun.isNe(r.get("bmid"))? user().getBaseDepCode() :r.getString("bmid")));
					}
					LocalThreadMap.remove("_wf_creator");
					r.put("started", 1);
				}catch (Exception e) {
					r.put("started", 4);
					e.printStackTrace();
				}
				connect("base");
				td.updateTaskFromCS(r);
				disconnect();
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void autoBackProcessFromCS() throws Exception {
		try {
			connect("base");
			TaskDao td = new TaskDao();
			Result res = td.queryBackProcessFromCS();
			disconnect();
			for(int i=0;i<res.getResultset().size();i++){
				Record r = res.getResultset(i);
				LocalThreadMap.put("_wf_creator", r.getString("operator"));
				System.out.println("djbh====="+r.getString("djbh")+"==i=="+i);
				connect("base");
				Result rt = new TaskDao().getTask(r.getString("djbh"));
				r.put("started", 2);//撤回
				td.updateTaskFromCS(r);
				disconnect();
				if(rt != null && rt.getResultset().size()>0){
					workflow.back(rt.getResultset(0).getString("id"), "用户自行撤销");
					LocalThreadMap.remove("_wf_creator");
				}
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public void autoPass() throws Exception {
		try {
			connect("base");
			TaskDao td = new TaskDao();
			Result res = td.queryAutoPassProcess();
			disconnect();
			for(Record r : res.getResultset()){
				WorkFlowEngine.applyAuto(r.getString("id"), "同意", r.getString("operator_"), r);
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Result queryFreeTask(Map<String, Object> params) throws Exception {
		try {
			connect();
			TaskDao td = new TaskDao();
			return td.queryFreeTask(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void saveFreeTask(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			TaskDao td = new TaskDao();
			Record tmp = new Record(params);
			int id;
			if (!CommonFun.isNe(params.get("djbh"))) {
				params.remove("djbh");
				id = td.insertFreeTask(params);
			}else{
				if (!CommonFun.isNe(params.get("id"))) {
					id = tmp.getInt("id");
					td.updateFreeTask(id, params);
				} else {
					id = td.insertFreeTask(params);
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
	public void cancelFreeTask(int id) throws Exception {
		try {
			connect();
			start();
			TaskDao td = new TaskDao();
			td.cancelFreeTask(id);
			Record tmp = td.getFreeTask(id);
			workflow.back(tmp.getString("task_id"), "用户自行撤销");
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void deleteFreeTask(int id) throws Exception {
		try {
			connect();
			start();
			TaskDao td = new TaskDao();
			td.deleteFreeTask(id);
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void startFreeTask(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			TaskDao td = new TaskDao();
			Record tmp = new Record(params);
			int id;
			if(!CommonFun.isNe(params.get("djbh"))){
				params.remove("djbh");
				id = td.insertFreeTask(params);
			}else{
				if (!CommonFun.isNe(params.get("id"))) {
					id = tmp.getInt("id");
					td.updateFreeTask(id, params);
				} else {
					id = td.insertFreeTask(params);
				}
			}
			
			td.startFreeTask(id);
			Record tmpe = td.getFreeTask(id);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			workflow.start("oa", tmpe.getString("subject"), "application.task.freetaskaudit", "application.task.freetaskdetail", tmpe.getString("djbh"), tmp.getString("operator"));
			if (!CommonFun.isNe(tmp.get("cc"))) {
				String[] cc = tmp.getString("cc").split(",");
				for (String c : cc) {
					workflow.ccProcess(tmpe.getString("djbh"), c);
					Map<String, Object> m = new HashMap<>();
					m.put("actor_id", c);
					list.add(m);
				}
			}
			commit();
			disconnect();
			
			connect("base");
			TaskDao td1 = new TaskDao();
			Record r = td1.getOrder(null, tmpe.getString("djbh"));
			disconnect();
			if(r != null){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("task_id", "");
				param.put("type", 3);
				param.put("message", "发起流程");
				param.put("order_id", r.get("id"));
				param.put("info_bill","application.task.freetaskaudit");
				param.put("hchry", list);
				new SuggestionsService().save(param);
			}
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record getFreeTask(int id) throws Exception {
		try {
			connect();
			TaskDao td = new TaskDao();
			return td.getFreeTask(id);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record getFreeTask(Map<String, Object> params) throws Exception {
		try {
			connect();
			TaskDao td = new TaskDao();
			if(CommonFun.isNe(params.get("djbh"))){
				Record r = td.getOrder((String)params.get("order_id"),null);
				if(r != null){
					params.put("djbh", r.get("order_no"));
				}else{
					Record r1 = td.getHistOrder((String)params.get("order_id"),null);
					params.put("djbh", r1.get("order_no"));;
				}
			}
			return td.getFreeTask((String)params.get("djbh"));
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void nextFreeTask(Map<String, Object> params) throws Exception {
		try {
			Record tmp = new Record(params);
			String task_id = (String) tmp.get("task_id");
			String advice = (String) tmp.get("advice");
			connect("base");
			TaskDao td = new TaskDao();
			//Record re = td.getFreeTask((String) params.get("djbh"));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("attach_files", params.get("attach_files"));
			td.updateFreeTask(Integer.parseInt((String)params.get("id")),map);
			
			Record r = td.get(task_id);
			Result res = td.getHistTaskActors(r.getString("order_id"));
			disconnect();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("message", advice);
			param.put("type", 2);
			param.put("task_id", task_id);
			param.put("order_id", r.get("order_id"));
			param.put("hchry", res.getResultset());
			param.put("info_bill","application.task.freetaskaudit");
			new SuggestionsService().save(param);
			workflow.next(tmp.getString("task_id"), tmp.getString("audited"), tmp.getString("advice"), tmp.getString("operator"));
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void finishFreeTask(Map<String, Object> params) throws Exception {
		try {
			Record tmp = new Record(params);
			String task_id = (String) tmp.get("task_id");
			String advice = (String) tmp.get("advice");
			connect("base");
			TaskDao td1 = new TaskDao();
			Record r = td1.get(task_id);
			//Result res = td1.getTaskActors(task_id);
			Result hres = td1.getHistTaskActors(r.getString("order_id"));
			disconnect();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("message", advice);
			param.put("type", 2);
			param.put("task_id", task_id);
			param.put("message", advice);
			param.put("order_id", r.get("order_id"));
			param.put("hchry", hres.getResultset());
			param.put("info_bill","application.task.freetaskaudit");
			new SuggestionsService().save(param);
			connect();
			start();
			TaskDao td = new TaskDao();
			td.finishFreeTask(tmp.getInt("id"));
			workflow.finish(tmp.getString("task_id"), tmp.getString("audited"), tmp.getString("advice"));
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void cancelFreeTask(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			TaskDao td = new TaskDao();
			Record tmp = new Record(params);
			td.cancelFreeTask(tmp.getInt("id"));
			workflow.finish(tmp.getString("task_id"), tmp.getString("audited"), tmp.getString("advice"));
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void deleteFreeTask(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			TaskDao td = new TaskDao();
			Record tmp = new Record(params);
			td.cancelFreeTask(tmp.getInt("id"));
			workflow.finish(tmp.getString("task_id"), tmp.getString("audited"), tmp.getString("advice"));
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Result listOrderTaskHistory(String djbh) throws Exception {
		try {
			connect("base");
			TaskDao td = new TaskDao();
			return td.listOrderTaskHistory(djbh);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public void skimsave(Map<String, Object> params) throws Exception {
		try {
			connect();
			new TaskDao().skimsave(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
