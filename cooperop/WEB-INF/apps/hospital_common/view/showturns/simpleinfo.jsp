<!-- 该文件现已被simpleinfo2.jsp取代 请勿使用此页面 2020.02.01-->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    
    <link rel="stylesheet" href="/theme/plugins/bootstrap/css/bootstrap.min.css?m=ipc">
	<link rel="stylesheet" type="text/css" href="/theme/plugins/font-awesome/css/font-awesome.min.css?m=ipc">
	<script type="text/javascript" src="/theme/plugins/jquery.min.js"></script>
	
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery.cokie.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery.json.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/scripts/metronic.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/scripts/layout.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/scripts/common.js" type="text/javascript"></script>
	<!-- <link rel="stylesheet" type="text/css" href="//at.alicdn.com/t/font_578622_lqkafxrjl717cik9.css"> -->
	<link href="${pageContext.request.contextPath}/res/hospital_common/css/demo2.css" rel="stylesheet" type="text/css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/font-awesome/css/font-awesome.min.css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/simple-line-icons/simple-line-icons.min.css">
</head>
<style type="text/css">
    .content{
        width: 450px;
         height: 310px; 
        box-shadow: 0 0 10px #c8c4c4;
    }

    .content .title{
        margin: 0;
        line-height: 35px;
        background-color: #eee;
        text-align: center;
        font-size:14px;
    }

    .content .dw{
        margin: 0;
        padding: 0 10px;
        font-size: 14px;
        line-height: 29px;
        border-bottom: 1px solid #eee;
    }

    .content .dw>span{
        margin-right: 20px;
    }

    .main{
        height: 180px;
        overflow: auto;
        padding: 5px 10px;
        border-bottom: 1px solid #eee;
    }

    .main .mainP{
        margin: 0;
        font-size: 12px;
        line-height: 25px;
        font-weight: 600;
    }

    .main .mainC{
        font-size: 12px;
        margin: 0;
    }

    .linkDiv{
        padding:0 10px;
    }

    .link{
        font-size: 12px;
        text-decoration: none;
    }

    .link:hover{
        text-decoration: underline;
    }
</style>
<body>
<div class="content">
    <p class="title"></p>
    <p class="dw" style="border: none;">
        <span id="gg">规格：</span>
    </p>
     <p class="dw">
        <span>单位：支(暂无)</span>
        <span>单价：26（暂无）</span>
    </p>
    <div class="main">
    	
    </div>
    <div class="linkDiv">
       <!--  <a href="" class="link">说明书</a>丨
        <a href="" class="link">抗菌谱</a>丨
        <a href="" class="link">剂量</a>丨
        <a href="" class="link">不良反应</a>丨
        <a href="" class="link">禁忌症</a>丨
        <a href="" class="link">治疗方案</a> -->
    </div>
</div>
</body>
</html>
<script type="text/javascript">
$(document).ready(function(){
	initPage();
});
function initPage(){
	$.call("hospital_common.showturns.getSimpleInfo",
			{
			  "drug_code" : '${param.drug_code}'
			},function(rtn){
				if(rtn && rtn.drug){
					$("title").append(rtn.drug.name_cn);
					$(".content .title").append(rtn.drug.name_cn);
					$("#gg").append(rtn.drug.spec);
					$.each(rtn.simpleDesc,function(index,v){
						 var s='<p class="mainP">'+v.brief_title+':</p><p class="mainC">'+(eval("rtn.drug."+v.column_name)==null?"":eval("rtn.drug."+v.column_name))+'</p>'
						$(".main").append(s);
					});
				}else{
					$.message('很抱歉，暂无该药品介绍!');
				}
				$.each(rtn.assist,function(ix,vx){
					var apn='<a href = "javascript:void(0);" data-url="'+vx.url+'" class="link" target="_blank">'+vx.queryname+'</a>';
					 if(ix < rtn.assist.length-1){
						apn=apn+'丨'
					} 
					$(".linkDiv").append(apn);
				}); 
				$(".linkDiv").find(".link").on("click", function(){
					var u = $(this).attr("data-url");
					if(crtech){
						crtech.modal(u, "800", "400");//宽高可以传百分比
					}
				});
			}
	);
}
</script>