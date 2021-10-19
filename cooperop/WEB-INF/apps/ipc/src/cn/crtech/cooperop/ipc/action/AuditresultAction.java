package cn.crtech.cooperop.ipc.action;

import java.util.HashMap;
import java.util.Map;
import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.ShowTurnsService;
import cn.crtech.cooperop.ipc.service.AuditResultService;
import cn.crtech.cooperop.ipc.service.AutoAuditService;
import cn.crtech.cooperop.ipc.service.SearchRResultService;

public class AuditresultAction extends BaseAction {
	
	@DisLoggedIn
	public Map<String, Object> detail(Map<String, Object> map) throws Exception{
		return new AuditResultService().detail(map);
	}
	
	//同步汇药通药品说明书测试方法
	@DisLoggedIn
	public void test(Map<String, Object> map) throws Exception{
		 new AuditResultService().sysnDrugDetail();
	}
	
	@DisLoggedIn
	public Map<String, Object> particulars(Map<String, Object> map) throws Exception{
		return new AuditResultService().particulars(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> particulars_lishi(Map<String, Object> map) throws Exception{
		return new AuditResultService().particulars_lishi(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> druglist(Map<String, Object> map) throws Exception{
		return new AuditResultService().druglist(map);
	}
	
	public Result druglist2(Map<String, Object> map) throws Exception{
		return new AuditResultService().druglist2(map);
	}
	
	@DisLoggedIn
	public int goodlike(Map<String, Object> map) throws Exception{
		return new AuditResultService().goodlike(map);
	}
	
	/**
	 * 一旦弹窗更改医嘱状态
	 * @param map
	 * @throws Exception
	 */
	@DisLoggedIn
	public void updateNotices(Map<String, Object> map) throws Exception{
		new AuditResultService().updateNotices(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> instruction(Map<String, Object> map) throws Exception{
		return new AuditResultService().getInstruction(new Record(map));
	}
	
	// 查询审查医嘱当前审查状态提示：护士站接口用
	@DisLoggedIn
	public Result getQMsg(Map<String, Object> map) throws Exception{
		return new SearchRResultService().queryQMsg(map);
	}

	@DisLoggedIn
	public Map<String, Object> notices(Map<String, Object> map) throws Exception{
		Map<String, Object> m = new HashMap<String, Object>();
		String s = null;
		map.put("is_overtime", "1");
		map.put("is_sure", "0");
		if(CommonFun.isNe(map.get("uid"))) {
			return null;
		}
		map.put("states","1" );
		Result result = new AutoAuditService().query(map);
		
		if(!CommonFun.isNe(result)) {
			m.put("notices", result.getResultset());
		}
		/*for (Record re :  result.getResultset()) {
			if("Y".equals(re.get("state"))) {
				s = SystemConfig.getSystemConfigValue("ipc", "check_result_y_notice", "药师:pharmacist:state患者:patient的:p_type_name！");
				s = s.replace(":pharmacist", re.getString("yaoshi_name"))
						.replace(":state","<span class='color3'>已通过</span>")
						.replace(":patient", "<span class='color1'>" + re.getString("patient_name") + "</span>")
						.replace(":p_type_name", "1".equals(re.getString("p_type"))?"医嘱":"处方");
			}else if("N".equals(re.get("state"))) {
				s = SystemConfig.getSystemConfigValue("ipc", "check_result_n_notice", "药师:pharmacist:state患者:patient的:p_type_name！");
				s = s.replace(":pharmacist", re.getString("yaoshi_name"))
						.replace(":state","<span class='color2'>已驳回</span>")
						.replace(":patient", re.getString("patient_name"))
						.replace(":p_type_name", "1".equals(re.getString("p_type"))?"医嘱":"处方");
			}else if("D".equals(re.get("state"))) {
				s = SystemConfig.getSystemConfigValue("ipc", "check_result_d_notice", "药师:pharmacist:state患者:patient的:p_type_name，由医生决定是否双签名用药！");
				s = s.replace(":pharmacist", re.getString("yaoshi_name"))
						.replace(":state","<span class='color4'>已返回</span>")
						.replace(":patient", re.getString("patient_name"))
						.replace(":p_type_name", "1".equals(re.getString("p_type"))?"医嘱":"处方");
			}
			
		}*/
		
	
		return m;
	}
   
	@DisLoggedIn
	public void updateCRIResult(Map<String, Object> map) throws Exception{
		new AuditResultService().updateCRIResult(map);
	}
}
