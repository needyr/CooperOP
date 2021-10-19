<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="实施向导" >
<link href="${pageContext.request.contextPath}/res/hospital_common/css/guide/index.css" rel="stylesheet" type="text/css">
<style type="text/css">
.main {
	padding: 0px;
}
</style>
<c:if test="${$return.is_finish eq true}">
<h1 style="text-align: center;">恭喜,已经完成了实施流程!</h1>
</c:if>
<c:if test="${$return.is_finish ne true}">
<div class="page-wrap">
	<div class="column-default" style="min-height: 300px;width: 100%;height:100%;">
		<ul class="large-btn">
			<li><a href="#" onclick="start();"><i class="cicon icon-note"></i>开始实施</a></li>
		</ul>
	</div>
</div>
</c:if>
</s:page>
<script type="text/javascript">
	$(function(){
		//console.log('${params.id}')
	});
	
	function start(){
		parent.ref_next();
	}
</script>