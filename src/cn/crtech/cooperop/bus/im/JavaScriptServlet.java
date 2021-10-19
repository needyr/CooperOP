package cn.crtech.cooperop.bus.im;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.crtech.cooperop.bus.im.resource.ResourceManager;
import cn.crtech.cooperop.bus.im.transfer.Engine;
import cn.crtech.cooperop.bus.im.ws.OfflineChecker;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.GlobalVar;

public class JavaScriptServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8297638507078060193L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			Engine.init(config.getInitParameter("appid"), config.getInitParameter("appkey"),
					config.getInitParameter("busiclass"));
			OfflineChecker.init();
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!Engine.isReady()) {
			resp.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "js sdk is not ready.");
			return;
		}
		String cp = req.getContextPath() == null ? "" : req.getContextPath();
		String sp = req.getServletPath();
		String cmd = req.getRequestURI().substring((cp + sp).length());
		if (cmd != null && cmd.startsWith("/")) {
			cmd = cmd.substring(1);
		}
		if (CommonFun.isNe(cmd)) {
			resp.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
		}
		if ("login".equals(cmd)) {
			try {
				String token = Engine.generateToken(req, resp);
				resp.setCharacterEncoding("UTF-8");
				resp.setContentType("text/html; charset=UTF-8");
				resp.getWriter().print(token);
				resp.getWriter().flush();
			} catch (Exception ex) {
				log.error(ex);
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			}
		} else if (cmd.startsWith("rm/")) {
			String fileid = cmd.substring(3);
			try {
				ResourceManager.showResource(fileid, false, req, resp);
			} catch (Exception ex) {
				log.error("file not found. id = " + fileid, ex);
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, ex.getMessage());
			}
		}
	}

	@Override
	public void destroy() {
		Engine.destroy();
		super.destroy();
	}
	public static void setCacheControl(HttpServletResponse response){
//		180秒， 半小时
//		httpheader.cache.control.maxage
//		统一在web容器上加，如nginx
		String cacheSecond = GlobalVar.getSystemProperty("httpheader.cache.control.maxage", "1800");
		response.setHeader("Cache-Control", "max-age="+cacheSecond);
	}
}
