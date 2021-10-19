package cn.crtech.precheck.thirdparty;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.cache.SystemConfig;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.ylz.ylz;

public class ThirdpartyAuditServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private static final long MAX_VISIT_NUM = 5;
	
	private static long visit_num = 0;
	
	public void init() throws ServletException {
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(visit_num <= MAX_VISIT_NUM) {
			visit_num += 1;
			try {
				String bodytxt = getBodytxt(req);
				//reqParams.put("req_ip", getIpAddr(req));
				//去除请求串特殊字符，防止类型转换失败
				bodytxt = bodytxt.replaceAll("[\\t\\n\\r]", "");
				bodytxt = bodytxt.replaceAll("\\\\", "/");
				
				Map<String, Object> detection = JsonCheck.detection(bodytxt);
				if(!CommonFun.isNe(detection.get("status")) && (boolean)detection.get("status") == true) {
					result.put("status", "error");
					result.put("error_msg", detection.get("err"));
				}else {
					if(SystemConfig.getSystemConfigValue("ipc", "ipc.thirdparty.active", "1") == "0") {
						result.put("status", "interface_close");
					}else {
						result = ifsAudit(bodytxt);
					}
				}
			} catch (Throwable e) {
				result.put("status", "error");
				result.put("error_msg", e);
			}finally {
				visit_num -= 1;
			}
		}else {
			result.put("status", "error");
			result.put("error_msg", "超过最大访问量!不进行审查!");
		}
		
		try {
			resp.setContentType("text/html; charset=UTF-8");
			resp.setHeader("Access-Control-Allow-Origin", "*");
			resp.getWriter().write(CommonFun.object2Json(result));
			resp.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		super.destroy();
	}
	
	// 审查
	public Map<String, Object> ifsAudit(String req_json) throws Throwable {
		Map<String, Object> result = new HashMap<String, Object>();
		// 参数校验
		boolean pvpass = true;
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> p = CommonFun.json2Object(req_json, Map.class);
			//List<Map<String, Object>> object = (List<Map<String, Object>>)p.get("orders");
			//Map<String, Object> object = (Map<String, Object>)p.get("orders");
			//Map<String, Object> map = object.get(0);
			// 事后审查标识
			if(CommonFun.isNe(p.get("orders"))) {
				pvpass = false;
				ylz.p("判定为未开具医嘱空提交,不审查");
			}
			// 参数校验 end
			if(pvpass) {
				//调用审查
				Map<String, Object> audit_result = new HashMap<String, Object>();
				audit_result = EngineInterface.audit(p);
				if(!CommonFun.isNe(audit_result)) {
					result.putAll(audit_result);
				}
			}
		} catch (Throwable e) {
			throw e;
		} finally {
		}
		return result;
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
	
	public String getBodytxt(HttpServletRequest request) throws Exception {
		BufferedReader br = null;
		String str, wholeStr = "";
		try {
			br = request.getReader();
			while((str = br.readLine()) != null){
				wholeStr += str;
			}
		} catch (Exception e) {
			throw e;
		}finally {
			if(br != null) {
				br.close();
			}
		}
		return wholeStr;
	}
}
