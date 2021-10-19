<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="检索方案">
<s:row>
	<s:form id="condi">
		<s:row>
			<s:radio label="所属产品" name="system_product_code" action="application.common.listProducts" cols="4">
				<s:option value="$[code]" label="$[name]"></s:option>
			</s:radio>
		</s:row>
		<s:row>
			<s:textfield cols="2" label="方案类型" name="fangalx" placeholder="输入方案类型进行查询" />
			<s:button label="查询" onclick="query()" color="blue"></s:button>
		</s:row>
	</s:form>
</s:row>
	<s:row>
		<s:table color="green" label="检索方案" autoload="false" id="mytable" select="single"
			action="crdc.scheme.dataretrieval.query" sort="true" fitwidth="true">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="引用"  
					onclick="add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="system_product_code" datatype="string" label="产品"
					sort="true" ></s:table.field>
				<s:table.field name="fangabh" datatype="string" label="方案编号"
					sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="fangalx" datatype="string" label="方案类型"
					sort="true"></s:table.field>
				<s:table.field name="fangamch" datatype="string" label="方案名称"
					sort="true"></s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
function query(){
	var d = $("#condi").getData();
	$("#mytable").params(d);
	$("#mytable").refresh();
}
function add(){
	$.closeModal($("#mytable").getSelected());
}
</script>