<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>邮件</title>
<meta charset="UTF-8">
<meta name="viewport"
	content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,initial-scale=1.0,width=device-width" />
<meta name="format-detection"
	content="telephone=no,email=no,date=no,address=no">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/font-awesome/css/font-awesome.min.css?iml=Y">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/api.css?iml=Y">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/common.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/readEmail.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/bootstrap/bootstrap.css?iml=Y">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/theme/mobile/simple-line-icons/simple-line-icons.css?iml=Y">

<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/css/jquery.fileupload.css?iml=Y">
<link rel="stylesheet" href="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/css/jquery.fileupload-ui.css?iml=Y">

<script type="text/javascript">
 var cooperopcontextpath = '${pageContext.request.contextPath}';
</script>
<script
	src="${pageContext.request.contextPath}/theme/plugins/apicloud/api.js?iml=Y"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js?iml=Y"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/vendor/tmpl.min.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/vendor/load-image.min.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/vendor/canvas-to-blob.min.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/blueimp-gallery/jquery.blueimp-gallery.min.js?iml=Y" type="text/javascript">
</script><script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/jquery.iframe-transport.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/jquery.fileupload.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-process.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-image.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-audio.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-video.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-validate.js?iml=Y" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-ui.js?iml=Y" type="text/javascript"></script>


<script src="${pageContext.request.contextPath}/theme/scripts/window.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/email.js?iml=Y"
	type="text/javascript"></script>
</head>
<body>
	<div id="wrap">
		<div class="header">
			<div class="header-div">
				<a href="javascript: void(0);" class="tiaoZhuan" onclick="backtomain();"><i
					class="icon-arrow-left"></i></a>
				<h4 class="header-h3">邮件</h4>
			</div>
		</div>
		<div id="main">
			<div class="main-div">
				<h3>${$return.subject }</h3>
				<p><span>发件人：</span>${$return.send_user_name}</p>
				<p><span>收件人：</span>${$return.to_name}</p>
				<p><span>时 &nbsp;&nbsp;&nbsp;间：</span>${$return.send_time}</p>
				<div class="fujian attach">
					<input type="hidden" name="attach_files" class="file_file" value="${$return.attach_files }"/>
				</div>
			<div class="email-content">
				<p>${$return.content }</p>
			</div>
			</div>
			
		</div>
		<div id="footer">
			<div class="footer-main">
				<div class="footer-mainDiv reply">
					<a href="javascript:void(0);"> <i class="fa fa-mail-reply"> </i>
					</a>
					<p class="footer-p ">回复</p>
				</div>
				<div class="footer-mainDiv replay-all">
					<a href="javascript:void(0);"> <i class="fa fa-mail-reply-all">
					</i>
					</a>
					<p class="footer-p">回复全部</p>
				</div>
				<div class="footer-mainDiv share">
					<i class="fa fa-share"></i>
					<p class="footer-p ">转发</p>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript">
$(document).ready(function(){
		//$(".fujian.attach").ccinit_appfile();
		$(".footer-mainDiv.reply").on("click",function(){
			$.open("application.mobile.email.modify",{nofooter: true, linkid: '${param.id}', type: 'r'});
		});
		$(".footer-mainDiv.replay-all").on("click",function(){
			$.open("application.mobile.email.modify",{nofooter: true, linkid: '${param.id}', type: 'ra'});
		});
		$(".footer-mainDiv.share").on("click",function(){
			$.open("application.mobile.email.modify",{nofooter: true, linkid: '${param.id}', type: 's'});
		});
		$(".fujian.attach").ccinit_appfile();
	});
	function re(){
		location.reload();
	}
	function backtomain(){
		$.close();
	}
</script>
</body>
</html>