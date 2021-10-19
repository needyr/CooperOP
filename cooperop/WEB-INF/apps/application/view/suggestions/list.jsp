<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="消息" dispermission="true">
	<s:row>
		<s:form id="conditions" fclass="portlet light bordered">
			<s:row>
				<s:textfield label="关键字" name="keyword" tips="关键字"></s:textfield>
				<s:switch label="状态" name="readflag" ontext="已处理" offtext="未处理" onvalue="1" offvalue="0"></s:switch>
				<s:button label="查询" icon="fa fa-search" color="green" onclick="query()"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="optionlist" action="application.suggestions.queryMine" autoload="false" fitwidth="true" sort="true" label="消息">
			<s:table.fields>
				<s:table.field name="subject" label="主题"  sort="true" datatype="script" app_field="content_field">
					var html = [];
					html.push('<a style="margin: 0px 5px;');
					html.push(record.readflag == 1 ? '' : 'font-weight:bold;');
					html.push('" href="javascript:void(0);" onclick="showdetail(\'' + record.order_id + '\',\'' + record.suggestions_id + '\',\'' + record.pageid+ '\',\'' + record.order_no + '\', \'' + (record.subject.length>20?record.subject.substring(0,20)+'......':record.subject) + '\');">');
					html.push(record.subject);
					html.push('</a>');
					html.push(record.attach_files ? '<i class="fa fa-paperclip" style="margin-left:5px;" title="有附件"></i>' : '');
					return html.join("");
				</s:table.field>
				<s:table.field name="create_time" label="日期"  sort="true" defaultsort="desc" align="center" width="160" app_field="title_field"></s:table.field>
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
	function showdetail(order_id,suggestions_id,pageid,order_no, subject){
		var u = pageid.split(".");
		var url ="";
		if(u[0]=="application"){
			url = cooperopcontextpath + "/w/application/task/freetaskdetail.html";
		}else if(u[1]=='bill'){
			url = cooperopcontextpath + "/w/"+u[0]+"/"+u[1]+"/"+u[2]+"/"+u[3]+".html";
		}else if(u[1]=='document'){
			url = cooperopcontextpath + "/w/"+u[0]+"/"+u[1]+"/"+u[2]+"/"+u[3]+".html";
		}
		
		$.modal(url,subject,{
			order_id: order_id,
			order_no: order_no,
			djbh: order_no,
			suggestions_id: suggestions_id,
			callback : function(rtn){
				if(suggestions_id){
					$.call("application.suggestions.saveRead", {id : suggestions_id, order_id : order_id}, function(rtn) {
						qq();
					},null,{async: false});
				}else{
					qq();
				}
			}
		})
	}
	function qq(){
		var start = $("#optionlist").dataTable().fnSettings()._iDisplayStart; 
		var total = $("#optionlist").dataTable().fnSettings().fnRecordsDisplay();
		var p = $("#optionlist").DataTable().page();
		if((total-start)==1){
			if (start > 0) {
				$("#optionlist").refresh_table(p-1);
			}else{
				$("#optionlist").refresh();
			}
		}else{
			$("#optionlist").refresh_table(p);
		}
	}
</script>
