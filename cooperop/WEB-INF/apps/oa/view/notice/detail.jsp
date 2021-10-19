<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="通知公告-详情" dispermission="true">
<style>
h3 {
    display: block;
    font-size: 1.57em;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    font-weight: bold;
}
body {
    background-color: #fff !important;
}

.main {
    background-color: #fff !important;
}
</style>
	<s:row>
		<s:form id="conditions" fclass="portlet light bordered">
			<s:row>
				<h3 style="text-align: center;">${subject }</h3>
			</s:row>
			<s:row style="border-bottom:1px solid #e1e1e1 !important">
				<p style="text-align: center;">作者：${author }<span style="margin-left:20px;">发布时间：<fmt:formatDate value="${release_time}" pattern="yyyy年MM月dd日 HH:mm"/>   </span></p>
				<p style="text-align: center;">
					<span style="margin-left:20px;">生效日期：${empty begin_time ? ' -' : ''}<fmt:formatDate value="${begin_time}" pattern="yyyy年MM月dd日"/></span>
					<span style="margin-left:20px;">失效日期：${empty end_time ? ' -' : ''}<fmt:formatDate value="${end_time}" pattern="yyyy年MM月dd日"/></span>
				</p>
			</s:row>
			<s:row style="margin-top: 20px;">
				${content}
			</s:row>
			<c:if test="${not empty attach_files}">
				<s:row style="margin-top: 20px;">
					<s:file cols="4" islabel="true" label="附件" value="${attach_files}" downloadable="${can_download eq 1}"></s:file>
				</s:row>
			</c:if>
		</s:form>
	</s:row>
	<%-- <div id="div" style="position:absolute;bottom:10px;left:0px;">
		<div style="color:green;margin-left:10px;font-size: large; ">通知公告阅读情况</div>
		<div style="float:left;color:red;margin-right: 60px;margin-top: 10px;margin-left: 45px;">已阅读人数：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="hasread();" title="点击可查看详细人员名单">${read_counts}</a></div>
		<div style="float:left;color:red;margin-right: 60px;margin-top: 10px;margin-left: 45px;">未阅读人数：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="notread();" title="点击可查看详细人员名单">${not_read_counts}</a></div>
	</div> --%>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
		if('${creator}' == userinfo.id || userinfo.supperUser){
			$("#div").show();
		}else{
			$("#div").hide();
		}
	});
// 	function hasread(){
// 		var url = cooperopcontextpath + "/w/setting/notification/has_read.html";
// 		$.modal(url,"已阅读人员详细列表",{
// 			width : '60%' ,
// 			height : '80%' ,
// 			notice_id : '${id}',
// 			callback : function(){
				
// 			}
// 		});
// 	}
// 	function notread(){
// 		var url = cooperopcontextpath + "/w/setting/notification/not_read.html";
// 		$.modal(url,"未阅读人员详细列表",{
// 			width : '50%' ,
// 			height : '80%',
// 			notice_id : '${id}',
// 			callback : function(){
				
// 			}
// 		});
// 	}
</script>
