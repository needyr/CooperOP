<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="上线科室" >
<link href="${pageContext.request.contextPath}/res/hospital_common/css/guide/index.css" rel="stylesheet" type="text/css">
<style type="text/css">
.main {
	padding: 0px;
}
</style>
<div class="note note-warning">
    <p>确认试点科室，提前进行开科，并测试，再到科室培训试点。</p>
  </div>
<div class="page-wrap">
	<div class="column-default" style="min-height: 300px;width: 100%;height:100%;">
		<ul class="large-btn">
			<li><a href="#" onclick="start();"><i class="fa fa-medkit"></i>【医保控费】</a></li>
			<li><a href="#" onclick="start2();"><i class="fa fa-flask"></i>【合理用药】</a></li>
		</ul>
	</div>
</div>
</s:page>
<script type="text/javascript">
	$(function(){
		console.log('${params.id}')
	});
	
	function start(){
		$.modal("/w/hospital_common/auditset/checkdept/imic_list.html","【医保控费】上线科室",{
	        width:"90%",
	        height:"90%",
	        callback : function(e){
	        }
		});
	}
	
	function start2(){
		$.modal("/w/hospital_common/auditset/checkdept/ipc_list.html","【合理用药】上线科室",{
			width:"90%",
	        height:"90%",
	        callback : function(e){
	        }
		});
	}
</script>