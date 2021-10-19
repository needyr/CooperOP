<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="上线准备工作" >
<style type="text/css">
.main {
	padding: 20px;
	background-color: #e2f1ff;
    height: 100%;
}

.msg {
	font-size: 15px;
	/*text-align: center;*/
    letter-spacing: 2px;
    line-height: 25px;
}

.title {
	font-size: 28px;
    font-weight: 600;
    /*text-align: center;*/
}
.warn {
	font-size: 15px;
    color: #d40000;
}
</style>
<div class="info">
	<p class="title">上线前准备工作</p>
	<p class="msg">
		1、协调信息科以及业务主管科室确定试点科室和时间，提交<a href="/rm/downTemp/hospital_common/3-1项目试点运行申请表.xls" download="3-1项目试点运行申请表.xls">3-1项目试点运行申请表.xls</a>,并确认签字。<br>
	2、准备<a href="/rm/downTemp/hospital_common/3-2培训签到表.xls" download="3-2培训签到表.xls">3-2培训签到表.xls </a>试点时进行培训签字。<br>
	</p>
</div>
</s:page>
<script type="text/javascript">
	$(function(){
		//console.log('${params.id}')
	});
	
</script>