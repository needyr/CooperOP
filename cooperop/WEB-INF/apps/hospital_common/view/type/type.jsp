<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="客户端类型定义详情">
	<s:row>
		<s:form label="${name}">
			<s:row>
				<s:textfield islabel="true" label="编号" value="${code}"></s:textfield>
				<s:textfield islabel="true" label="名称" cols="3" value="${name}"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea label="描述" cols="4" readonly="true" rows="2" autosize="false">${description}</s:textarea>
			</s:row>
			<s:row>
				<s:textarea label="连接定义模板" cols="4" readonly="true" rows="3" autosize="false">${definition}</s:textarea>
			</s:row>
			<s:row>
				<s:textarea label="验权参数模板" cols="4" readonly="true" rows="2" autosize="false">${initparams}</s:textarea>
			</s:row>
			<s:row>
				<s:textarea label="备注" cols="4" readonly="true" rows="10" autosize="false">${remark}</s:textarea>
			</s:row>
		</s:form>
	</s:row>
</s:page>