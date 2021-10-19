<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" dispermission="true">
	<s:row>
		<s:form id="cform" label="条件区域" color="green">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="主题、作者" cols="1"></s:textfield>
				<s:button label="查询" onclick="query();" color="blue"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="通知列表" autoload="false" id="dtable" action="oa.notice.queryMine" sort="true" fitwidth="true" limit="10">
			<s:table.fields>
				<s:table.field name="subject" datatype="script"  label="主题" sort="true">
					if(record.is_read == 1){
						return '<span style="color: #bbb;">'+record.subject+'</span>';
					}else{
						return '<span style="font-weight: 600;">'+record.subject+'</span>';
					}
				</s:table.field>
				<s:table.field name="author" datatype="string"  label="作者" sort="true"></s:table.field>
				<s:table.field name="begin_time" datatype="datetime"  label="生效时间" sort="true"></s:table.field>
				<s:table.field name="end_time" datatype="datetime"  label="失效时间" sort="true"></s:table.field>
				<s:table.field name="oper" datatype="script" label="操作" align="left" width="120" app_field="opr_field">
					var html=[];
					html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="showdetail('+record.id+')">查看</a>');
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
	var d=$("#cform").getData();
	$("#dtable").params(d);
	$("#dtable").refresh();
}
function showdetail(id, subject) {
// 	var url = cooperopcontextpath + "/w/application/notification/detail.html";
	$.modal("detail.html","",{
// 		width: "800px",
// 		height: "600px",
		id: id,
		preview: true,
		callback : function(rtn){
			$("#dtable").refresh("current");
		}
	})
}
</script>