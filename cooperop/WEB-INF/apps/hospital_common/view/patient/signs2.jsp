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
	<link href="${pageContext.request.contextPath}/res/hospital_common/css/pat/signs.css" rel="stylesheet" type="text/css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/font-awesome/css/font-awesome.min.css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/simple-line-icons/simple-line-icons.min.css">
	<link href="/theme/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<script src="/theme/plugins/highcharts-4.1.9/js/highcharts.js" type="text/javascript"></script>
	<script src="/theme/plugins/highcharts-4.1.9/js/highcharts-more.js" type="text/javascript"></script>
	<script src="/theme/plugins/highcharts-4.1.9/js/modules/exporting.js" type="text/javascript"></script>
	<script src="/theme/plugins/highcharts-4.1.9/js/modules/drilldown.js" type="text/javascript"></script>
</head>
<body>
	<div id="wrapper">
		<c:choose>
			<c:when test="${not empty $return.signs}">
				<div class="tizheng span-content tz">
					<div class="left-tb" style="width: 20%; height: 455px; border:0px solid; float: left; overflow: auto;">
						<table class="tizheng_table mytable">
							<thead>
								<tr>
									<th style="font-size: 14px;">项目</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${$return.signs}" var="sig">
									<tr>
										<th style="height: 25px;" class="sig_item">${sig.vital_signs}</th>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="right-content" style="width: 80%; height: 455px; border:0px solid; float: left; overflow: auto;">
						
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<h1 style="text-align: center;line-height: 360px;font-size: 20px;font-weight: 200;color: #a77d2d;">没有查询到数据</h1>
			</c:otherwise>
		</c:choose>
	
		
	</div>
</body>
<script type="text/javascript">
	$(function(){
		$('.left-tb table .sig_item').click(function(){
			$('.sig_item').removeClass('dd');
			$(this).addClass('dd');
			var exam_no = $(this).text();
			//$('#container').css('height', window.innerHeight);
			$.call('hospital_common.patient.sig_report',{vital_signs:exam_no,
				patient_id: '${param.patient_id}',
				visit_id:'${param.visit_id}'},function(rtn){
					$(".right-content").empty();
					if(rtn && rtn.signs){
						var re = rtn.signs;
						try{
							var dddd = +re[0].vital_signs_values;
							$('.right-content').append('<div id="container" style="width:100%;height:100%"></div>')
							var x = [];
							var y = [];
							for(var i=0;i<re.length;i++){
								var string = re[i];
								x.push(string.recording_date+' '+string.time_point);
								y.push(+string.vital_signs_values);
							}
							var chart = Highcharts.chart('container', {
								title: {
									text: exam_no+'('+rtn.signs[0].units+')'
								},
								yAxis: {
									title: {
										text: rtn.signs[0].units
									}
								},
								xAxis: {
									categories: x
								},
								legend: {
									layout: 'vertical',
									align: 'right',
									verticalAlign: 'middle'
								},
								plotOptions: {
									line: {
										dataLabels: {
											// 开启数据标签
											enabled: false          
										},
										// 关闭鼠标跟踪，对应的提示框、点击事件会失效
										enableMouseTracking: true
									}
								},
								series: [{
									name: '体征变化',
									data: y
								}]
							});
						}catch(e){
							var h = '';
							h += '<table id="tzdetail" class="mytable"><thead>';
							h += '<tr><th width="30px">序</th><th>记录日期</th>';
							h += '<th>时间点</th><th>项目</th><th>项目值</th>';
							h += '<th>单位</th></tr></thead></table>';
							$('.right-content').append(h);
							$.call("hospital_common.showturns.queryvitaldetail",{"exam_no":exam_no,"visit_id":'${param.visit_id}',"patient_id":'${param.patient_id}'},function(rtn2){
								if(rtn2!=null&&typeof(rtn2)!= "undefined"){
									 $("#tzdetail").empty();
					                 $("#tzdetail").append("<thead><tr><th  style='width: 30px;'>序</th><th style='width: 130px;'>记录时间</th><th>项目</th><th style='width: 100px;'>项目值</th><th>单位</th></tr></thead><tbody>");
									 $.each(rtn2.vitaldetail,function(i,v){
									     $("#tzdetail").append("<tr><td>"+(i+1)+"</td><td>"+v.recording_date+" "+v.time_point+"</td><td>"+v.vital_signs+"</td><td style='text-align: center;'>"+(v.vital_signs_values?v.vital_signs_values:'')+"</td><td>"+(v.units==null?"":v.units)+"</td></tr>");
									});
									 $("#tzdetail").append("</tbody>");
								}else{
									 $("#tzdetail").append("未查询到信息！");
								}
							},function(e){}, {async: false, remark: false})
						}
						
					}
				},function(e){}, {async: false, remark: false})
		});
		
		//点击第一个
		$('.sig_item').eq(0).click();
	})
	
	/* function get_report(vital_signs,vital_signs_name,unit){
		$.modal("/w/hospital_common/patient/signs_report.html", "体征图表", {
			height: "70%",
			width: "70%",
			vital_signs: vital_signs,
			vital_signs_name: vital_signs_name,
			unit:unit,
			patient_id:'${param.patient_id}',
			visit_id:'${param.visit_id}',
			maxmin: true,
			callback : function(rtn) {
		    }
		}); 
	} */
</script>