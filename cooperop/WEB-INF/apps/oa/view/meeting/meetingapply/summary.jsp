<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="">
	<s:row>
		<s:form label="" id="myform">
			<s:row>
				<s:textarea label="纪要内容:" name="meeting_summary" required="true">${meeting_summary}</s:textarea>
				<s:image label="纪要图片:" name="summary_img" value="${summary_img}" maxlength="10"></s:image>
				<s:file label="纪要附件:" name="summary_attach" value="${summary_attach}"></s:file>
			</s:row>
			<s:row style="text-align: center;margin-top: 10px;" id="save">
				<s:button label="保存" onclick="save();"></s:button>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
var id='${pageParam.id}';
	function save(){
		if (!$("form").valid()) {
			return false;	
		}
		var d=$("#myform").getData();
		d.id=id;
		$.call("oa.meeting.meetingapply.update", d, function(rtn) {
			if (rtn.result == "success") {
				$.closeModal(true);
			}else{
				$.error(rtn.msg);
			}
		});
	}

</script>