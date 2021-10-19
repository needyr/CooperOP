<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="参数表管理" ismodal="true">

	<s:row>
	
		<s:row>
			<s:form id="conditions" border="0">
				<s:row>
				<%--
					<s:select label="参数类型" name="type">
						<s:option label="" value="" />
						<s:option label="in" value="in" />
						<s:option label="out" value="out" />
					</s:select> --%>
					<input type="hidden" name="data_service_code" value="${pageParam.data_service_code }" />
					<input type="hidden" name="data_service_method_code" value="${pageParam.data_service_method_code }" />
					<s:textfield label="关键字" name="key" cols="2" placeholder="请输入表名、父表、参数名称"></s:textfield>
					<s:button label="查询" icon="fa fa-search"
						size="btn-sm btn-default" onclick="queryReft();"></s:button>
				</s:row>
			</s:form>
		</s:row>
		
		<s:row>
			<s:table label="参数表列表" id="dataTable" autoload="false"
				action="hospital_common.reft.query" icon="fa fa-table" fitwidth="true" sort="true">
				<s:toolbar>
					<s:button label="新建参数表" icon="fa fa-file-o" 
						size="btn-sm btn-default" onclick="addReft();">
					</s:button> 
				</s:toolbar>
				<s:table.fields>
					<s:table.field label="参数类型" name="type" datatype="string"
						sort="true"></s:table.field>
					<s:table.field label="表名" name="table_name" datatype="string"
						sort="true" defaultsort="asc"></s:table.field>
					<s:table.field label="父表" name="parent_table_name" datatype="string"
						sort="true"></s:table.field>
					<s:table.field label="定义参数名称" name="param_name" datatype="string"
						sort="true"></s:table.field>
					<%--<s:table.field label="参数加密" name="encrypt_fields" datatype="string"
						sort="true"></s:table.field>--%>
					<s:table.field label="参数表结构" name="ref" datatype="template">
						<a style="margin:0 5px;" href="javascript:void(0)"
							onclick="manageReft('$[table_name]');">管理</a>
					</s:table.field>
					<s:table.field label="操作" name="oper" datatype="template"
						align="center" width="90">
						<a style="margin:0 5px;" href="javascript:void(0)"
							onclick="modifyReft('$[table_name]');">修改</a>
						<a style="margin:0 5px;" href="javascript:void(0)"
							onclick="deleteReft('$[table_name]');">删除</a>
					</s:table.field>
				</s:table.fields>
			</s:table>
		</s:row>
		
	</s:row>
	
</s:page>
<%---------------------------------------
			JAVASCRIPT
---------------------------------------%>
<script type="text/javascript">

	$(document).ready(function(){
		queryReft();
		$("#conditions").keypress(function(e){
			if ((e ? e : event).keyCode == 13) {
				queryReft();
			}
		});
	});
	function addReft() {
		add(null);
	}
	function deleteReft(table_name) {
		$.confirm('是否确认删除 "' + table_name + ' "？删除后将无法恢复！', function(yn){
			if (yn) {
				$.call("hospital_common.reft.delete", {
					data_service_method_code: '${pageParam.data_service_method_code}',
					table_name: table_name,
					data_service_code: '${pageParam.data_service_code}'
				}, function(rtn){
					if (rtn)
						queryReft();
				});
			}
		});
	}
	function modifyReft(table_name) {
		add(table_name);
	}
	function queryReft() {
		$("#dataTable").params($("#conditions").getData());
		$("#dataTable").refresh();
	}
	function manageReft(table_name) {
		$.modal("../manage/edit.html", "参数表结构管理", {
			tablename: table_name,
			callback: function(rtn) {
				if (rtn) {
					queryReft();
				}
			}
		});
	}
	function add(table_name) {
		$.modal("edit.html", table_name ? "修改参数表-" + table_name : "增加参数表", {
			data_service_method_code: '${pageParam.data_service_method_code}',
			table_name: table_name,
			data_service_code: '${pageParam.data_service_code}',
			callback: function(rtn) {
				if (rtn)
					queryReft();
			}
		});
	}

</script>
