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
<c:if test="${_request_method eq 'ajax'}">对不起，您要访问的地址不存在。</c:if>
<c:if test="${_request_method ne 'ajax'}">
<s:page title="页面不存在" disloggedin="true">
<link type="text/css" rel="stylesheet" href="${request.contextPath }/theme/layout/css/error.css">
<div class="error_box">
    <h1>@_@</h1>
    <h2>亲，你找的网页，估计是去火星了！</h2>
   	<p>您要访问的网页未找到或者功能已关闭使用。</p>
</div>
</s:page>
</c:if>