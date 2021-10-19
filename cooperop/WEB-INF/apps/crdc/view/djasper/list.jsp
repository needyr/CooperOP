<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="打印方案">
<s:row>
	<s:form id="condi">
		<s:row>
			<s:radio label="所属产品" name="system_product_code" action="application.common.listProducts" cols="4">
				<s:option value="$[code]" label="$[name]"></s:option>
			</s:radio>
		</s:row>
		<s:row>
			<s:textfield cols="2" label="关键字" name="keyword" placeholder="输入方案编号，方案名称，方案类型进行查询" />
			<s:button label="查询" onclick="query()" color="blue"></s:button>
		</s:row>
	</s:form>
</s:row>
	<s:row>
		<s:table color="green" label="打印方案" autoload="true" id="mytable"
			action="crdc.djasper.query" sort="true" fitwidth="true">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="新建方案" size="btn-sm btn-default"
					action="crdc.workflow.designer" onclick="add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="system_product_code" label="产品"
					sort="true" ></s:table.field>
				<s:table.field name="no" datatype="string" label="方案编号"
					sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="view_id" datatype="string" label="方案类型"
					sort="true"></s:table.field>
				<s:table.field name="description" datatype="string" label="方案名称"
					sort="true"></s:table.field>
				<s:table.field name="oper" datatype="template" label="操作"
					align="center">
						<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="modify($[id])">修改</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="deleteFlow($[id])">删除</a>
					
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
$(document).ready(function (){
	query();
})
function query(){
		var d = $("#condi").getData();
		$("#mytable").params(d);
		$("#mytable").refresh();
	}
	function add(){
		$.modal("add.html", "新增", {
			width : '80%',
			height : '80%',
			callback : function(rtn) {
				$("#mytable").refresh();
		    }
		});
	}
	function modify(id) {
		$.modal("add.html", "修改方案", {
			id :id,
			width : '80%',
			height : '80%',
			callback : function(rtn) {
				$("#mytable").refresh();
			}
		});
	}
	function deleteFlow(id) {
		$.confirm("是否确认删除该方案？删除后将无法恢复！", function(c) {
			if (c) {
				$.call("crdc.djasper.delete", {
					id: id, 
				}, function(rtn) {
					if(rtn){
						$("#mytable").refresh();
					}
				});
			}
		});
	}
</script>