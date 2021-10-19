<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="测试" disloggedin="true" dispermission="true" ismodal="true">
	<s:row>
		<s:form border="0" id="conditions">
			<s:row>
				<s:textfield label="字段名称" name="fdname_" value="${pageParam.queryvalue}"></s:textfield>
				<s:textfield label="字段中文名称" name="chnname_"></s:textfield>
				<s:button label="查询" icon="fa fa-search" color="green" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="optionlist" action="crdc.scheme.fields.list" select="single" autoload="false" fitwidth="false" height="300" sort="true">
			<s:toolbar>
				<s:button icon="fa fa-check" label="确定" onclick="confirm();"></s:button>
				<s:button icon="fa fa-ban" label="取消" onclick="$.closeModal();"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="name" label="字段名称"  sort="true"></s:table.field>
				<s:table.field name="label" label="字段中文名"  sort="true"></s:table.field>
				<s:table.field name="fdtype" label="字段类型"  sort="true"></s:table.field>
				<s:table.field name="size" label="字段长度"  sort="true"></s:table.field>
				<s:table.field name="digits" label="字段精度"  sort="true"></s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
		$("#optionlist").params({fdname_ : '${pageParam.queryvalue}'});
		$("#optionlist").refresh();
	});
	function query() {
		$("#optionlist").params($("#conditions").getData());
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