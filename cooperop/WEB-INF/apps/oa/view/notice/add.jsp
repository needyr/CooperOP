<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true" dispermission="true">
	<s:row>
		<s:form  id="dform">
			<s:row>
				<input type="hidden" name="id" value="${id }" />
				<s:textfield label="主题" name="subject" value="${subject }" maxlength="256" required="true" cols="4"></s:textfield>
				<s:textfield id="author" label="作者" name="author" value="${author }" maxlength="128" required="true" cols="1"></s:textfield>
				<s:radio label="发布状态" name="state" value="${empty state ? 1 : state }" cols="1">
					<s:option label="发布" value="1"></s:option>
					<s:option label="不发布" value="0"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<s:datefield label="生效日期" name="begin_time" value="${begin_time }" format="yyyy-MM-dd" required="true" autocomplete="off"></s:datefield>
				<s:datefield id="etime" label="失效日期" name="end_time" value="${end_time }" format="yyyy-MM-dd" autocomplete="off"></s:datefield>
<%-- 				<s:datefield id="etime" label="失效时间" name="end_time" value="${end_time }" format="yyyy-MM-dd HH:mm:ss" min="%y-%M-%d 23:59:59" autocomplete="off"></s:datefield> --%>
				<s:richeditor label="内容" name="content" height="200" toolbar="full" cols="4">${content}</s:richeditor>
				<s:file cols="4" label="附件" name="attach_files" value="${attach_files}" maxlength="30"></s:file>
			</s:row>
			<s:row style="text-align: center;margin-top: 10px;">
				<s:button label="保存" onclick="save();"></s:button>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
$(document).ready(function(){
	if(!$("#author").val()){
		$("#author").val("["+userinfo.baseDepName+"]"+userinfo.name);
	}
})
function save() {
	if (!$("form").valid()) {
		return false;	
	}
	var d = $("#dform").getData();
	var method = d.id ? "update" : "insert";
	$.call("oa.notice."+method, d, function(rtn) {
		if (rtn.result == "success") {
			$.closeModal(true);
		}else{
			$.error(rtn.msg);
		}
	});
}
</script>
