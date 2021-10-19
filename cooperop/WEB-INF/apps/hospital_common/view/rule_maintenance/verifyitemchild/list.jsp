<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="" >
	<s:row>
		<s:form border="0" id="form" label="快速查找">
			<s:row>
			    <input type="hidden" value="${p_id }" name="p_id" />
				<s:textfield label="快速查找" name="sousuo" placeholder="请输入标识或者表名的关键字" cols="2"></s:textfield>
				<s:button label="查询" icon="" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="维护" autoload="true" param="" action="hospital_common.rule_maintenance.verifyitemchild.query" >
		    <s:toolbar>
				<s:button icon="" label="新增" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
			    <s:table.field name="id" label="标识" datatype="" ></s:table.field>
			    <s:table.field name="table_name" label="表名" datatype="" ></s:table.field>
				<s:table.field name="field" label="字段名" datatype="" ></s:table.field>
				<s:table.field name="parent_id" label="上级标识" datatype="" ></s:table.field>
				<s:table.field name="product" label="所属产品" datatype="" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="70px">
					<a href="javascript:void(0)" onclick="update('$[id]')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]')"> 删除</a>
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
		$.modal("edit.html","新增规则",{
			width:"50%",
			height:"80%",
			p_id:'${p_id}',
			callback : function(e){
				query();
			}
		});
	}
	
	function update(id){
		$.modal("edit.html","更改规则",{
			width:"50%",
			height:"80%",
			id:id,
			callback : function(e){
				query();
			}
		});
	}
	
	function Delete(id){
		$.confirm("是否确认删除？",function callback(e){
			if(e==true){
				$.call("hospital_common.rule_maintenance.verifyitemchild.delete",{"id":id},function (rtn){
					query();
				})	
			}
		})
	}
	
	
</script>