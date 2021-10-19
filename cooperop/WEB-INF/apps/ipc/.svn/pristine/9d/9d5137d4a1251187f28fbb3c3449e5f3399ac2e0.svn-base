package cn.crtech.cooperop.ipc.action;

import java.io.File;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.rm.ResourceManager;
import cn.crtech.cooperop.ipc.schedule.SyncInstruction;
import cn.crtech.cooperop.ipc.schedule.SyncUsersUpdate;
import cn.crtech.cooperop.ipc.service.AuditResultService;
import cn.crtech.cooperop.ipc.service.DrugInstructionService;

public class TestAction extends BaseAction{

	@DisLoggedIn
	public Map<String, Object> test(Map<String, Object> map) throws Exception{
		 new AuditResultService().quickGet();
		 return null ;
	}
	
	@DisLoggedIn
	public Map<String, Object> test_getdrug(Map<String, Object> map) throws Exception{
		return new AuditResultService().test_getDrugUrl(map);
	}

	@DisLoggedIn
	public void getInstruction_new (Map<String, Object> params) throws Exception{
		new DrugInstructionService().formatInstructionBySysCode(new Record(params));
	}

	@DisLoggedIn
	public void getAllInstruction_new (Map<String, Object> params) throws Exception{
		new DrugInstructionService().batchFormatInstruction();;
	}

	@DisLoggedIn
	public Map<String, Object> test_getdrug_one(Map<String, Object> map) throws Exception{
		return new AuditResultService().getbydrugcode_one(map);
	}
	
	@DisLoggedIn
	public Result hyt_audit_test_result(Map<String, Object> map) throws Exception{
		return new AuditResultService().hyt_audit_test_result(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> hyt_audit_test(Map<String, Object> map) throws Exception{
		new AuditResultService().hyt_audit_test(map);
		return null;
	}
	
	@DisLoggedIn
	public Map<String, Object> syncuser(Map<String, Object> map) throws Exception{
		new SyncUsersUpdate().executeOn();
		return null;
	}

	@DisLoggedIn
	public Map<String, Object> syncsms(Map<String, Object> map) throws Exception{
		new SyncInstruction().executeOn();
		return null;
	}

	@DisLoggedIn
	public Map<String, Object> img_test(Map<String, Object> map) throws Exception{
		Record re = new Record();
		re.put("version_no", "1");
		re.put("rowno", 1);
		re.put("create_time", "1587112456233");
		re.put("file_name", "Java Persistence with MyBatis 3(中文版).pdf");
		re.put("last_modify_time", "1587112456233");
		re.put("rm_id", "");
		re.put("description", null);
		re.put("file_size", "2528171.0");
		re.put("is_compress", "0");
		re.put("is_encrypt", "0");
		re.put("path", "/1/1/1");
		re.put("temp_oper", "1");
		re.put("mime_type", "application/pdf");
		re.put("is_temp", "1");
		re.put("file_id", "9f2fbafafe16b81478ee79735e869570");
		re.put("last_modify_user", "CRY0000root");
		re.put("system_product_code", "hospital_common");
		re.put("create_user", "CRY0000root");
		File f = new ResourceManager().getFile(true, re);
		
		return null;
	}
}
