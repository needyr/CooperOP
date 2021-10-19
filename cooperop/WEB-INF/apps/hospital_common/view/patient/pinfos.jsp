<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="X-UA-Compatible" content="chrome=1">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/plugins/simple-line-icons/simple-line-icons.min.css">

<script
	src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/theme/plugins/jquery.cokie.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.json.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/metronic.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js"
	type="text/javascript"></script>
<style type="text/css">
* {
	margin: 0px auto;
	padding: 0px;
	font-family: 'Microsoft Yahei';
}

#all_div {
	/* width: 924px;
	height: 652px; */
	border: 1px solid #ded9d9;
	padding: 3px;
}

#module_div {
	/* margin-top: 10px; */
	position: relative;
}

.tab-nav {
	border-bottom: 2px solid #3e6ca526;
	height: 40px;
}

.tab-nav a {
	font-size: 16px;
	border: 0px solid;
	text-decoration: none;
	line-height: 40px;
	min-width: 100px;
	display: block;
	float: left;
	text-align: center;
	color: #151515;
}

.tab-nav a:hover {
	color: #6992c3;
}

#m-content {
	height: 520px;
	
}

#cont-ifr {
	width: 100%;
	height: 100%;
	border: none;
}

.a-choose {
	border-bottom: 2px solid #3e6ca5 !important;
    color: #3e6ca5 !important;
    text-shadow: 2px 2px 2px #c3c3c3;
}

#be_sure {
	position: absolute;
    bottom: 10px;
    right: 10px;
    width: 97px;
    height: 35px;
    font-size: 16px;
    background: #55a6cc;
    border: 0px;
    border-radius: 2px;
    color: #f1edee;
    cursor: pointer;
}

#be_sure:hover {
	background-color: #5992ad;
    box-shadow: 0px 0px 3px 1px #cccccc;
}

</style>
</head>
<body>
	<div id="all_div">
		<div id="module_div">
			<nav class="tab-nav">
				<a href="javascript:void(0)" onclick="chooseMo(this);" 
					data-url="/w/hospital_imic_drgs/drgs/dgbill.html" class="a-choose">病例信息</a>
				<a href="javascript:void(0)" onclick="chooseMo(this);"
					data-url="/w/hospital_mqc/earlywarn/mqc.html">质控预警</a>
				<!-- <a href="javascript:void(0)" onclick="chooseMo(this);"
					data-url="http://www.taobao.com">病组分析</a> -->
			</nav>
			<div id="m-content">
				<iframe src="" id="cont-ifr"></iframe>
			</div>
		</div>
		<div id="foot">
			<input id="be_sure" type="button" onclick="closeWin();" value="我已知晓" />
		</div>		
	</div>
</body>
<script type="text/javascript">
	$(function(){
		//alert('${param.patient_id}, ${param.visit_id}');
		triNav();
		$('.tab-nav a').eq(0).click();
	});
	
	function triNav(){
		/* $('.tab-nav a').on('click', function(){
			$(this).addClass('a-choose').siblings().removeClass('a-choose');
			$('#cont-ifr').prop('src', $(this).data('url'));
		}); */
	}
	
	function closeWin(){
		if("undefined" != typeof crtech) {
			 window.close();
		 }else{
			 var furl = '';
			 if (parent !== window) { 
		        try {
		        	furl = parent.location.href; 
		        }catch (e) { 
		        	furl = document.referrer; 
		        } 
		     }
			//window.parent.postMessage({ffun: 'cclose', close: 1}, furl);
			var ret2par = {ffun: 'cback', close: 1};
			window.parent.postMessage(JSON.stringify(ret2par), '*');
		 }
	}

	function chooseMo(_this){
		$(_this).addClass('a-choose').siblings().removeClass('a-choose');
		var url = $(_this).data('url');
		var id = "${param.id}".replace('drgs_', '');
		url += '?id=' + id + '&&patient_id=${param.patient_id}&&visit_id=${param.visit_id}';
		url += '&&doctor_no=${param.doctor_no}&&dept_code=${param.dept_code}';
		url += '&&months=${param.months}&&years=${param.years}';
		url += '&&patient_name=${param.patient_name}';
		$('#cont-ifr').prop('src', url);
	}
</script>
</html>