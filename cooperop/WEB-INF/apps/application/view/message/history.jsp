<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="历史消息" dispermission="true">
<link href="${request.contextPath }/res/application/css/chat.css" type="text/css" rel="stylesheet" />
	<s:row>
		<s:form label="查询条件">
			<s:row>
				<s:datefield label="开始日期" name="starttime"></s:datefield>
				<s:datefield label="截至截至" name="endtime"></s:datefield>
				<s:textfield label="内容" name="contents"></s:textfield>
				<s:button label="查询" color="blue" onclick="query();"/>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
	<s:table label="消息历史" autoload="false" action="application.message.listnew" id="chathistory">
		<s:table.fields>
			<s:table.field label="消息记录" name="oper" datatype="script">
				var html = [];
				html.push(' <div crid="" class="cols1 nolabel">');
				html.push(' <div class="control-content">');
				html.push(' <div class="form-control-static">');
				html.push( '<font color="'+(userinfo.id == record.system_user_id ?'green':'blue')+'">'+record.system_user_name);
				html.push(' [ '+ record.send_time_label+' ]</font>');
				html.push(' </div>');
				html.push(' </div>');
				html.push(' </div>');
				html.push(' <div crid="" class="cols1 nolabel">');
				html.push(' <div class="control-content">');
				html.push(' <div class="form-control-static">');
				html.push(' <div class="message-send-content" style="border:0px !important;">');
				html.push('<div class="message ' + (record.type == 'F' ? 'filemessage' : (record.type == 'I' ? 'imagemessage' : '')) + '">');
				html.push('<span class="body" style="padding: 0px !important;">');
				if (record.type == 'F') {
					html.push('  <div class="filecontent">');
	    			html.push('  	<i class="fa fa-file-o"></i>');
	    			html.push('  	<div class="filename">');
	            	html.push('<a href="' + cooperopcontextpath + '/rm/d/' + module + '/' + record.content + '" title="' + record.file_name + '" download="' + record.file_name + '" >' + record.file_name + '</a>');
	    			html.push('  	</div>');
	    			html.push('  	<div class="filesize">');
					html.push($.formatfilesize(+record.file_size));
	    			html.push('  	</div>');
	    			html.push('  </div>');
				} else if (record.type == 'I'){
					html.push('		<div class="preview">');
	            	html.push('		<img src="' + cooperopcontextpath + '/rm/s/' + module + '/' + record.content + 'S" bigsrc="' + cooperopcontextpath + '/rm/s/' + module + '/' + record.content + '">');
					html.push('		</div>');
				} else {
					html.push(record.content);
				}
				html.push('</span></div></div>');
				html.push('</div>');
				html.push('</div>');
				html.push('</div>');
				return html.join('');
			</s:table.field>
		</s:table.fields>
	</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
		query();
	});
	function query(){
		var data = $("form").getData();
		if(data.starttime && data.endtime){
			if(data.starttime > data.endtime){
				$.message("请选择正确的查询时间范围！");
				return ;
			}
		}
		data["target"] = '${pageParam.target}';
		data["send_to"] = '${pageParam.sendto}';
		data["queryall"] = "all";
		$("#chathistory").params(data);
		$("#chathistory").refresh();
	}
</script>
