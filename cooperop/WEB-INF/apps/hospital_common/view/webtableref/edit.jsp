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
				<s:button label="取消" icon="fa fa-ban" onclick="$.closeModal(true);"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="服务编号" name="data_webservice_code" 
					id="id_code1" required="true">${pageParam.data_webservice_code }</s:textfield>
				<s:textfield label="交易编号" name="data_webservice_method_code" id="id_code2"
					required="true">${pageParam.data_webservice_method_code }</s:textfield>
				<s:textfield label="表名" name="table_name" id="id_code3"
					required="true" placeholder="参数存储表名">${table_name }</s:textfield>
				<s:select label="传参类型" name="type" value="${type }">
					<s:option label="in" value="in" />
					<s:option label="out" value="out" />
				</s:select>
				<s:autocomplete limit="10" id="id_parent_table_name" label="父表" placeholder="存储表对应父表"
					name="parent_table_name" value="${parent_table_name }" action="hospital_common.webtableref.queryExceptColumn">
					<s:option label="$[table_name]" value="$[table_name]">$[table_name]</s:option>
				</s:autocomplete>
				<s:textfield id="id_param_name" label="参数名称" name="param_name"
					placeholder="定义参数名称">${param_name }</s:textfield>
			</s:row>
		</s:form>
	</s:row>
	
</s:page>
<%----------------------------------------
	        JAVASCRIPT
-----------------------------------------%>
<script type="text/javascript">

	$(function(){
		if ('${pageParam.data_webservice_code }') {
			$("#id_code1").attr("disabled",true); 
		}
		if ('${pageParam.data_webservice_method_code }') {
			$("#id_code2").attr("disabled",true);
		}
		if ('${pageParam.table_name }') { 
			$("#id_code3").attr("disabled",true); 
		}	
	});
	
	function save() {
		if ($("#id_parent_table_name").val() && !$("#id_param_name").val()) {
			$.message("子表必须要有定义参数名称！");
			return false;
		}
		if (!$("form").valid()) {
			return false;	
		}
		var path = ('${pageParam.data_webservice_code }' &&
				'${pageParam.data_webservice_method_code }' &&
				'${pageParam.table_name }') ? "hospital_common.webtableref.update" : 
					"hospital_common.webtableref.insert";
		$.call(path, $("#dataForm").getData(), function(rtn) {
				$.closeModal(true);
		});
	}
</script>
