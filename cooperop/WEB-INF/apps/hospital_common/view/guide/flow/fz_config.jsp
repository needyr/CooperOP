<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="辅助查询地址更改" >
<link href="${pageContext.request.contextPath}/res/hospital_common/css/guide/index.css" rel="stylesheet" type="text/css">
<style type="text/css">
.main {
	padding: 0px;
}
</style>
<div class="page-wrap">
	<div class="column-default" style="min-height: 300px;width: 100%;height:100%;">
		<ul class="large-btn">
			<li><a href="#" onclick="start();"><i class="fa fa-tags"></i>简要信息地址更改</a></li>
			<li><a href="#" onclick="start2();"><i class="fa fa-gears"></i>系统地址更改</a></li>
		</ul>
	</div>
</div>
</s:page>
<script type="text/javascript">
	$(function(){
		console.log('${params.id}')
	});
	
	function start(){
		$.modal("/w/hospital_common/instructionconfig/assistconfig.html","简要信息地址更改",{
	        width:"90%",
	        height:"90%",
	        callback : function(e){
	        	if(e){
	        		
	        	}
	        }
		});
	}
	
	function start2(){
		$.modal("/w/hospital_common/dict/dictsysurl/list.html","系统地址更改",{
			width:"90%",
	        height:"90%",
	        callback : function(e){
	        }
		});
	}
</script>