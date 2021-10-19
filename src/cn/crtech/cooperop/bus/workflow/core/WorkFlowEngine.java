package cn.crtech.cooperop.bus.workflow.core;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.snaker.engine.SnakerEngine;
import org.snaker.engine.SnakerException;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.cfg.Configuration;
import org.snaker.engine.core.Execution;
import org.snaker.engine.core.ServiceContext;
import org.snaker.engine.entity.Order;
import org.snaker.engine.entity.Task;
import org.snaker.engine.helper.AssertHelper;
import org.snaker.engine.helper.DateHelper;
import org.snaker.engine.helper.JsonHelper;
import org.snaker.engine.model.NodeModel;
import org.snaker.engine.model.ProcessModel;
import org.snaker.engine.model.TaskModel;
import org.snaker.engine.model.TransitionModel;
import org.snaker.engine.entity.Process;

import cn.crtech.cooperop.application.service.MessageService;
import cn.crtech.cooperop.bus.cache.MemoryCache;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.cache.service.SystemConfigService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.LocalThreadMap;
import cn.crtech.cooperop.bus.workflow.workflow;
import cn.crtech.cooperop.bus.workflow.core.service.WorkFlowService;

public class WorkFlowEngine {
	public static final String STATE_NODE_OPERATOR = "system_process_state";
	protected static SnakerEngine engine;

	public static void init() throws Exception {
		engine = new Configuration().buildSnakerEngine();
		ByteArrayInputStream bis = null;
		String wf_process_id = SystemConfig.getSystemConfigValue("oa", "freeflow_id");
		try {
			bis = new ByteArrayInputStream(WorkFlowService.FREEFLOW().getBytes("UTF-8"));
			if (CommonFun.isNe(wf_process_id)) {
				wf_process_id = engine.process().deploy(bis);
			} else {
				engine.process().redeploy(wf_process_id, bis);
			}
			new SystemConfigService().saveConfig("oa", "freeflow_id", wf_process_id);
		} catch (Exception ex) {
			throw ex;
		} finally {
			bis.close();
		}
	}

	public static void deploy(String system_product_code, String id) throws Exception {
		WorkFlowService wfs = new WorkFlowService();
		Record flow = wfs.get(system_product_code, id);
		ByteArrayInputStream bis = null;
		String wf_process_id = flow.getString("wf_process_id");
		try {
			bis = new ByteArrayInputStream(flow.getString("wf_xml").getBytes("UTF-8"));
			if (CommonFun.isNe(wf_process_id)) {
				wf_process_id = engine.process().deploy(bis);
			} else {
				engine.process().redeploy(flow.getString("wf_process_id"), bis);
			}
			wfs.updateProcessId(system_product_code, id, wf_process_id);
		} catch (Exception ex) {
			throw ex;
		} finally {
			bis.close();
		}
	}

	public static void undeploy(String system_product_code, String id) throws Exception {
		WorkFlowService wfs = new WorkFlowService();
		Record flow = wfs.get(system_product_code, id);
		if (flow != null) {
			String wf_process_id = flow.getString("wf_process_id");
			try {
				if (!CommonFun.isNe(wf_process_id)) {
					engine.process().undeploy(flow.getString("wf_process_id"));
				}
			} catch (Exception ex) {
				throw ex;
			}
		}
	}

