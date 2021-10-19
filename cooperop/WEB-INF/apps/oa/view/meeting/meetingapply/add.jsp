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
				<input type="hidden" name="id" value="${id }"/>
				<input type="hidden" name="meeting_id" value="${meeting_id }"/>
				<s:textfield label="会议主题" name="name" value="${name }" required="true"></s:textfield>
				<s:textfield label="会议类型" name="type" value="${type }" required="true"></s:textfield>
			</s:row>
			<s:row>
				<s:autocomplete label="主持人" name="moderator" value="${moderator }" text="${moderator_name }" 
					action="oa.meeting.meetingapply.queryUser"  required="true">
					<s:option label="$[name]" value="$[id]">$[name]</s:option>
				</s:autocomplete>
				<s:autocomplete label="会议纪要人" name="meeting_summary_user" value="${meeting_summary_user }" text="${meeting_summary_user_name }"
					action="oa.meeting.meetingapply.queryUser" >
					<s:option label="$[name]" value="$[id]">$[name]</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:datefield id="stime" label="开始时间" name="start_time" required="true" value="${start_time }" format="yyyy-MM-dd HH:mm" onchange="jtime();"></s:datefield>
				<s:datefield id="etime" label="结束时间" name="end_time" required="true" value="${end_time }" format="yyyy-MM-dd HH:mm" onchange="jtime()"></s:datefield>
			</s:row>
			<s:row>
				<s:textarea label="会议主要内容" name="content" required="true" cols="4">${content }</s:textarea>
			</s:row>
			<!-- 无设备不显示筹备项 -->
			<c:if test="${hasFacility == 1}">
				<s:row>
					<s:switch label="是否需要筹备" name="preparation" value="${preparation }" onchange="ispreparation();"></s:switch>
				</s:row>
				<s:row id="preparations" >
					<s:checkbox id="facility" label="筹备项" name="preparation_detail" value="${preparation_detail }" 
						action="oa.meeting.meetingFacility.query"  params="{&#34;meeting_id&#34;:${meeting_id }}" cols="4">
						<s:option label="$[name]" value="$[id]">$[name]</s:option>
					</s:checkbox>
				</s:row>
			</c:if>
			<s:row>
				<s:textarea label="参会人员" name="jion_user " value="${jion_user  }" cols="4"></s:textarea>
			</s:row>
			<s:row style="text-align: center;margin-top: 10px;" id="save">
				<s:button label="保存" onclick="save();"></s:button>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
var meeting_id='${pageParam.meeting_id}';

$(document).ready(function(){
	$("#facility").params({meeting_id:meeting_id});
	ispreparation();
	//已开始无保存按钮
	var now = (new Date()).getTime();
	var start=new Date($("#myform").getData().start_time).getTime();
	var id = $("#myform").getData().id;
	if(now > start && id){
		$("#save").hide();
	}
})

function jtime(){
	var dd = $("#myform").getData();
	var now = new Date().getTime();
	var stime = new Date(dd.start_time).getTime();
	var etime = new Date(dd.end_time).getTime();
	if(stime && stime<now){
		$.message("开始时间不能小于当前时间!");
		$("#stime").val("");
	}
	if(etime && stime && etime<stime){
		$.message("结束时间不能小于开始时间!");
		$("#etime").val("");
	}
}

function ispreparation(){
	var d = $("#myform").getData();
	if(d.preparation == 1){
		$("#preparations").show();
	}else{
		$("#preparations").hide();
	}
}

function save(){
	if (!$("form").valid()) {
		return false;	
	}
	var d=$("#myform").getData();
	if(meeting_id){
		d.meeting_id=meeting_id;
	}
	var method=d.id ? "update" : "insert";
	if(d.preparation_detail){
		d.preparation_detail = d.preparation_detail.join(",");
	}
	$.call("oa.meeting.meetingapply." + method, d, function(rtn) {
		if (rtn.result == "success") {
			$.closeModal(true);
		}else{
			$.error(rtn.msg);
		}
	});
}

</script>