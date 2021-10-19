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
	<link href="${pageContext.request.contextPath}/res/hospital_common/css/pat/inspection.css" rel="stylesheet" type="text/css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/font-awesome/css/font-awesome.min.css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/simple-line-icons/simple-line-icons.min.css">
	<link href="/theme/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="wrapper">
		<c:choose>
			<c:when test="${not empty $return.inspection}">
				<div class="dleft" style="width: 50%; height: 455px; border:0px solid; float: left; overflow: auto;">
		            <div class="left-tb">
		                <table class="jianyan_table mytable">
		                	<thead>
		                		<tr>
				                	<th style="width: 30px;">序</th>
									<th style="width: 60px;">标本</th>
									<th style="width: 300px;">检验项目</th>
									<th style="width: 60px;">申请医生</th>
									<th style="width: 150px;">申请时间</th>
									<th style="width: 150px;">报告时间</th>
		                		</tr>
		                	</thead>
		                	<tbody>
		                		<c:forEach items="${$return.inspection}" var="ins" varStatus="k">
			                		<tr class="data_tr" data-id="${ins.test_no}">
										<td>${k.index + 1}</td>
										<td>${ins.specimen}</td>
										<td><a onClick="dc(this);" data-id="${ins.test_no}" 
					                		data-specimen="${ins.specimen}"
					                		data-ordering_provider="${ins.ordering_provider}"
					                		data-requested_date_time="${ins.requested_date_time}"
					                		data-results_rpt_date_time="${ins.results_rpt_date_time}"
					                		data-transcriptionist="${ins.transcriptionist}"
					                		data-verified_by="${ins.verified_by}">${ins.item_name}</a></td>
										<td>${ins.ordering_provider}</td>
										<td>${ins.requested_date_time}</td>
										<td>${ins.results_rpt_date_time}</td>
									</tr>
		                		</c:forEach>
		                	</tbody>
		                </table>
		            </div>
		        </div>
		        
		      	<div class="right-content" style="width: 49%; height: 455px;border:0px solid; float: left; overflow: auto">
			        <div class="right-content">
		                <table id="jydetail" class="mytable">
		                	
		                </table>
		            </div>
		       </div>
			</c:when>
			<c:otherwise>
				<h1 style="text-align: center;line-height: 360px;font-size: 20px;font-weight: 200;color: #a77d2d;">没有查询到数据</h1>
			</c:otherwise>
		</c:choose>
	
        
	</div>
</body>
<script src="/theme/plugins/html2canvas.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		$('.data_tr').click(function(){
			$(this).css("background-color","#a5d8af").siblings().css("background-color","");
			var test_no = $(this).attr("data-id");
			 $.call("hospital_common.showturns.queryrequestendetail",{"test_no":test_no},function(rtn){
				//console.log(rtn);
				  if(rtn!=null&&typeof(rtn)!= "undefined"){
				     $("#jydetail").empty();
	                 $("#jydetail").append("<thead><tr><th style='width: 20px;'>警</th><th style=''>项目名称</th><th style='width: 50px;'>检验结果</th><th style='width: 60px;'>单位</th><th style='width: 80px;'>参考范围</th><th style='width: 30px;'>结果</th></tr></thead>");
					 $.each(rtn.requestendetail,function(i,v){
						 var trbf="<tr><td style='color:red;width: 20px;'>";
						 if(v.tag=='↑'){
							 trbf=trbf+'<i class="fa fa-long-arrow-up fali"></i>';
						 }else if(v.tag=='↓'){
							 trbf=trbf+'<i class="fa fa-long-arrow-down fali"></i>';
						 }else{
							 trbf=trbf+(v.tag == null? "": v.tag);
						 }
						 var traf = "";
						 if(v.jc_num && v.jc_num > 1){
						 	traf="</td><td style=''><a onclick='get_report("+'"'+v.report_item_code+'"'+',"'+v.report_item_name+'"'+',"'+(v.units==null?"":v.units)+'"'+");'>"+v.report_item_name+'<i class="glyphicon glyphicon-stats"></i></a>'+"</td><td style='width: 50px;'>"+(v.result?v.result:'')+"</td><td style='width: 60px;'>"+(v.units==null?"":v.units)+"</td><td style='width: 80px;'>"+(v.print_context==null?"":v.print_context)+"</td><td style='width:  30px;'>"+(v.abnormal_indicator?v.abnormal_indicator:'')+"</td></tr>";
						 }else{
						 	traf="</td><td style=''>"+v.report_item_name+"</td><td style='width: 50px;'>"+(v.result?v.result:'')+"</td><td style='width: 60px;'>"+(v.units==null?"":v.units)+"</td><td style='width: 80px;'>"+(v.print_context==null?"":v.print_context)+"</td><td style='width:  30px;'>"+(v.abnormal_indicator?v.abnormal_indicator:'')+"</td></tr>";
						 }
					 	 var trend=trbf+traf
					 /*  $("#jydetail").append("<tr><td style='color:red;'><b>"+v.tag+"</b></td><td>"+v.report_item_name+"</td><td>"+v.result+"</td><td>"+v.units+"</td><td>"+(v.print_context==null?"":v.print_context)+"</td><td>"+v.abnormal_indicator+"</td></tr>"); */
					 $("#jydetail").append(trend);
					 }); 
					
				}else{
					 $("#jydetail").append("未查询到信息！");
				}  
				
			},function(e){}, {async: false, remark: false   })
		});
		$('.data_tr').eq(0).click();
	})
	function get_report(report_item_code,report_item_name,unit){
		$.modal("/w/hospital_common/patient/inspection_report.html", "检验图表", {
			height: "70%",
			width: "70%",
			report_item_code: report_item_code,
			report_item_name: report_item_name,
			unit:unit,
			patient_id:'${param.patient_id}',
			visit_id:'${param.visit_id}',
			maxmin: true,
			callback : function(rtn) {
		    }
		}); 
	}
	
	function dc(_this){
		var test_no = $(_this).attr("data-id");
		var specimen = $(_this).attr("data-specimen");
		var ordering_provider = $(_this).attr("data-ordering_provider");
		var requested_date_time = $(_this).attr("data-requested_date_time");
		var results_rpt_date_time = $(_this).attr("data-results_rpt_date_time");
		var transcriptionist = $(_this).attr("data-transcriptionist");
		var verified_by = $(_this).attr("data-verified_by");
		$.modal("/w/hospital_common/patient/inspection_export.html", "导出检验", {
			height: "600px",
			width: "850px",
			test_no: test_no,
			specimen: specimen,
			ordering_provider:ordering_provider,
			requested_date_time:requested_date_time,
			results_rpt_date_time:results_rpt_date_time,
			transcriptionist:transcriptionist,
			verified_by:verified_by,
			patient_id:'${param.patient_id}',
			visit_id:'${param.visit_id}',
			callback : function(rtn) {
		    }
		}); 
		/*window.location.href="/jytoword?patient_id="+"${param.patient_id}"+"&visit_id="+"${param.visit_id}"
		+"&test_no="+test_no
		+"&specimen="+specimen
		+"&ordering_provider="+ordering_provider
		+"&requested_date_time="+requested_date_time
		+"&results_rpt_date_time="+results_rpt_date_time;*/
	}
</script>