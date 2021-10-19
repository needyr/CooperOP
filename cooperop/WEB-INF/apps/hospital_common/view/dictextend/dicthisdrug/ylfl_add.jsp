<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
<style type="text/css">
.
</style>
	<s:row>
		<s:form border="0" id="myform" fclass="portlet light bordered">
			<s:row>
				<c:if test="${not empty id}">
					<s:textfield label="id" name="id" cols="3" value="${id }" disabled="disabled"></s:textfield>
				</c:if>
				<c:if test="${not empty parent_id}">
					<s:textfield label="父id" cols="3" name="parent_id" value="${parent_id}" readonly="true"/>
				</c:if>
				<s:textfield cols="3" label="药理分类名称" name="drug_ylfl_name"  required="true" value="${drug_ylfl_name}"></s:textfield>
				<s:textfield cols="3" label="药理分类CODE" name="drug_ylfl_code" value="${drug_ylfl_code}"></s:textfield>
				<s:textfield cols="3" label="排序" name="sort" value="${sort}"></s:textfield>
			</s:row>
			<s:row>
				<div class="cols">
					<s:button onclick="save();" color="green" label="保存"></s:button>
				</div>
			</s:row>
		</s:form>
	</s:row>
	
</s:page>
<script>
    $(document).ready(function(){
    });
	
    function save() {
		if(!$("form").valid()){
			return;
		}
		var data = $("#myform").getData();
		$.call("hospital_common.dictextend.dicthisdrug.save_ylfl", data ,function(rtn) {
			if (rtn.result == 'success') {
				$.closeModal(true);
			}
		},null,{async: false});
	}
</script>
