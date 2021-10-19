package cn.crtech.cooperop.bus.cache.localcache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import cn.crtech.cooperop.bus.cache.CacheInterface;
import cn.crtech.cooperop.bus.log.log;


public class LocalCache implements CacheInterface {

	private static int expire_time = -1; //过期时间
	private static ConcurrentHashMap<String, ConcurrentHashMap<String, Object>> memmap = new ConcurrentHashMap<String, ConcurrentHashMap<String, Object>>();
	private static ConcurrentHashMap<String, ConcurrentHashMap<String, Long>> tmap = new ConcurrentHashMap<String, ConcurrentHashMap<String, Long>>();
	private static ClearCache cc = null;

	@Override
	public void init(Properties config) throws Exception {
		expire_time = Integer.parseInt(config.getProperty("expire", "-1"));
		if (cc == null) {
			cc = new ClearCache();
			cc.start();
		}
		log.release("init memory cache [localcache] success.");
	}

	@Override
	public void destory() {
		if (cc != null) {
			cc.terminal();
			cc.interrupt();
			try {
				cc.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		memmap.clear();
		tmap.clear();
	}
	
	@Override
	public void put(String area, String key, Object value) {
		put(area, key, value, expire_time);
	}

	@Override
	public void putStr(String area, String key, String value) {
		put(area, key, value, expire_time);
	}

	@Override
	public void put(String area, String key, Object value, int lifetime) {
		if (memmap.containsKey(area)) {
			memmap.get(area).put(key, value);
		} else {
			ConcurrentHashMap<String, Object> am = new ConcurrentHashMap<String, Object>();
			am.put(key, value);
			memmap.put(area, am);
		}
		if (lifetime >= 0) {
			if (tmap.containsKey(area)) {
				tmap.get(area).put(key, System.currentTimeMillis());
			} else {
				ConcurrentHashMap<String, Long> am = new ConcurrentHashMap<String, Long>();
				am.put(key, System.currentTimeMillis());
				tmap.put(area, am);
			}
		}
	}

	@Override
	public void putStr(String area, String key, String value, int lifetime) {
		put(area, key, value, lifetime);
	}

	@Override
	public void putAll(String area, HashMap<String, Object> map) {
		putAll(area, map, expire_time);
	}

	@Override
	public void putAll(String area, HashMap<String, Object> map, int lifetime) {
		if (memmap.containsKey(area)) {
			memmap.get(area).putAll(map);
		} else {
			ConcurrentHashMap<String, Object> am = new ConcurrentHashMap<String, Object>();
			am.putAll(map);
			memmap.put(area, am);
		}
		if (lifetime >= 0) {
			Iterator<String> itr = map.keySet().iterator();
			while (itr.hasNext()) {
				String key = itr.next();
				if (tmap.containsKey(area)) {
					tmap.get(area).put(key, System.currentTimeMillis());
				} else {
					ConcurrentHashMap<String, Long> am = new ConcurrentHashMap<String, Long>();
					am.put(key, System.currentTimeMillis());
					tmap.put(area, am);
				}
			}
		}
	}

	@Override
	public Object get(String area, String key) {
		if (memmap.containsKey(area)) {
			return memmap.get(area).get(key);
		} else {
			ConcurrentHashMap<String, Object> am = new ConcurrentHashMap<String, Object>();
			memmap.put(area, am);
			return am.get(key);
		}
	}

	@Override
	public String getStr(String area, String key) {
		return (String)get(area, key);
	}

	@Override
	public boolean containsKey(String area, String key) {
		if(key == null) return false;
		if (memmap.containsKey(area)) {
			return memmap.get(area).containsKey(key);
		} else {
			ConcurrentHashMap<String, Object> am = new ConcurrentHashMap<String, Object>();
			memmap.put(area, am);
			return am.containsKey(key);
		}
	}

	@Override
	public Object remove(String area, String key) {
		if (memmap.containsKey(area)) {
			return memmap.get(area).remove(key);
		} else {
			ConcurrentHashMap<String, Object> am = new ConcurrentHashMap<String, Object>();
			memmap.put(area, am);
			return am.remove(key);
		}
	}

	@Override
	public String removeStr(String area, String key) {
		return (String)remove(area, key);
	}

	@Override
	public Set<String> areaSet() {
		return memmap.keySet();
	}

	@Override
	public Set<String> keySet(String area) {
		if (memmap.containsKey(area)) {
			return memmap.get(area).keySet();
		} else {
			ConcurrentHashMap<String, Object> am = new ConcurrentHashMap<String, Object>();
			memmap.put(area, am);
			return am.keySet();
		}
	}

	@Override
	public void flushArea(String area) {
		if (memmap.containsKey(area)) {
			memmap.get(area).clear();
		} else {
			ConcurrentHashMap<String, Object> am = new ConcurrentHashMap<String, Object>();
			memmap.put(area, am);
		}
	}

	@Override
	public void flushAll() {
		memmap.clear();
	}
	
	private static class ClearCache extends Thread {
		private static boolean running = true;
		
		public void terminal() {
			running = false;
		}
		
		@Override
		public void run() {
			while (running) {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
				}
				
				if (!running) {
					continue;
				}
				
				if (expire_time >= 0) {
					Set<String> areas = tmap.keySet();
					for (String area : areas) {
						try {
							Set<String> keys = tmap.get(area).keySet();
							for (String key : keys) {
								if (System.currentTimeMillis() - tmap.get(area).get(key) >= expire_time * 1000) {
									tmap.get(area).remove(key);
									memmap.get(area).remove(key);
								}
							}
						} catch (Exception e) {
							log.error(e);
						}
					}
				}
			}
		}
	}

}