	public static String start(String system_product_code, String instance_bill, String djbh,
			String system_department_id) throws Exception {
		WorkFlowService wfs = new WorkFlowService();
		Record flow = wfs.getRecordByDJ(system_product_code, instance_bill);
		if (flow != null) {
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("system_department_id", system_department_id);
			args.put(SnakerEngine.ID, djbh);
			args.put("system_product_code", system_product_code);
			String subject;
			if (!CommonFun.isNe(flow.getString("subject_scheme"))) {
				subject = wfs.executeExprScheme(system_product_code, flow.getString("subject_scheme"), djbh, null,
						null,new HashMap<String, Object>());
			} else {
				subject = flow.getString("name");
			}
			args.put("subject", subject);
			try {
				Order process = engine.startInstanceById(flow.getString("wf_process_id"), getOperator(), args);
				return process.getOrderNo();
			} catch (Throwable e) {
				processException(e);
			}
		}
		return null;
	}
	public static String start(String system_product_code, String instance_bill, String djbh,
			String system_department_id, Map<String, Object> args) throws Exception {
		WorkFlowService wfs = new WorkFlowService();
		Record flow = wfs.getRecordByDJ(system_product_code, instance_bill);
		if (flow != null) {
			args.put("system_department_id", system_department_id);
			args.put(SnakerEngine.ID, djbh);
			args.put("system_product_code", system_product_code);
			String subject;
			if (!CommonFun.isNe(flow.getString("subject_scheme"))) {
				subject = wfs.executeExprScheme(system_product_code, flow.getString("subject_scheme"), djbh, null,
						null, new HashMap<String, Object>());
			} else {
				subject = flow.getString("name");
			}
			args.put("subject", subject);
			try {
				Order process = engine.startInstanceById(flow.getString("wf_process_id"), getOperator(), args);
				return process.getOrderNo();
			} catch (Throwable e) {
				processException(e);
			}
		}
		return null;
	}
	public static String start(String system_product_code, String process_name, String audit_bill, String info_bill,
			String djbh, String operator, Map<String, Object> args) throws Exception {
		args.put("creator", getOperator());
		args.put(SnakerEngine.ID, djbh);
		args.put("system_product_code", system_product_code);
		args.put("process_name", process_name);
		args.put("subject", process_name);
		args.put("audit_bill", audit_bill);
		args.put("info_bill", info_bill);
		try {
			Order process = engine.startInstanceById(SystemConfig.getSystemConfigValue("oa", "freeflow_id"),
					getOperator(), args);
			createFreeTask(process, (CommonFun.isNe(args.get("task_name")) ? process_name : (String)args.get("task_name")), audit_bill, operator, args);
			return process.getOrderNo();
		} catch (Throwable e) {
			processException(e);
			return null;
		}
	}

	public static String ccProcess(String djbh, String... actorIds) throws Exception {
		try {
			QueryFilter qf = new QueryFilter();
			qf.setOrderNo(djbh);
			List<Order> lo = engine.query().getActiveOrders(qf);
			if (lo.size() > 0) {
				engine.order().createCCOrder(lo.get(0).getId(), lo.get(0).getCreator(), actorIds);
			}
			return null;
		} catch (Throwable e) {
			processException(e);
			return null;
		}
	}

	private static String createFreeTask(Order process, String task_name, String audit_bill, String operator,
			Map<String, Object> args) throws Exception {
		TaskModel tm1 = new TaskModel();
		tm1.setName(task_name);
		tm1.setDisplayName(task_name);
		tm1.setForm(audit_bill);
		tm1.setAssignee(operator);
		Object o = process.getVariableMap().get("expireTime");
		if (!CommonFun.isNe(process.getVariableMap().get("expireTime"))) {
			Date et = DateHelper.parseDate((String)process.getVariableMap().get("expireTime"));
			Date now = new Date();
			long mins = (et.getTime() - now.getTime()) / 1000 / 60;
			tm1.setExpireTime(String.valueOf(mins));
		}
		List<Task> tasks = null;
		tasks = engine.createFreeTask(process.getId(), operator, args, tm1);
		for (Task task : tasks) {
			if (!CommonFun.isNe(process.getVariableMap().get("startTime"))) {
				Date tct = DateHelper.parseDate(task.getCreateTime());
				Date ct = DateHelper.parseDate((String)process.getVariableMap().get("startTime"));
				if (ct.getTime() > tct.getTime()) {
					task.setCreateTime((String)args.get("startTime"));
					engine.task().updateTask(task);
				}
			}
			return task.getId();
		}
		return null;
	}

