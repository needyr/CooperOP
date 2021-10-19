<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="">
	<s:row>
		<s:form id="conForm" label="条件区域" color="green">
			<s:toolbar>
				<s:button label="查询" onclick="query()" color="blue" icon="fa fa-search"></s:button>
			</s:toolbar>
			<s:row>
				<s:autocomplete label="部门" name="department"
					action="setting.dep.querydep" cols="2">
					<s:option label="$[name]" value="$[id]">$[name]</s:option>
				</s:autocomplete>
				<s:autocomplete id="post" label="岗位" name="position" action="setting.post.query" 
					value="${p.position}" text="${p.jg_dep_post}" limit="10" editable="false" cols="2">
					<s:option value="$[id]" label="$[jg_dep_post]">$[jg_dep_post]</s:option>
				</s:autocomplete>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="人员详细信息" autoload="false" id="overid" action="application.notification.not_read" sort="true" fitwidth="true">
			<s:table.fields>
				<s:table.field name="name" datatype="string" label="人员" sort="true" ></s:table.field>
				<s:table.field name="department_name" datatype="string" label="部门" sort="true" ></s:table.field>
				<s:table.field name="position_name" datatype="string" label="岗位" sort="true" ></s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
		query();
	});
	
	function query(){
		var d=$("#conForm").getData();
		d.notice_id='${pageParam.notice_id}'
		$("#overid").params(d);
		$("#overid").refresh();
	}
</script>