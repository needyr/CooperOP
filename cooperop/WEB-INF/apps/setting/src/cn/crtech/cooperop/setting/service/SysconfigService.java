package cn.crtech.cooperop.setting.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.setting.dao.SysconfigDao;

public class SysconfigService extends BaseService{
	public Record query(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new SysconfigDao().query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new SysconfigDao().get((String)params.get("code"));
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Record getQrcode(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			SysconfigDao sc = new SysconfigDao();
			Record r = new Record();
			Record r1 = sc.get("android_qrcode");
			Record r2 = sc.get("ios_qrcode");
			Record r3 = sc.get("check_deviceId");
			if(r1 == null){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("code", "android_qrcode");
				param.put("name", "安卓二维码");
				param.put("system_product_code", "cooperop");
				sc.insert(param);
			}else{
				r.put("android_qrcode", r1.get("value"));
			}
			if(r2 == null){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("code", "ios_qrcode");
				param.put("name", "苹果二维码");
				param.put("system_product_code", "cooperop");
				sc.insert(param);
			}else{
				r.put("ios_qrcode", r2.get("value"));
			}
			if(r3 == null){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("code", "check_deviceId");
				param.put("name", "开启app校验");
				param.put("system_product_code", "cooperop");
				sc.insert(param);
			}else{
				r.put("check_deviceId", r3.get("value"));
			}
			commit();
			return r; 
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}

	public void save(Map<String, Object> params) throws Exception {
		try {
			connect();
			new SysconfigDao().insert(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void update(Map<String, Object> params) throws Exception {
		try {
			connect();
			new SysconfigDao().update(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public void saveBatch(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			SysconfigDao sc = new SysconfigDao();
			Iterator<String> keys = params.keySet().iterator();
			while(keys.hasNext()){
				String key = keys.next();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("code", key);
				param.put("value", params.get(key));
				sc.update(param);
			}
			commit();
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
}
