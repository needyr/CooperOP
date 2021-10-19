<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="消息" dispermission="true">
<s:row>
	<s:form id="conditions" fclass="portlet light bordered">
		<s:row>
			<s:textfield label="通道名称" name="name"></s:textfield>
			<s:button label="查询" onclick="query();"></s:button>
		</s:row>
	</s:form>
</s:row>
<s:row>
	<s:table label="模版列表"  action="application.tunnel.sms.query" id="template_">
		<s:toolbar>
			<s:button label="新增" onclick="add();"></s:button>
		</s:toolbar>
		<s:table.fields>
			<s:table.field name="name" label="通道名称"></s:table.field>
			<s:table.field name="limit_num" label="每轮发送数量"></s:table.field>
			<s:table.field name="is_default" label="默认通道"></s:table.field>
			<s:table.field name="debugging" label="调试模式"></s:table.field>
			<s:table.field name="addressee_separator" label="多收件人分隔符"></s:table.field>
			<s:table.field name="opr" label="操作" datatype="template">
				<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="modify('$[id]')">修改</a>
				<a style="margin: 0px 5px;" href="javascript:void(0)"
					onclick="deletet('$[id]')">删除</a>
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
		var d = $("#conditions").getData();
		d.state = 1
		$("#template_").params(d);
		$("#template_").refresh();
	}
	function add(){
		$.modal("add.html", "新增", {
			width : '70%',
			height : '65%',
			callback : function(rtn) {
				if(rtn){
					$("#template_").refresh();
				}
		    }
		});
	}
	function modify(id){
		$.modal("add.html", "修改", {
			width : '70%',
			height : '65%',
			id : id,
			callback : function(rtn) {
				if(rtn){
					$("#template_").refresh();
				}
		    }
		});
	}
	function deletet(){
		$.confirm("删除不可恢复，是否确认继续？", function(r){
			if(r){
				$.call("application.tunnel.sms.delete", {id: id}, function(rtn) {
					if (rtn) {
						$("#template_").refresh();
					}
				},null,{async: false});
			}
		})
	}
</script>
