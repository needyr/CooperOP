<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="消息未读提示" disloggedin="true">
<style type="text/css">
	body {
		background: white;
	}
	.main{
		padding: 0;
		margin: 0 auto;
		background: white;
	}
	#content{
		width: 300px;
		height: 200px;
		border: 0px solid #dad4d4;
		background: white;
		padding: 5px;
		font-size: 18px;
		text-align: center;
	}
	#btn{
		width: 100%;
		bottom: 0px;
		height: 40px;
		border: 0;
		background: #46a0d4;
		color: #ffffff;
		font-weight: 600;
		font-size: 14px;
		border-radius: 4px;
	}
	#btn:hover{
		box-shadow: 0px 0px 1px 2px #d4d4d4;
	}
	.msg-title {
		font-size: 18px;
		line-height: 30px;
		font-weight: 600;
		color: #e2774e;
	}
	.msg-content {
		font-size: 16px;
		line-height: 28px;
		border-top: 1px solid #dcd9d9;
		border-bottom: 1px solid #dcd9d9;
		text-align: left;
	}
	.msg-btn {

	}
	.close-desc{
		color: #d6d4d4;
		margin: 5px !important;
	}
	.icon{
	font-size: 22px;
	}
</style>
	<s:row>
		<div id="content">
			<div class="msg-title">
				<i class="fa fa-info-circle icon"></i> 重要提醒
			</div>
			<div class="msg-content">
				您似乎没有注意到屏幕右下角的提示，这可能导致您遗漏非常重要的信息，请及时关注并处理！
			</div>
			<div class="msg-btn">
				<p class="close-desc"><span id="ss"></span>秒后自动关闭</p>
				<button id="btn" onclick="closeMsg();">确定</button>
			</div>
		</div>
	</s:row>
</s:page>
<script type="text/javascript">
	var info = {second: 30};
	$(function(){
		readSecond();
		setInterval('readSecond()', 1000);
		setTimeout("closeMsg()", 1000 * info.second)
	});

	function readSecond(){
		$('#ss').text(--info.second);
	}

	function closeMsg(){
		if("undefined" != typeof crtech) {
			window.close();
		}
	}
</script>