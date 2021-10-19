<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	pageContext.setAttribute("imic_isopen_bingzadmin", SystemConfig.getSystemConfigValue("hospital_imic", "imic_isopen_bingzadmin", "0"));
%>
<!DOCTYPE html>
<head>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery.cokie.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery.json.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/scripts/common.js" type="text/javascript"></script>
	<link href="${pageContext.request.contextPath}/res/hospital_common/css/pat/baseinfo.css" rel="stylesheet" type="text/css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/font-awesome/css/font-awesome.min.css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/simple-line-icons/simple-line-icons.min.css">
</head>
<body>
	<div id="wrapper">
		<div class="otherinfo span-content sy">
			<div class="zdp" style="border-bottom: 2px solid #d8d0d0; width: 100%">
				<b>${$return.patient.patient_name}</b>　
				<b>${$return.patient.sex}</b>　
				<b>${$return.patient.age}</b>　
				<c:if test="${not empty $return.patient.height}">
                	<b>身高<fmt:formatNumber value="${$return.patient.height}" pattern="#.0" minFractionDigits='0' />cm</b>
                </c:if>
                 <c:if test="${not empty $return.patient.weight}">
                	<b>体重<fmt:formatNumber value="${$return.patient.weight}" pattern="#.0" minFractionDigits='0' />kg</b>
                </c:if>
				<c:if test="${$return.patient.d_type eq 1}">
					<b>第${$return.patient.visit_id}次住院</b>　
					<b>住院${$return.patient.ts}</b>
				</c:if>
				<c:if test="${$return.patient.d_type ne 1}">
					<b>就诊序号：${$return.patient.visit_id}</b>　
				</c:if>
				<c:if test="${$return.imic eq 1}">
					<b id="bzs"></b>
				</c:if>
				<a href="javascript:void(0);" onclick="caseHistory();" style="float: right;">电子病历</a>
			</div><br>
			<c:if test="${$return.patient.d_type eq 1}">
			<span class="infod">住院号：${$return.patient.patient_no}</span>
			</c:if>
			<c:if test="${$return.patient.d_type ne 1}">
			<span class="infod">门诊号：${$return.patient.patient_no}</span>
			</c:if>
			<span class="infod">患者ID：${$return.patient.patient_id}</span>
            <span class="infod">肌酐清除率：${empty $return.patient.ccr? "无":$return.patient.ccr} ${empty $return.patient.ccr? "":" ml/min"}</span>
			<span class="infod">医保体系：${$return.yb.interface_type_name}</span>
            <span class="infod">就诊类别：${$return.yb.claim_type_name}</span>
            <span class="infod">医保号：${$return.patient.insurance_no}</span>
            <span class="infod">住院原因：${$return.patient.admission_cause}</span>
            <span class="infod">入院方式：${$return.patient.patient_class_name}</span>
			<span class="infod">入院病情：${$return.patient.pat_adm_condition_name}</span>
            <span class="infod">入院时间：${$return.patient.admission_datetime}</span>
            <span class="infod">入院科室：${$return.patient.dept_in_name}</span>
            <span class="infod">床位：${$return.patient.bed_no}</span>
            <span class="infod">出院时间：${$return.patient.discharge_datetime}</span>
            <span class="infod">出院科室：${$return.patient.dept_discharge_name}</span>
            <span class="infod">出院方式：${$return.patient.discharge_disposition}</span>
            <span class="infod">主治医生：${$return.patient.attending_doctor}</span>
            <span class="infod">经治医生：${$return.patient.doctor_in_charge}</span>
            <span class="infod">科室主任：${$return.patient.director}</span> 
            <div class="zdp">主要诊断：
	            <c:if test="${not empty $return.diagnosGroup}">
		            <c:forEach items="${$return.diagnosGroup}" var = "diagno">
		            	${diagno.diagnosis_desc}、
		            </c:forEach>
	            </c:if>
            </div>
             <div class="zdp">
	         	过敏药物：${$return.patient.alergy_drugs}
            </div>
            <div class="zdp">
	         	不良反应药物：${$return.patient.adverse_reaction_drugs}
            </div>
    	</div>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	$(".dyq span").click(function (){
			$(".wk .div-list .dyq span").removeClass("active");
			$(this).addClass("active");
			$(".div-list").find(".span-content").hide();
			$(".div-list").find("."+$(this).attr("data-sort")).show();
	});
	
	if('${$return.imic}' == '1' && '${imic_isopen_bingzadmin}' == '1'){
		var req = {};
		req.patient_id = '${$return.patient.patient_id}';
		req.visit_id = '${$return.patient.visit_id}';
		$.call('hospital_imic.singledis.getManageMode', req, function(rtn){
			if(rtn && rtn.managemode == '1'){
				$('#bzs').append('单病种管理(<a href="javascript:void(0);" onclick="bingzinfo(\''+ rtn.bingz_id +'\');">'+ rtn.bingz_name +'</a>)');
			}
		});
	}
})

//电子病历
function caseHistory(){
	/* var path = 'C:\\APPSOFT\\zlSoftCISInterface.exe orcl:zlhis:aqa:1:0:#patient_id:#visit_id';
	path = path.replace('#patient_id', '${param.patient_id}');
	path = path.replace('#visit_id', '${param.visit_id}');
	//path = "notepad";
	try{
		crtech.exec(path);
	}catch(err){
		$.message('请使用IADSCP客户端访问！');
	} */
	//电子病历成都妇女儿童医院
	var path = 'http://10.180.7.208:8080/Home/DoqLeiView?id=${$return.patient.patient_no},${$return.patient.visit_id}';
	try{
		$.modal(path,"查看电子病历",{
		width:"95%",
		height:"95%",
		callback : function(e){}
	});
	}catch(err){
		$.message('请使用IADSCP客户端访问！');
	}
}

function bingzinfo(bingz_id){
	$.modal('/w/hospital_imic/singledis/bzinfo.html',"查看单病种详情",{
	  	width: '550px',
	  	height: '500px',
	  	id: bingz_id,
	    callback : function(e){}
	});
}

</script>