	public static boolean isFreeFlow(String task_id) throws Exception {
		try {
			Task task = engine.query().getTask(task_id);
			Order order = engine.query().getOrder(task.getOrderId());
			return order.getProcessId().equals(SystemConfig.getSystemConfig("oa", "freeflow_id"));
		} catch (Throwable e) {
			processException(e);
			return false;
		}
	}

	public static void checkout(String task_id) throws Exception {
		try {
			String operator = getOperator();
			Task task = engine.query().getTask(task_id);
			if (!engine.task().isAllowed(task, operator)) {
				throw new Exception("当前参与者[" + operator + "]不允许拣出任务[taskId=" + task_id + "]");
			}
			task.setOperator(operator);
			engine.task().updateTask(task);
		} catch (Throwable e) {
			processException(e);
		}
	}

	public static void checkin(String task_id) throws Exception {
		try {
			String operator = getOperator();
			Task task = engine.query().getTask(task_id);
			if (task.getOperator() != null) {
				if (task.getOperator().equals(operator) || operator == null) {
					task.setOperator(null);
					engine.task().updateTask(task);
				} else {
					throw new Exception("当前参与者[" + operator + "]不允许放回任务[taskId=" + task_id + "]");
				}
			}
		} catch (Throwable e) {
			processException(e);
		}
	}

	public static void next(String task_id, String next_operator, String audited, String advice) throws Exception {
		try {
			Task task = engine.query().getTask(task_id);
			Order order = engine.query().getOrder(task.getOrderId());
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("audited", audited);
			args.put("advice", advice);
			engine.task().complete(task_id, getOperator(), args);
			createFreeTask(order, (String) order.getVariableMap().get("process_name"),
					(String) order.getVariableMap().get("audit_bill"), next_operator, new HashMap<String, Object>());
		} catch (Throwable e) {
			processException(e);
		}
	}

	public static void finish(String task_id, String audited, String advice) throws Exception {
		try {
			Task task = engine.query().getTask(task_id);
			Order order = engine.query().getOrder(task.getOrderId());
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("audited", audited);
			args.put("advice", advice);
			engine.task().complete(task_id, getOperator(), args);
			engine.order().complete(order.getId());
		} catch (Throwable e) {
			processException(e);
		}
	}

