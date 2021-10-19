<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑交易配置信息" ismodal="true">
	<s:row>
		<s:form id="form" label="接口信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon="fa fa-save"></s:button>
				<s:button label="取消" onclick="cancel()" icon="fa fa-times"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="交易编码" name="trade_code" required="true" cols="1" value="${trade_code}"></s:textfield>
				<s:autocomplete label="接口编号" name="data_interface_code" action="hospital_common.trade.dataifs.query" text="${code}" params="{&#34;state&#34; : 1}" limit="10" editable="false" cols="1" value="${data_interface_code}" required="true" >
					<s:option value="$[code]" label="$[code]">
						$[code]　[$[name]]
					</s:option>
				</s:autocomplete>
				<s:switch label="状态" name="state" onvalue="1" offvalue="0" ontext="启用" offtext="停用" value="${state}"></s:switch>
			</s:row>
			<s:row>
				<s:select name="convert_way" label="数据转换方式" value="${convert_way}" disabled="disabled">
					<s:option value="PROC" label="SQL转换"></s:option>
					<s:option value="JAVA" label="JAVA转换"></s:option>
				</s:select>
				<s:autocomplete label="SQL处理方法" name="convert_deal" action="hospital_common.trade.proc.query" text="${proc_code}" params="{&#34;state&#34; : 1}" limit="10" editable="false" cols="2" value="${convert_deal}" required="true" >
					<s:option value="$[proc_code]" label="$[proc_code]">
						$[proc_code]　[$[description]]
					</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:select name="call_type" label="调用类型" value="${call_type}">
					<s:option value="INIT" label="[初始化]调用"></s:option>
					<s:option value="DS" label="[定时]调用"></s:option>
				</s:select>
				<s:textfield label="调用链接" name="interface_url" required="true" cols="2" value="${interface_url}"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea label="描述/备注" name="description" required="false" cols="3" value="${description}"></s:textarea>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
var trade_code = '${param.trade_code}';
$(function(){
	if(trade_code != ''){
		$('[name="trade_code"]').attr('disabled', 'true');
		$('[name="data_interface_code"]').attr('disabled', 'true');
	}
})

function save(){
	var sdata=$("#form").getData();
	if (!$("form").valid()){
		return false;
	}
	if(trade_code != ''){
		$.call("hospital_common.trade.config.update",sdata , function(s){
    		$.closeModal(true);
    	});
	}else{
		$.call("hospital_common.trade.config.insert",sdata,function(s){
    		$.closeModal(true);
    	});
	}
}

function cancel(){
	$.closeModal(false);
}
</script>