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
			<c:when test="${not empty $return.operation}">
				<div class="dleft" style="width: 50%; height: 455px; border:0px solid; float: left; overflow: auto;">
		            <div class="left-tb">
		                <table class="jiancha_table mytable">
		                	<thead class="bhead">
								<tr id="thead_jc">
									<th style="width: 120px;">??????</th>
									<th style="width: 100px;">????????????</th>
									<th style="width: 150px;">????????????</th>
									<th style="width: 150px;">????????????</th>
									<th style="width: 120px;">????????????</th>
									<th style="width: 120px;">????????????</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${$return.operation}" var="oper" varStatus="k">
			                		<tr class="data_tr" data-id="${oper.oper_id}">
										<td style="width: 120px;">${oper.oper_id}</td>
										<td style="width: 100px;">${oper.operation}</td>
										<td style="width: 150px;">${oper.start_date_time}</td>
										<td style="width: 150px;">${oper.end_date_time}</td>
										<td style="width: 120px;">${oper.patient_dept_name}</td>
										<td style="width: 120px;">${oper.oper_dept_name}</td>
									</tr>
			                	</c:forEach>
							</tbody>
		                </table>
		        	</div>
	        </div>
	        <div class="right-content">
						        
        	</div>
			</c:when>
			<c:otherwise>
				<h1 style="text-align: center;line-height: 360px;font-size: 20px;font-weight: 200;color: #a77d2d;">?????????????????????</h1>
			</c:otherwise>
		</c:choose>
	</div>
</body>
<script type="text/javascript">
		$(function(){
			$(".left-tb table .data_tr").click(function(){
				$(this).css("background-color","#a5d8af").siblings().css("background-color","");
				var oper_id=$(this).attr("data-id");
				$.call("hospital_common.patient.queryOperDtl",{"oper_id":oper_id,
					"patient_id":'${param.patient_id}',
					"visit_id":'${param.visit_id}'},function(rtn){
					//console.log(rtn);
					$(".right-content").empty();
					if(rtn!=null&&typeof(rtn)!= "undefined"){
						var html = [];
						$.each(rtn.oper,function(i,v){
                            html.push('<div class="item-d">');
                            html.push(' <h2 class="c-title">'+ (v.operation == null ? "":v.operation) +'</h2>');
                            if(v.operator){
                                html.push('<p class="des-t">???????????????<span class="des-c">'+ v.operator +'</span></p>');
                            }
                            if(v.operation_scale){
                                html.push('<p class="des-t">???????????????<span class="des-c">'+ v.operation_scale +'</span></p>');
                            }
                            if(v.wound_grade){
                                html.push('<p class="des-t">???????????????<span class="des-c">'+ v.wound_grade +'</span></p>');
                            }
                            if(v.smooth_indicator){
                                html.push('<p class="des-t">???????????????????????????<span class="des-c">'+ (v.smooth_indicator=='1'?'??????':'?????????') +'</span></p>');
                            }
                            if(v.operating_room){
                                html.push('<p class="des-t">????????????<span class="des-c">'+ v.operating_room +'</span></p>');
                            }
                            if(v.diag_before_operation){
                                html.push('<p class="des-t">???????????????<span class="des-c">'+ v.diag_before_operation +'</span></p>');
                            }
                            if(v.patient_condition){
                                html.push('<p class="des-t">???????????????<span class="des-c">'+ v.patient_condition +'</span></p>');
                            }
                            if(v.diag_after_operation){
                                html.push('<p class="des-t">???????????????<span class="des-c">'+ v.diag_after_operation +'</span></p>');
                            }
                            if(v.operation_class){
                                html.push('<p class="des-t">???????????????<span class="des-c">'+ v.operation_class +'</span></p>');
                            }
                            if(v.first_assistant){
                                html.push('<p class="des-t">?????????<span class="des-c">'+ v.first_assistant +'</span></p>');
                            }
                            if(v.second_assistant){
                                html.push('<p class="des-t">?????????<span class="des-c">'+ v.second_assistant +'</span></p>');
                            }
                            if(v.anesthesia_method){
                                html.push('<p class="des-t">???????????????<span class="des-c">'+ v.anesthesia_method +'</span></p>');
                            }
                            if(v.anesthesia_doctor){
                                html.push('<p class="des-t">???????????????<span class="des-c">'+ v.anesthesia_doctor +'</span></p>');
                            }
                            if(v.patient_dept_name){
                                html.push('<p class="des-t">???????????????<span class="des-c">'+ v.patient_dept_name +'</span></p>');
                            }
                            if(v.oper_dept_name){
                                html.push('<p class="des-t">???????????????<span class="des-c">'+ v.oper_dept_name +'</span></p>');
                            }
                            html.push('</div>');
						});
						$(".right-content").append(html.join(''));
					}else{
						$(".right-content").append("?????????????????????");
					}
					
				},function(e){}, {async: false, remark: false   })
			}); 
			$(".left-tb table .data_tr").eq(0).click();
		});
		
</script>