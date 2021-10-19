<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑系统配置信息" ismodal="true">
	<s:row>
		<s:form id="form" label="配置信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon="glyphicon glyphicon-floppy-saved"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="配置编号" name="code" required="true" cols="3" value="${code}" ></s:textfield>
			</s:row>
			<s:row>
				<s:select  label="所属产品" name="system_product_code" value="${param.system_product_code}" action="hospital_common.config.queryProduct" cols="3">
						<s:option value="$[product_code]" label="$[product_name]" ></s:option>
				</s:select>
			</s:row>
			<s:row>
				<s:textfield label="配置名称" name="name" required="true" cols="3" value="${name}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="值" name="value" required="true" cols="3" value="${value}"></s:textfield>
			</s:row>
				
			<s:row>
				<s:textarea label="描述" name="remark" required="false" cols="3" value="" height="3" >${remark}</s:textarea>
			</s:row>
				<%-- <s:textfield name="system_product_code" required="true" cols="2" value="precheck"  islabel="true"></s:textfield> --%>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	var code = '${param.code}';
	var param_sys_code='${param.system_product_code}';
	$(function(){
		if(param_sys_code){
			$("select[name='system_product_code']").attr("disabled","disabled");
		}
		if(code){
			$("input[name='code']").attr("disabled","disabled");
		}else{
			//判断重复
			$("input[name='code']").blur(function(){
				var sdata=$("#form").getData();
				$.call("hospital_common.config.edit",sdata,function(s){
					if(s!=null){
						$.message("配置编号已存在！请重新输入");
						$("input[name='code']").val("");
					}
				});
			});
		}
	});

function save(){
	var sdata=$("#form").getData();
	if (!$("form").valid()){
		return false;
	}
	if(code){
		$.call("hospital_common.config.update",sdata,function(s){
    		$.closeModal(s);
    	});
	}else{
		$.call("hospital_common.config.insert",sdata,function(s){
    		$.closeModal(s);
    	});
	}
}

function cancel(){
	$.closeModal(false);
}
</script>