<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
	pageContext.setAttribute("wait_timeout", SystemConfig.getSystemConfigValue("ipc", "wait_time", "30"));
	pageContext.setAttribute("wait_timeout_mz", SystemConfig.getSystemConfigValue("ipc", "wait_timeout_mz", "30"));
	pageContext.setAttribute("wait_timeout_jz", SystemConfig.getSystemConfigValue("ipc", "wait_timeout_jz", "30"));
	pageContext.setAttribute("wait_timeout_zy", SystemConfig.getSystemConfigValue("ipc", "wait_timeout_zy", "30"));
	pageContext.setAttribute("wait_timeout2", SystemConfig.getSystemConfigValue("ipc", "wait_time2", "30"));
	pageContext.setAttribute("notice_pharmacist_notwork", SystemConfig.getSystemConfigValue("ipc", "notice_pharmacist_notwork", "当前无药师在线，由医生决定是否用药"));
	pageContext.setAttribute("imic_can_toselfpaid", SystemConfig.getSystemConfigValue("hospital_imic", "imic_can_toselfpaid", "1"));
	pageContext.setAttribute("notice_doctor", SystemConfig.getSystemConfigValue("ipc", "notice_doctor","公告：后台药师正在审查，审查完成后，将会弹出结果提示"));
	pageContext.setAttribute("id", request.getParameter("id"));
	pageContext.setAttribute("imic_isopen_bingzadmin", SystemConfig.getSystemConfigValue("hospital_imic", "imic_isopen_bingzadmin", "0"));
	pageContext.setAttribute("ipc_csmssq_open", SystemConfig.getSystemConfigValue("ipc", "ipc_csmssq_open", "0"));
