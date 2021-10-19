<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="扣费数据分析" >
<link href="${pageContext.request.contextPath}/res/hospital_common/css/guide/index.css" rel="stylesheet" type="text/css">
<style type="text/css">
.main {
	padding: 0px;
}
</style>
<s:row>
  <div class="note note-warning">
    <p>注意：如果页面不存在请启动DTC服务后再使用。</p>
  </div>
</s:row>
<div class="page-wrap">
	<div class="column-default" style="min-height: 300px;width: 100%;height:100%;">
		<ul class="large-btn">
			<li><a href="#" onclick="start();"><i class="icon-hourglass"></i>扣费数据分析</a></li>
		</ul>
	</div>
</div>
</s:page>
<script type="text/javascript">
	$(function(){
	});
	
	function start(){
		$.modal("/w/hospital_imic/dda/task/list.html","扣费数据分析",{
	        width:"90%",
	        height:"90%",
	        callback : function(e){
	        }
		});
	}
	
</script>