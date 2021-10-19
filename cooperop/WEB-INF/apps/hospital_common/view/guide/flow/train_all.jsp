<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="全院培训工作" >
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
	<p class="title">全院培训工作</p>
	<p class="msg">
	1、联系信息科和业务主管科室确定全院启动时间，通知科室培训时间和地点;<br>
	2、提前准备好培训ppt和视频，并让信息科和主管科室签署<a href="/rm/downTemp/hospital_common/3-4 正式运行申请表.xls" download="3-4 正式运行申请表.xls">3-4 正式运行申请表.xls</a><br>
	3、准备好<a href="/rm/downTemp/hospital_common/3-2培训签到表.xls" download="3-2培训签到表.xls">3-2培训签到表.xls </a>做好会议签到和照相工作准备。<br><br>
	该步骤仅为提示作用，已经完成，直接点击下方【完成】即可。<br>
	</p>
</div>
</s:page>
<script type="text/javascript">
	$(function(){
		//console.log('${params.id}')
	});
	
</script>