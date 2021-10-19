<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="人员、部门数据导入" >
<link href="${pageContext.request.contextPath}/res/hospital_common/css/guide/index.css" rel="stylesheet" type="text/css">
<style type="text/css">
.main {
	padding: 0px;
}
</style>
<div class="page-wrap">
	<div class="column-default" style="min-height: 300px;width: 100%;height:100%;">
		<ul class="large-btn">
			<li><a href="#" onclick="start();"><i class="icon-users"></i>人员、部门数据导入</a></li>
		</ul>
		<ul class="large-btn" style="margin-top: -110px;">
		  <li>
		    <s:button icon="" label="部门数据查看" onclick="queryDicthisdeptment()"></s:button>
		    <s:button icon="" label="用户数据查看" onclick="queryDicthisuser()"></s:button>
		  </li>
		</ul>
	</div>
</div>
</s:page>
<script type="text/javascript">
	$(function(){
		
	});
	
	function start(){
		$.call('hospital_common.guide.flow.initUsers',{flow_id:'${param.id}'},function(rtn){
			if(rtn){
				$.message('执行成功!');
			}else{
				$.message('执行失败!');
			}
	  },null,{nomask:true,timeout:-1})
	}
	
	function queryDicthisdeptment(){
		$.openTabPage('hospital_common.guide.flow.dict_his_deptment_list','部门数据查看','hospital_common.guide.flow.dict_his_deptment_list',true,null,null)
	}
	
	function queryDicthisuser(){
		$.openTabPage('hospital_common.guide.flow.dict_his_user_list','用户数据查看','hospital_common.guide.flow.dict_his_user_list',true,null,null)
	}
</script>