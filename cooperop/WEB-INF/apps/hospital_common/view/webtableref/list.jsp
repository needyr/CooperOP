<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="客服端参数表管理" ismodal="true">

	<s:row>
	
		<s:row>
			<s:form id="conditions" border="0">
					<s:row>
						<input type="hidden" value="${pageParam.data_webservice_code }" name="data_webservice_code" />
						<input type="hidden" value="${pageParam.data_webservice_method_code }" name="data_webservice_method_code" />
						<%--
						<s:select label="传参类型" name="type">
							<s:option label="" value="" />
							<s:option label="in" value="in" />
							<s:option label="out" value="out" />
						</s:select> --%>
						<s:textfield label="关键字" name="key" 
							placeholder="请输入表名、父表名、参数名称" cols="2"></s:textfield>
						<s:button label="查询" onclick="queryWebtableref()" icon="fa fa-search"></s:button>
					</s:row>
			</s:form>
		</s:row>
		
		<s:row>
			<s:table label="参数表列表" autoload="false" id="dataTable"
				action="hospital_common.webtableref.query" sort="true" fitwidth="true" icon="fa fa-table">
				<s:toolbar>
					<s:button icon="fa fa-file-o" label="新增参数表" size="btn-sm btn-default"
						onclick="addWebtableref()"></s:button>
				</s:toolbar>
				<s:table.fields>
					<s:table.field name="type" datatype="string" label="传参类型" sort="true"></s:table.field>
					<s:table.field name="table_name" datatype="string" label="存储表" sort="true"
						defaultsort="asc"></s:table.field>
					<s:table.field name="parent_table_name" datatype="string" label="存储表父表" sort="true"></s:table.field>
					<s:table.field name="param_name" datatype="string" label="定义参数名称" sort="true"></s:table.field>
					<s:table.field name="look" datatype="template" label="表结构">
						<a style="margin: 0px 5px;" href="javascript:void(0)"
							onclick="lookStruct('$[table_name]');">管理</a>
					</s:table.field>
					<s:table.field name="oper" datatype="template" label="操作" align="center" width="80">
						<a style="margin: 0px 5px;" href="javascript:void(0)"
							onclick="modifyWebtableref('$[table_name]')">修改</a>
						<a style="margin: 0px 5px;" href="javascript:void(0)"
							onclick="deleteWebtableref('$[table_name]')">删除</a>
					</s:table.field>
				</s:table.fields>
			</s:table>
		</s:row>
		
	</s:row>
	
</s:page>
<%----------------------------------------
	        JAVASCRIPT
-----------------------------------------%>
<script type="text/javascript">

	$(document).ready(function() {
		queryWebtableref();
		$("#conditions").keypress(function(e) {
			if ((e ? e : event).keyCode == 13) {
				queryWebtableref();
			}
		});
	});
	
	function addWebtableref() {
		add(null);
	}
	
	function deleteWebtableref(table_name) {
		$.confirm('是否确认删除 "' + table_name + '" ？删除后将无法恢复！', function(btn) {
			if (btn) {
				$.call("hospital_common.webtableref.delete", {
					data_webservice_method_code: '${pageParam.data_webservice_method_code}',
					data_webservice_code: '${pageParam.data_webservice_code}',
					table_name: table_name
				}, function(rtn) {
					if (rtn) {
						queryWebtableref();
					}
				});
			}
		});
	}
	
	function modifyWebtableref(table_name) {
		add(table_name);
	}
	
	function queryWebtableref() {
		$("#dataTable").params($("#conditions").getData());
		$("#dataTable").refresh();
	}
	
	function add(table_name) {
		$.modal("edit.html", table_name ? "修改参数表-" + table_name : "增加参数表", {
			data_webservice_method_code: '${pageParam.data_webservice_method_code}',
			data_webservice_code: '${pageParam.data_webservice_code}',
			table_name: table_name,
			callback: function(rtn) {
				if (rtn)
					queryWebtableref();
			}
		});
	}
	
	function lookStruct(table_name){
		$.modal("../webtabstruct/edit.html", "参数表结构管理", {
			tablename: table_name,
			callback: function(rtn) {
				if (rtn) 
					queryWebtableref();
			}
		});
	}
	
</script>