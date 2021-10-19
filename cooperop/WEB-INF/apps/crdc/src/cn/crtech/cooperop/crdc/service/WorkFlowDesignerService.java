package cn.crtech.cooperop.crdc.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.mvc.view.ViewCreater;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.workflow.core.JudgeHandle;
import cn.crtech.cooperop.bus.workflow.core.WorkFlowEngine;
import cn.crtech.cooperop.crdc.dao.DesignerDao;
import cn.crtech.cooperop.crdc.dao.WorkFlowDesignerDao;

public class WorkFlowDesignerService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			WorkFlowDesignerDao dd = new WorkFlowDesignerDao();
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
			return new WorkFlowDesignerDao().queryFlowCount(req);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
		
	}
	public Map<String, Object> get(String system_product_code, String id) throws Exception {
		try {
			connect();
			WorkFlowDesignerDao dd = new WorkFlowDesignerDao();
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
			WorkFlowDesignerDao dd = new WorkFlowDesignerDao();
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
			WorkFlowEngine.undeploy(system_product_code, id);
			Record r = new Record();
			r.put("state", 0);
			new WorkFlowDesignerDao().update(system_product_code, id, r);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public int delete(String system_product_code, String id) throws Exception {
		try {
			connect();
			WorkFlowDesignerDao dd = new WorkFlowDesignerDao();
			start();
			WorkFlowEngine.undeploy(system_product_code, id);
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
			save(params);
			String system_product_code = (String) params.get("system_product_code");
			String id = (String) params.get("id");
			WorkFlowEngine.deploy(system_product_code, id);
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
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