%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/hospital_common/css/demo.css" >
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/hospital_common/css/dr_reason.css" >
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/plugins/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/plugins/simple-line-icons/simple-line-icons.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/plugins/bootstrap/css/bootstrap.min.css" >
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/hospital_common/css/precheck.css" >
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/css/custom.css" >
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/plugins/datatables/extensions/Scroller/css/dataTables.scroller.min.css?m={$module}"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/plugins/datatables/extensions/ColReorder/css/dataTables.colReorder.min.css?m={$module}"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css?m={$module}"/>
	        
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.json.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/metronic.js" type="text/javascript"></script>
<%-- <script src="${pageContext.request.contextPath}/theme/scripts/layout.js" type="text/javascript"></script> --%>
<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js" type="text/javascript"></script>
<script type="text/javascript" src="../jquery-1.8.3.min.js"></script>
<style>
.list p{
	margin : 0 0 0 0 ;
}
.list label{
	cursor: pointer;
}
::-webkit-scrollbar {
    width: 2px;
    height: 4px;
    background-color: #F5F5F5;
}
.hasreport{
	display: inline-block !important;
    width: 501px;
    height: 60px;
    /* border: 1px solid #9b9c9c; */
    text-align: center;
    padding: 3px;
    color: #bb7d0f;
    font-size: 16px;
    border-right: 1px solid #a99b9b;
    padding: 10px;
    vertical-align: middle;
    line-height: 40px;
}
.nlike{
	background: #e6e6e6;
    padding: 2px;
    margin-left: 18px;
    position: absolute;
    top: 0px;
    line-height: 23px;
}
.sp-nt{
	color: red;
    /* background: #dcd8d8; */
    padding: 1px 5px 1px 5px;
    font-weight: 600;
    /* border-radius: 15px; */
}
</style>
<body>
 <div class="wk">
 	<!-- 左边导航栏 -->
    <div class="left-content">
    	<ul>
    		<c:if test="${not empty $return.hospital_imic.tnum }">
	        	<li class="nav-header dhtitle">
					医保控费审查
				</li> 
	        	<c:forEach items="${$return.hospital_imic.tnum }" var="tmum">
					<li class="nav-content" title="${tmum.sort_name}">
						<a href="#question_${tmum.sort_id}" data-item-id="${tmum.sort_id }">${fn:substring(tmum.sort_name, 0, 8)}${fn:length(tmum.sort_name) > 9 ?  '...': ''}(<b>${tmum.num })</b></a>
					</li>
				</c:forEach>
			</c:if>
			<c:if test="${not empty $return.ipc.tnum }">
				<li class="nav-header dhtitle" title="${item.sort_name }">
					合理用药审查
				</li> 
	        	<c:forEach items="${$return.ipc.tnum }" var="item">
					<li class="nav-content">
						<a href="#question_${item.sort_id}" data-item-id="${item.sort_id }"> ${item.sort_name } (<b>${item.num }</b>)</a>
					</li>
				</c:forEach>
			</c:if>
		</ul>
    </div>
    <!-- 患者信息 -->
    <div class="top-content">
        <div class="top-div">
            <p class="top-p infobr">
                <span>${$return.patient.patient_name}</span>
                <span>${$return.patient.sex}</span>
                <span>${$return.patient.age}</span>
                <c:if test="${not empty $return.patient.height}">
                	<span>身高${$return.patient.height}cm</span>
                </c:if>
                 <c:if test="${not empty $return.patient.weight}">
                  <%-- <fmt:formatNumber value="${$return.patient.weight}" pattern="#.0" minFractionDigits='0' /> --%>
                	<span>体重${$return.patient.weight}kg</span>
                </c:if>
                <%-- <span>过敏史：${$return.patient. alergy_drugs}</span> --%>
                <a href="javascript:void(0);" onclick="topatient('${$return.patient.patient_id}','${$return.patient.visit_id}','${$return.patient.doctor_no}');">患者详情</a>
                 | <a href="javascript:void(0);" onclick="toItem();">计费项目</a>
                 <c:if test="${not empty $return.hospital_imic.tnum }">
                 	| <a href="javascript:void(0);" onclick="printSF();">打印自费通知书</a>
                 </c:if>
                <!--  | <a href="javascript:void(0);" onclick="topatient();">医嘱列表</a> -->
                <a href="javascript:void(0);" onclick="helps();" class="nlike">帮助？</a>
                <em style="margin-left: 80px; color: #b7b6b6;">审方耗时：${$return.patient.cost_time/1000}s</em>
            </p>
            <!-- 拼接入院诊断 -->
            <c:set var="dia_in" scope="session" value="" />
            <c:forEach items="${$return.diagnosis}" var="diag" >
            	<c:if test="${diag.diagnosis_type eq 1 or diag.diagnosis_type eq 21}">
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
            <p class="top-p">入院诊断：<span title="${dia_in}">${dia_in}</span></p>
            
            <!-- 拼接出院诊断 -->
            <c:set var="dia_out" scope="session" value="" />
            <c:forEach items="${$return.diagnosis}" var="diag" >
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
            <p class="top-p" title="${dia_out}">出院诊断：<span >${dia_out}</span></p>
            <p class="top-p">手　　术：<span>
            <c:forEach items="${$return.opers}" var="oper">
            	${oper.operation} ;
            </c:forEach>
            </span></p>
            <c:if test="${imic_isopen_bingzadmin eq 1}">
            	<c:choose>
	            	<c:when test="${empty $return.hospital_imic.bingz}">
	            		<p class="top-p" title="">
			            	管理模式：<span id="mode_s">
			            	当前患者是否加入单病种管理？ <a href="javascript:void(0)" onclick="enterBz('${$return.patient.patient_id}','${$return.patient.visit_id}','${$return.patient.doctor_no}','${$return.patient.patient_name}','${$return.patient.sex}');">是</a> | <a href="javascript:void(0)" onclick="recordMode(0,'${$return.patient.patient_id}','${$return.patient.visit_id}','${$return.patient.doctor_no}','${$return.patient.patient_name}','${$return.patient.sex}');">否</a></span>
	           			</p>
	            	</c:when>
	            	<c:otherwise>
	            		<c:choose>
		            		<c:when test="${$return.hospital_imic.bingz.managemode eq 1}">
		            			<p class="top-p" title="[${$return.hospital_imic.bingz.bingz_name}(${$return.hospital_imic.bingz.bingz_bh})]">
					            	管理模式：<span id="mode_s">
					            	当前患者为单病种管理,<a href="javascript:void(0);" onclick="exitBz('${$return.patient.patient_id}','${$return.patient.visit_id}','${$return.patient.doctor_no}','${$return.patient.patient_name}','${$return.patient.sex}');">点击管理</a>;<font style="font-weight: 600; font-size:14px;">[${$return.hospital_imic.bingz.bingz_name}(${$return.hospital_imic.bingz.bingz_bh})]</font></span>
		            			</p>
		            		</c:when>
		            		<c:when test="${$return.hospital_imic.bingz.managemode eq 0}">
		            			<p class="top-p" title="">
					            	管理模式：<span id="mode_s">
					            	当前患者为普通管理,<a href="javascript:void(0)" onclick="enterBz('${$return.patient.patient_id}','${$return.patient.visit_id}','${$return.patient.doctor_no}','${$return.patient.patient_name}','${$return.patient.sex}');">点击加入单病种管理</a></span>
		            			</p>
		            		</c:when>
		            		<c:otherwise>
		            			<font style="color: red;"> </font>
		            		</c:otherwise>
		            	</c:choose>
	            	</c:otherwise>
	            </c:choose>
            </c:if>
        </div>
    </div>
    <!-- 提示模拟弹窗 -->
    <div class="h5" id="rg_advice">
		<span class="advice-title" style="font-size: 16px;"></span>
		<span class="advice-content" style="font-size: 14px;"></span>
		<button class="qd qd-button" id="qd">确定</button>
	</div>
	<!-- 审查问题（结果明细） -->
	<div class="box-content">
		<div class="card-tabs-bar graygreen">
			<a href="javascript:void();" class="active" id="shenc" onclick="shenc();">智能审查结果</a>
			<a href="javascript:void();" class="" id="dianp" onclick="dianp();">药师审查结果</a>
		</div>
		<div class="card-tabs-stack graygreen">
			<div id="commentright" style="display: none;">
			</div>
			<div id="tab2" style="display: block;">
				
				<!-- 医保问题明细 -->
				<c:forEach items="${$return.hospital_imic.imic }" var="mr" varStatus="v">
					<div class="boc-list">
						<div class="box-list" id="question_${mr.sort_id}" >
							<p class="fstP">
								<span>
									<font  class="level_${mr.sys_level}">&#60;-${mr.sort_name}-&#62; ${mr.level_name} ${mr.level_star}</font>
									<c:if test="${mr.is_wf eq 1}">
										<font class="sp-nt">${empty mr.wf_process_shuom ?'强制使用将提交人工审批，审批通过方可使用': mr.wf_process_shuom}</font>					
									</c:if>
								</span>
								<i class="falike fa fa-thumbs-o-up likeup" data-id="${mr.id}" data-pro="hospital_imic"></i>
								<i class="falike fa fa-thumbs-o-down hatedown" data-id="${mr.id}" data-pro="hospital_imic"></i>
								<a href="javascript:void(0);"  onclick="imicDetil('${mr.id}','${mr.imic_audit_id}','${$return.patient.patient_id}','${$return.patient.visit_id}')">详情</a>
				            </p>
				    <!--         <p id="dorctor_comment">
			                    
			                </p> -->
	                  		<c:choose>
	                  			<c:when test="${mr.item_p_key eq '违反主单规则'}">
	                  				<!-- <p>违规项目：当次开单所有医嘱</p> -->
	                  			</c:when>
	                  			<c:otherwise>
	                  				<!-- [重复用药分类]去除重复药品名 -->
	                  				<c:choose>
	                  				<c:when test="${mr.check_code eq '100601'}">
	                  					<c:set value="0" var="tmpo" />
	                  					<c:forEach items="${$return.hospital_imic.bitems }" var="ors">
			                  				<c:if test="${fn:indexOf(mr.item_p_key.concat(','), ors.p_key.concat(',')) > -1 }">
			                  					<c:if test="${ors.item_code ne tmpo}">
			                  						<c:set value="${ors.item_code}" var="tmpo" ></c:set>
			                  						<p>医嘱：${tmpo}
										                  	<b>
																<span style="font-size: 14px;" class="ypsms" data-id="xxx">
																		【${ors.item_text}】
																</span>
																<em style="font-size: 14px">
																	单价：<fmt:formatNumber value="${ors.price}"></fmt:formatNumber>元；
																	数量：<fmt:formatNumber value="${ors.amount}"></fmt:formatNumber>；
																	合计：<fmt:formatNumber value="${ors.costs}"></fmt:formatNumber>元；
																	<fmt:formatNumber value="${ors.dosage}" pattern="#.0" minFractionDigits='0' />${ors.dosage_units} ${ors.administration} ${ors.frequency}
																</em> 
															</b>
													 	</p>
													 </c:if>
			                  					</c:if>
		                  			 	</c:forEach>	
	                  				</c:when>
	                  				<c:otherwise>
	                  					<c:set value="0" var="zf_state" />
	                  					<c:set value="0" var="is_drug_yb" />
		                  				<c:forEach items="${$return.hospital_imic.bitems }" var="ors">
			                  				<c:if test="${fn:indexOf(mr.item_p_key.concat(','), ors.p_key.concat(',')) > -1 }">
			                  						<c:set value="${ors.zf_state}" var="zf_state" ></c:set>
			                  						<c:set value="${ors.is_drug}" var="is_drug_yb" ></c:set>
			                  						<p class="cfyy">医嘱：
									                  	<b>
															<c:choose>
																<c:when test="${ors.is_drug eq 1}">
																	<span style="font-size: 14px;" class="ypsms" data-id="${ors.item_code}">
																	【${ors.item_text}】
																	</span>
																</c:when>
																<c:otherwise>
																	<span style="font-size: 14px;" data-id="${ors.item_code}">
																	【${ors.item_text}】
																	</span>
																</c:otherwise>
															</c:choose>
															<em style="font-size: 14px">
																<%-- 单价：${ors.price}元 ；数量：${ors.amount}； 合计：${ors.costs}元 --%>
																单价：<fmt:formatNumber value="${ors.price}"></fmt:formatNumber>元；
																数量：<fmt:formatNumber value="${ors.amount}"></fmt:formatNumber>；
																合计：<fmt:formatNumber value="${ors.costs}"></fmt:formatNumber>元；
																<fmt:formatNumber value="${ors.dosage}" pattern="#.0" minFractionDigits='0' />${ors.dosage_units} ${ors.administration} ${ors.frequency}
															</em> 
														</b>
													 </p>
			                  				</c:if>
		                  			 	</c:forEach>
		                  			</c:otherwise>
		                  			</c:choose>
	                  			</c:otherwise>
	                  		</c:choose>
				             <p>结果：<b>${mr.description}</b></p>
				             <c:if test="${not empty mr.audit_explain}">
				             	<p>说明：<b>${mr.audit_explain}</b></p> 
				             </c:if>
				             <%-- <c:if test="${not empty mr.insur_item_message}">
				             	<p>医保提示：<b>${mr.insur_item_message}</b></p> 
				             </c:if> --%>
				             <c:choose>
				             	<c:when test="${mr.check_type eq '1'}">
				             		<p><b style="color: #999999; font-size: 12px;">源自医保知识库审查；
				             			<c:choose>
							             	<c:when test="${mr.check_result eq 'N'}">
							             		当前开嘱因此问题被【驳回】，必须处理此问题
							             	</c:when>
							             	<c:when test="${mr.check_result eq 'T'}">
							             		当前开嘱因此问题被【提示】
							             	</c:when>
							             	<c:when test="${mr.check_result eq 'Q'}">
							             		当前开嘱因此问题被【拦截】，请处理此问题
							             	</c:when>
							             </c:choose></b>
				             		</p> 
				             	</c:when>
				             	<c:when test="${mr.check_type eq '2'}">
				             		<p><b style="color: #999999; font-size: 12px;">源自自定义医保审查；
				             			<c:choose>
							             	<c:when test="${mr.check_result eq 'N'}">
							             		当前开嘱因此问题被【驳回】，必须处理此问题
							             	</c:when>
							             	<c:when test="${mr.check_result eq 'T'}">
							             		当前开嘱因此问题被【提示】
							             	</c:when>
							             	<c:when test="${mr.check_result eq 'Q'}">
							             		当前开嘱因此问题被【拦截】，请处理此问题
							             	</c:when>
							             </c:choose></b>
				             		</p> 
				             	</c:when>
				             </c:choose>
				             <c:choose>
				             	<c:when test="${mr.check_result eq 'N'}">
				             		<span class="intercept_c fa fa-times" title="驳回问题：导致不能用药，且不能强制使用">保</span>
				             	</c:when>
				             	<c:when test="${mr.check_result eq 'T'}">
				             		<span class="hint_c fa fa-warning (alias)" title="警示问题">保</span>
				             	</c:when>
				             	<c:when test="${mr.check_result eq 'Q'}">
				             		<span class="ss_c fa fa-envelope" title="可申诉问题">保</span>
				             	</c:when>
				             </c:choose>
						</div>
						<!-- 转自费： is_zzf 在指定规则下允许转自费的提供该操作  -->
						<c:if test="${mr.is_zzf eq 1}">
							<p class="notsure">
								<c:choose>
									<c:when test="${zf_state eq 1}">
										<c:choose>
											<c:when test="${imic_can_toselfpaid eq 1}">
												该药品已转为自费使用 <a href="javascript:void(0);" onclick="admin_sp();">【管理转自费项目】</a>
											</c:when>
											<c:otherwise>
												该药品已转为自费使用 <a href="javascript:void(0);" onclick="admin_sp();">【查看转自费项目】</a>
											</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${imic_can_toselfpaid eq 1}">
												<label for="is_use${v.index}">
								             		<input type="checkbox" class="imic_zzf" name = "isuse${v.index}" id="is_use${v.index}" data-pkey="${mr.item_p_key}"  data-choose="0" data-id="${mr.id}" />
								             		患者自愿自费使用[同一药品仅操作一次即可，若[返回调整]，则此操作无效]
								             	</label>
											</c:when>
											<c:otherwise>
												若确认该问题且必须使用可将该项目转为自费使用（HIS系统中操作转自费）
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
				             	
			             	</p>
						</c:if>
					</div>
				</c:forEach>
				
				<!-- 合理用药问题明细  -->
				<c:forEach items="${$return.ipc.item_info }" var="iteminfo" varStatus="st">
		     			<%-- <c:forEach items="${$return.ipc.orders }" var="ord">
							<c:if test="${ord.sys_order_status eq '0' and fn:indexOf(iteminfo.order_id.concat(','), ord.p_key.concat(',')) > -1 and iteminfo.level >= iteminfo.interceptor_level or iteminfo.level >= iteminfo.info_level}">
							</c:if>
						</c:forEach> --%>
					<div class="boc-list">
			     		<div class="box-list" id="question_${iteminfo.sort_id}">
		     			<c:if test="${iteminfo.check_result_state eq 'N' }">
							<span class="intercept_c icon-ban" title="拦截问题:导致不能用药，可根据实际情况选择是否强制使用">药</span>
						</c:if> 
						<c:if test="${iteminfo.check_result_state eq 'T' }">
							<span class="hint_c fa fa-warning (alias)" title="警示问题">药</span>
						</c:if>
						<c:if test="${iteminfo.check_result_state eq 'B' }">
							<span class="hint_c fa fa-times" title="驳回问题：包含该类问题无法强制使用">药</span>
						</c:if>
				            <p class="fstP">
					            <span>
					            	<font  class="level_${iteminfo.sys_check_level}"> &#60;-${iteminfo.sort_name}-&#62; ${iteminfo.sys_check_level_name} ${iteminfo.star_level }</font>
								</span>
								<c:set var="csms1" scope="session" value="0" />
								<c:if test="${iteminfo.check_result_state ne 'T'}">
					            	<c:set var="csms1" value="csms1" />
					            </c:if>
					            <i class="falike fa fa-thumbs-o-up likeup ${csms1}${ipc_csmssq_open}" data-id="${iteminfo.id}" data-pro="ipc"></i>
					            <i class="falike fa fa-thumbs-o-down hatedown ${csms1}${ipc_csmssq_open}" data-id="${iteminfo.id}" data-pro="ipc"></i>
					            <a href="javascript:void(0);"  onclick="seeopen('${iteminfo.id}',${iteminfo.sort_id},'${$return.ipc.ipc_auto_id}')">详情</a>
					            <c:if test="${ipc_csmssq_open eq '1'}">
						            <c:if test="${csms1 eq 'csms1'}">
						            	<a href="javascript:void(0);"  onclick="csmsWf('question_${iteminfo.sort_id}');">超说明书申请　</a>
						            </c:if>
					            </c:if>
				            </p>
				            <p id="dorctor_comment">
			                    
			                </p>
				            <p>医嘱：<b>
					            <c:forEach items="${$return.ipc.orders }" var="ord">
									<c:if test="${fn:indexOf(iteminfo.order_id.concat(','), ord.p_key.concat(',')) > -1  }">
										<span style="font-size: 14px;" class="ypsms" data-id="${ord.order_code }">
												【
										<c:if test="${ord.xmz eq '是' }">
										<!-- <i class="glyphicon glyphicon-warning-sign" style="color: red" title="高危药品"></i> -->
											<span class="gwyp" title="高危药品">高危</span>
										</c:if>
										${ord.order_text }】
										</span>
										<em style="font-size: 14px">${empty ord.dosage2?"":"".concat(ord.dosage2).concat("") }
											${empty ord.dosage_units?"":"".concat(ord.dosage_units).concat("") }
											　${ord.administration }　${ord.frequency}
										</em> 
									</c:if>
								</c:forEach></b>
							 </p>
			             	<p>结果：<b style="color: #a50606; font-size: 16px;"> ${iteminfo.description }</b>
			             	<c:if test="${iteminfo.sort_name eq 'TPN'}">
				            <a href="javascript:void(0);" style="font-size: 14px;"  onclick="setTPN('${iteminfo.id}',${iteminfo.sort_id},'${$return.ipc.ipc_auto_id}')">【TPN详情】</a>
				            </c:if>
			             	</p>
			             	<c:if test="${not empty iteminfo.sp_is_must_yes}">
			             		<div class="ipc_sp_box" data-id="${iteminfo.id}" data-must="${iteminfo.sp_is_must_yes}">
			             		<label>${iteminfo.sp_description}</label>
			             		<%-- <c:if test="${not empty iteminfo.sp_result and iteminfo.sp_result eq '0'}">
			             			<label><input type="radio" name="ipc_sp_role${iteminfo.id}" class="ipc_sp_role" value="1" style="vertical-align:middle;margin-top:-2px;margin-bottom:1px;" >是</label>
			             			<label><input type="radio" name="ipc_sp_role${iteminfo.id}" checked="checked" class="ipc_sp_role" value="0" style="vertical-align:middle;margin-top:-2px;margin-bottom:1px;" >否</label>
			             		</c:if>
			             		<c:if test="${not empty iteminfo.sp_result and iteminfo.sp_result eq '1'}">
			             			<label><input type="radio" name="ipc_sp_role${iteminfo.id}" checked="checked" class="ipc_sp_role" value="1" style="vertical-align:middle;margin-top:-2px;margin-bottom:1px;" >是</label>
			             			<label><input type="radio" name="ipc_sp_role${iteminfo.id}" class="ipc_sp_role" value="0" style="vertical-align:middle;margin-top:-2px;margin-bottom:1px;" >否</label>
			             		</c:if> 
			             		<c:if test="${empty iteminfo.sp_result}">--%>
			             			<label class="ipc_radio ipc_radio_1 fa fa-square-o" style="color:#13c708;" data-id="ipc_sp_role${iteminfo.id}"><input type="radio" name="ipc_sp_role${iteminfo.id}" class="ipc_sp_role" value="1" style="vertical-align:middle;margin-top:-2px;margin-bottom:1px;display: none;" >是</label>
			             			<label class="ipc_radio ipc_radio_0 fa fa-square-o" style="color:#d50000;" data-id="ipc_sp_role${iteminfo.id}"><input type="radio" name="ipc_sp_role${iteminfo.id}" class="ipc_sp_role" value="0" style="vertical-align:middle;margin-top:-2px;margin-bottom:1px;display: none;" >否</label>
			             		<%-- </c:if> --%>
			             		<c:if test="${iteminfo.sp_is_must_yes eq '1'}">
			             			<!-- <font style="color: #d32424;">必须选择【是	】,否则只能【返回调整】</font> -->
			             			<font style="color: #d32424;">${iteminfo.sp_beiz}</font>
			             			<!-- 检查是否必须选择是选项才能用药 -->
									<c:set var="check_ipc_must_yes" value="1"></c:set>
			             		</c:if>
			             		</div>
			             	</c:if>
			             	<span class="meta">${empty iteminfo.reference?'':'源自 ' }${iteminfo.reference} [合理用药审查]；
			             	 <c:choose>
			             	 	<c:when test="${iteminfo.check_result_state eq 'T' }">
			             	 		当前开嘱因此问题被【提示】
			             	 	</c:when>
			             	 	<c:when test="${iteminfo.check_result_state eq 'N' }">
			             	 		当前开嘱因此问题被【拦截】，请处理此问题
			             	 	</c:when>
			             	 	<c:when test="${iteminfo.check_result_state eq 'B' }">
			             	 		当前开嘱因此问题被【驳回】，必须处理此问题
			             	 	</c:when>
			             	 </c:choose>
			             	 </span>
		         		</div>
		        	</div>
	     		</c:forEach>
			</div>
		</div>
	</div>
    <!-- 底部操作 -->
    <div class="botom">
    	<!-- 医生意见 -->
    	<div class="content" id="qadivce" style="display:none;">
            <div class="main">
            	<c:forEach items="${$return.product_advices}" var="pa" varStatus="">
            	<c:if test="${pa.is_null eq '0'}">
	            	<c:forEach items="${pa.tnum }" var="item" varStatus="status">
	            	<c:if test="${item.check_result_state eq 'N' or item.check_result_state eq 'Q'}">
	           		<div class="list">
	                	<p class="title" id="titles" data-product="${pa.product_code}">${item.sort_name }</p>
	                    <p class="optionText">
		                    <label style="font-s1ize: 12px;font-weight: 400;">其他:</label>
		                    <input type="text" class="adviceFruit" name="adviceFruit${pa.product_code}" data-sort-id="${item.sort_id}" data-sort-name="${item.sort_name}">
	                    </p>
	                </div>
	                </c:if>
	                <label class="advicelabel"><textarea class="doc_advice1${pa.product_code}" data-class="doc_advice1" data-product_code="${pa.product_code}" data-id="${item.sort_id}"></textarea></label>
	                <label class="advicelabel"><textarea class="doc_advice2${pa.product_code}" data-class="doc_advice2" data-product_code="${pa.product_code}" data-id="${item.sort_id}"></textarea></label>
	            	</c:forEach>
            	</c:if>
            	<c:if test="${pa.is_null eq '1'}">
	            	<c:forEach items="${pa.tnum }" var="item" varStatus="status">
	            	<c:if test="${item.check_result_state eq 'N' or item.check_result_state eq 'Q'}">
	           		<div class="list">
	                	<p class="title" id="titles" data-product="${pa.product_code}">${item.sort_name }</p>
	                    <c:forEach items="${pa.advices}" var="av" >
	                    	<c:if test="${av.apa_check_sorts_name == item.sort_name}">
	                        	<c:choose>
	                            	<c:when test="${av.is_must == 1}">
	                                	<div class="checkbox${status.index}${pa.product_code}">
	                                    	<input type="checkbox" name="Fruit${pa.product_code}" 
	                                       				data-id="${av.detail_id}" value="${av.usereasondetail_name}" data-sort-id="${item.sort_id}" class="commentcheckbox1" id="bindcheckbox${av.detail_id}"/>
	                                    	<label for="bindcheckbox${av.detail_id}" style="font-size: 12px;font-weight: 400;">${av.usereasondetail_name}</label>
	                                    </div>
	                                </c:when>
	                                <c:otherwise>
	                                	<div>
	                                    	<input type="checkbox" name="Fruit${pa.product_code}" value="${av.usereasondetail_name}" 
	                                            		data-id="${av.detail_id}" data-sort-id="${item.sort_id}" id="bindcheckbox${av.detail_id}" class="commentcheckbox1"/>
	                                        <label for="bindcheckbox${av.detail_id}" style="font-size: 12px;font-weight: 400;">${av.usereasondetail_name}</label>
	                                    </div>
	                                </c:otherwise>
	                            </c:choose>
	                        </c:if>
	            		</c:forEach>
	                    <p class="optionText">
		                    <label style="font-size: 12px;font-weight: 400;">其他:</label>
		                    <input type="text" class="adviceFruit" name="adviceFruit${pa.product_code}" data-sort-id="${item.sort_id}" data-sort-name="${item.sort_name}">
	                    </p>
	                </div>
	                </c:if>
	                <label class="advicelabel"><textarea class="doc_advice1${pa.product_code}" data-class="doc_advice1" data-product_code="${pa.product_code}" data-id="${item.sort_id}"></textarea></label>
	                <label class="advicelabel"><textarea class="doc_advice2${pa.product_code}" data-class="doc_advice2" data-product_code="${pa.product_code}" data-id="${item.sort_id}"></textarea></label>
	            	</c:forEach>
            	</c:if>
            	</c:forEach>
            </div>
            <button class="qzqd" onclick="qzqd();">提交</button>
        </div>
		<!-- 底部按钮区 -->
		<div class="btn-content">
			<div id="report" style="display : none">
				
			</div>
			<a class="caozuo2 qd-button" id="qd2">确定</a>
			<a class="sqmyy" id="sqmyy" onclick="sqmyy();">执行双签名用药</a>
			<a class="qz" id="qsave" onclick="qsave();" title="将此医嘱提交人工审查">强制使用</a>
	        <a class="qx" id="qx" onclick="qx();" title="返回到开医嘱界面修改医嘱">返回调整</a>
	        <a class="caozuo1" id="qd1" onclick="showAdvice();">审查结果</a>
   	    </div>
	</div>
 </div>
