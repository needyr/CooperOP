package cn.crtech.cooperop.bus.workflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemMessageTemplate;
import cn.crtech.cooperop.bus.message.MessageSender;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.workflow.core.WorkFlowEngine;
import cn.crtech.cooperop.bus.workflow.core.service.WorkFlowService;

public class workflow {
	
	/**
	 * 审核通过  audited = Y
	 */
	public static final String AUDITED_APPLY = "Y";
	/**
	 * 审核不通过，并根据流向继续运行，若无满足条件的流向，则结束任务  audited = N
	 */
	public static final String AUDITED_REJECT = "N";
	/**
	 * 审核驳回上一步，无需绘制流向  audited = NL
	 */
	public static final String AUDITED_REJECT_LASTNODE = "NL";
	/**
	 * 重新审核，传入的task_id是需要重新审核的任务id  audited = R
	 */
	public static final String AUDITED_REAUDIT = "R";
	/**
	 * 流程撤回  audited = B
	 */
	public static final String AUDITED_BACK = "B";
	
	public static Result listTask(Map<String, Object> params) throws Exception {
		return null;
	}
	
	public static Record getTask(String key) throws Exception {
		return null;
	}
	
	public static List<Record> listTaskRoutes(String task_id, String audited, String advice, boolean matched) throws Exception {
		return WorkFlowEngine.listTaskRoutes(task_id, audited, advice, matched);
	}
	
	/**
	 * 启动流程
	 * @param system_product_code
	 * @param system_product_process_id
	 * @return
	 * @throws Exception
	 */
	public static String start(String system_product_code, String instance_bill, String djbh, String system_department_id) throws Exception {
		return WorkFlowEngine.start(system_product_code, instance_bill, djbh, system_department_id);
	}
	/**
	 * 启动流程
	 * @param system_product_code
	 * @param system_product_process_id
	 * @return
	 * @throws Exception
	 */
	public static String start(String system_product_code, String instance_bill, String djbh, String system_department_id, Map<String, Object> args) throws Exception {
		return WorkFlowEngine.start(system_product_code, instance_bill, djbh, system_department_id, args);
	}
	/**
	 * 启动流程
	 * @param system_product_code
	 * @param system_product_process_id
	 * @return
	 * @throws Exception
	 */
	public static String start(String system_product_code, String process_name, String audit_bill, String info_bill, String djbh, String operator) throws Exception {
		return WorkFlowEngine.start(system_product_code, process_name, audit_bill, info_bill, djbh, operator, new HashMap<String, Object>());
	}
	
	/**
	 * 启动流程
	 * @param system_product_code
	 * @param system_product_process_id
	 * @return
	 * @throws Exception
	 */
	public static String start(String system_product_code, String process_name, String audit_bill, String info_bill, String djbh, String operator, Map<String, Object> args) throws Exception {
		return WorkFlowEngine.start(system_product_code, process_name, audit_bill, info_bill, djbh, operator, args);
	}
	
	/**
	 * 启动流程
	 * @param system_product_code
	 * @param system_product_process_id
	 * @return
	 * @throws Exception
	 */
	public static String ccProcess(String djbh, String... actorIds) throws Exception {
		return WorkFlowEngine.ccProcess(djbh, actorIds);
	}
	
	/**
	 * 审批通过
	 * @param task_id
	 * @param advice
	 * @throws Exception
	 */
	public static void next(String task_id, String audited, String advice) throws Exception {
		next(task_id, audited, advice, null, null, null,null);
	}
	public static void next(String task_id, String audited, String advice, Map<String, Object> arg) throws Exception {
		next(task_id, audited, advice, null, null, null, arg);
	}
	public static void next(String task_id, String audited, String advice, String tonode_id, String[] processors, String[] assistants, Map<String, Object> arg) throws Exception {
		Record r = new WorkFlowService().getByTaskId(task_id);
		if (AUDITED_APPLY.equals(audited)) {
			WorkFlowEngine.apply(task_id, advice, tonode_id, processors, assistants ,arg);
		} else if (AUDITED_REJECT.equals(audited)) {
			WorkFlowEngine.reject(task_id, advice, arg);
		} else if (AUDITED_REJECT_LASTNODE.equals(audited)) {
			WorkFlowEngine.reject2lastnode(task_id, advice);
		} else if (AUDITED_REAUDIT.equals(audited)) {
			WorkFlowEngine.reaudit(task_id, advice);
		} else if (AUDITED_BACK.equals(audited)) {
			WorkFlowEngine.back(task_id, advice);
		}
		//处理完成后发信息，根据task_id拿流程id和节点id
		if(r != null){
			if(SystemMessageTemplate.needSendM(r.getString("system_product_code") +  r.getString("process") +r.getString("node"))){
				arg.put("advice", advice);
				arg.put("audited", audited);
				MessageSender.sendProcessMessageAfter(r.getString("system_product_code"), r.getString("process"), r.getString("node"), arg);
			}
		}
	}
	/**
	 * 审批通过
	 * @param task_id
	 * @param advice
	 * @throws Exception
	 */
	public static void saveTask(String task_id, String audited, String advice) throws Exception {
		WorkFlowEngine.saveTask(task_id, audited, advice);
	}

	/**
	 * 审批通过
	 * @param task_id
	 * @param advice
	 * @throws Exception
	 */
	public static void next(String task_id, String audited, String advice, String next_operator) throws Exception {
		WorkFlowEngine.next(task_id, next_operator, audited, advice);
	}
	/**
	 * 审批通过
	 * @param task_id
	 * @param advice
	 * @throws Exception
	 */
	public static void finish(String task_id, String audited, String advice) throws Exception {
		WorkFlowEngine.finish(task_id, audited, advice);
	}

	/**
	 * 审批通过
	 * @param task_id
	 * @param advice
	 * @throws Exception
	 */
	public static void apply(String task_id, String advice) throws Exception {
		apply(task_id, advice, null, null, null);
	}
	
	/**
	 * 审批通过
	 * @param task_id
	 * @param advice
	 * @throws Exception
	 */
	public static void apply(String task_id, String advice, String tonode_id, String[] processors, String[] assistants) throws Exception {
		WorkFlowEngine.apply(task_id, advice, tonode_id, processors, assistants, null);
	}
	
	/**
	 * 审批驳回
	 * @param task_id
	 * @param advice
	 * @throws Exception
	 */
	public static void reject(String task_id, String advice) throws Exception {
		WorkFlowEngine.reject(task_id, advice, null);
	}
	
	/**
	 * 返回上一步重审
	 * @param task_id
	 * @param advice
	 * @throws Exception
	 */
	public static void reaudit(String task_id, String advice) throws Exception {
		WorkFlowEngine.reaudit(task_id, advice);
	}
	
	/**
	 * 撤回
	 * @param task_id
	 * @param advice
	 * @throws Exception
	 */
	public static void back(String task_id, String advice) throws Exception {
		WorkFlowEngine.back(task_id, advice);
	}
	public static void terminate(String task_id, String order_id) throws Exception {
		WorkFlowEngine.terminate(task_id, order_id);
	}
}
