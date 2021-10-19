package cn.crtech.cooperop.bus.util;

import java.util.HashMap;
import java.util.Map;

public class LocalThreadMap {
	private static ThreadLocal<Map<String, Object>> localMap = new ThreadLocal<Map<String, Object>>();
    
    public static void put(String key, Object obj) {
		if (localMap.get() == null) {
			localMap.set(new HashMap<String,Object>());
		}
		localMap.get().put(key, obj);
    }
    
    public static Object get(String key) {
    	if (localMap.get() == null) {
			localMap.set(new HashMap<String,Object>());
		}
		return localMap.get().get(key);
    }

    public static Object remove(String key) {
    	if (localMap.get() == null) {
			localMap.set(new HashMap<String,Object>());
		}
		return localMap.get().remove(key);
    }

	public static void clear() {
		if (localMap.get() == null) {
			localMap.set(new HashMap<String, Object>());
		}
		localMap.get().clear();
	}

}
