<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑定时器信息" ismodal="true"  disloggedin="true">
	<s:row>
		<s:form id="form" label="定时器信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon="fa fa-save"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="comm_p_key" cols="3" value="${p_key}"/>
				<input type="hidden" name="instance" cols="3" value="${instance}"/>
			</s:row>
			<s:row>
				<s:textfield label="编号" id="instance" name="scheduleinstance" required="true"  cols="3" value="${instance}" ></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="名称" id="name" name="name" required="true"  cols="3" value="${name}" ></s:textfield>
			</s:row>
			<%-- <s:row>
				<s:textfield label="所属产品" id="product_code" required="true" name="product_code"  cols="3" value="${product_code}" ></s:textfield>
			</s:row> --%>
			<s:row>
				<s:autocomplete label="所属产品" name="product_code" required="true" value="${code}" id="product_code" text="${product_code}" cols="3" action="hospital_common.qschedule.queryProduct" limit="10">
					<s:option value="$[code]" label="$[code]">
						<span style="display:block;width:110px;">$[code]</span>
					</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:autocomplete label="执行周期" name="time_expression_p_key" required="true" value="${sys_time_expression_p_key}" id="cycle" text="${sys_time_expression_name}(${code})" cols="3" action="hospital_common.dict.timeexpression.query" limit="10">
					<s:option value="$[p_key]" label="$[name]($[code])">
						<span style="float:left;display:block;width:183px;">$[name]</span>
						<span style="float:left;display:block;width:100px;">$[code]</span>
					</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:textfield label="定时器处理类" id="classz" name="classz" required="true"  cols="3" ></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="合法授权的PC" id="mac" name="mac" disabled="true" value="00:21:97:AF:58:3L" cols="3" ></s:textfield>
			</s:row>
			<s:row>
				<s:textarea label="任务"  id="description" cols="3" placeholder="任务简介" name="description"
					rows="2">${remark}</s:textarea>
			</s:row>
			<s:row>
				<s:switch label="状态" value="${state}" name="state" onvalue="1" offvalue="0"  ontext="开启" offtext="停用"></s:switch>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
$(function(){ 
	if ('${p_key}'){
		$("#name").attr("disabled","true");
		$("#product_code").attr("disabled","true");
		$("#description").attr("disabled","true");
		$("#classz").attr("disabled","true");
		$("#classz").attr("value","********");
		$("#instance").attr("disabled","true");
	}else{
		$("#cycle")[0].value="";
	}
});

function save(){
	debugger
	var cycle=$("#cycle")[0].value;
	var scronVar=cycle.substring(cycle.indexOf("(")+1,cycle.indexOf(")"));
	var scron;
	var sdata=$("#form").getData();
	sdata.scron=scronVar;
	if (!$("form").valid()){
		return false;
	} 
	
	if (sdata.comm_p_key!=""){
		$.call("hospital_common.qschedule.updateByCode",sdata,function(s){
    		$.closeModal(s);
    	});
	}else{
		$.call("hospital_common.qschedule.insert",sdata,function(s){
			if (s==2){
				$.message("定时器编号或定时器处理类重复，请重新输入！");
			}else{
	    		$.closeModal(s);
			}
    	});
	}
}

function cancel(){
	$.closeModal(false);
}
</script>