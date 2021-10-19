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
				<s:textfield label="服务编号" name="data_webservice_code" 
					id="id_code1" required="true">${pageParam.data_webservice_code }</s:textfield>
				<s:textfield label="交易编号" name="code" id="id_code2"
					required="true">${code }</s:textfield>
				<s:textfield label="交易名称" name="name">${name }</s:textfield>
				<%-- <s:textfield label="返回调用存储过程" name="execute_procedure"
					placeholder="返回调用存储过程 ">${execute_procedure }</s:textfield> --%>
				<s:textfield label="初始化存储过程" name="param_procedure"
					placeholder="初始化存储过程">${param_procedure }</s:textfield>
				<s:textfield label="触发周期" name="cycle_cron" dblaction="precheck/webmethod/display">${cycle_cron }</s:textfield>
				<s:switch label="初始化运行" value="${can_manual}" name="can_manual" onvalue="1" offvalue="0" ontext="是" offtext="否" ></s:switch>
				<s:radio label="所属类别" name="p_type" value="${p_type }" cols="2">
					<s:option label="无" value=""></s:option>
					<s:option label="全部" value="0"></s:option>
					<s:option label="医嘱" value="1"></s:option>
					<s:option label="处方" value="2"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<s:textarea label="描述" name="description" cols="4" rows="3">${description }</s:textarea>
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
		if ('${pageParam.code }') {
			$("#id_code2").attr("disabled",true); 
		}
	});
	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		var path = ('${pageParam.data_webservice_code }' &&
				'${pageParam.code }') ? 
				"hospital_common.webmethod.update" : "hospital_common.webmethod.insert";
		$.call(path, $("#dataForm").getData(), function(rtn) {
				$.closeModal(true);
		});
	}
</script>
