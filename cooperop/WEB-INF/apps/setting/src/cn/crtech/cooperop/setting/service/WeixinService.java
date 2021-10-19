package cn.crtech.cooperop.setting.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.setting.dao.WeixinDao;

public class WeixinService extends BaseService{
	public Record get(Map<String, Object> params) throws Exception {
		try {
			connect();
			WeixinDao dd = new WeixinDao();
			Record r = new Record();
			r.put("wx_appid", dd.get("wx_appid")== null ?"":dd.get("wx_appid").get("value"));
			r.put("wx_appsecret", dd.get("wx_appsecret")==null?"":dd.get("wx_appsecret").get("value"));
			return r;
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}


	public void save(Map<String, Object> params) throws Exception {
		try {
			connect();
			start();
			WeixinDao dd = new WeixinDao();
			Record wx_appid = dd.get("wx_appid");
			Record wx_appsecret = dd.get("wx_appsecret");
			if(wx_appid == null){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("code", "wx_appid");
				param.put("value", CommonFun.encryptString((String)params.get("wx_appid")));
				param.put("name","wx_appid");
				param.put("system_product_code", "cooperop");
				dd.insert(param);
			}else {
				 if(!params.get("wx_appid").equals(dd.get("wx_appid").get("value"))){
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("code", "wx_appid");
					param.put("value",CommonFun.encryptString((String)params.get("wx_appid")));
					param.put("name", "wx_appid");
					param.put("system_product_code", "cooperop");
					dd.update(param);
				 }
			}
			if(wx_appsecret == null){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("code", "wx_appsecret");
				param.put("value",CommonFun.encryptString((String)params.get("wx_appsecret")));
				param.put("name","wx_appsecret");
				param.put("system_product_code", "cooperop");
				dd.insert(param);
			}else {
				 if(!params.get("wx_appsecret").equals(dd.get("wx_appsecret").get("value"))){
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("code", "wx_appsecret");
					param.put("value",CommonFun.encryptString((String)params.get("wx_appsecret")));
					param.put("name","wx_appsecret");
					param.put("system_product_code", "cooperop");
					dd.update(param);
				 }
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
