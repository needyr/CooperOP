<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="通用方法列表">
	<s:row>
		<s:form id="conForm">
			<s:row>
				<s:textfield label="关键字查询" name="filter" tips="输入方法代码、名称查询"></s:textfield>
				<s:button label="查询" onclick="query()" color="blue"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="通用方法列表" autoload="true" id="funclist"
			action="crdc.funclist.query" sort="true" fitwidth="true">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="新增"
					onclick="add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="functionname" datatype="string" label="方法代码"
					sort="true" ></s:table.field>
				<s:table.field name="functitle" datatype="string" label="方法名称"
					sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="beizhu" datatype="string" label="备注"
					sort="true"></s:table.field>
				<s:table.field name="oper" datatype="template" label="操作" align="left" width="200">
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="modify('$[functionname]')">修改</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="deletefield('$[functionname]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	function query(){
		$("#funclist").params($("#conForm").getData());
		$("#funclist").refresh();
	}
	function add(){
		$.modal("add.html", "新增", {
			width : '80%',
			height : '60%',
			callback : function(rtn) {
				$("#funclist").refresh();
		    }
		});
	}
	function modify(functionname){
		$.modal("add.html", "修改", {
			width : '80%',
			height : '60%',
			functionname : functionname,
			callback : function(rtn) {
				$("#funclist").refresh();
		    }
		});
	}
	function deletefield(functionname){
		$.call("crdc.funclist.delete", {"functionname" : functionname}, function(rtn) {
			if (rtn.result == 'success') {
				$("#funclist").refresh();
			}
		},null,{async: false});
	}
</script>