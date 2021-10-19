<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="参数表更新" ismodal="true">

	<s:row>
		<s:form label="参数表信息" id="dataForm">
			<s:toolbar>
				<s:button label="保存" icon="fa fa-edit" onclick="save();"></s:button>
				<s:button label="取消" icon="fa fa-ban" onclick="$.closeModal();"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="服务编号" id="id_code1" required="true" name="data_service_code">${pageParam.data_service_code }</s:textfield>
				<s:textfield label="交易编码" id="id_code2" required="true" name="data_service_method_code">${pageParam.data_service_method_code }</s:textfield>
				<s:textfield label="表名" placeholder="参数存储表名" required="true" id="id_code3" name="table_name">${table_name }</s:textfield>
				<s:select label="参数类型" name="type" value="${type }">
					<s:option label="in" value="in"></s:option>
					<s:option label="out" value="out"></s:option>
				</s:select>
				<s:autocomplete limit="10" label="父表" name="parent_table_name" value="${parent_table_name }" 
					placeholder="存储表对应父表" action="hospital_common.reft.queryExceptColumn">
					<s:option label="$[table_name]" value="$[table_name]">$[table_name]</s:option>
				</s:autocomplete>
				<s:textfield label="参数名称" placeholder="定义参数名称" name="param_name">${param_name }</s:textfield>
			<%--	<s:textfield label="参数加密" name="encrypt_fields" placeholder="','分割多个字段名">${encrypt_fieldss }</s:textfield>
			 --%>
			</s:row>
		</s:form>
	</s:row>
	
</s:page>
<%---------------------------------------
			JAVASCRIPT
---------------------------------------%>
<script type="text/javascript">

	$(document).ready(function() {
		if ('${pageParam.data_service_code}') {
			$('#id_code1').attr('disabled', true);
		}
		if ('${pageParam.data_service_method_code}') {
			$('#id_code2').attr('disabled', true);
		}
		if ('${pageParam.table_name}') {
			$('#id_code3').attr('disabled', true);
		}
	});
	function save() {
		if (!$("form").valid()) {
			return false;
		}
		var path = ('${pageParam.data_service_code}' && '${pageParam.data_service_method_code}'
					&& '${pageParam.table_name}') ? "hospital_common.reft.update" : "hospital_common.reft.insert";
		$.call(path, $("#dataForm").getData(), function(rtn){
			if (rtn) {
				$.closeModal(true);
			}
		});
	}
</script>
