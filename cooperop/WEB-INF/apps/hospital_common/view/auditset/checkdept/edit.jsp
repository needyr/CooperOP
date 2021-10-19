<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑审查部门" ismodal="true">
	<s:row>
		<s:form id="form" label="审查部门信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon="glyphicon glyphicon-floppy-saved"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="部门编号" name="dept_code" cols="3" value="${dept_code}" required="true" disabled="disabled"></s:textfield>
			</s:row>
			<s:row>
				<input type="hidden" name="id" value="${id}">
				<s:autocomplete id="dp" label="部门" name="dept_name" action="hospital_common.auditset.checkdept.querydept" text="${dept_name}" params="{&#34;product_code&#34; : &#34;${param.product_code}&#34;}" limit="10" editable="false" cols="3" value="${dept_code}" required="true" >
					<s:option value="$[dept_code]" label="$[dept_name]($[dept_type])">
							$[dept_code]
							$[dept_name]
							($[dept_type])
					</s:option>
				</s:autocomplete>
				<%-- <s:textfield label="部门名称" name="dept_code" required="true" cols="3" value="${dept_code}" ></s:textfield> --%>
			</s:row>
			<s:row>
				<s:textarea label="备注" name="remark" required="false" cols="3" >${remark}</s:textarea>
			</s:row>
			<s:row>
				<s:switch label="是否启用" name="state" onvalue="1" offvalue="0" ontext="是" offtext="否" value="${state}"></s:switch>
			</s:row> 
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
$(function(){
	$("#dp").on("change",function(){
		var dept_code = $("#form").getData().dept_name;
		$('[name="dept_code"]').val(dept_code);
	})
});

function save(){
	var sdata = $("#form").getData();
	sdata.dept_name = $("#dp").val();
	sdata.product_code = '${param.product_code}';
	if (!$("form").valid()){
		return false;
	}
	if(sdata.id){
		$.call("hospital_common.auditset.checkdept.update",sdata,function(s){
    		$.closeModal(s);
    	});
	}else{
		delete sdata.id;
		$.call("hospital_common.auditset.checkdept.insert",sdata,function(s){
    		$.closeModal(s);
    	});
	}
}

function cancel(){
	$.closeModal(false);
}
</script>