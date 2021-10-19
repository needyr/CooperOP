package cn.crtech.cooperop.bus.workflow.core.service;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.workflow.core.AutoTaskHandler;
import cn.crtech.cooperop.bus.workflow.core.CountersignAssignment;
import cn.crtech.cooperop.bus.workflow.core.JudgeHandle;
import cn.crtech.cooperop.bus.workflow.core.PostInterceptor;
import cn.crtech.cooperop.bus.workflow.core.PreInterceptor;
import cn.crtech.cooperop.bus.workflow.core.RouteHandle;
import cn.crtech.cooperop.bus.workflow.core.TaskAssignment;
import cn.crtech.cooperop.bus.workflow.core.TaskExpireCallback;
import cn.crtech.cooperop.bus.workflow.core.WorkFlowEngine;
import cn.crtech.cooperop.bus.workflow.core.dao.WorkFlowDao;

public class WorkFlowService extends BaseService {
	public static final String FLOW_NAME_SPLIT = "-";
	
	public Record get(String system_product_code, String id) throws Exception {
		try {
			connect("base");
			WorkFlowDao dd = new WorkFlowDao();
			Record flow = dd.get(system_product_code, id);
			ObjectInputStream ois = null;
			
			try {
				ois = new ObjectInputStream(new ByteArrayInputStream(flow.getBytes("content")));
			} catch (Exception ex) {
				throw ex;
			} finally {
				if (ois != null) ois.close();
			}
			
			Map<String, Object> sppc = (Map<String, Object>) ois.readObject();
			flow.put("content", sppc);
			
			Record process = new Record();
			process.put("type", "process");
			process.put("name", flow.get("system_product_code") + FLOW_NAME_SPLIT + flow.get("id"));
			process.put("displayName", flow.get("name"));
			process.put("expireTime", CommonFun.isNe(sppc.get("expireTime")) ? null : sppc.get("expireTime"));
//			process.put("instanceNoClass", NoGenerator.class.getName());
			process.put("instanceUrl", "");
			
			List<Map<String, Object>> pnodes = (List<Map<String, Object>>) sppc.get("nodes"); 
			List<Map<String, Object>> proutes = (List<Map<String, Object>>) sppc.get("routes"); 
			List<Record> nodes = new ArrayList<Record>(); 
			if (pnodes != null) {
				Record node;
				for (Map<String, Object> pnode : pnodes) {
					node = new Record();
					node.put("name", pnode.get("id"));
					node.put("displayName", pnode.get("name"));
					node.put("preInterceptors", PreInterceptor.class.getName());
					node.put("postInterceptors", PostInterceptor.class.getName());
					
					if ("start".equals(pnode.get("type"))) {
						node.put("type", "start");
					} else if ("end".equals(pnode.get("type"))) {
						node.put("type", "end");
					} else if ("judge".equals(pnode.get("type"))) {
						node.put("type", "decision");
						node.put("expr", CommonFun.isNe(pnode.get("expr")) ? null : pnode.get("expr"));
						node.put("handleClass", CommonFun.isNe(pnode.get("expr_scheme")) ? null : JudgeHandle.class.getName());
					} else if ("task".equals(pnode.get("type"))) {
						node.put("type", "task");
						node.put("taskType", "Major");
						node.put("assignmentHandler", TaskAssignment.class.getName());
						node.put("performType", "ANY");
						node.put("reminderTime", CommonFun.isNe(pnode.get("reminderTime")) ? null : pnode.get("reminderTime"));
						node.put("reminderRepeat", CommonFun.isNe(pnode.get("reminderRepeat")) ? null : pnode.get("reminderRepeat"));
						node.put("expireTime", CommonFun.isNe(pnode.get("expireTime")) ? null : pnode.get("expireTime"));
						node.put("autoExecute", CommonFun.isNe(pnode.get("expireTime")) ? "N" : "Y");
						node.put("callback", CommonFun.isNe(pnode.get("expireTime")) ? null : TaskExpireCallback.class.getName());
					} else if ("countersign".equals(pnode.get("type"))) {
						node.put("type", "task");
						node.put("taskType", "Major");
						node.put("assignmentHandler", CountersignAssignment.class.getName());
						node.put("performType", "ALL");
						node.put("reminderTime", CommonFun.isNe(pnode.get("reminderTime")) ? null : pnode.get("reminderTime"));
						node.put("reminderRepeat", CommonFun.isNe(pnode.get("reminderRepeat")) ? null : pnode.get("reminderRepeat"));
						node.put("expireTime", CommonFun.isNe(pnode.get("expireTime")) ? null : pnode.get("expireTime"));
						node.put("autoExecute", CommonFun.isNe(pnode.get("expireTime")) ? "N" : "Y");
						node.put("callback", CommonFun.isNe(pnode.get("expireTime")) ? null : TaskExpireCallback.class.getName());
					} else if ("auto".equals(pnode.get("type"))) {
						node.put("type", "custom");
						node.put("clazz", AutoTaskHandler.class.getName());
					} else if ("state".equals(pnode.get("type"))) {
						node.put("type", "task");
						node.put("taskType", "Major");
						node.put("assignee", WorkFlowEngine.STATE_NODE_OPERATOR);
					}else if ("sub-process".equals(pnode.get("type"))) {
						node.put("type", "subprocess");
						node.put("processName", pnode.get("processname"));
					}
					if (proutes != null) {
						List<Record> routes = new ArrayList<Record>(); 
						Record route;
						for (Map<String, Object> proute : proutes) {
							if (pnode.get("id").equals(proute.get("fromNode"))) {
								route = new Record();
								route.put("type", "transition");
								route.put("to", proute.get("toNode"));
								route.put("name", proute.get("id"));
								route.put("displayName", proute.get("name"));
								route.put("expr", CommonFun.isNe(proute.get("expr")) ? null : proute.get("expr"));
								route.put("handleClass", CommonFun.isNe(proute.get("expr_scheme")) ? null : RouteHandle.class.getName());
								routes.add(route);
							}
						}
						node.put("routes", routes);
					}
					
					nodes.add(node);
				}
			}	
			process.put("nodes", nodes);
			flow.put("wf_content", process);
			flow.put("wf_xml", obj2xml(process));
			System.out.println(flow.get("wf_xml"));
			return flow;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public static String FREEFLOW() throws Exception {
		StringBuffer str = new StringBuffer();
		str.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n");
		str.append("<process displayName=\"自由流\" instanceUrl=\"/flow/free/all\" name=\"free\"/>");
		return str.toString();
	}
	
	private StringBuffer obj2xml(Map<String, Object> control) throws Exception {
		StringBuffer str = new StringBuffer();
		str.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\r\n");
		str.append(objxml(control));
		return str;
	}

	private StringBuffer objxml(Map<String, Object> control) throws Exception {
		StringBuffer node = new StringBuffer();
		String type = (String) control.get("type");
		List<Map<String, Object>> nodes = (List<Map<String, Object>>) control.get("nodes");
		List<Map<String, Object>> routes = (List<Map<String, Object>>) control.get("routes");

		node.append("<" + type);
		Iterator<String> it = control.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			if (control.get(key) != null && !"type".equals(key) && !"nodes".equals(key) && !"routes".equals(key)) {
				node.append(" " + key + "=\"" + control.get(key) + "\"");
			}
		}
		node.append(">\r\n");
		if (nodes != null) {
			for (Map<String, Object> c : nodes) {
				node.append(objxml(c));
			}
		}
		if (routes != null) {
			for (Map<String, Object> c : routes) {
				node.append(objxml(c));
			}
		}
		node.append("</" + type + ">\r\n");
		return node;
	}

	public Record getRecord(String system_product_code, String id) throws Exception {
		try {
			connect("base");
			WorkFlowDao dd = new WorkFlowDao();
			Record flow = dd.get(system_product_code, id);
			return flow;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Record getRecordByWFProcessId(String wf_process_id) throws Exception {
		try {
			connect("base");
			WorkFlowDao dd = new WorkFlowDao();
			Record flow = dd.getByWFProcessId(wf_process_id);
			return flow;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Record getRecordByDJ(String system_product_code, String instance_bill) throws Exception {
		try {
			connect("base");
			WorkFlowDao dd = new WorkFlowDao();
			Record flow = dd.getByInstanceBill(system_product_code, instance_bill);
			return flow;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Record getNode(String system_product_code, String system_product_process_id, String id) throws Exception {
		try {
			connect("base");
			WorkFlowDao dd = new WorkFlowDao();
			Record flow = dd.getNode(system_product_code, system_product_process_id, id);
			return flow;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public Record getRoute(String system_product_code, String system_product_process_id, String fromid, String toid) throws Exception {
		try {
			connect("base");
			WorkFlowDao dd = new WorkFlowDao();
			Record flow = dd.getRoute(system_product_code, system_product_process_id, fromid, toid);
			return flow;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int updateProcessId(String system_product_code, String id, String wf_process_id) throws Exception {
		try {
			connect("base");
			WorkFlowDao dd = new WorkFlowDao();
			start();
			int i = dd.update(system_product_code, id, wf_process_id);
			commit();
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public String getNo(String system_product_code, String system_product_process_id) throws Exception {
		String no = null;
		try {
			connect("base");
			WorkFlowDao dd = new WorkFlowDao();
			start();
			Record flow = dd.get(system_product_code, system_product_process_id);
			if (flow != null) {
				no = dd.getBusinessNo(flow.getString("no_prix"));
			}
			commit();
			return no;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public String[] executeAssignmentScheme(String system_product_code, String sql, String djbh) throws Exception {
		try {
			connect(system_product_code);
			WorkFlowDao dd = new WorkFlowDao();
			Record params = new Record();
			params.put("djbh", djbh);
			Result rs = dd.executeQuery(sql, params);
			List<String> rtn = new ArrayList<String>();
			if (rs.getCount() > 0) {
				Iterator<String> keys = rs.getResultset(0).keySet().iterator();
				while (keys.hasNext()) {
					String key = keys.next();
					if (!Result.ROWNO_NAME.equals(key)) {
						for (Record r : rs.getResultset()) {
							rtn.add(r.getString(key));
						}
					}
				}
			}
			String[] tmp = new String[rtn.size()];
			for(int i=0;i<rtn.size();i++){
				tmp[i]=rtn.get(i);
			}
			return tmp;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public String executeExprScheme(String system_product_code, String sql, String djbh, String audited, String advice, Map<String, Object> args) throws Exception {
		try {
			connect(system_product_code);
			WorkFlowDao dd = new WorkFlowDao();
			Record params = new Record();
			params.putAll(args);
			params.put("djbh", djbh);
			params.put("audited", audited);
			params.put("advice", advice);
			Result rs = dd.executeQuery(sql, params);
			if (rs.getCount() > 0) {
				Iterator<String> keys = rs.getResultset(0).keySet().iterator();
				while (keys.hasNext()) {
					String key = keys.next();
					if (!Result.ROWNO_NAME.equals(key)) {
						return rs.getResultset(0).getString(key);
					}
				}
			}
			return null;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void executeAutoScheme(String system_product_code, String sql, String djbh, String audited, String advice, Map<String, Object> args) throws Exception {
		try {
			connect(system_product_code);
			WorkFlowDao dd = new WorkFlowDao();
			Record params = new Record();
			params.putAll(args);
			params.put("djbh", djbh);
			params.put("audited", audited);
			params.put("advice", advice);
			dd.execute(sql, params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public String[] getActorList(String system_role_id, String system_department_id) throws Exception {
		try {
			connect("base");
			WorkFlowDao dd = new WorkFlowDao();
			Result result = dd.getActorList(system_department_id, system_role_id);
			List<Record> resultSet = result.getResultset();
			List<String> actorList = new ArrayList<>();
			for (Record record : resultSet) {
				String systemUserId = record.getString("system_user_id");
				if(!CommonFun.isNe(systemUserId)){
					actorList.add(systemUserId);
				}
			}
			String[] rtn = new String[actorList.size()];
			for (int i = 0; i < actorList.size(); i ++) {
				rtn[i] = actorList.get(i);
			}
			return rtn;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public String[] getActorListByPost(String system_post_id) throws Exception {
		try {
			connect("base");
			WorkFlowDao dd = new WorkFlowDao();
			Result result = dd.getActorListByPost(system_post_id);
			List<Record> resultSet = result.getResultset();
			List<String> actorList = new ArrayList<>();
			for (Record record : resultSet) {
				String systemUserId = record.getString("system_user_id");
				if(!CommonFun.isNe(systemUserId)){
					actorList.add(systemUserId);
				}
			}
			String[] rtn = new String[actorList.size()];
			for (int i = 0; i < actorList.size(); i ++) {
				rtn[i] = actorList.get(i);
			}
			return rtn;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record getByTaskId(String task_id) throws Exception {
		try {
			connect("base");
			Record r = new Record();
			WorkFlowDao wd = new WorkFlowDao();
			r.put("node", wd.getTask(task_id).get("task_name"));
			Record p = wd.getByTaskId(task_id);
			r.put("process", p.get("id"));
			r.put("system_product_code", p.get("system_product_code"));
			return r;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record getNode(String task_id, String nodeid) throws Exception {
		try {
			connect("base");
			return new WorkFlowDao().getNode(task_id, nodeid);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public String checkCountersign(String order_id, String system_product_process_id, String node_id, String task_id) throws Exception {
		try {
			connect("base");
			Record check = new WorkFlowDao().checkCountersign(order_id, system_product_process_id, node_id, task_id);
			if(check.getInt("s_num") > 0){//设置会签成功数大于0
				if((check.getInt("y_num") >= check.getInt("s_num")) || (check.getInt("w_num")==0 && check.getInt("n_num") == 0)){//通过数>=设置数 || 全部审核完成，并且全部通过
					//通过
					return "Y";
				}else if(check.getInt("n_num") > 0 &&(check.getInt("w_num") + check.getInt("y_num") < check.getInt("s_num"))){//有驳回，且待审核数加通过数小于设置数
					//驳回
					return "N";
				}else{
					//会签没结束
					return "W";
				}
			}else{//设置会签成功数等于0，没设置，则全部通过才通过，否则驳回
				if(check.getInt("n_num") > 0){
					//驳回
					return "N";
				}else if(check.getInt("w_num") == 0){
					//通过
					return "Y";
				}else{
					//会签没结束
					return "W";
				}
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
