<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="DB执行日志">
	<s:row>
		<s:row>
			<s:form id="conditions" border="0">
				<s:row>
					<s:datefield label="执行日期(起)" datatype="date" format="yyyy/MM/dd"
						name="begin_date"></s:datefield>
					<s:timefield label="执行时间(起)" datatype="time"
						name="begin_time"></s:timefield>
				</s:row>
				<s:row>
					<s:datefield label="执行日期(止)" datatype="date" format="yyyy/MM/dd"
						 name="end_date"></s:datefield>
					<s:timefield label="执行时间(止)" datatype="time"
						name="end_time"></s:timefield>
				</s:row>
				<s:row>
					<s:autocomplete label="执行编号" action="hospital_common.weblog.queryDistinct" name="data_webservice_method_code" id="webservice_method_code" params="{&#34;data_webservice_code&#34;:&#34;db&#34; }">
						<s:option label="$[code]" value="$[code]" />
					</s:autocomplete>
					<s:button label="搜索" icon="fa fa-search" onclick="weblogSearch();"
						style="margin-left:80px;" submit="false"></s:button>
				</s:row>
			</s:form>
		</s:row>
		
		<s:row>
			<s:table id="dataTable" sort="true" fitwidth="true"
				label="日志列表" icon="fa fa-table" autoload="false" action="hospital_common.weblog.queryDB">
				<s:table.fields>
					<s:table.field name="id" label="流水号" datatype="int" sort="true" defaultsort="desc" width="100">
					</s:table.field>
					<s:table.field name="data_webservice_method_code" label="执行编号" datatype="string" sort="true" width="200">
					</s:table.field>
					<s:table.field name="start_time" label="执行开始时间" datatype="datetime" sort="true" format="yyyy-MM-dd HH:mm:ss" width="140">
					</s:table.field>
					<s:table.field name="end_time" label="执行结束时间" datatype="datetime" sort="true" format="yyyy-MM-dd HH:mm:ss" width="140">
					</s:table.field>
					<s:table.field name="result" label="异常信息" datatype="string">
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
	
	$(document).ready(function(){
		setTimeout(weblogSearch, 100);
		$("#webservice_method_code").change(function(){
			weblogSearch();
		});
	});
	
	function weblogSearch() {
		$("#dataTable").params($("#conditions").getData());
		$("#dataTable").refresh();
	}

</script>