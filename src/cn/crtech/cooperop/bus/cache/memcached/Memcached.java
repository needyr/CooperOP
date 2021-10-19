package cn.crtech.cooperop.bus.cache.memcached;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import cn.crtech.cooperop.bus.cache.CacheInterface;
import cn.crtech.cooperop.bus.log.log;


public class Memcached implements CacheInterface {
	private static int expire_time = -1; //过期时间
	private static MemcachedClient mcc = new MemcachedClient();

	private static final String AREA_KEY_CONCATER = "~!+M+!~";

	@Override
	public void init(Properties config) throws Exception {
		expire_time = Integer.parseInt(config.getProperty("expire", "-1"));
		
		// 鑾峰彇socke杩炴帴姹犵殑瀹炰緥瀵硅薄
		SockIOPool pool = SockIOPool.getInstance();

		// 璁剧疆鏈嶅姟鍣ㄤ俊鎭�
		pool.setServers(config.getProperty("servers", "127.0.0.1:11211").split(","));
		String[] sws = config.getProperty("weights", "3").split(",");
		Integer[] weights = new Integer[sws.length];
		for (int i = 0; i < sws.length; i++) {
			weights[i] = Integer.parseInt(sws[i]);
		}
		pool.setWeights(weights);

		// 璁剧疆鍒濆杩炴帴鏁般�佹渶灏忓拰鏈�澶ц繛鎺ユ暟浠ュ強鏈�澶у鐞嗘椂闂�
		pool.setInitConn(Integer.parseInt(config.getProperty("initconn", "5")));
		pool.setMinConn(Integer.parseInt(config.getProperty("minconn", "5")));
		pool.setMaxConn(Integer.parseInt(config.getProperty("maxconn", "250")));
		pool.setMaxIdle(Long.parseLong(config.getProperty("maxidle", "21600000")));

		// 璁剧疆涓荤嚎绋嬬殑鐫＄湢鏃堕棿
		pool.setMaintSleep(Long.parseLong(config.getProperty("maintsleep", "30")));

		// 璁剧疆TCP鐨勫弬鏁帮紝杩炴帴瓒呮椂绛�
		pool.setNagle("true".equals(config.getProperty("nagle", "false")));
		pool.setSocketTO(Integer.parseInt(config.getProperty("socketto", "3000")));
		pool.setSocketConnectTO(Integer.parseInt(config.getProperty("socketconnectto", "0")));

		// 鍒濆鍖栬繛鎺ユ睜
		pool.initialize();

		// 鍘嬬缉璁剧疆锛岃秴杩囨寚瀹氬ぇ灏忥紙鍗曚綅涓篕锛夌殑鏁版嵁閮戒細琚帇缂�
		mcc.setCompressEnable("true".equals(config.getProperty("compressenable", "true")));
		mcc.setCompressThreshold(Long.parseLong(config.getProperty("compressthreshold", "65536")));
		if (mcc.stats().size() <= 0) {
			throw new Exception("connect to Memcached server failed");
		}
		log.release("init memory cache [memcached] success.");
	}

