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
	<script src="${pageContext.request.contextPath}/theme/scripts/welcome.js" type="text/javascript"></script>
	<%--<script src="${pageContext.request.contextPath}/theme/scripts/menu.js" type="text/javascript"></script>--%>
   <style type="text/css">
  	.page-content{
  		padding: 0 !important;
  	}
  </style>
</head>

<body>
	<div class="home-page" >
		<div class="column-container clearfix">
			<div class="columnbox daiban" id="task_panel">
				<div class="columnCard">
					<div class="title">
						<h3 class="column-name" id="textModalDj">待办事项<span><!-- (61) --></span></h3>
						<a class="more" id="moreTask" href="javascript:void(0);" title="更多"><i class="cicon icon-more"></i></a>
					</div>
					<div class="contentbox">
						<div class="tasks-list">
							<ul>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="columnbox notice" id="notify_panel">
				<div class="columnCard">
					<div class="title">
						<h3 class="column-name">通知公告</h3>
						<a class="more" id="moreNotiy" href="javascript: void(0);"><i class="cicon icon-more"></i></a>
					</div>
					<div class="contentbox">
						<div class="notice-list">
							<!-- <div class="headlines">
								<dl>
									<dt class="hideText"><a href="#">升级提醒！V3.0版本正式上线</a></dt>
									<dd>历时3个月的不断打磨，我们将于2019年6月30日凌晨正式升级新版，届时系统将停机，请用户提前处理好业务数据，停机时间待定</dd>
								</dl>
							</div> -->
							<ul class="nlist">
<%-- 								<li><a href="#">国庆节期间服务器停机维护公告</a><span>2019-05-11</span></li> --%>
<%-- 								<li><a href="#">北京大通生物医药有限公司申请交换首营资料及生产许可证电子扫描件</a><span>2019-05-11</span></li> --%>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div id="calendar" class="columnbox calendar"></div>
			<div class="columnbox info-warning" id="warning_panel">
				<div class="columnCard">
					<div class="title">
						<h3 class="column-name">预警信息</h3>
						<!-- <a class="more" href="javascript: void(0);" id="moreWarn"><i class="cicon icon-more"></i></a> -->
					</div>
					<div class="contentbox">
						<div class="warning-list clearfix">
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
		$("#moreTask").on("click", showMoreTask);
		if(typeof crtechCompany != 'undefined') {
			$('.tcContent').css('top', "0px");
			$('.tcContent').css('border-right', "1px solid #666");
		}
		initAll();
		$("#textModalDj").on("click", function(){
			if(typeof crtechTogglePage == 'undefined'){
				
			}else{
				var params = {"djlx": "G00",
						"djbs": "CGA",
						"djbh": "",
						"product_code": "erp"};
				//crtechCalc();
				//crtechOpenPage("erp.bill.CGA.G00", "测试弹窗单据", "crtech://cs/modalDJ", 0, JSON.stringify(params));
			}
		});
	});
</script>
</s:page>