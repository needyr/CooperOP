<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="会议室列表">
	<s:row>
		<s:form border="0" id="cfrom" >
			<s:row>
				<s:textfield label="关键字" name="tablefilter" placeholder="会议室名/会议室地点" cols="1"></s:textfield>
				<s:button label="查询" style="margin-left: 10px;" onclick="query()" class="btn-white" icon="fa fa-search"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table  label="会议室列表" autoload="false" id="dtable" action="oa.meeting.meetingroom.query" sort="true" fitwidth="true" limit="10">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="新增" size="btn-sm btn-default"
					onclick="add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="name" datatype="script"  label="会议室名" sort="true" >
	 				return '<a style="margin-left:10px;" onclick="details('+record.id+');">'+record.name+'</a>';
	 			</s:table.field>
				<s:table.field name="address" datatype="string"  label="会议室地点" ></s:table.field>
				<s:table.field name="galleryful" datatype="string"  label="可容纳人数"  sort="true"></s:table.field>
				<s:table.field name="oper" datatype="script" label="操作" align="center" width="240" app_field="opr_field" >
	 				var html=[];
	 				html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="facilityManage('+record.id+',\''+record.name+'\')">设备</a>');
	 				html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="tagsManage('+record.id+',\''+record.name+'\')">标签</a>');
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
	$("#dtable").params($("#cfrom").getData());
	$("#dtable").refresh();
}

//新增
function add(){
	$.modal("add.html", "新增", {
		width : '800px',
		height : '600px',
		callback : function(rtn) {
			$("#dtable").refresh('current');
	    }
	});
}
//修改
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
//删除
function del(id){
	$.confirm("删除后不可恢复，是否继续？", function(crtn){
		if(crtn){
			$.call("oa.meeting.meetingroom.update", {id:id, state:-1}, function(rtn) {
				if (rtn.result == "success") {
					$.message("删除成功！", function(){
						$("#dtable").refresh('current');
					})
				}else{
					$.error("删除失败！");
				}
			});
		}
	})
}
//设备管理
function facilityManage(id){
	$.modal("facilityManage.html", "设备管理", {
		width : '800px',
		height : '600px',
		id : id
	});
}
//标签管理
function tagsManage(id,name){
	$.modal("tagsManage.html",name+" 标签管理", {
		width : '800px',
		height : '600px',
		id : id
	});
}
//展示详情
function details(id){
	$.modal("details.html", "详情", {
		width : '400px',
		height : '400px',
		id : id
	});
}
</script>