<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="时间表达式字典维护" disloggedin="true">
	<div class="note note-warning">
	温馨提示：此处仅显示常用定时器时间表达式清单。
	</div>
	<s:row>
		<s:form id="form" label="快速查找">
			<s:row>
				<input type="hidden" name="product_code"  value="ipc"/>
				<s:textfield label="关键字"  name="filter" placeholder="请输入任务简介或名称关键字" cols="1"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="时间表达式字典维护" autoload="false"  sort="true" action="hospital_common.dict.timeexpression.query"  >
			<s:toolbar>
				<s:button icon="fa fa-plus" label="新增" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="p_key" label="编号" sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="code" label="表达式" ></s:table.field>
				<s:table.field name="name" label="名称" ></s:table.field>
				<s:table.field name="remark" label="任务简介" ></s:table.field>
				<s:table.field label="操作" name="oper" datatype="template">
					<a style="margin:0 5px;" href="javascript:void(0)"
						onclick="update('$[p_key]');">修改</a>
					<a style="margin:0 5px;" href="javascript:void(0)"
						onclick="del('$[p_key]');">删除</a>
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
	$("#datatable").params($("#form").getData());
	$("#datatable").refresh();
}

function Add(){
	$.modal("edit.html","新增",{
		width:"50%",
		height:"55%",
		callback : function(e){
			query();

		}
	});
}

function update(p_key){
	$.modal("edit.html","修改",{
		width:"50%",
		height:"55%",
		p_key:p_key,
		callback : function(e){
			query();
		}
	});
}

function del(p_key){
	$.confirm("是否确认删除该条数据？",function callback(e){
		if(e==true){
			$.call("hospital_common.dict.timeexpression.delete",{p_key: p_key},function(rtn){
				if(rtn){
					query();
				}
			})
		}
	})
	
}

</script>