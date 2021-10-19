<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑交易配置信息" ismodal="true">
	<s:row>
		<s:form id="form" label="链接信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon="fa fa-save"></s:button>
				<s:button label="取消" onclick="cancel()" icon="fa fa-times"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="编码" name="db_code" required="true" cols="1" value="${db_code}"></s:textfield>
				<s:textfield label="数据库" name="db_name" value="${db_name}" required="true"></s:textfield>
				<s:switch label="状态" name="state" onvalue="1" offvalue="0" ontext="启用" offtext="停用" value="${state}"></s:switch>
			</s:row>
			<s:row>
				<s:textfield label="配置名" name="db_config_name" value="${db_config_name}" required="true"></s:textfield>
				<s:select name="db_type" label="数据库类型" value="${db_type}">
					<s:option value="oracle" label="oracle"></s:option>
					<s:option value="sqlserver" label="sqlserver"></s:option>
					<s:option value="mysql" label="mysql"></s:option>
				</s:select>
			</s:row>
			<s:row>
				<s:textfield label="数据库连接" name="db_url" value="${db_url}" required="false"></s:textfield>
				<s:textfield label="用户名" name="db_user" required="false" cols="1" value="${db_user}"></s:textfield>
				<s:textfield label="密码" name="db_pwd" value="${db_pwd}" required="false"></s:textfield>
			</s:row>

			<s:row>
				<s:textarea label="描述/备注" name="description" required="false" cols="3" value="${description}"></s:textarea>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
var db_code = '${param.db_code}';
$(function(){
	if(db_code != ''){
		$('[name="db_code"]').attr('disabled', 'true');
	}
})

function save(){
	var sdata=$("#form").getData();
	if (!$("form").valid()){
		return false;
	}
	if(db_code != ''){
		$.call("hospital_common.trade.db.update",sdata , function(s){
    		$.closeModal(true);
    	});
	}else{
		$.call("hospital_common.trade.db.insert",sdata,function(s){
    		$.closeModal(true);
    	});
	}
}

function cancel(){
	$.closeModal(false);
}
</script>