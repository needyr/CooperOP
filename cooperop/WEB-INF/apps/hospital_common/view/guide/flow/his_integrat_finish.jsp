<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="HIS集成节点完成" >
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
	<p class="title">HIS集成节点完成</p>
	<p class="msg">确认集成接口已经完成:<br>
	1、根据集成文档进行测试;<br>
	2、测试形成测试文档;<br>
	3、标注还未完成的集成点，反馈给公司判定是否可以进行上线试点。<br><br>
	该步骤仅为提示作用，如HIS已经完成集成工作，并且测试了接口
	<font class="warn">无任何问题后</font>，直接点击下方【完成】即可。
	</p>
</div>
</s:page>
<script type="text/javascript">
	$(function(){
		//console.log('${params.id}')
	});
	
</script>