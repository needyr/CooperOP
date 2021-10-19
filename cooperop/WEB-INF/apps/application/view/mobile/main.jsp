<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<html>
<head>
<title>首页</title>
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


<script src="${pageContext.request.contextPath}/theme/scripts/menu.js?iml=Y" type="text/javascript"></script>
</head>
<body>
	<div id="wrap">
		<div id="main">
			<%-- <div class="main-important">
				<div class="main-importantDivchunk">
					<div class="main-smallchunk main-border" id="notification_menu" onclick="location.reload();">
						<div class="main-smallchunkImg">
							<img
								src="${pageContext.request.contextPath}/theme/img/tongzhigonggao_03.png"
								alt="">
						</div>
						<p>通知公告</p>
					</div>
					<div class="main-smallchunk main-border" id="new_menu">
						<div class="main-smallchunkImg">
							<img src="${pageContext.request.contextPath}/theme/img/news.png"
								alt="">
						</div>
						<p>公司新闻</p>
					</div>
					<div class="main-smallchunk main-border" id="activity_menu">
						<div class="main-smallchunkImg">
							<img
								src="${pageContext.request.contextPath}/theme/img/important_03.png"
								alt="">
						</div>
						<p>公司活动</p>
					</div>
					<div class="main-smallchunk">
						<div class="main-smallchunkImg" id="info_menu">
							<img
								src="${pageContext.request.contextPath}/theme/img/important_06.png"
								alt="">
						</div>
						<p>企业信息</p>
					</div>
				</div>
			</div> --%>
			<div class="menu-content">
				
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
/* onready = function(){
	$.initmenus();
} */
$(document).ready(function(){
	$.initmenus();
});
function re(){
	location.reload();
}
	
</script>
</html>
