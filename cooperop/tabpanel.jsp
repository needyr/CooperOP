<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title=""  dispermission="true">
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Document</title>
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/css/main.css">
  <link rel="stylesheet" href="http://at.alicdn.com/t/font_578622_xfturlr8r5p.css">
  <link rel="stylesheet" href="http://at.alicdn.com/t/font_1035067_97xk9u1rqh.css">
	<link rel="stylesheet" href="http://at.alicdn.com/t/font_1122195_6z3k7h9i7j8.css">
  <style type="text/css">
  	.page-content{
  		padding: 0 !important;
  	}
  	.right-content {
  		top :0;
  		left: 0;
  	}
  </style>
</head>

<body>
  <div class="right-content right-content1">
    <div class="headLeft">
    	<div class="tab ">
			<div class="refresh_div">
				<a harf="javascript:void(0);" class="left-tab" title="点击后退，查看历史访问"><i class="cicon icon-retreat"></i></a>
				<a harf="javascript:void(0);" class="right-tab" title="点击前进，查看历史访问"><i class="cicon icon-advance"></i></a>
				<a harf="javascript:void(0);" title="刷新当前页签" class="refresh-tab"><i class="cicon icon-refresh font14"></i></a>
			</div>
			<div class="tab_div">
    			<div class="tab_width">
    			</div>
    		</div>
    		<div class="tab_tool">
				<a harf="javascript:void(0);" class="left-tab" title="点击查看左边页签"><i class="cicon icon-back font12"></i></a>
				<a harf="javascript:void(0);" class="right-tab" title="点击查看右边页签"><i class="cicon icon-go font12"></i></a>
				<a class="tab_cursor close-all" href="javascript:void(0);" title="关闭全部页签"><i class="cicon icon-close-all"></i></a>
    		</div>
    	</div>
   		<div class="tab-content">
   		</div>
    </div>
  </div>
</body>
</html>
<script>
$(document).ready(function() {
	$.tabwindowinit();
});
</script>
</s:page>