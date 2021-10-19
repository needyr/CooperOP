<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="个人资料设置" dispermission="true">

<html>
<head>
<title>个人资料设置</title>
<meta charset="UTF-8">
<meta name="viewport"
	content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,initial-scale=1.0,width=device-width" />
<meta name="format-detection"
	content="telephone=no,email=no,date=no,address=no">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/font-awesome/css/font-awesome.min.css?iml=Y">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/api.css?iml=Y">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/common.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/mobileMine.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/bootstrap/bootstrap.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/simple-line-icons/simple-line-icons.css?iml=Y">
	
<script type="text/javascript">
	var cooperopcontextpath = "${pageContext.request.contextPath}";
</script>

<script src="${pageContext.request.contextPath}/theme/plugins/apicloud/api.js?iml=Y" type="text/javascript"></script>



<script src="${pageContext.request.contextPath}/theme/scripts/window.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js?iml=Y" type="text/javascript"></script>
<style type="text/css">
.img-circle{
    width: 4em;
    height: 4em;
    border-radius: 2em !important;
}
</style>
</head>
<body>
	<div id="wrap">
		<div id="main">
			<div class="main-div">
                <div class="main-i">
                	<div class="icon-circle"><i class="fa fa-user"></i></div>
                    <img alt="" class="img-circle" src="${pageContext.request.contextPath}/theme/layout/img/avatar3_small.jpg" />
                </div>
                <div class="main-content">
                    <span id="username_span"></span>
                </div>

            </div>
             <div class="main-div2">
                <div class="main-i2">
                    <i class="icon-settings"></i>
                </div>
                <div class="main-content2 border-none" id="setting">
                    <span>资料维护</span>
                </div>
            </div>
            <div class="main-div2">
                <div class="main-i2">
                    <i class="icon-lock"></i>
                </div>
                <div class="main-content2" id="modify_p">
                    <span>修改密码</span>
                </div>
            </div>
            <div class="main-div2">
                <div class="main-i2">
                    <i class="icon-power"></i>
                </div>
                <div class="main-content2" id="lgout" >
                    <span>退出系统</span>
                </div>
            </div>
		</div>
	</div>
</body>
</html>
</s:page>

<script type="text/javascript">
$(document).ready(function(){
	if (userinfo.id) {
		$("#username_span").html(userinfo.name);
			 if (userinfo.avatar) {
				 $(".icon-circle").hide();
				$(".img-circle").show();
				$(".img-circle").attr("src", cooperopcontextpath+ "/rm/s/application/" + userinfo.avatar + "S");
			} else {
				$(".img-circle").hide();
				if (userinfo.gender == '0') {
					$(".icon-circle").find(".fa").addClass("female");
				}
				$(".icon-circle").show();
			} 
		}
	$("#setting").on("click",function(){
		$.open("application.mine.profile",{api_title: '个人资料维护'});
	});
	$("#modify_p").on("click",function(){
		$.open("application.mine.changepwd",{api_title: '修改密码'});
	});
	$("#lgout").on("click",function(){
		$.call("application.auth.logout", {}, function(rtn) {
			if (rtn.emsg) {
				$.error(rtn.emsg);
			} else {
				store.clear();
				api.closeWidget({silent: true});
			}
		}, function(ems) {
			$.error(ems);
		});
	});
});
function re(){
	location.reload();
}
	
</script>