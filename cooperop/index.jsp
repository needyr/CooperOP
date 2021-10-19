<%@page import="cn.crtech.cooperop.bus.engine.window.HtmlWindow"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="cn.crtech.cooperop.bus.session.Session"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.io.File"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
    //Session mySession = Session.getSession(request, response);
    String include_page = "padIndex.jsp";
    out.println("<script type='text/javascript'>top.location = '" + include_page + "';</script>");
%>
