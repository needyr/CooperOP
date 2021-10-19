<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/ipc/css/comment.css" >
<s:page title="点评">
<style type="text/css">
	.pdhl{
	background-color: #e3e3e3; 
    color: #a7a7a7;
    border: none !important;
    border-radius: 21px !important;
    margin-left: 3px;
    line-height: 9px;
}

.sethl {
    float: right;
    font-weight: 600;
    font-size: 13px;
    margin-right: 10px;
    margin-top: 8px;
}

.ui-autocomplete .ui-menu-item {
	padding: 0;
	zoom: 1;
	float: left;
	clear: left;
	width: 100%;
	border-bottom: 1px #bd8a4c dashed !important;
	margin: 1px;
}

.ui-autocomplete {
	display:table;
}


#com_all {
    width: 100%;
    height: 300px;
    margin-top: 15px;
    margin-right: 54px;
    border: 2px solid #d3d1d1;
    background: white;
}

.com_span{
    border-bottom: 1px #d2d1d1 dashed;
    /* display: block; */
    line-height: 20px;
    overflow: hidden;
    /* text-overflow: ellipsis;
    white-space: nowrap; */
    width: 100%;
}
#choose_com{
	overflow: auto;
    height: 177px;
    background-color: #f3f3f38c;
    padding: 3px;
}

#question{
	width: 98%;
    background-color: white;
    height: 100px;
    border: 1px solid;
    margin: 0 0 0 5px;
}

.autocomplete{
	width: 98%;
    margin-left: 5px;
    height: 30px;
}

#comment_content_id{
	height: 120px !important;
    overflow-y: auto !important; 
}

#zhaoz{
	position:absolute;
	left:0;
	top:-50px;
	width:100%;
	height:100%;
	z-index:10000;
	background-color:rgba(206,206,206,0.36);
    cursor: no-drop;
}

.com_span_child{
    display: block;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
    float: left;
}

.glyphicon.glyphicon-remove{
	float: right;
    margin-top: 4px;
}

.tag_type{
	border: 1px solid #ff8100;
	border-radius: 8px;
	padding: 2px;
	font-family: 微软雅黑;
	background-color: #ffdd00;
	font-weight: 600;
	font-size: 10px;
	margin: 2px;
}

#divInfo{
    position: fixed;
    font-size: 14px;
    line-height: 0;
    z-index: 1000;
    background-color: #fdfdfd;
    box-shadow: grey 0px 0px 25px 1px;
}

