<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑科室信息" >
	<s:row>
		<s:form id="form" label="科室信息">
			<s:toolbar>
				<s:button label="保存" onclick="save()" icon="glyphicon glyphicon-floppy-saved"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<s:autocomplete id="toinfo" label="科室名称" name="name" action="hospital_common.department.queryHisDept" limit="10" cols="1" value="${name}" required="true" >
					<s:option value="$[dept_code]" label="$[dept_name]" >$[dept_name]</s:option>
				</s:autocomplete>
				<s:textfield label="科室编码" name="code" required="true" cols="1" value="${code}" />
				<s:textfield label="简称" name="simplename" required="false" cols="1" value="${simplename}" />
			</s:row>
			<s:row>
				<s:switch label="科室类型"  name="outp_or_inp" onvalue="1" offvalue="0" ontext="门诊" offtext="住院" value="${outp_or_inp}" />
				<s:switch label="是否启用"  name="state" onvalue="1" offvalue="0" ontext="是" offtext="否" value="${state}" />
			</s:row>
			<s:row>
				<s:textfield label="科室属性" name="dept_attr" required="false" cols="4" value="${dept_attr}" />
			</s:row>
			<s:row>
				<s:textfield label="描述" name="description" required="false" cols="4" value="${description}" />
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		 $("#toinfo").on("change",function(){
			var no = $("#form").getData().name;
			$.call("hospital_common.department.getHisDept",{"dept_code":no},function(rtn){
				$("[name='code']").setData(rtn.dept_code);
				$("[name='simplename']").setData(rtn.jianchen);
				$("[name='outp_or_inp']").setData(rtn.outp_or_inp);
				$("[name='dept_attr']").setData(rtn.dept_attr);
			});
		}) 
	})

	function save(){
		var sdata=$("#form").getData();
		console.log(sdata);
		sdata.dept_code=sdata.name;
		sdata.name=$("#toinfo").val();
		if('${param.id}'!=null||typeof('${param.id}')!='undefind'){
			sdata.id='${param.id}';
		}
    	if (!$("form").valid()){
    		return false;
    	}
    	$.call("hospital_common.department.save",sdata,function(s){
    		$.closeModal(s);
    	});
	}

	//取消
    function cancel(){
    	$.closeModal(false);
    }
</script>