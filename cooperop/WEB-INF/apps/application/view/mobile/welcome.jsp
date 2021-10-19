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
<link href="${request.contextPath }/res/application/css/welcome.css?iml=Y" type="text/css" rel="stylesheet" />
<style type="text/css">
.set-menu{
	width:50%;
	float:left;
	border-right: 1px solid #eee;
	border-bottom: 1px solid #eee;
}
.set-menu-div .set-menu-title{
    /* text-align: center; */
    position: relative;
    display: block;
    top: 10px;
    width: 75%;
    float: right;
    overflow: hidden;
    white-space: nowrap;
text-overflow: ellipsis;
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
   	position: relative;
	height:50px; 
	margin-bottom: 10px;
}

.set-menu-div i{
    font-size: 24px !important;
    display: block;
    padding: 15px 0 0 8px;
    width: 25%;
    height: 100%;
    float: left;
    position: relative;
    top: 7px;
}


#menus .set-menu:nth-child(5n-1) .set-menu-div i{
    color: #1BA39C !important;
}

#menus .set-menu:nth-child(5n-2) .set-menu-div i{
    color: #f2764b !important;
}

#menus .set-menu:nth-child(5n-3) .set-menu-div i{
    color: #F3C200 !important;
}

#menus .set-menu:nth-child(5n-4) .set-menu-div i{
    color: #1BBC9B !important;
}

#menus .set-menu:nth-child(5n) .set-menu-div i{
    color: #4B77BE !important;
}

</style>
<%
Map<String, Object> mm = MineAction.initHomeSetting();
pageContext.setAttribute("cmenus", mm.get("cmenus"));
pageContext.setAttribute("charts", mm.get("charts"));
%>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/application/js/welcome.js?iml=Y"></script>
	<div class="row">
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
	</div>
	<s:row id="menus">
		<c:forEach items="${cmenus }" var="m">
			<div class="set-menu">
				<div class="set-menu-div " data-p-code="${m.code }" data-p-id="${m.fk_id }" data-p-title="${m.name }">
					<i class="${empty m.icon?'icon-briefcase':m.icon }"></i>
					<h4 class="set-menu-title">
					${m.name }</h4>
					<span class="set-menu-product">${m.system_product_name }</span>
				</div>
			</div>
		</c:forEach>
		<div class="set-menu">
			<div class="set-menu-div " data-p-code="re">
				<i class="fa fa-refresh"></i>
				<h4 class="set-menu-title">重新加载</h4>
				<span class="set-menu-product">系统设置</span>
			</div>
		</div>
	</s:row>
	<c:forEach items="${charts }" var="cha">
		<s:row>
			<s:chart label="${cha.title }" cols="4" color="grey-silver" flag="${cha.flag }" tableid="${cha.tableid }" 
			autoload="true" pageid="${cha.pageid }" chart_height="${cha.chart_height }">
			</s:chart>
		</s:row>
	</c:forEach>
	
 </s:page>
<script>
$(document).ready(function(){
	$(".set-menu-div").on("click", function(){
		var pageid = $(this).attr("data-p-code");
		if(pageid == 're'){
			location.reload();
		}else{
			var p1 = $(this).attr("data-p-id");
			var p2 = $(this).attr("data-p-title");
			var u = pageid.split(".").join("/");
			var url = cooperopcontextpath + "/w/" + u + ".html";
			$.modal(url,p2,{
				callback : function() {
			    }
			});
		}
	});
})
</script>
