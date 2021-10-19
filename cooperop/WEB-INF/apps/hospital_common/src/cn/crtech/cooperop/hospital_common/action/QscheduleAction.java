package cn.crtech.cooperop.hospital_common.action;

import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.QscheduleService;
/**
 * @author YAN
 *
 */
@DisLoggedIn
public class QscheduleAction extends BaseAction {
	
	public Result query(Map<String, Object> params) throws Exception {
		return new QscheduleService().query(params);
	}
	
	public Result queryProduct(Map<String, Object> params) throws Exception {
		return new QscheduleService().queryProduct(params);
	}

	//手动执行一次
	public void execute(Map<String, Object> params) throws Exception {
		 new QscheduleService().execute(params);
	}

	public int update(Map<String, Object> params) throws Exception {
		return new QscheduleService().update(params);
	}
	
	public int updateByState(Map<String, Object> params) throws Exception {
		return new QscheduleService().updateByState(params);
	}
	
	//修改IADSCP.dbo.schedule表的定时器
	public int updateByCode(Map<String, Object> params) throws Exception {
		return new QscheduleService().updateByCode(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return new QscheduleService().insert(params);
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("p_key"))){
			return new QscheduleService().get(params);
		}else {
			return null;
		}
	}
}
