<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="我的邮件" dispermission="true">
<link href="${pageContext.request.contextPath }/res/application/css/email-mine.css" rel="stylesheet" type="text/css">
	<s:row class="row inbox">
		<div class="col-md-2">
			<ul class="inbox-nav margin-bottom-10">
				<li class="compose-btn">
					<a href="javascript:;" class="btn">
					<i class="fa fa-edit"></i> 写邮件 </a>
				</li>
				<li class="folder-btn">
					<a href="javascript:;" class="btn">
					<i class="fa fa-folder"></i> 系统文件夹 </a>
				</li>
				<c:forEach items="${folders}" var="folder" varStatus="f">
					<c:if test="${folder.id le 4 }">
						<li class="${f.index eq 0 ? 'active' : ''}" fid="${folder.id}" fname="${folder.name}">
							<a href="javascript:;" class="btn">
								<c:choose>
									<c:when test="${folder.id eq 1}">
										<i class="fa fa-download"></i>
									</c:when>
									<c:when test="${folder.id eq 2}">
										<i class="fa fa-send"></i>
									</c:when>
									<c:when test="${folder.id eq 3}">
										<i class="fa fa-thumb-tack"></i>
									</c:when>
									<c:when test="${folder.id eq 4}">
										<i class="fa fa-trash"></i>
									</c:when>
									<c:otherwise>
										<i class="fa fa-folder"></i>
									</c:otherwise>
								</c:choose>
								${folder.name}(${folder.noreadnum})
							</a>
							<b></b>
						</li>
					</c:if>
				</c:forEach>
				<li class="folder-btn">
					<a href="javascript:;" class="btn">
					<i class="fa fa-folder"></i> 我的文件夹 </a>
				</li>
				<c:forEach items="${folders}" var="folder" varStatus="f">
					<c:if test="${folder.id gt 4 }">
						<li class="${f.index eq 0 ? 'active' : ''}" fid="${folder.id}" fname="${folder.name}">
							<a href="javascript:;" class="btn">
								<i class="fa fa-folder"></i>
								${folder.name}(${folder.noreadnum})
							</a>
							<b></b>
						</li>
					</c:if>
				</c:forEach>
				<li class="server-btn">
					<a href="javascript:;" class="btn">
					<i class="fa fa-gears"></i> 邮箱设置 </a>
				</li>
			</ul>
		</div>
		<div class="col-md-10">
			<s:row>
				<s:form border="0"  id="conditions">
					<s:row>
						<input type="hidden" name="email_folder_id" value="1"/>
						<s:textfield label="主题" cols="1" name="subject"></s:textfield>
						<s:textfield label="发件人" cols="1" name="send_user_name"></s:textfield>
						<s:textfield label="收件人" cols="1" name="to_name"></s:textfield>
						<s:button label="查询" icon="fa fa-search" color="blue" onclick="query();"></s:button>
						<s:button label="清除" icon="fa fa-eraser" onclick="eraser();"></s:button>
					</s:row>
				</s:form>
			</s:row>
			<div class="inbox-content">
				<s:row id="content">
					<s:table id="optionlist" action="application.email.queryMine" select="multi" autoload="false" fitwidth="true" sort="true" label="${folders[0].name}">
						<s:toolbar>
							<s:button icon="fa fa-eye" label="标为已读" onclick="readEmails()" ufid="2,3"></s:button>
							<s:button icon="fa fa-eye-slash" label="标为未读" onclick="unreadEmails()" ufid="2,3"></s:button>
							<s:buttongroup icon="fa fa-sign-in" label="移动到" ufid="2,3,4">
								<c:forEach items="${folders}" var="folder" varStatus="f">
									<c:if test="${folder.id eq 1 or folder.id gt 4 }">
										<s:buttongroup.button icon="fa fa-folder-o" label="${folder.name }" ufid="2,3,4,${folder.id}" onclick="changeFolder('${folder.id}')"></s:buttongroup.button>
									</c:if>
								</c:forEach>
							</s:buttongroup>
							<s:button icon="fa fa-trash" label="删除" onclick="recycleEmails()" ufid="2,3,4"></s:button>
							<s:button icon="fa fa-repeat" label="恢复" onclick="restoreEmails()" fid="4"></s:button>
							<s:button icon="fa fa-trash" label="彻底删除" onclick="deleteEmails()" fid="2,3,4"></s:button>
						</s:toolbar>
						<s:table.fields>
							<s:table.field name="send_user_name" label="发件人"  sort="true" width="120" datatype="script">
								var html = [];
								html.push('<font style="margin: 0px 5px;');
								html.push(record.read_time > 0 ? '' : 'font-weight:bold;color:#3598DC;');
								html.push('">');
								html.push(record.send_user_name)
								html.push('</font>');
								return html.join("");
							</s:table.field>
							<s:table.field name="to_name" label="收件人"  sort="true" width="120" datatype="script">
								var html = [];
								html.push('<font style="margin: 0px 5px;');
								html.push(record.read_time > 0 ? '' : 'font-weight:bold;color:#3598DC;');
								html.push('">');
								if(record.to_name && record.to_name.length>30){
									html.push(record.to_name.substring(0,30)+'……');
								}else{
									html.push(record.to_name);
								}
								
								html.push('</font>');
								return html.join("");
							</s:table.field>
							<s:table.field name="subject" label="主题"  sort="true" datatype="script">
								var html = [];
								html.push('<a style="margin: 0px 5px;');
								html.push(record.read_time > 0 ? '' : 'font-weight:bold;');
								html.push('" href="javascript:void(0);"');
								if (record.type == 'from' && !record.send_time) {
									html.push(' onclick="modify(\'' + record.id + '\');"');
								} else {
									html.push(' onclick="showdetail(\'' + record.id + '\', \'' + record.subject + '\', \'' + record.email_folder_id + '\');"');
								}
								html.push('>');
								html.push(record.subject);
								html.push('</a>');
								html.push(record.attach_files ? '<i class="fa fa-paperclip" style="margin-left:5px;" title="有附件"></i>' : '');
								return html.join("");
							</s:table.field>
							<s:table.field name="send_time" label="日期"  sort="true" defaultsort="desc" align="center" datatype="datetime" format="yyyy-MM-dd HH:mm" width="120"></s:table.field>
						</s:table.fields>
					</s:table>
				</s:row>
			</div>
		</div>
	</s:row>
