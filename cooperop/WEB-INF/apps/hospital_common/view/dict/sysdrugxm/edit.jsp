<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑药品属性信息" ismodal="true"  disloggedin="true">
	<s:row>
		<s:form id="form" label="药品属性信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon="fa fa-save"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="xmid" value="${xmid}"/>
				<s:textfield label="项目编号" name="xmbh" required="true" cols="1" value="${xmbh}" ></s:textfield>
				<s:select label="项目类型" required="true" name="xmtype" value="${xmtype}" cols="2">
					<s:option value="通用" label="通用"></s:option>
					<s:option value="PIVAS" label="PIVAS"></s:option>
				</s:select>
			</s:row>
			<s:row>
				<s:textfield label="项目名称" name="xmmch" required="true" cols="3" value="${xmmch}" ></s:textfield>
			</s:row>
			<s:row>
				<s:select label="数字类型" required="true" name="fdtype" cols="2" value="${fdtype}">
					<s:option value="浮点" label="浮点"></s:option>
					<s:option value="整数" label="整数"></s:option>
					<s:option value="字符" label="字符"></s:option>
				</s:select>
				<s:switch label="是否开启" value="${empty beactive ? '是' : beactive}"  name="beactive" onvalue="是" offvalue="否" ontext="是" offtext="否" ></s:switch>
			</s:row>
			<s:row>
				<s:textfield label="拼音码" name="pym"  cols="2" value="${pym}" ></s:textfield>
				<s:textfield label="单位" name="xmdw"  cols="1" value="${xmdw}" ></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
$(function (){
	debugger
	//修改操作禁止修改
	var xmid = '${param.xmid}';
	if(xmid){
		$("input[name='xmbh']").attr("disabled","disabled");
	}
});
function save(){
	var sdata=$("#form").getData();
	if (!$("form").valid()){
		return false;
	}
	if('${param.xmid}'){
		$.call("hospital_common.dict.sysdrugxm.update",sdata,function(s){
			if (s==2){
				$.message("该药品属性已创建，请重新输入！");
			}else{
		    	$.closeModal();
			}
    	});
	}else{
		sdata.isadd="true";
		$.call("hospital_common.dict.sysdrugxm.insert",sdata,function(s){
			if (s==2){
				$.message("该药品编号已创建，请重新输入！");
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