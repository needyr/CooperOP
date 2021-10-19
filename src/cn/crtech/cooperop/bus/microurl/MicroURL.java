package cn.crtech.cooperop.bus.microurl;

import java.util.HashMap;
import java.util.Map;

import cn.crtech.choho.authresource.Resource;
import cn.crtech.choho.authresource.ResourceException;
import cn.crtech.choho.authresource.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class MicroURL {
	public static String create(String url) throws Exception {
		String appid = GlobalVar.getSystemProperty("microurl.appid");
		if (CommonFun.isNe(appid)) throw new Exception("microurl.appid globalvar not setted.");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("url", url);
		Map<String, Object> rtn;
		try {
			rtn = Resource.call(appid, "create", params);
		} catch (ResourceException e) {
			throw new Exception(e);
		}
		return (String) rtn.get("data");
	}
}
