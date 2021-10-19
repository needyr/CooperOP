<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="cn.crtech.cooperop.bus.session.Session"%>
<%
	pageContext.setAttribute("wx_appid", CommonFun.decryptString(SystemConfig.getSystemConfigValue("cooperop", "wx_appid")));
	pageContext.setAttribute("state", Session.getSession(request, response).getId());
	pageContext.setAttribute("now", new Date());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<title>${system_title }</title>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="${pageContext.request.contextPath}/theme/plugins/google-fonts/opensans.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/theme/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/theme/plugins/simple-line-icons/simple-line-icons.min.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/theme/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/theme/plugins/uniform/css/uniform.default.css"
	rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="${pageContext.request.contextPath}/theme/pages/css/lock.css" rel="stylesheet"
	type="text/css" />
<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="${pageContext.request.contextPath}/theme/css/components.css" id="style_components"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/theme/css/plugins.css" rel="stylesheet"
	type="text/css" />
<link href="${pageContext.request.contextPath}/theme/layout/css/layout.css" rel="stylesheet"
	type="text/css" />
<link href="${pageContext.request.contextPath}/theme/layout/css/themes/grey.css"
	rel="stylesheet" type="text/css" id="style_color" />
<link href="${pageContext.request.contextPath}/theme/layout/css/custom.css" rel="stylesheet"
	type="text/css" />
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico" />
<script type="text/javascript">
	var cooperopcontextpath = "${pageContext.request.contextPath}";
</script>
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<!-- DOC: Apply "page-header-fixed-mobile" and "page-footer-fixed-mobile" class to body element to force fixed header or footer in mobile devices -->
<!-- DOC: Apply "page-sidebar-closed" class to the body and "page-sidebar-menu-closed" class to the sidebar menu element to hide the sidebar by default -->
<!-- DOC: Apply "page-sidebar-hide" class to the body to make the sidebar completely hidden on toggle -->
<!-- DOC: Apply "page-sidebar-closed-hide-logo" class to the body element to make the logo hidden on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-hide" class to body element to completely hide the sidebar on sidebar toggle -->
<!-- DOC: Apply "page-sidebar-fixed" class to have fixed sidebar -->
<!-- DOC: Apply "page-footer-fixed" class to the body element to have fixed footer -->
<!-- DOC: Apply "page-sidebar-reversed" class to put the sidebar on the right side -->
<!-- DOC: Apply "page-full-width" class to the body element to have full width page without the sidebar menu -->
<div class="page-lock">
	<div class="page-logo">
		<a class="brand" href="javascript:void(0)"> <img
			src="${pageContext.request.contextPath}/theme/layout/img/logo-big.png" alt="logo" />
		</a>
	</div>
	<div class="page-body">
		<div id="login_container" style="background-color: #fff;text-align:center;">
		</div>
	</div>
	<div class="page-footer-custom"><fmt:formatDate value="${now}" pattern="yyyy"/> &copy; ${copyright }</div>
</div>
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="${pageContext.request.contextPath}/theme/plugins/respond.min.js"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/excanvas.min.js"></script> 
<![endif]-->
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.blockui.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/uniform/jquery.uniform.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.cokie.min.js"
	type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/plugins/jquery-validation/js/jquery.validate.min.js"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js" type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/theme/plugins/backstretch/jquery.backstretch.min.js"
	type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<script src="${pageContext.request.contextPath}/theme/scripts/metronic.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/layout/scripts/layout.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/controls/controls.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/controls/input/imagevalidcode.js"
	type="text/javascript"></script>
 <script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
 <script type="text/javascript">
window.onload = function() {
var obj = new WxLogin({  
     id:"login_container",   
     appid: '${wx_appid}',   
     scope: "snsapi_login",   
     redirect_uri: encodeURIComponent("http://cooperop.crtech.cn/w/application/auth/wxlogin.json"),  
     state: '${state}',  
     style: "black",
     href: ""
   });

}
</script>
<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>