	@Override
	public void destory() {
		try {
			SockIOPool pool = SockIOPool.getInstance();
			pool.shutDown();
		} catch (Throwable e) {
			log.error("destory memory cached failed.", e);
		}
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
		String k = area + AREA_KEY_CONCATER + key;
		synchronized (mcc) {
			if (mcc.stats().size() > 0) {
				if (mcc.keyExists(k)) {
					mcc.replace(k, value);
				} else {
					mcc.add(k, value);
				}
			} else {
				log.error("connect to Memcached server failed.");
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
		synchronized (mcc) {
			if (mcc.stats().size() > 0) {
				for (Iterator<String> itemIt = map.keySet().iterator(); itemIt.hasNext();) {
					String key = area + AREA_KEY_CONCATER + itemIt.next();
					if (mcc.keyExists(key)) {
						mcc.replace(key, map.get(key));
					} else {
						mcc.add(key, map.get(key));
					}
				}
			} else {
				log.error("connect to Memcached server failed.");
			}
		}
	}

	@Override
	public Object get(String area, String key) {
		String k = area + AREA_KEY_CONCATER + key;
		synchronized (mcc) {
			if (mcc.stats().size() > 0) {
				return mcc.get(k);
			} else {
				log.error("connect to Memcached server failed.");
				return null;
			}
		}
	}

	@Override
	public String getStr(String area, String key) {
		return (String)get(area, key);
	}


	@Override
	public boolean containsKey(String area, String key) {
		String k = area + AREA_KEY_CONCATER + key;
		synchronized (mcc) {
			if (mcc.stats().size() > 0) {
				return mcc.keyExists(k);
			} else {
				log.error("connect to Memcached server failed.");
				return false;
			}
		}
	}

	@Override
	public Object remove(String area, String key) {
		String k = area + AREA_KEY_CONCATER + key;
		synchronized (mcc) {
			if (mcc.stats().size() > 0) {
				Object value = mcc.get(k);
				mcc.delete(k);
				return value;
			} else {
				log.error("connect to Memcached server failed.");
				return null;
			}
		}
	}

	@Override
	public String removeStr(String area, String key) {
		return (String)removeStr(area, key);
	}

	@Override
	public Set<String> areaSet() {
		synchronized (mcc) {
			if (mcc.stats().size() > 0) {
				Set<String> keyset = new HashSet<String>();

				Map<String, Map<String, String>> items = mcc.statsItems();
				for (Iterator<String> itemIt = items.keySet().iterator(); itemIt.hasNext();) {
					String itemKey = itemIt.next();
					Map<String, String> maps = items.get(itemKey);
					for (Iterator<String> mapsIt = maps.keySet().iterator(); mapsIt.hasNext();) {
						String mapsKey = mapsIt.next();
						String mapsValue = maps.get(mapsKey);
						if (mapsKey.endsWith("number")) {// memcached key 绫诲瀷
															// item_str:integer:number_str
							String[] arr = mapsKey.split(":");
							int slabNumber = Integer.valueOf(arr[1].trim());
							int limit = Integer.valueOf(mapsValue.trim());
							Map<String, Map<String, String>> dumpMaps = mcc.statsCacheDump(slabNumber, limit);
							for (Iterator<String> dumpIt = dumpMaps.keySet().iterator(); dumpIt.hasNext();) {
								String dumpKey = dumpIt.next();
								Map<String, String> allMap = dumpMaps.get(dumpKey);
								for (Iterator<String> allIt = allMap.keySet().iterator(); allIt.hasNext();) {
									String allKey = allIt.next().trim();
									allKey = allKey.split(AREA_KEY_CONCATER)[0];
									if (!keyset.contains(allKey)) {
										keyset.add(allKey);
									}
								}
							}
						}
					}
				}

				return keyset;

			} else {
				log.error("connect to Memcached server failed.");
				return null;
			}
		}
	}

	@Override
	public Set<String> keySet(String area) {
		synchronized (mcc) {
			if (mcc.stats().size() > 0) {
				Set<String> keyset = new HashSet<String>();

				Map<String, Map<String, String>> items = mcc.statsItems();
				for (Iterator<String> itemIt = items.keySet().iterator(); itemIt.hasNext();) {
					String itemKey = itemIt.next();
					Map<String, String> maps = items.get(itemKey);
					for (Iterator<String> mapsIt = maps.keySet().iterator(); mapsIt.hasNext();) {
						String mapsKey = mapsIt.next();
						String mapsValue = maps.get(mapsKey);
						if (mapsKey.endsWith("number")) {// memcached key 绫诲瀷
															// item_str:integer:number_str
							String[] arr = mapsKey.split(":");
							int slabNumber = Integer.valueOf(arr[1].trim());
							int limit = Integer.valueOf(mapsValue.trim());
							Map<String, Map<String, String>> dumpMaps = mcc.statsCacheDump(slabNumber, limit);
							for (Iterator<String> dumpIt = dumpMaps.keySet().iterator(); dumpIt.hasNext();) {
								String dumpKey = dumpIt.next();
								Map<String, String> allMap = dumpMaps.get(dumpKey);
								for (Iterator<String> allIt = allMap.keySet().iterator(); allIt.hasNext();) {
									String allKey = allIt.next().trim();
									if (allKey.startsWith(area + AREA_KEY_CONCATER)) {
										allKey = allKey.split(AREA_KEY_CONCATER)[1];
										if (!keyset.contains(allKey)) {
											keyset.add(allKey);
										}
									}
								}
							}
						}
					}
				}

				return keyset;

			} else {
				log.error("connect to Memcached server failed.");
				return null;
			}
		}
	}

	@Override
	public void flushArea(String area) {
		Set<String> set = keySet(area);
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			remove(area, it.next());
		}
	}

	@Override
	public void flushAll() {
		synchronized (mcc) {
			try {
				mcc.flushAll();
			} catch (Exception e) {
				log.error("娓呯┖Memcached鏁版嵁寮傚父", e);
			}
		}
	}
}
