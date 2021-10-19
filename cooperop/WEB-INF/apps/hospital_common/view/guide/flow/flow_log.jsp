<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="日志查看" >
<style type="text/css">
.open {
    background-color: #56d0ff;
    font-size: 12px;
    border-radius: 20px;
    padding: 2px;
}

.complete {
    background-color: #ddb133;
    font-size: 12px;
    border-radius: 20px;
    padding: 2px;
}
</style>
<s:row>
<s:form id="dataform">
	<s:row>
		<s:textfield placeholder="检索流程名称" name="name"></s:textfield>
		<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
	</s:row>
</s:form>

</s:row>
<s:row>
	<s:table action="hospital_common.guide.flow.queryFlowLog" sort="true" autoload="false" fitwidth="true" label="日志查看"  id="datatable">
		<s:table.fields>
			<s:table.field name="name" label="流程名称" ></s:table.field>
			<s:table.field name="create_time" sort="true" defaultsort="asc" label="操作时间" datatype="datetime" format="yyyy-MM-dd HH:mm:ss"></s:table.field>
			<s:table.field name="info" label="操作信息" datatype="script">
				return '<label class="'+(record.info == '进入流程页面'?'open':record.info == '完成流程'?'complete':'')+'">'+record.info+'</label>';
			</s:table.field>
			<s:table.field name="remark" label="备注" ></s:table.field>
		</s:table.fields>
	</s:table>
</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		query();
	});
	
	function query() {
		var data = $('#dataform').getData();
		$("#datatable").params(data);
		$("#datatable").refresh();
	}
</script>