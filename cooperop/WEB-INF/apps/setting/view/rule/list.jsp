<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="权限列表">
	<s:row>
		<s:form id="conForm">
			<s:row>
				<s:radio label="产品" name="system_product_code" cols="2">
					<s:option label="Erp" value="erp"></s:option>
					<s:option label="OA" value="oa"></s:option>
				</s:radio>
				<s:textfield label="权限名称" name="name" tips="权限名称"></s:textfield>
				<s:button label="查询" onclick="query()" color="blue"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="角色列表" autoload="true" id="ruletable"
			action="setting.rule.query" sort="true" fitwidth="true">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="新增字段" size="btn-sm btn-default"
					onclick="add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="system_product_code" datatype="string" label="所属产品"
					sort="true" ></s:table.field>
				<s:table.field name="name" datatype="string" label="权限名称"
					sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="description" datatype="string" label="描述"
					sort="true"></s:table.field>
				<s:table.field name="oper" datatype="template" label="操作" align="left" width="200">
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="modifydj('$[id]')">修改</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="deletedj('$[id]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
function query(){
		$("#ruletable").params($("#conForm").getData());
		$("#ruletable").refresh();
	}
	function add(){
		$.modal("add.html", "新增", {
			width : '90%',
			height : '90%',
			callback : function(rtn) {
				$("#ruletable").refresh();
		    }
		});
	}
</script>