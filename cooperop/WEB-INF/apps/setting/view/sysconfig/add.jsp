<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
	<s:row>
		<s:form border="0" id="myform" fclass="portlet light bordered" label="系统设置">
			<s:toolbar>
				<s:button label="保存" icon="fa fa-save" onclick="save();"></s:button>
			</s:toolbar>
			<s:row>
				<s:switch label="通用参数" name="common_p" value="${system_product_code eq 'cooperop'?'Y':'N' }"
					onvalue="Y" offvalue="N" onchange="common_p();" islabel="${empty code?false:true"></s:switch>
				<s:switch label="开放参数" name="is_open" value="${is_open}"
					onvalue="Y" offvalue="N"></s:switch>
			</s:row>
			<s:row id="sys_pro">
				<s:radio label="选择所属产品" name="system_product_code" value="${system_product_code }" islabel="${empty code?false:true"></s:radio>
			</s:row>
			<s:row>
				<s:textfield cols="2" label="配置名称" name="name" value="${name}" islabel="${empty code?false:true" maxlength="64" required="true"></s:textfield>
				<s:textfield cols="2" label="配置编码" name="code" value="${code}" islabel="${empty code?false:true" required="true"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield cols="4" label="配置值" name="value" value="${value }" required="true"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea cols="4" label="备注" name="remark" required="true">${remark }</s:textarea>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
$(document).ready(function(){
	common_p();
});
	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		var d = $("#myform").getData();
		if(d.common_p == 'Y'){
			d.system_product_code='cooperop';
		}
		delete d.common_p;
		if('${pageparam.code}'){
			d.code = '${pageparam.code}';
		}
		$.call("setting.sysconfig.save", d, function(rtn) {
			if (rtn) {
				$.closeModal(true);
			}
		});
	}
	function common_p(){
		var d = $("#myform").getData();
		if(d.common_p == 'Y'){
			$("#sys_pro").hide();
		}else{
			$("#sys_pro").show();
		}
	}
</script>
