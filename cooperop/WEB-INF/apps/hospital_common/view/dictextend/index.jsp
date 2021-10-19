<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<title></title>
<script src="${pageContext.request.contextPath}/theme/scripts/layout.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js" type="text/javascript"></script>
</head>
<style type="text/css">
.tab-nav{
    height: 50px;
    background: #f8f8f8;
}
.tab-nav a {
    border: 0px solid;
    text-decoration: none;
    line-height: 45px;
    display: block;
    float: left;
    text-align: center;
    color: #000000;
    font-size: 16px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin-right: 30px;
    padding: 0px 5px 0px 5px;
}
.tab-nav a:hover{
	/* color: #ff8460; */
	animation: btocolor 1.5s;
    animation-fill-mode: forwards;
}
#m-content {
    padding-top: 10px;
    height: calc(100% - 63px);
}

#cont-ifr{
	width: 100%;
	height: 100%;
	border: none;
}

.a-choose{
    /* border-bottom: 2px solid #ff8460 !important; */
    /* border-top: 2px solid #ff8460 !important; */
   /*  color: #ff8460 !important;
    border-radius: 6px;
    box-shadow: 1px 3px 5px 1px #e4e3e3;
    border-radius: 22px; */
    animation: btocolor 0.8s;
    animation-fill-mode: forwards;
}

@keyframes btocolor{
	100%{color: #ff8460;
    border-radius: 6px;
    box-shadow: 1px 3px 5px 1px #e4e3e3;
    border-radius: 22px}
}
</style>
<body>
<div id="module_div">
<nav class="tab-nav">
	<a href="javascript:void(0)" data-url="/w/hospital_common/dictextend/dicthisdrug/list.html" class="iframe_a" title="药品属性维护">药品属性维护</a>
	<a href="javascript:void(0)" data-url="/w/hospital_common/dictextend/routename/list.html" class="iframe_a" title="给药途径类别维护">给药途径类别维护</a>
	<!-- <a href="javascript:void(0)" data-url="" class="iframe_a" title="手术类别维护">手术类别维护</a> -->
	<a href="javascript:void(0)" data-url="/w/hospital_common/dictextend/dept/list.html" class="iframe_a" title="科室类别维护">科室类别维护</a>
	<a href="javascript:void(0)" data-url="/w/hospital_common/dictextend/diagnosis/list.html" class="iframe_a" title="疾病类别维护">疾病类别维护</a>
	<a href="javascript:void(0)" data-url="/w/hospital_common/dictextend/freq/list.html" class="iframe_a" title="给药频次维护">给药频次维护</a>
	<!-- <a href="javascript:void(0)" data-url="" class="iframe_a" title="检验结果项目维护">检验结果项目维护</a>
	<a href="javascript:void(0)" data-url="" class="iframe_a" title="检验检查申请项目维护">检验检查申请项目维护</a> -->
	<a href="javascript:void(0)" data-url="/w/hospital_common/dictextend/user/list.html" class="iframe_a" title="医生级别维护">医生级别维护</a>
</nav>
<div id="m-content">
<iframe src="" id="cont-ifr" class="iframe_w" scrolling="no"></iframe>
<iframe src="" id="cont-ifr" class="iframe_w" scrolling="no"></iframe>
<!-- <iframe src="" id="cont-ifr" class="iframe_w" ></iframe> -->
<iframe src="" id="cont-ifr" class="iframe_w" scrolling="no"></iframe>
<iframe src="" id="cont-ifr" class="iframe_w" scrolling="no"></iframe>
<iframe src="" id="cont-ifr" class="iframe_w" scrolling="no"></iframe>
<!-- <iframe src="" id="cont-ifr" class="iframe_w" ></iframe>
<iframe src="" id="cont-ifr" class="iframe_w" scrolling="no"></iframe> -->
<iframe src="" id="cont-ifr" class="iframe_w" scrolling="no"></iframe>
</div>
</div>
</body>
 
</html>
<script type="text/javascript">
$(function(){
	$('.iframe_a').eq(0).click();
});
$('.iframe_a').click(function(){
	var index = $(this).index();
	var url = $(this).data('url');
	if($('.iframe_w').eq(index).attr('src') == ''){
		$('.iframe_w').eq(index).attr('src', url);
	}
	$('.iframe_w').eq(index).css('display','block').siblings().css('display','none');
	$(this).addClass('a-choose').siblings().removeClass('a-choose');
})

</script>