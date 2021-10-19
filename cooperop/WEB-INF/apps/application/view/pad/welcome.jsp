<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/theme/plugins/font-awesome/css/font-awesome.min.css?ipl=Y">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/theme/plugins/simple-line-icons/simple-line-icons.min.css?ipl=Y">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/theme/css/aliicon/iconfont.css?ipl=Y">
<link href="${pageContext.request.contextPath}/theme/css/im.css?ipl=Y" type="text/css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js?ipl=Y"
	type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/themes/im/script/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/themes/im/script/md5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/themes/im/script/common.js"></script>
<script type="text/javascript" src="${request.contextPath }/res/application/js/padchat.js"></script>
<style type="text/css">
.leftMain {
	margin-left: 20px;
    border-left: 1px solid lightgrey;
}

.rightMain {
	margin-left: 20px;
    margin-right: 5px;
    border-right: 1px solid lightgrey;
}
</style>
</head>
<body>
<div class="content">
    <div class="leftMain">
        <div class="iptDiv"><input type="text" placeholder="搜索" class="choho-im-search-input"><i class="icon-magnifier"></i>
        	<i class="icon-close"></i>
				<div class="choho-im-search-warper">
					<div class="choho-im-search-result">
						<div class="choho-im-search-result-title">联系人</div>
						<div class="choho-im-search-result-list U">
						</div>
					</div>
					<div class="choho-im-search-result">
						<div class="choho-im-search-result-title">部门</div>
						<div class="choho-im-search-result-list D"></div>
					</div>
					<div class="choho-im-search-result">
						<div class="choho-im-search-result-title">群组</div>
						<div class="choho-im-search-result-list G"></div>
					</div>
				</div>
        </div>
        <div class="listDiv left-content active">
            <%-- <div class="list">
                <img src="/theme/img/avatar3_small.jpg" alt="">
                <p class="titleP">测试群会话</p>
                <p class="textP">最受欢迎的送祝福方式</p>
                <span class="daySpan">5月4日</span>
                <span class="weiSpan">9</span>
                <i class="iconfont icon-mangluim status" ></i>
            </div> --%>
        </div>
        <div class="qz-listDiv left-content">
            <!-- <div class="list">
                <img src="../img/logo1.png" alt="">
                <p class="titleP">测试群会话</p>
            </div> -->
        </div>
        <div class="jg-listDiv left-content">
            <p class="dhP"></p>
            <%-- <div class="list">
                <img src="../img/logo1.png" alt="">
                <p class="titleP"><span class="content-text">测试群会话</span> (<span class="child_num">5</span>)</p>
                <i class="fa fa-angle-right"></i>
            </div> --%>
        </div>
        <div class="footer">
            <div class="session footerdiv active">
                <i class="icon-speech"></i>
                <span>会话</span>
            </div>
            <div class="contactor footerdiv">
                <i class="icon-user"></i>
                <span>通讯录</span>
            </div>
            <div class="group footerdiv">
                <i class="icon-users"></i>
                <span>群组</span>
            </div>
        </div>
    </div>
    <div class="right-content">
    </div>
   	<div class="rightMain-mask"></div>
</div>
</body>
</html>
