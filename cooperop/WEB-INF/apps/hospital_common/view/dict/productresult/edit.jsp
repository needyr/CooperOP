<%@page import="java.util.Date"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑产品当前审查结果字典信息" ismodal="true">
	<s:row>
		<s:form id="form" label="产品当前审查结果字典信息">
			<s:toolbar>
				<s:button label="保存" onclick="saveOrUpdate()" icon="glyphicon glyphicon-floppy-saved"/>
				<s:button label="取消" onclick="cancel()" icon="glyphicon glyphicon-remove"/>
			</s:toolbar>
			<s:row>
				<s:row>
				    <c:if test="${!empty param.p_key}">
				        <s:textfield label="ID" name="p_key" cols="4" value="${p_key}"/>
				    </c:if>
					<s:textfield label="管理状态" name="manage_state" value="${manage_state}" required="true" cols="3"/>
					<s:textfield label="状态名称" name="manage_state_name" value="${manage_state_name}" required="true" cols="3"/>
					<s:autocomplete label="所属产品" name="product_code" value="${product_code}" text="${name}" required="true" cols="3" action="crdc.product.query" limit="10">
					    <s:option label="$[name]" value="$[code]">
						    <span style="display:block;width:110px;">$[name]</span>
					    </s:option>
				    </s:autocomplete>
					<s:textfield label="优先级" name="priority" value="${priority}" required="true" cols="3" placeholder="大于等于0纯数字"/>
					<s:textfield label="状态sql表示" name="sql_record_flag" value="${sql_record_flag}" cols="3"/>
				    <s:switch label="能否用药" name="use_flag" value="${use_flag}" onvalue="1" offvalue="0" ontext="能" offtext="不能"/>
					<s:textfield label="最终审查结果" name="final_state" value="${final_state}" cols="3"/>
					<s:switch label="用于智能审查" name="usefor_manage" value="${usefor_manage}" onvalue="1" offvalue="0" ontext="是" offtext="否"/>
				    <s:textfield label="审查说明flag" name="flag" value="${flag}" cols="3"/>
				    <s:textarea label="描述" name="remark" value="${remark}" cols="3"/>
				</s:row>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		if('${param.p_key}'!=null&&typeof('${param.p_key}')!='undefind'&&'${param.p_key}'!=''){
			$("input[name='p_key']").attr("disabled","disabled");
		}
	});

	function saveOrUpdate(){
		if (!$("form").valid()){return false;}//必填
		var data=$("#form").getData();
		var id = '${param.p_key}';
		if(id!=null&&typeof(id)!='undefind'&&id!=''){//id不为空,修改
			$.call("hospital_common.dict.productresult.update",data,function(s){
				if(s==1){$.closeModal(s);}
	    	});
		}else{//为空，新增
			$.call("hospital_common.dict.productresult.save",data,function(s){
				if(s==1){$.closeModal(s);}
	   		});
		}
	}

	//取消
    function cancel(){
    	$.closeModal(false);
    }
</script>