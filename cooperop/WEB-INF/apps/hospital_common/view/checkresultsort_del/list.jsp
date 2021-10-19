<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="标准分类与审查结果分类对接维护" >
	<s:row>
		<s:form id="form" label="快速查找">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="请输入标准分类名称" cols="2"></s:textfield>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="标准分类与审查结果分类匹配维护" autoload="false" action="hospital_common.checkresultsort.query" sort="true" >
			<s:toolbar>
				<s:button icon="" label="新增匹配" onclick="Add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="sort_id" label="标准类别编号" ></s:table.field>
				<s:table.field name="sort_name" label="标准类别名称"></s:table.field>
				<s:table.field name="check_type" label="审查分类" datatype="script" >
					var check_type = record.check_type;
					if(check_type == '1'){
					return "合理用药审查"
					}else if (check_type == 2){
					return "自定义审查"
					}
				</s:table.field>
			    <s:table.field name="type" label="审方类别编号" ></s:table.field>
				<s:table.field name="name" label="审方类别名称" ></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="70">
					<a href="javascript:void(0)" onclick="update('$[sort_id]','$[type]')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[sort_id]','$[type]')">删除</a>
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
	/* 	qdata.ts='${param.ts}';
		qdata.vs='${param.vs}';
		qdata.uid='${param.uid}'; */
		$("#datatable").params(qdata);
		$("#datatable").refresh();
	}
	
	function Add(){
		$.modal("edits.html","新增参数",{
			width:"400px",
			height:"70%",
		/* 	ts:'${param.ts}',
			vs:'${param.vs}',
			uid:'${param.uid}', */
			callback : function(e){
				query();
			}
		});
	}
	
	function update(sort_id,type){
		$.modal("edits.html","修改参数",{
			width:"400px",
			height:"70%",
			sort_id:sort_id,
			type:type,
			/* ts:'${param.ts}',
			vs:'${param.vs}',
			uid:'${param.uid}', */
			callback : function(e){
				query();
			}
		});
	}
	
	function Delete(sort_id,type){
		$.confirm("确认删除？\t\n删除后无法恢复！",function callback(e){
			if(e==true){
				$.call("hospital_common.checkresultsort.delete",{"sort_id":sort_id,"type":type},function(s){
					query();
				});
			}
		});
	}
</script>