</body>
<script src="${pageContext.request.contextPath}/res/hospital_common/audit_showturn/common.js" type="text/javascript"></script>
<c:if test="${not empty $return.hospital_imic}">
	<script src="${pageContext.request.contextPath}/res/hospital_common/audit_showturn/imic.js" type="text/javascript"></script>
</c:if>
<c:if test="${not empty $return.ipc}">
	<script src="${pageContext.request.contextPath}/res/hospital_common/audit_showturn/ipc.js" type="text/javascript"></script>
</c:if>
<script type="text/javascript">
 var doctor = {};
 doctor._CRSID = '${_CRSID}';
 doctor.dept_code = '${$return.ipc.ipca.deptment_code}';
 doctor.dept_name = '${$return.ipc.ipca.deptment_name}';
 var patient = {};
 patient.patient_name= '${$return.patient.patient_name}';
 patient.sex = '${$return.patient.sex}';
 patient.age = '${$return.patient.age}';
 patient.bed_no = '${$return.patient.bed_no}';
 patient.dept_in_name = '${$return.patient.dept_in_name}';
 patient.patient_no = '${$return.patient.patient_no}';
 
 var prlv = {};
 //是否发送给药师
 var to_pharmacist = '0' ;
 prlv.prts = eval('(${$return.prts})');
 prlv.mangerlv = eval('(${$return.mangerlv})');
 var fstate = '${param.state}';
 var rtnflag = eval('(${$return.initst})');
 var d_type = '${$return.ipc.d_type}';
 
