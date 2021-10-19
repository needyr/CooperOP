<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="数据显示" ismodal="true">

	<s:row>
		<s:form label="${pageParam.name }">
			<s:toolbar>
				<s:button label="关闭" icon="fa fa-close" onclick="$.closeModal(true);"></s:button>
			</s:toolbar>
			<s:row style="margin-left:5px;">
				<s:textarea cols="4">${pageParam.value }</s:textarea>
			</s:row>
		</s:form>
	</s:row>

</s:page>