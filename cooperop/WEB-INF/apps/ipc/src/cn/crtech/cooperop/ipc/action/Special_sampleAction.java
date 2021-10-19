package cn.crtech.cooperop.ipc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.PatientService;
import cn.crtech.cooperop.ipc.service.CommentFlowService;
import cn.crtech.cooperop.ipc.service.Special_sampleService;

public class Special_sampleAction extends BaseAction{
	
	public Result query(Map<String, Object> params) throws Exception {
		return new Special_sampleService().query(params);
	}
	
	public Map<String, Object> queryAll(Map<String, Object> params) throws Exception {
		return new Special_sampleService().queryAll(params);
	}
	
	public Result queryComment(Map<String, Object> params) throws Exception {
		return new Special_sampleService().queryComment(params);
	}
	
	@DisLoggedIn
	public Map<String, Object> insertSample(Map<String, Object> params) throws Exception {
		return new Special_sampleService().insertSample(params);
	}
	
	@DisLoggedIn
	public Result queryStartSample(Map<String, Object> params) throws Exception {
		if (CommonFun.isNe(params.get("GZID"))) {
			return null;
		}
		return new Special_sampleService().queryStartSample(params);
	}
	
	public Map<String, Object> saveSample(Map<String, Object> params) throws Exception {
		return new Special_sampleService().saveSample(params);
	}
	
	public Map<String, Object> saveSamplebyedit(Map<String, Object> params) throws Exception {
		return new Special_sampleService().saveSamplebyedit(params);
	}
	
	public Result querysample(Map<String, Object> params) throws Exception {
		if ("0".equals(params.get("fpdpr"))) {
			params.put("user_no",user().getNo());
		}
		return new Special_sampleService().querysample(params);
	}
	
	public Result querydetail(Map<String, Object> params) throws Exception {
		return new Special_sampleService().querydetail(params);
	}
	
	public int updateSample(Map<String, Object> params) throws Exception {
		if (!CommonFun.isNe(params)) {
			params.put("comment_flag", "1");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("comment_user_mx", params.get("comment_user_mx"));
		map.put("id", params.get("id"));
		new Special_sampleService().assign(map);
		params.remove("comment_user_mx");
		return new Special_sampleService().updateSample(params);
	}
	
	public Result querysystem(Map<String, Object> params) throws Exception {
		return new Special_sampleService().querysystem(params);
	}
	
	/*public List<Record> querysystem(Map<String, Object> params) throws Exception {
		return new SampleService().querysystem(params).getResultset();
	}*/
	
	public Map<String, Object> list(Map<String, Object> params){
		return new Special_sampleService().getDefalutTime();
	}
	
	public Map<String, Object> sampledetail(Map<String, Object> params) throws Exception{
		return new Special_sampleService().sampledetail(params);
	}
	
	public Map<String, Object> samplerandom(Map<String, Object> params) throws Exception{
		return new Special_sampleService().samplerandom(params);
	}
	
	public int remove(Map<String, Object> params) throws Exception {
		return new Special_sampleService().remove(params);
	}
	
	public int updateState(Map<String, Object> params) throws Exception {
		List<Record> assign_persons = new Special_sampleService().queryToHandlerGroup(params);
		params.put("assign_persons", assign_persons);
		if (!CommonFun.isNe(params)) {
			params.put("state", 1);
			new CommentFlowService().submit(params);
			params.put("comment_flag", "2");
			params.remove("state");
			params.remove("assign_persons");
		}
		return new Special_sampleService().updateSample(params);
	}
	
	public int updateState2(Map<String, Object> params) throws Exception {
		if (!CommonFun.isNe(params)) {
			params.put("state", 1);
			params.put("comment_flag", "2");
		}
		return new Special_sampleService().updateSample(params);
	}
	
	public void updateSampleDel(Map<String, Object> params) throws Exception {
		if (!CommonFun.isNe(params)) {
			params.put("is_active", "0");
		}
		new Special_sampleService().updatesampledel(params);
	}
	
	public void updateSampleAdd(Map<String, Object> params) throws Exception {
		if (!CommonFun.isNe(params)) {
			params.put("is_active", "1");
		}
		new Special_sampleService().updatesampleAdd(params);
	}
	
	//患者详情
	public Map<String, Object> patientdetail(Map<String, Object> map) throws Exception{
		return new PatientService().querypatient(map);
	}
	
	public Result queryDept(Map<String, Object> params) throws Exception {
		return new Special_sampleService().queryDept(params);
	}
	
	public Result queryFeibie(Map<String, Object> params) throws Exception {
		return new Special_sampleService().queryFeibie(params);
	}
	
	public Result queryDrug(Map<String, Object> params) throws Exception {
		return new Special_sampleService().queryDrug(params);
	}
	
	public Result queryDoctor(Map<String, Object> params) throws Exception {
		return new Special_sampleService().queryDoctor(params);
	}
	
	/*public Record sampledetail(Map<String, Object> params) throws Exception {
		return new SampleService().sampledetail(params);
	}*/
	
	public void addSample(Map<String, Object> params) throws Exception {
		new Special_sampleService().addSample(params);
	}
	
	public Map<String, Object> sampleshow(Map<String, Object> params) throws Exception {
		return new Special_sampleService().sampledetail(params);
	}
	
	public Map<String, Object> edit(Map<String, Object> params) throws Exception {
		return new Special_sampleService().sampledetail(params);
	}
	
	public Map<String, Object> distribution(Map<String, Object> params) throws Exception {
		return new Special_sampleService().distribution(params);
	}
	
	public Map<String, Object> queryIsOwn(Map<String, Object> params) throws Exception {
		params.put("user_no",user().getNo());
		return new Special_sampleService().queryIsOwn(params);
	}
	
	public Result queryCommentWay(Map<String, Object> params) throws Exception {
		return new Special_sampleService().queryCommentWay(params);
	}
	
	@DisLoggedIn
	public Record realSaveSample(Map<String, Object> params) throws Exception {
		return new Special_sampleService().realSaveSample(params);
	}
	
	@DisLoggedIn
	public void sampleAllTMPByGZID(Map<String, Object> params) throws Exception {
		new Special_sampleService().sampleAllTMPByGZID(params);
	}
	
	@DisLoggedIn
	public Result sp_scheme(Map<String, Object> params) throws Exception {
		return new Special_sampleService().sp_scheme(params);
	}
	
	@DisLoggedIn
	public Record getSp_scheme(Map<String, Object> params) throws Exception {
		return new Special_sampleService().getSp_scheme(params);
	}
}
