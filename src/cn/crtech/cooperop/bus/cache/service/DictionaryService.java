package cn.crtech.cooperop.bus.cache.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import cn.crtech.cooperop.bus.cache.Dictionary;
import cn.crtech.cooperop.bus.cache.dao.DictionaryDao;
import cn.crtech.cooperop.bus.cache.MemoryCache;
import cn.crtech.cooperop.bus.mvc.control.BaseService;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.rdms.Result;
import cn.crtech.cooperop.bus.util.CommonFun;

public class DictionaryService extends BaseService {
	@Override
	public void connect() throws Exception {
		connect("base");
	}

	public void load() throws Exception {
		HashMap<String, Object> memmap = new HashMap<String, Object>();
		Result result2 = null;
		Result result = null;
		try {
			connect();
			DictionaryDao dao = new DictionaryDao();
			result = dao.loadFields();
			result2 = dao.loadAllOptions();
		}
		catch (Exception ex) {
			log.error("load dictionary failed.", ex);
		} finally {
			disconnect();
		}
		if(result2 == null || result == null){
			return;
		}
		for (Record field : result.getResultset()) {
			String memkey = (String) field.get("fdname");
			field.put("options", new ArrayList<Record>());
			memmap.put(memkey, field);
		}
		for (Record option : result2.getResultset()) {
			String memkey = (String) option.get("fdname");
			Object obj = memmap.get(memkey);
			if(!CommonFun.isNe(obj)){
				Record field = (Record) obj;
				@SuppressWarnings("unchecked")
				List<Record> options = (List<Record>) field.get("options");
				options.add(option);
			}
		}
		MemoryCache.putAll(Dictionary.area, memmap);
	}

	public int updateOptions(String fdname, String system_product_code, List<Map<String, Object>> options) throws Exception {
		try {
			connect();
			DictionaryDao dao = new DictionaryDao();
			start();
			dao.deleteOptions(fdname, system_product_code);
			int i = 0;
			if (options != null ) {
				for (Map<String, Object> option : options) {
					i += dao.insertOptions(fdname, system_product_code, option);
				}
			}
			commit();
			return i;
		} catch (Exception ex) {
			rollback();
			throw ex;
		}
		finally {
			disconnect();
		}
	}

}
