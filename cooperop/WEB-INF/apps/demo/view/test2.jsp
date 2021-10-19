<%@page import="cn.crtech.cooperop.application.action.SchemeAction"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>

<style>
#nav {
	width: 98%;
	height: 80px;
	background-color: white;
}

.right_arrow {
	width: 20px;
	height: 100%;
	background-color: red;
	font-size: 30px;
}

* {
	margin: 0;
	padding: 0;
}

html, body {
	min-height: 100%;
}

body {
	text-align: center;
	padding-top: 100px;
	background: #689976;
	background: linear-gradient(#689976, #ACDACC);
	font-family: 'Merriweather Sans', arial, verdana;
}

.breadcrumb {
	/*centering*/
	display: inline-block;
	box-shadow: 0 0 15px 1px rgba(0, 0, 0, 0.35);
	overflow: hidden;
	border-radius: 5px;
	/*Lets add the numbers for each link using CSS counters. flag is the name of the counter. to be defined using counter-reset in the parent element of the links*/
	counter-reset: flag;
}

.breadcrumb a {
	text-decoration: none;
	outline: none;
	display: block;
	float: left;
	font-size: 12px;
	line-height: 36px;
	color: white;
	/*need more margin on the left of links to accomodate the numbers*/
	padding: 0 10px 0 60px;
	background: #666;
	background: linear-gradient(#666, #333);
	position: relative;
}
/*since the first link does not have a triangle before it we can reduce the left padding to make it look consistent with other links*/
.breadcrumb a:first-child {
	padding-left: 46px;
	border-radius: 5px 0 0 5px; /*to match with the parent's radius*/
}

.breadcrumb a:first-child:before {
	left: 14px;
}

.breadcrumb a:last-child {
	border-radius: 0 5px 5px 0; /*this was to prevent glitches on hover*/
	padding-right: 20px;
}

/*hover/active styles*/
.breadcrumb a.active, .breadcrumb a:hover {
	background: #333;
	background: linear-gradient(#333, #000);
}

.breadcrumb a.active:after, .breadcrumb a:hover:after {
	background: #333;
	background: linear-gradient(135deg, #333, #000);
}

/*adding the arrows for the breadcrumbs using rotated pseudo elements*/
.breadcrumb a:after {
	content: '';
	position: absolute;
	top: 0;
	right: -18px; /*half of square's length*/
	/*same dimension as the line-height of .breadcrumb a */
	width: 36px;
	height: 36px;
	/*as you see the rotated square takes a larger height. which makes it tough to position it properly. So we are going to scale it down so that the diagonals become equal to the line-height of the link. We scale it to 70.7% because if square's: 
    length = 1; diagonal = (1^2 + 1^2)^0.5 = 1.414 (pythagoras theorem)
    if diagonal required = 1; length = 1/1.414 = 0.707*/
	transform: scale(0.707) rotate(45deg);
	/*we need to prevent the arrows from getting buried under the next link*/
	z-index: 1;
	/*background same as links but the gradient will be rotated to compensate with the transform applied*/
	background: #666;
	background: linear-gradient(135deg, #666, #333);
	/*stylish arrow design using box shadow*/
	box-shadow: 2px -2px 0 2px rgba(0, 0, 0, 0.4), 3px -3px 0 2px rgba(255, 255, 255, 0.1);
	/*
        5px - for rounded arrows and 
        50px - to prevent hover glitches on the border d using shadows*/
	border-radius: 0 5px 0 50px;
}
/*we dont need an arrow after the last link*/
.breadcrumb a:last-child:after {
	content: none;
}
/*we will use the :before element to show numbers*/
.breadcrumb a:before {
	content: counter(flag);
	counter-increment: flag;
	/*some styles now*/
	border-radius: 100%;
	width: 20px;
	height: 20px;
	line-height: 20px;
	margin: 8px 0;
	position: absolute;
	top: 0;
	left: 30px;
	background: #444;
	background: linear-gradient(#444, #222);
	font-weight: bold;
}

.flat a, .flat a:after {
	background: white;
	color: black;
	transition: all 0.5s;
}

.flat a:before {
	background: white;
	box-shadow: 0 0 0 1px #ccc;
}

.flat a:hover, .flat a.active, .flat a:hover:after, .flat a.active:after {
	background: #9EEB62;
}

/* 面包屑导航 */
#crumbs ul li {
	float: left;
	list-style-type: none;
	border-left: 1px solid gray;
	border-top: 1px solid gray;
	border-bottom: 1px solid gray;
	border-right: 1px solid gray;
	display: block;
	float: left;
	height: 40px;
	width: 120px;
	background: transparent;
	text-align: center;
	position: relative;
	margin: 0 10px 0 0;
	font-size: 20px;
	text-decoration: none;
	color: black;
	line-height: 40px;
}

#crumbs ul li:after {
	content: "";
	position: absolute;
	border-top: 1px solid gray;
	border-right: 1px solid gray;
	width: 29px;
	height: 29px;
	right: -15px;
	top: 5px;
	z-index: 5;
	transform: rotate(45deg);
	color: black;
	background: white;
}

#crumbs ul li:before {
	content: "";
	position: absolute;
	border-top: 1px solid gray;
	border-right: 1px solid gray;
	width: 29px;
	height: 29px;
	left: -15px;
	top: 5px;
	transform: rotate(45deg);
	color: black;
	background: white;
}

#crumbs ul li:first-child {
	border-top-left-radius: 10px;
	border-bottom-left-radius: 10px;
}

#crumbs ul li:first-child:before {
	display: none;
}

#crumbs ul li:last-child {
	/*padding-right: 60px;*/
	width: 400px;
	border-right: 1px solid gray;
	border-top-right-radius: 10px;
	border-bottom-right-radius: 10px;
}

