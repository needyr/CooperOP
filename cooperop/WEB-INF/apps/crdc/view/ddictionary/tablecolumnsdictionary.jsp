<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="字段信息">
	<s:row>
		<s:table label="字段信息" autoload="false" id="overid" action="crdc.ddictionary.queryColumns" sort="true" fitwidth="true">
			<s:table.fields>
				<s:table.field name="colid" datatype="string" label="字段序号" sort="true" ></s:table.field>
				<s:table.field name="columns" datatype="string" label="字段名" sort="true" ></s:table.field>
				<s:table.field name="type_explain" datatype="string" label="类型" sort="true" ></s:table.field>
				<s:table.field name="columns_type" datatype="string" label="注释" sort="true" ></s:table.field>
				<s:table.field name="remark" datatype="string" label="备注" sort="true" ></s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
		query();
	});
	
	function query(){
		var d={databasename:'${pageParam.databasename}'};
		d.tablename='${pageParam.tablename}'
		console.log(d);
		$("#overid").params(d);
		$("#overid").refresh();
	}
</script>