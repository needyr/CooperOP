package cn.crtech.cooperop.hospital_common.action.auditset;
import java.util.Map;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.auditset.CheckDeptService;
import cn.crtech.precheck.EngineInterface;

public class CheckdeptAction extends BaseAction{

	public Result query(Map<String, Object> params) throws Exception {
		return new CheckDeptService().query(params);
	}
	
	public Result queryDeptAll(Map<String, Object> params) throws Exception {
		return new CheckDeptService().queryDeptAll(params);
	}
	
	public Result queryToDeptType(Map<String, Object> params) throws Exception {
		return new CheckDeptService().queryToDeptType(params);
	}
	
	public Result querydept(Map<String, Object> params) throws Exception {
		return new CheckDeptService().querydept(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		params.put("oper_user", user().getName() + "[新增]");
		CheckDeptService cds = new CheckDeptService();
		cds.insert(params);
		EngineInterface.loadCheckDept();
		return 1;
	}
	
	public int update(Map<String, Object> params) throws Exception {
		params.put("oper_user", user().getName() + "[修改]");
		CheckDeptService cds = new CheckDeptService();
		cds.update(params);
		EngineInterface.loadCheckDept();
		return 1;
	}
	
	public int open_close(Map<String, Object> params) throws Exception {
		CheckDeptService cds = new CheckDeptService();
		cds.update_state(params);
		EngineInterface.loadCheckDept();
		return 1;
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("id"))) {
			return -1;
		}else {
			CheckDeptService cds = new CheckDeptService();
			cds.delete(params);
			EngineInterface.loadCheckDept();
			return 1;
		}
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){
			return new CheckDeptService().get(params);
		}else {
			return null;
		}
	}
}
