<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="日志查看" >
<link href="${pageContext.request.contextPath}/res/hospital_common/css/guide/index.css" rel="stylesheet" type="text/css">
<style type="text/css">
.main {
	padding: 0px;
}
</style>
<div class="page-wrap">
	<div class="column-default" style="min-height: 300px;width: 100%;height:100%;">
		<ul class="large-btn">
			<li><a href="#" onclick="start();"><i class="fa fa-tags"></i>操作日志</a></li>
			<li><a href="#" onclick="start2();"><i class="glyphicon glyphicon-warning-sign"></i>错误日志</a></li>
		</ul>
	</div>
</div>
</s:page>
<script type="text/javascript">
	$(function(){
		
	});
	
	function start(){
		var page = "hospital_common.guide.flow.flow_log";
		$.openTabPage(page,'流程操作日志',page,true,null,null)
		$.closeModal(true);
	}
	
	function start2(){
		var page = "hospital_common.guide.flow.flow_log_error";
		$.openTabPage(page,'流程错误日志',page,true,null,null)
		$.closeModal(true);
	}
</script>