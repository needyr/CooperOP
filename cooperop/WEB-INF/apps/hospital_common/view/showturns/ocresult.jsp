<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="医嘱审查不通过原因查看" disloggedin="true">
	<!DOCTYPE HTML>
	<html>
	<head>
		<meta charset="UTF-8">
		<title>医嘱审查不通过原因查看</title>
		<style type="text/css">
			*{
				margin: 0px auto;
				padding: 0px;
				font-family: '微软雅黑';
				font-size: 12px;
			}
			.main{
				padding: 0px ;
			}
			.dataTables_scrollBody{
				height: unset !important;
				max-height: 400px !important;
			}
			.page-content{
				padding: 0px !important;
			}
			#main{
				width: 1150px;
				height: 550px;
				padding: 5px;
				background: white;
				border: 1px solid #eae8e8;
			}
			#pat-div p{
				font-size: 16px;
				margin: 0px !important;
				padding-left: 15px;
			}

			#middle{
				height: 410px;
			}


			.portlet {margin: 0px !important;}
			.portlet > .portlet-title > .tools {float: left !important;}

			#btn_group{
				float: right;
				margin-right: 10px;
			}
			.inp_btn{
				width: 80px;
				height: 33px;
				background: #5992ad;
				border: 0px;
				border-radius: 2px;
				color: #f1edee;
				cursor: pointer;
				border:none;
				outline:medium;
			}
			.inp_btn:hover{
				box-shadow: 0px 0px 3px 1px #c5c5c5;
			}
			a{
				color: #186aaf !important;
				text-decoration: underline !important;
			}
		</style>
	</head>
	<body>

	<div id="main">
		<div id="top">
			<div id="pat-div">
				<p>${pat.patient_name}　
						${pat.sex}　
						${pat.age}　
					<c:if test="${not empty pat.height}">
						身高<fmt:formatNumber value="${pat.height}" pattern="#.0" minFractionDigits='0' />cm　
					</c:if>
					<c:if test="${not empty pat.weight}">
						体重<fmt:formatNumber value="${pat.weight}" pattern="#.0" minFractionDigits='0' />kg
					</c:if>　
					<a href="javascript:void(0);" onclick="patInfos();" style="font-size: 16px;">患者详情</a>　<!-- <a>帮助</a> -->
				</p>


				<p>
					<!-- 拼接入院诊断 -->
					<c:set var="dia_in" scope="session" value="" />
					<c:forEach items="${diagnosis}" var="diag" >
						<c:if test="${diag.diagnosis_type eq 1}">
							<c:choose>
								<c:when test="${empty dia_in}">
									<c:set var="dia_in" value="${dia_in.concat(diag.diagnosis_desc)}" />
								</c:when>
								<c:otherwise>
									<c:set var="dia_in" value="${dia_in.concat('、').concat(diag.diagnosis_desc)}" />
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
					诊　　断：<span  title="${dia_in}">${dia_in}</span></p>

				<!-- 拼接出院诊断 -->
				<c:set var="dia_out" scope="session" value="" />
				<c:forEach items="${diagnosis}" var="diag" >
					<c:if test="${diag.diagnosis_type eq 2}">
						<c:choose>
							<c:when test="${empty dia_out}">
								<c:set var="dia_out" value="${dia_out.concat(diag.diagnosis_desc)}" />
							</c:when>
							<c:otherwise>
								<c:set var="dia_out" value="${dia_out.concat('、').concat(diag.diagnosis_desc)}" />
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
				<p>出院诊断：<span >${dia_out}</span>
				</p>
				<p>手　　术：<c:forEach items="${opers}" var="oper">${oper.operation} ;</c:forEach></p>
			</div>
		</div>
		<div id="middle">
			<s:row>
				<s:table autoload="false" action="ipc.auditresult.getQMsg" id="data-table" sort="true" limit="0">
					<s:toolbar>
						<font style="color: #b97a39;font-size: 16px; font-weight: 500;">检查到以下医嘱[合理用药审核]未通过，不能进行后续流程，请及时跟进</font>
					</s:toolbar>
					<s:table.fields>
						<s:table.field name="tag" label="组" datatype="string" width="20px"></s:table.field>
						<s:table.field name="repeat_indicator" label="长/临" datatype="script" width="30px">
							var repeat_indicator = record.repeat_indicator;
							if(repeat_indicator){
							if(repeat_indicator == 1){
							return '长';
							}else if(repeat_indicator == 0){
							return '临';
							}
							}else{
							return '';
							}
						</s:table.field>
						<s:table.field name="order_text" label="医嘱内容" datatype="string" width="170px"></s:table.field>
						<s:table.field name="dosage" label="剂量" datatype="script" width="80px">
							return (parseFloat(record.dosage) + record.dosage_units);
						</s:table.field>
						<s:table.field name="administration" label="给药途径" datatype="string" width="80px"></s:table.field>
						<s:table.field name="frequency" label="频次" datatype="string" width="80px"></s:table.field>
						<s:table.field name="start_date_time" label="下达时间" datatype="string" width="130px"></s:table.field>
						<s:table.field name="doctor" label="开嘱医生" datatype="string" width="60px"></s:table.field>
						<s:table.field name="order_status" label="医嘱状态" datatype="script" width="40px">
							if(record.order_status==0){
							return "新开、校对";
							}
							else if(record.order_status==1){
							return "执行";
							}
							else if(record.order_status==2){
							return "停止";
							}else {
							return "作废";
							}
						</s:table.field>
						<s:table.field name="msg" label="不通过原因提示" datatype="string" width="200px"></s:table.field>
						<s:table.field name="caozuo" label="操作" datatype="template" width="40px">
							<a href="javascript:void(0);" onclick="Qdetail('$[last_auto_audit_id]', '$[last_check_result_info_id]', '')">详情</a>
						</s:table.field>
						<!-- <a href="javascript:void(0);" onclick="niticeDoc();">提醒医生</a> -->
					</s:table.fields>
				</s:table>
			</s:row>
		</div>
		<div id="foot">
			<div id="btn_group">
				<input class="inp_btn" type="button" style="width: 90px" onclick="sureClose();" value="我已知晓" />
			</div>
		</div>
	</div>
	</body>
	<script>
		$(function(){
			query();
		});

		function query(){
			$("#data-table").params({search_id: '${id}'});
			$("#data-table").refresh();
		}

		function patInfos(){
			layer.open({
				type: 2,
				title: "医策智能辅助决策支持  - 患者详情",
				/* area: ['847px', '550px'],  */
				area: ['95%', '550px'],
				content: "/w/hospital_common/patient/index.html?patient_id=${pat.patient_id}&&visit_id=${pat.visit_id}&&doctor_no=${pat.doctor_no}"
			});
		}

		function niticeDoc(){
			$.message('当前请自行通知医生！');
		}

		function Qdetail(auto_audit_id ,check_result_info_id, sort_id){
			layer.open({
				type: 2,
				title: "医策智能辅助决策支持 - 用药审查结果详情",
				area: ['910px', '540px'],
				content: "/w/ipc/auditresult/particulars.html?auto_audit_id="+auto_audit_id+"&&sort_id="+sort_id+"&&check_result_info_id="+check_result_info_id+"#topsta"
			});
		}

		function sureClose(){
			if(typeof crtech != "undefined"){
				window.close();
			}else{
				// 跨域传值
				var furl = '';
				if (parent !== window) {
					try {
						furl = parent.location.href;
					}catch (e) {
						furl = document.referrer;
					}
				}
				window.parent.postMessage({ffun: 'cclose', close: 1}, furl);
			}
		}
	</script>
	</html>
</s:page>