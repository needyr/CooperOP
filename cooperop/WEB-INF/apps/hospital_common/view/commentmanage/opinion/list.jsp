<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>

<s:page title="点评预判规则维护">
	<s:row>
		<s:form border="0" id="form" label="快速查找">
				<s:row>
					<s:textfield label="关键字" name="filter" placeholder="" cols="3"></s:textfield>
				</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="mytable" label="预判规则" autoload="false" action="hospital_common.commentmanage.opinion.query">
			 <s:toolbar>
				<s:button label="查询" icon="fa fa-search" onclick="query()"></s:button>
				<s:button icon="" label="新增" onclick="Add()"></s:button>
			</s:toolbar> 
			<s:table.fields>
				<s:table.field name="thirdt_name" label="问题名称"></s:table.field>
				<s:table.field name="comment_result" label="点评结果" datatype="script">
					var re = record.comment_result;
					if(re == 0){
					return '不合理';
					}
					return '合理';
				</s:table.field>
				<s:table.field name="comment_content" label="点评内容"></s:table.field>
				<s:table.field name="caozuo" label="操作" datatype="template" width="70">
					<a href="javascript:void(0)" onclick="update('$[id]')">修改</a>
					<a href="javascript:void(0)" onclick="Delete('$[id]')">删除</a>
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
	var qdata=$("#form").getData();
	$("#mytable").params(qdata);
	$("#mytable").refresh();
}

function Add(){
	$.modal("edit.html","新增",{
		width:"70%",
		height:"70%",
		callback : function(e){
			query();
		}
	});
}

function update(id){
	$.modal("edit.html","修改",{
		width:"70%",
		height:"70%",
		id: id,
		callback : function(e){
			query();
		}
	});
}

function Delete(id){
	$.confirm("确认删除？\t\n删除后无法恢复！",function callback(e){
		if(e==true){
			$.call("hospital_common.commentmanage.opinion.delete",{"id":id},function(s){
				query();
			});
		}
	});
}
</script>