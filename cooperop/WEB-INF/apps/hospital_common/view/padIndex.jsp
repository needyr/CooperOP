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
	href="${pageContext.request.contextPath}/theme/pad/font-awesome/css/font-awesome.min.css?ipl=Y">
<%-- <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/plugins/apicloud/aaui.css?ipl=Y"> --%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/pad/padindex.css?ipl=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/pad/bootstrap/bootstrap.css?ipl=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/pad/simple-line-icons/simple-line-icons.css?ipl=Y">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/plugins/iconfont/iconfont.css?ipl=Y">
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

<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js?ipl=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.json.min.js?ipl=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js?ipl=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js?ipl=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js?ipl=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/im/client.js?ipl=Y" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/theme/scripts/padindex.js?ipl=Y" type="text/javascript"></script>
<title>智能辅助决策支持平台</title>
<style type="text/css">
.head .t-icon>i{
	position: relative;
}
/* .head .t-icon i span{
    position: absolute;
    top: 0px;
    right: -5px;
    font-weight: 300;
    padding: 3px 6px;
    height: 1px;
    background-color: #F3565D;
    border-radius: 12px !important;
    text-shadow: none !important;
    text-align: center;
    vertical-align: middle;
    display: none;
    min-width: 1px;
    padding: 8px 8px;
    font-size: 12px;
    font-weight: 700;
    line-height: 1;
} */
.head .t-icon i span{
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

.menu_name{
	color: #ffffff;
    display: inline-table;
    height: 100%;
    line-height: 30px;
    margin-left: 10px;
    cursor: pointer;
}
    
.menu_name span{
	background: #bbbcbf;
    padding: 3px;
    border-radius: 3px;
    border-right: 5px solid #85b9c1;
    font-weight: 600;
    margin-left: 4px;
    box-shadow: 2px 2px 5px 1px #979bad;
}

.choose_menu{
	background: #555d7d !important;
} 
   
</style>
</head>
<body>
<div class="head">
	<div class="menu_name">
		
	</div>
    <div class="t-icon">
        <i class="icon-bubbles" id="header_im_bar"><span></span></i>
        <i class="icon-bell" id="header_notification_bar"><span></span></i>
        <i class="icon-volume-2" id="header_suggestions_bar"><span></span></i>
        <i class="icon-calendar" id="header_task_bar"><span></span></i>
        <i class="icon-envelope-open" id="header_inbox_bar"><span></span></i>
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
<div class="page-right-content">
	<iframe class="im_ index-iframe" id="indexIframe_im" style="width:100%;border:0px; display:none;" frameborder="0" src="/w/application/pad/welcome.html"></iframe>
	<iframe class="oth_ index-iframe" id="indexIframe_oth" style="width:100%;border:0px;" frameborder="0" src="/w/application/task/mine.html"></iframe>
</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	var default_url = '${request_page_url}';
	var default_url1 = '${request_pageurl}';
	if(default_url){
		if(default_url == 'application.pad.welcome'){
			//$("#indexIframe_im").show();
			//$("#indexIframe_oth").hide();
		}else{
			var u = default_url.replace(/\./g, "/");
			$("#indexIframe_oth").attr("src", cooperopcontextpath + "/w/" + u+".html");
			$("#indexIframe_im").hide();
			$("#indexIframe_oth").show();
		}
	}
	if(default_url1){
		default_url1 = decodeURIComponent(default_url1);
		$("#indexIframe_oth").attr("src", default_url1);
		$("#indexIframe_im").hide();
		$("#indexIframe_oth").show();
	}
	$cimc.init({
		userid: userinfo_id
	});
	/* if(typeof crtech != "undefined"){
		var interval= setInterval(function(){
			var _this;
			_this = crtech
			var url = "/w/hospital_common/user/link.json";
			$.ajax({
				type: "post",
				url: url,
				contentType: "application/x-www-form-urlencoded; charset-UTF-8",
				data: {},
				timeout: 5000,
				complete: function(XMLHttpRequest, status){
					if(status == 'timeout'){
						clearInterval(interval);
						try{clearInterval(error_interval);}catch(e){}
						crtech.closeModal();
						_this.logout();
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrow){
					if(XMLHttpRequest.status == 401 || XMLHttpRequest.status == 0){
						try{
							if(_this){
								clearInterval(interval);
								crtech.closeModal();
								var error_interval = setInterval(function(){
									_this.relogin();
								},3000)
							}
						}catch(r){}
					}
				}	
			})
		}, 3000);
	} */
	setTimeout(function (){
		$.initmenus();
	}, 1000);
	
	window.setInterval("reinitIframe()", 200);
	sendTips();
	var check_first = 0;
	$('#indexIframe_im').load(function(){
		if(check_first != 0){
			$chohoim.init();
		}else{
			check_first = 1;
		}
	})
});
function sendTips(){
	$.call("ipc.auditresult.notices",{uid: userinfo_id},function(rtn){
		if(typeof crtech != "undefined"){
			crtech.closeModal();
		}
		if(rtn.notices && rtn.notices.length > 0){
			if(typeof crtech != "undefined"){
				//alert('uid:' + userinfo_id + '250 85');
				crtech.modal("/w/ipc/auditresult/notice.html?uid="+userinfo_id, "250", "85");
			}
		}
	});
}
function reinitIframe(){
	try{
		var h = document.documentElement.scrollHeight || document.body.scrollHeight;
		$(".index-iframe").css("height",h-30+"px");
	}catch (ex){}
	}
function re(){
	location.reload();
}

</script>
</html>