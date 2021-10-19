package cn.crtech.cooperop.ipc.action;

import java.util.Map;
import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.ipc.service.OrderSendErrorService;

/**
 * @className: OrdersenderrorAction   
 * @description: 医嘱推送药师端失败action
 * @author: 魏圣峰 
 * @date: 2019年1月16日 上午9:06:45
 */
public class Order_send_errorAction extends BaseAction {
	
	/**
	 * @author: 魏圣峰
	 * @description: 查询医嘱推送失败信息
	 * @param: params Map
	 * @return: Result      
	 * @throws: Exception
	 */
	public Result query(Map<String, Object> params) throws Exception{
		return new OrderSendErrorService().query(params);
	}

	public int insert(Map<String, Object> params) throws Exception {
		new OrderSendErrorService().insert(params);
		return 1;
	}
	
	public int update(Map<String, Object> params) throws Exception {
		new OrderSendErrorService().update(params);
		return 1;
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		if(CommonFun.isNe(params.get("id"))) {return -1;}
		else {return new OrderSendErrorService().delete(params);}
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){return new OrderSendErrorService().get(params);}
		else {return null;}
	}
}
