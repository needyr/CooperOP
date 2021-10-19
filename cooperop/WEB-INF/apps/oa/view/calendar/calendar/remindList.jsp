<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" dispermission="true">
	<s:row>
		<s:table label="通知列表" autoload="false" id="dtable" action="oa.calendarEvent.query" sort="true" fitwidth="true" limit="10">
			<s:table.fields>
				<s:table.field name="dlmc" datatype="string"  label="大类" sort="true"></s:table.field>
				<s:table.field name="xlmc" datatype="string"  label="小类" sort="true"></s:table.field>
				<s:table.field name="subject" datatype="string"  label="主题"></s:table.field>
				<s:table.field name="expire_time" datatype="datetime"  label="发布时间" sort="true" defaultsort="desc"></s:table.field>
				<s:table.field name="oper" datatype="script" label="操作" align="left" width="120" app_field="opr_field">
					var html=[];
					html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="showdetail('+record.data_id+',\''+record.subject+'\')">预览</a>');
					return html.join('');
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>

<script type="text/javascript">
var begin_date = '${pageParam.begin_date}';
var end_date = '${pageParam.end_date}';
var dl = 'remind';
$(document).ready(function() {
	query();
})

function query(){
	var d = {};
	d.begin_date = begin_date;
	d.end_date = end_date;
	d.dl = dl;
	$("#dtable").params(d);
	$("#dtable").refresh();
}

function showdetail(id,subject){
	$.modal(cooperopcontextpath + "/w/oa/notice/detail.html", "", {
		id : id
	})
}

</script>