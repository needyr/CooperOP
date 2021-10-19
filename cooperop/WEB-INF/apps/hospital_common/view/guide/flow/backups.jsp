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
    <p>注意：如果睿备份软件的设置错误，将会导致调用软件失败。具体配置在软件内进行配置。</p>
  </div>
</s:row>
<div class="page-wrap">
	<div class="column-default" style="min-height: 300px;width: 100%;height:100%;">
		<ul class="large-btn">
			<li><a href="#" onclick="start();"><i class="fa fa-gear"></i>配置备份软件</a></li>
		</ul>
		<ul class="large-btn" style="margin-top: -110px;">
			<li>
				<s:button icon="" label="睿备份说明书" onclick="rbf()"></s:button>
			</li>
		</ul>
	</div>
</div>
</s:page>
<script type="text/javascript">
	$(function(){
		
	});
	
	function start(){
	  var str = "1、该操作只能够在服务器本机上进行操作。\n2、将睿备份软件系统解压到[D:\睿备份]目录下。\n3、点击按钮进行备份配置。\n4、一般要求每日晚上备份一次，也可以每周进行一次备份，备份数据保留1-3天。";
	  $.call('hospital_common.guide.flow.callRBFexe',{flow_id:'${param.id}'},function(rtn){
			$.message('执行成功!');
	  },function(){
		  $.error(str);
	  },{nomask:true,timeout:-1})
	}

	function rbf(){
		$.modal("/rm/lookTemp/hospital_common/如何设置睿备份 - 睿备份.pdf","睿备份说明书",{
			width:"90%",
			height:"90%",
			callback : function(e){
				if(e){

				}
			}
		});
	}
</script>