package cn.crtech.cooperop.crdc.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
import cn.crtech.cooperop.crdc.dao.DesignerDao;
import cn.crtech.cooperop.crdc.dao.RuleFlowDesignerDao;

public class RuleFlowDesignerService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			RuleFlowDesignerDao dd = new RuleFlowDesignerDao();
			return dd.query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result queryFlowCount(Map<String, Object> req) throws Exception{
		try{
			connect();
			req.put("type", "simple");
			return new RuleFlowDesignerDao().queryFlowCount(req);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
		
	}
	public Map<String, Object> get(String system_product_code, String id) throws Exception {
		try {
			connect();
			RuleFlowDesignerDao dd = new RuleFlowDesignerDao();
			Record flow = dd.get(system_product_code, id);
			ObjectInputStream ois = null;

			try {
				ois = new ObjectInputStream(new ByteArrayInputStream(flow.getBytes("content")));
			} catch (Exception ex) {
				throw ex;
			} finally {
				if (ois != null)
					ois.close();
			}

			return (Map<String, Object>) ois.readObject();
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public int save(Map<String, Object> params) throws Exception {
		try {
			connect();
			RuleFlowDesignerDao dd = new RuleFlowDesignerDao();
			start();
			Record flow = new Record();
			flow.put("system_product_code", params.get("system_product_code"));
			flow.put("wf_process_id", params.get("wf_process_id"));
			flow.put("id", params.get("id"));
			flow.put("name", params.get("name"));
			flow.put("instance_bill", params.get("instance_bill"));
			flow.put("info_bill", params.get("info_bill"));
			flow.put("no_prix", params.get("no_prix"));
			flow.put("state", 0);
			flow.put("expireTime", params.get("expireTime"));
			flow.put("last_modify_time", "sysdate");
			flow.put("last_modifier", user().getId());
			flow.put("subject_scheme", params.get("subject_scheme"));
			flow.put("type", params.get("type"));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(params);
			oos.flush();
			flow.put("content", new ByteArrayInputStream(baos.toByteArray()));

			Record r = dd.get(flow.getString("system_product_code"), flow.getString("id"));
			int i = 0;
			if (r == null) {
				i = dd.insert(flow);
			} else {
				flow.put("wf_process_id", r.get("wf_process_id"));
				i = dd.update(flow.getString("system_product_code"), flow.getString("id"), flow);
			}
			dd.deleteNodes(flow.getString("system_product_code"), flow.getString("id"));
			List<Map<String, Object>> pnodes = (List<Map<String, Object>>) params.get("nodes");
			if (pnodes != null) {
				Record node;
				for (Map<String, Object> pnode : pnodes) {
					node = new Record();
					node.put("system_product_code", flow.get("system_product_code"));
					node.put("system_product_process_id", flow.get("id"));
					node.put("id", pnode.get("id"));
					node.put("name", pnode.get("name"));
					node.put("type", pnode.get("type"));
					node.put("instance_bill", pnode.get("instance_bill"));
					node.put("processor_scheme", pnode.get("processor_scheme"));
					node.put("processor_role", pnode.get("processor_role"));
					node.put("processor_post", pnode.get("processor_post"));
					node.put("wx_auth", pnode.get("wx_auth"));
					node.put("auto_pass", pnode.get("auto_pass"));
					node.put("processors", pnode.get("processors"));
					node.put("assistant_scheme", pnode.get("assistant_scheme"));
					node.put("assistant_role", pnode.get("assistant_role"));
					node.put("assistants", pnode.get("assistants"));
					node.put("countersign_scheme", pnode.get("countersign_scheme"));
					node.put("countersign_role", pnode.get("countersign_role"));
					node.put("countersigners", pnode.get("countersigners"));
					node.put("finish_num", pnode.get("finish_num"));
					node.put("state_scheme", pnode.get("state_scheme"));
					node.put("execute_scheme", pnode.get("execute_scheme"));
					node.put("expr_scheme", pnode.get("expr_scheme"));
					node.put("expireTime", pnode.get("expireTime"));
					node.put("reminderTime", pnode.get("reminderTime"));
					node.put("reminderRepeat", pnode.get("reminderRepeat"));
					node.put("processname", pnode.get("processname"));
					dd.insertNode(node);
				}
			}
			dd.deleteRoutes(flow.getString("system_product_code"), flow.getString("id"));
			List<Map<String, Object>> proutes = (List<Map<String, Object>>) params.get("routes");
			if (proutes != null) {
				Record route;
				for (Map<String, Object> proute : proutes) {
					route = new Record();
					route.put("system_product_code", flow.get("system_product_code"));
					route.put("system_product_process_id", flow.get("id"));
					route.put("fromnode", proute.get("fromNode"));
					route.put("tonode", proute.get("toNode"));
					route.put("expr_scheme", proute.get("expr_scheme"));
					dd.insertRoute(route);
				}
			}

			commit();
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}

	}

	public void undeploy(String system_product_code, String id) throws Exception {
		try {
			connect();
			Record r = new Record();
			r.put("state", 0);
			new RuleFlowDesignerDao().update(system_product_code, id, r);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public int delete(String system_product_code, String id) throws Exception {
		try {
			connect();
			RuleFlowDesignerDao dd = new RuleFlowDesignerDao();
			start();
			dd.deleteRoutes(system_product_code, id);
			dd.deleteNodes(system_product_code, id);
			int i = dd.delete(system_product_code, id);
			commit();
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void deploy(Map<String, Object> params) throws Exception {
		try {
			/*save(params);
			String system_product_code = (String) params.get("system_product_code");
			String id = (String) params.get("id");
			WorkFlowEngine.deploy(system_product_code, id);*/
			
			String system_product_code = (String) params.get("system_product_code");
			String id = (String) params.get("id");
			connect("base");
			RuleFlowDesignerDao dd = new RuleFlowDesignerDao();
			Record r = new Record();
			r.put("state", 1);
			dd.update(system_product_code, id, r);
			Record flow = dd.get(system_product_code, id);
			disconnect();
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
			
			String produce = "rule_process_" + flow.get("id");
			List<Map<String, Object>> pnodes = (List<Map<String, Object>>) sppc.get("nodes"); 
			List<Map<String, Object>> proutes = (List<Map<String, Object>>) sppc.get("routes");
			Map<String, Object> map = new HashMap<String, Object>();
			for (Map<String, Object> pnode : pnodes) {
				Object nodeid = "";
				if("start".equals(pnode.get("type")) || "end".equals(pnode.get("type"))){
					nodeid = pnode.get("type");
				}else{
					nodeid = pnode.get("id");
				}
				map.put("n_" + nodeid, pnode);
				List<Map<String, Object>> nroutes = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> proute : proutes) {
					if (pnode.get("id").equals(proute.get("fromNode"))) {
						nroutes.add(proute);
					}
				}
				map.put("r_" + nodeid, nroutes);
			}
			StringBuffer  sb = new StringBuffer();
			StringBuffer  sb1 = new StringBuffer();
			sb1.append(" IF EXISTS (SELECT * FROM DBO.SYSOBJECTS WHERE ID = OBJECT_ID(N'[dbo].["+produce+"]') and OBJECTPROPERTY(ID, N'IsProcedure') = 1) \n ");
			sb1.append(" DROP PROCEDURE [dbo].["+produce+"] \n ");
			
			sb.append(" create proc " + produce);
			sb.append(" (@common_id varchar(128), \n ");
			sb.append(" @res int output )\n ");
			sb.append(" as  \n ");
			sb.append(" declare  @flag varchar(8) \n ");
			setChild(sb, null, map);
			//sb.append(" go ");
			System.out.println("=====jq===="+sb);
			connect(system_product_code);
			new RuleFlowDesignerDao().execute(sb1.toString(), null);
			new RuleFlowDesignerDao().execute(sb.toString(), null);
			} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}

	public StringBuffer setChild(StringBuffer sb, String nodeid, Map<String, Object> map){
		if(nodeid == null){
			Map<String, Object> node = (Map<String, Object>) map.get("n_start");
			List<Map<String, Object>> routes = (List<Map<String, Object>>) map.get("r_start");
			for(Map<String, Object> route : routes){
				setChild(sb, (String)route.get("toNode"), map);
			}
		}else{
			Map<String, Object> node = (Map<String, Object>) map.get("n_" + nodeid);
			if(CommonFun.isNe(node)){
				sb.append(" \n return \n ");
				return sb;
			}
			sb.append(CommonFun.isNe(node.get("expr_scheme")) ? node.get("execute_scheme") : node.get("expr_scheme")+" ");
			if(!CommonFun.isNe(map.get("r_" + nodeid))){
				List<Map<String, Object>> routes = (List<Map<String, Object>>) map.get("r_" + nodeid);
				for(int i = 0; i < routes.size(); i++){
					Map<String, Object> route = routes.get(i);
					if(CommonFun.isNe(route.get("id"))){
						setChild(sb, (String)route.get("toNode"), map);
					}else{
						sb.append((i == 0 ? " \n if" : " else if ")+" @flag = '" + route.get("id")+"'\n ");
						sb.append(" begin \n ");
						setChild(sb, (String)route.get("toNode"), map);
						sb.append(" \n end \n ");
					}
					
				}
			}
		}
		return sb;
	}
	
	public void redeployAll(String productCode) throws Exception {
	}

	public Result selectbill(Map<String, Object> params) throws Exception {
		try {
			connect();
			DesignerDao dd = new DesignerDao();
			return dd.query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
}
