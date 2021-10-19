package cn.crtech.precheck;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.hospital_common.service.VersionControlClientService;

public class IMUPCache {
	public static Map<String, Object> version = new ConcurrentHashMap<String, Object>();//升级
	public static Map<String, Object> version_mx = new ConcurrentHashMap<String, Object>();//是否升级
	
	/**
	* 加载唯一IP客户端程序版本号
	*/
	public static void loadVersion() throws Exception {
		List<Record> resultset = new VersionControlClientService().query(null).getResultset();
		version.clear();
		for (Record record : resultset) {
			version.put(record.getString("ip_address"),record);
		}
	}
	
	/**
	* 加载所有客户端程序版本号
	*/
	public static void loadVersionMx() throws Exception {
		List<Record> resultset = new VersionControlClientService().query_mx(null).getResultset();
		version_mx.clear();
		for (Record record : resultset) {
			version_mx.put(record.getString("p_key"),record);
		}
	}
	
	/**
	* 增加唯一IP客户端程序升级内容
	*/
	public static void addVersion(Map<String, Object> params) throws Exception {
		version.put((String)params.get("ip_address"), params);
	}
	
	/**
	* 增加所有客户端程序升级内容
	*/
	public static void addVersionMx(Map<String, Object> params) throws Exception {
		version_mx.put((String)params.get("p_key"), params);
	}
	
	/**
	* 更新唯一IP客户端程序升级内容
	*/
	public static void updateVersion(Map<String, Object> params) throws Exception {
		Object object = version.get((String)params.get("ip_address"));
		Map<String, Object> map = new HashMap<String, Object>();
		if(object != null) {
			Map<String, Object> new_version = (Map<String, Object>) object;
			Iterator<Entry<String, Object>> entries = params.entrySet().iterator();
			while(entries.hasNext()){
			    Entry<String, Object> entry = entries.next();
			    String key = entry.getKey();
			    String value = (String) entry.getValue();
			    new_version.put(key, value);
			}
			version.put((String)params.get("ip_address"), new_version);
		}
	}
	
	/**
	* 更新所有客户端程序升级内容
	*/
	public static void updateVersionMx(Map<String, Object> params) throws Exception {
		Object object = version_mx.get((String)params.get("p_key"));
		Map<String, Object> map = new HashMap<String, Object>();
		if(object != null) {
			Map<String, Object> new_version = (Map<String, Object>) object;
			Iterator<Entry<String, Object>> entries = params.entrySet().iterator();
			while(entries.hasNext()){
			    Entry<String, Object> entry = entries.next();
			    String key = entry.getKey();
			    String value = (String) entry.getValue();
			    new_version.put(key, value);
			}
			version_mx.put((String)params.get("p_key"), new_version);
		}
	}
}
