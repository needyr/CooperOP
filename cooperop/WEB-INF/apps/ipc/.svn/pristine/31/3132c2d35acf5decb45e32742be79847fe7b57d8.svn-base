package cn.crtech.cooperop.ipc.action;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.CommentManageService;
import cn.crtech.cooperop.ipc.service.CheckaAndCommentService;
import cn.crtech.cooperop.ipc.service.CommentFlowService;
import cn.crtech.cooperop.ipc.service.CommentService;
import cn.crtech.cooperop.ipc.service.PatientService;

public class CommentAction extends BaseAction{

	
	//历史记录
	@DisLoggedIn
	public Result querySample(Map<String, Object> params) throws Exception {
		params.put("comment_user", user().getId());
		Result r =new CommentService().querySample(params);
		return r;
	}
	
	//查询医嘱组 列表
	@DisLoggedIn
	public Result queryCRorders(Map<String, Object> params) throws Exception {
		params.put("doctor_no", user().getNo());
		Result r =new CommentService().queryCRorders(params);
		return r;
	}
	
	//查询医嘱组 列表 （部门查询）
	@DisLoggedIn
	public Result queryCRordersdept(Map<String, Object> params) throws Exception {
		params.put("doctor_no", user().getNo());
		Result r =new CommentService().queryCRorders(params);
		return r;
	}
	
	@DisLoggedIn
	public Map<String, Object> queryLastResult(Map<String, Object> params) throws Exception{
		return new CommentFlowService().queryLastResult(params);
	}
	
	@DisLoggedIn
	//完成点评  默认合理
	public int finishMRHL(Map<String, Object> params) throws Exception{
		return new CommentService().finishMRHL(params);
	}
	
	//点评时的查看：从工作表里面查询
	@DisLoggedIn
	public Map<String, Object> getCommenting(Map<String, Object> params) throws Exception{
		return new CommentService().getCommenting(params);
	}
	
	//历史记录的查看：从历史表里面查询
	@DisLoggedIn
	public Map<String, Object> commentHistory(Map<String, Object> params) throws Exception{
		return new CommentService().commentHistory(params);
	}
	
	//药师自己点评时的，患者列表
	@DisLoggedIn
	public Result queryPats(Map<String, Object> params) throws Exception{
		if (CommonFun.isNe(params.get("comment_user"))) {
			params.put("comment_user", user().getId());
		}
		return new CommentService().queryPatients(params);
	}
	
	//直接点评，最终完成点评
	@DisLoggedIn
	public Map<String, Object> finish(Map<String, Object> params) throws Exception{
		params.put("comment_user", user().getId());
		Result	result = new CommentService().queryFinishNum(params);
		Long res = result.getCount();
		if(res!=0) {
			params.put("result", "N");
			return params;
		}
		new CommentService().approval(params);
		return params;
	}
	
	//直接点评，发送消息给医生
	@DisLoggedIn
	public Map<String, Object> sendMsg(Map<String, Object> params) throws Exception{
		new CommentService().sendMsg(params);
		return params;
	}
	
	//点评历史记录：医嘱界面的
	@DisLoggedIn
	public Map<String, Object> ordes_history(Map<String, Object> map) throws Exception{
		map.put("comment_user", user().getId());
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("pat",new CommentService().getPatientInfo(map));
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
	
	//保存点评信息
	@DisLoggedIn
	public void saveCommentR (Map<String, Object> params) throws Exception{
		if(!CommonFun.isNe("sample_orders_id_set")) {
			new CommentService().saveCommentR(CommonFun.json2Object((String)params.get("data"), Map.class));
		}
	}
	
	//完成一个人的点评
	@DisLoggedIn
	public Map<String, Object> finishOne(Map<String, Object> params) throws Exception{
		Map<String, Object> rtnMap =new HashMap<String, Object>();
		Map<String, Object> tjMap =new HashMap<String, Object>();
		tjMap.put("id", params.remove("sample_pid"));
		int num=new CommentService().getNullNum(params);
		rtnMap.put("order", num);
		if(num==0) {
			tjMap.put("state", 1);
			new CommentService().updateCommentState(tjMap);
		}
		return rtnMap;
	}
	
	//直接点评，查看下一单的信息
	@DisLoggedIn
	public Map<String, Object> queryNextPatOwn(Map<String, Object> params) throws Exception {
		params.put("comment_user", user().getId());
		Result result = new CommentService().queryNextPat(params);
		if(CommonFun.isNe(result.getResultset())) {
			params.put("nextpat", 0);
		}else {
			params.put("nextpat", result.getResultset().get(0));
		}
		return params;
	}
	
	@DisLoggedIn
	public Result queryPatiens(Map<String, Object> params) throws Exception {
		return new CommentService().queryPatients(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> showorders(Map<String, Object> map) throws Exception{
		if (CommonFun.isNe(map.get("comment_user"))) {
			map.put("comment_user", user().getId());
		}
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("pat",new CommentService().getPatientInfo(map));
		rtnMap.put("comment", new CommentManageService().queryComment(new HashMap<String, Object>()).getResultset());
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
	
	@DisLoggedIn
	public Map<String, Object> setHL(Map<String, Object> params) throws Exception {
		return new CommentService().setHL(params);
	}
	
	@DisLoggedIn
	public int updateHL(Map<String, Object> params) throws Exception {
		return new CommentService().updateHL(params);
	}
	
	@DisLoggedIn
	public Result queryCheck(Map<String, Object> params) throws Exception {
		return new CommentService().queryCheck(params);
	}
	
	@DisLoggedIn
	public Record pass(Map<String, Object> params) throws Exception {
		return new CommentService().queryPass(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> queryRealYizu(Map<String, Object> params) throws Exception {
		return new CommentService().queryRealYizu(params);
	}
}
