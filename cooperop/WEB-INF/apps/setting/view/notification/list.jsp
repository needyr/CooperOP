<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="通知公告列表">
	<s:row>
		<s:form border="0" id="conditions">
			<s:row>
				<s:datefield label="发布日期" name="pdate"></s:datefield>
				<s:datefield label="结束日期" name="edata"></s:datefield>
				<s:checkbox label="发布状态" name="published" action="application.common.dictionary" params="{&#34;field&#34;:&#34;published&#34;}" value="0,1">
					<s:option value="$[bianh]" label="$[dictlist]"></s:option>
				</s:checkbox>
				<s:select label="类别" name="notification_type" dictionary="notification_type" cold="1">
					<s:option label="全部" value=""></s:option>
				</s:select>
			</s:row>
			<s:row>
				<s:textfield label="关键字" cols="2" name="filter" placeholder="匹配通知公告标题、内容、作者"></s:textfield>
				<s:button icon="fa fa-search" label="查询" size="btn-sm" onclick="query();"></s:button>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table table="notification" label="通知公告列表" autoload="true" id="noticetable" action="setting.notification.query" sort="true" fitwidth="true">
			<s:toolbar>
				<s:button icon="fa fa-file-o" label="新增" size="btn-sm btn-default"
					onclick="add()"></s:button>
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="notification_type" label="类别" sort="true" align="center"></s:table.field>
				<s:table.field name="author" label="作者"  sort="true" width="120" datatype="script" app_field="pre_title_field">
					var html = [];
					if(record.modifier_name == null){
						html.push('['+record.author+'] '+'系统超级用户');
					}else{
						html.push('['+record.author+'] '+record.modifier_name);
					}
					return html.join("");
				</s:table.field>
<%-- 				<s:table.field name="author" datatype="string" label="作者" sort="true" align="center" width="60"></s:table.field> --%>
				<s:table.field name="subject" datatype="script" label="主题" app_field="title_field" sort="true" >
					var html=[];
					html.push(record.subject);
<!-- 					if(record.modifier==userinfo.id){ -->
						html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" ');
						html.push(' onclick="showdetail('+record.id+',\''+record.subject+'\')">预览</a>');
<!-- 					} -->
					return html.join("");
				</s:table.field>
				<%-- <s:table.field name="sendto" datatype="template" label="对象" sort="true" hidden="true">
					$[sendto]
					<a style="margin: 0px 5px;" href="javascript:void(0)"
						onclick="showreader('$[id]', '$[subject]')">浏览记录</a>
				</s:table.field> --%>
				<s:table.field name="pdate" datatype="date" label="生效日期" sort="true" align="center" width="60" defaultsort="desc"></s:table.field>
				<s:table.field name="edate" datatype="date" label="失效日期" sort="true" align="center" width="60"></s:table.field>
				<s:table.field name="published" datatype="script" label="发布状态" app_field="content_field" sort="true" align="center" width="100">
					var html = [];
					if (record.published == 1) {
						html.push('<font color="green">已发布</font>');
						if(record.modifier==userinfo.id){
							html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="unpublish(\'' + record.id + '\')">取消</a>');
						}
					} else {
						html.push('未发布');
						if(record.modifier==userinfo.id){
							html.push('<a style="margin: 0px 5px;" href="javascript:void(0)" onclick="publish(\'' + record.id + '\')">发布</a>');
						}
					}
					return html.join("");
				</s:table.field>
				<s:table.field name="oper" datatype="script" label="操作" align="center" width="100" app_field="opr_field">
					var html=[];
					if(record.modifier==userinfo.id){
						html.push(' <a style="margin: 0px 5px;" href="javascript:void(0)" ');
						html.push(' onclick="modify('+record.id+')">修改</a>');
						html.push(' <a style="margin: 0px 5px;" href="javascript:void(0)" ');
						html.push(' onclick="deletenotice('+record.id+')">删除</a> ');
					}
					return html.join("");
				</s:table.field>
			</s:table.fields>
		</s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	function query(){
		$("#noticetable").params($("#conditions").getData());
		$("#noticetable").refresh();
	}
	function add(){
		$.modal("modify.html", "新增", {
			callback : function(rtn) {
				if(rtn){
					query();
				}
		    }
		});
	}
	function modify(id){
		$.modal("modify.html", "修改", {
			id : id,
			callback : function(rtn) {
				if(rtn){
					query();
				}
		    }
		});
	}
	function deletenotice(id) {
		$.confirm("是否确认删除此公告？", function(c) {
			if (c) {
				$.call("setting.notification.delete", {id:id}, function(rtn) {
					$.message("删除成功",function(){
						query();
					});
				},null);
			}
		});
	}
	function publish(id) {
		$.call("setting.notification.publish", {id:id}, function(rtn) {
			query();
		},null);
	}
	function unpublish(id) {
		$.call("setting.notification.unpublish", {id:id}, function(rtn) {
			query();
		},null);
	}
	function showdetail(id, subject) {
		var url = cooperopcontextpath + "/w/application/notification/detail.html";
		$.modal(url,subject,{
			id: id,
			preview: true,
			callback : function(rtn){
			}
		})
	}
	function showreader(id, subject) {
		var url = cooperopcontextpath + "/w/application/notification/detail.html";
		$.modal(url,subject,{
			id: id,
			callback : function(rtn){
			}
		})
	}
</script>