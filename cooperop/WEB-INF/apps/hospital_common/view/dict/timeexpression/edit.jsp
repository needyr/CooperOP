<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑定时器信息" ismodal="true"  disloggedin="true">
	<s:row>
		<s:form id="form" label="时间表达式字典维护信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon="fa fa-save"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="p_key" cols="3" value="${p_key}"/>
			</s:row>
			<s:row>
				<s:textfield label="表达式" name="code" required="true" cols="3" value="${code}" ></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="名称" name="name" required="true" cols="3" value="${name}" ></s:textfield>
			</s:row>
			<s:row>
				<s:textarea label="任务简介" cols="3" placeholder="任务简介" name="remark"
					rows="2">${remark}</s:textarea>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
function save(){
	var sdata=$("#form").getData();
	if (!$("form").valid()){
		return false;
	}
	if(sdata.p_key){
		$.call("hospital_common.dict.timeexpression.update",sdata,function(s){
			if (s==2){
				$.message("时间表达式已创建，请重新输入！");
			}else{
		    	$.closeModal();
			}
    	});
	}else{
		$.call("hospital_common.dict.timeexpression.insert",sdata,function(s){
			if (s==2){
				$.message("时间表达式已创建，请重新输入！");
			}else{
		    	$.closeModal();
			}
    	});
	}
}

function cancel(){
	$.closeModal(false);
}
</script>