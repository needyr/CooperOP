<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="">
	<s:row>
		<s:form id="myform">
			<s:row>
				<s:textfield label="会议主题"   readonly="true">${name }</s:textfield>
				<s:textfield label="会议类型"   readonly="true">${type }</s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="主持人"  readonly="true">${moderator_name }</s:textfield>
				<s:textfield label="会议纪要人" readonly="true">${meeting_summary_user_name }</s:textfield>
			</s:row>
			<s:row>
				<s:datefield  label="开始时间" name="start_time"  value="${start_time }" format="yyyy-MM-dd HH:mm" readonly="true"></s:datefield>
				<s:datefield  label="结束时间" name="end_time"  value="${end_time }" format="yyyy-MM-dd HH:mm" readonly="true"></s:datefield>
			</s:row>
			<s:row>
				<s:textarea label="会议主要内容："  cols="4" readonly="true">${content }</s:textarea>
			</s:row>
			<s:row>
				<s:switch label="是否需要筹备" name="preparation" value="${preparation }"></s:switch>
			</s:row>
			<s:row id="preparations" >
				<s:checkbox id="facility" label="筹备项" name="preparation_detail" value="${preparation_detail }"  readonly="true"
					action="oa.meeting.meetingFacility.query" params="{&#34;meeting_id&#34;:${meeting_id }}" cols="4" >
					<s:option label="$[name]" value="$[id]">$[name]</s:option>
				</s:checkbox>
			</s:row>
			<s:row>
				<s:textarea label="参会人员" name="jion_user " value="${jion_user  }" cols="4" readonly="true"></s:textarea>
			</s:row>
			<s:row>
				<s:textarea label="会议纪要" name="meeting_summary " cols="4" readonly="true">${meeting_summary}</s:textarea>
				<s:image label="纪要图片" name="summary_img" value="${summary_img}" cols="4" islabel="true"></s:image>
				<s:file label="纪要附件" name="summary_attach" value="${summary_attach}" cols="4" islabel="true"></s:file>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	var id = '${pageParam.id}';
	$(document).ready(function() {
		ispreparation();
	})
	function ispreparation() {
		var d = $("#myform").getData();
		if (d.preparation == 1) {
			$("#preparations").show();
		} else {
			$("#preparations").hide();
		}

	}
</script>