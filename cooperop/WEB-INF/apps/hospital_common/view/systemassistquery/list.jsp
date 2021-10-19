<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="辅助查询维护" >
	<s:row>
		<s:form id="form" label="快速查找">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="请输入链接地址或标题" cols="2"></s:textfield>
				<s:button label="查询" icon="" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="系统链接列表" autoload="false" action="hospital_common.systemassistquery.query"  >
			<s:toolbar>
				<s:button icon="" label="新增" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="id" label="编号"  ></s:table.field>
				<s:table.field name="fz_title" label="标题"  ></s:table.field>
				<s:table.field name="address" label="地址" ></s:table.field>
				<s:table.field name="description" label="描述" ></s:table.field> 
				<s:table.field name="state" label="状态" datatype="script">
					var state = record.state;
					if(state == 0){
						return "停用" ;
					}else if(state == 1){
						return "启用";
					}
				</s:table.field> 
				<s:table.field name="caozuo" label="操作" datatype="template" width="100">
					<a href="javascript:void(0)" onclick="update('$[id]')">修改</a>
					<a href="javascript:void(0)" onclick="del('$[id]')">删除</a>
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
	$.modal("edit.html","新增",{
		width:"50%",
		height:"60%",
		callback : function(e){
			query();
		}
	});
}

function update(id){
	$.modal("edit.html","修改",{
		width:"50%",
		height:"60%",
		id: id,
		callback : function(e){
			query();
		}
	});
}

function del(id){
	$.call("hospital_common.systemassistquery.delete",{id: id},function(rtn){
		if(rtn){
			query();
		}
	})
}
</script>