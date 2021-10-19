<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
	if ((!CommonFun.isNe(request.getContentType()) && request.getContentType().indexOf("application/x-www-form-urlencoded") > -1)
			|| "XMLHttpRequest".equals(request.getHeader("x-requested-with"))
			|| "ajax".equals(request.getParameter("request_method"))) {
		pageContext.setAttribute("_request_method", "ajax");
	}
%>
<c:if test="${_request_method eq 'ajax'}">对不起，您不具备地址的访问权限。</c:if>
<c:if test="${_request_method ne 'ajax'}">
<s:page title="无权访问" disloggedin="true">
<link type="text/css" rel="stylesheet" href="${request.contextPath }/theme/layout/css/error.css">
<div class="error_box">
    <h1>O_O</h1>
    <h2>亲，你想干什么？你好像没有权限哦！</h2>
   	<p>您不具备页面的访问权限。</p>
</div>
</s:page>
</c:if>