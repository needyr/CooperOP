<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="客服端定时异常日志" ismodal="true">

	<s:row>
		
		<s:row>
			<s:form id="conditions" border="0">
				<s:row>
					<s:datefield label="开始日期(起)" datatype="date" format="yyyy/mm/dd"
						name="begin_date"></s:datefield>
					<s:datefield label="开始日期(止)" datatype="date" format="yyyy/mm/dd"
						 name="end_date"></s:datefield>
					<s:timefield label="开始时间(起)" datatype="time"
						name="begin_time"></s:timefield>
					<s:timefield label="开始时间(止)" datatype="time"
						name="end_time"></s:timefield>
				</s:row>
				<s:row>
					<input type="hidden" value="${pageParam.data_webservice_code }"
						name="data_webservice_code" />
					<input type="hidden" value="${pageParam.data_webservice_method_code }"
						name="data_webservice_method_code" />
					<s:button label="搜索" icon="fa fa-search" onclick="scheduleSearch();"
						style="margin-left:80px;" submit="false"></s:button>
				</s:row>
			</s:form>
		</s:row>
		
		<s:row>
			<s:table id="dataTable" sort="true" fitwidth="true"
				label="定时异常列表" icon="fa fa-table" autoload="false" action="hospital_common.schedule.query">
				<s:table.fields>
					<s:table.field name="id" label="ID" datatype="int" sort="true" defaultsort="asc">
					</s:table.field>
					<s:table.field name="start_time" label="开始执行时间" datatype="string" sort="true">
					</s:table.field>
					<s:table.field name="end_time" label="结束执行时间" datatype="string" sort="true">
					</s:table.field>
					<s:table.field name="result" label="结果值" datatype="string" sort="true">
					</s:table.field>
				</s:table.fields>
			</s:table>
		</s:row>
		
	</s:row>

</s:page>
<%------------------------------
			JAVASCRIPT
------------------------------%>
<script type="text/javascript">
	
	$(document).ready(function() {
		scheduleSearch();
		$("#conditions").keypress(function(e) {
			if ((e ? e : event).keyCode == 13) {
				scheduleSearch();
			}
		});
	});
	function scheduleSearch() {
		$("#dataTable").params($("#conditions").getData());
		$("#dataTable").refresh();
	}

</script>