<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="更新模块字典维护" >
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
				<s:textfield label="快速查找" name="sousuo" placeholder="请输入更新模块标识或项目或名称" cols="2"></s:textfield>
				<s:button label="查询" icon="" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="更新模块管理" autoload="false" action="hospital_common.version_message.ycmoduledict.query" >
			<s:toolbar>
				<s:button icon="" label="新增更新模块" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
			    <s:table.field name="module_id" label="更新标识" datatype="" ></s:table.field>
			    <s:table.field name="module_project" label="更新项目" datatype="" ></s:table.field>
				<s:table.field name="module_name" label="更新名称" datatype="" ></s:table.field>
				
				<s:table.field name="caozuo" label="操作" datatype="template" width="70px">
					<a href="javascript:void(0)" onclick="update('$[module_id]')">修改</a>
					<!-- <a href="javascript:void(0)" onclick="Delete('$[module_id]')">删除</a> -->
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		query();
		
	});

	function query(){
		var qdata=$("#form").getData();
		$("#datatable").params(qdata);
		$("#datatable").refresh();
	}
	
	function Add(){
		$.modal("edit.html","新增更新模块",{
			width:"50%",
			height:"60%",
			callback : function(e){
				query();
			}
		});
	}
	
	function update(module_id){
		$.modal("edit.html","修改更新模块",{
			width:"30%",
			height:"60%",
			module_id:module_id,
			callback : function(e){
				query();
			}
		});
	}
	
	function Delete(module_id){
		$.confirm("是否确认删除？",function callback(e){
			if(e==true){
				$.call("hospital_common.version_message.ycmoduledict.delete",{"module_id":module_id},function (rtn){
					query();
				})	
			}
		})
	}
	

	
</script>