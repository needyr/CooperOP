<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="接口更新" ismodal="true">

	<s:row>
		<s:form label="接口信息" id="dataForm">
			<s:toolbar>
				<s:button label="保存" icon="fa fa-edit" onclick="save();"></s:button>
				<s:button label="取消" icon="fa fa-ban" onclick="$.closeModal(true);"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="服务编号" id="id_code1" required="true" name="data_service_code">${pageParam.data_service_code }</s:textfield>
				<s:textfield label="交易编号" id="id_code2" name="code" required="true">${pageParam.code }</s:textfield>
				<s:textfield label="交易名称" name="name">${name }</s:textfield>
				<s:textfield label="存储过程" name="execute_procedure">${execute_procedure }</s:textfield>
				<s:textarea label="交易描述" name="description" cols="4"
					>${description }</s:textarea>
			</s:row>
		</s:form>
	</s:row>
	
</s:page>
<%------------------------------------------
				JAVASCRIPT
-------------------------------------------%>
<script type="text/javascript">

	$(document).ready(function(){
		if ('${pageParam.data_service_code}') {
			$('#id_code1').attr('disabled', true);
		}
		if ('${pageParam.code}') {
			$('#id_code2').attr('disabled', true);
		}
	});
	function save() {
		if (!$("form").valid()) {
			return false;
		}
		
		var  path = ('${pageParam.code}' && '${pageParam.data_service_code}') ? 
				"hospital_common.method.update" : "hospital_common.method.insert";
		$.call(path, $("#dataForm").getData(), function(rtn){
			if (rtn) {
				$.closeModal(true);
			}
		});
	}
</script>