	public static void apply(String task_id, String advice, String tonode_id, String[] processors, String[] assistants, Map<String, Object> params)
			throws Exception {
		try {
			Map<String, Object> args = new HashMap<String, Object>();
			if(params != null){
				args.putAll(params);
			}
			args.put("audited", workflow.AUDITED_APPLY);
			args.put("advice", advice);
			args.put("_ROUTE2NODE_", tonode_id);
			args.put("_PROCESSORS_", processors);
			args.put("_ASSISTANTS_", assistants);
			List<Task> tasks = engine.executeTask(task_id, getOperator(), args);
			if (assistants != null) {
				for (Task t : tasks) {
					engine.task().createNewTask(t.getId(), t.getPerformType(), assistants);
				}
			}
		} catch (Throwable e) {
			processException(e);
		}
	}
	public static void applyAuto(String task_id, String advice, String operator, Map<String, Object> params)
			throws Exception {
		try {
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("audited", workflow.AUDITED_APPLY);
			args.put("advice", advice);
			if(params != null){
				args.put("gzid", params.get("gzid"));
			}
			engine.executeTask(task_id, operator, args);
		} catch (Throwable e) {
			processException(e);
		}
	}
	public static void reject(String task_id, String advice, Map<String, Object> param) throws Exception {
		try {
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("audited", workflow.AUDITED_REJECT);
			args.put("advice", advice);
			if(param != null){
				args.put("gzid", param.get("gzid"));
			}
			Task task = engine.query().getTask(task_id);
			Record node = new WorkFlowService().getNode(task.getId(), task.getTaskName());
			//驳回流程在即时消息中发生信息给发起人
			Order order1 = engine.query().getOrder(task.getOrderId());
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("target", "U");
			params.put("send_to", order1.getCreator());
			params.put("type", "T");
			params.put("content", "您的流程‘"+order1.getVariableMap().get("subject")+"’已被驳回，单据编号为：‘"+order1.getOrderNo()+"’");
			new MessageService().send(params);
			List<Task> tasks = engine.executeTask(task_id, getOperator(), args);
			Order order = engine.query().getOrder(task.getOrderId());
			if ((tasks == null || tasks.size() == 0) && order!=null) {
				if(!CommonFun.isNe(node) && "countersign".equals(node.get("type"))){
					//会签结束并且结果是驳回，则结束流程
					try {
						String check = new WorkFlowService().checkCountersign(order.getId(), null, task.getTaskName(), null);
						if("N".equals(check)){
							engine.order().terminate(task.getOrderId(), SnakerEngine.ADMIN);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{//普通节点直接结束
					engine.order().terminate(task.getOrderId(), SnakerEngine.ADMIN);
				}
			}
		} catch (Throwable e) {
			processException(e);
		}
	}

	public static void reject2lastnode(String task_id, String advice) throws Exception {
		try {
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("audited", workflow.AUDITED_REJECT);
			args.put("advice", advice);
			engine.executeAndJumpTask(task_id, getOperator(), args, null);
		} catch (Throwable e) {
			processException(e);
		}
	}

	public static void reaudit(String task_id, String advice) throws Exception {
		try {
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("audited", workflow.AUDITED_REAUDIT);
			args.put("advice", advice);
			Task task = engine.query().getTask(task_id);
			task.setVariable(JsonHelper.toJson(args));
			engine.task().updateTask(task);
			engine.task().withdrawTask(task_id, getOperator());
			// String operator = getOperator();
			// Task task = engine.query().getTask(task_id);
			// if (!engine.task().isAllowed(task, operator)) {
			// throw new Exception("当前参与者[" + operator +
			// "]不允许执行任务[taskId=" +
			// task_id + "]");
			// }
			// task.setVariable(JsonHelper.toJson(args));
			// task.setOperator(operator);
			// engine.task().updateTask(task);
			// Order order = engine.query().getOrder(task.getOrderId());
			// ProcessModel model =
			// engine.process().getProcessById(order.getProcessId()).getModel();
			// engine.task().rejectTask(model, task);
		} catch (Throwable e) {
			processException(e);
		}
	}

	public static void back(String task_id, String advice) throws Exception {
		try {
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("audited", workflow.AUDITED_BACK);
			args.put("advice", advice);
			Task task = engine.query().getTask(task_id);
			task.setVariable(JsonHelper.toJson(args));
			engine.task().updateTask(task);
			engine.order().terminate(task.getOrderId(), SnakerEngine.ADMIN);
		} catch (Throwable e) {
			processException(e);
		}
	}

	public static void saveTask(String task_id, String audited, String advice) throws Exception {
		try {
			Map<String, Object> args = new HashMap<String, Object>();
			args.put("audited", audited);
			args.put("advice", advice);
			Task task = engine.query().getTask(task_id);
			task.setVariable(JsonHelper.toJson(args));
			engine.task().updateTask(task);
		} catch (Throwable e) {
			processException(e);
		}
	}
	public static void terminate(String task_id, String order_id) throws Exception {
		try {
			if(order_id != null){
				finish(order_id, null);
			}else if(task_id != null){
				order_id = engine.query().getTask(task_id).getOrderId();
				finish(order_id, null);
			}
		} catch (Throwable e) {
			processException(e);
		}
	}
	public static void finish(String order_id, String advice) throws Exception {
		try {
			engine.order().terminate(order_id, SnakerEngine.ADMIN);
		} catch (Throwable e) {
			processException(e);
		}
	}

	public static List<Record> listTaskRoutes(String task_id, String audited, String advice, boolean matched) throws Exception {
		try {
			Task task = engine.query().getTask(task_id);
			Order order = engine.query().getOrder(task.getOrderId());
			String djbh = order.getOrderNo();
			String system_department_id = (String) task.getVariableMap().get("system_department_id");
			String name = engine.process().getProcessById(order.getProcessId()).getName();
			String system_product_code = name.split(WorkFlowService.FLOW_NAME_SPLIT)[0];
			String id = name.split(WorkFlowService.FLOW_NAME_SPLIT)[1];
			TaskModel t ;
	        Process process = ServiceContext.getEngine().process().getProcessById(order.getProcessId());
	        ProcessModel model = process.getModel();
	        String s = task.getTaskName();
	        NodeModel nodeModel = model.getNode(task.getTaskName());
	        AssertHelper.notNull(nodeModel, "任务id无法找到节点模型.");
	        if(nodeModel instanceof TaskModel) {
	            t = (TaskModel)nodeModel;
	        } else {
	            throw new IllegalArgumentException("任务id找到的节点模型不匹配");
	        }
	        List<TransitionModel> tms = t.getOutputs();
	    	//List<TransitionModel> tms = task.getModel().getOutputs();
	        WorkFlowService wfs = new WorkFlowService();

			Result rtn = new Result();
			for (TransitionModel tm : tms) {
				Record route = wfs.getRoute(system_product_code, id, tm.getSource().getName(),
						tm.getTarget().getName());
				if (route != null) {
					if (matched && !CommonFun.isNe(route.get("expr_scheme"))) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("operator", getOperator());
						map.put("finish_time", DateHelper.getTime());
						map.put("audited", "Y");
						if ("Y".equals(wfs.executeExprScheme(system_product_code, route.getString("expr_scheme"), djbh,
								audited, advice, map))) {
							rtn.addRecord(route);
						}
					} else {
						rtn.addRecord(route);
					}
				}
			}
			for (Record route : rtn.getResultset()) {
				Record node = wfs.getNode(system_product_code, id, route.getString("tonode"));
				if (node != null) {
					if (!CommonFun.isNe(node.get("processor_scheme"))) {
						node.put("processors", wfs.executeAssignmentScheme(system_product_code, node.getString("processor_scheme"), djbh));
					} else if (!CommonFun.isNe(node.getString("processor_role"))) {
						node.put("processors", wfs.getActorList(node.getString("processor_role"), system_department_id));
					} else if (!CommonFun.isNe(node.getString("processors"))) {
						node.put("processors", node.getString("processors").split(","));
					}
					if (!CommonFun.isNe(node.get("assistant_scheme"))) {
						node.put("assistants", wfs.executeAssignmentScheme(system_product_code, node.getString("assistant_scheme"), djbh));
					} else if (!CommonFun.isNe(node.getString("assistant_role"))) {
						node.put("assistants", wfs.getActorList(node.getString("assistant_role"), system_department_id));
					} else if (!CommonFun.isNe(node.getString("assistants"))) {
						node.put("assistants", node.getString("assistants").split(","));
					}
					route.put("target_node", node);
				}
			}
			return rtn.getResultset();
		} catch (Throwable e) {
			processException(e);
			return null;
		}
	}

	private static String getOperator() {
		WorkFlowService wfs = new WorkFlowService();
		return wfs.user() == null
				? (LocalThreadMap.get("_wf_creator") == null ? "system" : (String) LocalThreadMap.get("_wf_creator"))
				: wfs.user().getId();
	}

	private static void processException(Throwable e) throws Exception {
		//e.printStackTrace();
		Throwable e1 = e;
		while (e1.getCause() != null && e1 instanceof SnakerException) {
			e1 = e1.getCause();
		}
		throw new Exception(e1);
	}
	public static void addActor(String task_id, String[] actors)
			throws Exception {
		try {
			engine.task().addTaskActor(task_id, actors);
		} catch (Throwable e) {
			processException(e);
		}
	}
}
