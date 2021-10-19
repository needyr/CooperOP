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
		List<Map<String, Object>> ap = (List<Map<String, Object>>) map.get("attr_p");
		pageContext.setAttribute("ap", ap);
	%>
<s:row>
	<s:table label="自定义单据存储表" id="table_" select="multi">
		<s:toolbar>
			<s:button label="新增" onclick="addr()"></s:button>
			<s:button label="删除" onclick="deleter()"></s:button>
			<s:button label="保存" onclick="save()"></s:button>
		</s:toolbar>
		<s:table.fields>
			<s:table.field name="lx" label="类型标识" datatype="template">
				<s:select name="lx" value="$[lx]">
					<s:option label="单据汇总表" value="H"></s:option>
					<s:option label="单据明细表" value="M"></s:option>
					<s:option label="存储过程名" value="P"></s:option>
					<s:option label="调用函数名" value="D"></s:option>
					<s:option label="检验函数名" value="J"></s:option>
				</s:select>
			</s:table.field>
			<s:table.field name="tbname" label="西文表名" datatype="template">
				<s:textfield name="tbname" value="$[tbname]"></s:textfield>
			</s:table.field>
			<!-- 这个备注是用来输入查询标间跟在where后面的 -->
			<%-- <s:table.field name="beizhu" label="备注" datatype="template">
				<s:textfield name="beizhu" value="$[beizhu]"></s:textfield>
			</s:table.field> --%>
			<s:table.field name="tableid" label="明显表id（类型为‘单据明细表’的需要填写）" datatype="template">
				<s:textfield name="tableid" value="$[tableid]"></s:textfield>
			</s:table.field>
		</s:table.fields>
		<s:table.data>
			<c:if test="${empty ap }">
				<tr>
					<td></td>
					<td></td>
<!-- 					<td></td> -->
					<td></td>
				</tr>
			</c:if>
			<c:if test="${not empty ap }">
			<c:forEach items="${ap }" var="p">
				<tr>
					<td>${p.lx }</td>
					<td>${p.tbname }</td>
<%-- 					<td>${p.beizhu }</td> --%>
					<td>${p.tableid }</td>
				</tr>
			</c:forEach>
			</c:if>
		</s:table.data>
	</s:table>
</s:row>
</s:page>
<script type="text/javascript">
function save(){
	var data = {};
	data.attr_p = [];
	var trdata = $("#table_ > tbody > tr");
	trdata.each(function(index) {
		var tdd = $(this).getData();
		if(!tdd.tbname){
			return false;
		}
		data.attr_p.push(tdd);
	});
	$.closeModal(data);
}
function returnback(){
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
