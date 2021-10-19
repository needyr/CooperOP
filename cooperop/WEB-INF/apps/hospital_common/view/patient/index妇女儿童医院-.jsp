<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<head>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery.cokie.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery.json.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/scripts/common.js" type="text/javascript"></script>
	<link href="${pageContext.request.contextPath}/res/hospital_common/css/pat/index.css" rel="stylesheet" type="text/css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/font-awesome/css/font-awesome.min.css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/simple-line-icons/simple-line-icons.min.css">
</head>
<body>
<div class="wk">
	<div class="div-list">
		<p class="dyq">
			<span data-sort="sy" class="active" data-page="baseinfo.html">基本信息</span>
			<span data-sort="yz" data-page="order2.html">医嘱</span>
			<c:if test="${not empty $return.ipc}">
				<span data-sort="tzdp" data-page="ipc_result.html">用药审查</span>
			</c:if>
			<c:if test="${not empty $return.hospital_imic}">
				<span data-sort="tzdp" data-page="imic_result.html">医保审查</span>
			</c:if>
			<span data-sort="zd" data-page="diagnosis.html">诊断</span>
			<span data-sort="jc" data-page="check.html">检查</span>
			<span data-sort="jy" data-page="inspection.html">检验</span>
			<span data-sort="tz" data-page="signs2.html">体征</span>
			<span data-sort="oper" data-page="oper2.html">手术</span>
			
			<c:if test="${not empty $return.hospital_imic}">
				<span data-sort="items" data-page="/w/hospital_imic/simic/bill.html">费用</span>
			</c:if>
			
		 </p>
	</div>
      
	<div class="item_show">
		<iframe id="conifr" src="">
		
		</iframe>
	</div>
</div>
</body>
<script type="text/javascript">
var userid_second;
var bill_log_id;
$(document).ready(function(){
	
	$('#conifr').prop('src', 'baseinfo.html?patient_id=${param.patient_id}&visit_id=${param.visit_id}');
	
	$(".dyq span").click(function (){
		if($(this).data('sort') == 'items' && $(".dyq span.active").data('sort') != 'items'){
			var _this = $(this);
			//访问费用界面, 验证权限, 以及插入时间
			/*$.modal("/w/hospital_common/patient/bill_user_check.html","检验第二用户登录",{
				width:"400px",
				height:"280px",
				callback : function(check){
					if(check && check.check){*/
						var check ={};
						check.type = 1;
						check.patient_id = '${param.patient_id}';
						check.visit_id = '${param.visit_id}';
						//check.user_sub = JSON.stringify(check.user_sub);
						//userid_second = check.user_sub;
						$.call('hospital_common.patientdata.bill.visit_insert',check,function(r){
							bill_log_id = r;
							$(".dyq span").removeClass("active");
							_this.addClass("active");
							var page_u = _this.data('page');
							page_u = page_u + '?patient_id=${param.patient_id}&visit_id=${param.visit_id}';
							$('#conifr').prop('src', page_u);
						})
					/*}
				}
			});*/
		}else if($(this).data('sort') != 'items'){
			if($(".dyq span.active").data('sort') == 'items'){
				//执行退出费用, 存储具体访问人信息
				var _this = $(this);
				var check = {};
				userid_second = '';
				check.id = bill_log_id;
				bill_log_id = '';
				$.call('hospital_common.patientdata.bill.update_log',check,function(r){
					$(".dyq span").removeClass("active");
					_this.addClass("active");
					var page_u = _this.data('page');
					page_u = page_u + '?patient_id=${param.patient_id}&visit_id=${param.visit_id}';
					$('#conifr').prop('src', page_u);
				})
			}else{
				$(".dyq span").removeClass("active");
				$(this).addClass("active");
				var page_u = $(this).data('page');
				page_u = page_u + '?patient_id=${param.patient_id}&visit_id=${param.visit_id}';
				$('#conifr').prop('src', page_u);
			}
		}
	});
	
	//关闭
	window.onunload = function (){
		if(bill_log_id){
			var check = {};
			var check = {};
			userid_second = '';
			check.id = bill_log_id;
			bill_log_id = '';
			$.ajax({
				type: "post",
				url: "/w/hospital_common/patientdata/bill/update_log.json",
				contentType: "application/x-www-form-urlencoded; charset-UTF-8",
				data: check,
				complete: function(XMLHttpRequest, status){
					
				},
				error: function(XMLHttpRequest, textStatus, errorThrow){
					
				}	
			})
		}
 	}
	
	//刷新
	window.onbeforeunload = function (){ 
		if(bill_log_id){
			var check = {};
			var check = {};
			userid_second = '';
			check.id = bill_log_id;
			bill_log_id = '';
			$.ajax({
				type: "post",
				url: "/w/hospital_common/patientdata/bill/update_log.json",
				contentType: "application/x-www-form-urlencoded; charset-UTF-8",
				data: check,
				complete: function(XMLHttpRequest, status){
					
				},
				error: function(XMLHttpRequest, textStatus, errorThrow){
					
				}	
			})
		}
	}

	
})

</script>