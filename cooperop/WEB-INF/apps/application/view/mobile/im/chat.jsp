<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page import="cn.crtech.cooperop.bus.session.Session"%>
<%@page import="cn.crtech.cooperop.bus.ws.server.Engine"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="cn.crtech.cooperop.application.bean.Account"%>
<html>
<head>
<%
//不能删，删了session就乱了
	Session s = Session.getSession(request, response);
	Account a = (Account)s.get("userinfo");
	pageContext.setAttribute("system_title", SystemConfig.getSystemConfigValue("global", "system_title"));
	pageContext.setAttribute("avatar", a.getAvatar());
	
	String server = request.getHeader("Host");
	if (!CommonFun.isNe(request.getHeader("Scheme"))) {
		server = request.getHeader("Scheme") + "://" + server;
	} else {
		server = request.getScheme() + "://" + server;
	}
	String http_url, ws_url;
	http_url = server + request.getContextPath();
	if (server.indexOf("https://") == 0) {
		ws_url = server.replaceAll("https://", "wss://");
	} else {
		ws_url = server.replaceAll("http://", "ws://");
	}
	ws_url = ws_url + request.getContextPath() + "/ws";
	pageContext.setAttribute("userinfo_id", a.getId());
	pageContext.setAttribute("http_url", http_url);
	pageContext.setAttribute("ws_url", ws_url);
	pageContext.setAttribute("ws_token_key", Engine.TOKEN_KEY);
	pageContext.setAttribute("ws_app_key", Engine.APP_KEY);
	
	pageContext.setAttribute("session_id_", request.getParameter("id"));
	pageContext.setAttribute("session_type_", request.getParameter("type"));
	pageContext.setAttribute("session_avatar_", request.getParameter("avatar"));
%>
<title>即时消息</title>
<meta charset="UTF-8">
  <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,initial-scale=1.0,width=device-width"/>
  <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/font-awesome/css/font-awesome.min.css?iml=Y">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/api.css?iml=Y">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/common.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/chatText.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/bootstrap/bootstrap.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/simple-line-icons/simple-line-icons.css?iml=Y">
	
	<script type="text/javascript">
	var cooperopcontextpath = "${pageContext.request.contextPath}";
	var ws_config = {
		http_url: "${http_url}",
		ws_url: "${ws_url}",
		token_key: "${ws_token_key}",
		app_key: "${ws_app_key}",
		app_id: "yaoxunkang"
	};
	var session_ = {};
	session_.type = "${session_type_}";
	session_.id = "${session_id_}";
	session_.avatar = "${session_avatar_}";
	var userinfo_id = '${userinfo_id}';
	var userinfo_avatar = '${avatar}';
</script>
<script src="${pageContext.request.contextPath}/theme/plugins/apicloud/api.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/window.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js?iml=Y" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/themes/im/script/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/themes/im/script/md5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/themes/im/script/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/themes/im/script/websocket.js"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/chattext.js?iml=Y" type="text/javascript"></script>
</head>
<body>
<div id="wrap">
		<div id="main">
		</div>
		<div id="footer">
			<!-- <input type="text"> -->
			<div contenteditable="true" style="-webkit-user-select:text;max-height:90px;" class="choho-im-text-content"></div>
			<!--  <button onclick="location.reload();">刷新</button> -->
			<button class="send-btn">发送</button> 
			<div class="footer-iconDiv">
				<ul>
					<li><i class="icon-picture"><input type="file" accept="image/*"></input></i></li>
					<!-- <li><i class="icon-microphone"><input type="file"></input></i></li>
					<li><i class="icon-camera"></i></li>
					<li><i class="icon-pointer"><input type="file"></input></i></li>
					<li><i class="fa fa-video-camera"></i></li>
					<li><i class="icon-plus"></i></li> -->
				</ul>
			</div>
		</div>
	</div>
<script type="text/javascript">
	function re(){
		location.reload();
	}
	function backtomain(){
		$.close();
	}
</script>
</body>	
</html>