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
				<input type="hidden" name="id" value="${id }"/>
				<c:if test="${not empty param.memo_time }">
					<s:textfield id="mt" label="备忘日期" name="memo_time" readonly="true" cols="1" value="${param.memo_time }"></s:textfield>
				</c:if>
				<c:if test="${empty param.memo_time }">
					<s:datefield id="mt" label="备忘日期" name="memo_time" required="true" cols="1" value="${memo_time }"></s:datefield>
				</c:if>
			</s:row>
			<s:row>
				<s:textarea label="备忘内容" name="content" required="true" cols="4">${content }</s:textarea>
			</s:row>
			<s:row>
				<div class="cols4" style="position: relative;">
					<div style="position: absolute; right: 19px;">
						<s:button label="保存" onclick="save();" color="green" icon="fa fa-save"></s:button>
					</div>
				</div>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
$(document).ready(function(){
	if(!$("#mt").getData().memo_time){
		var today = formatDate(new Date(), "yyyy-MM-dd");
		$("#mt").setData(today);
	}
})
function save() {
	if (!$("form").valid()) {
		return false;	
	}
	var d = $("#dform").getData();
	if(d.id){
		$.call("oa.calendar.memo.update", d, function(rtn) {
			if (rtn.result == "success") {
				$.closeModal(true);
			}else{
				$.error(rtn.msg);
			}
		});
	}else{
		$.call("oa.calendar.memo.insert", d, function(rtn) {
			if (rtn.result == "success") {
				$.closeModal(true);
			}else{
				$.error(rtn.msg);
			}
		});
	}
}
</script>
