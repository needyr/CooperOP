package cn.crtech.precheck.client;

import java.lang.reflect.Method;
import java.util.Iterator;


import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.precheck.client.Engine;

public class EngineClient{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8359410119784582523L;
	public static final String PARAM_METHOD_NAME = "CLIENT_METHOD";
	public static final String PARAM_ENGINE_NAME = "CLIENT_ENGINE";
	
	public static void init() throws Exception {
		
		String code = GlobalVar.getSystemProperty("webservice.client");
		try {
			Class<?> c = Class.forName("cn.crtech.precheck.client."+code.toLowerCase()+".Engine");
			Method m = c.getDeclaredMethod("init", String.class);
			m.invoke(c.newInstance(), code.toLowerCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void destroy() {
		Iterator<String> it = Engine.engines.keySet().iterator();
		while(it.hasNext()){
			Engine.engines.get(it.next()).destroy();
		}
	}
}
