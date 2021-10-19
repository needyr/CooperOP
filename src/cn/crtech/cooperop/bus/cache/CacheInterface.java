package cn.crtech.cooperop.bus.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public interface CacheInterface {

	public void init(Properties config) throws Exception;
	
	public void destory();
	
	public void put(String area, String key, Object value);

	public void putStr(String area, String key, String value);

	public void put(String area, String key, Object value, int lifetime);
	
	public void putStr(String area, String key, String value, int lifetime);
	
	public void putAll(String area, HashMap<String, Object> map);
	
	public void putAll(String area, HashMap<String, Object> map, int lifetime);
	
	public Object get(String area, String key);
	
	public String getStr(String area, String key);
	
	public boolean containsKey(String area, String key);
	
	public Object remove(String area, String key);
	
	public String removeStr(String area, String key);
	
	public Set<String> areaSet();
	
	public Set<String> keySet(String area);
	
	public void flushArea(String area);

	public void flushAll();
	
}