.ischeck{
	
}
</style>
	<div id="divInfo" style="display:none;">
	<div class="quesdiv" id="quesdiv_box">
	</div>
	</div>
 	<div class="col-md-9">
 			<%-- <span class="input_titles" style="margin-left: 40px;">患者基本信息</span> --%>
 			<div style="margin-left: 20px;z-index=99">
 				<c:if test="${pat.d_type eq 1}">
 					<p class="otherp">
					<b>
						${pat.patient_name}　
						${pat.sex}　
						${pat.age}　
						${empty pat.height ? "":pat.height}${empty pat.height ? "":"cm　"}
						${empty pat.weight ? "":pat.weight}${empty pat.weight ? "":"kg　"}
						第 ${pat.visit_id}次住院　
						住院 ：${pat.ts}
						<a href="javascript:void(0)" onclick="caseHistory();" style="margin-left: 10px">【电子病历】</a>
					</b>
			    	</p>
 				</c:if>
 				<c:if test="${pat.d_type ne 1}">
 					<p class="otherp">
					<b>
						${pat.patient_name}　
						${pat.sex}　
						${pat.age}　
						${empty pat.height ? "":pat.height}${empty pat.height ? "":"cm　"}
						${empty pat.weight ? "":pat.weight}${empty pat.weight ? "":"kg　"}
						就诊编号：${pat.visit_id}　
						<a href="javascript:void(0)" onclick="caseHistory();" style="margin-left: 10px">【电子病历】</a>
					</b>
			    	</p>
 				</c:if>
 			</div>
	 		<div class="simpleinfo" id="xiala">
			<c:if test="${pat.d_type eq 1}">
	 			<span>住院号：${pat.patient_no}</span>
				<span>患者id：${pat.patient_id}</span>
				<span>肌酐清除率：${empty pat.ccr ? "":pat.ccr} ${empty pat.ccr ? "":"ml/min"} </span>
				<span>费别：${pat.charge_type}</span>
				<span>医保号：${pat.insurance_no}</span>
				<span>医保类别：${pat.insurance_type}</span>
				<span>入院科室：${pat.dep_in}</span>
				<span>入院日期：${pat.admission_datetime}</span>
				<span>出院科室：${pat.dep_discharge}</span>
				<span>出院日期：${pat.discharge_datetime}</span>
				<span>科主任：${pat.director}</span>
				<span>主治医生：${pat.attending_doctor}</span>
				<span>经治医生：${pat.doctor_in_charge}</span>
				<c:if test="${not empty $return.diagnosis}">
					<p class="otherp">诊断：
			            <c:forEach items="${$return.diagnosis}" var="diag" >
			            	${diag.diagnosis_desc}、
			            </c:forEach>
			         </p>
	            </c:if>
	            <c:if test="${not empty pat.alergy_drugs}">
	            	<p class="otherp">过敏史：${pat.alergy_drugs}</p>
	            </c:if>
			</c:if>
			<c:if test="${pat.d_type ne 1}">
				<span>肌酐清除率：${empty pat.ccr ? "":pat.ccr} ${empty pat.ccr ? "":"ml/min"} </span>
				<span>费别：${pat.charge_type}</span>
				<span>医保号：${pat.insurance_no}</span>
				<span>医保类别：${pat.insurance_type}</span>
				<span>入院科室：${pat.dep_in}</span>
				<span>入院日期：${pat.admission_datetime}</span>
				<span>出院科室：${pat.dep_discharge}</span>
				<span>出院日期：${pat.discharge_datetime}</span>
				<c:if test="${not empty $return.diagnosis}">
					<p>诊断：
			            <c:forEach items="${$return.diagnosis}" var="diag" >
			            	${diag.diagnosis_desc}、
			            </c:forEach>
		            </p>
	            </c:if>
				<c:if test="${not empty pat.alergy_drugs}">
	            	<p class="otherp">过敏史：${pat.alergy_drugs}</p>
	            </c:if>
			</c:if>
			</div>
			<div id="btn_ss" class="fa fa-angle-double-down showhide"></div>
		<s:row>
		<s:tabpanel >
			<s:form label="点评处方（医嘱）" active="true" >
				<s:row>
				<!-- <div id="showdrug" style="overflow: auto;height:350px"> -->
					<div class="thead_Div" id="main_thead">
						<table class="mytable">
							<thead>
								<tr>
									<th width="20px">警</th>
									<th width="60px">严重程度</th>
									<th width="120px">标志</th>
									<th width="40px">组</th>
									<th width="60px">长/临</th>
									<th width="60px">类别</th>
									<th width="130px">下达时间</th>
									<th width="250px">药品名称</th>
									<th width="60px">剂量</th>
									<th width="60px">单位</th>
									<th width="60px">途径</th>
									<th width="60px">频次</th>
									<th width="130px">停嘱时间</th>
									<th width="120px">医生嘱托</th>
									<th width="80px">开嘱科室</th>
									<th width="80px">开嘱医生</th>
									<th width="60px">剂型</th>
									<th width="60px">毒理分类</th>
								</tr>
							</thead>
						</table>
					</div>
					
					<div class="tbody_Div_info" id="main_tbody">
						<table class="mytable" id="showdrug_table">
							<tbody>
								
							</tbody>
						</table>
					</div>
				<!-- </div> -->
				</s:row>
			</s:form> 
			
			<c:if test="${not empty $return.checkandcommentdetail}">
			<s:form label="审查（点评）结果" active="">
				<s:row>
				<div style="max-height:280px">
					<div class="thead_Div" id="comment_top_thead">
						<table class="mytable">
							<thead>
								<tr>
									<th style="width:20px">警</th>
									<th style="width:60px">严重程度</th>
									<th style="width:110px">审查类型</th>
									<th>药品名称</th>
									<!-- <th>问题来源</th> -->
									<!-- <th style="width:110px">患者姓名</th> -->
									<th style="width:150px">审查时间</th>
									<th>医生使用理由</th>
									<th style="width:85px">审查来源</th>
								</tr>
							</thead>
						</table>
					</div>
					<div  class="tbody_Div_info" id="comment_top_tbody">
						<table class="mytable" id="checkandcomment">
							<tbody>
								<c:forEach items="${$return.checkandcommentdetail}" var="cac">
								<tr data_doctor_advice="${cac.doctor_advice}" 
									data_related_drugs_show="${cac.related_drugs_show}" 
									data_sort_name="${cac.sort_name}" 
									data_sys_check_level_name="${cac.sys_check_level_name}" 
									data_description="${cac.description}"
									data_reference="${cac.reference}" data_type="${cac.type}"
									data_yaoshi_advice="${cac.yaoshi_advice}"
									data_yaoshi_name="${cac.yaoshi_name}"
									data_related_drugs_pkey="${cac.related_drugs_pkey}"
									data_order_no="${cac.order_no}"
									data_check_result_info_id="${cac.check_result_info_id}"
									data_auto_audit_id="${cac.auto_audit_id}"
									data_auto_audit_level="${cac.auto_audit_level}"
									data_check_result_state="${cac.check_result_state}"
									data_yaoshi_audit_time="${cac.yaoshi_audit_time}"
									data_patient_id="${cac.patient_id}"
									data_visit_id="${cac.visit_id}"
									data_order_no="${cac.order_no}"
									data_group_id="${cac.group_id}">
									<c:choose>
										<c:when test="${cac.sys_check_level_name eq '不合理'}">
											<td style="color:red;width:20px">×</td>
										</c:when>
										<c:when test="${cac.sys_check_level_name eq '合理'}">
										<td style="color:green;width:20px">√</td>
										</c:when>
										<c:when test="${cac.sys_check_level_name eq '争议'}">
										<td style="color:orange;width:20px">O</td>
										</c:when>
										<c:when test="${cac.check_result_state eq 'Y' }">
										<td style="width:20px"><span class="rstate1 fa fa-check-circle-o" title="通过级别问题" ></span></td>
										</c:when>
										<c:when test="${cac.check_result_state eq 'N' }">
										<td style="width:20px"><span class="rstate2 icon-ban" title="拦截级别问题"></span></td>
										</c:when>
										<c:when test="${cac.check_result_state eq 'T' }">
										<td style="width:20px"><span class="rstate3 fa fa-warning (alias)" title="提示级别问题"></span></td>
										</c:when>
										<c:otherwise>
										<td style="width:20px"></td>
										</c:otherwise>
									</c:choose>
									<td style="width:60px">${cac.sys_check_level_name}</td>
									<td style="width:110px">${cac.sort_name}</td>
									<td class="is_hiddenmore" title="${cac.related_drugs_show}">${cac.related_drugs_show}</td>
									<%-- <td>${cac.d_type}${cac.p_type}</td> --%>
									<%-- <td style="width:110px">${cac.patient_name}</td> --%>
									<td style="width:150px">${cac.check_datetime}</td>
									<td>${cac.yxk_advice}</td>
									<td style="width:85px">${cac.audit_source_type}</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				</s:row>
			</s:form>	
			</c:if>	
		
			<c:if test="${not empty dias}" >
				<s:form label="诊断">
					<s:row>
						<div class="thead_Div">
							<table class="mytable" >
								<thead>
									<tr>
										<th style="width: 200px">诊断类别</th>
										<th style="width: 150px">诊断序号</th>
										<th style="width: 250px">诊断</th>
										<th style="width: 150px">诊断日期</th>
									</tr>
								</thead>
							</table>
						</div>
						<div  class="tbody_Div">
							<table class="mytable" id="zhenduan">
								<tbody>
								<c:forEach items="${dias}" var="d">
									<tr>
										<td style="width: 200px">${d.diagnosisclass_name}</td>
										<td style="width: 150px">${d.diagnosis_no}</td>
										<td style="width: 250px">${d.diagnosis_desc}</td>
										<td style="width: 150px">${d.diagnosis_date}</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
				</s:row>
			</s:form>
			</c:if>
			<c:if test="${not empty enjoin}">
				<s:form label="医嘱">
					<s:row>
					 <div class="thead_Div" id="lsdiv_thead">
					 <table class="mytable" >
						<thead>
							<tr>
								<th width="20px">组</th>
								<th width="40px">长/临</th>
								<th width="60px">类别</th>
								<th width="130px">下达时间</th>
								<th width="200px">医嘱内容</th>
								<th width="60px">剂量</th>
								<th width="60px">单位</th>
								<th width="60px">途径</th>
								<th width="60px">频次</th>
								<th width="130px">开始执行时间</th>
								<th width="60px">持续时间</th>
								<th width="130px">停嘱时间</th>
								<th width="60px">类型</th>
								<th width="120px">医生嘱托</th>
								<th width="80px">科室</th>
								<th width="130px">医生说明</th>
								<th width="60px">医生</th>
								<th width="60px">停嘱医生</th>
								<th width="60px">护士</th>
								
							</tr>
						</thead>
						</table> 
						</div>
						<div class="tbody_Div" id="lsdiv_tbody">
						<table class="mytable" id="yizhu" style="">
						<tbody>
						<c:forEach items="${$return.enjoin}" var="d">
							<tr>
								<td width="20px">${d.tag}</td>
								<td width="40px">
									${empty d.repeat_indicator?'': (d.repeat_indicator eq '0' ? '临':'长')}
								</td>
								<td width="60px">${d.order_class}</td>
								<td width="130px">${d.enter_date_time}</td>
								<td width="200px" class="is_hiddenmore" title="${d.order_text}">${d.order_text}</td>
								<td width="60px">${d.dosage2}</td>
								<td width="60px">${d.dosage_units}</td>
								<td width="60px">${d.administration}</td>
								<td width="60px">${d.frequence}</td>
								<td width="130px">${d.start_date_time}</td>
								<td width="60px">${d.continue_day}</td>
								<td width="130px">${d.stop_date_time}</td>
								<td width="60px">${d.p_type eq '1' ? '医嘱':'处方'}</td>
								<td width="120px">${empty d.beizhu?'':'d.beizhu'}</td>
								<td width="80px">${d.dept_name}</td>
								<td width="130px">${d.freq_detail}</td>
								<td width="60px">${d.doctor}</td>
								<td width="60px">${d.stop_doctor}</td>
								<td width="60px">${d.nurse}</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					</div>
				</s:row>
			</s:form>
			</c:if>
			<c:if test="${not empty qexam}">
				<s:form label="检查">
					<s:row>
						<div class="col-md-6">
							<div class="thead_Div" id="jc_master_thead">
								<table class="mytable" >
									<thead>
										<tr>
											<th width="100px">检查类别</th>
											<th width="60px">检查子类</th>
											<th width="60px">申请医生</th>
											<th width="130px">申请时间</th>
											<th width="130px">检查时间</th>
											<th width="60px">报告者</th>
											<th width="130px">报告时间</th>
											
										</tr>
									</thead>
								</table>
							</div>
							<div class="tbody_Div" id="jc_mater_tbody">
							<table class="mytable" id="jiancha">
							<tbody>
							<c:forEach items="${qexam}" var="d">
								<tr data-id="${d.exam_no}" >
									<td width="100px">${d.exam_class}</td>
									<td width="60px">${d.exam_sub_class}</td>
									<td width="60px">${d.req_physician}</td>
									<td width="130px">${d.req_date_time}</td>
									<td width="130px">${d.exam_date_time}</td>
									<td width="60px">${d.reporter}</td>
									<td width="130px">${d.report_date_time}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						</div>
					</div>
					<div class="col-md-6">
							<div class="thead_Div" id="jc_thead">
							<table class="mytable">
							<thead>
								<tr>
									<!-- <th width="120px">申请号</th> -->
									<th width="300px">检查所见</th>
									<th width="100px">检查项目</th>
									<th width="60px">阴阳性</th>
									<th width="100px">印象</th>
									<th width="100px">建议</th>
									
								</tr>
							</thead>
							<tbody>
								
							</tbody>
						</table>
						</div>
							<div class="tbody_Div" id="jc_tbody">
							<table class="mytable" id="jcdetail">
							<tbody>
							</tbody>
						</table>
						</div>
					</div>
				</s:row>
			</s:form>
			</c:if>
			
			<c:if test="${not empty requesten}">
				<s:form label="检验">
					<s:row>
						<div class="col-md-6">
							<div class="thead_Div" id="jy_thead">
							<table class="mytable" >
							<thead>
								<tr>
									<th width="80px">标本</th>
									<th width="300px">检验项目</th>
									<th width="60px">申请医生</th>
									<th width="130px">申请时间</th>
									<th width="130px">报告时间</th>
									
								</tr>
							</thead>
						</table>
						</div>
						
						<div class="tbody_Div" id="jy_tbody">
							<table class="mytable" id="jianyan">
							<tbody>
							<c:forEach items="${requesten}" var="d">
								<tr data-id="${d.test_no}">
									<td width="80px">${d.specimen}</td>
									<td width="300px">${d.item_name}</td>
									<td width="60px">${d.ordering_provider}</td>
									<td width="130px">${d.requested_date_time}</td>
									<td width="130px">${d.results_rpt_date_time}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						</div>
					</div>
					<div class="col-md-6">
						<div class="thead_Div" id="jy_detail_thead">
							<table class="mytable">
								<thead>
									<tr>
										<th width="20px">警</th>
										<th width="170px">项目名称</th>
										<th width="60px">检验结果</th>
										<th width="60px">单位</th>
										<th width="80px">参考范围</th>
										<th width="60px">结果标志</th>
										
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>
						</div>
						<div class="tbody_Div" id="jy_detail_tbody">
							<table class="mytable" id="jydetail">
								<tbody>
									
								</tbody>
							</table>
						</div>
					</div>
				</s:row>
			</s:form>
			</c:if>
			
			<c:if test="${not empty vital}">
				<s:form label="体征">
					<s:row>
						<div class="col-md-2">
							<div class="tbody_Div">
								<table class="mytable" id="tizheng">
									<thead>
										<tr>
											<th>项目</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${vital}" var="d">
										<tr data-id="${d.vital_signs}">
											<td>${d.vital_signs}</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
					</div>
					<div class="col-md-10">
						<div class="thead_Div" id="tz_thead">
							<table class="mytable" >
								<thead>
									<tr>
										<th width="130px">记录时间</th>
										<th>项目</th>
										<th width="100px">项目值</th>
										<th width="100px">单位</th>
									</tr>
								</thead>
							</table>
						</div>
						
						<div class="tbody_Div">
							<table class="mytable" id="tzdetail">
								<!-- <thead>
									<tr>
										<th width="130px">记录时间</th>
										<th>项目</th>
										<th>项目值</th>
										<th>单位</th>
									</tr>
								</thead> -->
								<tbody>
								
								</tbody>
							</table>
						</div>
					</div>
				</s:row>
			</s:form>
			</c:if>
			
			<c:if test="${not empty oper}">
				<s:form label="手术">
					<s:row>
						<div class="col-md-6">
							<div class="tbody_Div">
							<table class="mytable" id="shoushu">
							<thead>
								<tr>
									<th width="60px">编号</th>
									<th width="60px">所在科室代码</th>
									<th width="120px">术前诊断</th>
									<th width="40px">手术等级</th>
									<th width="60px">Ⅰ助</th>
									<th width="80px">所在科室</th>
									<th width="80px">手术科室</th>
									<th width="60px">手术操作人</th>
									<th width="130px">开始时间</th>
									<th width="130px">截止时间</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${oper}" var="d">
								<tr data-id="${d.oper_id}">
									<td>${d.oper_id}</td>
									<td>${d.dept_stayed}</td>
									<td>${d.diag_after_operation}</td>
									<td>${d.operation_scale}</td>
									<td>${d.first_assistant}</td>
									<td>${d.dept}</td>
									<td>${d.oper_dept}</td>
									<td>${d.operator}</td>
									<td>${d.start_date_time}</td>
									<td>${d.end_date_time}</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						</div>
					</div>
					<div class="col-md-6">
							<div class="tbody_Div">
							<table class="mytable" id="ssdetail">
							<thead>
								<tr>
									<th width="60px">编号</th>
									<th width="100px">手术编码</th>
									<th width="100px">手术名称</th>
									<th width="80px">手术</th>
									<th width="40px">手术规模</th>
									<th width="40px">创伤等级</th>
								</tr>
							</thead>
							<tbody>
								
							</tbody>
						</table>
						</div>
					</div>
				</s:row>
			</s:form>
			</c:if>
				</s:tabpanel> 
		</s:row>
		<s:row>
			<div style="" id="bom_zong">
				<div class="card-tabs-bar graygreen">
				<a class="active" id="shenc" onclick="shenc();">点评详情</a>
				<c:if test="${not empty $return.checkandcommentdetail}">
				<a class="" id="dianp" onclick="dianp();">审查详情</a>
				</c:if>
				</div>
				<div class="card-tabs-stack graygreen">
				<div id="commentright" style="display: none;">
					<div class="detail_result" style="max-height:160px;overflow: auto;">
						
						</div>
						<div>
						<div class="thead_Div" style="margin-top: 5px" id="comment_search_thead">
							<table class="mytable">
								<thead>
									<tr>
										<th width="60px">严重程度</th>
										<th width="20px">组</th>
										<th width="40px">长/临</th>
										<th width="60px">类别</th>
										<th width="130px">下达时间</th>
										<th width="130px">停嘱时间</th>
										<th width="300px">药品名称</th>
										<!-- <th width="60px">剂型</th> -->
										<th width="60px">剂量</th>
										<th width="60px">单位</th>
										<th width="70px">途径</th>
										<th width="60px">频次</th>
										<th width="120px">医生嘱托</th>
										<th width="100px">开嘱科室</th>
										<th width="70px">开嘱医生</th>
										<!-- <th width="60px">毒理分类</th> -->
										<th class="tbth"></th>
									</tr>
								</thead>
							</table>
						</div>
						<div  class="tbody_Div_shenc" id="comment_search_tbody">
							<table class="mytable" id="sc_realinfo">
								<tbody>
									<!-- <tr>
										<td width="50px"></td>
										<td width="20px"></td>
										<td width="40px"></td>
										<td width="40px"></td>
										<td width="130px"></td>
										<td width="130px"></td>
										<td width="300px"></td>
										<td width="70px"></td>
										<td width="60px"></td>
										<td width="60px"></td>
										<td width="60px"></td>
										<td width="60px"></td>
										<td width="120px"></td>
										<td width="100px"></td>
										<td width="70px"></td>
										<td width="60px"></td>
									</tr> -->
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div id="tab2" style="display: block;">
					<!-- <div class="quesdiv" id="quesdiv_box">
						</div> -->
						<div class="last_left_bom_table">
							<table class="mytable" id="bx_table">
								<thead>
									<tr>
										<th width="20px">警</th>
										<th width="60px">严重程度</th>
										<th width="120px">标志</th>
										<th width="30px">组</th>
										<th width="60px">长/临</th>
										<th width="60px">类别</th>
										<th width="130px">下达时间</th>
										<th width="250px">药品名称</th>
										<th width="60px">剂量</th>
										<th width="60px">单位</th>
										<th width="60px">途径</th>
										<th width="60px">频次</th>
										<th width="130px">停嘱时间</th>
										<th width="120px">医生嘱托</th>
										<th width="80px">开嘱科室</th>
										<th width="80px">开嘱医生</th>
										<th width="60px">剂型</th>
										<th width="60px">毒理分类</th>
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>
						</div>
				</div>
				</div>
				</div>
		</s:row>
 	</div>
 	
 	<!-- 点评 右侧 -->
 	<div class="col-md-3" id="dpdiv">
 		
 		
 		<div id="com_place">
	 		<!-- 保护层  -->
	 		<div id="zhaoz" style="display: block"></div>
	 		<div class="sr" style="background: #95aeb1;color: white;">
	 			<%-- <span style="color:white">点评</span> --%>
				<span style="color:white">
				<c:if test="${comment.comment_way eq '1'}">
				常规点评
				</c:if>
				<c:if test="${comment.comment_way eq '2'}">
				专项点评
				</c:if>
				</span>
			</div>
			<!-- <div id="com_online">
			
			</div> -->
	 		<!-- 检索输入 -->
		 	<s:row>
				<div id="check_div">
					<s:autocomplete name="system_code" action="ipc.commentflow.queryComByInp"  value="${system_code}"  limit="15" editable="true" placeholder="请输入规则编码，名称，或简称" params="{&#34;sample_id&#34;: &#34;${param.sample_id}&#34;}">
							<s:option value="$[system_code]" label="$[comment_name]">
								<span style="width:60px;display:block;float:left" title="$[comment_name]">
									$[comment_name]
								</span>
							</s:option>
					</s:autocomplete>
		   		 	<div id="com_all">
		   		 		<div id="choose_com">
		   		 		</div>
		   		 		<s:textarea rows="4" placeholder="请输入点评意见" id="comment_content_id"></s:textarea>
		   		 	</div>
				</div>
			</s:row>
				<div class="btn-group" style="margin:5px 0px 0px 5px;">
					<c:if test="${comment.comment_way eq '2'}">
				    <button type="button" class="btn btn-default pdhl" data-choose="1"><i>○</i>合理</button>
					</c:if>
				    <button type="button" class="btn btn-default pdhl" data-choose="0"><i>○</i>不合理</button>
				    <c:if test="${comment.comment_way eq '1'}">
				    <button type="button" class="btn btn-default pdhl" data-choose="2"><i>○</i>争议</button>
				    </c:if>
				</div>
				<div class="btn-group" style="margin:20px 0px 0px 5px;float:right" >
				  	<button type="button" onclick="saveResult();" class="btn btn-default" id="btnsave">
						<span class="fa fa-save"></span>
						保存
					</button>
				</div>
 			</div>
        	<div style="margin-top: 60px;">
        		<button type="button" onclick="finishOne()" class="btn btn-default"   id="btnsave">
					<span class="fa fa-check"></span>
					完成点评
				</button>  
				<label for="tonext_id" >
					<input type="checkbox" name="tonext" checked="checked" id="tonext_id">
					<span>继续点评下一个患者${empty param.notcnum?'': '【剩'}${param.notcnum}${empty param.notcnum?'': '人】'}</span>
				</label>
			</div>
	</div>
