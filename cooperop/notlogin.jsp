<%@page import="cn.crtech.cooperop.bus.engine.window.HtmlWindow"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="cn.crtech.cooperop.bus.session.Session"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.io.File"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
	Session mySession = Session.getSession(request, response);
	String lastAccessPage = request.getParameter("lastAccessPage");
	if (CommonFun.isNe(lastAccessPage)) {
		lastAccessPage = (String) mySession.get("lastAccessPage");
	}
	String message = (String) mySession.get("message");
	if (CommonFun.isNe(message))
		mySession.put("message", "您尚未登录，请先登录！");
	String request_method = "";
	if ((!CommonFun.isNe(request.getContentType()) && request.getContentType().indexOf("application/x-www-form-urlencoded") > -1)
			|| "XMLHttpRequest".equals(request.getHeader("x-requested-with"))
			|| "ajax".equals(request.getParameter("request_method"))) {
		request_method = "ajax";
	}
	if (!"ajax".equals(request_method)) {
		String include_page = "login.jsp";
		/*if (!CommonFun.isNe(lastAccessPage)) {
			String res = lastAccessPage.substring(lastAccessPage.indexOf("/w/") + 3);
			String window = res.substring(res.lastIndexOf(".") + 1);
			String pageid = res.substring(0, res.lastIndexOf(".")).replace('/', '.');
			if (!CommonFun.isNe(pageid)) {
				String module = pageid.substring(0, pageid.indexOf("."));
				String view = HtmlWindow.getViewPath(module + ".login");
				if (!CommonFun.isNe(view)) {
					String server = request.getHeader("host");
					if (!CommonFun.isNe(request.getHeader("Scheme"))) {
						server = request.getHeader("Scheme") + "://" + server;
					} else {
						server = request.getScheme() + "://" + server;
					}
					include_page = server + request.getContextPath() + "/w/" + module + "/login.html"; //
					URL url = new URL(server + request.getContextPath() + lastAccessPage);
					String query = url.getQuery();
					include_page += "?lastAccessPage=" + URLEncoder.encode(lastAccessPage);
				}
			}
		}*/
		out.println("<script type='text/javascript'>top.location = '" + include_page + "';</script>");
		//response.sendRedirect(include_page);
		return;
	} else {
		out.print(mySession.get("message"));
		mySession.remove("message");
	}
%>
