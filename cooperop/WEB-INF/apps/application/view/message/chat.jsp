<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="我的邮件-详情" dispermission="true">
<link href="${request.contextPath }/res/application/css/chat.css" type="text/css" rel="stylesheet" />
<link href="${request.contextPath }/theme/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet"/>
<link href="${request.contextPath }/theme/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet"/>
<script type="text/javascript" src="${request.contextPath }/theme/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${request.contextPath }/theme/plugins/jquery-file-upload/js/vendor/tmpl.min.js"></script>
<script type="text/javascript" src="${request.contextPath }/theme/plugins/jquery-file-upload/js/vendor/load-image.min.js"></script>
<script type="text/javascript" src="${request.contextPath }/theme/plugins/jquery-file-upload/js/vendor/canvas-to-blob.min.js"></script>
<script type="text/javascript" src="${request.contextPath }/theme/plugins/jquery-file-upload/blueimp-gallery/jquery.blueimp-gallery.min.js"></script>
<script type="text/javascript" src="${request.contextPath }/theme/plugins/jquery-file-upload/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${request.contextPath }/theme/plugins/jquery-file-upload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="${request.contextPath }/theme/plugins/jquery-file-upload/js/jquery.fileupload-process.js"></script>
<script type="text/javascript" src="${request.contextPath }/theme/plugins/jquery-file-upload/js/jquery.fileupload-image.js"></script>
<script type="text/javascript" src="${request.contextPath }/theme/plugins/jquery-file-upload/js/jquery.fileupload-audio.js"></script>
<script type="text/javascript" src="${request.contextPath }/theme/plugins/jquery-file-upload/js/jquery.fileupload-video.js"></script>
<script type="text/javascript" src="${request.contextPath }/theme/plugins/jquery-file-upload/js/jquery.fileupload-validate.js"></script>
<script type="text/javascript" src="${request.contextPath }/theme/plugins/jquery-file-upload/js/jquery.fileupload-ui.js"></script>
<script type="text/javascript" src="${request.contextPath }/res/application/js/chat.js"></script>
	<div class="col-md-9">
	<div class="message-send-content">
	</div>
	<div class="message-send-toolbar navbar-default">
		<ul class="nav navbar-nav">
			<li class="sendfile">
				<span class="btn btn-default fileinput-button">
					<i class="fa fa-file-o"></i><span>文件</span>
					<input type="file" ccinput="file" name="file_files" multiple="" title="发送文件" />
				</span>
			</li>
			<li class="sendimage">
				<span class="btn btn-default fileinput-button">
					<i class="fa fa-image"></i><span>图片</span>
					<input type="file" ccinput="file" name="image_files" multiple="" title="发送图片" />
				</span>
			</a>
			</li>
			<li class="fontstyle">
				<a class="btn btn-default fileinput-button">
					<i class="fa fa-font"></i><span>字体</span>
				</a>
			</a>
			</li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<li>
			<a action="x41" href="javascript: showhistory();" title="查看历史聊天记录"><i class="fa fa-history"></i>历史
			</a>
			</li>
			<li>
			<a  href="javascript:;" class="message-send-btn" title="[Ctrl+Enter]"><i class="fa fa-send"></i>发送
			</a>
			</li>
		</ul>
	</div>
	<div class="message-send-form" data-taget="${pageParam.target}" data-send_to="${pageParam.send_to}" data-avatar="${user.avatar}" data-system_user_name="${user.name}">
		<s:richeditor cols="4" name="content" height="120" toolbar="simple" resize="false"></s:richeditor>
	</div>
	</div>
	<div class="col-md-3">
<c:if test="${pageParam.target eq 'U' }">
		<div class="profile-userpic">
			<c:choose>
				<c:when test="${not empty avatar}">
					<img class="img-responsive" src="${request.contextPath }/rm/s/application/${avatar}S" alt="..." />
				</c:when>
				<c:otherwise>
					<span class="img-responsive"><i class="fa fa-user ${gender eq 0 ? 'female' : '' }"></i></span>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="profile-usertitle">
			<div class="profile-usertitle-name">
				 ${name}
			</div>
			<div class="profile-usertitle-job">
				 ${role_names }
			</div>
		</div>
	<s:form border="0" id="myform">
			<s:row>
				<s:textfield label="真实姓名" cols="4" islabel="true"
					value="${actual_name}"></s:textfield>
				<s:textfield label="性别" islabel="true" cols="4" value="${gender eq 0 ? '女' : '男'}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="所属部门" islabel="true" cols="4" value="${fn:replace(department_names, ',', '>')}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="办公电话" cols="4" islabel="true" value="${telephone}"></s:textfield>
				<s:textfield label="移动电话" cols="4" islabel="true" required="true" value="${mobile}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="邮箱" cols="4" islabel="true"  value="${email}"></s:textfield>
				<s:textfield label="qq" cols="4" islabel="true" value="${qq}"></s:textfield>
			</s:row>
		</s:form>
</c:if>
<c:if test="${pageParam.target ne 'U' }">
		<div class="message-send-users-toolbar">
			<c:if test="${pageParam.target eq 'G' }">
				<c:if test="${currentuserid eq system_user_id }">
					<a onclick="manage_user('${pageParam.target}','${id}');" href="javascript:void(0)"><i class="fa fa-gears"></i>管理</a>
				</c:if>
				<c:if test="${currentuserid ne system_user_id }">
					<a onclick="outGroup('${id}');" href="javascript:void(0)"><i class="fa fa-gears"></i>退出</a>
				</c:if>
			</c:if>
			<a onclick="contacter_users();" href="javascript:void(0)"><i class="fa fa-refresh"></i>刷新</a>
		</div>
		<div class="message-send-users">
			<ul class="media-list list-users">
				<li class="media"><a herf="javascript:void(0);" mtype="D"
					mid="1" mname="超然"> <span class="media-object"><i
							class="fa fa-sitemap"></i></span> <span class="media-body">
							<h4 class="media-heading">超然</h4>
					</span>
				</a></li>
				<li class="media"><a herf="javascript:void(0);" mtype="D"
					mid="1" mname="超然"> </a><a herf="javascript:void(0);" mtype="U"
					mid="CRY0000root" mname="系统超级用户(超然)"> <span
						class="media-object"><i class="fa fa-user"></i></span> <span
						class="media-body">
							<h4 class="media-heading">系统超级用户(超然)</h4>
					</span>
				</a></li>
			</ul>
</c:if>
		</div>
	</div>
</s:page>
<script type="text/javascript">
	function showhistory(){
		$.modal("history.html", "历史消息",{
			width : '80%',
			height : '80%',
			target : $(".message-send-form").attr("data-taget"),
			sendto : $(".message-send-form").attr("data-send_to"),
			callback : function(rtn) {
				if (rtn) {
					designer.page.attr_gj_jg = rtn.attr_gj_jg;
				}
			}
		});
	}
	function manage_user(type,id){
		var url = cooperopcontextpath + "/w/application.contacter.group.html";
		$.modal(url, "管理群组", {
			width : '80%',
			height : '80%',
			id: id,
			type: type,
			callback : function(rtn) {
				if(rtn){
					contacter_users();
				}
			}
		});
	}
	function outGroup(id){
		$.confirm("是否确认退出该群组！" ,function (rtn){
			if(rtn){
				$.call("application.contacter.outGroup", {id: id}, function(rtn) {
					$.closeModal(true);
				});
			}
		});
	}
</script>