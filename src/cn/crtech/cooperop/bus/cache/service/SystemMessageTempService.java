package cn.crtech.cooperop.bus.cache.service;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.application.dao.MessageTemplateDao;
import cn.crtech.cooperop.bus.cache.MemoryCache;
import cn.crtech.cooperop.bus.cache.SystemMessageTemplate;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class SystemMessageTempService extends BaseService {
	@Override
	public void connect() throws Exception {
		connect("base");
	}

	public void load() throws Exception {
		HashMap<String, Object> memmap = new HashMap<String, Object>();
		try {
			connect();
			MessageTemplateDao dao = new MessageTemplateDao();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("state", 1);
			Result t = dao.query(map);
			for(Record r : t.getResultset()){
				String cid = r.getString("system_product_code");
				if(!CommonFun.isNe(r.get("action_"))){
					cid = cid + r.getString("action_");
				}else if(!CommonFun.isNe(r.get("pageurl"))){
					cid = cid + r.getString("pageurl");
				}else{
					cid = cid + r.getString("system_product_process_id") + r.getString("system_product_process_node");
				}
				//r.put("templates", new ArrayList<Record>());
				memmap.put(cid, r);
			}
			MemoryCache.putAll(SystemMessageTemplate.prix, memmap);
		} catch (Exception ex) {
			log.error("load system user failed.", ex);
		} finally {
			disconnect();
		}
	}
}
