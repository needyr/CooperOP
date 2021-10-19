<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>

<s:page title="点评细则查看" disloggedin="true">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/ipc/css/comment.css" >
 	<div class="col-md-9">
 			<div style="margin-left: 40px;z-index=99">
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
			<div id="btn_ss">︾ </div>
		<s:row>
		<s:tabpanel >
			<s:table id="datatable" height="250" active="true" label="点评处方(医嘱)" autoload="false" action="ipc.commentflow.queryOrders" sort="true" select="single">
				<s:toolbar>
					<%-- <s:button label="药品说明书" onclick="yaopin();" icon=""></s:button> --%>
				</s:toolbar>
				<s:table.fields>
					<s:table.field name="comment_result" label="警" datatype="script" width="20px">
						var comt = record.comment_result;
						if(comt == '0'){
							return '<font style="color:red;font-size:20px">×</font>';
						}else if(comt == '1'){
							return '<font style="color:green;font-size:20px">√</font>';
						}else if(comt == '2'){
							return '<font style="color:orange;font-size:20px">○</font>';
						}else{
							return '';
						}
					</s:table.field>
					<s:table.field name="sys_check_level_name" label="严重程度" width="60px"></s:table.field>
				    <s:table.field name="zu" label="组" ></s:table.field>
					<s:table.field name="lc" label="长/临" datatype="script" width="30px">
						var cl=record.repeat_indicator;
						if(cl=='0'){
							return '临';
						}else if(cl=='1'){
							return '长';
						}
					</s:table.field>
					<s:table.field name="yp" label="类别" width="60px"></s:table.field>
					<s:table.field name="stime" label="下达时间" width="130px"></s:table.field>
					<s:table.field name="order_text_mx" label="药品名称" datatype="script" width="200px">
						var is_gwyp=record.is_gwyp;
						if(is_gwyp == '是'){
							return '<a onclick="yaopin('+record.order_code+');"><span class="gwyp" title="高危药品">高危</span>'+ record.order_text+'</a>';
						}else{
							return '<a onclick="yaopin('+record.order_code+');">'+record.order_text+'</a>';
						}
					</s:table.field>
					<s:table.field name="gy" label="给药方式"  width="60px"></s:table.field>
					<s:table.field name="jixing" label="剂型" width="60px"></s:table.field>
					<s:table.field name="dosage" label="剂量" datatype="script" width="60px">
						var x=record.dosage;
						return parseFloat(x);
					</s:table.field>
					<s:table.field name="dosage_units" label="单位" width="60px"></s:table.field>
					<s:table.field name="frequency" label="频次" width="60px"></s:table.field>
					<s:table.field name="stop_date_time" label="停嘱时间" width="130px"></s:table.field>
					<s:table.field name="dept_name" label="开嘱科室" width="80px"></s:table.field>
					<s:table.field name="kz_doctor" label="开嘱医生" width="80px"></s:table.field>
					<s:table.field name="property_toxi" label="毒理分类" width="60px"></s:table.field>
					
				</s:table.fields>
			</s:table>
			
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
									<th style="width:110px">患者姓名</th>
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
									data_yaoshi_audit_time="${cac.yaoshi_audit_time}">
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
									<td>${cac.related_drugs_show}</td>
									<%-- <td>${cac.d_type}${cac.p_type}</td> --%>
									<td style="width:110px">${cac.patient_name}</td>
									<td style="width:150px">${cac.yaoshi_audit_time}</td>
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
						<c:forEach items="${enjoin}" var="d">
							<tr>
								<td width="20px">${d.tag}</td>
								<td width="40px">${empty d.repeat_indicator?'': (d.repeat_indicator eq '0' ? '临':'长')}</td>
								<td width="60px">${d.order_class_show}</td>
								<td width="130px">${d.enter_date_time}</td>
								<td width="200px">${d.order_text}</td>
								<td width="60px">${d.dosage2}</td>
								<td width="60px">${d.dosage_units}</td>
								<td width="60px">${d.administration}</td>
								<td width="60px">${d.frequence}</td>
								<td width="130px">${d.start_date_time}</td>
								<td width="60px">${d.continue_day}</td>
								<td width="130px">${d.stop_date_time}</td>
								<td width="60px">${d.p_type eq '1' ? '医嘱':'处方'}</td>
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
									<!-- <th width="120px">申请号</th> -->
									<th width="200px">检查所见</th>
									<th width="100px">检查项目</th>
									<th width="60px">阴阳性</th>
									<th width="100px">印象</th>
									<th width="100px">建议</th>
									
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
									<td width="200px">${d.item_name}</td>
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
				<a class="active" id="shenc" onclick="shenc();">已选医嘱/处方</a>
				<c:if test="${not empty $return.checkandcommentdetail}">
				<a class="" id="dianp" onclick="dianp();">审查详情</a>
				</c:if>
				</div>
				<div class="card-tabs-stack graygreen">
				<div id="commentright" style="display: none;">
					<div class="detail_result">
						
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
										<th width="300px">药品名称</th>
										<th width="70px">给药方式</th>
										<!-- <th width="60px">剂型</th> -->
										<th width="60px">剂量</th>
										<th width="60px">单位</th>
										<th width="60px">频次</th>
										<th width="130px">停嘱时间</th>
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
					<div class="quesdiv" id="quesdiv_box">
						</div>
				</div>
				</div>
				</div>
		</s:row>
 	</div>
 	<div class="col-md-3" style="border: 1px #cccccc dashed;" id="dpdiv">
 		<p class="ptitle">普通点评</p>
 		<div class="sr">
 			<div class="dp_mb">
 				<span class="input_titles titlw">点评结果</span>
	 			<div id="comment_result_ne" style="border: 1px solid #cbcbcb;font-weight: 600;height:30px">
	 			</div>
 			</div>
 			
 			<div class="dp_mb">
 				<span class="input_titles titlw">点评理由</span>
	 			<div id="comment_reason">
	 				
	 			</div>
 			</div>
 			
 			<div class="dp_mb">
	 				<span class="input_titles titlw">存在的问题</span>
					<div id="czdwt" style="border: 1px solid #cccccc;padding: 5px;">
					</div>
 			</div>
		</div>
 	</div>
		
