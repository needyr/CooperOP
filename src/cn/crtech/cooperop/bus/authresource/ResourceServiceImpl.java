package cn.crtech.cooperop.bus.authresource;

import java.lang.reflect.Method;
import java.util.Map;

import cn.crtech.choho.authresource.ResourceException;
import cn.crtech.choho.authresource.ResourceService;
import cn.crtech.choho.authresource.bean.License;
import cn.crtech.cooperop.application.authenticate.Authenticate;
import cn.crtech.cooperop.application.service.CommonService;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.LocalThreadMap;

public class ResourceServiceImpl implements ResourceService {
	
	@Override
	public Object execute(Map<String, Object> clientinfo, Map<String, Object> userinfo, String action, Map<String, Object> params) throws ResourceException {
		Map<String, Object> map = Authenticate.getAction(action);
		if (map == null) {
			throw new ResourceException(ResourceException.ERROR.RESOURCE_NOT_FOUND);
		}
		Class<?> c = (Class<?>) map.get("class");
		Method m = (Method) map.get("method");
		
		long threadid = Thread.currentThread().getId();
		log.debug("call resource action[Thread-" + threadid + "][start]: " + c.getName() + "." + m.getName() + "(" + params + ")");
		
		LocalThreadMap.put("clientinfo", clientinfo);
		LocalThreadMap.put("userinfo", userinfo);
		try {
			return m.invoke(c.newInstance(), params);
		} catch (Exception e) {
			throw new ResourceException(ResourceException.ERROR.RESOURCE_SERVICE_ERROR, (e.getCause() == null ? e : e.getCause()));
		} finally {
			LocalThreadMap.clear();
		}
	}
	
	@Override
	public void loadedLicense() {
		try {
			//new CommonService().loadLicense();
			log.debug("load license success: " + License.toJSON());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void refreshedLicense() {
		try {
			//new CommonService().loadLicense();
			log.debug("refresh license success: " + License.toJSON());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
