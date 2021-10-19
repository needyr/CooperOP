package cn.crtech.cooperop.bus.mvc.control;


import java.util.Map;

import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CommonFun;

public class BaseAction {
    public boolean sessionContains(String key) throws Exception {
		Session session = Session.getSession();
		return session.containsKey(key);
	}

    public Session session(){
    	return Session.getSession();
    }
    public Object session(String key) throws Exception {
		Session session = Session.getSession();
		if (session == null) return null;
		return session.get(key);
	}

	public Object sessionDefault(String key, Object defaultvalue) throws Exception {
    	Object value =  session(key);
    	if (value == null) {
    		return defaultvalue;
    	}
    	else {
    		return value;
    	}
	}

	public void session(String key, Object value) throws Exception {
		Session session = Session.getSession();
		if (session == null) return;
		session.put(key, value);
	}
	
	public Object sessionRemove(String key) throws Exception {
		Session session = Session.getSession();
		if (session == null) return null;
		return session.remove(key);
	}
	
	public void sessionClear() throws Exception {
		Session session = Session.getSession();
		if (session == null) return;
		session.clear();
	}
	
	public Account user() throws Exception {
		return (Account)session("userinfo");
	}

	/**
	 * @param sourceMap
	 * @param resultMap
	 * @param key
	 */
	public void setParamMap(String key,Map<String, Object> sourceMap, Map<String, Object> resultMap) {
		setParamMap(key, sourceMap, resultMap, false);
	}
	/**
	 * @param sourceMap
	 * @param resultMap
	 * @param key
	 */
	public void setParamMapNull(String key,Map<String, Object> sourceMap, Map<String, Object> resultMap) {
		setParamMapNull(key, sourceMap, resultMap, false);
	}
	/**
	 * @param sourceMap
	 * @param resultMap
	 * @param key
	 */
	public void setParamMap(String key,Map<String, Object> sourceMap, Map<String, Object> resultMap,Boolean isDate) {
		if(sourceMap.containsKey(key)){
			Object obj = sourceMap.get(key);
			if(!CommonFun.isNe(obj)){
				if(isDate){
					resultMap.put(key, CommonFun.parseDate(obj.toString()));
				}else{
					resultMap.put(key, obj);
				}
			}
		}
	}
	/**
	 * @param sourceMap
	 * @param resultMap
	 * @param key
	 */
	public void setParamMapNull(String key,Map<String, Object> sourceMap, Map<String, Object> resultMap,Boolean isDate) {
		if(sourceMap.containsKey(key)){
			Object obj = sourceMap.get(key);
//			if(!CommonFun.isNe(obj)){
				if(isDate && !CommonFun.isNe(obj)){
					resultMap.put(key, CommonFun.parseDate(obj.toString()));
				}else{
					resultMap.put(key, obj);
				}
//			}
		}
	}
}
