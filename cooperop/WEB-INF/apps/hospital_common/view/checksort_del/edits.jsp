<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑参数信息" disloggedin="true">
	<s:row>
		<s:form id="form" label="分类信息">
			<s:toolbar>
				<s:button label="确认" onclick="save()" icon="glyphicon glyphicon-ok-sign"></s:button>
				<s:button label="取消" onclick="cancel()"></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="sort_id" value="${sort_id}"/>
				<s:textfield label="分类名称" name="sort_name" required="true" cols="2" value="${sort_name}"></s:textfield>
				<s:textfield label="排序值" name="sort_num" required="true" cols="1" value="${sort_num}"></s:textfield>
			</s:row>
			<s:row>
				<s:radio  label="拦截级别" name="interceptor_level" required="true"
					cols="3" value="${interceptor_level}" action="hospital_common.checksort.queryLevel">
					<s:option value="$[sys_check_level]" label="$[sys_check_level_name]"></s:option>
					<s:option value="99" label="不拦截"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<s:radio  label="提示级别" name="info_level" required="true"
					cols="3" value="${info_level}" action="hospital_common.checksort.queryLevel">
					<s:option value="$[sys_check_level]" label="$[sys_check_level_name]"></s:option>
					<s:option value="99" label="不提示"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<s:select label="合理用药分类" name="type" cols="2" value="${type}" >
					<s:option value="1" label="全院用药审查" ></s:option>
					<s:option value="2" label="医院自定义用药审查"></s:option>
					<s:option value="3" label="HIS审查"></s:option>
				</s:select>
				<s:switch label="是否启用" name="state" onvalue="Y" offvalue="N" ontext="是" offtext="否" value="${state}"></s:switch>
			</s:row>
			<s:row>
				<s:switch label="门诊是否审查" name="outpatient_check" onvalue="Y" offvalue="N" ontext="是" offtext="否" value="${outpatient_check}"></s:switch>
				<s:switch label="急诊是否审查" name="emergency_check" onvalue="Y" offvalue="N" ontext="是" offtext="否" value="${emergency_check}"></s:switch>
				<s:switch label="住院是否审查" name="hospitalization_check" onvalue="Y" offvalue="N" ontext="是" offtext="否" value="${hospitalization_check}"></s:switch>
			</s:row>
			<s:row>
			     <s:textarea label="审查说明" name="audit_explain" cols="4" height="50" value="${audit_explain}"></s:textarea>
			</s:row>
				
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		//ishave();
		//$("input[name='sort_id']").attr("disabled","disabled");
	})
	
	
	//判断重复
	function ishave(){
		$("input[name='code']").blur(function(){
			var sdata=$("#form").getData();
			/* sdata.ts='${param.ts}';
			sdata.vs='${param.vs}';
			sdata.uid='${param.uid}'; */
			$.call("hospital_common.config.get",sdata,function(s){
	    		if(s!=null){
	    			$.message("配置编号已存在！请重新输入");
	    			$("input[name='code']").val("");
	    		}
	    	});
		});
	}

	function save(){
		var sdata=$("#form").getData();
		/* sdata.ts='${param.ts}';
		sdata.vs='${param.vs}';
		sdata.uid='${param.uid}'; */
		//空表单提交验证
    	if (!$("form").valid()){
    		return false;
    	}
    	//保存
    	$.call("hospital_common.checksort.save",sdata,function(s){
    		$.closeModal(s);
    	}); 
	}

	//取消
    function cancel(){
    	$.closeModal(false);
    }
</script>