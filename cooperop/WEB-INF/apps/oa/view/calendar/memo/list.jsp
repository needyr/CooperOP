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
				<input type="hidden" name="creator" value="${param.userid }">
				<s:datefield label="开始日期" name="begin_date" value="${param.begin_date }" cols="1" ></s:datefield>
				<s:datefield label="结束日期" name="end_date" cols="1" value="${param.end_date }"></s:datefield>
				<s:button label="查询" onclick="query()" class="btn-white" icon="fa fa-search"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="备忘列表" autoload="false" id="dtable" action="oa.calendar.memo.queryMine" sort="true" fitwidth="true">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="新增" onclick="add()"></s:button> 
 			</s:toolbar>
			<s:table.fields>
				<s:table.field label="备忘日期" name="memo_time" datatype="date" format="yyyy-MM-dd" sort="true"></s:table.field>
				<s:table.field label="备忘人" name="creator_name" datatype="string" sort="true"></s:table.field>
				<s:table.field label="备忘内容" name="content" datatype="string" sort="true"></s:table.field>
				<s:table.field name="oper" datatype="script" label="操作" align="center" width="200" app_field="opr_field">
					var html=[];
						html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="modify('+record.id+')">修改</a>');
						html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="del('+record.id+')">删除</a>');
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
	d.sort="create_time desc";
	$("#dtable").params(d);
	$("#dtable").refresh();	
}
function add(){
	$.modal("add.html", "新增", {
		width : '800px',
		height : '600px',
		callback : function(rtn) {
			if(rtn){
				$("#dtable").refresh('current');
			}
	    }
	});
}
function modify(id){
	$.modal("add.html", "修改", {
		width : '800px',
		height : '600px',
		id : id,
		callback : function(rtn) {
			if(rtn){
				$("#dtable").refresh('current');
			}
	    }
	});
}
function del(id) {
	$.confirm("删除后无法恢复，是否继续！" ,function (crtn){
		if(crtn){
			$.call("oa.calendar.memo.delete", {id:id}, function(rtn) {
				if (rtn.result == "success") {
					$("#dtable").refresh('current');
				}else{
					$.error(rtn.msg);
				}
			});
		}
	});
}
</script>