<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="确认完成数据库还原" >
<link href="${pageContext.request.contextPath}/res/hospital_common/css/guide/index.css" rel="stylesheet" type="text/css">
<style type="text/css">
.main {
	padding: 0px;
}
</style>
<div class="page-wrap">
	<div class="column-default" style="min-height: 300px;width: 100%;height:100%;">
		<ul class="large-btn1">
		    <li style="margin: auto;font-size: 14px;">
		      平台支持库：IADSCP<br>
              平台数据支持库：hospital_common<br>
              平台数据采集库：data_collectserver<br>
              平台质控预警库：hospital_mqc<br>
              合理用药审查库：hospital_autopa<br>
              合理用药点评库：hospital_oc<br>
              合理用药第三方接口库：autopa_thirdparty<br>
              医保智能审查库：hospital_imic<br>
              医保病组分组审查库：hospital_imic_drgs<br>
              病案首页审查库：hospital_bazk<br>
		    </li>
            <li style="margin: auto;"><a href="#" onclick="start();"><i class="fa fa-database"></i>确认完成数据库还原</a></li>
            <li style="margin: auto;font-size: 14px;">
              还原程序<br>
              配置程序下的DataSource.xml文件的地址、用户名、密码
            </li>
		</ul>
    <div>
</div>
</s:page>

<script type="text/javascript">
	$(function(){
		//console.log('${params.id}')
	});
	
	function start(){
		parent.$('.finish').click();
	}
</script>