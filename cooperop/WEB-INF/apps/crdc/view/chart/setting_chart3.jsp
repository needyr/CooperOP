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
	List<Map<String, Object>> plot_bands = (List<Map<String, Object>>) attrs.get("plot_bands");
	pageContext.setAttribute("control", attrs);
	pageContext.setAttribute("contents", contents);
	pageContext.setAttribute("ap", plot_bands);
%>
<s:page ismodal="true" title="">
	<s:row>
		<s:form id="setFrom" fclass="portlet light bordered">
		<s:toolbar>
			<s:button label="保存" onclick="save();"></s:button>
		</s:toolbar>
			<s:row>
				<input type="hidden" id="system_product_code" value="${control.system_product_code}"/>
				<s:textfield label="图表标识" name="chart_flag" value="${control.chart_flag}"></s:textfield>
				<s:textfield label="中文说明" name="label" value="${control.label }"></s:textfield>
				<s:select label="色系" name="color" value="${empty control.color?'1':control.color }">
					<s:option label="通用组" value="1"></s:option>
					<s:option label="红绿灯" value="2"></s:option>
					<s:option label="彩虹组" value="3"></s:option>
				</s:select>
			</s:row>
			<s:row>
				<s:textarea label="统计sql" cols="4" name="yaxis_sql" placeholder="select 分类字段, x, y from table">${control.yaxis_sql }</s:textarea>
			</s:row>
			<s:row>
				<s:select label="宽度" name="cols"
					value="${empty control.cols?'1':control.cols }">
					<s:option label="1" value="1"></s:option>
					<s:option label="2" value="2"></s:option>
					<s:option label="3" value="3"></s:option>
					<s:option label="4" value="4"></s:option>
				</s:select>
				<s:textfield label="图表高度" name="chart_height" value="${control.chart_height }" min="200"/>
				<s:switch label="自动加载" name="autoload" onvalue="true" offvalue="false" value="${control.autoload }"></s:switch>
			</s:row>
			<s:row>
				<s:textfield label="横轴名称" name="x_name" value="${control.x_name }"></s:textfield>
				<s:textfield label="纵轴名称" name="y_name" value="${control.y_name }"></s:textfield>
				<s:textfield label="分类字段" name="g_name" value="${control.g_name }"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="横向标线名称" name="plotline_xname" value="${control.plotline_xname }"></s:textfield>
				<s:textfield label="横向标线值" name="plotline_xvalue" value="${control.plotline_xvalue }"></s:textfield>
				<s:textfield label="纵向标线名称" name="plotline_yname" value="${control.plotline_yname }"></s:textfield>
				<s:textfield label="纵向标线值" name="plotline_yvalue" value="${control.plotline_yvalue }"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>

	function save() {
		if (!$("form").valid()) {  
			return false;	
		}
		var data = {};
		var attrs = $("#setFrom").getData();
		attrs.chart_type = '4';
		attrs.flag = attrs.chart_flag;
		data.attrs = attrs;
		$.closeModal(data);
	}
	function returnback() {
		$.closeModal(false);
	}
</script>
