package cn.crtech.cooperop.hospital_common.service.dict;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.dao.dict.SysOrdersTagDao;

public class SysOrdersTagService extends BaseService{
	
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysOrdersTagDao().query(params);
		} finally {
			disconnect();
		}
	}
	
	public int insert(Map<String, Object> params) throws Exception {
		try {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("ordertagbh", params.get("ordertagbh"));
			connect("hospital_common");
			SysOrdersTagDao sysdicttagdao = new SysOrdersTagDao();
			Record record = sysdicttagdao.get(map);
			if (record==null) { 
				return sysdicttagdao.insert(params);
			}else {
				return 2;
			}
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int update(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysOrdersTagDao().update(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int delete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysOrdersTagDao().delete(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysOrdersTagDao().get(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	/**********************************以下为医嘱属性值的维护**************************************/
	
	public Result queryOrdersValue(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysOrdersTagDao().queryOrdersValue(params);
		} finally {
			disconnect();
		}
	}
	
	public int ordersValueInsert(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			SysOrdersTagDao orderstagdao = new SysOrdersTagDao();
			if(CommonFun.isNe(params.get("tiaojian"))) {
				params.put("tiaojian", "=");
			}
			int insert = orderstagdao.ordersValueInsert(params);
			return insert;
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int ordersValueUpdate(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysOrdersTagDao().ordersValueUpdate(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public int ordersValueDelete(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysOrdersTagDao().ordersValueDelete(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
	
	public Record ordersValueGet(Map<String, Object> params) throws Exception {
		try {
			connect("hospital_common");
			return new SysOrdersTagDao().ordersValueGet(params);
		} catch (Exception e) {
			throw e;
		}finally {
			disconnect();
		}
	}
}
