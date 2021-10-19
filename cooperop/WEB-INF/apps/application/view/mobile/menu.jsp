<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<html>
<head>
<title>子菜单</title>
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
	href="${pageContext.request.contextPath}/theme/mobile/mobileMain.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/bootstrap/bootstrap.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/chatrz.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/simple-line-icons/simple-line-icons.css?iml=Y">
	
<script type="text/javascript">
	var cooperopcontextpath = "${pageContext.request.contextPath}";
</script>

<script src="${pageContext.request.contextPath}/theme/plugins/apicloud/api.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js?iml=Y"
	type="text/javascript"></script>


<script src="${pageContext.request.contextPath}/theme/scripts/window.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js?iml=Y" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/theme/scripts/mobileindex.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/menu.js?iml=Y" type="text/javascript"></script>
</head>
<body>
	<div id="wrap">
        <div id="main" onclick="reee()">
        </div>
    </div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$.initmenus('${param.popedom}');
})

function reee(){
	location.reload();
}
</script>
</html>
