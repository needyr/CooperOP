package cn.crtech.cooperop.bus.cache;

import cn.crtech.cooperop.bus.cache.service.SystemConfigService;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.cache.MemoryCache;
import cn.crtech.cooperop.bus.util.CommonFun;

public class SystemConfig {

	public static final String prix = "system.config";

	public static Record getSystemConfig(String productCode, String code)
			throws Exception {
		String key = productCode + "." + code;
		if (CommonFun.isNe(productCode)) {
			key = code;
		}
		Record c = null;
		if(MemoryCache.containsKey(prix, key)){
			c = (Record) MemoryCache.get(prix, key);
		}else if(CommonFun.isNe(productCode)){
			key = "base."+code;
			c = (Record) MemoryCache.get(prix, key);
		}
		return c;
	}

	public static String getSystemConfigValue(String productCode, String code, String default_value) {
		String value = getSystemConfigValue(productCode, code);
		if (CommonFun.isNe(value))
			return default_value;
		return value;
	}

	public static String getSystemConfigValue(String productCode, String code){
		String key = productCode + "." + code;
		if (CommonFun.isNe(productCode)) {
			key = code;
		}
		Record c = null;
		if(MemoryCache.containsKey(prix, key)){
			c = (Record) MemoryCache.get(prix, key);
		}else if(CommonFun.isNe(productCode)){
			key = "base."+code;
			c = (Record) MemoryCache.get(prix, key);
		}
		if (c != null) {
			return c.getString("value");
		}
		else {
			return null;
		}
	}

	public static void load() throws Exception {
		MemoryCache.flushArea(prix);
		SystemConfigService service = new SystemConfigService();
		service.load();
		log.release("load system config success.");
	}
}
