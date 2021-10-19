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
			<s:row>
			<input type="hidden" id="system_product_code" value="${control.system_product_code}"/>
				<s:textfield label="图表标识" name="chart_flag" value="${control.chart_flag}"></s:textfield>
				<s:textfield label="中文说明" name="label" value="${control.label }"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="统计表id" name="tableid" value="${control.tableid }"></s:textfield>
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
			</s:row>
			<s:row>
				<s:switch label="自动加载" name="autoload" onvalue="true" offvalue="false" value="${control.autoload }"></s:switch>
				<s:switch label="开放首页设置" name="welcome_func" onvalue="Y" offvalue="N" value="${control.welcome_func }"></s:switch>
			</s:row>
			<s:row>
				<s:textfield label="仪表最小值" name="min_num" value="${control.min_num }"></s:textfield>
				<s:textfield label="仪表最大值" name="max_num" value="${control.max_num }"></s:textfield>
				<s:textfield label="默认值" name="default_num" value="${control.default_num }"></s:textfield>
				<s:textfield label="数据显示单位" name="num_unit" value="${control.num_unit }" placeholder="如：万元"></s:textfield>
			</s:row>
			<s:row>
				<s:switch label="动态刷新" name="autorefresh" onvalue="Y" offvalue="N" value="${control.autorefresh }"></s:switch>
				<s:textfield label="刷新间隔/秒" name="refresh_time" value="${control.refresh_time }"></s:textfield>
				<s:textarea label="刷新语句" cols="2" name="xaxis_sql">${control.xaxis_sql }</s:textarea>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="刻度设置" id="table_" select="multi">
			<s:toolbar>
				<s:button label="新增" onclick="addr()"></s:button>
				<s:button label="删除" onclick="deleter()"></s:button>
				<s:button label="保存" onclick="save()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="from_num" label="刻度开始值" datatype="template">
					<s:textfield name="from_num" value="$[from_num]"/>
				</s:table.field>
				<s:table.field name="to_num" label="刻度结束值" datatype="template">
					<s:textfield name="to_num" type="text" value="$[to_num]"/>
				</s:table.field>
				<s:table.field name="color" label="颜色设置" width="120" datatype="template">
					<s:select name="color" type="text" value="$[color]">
						<s:option label="绿" value="#1BBC9B"></s:option>
						<s:option label="黄" value="#F3C200"></s:option>
						<s:option label="红" value="#f2764b"></s:option>
						<s:option label="紫" value="#942a8c"></s:option>
						<s:option label="蓝" value="#3e67ab"></s:option>
					</s:select>
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
						<td>${p.from_num }</td>
						<td>${p.to_num }</td>
						<td>${p.color }</td>
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
})
	function save() {
		if (!$("form").valid()) {  
			return false;	
		}
		var data = {};
		var attrs = $("#setFrom").getData();
		/* if(attrs.xaxis_sql && (attrs.xaxis_sql.indexOf(">") > -1 || attrs.xaxis_sql.indexOf("<") > -1)){
			$.message("语句不能包含‘<’或者‘>’符号！");
			return false;	
		} */
		attrs.chart_type = '3';
		attrs.flag = attrs.chart_flag;
		data.attrs = attrs;
		
		data.attrs.plot_bands = [];
		var trdata = $("#table_ > tbody > tr");
		trdata.each(function(index) {
			var tdd = $(this).getData();
			data.attrs.plot_bands.push(tdd);
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
