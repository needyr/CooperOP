<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" disloggedin="">
<style type="text/css">
	body{
		overflow: hidden;
	}
	#outer{
		height: 100%;
		width: 100%;
		padding-left: 5%;
		padding-top: 5px;
	}
	#mian{
		height: 90%;
		width: 100%;
	}
	#mian input{
		right: 25px;
		position: absolute;
	}
</style>
<div id="outer">
	<div id="mian" >
		<input type="button" value="复制" onclick="copy()">
		<textarea rows="29" cols="79%" id="databunch"></textarea>
	</div>
</div>
</s:page>
<script type="text/javascript">
$(function(){
	var databunnch;
	databunnch='${param.data}';
	$("#databunch").val(databunnch.replace(/\r<br>/g, "\n").replace(/<br>/g,"\n"));

});
function copy(){
	var data=$("#databunch").val();
	const input = document.createElement('input');
	document.body.appendChild(input);
	input.setAttribute('value', data);
	input.select();
	if (document.execCommand('copy')) {
		layer.msg('复制成功', {
	 		  icon: 1,
	 		  time: 800 //2秒关闭（如果不配置，默认是3秒）
	 		}, function(){
	 		  //do something
	 		}); 
	}
	document.body.removeChild(input);
	event.stopPropagation(); 
}
</script>