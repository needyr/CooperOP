<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="测试智能审查">
	<s:row>
		<s:form id="conditions" border="0">
			<s:row>
				<s:textarea label="数据来源sql" cols="3" name="source_sql" 
				value="select top 10 id,doctor_no,patient_id,visit_id from hospital_autopa..auto_audit (nolock) "></s:textarea>
				<%-- <s:textfield label="执行数据库" name="system_product_code" value="ho"></s:textfield> --%>
				<s:textfield label="并发数" name="bf_num" placeholder="并发数量" value="10"></s:textfield>
				<s:textfield label="每单间隔时间" name="jg_num" value="2"></s:textfield>
				<s:button label="执行" onclick="exe_autoaudit()" icon="fa fa-search"></s:button>
			</s:row>
		</s:form>
	</s:row>

	<s:row>
		<s:table label="服务端列表" id="dataTable" autoload="false" 
			action="hospital_common.test_jq.query" icon="fa fa-table" fitwidth="true" sort="true">
			<s:table.fields>
				<s:table.field label="测试项目" name="name" datatype="string"
					sort="true" defaultsort="asc"></s:table.field>
				<s:table.field label="状态" name="state" datatype="string"
					sort="true"></s:table.field>
				<s:table.field label="花费时间（毫秒）" name="cost_time" datatype="string"
					sort="true"></s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
		
	
</s:page>
<script type="text/javascript">
	$(function(){
		query();
	});
	
	function query(){
		$("#dataTable").params({"type": 2});
		$("#dataTable").refresh();
	}
	
	function exe_autoaudit() {
		$.call("hospital_common.test_jq.test_uploadyun", $("#conditions").getData(), function(rtn){
			query();
		});
	}
</script>
