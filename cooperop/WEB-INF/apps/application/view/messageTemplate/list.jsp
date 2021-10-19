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
			<s:radio label="所属产品" name="system_product_code" action="application.common.listProducts" cols="4" value="">
				<s:option value="$[code]" label="$[name]"></s:option>
			</s:radio>
		</s:row>
		<s:row>
			<s:radio label="类型" name="sort" cols="2" value="">
				<s:option value="1" label="业务消息"></s:option>
				<s:option value="2" label="流程消息"></s:option>
				<s:option value="3" label="单据消息"></s:option>
			</s:radio>
			<s:textfield label="模版名称" name="title"></s:textfield>
			<s:button label="查询" onclick="query();"></s:button>
		</s:row>
	</s:form>
</s:row>
<s:row>
	<s:table label="模版列表"  action="application.messageTemplate.query" id="template_">
		<s:toolbar>
			<s:button label="新增业务消息模版" onclick="add(1);"></s:button>
			<s:button label="新增流程消息模版" onclick="add(2);"></s:button>
			<s:button label="新增单据消息模版" onclick="add(3);"></s:button>
		</s:toolbar>
		<s:table.fields>
			<s:table.field name="title" label="模版名称"></s:table.field>
			<s:table.field name="system_product_name" label="所属产品"></s:table.field>
			<s:table.field name="sort" label="类型" datatype="script">
				if(record.sort =='1'){
					return '业务消息';
				}else if(record.sort == '2'){
					return '流程节点完成';
				}else if(record.sort == '3'){
					return '单据消息';
				}else if(record.sort == '4'){
					return '流程节点到达';
				}
			</s:table.field>
			<s:table.field name="opr" label="操作" datatype="template">
				<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="modify('$[id]','$[sort]')">修改</a>
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
		$("#template_").params($("#conditions").getData());
		$("#template_").refresh();
	}
	function add(sort){
		$.modal("add.html", "新增", {
			width : '70%',
			height : '85%',
			sort: sort,
			callback : function(rtn) {
				if(rtn){
					$("#template_").refresh();
				}
		    }
		});
	}
	function modify(id, sort){
		$.modal("add.html", "修改", {
			width : '70%',
			height : '85%',
			id : id,
			sort: sort,
			callback : function(rtn) {
				if(rtn){
					$("#template_").refresh();
				}
		    }
		});
	}
	function deletet(id){
		$.confirm("删除不可恢复，是否确认继续？", function(r){
			if(r){
				$.call("application.messageTemplate.delete", {id: id}, function(rtn) {
					if (rtn) {
						$("#template_").refresh();
					}
				},null,{async: false});
			}
		})
	}
</script>
