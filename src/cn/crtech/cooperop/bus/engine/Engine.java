package cn.crtech.cooperop.bus.engine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.ast.MessageSend;

import cn.crtech.cooperop.application.authenticate.Authenticate;
import cn.crtech.cooperop.application.bean.Account;
import cn.crtech.cooperop.application.service.MessageTemplateService;
import cn.crtech.cooperop.bus.cache.MemoryCache;
import cn.crtech.cooperop.bus.cache.SystemMessageTemplate;
import cn.crtech.cooperop.bus.engine.window.Window;
import cn.crtech.cooperop.bus.engine.window.XlsxWindow;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.message.MessageSender;
import cn.crtech.cooperop.bus.rdms.Record;
import cn.crtech.cooperop.bus.session.Session;
import cn.crtech.cooperop.bus.util.CommonFun;
import cn.crtech.cooperop.bus.util.CooperopException;
import cn.crtech.cooperop.bus.util.GlobalVar;
import cn.crtech.cooperop.bus.util.LocalThreadMap;

public class Engine extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 3930208932859914393L;

	private static String contextPath;
	private static String servletPath;

	public static String getContextPath() {
		return contextPath;
	}

	public static String getServletPath() {
		return servletPath;
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		contextPath = req.getContextPath() == null ? "" : req.getContextPath();
		servletPath = req.getServletPath();
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		String s = req.getRequestURI().substring((contextPath + servletPath).length() + 1);
		if ("excelprogress".equals(s)) {
			resp.getWriter().write(CommonFun.object2Json(XlsxWindow.getProgress(req)));
			resp.getWriter().flush();
			return;
		}
		if (!req.getRequestURI().endsWith(".html")
				&& !req.getRequestURI().endsWith(".xlsx")
				&& !req.getRequestURI().endsWith(".pdf")
				&& !req.getRequestURI().endsWith(".json")
				&& !req.getRequestURI().endsWith(".jsonp")
				&& !req.getRequestURI().endsWith(".doc")) {
			log.debug("=============>" + req.getRequestURI());
			return;
		}
		//允许跨域
		resp.setHeader("Access-Control-Allow-Origin", "*");

		String res = req.getRequestURI().substring((contextPath + servletPath).length() + 1);
		String window = res.substring(res.lastIndexOf(".") + 1);
		String pageid = res.substring(0, res.lastIndexOf(".")).replace('/', '.');
		Account user = null;
		try {
			if (pageid == null) {
				sendError(req, resp, HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			Session session = Session.getSession(req, resp);
			user = (Account) session.get("userinfo");

			int right = Authenticate.checkRight(pageid, user);

			String query = req.getQueryString();
			String url = req.getRequestURI();
			String lastAccessPage = url + "?" + query;
			if (query == null) {
				lastAccessPage = url;
			}
			if (!"base.message.statecount".equals(pageid) && !"base.task.taskCount".equals(pageid)) {
				session.put("lastAccessPage", lastAccessPage);
			}
			if (right == -1) {
				sendError(req, resp, HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
			if (right == 0) {
				sendError(req, resp, HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			if (right == -2) {
				sendError(req, resp, "无效产品，请联系管理员购买或续约！");
				return;
			}

			req.getSession().setAttribute("CKFinder_UserRole", "user");

			log.debug("Thread[" + Thread.currentThread().getId() + "]start>>sessionId: "
					+ session.getId() + " -->[T]" + LocalThreadMap.get("sessionId") + "，pageid: " + pageid
					+ " -->[T]" + LocalThreadMap.get("pageid"));

			Window win;
			try {
				win = getWindow(window);
				long threadid = Thread.currentThread().getId();
				log.debug("mapping window[" + window + "][Thread-" + threadid + "]: " + pageid);
				win.excute(req, resp);
			} catch (Exception e) {
				log.error("frame engine excute window error.", e);
				sendError(req, resp, e.getMessage());
				return;
			}
		} catch (Exception ex) {
			log.error(ex);
			sendError(req, resp, ex.getMessage());
			return;
		} finally {
			log.debug("Thread[" + Thread.currentThread().getId() + "]end>>sessionId: "
					+ Session.getSession(req, resp).getId() + " -->[T]" + LocalThreadMap.get("sessionId") + "，pageid: " + pageid
					+ " -->[T]" + LocalThreadMap.get("pageid"));

			if ("true".equalsIgnoreCase(GlobalVar.getSystemProperty("system.gc"))) {
				System.gc();
			}
		}
	}

	private void sendError(HttpServletRequest req, HttpServletResponse resp,
			Object scBadRequest) throws IOException {
		String request_method = null;
		if ((!CommonFun.isNe(req.getContentType()) && req.getContentType().indexOf("application/x-www-form-urlencoded") > -1)
				|| "XMLHttpRequest".equals(req.getHeader("x-requested-with"))
				|| "ajax".equals(req.getParameter("request_method"))) {
			request_method = "ajax";
		}

		boolean app_flag = !CommonFun.isNe(req.getParameter(GlobalVar.getSystemProperty("app.flag", "_IA_")));
		if (app_flag) {
			String message = "";
			if (scBadRequest instanceof Integer) {
				int sc = (Integer)scBadRequest;
				switch (sc) {
				case HttpServletResponse.SC_BAD_REQUEST : {
					message = "错误的请求。";
				}
				case HttpServletResponse.SC_UNAUTHORIZED : {
					message = "未登录。";
				}
				case HttpServletResponse.SC_FORBIDDEN : {
					message = "无权访问。";
				}
				case HttpServletResponse.SC_NOT_FOUND : {
					message = "资源未找到 。";
				}
				default: {
					message = scBadRequest.toString();
				}
				}
			} else {
				message = (String)scBadRequest;
			}
			Record rs = new Record();
			rs.put("rs", message);
			resp.setContentType("text/html; charset=UTF-8");
			resp.getWriter().write(CommonFun.object2Json(rs));
			resp.getWriter().flush();
		} else {
			if (scBadRequest instanceof Integer) {
				resp.sendError((Integer)scBadRequest);
			} else {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, URLEncoder.encode((String)scBadRequest, "UTF-8"));
			}
		}
	}

	public static void executeCallAction(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String cp = req.getContextPath() == null ? "" : req.getContextPath();
		String sp = req.getServletPath();
		String res = req.getRequestURI().substring((cp + sp).length() + 1);
		String window = res.substring(res.lastIndexOf(".") + 1);
		String pageid = res.substring(0, res.lastIndexOf(".")).replace('/', '.');
		boolean removeflag = false;

		Session session = Session.getSession(req, resp);

		Map<String, Object> action = Authenticate.getAction(pageid);


		if (action == null && !window.equals(GlobalVar.getSystemProperty("window.default"))) {
			throw new Exception("frame engine call action error.", new NoSuchMethodException(pageid));
		}

		HashMap<String, Object> map = CommonFun.requestMap(req);
		map.remove(GlobalVar.getSystemProperty("app.flag", "_IA_"));

		if (LocalThreadMap.get("pageid") == null) {
			removeflag = true;
			LocalThreadMap.put("pageid", pageid);
			LocalThreadMap.put("sessionId", session.getId());
			setLocalThreadMapParam(req, map);
			setCookieToLocalThread(req);
		}
		String error = null;
		try {
			long s = System.currentTimeMillis();
			Class<?> c = (Class<?>) action.get("class");
			Method m = (Method) action.get("method");
			Object t = callAction(c, m, map);
			req.setAttribute("$actioncost", System.currentTimeMillis() - s);
			req.setAttribute("$return", t);
			//发送mq消息
			/*if(MemoryCache.containsKey("mq_"+pageid)){
				Record r = (Record)MemoryCache.get("mq_"+pageid);
			}*/

			//发送消息, 具体是否发送在方法中有判断
			if(SystemMessageTemplate.needSendM(pageid.substring(0, pageid.indexOf("."))+pageid)){
				MessageSender.sendActionMessage(pageid.substring(0, pageid.indexOf(".")), pageid, map);
			}
		} catch (Exception e) {
			log.error("frame engine call action error.", e);
			error = e.getMessage() == null ? "null" : e.getMessage();
			if (e.getCause() != null) {
				error = e.getCause().getMessage() == null ? "null" : e.getCause().getMessage();
			}
		}

		if (error != null) {
			if (removeflag) {
				LocalThreadMap.clear();
			}
			throw new Exception(error);
		}

		if (LocalThreadMap.get("$cookies") != null) {
			Record cookies = (Record) LocalThreadMap.get("$cookies");
			for (Object c : cookies.values()) {
				Record rc = (Record) c;
				if (rc.getFlag() != Record.NORMAL) {
					Cookie cookie = new Cookie(rc.getString("name"), URLEncoder.encode(rc.getString("value"),
							"UTF-8"));
					if (rc.containsKey("maxage")) {
						cookie.setMaxAge(rc.getInt("maxage"));
					}
					if (rc.containsKey("domain")) {
						cookie.setPath(rc.getString("domain"));
					}
					if (rc.containsKey("path")) {
						cookie.setPath(rc.getString("path"));
					}
					resp.addCookie(cookie);
				}
			}
		}

		if (removeflag) {
			LocalThreadMap.clear();
		}

		if ("true".equalsIgnoreCase(GlobalVar.getSystemProperty("debug"))) {
			long action_waring = Long.parseLong(GlobalVar.getSystemProperty("debug.action.waring"));
			long action_alarm = Long.parseLong(GlobalVar.getSystemProperty("debug.action.alarm"));
			long actioncost = req.getAttribute("$actioncost") == null ? -1 : Long.parseLong(req.getAttribute(
					"$actioncost").toString());
			if (actioncost >= action_alarm) {
				log.error("action[" + pageid + "] cost: " + actioncost + "ms");
			} else if (actioncost >= action_waring) {
				log.warning("action[" + pageid + "] cost: " + actioncost + "ms");
			}
		}
	}

	public static void setLocalThreadMapParam(HttpServletRequest req, Map<String, Object> map)
			throws UnsupportedEncodingException {
		String cp = req.getContextPath() == null ? "" : req.getContextPath();
		String sp = req.getServletPath();
		String res = req.getRequestURI().substring((cp + sp).length() + 1);
		Enumeration<String> headernames = req.getHeaderNames();
		Map<String, String> header = new HashMap<String, String>();
		while (headernames.hasMoreElements()) {
			String name = headernames.nextElement();
			header.put(name, URLDecoder.decode(req.getHeader(name), "UTF-8"));
		}
		LocalThreadMap.put("httpHeader", header);
		LocalThreadMap.put("start", map.get("start"));
		LocalThreadMap.put("limit", map.get("limit"));
		LocalThreadMap.put("sort", map.get("sort"));
		LocalThreadMap.put("filter", map.get("filter"));
		LocalThreadMap.put("ip", CommonFun.getIp(req));
	}

	private static void setCookieToLocalThread(HttpServletRequest req) throws UnsupportedEncodingException {
		Record cookies = new Record();
		// cookie
		if (req.getCookies() != null) {
			for (Cookie cookie : req.getCookies()) {
				try {
					Record c = new Record();
					c.put("name", cookie.getName());
					c.put("comment", cookie.getComment());
					c.put("domain", cookie.getDomain());
					c.put("maxage", cookie.getMaxAge());
					c.put("path", cookie.getPath());
					c.put("secure", cookie.getSecure());
					c.put("value", replacer(cookie.getValue()));
					c.put("version", cookie.getVersion());
					cookies.put(cookie.getName(), c);
				} catch (Exception e) {
					e.printStackTrace();
					log.warning("cookie set error " + e.getMessage());
				}
			}
		}
		LocalThreadMap.put("$cookies", cookies);
	}

	public static String replacer(String data) {
		try {
			StringBuffer tempBuffer = new StringBuffer();
			int incrementor = 0;
			int dataLength = data.length();
			while (incrementor < dataLength) {
				char charecterAt = data.charAt(incrementor);
				if (charecterAt == '%') {
					tempBuffer.append("<percentage>");
				} else if (charecterAt == '+') {
					tempBuffer.append("<plus>");
				} else {
					tempBuffer.append(charecterAt);
				}
				incrementor++;
			}
			data = tempBuffer.toString();
			data = URLDecoder.decode(data, "utf-8");
			data = data.replaceAll("<percentage>", "%");
			data = data.replaceAll("<plus>", "+");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public Window getWindow(String windowName) throws Exception {
		String pkg = Window.class.getPackage().getName();
		String clazz = pkg + "." + CommonFun.capitalize(windowName) + "Window";
		Window window;
		try {
			window = (Window) (Class.forName(clazz).newInstance());
		} catch (Exception e) {
			throw new Exception("window implements not found.", e);
		}
		return window;
	}

	public static Object callAction(Class<?> clazz, Method method, Map<String, Object> map) throws Exception {
		if (map == null) {
			map = new HashMap<String, Object>();
		}

		map.remove(Session.SIDNAME);
		map.remove("browser");
		map.remove("browser.version");

		map.remove("request_method");
		map.remove("t");
		map.remove("window");
		map.remove("page");

		long threadid = Thread.currentThread().getId();
		String mapStr = map.toString();
		if (mapStr.length() > 200) {
			mapStr = mapStr.substring(0, 200) + "...";
		}
		log.debug("call action[Thread-" + threadid + "][start]: " + clazz.getName() + "." + method.getName()
				+ "(" + mapStr + ")");

		Object tmp = null;
		tmp = method.invoke(clazz.newInstance(), map);

		log.debug("call action[Thread-" + threadid + "][end]: " + clazz.getName() + "." + method.getName() + "("
				+ mapStr + ")");
		return tmp;
	}
}
