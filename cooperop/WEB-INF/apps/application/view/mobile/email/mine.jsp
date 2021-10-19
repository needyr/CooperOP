<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>收件箱</title>
<meta charset="UTF-8">
  <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,initial-scale=1.0,width=device-width"/>
  <meta name="format-detection" content="telephone=no,email=no,date=no,address=no">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/font-awesome/css/font-awesome.min.css?iml=Y">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/api.css?iml=Y">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/common.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/mobileEmail.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/bootstrap/bootstrap.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/simple-line-icons/simple-line-icons.css?iml=Y">
	
	
<script src="${pageContext.request.contextPath}/theme/plugins/apicloud/api.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/window.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/email.js?iml=Y" type="text/javascript"></script>
</head>
<body>
<div id="wrap">
    <div class="header">
      <div class="header-div">
        <a href="javascript: void(0);" class="tiaoZhuan" onclick="backtomain();"><i class="icon-arrow-left"></i></a>
        <h4 class="header-h3">邮件</h4>
        <i class="fa fa-plus nanshou"></i>
        <i class="icon-note xianggu"></i>
        <div class="icon-d icon-choose">
            <span><i class="fa  fa-file-o"></i>草稿箱</span>
            <span><i class="fa fa-trash"></i>废件箱</span>
            <span><i class="icon-paper-plane"></i>发件箱</span>
            <span><i class="icon-settings"></i> &nbsp; 邮箱设置</span>
        </div>
      </div>
    </div>
    <div id="main">
      <div>
        <input type="text" class="main-ipt" placeholder="搜索邮件">
      </div>
      <div class="main-massage" id="emails">
      	
      </div>
    </div>
  </div>
<script type="text/javascript">
onready = function(){
	
}
$(document).ready(function(){
	$.initEmails(1);
	$(".nanshou").on("click",function(){
        if ($(".icon-d").css("display") == "none") {
            $(".icon-d").show();
        } else {
            $(".icon-d").hide();
        }
    });
	$(".xianggu").on("click",function(){
		$.open("application.mobile.email.modify",{nofooter: true});
		$(".nanshou").click();
	});
	$(".fa-file-o").on("click",function(){
		$.initEmails(3);
		$(".nanshou").click();
	});
	$(".fa-trash").on("click",function(){
		$.initEmails(4);
		$(".nanshou").click();
	});
	$(".icon-paper-plane").on("click",function(){
		$.initEmails(2);
		$(".nanshou").click();
	});
	$(".icon-settings").on("click", "a", function() {
		$.open("application.email.setting",{api_title: "邮箱设置"});
	});
})
	
	 
	function re(){
		location.reload();
	}
	function backtomain(){
		$.close();
	}
</script>
</body>	
</html>