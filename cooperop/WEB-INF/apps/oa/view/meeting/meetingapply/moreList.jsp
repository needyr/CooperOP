<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="会议室记录">
	<s:row>
		<s:table  label="会议室使用记录" autoload="false" id="dtable" action="oa.meeting.meetingapply.query" sort="true" fitwidth="true" limit="10">
			<s:table.fields>
				<s:table.field label="申请人" name="creator_name" datatype="string"></s:table.field>
				<s:table.field label="类型" name="type" datatype="string"></s:table.field>
				<s:table.field label="主题" name="name" datatype="string"></s:table.field>
				<s:table.field label="开始时间" name="start_time" datatype="datetime" sort="true" format="yyyy-MM-dd HH:mm" ></s:table.field>
				<s:table.field label="结束时间" name="end_time" datatype="datetime" sort="true" format="yyyy-MM-dd HH:mm"></s:table.field>
				<s:table.field label="主持人" name="moderator_name" datatype="string"></s:table.field>
				<s:table.field name="oper" datatype="script"  label="操作" >
	 				var html=[];
	 				var userid=userinfo.id;
	 				if(userid == record.meeting_summary_user || userid == record.creator){
	 					html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="summary('+record.id+')">纪要</a>');
	 				}
	 				html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="details('+record.id+')">详情</a>');
					return html.join('');
	 			</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
var meeting_id='${pageParam.meeting_id}';
$(document).ready(function() {
	query();
});
function query(){
	$("#dtable").params({meeting_id:meeting_id});
	$("#dtable").refresh();
}
	
//修改
function modify(id,meeting_id){
	$.modal("add.html", "会议室修改", {
		width : '800px',
		height : '600px',
		id:id,
		meeting_id,meeting_id,
		callback : function(rtn) {
			if(rtn){
				$("#dtable").refresh();
			}
	    }
	});
}
//详情
function details(id){
	$.modal("details.html", "会议详情", {
		width : '800px',
		height : '600px',
		id:id,
		callback : function(rtn) {
			if(rtn){
				$("#dtable").refresh();
			}
	    }
	});
}
//会议纪要
function summary(id){
	$.modal("summary.html", "会议纪要", {
		width : '600px',
		height : '400px',
		id:id,
		callback : function(rtn) {
			if(rtn){
				$("#dtable").refresh();
			}
	    }
	});
}
</script>