//等待时间
 var timeo = '${wait_timeout}';
 var timeo_mz = '${wait_timeout_mz}';
 var timeo_jz = '${wait_timeout_jz}';
 var timeo_zy = '${wait_timeout_zy}';
 var timeo2 = '${wait_timeout2}';
 var notice_doctor = '${notice_doctor}';
 var state = "W";
 var advice;
 var yaoshi="";
 var index;
 var id = '${$return.ipc.ipc_auto_id}';
 var doctor_no = '${$return.patient.doctor_no}';
 // 转自费框是否被点击
 var is_sp_click = 0;
 // 是否仅含医保限专问题
 var onlyxz = true ;

/*  var ipc_state = '${$return.ipc.state}';
 var imic_state = '${$return.hospital_imic.state}'; */

 //验证强制使用的必填
 var boo = true;
 var commid = '${param.id}';
 var auto_audit_id = '${$return.ipc.ipc_auto_id}';
 var imic_audit_id = '${$return.hospital_imic.imic_audit_id}';
 var id1;
$(function(){
	report_text();//report提示
	imic_zzf();//限专药品转自费操作
	<c:if test="${not empty $return.ipc}">
		ipc_sp_role('${check_ipc_must_yes}','${$return.hospital_imic.tnum}');//合理用药特殊类型审查操作
	</c:if>
	/* 公告说明
	if(is_work == false){
		$('#report').addClass('hasreport').text('${notice_pharmacist_notwork}');
	} */
	$(".advicelabel").hide();
	
	check_first_but_state('${$return.hospital_imic.tnum}','${check_ipc_must_yes}');//根据结果判断按钮状态
	
	check_repeat_submit('${$return.ipc.ipc_auto_id}');//判断医嘱是否重复提交
  
	goodlike();//点赞
	 
	openIns();//说明书事件添加
	
	//导航
 	$(".nav-content").click(function(){
 		$(this).addClass("li-active").siblings().removeClass("li-active");
 	});
	
 	qzsave_doctor_reason_required('${$return.product}');//强制使用填写理由，必填逻辑判断
    $("#dianp").hide();
    
	/* if(rtnflag.ipc && rtnflag.ipc == 'HL_N'){
		notice_doctor = '您已提交审查，审查完成后将会弹结果提示,请关注右下角弹窗！';
	}else{
		notice_doctor = '您已选择使用，请发送该患者医嘱！';
	} */
    
})

