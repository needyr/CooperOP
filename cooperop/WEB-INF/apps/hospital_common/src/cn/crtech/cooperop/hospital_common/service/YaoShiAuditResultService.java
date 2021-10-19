package cn.crtech.cooperop.hospital_common.service;

import java.util.Map;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.YaoShiAuditResultDao;

/**
 * @className: YaoShiAuditResultDao   
 * @description: 药师审查结果
 * @author: 魏圣峰 
 * @date: 2019年1月28日 下午7:32:14
 */
public class YaoShiAuditResultService extends BaseService{
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new YaoShiAuditResultDao().get(params);
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	public Result getyzsAll(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			if(CommonFun.isNe(params.get("auto_audit_id"))) {
				return null;
			}
			return new YaoShiAuditResultDao().getyzsAll(params);
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	/**
	 * @description: 获取药师的点评
	 * @param: params Map     
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result queryByAuditId(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			return new YaoShiAuditResultDao().queryByAuditId(params);
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	/**
	 * @description: 获取医嘱
	 * @param: params Map      
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result queryOrdersGroup(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			if(CommonFun.isNe(params.get("auto_audit_id"))) {
				return null;
			}
			return new YaoShiAuditResultDao().queryOrdersGroup(params);
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	/**
	 * @description: 审查结果详情
	 * @param: params Map  
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result queryCheckResultInfo(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			if(CommonFun.isNe(params.get("auto_audit_id"))) {
				return null;
			}
			return new YaoShiAuditResultDao().queryCheckResultInfo(params);
		} catch (Exception e) {throw e;}
		finally {disconnect();}                             
	}
}
