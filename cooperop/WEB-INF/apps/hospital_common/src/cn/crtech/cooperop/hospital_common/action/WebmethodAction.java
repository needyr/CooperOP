package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.hospital_common.service.WebmethodService;
import cn.crtech.precheck.client.Engine;


public class WebmethodAction extends BaseAction{
	
	public int insert(Map<String,Object> params) throws Exception{
		return new WebmethodService().insert(params);
	}
	
	public int delete(Map<String,Object> params) throws Exception{
		stop(params);
		int i = new WebmethodService().delete(params);
		return i;
	}

	public int update(Map<String,Object> params) throws Exception{
		stop(params);
		int r = new WebmethodService().update(params);
		reload(params);
		return  r;
	}

	public Result query(Map<String,Object> params) throws Exception{
		Result rs = new WebmethodService().query(params);
		for (Record re : rs.getResultset()) {
			re.put("running", Engine.containsSchedule(re.getString("data_webservice_code"), re.getString("code")));
			// +++
			re.put("scheduleException", Engine.exceptionMark.containsKey("scheduleException#" + re.getString("code")));
			// +++
		}
		return rs;
	}
	
	public Map<String,Object> edit(Map<String,Object> params) throws Exception{
		return new WebmethodService().get(params);
	}
	
	public void reload(Map<String, Object> params) throws Exception {
		params.put("state", "1");
		new WebmethodService().update(params);
		Engine.schedule((String)params.get("data_webservice_code"), (String)params.get("code"));
	}
	
	public void stop(Map<String, Object> params) throws Exception {
		params.put("state", "0");
		Engine.exceptionMark.remove("scheduleException#" + params.get("code"));
		Engine.removeSchedule((String)params.get("data_webservice_code"), (String)params.get("code"));
	}
}
