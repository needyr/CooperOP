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
				<s:textfield label="关键字" name="keyword" tips="匹配单据号"></s:textfield>
				<s:button label="查询" icon="fa fa-search" color="green" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="optionlist" action="application.task.queryFreeTask" tableid="test" autoload="false" fitwidth="true" sort="true" label="我的个人配合单">
			<s:toolbar>
				<s:button label="新建个人配合单" icon="fa fa-file-o" onclick="createFreeTask();"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="djbh" label="单据号"  sort="true" app_field="pre_title_field"></s:table.field>
				<s:table.field name="subject" label="主题"  sort="true" app_field="title_field"></s:table.field>
				<s:table.field name="create_time" label="创建时间" datatype="datetime" sort="true" defaultsort="desc" app_field="pre_title_field"></s:table.field>
				<s:table.field name="operator_name" label="当前状态" sort="true" datatype="script" app_field="content_field">
					var html = [];
					if (record.state == 9) {
						html.push("已结束");
					} else if (record.state == 0) {
						html.push("未提交");
					} else if (record.state == -1) {
						html.push("已取消");
					} else if (record.state == 1) {
						html.push('待<font class="font-yellow-casablanca">' + (record.operator_name ? record.operator_name.substring(0, record.operator_name.length - 1) : '') + '</font>处理');
					}
					return html.join("");
				</s:table.field>
				<s:table.field name="oper" label="操作" align="center" width="80" datatype="script">
					var html = [];
					if (record.state == 9) {
						html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="showdetail(\'' + record.djbh + '\')">详情</a>');
					} else if (record.state == 0) {
						html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="modify(\'' + record.id + '\')">修改</a>');
						html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="deleteF(\'' + record.id + '\')">删除</a>');
					} else if (record.state == -1) {
						html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="modify(\'' + record.id + '\')">修改</a>');
						html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="deleteF(\'' + record.id + '\')">删除</a>');
					} else if (record.state == 1) {
						html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="showdetail(\'' + record.djbh + '\')">详情</a>');
						html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="cancelF(\'' + record.id + '\')">撤销</a>');
					}
					return html.join("");
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
	function createFreeTask() {
		var url = cooperopcontextpath + "/w/application/task/freetask.html";
		$.modal(url, "新建个人配合单", {
			callback : function(rtn){
				$("#optionlist").refresh();
			}
		});
	}
	function modify(id) {
		var url = cooperopcontextpath + "/w/application/task/freetask.html";
		$.modal(url, "个人配合单修改", {
			id : id, 
			callback : function(rtn){
				$("#optionlist").refresh();
			}
		});
	}
	function showdetail(djbh){
		var url = cooperopcontextpath + "/w/application/task/freetaskdetail.html";
		$.modal(url, "个人配合单详情",{
			djbh: djbh,
			callback : function(rtn){
			}
		});
	}
	function deleteF(id) {
		$.confirm("是否确认删除此配合单？", function(c) {
			if (c) {
				$.call("application.task.deleteFreeTask", {id: id}, function(rtn) {
					$.success("删除成功。");
					$("#optionlist").refresh();
				});
			}
		});
	}
	function cancelF(id) {
		$.confirm("是否确认撤销此配合单？", function(c) {
			if (c) {
				$.call("application.task.cancelFreeTask", {id: id}, function(rtn) {
					$("#optionlist").refresh();
				});
			}
		});
	}
</script>