<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="患者审查详情" disloggedin="true">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/hospital_common/css/patientinfo.css">
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
			就诊序号：${pat.visit_id}　
			<a href="javascript:void(0)" onclick="caseHistory();" style="margin-left: 10px">【电子病历】</a>
		</b>
    	</p>
		</c:if>
	</div>
	 <div class="simpleinfo" id="xiala">
	 		<c:if test="${pat.d_type eq 1}">
	    		<span>住院号：${pat.patient_no}</span>
				<span>患者ID：${pat.patient_id}</span>
				<span>肌酐清除率：${empty pat.ccr? "无":pat.ccr} ${empty pat.ccr? "":" ml/min"}</span>
				<span>住院次数：${pat.visit_id}</span>
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
				<c:if test="${not empty $return.diagnosisgroup}">
					<p class="otherp">诊断：
			            <c:forEach items="${$return.diagnosisgroup}" var="diag" >
			            	${diag.diagnosis_desc}、
			            </c:forEach>
			         </p>
	            </c:if>
	            <c:if test="${not empty pat.alergy_drugs}">
	            	<p class="otherp">过敏：${pat.alergy_drugs}</p>
	            </c:if>
			</c:if>
			<c:if test="${pat.d_type ne 1}">
				<span>肌酐清除率：${empty pat.ccr? "无":pat.ccr} ${empty pat.ccr? "":" ml/min"}</span>
				<span>费别：${pat.charge_type}</span>
				<span>医保号：${pat.insurance_no}</span>
				<span>医保类别：${pat.insurance_type}</span>
				<span>入院科室：${pat.dep_in}</span>
				<span>入院日期：${pat.admission_datetime}</span>
				<span>出院科室：${pat.dep_discharge}</span>
				<span>出院日期：${pat.discharge_datetime}</span>
				<c:if test="${not empty $return.diagnosis}">
					<p class="otherp">诊断：
			            <c:forEach items="${$return.diagnosis}" var="diag" >
			            	${diag.diagnosis_desc}、
			            </c:forEach>
		            </p>
	            </c:if>
				<c:if test="${not empty pat.alergy_drugs}">
	            	<p class="otherp">过敏：${pat.alergy_drugs}</p>
	            </c:if>
			</c:if>
	</div>
	<div id="btn_ss" class="fa fa-angle-double-down showhide"></div>
	<s:tabpanel>
			<c:if test="${not empty $return.checkandcommentdetail}">
			<s:form active="true" label="审查(点评结果)">
				<s:row>
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
									<th class="tbth"></th>
								</tr>
							</thead>
						</table>
					</div>
					<div  class="tbody_Div_result" id="comment_top_tbody">
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
									data_check_result_info_id="${cac.check_result_info_id}"
									data_auto_audit_id="${cac.auto_audit_id}"
									data_auto_audit_level="${cac.auto_audit_level}"
									data_check_result_state="${cac.check_result_state}"
									data_yaoshi_audit_time="${cac.yaoshi_audit_time}"
									data_patient_id="${cac.patient_id}"
									data_visit_id="${cac.visit_id}"
									data_order_no="${cac.order_no}"
									data_group_id="${cac.group_id}"
									>
									<c:choose>
										<c:when test="${cac.sys_check_level_name eq '不合理'}">
											<td style="color:red;width:20px;font-size: 18px;" title="不合理">×</td>
										</c:when>
										<c:when test="${cac.sys_check_level_name eq '合理'}">
										<td style="color:green;width:20px;font-size: 18px;" title="合理">√</td>
										</c:when>
										<c:when test="${cac.sys_check_level_name eq '争议'}">
										<td style="color:orange;width:20px;font-size: 18px;" title="争议">O</td>
										</c:when>
										<c:when test="${empty cac.check_result_state}">
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
									<td style="width:150px">${cac.check_datetime}</td>
									<td>${cac.yxk_advice}</td>
									<td style="width:85px">${cac.audit_source_type}</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</s:row>
				<s:row>
					<div class="card-tabs-bar graygreen">
					<a class="active" id="shenc" onclick="shenc();">结果详情</a>
					<!-- <a class="" id="dianp" onclick="dianp();">点评结果详情</a> 放开加页签-->
					</div>
					<div class="card-tabs-stack graygreen">
						<div id="commentright" style="display: none;">
						</div>
						<div id="tab2" style="display: block;">
							<div>
							<div class="thead_Div" style="margin-top: 5px" id="comment_thead">
								<table class="mytable">
									<thead>
										<tr>
											<th width="60px">严重程度</th>
											<th width="20px">组</th>
											<th width="40px">长/临</th>
											<th width="40px">类别</th>
											<th width="130px">下达时间</th>
											<th width="300px">药品名称</th>
											<!-- <th width="60px">剂型</th> -->
											<th width="60px">剂量</th>
											<th width="60px">单位</th>
											<th width="70px">途径</th>
											<th width="60px">频次</th>
											<th width="350px">药品信息</th>
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
							<div  class="tbody_Div_detail" id="comment_tbody">
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
						<div class="detail_result">
							
						</div>
						</div>
					</div>
				</s:row>
			</s:form>
			</c:if>
			
			<c:if test="${empty $return.checkandcommentdetail}">
			<s:form  label="医嘱" active="true">
			<s:row>
				<div class="thead_Div" id="usedrug_thead" >
					<table class="mytable">
						<thead>
							<tr>
								<th width="30px">警</th>
								<th width="40px">点评</th>
								<th width="60px">严重程度</th>
								<th width="20px">组</th>
								<th width="40px">长/临</th>
								<th width="40px">类别</th>
								<th width="130px">下达时间</th>
								<th width="300px" >药品名称</th>
								<th width="60px">剂型</th>
								<th width="60px">剂量</th>
								<th width="60px">单位</th>
								<th width="70px">途径</th>
								<th width="60px">频次</th>
								<th width="130px">停嘱时间</th>
								<th width="120px">医生嘱托</th>
								<th width="100px">开嘱科室</th>
								<th width="70px">开嘱医生</th>
								<th width="60px">毒理分类</th>
								<th class="tbth"></th>
							</tr>
						</thead>
					</table>
				</div>
				<div class="tbody_Div" id="usedrug_tbody" >
					<table class="mytable" id="usedrug_t" >
						<tbody>
						<c:forEach items="${$return.usedrug}" var="v">
							<tr  data-id="${v.order_no}">
								<td width="30px">
								<c:choose>
									<c:when test="${v.is_check eq '是'}">
									<div class="hisicon" title="已前置审方">查 </div>
									</c:when>
									<c:when test="${v.is_comment eq '是'}">
									<div class="hisicon" title="已点评">评</div>
									</c:when>
									<c:when test="${v.is_pharmacist_check eq '是'}">
									<div class="hisicon" title="药师已审查">审</div>
									</c:when>
									<c:otherwise>
										<div> </div>
									</c:otherwise>
								</c:choose>
								</td>
								<c:choose>
									<c:when test="${v.comment_result eq '1'}">
										<td width="40px"><font style="color: green;font-weight: 600;">√</font></font></td>
									</c:when>
									<c:when test="${v.comment_result eq '0'}">
										<td width="40px"><font style="color: red;font-weight: 600;">×</font></font></td>
									</c:when>
									<c:when test="${v.comment_result eq '2'}">
										<td width="40px"><font style="color: orange;font-weight: 600;">O</font></font></td>
									</c:when>
									<c:otherwise>
										<td width="40px"></td>
									</c:otherwise>
								</c:choose>
								<td width="60px">${v.check_level_name}</td>
								<td width="20px">${v.tag}</td>
								<td width="40px">${empty v.repeat_indicator?'': (v.repeat_indicator eq '0' ? '临':'长')}</td>
								<td width="40px">${v.order_class}</td>
								<td width="130px">${v.enter_date_time}</td>
								<td width="300px" class="is_hiddenmore" title="${v.order_text}"><a onclick="yaopin('${v.order_code}')">${v.order_text}</a></td>
								<td width="60px">${v.jixing}</td>
								<td width="60px">${v.dosage2}</td>
								<td width="60px">${v.dosage_units}</td>
								<td width="70px">${v.administration}</td>
								<td width="60px">${v.frequency}</td>
								<td width="130px">${v.stop_date_time}</td>
								<td width="120px">${v.beizhu}</td>
								<td width="100px">${v.dept_name}</td>
								<td width="70px">${v.doctor}</td>
								<td width="60px">${v.property_toxi}</td>
							</tr>
						</c:forEach> 
						</tbody>
					</table>
				</div>
			</s:row>
			</s:form>
			</c:if>
			
			<c:if test="${not empty $return.checkandcommentdetail}">
			<s:form  label="医嘱" active="">
			<s:row>
				<div class="thead_Div" id="usedrug_thead" >
					<table class="mytable">
						<thead>
							<tr>
								<th width="30px">警</th>
								<th width="40px">点评</th>
								<th width="60px">严重程度</th>
								<th width="20px">组</th>
								<th width="40px">长/临</th>
								<th width="40px">类别</th>
								<th width="130px">下达时间</th>
								<th width="300px" >药品名称</th>
								<!-- <th width="60px">剂型</th> -->
								<th width="60px">剂量</th>
								<th width="60px">单位</th>
								<th width="70px">途径</th>
								<th width="60px">频次</th>
								<th width="130px">开始执行时间</th>
								<th width="60px">持续时间</th>
								<th width="130px">停嘱时间</th>
								<th width="120px">医生嘱托</th>
								<th width="100px">开嘱科室</th>
								<th width="70px">开嘱医生</th>
								<th width="60px">毒理分类</th>
								<th class="tbth"></th>
							</tr>
						</thead>
					</table>
				</div>
				<div class="tbody_Div" id="usedrug_tbody" >
					<table class="mytable" id="usedrug_t" >
						<tbody>
						<c:forEach items="${$return.usedrug}" var="v" >
							<tr  data-id="${v.order_no}">
								<td width="30px">
								<c:choose>
									<c:when test="${v.is_check eq '是'}">
									<div class="hisicon" title="已前置审方">查 </div>
									</c:when>
									<c:when test="${v.is_comment eq '是'}">
									<div class="hisicon" title="已点评">评</div>
									</c:when>
									<c:when test="${v.is_pharmacist_check eq '是'}">
									<div class="hisicon" title="药师已审查">审</div>
									</c:when>
									<c:otherwise>
										<div> </div>
									</c:otherwise>
								</c:choose>
								</td>
								<c:choose>
									<c:when test="${v.comment_result eq '1'}">
										<td width="40px"><font style="color: green;font-weight: 600;">√</font></font></td>
									</c:when>
									<c:when test="${v.comment_result eq '0'}">
										<td width="40px"><font style="color: red;font-weight: 600;">×</font></font></td>
									</c:when>
									<c:when test="${v.comment_result eq '2'}">
										<td width="40px"><font style="color: orange;font-weight: 600;">O</font></font></td>
									</c:when>
									<c:otherwise>
										<td width="40px"></td>
									</c:otherwise>
								</c:choose>
								<td width="60px">${v.check_level_name}</td>
								<td width="20px">${v.tag}</td>
								<td width="40px">${empty v.repeat_indicator?'': (v.repeat_indicator eq '0' ? '临':'长')}</td>
								<td width="40px">${v.order_class_show}</td>
								<td width="130px">${v.enter_date_time}</td>
								<td width="300px" class="is_hiddenmore" title="${v.order_text}"><a onclick="yaopin('${v.order_code}')">${v.order_text}</a></td>
								<%-- <td width="60px">${v.jixing}</td> --%>
								<td width="60px">${v.dosage2}</td>
								<td width="60px">${v.dosage_units}</td>
								<td width="70px">${v.administration}</td>
								<td width="60px">${v.frequency}</td>
								<td width="130px">${d.start_date_time}</td>
								<td width="60px">${d.continue_day}</td>
								<td width="130px">${v.stop_date_time}</td>
								<td width="120px">${v.beizhu}</td>
								<td width="100px">${v.dept_name}</td>
								<td width="70px">${v.doctor}</td>
								<td width="60px">${v.property_toxi}</td>
							</tr>
						</c:forEach> 
						</tbody>
					</table>
				</div>
			</s:row>
			</s:form>
			</c:if>
		
		<c:if test="${not empty diagnosis}" >
				<s:form label="诊断">
					<s:row>
						<div class="thead_Div">
							<table class="mytable" >
								<thead>
									<tr>
										<th>诊断类别</th>
										<th>诊断序号</th>
										<th>诊断</th>
										<th style="width: 150px">诊断日期</th>
										<th class="tbth"></th>
									</tr>
								</thead>
							</table>
						</div>
						<div  class="tbody_Div">
							<table class="mytable" id="zhenduan" >
								<tbody>
								<c:forEach items="${diagnosis}" var="d">
									<tr>
										<td>${d.diagnosisclass_name}</td>
										<td>${d.diagnosis_no}</td>
										<td>${d.diagnosis_desc}</td>
										<td style="width: 150px">${d.diagnosis_date}</td>
									</tr>
								</c:forEach>
								</tbody>
							</table>
						</div>
				</s:row>
			</s:form>
		</c:if>
			
		<c:if test="${not empty exams}">
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
											<th class="tbth"></th>
										</tr>
									</thead>
								</table>
							</div>
							<div class="tbody_Div" id="jc_master_tbody">
							<table class="mytable" id="jiancha">
							<tbody>
							<c:forEach items="${exams}" var="d">
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
									<th width="60px">阴阳性</th>
									<th width="120px">申请号</th>
									<th width="100px">检查项目</th>
									<th width="200px">检查所见</th>
									<th width="100px">印象</th>
									<th width="100px">建议</th>
									<th class="tbth"></th>
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
									<th width="200px">检验项目</th>
									<th width="60px">申请医生</th>
									<th width="130px">申请时间</th>
									<th width="130px">报告时间</th>
									<th class="tbth"></th>
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
										<th class="tbth"></th>
									</tr>
								</thead>
								<tbody>
									
								</tbody>
							</table>
						</div>
						<div class="tbody_Div" id="jy_detail_tbody">
							<table class="mytable" id="jydetail" >
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
										<th class="tbth"></th>
									</tr>
								</thead>
							</table>
						</div>
						
						<div class="tbody_Div">
							<table class="mytable" id="tzdetail">
								<tbody>
								
								</tbody>
							</table>
						</div>
					</div>
				</s:row>
			</s:form>
			</c:if>
		<s:form label="手术">
				<s:row>
					<div class="col-md-6">
						<s:table id="dtable5a"  autoload="false" action="hospital_common.additional.queryOperation" sort="true" select="single" limit="10" >
							<s:table.fields>
								<s:table.field name="oper_id" label="编号" datatype="String"></s:table.field>
								<s:table.field name="dept_stayed" label="所在科室代码" datatype="String"></s:table.field>
								<s:table.field name="diag_after_operation" label="术前诊断" datatype="String"></s:table.field>
								<s:table.field name="operation_scale" label="手术等级" datatype="String"></s:table.field>
								<s:table.field name="first_assistant" label="Ⅰ助" datatype="String"></s:table.field>
								<s:table.field name="dept" label="所在科室" datatype="String"></s:table.field>
								<s:table.field name="oper_dept" label="手术科室" datatype="String"></s:table.field>
								<s:table.field name="operator" label="手术操作人" datatype="String"></s:table.field>
								<s:table.field name="start_date_time" label="开始时间" datatype="String"></s:table.field>
								<s:table.field name="end_date_time" label="截止时间" datatype="String"></s:table.field>
							</s:table.fields>
						</s:table>
					</div>
					<div class="col-md-6">
						<s:table id="dtable5b"  autoload="false" action="hospital_common.additional.queryOperDetil" sort="true"  limit="10">
							<s:table.fields>
								<s:table.field name="oper_id" label="编号" datatype="String"></s:table.field>
								<s:table.field name="operation_no" label="手术编码" datatype="String"></s:table.field>
								<s:table.field name="operation" label="手术" datatype="String"></s:table.field>
								<s:table.field name="operation_name" label="手术名称" datatype="String"></s:table.field>
								<s:table.field name="operation_scale" label="手术规模" datatype="String"></s:table.field>
								<s:table.field name="woundgrade_name" label="创伤等级" datatype="String"></s:table.field>
							</s:table.fields>
						</s:table>
					</div>
				</s:row>
			</s:form>
			<s:form label="费用">
			<s:row>
			<div class="thead_Div" id="feiyong_thead">
				<table class="mytable" >
					<thead>
						<tr>
						<th style="width: 30px">组</th>
						<th style="width: 40px">长/临</th>
                 		<th style="width: 60px">类别</th>
                 		<th style="width: 140px">收费时间</th>
                 		<th style="width: 100px">项目编码</th>
                 		<th style="width: 200px">项目名称</th>
                 		<th style="width: 100px">规格</th>
                 		<th style="width: 60px">数量单位</th>
                 		<th style="width: 50px">单价/元</th>
                 		<th style="width: 60px">金额</th>
                 		<th style="width: 60px">途径</th>
                 		<th style="width: 60px">频次</th>
                 		<th style="width: 150px">科室</th>
                 		<th style="width: 60px">医生</th>
                 		<th style="width: 60px">护士</th>
                 		<th class="tbth"></th>
						</tr>
					</thead>
				</table>
			</div>
			<div  class="tbody_Div" id="feiyong_tbody">
				<table class="mytable" id="feiyong" >
					<tbody>
					</tbody>
				</table>
			</div>
		    </s:row>
			</s:form>
	</s:tabpanel>
			