//填完理由后 提交
function qzqd() {
	//强制使用填写理由，判断是否必填
	var length= $('.list').length;
	var message='';
    boo = true;//是否能够强制用药
    var product_advices = eval('('+'${$return.product}'+')');//产品信息
    //是否必填
 	for(j=0;j<product_advices.length;j++){
 		var product_code = product_advices[j].product_code;
 		append_advice(product_code);
 		//判断是否必填
	    for(i=0;i<length;i++){
	        var isExist= $('div.checkbox'+i+product_code).length;
	        var checked = $('div.checkbox'+i+product_code+' input[type=checkbox]:checked').length;
	        if(isExist>0 && checked<=0){
	            if($('input[name="adviceFruit'+product_code+'"]').eq(i).val() == null || $('input[name="adviceFruit'+product_code+'"]').eq(i).val() == ''){
	                boo = false;
	                message = $('input[name="adviceFruit'+product_code+'"]').eq(i).attr('data-sort-name');
                    break;
	            }
	        }
	    }
    }
    //-------------------------------
 	//ipc强制用药流程,医生等待时间获取
 	var time_work;
	if(d_type == '1'){
		time_work = timeo_zy;
	}else if(d_type == '2'){
		time_work = timeo_mz;
	}else if(d_type == '3'){
		time_work = timeo_jz;
	}
	//-----------------------------
    
    
 	// --- 合理用药药师工作时间判断
	var is_work = true ;//--- 判断药师是否工作
	if(rtnflag.ipc && rtnflag.ipc == 'HL_N'){
		to_pharmacist = '1'; //需要发送药师审方
		$.call('hospital_common.showturns.isWork', {d_type:'${$return.ipc_d_type}'}, function(rtn){
			is_work = rtn.iswork;
			if(is_work && time_work == '0'){
				$.call("ipc.autoaudit.update", {id: id, is_overtime: "1"}, function(rtn6){});
	        }
		},function(e){}, {async: false, remark: false});
	}
	//-----------------------
    
	// --- 医保部分
	/* if(rtnflag.hospital_imic){
		var nsp = $.('sp-nt').length;
		if(nsp > 0){
			//包含需要审批内容
		}
	} */
	//-----------------------
	if(boo){
    	//强制使用理由获取
        var advices=[];
        $('[data-class="doc_advice1"]').each(function(){
        	advices.push({   
        		'sort_id':$(this).attr("data-id"),
        		'doc_advice_id': $(this).val(),
        		'doc_advice': $('[data-class="doc_advice2"][data-id="'+$(this).attr('data-id')+'"]').val(),
        		'doc_other_advice': $('[name="adviceFruit'+$(this).attr('data-product_code')+'"][data-sort-id="'+$(this).attr('data-id')+'"]').val() ||'',
        		'product_code': $(this).attr('data-product_code')
        	});
        });
        //----------------
        
        //强制使用理由窗口关闭,以及按钮的隐藏等
		layer.close(index);
		$("#qsave").remove();
		$("#qx").hide();
		//-------------------
		
		//特殊处理：非药师工作时间：医生强制使用自动通过
		/* if(is_work == false && to_pharmacist == '1'){
			$.call("hospital_common.showturns.backAdjust",{"id":'${$return.ipc.ipc_auto_id}',"common_id":commid,"state": "DQ1"},function(rtn){
				$("#rg_advice").find(".advice-content").html('<font class="ntcinfo">${notice_pharmacist_notwork}</font>');
				id1 = layer.open({
					  type: 1,
					  title: "系统提示",
					  area: ['400px', '300px'], //宽高
					  closeBtn: 0,
					  content: $("#rg_advice")
					});
				$(".qd-button").on("click", function(){
					rtnflag.ipc = 'DQ1';
					var frg = calflag();
					closeModal({'flag': frg.flag, 'state': "1", 'use_flag': "1"});//非药师工作时间：医生强制使用自动通过
				});
			});
			return ;
		}  */
		//保存具体ipc强制用药流程的时间配置, 用来做时间动态显示(时间递增/递减)
		if(d_type == '1'){
			timeo = timeo_zy;
		}else if(d_type == '2'){
			timeo = timeo_mz;
		}else if(d_type == '3'){
			timeo = timeo_jz;
		}
		//----------------------------
		//强制使用前必须做的后台操作,已包括所有项目
		//准备强制使用前必须做的后台操作方法所需参数
		var tjdata = {
			'to_pharmacist': to_pharmacist,
			'is_work': is_work,
			'id': id,
			'doctor_no': doctor_no,
			'advices':$.toJSON(advices),
			'patient_id':'${$return.patient.patient_id}',
			'visit_id':'${$return.patient.visit_id}',
			'common_id': commid,
			'd_type': d_type
		};
		var mustDo_timeout = timeo == '0' ? 60000 : (timeo == '-1'? -1: (timeo + 20000));
		$.ajax({
			"async": true,
			"dataType" : "json",
			"type" : "POST",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"cache" : false,
			"url" : "/w/hospital_common/showturns/mustDo.json",
			"timeout" : mustDo_timeout,
			"data" : tjdata ,
			"success" : function(rtn) {
				<c:if test="${not empty $return.ipc}">
					state = rtn.ipc.state;
					yaoshi = rtn.ipc.yaoshi;
					advice= rtn.ipc.yxk_advice;
				</c:if>
			},
			"error" : function(XMLHttpRequest, textStatus, errorThrown) {
				
			}
		});
		//------------------------------------
		if(!timeo || timeo == '0' || (rtnflag.ipc && rtnflag.ipc != 'HL_N') || (!rtnflag.ipc) || is_work == false){
			/* 当ipc强制用药流程的时间配置存在,
			 * 或者不需要医生等待时,
			 * 或者不存在合理用药项目, 
			 * 或者存在合理用药项目并且审查结果不为'HL_N'时,
			 * 或者药师不在工作时间。
			 * 直接执行以下逻辑,直接关闭窗口！
			 */
			
			// 提示语 ，及返回值，中间表数据
			/* if(rtnflag.ipc && rtnflag.ipc == 'HL_N'){
				notice_doctor = '您已提交审查，审查完成后将会弹结果提示,请关注右下角弹窗！';
			}else{
				notice_doctor = '您已选择使用，请发送该患者医嘱！';
			} */
			
			//对整体的结果进行相关弹窗提示
			// 默认
			notice_doctor = '您已提交审查，审查完成后将会弹出结果提示,「请关注右下角弹窗」！';
			var use_flag = "0";
			// 仅合理用药强制，且药师不在线
			if(is_work == false && to_pharmacist == 1 && rtnflag.hospital_imic != 'MQ'){
				notice_doctor = '当前无人审核，系统默认「通过」';
				use_flag = "1";
			}
			
			//仅医保强制， 且不需要审批
			if(($('.sp-nt').length <= 0) && rtnflag.ipc != 'HL_N'){
				notice_doctor = '不含待审批项目，「审核通过」！';
				use_flag = "1";
			}
			
			id1 = layer.open({
				  type: 1,
				  title: "温馨提示",
				  area: ['400px', '240px'], //宽高
				  content: $("#rg_advice"),
				  cancel: function(index, layero){ 
					  closeModal({'flag': frg.flag, 'state': "1", 'use_flag': use_flag});
				  } 
			});
			if(rtnflag.ipc && rtnflag.ipc == 'HL_N'){
				rtnflag.ipc = 'DQ';
			}
			if(rtnflag.hospital_imic && rtnflag.hospital_imic == 'MQ'){
				rtnflag.hospital_imic = 'DQ';
			}
			var frg = calflag();
			//notice_doctor = '【' + frg.remark +'】<br/>' + notice_doctor;
			$("#rg_advice").find(".advice-content").html('<font class="ntcinfo">'+notice_doctor+'</font>');
			$("#qd").on("click", function(){
				closeModal({'flag': frg.flag, 'state': "1", 'use_flag': use_flag});
			});
			//--------------------------------------------------------------------------
		}else if(timeo == '-1' || timeo > 0){
			/*
			 * 当ipc强制用药流程的时间配置,需要医生等待时逻辑
			 */
			var tt = timeo;
			var tt2 = timeo2;
			$("#qd").hide();
			$(".caozuo1").show();
			$("#rg_advice").find(".advice-content").html("等待药师审查用药合理性：<span style='font-size: 18px;color:#919090;'>"+tt+"</span> 秒");
			id1 = layer.open({
			  type: 1,
			  title: "温馨提示",
			  //skin: 'layui-layer-rim', //加上边框
			  area: ['400px', '240px'], //宽高
			  content: $("#rg_advice")
			});
			var exit_qd = 0;
			var rg_advice_full = ""
			var timer = setInterval(function(){
				if(timeo == '-1'){
					++tt;
				}else{
					--tt;
				}
				if(state == "W"){
					if(timeo != '-1' && tt <=0){
						//$("#rg_advice").show();
						$(".qd-button").show();
						$(".caozuo1").show();
						$("#rg_advice").find(".advice-title").text("药师审查超时");
						$("#rg_advice").find(".advice-content").text("当前没有空闲药师，稍后药师审查完毕后，将推送消息，敬请留意！");
						$.call("ipc.autoaudit.update", {id: id, is_overtime: "1"}, function(rtn6){});
						$(".qd-button").on("click", function(){
			        		closeModal({flag: "2", state: "1", "use_flag": "0"});//超时
						});
						clearInterval(timer);
					}else{
						$("#rg_advice").find(".advice-content").html("等待药师审查用药合理性：<span style='font-size: 18px;color:#919090;'>"+tt+"</span> 秒<br>"+rg_advice_full);
						if(timeo == '-1' && tt > 180 && exit_qd == 0){
							exit_qd = 1;
							$(".qd-button").show();
							rg_advice_full = "<font color='red'>药师审查时间过长，稍后药师审查完毕后，将推送消息，敬请留意！</font>";
							$("#rg_advice").find(".advice-content").html("等待药师审查用药合理性：<span style='font-size: 18px;color:#919090;'>"+tt+"</span> 秒<br>"+rg_advice_full);
							$("#qd2").text('退出等待');
							$("#qd2").css('background-color','#d23e3e');
							$("#qd2").on("click", function(){
								$.confirm('是否确认退出等待？之后药师审查完毕后，将推送消息，敬请留意！',function(rtn){
									if(rtn){
									$.call("ipc.autoaudit.update", {id: id, is_overtime: "1"}, function(rtn6){});
									closeModal({flag: "2", state: "1", "use_flag": "0"});//超时
									}
								})
							});
							$("#qd").on("click", function(){
								layer.close(id1);
							});
						}
					}
				}else if(state == "J"){
					state = "S";
					$("#rg_advice").find(".advice-title").text("药师审查中...");
					$("#rg_advice").find(".advice-content").html("药师 <span style='font-size: 18px;'>【"+yaoshi+"】</span> 正在审查，请稍后！<br>等待时间:<span style='font-size: 18px;color:#919090;'>"+tt+"</span> 秒");
					var exit_qd2 = 0;
					var rg_advice_full2 = ""
					var J_times = setInterval(function(){
						if(timeo == '-1'){
							++tt;
						}else{
							--tt;
						}
						$("#rg_advice").find(".advice-title").text("药师审查中...");
						$("#rg_advice").find(".advice-content").html("药师 <span style='font-size: 18px;'>【"+yaoshi+"】</span> 正在审查，请稍后！<br>等待时间:<span style='font-size: 18px;color:#919090;'>"+tt+"</span> 秒<br>"+rg_advice_full2);
						if((timeo == '-1' && tt > 180 && exit_qd == 0) || (timeo != '-1' && tt <=0)){
							exit_qd2 = 1;
							$(".qd-button").show();
							rg_advice_full2 = "<font color='red'>药师审查时间过长，稍后药师审查完毕后，将推送消息，敬请留意！</font>";
							$("#rg_advice").find(".advice-content").html("药师 <span style='font-size: 18px;'>【"+yaoshi+"】</span> 正在审查，请稍后！<br>等待时间:<span style='font-size: 18px;color:#919090;'>"+tt+"</span> 秒<br>"+rg_advice_full2);
							$("#qd2").text('退出等待');
							$("#qd2").css('background-color','#d23e3e');
							$("#qd2").on("click", function(){
								$.confirm('是否确认退出等待？之后药师审查完毕后，将推送消息，敬请留意！',function(rtn){
									if(rtn){
									$.call("ipc.autoaudit.update", {id: id, is_overtime: "1"}, function(rtn6){});
									closeModal({flag: "2", state: "1", "use_flag": "0"});//超时
									}
								})
							});
							$("#qd").on("click", function(){
								layer.close(id1);
							});
						}
					},1000)
					$.ajax({
						"async": true,
						"dataType" : "json",
						"type" : "POST",
						"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
						"cache" : false,
						"url" : "/w/hospital_common/showturns/getPCResult.json",
						"timeout" : -1,
						"data" : {id: id,doctor_no: doctor_no},
						"success" : function(rtn) {
							layer.close(id1);
							advice= rtn.yxk_advice;
							state = rtn.state;
							var timer2 = setInterval(function(){
								--tt2;
								if(tt2 !=-1 && tt2 <=0 && (state == 'J' || state =='Z')){
									$(".qd-button").show();
									$("#rg_advice").find(".advice-title").text("药师审查超时");
									$("#rg_advice").find(".advice-content").text("稍后药师审查完毕后，将推送消息，敬请留意！");
									$.call("ipc.autoaudit.update", {id: id, is_overtime: "1"}, function(rtn6){});
									$(".qd-button").on("click", function(){
						        		closeModal({flag: "2", state: "1", "use_flag": "0"});//超时
									});
									clearInterval(timer2);
									/* id1 = layer.open({
									  type: 1,
									  title: "药师意见",
									  //skin: 'layui-layer-rim', //加上边框
									  area: ['400px', '300px'], //宽高
									  content: $("#rg_advice")
									}); */
								}else{
									clearInterval(J_times);
									//点评返回结果
									var check=0;
									$("#dianp").show();
									$("#dianp").attr("style","");
									$.call("hospital_common.showturns.queryComment",{id: id},function(rtn){
										var html=[];
										 if(rtn.ordersgroup){
											var og = rtn.ordersgroup;
											for(i=0;i<og.length;i++){
												html.push('<div class="commentcontent">');
												html.push('<p class="comment_result_p">医嘱信息：<span>');
												if(rtn.comment_orders){
												var orders = rtn.comment_orders;
													for(j=0;j<orders.length;j++){
														if(orders[j].order_no == og[i].order_no){
															html.push('<span style="font-size: 14px;" class="ypsms">');
															html.push('<a data-id="'+orders[j].order_code+'" class="ypsms">');
															html.push('【');
															if(orders[j].xiangm == '高危药品' && orders[j].xmz == '是'){
																html.push('<span class="gwyp">高危</span>');
															}
															html.push(orders[j].order_text+'】');
															html.push('</a></span>');
															html.push('<em>');
															html.push(orders[j].dosage+orders[j].dosage_units+' '+orders[j].administration+' '+orders[j].frequency);
															html.push('</em>');
														}
													}
												}
												html.push('</p>');
												if(rtn.comments){
													var comment = rtn.comments;
													html.push('<div class="comment_result_p">');
													for(p=0;p<comment.length;p++){
														if(comment[p].order_no == og[i].order_no){
															if(check==0){
																check=1;
																html.push('药师意见：<br>');
																if(comment[p].comment_content){
																	html.push('<p class="comment_result_p"><span>【');
																	html.push(comment[p].comment_content);
																	html.push('】</span></p>');
																}
																html.push('<div id="comment_sys_qustion">');
															}
															if(comment[p].comment_name){
																html.push('<span>'+comment[p].comment_name+'<br></span>');
															}
														}
													}
													html.push('</div>');
													html.push('</div>');
													check=0;
												}
												html.push('</p>');
												html.push('</span></p>');
												html.push('</div>');
											}
											$("#commentright").append(html.join(''));
											$(".ypsms").on("click", function(){
												if($(this).attr("data-id")){
													var drugcode = $(this).attr("data-id");
													//alert(drugcode);
													layer.open({
																  type: 2,
																  title: $(this).text()+'说明书',
																  //skin: 'layui-layer-rim', //加上边框
																  area: ['800px', '580px'], //宽高
																  //content: "instruction.html?his_drug_code="+drugcode
																  content: "/w/ipc/auditresult/instruction.html?his_drug_code="+drugcode	  
													});
												}
											});
										}
									})
									
									//
									if(state == 'Y'){
										$("#rg_advice").find(".advice-title").text("药师审查：通过");
										$("#rg_advice").find(".advice-content").text("");
										clearInterval(timer2);
									}else if(state == 'N'){
										$("#rg_advice").find(".advice-title").text("药师审查：不通过");
										$("#rg_advice").find(".advice-content").text("");
										clearInterval(timer2);
									}else if(state == 'D'){
										$("#rg_advice").find(".advice-title").html("药师审查：返回医生决定");
										advice = (advice?advice:"")+"<br /><font color='red' "+
										"style='margin-top: 10px;display:  block;'>您可以点击右下角按钮【执行双签名用药】/【返回调整】，如果强行用药造成的后果由您负责！</font>"
										clearInterval(timer2);
									}else if(state == 'Z'){
										$("#rg_advice").find(".advice-title").text("药师审查超时");
										$("#rg_advice").find(".advice-content").text("稍后药师审查完毕后，将推送消息，敬请留意！");
										clearInterval(timer2);
									}
									$("#rg_advice").find(".advice-content").html(advice);
									id1 = layer.open({
									  type: 1,
									  title: "药师意见",
									  //skin: 'layui-layer-rim', //加上边框
									  area: ['400px', '300px'], //宽高
									  content: $("#rg_advice")
									});
									if(state == 'Y'){
										$(".qd-button").show();
										$(".caozuo1").show();
										$("#qd2").text('确定');
										$("#qd2").css('background-color','#0fbbab');
										$(".qd-button").on("click", function(){
											topass({id: id,doctor_no: doctor_no, state: 'Y'});
											closeModal({flag: "3", state: "1", "use_flag": "1"});//人工通过
										});
									}else if(state == 'N'){
										$(".qd-button").show();
										$(".caozuo1").show();
										$("#qd2").text('返回调整');
										$("#qd2").css('background-color','#0fbbab');
										$("#qd2").on("click", function(){
											topass({id: id,doctor_no: doctor_no, state: 'N'});
											closeModal({flag: "4", state: "0", "use_flag": "0"});//人工不通过
										});
										$("#qd").on("click", function(){
											layer.close(id1);
										});
									}else if(state == 'Z'){
										$(".qd-button").show();
										$(".caozuo1").show();
										$("#qd2").css('background-color','#0fbbab');
										$.call("ipc.autoaudit.update", {id: id, is_overtime: "1"}, function(rtn6){});
										$(".qd-button").on("click", function(){
							        		closeModal({flag: "2", state: "1", "use_flag": "0"});//超时
										});
									}else{//设置允许医生双签名用药
										$("#qd").show();
										$(".caozuo1").hide();
										$("#qd").on("click", function(){
											layer.close(id1);
										});
										$("#sqmyy").show();
										//取消用药
										$("#qd2").show();
										$("#qd2").text("取消用药");
										$("#qd2").css('background-color','#0fbbab');
										$("#qd2").on("click", qx);
									}
								}
							});
						},
						"error" : function(XMLHttpRequest, textStatus, errorThrown) {
							
						}
					});
					clearInterval(timer);
				}else if(state == "S"){
					if(tt <=0){
						$(".qd-button").show();
						$("#qd").on("click", function(){
			        		closeModal({flag: "2", state: "1", "use_flag": "0"});//超时
						});
						clearInterval(timer);
					}else{
						layer.msg("药师 【"+yaoshi+"】 正在审查，预计花费时间<span style='font-size: 18px;'>"+tt+"</span> 秒");
					}
				}else{//药师在时间内审查返回，按药师结果返回
					layer.close(id1);
					if(state == 'Y'){
						$("#rg_advice").find(".advice-title").text("药师审查：通过");
						$("#rg_advice").find(".advice-content").text("");
					}else if(state == 'N'){
						$("#rg_advice").find(".advice-title").text("药师审查：不通过");
						$("#rg_advice").find(".advice-content").text("");
					}else if(state == 'D'){
						$("#rg_advice").find(".advice-title").html("药师审查：返回医生决定");
						advice = (advice?advice:"")+"<br /><font color='red' "+
						"style='margin-top: 10px;display:  block;'>您可以点击右下角按钮【执行双签名用药】/【返回调整】，如果强行用药造成的后果由您负责！</font>"
					}else if(state == 'Z'){
						$("#rg_advice").find(".advice-title").text("药师审查超时");
						$("#rg_advice").find(".advice-content").text("稍后药师审查完毕后，将推送消息，敬请留意！");
					}else{
						$("#rg_advice").find(".advice-title").text("");
					}
					$("#rg_advice").find(".advice-content").html(advice);
					var i1 = layer.open({
						  type: 1,
						  title: "药师意见",
						  //skin: 'layui-layer-rim', //加上边框
						  area: ['400px', '300px'], //宽高
						  content: $("#rg_advice")
						});
					$("#qd").show();
					if(state == 'Y'){
						$(".qd-button").show();
						$("#qd2").text('确定');
						$("#qd2").css('background-color','#0fbbab');
						$(".qd-button").on("click", function(){
							closeModal({flag: "3", state: "1", "use_flag": "1"});//人工通过
						});
					}else if(state == 'N'){
						$(".qd-button").show();
						$(".caozuo1").show();
						$("#qd2").text('返回调整');
						$("#qd2").css('background-color','#0fbbab');
						$("#qd2").on("click", function(){
							closeModal({flag: "4", state: "0", "use_flag": "0"});//人工不通过
						});
						$("#qd").on("click", function(){
							layer.close(i1);
						});
					}else if(state == 'Z'){
						$(".qd-button").show();
						$(".caozuo1").show();
						$("#qd2").css('background-color','#0fbbab');
						$.call("ipc.autoaudit.update", {id: id, is_overtime: "1"}, function(rtn6){});
						$(".qd-button").on("click", function(){
			        		closeModal({flag: "2", state: "1", "use_flag": "0"});//超时
						});
					}else{//设置允许医生双签名用药
						$("#qd").show();
						$(".caozuo1").hide();
						$("#qd").on("click", function(){
							layer.close(i1);
						});
						$("#sqmyy").show();
						//取消用药
						$("#qd2").show();
						$("#qd2").text("取消用药");
						$("#qd2").css('background-color','#0fbbab');
						$("#qd2").on("click", qx);
					}
					clearInterval(timer);
				}
		    },1000);
			dianp();
		}
    }else{
		$.message("请填写必填项："+message);
    }
}

</script>
