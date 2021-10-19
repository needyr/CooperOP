<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>

<s:page title="属性值维护" >
		<s:form border="0" id="form" label="药品信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon="fa fa-save"></s:button>
				<s:button label="取消" onclick="cancel()" icon="fa fa-times"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="项目ID"  name="xmid" id="xmmch03" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="项目名"  name="xmmch" id="xmmch01" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="值" name="value" cols="2" id="value01" ></s:textfield>
			</s:row>
		</s:form>
</s:page>
<script type="text/javascript">
	$(function(){
		var xmmch='${param.xmmch}';
		$("#xmmch01").val(xmmch);
		$("#xmmch01").attr("disabled","disabled");
		$("#xmmch03").val('${param.xmid}');
		$("#xmmch03").attr("disabled","disabled");
		if(xmmch=="配置简称"){
			$("#div01").show();
		}
	});
	function save(){
		if($("#value01").val()){
			var value = $("#value01").val();
			var xmmch='${param.xmmch}';
			$.call("hospital_common.tpn.xiangmuwh.addXmValue", {"xmmch": xmmch,"value":value,"xmid":'${param.xmid}'}, function(rtn){
				$.closeModal(true);
			},null,{async:false});
		}else{
			alert("请输入值");
		}
	}
	function cancel(){
		$.closeModal(false);
	}
	
</script>