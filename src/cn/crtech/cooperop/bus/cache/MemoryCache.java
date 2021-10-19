package cn.crtech.cooperop.bus.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import cn.crtech.cooperop.bus.cache.localcache.LocalCache;
import cn.crtech.cooperop.bus.cache.memcached.Memcached;
import cn.crtech.cooperop.bus.cache.redis.Redis;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class MemoryCache {

	private static CacheInterface cacher = null;

	public static void init() {
		String config = GlobalVar.getSystemProperty("memcached.config");
		if (config != null) {
			if (config.charAt(0) != '/' && config.charAt(1) != ':') {
				config = GlobalVar.getWorkPath() + File.separator + config;
			}

			Properties p = null;
			try {
				p = CommonFun.loadPropertiesFile(config);
				String type = p.getProperty("type", "localcache");

				if ("memcached".equals(type)) {
					cacher = new Memcached();
					cacher.init(p);
				} else if ("redis".equals(type)) {
					cacher = new Redis();
					cacher.init(p);
				} else {
					cacher = new LocalCache();
					cacher.init(p);
				}

				//由于是持久化内存，不能初始化就清空
				//flushAll();
			} catch (Exception e) {
				log.warning(e.getMessage() + ", use local memeory!");
				cacher = new LocalCache();
				try {
					cacher.init(null);
				} catch (Exception ex) {
					log.error("init memory cached failed.", e);
				}
			}
		} else {
			log.warning("no Memcached server no config file, use local memeory!");
			cacher = new LocalCache();
			try {
				cacher.init(null);
				log.release("init memory cached success.");
			} catch (Exception e) {
				log.error("init memory cached failed.", e);
			}
		}
	}

	public synchronized static void destory() {
		if (cacher != null) {
			cacher.destory();
		}
		log.release("destory memory cache success.");
	}

	public static void put(String area, String key, Object value) {
		cacher.put(area, key, value);
	}

	public static void putStr(String area, String key, String value) {
		cacher.putStr(area, key, value);
	}

	public static void put(String area, String key, Object value, int lifetime) {
		cacher.put(area, key, value, lifetime);
	}

	public static void putStr(String area, String key, String value, int lifetime) {
		cacher.putStr(area, key, value, lifetime);
	}

	public static void putAll(String area, HashMap<String, Object> map) {
		cacher.putAll(area, map);
	}

	public static void putAll(String area, HashMap<String, Object> map, int lifetime) {
		cacher.putAll(area, map, lifetime);
	}

	public static Object get(String area, String key) {
		return cacher.get(area, key);
	}

	public static String getStr(String area, String key) {
		return cacher.getStr(area, key);
	}

	public static boolean containsKey(String area, String key) {
		return cacher.containsKey(area, key);
	}

	public static Object remove(String area, String key) {
		return cacher.remove(area, key);
	}

	public static String removeStr(String area, String key) {
		return cacher.removeStr(area, key);
	}

	public static Set<String> areaSet() {
		return cacher.areaSet();
	}

	public static Set<String> keySet(String area) {
		return cacher.keySet(area);
	}

	public static void flushArea(String area) {
		cacher.flushArea(area);
	}

	public static void flushAll() {
		cacher.flushAll();
	}

	public static void main(String[] args) {
		String workpath = System.getProperty("user.dir");
		String configfile = workpath + "\\config\\conf.properties";
		try {
			GlobalVar.init(workpath, configfile);
			log.release("init global vars success.");
			log.init();
			MemoryCache.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.release("init success");

		MemoryCache.put("testArea", "testStr", "9876夏云");
		MemoryCache.put("testArea", "testInt", 123);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "夏云");
		map.put("age", 37);
		map.put("sex", "M");
		map.put("date", new Date());
		MemoryCache.put("testArea", "testMap", map);
		List<Map<String, Object>> array = new ArrayList<Map<String, Object>>();
		array.add(map);
		map = new HashMap<String, Object>();
		map.put("name", "蒋强");
		map.put("age", 32);
		map.put("sex", "M");
		map.put("date", new Date());
		array.add(map);
		MemoryCache.put("testArea", "testArray", array);



		MemoryCache.put("testArea2", "testStr", "夏云");


//		MemoryCache.flushArea("testArea");

//		MemoryCache.flushAll();

		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
		}
		System.exit(0);
	}

}
