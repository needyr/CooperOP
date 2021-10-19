<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.json.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/res/hospital_common/css/demo2.css" rel="stylesheet" type="text/css">
<link type="text/css" rel="stylesheet" href="/theme/plugins/font-awesome/css/font-awesome.min.css">
<link type="text/css" rel="stylesheet" href="/theme/plugins/simple-line-icons/simple-line-icons.min.css">
<html>


<body>
<div class="wk">
    <div class="div-list">
        <p class="dyq">
        	<span data-sort="sy" class="active">基本信息</span>
        	<span data-sort="yz">医嘱</span>
        	<c:if test="${not empty $return.checkandcommentdetail}">
        		<span data-sort="tzdp">用药审查</span>
        	</c:if>
        	<c:if test="${not empty $return.checkandcommentdetail}">
        		<span data-sort="tzdp">医保审查</span>
        	</c:if>
        	<span data-sort="zd">诊断</span>
        	<span data-sort="jc">检查</span>
        	<span data-sort="jy">检验</span>
        	<span data-sort="tz">体征</span>
        	<span data-sort="ss">手术</span>
        	<span data-sort="items">费用</span>
        </p>
        <div class="otherinfo span-content sy">
			<div class="zdp" style="border-bottom: 2px solid #d8d0d0; width: 100%">
				<b>${$return.patient.patient_name}</b>　
				<b>${$return.patient.sex}</b>　
				<b>${$return.patient.age}</b>　
				<b>${empty $return.patient.height ? "":$return.patient.height}${empty $return.patient.height ? "":"cm"}</b>　
				<b>${empty $return.patient.weight ? "":$return.patient.weight}${empty $return.patient.weight ? "":"kg"}</b>
				<c:if test="${$return.patient.d_type eq 1}">
					<b>第${$return.patient.visit_id}次住院</b>　
					<b>住院${$return.patient.ts}</b>
				</c:if>
				<c:if test="${$return.patient.d_type ne 1}">
					<b>就诊序号：${$return.patient.visit_id}</b>　
				</c:if>
				<a href="javascript:void(0);" onclick="caseHistory();" style="float: right;">电子病历</a>
			</div><br>
			<span class="infod">住院号：${$return.patient.patient_no}</span>
			<span class="infod">患者ID：${$return.patient.patient_id}</span>
            <span class="infod">肌酐清除率：${empty $return.patient.ccr? "无":$return.patient.ccr} ${empty $return.patient.ccr? "":" ml/min"}</span>
			<span class="infod">费别：${$return.patient.charge_type_name}</span>
            <span class="infod">医保类别：${$return.patient.insurance_type_name}</span>
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
        <div class="yizhu span-content yz scroll-div" id="scroll-div_yz">
			<table>
			<thead>
				<tr class="position-th-fixed" id="thead_yz">
	                	<th style="width: 60px;">组</th>
	                    <th style="width: 60px;">长/临</th>
	                    <th style="width: 60px;">类别</th>
	                    <th style="width: 140px;">下达时间</th>
	                    <th style="width: 140px;">结束时间</th>
	                    <th style="width: 200px;">医嘱内容</th>
	                    <th style="width: 80px;">剂量</th>
	                    <th style="width: 80px;">单位</th>
	                    <th style="width: 80px;">途径</th>
	                    <th style="width: 80px;">频次</th>
	                    <th style="width: 350px;">药品信息</th>
	                    <th style="width: 80px;">嘱托</th>
	                    <th style="width: 140px;">执行时间</th>
	                    <th style="width: 80px;">医生说明</th>
	                    <th style="width: 80px;">医生</th>
	                    <th style="width: 80px;">停止医生</th>
	                    <th style="width: 80px;">护士</th>
                </tr>
                </thead>
                <tbody class="yizhu_tbody">
                
                </tbody>
         	</table>
        </div>
        
        <div class="tzdianp span-content tzdp" id="scroll-div_tzdianp">
            <div class="left-tb">
            	<c:if test="${not empty $return.checkandcommentdetail}">
				<table class="bhead">
					<thead>
						<tr>
							<th style="width:35px">警</th>
							<th style="width:60px">严重程度</th>
							<th style="width:110px">审查类型</th>
							<th width=300px>药品名称</th>
							<!-- <th>问题来源</th> -->
							<th style="width:110px">患者姓名</th>
							<th style="width:150px">审查时间</th>
							<th width=200px>医生使用理由</th>
							<th style="width:100px">审查来源</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${$return.checkandcommentdetail}" var="cac">
						<tr data-doctor_advice="${cac.doctor_advice}" 
							data-related_drugs_show="${cac.related_drugs_show}" 
							data-sort_name="${cac.sort_name}" 
							data-sys_check_level_name="${cac.sys_check_level_name}" 
							data-description="${cac.description}"
							data-reference="${cac.reference}" data-type="${cac.type}"
							data-yaoshi_advice="${cac.yaoshi_advice}"
							data-yaoshi_name="${cac.yaoshi_name}"
							data-related_drugs_pkey="${cac.related_drugs_pkey}"
							data-patient_id="${cac.patient_id}"
							data-visit_id="${cac.visit_id}"
							data-order_no="${cac.order_no}"
							data-group_id="${cac.group_id}"
							class="data_tr">
							<c:choose>
								<c:when test="${cac.sys_check_level_name eq '不合理'}">
									<td style="color:red;width:35px;font-size: 18px;" title="不合理">×</td>
								</c:when>
								<c:when test="${cac.sys_check_level_name eq '合理'}">
								<td style="color:green;width:35px;font-size: 18px;" title="合理">√</td>
								</c:when>
								<c:when test="${cac.sys_check_level_name eq '争议'}">
								<td style="color:orange;width:35px;font-size: 18px;" title="争议">O</td>
								</c:when>
								<c:when test="${empty cac.check_result_state}">
								<td style="width:35px"><span class="rstate1 fa fa-check-circle-o" title="通过级别问题" ></span></td>
								</c:when>
								<c:when test="${cac.check_result_state eq 'N' }">
								<td style="width:35px"><span class="rstate2 icon-ban" title="拦截级别问题"></span></td>
								</c:when>
								<c:when test="${cac.check_result_state eq 'T' }">
								<td style="width:35px"><span class="rstate3 fa fa-warning (alias)" title="提示级别问题"></span></td>
								</c:when>
								<c:otherwise>
								<td style="width:35px"></td>
								</c:otherwise>
							</c:choose>
							<td style="width:60px">${cac.sys_check_level_name}</td>
							<td style="width:110px">${cac.sort_name}</td>
							<td style="width:300px" class="is_hiddenmore" title="${cac.related_drugs_show}">${cac.related_drugs_show}</td>
							<td style="width:110px">${cac.patient_name}</td>
							<td style="width:150px">${cac.check_datetime}</td>
							<td style="width:200px">${cac.yxk_advice}</td>
							<td style="width:100px">${cac.audit_source_type}</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				</c:if>
            </div>
            <c:if test="${not empty $return.checkandcommentdetail}">
            <div class="detail_result">
							
			</div>
			</c:if>
            <div class="right-content-demo">
            	<c:if test="${not empty $return.checkandcommentdetail}">
				<div  style="width: 900px;">
					<table class="bhead" id="comment_demo">
						<thead>
							<tr>
								<th width="60px">等级</th>
								<th width="20px">组</th>
								<th width="40px">长/临</th>
								<th width="60px">类别</th>
								<th width="150px">开嘱时间</th>
								<th width="150px">停嘱时间</th>
								<th width="300px">药品名称</th>
								<th width="70px">给药方式</th>
								<!-- <th width="60px">剂型</th> -->
								<th width="60px">剂量</th>
								<th width="60px">单位</th>
								<th width="60px">频次</th>
								<th width="350px">药品信息</th>
								<th width="120px">嘱托</th>
								<th width="100px">开嘱科室</th>
								<th width="70px">开嘱医生</th>
								<!-- <th width="60px">毒理分类</th> -->
							</tr>
						</thead>
						<tbody>
						
						</tbody>
					</table>
				</div>
				</c:if>
            </div>
        </div>
        
        <div class="zd span-content " >
            <table class="zd_table">
            </table>
        </div>
        <div class="jiancha span-content jc" id="scroll-div_jc">
            <div class="left-tb">
                <table class="jiancha_table">
                	
                </table>
            </div>
            <div class="right-content">
                <table id="jianchad">
                	
                </table>
            </div>
        </div>
        <div class="jianyan span-content jy">
            <div class="left-tb">
                <table class="jianyan_table">
                	
                </table>
            </div>
            <div class="right-content">
                <table id="jydetail">
                	
                </table>
            </div>
        </div>
        <div class="tizheng span-content tz">
            <div class="left-tb">
                <table class="tizheng_table">
                	
                </table>
            </div>
            <div class="right-content" style="width: 80%">
                <table id="tzdetail">
                <tr>
                    <th>序</th>
                    <th>记录日期</th>
                    <th>时间点</th>
                    <th>项目</th>
                    <th>项目值</th>
                    <th>单位</th>
                </tr>
            </table>
            </div>
        </div>
        
        <div class="shoushu span-content ss">
        	<div class="left-tb">
                <table>
                	<c:if test = "${not empty $return.operation}">
                	<thead class="bhead">
                		<tr>
                        <th style="width: 60px">编号</th>
                        <!-- <th style="width: 60px">所在科室代码</th> -->
                        <th style="width: 60px">术前诊断</th>
                        <th style="width: 60px">手术等级</th>
                        <th style="width: 60px">Ⅰ助</th>
                        <th style="width: 60px">所在科室</th>
                        <th style="width: 60px">手术科室</th>
                        <th style="width: 100px">手术操作人</th>
                        <th style="width: 170px">开始时间</th>
                        <th style="width: 170px">截止时间</th>
                    </tr>
                    </thead>
                	</c:if>
                    <c:forEach items = "${$return.operation}" var = "ope" varStatus = "vs">
                    <tr class="data_tr" data-id="${ope.oper_id}">
                        <td style="width: 60px">${ope.oper_id}</td>
                        <%-- <td style="width: 60px">${ope.dept_stayed}</td> --%>
                        <td style="width: 60px">${ope.diag_after_operation}</td>
                        <td style="width: 60px">${ope.operation_scale}</td>
                        <td style="width: 60px">${ope.first_assistant}</td>
                        <td style="width: 60px">${ope.dept_name}</td>
                        <td style="width: 60px">${ope.oper_dept_name}</td>
                        <td style="width: 100px">${ope.operator_name}</td>
                        <td style="width: 170px">${ope.start_date_time}</td>
                        <td style="width: 170px">${ope.end_date_time}</td>
                    </tr>
                    </c:forEach>
                </table>
            </div>
            
      		<div class="right-content">
      			<table id="ssdetail">
	      			
      			</table>
      		</div>
        </div>
        <!-- 收费项目 -->
        <div class="items span-content">
        	<div class="table_div" style="height: 86%">
        		<table id="bitems" data-q="0">
					<thead>
						<tr>
							<!-- <th style="width: 30px">组</th>
							<th style="width: 40px">长/临</th> -->
	                 		<th style="width: 60px">类别</th>
	                 		<th style="width: 120px">收费时间</th>
	                 		<th style="width: 100px">项目编码</th>
	                 		<th style="width: 200px">项目名称</th>
	                 		<th style="width: 100px">规格</th>
	                 		<th style="width: 60px">数量单位</th>
	                 		<th style="width: 50px">单价/元</th>
	                 		<th style="width: 60px">金额</th>
	                 		<!-- <th style="width: 60px">途径</th> 
	                 		<th style="width: 60px">频次</th>-->
	                 		<th style="width: 250px">药品信息</th>
	                 		<th style="width: 60px">科室</th>
	                 		<th style="width: 60px">医生</th>
	                 		<th style="width: 60px">护士</th>
	                    </tr>
					</thead>
					<tbody>
					
					</tbody>
				</table>
        	</div>
        	<div class="page_div">
        		<span style="position: fixed;left: 18px;">第1页，共100页</span>
			    <a href="#" class="pagenum">上一页</a>
			   	<a href="#" class="pagenum">1</a>
			    <a href="#" class="pagenum">2</a>
		        <a href="#" class="pagenum">3</a>
		        <a href="#" class="pagenum">4</a>
		        <a href="#" class="pagenum">...</a>
		        <a href="#" class="pagenum">下一页</a>
		    </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	 $("#scroll-div_yz").scroll(function() {
         $("#thead_yz").css({"top":$(this).scrollTop()})
   });
	 
	 $("#scroll-div_jc").scroll(function() {
         $("#thead_jc").css({"top":$(this).scrollTop()})
   });
	 
	 $("#scroll-div_zd").scroll(function() {
         $("#thead_zd").css({"top":$(this).scrollTop()})
   });
	 
	 $("#scroll-div_tzdianp").scroll(function() {
         $("#thead_tzdp").css({"top":$(this).scrollTop()})
   });
	$(".dyq span").click(function (){
			$(".wk .div-list .dyq span").removeClass("active");
			$(this).addClass("active");
			$(".div-list").find(".span-content").hide();
			$(".div-list").find("."+$(this).attr("data-sort")).show();
	});
	info();
	//pageNumRe(98, 2, 15);
})

