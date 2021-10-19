<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="智能辅助决策支持平台" dispermission="true">
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>智能辅助决策支持平台</title>
  <meta name="format-detection" content="telephone=no,email=no,address=no,date=no" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no, viewport-fit=cover">
	<meta name="apple-touch-fullscreen" content="yes">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="screen-orientation" content="portrait">
	<meta name="x5-orientation" content="portrait">
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="HandheldFriendly" content="true">
	<meta name="MobileOptimized" content="320">
	<meta name="screen-orientation" content="portrait">
	<meta name="x5-orientation" content="portrait">
	<meta name="full-screen" content="yes">
	<meta name="x5-fullscreen" content="true">
	<meta name="browsermode" content="application">
	<meta name="x5-page-mode" content="app">
	<meta name="msapplication-tap-highlight" content="no">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/css/main/layout.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/css/main/home.css" type="text/css">
	<script src="${pageContext.request.contextPath}/theme/scripts/head.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/scripts/product.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/scripts/menu.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/scripts/controls/layout/tabwindow.js" type="text/javascript"></script>	
	<script src="${pageContext.request.contextPath}/theme/im/client.js" type="text/javascript"></script>
</head>

<body>
<div class="container">
	<div class="layout-header">
		<div class="header-cr">
			<div class="clearfix">
				<div class="switch"><a href="javascript:void(0);"><i class="cicon icon-menu"></i></a></div>
				<div class="logo"><a href="javascript:void(0);"></a></div>
				<div class="enterprise" id="enterprise"><a href="javascript: void(0);"></a></div>
			</div>
			<div class="topbar">
				<div class="clearfix">
					<div class="menu-item"><a href="javascript: void(0);" id="header_suggestions_bar"><i class="cicon icon-msg"></i>消息<span class="tips"></span></a></div>
					<div class="menu-item"><a href="javascript: void(0);" id="header_notification_bar"><i class="cicon icon-ling"></i>公告<span class="tips"></span></a></div>
					<div class="menu-item"><a href="javascript: void(0);" id="header_task_bar"><i class="cicon icon-task"></i>待办<span class="tips"></span></a></div>
					<div class="menu-item"><a href="javascript: void(0);" id="header_calendar_bar"><i class="cicon icon-calendar"></i>日历</a></div>
					<div class="menu-item exit-btn"><a href="javascript: void(0);" id="header_logout_bar"><i class="cicon icon-exit"></i></a></div>
				</div>
			</div>
		</div>
	</div>
	
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
	
	
	<div class="layout-toolbar">
		<div class="statusbar-cr">
			<div class="tools leftbar clearfix">
				<div class="item goback"><a class="disabled" href="javascript: void(0);"><i class="cicon icon-retreat"></i></a></div>
				<div class="item forward"><a href="javascript: void(0);"><i class="cicon icon-advance"></i></a></div>
				<div class="item refresh-tab"><a href="javascript: void(0);"><i class="cicon icon-refresh"></i></a></div>
			</div>
			<div class="tools rightbar clearfix">
				<div class="item left-tab"><a href="javascript: void(0);" class="disabled"><i class="cicon icon-back"></i></a></div>
				<div class="item right-tab"><a href="javascript: void(0);" class="disabled"><i class="cicon icon-go"></i></a></div>
				<div class="item close-tabs"><a href="javascript: void(0);" class="disabled"><i class="cicon icon-close-all"></i></a></div>
			</div>
			<div class="bookmark">
				<div class="itempath" id="itempath">
					<ul class="home-ul" style="z-index: 2; background: #fff; padding: 5px 0; top: 0px;">
						<li wid="home-page">
							<a class="item-tab homebtn" href="javascript: void(0);"><i class="cicon icon-home"></i></a>
						</li>
					</ul>
					<ul class="tab-ul" style="left: 68px;">
						<!-- <li wid="home-page">
							<a class="item-tab homebtn" href="javascript: void(0);"><i class="cicon icon-home"></i></a>
						</li> -->
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="layout-content">
		<div class="view-container" id="page-content">
			<div class="page-tab" wid="home-page">
				<iframe src ="${contextpath}/welcome.jsp" frameborder="0"  style="width:100%;border:0px;" ></iframe>
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
</div>
</body>
</html>
<script type="text/javascript">
$(document).ready(function(){
	$.tabwindowinit();
	window.setInterval("reinitIframe()", 200);
	$.initHead();
	
	setTimeout(function (){
		$cimc.init({
			userid: userinfo.id ,
			_CRSID: '${_CRSID}'
		});
	}, 2000);
});
function reinitIframe(){
	try{
		var h = document.documentElement.scrollHeight || document.body.scrollHeight;
		$("#page-content > .page-tab iframe").css("height", h-116+"px");
	}catch (ex){}
	}
</script>
</s:page>