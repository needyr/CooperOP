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
%>
<s:page ismodal="true" title="">
	<s:row>
		<s:form id="setFrom" fclass="portlet light bordered">
			<s:toolbar>
				<s:button label="保存" onclick="save()"></s:button>
			</s:toolbar>
			<s:row>
			<input type="hidden" id="system_product_code" value="${control.system_product_code}"/>
				<s:textfield label="图表标识" name="chart_flag" value="${control.chart_flag}"></s:textfield>
				<s:textfield label="中文说明" cols="2" name="label" value="${control.label }"></s:textfield>
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
			</s:row>
			<s:row>
				<s:textfield label="显示链接名称" name="sub_label" type="text" value="${control.sub_label }"></s:textfield>
				<s:autocomplete cols="2" label="显示链接报表" name="sub_label_href" action="crdc.designer.query" value="${control.sub_label_href}" editable="true">
					<s:option value="$[system_product_code].$[type].$[flag].$[id]" label="$[system_product_code].$[type].$[flag].$[id]">$[system_product_code].$[type].$[flag].$[id]($[description])</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:textfield label="head模板" cols="2" name="span_head" value="${control.span_head }"></s:textfield>
				<s:textfield label="content模板" cols="2" name="span_content" value="${control.span_content }"></s:textfield>
				<s:textfield label="footer模板" cols="2" name="span_footer" value="${control.span_footer }"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea label="统计sql" cols="4" name="yaxis_sql">${control.yaxis_sql }</s:textarea>
			</s:row>
			<s:row>
				<s:switch label="开放首页设置" name="welcome_func" onvalue="Y" offvalue="N" value="${control.welcome_func }"></s:switch>
				<s:switch label="自动加载" name="autoload" onvalue="true" offvalue="false" value="${empty control.autoload ?'true':control.autoload }"></s:switch>
				<s:switch label="动态刷新" name="autorefresh" onvalue="Y" offvalue="N" value="${(empty control.autorefresh)?'Y':control.autorefresh}"></s:switch>
				<s:textfield label="刷新间隔/秒" name="refresh_time" value="${control.refresh_time }"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function(){
		var system_product_code = $("#system_product_code").val();
		$("[name='sub_label_href']").params({"system_product_code":system_product_code,type: 'chart'});
	});
	
	function save() {
		if (!$("form").valid()) {  
			return false;	
		}
		var data = {};
		var attrs = $("#setFrom").getData();
		/* if(attrs.yaxis_sql && (attrs.yaxis_sql.indexOf(">") > -1 || attrs.yaxis_sql.indexOf("<") > -1)){
			$.message("语句不能包含‘<’或者‘>’符号！");
			return false;	
		} */
		attrs.chart_type = '2';
		attrs.flag = attrs.chart_flag;
		data.attrs = attrs;
		
		$.closeModal(data);
	}
	function returnback() {
		$.closeModal(false);
	}
</script>
