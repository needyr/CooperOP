package cn.crtech.cooperop.ipc.action;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.CommentManageService;
import cn.crtech.cooperop.ipc.service.CheckaAndCommentService;
import cn.crtech.cooperop.ipc.service.CommentFlowService;
import cn.crtech.cooperop.ipc.service.CommentService;
import cn.crtech.cooperop.ipc.service.PatientService;

public class CommentflowAction extends BaseAction {
	
	public Map<String, Object> detail(Map<String, Object> map) throws Exception{
		map.put("comment_user", user().getId());
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("pat",new CommentFlowService().getPatientInfo(map));
		rtnMap.put("comment", new CommentManageService().queryCommentType(map));
		rtnMap.put("diagnosis", new CommentService().getDiagnosis(map).getResultset());
		PatientService patient = new PatientService();
		rtnMap.put("dias", patient.queryDiagnos(map).getResultset());
		rtnMap.put("enjoin", patient.queryEnjoin(map).getResultset());
		rtnMap.put("qexam", patient.queryExam(map).getResultset());
		rtnMap.put("requesten", patient.queryRequesten(map).getResultset());
		rtnMap.put("vital", patient.queryVital(map).getResultset());
		rtnMap.put("oper", patient.queryOperation(map).getResultset());
		rtnMap.putAll(new CheckaAndCommentService().queryCheckAndCommentInfo(map));
		return rtnMap;
	}
	
	public Map<String, Object> orders(Map<String, Object> map) throws Exception{
		map.put("comment_user", user().getId());
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("pat",new CommentFlowService().getPatientInfo(map));
		rtnMap.put("comment", new CommentManageService().queryCommentType(map));
		rtnMap.put("diagnosis", new CommentService().getDiagnosis(map).getResultset());
		PatientService patient = new PatientService();
		rtnMap.put("dias", patient.queryDiagnos(map).getResultset());
		rtnMap.put("enjoin", patient.queryEnjoin(map).getResultset());
		rtnMap.put("qexam", patient.queryExam(map).getResultset());
		rtnMap.put("requesten", patient.queryRequesten(map).getResultset());
		rtnMap.put("vital", patient.queryVital(map).getResultset());
		rtnMap.put("oper", patient.queryOperation(map).getResultset());
		rtnMap.putAll(new CheckaAndCommentService().queryCheckAndCommentInfo(map));
		return rtnMap;
	}
	
	public Map<String, Object> detailshow(Map<String, Object> map) throws Exception{
		if (CommonFun.isNe(map.get("comment_user"))) {
			map.put("comment_user", user().getId());
		}
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("pat",new CommentFlowService().getPatientInfo(map));
		rtnMap.put("comment", new CommentManageService().queryComment(map).getResultset());
		map.remove("sort");
		rtnMap.put("diagnosis", new CommentService().getDiagnosis(map).getResultset());
		PatientService patient = new PatientService();
		rtnMap.put("dias", patient.queryDiagnos(map).getResultset());
		rtnMap.put("enjoin", patient.queryEnjoin(map).getResultset());
		rtnMap.put("qexam", patient.queryExam(map).getResultset());
		rtnMap.put("requesten", patient.queryRequesten(map).getResultset());
		rtnMap.put("vital", patient.queryVital(map).getResultset());
		rtnMap.put("oper", patient.queryOperation(map).getResultset());
		rtnMap.putAll(new CheckaAndCommentService().queryCheckAndCommentInfo(map));
		return rtnMap;
	}
	
	public Map<String, Object> approval(Map<String, Object> map) throws Exception{
		new CommentFlowService().approval(map);
		return map;
	}
	
	//发起流程
	@DisLoggedIn
	public void startFlow(Map<String, Object> params) throws Exception{
		new CommentFlowService().submit(params);
	}
	
	//查询患者列表
	public Result queryPats(Map<String, Object> params) throws Exception {
		if (CommonFun.isNe(params.get("comment_user"))) {
			params.put("comment_user", user().getId());
		}
		if(!CommonFun.isNe(params.get("djbh"))) {
			return new CommentFlowService().queryPatients(params);
		}else {
			//直接点评时不进流程 使用sample查询
			if(!CommonFun.isNe(params.get("sample_id"))) {
				return new CommentFlowService().queryPatients(params);
			}else {
				return null;
			}
		}
	}
	
	//查询下一个患者
	public Map<String, Object> queryNextPat(Map<String, Object> params) throws Exception {
		params.put("comment_user", user().getId());
		Result result = new CommentFlowService().queryNextPat(params);
		if(CommonFun.isNe(result.getResultset())) {
			params.put("nextpat", 0);
		}else {
			params.put("nextpat", result.getResultset());
		}
		return params;
	}
	
	public Map<String, Object> finish(Map<String, Object> params) throws Exception{
		params.put("comment_user", user().getId());
		Result	result = new CommentFlowService().queryFinishNum(params);
		Long res = result.getCount();
		if(res!=0) {
			params.put("result", "N");
			return params;
		}
		new CommentFlowService().approval(params);
		return params;
	}
	
	//发送消息给医生
	public Map<String, Object> sendMsg(Map<String, Object> params) throws Exception{
		new CommentFlowService().sendMsg(params);
		return params;
	}
	
	public Map<String, Object> cancel(Map<String, Object> params) throws Exception{
		new CommentFlowService().reject(params);
		return params;
	}
	
	public Result queryOrders(Map<String, Object> params) throws Exception{
		return new CommentFlowService().queryOrders(params);
	}
	
	public Map<String, Object> getQuestions(Map<String, Object> params) throws Exception{
		return new CommentFlowService().getQuestions(params);
	}
	
	public Map<String, Object> finishOne(Map<String, Object> params) throws Exception{
		Map<String, Object> rtnMap =new HashMap<String, Object>();
		Map<String, Object> tjMap =new HashMap<String, Object>();
		tjMap.put("id", params.remove("sample_pid"));
		int num=new CommentFlowService().getNullNum(params);
		rtnMap.put("order", num);
		if(num==0) {
			tjMap.put("state", 1);
			new CommentFlowService().updateCommentState(tjMap);
		}
		return rtnMap;
	}
	
	
	public Map<String, Object> getHasComment(Map<String, Object> params) throws Exception{
		return new CommentFlowService().getOrderComment(params);
	}
	
	
	public Result queryComByInp(Map<String, Object> params) throws Exception {
		return new CommentManageService().queryComment(params);
	}
	
	public long hasCommentContent(Map<String, Object> params) throws Exception {
		return new CommentFlowService().hasCommentContent(params);
	}
	
}
