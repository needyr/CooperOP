<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑接口信息" ismodal="true">
	<s:row>
		<s:form id="form" label="接口信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon="fa fa-save"></s:button>
				<s:button label="取消" onclick="cancel()" icon="fa fa-times"></s:button>
			</s:toolbar>
			<s:row>
				<s:switch label="状态" name="state" onvalue="1" offvalue="0" ontext="启用" offtext="停用" value="${state}"></s:switch>
				<s:switch label="初始化调用" name="is_init_call" onvalue="1" offvalue="0" ontext="启用" offtext="停用" value="${is_init_call}"></s:switch>
				<s:switch label="定时调用" name="is_ds_call" onvalue="1" offvalue="0" ontext="启用" offtext="停用" value="${is_ds_call}"></s:switch>
			</s:row>
			<s:row>
				<s:textfield label="目标数据表" name="code" required="true" cols="1" value="${code}"></s:textfield>
				<s:textfield label="中文名" name="name" value="${name}" required="true"></s:textfield>
				<s:textfield label="定时表达式" name="cycle_cron" required="true" cols="1" value="${cycle_cron}"></s:textfield>
			</s:row>
			<%-- <s:row>
				<s:textfield label="接口" name="interface_url" value="${interface_url}" required="true" cols="3"></s:textfield>
			</s:row> --%>
			<s:row>
				<s:textarea label="描述/备注" name="description" required="false" cols="3" value="${description}"></s:textarea>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
var code = '${param.code}';
$(function(){
	if(code != ''){
		$('[name="code"]').attr('disabled', 'true');
	}
})

function save(){
	var sdata=$("#form").getData();
	if (!$("form").valid()){
		return false;
	}
	if(code != ''){
		$.call("hospital_common.trade.dataifs.update",sdata , function(s){
    		$.closeModal(true);
    	});
	}else{
		$.call("hospital_common.trade.dataifs.insert",sdata,function(s){
    		$.closeModal(true);
    	});
	}
}

function cancel(){
	$.closeModal(false);
}
</script>