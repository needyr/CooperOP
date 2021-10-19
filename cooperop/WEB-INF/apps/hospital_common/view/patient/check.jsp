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
	<link href="${pageContext.request.contextPath}/res/hospital_common/css/pat/check.css" rel="stylesheet" type="text/css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/font-awesome/css/font-awesome.min.css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/simple-line-icons/simple-line-icons.min.css">
</head>

<style type="text/css">
	.right-content{
		width: 49%;
		height: 455px; 
		float: left; 
		overflow: auto;
		border-left: 4px solid #c1d8f1;
	}
	.item-d{
		padding: 5px;
	}
	.des-t{
		line-height: 25px;
	    font-size: 14px;
	    font-weight: 600;
	}
	.des-c{
		font-weight: 200;
	}
	.c-title{
		text-align: center;
	    font-size: 14px;
	    line-height: 20px;
	    border-bottom: 2px solid #4e5f6d8a;
		
	}
</style>
<body>
	<div id="wrapper">
		<c:choose>
			<c:when test="${not empty $return.exam}">
				<div class="dleft" style="width: 50%; height: 455px; border:0px solid; float: left; overflow: auto;">
		            <div class="left-tb">
		                <table class="jiancha_table mytable">
		                	<thead class="bhead">
								<tr id="thead_jc">
									<th style="width: 20px;">序</th>
									<th style="width: 120px;">检查编号</th>
									<th style="width: 100px;">检查类别</th>
									<th style="width: 200px;">检查子类</th>
									<th style="width: 80px;">申请医生</th>
									<th style="width: 150px;">申请时间</th>
									<th style="width: 150px;">检查时间</th>
									<th style="width: 80px;">报告者</th>
									<th style="width: 150px;">报告时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${$return.exam}" var="exm" varStatus="k">
			                		<tr class="data_tr" data-id="${exm.exam_no}">
										<td style="width: 20px;">${k.index + 1}</td>
										<td style="width: 100px;">${exm.exam_no}</td>
										<td style="width: 100px;">${exm.exam_class}</td>
										<td style="width: 200px;">
										<a onClick="dc(this);" style="color: #2196F3;"
											data-exam_class="${exm.exam_class}"
											data-exam_sub_class="${exm.exam_sub_class}"
											data-req_physician="${exm.req_physician}"
											data-report_date_time="${exm.report_date_time}"
											data-exam_date_time="${exm.exam_date_time}"
											data-req_dept_name="${exm.req_dept_name}"
											data-exam_no="${exm.exam_no}"
										>
										${exm.exam_sub_class}
										</a>
										</td>
										<td style="width: 80px;">${exm.req_physician}</td>
										<td style="width: 150px;">${exm.req_date_time}</td>
										<td style="width: 150px;">${exm.exam_date_time}</td>
										<td style="width: 80px;">${exm.reporter}</td>
										<td style="width: 150px;">${exm.report_date_time}</td>
									</tr>
			                	</c:forEach>
							</tbody>
		                </table>
		        	</div>
	        </div>
	        <div class="right-content">
		        <!-- <table id="jianchad" class="mytable">
		        	
		        </table> -->
		        <!-- <div class="item-d">
		        	<h2 class="c-title">检查是的粉丝飞机的设计覅设计的覅多少积分</h2>
		        	<p class="des-t">阴阳性：<span class="des-c">影星</span></p>
		        	<p class="des-t">检查所见：<span class="des-c">撒顶顶顶顶顶顶顶顶顶顶</span></p>
		        	<p class="des-t">印象：<span class="des-c">撒顶顶顶顶顶顶顶顶顶顶顶</span></p>
		        	<p class="des-t">建议：<span class="des-c">撒顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶</span></p>
		        </div> -->
		        
		        
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
			$(".left-tb table .data_tr").click(function(){
				$(this).css("background-color","#a5d8af").siblings().css("background-color","");
				var exam_no=$(this).attr("data-id");
				$.call("hospital_common.patient.queryExamDtl",{"exam_no":exam_no},function(rtn){
					//console.log(rtn);
					$(".right-content").empty();
					if(rtn!=null&&typeof(rtn)!= "undefined"){
						var html = [];
						$.each(rtn.jianc,function(i,v){
                            html.push('<div class="item-d">');
                            html.push(' <h2 class="c-title">'+ (v.exam_item == null ? "":v.exam_item) +'</h2>');
                            if(v.is_abnormal){
                                html.push('<p class="des-t">阴阳性：<span class="des-c">'+ v.is_abnormal +'</span></p>');
                            }
                            if(v.description){
                                if(v.use_image && v.use_image.indexOf('http')>=0){
                                    html.push('<p class="des-t">检查所见：<span class="des-c"><a onclick="toxd(\''+v.use_image+'\')">'+v.description+'</a></span></p>');
                                }else{
                                    html.push('<p class="des-t">检查所见：<span class="des-c">'+v.description+'</span></p>');
                                }
                            }
                            if(v.impression){
                                html.push('<p class="des-t">印象：<span class="des-c">'+v.impression+'</span></p>');
                            }
                            if(v.recommendation){
                                html.push('<p class="des-t">建议：<span class="des-c">'+v.recommendation+'</span></p>');
                            }
                            html.push('</div>');
						});
						$(".right-content").append(html.join(''));
					}else{
						$(".right-content").append("未查询到信息！");
					}
					
				},function(e){}, {async: false, remark: false   })
			}); 
			$(".left-tb table .data_tr").eq(0).click();
		});
		
function dc(_this){
	var exam_class = $(_this).attr("data-exam_class");
	var exam_sub_class = $(_this).attr("data-exam_sub_class");
	var req_physician = $(_this).attr("data-req_physician");
	var report_date_time = $(_this).attr("data-report_date_time");
	var exam_date_time = $(_this).attr("data-exam_date_time");
	var req_dept_name = $(_this).attr("data-req_dept_name");
	var exam_no = $(_this).attr("data-exam_no");
	$.modal("/w/hospital_common/patient/check_export.html", "导出检查", {
		height: "600px",
		width: "850px",
		exam_no:exam_no,
		req_dept_name:req_dept_name,
		exam_date_time:exam_date_time,
		report_date_time:report_date_time,
		req_physician:req_physician,
		exam_sub_class:exam_sub_class,
		exam_class:exam_class,
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