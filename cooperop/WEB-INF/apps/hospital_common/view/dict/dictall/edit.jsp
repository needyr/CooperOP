<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="编辑系统配置信息" >
	<s:row>
		<s:form id="form" label="配置信息">
			<s:toolbar>
				<s:button label="确认" onclick="save()" icon="fa fa-save"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="tablename" value="${param.tablename}"/>
				<input type="hidden" name="p_key" value="${param.p_key}"/>
				<s:textfield cols="3" label="医院字典内容" value="${param.hisname}" disabled="disabled"></s:textfield>
				<s:autocomplete  id="ssks" label="系统字典内容" name="sys_p_key" action="hospital_common.dict.dictall.querysys" limit="10" editable="false" cols="3" value="${p_key}" text="${sys_name}" required="true" >
					<s:option value="$[p_key]" label="$[sysname]">
						<span style="width:100px;display:block;float:left">
							$[p_key]
						</span>
						<span style="width:200px;display:block;float:left">
							$[sysname]
						</span>
					</s:option>
				</s:autocomplete>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		//ishave();
		var data = {
			"tablename": '${param.tablename}',
			"clname": '${param.clname}',
			"systname": '${param.systname}'
			};
		$("#ssks").params(data);
		$("#ssks").refresh(); 
	})
	
	//判断重复

	function save(){
		//空表单提交验证
		//console.log($("#form").getData());
		var sdata=$("#form").getData();
    	if (!$("form").valid()){
    		return false;
    	}
    	//console.log($("#form").getData())
    	//保存
    	 $.call("hospital_common.dict.dictall.editss",sdata,function(s){
    		$.closeModal(s);
    	});  
	}

	//取消
    function cancel(){
    	$.closeModal(false);
    }
</script>