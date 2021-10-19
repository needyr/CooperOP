<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
	<s:row>
		<s:form border="0" id="myform" fclass="portlet light bordered">
			<s:row>
				 <input type="hidden" name="parent_id" value="${empty param.parent_id ? depart.parent_id : param.parent_id}"/>
				  <input type="hidden" name="id" value="${param.id }"/>
				<s:textfield label="部门代码" name="code" required="true"
					value="${depart.code }"></s:textfield>
				<s:textfield label="部门名称" name="name" required="true"
					value="${depart.name }"></s:textfield>
				<s:textfield label="部门简称" name="sort_name" required="true"
					value="${depart.sort_name }"></s:textfield>	
			</s:row>
			<s:row>
				<s:radio required="true" label="类型" name="type" value="${depart.type }" cols="4">
					<s:option label="虚拟单位" value="0"></s:option>
					<s:option label="运营机构" value="1"></s:option>
					<s:option label="部门" value="2"></s:option>
					<s:option label="分组" value="3"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<s:textfield label="排序值" name="order_no" value="${depart.order_no }" required="true"></s:textfield>
				<c:if test="${param.is_dep != 1}">
					<s:textfield label="机构id" name="jigid" value="${depart.jigid }"></s:textfield>
				</c:if>
				<c:if test="${param.is_dep == 1}">
					<c:if test="${not empty param.id}">
						<s:textfield label="机构id" name="jigid" value="${depart.jigid }"></s:textfield>
					</c:if>
				</c:if>
				<s:textfield label="部门id" name="bmid" value="${depart.bmid }"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="说明" name="introduce" value="${depart.introduce }" cols="2"></s:textfield>
				<s:button onclick="save();" color="green" label="保存"></s:button>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	$(function (){
		if ('${param.id}'){
			$("input[name='code']").attr("readonly","readonly");
			$("input[name='type']").attr("disabled","disabled");
			$("input[name='jigid']").attr("readonly","readonly");
		}
	});

	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		$.call("setting.dep.save", $("#myform").getData(), function(rtn) {
			if (rtn.result == 'add_success') {
				parent.location.reload();
			}else if (rtn.result == 'add_fail'){
				$.message("新增失败");
			}else if (rtn.result == 'code_repeat'){
				$.message("机构id已存在或code已存在");
			}
		},null,{async: false});
	}
</script>
