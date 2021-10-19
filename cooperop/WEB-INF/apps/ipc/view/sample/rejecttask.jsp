<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="分配点评">
<s:row>
	<s:form label="分配药师" id="form">
		<s:toolbar>
			<s:button label="转交" icon="fa fa-save" onclick="Add()"></s:button> 
		</s:toolbar>
		<s:row>
		<s:autocomplete action="ipc.sample.querysystem" name="comment_user" 
			 label="药师名称" cols="3" limit="10" required="true">
			<s:option value="$[id]" label="$[name]" >
				$[no]	$[name]
			</s:option>
		</s:autocomplete>
		</s:row>
	</s:form>
</s:row>
</s:page>
<script type="text/javascript">
	function Add(){
		var data=$("#form").getData();
    	if (!$("form").valid()){
    		return false;
    	}
		data.djbh = '${param.djbh}';
		data.task_id = '${param.task_id}';
		data.audited = '${param.audited}';
		data.advice = '${param.advice}';
		$.call("ipc.commentflow.cancel",data,function(){
			$.closeModal(true);
		}); 
	}
</script>