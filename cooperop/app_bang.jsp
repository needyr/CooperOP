<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@page import="java.util.Date"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
	String welcome_page = GlobalVar.getSystemProperty("mobileLogin.jsp");
	pageContext.setAttribute("system_title", SystemConfig.getSystemConfigValue("global", "system_title"));
	pageContext.setAttribute("copyright", SystemConfig.getSystemConfigValue("global", "copyright"));
	pageContext.setAttribute("now", new Date());
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,initial-scale=1.0,width=device-width" />
<meta name="format-detection"
	content="telephone=no,email=no,date=no,address=no">
<title>${system_title }</title>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/plugins/font-awesome/css/font-awesome.min.css?iml=Y">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/api.css?iml=Y">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/common.css?iml=Y">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/mobileLogin.css?iml=Y">
	
<script type="text/javascript">
	var cooperopcontextpath = "${pageContext.request.contextPath}";
</script>

<script src="${pageContext.request.contextPath}/theme/plugins/apicloud/api.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/bootstrap/js/bootstrap.min.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.blockui.min.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/uniform/jquery.uniform.min.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.cokie.min.js?iml=Y"
	type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/plugins/jquery-validation/js/jquery.validate.min.js?iml=Y"></script>
<!-- END PAGE LEVEL PLUGINS -->
<script src="${pageContext.request.contextPath}/theme/scripts/window.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js?iml=Y" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/theme/scripts/controls/controls.js?iml=Y"
	type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/theme/scripts/controls/input/imagevalidcode.js?iml=Y"
	type="text/javascript"></script>
</head>
<body>
	<div id="wrap">
		<div class="header">
			<img src="${pageContext.request.contextPath}/theme/img/logo.png?iml=Y" alt="">
		</div>
		<form id="main">
			<input name="userid" required="required" type="text" class="main-ipt" placeholder="账号/手机号" readonly="readonly">
			<input name="deviceId" required="required" type="text" class="main-ipt" placeholder="账号/手机号" readonly="readonly">
			<!-- <input name="password" required="required" type="password" class="main-ipt" placeholder="密码"> -->
			<a href="javascript: re();"><p class="main-p">一个app只能绑定一个账号，绑定后该app只能登陆此账号，同时app可以授权pc端的登陆</p></a><%--  <a href="javascript: submitMe();"
				class="main-btn"><span>登录</span></a> --%>
			<%-- <button type="button" class="main-btn" onclick="bang();"><span>绑定</span></button> --%>
		</form>
		<div id="footer">
			<p class="footer-p"><fmt:formatDate value="${now}" pattern="yyyy"/> &copy; ${copyright }</p>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	var deviceId;
	apiready = function() {
		var u = store.getJson("user");
		if(u){
			$("#main").find('input[name="userid"]').val(u.userno);
			$("#main").find('input[name="deviceId"]').val(deviceId);
		}
		api.parseTapmode();
		deviceId = api.deviceId;
	};
	jQuery(document).ready(
			function() {
				$.call("application.auth.isneedvalid", {}, function(rtn) {
					if (rtn) {
						$(".form-validcode").show();
						$(".form-validcode").find("[name='validcode']").attr("required", "required");
					}
				});
				
			});
	
	var submitMe = function() {
		var da = $("#main").getData();
		da.ism = "Y";
		da.deviceId = deviceId;
		$.call("application.auth.appbang", da, function(rtn) {
			if (rtn.emsg) {
				$.error(rtn.emsg, function(){
					$("input[name='password']").val("");
					$("body").focus();
					//$("input[name='password']").focus();
				});
				if (rtn.isneedvalid) {
					$(".form-validcode").show();
					$(".form-validcode").find("[name='validcode']").attr("required", "required");
					$(".form-validcode").find("img[cooperoptype='imagevalidcode_img']").click();
				} else {
					$(".form-validcode").hide();
					$(".form-validcode").find("[name='validcode']").removeAttr("required");
				}
			} else {
				store.set('user',da);
				api.openWin({name: "main",
					url: cooperopcontextpath + "/" + rtn.redirect_url,
					params: {_CRSID: $.cookie("_CRSID")},
					bounces: false,
					reload : true});
			}
		}, function(ems) {
			$.error(ems);
		});
	}	
	function re(){
		location.reload();
	}
</script>
