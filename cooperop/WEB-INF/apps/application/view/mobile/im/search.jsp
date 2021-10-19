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
	pageContext.setAttribute("avatar", s.get("avatar"));
	
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
	href="${pageContext.request.contextPath}/theme/mobile/search.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/bootstrap/bootstrap.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/simple-line-icons/simple-line-icons.css?iml=Y">
	
<script src="${pageContext.request.contextPath}/theme/plugins/apicloud/api.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/window.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js?iml=Y" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/themes/im/script/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/themes/im/script/md5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/themes/im/script/common.js"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/search.js?iml=Y" type="text/javascript"></script>
<script type="text/javascript">
	var cooperopcontextpath = "${pageContext.request.contextPath}";
	var ws_config = {
		http_url: "${http_url}",
		ws_url: "${ws_url}",
		token_key: "${ws_token_key}",
		app_key: "${ws_app_key}",
		app_id: "yaoxunkang"
	};
	var userinfo_id = '${userinfo_id}';
</script>
</head>
<body>
<div id="wrap">
		<div id="header">
			<div class="header-div">
				<a href="javascript: backtomain();" class="tiaoZhuan"><i class="icon-arrow-left"></i></a>
			</div>
		  <div class="choho-im-search">
	        <input type="text" class="main-ipt choho-im-search-input" placeholder="搜索联系人/部门/群组">
	      </div>
		</div>
   <div id="main">
		<div class="main-tree">
		  	<p class="choho-im-search-result-title">联系人 </p>
	        <div class="daohang U">
	        </div>
	        
		     <p class="choho-im-search-result-title">部门</p>
	         <div class="daohang D">
	        </div>
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