package cn.crtech.precheck.thirdparty;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.hospital_common.util.GetAuditClass;

public class EngineInterface {
	
	public static void init(){
		
	}
	
	public static Map<String, Object> audit(Map<String, Object> req) throws Exception{
		//监听审查是否结束
		Map<String, Object> finalRtn = new HashMap<String, Object>();
		Method m = null;
		m = GetAuditClass.getInstance().ipc_audit_method_thirdparty;
		if(m!=null) {
			try {
				 Object invoke = m.invoke(null, req);
				 if(invoke != null) {
					 finalRtn = (Map<String, Object>) invoke;
				 }
			} catch (Exception e) {
				throw e;
			}
		}
		return finalRtn;
	}
	
}
