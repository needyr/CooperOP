<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
	String welcome_page = GlobalVar.getSystemProperty("welcome.url");
	if (!CommonFun.isNe(welcome_page) && !"login.jsp".equals(welcome_page)) {
		if (!welcome_page.startsWith("/") && !welcome_page.startsWith("http")) {
			welcome_page = request.getContextPath() + "/" + welcome_page;
		}
		String server = request.getHeader("host");
		if (!CommonFun.isNe(request.getHeader("Scheme"))) {
			server = request.getHeader("Scheme") + "://" + server;
		} else {
			server = request.getScheme() + "://" + server;
		}
		response.sendRedirect(server + welcome_page);
		return;
	}
%>
<s:page title="" disloggedin="true" ismodal="true">
	<link
		href="${pageContext.request.contextPath}/theme/pages/css/lock.css"
		rel="stylesheet" type="text/css" />
	<script
		src="${pageContext.request.contextPath}/theme/plugins/backstretch/jquery.backstretch.min.js"
		type="text/javascript"></script>
	<div class="page-lock">
		<div class="page-logo">
			<a class="brand" href="javascript:void(0)"> <img
				src="${pageContext.request.contextPath}/theme/layout/img/logo-big.png"
				alt="logo" />
			</a>
		</div>
		<div class="page-body">
			<div class="lock-head">超然协同办公平台 CooperOP</div>
			<div class="lock-body">
				<div class="pull-left lock-avatar-block">
					<i class="fa fa-users lock-avatar"></i>
				</div>
				<form id="loginForm" class="lock-form pull-left" action=""
					method="post" onsubmit="submitMe();return false;">
					<div class="form-group">
						<input class="form-control placeholder-no-fix" type="text"
							autocomplete="off" placeholder="工号/手机/邮箱" name="userid"
							required="required" />
					</div>
					<div class="form-group">
						<input class="form-control placeholder-no-fix" type="password"
							autocomplete="off" placeholder="密码" name="password"
							required="required" />
					</div>
					<div class="form-group form-validcode"
						cooperoptype="imagevalidcode">
						<input class="form-control placeholder-no-fix" type="text"
							cooperoptype="imagevalidcode_input" autocomplete="off"
							placeholder="验证码" name="validcode" /><img
							cooperoptype="imagevalidcode_img" alt="验证码">
					</div>
					<div class="form-actions">
						<button type="submit" class="btn btn-success uppercase">登&nbsp;&nbsp;录</button>
					</div>
				</form>
			</div>
			<div class="lock-bottom">
				<a href="javascript:testModal();">忘记密码？点击找回</a>
			</div>
		</div>
		<div class="page-footer-custom">${now.year} &copy; 成都超然祥润科技有限公司
		</div>
	</div>
</s:page>
<script>
	jQuery(document)
			.ready(
					function() {
						$.call("application.auth.isneedvalid", {},
								function(rtn) {
									if (rtn) {
										$(".form-validcode").show();
										$(".form-validcode").find(
												"[name='validcode']").attr(
												"required", "required");
									}
								});

						Metronic.init(); // init metronic core components
						Layout.init(); // init current layout

						$
								.backstretch(
										[
												"${pageContext.request.contextPath}/theme/pages/media/bg/1.jpg",
												"${pageContext.request.contextPath}/theme/pages/media/bg/2.jpg",
												"${pageContext.request.contextPath}/theme/pages/media/bg/3.jpg",
												"${pageContext.request.contextPath}/theme/pages/media/bg/4.jpg" ],
										{
											fade : 1000,
											duration : 8000
										});
					});

	var submitMe = function() {
		$.call("application.auth.login", $("#loginForm").getData(), function(
				rtn) {
			if (rtn.emsg) {
				$.error(rtn.emsg);
			} else {
				location.href = cooperopcontextpath + "/" + rtn.redirect_url;
			}
		}, function(ems) {
			$.error(ems);
		});
	}

	var testModal = function() {
		var d = $("#loginForm").getData();
		$.modal("welcome.jsp", "测试", {
			t : 123,
			callback : function(rtn) {
				alert(rtn);
			}
		})
	}

	
</script>