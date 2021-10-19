<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	pageContext.setAttribute("android_qrcode", SystemConfig.getSystemConfigValue("cooperop", "android_qrcode"));
	pageContext.setAttribute("ios_qrcode", SystemConfig.getSystemConfigValue("cooperop", "ios_qrcode"));
	pageContext.setAttribute("check_app", SystemConfig.getSystemConfigValue("cooperop", "check_deviceId"));
	pageContext.setAttribute("system_title", SystemConfig.getSystemConfigValue("global", "system_title"));
	pageContext.setAttribute("copyright", SystemConfig.getSystemConfigValue("global", "copyright"));
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
<link href="${pageContext.request.contextPath}/theme/plugins/google-fonts/opensans.css?ipl=Y"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/theme/plugins/font-awesome/css/font-awesome.min.css?ipl=Y"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/theme/plugins/simple-line-icons/simple-line-icons.min.css?ipl=Y"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/theme/plugins/bootstrap/css/bootstrap.min.css?ipl=Y"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/theme/plugins/uniform/css/uniform.default.css?ipl=Y"
	rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="${pageContext.request.contextPath}/theme/pad/lock.css?ipl=Y" rel="stylesheet"
	type="text/css" />
<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="${pageContext.request.contextPath}/theme/css/components.css?ipl=Y" id="style_components"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/theme/css/plugins.css?ipl=Y" rel="stylesheet"
	type="text/css" />
<link href="${pageContext.request.contextPath}/theme/css/layout.css?ipl=Y" rel="stylesheet"
	type="text/css" />
<link href="${pageContext.request.contextPath}/theme/css/custom.css?ipl=Y" rel="stylesheet"
	type="text/css" />
<!-- END THEME STYLES -->
<link rel="shortcut icon" href="favicon.ico" />
<link href="${pageContext.request.contextPath}/theme/plugins/iconfont/iconfont.css?ipl=Y" rel="stylesheet" type="text/css">
<script type="text/javascript">
	var cooperopcontextpath = "${pageContext.request.contextPath}";
</script>

<style type="text/css">
body{
	background-color: #fff !important;
}
</style>
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
			src="${pageContext.request.contextPath}/theme/img/logo-big.png?ipl=Y" alt="logo" />
		</a>
	</div>
	<div class="page-body">
		<div class="lock-head">${system_title }</div>
		<div class="lock-body">
			<div id="zhform">
				<div class="pull-left lock-avatar-block">
					<i class="fa fa-users lock-avatar"></i>
				</div>
			
				<form id="loginForm" class="lock-form pull-left" action="" method="post" onsubmit="submitMe();return false;">
					<div class="form-group">
						<input class="form-control placeholder-no-fix" type="text"
							autocomplete="off" placeholder="工号/手机/邮箱" name="userid" required="required"/>
					</div>
					<div class="form-group">
						<input class="form-control placeholder-no-fix" type="password"
							autocomplete="off" placeholder="密码" name="password" required="required" />
					</div>
					<div class="form-group form-validcode" cooperoptype="imagevalidcode">
						<input class="form-control placeholder-no-fix" type="text" cooperoptype="imagevalidcode_input"
							autocomplete="off" placeholder="验证码" name="validcode" /><img cooperoptype="imagevalidcode_img" alt="验证码">
					</div>
					<div class="form-actions">
						<button type="submit" class="btn btn-success uppercase">登&nbsp;&nbsp;录</button>
					</div>
				</form>
			</div>
		</div>
		
		<div class="lock-bottom">
			<a href="javascript:location.reload();" class="fingerdiv">忘记密码？点击找回</a>
		</div>
		</div>
	</div>
	<div class="p-footer">
		<div class="page-footer-custom">
			<fmt:formatDate value="${now}" pattern="yyyy"/> &copy; ${copyright }
		</div>
	</div>
</div>
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src="${pageContext.request.contextPath}/theme/plugins/respond.min.js?ipl=Y"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/excanvas.min.js?ipl=Y"></script> 
<![endif]-->
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js?ipl=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js?ipl=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/bootstrap/js/bootstrap.min.js?ipl=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.blockui.min.js?ipl=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/uniform/jquery.uniform.min.js?ipl=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.cokie.min.js?ipl=Y"
	type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/plugins/jquery-validation/js/jquery.validate.min.js?ipl=Y"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js?ipl=Y" type="text/javascript"></script>
<%-- <script
	src="${pageContext.request.contextPath}/theme/plugins/backstretch/jquery.backstretch.min.js?ipl=Y"
	type="text/javascript"></script> --%>
<!-- END PAGE LEVEL PLUGINS -->
<script src="${pageContext.request.contextPath}/theme/scripts/metronic.js?ipl=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/layout/scripts/layout.js?ipl=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js?ipl=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/controls/controls.js?ipl=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/controls/input/imagevalidcode.js?ipl=Y"
	type="text/javascript"></script>
<script>
	apiready = function () {
		cloud_app.is_app = true;
		cloud_app.app_name = api.appName;
	}	
	cwbready = function() {
		var html = [];
		html.push('<div class="jwin_tools">');
		html.push('<a title="重载" class="reload" href="javascript:void(0);" onclick="location.reload(true);">');
		html.push('<i class="icon-reload"></i>');
		html.push('</a>');
		html.push('<a title="最小化" class="min" href="javascript:void(0);" onclick="jwin.min()">');
		html.push('<i class="min-icon"></i>');
		html.push('</a>');
		html.push('<a title="最大化" class="max" href="javascript:void(0);">');
		html.push('<i class="max-icon"></i>');
		html.push('</a>');
		html.push('<a title="退出系统" class="exit" href="javascript:void(0);" onclick="jwin.exit()">');
		html.push('<i class="exit-icon"></i>');
		html.push('</a>');
		html.push('</div>');
		$("body").append(html.join(""));
		$('.jwin_tools').on('click', '.max, .normal', function(e) {
            e.preventDefault();
            if ($(this).hasClass("max")) {
                $(this).removeClass("max").addClass("normal").attr("title", "还原窗口");
                $(this).find("i").removeClass("max-icon").addClass("normal-icon");
                jwin.max();
                
            } else {
                $(this).removeClass("normal").addClass("max").attr("title", "最大化");
                $(this).find("i").removeClass("normal-icon").addClass("max-icon");
                jwin.normal();
            }
        });
	}
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
		var da = $("#loginForm").getData();
		$.call("application.auth.login_ipc", da, function(rtn) {
			/* if (rtn.emsg) {
				if(rtn.error_flag == 'A'){
					$.message(rtn.emsg);
				}else{
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
				}
			} else {
				//location.href = cooperopcontextpath + rtn.redirect_url;
			    closeModal(rtn);
			} */
			if(rtn){
				if (typeof(crtech) != "undefined")
				    crtech.register(JSON.stringify(rtn));
			}
		}, function(ems) {
			$.error(ems);
		});
	}
</script>
</body>
</html>