<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="通知公告" dispermission="true">
	<s:row>
		<s:form id="conditions" fclass="portlet light bordered">
			<s:row>
				<s:textfield label="关键字" name="keyword" tips="关键字"></s:textfield>
				<s:switch label="状态" name="isread" ontext="全部" offtext="未读" onvalue="1" offvalue="0"></s:switch>
<%-- 				<s:select label="状态" name="isread"> --%>
<%-- 					<s:option label="全部"></s:option> --%>
<%-- 					<s:option label="已读" value="1"></s:option> --%>
<%-- 					<s:option label="未读" value="0"></s:option> --%>
<%-- 				</s:select> --%>
				<s:button label="查询" icon="fa fa-search" color="green" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="optionlist" action="application.notification.queryMine" autoload="false" fitwidth="true" sort="true" label="通知公告">
			<s:table.fields>
				<s:table.field name="subject" label="主题"  sort="true" datatype="script" app_field="content_field">
					var html = [];
					html.push('<a style="margin: 0px 5px;');
					html.push(record.readflag == 1 ? '' : 'font-weight:600;');
					html.push('" href="javascript:void(0);" onclick="showdetail(\'' + record.id + '\', \'' + record.subject + '\');">');
					html.push(record.subject);
					html.push('</a>');
					html.push(record.attach_files ? '<i class="fa fa-paperclip" style="margin-left:5px;" title="有附件"></i>' : '');
					return html.join("");
				</s:table.field>
				<s:table.field name="author" label="作者"  sort="true" width="120" datatype="script" app_field="title_field">
					var html = [];
					html.push('['+record.author+'] ');
					return html.join("");
				</s:table.field>
				<s:table.field name="pdate" label="发布日期"  sort="true" defaultsort="desc" align="center" datatype="datetime" format="yyyy-MM-dd HH:mm" width="120" app_field="pre_title_field">></s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
		query();
	});
	function query() {
		$("#optionlist").params($("#conditions").getData());
		$("#optionlist").refresh();
	}
	function showdetail(notification_id, subject){
		var url = cooperopcontextpath + "/w/application/notification/detail.html";
		$.modal(url,subject,{
			id: notification_id,
			callback : function(rtn){
				query();
			}
		})
	}
</script>
