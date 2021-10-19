<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="图表管理">
	<s:form>
		<s:row id="conditions">
			<s:textfield label=" " name="filter" placeholder="匹配编号、名称"></s:textfield>
			<s:button icon="fa fa-search" id="btnQuery" label="搜索"></s:button>
		</s:row>
		<s:row style="margin-top: 30px;">
			<s:table id="datatable" label="图表列表" action="pivascockpit.chart.query" autoload="false" sort="true">
				<s:table.fields>
					<s:table.field name="code" label="编号" sort="true" width="60"></s:table.field>
					<s:table.field name="name" label="名称" sort="true" defaultsort="asc"></s:table.field>
					<s:table.field name="childname" label="下钻图表" sort="true"></s:table.field>					
					<s:table.field name="refresh_time" label="刷新周期(秒)" sort="true" dictionary="pivascockpit.cockpit.template" width="60" align="right"></s:table.field>
					<s:table.field name="oper" label="操作" datatype="template" align="center" width="60">
						<a href="javascript:void(0)" class="btnEdit" data-ccode="$[code]" data-cname="$[name]"><i class="fa fa-edit" style="margin-right:3px"></i>编辑</a>
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
		$("#datatable").on("click", "a.btnEdit", function() {
			$.openTabPage("pivascockpit.chart.edit#" + $(this).data("ccode"), $(this).data("cname") + "-图表编辑", cooperopcontextpath + "/w/pivascockpit/chart/edit.html?code=" + $(this).data("ccode"), false);
		});
		query();
	});
	</script>
</s:page>