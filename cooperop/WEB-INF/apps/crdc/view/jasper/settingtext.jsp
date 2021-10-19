<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="">
	<s:row>
		<s:form id="setFrom">
			<s:toolbar>
				<s:button label="确定" onclick="save();"></s:button>
				<s:button label="取消" onclick="returnback();"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="赋值" name="textval" value="${pageParam.textval }"></s:textfield>
			</s:row>
			<c:if test="${pageParam.jtype eq 'textField'}">
				<s:row>
					<s:radio label="变量类型" name="fieldtype"
						value="${pageParam.fieldtype }">
						<s:option label="常量" value="parameter"></s:option>
						<s:option label="变量" value="field"></s:option>
					</s:radio>
				</s:row>
				<s:row>
					<s:radio label="字段类型" name="classtype"
						value="${pageParam.classtype }" cols="2">
						<s:option label="字符" value="java.lang.String"></s:option>
						<s:option label="数字" value="java.lang.Number"></s:option>
						<s:option label="日期" value="java.util.Date"></s:option>
						<s:option label="浮点型" value="java.lang.Float"></s:option>
						<s:option label="整数" value="java.lang.Integer"></s:option>
					</s:radio>
				</s:row>
			</c:if>
		</s:form>
	</s:row>
</s:page>
<script>
	function save() {
		var data = $("#setFrom").getData();
		$.closeModal(data);
	}
	function returnback() {
		$.closeModal(false);
	}
</script>
