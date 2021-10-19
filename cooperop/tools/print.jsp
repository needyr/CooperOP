<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page import="cn.crtech.cooperop.bus.util.spring.HtmlUtils"%>
<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	pageContext.setAttribute("system_title", SystemConfig.getSystemConfigValue("global", "system_title"));
	Enumeration<String> names = request.getParameterNames();
	
	
	String filename = "";
	String _page = "";
	StringBuffer params = new StringBuffer();
	while (names.hasMoreElements()) {
		String name = names.nextElement();
		String[] values = request.getParameterValues(name);
		if ("filename".equals(name)) {
			filename = values[0];
			params.append("&" + name + "=" + values[0]);
		} else if ("_page".equals(name)) {
			_page = values[0].replace('.', '/');
			params.append("&" + name + "=" + values[0]);
		} else if (values != null) {
			for (int i = 0; i < values.length; i ++) {
				values[i] = HtmlUtils.htmlEscape(values[i]);
				params.append("&" + name + "=" + values[i]);
			}
			if (values.length == 1) {
				pageContext.setAttribute(name,values[0]);
			} else if (values.length > 1) {
				pageContext.setAttribute(name,values);
			}
		}
	}
	pageContext.setAttribute("url", request.getContextPath() + "/w/" + _page + ".pdf?" + params.toString().substring(1));
	pageContext.setAttribute("filename", filename);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<title>${system_title }</title>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link
	href="${pageContext.request.contextPath}/theme/plugins/google-fonts/opensans.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/theme/plugins/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/theme/plugins/simple-line-icons/simple-line-icons.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/theme/plugins/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/theme/plugins/uniform/css/uniform.default.css"
	rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="${pageContext.request.contextPath}/theme/css/components.css"
	id="style_components" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/theme/css/plugins.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/theme/layout/css/layout.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/theme/layout/css/themes/grey.css"
	rel="stylesheet" type="text/css" id="style_color" />
<link
	href="${pageContext.request.contextPath}/theme/layout/css/custom.css"
	rel="stylesheet" type="text/css" />
<!-- END THEME STYLES -->
<style>
body {
	background: transparent;
}
body .noprint {
	text-align:center;
}
body .print {
	text-align:center;
	margin-top: 10px;
	max-width: 100%;
}

@media print {
	body .noprint {
		display: none;
	}
	body .print {
		text-align:left;
		margin-top: 0px;
		max-width: none;
	}
}
</style>
<link rel="shortcut icon" href="favicon.ico" />
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
	var cooperopcontextpath = "${pageContext.request.contextPath}";
</script>
</head>
<!-- END HEAD -->
<body>
	<div class="noprint">
		${pageParam}
		<a href="javascript:location.reload();" class="icon-btn"> <i class="fa fa-refresh"><i></i></i>
			<div>刷新</div>
		</a>
		<a href="${url}&type=jpg&_rate=2&attachment=true" class="icon-btn"> <i class="fa fa-file-image-o"><i></i></i>
			<div>保存为JPG图片</div>
		</a>
		<a href="${url}&attachment=true" download="true" class="icon-btn"> <i class="fa fa-file-pdf-o"><i></i></i>
			<div>保存为PDF</div>
		</a>
		<a href="javascript:printpdf();" class="icon-btn"> <i class="fa fa-print"><i></i></i>
			<div>打印</div>
		</a>
	</div>
	<div class="print">
		<iframe id="pdfframe" src="${url}" frameborder="0" width="100%" height="100%"></iframe>
	</div>
	<script>
		$(document).ready(function() {
			$("#pdfframe").height($(document).height() - $("#pdfframe").offset().top - 10);
		});
		function printjpg() {
			window.print();
		}
		function printpdf() {
			pdfframe.contentWindow.print();
		}
	</script>
	<!-- END JAVASCRIPTS -->
</body>
<!-- END BODY -->
</html>