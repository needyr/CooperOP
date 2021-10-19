<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="检索参数设计">
<s:row>
	<s:form label="条件搜索"  id="condi" >
		<s:row >
			<s:textfield cols="2" label="关键字" name="keyword" boder="0" placeholder="参数名，参数类型进行查询" />
			<s:button label="查询" onclick="query()" color="blue"></s:button>
		</s:row>
	</s:form>
</s:row>
	<s:row>
		<s:table color="green" label="参数列表" autoload="true" id="mytable"
			action="crdc.sysparams.query" sort="true" fitwidth="true">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="参数新增" size="btn-sm btn-default"
					 onclick="add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="name" datatype="string" label="参数名" sort="true"></s:table.field>
				<s:table.field name="param_" datatype="string" label="参数"
					sort="true" ></s:table.field>
				<s:table.field name="type" datatype="script" label="参数类型" 
					sort="true" defaultsort="asc">
					if (record.type == "U") {return '<div>人员</div>';} 
					if (record.type == "D") {return '<div>部门</div>';} 
					if (record.type == "A") {return '<div>全公司</div>';} 
					if (record.type == "P") {return '<div>岗位</div>';} 
					</s:table.field>
				<s:table.field name="exe_scheme" datatype="string" label="执行方法"
					sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="oper" datatype="template" label="操作"
					align="center">
						<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="modifyParams('$[id]')">修改</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="deleteParams('$[id]')">删除</a>
					
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
	<s:row>
		
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
		$.modal("add.html", "参数新增", {
			width : '50%',
			height : '65%',
			callback : function(rtn) {
				$("#mytable").refresh();
		    }
		});
	}
	function modifyParams(id) {
		$.modal("add.html", "修改参数", {
			id : id,
			width : '50%',
			height : '65%',
			callback : function(rtn) {
				$("#mytable").refresh();
			}
		});
	}
	function deleteParams(id) {
		$.confirm("是否确认删除该参数？删除后将无法恢复！", function(c) {
			if (c) {
				$.call("crdc.sysparams.delete", {
					id : id,
				}, function(rtn) {
					if(rtn.result=='success'){
						$("#mytable").refresh();
					}
				});
			}
		});
	}
</script>