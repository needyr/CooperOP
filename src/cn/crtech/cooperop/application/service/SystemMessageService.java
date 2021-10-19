package cn.crtech.cooperop.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import cn.crtech.cooperop.application.dao.MessageTemplateDao;
import cn.crtech.cooperop.application.dao.SystemMessageDao;
import cn.crtech.cooperop.bus.microurl.MicroURL;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class SystemMessageService extends BaseService {
	public Result query(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new SystemMessageDao().query(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public Result messagenum(Map<String, Object> params) throws Exception {
		try {
			connect();
			return new SystemMessageDao().messagenum(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void insert(Map<String, Object> params) throws Exception {
		try {
			connect();
			new SystemMessageDao().insert(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void ignoreAll(Map<String, Object> params) throws Exception {
		try {
			connect();
			new SystemMessageDao().ignoreAll(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void add(Map<String, Object> params) throws Exception {
		try {
			connect();
			new SystemMessageDao().get(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void update(Map<String, Object> params) throws Exception {
		try {
			connect();
			new SystemMessageDao().update(params);
		} catch (Exception ex) {
			throw ex;
		} finally {
			disconnect();
		}
	}
	public void saveBatch(Map<String, Object>  m, Map<String, Object> params) throws Exception {
		saveBatch(m, params, null);
	}
	public void saveBatch(Map<String, Object>  m, Map<String, Object> params, String[] send_to) throws Exception {
		try {
			connect("base");
			MessageTemplateDao mtd = new MessageTemplateDao();
			m.put("state", 1);
			m.put("t_state", 1);
			Result r =  mtd.queryTemplate(m);
			if(r != null && r.getResultset().size()>0){
				disconnect();
				connect(r.getResultset(0).getString("system_product_code"));
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				SystemMessageDao smd = new SystemMessageDao();
				for(Record re: r.getResultset()){
					if("1".equals(re.get("state"))){
						if(CommonFun.isNe(re.get("condition_sql")) || "Y".equals(getResultString(smd.executeQuery(re.getString("condition_sql"), params),"Y"))){
							Map<String, Object> p = new HashMap<String, Object>();
							Record rec = smd.executeQuerySingleRecord(re.getString("content_sql"), params);
							if(rec != null){
								Iterator<String> it = rec.keySet().iterator();
								while(it.hasNext()){
									String key = it.next();
									if(rec.getString(key).startsWith("ws:")){
										String v = rec.getString(key).replace("ws:", "");
										rec.put(key, MicroURL.create(v));
									}
								}
								p.put("content_param", CommonFun.object2Json(rec));
							}
							if(re.get("subject") != null){
								p.put("subject", getResultString(smd.executeQuery(re.getString("subject"), params),""));
							}
							p.put("content", CommonFun.isNe(re.get("content"))? getResultString(smd.executeQuery(re.getString("content_sql"), params), ""):re.get("content"));
							p.put("content_template_id", re.get("content_template_id"));
							p.put("type", re.get("type"));
							p.put("template_id", re.get("template_id"));
							p.put("tunnel_id", re.get("tunnel_id"));
							p.put("state", 0);
							p.put("send_from", "");//TODO from
							p.put("system_product_code", m.get("system_product_code"));
							Result res = smd.executeQuery(re.getString("sendto_sql"), params);
							p.put("sendto_", res);
							list.add(p);
						}
					}
				}
				disconnect();
				connect("base");
				smd = new SystemMessageDao();
				start();
				for(Map<String, Object> re: list){
					Result res = (Result) re.remove("sendto_");
					if(res != null && res.getResultset().size() > 0){
						String[] sends = getResultString(res, "").split(",");
						for(String s : sends){
							if(s != ""){
								re.put("send_to", s);
								smd.insert(re);
							}
						}
					}else if (send_to != null){
						//p.put("send_to", StringUtils.join(send_to, ","));
						for(String s : send_to){
							re.put("send_to", s);
							smd.insert(re);
						}
					}
								
				}
				commit();
			}
		} catch (Exception ex) {
			rollback();
			throw ex;
		} finally {
			disconnect();
		}
	}
	
	public String getResultString(Result rs, String def) throws Exception {
			if (rs.getCount() > 0) {
				Iterator<String> keys = rs.getResultset(0).keySet().iterator();
				while (keys.hasNext()) {
					String key = keys.next();
					if (!Result.ROWNO_NAME.equals(key)) {
						String s = null;
						for(Record r : rs.getResultset()){
							if(s == null){
								s = r.getString(key);
							}else{
								s = s + "," + r.getString(key);
							}
						}
						if(s != null){
							return s;
						}
					}
				}
			}
			return def;
	}
}
