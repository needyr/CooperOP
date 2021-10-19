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
<%
	String str = request.getParameter("attdata");
	Map<String, Object> map = CommonFun.json2Object(str, Map.class);
	Map<String, Object> attrs = (Map<String, Object>) map.get("attrs");
	List<Map<String, Object>> contents = (List<Map<String, Object>>) map.get("contents");
	pageContext.setAttribute("control", attrs);
	pageContext.setAttribute("contents", contents);
%>
<s:page ismodal="true" title="">
	<s:row>
		<s:form id="setFrom">
			<s:row>
				<s:textfield label="字段名" name="name" value="${control.name }"></s:textfield>
				<s:textfield label="中文说明" name="label" value="${control.label }"></s:textfield>
				<s:select name="cols" style="display:none;"
					value="${empty control.cols?'1':control.cols }">
					<s:option label="1" value="1"></s:option>
					<s:option label="2" value="2"></s:option>
					<s:option label="3" value="3"></s:option>
					<s:option label="4" value="4"></s:option>
				</s:select>
			</s:row>
			<s:row>
				<s:textfield label="选中值" name="ontext" value="${control.ontext }"></s:textfield>
				<s:textfield label="取消值" name="offtext" value="${control.offtext }"></s:textfield>
				<s:textfield label="默认值" name="onvalue"
					value="${control.onvalue }"></s:textfield>
				<s:textfield label="焦点去向字段" name="nextfocusfield"
					value="${control.nextfocusfield }"></s:textfield>
			</s:row>
			<s:row>
				<s:button label="确定" onclick="save();"></s:button>
				<s:button label="取消" onclick="returnback()"></s:button>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	function save() {
		var data = {};
		var attrs = $("#setFrom").getData();
		data.attrs = attrs;
		$.closeModal(data);
	}
	function returnback() {
		$.closeModal(false);
	}
	/* $(document).ready(function() {
		$(".icheck-colors").click(function(e) {
			var color = $(this).find(".active").attr("class").split(" ")[0];
			if (color == "active") {
				color = "default";
			}
			$("#color_").val(color);
		});
	}); */
</script>
