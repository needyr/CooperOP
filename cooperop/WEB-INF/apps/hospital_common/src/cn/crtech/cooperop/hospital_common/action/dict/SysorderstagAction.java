package cn.crtech.cooperop.hospital_common.action.dict;

import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseAction;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.dict.SysOrdersTagService;

public class SysorderstagAction extends BaseAction{
	
	public Result query(Map<String, Object> params) throws Exception {
		return new SysOrdersTagService().query(params);
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		return new SysOrdersTagService().insert(params);
	}

	public int update(Map<String, Object> params) throws Exception {
		return new SysOrdersTagService().update(params);
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		return new SysOrdersTagService().delete(params);
	}
	
	public Record edit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("ordertagid"))){
			return new SysOrdersTagService().get(params);
		}else {
			return null;
		}
	}
	
	/**********************************以下为医嘱属性值的维护**************************************/
	
	public Result queryOrdersValue(Map<String, Object> params) throws Exception {
		return new SysOrdersTagService().queryOrdersValue(params);
	}
	
	public int ordersValueInsert(Map<String, Object> params) throws Exception {
		if("大于".equals(params.get("tiaojian"))) {
			params.put("tiaojian", ">");
		}else if("小于".equals(params.get("tiaojian"))) {
			params.put("tiaojian", "<");
		}else if("等于".equals(params.get("tiaojian"))) {
			params.put("tiaojian", "=");
		}else if("不等于".equals(params.get("tiaojian"))) {
			params.put("tiaojian", "<>");
		}else if("类似".equals(params.get("tiaojian"))) {
			params.put("tiaojian", "like");
		}else if("不类似".equals(params.get("tiaojian"))) {
			params.put("tiaojian", "not like");
		}
		return new SysOrdersTagService().ordersValueInsert(params);
	}

	public int ordersValueUpdate(Map<String, Object> params) throws Exception {
		if("大于".equals(params.get("tiaojian"))) {
			params.put("tiaojian", ">");
		}else if("小于".equals(params.get("tiaojian"))) {
			params.put("tiaojian", "<");
		}else if("等于".equals(params.get("tiaojian"))) {
			params.put("tiaojian", "=");
		}else if("不等于".equals(params.get("tiaojian"))) {
			params.put("tiaojian", "<>");
		}else if("类似".equals(params.get("tiaojian"))) {
			params.put("tiaojian", "like");
		}else if("不类似".equals(params.get("tiaojian"))) {
			params.put("tiaojian", "not like");
		}
		return new SysOrdersTagService().ordersValueUpdate(params);
	}
	
	public int ordersValueDelete(Map<String, Object> params) throws Exception {
		return new SysOrdersTagService().ordersValueDelete(params);
	}
	
	public Record mxedit(Map<String, Object> params) throws Exception {
		if(!CommonFun.isNe(params.get("id"))){
			return new SysOrdersTagService().ordersValueGet(params);
		}else {
			return null;
		}
	}
}
