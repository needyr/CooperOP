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
<%@page import="cn.crtech.cooperop.application.action.AuthAction"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
//不能删，删了session就乱了
	Session s = Session.getSession(request, response);
	Account a = (Account)s.get("userinfo");
	pageContext.setAttribute("system_title", SystemConfig.getSystemConfigValue("global", "system_title"));
	pageContext.setAttribute("kehubh", SystemConfig.getSystemConfigValue("cooperop", "kehubh"));
	pageContext.setAttribute("avatar", a.getAvatar());
	pageContext.setAttribute("user_name", a.getName());
	
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
	pageContext.setAttribute("request_page_url", request.getParameter("page"));
	pageContext.setAttribute("request_pageurl", request.getParameter("pageurl"));
%>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,initial-scale=1.0,width=device-width" />
<meta name="format-detection"
	content="telephone=no,email=no,date=no,address=no">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/pad/font-awesome/css/font-awesome.min.css">
<%-- <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/plugins/apicloud/aaui.css"> --%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/pad/padindex.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/pad/bootstrap/bootstrap.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/pad/simple-line-icons/simple-line-icons.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/plugins/iconfont/iconfont.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/alifont/iconfont.css">
<script type="text/javascript">
	var cooperopcontextpath = "${pageContext.request.contextPath}";
	var ws_config = {
		http_url: "${http_url}",
		ws_url: "${ws_url}",
		token_key: "${ws_token_key}",
		app_key: "${ws_app_key}",
		app_id: "new_im"
	};
	var kehubh = '${kehubh}';
	var userinfo_id = '${userinfo_id}';
</script>

<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.json.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/im/client.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/theme/scripts/padindex.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/controls/layout/tabwindow.js" type="text/javascript"></script>
<title>智能辅助决策支持平台</title>
<style type="text/css">

 .t-icon{
    position: absolute;
 	right: 0;
 	top : 0;
}

.t-icon>i{
    color: #888888;
    font-size: 16px;
    margin-right: 15px;
    line-height: 30px;
    position: relative;
}
 .t-icon>i:hover {
    color: #039eb5;
    cursor: pointer;
    
}
.t-icon i span{
    position: absolute;
    top: -6px;
    right: -11px;
    /* font-weight: 300; */
    padding: 3px 6px;
    height: 1px;
    background-color: #F3565D;
    border-radius: 12px !important;
    text-shadow: none !important;
    text-align: center;
    vertical-align: middle;
    display: none;
    /* min-width: 1px; */
    padding: 7px 4px;
    font-size: 2px;
    /* font-weight: 700; */
    line-height: 0;
    color: #fff;
}
</style>
</head>
<body>
<%-- <div class="head">
    <div class="t-icon">
        <i class="icon-bubbles" id="header_im_bar"><span></span></i>
        <i class="icon-bell" id="header_notification_bar"><span></span></i>
        <i class="icon-volume-2" id="header_suggestions_bar"><span></span></i>
        <i class="icon-calendar" id="header_task_bar"><span></span></i>
        <i class="icon-envelope-open" id="header_inbox_bar"><span></span></i>
    </div>
