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
	List<Map<String, Object>> yaxis = (List<Map<String, Object>>) attrs.get("yaxis");
	pageContext.setAttribute("control", attrs);
	pageContext.setAttribute("contents", contents);
	pageContext.setAttribute("ap", yaxis);
%>
<s:page ismodal="true" title="">
	<s:row>
		<s:form id="setFrom" fclass="portlet light bordered">
			<s:row>
				<input type="hidden" id="system_product_code" value="${control.system_product_code}"/>
				<s:textfield label="图表标识" name="chart_flag" value="${control.chart_flag}"></s:textfield>
				<s:textfield label="中文说明" name="label" value="${control.label }"></s:textfield>
				<s:textfield label="统计表id" name="tableid" value="${control.tableid }"></s:textfield>
				<s:select label="色系" name="color" value="${empty control.color?'1':control.color }">
					<s:option label="通用组" value="1"></s:option>
					<s:option label="红绿灯" value="2"></s:option>
					<s:option label="彩虹组" value="3"></s:option>
				</s:select>
			</s:row>
			<s:row>
				<s:textarea label="统计sql" cols="4" name="yaxis_sql">${control.yaxis_sql }</s:textarea>
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
				<s:autocomplete label="下钻报表" name="drill_chart" action="crdc.designer.query" value="${control.drill_chart}" editable="true">
					<s:option value="$[system_product_code].$[type].$[flag].$[id]" label="$[system_product_code].$[type].$[flag].$[id]">$[system_product_code].$[type].$[flag].$[id]($[description])</s:option>
				</s:autocomplete>
				<s:textfield label="下钻页面名称" name="drill_chart_name" value="${control.drill_chart_name }" placeholder="下钻页面:xaxis"></s:textfield>
			</s:row>
			<s:row>
				<s:switch label="自动加载" name="autoload" onvalue="true" offvalue="false" value="${control.autoload }"></s:switch>
				<s:switch label="开放首页设置" name="welcome_func" onvalue="Y" offvalue="N" value="${control.welcome_func }"></s:switch>
				<s:switch label="开启柱状堆叠" name="stacking" onvalue="Y" offvalue="N" value="${control.stacking }"></s:switch>
				<s:textfield label="分类字段" name="group_field" value="${control.group_field }" placeholder="请输入分类统计字段"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="横坐标字段" name="g_name" value="${control.g_name }" placeholder="请输入横坐标显示字段"></s:textfield>
				<s:textfield label="显示链接名称" name="sub_label" type="text" value="${control.sub_label }"></s:textfield>
				<s:autocomplete cols="2" label="显示链接报表" name="sub_label_href" action="crdc.designer.query" value="${control.sub_label_href}" editable="true">
					<s:option value="$[system_product_code].$[type].$[flag].$[id]" label="$[system_product_code].$[type].$[flag].$[id]">$[system_product_code].$[type].$[flag].$[id]($[description])</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:textarea label="横坐标initsql" cols="3" name="xaxis_sql">${control.xaxis_sql }</s:textarea>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="统计字段" id="table_" select="multi">
			<s:toolbar>
				<s:button label="新增" onclick="addr()"></s:button>
				<s:button label="删除" onclick="deleter()"></s:button>
				<s:button label="保存" onclick="save()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="name" label="字段名称" datatype="template">
					<s:textfield dblaction="fieldsmodify" name="name" value="$[name]"/>
				</s:table.field>
				<s:table.field name="label" label="显示标题" datatype="template">
					<s:textfield name="label" type="text" value="$[label]"/>
				</s:table.field>
				<s:table.field name="chart_type" label="展示图形" width="120" datatype="template">
					<s:select name="chart_type" type="text" value="$[chart_type]">
						<s:option label="曲线图" value="spline"></s:option>
						<s:option label="饼状图" value="pie"></s:option>
						<s:option label="柱状图" value="column"></s:option>
						<s:option label="条状图" value="bar"></s:option>
						<s:option label="雷达图" value="spider"></s:option>
						<s:option label="增速图" value="columnrange"></s:option><%-- 
						<s:option label="仪表图(敬请期待)" value="speed"></s:option> --%>
						<s:option label="环形图" value="pie1"></s:option>
					</s:select>
				</s:table.field>
				<s:table.field name="stack" label="柱状堆叠分类" datatype="template">
					<s:textfield name="stack" type="text" value="$[stack]"/>
				</s:table.field>
			</s:table.fields>
			<s:table.data>
				<c:if test="${empty ap }">
					<tr>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</c:if>
				<c:if test="${not empty ap }">
				<c:forEach items="${ap }" var="p">
					<tr>
						<td>${p.name }</td>
						<td>${p.label }</td>
						<td>${p.chart_type }</td>
						<td>${p.stack }</td>
					</tr>
				</c:forEach>
				</c:if>
			</s:table.data>
		</s:table>
	</s:row>
</s:page>
<script>
$(document).ready(function(){
	var system_product_code = $("#system_product_code").val();
	$("[name='drill_chart']").params({"system_product_code":system_product_code,type: 'chart'});
	$("[name='sub_label_href']").params({"system_product_code":system_product_code,type: 'chart'});
})
	function save() {
		if (!$("form").valid()) {  
			return false;	
		}
		var data = {};
		var attrs = $("#setFrom").getData();
		/* if(attrs.yaxis_sql && (attrs.yaxis_sql.indexOf(">") > -1 || attrs.yaxis_sql.indexOf("<") > -1)){
			$.message("统计语句不能包含‘<’或者‘>’符号！");
			return false;	
		}
		if(attrs.xaxis_sql && (attrs.xaxis_sql.indexOf(">") > -1 || attrs.xaxis_sql.indexOf("<") > -1)){
			$.message("横坐标语句不能包含‘<’或者‘>’符号！");
			return false;	
		} */
		attrs.chart_type = '1';
		attrs.flag = attrs.chart_flag;
		data.attrs = attrs;
		
		data.attrs.yaxis = [];
		var trdata = $("#table_ > tbody > tr");
		trdata.each(function(index) {
			var tdd = $(this).getData();
			if(!tdd.name){
				return false;
			}
			if(!tdd.chart_type){
				return false;
			}
			data.attrs.yaxis.push(tdd);
		});
		$.closeModal(data);
	}
	function returnback() {
		$.closeModal(false);
	}
	function deleter() {
		$("#table_ > tbody").find("input[type=checkbox]").each(function() {
			if (this.checked) {
				$(this).parents("tr").remove();
			}
		});
	}
	function addr() {
		var tr = $("#table_ > tbody > tr");
		var trnew = $(tr[0]).clone();
		trnew.find("input").val("");
		$("#table_ > tbody").append(trnew);
	}
</script>
