<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="消息内容提示" disloggedin="true">
<style type="text/css">
	*{
		margin: 0px auto;
		padding: 0px;
		font-family: "微软雅黑";
	}

	.main{
		padding: 0px;
	}
	
	.page-content {
   		padding: 0px !important;
	}

	pre{
		font-family: "微软雅黑";	
		height: 100%;
	    background: #ffffff;
	    border: 0px;
	    font-size: 16px;
	    color: #000000;
	}

	div#main {
	    width: 700px;
	    height: 500px;
	    border: 0px solid #d8d8d8;
	}
</style>
	<s:row>
		<div id="main">
			<pre id="msg_pre">
			 </pre>
		</div>
	</s:row>
<script type="text/javascript">
	$(function (){
		$('#msg_pre').html('${param.content_detail}');
	});
</script>
</s:page>
