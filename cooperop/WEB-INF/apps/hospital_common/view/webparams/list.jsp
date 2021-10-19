<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="客服端请求参数" ismodal="true">

	<s:row>
		
		<s:row>
			<s:form id="conditions" border="0">
				<s:row>
					<input type="hidden" value="${pageParam.case_id }" name="case" />
					<input type="hidden" value="${pageParam.id }" name="id" />
					<s:textfield label="关键字" cols="2" name="key" placeholder="输入表名或值"></s:textfield>
					<s:button label="搜索" icon="fa fa-search"  onclick="webParamsSearch();"
						submit="false"></s:button>
				</s:row>
			</s:form>
		</s:row>
		
		<s:row>
			<s:table id="dataTable" sort="true" fitwidth="true"
				label="请求参数列表" icon="fa fa-table" autoload="false" action="hospital_common.webparams.query">
				<s:table.fields>
					<s:table.field name="table_name" label="表名" datatype="string" sort="true" defaultsort="asc">
					</s:table.field>
					<s:table.field name="value" label="基础类值或主键ID" datatype="string" sort="true">
					</s:table.field>
					<s:table.field name="order_no" label="参数顺序" datatype="int" sort="true">
					</s:table.field>
				</s:table.fields>
			</s:table>
		</s:row>
		
	</s:row>

</s:page>
<%------------------------------------
			JAVASCRIPT
------------------------------------%>
<script type="text/javascript">
	
	$(document).ready(function() {
		webParamsSearch();
		$("#conditions").keypress(function(e) {
			if ((e ? e : event).keyCode == 13) {
				webParamsSearch();
			}
		});
	});
	function webParamsSearch() {
		$("#dataTable").params($("#conditions").getData());
		$("#dataTable").refresh();
	}

</script>