package cn.crtech.cooperop.ipc.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ShowTurnsService;
import cn.crtech.cooperop.ipc.service.AuditFlowService;
import cn.crtech.cooperop.ipc.service.AuditResultService;
import cn.crtech.precheck.EngineInterface;

public class AuditflowAction extends BaseAction {
	
	@DisLoggedIn
	public Map<String, Object> detail(Map<String, Object> map) throws Exception{
		return new AuditFlowService().get(map);
	}
	
	public Map<String, Object> approval(Map<String, Object> map) throws Exception{
		new AuditFlowService().approval(map);
		return map;
	}

	/**
	 * @author: 魏圣峰
	 * @Description: 超时  医生决定的处理
	 * @param: map Map
	 * @return: Map    
	 * @throws Throwable
	 */
	@DisLoggedIn
	public Map<String, Object> timeoutdetail(Map<String, Object> map) throws Throwable{
		Map<String, Object> map2 = new AuditResultService().timeOutDetail(map);
		map.put("id", map2.get("common_id"));
		map.put("common_id", map2.get("common_id"));
		Map<String, Object> info =  new ShowTurnsService().getBasicInfo(map);
		info.putAll(map2);
		info.putAll(EngineInterface.getShowData(map));
		return info;
	}
	@DisLoggedIn
	public Map<String, Object> ChangeState(Map<String, Object> map) throws Throwable{
		/*Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("id", map.get("auto_audit_id"));
		map2.put("is_sure", 1);
		new AutoAuditService().update(map2);*/
		if(CommonFun.isNe(map.get("uid")) || "".equals(map.get("uid"))) {
			map.put("uid", user().getId());
		}
		String approval = new AuditFlowService().approval(map);
		map.put("is_success", approval);
		return map;
	}
	
	/** 审查结果显示 */
	@DisLoggedIn
	public Map<String, Object> show(Map<String, Object> map) throws Throwable{
		Map<String, Object> map2 = new AuditResultService().timeOutDetail(map);
		map.put("id", map2.get("common_id"));
		map.put("common_id", map2.get("common_id"));
		Map<String, Object> info =  new ShowTurnsService().getBasicInfo(map);
		info.putAll(map2);
		info.putAll(EngineInterface.getShowData(map));
		return info;
	}
	
	@DisLoggedIn
	public Map<String, Object> show_ywlsb(Map<String, Object> map) throws Throwable{
		Map<String, Object> map2 = new AuditResultService().show_ywlsb(map);
		Map<String, Object> info =  new ShowTurnsService().getBasicInfoByYwlsb(map);
		info.putAll(map2);
		return info;
	}
	
}
