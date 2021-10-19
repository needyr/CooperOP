<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page import="cn.crtech.cooperop.bus.session.Session"%>
<%@page import="cn.crtech.cooperop.bus.ws.server.Engine"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="cn.crtech.cooperop.application.bean.Account"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
//不能删，删了session就乱了
	Session s = Session.getSession(request, response);
	Account a = (Account)s.get("userinfo");
	pageContext.setAttribute("system_title", SystemConfig.getSystemConfigValue("global", "system_title"));
	pageContext.setAttribute("kehubh", SystemConfig.getSystemConfigValue("cooperop", "kehubh"));
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

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,initial-scale=1.0,width=device-width" />
<meta name="format-detection"
	content="telephone=no,email=no,date=no,address=no">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/font-awesome/css/font-awesome.min.css?iml=Y">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/api.css?iml=Y">
<%-- <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/plugins/apicloud/aaui.css?iml=Y"> --%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/common.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/mobileMain.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/bootstrap/bootstrap.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/simple-line-icons/simple-line-icons.css?iml=Y">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/plugins/iconfont/iconfont.css?iml=Y">
	
<script type="text/javascript">
	var cooperopcontextpath = "${pageContext.request.contextPath}";
	var ws_config = {
		http_url: "${http_url}",
		ws_url: "${ws_url}",
		token_key: "${ws_token_key}",
		app_key: "${ws_app_key}",
		app_id: "yaoxunkang"
	};
	var kehubh = '${kehubh}';
	var userinfo_id = '${userinfo_id}';
</script>

<script src="${pageContext.request.contextPath}/theme/plugins/apicloud/api.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.json.min.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/window.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js?iml=Y" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/themes/im/script/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/themes/im/script/md5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/themes/im/script/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/themes/im/script/websocket.js"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/mobileindex.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.sha1.js?iml=Y"></script>
<title>main</title>
<style type="text/css">
.img-circle{
    width: 2em;
    height: 2em;
    border-radius: 2em !important;
}
</style>
</head>

<body>
	<div id="wrap">
		<div id="header" class="header"  >
			<div class="header-div">
				<i class="icon-arrow-left back" onclick="backtomain();"></i>
				<img alt="" class="img-circle touxiang" src="${pageContext.request.contextPath}/theme/layout/img/avatar3_small.jpg" />
				<i class="iconfont icon-scanning" ></i>
				<i class="icon icon-bell header-main" id="header_notification_bar">
					<div class="header-span" style="display:none;">
						<span class="notif-span">0</span>
					</div>
				</i> <i class="icon-volume-2 header-main" id="header_suggestions_bar">
					<div class="header-span" style="display:none;">
						<span class="suggestions-span" >0</span>
					</div>
				</i>
				<h4 class="header-h3 aui-title">超然协同办公平台</h4>
			</div>
		</div>
		<div id="main">
			
		</div>
		<div id="footer" class="footerdiv">
			<div class="footer-main">
				<a class="footer-mainDiv" href="javascript:void(0);" id="footer_inbox_bar"> <i
					class="fa icon-envelope"> </i>
					<span class="footer-span" style="display:none;">0</span>
					<p class="footer-p">邮件</p>
				</a>
				<a class="footer-mainDiv" href="javascript:void(0);" id="footer_chat_bar"> <i
					class="fa icon-bubble"> </i>
					<span class="footer-span" style="display:none;">0</span>
					<p class="footer-p">聊天</p>
				</a>
				<a class="footer-mainDiv mainMenu" href="javascript:void(0);" id="footer_menu_bar"> <i
					class="fa fa-home"> </i>
					<p class="footer-p">首页</p>
				</a>
				<a class="footer-mainDiv" href="javascript:void(0);" id="footer_tasks_bar"> <i
					class="fa icon-calendar"> </i>
					<span class="footer-span" style="display:none;">0</span>
					<p class="footer-p">待办</p>
				</a>
				<a class="footer-mainDiv" href="javascript:void(0);" id="footer_profile_bar"> <i
					class="fa icon-user"> </i>
					<p class="footer-p">工作</p>
				</a>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var exitflag = false;
var isdefault = true;
var nofooter = false;
var avatar = '${avatar}';
onready = function(params) {
	if(avatar) {
		$(".img-circle").attr("src", cooperopcontextpath+ "/rm/s/application/" + avatar + "S");
	}
	$main.init({
		header: $("#header"),
		footer: $("#footer"),
		main: $("#main"),
		defaultFrame: {page : 'application.mobile.welcome',title: '超然协同办公平台',footer: footer},
		onexit: function() {
			if (!exitflag) {
				exitflag = true;
				$.tips("", "再次返回退出系统", function() {
					exitflag = false;
				});
				return false;
			} else {
				return exitflag;
			}
		},
		setTitle: function(title, isdefault, nofooter) {
			//$("#header").find(".aui-title").text(title);
			$("#header").find(".aui-title").text(title);
			if (isdefault) {
				$("#header").find(".back").hide();
				$("#header").show();
				$("#header").find(".touxiang").show();
				$(".icon-scanning").show();
				$(".footerdiv").show();
			} else {
				$("#header").find(".touxiang").hide();
				$("#header").find(".back").show();
				$(".icon-scanning").hide();
				if(title){
					$("#header").show();
				}else{
					$("#header").hide();
				}
				if(nofooter){
					$(".footerdiv").hide();
				}else{
					$(".footerdiv").show();
				}
			}
			
			var header = $api.byId('header');
			//适配iOS7+，Android4.4+状态栏沉浸式效果，详见config文档statusBarAppearance字段
			if (api.statusBarAppearance)
				$api.fixStatusBar(header);
			var headerH = $api.offset(header).h;
			//动态计算header的高度，因iOS7+和Android4.4+上支持沉浸式效果，
			//因此header的实际高度可能为css样式中声明的44px加上设备状态栏高度
			//其中，IOS状态栏
			//高度为20px，Android为25px
			$("#main").css("margin-top", headerH + "px");
		}
	});
}
function backtomain(){
	$.close();
}
function re(){
	location.reload();
}
</script>
</html>