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
  <title>menu</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/css/main/layout.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/css/main/home.css">
  <script src="${pageContext.request.contextPath}/theme/scripts/product.js" type="text/javascript"></script>
  <script src="${pageContext.request.contextPath}/theme/scripts/menu.js" type="text/javascript"></script>
   <style type="text/css">
  	.page-content{
  		padding: 0 !important;
  	}
  	.siderbar-cr {
  		padding-top: 0;
  	}
  	.siderbar-cr .menu {
  		top: 0;
	}
  </style>
</head>

<body>
		<div class="layout-siderbar">
		<div class="siderbar-cr">
			<div class="menu">
				<div id="scrollbox1">
					<ul>
						<li class="user-li"><a data-id="userinfo-pro" class="userhead active" href="javascript: void(0);"><img src="img/avatar3_small.jpg"></a></li>
						<li class="user-li" title="功能菜单搜索"><a data-id="search-pro" class="search" href="javascript: void(0);"><i class="cicon icon-search"></i></a></li>
					</ul>
				</div>
			</div>
			<div class="submenu">
				<div id="scrollbox2">
				</div>

				<div id="scrollbox3">
					<div class="userinfo">
						<div class="userhead"><img src="img/avatar3_small.jpg"></div>
						<dl>
							<dt>王宝强</dt>
							<dd>项目部-采购员</dd>
						</dl>
						<ul class="btnbox">
							<li><a href="javascript: void(0);" id="profile">资料</a></li>
							<li><a href="javascript: void(0);" id="changepwd">密码</a></li>
							<li><a class="exitbtn" id="exitbtn" href="javascript: void(0);">注销</a>
								<div class="form-item invisible" id="change_password" >
									<input type="password" name="pwd" placeholder="输入密码按回车键" autocomplete="off"/>
									<i class="cicon icon-advance" style="display:none;"></i>
									<a href="javascript: void(0);">取消</a>
								</div>
							</li>
						</ul>
					</div>
					<div class="nav-list">
						<h5>我的收藏</h5>
						<ul class="navtree sc">
						</ul>
					</div>
				</div>
				<div id="scrollbox4">
					<div class="nav-list">
						<h5>搜索</h5>
						<div class="search-field">
							<input type="text" placeholder="输入关键字查询"><button><i class="cicon icon-search"></i></button>
						</div>
						<ul class="navtree ss">
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="layout-floatmenu invisible">
		<div class="left-menu">
			<a class="shut-off-btn" href="javascript: void(0);"><i class="cicon icon-retract"></i></a>
			<div class="content">
				<div id="scrollbox3">
					<div class="enterprise-list">
						<ul>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	$(document).ready(function(){
		//$.initProduct();
		if(typeof crtechCompany != 'undefined') { 
			$('.tcContent').css('top', "0px");
			$('.tcContent').css('border-right', "1px solid #666");
		}
	});
			
</script>
</s:page>