</div>
	
</s:page>
<script type="text/javascript">
	var comment_way = '${comment.comment_way}';
	var selectData={};//选择医嘱
	var _code = [];//所有的问题code
	var is_checked_comment_result = '';//是否合理
	
	$(function(){
		autoAll();//适配
		refrechMain();//绘制点评医嘱表格
		
		//定表头
		syncMove('lsdiv_thead', 'lsdiv_tbody');
		syncMove('jc_master_thead', 'jc_mater_tbody');
		syncMove('jy_thead', 'jy_tbody');
		syncMove('main_thead', 'main_tbody');
		syncMove('comment_top_thead', 'comment_top_tbody');
		syncMove('comment_search_thead', 'comment_search_tbody');
		
		//默认点选第一个
		$("#jiancha tbody tr").eq(0).click();
		$("#jianyan tbody tr").eq(0).click();
		$("#tizheng tbody tr").eq(0).click();
		$("#shoushu tbody tr").eq(0).click();
		
		//点评结果选择
		$('.pdhl').click(function(){
			$(this).css({'background-color':'#3498db !important','color':'white'}).siblings().css({'background-color':'','color' : ''});
			$(this).find('i').text('●');
			$(this).siblings().find('i').text('○');
			is_checked_comment_result = $(this).attr('data-choose');
			/* if(check_qiehuan){
				$('#choose_com').empty();
				//$('[name=system_code]').attr('readonly','readonly');
				//$('[id=comment_content_id]').val('');
				_code = [];
			}else{
				//$('[name=system_code]').removeAttr('readonly');
			} */
			$('#choose_com').empty();
			$('[id=comment_content_id]').val('');
			_code = [];
			if(comment_way == '2'){
				$('[name="system_code"]').attr('params','{"sample_id":"'+${param.sample_id}+'","data_choose":"'+is_checked_comment_result+'"}');
				$('[name="system_code"]').params_autocomplete({"sample_id":${param.sample_id},"data_choose":is_checked_comment_result});
			}
		});
		
		//选择添加点评
		$('[name=system_code]').change(function(){
			var content = $(this).val();
			var data_des = $(this).val('');
			var data = $('[name=system_code]').getData().system_code;
			if(data){
				var num = _code.length;
				if(num == 0){
					$('#choose_com').append('<div title="'+content+'" class="com_span" ><span class="com_span_child">'+content+'</span><a data_id="'+data+'" onclick="deleteQuestion(this);" class="glyphicon glyphicon-remove"></a></div> ');
					$('.com_span_child').width($('#choose_com').width()-18);
					_code.push(data);
				}else{
					var is_had = false ;
					for(i=0;i<num;i++){
						if(_code[i] == data){
							is_had = true;
						}
					}
					if(is_had == false ){
						$('#choose_com').append('<div title="'+content+'"  class="com_span" ><span class="com_span_child">'+content+'</span><a data_id="'+data+'" onclick="deleteQuestion(this);" class="glyphicon glyphicon-remove"></a></div> ');
						$('.com_span_child').width($('#choose_com').width()-18);
						_code.push(data);
					}
				}
			}
			$('[name=system_code]').blur();
			$('#comment_content_id').focus();
		});
		
		//失去焦点 解决检索小bug
		$('[name=system_code]').blur(function(){
			$('[name=system_code]').val('');
		});
		
		$("#showdrug_table").find("tbody").on("click", "tr", function() {
				//增加到  下面的列表中
				var tr_order_no  = $(this).attr('data-id');
				var tr_pid  = $(this).attr('data-pid');
				var tr_group_id = $(this).attr('data-group_id');
				var tr_sample_orders_id  = $(this).attr('data-sample_orders_id');
				selectData.order_no = tr_order_no;
				selectData.sample_patients_id = tr_pid;
				selectData.group_id = tr_group_id;
				selectData.sample_orders_id = tr_sample_orders_id;
				var data_choose = $(this).data('choose');
				var tr_class ='.' + $(this).attr("class");
				if(data_choose == true){
					$(tr_class).css('background-color', '');
					$(tr_class).data('choose' , false);
					$('#bx_table tbody '+tr_class).remove();
					_code = [];
					is_checked_comment_result = 1;
					$('#choose_com').empty();
					$('#comment_content_id').empty();
					$('#comment_content_id').val('');
					if(comment_way == '1'){
						$('[data-choose = 0]').css({'background-color':'#3498db !important','color':'white'}).siblings().css({'background-color':'','color' : ''});
						$('[data-choose = 0]').find('i').text('●');
						$('[data-choose = 0]').siblings().find('i').text('○');
						is_checked_comment_result = 0;
					}else if(comment_way == '2'){
						$('[data-choose = 1]').css({'background-color':'#3498db !important','color':'white'}).siblings().css({'background-color':'','color' : ''});
						$('[data-choose = 1]').find('i').text('●');
						$('[data-choose = 1]').siblings().find('i').text('○');
						is_checked_comment_result = 1;
						$('[name="system_code"]').attr('params','{"sample_id":"'+${param.sample_id}+'","data_choose":"'+is_checked_comment_result+'"}');
						$('[name="system_code"]').params_autocomplete({"sample_id":${param.sample_id},"data_choose":is_checked_comment_result});
					}
					var is_nochoose = $('#bx_table tbody tr');
					if(is_nochoose.length == 0){
						$('#zhaoz').css('display', 'block')
					} 
				}else{
					if(comment_way == '1'){
						$('[data-choose = 0]').css({'background-color':'#3498db !important','color':'white'}).siblings().css({'background-color':'','color' : ''});
						$('[data-choose = 0]').find('i').text('●');
						$('[data-choose = 0]').siblings().find('i').text('○');
						is_checked_comment_result = 0;
					}else if(comment_way == '2'){
						$('[data-choose = 1]').css({'background-color':'#3498db !important','color':'white'}).siblings().css({'background-color':'','color' : ''});
						$('[data-choose = 1]').find('i').text('●');
						$('[data-choose = 1]').siblings().find('i').text('○');
						is_checked_comment_result = 1;
						$('[name="system_code"]').attr('params','{"sample_id":"'+${param.sample_id}+'","data_choose":"'+is_checked_comment_result+'"}');
						$('[name="system_code"]').params_autocomplete({"sample_id":${param.sample_id},"data_choose":is_checked_comment_result});
					}
					$('#zhaoz').css('display', 'none')
					$(tr_class).data('choose' , true);
					$(tr_class).css('background-color', '#e6e6e7 !important');
					var clone_tr=$(tr_class).clone();
					$('#bx_table tbody').append(clone_tr);
					//勾选
					var gxdata={
							order_no: selectData.order_no,
							sample_orders_id: selectData.sample_orders_id,
							group_id: selectData.group_id ,
							visit_id: '${param.visit_id}',
							patient_id: '${param.patient_id}', 
							sample_id: '${param.sample_id}',
							sample_patients_id: selectData.sample_patients_id
					};
					$.call("ipc.commentflow.getHasComment",gxdata,function(rtn){
						$('#choose_com').empty();
						$('#comment_content_id').empty();
						$('#comment_content_id').val('');
						//$("[data-choose]").css({'background-color': '','color': ''});清空点击 暂时不需要，默认合理
						_code = [];
						if(rtn){
							if(rtn.checker){
							for(i=0;i<rtn.checker.length;i++){
								if(rtn.checker[i].pharmacist_comment_id){
									if(rtn.checker[i].comment_name){
										$('#choose_com').append('<div title="'+rtn.checker[i].comment_name+'"  class="com_span" ><span class="com_span_child">'+rtn.checker[i].comment_name+'</span><a data_id="'+rtn.checker[i].pharmacist_comment_id+'" class="glyphicon glyphicon-remove glyphicon-remove-s" onclick="deleteQuestion(this);"></a></div> ');
										$('.com_span_child').width($('#choose_com').width()-18);
										_code.push(rtn.checker[i].system_code);
									}
								}
							}  
							}
							$("#comment_content_id").val(rtn.writer.comment_content)
							if(rtn.writer.comment_result == null || rtn.writer.comment_result == 1){
								is_checked_comment_result = 1;
								$('[data-choose = 1]').css({'background-color':'#3498db !important','color':'white'}).siblings().css({'background-color':'','color' : ''});
								$('[data-choose = 1]').find('i').text('●');
								$('[data-choose = 1]').siblings().find('i').text('○');
								if(comment_way == '2'){
									$('[name="system_code"]').attr('params','{"sample_id":"'+${param.sample_id}+'","data_choose":"'+is_checked_comment_result+'"}');
									$('[name="system_code"]').params_autocomplete({"sample_id":${param.sample_id},"data_choose":is_checked_comment_result});
								}
							}else{
								is_checked_comment_result = rtn.writer.comment_result;
								$('[data-choose = '+rtn.writer.comment_result+']').css({'background-color':'#3498db !important','color':'white'}).siblings().css({'background-color':'','color' : ''});
								$('[data-choose = '+rtn.writer.comment_result+']').find('i').text('●');
								$('[data-choose = '+rtn.writer.comment_result+']').siblings().find('i').text('○');
								if(comment_way == '2'){
									$('[name="system_code"]').attr('params','{"sample_id":"'+${param.sample_id}+'","data_choose":"'+is_checked_comment_result+'"}');
									$('[name="system_code"]').params_autocomplete({"sample_id":${param.sample_id},"data_choose":is_checked_comment_result});
								}
							}
						}
					},function(e){},{async:false,remark:false});
					
					var ischeck=$(".datadataTables_select").attr("checked");
					$("#dpdiv").css("display","block");
					var sdata = {
						order_no: selectData.order_no,
						group_id: selectData.group_id,
						visit_id: '${param.visit_id}',
						patient_id: '${param.patient_id}'
					};
					$.call("ipc.commentflow.getQuestions",sdata,function(rtn){
						// $(".quesdiv").empty();
						 $('#com_online').empty();
						 var data=rtn.data;
						 if(rtn.bcomt && rtn.bcomt.length > 0){
							 var combhtml = [] ;
							 combhtml.push('<div class="input_titles" style="margin-top: 5px">在线点评结果</div>');
							 combhtml.push('<p>【'+rtn.bcomt[0].comment_pharmacist+'】' + rtn.bcomt[0].comment_datetime+ rtn.bcomt[0].comment_source +'：');
							 if(rtn.bcomt[0].comment_result == 0){
								 combhtml.push('<b>不合理</b>');
							 }else if(rtn.bcomt[0].comment_result == 1){
								 combhtml.push('<b>合理</b>');
							 }else if(rtn.bcomt[0].comment_result == 2){
								 combhtml.push('<b>争议</b>');
							 }
							 combhtml.push('</p>');
							 for(var i=0; i<rtn.bcomt.length; i++){
								 combhtml.push('<li>');
								 combhtml.push(rtn.bcomt[i].comment_name);
								 combhtml.push('</li>');
							 }
							 combhtml.push('<p>附加描述：'+rtn.bcomt[0].comment_content +'</p>');
							 $('#com_online').append(combhtml.join(''));
						 }
					},function(e){},{async:false,remark:false});
					
				}
				delete_choose();//双击移除
				
			}); 
		//$('#showdrug_table tbody tr').eq(0).click();
	});
	
	
	/* function 定义  */
	function autoAll(){
		//患者信息下拉
		var box = document.getElementById("xiala")
	    var btn = document.getElementById("btn_ss")
		var real_height=box.offsetHeight+10;
		$('#xiala').hide();
		$('#btn_ss').attr('class','fa fa-angle-double-down');
		//适配一些DIV高度
		var left_bom_height=$(window).height() - real_height - 42;
		//$('.card-tabs-stack.graygreen').css('height',$(window).height() - 450);
		//$('.last_left_bom_table').css('height',$(window).height() - 610);
		$('.card-tabs-stack.graygreen').css('height',$(window).height() - 420);
		$('.last_left_bom_table').css('height',$(window).height() - 460);
		$('.tbody_Div').css('height',$(window).height() - 140);
		btn.onclick = function() {
	        if (box.offsetTop == 26) {
	        	box.style['margin-top'] = -real_height+15 + "px";
	    		$('#xiala').hide(500);
	    		$('#btn_ss').attr('class','fa fa-angle-double-down');
	        } else {
	        	box.style['margin-top'] = 0 + "px";
	        	$('#xiala').show(500);
	        	$('#btn_ss').attr('class','fa fa-angle-double-up');
	        }
	    }
		
		//计算右边高度
		var right_height_cols = $(window).height()-270;
		$("#right_gx").css("height" ,right_height_cols); 
		$('#com_online').css('max-height', $(window).height() - 510 );
		//alert($(window).height() - 510);
		$('')
	}
	
	//表格表头跟随内容滑动
	 function syncMove(theadId, tbodyId){
		try{
			document.getElementById(tbodyId).onscroll=function(e) {
				document.getElementById(theadId).scrollLeft = document.getElementById(tbodyId).scrollLeft;
			}	
		}catch(error){
			console.log(tbodyId + " onscroll is miss ... ");
		}
	}
	
	function introDisplayNone(_this){
		var x = event.x; //鼠标X轴坐标
        var y = event.y; //鼠标y轴坐标
        //calc(100% - 80px)
        //var height ="calc(100% - "+(x+20)+"px)";
        var width ="calc(100% - "+(y-20)+"px)";
        $('#divInfo').css({"left":x+20,"top":y+10,"max-width":width});
        $('#divInfo').addClass("ischeck");
        //++++++++++
        var tr_order_no  = $(_this).attr('data-id');
		var tr_group_id = $(_this).attr('data-group_id');
        var sdata = {
			order_no: tr_order_no,
			group_id: tr_group_id,
			visit_id: '${param.visit_id}',
			patient_id: '${param.patient_id}'
		};
        $.call("ipc.commentflow.getQuestions",sdata,function(rtn){
       	$(".quesdiv").empty();
       	var data = rtn.data;
        if(data && data.length >0){
			 var html=[];
				for(i=0;i<data.length;i++){
					html.push(' <div class="quesdetail">');
					html.push('<div><a href="javascript:void(0)" onclick="setHL('+"'"+data[i].check_result_info_id+"'"+',this'+",'"+data[i].auto_audit_id+"',"+data[i].auto_audit_level+');" class="sethl">审查结果调整</a></div>');
					if(data[i].related_drugs_show){
						html.push('<p class="scp" style="color: #aa0c0c" >');
						html.push('问题药品：' +data[i].related_drugs_show);
						html.push('</p>');
					}
					html.push('<p class="scp">');
					html.push('医生用药理由：'+data[i].doctor_advice);
					html.push('</p>');
					/* html.push('<p class="scp" style="color: #aa0c0c">');
					html.push('前置审方结果：'+data[i].yxk_advice+'<font style="color:#929292">'+'【'+data[i].yaoshi_audit_time+'　'+data[i].yaoshi_name+'】</font>');
					html.push('</p>'); */
					html.push(' <p class="scp">');
					html.push('审查类型：');
					if(data[i].check_result_state=='Y'){
						html.push('<span class="rstate1 fa fa-check-circle-o" title="通过级别问题" ></span>');
					}else if(data[i].check_result_state=='N'){
						html.push('<span class="rstate2 icon-ban"  title="拦截级别问题"></span>');
					}else if(data[i].check_result_state=='T'){
						html.push('<span class="rstate3 fa fa-warning (alias)"  title="审查级别问题"></span>');
					}
					html.push(data[i].sort_name+'<font style="color: #929292;margin-left:20px">【'+data[i].check_datetime+'　'+data[i].audit_source_type+'】</font>');
					//html.push('<a href="javascript:void(0)" onclick="setHL('+"'"+data[i].check_result_info_id+"'"+',this'+",'"+data[i].auto_audit_id+"',"+data[i].auto_audit_level+');" class="sethl">审查结果调整</a>');
					html.push(' </p>');
					html.push('<p class="scp">');
					html.push('严重程度：'+data[i].sys_check_level_name +'【' +data[i].star_level+'】');
					html.push('</p>');
					html.push('<p class="scp">');
					html.push('审查结果：'+data[i].description);
					html.push('</p>');
					html.push('<p style="color: #929292" class="scp">');
					html.push(data[i].reference == null ? "": "参考文献："+data[i].reference);
					html.push('</p>');
					html.push('</div>');
				 
					 //相互作用，重复用药等多组问题带出
					 if(data[i].related_drugs_pkey != null){
						 //去掉自身的p_key 
						 var all_pkeys =  data[i].related_drugs_pkey.replace(tr_group_id,'');
						 arr_pkey=all_pkeys.split(',');
						 for(y=0 ; y<arr_pkey.length ; y++){
							 $('#showdrug_table tbody tr').each(function (i){
									var _trpkey = $(this).attr('data-group_id');
									if(_trpkey == arr_pkey[y]){
										var _class = '.' +$(this).attr('class');
										try{
											var is_bxz = $('#showdrug_table tbody ' + _class).data('choose');
											if(is_bxz != true){
												$('#showdrug_table tbody ' + _class).data('choose' , true);
												$('#showdrug_table tbody ' + _class).css('background-color', '#e6e6e7 !important');
												var clone_tr2=$('#showdrug_table tbody ' + _class).clone();
												$('#bx_table tbody').append(clone_tr2);
											}
										}catch(error){
											console.log('已被选中...');
										}
									}
								});
						 }
					} 
				}
				//console.log(data);
				$(".quesdiv").append(html.join(''));
				$('#divInfo').show();
       		}else{
       			$('#divInfo').hide();
       		}
        },function(e){},{async:false});
        //-----------
	}
	
	function introDisplayBlock(){
		setTimeout(function(){
			if(!$('#divInfo').hasClass("ischeck")){
				$('#divInfo').hide();
			}
		},200);
	}
	
	//隐藏医嘱信息
	$('#divInfo').hover(function(){
		$('#divInfo').addClass("ischeck");
		$('#divInfo').show();
	},function(){
		$('#divInfo').removeClass("ischeck");
		$('#divInfo').hide();
	})
	
	$('#bom_zong').hover(function(){
		$('#divInfo').removeClass("ischeck");
		$('#divInfo').hide();
	},function(){
		$('#divInfo').removeClass("ischeck");
		$('#divInfo').hide();
	})
	
	$('#dpdiv').hover(function(){
		$('#divInfo').removeClass("ischeck");
		$('#divInfo').hide();
	},function(){
		$('#divInfo').removeClass("ischeck");
		$('#divInfo').hide();
	})
		
	//刷新 点评医嘱表
	function refrechMain(){
		$("#showdrug_table tbody").empty();
		var data={
				sample_id: '${param.sample_id}',
				patient_id: '${param.patient_id}'
		};
		var html = [], rdata;
		$.call("ipc.commentflow.queryOrders" ,data , function(rtn){
			var zuindex = 0;
			rdata = rtn.resultset;
			if(rtn && rdata.length > 0){
				for(x in rdata){
					html.push('<tr onmouseover="introDisplayNone(this);" onmouseout="introDisplayBlock(this);"  data-id = "'+rdata[x].order_no+'" ');
					html.push('data-pid="'+rdata[x].sample_patients_id+'" ');
					html.push('data-group_id="'+rdata[x].group_id+'"');
					html.push('data-sample_orders_id="'+rdata[x].order_result_id+'"');
					html.push('class="bxz_'+rdata[x].order_no.replace('#', 'a')+'b'+rdata[x].group_id+'"');
					html.push('>');
					if(rdata[x].comment_result == '0')
						html.push('<td style="color:red;width:20px">×</td>');
					else if(rdata[x].comment_result == '1')
						html.push('<td style="color:green;width:20px">√</td>');
					else if(rdata[x].comment_result == '2')
						html.push('<td style="color:orange;width:20px">O</td>');
					else
						html.push('<td style="width:20px"></td>');
					html.push('<td style="width:60px">' + (rdata[x].sys_check_level_name == null ?"":rdata[x].sys_check_level_name) + '</td>');
					if(rdata[x].tags && (rdata[x].tag == '┍' || rdata[x].tag == '﹣')){
						html.push('<td style="width:120px">');
						var tags = rdata[x].tags;
						var tag = tags.substring(0, tags.length - 1).split(',');
						for(i in tag){
							var shortnameAndName = tag[i].split(':');
							html.push('<nobr class="tag_type" title="'+shortnameAndName[1]+'">'+shortnameAndName[0]+'</nobr>');
						}
						html.push('</td>');
					}else{
						html.push('<td style="width:120px"> </td>');
					}
					html.push('<td style="width:40px">' + rdata[x].zu + '</td>');
					if(rdata[x].lc == '0')
						html.push('<td style="width:60px">临</td>');
					else if (rdata[x].lc == '1')
						html.push('<td style="width:60px">长</td>');
					else
						html.push('<td style="width:60px"></td>');
					if(rdata[x].yp){
						html.push('<td style="width:60px">' + rdata[x].yp + '</td>');
					}
					else
						html.push('<td style="width:60px"></td>');
					html.push('<td style="width:130px">' + rdata[x].stime + '</td>');
					if(rdata[x].is_gwyp == '是'){
						html.push('<td style="width:250px" class="is_hiddenmore" title="'+rdata[x].order_text+'"><span class="gwyp" title="高危药品">高危</span><a onclick="yaopin(\''+rdata[x].order_code+'\')">' + rdata[x].order_text + '</a></td>');
					}else{
						html.push('<td style="width:250px" class="is_hiddenmore" title="'+rdata[x].order_text+'"><a onclick="yaopin(\''+rdata[x].order_code+'\')">' + rdata[x].order_text + '</a></td>');
					}
					html.push('<td style="text-align: right;width:60px">' + parseFloat(rdata[x].dosage) + '</td>');
					html.push('<td style="width:60px">' + rdata[x].dosage_units + '</td>');
					html.push('<td style="width:60px">' + rdata[x].gy + '</td>');
					html.push('<td style="width:60px">' + rdata[x].frequency + '</td>');
					html.push('<td style="width:130px">' + (rdata[x].stop_date_time == null ? "":rdata[x].stop_date_time) + '</td>');
					html.push('<td style="width:120px">' + (rdata[x].beizhu ==null ?"":rdata[x].beizhu) + '</td>');
					html.push('<td style="width:80px">' + rdata[x].dept_name + '</td>');
					html.push('<td style="width:80px">' + rdata[x].kz_doctor + '</td>');
					html.push('<td style="width:60px">' + (rdata[x].jixing == null ?"":rdata[x].jixing) + '</td>');
					html.push('<td style="width:60px">' + (rdata[x].property_toxi == null ? "":rdata[x].property_toxi) + '</td>');
					html.push('</tr>');
				}
			}
			$("#showdrug_table tbody").append(html.join(''));
		},function(e){},{async:false,remark:false});
	}
	
	$('.nav nav-tabs li a').click(function(){
		alert(1)
	})
	
	//患者信息点击
	//function info_tabclick(){
		//检查明细
		$("#jiancha tbody tr").click(function(){
			$(this).css("background-color","#c8ecc9").siblings().css("background-color","");
			d = $(this).attr("data-id");
			var html = [];
			$.call("ipc.patient.getExamDetail", {exam_no: d}, function(rtn){
				$("#jcdetail tbody").empty();
				if(rtn && rtn.data.length > 0){
					for(v in rtn.data){
						html.push('<tr >');
						if(rtn.data[v].description.indexOf("http://") > -1){
							html.push('<td width="300px"><a onclick="heartmap(\''+rtn.data[v].use_image+'\')">'+rtn.data[v].description+'<a></td>');
						}else{
							html.push('<td width="300px">'+rtn.data[v].description+'</td>');
						}
						html.push('<td width="100px">'+(rtn.data[v].exam_item == null ? "":rtn.data[v].exam_item)+'</td>');
						html.push('<td width="60px">'+rtn.data[v].is_abnormal+'</td>');
						html.push('<td width="100px">'+rtn.data[v].impression+'</td>');
						html.push('<td width="100px">'+rtn.data[v].recommendation+'</td>');
						html.push('</tr>');
					}
				}else{
					html.push('无数据');
				}
				$("#jcdetail tbody").append(html.join(''));
					syncMove('jc_thead', 'jc_tbody'); 
			});
		});
		
		//检验明细
		$("#jianyan tbody tr").click(function(){
			$(this).css("background-color","#c8ecc9").siblings().css("background-color","");
			d = $(this).attr("data-id");
			var html = [];
			$.call("ipc.patient.queryRequestenDetail", {test_no: d}, function(rtn){
				$("#jydetail tbody").empty();
				if(rtn && rtn.data.length > 0){
					for(v in rtn.data){
						html.push('<tr >');
						if(rtn.data[v].tag == '↑'){
							html.push('<td width="20px"><i class="fa fa-long-arrow-up fali" style="color:red"></i></td>');
						}else if(rtn.data[v].tag == '↓'){
							html.push('<td width="20px"><i class="fa fa-long-arrow-down fali" style="color:red"></i></td>');
						}else{
							html.push('<td width="20px"></td>');
						}
						html.push('<td width="170px">'+rtn.data[v].report_item_name+'</td>');
						html.push('<td width="60px">'+rtn.data[v].result+'</td>');
						html.push('<td width="60px">'+rtn.data[v].units+'</td>');
						html.push('<td width="80px">'+rtn.data[v].print_context+'</td>');
						html.push('<td width="60px">'+rtn.data[v].abnormal_indicator+'</td>');
						html.push('</tr>');
					}
				}else{
					html.push('无数据');
				}
				$("#jydetail tbody").append(html.join(''));
				 syncMove('jy_detail_thead', 'jy_detail_tbody'); 
			});
		});
		
		//体征明细
		$("#tizheng tbody tr").click(function(){
			$(this).css("background-color","#c8ecc9").siblings().css("background-color","");
			d = $(this).attr("data-id");
			var datas={
					exam_no: d,
					patient_id: '${param.patient_id}',
					visit_id: '${param.visit_id}'
				};
			var html = [];
			$.call("ipc.patient.queryVitalDetail", datas, function(rtn){
				$("#tzdetail tbody").empty();
				if(rtn && rtn.data.length > 0){
					for(v in rtn.data){
						html.push('<tr >');
						html.push('<td width="130px">'+rtn.data[v].recording_date+' '+rtn.data[v].time_point+'</td>');
						//html.push('<td>'+rtn.data[v].time_point+'</td>');
						html.push('<td>'+rtn.data[v].vital_signs+'</td>');
						html.push('<td width="100px">'+rtn.data[v].vital_signs_values+'</td>');
						html.push('<td width="100px">'+rtn.data[v].units+'</td>');
						html.push('</tr>');
					}
				}else{
					html.push('无数据');
				}
				$("#tzdetail tbody").append(html.join(''));
			});
		});
		
		//手术明细
		$("#shoushu tbody tr").click(function(){
			$(this).css("background-color","#c8ecc9").siblings().css("background-color","");
			d = $(this).attr("data-id");
			var datas={
					oper_id: d,
					patient_id: '${param.patient_id}',
					visit_id: '${param.visit_id}'
				};
			var html = [];
			$.call("ipc.patient.queryOperDetil", datas, function(rtn){
				$("#ssdetail tbody").empty();
				if(rtn && rtn.data.length > 0){
					for(v in rtn.data){
						html.push('<tr >');
						html.push('<td>'+rtn.data[v].oper_id+'</td>');
						html.push('<td>'+rtn.data[v].operation_no+'</td>');
						html.push('<td>'+rtn.data[v].operation+'</td>');
						html.push('<td>'+rtn.data[v].operation_name+'</td>');
						html.push('<td>'+rtn.data[v].operation_scale+'</td>');
						html.push('<td>'+rtn.data[v].woundgrade_name+'</td>');
						html.push('</tr>');
					}
				}else{
					html.push('无数据');
				}
				$("#ssdetail tbody").append(html.join(''));
			});
		});
	//}
	
	function queryInfos(tid){
		var data={
			/* 	patient_id: '428265',
				visit_id: '2' */
			 	patient_id: '${param.patient_id}',
				visit_id: '${param.visit_id}' 
		};
		$("#"+tid).params(data);
		$("#"+tid).refresh();
	}
	
	//保存点评
	function saveResult(){
		if(comment_way == '2'){
			if($('#choose_com').html()){
				save_comment_mx();
			}else{
				var has_comment_content = false;
				$.call("ipc.commentflow.hasCommentContent",{"sample_id": '${param.sample_id}',"data_choose":is_checked_comment_result},function(rtn){
					if(rtn > 0){
						has_comment_content = true;
					}
				},function(e){},{async:false});
				if(has_comment_content){
					$.message("需要选择快捷点评规则！");
				}else{
					if($('#comment_content_id').val()){
						save_comment_mx();
					}else{
						$.message("无快捷点评规则！需要填写意见!");
					}
				}
			}
		}else{
			save_comment_mx();
		}
	}
	
	function save_comment_mx(){
		//获取order_no的个数
		var s = $('#bx_table tbody tr');
		//var order_no_set = new Set();
		var sample_orders_id_set = new Set();
		//var p_group_id_set = new Set();
		$('#bx_table tbody tr').each(function (i){
			var tr_this = $(this);
			sample_orders_id_set.add(tr_this.attr('data-sample_orders_id'));
			//order_no_set.add(tr_this.attr('data-id'));
			//p_group_id_set.add(tr_this.attr('data-group_id'));
		});
		
		var data= {} ;
		data.check_gx = _code;
		data.comment_content = $("#comment_content_id").val();
		data.comment_result = is_checked_comment_result;//是否合理;
		//data.p_key = selectData.group_id;
		//data.order_no = selectData.order_no;
		data.sample_id = '${param.sample_id}';
		data.sample_patients_id = selectData.sample_patients_id;
		//data.sample_orders_id = selectData.sample_orders_id;
		//data.visit_id = '${param.visit_id}';
		//data.patient_id = '${param.patient_id}';
		//data.order_no_set = Array.from(order_no_set);
		data.sample_orders_id_set = Array.from(sample_orders_id_set);
		//data.p_group_id_set = Array.from(p_group_id_set);
		//console.log(data);
		$.call("ipc.comment.saveCommentR", {"data":$.toJSON(data)}, function(rtn){
			refrechMain();
		},function(e){},{async:false,remark:false});
		//清理点评界面
		$("#bx_table tbody").empty();
		_code = [];
		is_checked_comment_result = 1;
		$('#choose_com').empty();
		$('#comment_content_id').val('');
		$('#comment_content_id').empty();
		if(comment_way == '1'){
			$('[data-choose = 0]').css({'background-color':'#3498db !important','color':'white'}).siblings().css({'background-color':'','color' : ''});
			$('[data-choose = 0]').find('i').text('●');
			$('[data-choose = 0]').siblings().find('i').text('○');
			is_checked_comment_result = 0;
		}else if(comment_way == '2'){
			$('[data-choose = 1]').css({'background-color':'#3498db !important','color':'white'}).siblings().css({'background-color':'','color' : ''});
			$('[data-choose = 1]').find('i').text('●');
			$('[data-choose = 1]').siblings().find('i').text('○');
			is_checked_comment_result = 1;
			$('[name="system_code"]').attr('params','{"sample_id":"'+${param.sample_id}+'","data_choose":"'+is_checked_comment_result+'"}');
			$('[name="system_code"]').params_autocomplete({"sample_id":${param.sample_id},"data_choose":is_checked_comment_result});
		}
		$('#zhaoz').css('display', 'block');
	}
	
	function finishOne(){
		var djbh = '${param.djbh}';
		//var is_next = $("#funnext").getData().tonext[0];
		var is_next = $("#tonext_id").prop("checked");
		if(is_next == true){
			is_next = 1;
		}else{
			is_next = 0;
		}
		var sample_id = '${param.sample_id}';
		var patient_id = '${param.patient_id}';
		var sample_pid = '${param.sample_pid}'
		var visit_id= '${param.visit_id}';
		$.call("ipc.commentflow.finishOne", {sample_id: sample_id, patient_id: patient_id, sample_pid: sample_pid}, function(rtn){
			if(rtn.order == 0){
				if(is_next == 1){
					//alert("继续下一单");
					//查询下一单信息 判断是否为空
					$.call("ipc.commentflow.queryNextPat", {djbh: djbh}, function(rtn){
						if(rtn.nextpat == 0){
							$.closeModal(true);
						}else{
							location.href = "/w/ipc/commentflow/detail.html?djbh="+djbh+
							"&sample_id="+rtn.nextpat[0].sample_id+
							"&patient_id="+rtn.nextpat[0].patient_id+
							"&sample_pid="+rtn.nextpat[0].id+
							"&visit_id="+rtn.nextpat[0].visit_id+
							"&notcnum="+rtn.nextpat.length;
						}
					});
				}else{
					$.closeModal(true);
				}
			}else{
				if(comment_way == '1'){
					$.confirm("该患者有未点评的医嘱/处方，将默认为：【合理】",function callback(e){
						if(e==true){
							var dataup = {
								sample_id: sample_id,
								patient_id: patient_id,
								visit_id: visit_id,
								sample_pid: sample_pid,
								comment_result: '1',
								comment_content: ''
							};
							$.call("ipc.comment.finishMRHL", dataup, function(rtn){
								if(is_next == 1){
									//alert("继续下一单");
									//查询下一单信息 判断是否为空
									$.call("ipc.commentflow.queryNextPat", {djbh: djbh}, function(rtn){
										//console.log(rtn);
										if(rtn.nextpat == 0){
											$.closeModal(true);
										}else{
											location.href = "/w/ipc/commentflow/detail.html?djbh="+djbh+
											"&sample_id="+rtn.nextpat[0].sample_id+
											"&patient_id="+rtn.nextpat[0].patient_id+
											"&sample_pid="+rtn.nextpat[0].id+
											"&visit_id="+rtn.nextpat[0].visit_id+
											"&notcnum="+rtn.nextpat.length;
										}
									});
								}
							});
						}
					});
				}else if(comment_way == '2'){
					$.message("该患者有未点评的医嘱/处方！专项点评必须点评！");
				}
			}
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
		event.stopPropagation();
	}
	
	window.onload = function(){
		setTimeout(startclick,1000);
	}
	function startclick(){
		$("#_t1_row_1").trigger('click');
	}
	
	function heartmap(url){
		 layer.open({
			  type: 2,
			  title: "心电图",
			  area: ['847px', '610px'], //宽高
			  content: url
		}); 
	}
	
function setHL(check_result_info_id,_this,auto_audit_id,auto_audit_level){
		$.modal("/w/ipc/comment/pass.html", "审查结果调整", {
			height: "470px",
			width: "515px",
			maxmin: false,
			"check_result_info_id":check_result_info_id, 
			user_name: userinfo.name,
			auto_audit_id: auto_audit_id,
			shenc_change_level: auto_audit_level,
			callback : function(rtn) {
				if (rtn>0) {
					
				}	
		    }
		});
	}
	
	function caseHistory(){
		var patient_id = '${pat.patient_id}';
		var d_type = ${empty $return.patient.d_type ? 0:$return.patient.d_type};
		var url = 'http://168.168.170.50:18002/winsso/c/00/0/2/'+patient_id+
		'/'+d_type+'/0/0/0/mzysclient';
		layer.open({
			  type: 2,
			  title: "电子病历",
			  area: ['90%', '90%'], //宽高
			  content: url
		}); 
	}
	
	function delete_choose(){
		$('#bx_table tbody tr').dblclick(function(){
			var tr_class ='.' + $(this).attr('class');
			$(tr_class).data('choose' , false);
			$('#showdrug_table tbody '+tr_class).css('background-color', '');
			$('#bx_table tbody '+tr_class).remove();
			var is_nochoose = $('#bx_table tbody tr');
			if(is_nochoose.length == 0){
				$('#zhaoz').css('display', 'block')
			}
		});
	}
	
	$("#checkandcomment tbody tr").click(function(){
		$(this).css("background-color","#c8ecc9").siblings().css("background-color","");
		var p_keys = $(this).attr("data_related_drugs_pkey");
		var level = $(this).attr("data_check_result_state");
		/* var data_related_drugs_show = $(this).attr("data_related_drugs_show");
		var data_doctor_advice = $(this).attr("data_doctor_advice");
		var data_yaoshi_audit_time = $(this).attr("data_yaoshi_audit_time");
		var data_yaoshi_name = $(this).attr("data_yaoshi_name");
		var data_sys_check_level_name = $(this).attr("data_sys_check_level_name");
		var data_description = $(this).attr("data_description");
		var data_reference = $(this).attr("data_reference");
		var data_sort_name = $(this).attr("data_sort_name"); */
		var datas = {
			patient_id: '${param.patient_id}',
			visit_id: '${param.visit_id}',
			order_no: $(this).attr("data_order_no"),
			p_keys: p_keys.split(','),
			auto_audit_id: $(this).attr("data_auto_audit_id"),
			group_id: $(this).attr("data_group_id")
		}
		var html = [];
		var html2 = [];
		$(".detail_result").empty();
		$("#sc_realinfo tbody").empty();
		html.push('<div><a href="javascript:void(0)" onclick="setHL('+"'"+$(this).attr("data_check_result_info_id")+"'"+',this'+",'"+$(this).attr("data_auto_audit_id")+"',"+$(this).attr("data_auto_audit_level")+');" class="sethl">审查结果调整</a></div>');
		html.push('<div style="color: #aa0c0c"><span style="color: #aa0c0c">问题药品：</span>'+$(this).attr("data_related_drugs_show")+'</div>');
		html.push('<div><span>医生使用理由：</span><font style="color: #af0000">'+$(this).attr("data_doctor_advice")+'</font>');
		html.push('</div>');
		html.push('<div style="color: #aa0c0c"><span class="scp" style="color: #aa0c0c">');
		html.push('前置审方结果：'+$(this).attr("data_yaoshi_advice")+'<font style="color:#929292">'+'【'+$(this).attr("data_yaoshi_audit_time")+'　'+$(this).attr("data_yaoshi_name")+'】</font>');
		html.push('</span></div>');
		html.push('<div><span>审查类型：</span>');
		if(level=='Y'){
			html.push('<span class="rstate1 fa fa-check-circle-o" title="通过级别问题" ></span>');
		}else if(level=='N'){
			html.push('<span class="rstate2 icon-ban"  title="拦截级别问题"></span>');
		}else if(level=='T'){
			html.push('<span class="rstate3 fa fa-warning (alias)"  title="审查级别问题"></span>');
		}
		html.push($(this).attr("data_sort_name")+'</div>');
		html.push('<div><span>严重级别：</span>'+$(this).attr("data_sys_check_level_name")+'</div>');
		html.push('<div><span>审查结果：</span>'+$(this).attr("data_description")+'</div>');
		html.push('<div><span>参考文献：</span>'+$(this).attr("data_reference")+'</div>');
		$(".detail_result").append(html.join(''));
		$.call("ipc.comment.queryRealYizu", datas, function(rtn){
			if(rtn.realyizu){
				var v = rtn.realyizu;
				for(i=0;i<rtn.realyizu.length;i++){
					html2.push('<tr>');
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
					html2.push('<td width="60px">'+v[i].order_class_show+'</td>');
					if(v[i].tag == '┍' || v[i].tag == '﹣'){
						html2.push('<td width="130px">'+v[i].enter_date_time+'</td>');
						html2.push('<td width="130px">'+v[i].stop_date_time+'</td>');
					}else{
						html2.push('<td width="130px"></td>');
						html2.push('<td width="130px"></td>');
					}
					
					html2.push('<td width="300px" class="is_hiddenmore" title="'+v[i].order_text+'"><a onclick="yaopin(\''+v[i].order_code+'\')">'+v[i].order_text+'</a></td>');
					/* html2.push('<td width="60px">'+v[i].jixing+'</td>'); */
					html2.push('<td width="60px">'+parseFloat(v[i].dosage)+'</td>');
					html2.push('<td width="60px">'+v[i].dosage_units+'</td>');
					html2.push('<td width="70px">'+v[i].administration+'</td>');
					html2.push('<td width="60px">'+v[i].frequency+'</td>');
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
			$("#sc_realinfo tbody").append(html2.join(''));
		});
		
	});
	
	function shenc(){
		$('#bom_zong').show();
		$('#shenc').show();
		$('#dianp').hide();
		$("#shenc").attr("class","active");
		$("#dianp").attr("class","");
		$("#commentright").css("display","none");
		$("#tab2").css("display","block");
	}
	
	function dianp(){
		$('#bom_zong').show();
		$('#shenc').hide();
		$('#dianp').show();
		$("#dianp").attr("class","active");
		$("#shenc").attr("class","");
		$("#commentright").css("display","block");
		$("#tab2").css("display","none");
		$("#checkandcomment tbody tr:first").click();
		//适配高度
		$('.tbody_Div_shenc').css("height",$('.card-tabs-stack.graygreen').height()-$('.detail_result').height()-38);
		//$("#quesdiv_box").empty();
	}
	
	shenc();
	$('[class="nav nav-tabs"] li:first a').click(function(){
		shenc();
	});
	
	$('[class="nav nav-tabs"] li:nth-child(2) a').click(function(){
		dianp();
	});
	
	$('[class="nav nav-tabs"] li:nth-child(2)').nextAll().click(function(){
		$('#bom_zong').hide();
	});
	
	//点评细则删除
	function deleteQuestion(_this){
		var del_code = $(_this).attr('data_id');
		_code.splice($.inArray(del_code,_code),1);//删除数组元素
		$(_this).parent().remove();
	}
</script>