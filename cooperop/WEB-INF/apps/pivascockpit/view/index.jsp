<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="我的驾驶舱" disloggedin="true">
<link type="text/css" rel="stylesheet" href="${contextpath}/res/${module}/styles/index.css"></link>
	<ul class="cockpit-list">
		<c:forEach items="${cockpits}" var="cockpit">
			<c:if test="${not empty cockpit.preview }">
				<c:set var="img" value="background-image:url('${contextpath}/rm/s/${module}/${cockpit.preview}')"></c:set>
			</c:if>
			<li>
				<a href="javascript:void(0);" onclick="show(${cockpit.id});">
					<i style="${img}"></i>
					<span>${cockpit.name}</span>
				</a>
			</li>
		</c:forEach>
	
	</ul>
	<script type="text/javascript">
	$(document).ready(function() {
	});
	function show(id) {  //, top=0, left=0, height=768, width=1366
		window.open("cockpit.html?id=" + id, "cockpit_" + id);
// 		window.open("cockpit.html", "cockpit", "channelmode=yes, directories=no, fullscreen=yes, location=no, menubar=no, resizable=no, scrollbars=yes, status=no, titlebar=no, toolbar=no, copyhistory=yes");
	}
	
// 	channelmode=yes|no|1|0	是否要在影院模式显示 window。默认是没有的。仅限IE浏览器
// 	directories=yes|no|1|0	是否添加目录按钮。默认是肯定的。仅限IE浏览器
// 	fullscreen=yes|no|1|0	浏览器是否显示全屏模式。默认是没有的。在全屏模式下的 window，还必须在影院模式。仅限IE浏览器
// 	height=pixels	窗口的高度。最小.值为100
// 	left=pixels	该窗口的左侧位置
// 	location=yes|no|1|0	是否显示地址字段.默认值是yes
// 	menubar=yes|no|1|0	是否显示菜单栏.默认值是yes
// 	resizable=yes|no|1|0	是否可调整窗口大小.默认值是yes
// 	scrollbars=yes|no|1|0	是否显示滚动条.默认值是yes
// 	status=yes|no|1|0	是否要添加一个状态栏.默认值是yes
// 	titlebar=yes|no|1|0	是否显示标题栏.被忽略，除非调用HTML应用程序或一个值得信赖的对话框.默认值是yes
// 	toolbar=yes|no|1|0	是否显示浏览器工具栏.默认值是yes
// 	top=pixels	窗口顶部的位置.仅限IE浏览器
// 	width=pixels	窗口的宽度.最小.值为100

	</script>
</s:page>