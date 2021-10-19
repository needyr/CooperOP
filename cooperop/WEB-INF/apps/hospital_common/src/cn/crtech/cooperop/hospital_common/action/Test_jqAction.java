package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.PatientService;
import cn.crtech.cooperop.hospital_common.service.ShowTurnsService;
import cn.crtech.cooperop.hospital_common.service.TestjqService;
import cn.crtech.precheck.server.OptionIFSServlet;
import cn.crtech.ylz.MAEngine;

public class Test_jqAction extends BaseAction{

	public String test_autoaudit(Map<String, Object> params) throws Exception {
		new TestjqService().auto_audit(params);
		return "";
	}
	
	public Result query(Map<String, Object> params) throws Exception {
		Result result = new TestjqService().query(params);
		return result;
	}
	
	public Record queryInfo(Map<String, Object> params) throws Exception {
		Result result = new TestjqService().queryInfo(params);
		return result.getResultset().get(0);
	}
	
	public String test_uploadyun(Map<String, Object> params) throws Exception {
		new TestjqService().uploadToYun(params);
		return "";
	}
	
	//+++++++++++++++++++++++++++++++++++++++++++
	@DisLoggedIn
	public Map<String, Object> detail(Map<String, Object> map) throws Throwable{
		return new ShowTurnsService().detail(map);
	}
	
	
	
	@DisLoggedIn
	public Map<String, Object> backAdjust(Map<String, Object> map) throws Exception{
		return new ShowTurnsService().backAdjust(map);
	}
	
		
	@DisLoggedIn
	public Map<String, Object> patientdetail(Map<String, Object> map) throws Exception{
		return new PatientService().querypatient(map);
	}
		
	@DisLoggedIn
	public Map<String, Object> getexamdetail(Map<String, Object> map) throws Exception{
		return new PatientService().getexamdetail(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> queryvitaldetail(Map<String, Object> map) throws Exception{
		return new PatientService().queryvitaldetail(map);
	}
		
	@DisLoggedIn
	public Map<String, Object> queryrequestendetail(Map<String, Object> map) throws Exception{
		return new PatientService().queryrequestendetail(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> queryOperDetil(Map<String, Object> map) throws Exception{
		return new PatientService().queryOperDetil(map);
	}
	
	
	@DisLoggedIn
	public Map<String, Object> getSimpleInfo(Map<String, Object> map) throws Exception{
		return new ShowTurnsService().getSimpleInfo(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> jsexesql(Map<String, Object> map) throws Exception{
		return new TestjqService().jsexesql(map);
	}
	
	@DisLoggedIn
	public String upctrl(Map<String, Object> map) throws Exception{
		OptionIFSServlet.final_control = Short.parseShort(map.get("val").toString());
		System.out.println("final_control 值更新为" + OptionIFSServlet.final_control);
		return "1";
	}
	
	public void addnewmsg(Map<String, Object> map) throws Exception{
		Record r = new Record(map);
		r.put("send_to_user", "XTY02695128");
		r.put("content", " 这是一条测试记录");
		r.put("is_ignore", 0);
		r.put("source_type", 1);
		r.put("can_is_read", 1);
		r.put("content_detail", "明细数据12839018390821308");
		MAEngine.addNewMsg(r);
	}
	
}
