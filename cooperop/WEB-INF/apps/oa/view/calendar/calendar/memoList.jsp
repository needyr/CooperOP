<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" dispermission="true">
	<s:row>
		<s:table label="备忘列表" autoload="false" id="dtable" action="oa.calendar.memo.query" sort="true" fitwidth="true">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="新增" onclick="add()"></s:button> 
 			</s:toolbar>
			<s:table.fields>
				<s:table.field label="备忘内容" name="content" datatype="string" sort="true"></s:table.field>
				<s:table.field name="oper" datatype="script" label="操作" align="center" width="120" app_field="opr_field">
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
var memo_time = '${pageParam.memo_time}';
var userid = '${pageParam.userid}';
$(document).ready(function() {
	query();
})

function query(){
	var d = {};
	d.begin_date = memo_time;
	d.end_date = memo_time;
	d.creator = userid;
	$("#dtable").params(d);
	$("#dtable").refresh();
}

function modify(id){
	$.modal(cooperopcontextpath + "/w/oa/calendar/memo/add.html", "备忘 - 修改",{
		width : '800px',
		height : '600px',
		id : id,
		callback : function(rtn) {
			$("#dtable").refresh();
        }
	},null,{async: false});
}

function add(){
	$.modal(cooperopcontextpath + "/w/oa/calendar/memo/add.html", "备忘 - 新增", {
		width : '800px',
        height : '600px',
		memo_time:memo_time,
        callback : function(rtn) {
            $("#dtable").refresh();
        }
	},null,{async: false});
}

function del(id) {
	$.confirm("删除后无法恢复，是否继续！" ,function (crtn){
		if(crtn){
			$.call("oa.calendar.memo.delete", {id:id});
			$("#dtable").refresh();
		}
	},null,{async: false});
}

</script>