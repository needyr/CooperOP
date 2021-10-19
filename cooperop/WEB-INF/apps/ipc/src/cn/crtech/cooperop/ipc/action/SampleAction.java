package cn.crtech.cooperop.ipc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.PatientService;
import cn.crtech.cooperop.ipc.service.CommentFlowService;
import cn.crtech.cooperop.ipc.service.SampleService;
import cn.crtech.precheck.ipc.service.DataService;

public class SampleAction extends BaseAction{
	
	public Result query(Map<String, Object> params) throws Exception {
		return new SampleService().query(params);
	}
	
	public Map<String, Object> queryAll(Map<String, Object> params) throws Exception {
		return new SampleService().queryAll(params);
	}
	
	public Result queryComment(Map<String, Object> params) throws Exception {
		return new SampleService().queryComment(params);
	}
	
	public Map<String, Object> insertSample(Map<String, Object> params) throws Exception {
		return new SampleService().insertSample(params);
	}
	
	public Result queryStartSample(Map<String, Object> params) throws Exception {
		if (CommonFun.isNe(params.get("GZID"))) {
			return null;
		}
		return new SampleService().queryStartSample(params);
	}
	
	public Map<String, Object> saveSample(Map<String, Object> params) throws Exception {
		return new SampleService().saveSample(params);
	}
	
	public Map<String, Object> saveSamplebyedit(Map<String, Object> params) throws Exception {
		return new SampleService().saveSamplebyedit(params);
	}
	
	public Result querysample(Map<String, Object> params) throws Exception {
		if ("0".equals(params.get("fpdpr"))) {
			params.put("user_no",user().getNo());
		}
		return new SampleService().querysample(params);
	}
	
	public Result querydetail(Map<String, Object> params) throws Exception {
		return new SampleService().querydetail(params);
	}
	
	public int updateSample(Map<String, Object> params) throws Exception {
		if (!CommonFun.isNe(params)) {
			params.put("comment_flag", "1");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("comment_user_mx", params.get("comment_user_mx"));
		map.put("id", params.get("id"));
		new SampleService().assign(map);
		params.remove("comment_user_mx");
		return new SampleService().updateSample(params);
	}
	
	public Result querysystem(Map<String, Object> params) throws Exception {
		return new SampleService().querysystem(params);
	}
	
	/*public List<Record> querysystem(Map<String, Object> params) throws Exception {
		return new SampleService().querysystem(params).getResultset();
	}*/
	
	public Map<String, Object> list(Map<String, Object> params){
		return new SampleService().getDefalutTime();
	}
	
	public Map<String, Object> sampledetail(Map<String, Object> params) throws Exception{
		return new SampleService().sampledetail(params);
	}
	
	public Map<String, Object> samplerandom(Map<String, Object> params){
		return new SampleService().getDefalutTime();
	}
	
	public int remove(Map<String, Object> params) throws Exception {
		return new SampleService().remove(params);
	}
	
	public int updateState(Map<String, Object> params) throws Exception {
		List<Record> assign_persons = new SampleService().queryToHandlerGroup(params);
		params.put("assign_persons", assign_persons);
		if (!CommonFun.isNe(params)) {
			params.put("state", 1);
			new CommentFlowService().submit(params);
			params.put("comment_flag", "2");
			params.remove("state");
			params.remove("assign_persons");
		}
		return new SampleService().updateSample(params);
	}
	
	public int updateState2(Map<String, Object> params) throws Exception {
		if (!CommonFun.isNe(params)) {
			params.put("state", 1);
			params.put("comment_flag", "2");
		}
		return new SampleService().updateSample(params);
	}
	
	public void updateSampleDel(Map<String, Object> params) throws Exception {
		if (!CommonFun.isNe(params)) {
			params.put("is_active", "0");
		}
		new SampleService().updatesampledel(params);
	}
	
	public void updateSampleAdd(Map<String, Object> params) throws Exception {
		if (!CommonFun.isNe(params)) {
			params.put("is_active", "1");
		}
		new SampleService().updatesampleAdd(params);
	}
	
	//患者详情
	public Map<String, Object> patientdetail(Map<String, Object> map) throws Exception{
		return new PatientService().querypatient(map);
	}
	
	public Result queryDept(Map<String, Object> params) throws Exception {
		return new SampleService().queryDept(params);
	}
	
	public Result queryFeibie(Map<String, Object> params) throws Exception {
		return new SampleService().queryFeibie(params);
	}
	
	public Result queryDrug(Map<String, Object> params) throws Exception {
		return new SampleService().queryDrug(params);
	}
	
	public Result queryDoctor(Map<String, Object> params) throws Exception {
		return new SampleService().queryDoctor(params);
	}
	
	/*public Record sampledetail(Map<String, Object> params) throws Exception {
		return new SampleService().sampledetail(params);
	}*/
	
	public void addSample(Map<String, Object> params) throws Exception {
		new SampleService().addSample(params);
	}
	
	public Map<String, Object> sampleshow(Map<String, Object> params) throws Exception {
		return new SampleService().sampledetail(params);
	}
	
	public Map<String, Object> edit(Map<String, Object> params) throws Exception {
		return new SampleService().sampledetail(params);
	}
	
	public Map<String, Object> distribution(Map<String, Object> params) throws Exception {
		return new SampleService().distribution(params);
	}
	
	public Map<String, Object> queryIsOwn(Map<String, Object> params) throws Exception {
		params.put("user_no",user().getNo());
		return new SampleService().queryIsOwn(params);
	}
	
	public Result queryCommentWay(Map<String, Object> params) throws Exception {
		return new SampleService().queryCommentWay(params);
	}
	
	public Record realSaveSample(Map<String, Object> params) throws Exception {
		return new SampleService().realSaveSample(params);
	}
	
	public void sampleAllTMPByGZID(Map<String, Object> params) throws Exception {
		new SampleService().sampleAllTMPByGZID(params);
	}
	
}
