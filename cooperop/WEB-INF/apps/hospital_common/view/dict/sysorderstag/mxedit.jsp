<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="项目信息" ismodal="true"  disloggedin="true">
	<s:row>
		<s:form id="form" label="项目信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon="fa fa-save"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" id="id" name="id" value="${id}"/>
				<input type="hidden" id="ordertagid" name="ordertagid" value="${ordertagid}"/>
			</s:row>
			<s:row>
				<s:autocomplete  id="ssks" label="项目ID" name="xmid" action="hospital_common.dict.sysdrugxm.query" limit="10" editable="false" cols="2" value="${xmid}" required="true">
					<s:option value="$[xmid]" label="$[xmid]">
						<span style="float:left;display:block;width:50px;">$[xmid]</span>
						<span style="float:left;display:block;width:50px;">$[xmbh]</span>
						<span style="float:left;display:block;width:100px;">$[xmmch]</span>
					</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:textfield label="项目编号" name="xmbh" disabled="disabled" value="${xmbh}"  required="true" cols="1" ></s:textfield>
				<s:textfield label="项目名称" name="xmmch" disabled="disabled" value="${xmmch}"  required="true" cols="2" ></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="条件" disabled="disabled"   name="tiaojian"  cols="1" value="${tiaojian_name}" dbl_action='zl_select_ordersvaluetiaojian,ordersvaluetiaojian'></s:textfield>
				<%-- <s:textfield label="条件" name="tiaojian" required="true" cols="1" value="${tiaojian_name}" ></s:textfield> --%>
				<s:autocomplete   label="值 " editable="true" name="value" id="value" required="true" action="hospital_common.dict.sysdrugxm.queryXmValue" limit="5" params="{&#34;XMID&#34;:&#34;${param.xmid}&#34;}" cols="2" value="${value}" >
					<s:option value="$[value]" label="$[value]">
						<span style="float:left;display:block;width:50px;">$[value]</span>
					</s:option>
				</s:autocomplete>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
$(function(){
	var ordertagid='${param.ordertagid}';
	if(ordertagid==''){
	}else{
		$("#ordertagid").val(ordertagid);
	}
	$("#ssks").change(function(){
		getdrugXM($(this).val());
	});
	
	var id='${param.id}';
	if(id==''){
	}else{
		$("#id").val(id);
		$("input[name='xmid']").attr("disabled","disabled");
	}
})

function save(){
	var sdata=$("#form").getData();
	if (!$("form").valid()){
		return false;
	}
	var tiaojian=$("input[name='tiaojian']").val();
	if (tiaojian==""){
		$.message("请选择条件！");
		return false;
	}
	if('${param.id}'){
		$.call("hospital_common.dict.sysorderstag.ordersValueUpdate",sdata,function(s){
			$.closeModal();
			
    	});
	}else{
		$.call("hospital_common.dict.sysorderstag.ordersValueInsert",sdata,function(s){
			$.closeModal();
    	});
	}
}

function getdrugXM(xmid){
	 $.call("hospital_common.dict.sysdrugxm.edit",{xmid :xmid},function(rtn){
		if(rtn==null||typeof(rtn) == "undefined"){
			$("input[name='xmbh']").val("");
			$("input[name='xmmch']").val("");
		}else{
			$("input[name='xmbh']").val(rtn.xmbh);
			$("input[name='xmmch']").val(rtn.xmmch);
			$("[name='value']").params({'XMID':rtn.xmid});
		}
	}); 
}

function cancel(){
	$.closeModal(false);
}
</script>