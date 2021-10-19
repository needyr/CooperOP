<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="驾驶舱管理">
	<s:form>
		<s:row id="conditions">
			<s:textfield label="关键字" name="filter" placeholder="驾驶舱名称、备注"></s:textfield>
			<s:select label="状态" name="state" id="state" dictionary="pivascockpit.record.state" value="1">
			</s:select>
			<s:button icon="fa fa-search" id="btnQuery" label="搜索"></s:button>
		</s:row>
		<s:row style="margin-top: 30px;">
			<s:table id="datatable" label="驾驶舱列表" action="pivascockpit.cockpit.query" autoload="false" sort="true">
				<s:toolbar>
					<s:button icon="fa fa-plus" id="btnAdd" label="添加"></s:button>
				</s:toolbar>
				<s:table.fields>
					<s:table.field name="name" label="名称" sort="true" defaultsort="asc" ></s:table.field>
					<s:table.field name="template" label="模板" sort="true" dictionary="pivascockpit.cockpit.templates" width="80"></s:table.field>
					<s:table.field name="last_modifier_name" label="最后修改人" sort="true" width="80"></s:table.field>
					<s:table.field name="last_modify_time" datatype="datetime" label="最后修改时间" sort="true" width="150"></s:table.field>
					<s:table.field name="state" label="状态" sort="true" width="60" datatype="template" align="center">
						<s:switch cols="1" class="switchState" name="state" value="$[state]" data-cid="$[id]" ontext="在用" offtext="停用"></s:switch>
					</s:table.field>
					<s:table.field name="oper" label="操作" datatype="template" align="center" width="60">
						<a href="javascript:void(0);" class="btnEdit" data-cid="$[id]" data-cname="$[name]"><i class="fa fa-edit" style="margin-right:3px"></i>编辑</a><a class="btnDelete" data-cid="$[id]" data-cname="$[name]" style="margin-left:10px;" href="javascript:void(0);"><i class="fa fa-trash-o" style="margin-right:3px"></i>删除</a>
					</s:table.field>
				</s:table.fields>
			</s:table>
		</s:row>
	</s:form>
	<script type="text/javascript">
	$(document).ready(function() {
		var query = function() {
			var data = $("#conditions").getData();
			$("#datatable").params(data);
			$("#datatable").refresh();
		}
		$("#btnQuery").on("click", function() {
			query();
		});
		$("#state").on("change", function() {
			query();
		});
		$("#btnAdd").on("click", function() {
			$.openTabPage("pivascockpit.cockpit.edit#" + new Date().getTime(), "添加驾驶舱", cooperopcontextpath + "/w/pivascockpit/cockpit/edit.html?", false);
		});
		$("#datatable").on("switch", ".switchState", function(d) {
			var $this = $(this);
			var data = $this.getData();
			data.id = $this.data("cid");
			$.call("pivascockpit.cockpit.updateState", data, function() {
			})
		});
		$("#datatable").on("click", "a.btnEdit", function() {
			$.openTabPage("pivascockpit.cockpit.edit#" + $(this).data("cid"), $(this).data("cname") + "-驾驶舱编辑", cooperopcontextpath + "/w/pivascockpit/cockpit/edit.html?id=" + $(this).data("cid"), false);
		});
		$("#datatable").on("click", "a.btnDelete", function() {
			var $this = $(this);
			$.confirm("是否确认删除驾驶舱“" + $this.data("cname") + "”？", function(c) {
				if (c) {
					$.call("pivascockpit.cockpit.delete", {id: $this.data("cid")}, function() {
						query();
					})
				}
			});
		});
		query();
	});
	</script>
</s:page>