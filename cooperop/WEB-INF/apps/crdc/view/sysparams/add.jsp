<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
	<s:row>
		<s:form label="参数设置"  id="myform">
			<s:toolbar>
				<s:button label="保存" onclick="save()"></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="id" value="${id }"/>
				<s:textfield label="参数名" name="name" value="${name}" maxlength="64" cols="2"></s:textfield>
				<s:textfield label="参数" name="param_" value="${param_}" required="true" maxlength="32"></s:textfield>
				<s:radio label="参数类型" name="type" value="${type }" required="true" cols="3">
					<s:option label="人员" value="U" ></s:option>
					<s:option label="部门" value="D"></s:option>
					<s:option label="全公司" value="A"></s:option>
					<s:option label="岗位" value="P"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<s:radio cols="4" label="查询产品" name="system_product_code" action="application.common.listProducts" >
					<s:option value="$[code]" label="$[name]"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<s:textfield label="每隔" name="query_frequency" value="${empty query_frequency?0:query_frequency }" min="0" max="99"></s:textfield>
				<div crid="" class="cols1 nolabel" style="width:60px!important">
					<div class="control-content">
						<select value="${query_frequency_unit }" class="form-control " ctype="select" islabel="false" isherf="false" onchange="showHide();" 
							encryption="false" name="query_frequency_unit" cols="1">
							<option value="D">日</option>
							<option value="W">周</option>
							<option value="M">月</option>
							<option value="Y">年</option>
						</select>
					</div>
				</div>
				
				<div class="cols" id="mdiv" style="display:none;">
					<s:select label="执行月" name="exe_query_time_m" value="${exe_query_time_m}">
						<s:option label="一月" value="1"></s:option>
						<s:option label="二月" value="2"></s:option>
						<s:option label="三月" value="3"></s:option>
						<s:option label="四月" value="4"></s:option>
						<s:option label="五月" value="5"></s:option>
						<s:option label="六月" value="6"></s:option>
						<s:option label="七月" value="7"></s:option>
						<s:option label="八月" value="8"></s:option>
						<s:option label="九月" value="9"></s:option>
						<s:option label="十月" value="10"></s:option>
						<s:option label="十一月" value="11"></s:option>
						<s:option label="十二月" value="12"></s:option>
					</s:select>
				</div>
				<div class="cols" id="ddiv" style="display:none;">
					<s:textfield label="执行日" name="exe_query_time" value="${exe_query_time}" placeholder="执行日" min="1" max="31"></s:textfield>
				</div>
				<div class="cols" id="wdiv" style="display:none;">
					<s:select label="执行日" name="exe_query_time_w" value="${exe_query_time}">
						<s:option label="周一" value="2"></s:option>
						<s:option label="周二" value="3"></s:option>
						<s:option label="周三" value="4"></s:option>
						<s:option label="周四" value="5"></s:option>
						<s:option label="周五" value="6"></s:option>
						<s:option label="周六" value="7"></s:option>
						<s:option label="周日" value="1"></s:option>
					</s:select>
				</div>
			</s:row>
			<s:row>
				<s:textfield label="查询前" name="query_times" value="${query_times }" min="0" max="99" defaultValue="1"></s:textfield>
				<%-- <s:select name="query_unit" value="${query_unit }">
					<s:option label="日" value="D"></s:option>
					<s:option label="周" value="W"></s:option>
					<s:option label="月" value="M"></s:option>
					<s:option label="年" value="Y"></s:option>
				</s:select> --%>
				<div crid="" class="cols1 nolabel" style="width:60px!important">
					<div class="control-content">
						<select value="${query_unit }" class="form-control " ctype="select" islabel="false" isherf="false" onchange="showHide();" 
							encryption="false" name="query_unit" cols="1">
							<option value="D">日</option>
							<option value="W">周</option>
							<option value="M">月</option>
							<option value="Y">年</option>
						</select>
					</div>
				</div>
				<s:textfield islabel="true" value="数据"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea label="详细描述" name="description" value="${description }" maxlength="1000" placeholder="不能超过1000个字" rows="3" cols="4"></s:textarea>
			</s:row>
			<s:row>
				<s:textfield label="执行方法" cols="2" name="exe_scheme" value="${exe_scheme}" required="true" maxlength="32"></s:textfield>
				<s:switch label="状态" value="${state }" name="state" onvalue="1" offvalue="0"  ontext="是" offtext="否"></s:switch>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
$(document).ready(function(){
	showHide();
})
function save() {
	if (!$("form").valid()) {
		return false;	
	}
	var data = $(document).getData();
	if(data.query_frequency_unit=='W'){
		data.exe_query_time = data.exe_query_time_w;
		delete data.exe_query_time_w;
	}
    $.call("crdc.sysparams.save", data, function(data) {
		if (data.result=="success") {
			$.closeModal(true);
		}
	});
}
function showHide(){
	var data = $(document).getData();
	$("#mdiv").hide();
	$("#ddiv").hide();
	$("#wdiv").hide();
	if(data.query_frequency_unit=='W'){
		$("#wdiv").show();
	}else if(data.query_frequency_unit=='Y'){
		$("#mdiv").show();
		$("#ddiv").show();
	}else if(data.query_frequency_unit=='M'){
		$("#ddiv").show();
	}
}
</script>
