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
			<s:button onclick="save();" label="确定"></s:button>
			<s:button onclick="returnback();" label="取消"></s:button>
		</s:toolbar>
		<s:row>
			<s:textfield label="报表名字" name="name" value="${pageParam.name }"></s:textfield>
			<s:textfield label="报表高度" name="pageHeight" value="${pageParam.pageHeight }"></s:textfield>
			<s:textfield label="报表宽度" name="pageWidth" value="${pageParam.pageWidth }"></s:textfield>
		</s:row>
		<s:row>
			<s:textfield label="页面上边空距" name="topMargin" value="${pageParam.topMargin }"></s:textfield>
			<s:textfield label="页面右边空距" name="rightMargin" value="${pageParam.rightMargin }"></s:textfield>
			<s:textfield label="页面下边空距" name="bottomMargin" value="${pageParam.bottomMargin }"></s:textfield>
			<s:textfield label="页面左边空距" name="leftMargin" value="${pageParam.leftMargin }"></s:textfield>
		</s:row>
	</s:form>
</s:row>
</s:page>
<script>
	function save() {
		var data = $("#setFrom").getData();
		if(data.pageHeight){
			data.pageHeight = data.pageHeight +"px";
		}
		if(data.pageWidth){
			data.pageWidth = data.pageWidth +"px";
		}
		if(data.topMargin){
			data.topMargin = data.topMargin +"px";
		}
		if(data.rightMargin){
			data.rightMargin = data.rightMargin +"px";
		}
		if(data.bottomMargin){
			data.bottomMargin = data.bottomMargin +"px";
		}
		if(data.leftMargin){
			data.leftMargin = data.leftMargin +"px";
		}
		$.closeModal(data);
	}
	function returnback() {
		$.closeModal(false);
	}
</script>
