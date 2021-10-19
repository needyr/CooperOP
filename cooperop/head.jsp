<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="" dispermission="true">
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Document</title>
	<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/css/main.css">
  <link rel="stylesheet" href="http://at.alicdn.com/t/font_578622_xfturlr8r5p.css">
  <link rel="stylesheet" href="http://at.alicdn.com/t/font_1035067_97xk9u1rqh.css">
	<link rel="stylesheet" href="http://at.alicdn.com/t/font_1122195_kp4oa9c8azq.css">
  <script src="${pageContext.request.contextPath}/theme/scripts/head.js" type="text/javascript"></script>
  <style type="text/css">
  	.page-content{
  		min-height: 100% !important;
  		padding: 0 !important;
  	}
/*   	.head{
  		box-shadow: none;
  		outline: 1px solid #000;
  	} */
  	body{
  		overflow: hidden;
  	}
  	
  </style>
</head>

<body>
  <div class="head">
    <img src="${pageContext.request.contextPath}/theme/img/logo-big.png" alt=""/>
    <a class="qyName" data-id=""> <span>切换</span></a>
    <div class="head-menu">
      <div class="headDiv">
        <a href="javascript:void(0);" class="toolbar-logout" id="header_logout_bar" title="注销登录"><i class="cicon icon-exit headRicon"></i></a>
        <a href="javascript:void(0);" class="toolbar-task" id="header_task_bar" title="待办事项"><i class="cicon icon-calendar headRicon"><span>2</span></i></a>
        <a href="javascript:void(0);" class="toolbar-notify" id="header_notification_bar" title="通知公告"><i class="cicon icon-ling headRicon"><span>2</span></i></a>
        <a href="javascript:void(0);" class="toolbar-message" id="header_suggestions_bar" title="消息提醒"><i class="cicon icon-msg headRicon"><span>2</span></i></a>
      </div>
    </div>
  </div>
</body>
</html>
<script type="text/javascript">
	$(document).ready(function(){
		$.initHead();
	});
			
</script>
</s:page>