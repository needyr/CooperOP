<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="诊断信息" ismodal="true">
	<s:row>
		<s:form id="form" label="诊断信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon=""></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="spbh" value="${param.item_code}">
				<input type="hidden" name="fdname" value="1">
				<input type="hidden" name="xmbh" value="${xmbh}">
				<s:textfield label="项目名称" disabled="disabled" name="xmmch"  cols="1" value="${xmmch}" dbl_action='zl_select_sys_shengfsyz_01,sys_shengfangzl_syz'></s:textfield>
				<s:textfield label="公式" name="formul" dbl_action="zl_select_shengfsyz_02,shengfangzl_syz" value="${formul_name}"></s:textfield>
				<s:textfield label="值" name="value" dbl_action="zl_select_shengfsyz_05,shengfangzl_syz" value="${value}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="诊断编码" name="diagnosis_code" disabled="disabled" value="${diagnosis_code}" ></s:textfield>
				<s:textfield label="诊断名称" name="diagnosis_name" disabled="disabled"  cols="1" value="${diagnosis_name}"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	function save(){
			if (!$("form").valid()){
		   		return false;
		   	}
			var sdata = $('#form').getData();
			sdata.id = '${id}';
			sdata.parent_id = '${param.parent_id}';
			$.call("hospital_common.imiccustomre.ybxianz.saveDia",sdata,function(s){
				$.closeModal(s);
	    	});
	}

	//取消
   function cancel(){
		$.closeModal(false);
   }
	
</script>