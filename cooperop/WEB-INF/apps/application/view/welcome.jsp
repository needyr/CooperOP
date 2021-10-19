<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map"%>
<%@page import="cn.crtech.cooperop.application.action.MineAction"%>
<s:page title="" dispermission="true">
<link href="${pageContext.request.contextPath }/res/application/css/welcome.css" type="text/css" rel="stylesheet" />
<style type="text/css">
.set-menu-div .set-menu-title{
    text-align: center;
    position: relative;
    top: 10px;
}
.set-menu-div .set-menu-type{
    float: left;
    margin: 35px 0 0 20px;
    font-size: 10px;
    
}
.set-menu-div .set-menu-product{
    float: right;
    margin: 3px 10px 0 0;
    font-size: 12px;
}
.set-menu-div{
	color: #fff;
   	position: relative;
    box-shadow: 3px 4px 4px rgba(0, 0, 0, 0.3), 2px 2px 1px rgba(0, 0, 0, 0.1) inset;
	height:50px; 
	margin-bottom: 30px;
	cursor: pointer;
}


#menus .col-md-2:nth-child(5n-1) .set-menu-div{
    background-color: #1BA39C !important;
}

#menus .col-md-2:nth-child(5n-2) .set-menu-div{
    background-color: #f2764b !important;
}

#menus .col-md-2:nth-child(5n-3) .set-menu-div{
    background-color: #F3C200 !important;
}

#menus .col-md-2:nth-child(5n-4) .set-menu-div{
    background-color: #1BBC9B !important;
}

#menus .col-md-2:nth-child(5n) .set-menu-div{
    background-color: #4B77BE !important;
}

</style>
<%
	Map<String, Object> mm = MineAction.initHomeSetting();
	pageContext.setAttribute("cmenus", mm.get("cmenus"));
	pageContext.setAttribute("charts", mm.get("charts"));
%>
<script type="text/javascript" src="${pageContext.request.contextPath }/res/application/js/welcome.js"></script>
<div class="row">
		<div class="col-md-3">
			<!-- BEGIN WIDGET BLOG -->
			<div class="widget-blog rounded-3 text-center margin-bottom-30 clearfix notification"
				style="height:271px;  background-image: url(${pageContext.request.contextPath}/theme/layout/img/07.jpg);">
				<div class="widget-blog-heading text-uppercase">
					<h3 class="widget-blog-title"><img src="${pageContext.request.contextPath}/theme/plugins/layer/skin/default/loading-0.gif" /></h3>
					<span class="widget-blog-subtitle"></span>
				</div>
				<div class="widget-blog-content"></div>
				<a class="btn btn-danger text-uppercase" href="javascript:void(0)" style="display:none;">查看全文</a>
			</div>
			<!-- END WIDGET BLOG -->
		</div>
		
		<div class="col-md-9">

			<!-- BEGIN WIDGET TAB -->
			<div class="portlet light bordered categorys" cid="10000" limit="20">
				<a href="javascript:void(0)" class="action btn btn-sm">
					<i class="fa fa-ellipsis-h"></i>更多</a>
				</a>
				<ul class="nav nav-tabs">
				</ul>
				<div class="tab-content scroller" style="height: 190px;" data-always-visible="1" data-handle-color="#D7DCE2">
				</div>
			</div>
			<!-- END WIDGET TAB -->
		</div>
	</div>
	<%-- <div class="row">
		<div class="col-md-12">
			<!-- BEGIN WIDGET BLOG -->
			<div class="widget-blog rounded-3 text-center   clearfix notification"
				style="height: 120px; background-image: url(${pageContext.request.contextPath}/theme/layout/img/07.jpg);">
				<div class="widget-blog-heading text-uppercase">
					<h3 class="widget-blog-title"><img src="${pageContext.request.contextPath}/theme/plugins/layer/skin/default/loading-0.gif" /></h3>
				</div>
			</div>
			<!-- END WIDGET BLOG -->
		</div>
	</div> --%>
	<s:row id="menus">
		<c:forEach items="${cmenus }" var="m">
			<div class="col-md-2">
				<div class="set-menu-div " data-p-code="${m.code }" data-p-id="${m.fk_id }" data-p-title="${m.name }">
					<h4 class="set-menu-title">
					<i class="${m.icon }"></i>
					${m.name }</h4>
					<span class="set-menu-product">${m.system_product_name }</span>
				</div>
			</div>
		</c:forEach>
	</s:row>
	<s:row>
		<c:forEach items="${charts }" var="cha">
			<s:chart label="${cha.title }" cols="${cha.chart_width }" color="grey-silver" flag="${cha.flag }" tableid="${cha.tableid }" 
			autoload="true" pageid="${cha.pageid }" chart_height="${cha.chart_height }">
			</s:chart>
		</c:forEach>
	</s:row>
	<div class="row">
		<div class="col-md-12">
			<!-- BEGIN WIDGET TAB -->
			<div class="portlet light bordered categorys" cid="20000" limit="20">
				<a href="javascript:void(0)" class="action btn btn-sm">
					<i class="fa fa-ellipsis-h"></i>更多</a>
				</a>
				<ul class="nav nav-tabs">
				</ul>
				<div class="tab-content scroller" style="height: 290px;" data-always-visible="1" data-handle-color="#D7DCE2">
				</div>
			</div>
			<!-- END WIDGET TAB -->
		</div>
	</div>
 </s:page>
<script>
$(document).ready(function(){
	$.call("application.auth.checkpwd", {}, function(rtn) {
		if (rtn) {
			$.confirm("为了数据安全，请修改您的初始密码！",function(r){
				if(r){
					var url = cooperopcontextpath + "/w/application/mine/changepwd.html";
					top.$(".page-content-tabs").open_tabwindow("","密码修改", url);
				}
			});
		} 
	}, function(ems) {
		$.error(ems);
	});
	
	$(".set-menu-div").on("click", function(){
		var pageid = $(this).attr("data-p-code");
		var p1 = $(this).attr("data-p-id");
		var p2 = $(this).attr("data-p-title");
		var u = pageid.split(".").join("/");
		var url = cooperopcontextpath + "/w/" + u + ".html";
		top.$(".page-content-tabs").open_tabwindow(p1, p2, url);
	})
})
</script>
