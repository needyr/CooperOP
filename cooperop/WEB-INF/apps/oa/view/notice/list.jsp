<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="">
	<s:row>
		<s:form id="cform" label="条件区域" color="green">
			<s:row>
				<s:textfield label="关键字" name="filter" placeholder="主题、作者" cols="1"></s:textfield>
				<s:radio label="发布状态" name="state" value="" cols="2">
					<s:option label="全部" value=""></s:option>
					<s:option label="已发布" value="1"></s:option>
					<s:option label="未发布" value="0"></s:option>
					<s:option label="已作废" value="-2"></s:option>
				</s:radio>
				<s:button label="查询" onclick="query();" color="blue"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table label="通知列表" autoload="false" id="dtable" action="oa.notice.query" sort="true" fitwidth="true" limit="10">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="新增"
					onclick="add()"></s:button> 
 			</s:toolbar>
			<s:table.fields>
				<s:table.field name="subject" datatype="string"  label="主题" sort="true"></s:table.field>
				<s:table.field name="author" datatype="string"  label="作者" sort="true"></s:table.field>
				<s:table.field name="begin_time" datatype="date"  label="生效日期" sort="true" defaultsort="desc"></s:table.field>
				<s:table.field name="end_time" datatype="date"  label="失效日期" sort="true"></s:table.field>
				<%-- <s:table.field name="state" dictionary="notice.state" datatype="script" label="状态" sort="true">
					var color = "#00cdb1";
					if(record.state == 0){
						color = "#bbb";
					}else if(record.state == -2){
						color = "#ffa853";
					}
					return '<span style="color: '+color+';">'+dictionary[record.state]+'</span>';
				</s:table.field> --%>
				<s:table.field name="state" datatype="script"  label="状态" sort="true">
					var html = [];
					if(record.state == 1){
						html.push('<span style="color: #00cdb1;">已发布</span>');
					}else if(record.state == 0){
						html.push('<span style="color: #bbb;">未发布</span>');
					}else if(record.state == -2){
						html.push('<span style="color: red;">已作废</span>');
					}
					return html.join("");
				</s:table.field>
				<s:table.field name="oper" datatype="script" label="操作" align="left" width="120" app_field="opr_field">
					var html=[];
					if(userinfo.supperUser || record.creator == userinfo.id){
						html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="showdetail('+record.id+',\''+record.subject+'\')">预览</a>');
						if(record.state == 0){
							html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="modify('+record.id+')">修改</a>');
							html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="del('+record.id+')">删除</a>');
						}else if(record.state == 1){
							html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="invalid('+record.id+')">作废</a>');
						}
					}
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
function add(){
	$.modal("add.html", "新增", {
		width : '960px',
		height : '650px',
		callback : function(rtn) {
			if(rtn){
				$("#dtable").refresh('current');
			}
	    }
	});
}
function modify(id){
	$.modal("add.html", "修改", {
		width : '960px',
		height : '650px',
		id : id,
		callback : function(rtn) {
			if(rtn){
				$("#dtable").refresh('current');
			}
	    }
	});
}
function showdetail(id, subject) {
// 	var url = cooperopcontextpath + "/w/application/notification/detail.html";
	$.modal("detail.html",subject,{
		id: id,
		preview: true,
		callback : function(rtn){
		}
	})
}
function del(id) {
	$.confirm("删除后无法恢复，是否继续？" ,function (crtn){
		if(crtn){
			$.call("oa.notice.update", {id:id, state:-1}, function(rtn) {
				if (rtn.result == "success") {
					$("#dtable").refresh('current');
				}else{
					$.error(rtn.msg);
				}
			});
		}
	});
}
function invalid(id){
	$.confirm("作废后无法恢复，是否继续？" ,function (crtn){
		if(crtn){
			$.call("oa.notice.update", {id:id, state:-2}, function(rtn) {
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