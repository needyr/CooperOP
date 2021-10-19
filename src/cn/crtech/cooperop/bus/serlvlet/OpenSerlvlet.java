package cn.crtech.cooperop.bus.serlvlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.util.CommonFun;

@WebServlet(urlPatterns = { "/api/1.0/pharmacist/monitor" })
public class OpenSerlvlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7693261584511734639L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// super.doGet(req, resp);
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// super.doPost(req, resp);
		Map map = CommonFun.json2Object(getBodyString(req), Map.class);
		map.put("clientip", getIpAddr(req));
		int rspCode = 0;
		String errorMsg = "";
		Map<String, Object> rtn = new HashMap<String, Object>();
		try {
			Class<?> c = Class.forName("cn.crtech.cooperop.yaoxunkang.util.YunnCfdaMonitor");
			Method m = c.getDeclaredMethod("pharmacistLive", Map.class);
			rtn.put("result", m.invoke(c.newInstance(), map));
		} catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
			rspCode = -1;
			errorMsg = e.getCause().toString();
			e.printStackTrace();		
		} catch (Throwable ex) {
			rspCode = -1;
			errorMsg = ex.toString();
			ex.printStackTrace();
		}
		if (errorMsg.length() > 0) {
			errorMsg = errorMsg.replace("java.lang.Exception: ", "");
		}
		rtn.put("rspCode", rspCode);
		rtn.put("errorMsg", errorMsg);

		resp.setContentType("application/json;charset=utf-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.write(CommonFun.object2Json(rtn));
		out.flush();
	}

	public static String getIpAddr(HttpServletRequest request) {
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
		if (ip != null && ip.length() > 0) {
			String[] ips = ip.split(",");
			for (int i = 0; i < ips.length; i++) {
				if (ips[i].trim().length() > 0 && !"unknown".equalsIgnoreCase(ips[i].trim())) {
					ip = ips[i].trim();
					break;
				}
			}
		}
		return ip;
	}

	public static String getBodyString(ServletRequest request) throws IOException {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"))) {
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		}
	}
}
