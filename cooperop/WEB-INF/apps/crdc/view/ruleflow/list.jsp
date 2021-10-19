<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="规则引擎设计">
	<s:row>
		<s:form border="0" id="conditions">
			<s:row>
				<s:radio cols="4" label="所属产品" name="system_product_code" action="application.common.listProducts" >
					<s:option value="$[code]" label="$[name]"></s:option>
				</s:radio>
				<s:textfield cols="1" name="filter" label="关键字" placeholder="编号、流程"></s:textfield>
				<s:button icon="fa fa-search" label="查询" size="btn-sm" onclick="query();"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table icon="fa fa-table" id="ruletable" label="规则引擎列表"
			action="crdc.ruleflowdesigner.query" autoload="true" limit="25"
			sort="true" fitwidth="true">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="新建" size="btn-sm btn-default"
					action="crdc.ruleflow.designer" onclick="add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="system_product_name" datatype="string" label="所属产品" sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="id" datatype="string" label="编号" sort="true" defaultsort="asc"></s:table.field>
				<s:table.field name="name" label="规则名称" datatype="string" sort="true"></s:table.field>
				<s:table.field name="state" label="状态" datatype="script" align="center" sort="true">
					if (+record.state == 1) {
						return '<font color="green">已发布</font>';
					} else {
						return '未发布';
					}
				</s:table.field>
				<s:table.field name="last_modifier_name" datatype="string" label="最后修改人" sort="true"></s:table.field>
				<s:table.field name="last_modify_time" label="最后修改时间" datatype="datetime" align="center" sort="true"></s:table.field>
				<s:table.field name="oper" datatype="template" label="操作" align="center">
					<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="modify('$[system_product_code]', '$[id]','$[name]')">修改</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="deleteFlow('$[system_product_code]', '$[id]')">删除</a>
					<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="undeploy('$[system_product_code]', '$[id]')">停用</a>
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
	});
	function query() {
		$("#ruletable").params($("#conditions").getData());
		$("#ruletable").refresh();
	}
	function add() {
		location.href = cooperopcontextpath + "/w/crdc/ruleflow/designer.html?_pid_=${pageParam._pid_}";
		//top.$(".page-content-tabs").open_tabwindow("ruleflow_new", "新建流程", url);
	}
	function modify(system_product_code, id,name) {
		location.href = cooperopcontextpath + "/w/crdc/ruleflow/designer.html?_pid_=${pageParam._pid_}&system_product_code=" + system_product_code + "&id=" + id;
	}
	function deleteFlow(system_product_code, id) {
		$.confirm("是否确认删除此流程定义？删除后将无法恢复！", function(c) {
			if (c) {
				$.call("crdc.ruleflowdesigner.delete", {
					system_product_code: system_product_code, 
					id: id
				}, function(rtn) {
					$("#ruletable").refresh();
				});
			}
		});
	}
	function undeploy(system_product_code, id) {
		$.confirm("是否确认停用此流程定义？", function(c) {
			if (c) {
				$.call("crdc.ruleflowdesigner.undeploy", {
					system_product_code: system_product_code, 
					id: id
				}, function(rtn) {
					$("#ruletable").refresh();
				});
			}
		});
	}
</script>