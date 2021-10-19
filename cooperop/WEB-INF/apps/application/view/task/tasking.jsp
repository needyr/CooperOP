<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="我的任务列表" dispermission="true">
	<s:row>
		<s:form id="conditions" fclass="portlet light bordered">
			<s:row>
				<s:textfield label="关键字" name="keyword" tips="关键字"></s:textfield>
				<s:button label="查询" icon="fa fa-search" color="green" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="optionlist" action="application.task.queryMine" autoload="false" fitwidth="true" sort="true" label="任务列表">
			<s:table.fields>
				<s:table.field name="order_no" label="单据号"  sort="true"></s:table.field>
				<s:table.field name="node_name" label="审批节点" ></s:table.field>
				<s:table.field name="create_user_name" label="发起人"></s:table.field>
				<s:table.field name="create_time" label="到达时间"  sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="process_name" label="流程"  sort="true"></s:table.field>
				<s:table.field name="system_product_code" label="所属产品"  sort="true"></s:table.field>
				<s:table.field name="oper" datatype="template" label="操作"
					align="left" width="80">
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="todeal('$[node_bill]', '$[order_no]', '$[task_id]','$[node_name]')">处理</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
		query();
	});
	function query() {
		$("#optionlist").params($("#conditions").getData());
		$("#optionlist").refresh();
	}
	function todeal(pageid,order_no,task_id,node_name){
		var u = pageid.split(".").join("/");
		var url = cooperopcontextpath + "/w/" + u + ".html";
		$.modal(url,node_name,{
			djbh: order_no,
			task_id: task_id,
			callback : function(rtn){
				$("#optionlist").refresh();
			}
		})
	}
</script>