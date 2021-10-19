<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="驾驶舱" isblank="true" disloggedin="true">
<html>
<head>
<meta charset="utf-8">
<title>${name}</title>
<meta name="format-detection" content="telephone=no,email=no,address=no,date=no" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no, viewport-fit=cover">
<meta name="apple-touch-fullscreen" content="yes">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<meta name="renderer" content="webkit">
<!-- 避免IE使用兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- 针对手持设备优化，主要是针对一些老的不识别viewport的浏览器，比如黑莓 -->
<meta name="HandheldFriendly" content="true">
<!-- 微软的老式浏览器 -->
<meta name="MobileOptimized" content="320">
<!-- uc强制竖屏 -->
<meta name="screen-orientation" content="portrait">
<!-- QQ强制竖屏 -->
<meta name="x5-orientation" content="portrait">
<!-- UC强制全屏 -->
<meta name="full-screen" content="yes">
<!-- QQ强制全屏 -->
<meta name="x5-fullscreen" content="true">
<!-- UC应用模式 -->
<meta name="browsermode" content="application">
<!-- QQ应用模式 -->
<meta name="x5-page-mode" content="app">
<!-- windows phone 点击无高光 -->
<meta name="msapplication-tap-highlight" content="no">
<script type="text/javascript" src="${contextpath}/theme/plugins/jquery.min.js"></script>
<script type="text/javascript" src="${contextpath}/theme/plugins/jquery-migrate.min.js"></script>
<script type="text/javascript" src="${contextpath}/theme/plugins/jquery.json.min.js"></script>
<script type="text/javascript" src="${contextpath}/theme/scripts/common.js"></script>
<script type="text/javascript" src="${contextpath}/theme/plugins/layer/layer.js"></script>
<script type="text/javascript" src="${contextpath}/res/${module}/cockpit/Highcharts-8.0.0/highcharts.js"></script>
<script type="text/javascript" src="${contextpath}/res/${module}/cockpit/Highcharts-8.0.0/highcharts-more.js"></script>
<script type="text/javascript" src="${contextpath}/res/${module}/cockpit/Highcharts-8.0.0/modules/drilldown.js"></script>
<%-- <script type="text/javascript" src="${contextpath}/res/${module}/cockpit/Highcharts-8.0.0/modules/exporting.js"></script> --%>

<script type="text/javascript" src="${contextpath}/res/${module}/cockpit/cockpit.js"></script>

</head>
<body>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$crcockpit.create("body", ${json});
	});

</script>
</html> 
</s:page>