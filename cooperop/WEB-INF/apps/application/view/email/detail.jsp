<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="我的邮件-详情" dispermission="true">
	<s:row>
		<s:form id="conditions" label="${subject }" style="background-color:#EEEEEE;">
			<s:toolbar>
				<s:button label="回复" icon="fa fa-mail-reply" onclick="reply();"></s:button>
				<s:button label="回复全部" icon="fa fa-mail-reply-all" onclick="replyAll();"></s:button>
				<s:button label="转发" icon="fa fa-share" onclick="share();"></s:button>
				<%-- <s:buttongroup icon="fa fa-sign-in" label="移动到" ufid="2,3,4">
					<c:forEach items="${folders}" var="folder" varStatus="f">
						<c:if test="${folder.id eq 1 or folder.id gt 4 }">
							<s:buttongroup.button icon="fa fa-folder-o" label="${folder.name }" ufid="2,3,4,${folder.id}" onclick="changeFolder('${folder.id}')"></s:buttongroup.button>
						</c:if>
					</c:forEach>
				</s:buttongroup>
				<s:button icon="fa fa-trash" label="删除" onclick="recycleEmails()" ufid="2,3,4"></s:button>
				<s:button icon="fa fa-repeat" label="恢复" onclick="restoreEmails()" fid="4"></s:button>
				<s:button icon="fa fa-trash" label="彻底删除" onclick="deleteEmails()" fid="2,3,4"></s:button> --%>
			</s:toolbar>
			<s:row>
				<s:textfield cols="4" label="发件人" islabel="true" value="${send_user_name}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield cols="4" label="收件人" islabel="true" value="${to_name}"></s:textfield>
			</s:row>
			<c:if test="${not empty cc_name}">
			<s:row>
				<s:textfield cols="4" label="抄送" islabel="true" value="${cc_name}"></s:textfield>
			</s:row>
			</c:if>
			<s:row>
				<s:textarea cols="4" label="时间" islabel="true"><fmt:formatDate value="${send_time}" pattern="yyyy年MM月dd日 HH:mm"/>  </s:textarea>
			</s:row>
			<s:row>
				<div crid="" class="cols4 nolabel" style="padding:5px;">
					<div class="control-content" style="margin:0px!important;padding: 15px!important;background-color: #FFF;">
						${content}
					</div>
				</div>
			</s:row>
			<c:if test="${not empty attach_files}">
				<s:row style="margin-top: 20px;">
					<s:file cols="4" islabel="true" label="附件" value="${attach_files}"></s:file>
				</s:row>
			</c:if>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
function reply() {
	var url = cooperopcontextpath + "/w/application/email/modify.html?linkid=${id}&type=r";
	top.$(".page-content-tabs").open_tabwindow("email_new_" + Math.random(),"写邮件", url);
	top.$(".page-content-tabs").close_tabwindow("email_${id}");
}
function replyAll() {
	var url = cooperopcontextpath + "/w/application/email/modify.html?linkid=${id}&type=ra";
	top.$(".page-content-tabs").open_tabwindow("email_new_" + Math.random(),"写邮件", url);
	top.$(".page-content-tabs").close_tabwindow("email_${id}");
}
function share() {
	var url = cooperopcontextpath + "/w/application/email/modify.html?linkid=${id}&type=s";
	top.$(".page-content-tabs").open_tabwindow("email_new_" + Math.random(),"写邮件", url);
	top.$(".page-content-tabs").close_tabwindow("email_${id}");
}
</script>