#crumbs ul li:last-child:after {
	display: none;
}

#crumbs ul li:hover {
	border-left: 1px solid #3498db;
	border-top: 1px solid #3498db;
	border-bottom: 1px solid #3498db;
}

#crumbs ul li:hover:after {
	border-right: 1px solid #3498db;
	border-top: 1px solid #3498db;
}

#crumbs ul li:hover:before {
	border-right: 1px solid #3498db;
	border-top: 1px solid #3498db;
}

#crumbs ul li:hover:last-child {
	border-right: 1px solid #3498db;
}

#arr {
	margin-top: -39px;
	color: #EFF2F5;
	font-size: 130px;
	font-weight: 100;
}

.right_arrow {
	width: 48px;
	height: 60px;
	background-color: scrollbar;
	overflow: hidden;
}
</style>

<s:page dispermission="true" disloggedin="true" title="测试2" ismodal="false">
	<%-- 	<s:row>

		<div class="right_arrow">
			<i class="fa fa-angle-right" id="arr"></i>
		</div>



		<i class="fa fa-long-arrow-right" id="pp"></i>
		<p>
			Arrow-right icon: <span class="glyphicon glyphicon-arrow-right"></span>
		</p>
		<button type="button" class="btn btn-primary btn-arrow-left">箭头向左的按钮</button>
				<div id="nav">
			<div class="item">
				<span>数据导入</span>
				<span class="right_arrow"><i class="fa fa-chevron-right"></i></span>
				>
			</div>
		</div>
		<!-- a simple div with some links -->
		<div class="breadcrumb">
			<a href="#" class="active">Browse</a> <a href="#">Compare</a> <a href="#">Order Confirmation</a> <a href="#">Checkout</a>
		</div>
		<!-- another version - flat style with animated hover effect -->
		<div class="breadcrumb flat">
			<a href="#" class="active">Browse</a> <a href="#">Compare</a> <a href="#">Order Confirmation</a> <a href="#">Checkout</a>
		</div>
	</s:row>

	<div id="crumbs">
		<ul>
			<li>导航栏1</li>
			<li>导航栏2</li>
			<li>导航栏3</li>
			<li>导航栏3</li>
			<li>导航栏4</li>
		</ul>
	</div>
 --%>

	<s:row>
		<p>这是四个用fa-spin类实现的旋转流畅的加载图标</p>
		<i class="fa fa-spinner fa-spin"></i>
		<i class="fa fa-circle-o-notch fa-spin"></i>
		<i class="fa fa-ambulance fa-spin"></i>
		<i class="fa fa-cog fa-spin"></i>
		<p>这是四个用fa-pulse类实现的旋转不太流畅的加载图标</p>
		<i class="fa fa-spinner fa-pulse"></i>
		<i class="fa fa-circle-o-notch fa-pulse"></i>
		<i class="fa fa-refresh fa-pulse"></i>
		<i class="fa fa-cog fa-pulse"></i>
		<!-- 按钮触发模态框 -->
		<div style="width: 100%; height: 100%;">
			<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">开始演示模态框</button>

			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div style="coler: white">在这里添加一些文本</div>
				</div>
			</div>

			<div id="loading" style="position: absolute; left: 0px; top: 0px; width: 100%; height: 100%; z-index: 999; background-color: rgba(0, 0, 0, 0.3); text-align: center; vertical-align: middle; color: white;">文件上传中,请稍等.....</div>
		</div>
	</s:row>

</s:page>

<script type="text/javascript">
	function pageload() {
		$("#loading").show();
	}
</script>
