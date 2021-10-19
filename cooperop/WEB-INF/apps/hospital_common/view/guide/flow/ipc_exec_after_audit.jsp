<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="事后审查" >
<link href="${pageContext.request.contextPath}/res/hospital_common/css/guide/index.css" rel="stylesheet" type="text/css">
<style type="text/css">
.main {
	padding: 0px;
}
</style>
<div class="page-wrap">
	<div class="column-default" style="min-height: 300px;width: 100%;height:100%;">
		<ul class="large-btn">
			<li><a href="#" onclick="start();"><i class="icon-hourglass"></i>执行【合理用药】事后审查</a></li>
			<li><a href="#" onclick="start2();"><i class="fa fa-mail-forward"></i>事后审查查看</a></li>
		</ul>
	</div>
</div>
</s:page>
<script type="text/javascript">
	$(function(){
		console.log('${params.id}')
	});
	
	function start(){
	  $.call('hospital_common.guide.flow.execOneAfterAudit',{flow_id:'${param.id}',exec_pro:'JCR_after_auto_audit_discharge'},function(rtn){
			$.message('执行成功!');
	  },null,{nomask:true,timeout:-1})
	}
	
	function start2(){
		$.openTabPage('hospital_common.afaudit.list','事后审查记录查看','hospital_common.afaudit.list',true,null,null)
	}
</script>