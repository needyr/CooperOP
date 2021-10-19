<%@page import="java.util.ArrayList"%>
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
<s:page ismodal="true" title="">
	<%
		String gj = request.getParameter("gj");
			Map<String, Object> map = CommonFun.json2Object(gj, Map.class);
			List<Map<String, Object>> gjs = (List<Map<String, Object>>) map.get("attr_gj");
			pageContext.setAttribute("gjs", gjs);
	%>
	<s:row>
		<s:table id="table_" label="高级设置列表" select="multi">
			<s:toolbar>
				<s:button label="新增" onclick="addr()"></s:button>
				<s:button label="删除" onclick="deleter()"></s:button>
				<s:button label="保存" onclick="save()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="shezhibs" label="标识"></s:table.field>
				<s:table.field name="shezmc" label="设置名称"></s:table.field>
				<s:table.field name="shezlx" label="类型"></s:table.field>
				<s:table.field name="quesz" label="缺省值"></s:table.field>
				<s:table.field name="shurz" label="输入值"></s:table.field>
				<s:table.field name="shuom" label="说明" width="300"></s:table.field>
			</s:table.fields>
			<s:table.data>
				<c:if test="${empty gjs }">
					<tr id="childs">
						<td><s:textfield name="shezhibs" value="" /></td>
						<td><s:textfield name="shezmc" value="" /></td>
						<td><s:select name="shezlx" value="">
								<s:option label="字符型" value="字符型" />
								<s:option label="逻辑型" value="逻辑型" />
								<s:option label="枚举型" value="枚举型" />
								<s:option label="整数型" value="整数型" />
							</s:select></td>
						<td><s:textfield name="quesz" value="" /></td>
						<td><s:textfield name="shurz" value="" /></td>
						<td><s:textarea name="shuom" value="" autosize="false"/></td>
					</tr>
				</c:if>
				<c:if test="${not empty gjs }">
					<c:forEach items="${gjs }" var="gj" varStatus="a">
						<tr id="childs">
							<td><s:textfield name="shezhibs" value="${gj.shezhibs }" /></td>
							<td><s:textfield name="shezmc" value="${gj.shezmc }" /></td>
							<td><s:select name="shezlx" value="${gj.shezlx }">
									<s:option label="字符型" value="字符型" />
									<s:option label="逻辑型" value="逻辑型" />
									<s:option label="枚举型" value="枚举型" />
									<s:option label="整数型" value="整数型" />
								</s:select></td>
							<td><s:textfield name="quesz" value="${gj.quesz }" /></td>
							<td><s:textfield name="shurz" value="${gj.shurz }" /></td>
							<td><s:textarea name="shuom" value="${gj.shuom }" autosize="false"/></td>
						</tr>
					</c:forEach>
				</c:if>
			</s:table.data>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
		var d = [];
		d.push('${pageParam.attr_gj}');
	})
	function save() {
		var data = {};
		data.attr_gj = [];
		var trdata = $("#table_ > tbody > tr");
		trdata.each(function(index) {
			var tdd = $(this).getData();
			if(!tdd.shezhibs){
				return false;
			}
			data.attr_gj.push(tdd);
		});
		$.closeModal(data);
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
		trnew.find("textarea").val("");
		$("#table_ > tbody").append(trnew);
	}
</script>
