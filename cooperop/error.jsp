<%@page import="cn.crtech.cooperop.bus.util.LocalThreadMap"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@ page isErrorPage="true"%>
<%
	LocalThreadMap.clear();
	if ((!CommonFun.isNe(request.getContentType()) && request.getContentType().indexOf("application/x-www-form-urlencoded") > -1)
			|| "XMLHttpRequest".equals(request.getHeader("x-requested-with"))
			|| "ajax".equals(request.getParameter("request_method"))) {
		pageContext.setAttribute("_request_method", "ajax");
	}
	String emsg = request.getAttribute("javax.servlet.error.message") + "";
	if (CommonFun.isNe(emsg)) {
		emsg = request.getAttribute("_error_message") + "";
	}
	pageContext.setAttribute("emsg", URLDecoder.decode(emsg, "UTF-8"));
	Object e = request.getAttribute("_e");
    pageContext.setAttribute("_eid", request.getAttribute("_eid"));
%>
<c:if test="${_request_method eq 'ajax' }">${emsg}</c:if>
<c:if test="${_request_method ne 'ajax' }">
 <s:page title="系统异常" disloggedin="true">
<link type="text/css" rel="stylesheet" href="${request.contextPath }/theme/layout/css/error.css">
   <div class="error_box">
    <h1>::>_<::</h1>
    <h2>亲，对不起，此功能崩溃了，请与系统管理员联系！</h2>
    <p>原因：${emsg }。</p>
    <c:if test="${not empty _eid}">
     <p><a href='e?page=base.systemexception.details&id=${_eid }' target='_blank' style='font-size:10px;margin-left:10px;'>点击查看</a></p>
    </c:if>
   </div>
  </s:page>
</c:if>