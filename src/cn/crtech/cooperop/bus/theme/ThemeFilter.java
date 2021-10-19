package cn.crtech.cooperop.bus.theme;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.engine.Engine;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class ThemeFilter extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3169358395817154177L;

	@Override
	public void init(ServletConfig config) throws ServletException {
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String loginType = CommonFun.getLoginType(req);
		String cp = req.getContextPath() == null ? "" : req.getContextPath();
		String sp = req.getServletPath();
		String uri = req.getRequestURI().substring((cp + sp).length());

		String module = null;
		String referer = req.getHeader("referer");
		if (!CommonFun.isNe(referer)) {
			Map<String, Object> rp =  CommonFun.requestMap(referer);
			if (rp.containsKey("m")) {
				module = (String) rp.get("m");
			} else {
				referer = referer.substring(referer.indexOf("://") + 2);
				referer = referer.substring(referer.indexOf("/", 1));
				if (Engine.getContextPath() !=null && !CommonFun.isNe(referer) && referer.lastIndexOf(".")>0 && referer.length() > (Engine.getContextPath() + Engine.getServletPath()).length()) {
					referer = referer.substring((Engine.getContextPath() + Engine.getServletPath()).length() + 1);
					String pageid = referer.substring(0, referer.lastIndexOf(".")).replace('/', '.');
					module = pageid.indexOf('.') < 0 ? pageid : pageid.substring(0, pageid.indexOf('.'));
				}
			}
		} else {
			Map<String, Object> rp = CommonFun.requestMap(req);
			if (rp.containsKey("m")) {
				module = (String) rp.get("m");
			}
		}

		String path = GlobalVar.getSystemProperty("theme." + module + ".pc.path", GlobalVar.getSystemProperty("theme.pc.path")) + uri;
		if ("m".equals(loginType)) {
			path = GlobalVar.getSystemProperty("theme." + module + ".app.path", GlobalVar.getSystemProperty("theme.app.path")) + uri;
		}else if("p".equals(loginType)){
			path = GlobalVar.getSystemProperty("theme." + module + ".pad.path", GlobalVar.getSystemProperty("theme.pad.path")) + uri;
		}else if("w".equals(loginType)){
			path = GlobalVar.getSystemProperty("theme." + module + ".wx.path", GlobalVar.getSystemProperty("theme.wx.path")) + uri;
		}

		// 判断不是文件路径，同时与servlet监听不同（相同会导致死循环溢出）
		if (path.charAt(0) != '/' && path.charAt(1) != ':' && !path.startsWith(sp.substring(1) + "/")) {
			path = "/" + path;
			setCacheControl(resp);
			RequestDispatcher rd = req.getRequestDispatcher(path);
			rd.forward(req, resp);
		} else {
			File res = new File(path);
			if (res.exists()) {
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(res);
					byte[] rb = new byte[1024];
					while (fis.read(rb) > -1) {
						resp.getOutputStream().write(rb);
					}
					resp.getOutputStream().flush();
				} catch (Exception ex) {
					resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, path);
				} finally {
					if (fis != null) {
						fis.close();
					}
				}
			} else {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, path);
			}
		}

	}

	public static void setCacheControl(HttpServletResponse response) {
		// 180秒， 半小时
		// httpheader.cache.control.maxage
		// 统一在web容器上加，如nginx
		String cacheSecond = GlobalVar.getSystemProperty("httpheader.cache.control.maxage", "1800");
		response.setHeader("Cache-Control", "max-age=" + cacheSecond);
	}
}
