<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="cn.crtech.cooperop.bus.util.LocalThreadMap"%>
<%@page import="java.util.Map"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="cn.crtech.cooperop.application.action.AuthAction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
	/**
	 * 使用方法：
	 *    访问地址：	http://协同办公服务器:端口/[发布应用名称]/sso.jsp?uid=xxx&ts=xxx&vs=xxx&rd=xxx&其它参数
	 *    参数说明：	uid	用户在系统中的唯一ID,v_system_user中的id
	 *				ts	登录时的Unix时间戳，精确到秒级，如：2017-09-01 12:01:01.001 = 1504238461001
	 *				vs	登录校验串，值等于MD5(uid + 协同办公配置的连接密钥 + timestamp)
	 *				rd	所需跳转的页面地址，格式为：/w/产品/网页.html 如：/w/application/welcome.html
	 *				其它参数	传向目标网页的其他参数
	 */
	
	cn.crtech.cooperop.bus.session.Session s = cn.crtech.cooperop.bus.session.Session.getSession(request, response);
	LocalThreadMap.put("sessionId", s.getId());
	AuthAction action = new AuthAction();
	Map<String, Object> data = CommonFun.requestMap(request);
	Map<String, Object> res = action.sso(data);

	if (res.get("error") != null && (boolean) res.get("error")) {
		pageContext.setAttribute("emsg", res.get("emsg"));
	} else {
		data.remove("vs");
		data.remove("ts");
		data.remove("uid");
		String url = CommonFun.isNe(data.get("rd")) ? (String) res.get("redirect_url") : (String) data.get("rd");
		data.remove("rd");
		if (CommonFun.isNe(url)) {
			pageContext.setAttribute("emsg", "无效的跳转地址");
		} else {
			Iterator<String> keys = data.keySet().iterator();
			String params = "";
			while (keys.hasNext()) {
				String key = keys.next();
				if (params.length() > 0) {
					params += "&";
				}
				params += key + "=" + data.get(key);
			}
			url += "?" + params;
			response.sendRedirect(url);
			return;
		}
	}
%>
<s:page title="单点登录异常" disloggedin="true">
	<link type="text/css" rel="stylesheet" href="${request.contextPath }/theme/layout/css/error.css">
	<div class="error_box">
		<h1>
			::>_<::
		</h1>
		<h2>亲，对不起，单点登录协同办公系统失败！</h2>
		<p>原因：${emsg}。</p>
	</div>
</s:page>
