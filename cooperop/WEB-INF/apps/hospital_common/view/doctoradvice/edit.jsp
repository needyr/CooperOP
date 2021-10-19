<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑建议信息" ismodal="true">
	<s:row>
		<s:form id="form" label="建议信息">
			<s:toolbar>
				<s:button label="确认" onclick="save()" icon="glyphicon glyphicon-floppy-saved"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="建议名称" name="advicename" required="true" cols="1" value="${advicename}"></s:textfield>
				<s:switch label="是否启用配置"  name="isopen" onvalue="Y" offvalue="N" ontext="是" offtext="否" value="${isopen}"></s:switch>
			</s:row>
			<s:row>
				<s:textfield label="建议描述" name="description" required="false" cols="4" value="${description}"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		
	})

	function save(){
		var sdata=$("#form").getData();
		sdata.id='${param.id}'
		/* sdata.ts='${param.ts}';
		sdata.vs='${param.vs}';
		sdata.uid='${param.uid}'; */
		//空表单提交验证
    	if (!$("form").valid()){
    		return false;
    	}
    	//保存
    	$.call("hospital_common.doctoradvice.save",sdata,function(s){
    		$.closeModal(s);
    	});
	}

	//取消
    function cancel(){
    	$.closeModal(false);
    }
</script>