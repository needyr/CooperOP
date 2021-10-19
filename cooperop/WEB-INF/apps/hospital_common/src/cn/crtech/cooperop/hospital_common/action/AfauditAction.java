package cn.crtech.cooperop.hospital_common.action;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.authenticate.DisLoggedIn;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.AfAuditService;
import cn.crtech.cooperop.hospital_common.service.AfterAutoService;
import cn.crtech.cooperop.hospital_common.service.ShowTurnsService;

/**
 * @ClassName: AfauditAction   
 * @Description: 事后审查action
 * @author: 魏圣峰 
 * @date: 2019年1月9日 下午4:57:29   
 */
public class AfauditAction extends BaseAction {
	
	/**
	 * @author: 魏圣峰
	 * @description: 查询事后审查队列
	 * @param: params Map 筛选条件
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result query(Map<String, Object> params) throws Exception{
		return new AfAuditService().query(params);
	}

	/**
	 * @author: 魏圣峰
	 * @description: 查询当前事后审查队列详情
	 * @param: params Map audit_queue_id
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result queryDetails(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("audit_queue_id"))) {
			return new AfAuditService().queryDetails(params);
		}else {return null;}
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 获取统计信息
	 * @param: params Map audit_queue_id
	 * @return: Result      
	 * @throws Exception
	 */
	public Record getStatistics(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("audit_queue_id"))) {
			return new AfAuditService().getStatistics(params);
		}else {return null;}
	}
	/**
	 * @author: 魏圣峰
	 * @description: 判断当前审查规则是否有修改
	 * @param: params Map      
	 * @return: int 0未更新 1有更新     
	 * @throws: Exception
	 */
	public int isUpdateRegulation(Map<String, Object> params) throws Exception {
		return new AfAuditService().isUpdateRegulation(params);
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 改变事后审查队列状态
	 * @param: params Map 队列state，队列id       
	 * @return: int      
	 * @throws: Exception
	 */
	public int changeState(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("id"))){return -1;}
		new AfAuditService().changeState(params);
		return 1;
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 获取临时筛选队列
	 * @param: params Map 筛选条件  
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result queryTmpQueue(Map<String, Object> params) throws Exception {
		return new AfAuditService().queryTmpQueue(params);
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 获取医嘱信息
	 * @param: params Map patient_id,visit_id
	 * @return: Result      
	 * @throws: Exception
	 */
	public Map<String, Object> druglist(Map<String, Object> params) throws Exception {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("orders", new AfAuditService().queryDrugList(params).getResultset());
		return ret;
	}
	
	/**
	 * @author: 魏圣峰
	 * @description: 添加事后审查队列
	 * @param: params 筛选参数 、remark 队列描述   
	 * @return: int      
	 * @throws: Exception
	 */
	public int insert(Map<String, Object> params) throws Exception {
		return new AfAuditService().insertQueue(params);
	}
	
	public int update(Map<String, Object> params) throws Exception {
		new AfAuditService().update(params);
		return 1;
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("id"))) {return -1;}
		else {return new AfAuditService().delete(params);}
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){return new AfAuditService().get(params);}
		else {return null;}
	}
	
	@DisLoggedIn
	public Map<String, Object> audit_detail(Map<String, Object> map) throws Throwable{
		return new AfAuditService().auditDetail(map);
	}
	
	@DisLoggedIn
	public Map<String, Object> auditonepat(Map<String, Object> map) throws Throwable{
		map.put("doctor_no", user().getNo());
		map.put("doctor_name", user().getName());
		return new AfterAutoService().auditOnePat(map);
	}
}