//点击标题异步查看
function info(){
	$("span[data-sort='yz']").click(function(){
		patientdetailYZ();
	})
	$("span[data-sort='zd']").click(function(){
		patientdetailZD();
	})
	$("span[data-sort='jc']").click(function(){
		patientdetailJC();
	})
	$("span[data-sort='jy']").click(function(){
		patientdetailJY();
	})
	$("span[data-sort='tz']").click(function(){
		patientdetailTZ();	
	})
	$("span[data-sort='items']").click(function(){
		patientBillItems(1, 1, 15, 'one');	
	})
}

function syncMove(theadId, tbodyId){
	document.getElementById(tbodyId).onscroll=function(e) {
		document.getElementById(theadId).scrollLeft = document.getElementById(tbodyId).scrollLeft;
	}
}

	function jiancha_click(){
		$(".jiancha .left-tb table .data_tr").click(function(){
			$(this).css("background-color","#a5d8af").siblings().css("background-color","");
			var exam_no=$(this).attr("data-id");
			$.call("hospital_common.showturns.getexamdetail",{"exam_no":exam_no},function(rtn){
				console.log(rtn);
				$("#jianchad").empty();
				if(rtn!=null&&typeof(rtn)!= "undefined"){
					var html = [];
					html.push('<tr>');
					html.push('<th>阴阳性</th>');
					html.push('<th>检查所见</th>');
					/* html.push('<th>申请号</th>'); */
					html.push('<th>检查项目</th>');
					html.push('<th>印象</th>');
					html.push('<th>建议</th>');
					html.push('</tr>');
					$.each(rtn.jianc,function(i,v){
						html.push('<tr>');
						html.push('<td>'+v.is_abnormal+'</td>');
						if(v.use_image != null && v.use_image.indexOf('http')>=0){
							html.push('<td><a onclick="toxd(\''+v.use_image+'\')">'+v.description+'</a></td>');
						}else{
							html.push('<td>'+v.description+'</td>');
						}
						/* html.push('<td>'+v.exam_no+'</td>'); */
						html.push('<td>'+(v.exam_item == null ? " ":v.exam_item)+'</td>');
						html.push('<td>'+v.impression+'</td>');
						html.push('<td>'+v.recommendation+'</td>');
						html.push('</tr>');
					});
					$("#jianchad").append(html.join(''));
				}else{
					 $("#jianchad").append("未查询到信息！");
				}
				
			},function(e){}, {async: false, remark: false   })
		}); 
	}
	
	function tizheng_click(){
		$(".tizheng .left-tb table .data_tr").click(function(){
			$(this).css("background-color","#a5d8af").siblings().css("background-color","");
			var exam_no = $(this).children("th").text();
			$.call("hospital_common.showturns.queryvitaldetail",{"exam_no":exam_no,"visit_id":'${param.visit_id}',"patient_id":'${param.patient_id}'},function(rtn){
				console.log(rtn);
				 if(rtn!=null&&typeof(rtn)!= "undefined"){
					 $("#tzdetail").empty();
	                 $("#tzdetail").append("<tr><th>序</th><th style='width: 130px;'>记录时间</th><th>项目</th><th style='width: 60px;'>项目值</th><th>单位</th></tr>");
					 $.each(rtn.vitaldetail,function(i,v){
					     $("#tzdetail").append("<tr><td>"+(i+1)+"</td><td>"+v.recording_date+" "+v.time_point+"</td><td>"+v.vital_signs+"</td><td style='text-align: center;'>"+v.vital_signs_values+"</td><td>"+(v.units==null?"":v.units)+"</td></tr>");
					});
				}else{
					 $("#tzdetail").append("未查询到信息！");
				} 
				
			},function(e){}, {async: false, remark: false   })
		});
	}
	
	function jianyan_click(){
		$(".jianyan .left-tb table .data_tr").click(function(){
			$(this).css("background-color","#a5d8af").siblings().css("background-color","");
			var test_no = $(this).attr("data-id");
			 $.call("hospital_common.showturns.queryrequestendetail",{"test_no":test_no},function(rtn){
				console.log(rtn);
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
							 trbf=trbf+v.tag;
						 }
						 var traf="</td><td style=''>"+v.report_item_name+"</td><td style='width: 50px;'>"+v.result+"</td><td style='width: 60px;'>"+(v.units==null?"":v.units)+"</td><td style='width: 80px;'>"+(v.print_context==null?"":v.print_context)+"</td><td style='width:  30px;'>"+v.abnormal_indicator+"</td></tr>";
					 	 var trend=trbf+traf
					 /*  $("#jydetail").append("<tr><td style='color:red;'><b>"+v.tag+"</b></td><td>"+v.report_item_name+"</td><td>"+v.result+"</td><td>"+v.units+"</td><td>"+(v.print_context==null?"":v.print_context)+"</td><td>"+v.abnormal_indicator+"</td></tr>"); */
					 $("#jydetail").append(trend);
					 }); 
					
				}else{
					 $("#jydetail").append("未查询到信息！");
				}  
				
			},function(e){}, {async: false, remark: false   })
		});
	}
	
	$(".shoushu .left-tb table .data_tr").click(function(){
		$(this).css("background-color","#a5d8af").siblings().css("background-color","");
		var oper_id = $(this).attr("data-id");
		$.call("hospital_common.showturns.queryOperDetil",{"oper_id":oper_id,"visit_id":'${param.visit_id}',"patient_id":'${param.patient_id}'},function(rtn){
			console.log(rtn);
			if(rtn!=null&&typeof(rtn)!= "undefined"){
				 $("#ssdetail").empty();
                 $("#ssdetail").append("<tr><th>编号</th><th>手术编码</th><th style='width: 200px;'>手术</th><th>手术名称</th><th>手术规模</th><th>创伤等级</th></tr>");
				 $.each(rtn.operdetail,function(i,v){
				     $("#ssdetail").append("<tr><td>"+v.oper_id+"</td><td>"+v.operation_no+"</td><td>"+v.operation+"</td><td>"+(v.operation_name==null?"":v.operation_name)+"</td><td>"+(v.operation_scale==null?"":v.operation_scale)+"</td><td>"+(v.woundgrade_name==null?"":v.woundgrade_name)+"</td></tr>");
				});
			}else{
				 $("#ssdetail").append("未查询到信息！");
			} 
			
		},function(e){}, {async: false, remark: false   })
	});
	
	$(".tzdianp .left-tb table .data_tr").click(function(){
		$(this).css("background-color","#c8ecc9").siblings().css("background-color","");
		var p_keys = $(this).attr("data-related_drugs_pkey");
		var datas = {
			p_keys: p_keys,
			patient_id: $(this).attr("data-patient_id"),
			visit_id: $(this).attr("data-visit_id"),
			order_no: $(this).attr("data-order_no"),
			group_id: $(this).attr("data-group_id")
		}
		var html = [];
		var html2 = [];
		$(".detail_result").empty();
		$(".right-content-demo .bhead tbody").empty();
		if($(this).attr("data-type") == '审查'){
			html.push('<div><span>医生使用理由:</span>'+$(this).attr("data-doctor_advice")+'</div>');
			html.push('<div><span>问题医嘱:</span>'+$(this).attr("data-related_drugs_show")+'</div>');
			html.push('<div><span>审查问题:</span>'+$(this).attr("data-sort_name")+'</div>');
			html.push('<div><span>严重级别:</span>'+$(this).attr("data-sys_check_level_name")+'</div>');
			html.push('<div><span>审查结果:</span>'+$(this).attr("data-description")+'</div>');
			html.push('<div><span>参考文献:</span>'+$(this).attr("data-reference")+'</div>');
		}else{
			html.push('<div><span>点评药师:</span>'+$(this).attr("data-yaoshi_name")+'</div>');
			html.push('<div><span>药师点评意见:</span>'+$(this).attr("data-yaoshi_advice")+'</div>');
			html.push('<div><span>问题医嘱:</span>'+$(this).attr("data-related_drugs_show")+'</div>');
			html.push('<div><span>审查问题:</span>'+$(this).attr("data-sort_name")+'</div>');
			html.push('<div><span>严重级别:</span>'+$(this).attr("data-sys_check_level_name")+'</div>');
			html.push('<div><span>审查结果:</span>'+$(this).attr("data-description")+'</div>');
			html.push('<div><span>参考文献:</span>'+$(this).attr("data-reference")+'</div>');
		}
		$(".detail_result").append(html.join(''));
		$.call("hospital_common.additional.queryRealYizu", datas, function(rtn){
			if(rtn){
				if(rtn.realyizu){
					var v = rtn.realyizu;
					for(i=0;i<rtn.realyizu.length;i++){
						html2.push('<tr class="data_tr">');
						if(v[i].check_level_name){
							html2.push('<td width="60px">'+v[i].check_level_name+'</td>');
						}else{
							html2.push('<td width="60px"></td>');
						}
						html2.push('<td width="20px">'+v[i].tag+'</td>');
						if(v[i].repeat_indicator=='0'){
							html2.push('<td width="40px">临</td>');
						}else if(v[i].repeat_indicator=='1'){
							html2.push('<td width="40px">长</td>');
						}else{
							html2.push('<td width="40px"></td>');
						}
						html2.push('<td width="60px">'+v[i].order_class+'</td>');
						if(v[i].tag == '┍' || v[i].tag == '﹣'){
							if(v[i].enter_date_time){
								html2.push('<td width="150px">'+v[i].enter_date_time+'</td>');
							}else{
								html2.push('<td width="150px"></td>');
							}
							if(v[i].stop_date_time){
								html2.push('<td width="150px">'+v[i].stop_date_time+'</td>');
							}else{
								html2.push('<td width="150px"></td>');
							}
						}else{
							html2.push('<td width="150px"></td>');
							html2.push('<td width="150px"></td>');
						}
						html2.push('<td width="300px"><a onclick="yaopin(\''+v[i].order_code+'\')">'+v[i].order_text+'</a></td>');
						html2.push('<td width="70px">'+v[i].administration+'</td>');
						/* html2.push('<td width="60px">'+v[i].jixing+'</td>'); */
						html2.push('<td width="60px">'+parseFloat(v[i].dosage)+'</td>');
						html2.push('<td width="60px">'+v[i].dosage_units+'</td>');
						html2.push('<td width="60px">'+v[i].frequency+'</td>');
						html2.push('<td width="350px">'+v[i].drug_message+'</td>');
						if(v[i].beizhu){
							html2.push('<td width="120px">'+v[i].beizhu+'</td>');
						}else{
							html2.push('<td width="120px"></td>');
						}
						html2.push('<td width="100px">'+v[i].dept_name+'</td>');
						html2.push('<td width="70px">'+v[i].doctor+'</td>');
						/* html2.push('<td width="60px">'+v[i].property_toxi+'</td>'); */
						html2.push('</tr>');
					}
				}
			}
			$(".right-content-demo .bhead tbody").append(html2.join(''));
		});
	});
	
	function toxd(url){
		 layer.open({
			  type: 2,
			  title: "心电图",
			  area: ['847px', '610px'], //宽高
			  content: url
		}); 
	}
	
	function caseHistory(){
		var patient_id = '${param.patient_id}';
		/* var doctor_no = '${param.doctor_no}';
		if(!doctor_no){
			doctor_no= '${$return.user_no}';
		} */
		var d_type = ${empty $return.patient.d_type? 0:($return.patient.d_type eq 1?2:1)};
		var url = 'http://168.168.170.50:18002/winsso/c/00/0/'+d_type+'/'+patient_id+
		'/'+${$return.patient.visit_id}+'/0/0/0/mzysclient';
		layer.open({
			  type: 2,
			  title: "电子病历",
			  area: ['95%', '90%'], //宽高
			  content: url
		}); 
	}
	
	function yaopin(drugcode){
		if(drugcode){
			//打开药品说明书
			$.modal("/w/ipc/auditresult/instruction.html?his_drug_code="+drugcode,"查看药品说明书",{
		        width:"80%",
		        height:"90%",
		        callback : function(e){
		        }
    		});
		}else{
			$.message("请选择药品！");
		}
		//event.stopPropagation();
	}
	
	//默认第一行被点击
	//$(".dyq span").eq(0).click();
	$(".shoushu .left-tb table .data_tr").eq(0).click();
	$(".tzdianp .left-tb table .data_tr").eq(0).click();
	
	//医嘱信息
	function patientdetailYZ(){
		if($(".yizhu_tbody").text().replace(/(^\s+)|(\s+$)/g,"") == ''){
			var html = [];
			$.call('hospital_common.showturns.patientdetailYZ',{patient_id: '${param.patient_id}',visit_id: '${param.visit_id}'},function(rtn){
				if(rtn && rtn!=''){
					for(i=0;i<rtn.length;i++){
						html.push('<tr>');
						html.push('<td style="width: 60px;">'+rtn[i].tag+'</td>');
						if(rtn[i].repeat_indicator == '1'){
						html.push('<td style="width: 60px;">长</td>');
						}else if(rtn[i].repeat_indicator == '0'){
						html.push('<td style="width: 60px;">临</td>');
						}else{
						html.push('<td style="width: 60px;"></td>');
						}
						html.push('<td style="width: 60px;">'+rtn[i].order_class_name+'</td>');
						html.push('<td style="width: 140px;">'+rtn[i].enter_date_time+'</td>');
						if(rtn[i].stop_date_time != null){
							html.push('<td style="width: 140px;">'+rtn[i].stop_date_time+'</td>');
						}else{
							html.push('<td style="width: 140px;"></td>');
						}
						html.push('<td style="width: 200px;" class="is_hiddenmore" title="'+rtn[i].order_text+'"><a onclick="yaopin(\''+rtn[i].order_code+'\')">'+rtn[i].order_text+'</a></td>');
						html.push('<td style="width: 80px;">'+rtn[i].dosage+'</td>');
						html.push('<td style="width: 80px;">'+rtn[i].dosage_units+'</td>');
						html.push('<td style="width: 80px;">'+rtn[i].administration+'</td>');
						html.push('<td style="width: 80px;">'+rtn[i].frequence+'</td>');
						html.push('<td style="width: 350px;">'+rtn[i].drug_message+'</td>');
						if(rtn[i].beizhu != null){
						html.push('<td style="width: 80px;">'+rtn[i].beizhu+'</td>');
						}else{
						html.push('<td style="width: 80px;"></td>');
						}
						html.push('<td style="width: 140px;">'+rtn[i].start_date_time+'</td>');
						html.push('<td style="width: 80px;">'+rtn[i].freq_detail+'</td>');
						html.push('<td style="width: 80px;">'+rtn[i].doctor+'</td>');
						if(rtn[i].stop_doctor){
	 					html.push('<td style="width: 80px;">'+rtn[i].stop_doctor+'</td>');
	 					}else{
						html.push('<td style="width: 80px;"></td>');	
	 					}
						html.push('<td style="width: 80px;">'+rtn[i].nurse+'</td>');
						html.push('</tr>');
					}
					$(".yizhu_tbody").append(html.join(''));
				}else{
					html.push('<span style="font-size:20px">检查信息无记录</span>');
					$(".yizhu_tbody").append(html.join(''));
				}
			})
		}
	}
	
	//诊断信息
	function patientdetailZD(){
		if($(".zd_table").text().replace(/(^\s+)|(\s+$)/g,"") == ''){
			var html = [];
			$.call('hospital_common.showturns.patientdetailZD',{patient_id: '${param.patient_id}',visit_id: '${param.visit_id}'},function(rtn){
				if(rtn  && rtn!=''){
					html.push('<thead class="bhead" >');
					html.push('<tr >');
					html.push('<th style="width:180px">诊断类别</th>');
					html.push('<th style="width:150px">诊断序号</th>');
					html.push('<th style="width:300px">诊断</th>');
					html.push('<th style="width:150px">诊断日期</th>');
					html.push('</tr>');
					html.push('</thead>');
					for(i=0;i<rtn.length;i++){
						html.push('<tr class="">');
						if(rtn[i].diagnosisclass_name){
						html.push('<td style="width:180px">'+rtn[i].diagnosisclass_name+'</td>');
						}else{
							html.push('<td style="width:180px"></td>');	
						}
						html.push('<td style="width:150px">'+rtn[i].diagnosis_no+'</td>');
						html.push('<td style="width:300px">'+rtn[i].diagnosis_desc+'</td>');
						html.push('<td style="width:150px">'+rtn[i].diagnosis_date+'</td>');
						html.push('</tr>');
					}
					$(".zd_table").append(html.join(''));
				}else{
					html.push('<span style="font-size:20px">检查信息无记录</span>');
					$(".zd_table").append(html.join(''));
				}
			})
		}
	}
	
	//检查信息
	function patientdetailJC(){
		if($(".jiancha_table").text().replace(/(^\s+)|(\s+$)/g,"") == ''){
			var html = [];
			$.call('hospital_common.showturns.patientdetailJC',{patient_id: '${param.patient_id}',visit_id: '${param.visit_id}'},function(rtn){
				if(rtn && rtn!=''){
					html.push('<thead class="bhead">');
					html.push('<tr id="thead_jc" >');
					html.push('<th style="width: 20px;">序</th>');
					html.push('<th style="width: 100px;">检查编号</th>');
					html.push('<th style="width: 100px;">检查类别</th>');
					html.push('<th style="width: 200px;">检查子类</th>');
					html.push('<th style="width: 80px;">申请医生</th>');
					html.push('<th style="width: 150px;">申请时间</th>');
					html.push('<th style="width: 150px;">检查时间</th>');
					html.push('<th style="width: 80px;">报告者</th>');
					html.push('<th style="width: 150px;">报告时间</th>');
					html.push('</tr>');
					html.push('</thead>');
					for(i=0;i<rtn.length;i++){
						html.push('<tr class="data_tr" data-id="'+rtn[i].exam_no+'">');
						html.push('<td>'+(i+1)+'</td>');
						html.push('<td style="width: 100px;">'+rtn[i].exam_no+'</td>');
						html.push('<td>'+rtn[i].exam_class+'</td>');
						html.push('<td>'+rtn[i].exam_sub_class+'</td>');
						html.push('<td>'+rtn[i].req_physician+'</td>');
						html.push('<td>'+rtn[i].req_date_time+'</td>');
						html.push('<td>'+rtn[i].exam_date_time+'</td>');
						html.push('<td>'+rtn[i].reporter+'</td>');
						html.push('<td>'+rtn[i].report_date_time+'</td>');
						html.push('</tr>');
					}
					$(".jiancha_table").append(html.join(''));
					jiancha_click();
					$(".jiancha .left-tb table .data_tr").eq(0).click();
				}else{
					html.push('<span style="font-size:20px">检查信息无记录</span>');
					$(".jiancha_table").append(html.join(''));
				}
			})
		}
	}
	
	
	//检验信息
	function patientdetailJY(){
		if($(".jianyan_table").text().replace(/(^\s+)|(\s+$)/g,"") == ''){
			var html = [];
			$.call('hospital_common.showturns.patientdetailJY',{patient_id: '${param.patient_id}',visit_id: '${param.visit_id}'},function(rtn){
				if(rtn  && rtn!=''){
					html.push('<thead class="bhead">');
					html.push('<tr>');
					html.push('<th style="width: 30px;">序</th>');
					html.push('<th style="width: 60px;">标本</th>');
					html.push('<th style="width: 300px;">检验项目</th>');
					html.push('<th style="width: 60px;">申请医生</th>');
					html.push('<th style="width: 150px;">申请时间</th>');
					html.push('<th style="width: 150px;">报告时间</th>');
					html.push('</tr>');
					html.push('</thead>');
					for(i=0;i<rtn.length;i++){
						html.push('<tr class="data_tr" data-id="'+rtn[i].test_no+'">');
						html.push('<td>'+(i+1)+'</td>');
						if(rtn[i].specimen){
						html.push('<td>'+rtn[i].specimen+'</td>');	
						}else{
						html.push('<td></td>');	
						}
						html.push('<td>'+(rtn[i].item_name!= null?rtn[i].item_name:'')+'</td>');
						html.push('<td>'+rtn[i].ordering_provider+'</td>');
						html.push('<td>'+rtn[i].requested_date_time+'</td>');
						html.push('<td>'+(rtn[i].results_rpt_date_time != null?rtn[i].results_rpt_date_time:'')+'</td>');
						html.push('</tr>');
					}
					$(".jianyan_table").append(html.join(''));
					jianyan_click();
					$(".jianyan .left-tb table .data_tr").eq(0).click();
				}else{
					html.push('<span style="font-size:20px">检验信息无记录</span>');
					$(".jianyan_table").append(html.join(''));
				}
			})
		}		
	}
	
	//体征信息
	function patientdetailTZ(){
		if($(".tizheng_table").text().replace(/(^\s+)|(\s+$)/g,"") == ''){
			var html = [];
			$.call('hospital_common.showturns.patientdetailTZ',{patient_id: '${param.patient_id}',visit_id: '${param.visit_id}'},function(rtn){
				if(rtn && rtn!=''){
					html.push('<thead class="bhead">');
					html.push('<th>项目</th>');
					html.push('</thead>');
					for(i=0;i<rtn.length;i++){
						html.push('<tr class="data_tr">');
						html.push('<th>'+rtn[i].vital_signs+'</th>');
						html.push('</tr>');
					}
					$(".tizheng_table").append(html.join(''));
					tizheng_click();
					$(".tizheng .left-tb table .data_tr").eq(0).click();
				}else{
					html.push('<span style="font-size:20px">体征信息无记录</span>');
					$(".tizheng_table").append(html.join(''));
				}
			})
		}
	}
	
	function patientBillItems(start, page, limit, everytime){
		var is_query = $('#bitems').data('q');
		if((is_query == 0 && everytime == 'one') || everytime == 'more'){
			var tbodyhtml = [];
			var reqdata = {
				'patient_id' : '${param.patient_id}',
				'visit_id' : '${param.visit_id}',
				'start' : start,
				'limit': limit
			};
			$.call('hospital_common.showturns.queryBillItems', reqdata, function(rtn){
				//alert('start' + start + 'page' + page + 'limit' + limit);
				$('#bitems').find('tbody').html('');
				if(rtn && rtn.bitems){
					var items = rtn.bitems.resultset;
					var countNum = rtn.bitems.count;
					for(var i=0; i< items.length; i++){
						console.log(items.shpgg);
						tbodyhtml.push('<tr>');
						/* tbodyhtml.push('<td style="width: 60px">'+ (items[i].tag == null?'':items[i].tag) +'</td>');
						tbodyhtml.push('<td style="width: 60px">'+ (items[i].repeat_indicator == null?'':items[i].repeat_indicator) +'</td>'); */
						tbodyhtml.push('<td style="width: 60px">'+ (items[i].item_class == null ? "": items[i].item_class) +'</td>');
						tbodyhtml.push('<td style="width: 120px">'+ items[i].billing_date_time +'</td>');
						tbodyhtml.push('<td style="width: 100px">'+ items[i].item_code +'</td>');
						tbodyhtml.push('<td style="width: 200px">'+ items[i].item_name +'</td>');
						tbodyhtml.push('<td style="width: 200px">'+ items[i].shpgg +'</td>');
						tbodyhtml.push('<td style="width: 80px">'+ ((parseFloat(items[i].shl)) + items[i].dw) +'</td>');
						tbodyhtml.push('<td style="width: 80px">'+ parseFloat(items[i].dj) +'</td>');
						tbodyhtml.push('<td style="width: 80px">'+ parseFloat(items[i].je) +'</td>');
						/* tbodyhtml.push('<td style="width: 80px">'+ (items[i].administration == null ? "": items[i].administration) +'</td>'); 
						tbodyhtml.push('<td style="width: 80px">'+ (items[i].frequency == null ? "": items[i].frequency) +'</td>');*/
						tbodyhtml.push('<td style="width: 250px">'+ (items[i].drug_message == null ? "":items[i].drug_message ) +'</td>');
						tbodyhtml.push('<td style="width: 60px">'+ items[i].dept_name +'</td>');
						tbodyhtml.push('<td style="width: 80px">'+ (items[i].doctor == null ? "": items[i].doctor) +'</td>');
						tbodyhtml.push('<td style="width: 80px">'+ (items[i].nurse == null ? "": items[i].nurse) +'</td>');
						tbodyhtml.push('</tr>');
					}
					$('#bitems').find('tbody').append(tbodyhtml.join(''));
					pageNumRe(countNum, page, 15);
				}
			});
			$('#bitems').data('q', '1');
		}
	}
	
	function pageNumRe(count, page, rnum){
		var pagehtml = [];
		var maxpage = (count%rnum == 0 ? (count/rnum) :(parseInt(count/rnum) + 1));
		pagehtml.push('<span style="position: fixed;left: 18px;">共'+count+'条，第'+page+'页，共'+ maxpage +'页，每页'+ rnum +'条</span>');
		//start = 10(page-1)+1
		if(page  >1){
			pagehtml.push('<a href="#" class="pagenum" data-num = "'+(page -1)+'">上一页</a>');
			//pagehtml.push('<a href="#" class="pagenum" data-num = "1">1</a>');
		}
		if(page > 1){
			pagehtml.push('<a href="#" class="pagenum"  data-num = "1">1</a>');
		}
		if(page > 2){
			pagehtml.push('<a href="#" class="pagenum"  data-num = "2">2</a>');
		}
		if(page -3 >0){
			pagehtml.push('<a href="#" class="pagenum" style="pointer-events: none;">...</a>');
		} 
		pagehtml.push('<a href="#" class="pagenum bechoose" data-num = "'+(page)+'">'+ page +'</a>');
		 if(page< maxpage- 2){
			pagehtml.push('<a href="#" class="pagenum" style="pointer-events: none;">...</a>');
		}
		 if(page< maxpage- 1){
				pagehtml.push('<a href="#" class="pagenum"  data-num = "'+(maxpage - 1)+'">'+ (maxpage - 1) +'</a>');
			}
		if(page < maxpage){
			pagehtml.push('<a href="#" class="pagenum"  data-num = "'+(maxpage)+'">'+ (maxpage) +'</a>');
		} 
		if(page < maxpage){
			/* pagehtml.push('<a href="#" class="pagenum"  data-num = "'+(maxpage)+'">'+ (maxpage) +'</a>'); */
			pagehtml.push('<a href="#" class="pagenum" data-num = "'+(page+1)+'">下一页</a>');
		}
		$('.page_div').html('');
		$('.page_div').append(pagehtml.join(''));
		$('.page_div .pagenum').click(function(){
			var page = parseInt($(this).data('num'));
			//pageNumRe(98, page, 10);
			patientBillItems(rnum*(page-1) +1, page, rnum, 'more');
		});
	}
</script>