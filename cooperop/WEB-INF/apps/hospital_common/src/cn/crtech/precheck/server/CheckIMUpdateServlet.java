package cn.crtech.precheck.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.hospital_common.service.VersionControlClientService;
import cn.crtech.precheck.IMUPCache;

public class CheckIMUpdateServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	
	public void init() throws ServletException {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		doPost(req, resp);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e2) {
			log.error(e2);
		}
		String xml = "Y";
		try {
			String is_im_update = SystemConfig.getSystemConfigValue("hospital_common", "is_im_update","N");
			if("Y".equals(is_im_update)) {
				BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream(),"utf-8"));
				String line = null;
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				Map<String, Object> version = IMUPCache.version;
				//Map json2Object = CommonFun.json2Object(sb.toString(), Map.class);
				HashMap<String, Object> hmap = CommonFun.requestMap(req);
				Map<String, Object> p = CommonFun.json2Object((String)hmap.get("json"), Map.class);
				if(p != null) {
					String old_version = (String) p.get("version");
					//Object old_version = json2Object.get("version");
					Map<String, Object> map = new HashMap<String, Object>();
					Map<String, Object> cache = new HashMap<String, Object>();
					String im_version = SystemConfig.getSystemConfigValue("hospital_common", "im_version");
					String ipAddr = getIpAddr(req);
					Object data = version.get(ipAddr);
					//if (old_version!=null && !CommonFun.isNe((String) old_version) && im_version.equals((String) old_version)) {
					if (!CommonFun.isNe(old_version) && im_version.equals(old_version)) {
						xml="Y";
						if(CommonFun.isNe(data)) {
							//EngineInterface.loadVersion();
							map.put("ip_address", ipAddr);
							map.put("version", im_version);
							map.put("state", "1");
							map.put("update_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
							cache.putAll(map);
							//new VersionControlClientService().insert(map);
							IMUPCache.addVersion(cache);
						}else {
							if("3".equals(((Map<String, Object>)data).get("state"))) {
								xml="N";
								map.put("ip_address", ipAddr);
								//map.put("version", im_version);
								map.put("state", "2");
								map.put("update_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
								cache.putAll(map);
								//new VersionControlClientService().updateByIp(map);
								IMUPCache.updateVersion(cache);
							}
						}
					}else {
						xml="N";
						if(CommonFun.isNe(data)) {
							map.put("ip_address", ipAddr);
							//map.put("version", im_version);
							map.put("state", "2");
							map.put("update_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
							cache.putAll(map);
							//new VersionControlClientService().insert(map);
							IMUPCache.addVersion(cache);
						}else {
							if("1".equals(((Map<String, Object>)data).get("state")) || "3".equals(((Map<String, Object>)data).get("state"))) {
								map.put("ip_address", ipAddr);
								//map.put("version", im_version);
								map.put("state", "2");
								map.put("update_time", CommonFun.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
								cache.putAll(map);
								//new VersionControlClientService().updateByIp(map);
								IMUPCache.updateVersion(cache);
							}
						}
					}
				}else {
					xml = "N";
				}
			}
		} catch (Throwable e) {
			log.error(e);
		}
		resp.setContentType("text/html; charset=UTF-8");
		try {
			resp.getWriter().write(xml);
			resp.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void destroy() {
		super.destroy();
	}

	// 返回用IP地址
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getRemoteAddr();
		}
		return ip;
	}
	
}
