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
				<s:button label="确认" onclick="save()" icon="glyphicon glyphicon-floppy-saved"></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="tablename" value="${param.tablename}"/>
				<input type="hidden" name="p_key" value="${param.p_key}"/>
				<s:autocomplete  id="ssks" label="标准库" name="sys_p_key" action="hospital_common.dictall.querysys" limit="10" editable="false" cols="3" value="${param.sysname}" required="true" >
					<s:option value="$[p_key]" label="$[sysname]">
						<span style="width:100px;display:block;float:left">
							$[p_key]
						</span>
						<span style="width:100px;display:block;float:left">
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
		$("#ssks").params({"tablename": '${param.tablename}',"clname":'${param.clname}'});
		$("#ssks").refresh(); 
	})
	
	//判断重复

	function save(){
		//空表单提交验证
		var sdata=$("#form").getData();
    	if (!$("form").valid()){
    		return false;
    	}
    	//console.log($("#form").getData())
    	//保存
    	 $.call("hospital_common.dictall.editss",sdata,function(s){
    		$.closeModal(s);
    	});  
	}

	//取消
    function cancel(){
    	$.closeModal(false);
    }
</script>