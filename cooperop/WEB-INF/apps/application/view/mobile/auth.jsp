<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="app授权登陆" dispermission="true">
<html>
<head>
<title>app授权登陆</title>
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
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/plugins/iconfont/iconfont.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/simple-line-icons/simple-line-icons.css?iml=Y">
	
<script type="text/javascript">
	var cooperopcontextpath = "${pageContext.request.contextPath}";
</script>
<script src="${pageContext.request.contextPath}/theme/plugins/apicloud/api.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/window.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js?iml=Y" type="text/javascript"></script>
<style type="text/css">
.iconfont.icon-computer{
		font-size:15rem;
		display:block;
		text-align: center;
		line-height: 15rem;
		margin: 10rem auto;
	}
.bu1{
	font-size: 18px;
	width: 16rem;
	height:3rem;
	border: 1px solid #4bd94b;
	color: #4bd94b;
	font-weight: 400;
	border-radius:0.3rem!important;
	margin-bottom: 1.3rem;
}
.bu2{
	font-size: 14px;
	width: 16rem;
	height:3rem;
	border: 0px solid #999;
	color: #999;
	font-weight: 300;
	border-radius:0.3rem!important;
}
</style>
</head>
<body>
	<div id="wrap" style="background-color: #fff;">
		<div id="main">
			<div class="iconfont icon-computer">

			</div>
			<p style="text-align: center;">
			<button type="button" onclick="authlogin()" class="bu1">确认登陆</button></p>
			<p style="text-align: center;">
			<button type="button" onclick="authc()" class="bu2">取消登陆</button></p>
		</div>
	</div>
</body>
</html>
</s:page>

<script type="text/javascript">
 onready = function(){
}
$(document).ready(function(){
});
function authlogin(){
	$.call("application.auth.barcodeLogin", {loginid: '${param.loginid}'} , function(result){
		if(result){
			$.close();
		}
	});
}
function authc(){
	$.close();
}
function re(){
	location.reload();
}
	
</script>