</s:page>
<script type="text/javascript">
	//患者信息下拉
	var box = document.getElementById("xiala")
	var btn = document.getElementById("btn_ss")
	var real_height=box.offsetHeight;
	var shentu = $(window).height()-$('#comment_top_tbody').height()-210;
	//btn.style['top'] = real_height + "px";
	$('#xiala').hide();
	$('#btn_ss').attr('class','fa fa-angle-double-down');
	$('.card-tabs-stack.graygreen').css('height', $(window).height()-$('#comment_top_tbody').height() - 170);
	$('.tbody_Div_detail').css('height',shentu*0.4-20);
	$('.detail_result').css('height',shentu*0.6);
	//$('.detail_result').css('height',$(window).height() - 620);
	$('.tbody_Div').css('height',$(window).height() - 130);
	btn.onclick = function() {
	    if (box.offsetTop == 28) {
	    	spdiv();
	    } else {
	    	//box.style['margin-top'] = 0 + "px";
        	$('#xiala').show(500);
        	$('#btn_ss').css('margin-top',$('xiala').outerHeight()-20);
        	$('#btn_ss').attr('class','fa fa-angle-double-up');
	    }
	}
	
	function spdiv(){
		//box.style['margin-top'] = -real_height+15+120 + "px";
		$('#xiala').hide(500);
		$('#btn_ss').css('margin-top',0);
		$('#btn_ss').attr('class','fa fa-angle-double-down');
	}
	
	function syncMove(theadId, tbodyId){
		try{
		document.getElementById(tbodyId).onscroll=function(e) {
			document.getElementById(theadId).scrollLeft = document.getElementById(tbodyId).scrollLeft;
		}}catch(err){
			console.log('onscroll is error, don\'t worry ');
		}
	}
	syncMove('usedrug_thead', 'usedrug_tbody');
	syncMove('jc_master_thead', 'jc_master_tbody');
	syncMove('jy_thead', 'jy_tbody');
	syncMove('comment_thead', 'comment_tbody');
	syncMove('comment_top_thead', 'comment_top_tbody');
	syncMove('feiyong_thead', 'feiyong_tbody');
		//检查明细
		$("#jiancha tbody tr").click(function(){
			$(this).css("background-color","#c8ecc9").siblings().css("background-color","");
			d = $(this).attr("data-id");
			var html = [];
			$.call("hospital_common.additional.queryPatExam_Detail", {exam_no: d}, function(rtn){
				$("#jcdetail tbody").empty();
				if(rtn && rtn.data !=null){
					for(v in rtn.data){
						html.push('<tr >');
						html.push('<td width="60px">'+rtn.data[v].is_abnormal+'</td>');
						html.push('<td width="120px">'+rtn.data[v].exam_no+'</td>');
						html.push('<td width="100px">'+(rtn.data[v].exam_item == null ? "":rtn.data[v].exam_item)+'</td>');
						//html.push('<td>'+rtn.data[v].req_date_time+'</td>');
						if(rtn.data[v].description.indexOf("http://") > -1){
							html.push('<td width="200px"><a onclick="heartmap(\''+rtn.data[v].use_image+'\')">'+rtn.data[v].description+'<a></td>');
						}else{
							html.push('<td width="200px">'+rtn.data[v].description+'</td>');
						}
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
			$.call("hospital_common.additional.queryPatRequesten_Detail", {test_no: d}, function(rtn){
				$("#jydetail tbody").empty();
				if(rtn && rtn.data !=null){
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
						html.push('<td width="60px">'+(rtn.data[v].abnormal_indicator == null ? "":rtn.data[v].abnormal_indicator)+'</td>');
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
			$.call("hospital_common.additional.queryPatVital_Detail", datas, function(rtn){
				$("#tzdetail tbody").empty();
				if(rtn && rtn.data !=null){
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
		
		/* //审查流水
		$("#usedrug_t tbody tr").click(function(){
			$(this).css("background-color","#c8ecc9").siblings().css("background-color","");
			d = $(this).attr("data-id");
			var datas = {
				patient_id: '${param.patient_id}',
				visit_id: '${param.visit_id}',
				order_no: d
			}
			var html = [];
			var html2 = [];
			var html3 = [];
			$.call("hospital_common.additional.queryPatCheckRunInfo", datas, function(rtn){
				$("#check_run_info tbody").empty();
				$("#check_result").empty();
				//审查流水
				if(rtn && rtn.data.length > 0){
					for(v in rtn.data){
						html.push(' <tr>');
						html.push(' <td style="width: 160px; height: 30px">'+rtn.data[v].check_datetime+'</td>');
						html.push(' <td>'+rtn.data[v].check_results_message+'</td>');
						html.push(' </tr>');
					}
					$("#check_run_info tbody").append(html.join(''));
				}else{
					html.push('<font style="color:red;">无审查流水数据</font>');
					$("#check_run_info tbody").append(html.join(''));
				}
				
				//审查信息
				if(rtn && rtn.data_check.length > 0){
					for(v2 in rtn.data_check){
						html2.push(' <div class="quesdetail">');
						html2.push(' <p>');
						if(rtn.data_check[v2].check_result_state=='Y'){
							html2.push('<span class="rstate1 fa fa-check-circle-o" title="通过级别问题" color="red"></span>');
						}else if(rtn.data_check[v2].check_result_state=='N'){
							html2.push('<span class="rstate2 icon-ban"  title="拦截级别问题"></span>');
						}else if(rtn.data_check[v2].check_result_state=='T'){
							html2.push('<span class="rstate3 fa fa-warning (alias)"  title="审查级别问题"></span>');
						}
						html2.push(' 问题分类： '+rtn.data_check[v2].sort_name + '<font style="color:#bfafaf;margin-left:20px">来自：'+rtn.data_check[v2].check_datetime+' 【'+rtn.data_check[v2].audit_source_type+'】</font>');
						html2.push(' </p>');
						html2.push('<p>');
						if(rtn.data_check[v2].related_drugs_show){
							html2.push('<p>');
							html2.push('药品：' +rtn.data_check[v2].related_drugs_show);
							html2.push('</p>');
						}
						html2.push('严重程度：'+rtn.data_check[v2].sys_check_level_name +'【' +rtn.data_check[v2].star_level+'】');
						html2.push('</p>');
						html2.push('<p>');
						html2.push('审查类型：'+rtn.data_check[v2].description);
						html2.push('</p>');
						html2.push('<p style="color:#bfafaf">');
						html2.push(rtn.data_check[v2].reference == null ? "":"参考文献："+rtn.data_check[v2].reference);
						html2.push('</p>');
						html2.push('</div>');
					}
					$("#check_result").append(html2.join(''));
				}else{
					html2.push('<font style="color:red;">无审查数据</font>');
					$("#check_result").append(html2.join(''));
				}
				
				//点评信息
				$("#comment_result").empty();
				if(rtn && rtn.data_comment.length > 0){
				 	html3.push(' <p class="res_comp">点评结果：');
					if(rtn.data_comment[0].comment_result == 0 ){
						html3.push('<font style="font-size: 14px ;color: red">× 不通过</font>');
					}else if(rtn.data_comment[0].comment_result == 1){
						html3.push('<font style="font-size: 14px ;color: green">√ 通过</font>');
					}else if(rtn.data_comment[0].comment_result == 2){
						html3.push('<font style="font-size: 14px ;color: orange">O 争议</font>');
					}else{
						html3.push(' ');
					}
					html3.push('</p>');
					html3.push(' <ul>');
					for(v3 in rtn.data_comment){
						html3.push('<li>');
						if(rtn.data_comment[v3].comment_system_code != null){
							html3.push('【');
							html3.push(rtn.data_comment[v3].comment_system_code);
							html3.push('】');
						}
						html3.push(rtn.data_comment[v3].comment_name);
						html3.push('<font class="dpfj">[' + rtn.data_comment[v3].comment_datetime+'　' );
						html3.push( rtn.data_comment[v3].comment_username +'　'+ rtn.data_comment[v3].comment_source+':　'+rtn.data_comment[v3].comment_result_message+']</font>');
						html3.push('</li>');
					} 
					html3.push(' </ul>');
					$("#comment_result").append(html3.join(''));
				}else{
					html3.push('<font style="color:red;">无点评数据</font>');
					$("#comment_result").append(html3.join(''));
				}
				
			});
		}); */
		
		//默认点选第一个
		$("#jiancha tbody tr").eq(0).click();
		$("#jianyan tbody tr").eq(0).click();
		$("#tizheng tbody tr").eq(0).click();
		$("#shoushu tbody tr").eq(0).click();
		
		
	function heartmap(url){
		layer.open({
			type: 2,
			title: "心电图",
			area: ['90%', '90%'], //宽高
			content: url
		}); 
	}
	
	function caseHistory(){
		var patient_id = '${pat.patient_id}';
		/* var doctor_no = userinfo.no;
		if(!doctor_no){
			$.message('未登录，不予查看！');
		} */
		var d_type = ${empty $return.patient.d_type? 0:($return.patient.d_type eq 1?2:1)};
		var url = 'http://168.168.170.50:18002/winsso/c/00/0/'+d_type+'/'+patient_id+
		'/'+'${pat.visit_id}'+'/0/0/0/mzysclient';
		layer.open({
			  type: 2,
			  title: "电子病历",
			  area: ['90%', '95%'], //宽高
			  content: url
		}); 
	}
	
	function shenc(){
		$("#shenc").attr("class","active");
		$("#dianp").attr("class","");
		$("#commentright").css("display","none");
		$("#tab2").css("display","block");
	}
	
	function dianp(){
		$("#dianp").attr("class","active");
		$("#shenc").attr("class","");
		$("#commentright").css("display","block");
		$("#tab2").css("display","none");
	}
	
	$("#checkandcomment tbody tr").click(function(){
		$(this).css("background-color","#c8ecc9").siblings().css("background-color","");
		var p_keys = $(this).attr("data_related_drugs_pkey");
		var level = $(this).attr("data_check_result_state");
		var data_related_drugs_show = $(this).attr("data_related_drugs_show");
		var data_doctor_advice = $(this).attr("data_doctor_advice");
		var data_yaoshi_audit_time = $(this).attr("data_yaoshi_audit_time");
		var data_yaoshi_name = $(this).attr("data_yaoshi_name");
		var data_sys_check_level_name = $(this).attr("data_sys_check_level_name");
		var data_description = $(this).attr("data_description");
		var data_reference = $(this).attr("data_reference");
		var data_sort_name = $(this).attr("data_sort_name");
		var data_yaoshi_advice = $(this).attr("data_yaoshi_advice");
		var datas = {
			p_keys: p_keys,
			auto_audit_id: $(this).attr("data_auto_audit_id"),
			patient_id: $(this).attr("data_patient_id"),
			visit_id: $(this).attr("data_visit_id"),
			order_no: $(this).attr("data_order_no"),
			group_id: $(this).attr("data_group_id")
		}
		var html = [];
		var html2 = [];
		$(".detail_result").empty();
		$("#sc_realinfo tbody").empty();
		$.call("hospital_common.additional.queryRealYizu", datas, function(rtn){
			html.push('<div style="color: #aa0c0c"><span style="color: #aa0c0c">问题药品:</span>'+data_related_drugs_show+'</div>');
			html.push('<div><span>医生使用理由:</span><font style="color: #af0000">'+data_doctor_advice+'</font>');
			html.push('</div>');
			html.push('<div style="color: #aa0c0c"><span class="scp" style="">');
			html.push('前置审方结果：');
			html.push('<font style="color:#929292">【');
			if(data_yaoshi_audit_time){
				html.push(data_yaoshi_audit_time+' ');
			}
			if(data_yaoshi_name){
				html.push(data_yaoshi_name);
			}
			html.push('】</font>');
			if(data_yaoshi_advice){
				html.push('['+data_yaoshi_advice+']');
			}
			html.push('</span></div>');
			html.push('<div><span>审查类型:</span>');
			if(level=='Y'){
				html.push('<span class="rstate1 fa fa-check-circle-o" title="通过级别问题" ></span>');
			}else if(level=='N'){
				html.push('<span class="rstate2 icon-ban"  title="拦截级别问题"></span>');
			}else if(level=='T'){
				html.push('<span class="rstate3 fa fa-warning (alias)"  title="审查级别问题"></span>');
			}
			html.push(data_sort_name+'</div>');
			html.push('<div><span>严重级别:</span>'+data_sys_check_level_name+'</div>');
			html.push('<div><span>审查结果:</span>'+data_description+'</div>');
			html.push('<div><span>参考文献:</span>'+data_reference+'</div>');
			$(".detail_result").append(html.join(''));
			if(rtn){
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
							html2.push('<td width="40px">'+v[i].order_class_show+'</td>');
						}else{
							html2.push('<td width="40px"></td>');
						}
						if(v[i].tag == '┍' || v[i].tag == '﹣' || v[i].tag == ' '){
							html2.push('<td width="130px">'+v[i].enter_date_time+'</td>');
						}else{
							html2.push('<td width="130px"></td>');
						}
						html2.push('<td width="300px" class="is_hiddenmore" title="'+v[i].order_text+'"><a onclick="yaopin(\''+v[i].order_code+'\')">' + v[i].order_text +'</a></td>');
						/* html2.push('<td width="60px">'+v[i].jixing+'</td>'); */
						html2.push('<td width="60px">'+parseFloat(v[i].dosage)+'</td>');
						html2.push('<td width="60px">'+v[i].dosage_units+'</td>');
						html2.push('<td width="70px">'+v[i].administration+'</td>');
						html2.push('<td width="60px">'+v[i].frequency+'</td>');
						html2.push('<td width="350px">'+v[i].drug_message+'</td>');
						if(v[i].tag == '┍' || v[i].tag == '﹣' || v[i].tag == ' '){
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
			}
			$("#sc_realinfo tbody").append(html2.join(''));
		});
		
	});
	$("#checkandcomment tbody tr:first").click();
	
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
	
	$('li a').click(function(){
		if($(this).text() == '费用' && $("#feiyong tbody tr").html() == undefined){
			$.call("hospital_common.showturns.queryBillItems", {patient_id:'${param.patient_id}',visit_id:'${param.visit_id}', 'qall': 1}, function(rtn){
				$("#feiyong tbody").empty();
				if(rtn && rtn.bitems){
					var tbodyhtml = [];
					var items = rtn.bitems.resultset;
					for(var i=0; i< items.length; i++){
						tbodyhtml.push('<tr>');
						tbodyhtml.push('<td style="width: 30px">'+ (items[i].tag == null ? '': items[i].tag) +'</td>');
						tbodyhtml.push('<td style="width: 40px">'+ (items[i].repeat_indicator == null ? '': items[i].repeat_indicator) +'</td>');
						tbodyhtml.push('<td style="width: 60px">'+ (items[i].order_class_name == null ? '':items[i].order_class_name) +'</td>');
						tbodyhtml.push('<td style="width: 140px">'+ items[i].billing_date_time +'</td>');
						tbodyhtml.push('<td style="width: 100px">'+ items[i].item_code +'</td>');
						tbodyhtml.push('<td style="width: 200px">'+ items[i].item_name +'</td>');
						tbodyhtml.push('<td style="width: 100px">'+ items[i].shpgg +'</td>');
						tbodyhtml.push('<td style="width: 60px">'+ (items[i].shl + items[i].dw) +'</td>');
						tbodyhtml.push('<td style="width: 50px">'+ parseFloat(items[i].dj) +'</td>');
						tbodyhtml.push('<td style="width: 60px">'+ parseFloat(items[i].je) +'</td>');
						tbodyhtml.push('<td style="width: 60px">'+ (items[i].administration == null ?'': items[i].administration) +'</td>');
						tbodyhtml.push('<td style="width: 60px">'+ (items[i].frequency == null ? "": items[i].frequency) +'</td>');
						tbodyhtml.push('<td style="width: 150px">'+ items[i].dept_name +'</td>');
						tbodyhtml.push('<td style="width: 60px">'+ (items[i].doctor == null ? "": items[i].doctor) +'</td>');
						tbodyhtml.push('<td style="width: 60px">'+ (items[i].nurse == null ? "": items[i].nurse) +'</td>');
						tbodyhtml.push('</tr>');
					}
				}else{
					tbodyhtml.push('无数据');
				}
				$("#feiyong tbody").append(tbodyhtml.join(''));
			});
		}
	})
</script>