</s:page>
<script type="text/javascript">
	$(document).ready(function() {
		$(".inbox-nav > li[fid]").on("click", "a", function() {
			var fid = $(this).parent().attr("fid");
			var fname = $(this).parent().attr("fname");
			$(".inbox-nav > li[fid]").removeClass("active");
			$(".inbox-nav > li[fid='" + fid + "']").addClass("active");
			$("#conditions").find("input[name='email_folder_id']").val(fid);
			$("#content").find(".caption").text(fname);
			$("#content").find("[ctype='toolbar']").find("[fid], [ufid]").each(function() {
				var $b = $(this);
				if ($b.is("[ufid]")) {
					if ($.inArray(fid, $b.attr("ufid").split(/,/g)) > -1) {
						$b.hide();
					} else {
						$b.show();
					}
				}
				if ($b.is("[fid]")) {
					if ($.inArray(fid, $b.attr("fid").split(/,/g)) > -1) {
						$b.show();
					} else {
						$b.hide();
					}
				}
			})
			//eraser();
			query();
		});
		$(".compose-btn").on("click", "a", function() {
			var url = cooperopcontextpath + "/w/application/email/modify.html";
			top.$(".page-content-tabs").open_tabwindow("email_new_" + Math.random(),"写邮件", url);
		});
		$(".server-btn").on("click", "a", function() {
			var url = cooperopcontextpath + "/w/application/email/setting.html";
			$.modal(url, "邮箱设置", {
				callback: function(rtn) {
					location.reload(true);
				}
			});
		});
		$(".inbox-nav > li[fid]:first > a").click();
	});
	function eraser() {
		$("#conditions").find("[name='subject']").val("");
		$("#conditions").find("[name='send_user_name']").val("");
		$("#conditions").find("[name='to_name']").val("");
		query();
	}
	function query() {
		$("#optionlist").params($("#conditions").getData());
		$("#optionlist").refresh();
	}
	function showdetail(email_id, subject, email_folder_id){
		var url = cooperopcontextpath + "/w/application/email/detail.html?id=" + email_id + "&email_folder_id=" + email_folder_id;
		top.$(".page-content-tabs").open_tabwindow("email_" + email_id,"邮件：" + subject, url);
	}
	function modify(email_id){
		var url = cooperopcontextpath + "/w/application/email/modify.html?id=" + email_id;
		top.$(".page-content-tabs").open_tabwindow("email_" + email_id,"写邮件", url);
	}
	function readEmails() {
		var selected_ = $("#optionlist").getSelected();
		if (selected_.length == 0) {
			$.warning("请选择需要标记为已读的邮件。");
			return;
		}
		var ids = [];
		for (var i in selected_) {
			ids.push(selected_[i].data.id);
		}
		$.call("application.email.read", {ids: ids.join(",")}, function(rtn) {
			query();
		});
	}
	function unreadEmails() {
		var selected_ = $("#optionlist").getSelected();
		if (selected_.length == 0) {
			$.warning("请选择需要标记为未读的邮件。");
			return;
		}
		var ids = [];
		for (var i in selected_) {
			ids.push(selected_[i].data.id);
		}
		$.call("application.email.unread", {ids: ids.join(",")}, function(rtn) {
			query();
		});
	}
	function changeFolder(new_folder_id) {
		var selected_ = $("#optionlist").getSelected();
		if (selected_.length == 0) {
			$.warning("请选择需要标记为已读的邮件。");
			return;
		}
		var ids = [];
		for (var i in selected_) {
			ids.push(selected_[i].data.id);
		}
		$.call("application.email.changeFolder", {ids: ids.join(","), new_folder_id: new_folder_id, isfrom: selected_[0].data.type == 'from' ? "1" : "0"}, function(rtn) {
			query();
		});
	}
	function recycleEmails() {
		var selected_ = $("#optionlist").getSelected();
		if (selected_.length == 0) {
			$.warning("请选择需要删除的邮件。");
			return;
		}
		var ids = [];
		for (var i in selected_) {
			ids.push(selected_[i].data.id);
		}
		$.call("application.email.recycle", {ids: ids.join(","), isfrom: selected_[0].data.type == 'from' ? "1" : "0"}, function(rtn) {
			query();
		});
	}
	function restoreEmails() {
		var selected_ = $("#optionlist").getSelected();
		if (selected_.length == 0) {
			$.warning("请选择需要恢复的邮件。");
			return;
		}
		var ids = [];
		for (var i in selected_) {
			ids.push(selected_[i].data.id);
		}
		$.call("application.email.restore", {ids: ids.join(","), isfrom: selected_[0].data.type == 'from' ? "1" : "0"}, function(rtn) {
			query();
		});
	}
	function deleteEmails() {
		var selected_ = $("#optionlist").getSelected();
		if (selected_.length == 0) {
			$.warning("请选择需要彻底删除的邮件。");
			return;
		}
		var ids = [];
		for (var i in selected_) {
			ids.push(selected_[i].data.id);
		}
		$.call("application.email.delete", {ids: ids.join(","), isfrom: selected_[0].data.type == 'from' ? "1" : "0"}, function(rtn) {
			query();
		});
	}
</script>
