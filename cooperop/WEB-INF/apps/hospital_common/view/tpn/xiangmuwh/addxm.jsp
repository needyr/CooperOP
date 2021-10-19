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
				<s:textfield label="类别名"  name="class_name" id="name01" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="项目编号"  name="xmbh" id="xmbh01" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="项目名称" name="xxmch" cols="2" id="xmmch01" required="true"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="单位" name="xmdw" cols="2" id="xmdw01"></s:textfield>
			</s:row>
			<s:row>
				<s:select id="fdtype02" name="fdtype" label="值类型" cols="2" >
					<s:option label="字符" value="字符"></s:option>
					<s:option label="整数" value="整数"></s:option>
					<s:option label="浮点" value="浮点"></s:option>
				</s:select>
			</s:row>
		</s:form>
</s:page>
<script type="text/javascript">
	$(function(){
		var name='${param.class_name}';
		$("#name01").val(name);
		$("#name01").attr("disabled","disabled");
	});
	
	function save(){
		if($("#xmmch01").val()){
			var xmmch=$("#xmmch01").val();
			var xmdw=$("#xmdw01").val();
			var fdtype=$("#fdtype02").val();
			var xmbh=$("#xmbh01").val();
			var code='${param.class_code}';
			var d={'xmmch':xmmch,
					'xmdw':xmdw,
					'fdtype':fdtype,
					'class_code':code,
					'xmbh':xmbh};
			console.dir(d);
			$.call("hospital_common.tpn.xiangmuwh.addXm", d, function(rtn){
				$.closeModal(true);
			},null,{async:false});
		}else{
			alert("请输入项目名称");
		}
	}
	function cancel(){
		$.closeModal(false);
	}
	
	
	/* 
	
	if($("#value01").val()){
			var value = $("#value01").val();
			var xmmch='${param.xmmch}';
			$.call("pivas_common.xiangmuwh.addXmValue", {"xmmch": xmmch,"value":value}, function(rtn){
				$.closeModal(true);
			});
		}else{
			alert("请输入值");
		}
	
	
	*/
</script>