</s:page>
<script type="text/javascript">
	$("input").attr("disabled","disabled");
	var selectData={};
	//患者信息下拉
	var box = document.getElementById("xiala")
    var btn = document.getElementById("btn_ss")
	var real_height=box.offsetHeight+10;
	$('#xiala').hide();
	$('#btn_ss').text('︾ ');
	//适配一些DIV高度
	var left_bom_height=$(window).height() - real_height - 42;
	//$('#bom_zong').css('height',$(window).height() - 540);
	$('.card-tabs-stack.graygreen').css('height',$(window).height() - 430);
	$('.tbody_Div').css('height',$(window).height() - 140);
	btn.onclick = function() {
        if (box.offsetTop == 26) {
        	spdiv();
        } else {
        	box.style['margin-top'] = 0 + "px";
        	$('#xiala').show(500);
        	//left_bom_height=$(window).height() - real_height - 42;
        	$('#btn_ss').text('︽');
        }
        //$(".quesdiv").css("height",left_bom_height-340-30+"px");
    }
	$("input[checked='checked']").parents(".checked").parents(".checker").parents("label.checkbox-inline").css("color","#da4242");
	$("#sres").change(function(){
		var s = $("#formwrite").getData().comment_result;
		if(s == 1){
			$("input").attr("disabled","disabled");
			$("[name='comment_result']").removeAttr("disabled");
			$("[name='tonext']").removeAttr("disabled");
			$("#bgfcf").setData([0]);
			$("#bgfcf2").setData([0]);
			$("#bgfcf3").setData([0]);
		}else{
			$("input").removeAttr("disabled"," ");
		}
	});
	
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
	
	function spdiv(){
		box.style['margin-top'] = -real_height+15 + "px";
		$('#xiala').hide(500);
		$('#btn_ss').text('︾ ');
	}
	
	$(function(){
		$("#datatable_length").hide();
		$("#datatable_paginate").hide();
		$("#datatable_info").hide();
		
		syncMove('lsdiv_thead', 'lsdiv_tbody');
		syncMove('jc_master_thead', 'jc_mater_tbody');
		syncMove('jy_thead', 'jy_tbody');
		syncMove('comment_top_thead', 'comment_top_tbody');
		syncMove('comment_search_thead', 'comment_search_tbody');
		
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
		
		//默认点选第一个
		$("#jiancha tbody tr").eq(0).click();
		$("#jianyan tbody tr").eq(0).click();
		$("#tizheng tbody tr").eq(0).click();
		$("#shoushu tbody tr").eq(0).click();
		
		query();
		$("#czdwt").css("height",$(window).height()-320);
		$("#datatable").find("tbody").on("click", "tr", function() {
			$("label.checkbox-inline").css("color","black");
			var getSelected = $("#datatable").getSelected()[0];
			selectData = getSelected.data;
			var ischeck=$(".datadataTables_select").attr("checked");
			$("#dpdiv").css("display","block");
			console.log(selectData);
			var sdata = {
				order_no: selectData.order_no,
				group_id: selectData.group_id,
				sample_orders_id: selectData.order_result_id,
				visit_id: '${param.visit_id}',
				patient_id: '${param.patient_id}'
			};
			$.call("ipc.commentflow.getQuestions",sdata,function(rtn){
				 $(".quesdiv").empty();
				 var data=rtn.data;
				 if(data && data.length >0){
					 var html=[];
						for(i=0;i<data.length;i++){
							html.push(' <div class="quesdetail">');
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
								html.push('<span class="rstate3 fa fa-warning (alias)"  title="提示级别问题"></span>');
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
						} 
						$(".quesdiv").append(html.join(''));
				 }
			});
			
			//曾经的点评信息
			var gxdata={
					order_no: selectData.order_no,
					group_id: selectData.group_id,
					sample_orders_id: selectData.order_result_id,
					visit_id: '${param.visit_id}',
					patient_id: '${param.patient_id}',
					sample_id: '${param.sample_id}'	,
					sample_patients_id: selectData.sample_patients_id
			};
			$("#czdwt").empty();
			$("#comment_result_ne").empty();
			var wthtml = [];
			$.call("ipc.comment.commentHistory",gxdata,function(rtn){
				wthtml.push('<ur>');
				if(rtn.data && rtn.data.length > 0){
					for(i=0;i<rtn.data.length;i++){
						wthtml.push('<li title="'+rtn.data[i].comment_name+'">');
						wthtml.push(rtn.data[i].comment_name);
						wthtml.push('</li>');
					} 
					wthtml.push('</ur>');
					$("#comment_reason").text(rtn.data[0].comment_content == null ? "未填写点评理由":rtn.data[0].comment_content);
					var comment_result_ne = rtn.data[0].comment_result;
					if(comment_result_ne == '0')
						comment_result_ne = '<span class="resu1">不合理</span>';
					else if (comment_result_ne == '1')
						comment_result_ne = '<span class="resu2">合理</span>';
					else if (comment_result_ne == '2')
						comment_result_ne = '<span class="resu3">争议</span>';
					else
						comment_result_ne = '<span>未点评</span>';
					$("#comment_result_ne").append(comment_result_ne);
					$("#czdwt").append(wthtml.join(''));
					$("input[checked='checked']").parents(".checked").parents(".checker").parents("label.checkbox-inline").css("color","#da4242");
					
				}
				
			});
			
		}); 
	});

	function query(){
		var data={
				sample_id: '${param.sample_id}',
				patient_id: '${param.patient_id}'
		};
		$("#datatable").params(data);
		$("#datatable").refresh();
	}
	
	function yaopin(event){
		var drugcode = selectData.order_code;
		if(drugcode){
			//打开药品说明书
			$.modal("/w/ipc/auditresult/instruction.html?his_drug_code="+drugcode,"查看药品说明书",{
		        width:"90%",
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
	
	function caseHistory(){
		var patient_id = '${pat.patient_id}';
	/* 	var doctor_no = userinfo.no;
		if(!doctor_no){
			doctor_no= '${$return.user_no}';
		} */
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
	
	$("#checkandcomment tbody tr").click(function(){
		$(this).css("background-color","#c8ecc9").siblings().css("background-color","");
		var p_keys = $(this).attr("data_related_drugs_pkey");
		var level = $(this).attr("data_check_result_state");
		var datas = {
			patient_id: '${param.patient_id}',
			visit_id: '${param.visit_id}',
			order_no: $(this).attr("data_order_no"),
			p_keys: p_keys.split(',')
		}
		var html = [];
		var html2 = [];
		$(".detail_result").empty();
		$("#sc_realinfo tbody").empty();
		html.push('<div style="color: #aa0c0c"><span style="color: #aa0c0c">问题药品:</span>'+$(this).attr("data_related_drugs_show")+'</div>');
		html.push('<div><span>医生使用理由:</span><font style="color: #af0000">'+$(this).attr("data_doctor_advice")+'</font>');
		html.push('</div>');
		html.push('<div style="color: #aa0c0c"><span class="scp" style="color: #aa0c0c">');
		html.push('前置审方结果：'+$(this).attr("data_yaoshi_advice")+'<font style="color:#929292">'+'【'+$(this).attr("data_yaoshi_audit_time")+'　'+$(this).attr("data_yaoshi_name")+'】</font>');
		html.push('</span></div>');
		html.push('<div><span>审查类型:</span>');
		if(level=='Y'){
			html.push('<span class="rstate1 fa fa-check-circle-o" title="通过级别问题" ></span>');
		}else if(level=='N'){
			html.push('<span class="rstate2 icon-ban"  title="拦截级别问题"></span>');
		}else if(level=='T'){
			html.push('<span class="rstate3 fa fa-warning (alias)"  title="审查级别问题"></span>');
		}
		html.push($(this).attr("data_sort_name")+'</div>');
		html.push('<div><span>严重级别:</span>'+$(this).attr("data_sys_check_level_name")+'</div>');
		html.push('<div><span>审查结果:</span>'+$(this).attr("data_description")+'</div>');
		html.push('<div><span>参考文献:</span>'+$(this).attr("data_reference")+'</div>');
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
					if(v[i].order_class_show){
						html2.push('<td width="60px">'+v[i].order_class_show+'</td>');
					}else{
						html2.push('<td width="60px"></td>');
					}
					if(v[i].tag == '┍'){
						html2.push('<td width="130px">'+v[i].enter_date_time+'</td>');
					}else{
						html2.push('<td width="130px"></td>');
					}
					html2.push('<td width="300px" class="is_hiddenmore" title="'+v[i].order_text+'"><a onclick="yaopin(\''+v[i].order_code+'\')">'+v[i].order_text+'</a></td>');
					html2.push('<td width="70px">'+v[i].administration+'</td>');
					/* html2.push('<td width="60px">'+v[i].jixing+'</td>'); */
					html2.push('<td width="60px">'+parseFloat(v[i].dosage)+'</td>');
					html2.push('<td width="60px">'+v[i].dosage_units+'</td>');
					html2.push('<td width="60px">'+v[i].frequency+'</td>');
					if(v[i].tag == '┍'){
						html2.push('<td width="130px">'+v[i].stop_date_time+'</td>');
					}else{
						html2.push('<td width="130px"></td>');
					}
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
		$('.tbody_Div_shenc').css("height",$('.card-tabs-stack.graygreen').height()-$('.detail_result').height()-40);
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
	
	function setHL(check_result_info_id,_this,auto_audit_id,auto_audit_level){
		$.modal("/w/ipc/comment/pass.html", "审查合理理由填写", {
			height: "280px",
			width: "515px",
			maxmin: false,
			"id":check_result_info_id, 
			user_name: userinfo.name,
			auto_audit_id: auto_audit_id,
			shenc_change_level: auto_audit_level,
			callback : function(rtn) {
				if (rtn>0) {
					
				}	
		    }
		});
	}
</script>