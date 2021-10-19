<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="测试" disloggedin="true" dispermission="true" ismodal="true">
	<s:row style="${not empty fn:trim(scheme.filterflds) ? '' : 'display:none;'}">
		<s:form border="0" id="conditions">
			<s:row>
				<c:forEach items="${scheme.filterfields}" var="field">
					<s:textfield label="${field.chnname}" name="flt_${field.fdname}"></s:textfield>
				</c:forEach>
				<s:button label="查询" icon="fa fa-search" color="green" onclick="query()"></s:button>
				<c:forEach items="${pageParam}" var="p">
					<input type="hidden" name="${p.key}" value="${p.value}"/>
				</c:forEach>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="optionlist" action="application.scheme.executeQuerySchemeList" select="single" autoload="false" fitwidth="false" height="300" sort="true">
			<s:toolbar>
				<s:button icon="fa fa-check" label="确定" onclick="confirm();"></s:button>
				<s:button icon="fa fa-ban" label="取消" onclick="$.closeModal();"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="fanganbh" label="方案编号"  sort="true" app_field="content_field"></s:table.field>
				<s:table.field name="fangamch" label="方案名称"  sort="true" app_field="title_field"></s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
		query();
	});
	function query() {
		var d = $("#conditions").getData();
		d.fangalx= d.djlx;
		$("#optionlist").params(d);
		$("#optionlist").refresh();
	}
	function confirm() {
		if ($("#optionlist").getSelected().length == 0) {
			$.warning("请选择您所需要的选项。");
			return;
		}
		$.closeModal($("#optionlist").getSelected());
	}
</script>