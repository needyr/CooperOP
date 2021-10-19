<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑信息" ismodal="true"  disloggedin="true">
	<s:row>
		<s:form id="form" label="编辑患者用药规则信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon="fa fa-save"></s:button>
				<s:button label="取消" onclick="cancel()" icon="fa fa-times"></s:button>
			</s:toolbar>
			<s:row>
				<s:switch label="加入名单" value="${condition}" name="condition" cols="1" onvalue="1" offvalue="0"  ontext="白名单" offtext="黑名单"  ></s:switch>
				<s:switch label="是否开启" value="${beactive}" name="beactive"  onvalue="1" offvalue="0"  ontext="开启" offtext="关闭"  ></s:switch>
			</s:row>
			<s:row>
				<input type="hidden" name="id" value="${id}">
				<input type="hidden" name="spbh" value="${spbh}">
				<input type="hidden" name="item" value="patient" title="患者">
				<input type="hidden" name="paramcode" value="${value}">
				<s:textfield cols="3" required="true" name="value" id="value_id" label="值" value="${value_name}"   placeholder="单击选择患者"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea label="提示信息" cols="3" value="${message}" required="true"  name="message" rows="2"></s:textarea>
			</s:row>
			<s:row>
				<s:autocomplete  label="问题类别" name="zdy_type" action="hospital_common.drugcontrol.queryRegulation" limit="7" editable="false" cols="2" text="${thirdt_name}" value="${zdy_type}" required="true">
						<s:option value="$[thirdt_code]" label="$[thirdt_name]">
							<span style="width:50px;display:block;float:left">$[thirdt_code]</span>
							<span style="width:300px;display:block;float:left">$[thirdt_name]</span>
						</s:option>
				</s:autocomplete>
				<s:autocomplete  label="提示等级" name="level" action="hospital_common.drugcontrol.queryCheckLevel"  editable="false" cols="1" text="${level_name}" value="${level}" required="true">
						<s:option value="$[level_code]" label="$[level_name]">
							<span style="width:50px;display:block;float:left">$[level_code]</span>
							<span style="width:60px;display:block;float:left">$[level_name]</span>
						</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:textarea label="备注" cols="3" value="${remark}" name="remark" rows="2"></s:textarea>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
$(function(){ 
	if('${param.drug_code}'){
		$('[name=spbh]').val('${param.drug_code}')
	}
	if ('${id}'){
		$('[name=id]').attr("disabled","true");
	}
	$("#value_id").css("cursor","pointer");
});

function save(){
	var sdata=$("#form").getData();
	if (!$("form").valid()){
		return false;
	}
	sdata.itemname=$("input[name=item]")[0].title;
	if ('${param.id}'){
		$.call("hospital_common.drugcontrol.update",sdata,function(s){
    		$.closeModal(s);
    	});
	}else{
		$.call("hospital_common.drugcontrol.insert",sdata,function(s){
			$.closeModal(s);
    	});
	}
}


$('[name=value]').click(function(){
	var code = $('[name=paramcode]').val();
	$.modal("/w/ipc/sample/choose/patient.html", "添加患者", {
		height: "550px",
		width: "50%",
		code: code,
		maxmin: false,
		callback : function(rtn) {
			if(rtn){
				var name = rtn.name.toString();
				var code = rtn.code.toString();
				$('[name=value]').val(name);
				$('[name=paramcode]').val(code);
			}
	    }
	});
});



function cancel(){
	$.closeModal(false);
}
</script>