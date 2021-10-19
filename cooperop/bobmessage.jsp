<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="http://at.alicdn.com/t/font_1122195_fjtg8ql9q24.css">
    <title>bobmessage</title>
</head>
<style type="text/css">
.content{
    font-size: 12px;
    display: inline-block;
    width: 200px;
    border: 1px solid #ddd;
    border-radius: 3px;
    background-color: #fff;
}

.content .contentBox{
    line-height: 30px;
    padding: 0 10px;
    cursor: pointer;
}

.content .contentBox:hover{
    background-color: #f1f1f1;
}


.content .contentBox i{
    margin-right: 10px;
    color: #3c3c3c;
    font-weight: bold;
}

.content .contentBox span{
    float: right;
    width: 15px;
    height: 15px;
    line-height: 15px;
    text-align: center;
    margin-top: 8px;
    border-radius: 50%;
    background-color: rgba(244, 67, 54, 0.6);
    color: #fff;
}

.p_title{
    margin: 0;
    border-bottom: 1px solid #ddd;
    line-height: 30px;
    padding: 0 10px;
    font-weight: bold;
}

.ahl{
    display: block;
    box-sizing: border-box;
    line-height: 24px;
    width: 100%;
    text-align: right;
    padding-right: 10px;
    border-top: 1px solid #ddd;
    text-decoration: none;
    float: left;
    color: #2196F3;
}
.p_title span {
    padding: 0 2px;
    color: #f88e86;
}
</style>
<body>
    <div class="content">
        <p class="p_title">新消息<%-- (<span>1</span>) --%></p>
        <div class="contentBox task-mes">
            <i class="cicon icon-calendar1"></i>待办消息<span>3</span>
        </div>
        <div class="contentBox notify-mes">
            <i class="cicon icon-ling"></i>通知公告<span>3</span>
        </div>
        <div class="contentBox system-mes">
            <i class="cicon icon-msg"></i>站内消息<span>3</span>
        </div>
        <a href="javascript: void(0);" class="ahl">忽略全部</a>
    </div>
</body>
</html>
<script type="text/javascript">
	$(document).ready(function(){
		$.tasknum(function(num) {
			if(num > 0){
				$(".task-mes span").text(num);
				$(".task-mes").show();
			}else{
				$(".task-mes").hide();
			}
		}, 1);
		$.notificationnum(function(num) {
			if(num > 0){
				$(".notify-mes span").text(num);
				$(".notify-mes").show();
			}else{
				$(".notify-mes").hide();
			}
		}, 1);
		$.systemMessagenum(function(num) {
			if(num > 0){
				$(".system-mes span").text(num);
				$(".system-mes").show();
			}else{
				$(".system-mes").hide();
			}
		}, 1);
		
		$(".ahl").on("click", function(){
			$("application.systemMessage.ignoreAll", {}, function(rtn){
				//调用陈杰关闭冒泡
				crtechCloseWindow();
			})
		})
	})
</script>
</s:page>