<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="配置备份软件" >
<link href="${pageContext.request.contextPath}/res/hospital_common/css/guide/index.css" rel="stylesheet" type="text/css">
<style type="text/css">
.main {
	padding: 0px;
}
</style>
<s:row>
  <div class="note note-warning">
    <p>这只是一个提示流程</p>
  </div>
</s:row>
<h1>异地备份提示</h1>
<h3 style="color: red;">1、请联系信息科进行异地备份。需要加入到医院的整体备份计划。<br>
2、一般要求每日晚上备份一次，也可以每周进行一次备份。<br>
3、如果不进行异地备份，一旦感染勒索等病毒，将无法读取本地数据(包含本地备份数据)。<br>
</h3>
</s:page>
<script type="text/javascript">
	$(function(){
		
	});
	
</script>