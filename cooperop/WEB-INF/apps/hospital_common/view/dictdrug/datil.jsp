<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑系统配置信息" >
	<s:row>
		<s:form id="form" border="0">
			<s:row>
				<s:textfield label="药品名" name="drug_name" value="${drug_name}" cols="4" islabel="true"></s:textfield>
				<s:textfield label="批准文号" name="pizhwh" value="${pizhwh}" cols="4" islabel="true"></s:textfield>
				<s:textfield label="规格" name="druggg" value="${druggg}" cols="4" islabel="true"></s:textfield>
				<s:textfield label="剂型" name="jixing" value="${jixing}" cols="4" islabel="true"></s:textfield>
				<s:textfield label="生产厂家" name="shengccj" value="${shengccj}" cols="4" islabel="true"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
