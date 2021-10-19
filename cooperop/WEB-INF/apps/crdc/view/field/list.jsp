<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="字段列表">
	<s:row>
		<s:form id="conForm">
			<s:row>
				<s:textfield label="关键字查询" name="filter" tips="输入字段西文、中文名查询"></s:textfield>
				<s:button label="查询" onclick="query()" color="blue"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="字段列表" autoload="true" id="fieldstable"
			action="crdc.field.query" sort="true" fitwidth="true">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="重新加载" size="btn-sm btn-default"
					onclick="reloadfield()"></s:button>
				<s:button icon="fa fa-file-o" label="新增字段" size="btn-sm btn-default"
					onclick="add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="fdname" datatype="string" label="西文字段名"
					sort="true" ></s:table.field>
				<s:table.field name="chnname" datatype="string" label="中文名称"
					sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="fdtype" datatype="string" label="字段类型"
					sort="true"></s:table.field>
				<s:table.field name="fdsize" datatype="string" label="字段宽度"></s:table.field>
				<s:table.field name="fddec" datatype="string" label="字段精度"></s:table.field>
				<s:table.field name="nouse" datatype="string" label="检索字段显示宽度控制"></s:table.field>
				<s:table.field name="oper" datatype="template" label="操作" align="left" width="200">
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="modify('$[fdname]')">修改</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="deletefield('$[fdname]')">删除</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	function query(){
		$("#fieldstable").params($("#conForm").getData());
		$("#fieldstable").refresh();
	}
	function add(){
		$.modal("add.html", "新增", {
			width : '80%',
			height : '60%',
			callback : function(rtn) {
				$("#fieldstable").refresh();
		    }
		});
	}
	function modify(fdname){
		$.modal("add.html", "修改", {
			width : '80%',
			height : '60%',
			fdname : fdname,
			callback : function(rtn) {
				$("#fieldstable").refresh();
		    }
		});
	}
	function deletefield(fdname){
		$.call("crdc.field.delete", {"fdname" : fdname}, function(rtn) {
			if (rtn.result == 'success') {
				$("#fieldstable").refresh();
			}
		},null,{async: false});
	}
	function reloadfield(){
		$.call("crdc.field.reloadfield", {}, function(rtn) {
			if(rtn){
				$.message("加载成功！");			
			}
		});
	}
</script>