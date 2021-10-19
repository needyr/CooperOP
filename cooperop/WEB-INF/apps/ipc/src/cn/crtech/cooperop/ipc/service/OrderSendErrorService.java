package cn.crtech.cooperop.ipc.service;

import java.util.Map;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.ipc.dao.OrderSendErrorDao;

/**
 * @className: OrderSendErrorService   
 * @description: 医嘱推送药师端失败service
 * @author: 魏圣峰 
 * @date: 2019年1月16日 上午9:09:06
 */
public class OrderSendErrorService extends BaseService{
	
	/**
	 * @author: 魏圣峰
	 * @description: 查询医嘱推送失败信息
	 * @param: params Map
	 * @return: Result      
	 * @throws Exception
	 */
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("ipc");
			params.put("state", 0);
			return new OrderSendErrorDao().query(params);
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new OrderSendErrorDao().insert(params);
			return 1;
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new OrderSendErrorDao().update(params);
			return 1;
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			new OrderSendErrorDao().delete(params);
			return 1;
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new OrderSendErrorDao().get(params);
		} catch (Exception e) {throw e;}
		finally {disconnect();}
	}
}