</div> --%>
<div class="topbar-global">
	<div class="page-tabs">
		<div class="content-tabs" id="itempath">
			<ul>
				<li wid="home-page">
					<a class="tab-items" href="javascript:void(0)"><span><i class="cicon icon-home"></i></span></a>
				</li>
				<%-- <li>
					<a class="tab-items active" href="javascript:void(0)"><span>在线报税申请</span></a>
					<a class="set ding" href="javascript:void(0)"><i class="cicon icon-ding"></i></a>
					<a class="set release" href="javascript:void(0)"><i class="cicon icon-imize-exit"></i></a>
				</li>
				<li>
					<a class="tab-items" href="javascript:void(0)"><span>人员设置</span></a>
					<a class="set ding" href="javascript:void(0)"><i class="cicon icon-ding"></i></a>
					<a class="set release" href="javascript:void(0)"><i class="cicon icon-imize-exit"></i></a>
				</li>
				<li>
					<a class="tab-items" href="javascript:void(0)"><span>系统药品目录</span></a>
					<a class="set ding active" href="javascript:void(0)"><i class="cicon icon-ding2"></i></a>
					<a class="set release" href="javascript:void(0)"><i class="cicon icon-imize-exit"></i></a>
				</li>
				<li>
					<a class="tab-items" href="javascript:void(0)"><span>静配系统</span></a>
					<a class="set ding" href="javascript:void(0)"><i class="cicon icon-ding"></i></a>
					<a class="set release" href="javascript:void(0)"><i class="cicon icon-imize-exit"></i></a>
				</li>
				<li>
					<a class="tab-items" href="javascript:void(0)"><span>医保控费</span></a>
					<a class="set ding" href="javascript:void(0)"><i class="cicon icon-ding"></i></a>
					<a class="set release" href="javascript:void(0)"><i class="cicon icon-imize-exit"></i></a>
				</li> --%>
			</ul>
		</div>
		<div class="tools-box">
			<a href="javascript:void(0)" class="left-tab" title="页签左移"><i class="cicon icon-retreat"></i></a>
			<a href="javascript:void(0)" class="right-tab" title="页签左移"><i class="cicon icon-advance"></i></a>
			<a href="javascript:void(0)" class="refresh-tab" title="刷新当前页签"><i class="cicon icon-refresh"></i></a>
		</div>
		<div class="tools-box place-right">
			<a href="javascript:void(0)" title="关闭全部页签" class="close-all"><i class="cicon icon-close-all"></i></a>
		</div>
	</div>
	<div class="t-icon">
        <i class="cicon icon-msg" id="header_im_bar" title="消息"><span></span></i>
        <i class="cicon icon-notice" id="header_suggestions_bar" title="通知"><span></span></i>
        <i class="cicon icon-calendar" id="header_task_bar" title="待办"><span></span></i>
    </div>
</div>
<div class="menus-div">
	<div class="left-menu">
	    <div class="point">
	       <img alt="" src="${pageContext.request.contextPath}/theme/img/logo-2.png" class="">
	    </div>
	    <div class="person">
	        <img class="img-circle menu-lia" data-level="0" data-id="user0" data-name="${user_name }"
	         data-child="3" src="${pageContext.request.contextPath}/theme/img/avatar3_small.jpg" alt="">
	        <%-- <p>${user_name}</p> --%>
	    </div>
	</div>
</div>
<div class="page-right-content zzc">
</div>
<div id="page-content">
	<div class="page-right-content page-tab active" wid="home-page">
		<iframe class="im_ index-iframe" id="indexIframe_im" style="width:100%;border:0px;" frameborder="0" src="/w/application/pad/welcome.html"></iframe>
	</div>
</div>
</body>
<script type="text/javascript">
//var msg_num = 0;
$(document).ready(function(){
	var default_url = '${request_page_url}';
	var default_name = '${request_page_name}';
	if(default_url){
		$("#header_task_bar").click();
	}else{
		$("#page-content .page-tab[wid='home-page']").click();
	}
	
	setTimeout(function (){
		$cimc.init({
			userid: userinfo_id ,
			_CRSID: '${_CRSID}'
		});
		
		setTimeout(function (){
			$.initmenus();
		}, 1000);
		var check_first = 0;
		window.setInterval("reinitIframe()", 200);
		$.tabwindowinit();
	}, 2000);
	/* if(typeof crtech != "undefined"){
		sendTips();
		window.setInterval("sendTips()", 10000);
	} */
});
function sendTips(){
	$.ajax({
		"async": true,
		"dataType" : "json",
		"type" : "POST",
		"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
		"cache" : false,
		"url" : "/w/hospital_common/system/msgalert/queryMsgCount.json",
		"data" : {uid: userinfo_id},
		"success" : function(rtn) {
			if(rtn.num && rtn.num >= 0 && msg_num != rtn.num){
				if(typeof crtech != "undefined"){
					crtech.closeModal();
				}
				if(typeof crtech != "undefined"){
					crtech.modal("/w/hospital_common/system/msgalert/index.html?uid="+userinfo_id+"&_CRSID="+'${_CRSID}', "250", "85");
				}
				msg_num = rtn.num;
			}
		},
		"error" : function(XMLHttpRequest, textStatus, errorThrown) {
			$.message('接收取消息失败！');
		}
	});
}
function reinitIframe(){
	try{
		var h = document.documentElement.scrollHeight || document.body.scrollHeight;
		$("#page-content > .page-tab iframe").css("height", h-36+"px");
	}catch (ex){}
	}

</script>
</html>