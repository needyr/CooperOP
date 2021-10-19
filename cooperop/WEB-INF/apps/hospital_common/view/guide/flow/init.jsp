<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="开始初始化" >
<link href="${pageContext.request.contextPath}/res/hospital_common/css/guide/index.css" rel="stylesheet" type="text/css">
<style type="text/css">
.main {
	padding: 0px;
}
</style>
<s:row>
  <div class="note note-warning">
    <p>1. 初始化前请提供医院基础信息中的【医院ID】、【医院名称】，由公司给到密码后方可进行初始化。</p>
    <p style="color:red">2. 请勿随意操作，否则后果自负！！！！</p>
  </div>
</s:row>
<div class="page-wrap">
	<div class="column-default" style="min-height: 300px;width: 100%;height:100%;">
	<ul class="large-btn">
	<li><a style="box-shadow: 0 3px 8px #8c8888;" href="#" onclick="start();"><i class="icon-loop"></i>开始初始化</a></li>
	</ul></div>
</div>
</s:page>
<script type="text/javascript">
	$(function(){
		$.message('<span style="color:red;font-size:14px">注意！当前页面操作及其敏感，请勿随意执行！！！</span>');
		$('.column-default').css({'background-size':'70px','background-image':'url('+'${pageContext.request.contextPath}'+'/res/hospital_common/img/warning.png)'});
	});
	
	function start(){
		if('${param.id}'){
			layer.open({
			  type: 1, 
			  title: '校验密码',
			  area: ['300px', '300px'],
			  btn: ['确定', '取消'],
			  skin: 'layui-layer-lan',
		      closeBtn: 0,
			  content:'<div class="control-content">'
				  +'<textarea style="height: 170px;margin-top:20px" class="form-control" type="textarea" cols="4" maxlength="2000" id="finish_text"></textarea>'
				  +'</div>', //这里content是一个普通的String
			  yes: function(index, layero){
				  $.call('hospital_common.guide.flow.initData',{flow_id:'${param.id}',password:layero.find('#finish_text').val()},function(rtn){
						if(rtn){
							$.message('执行成功!');
						}else{
							$.message('执行失败!');
						}
						layer.close(index);
				  },null,{nomask:false})
			  }
			});
		}else{
			$.message('流程ID不能为空!');
		}
	}
</script>