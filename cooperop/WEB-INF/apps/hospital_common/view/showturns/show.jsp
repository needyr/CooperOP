<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
	pageContext.setAttribute("wait_timeout", SystemConfig.getSystemConfigValue("ipc", "wait_time", "30"));
	pageContext.setAttribute("wait_timeout2", SystemConfig.getSystemConfigValue("ipc", "wait_time2", "30"));
	pageContext.setAttribute("notice_pharmacist_notwork", SystemConfig.getSystemConfigValue("ipc", "notice_pharmacist_notwork", "当前无药师在线，由医生决定是否用药"));
	pageContext.setAttribute("notice_doctor", SystemConfig.getSystemConfigValue("ipc", "notice_doctor","公告：后台药师正在审查，审查完成后，将会弹出结果提示"));
	pageContext.setAttribute("id", request.getParameter("id"));
%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/hospital_common/css/audit_show.css" >
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
<%--<script src="${pageContext.request.contextPath}/theme/scripts/layout.js" type="text/javascript"></script>--%>
<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js" type="text/javascript"></script>
<script type="text/javascript" src="../jquery-1.8.3.min.js"></script>
<style>
.list p{margin : 0 0 0 0 ;}
.list label{cursor: pointer;}
::-webkit-scrollbar {width: 2px;height: 4px;background-color: #F5F5F5;}
.hasreport{display: inline-block !important;width: 450px;eight: 60px;text-align: center;padding: 3px;color: #bb7d0f;font-size: 16px;}
.shenc-result{color: #ae0202;width: 400px;margin-top: 8px;margin-left: 2px;background-color: #dee2e0;font-size: 15px;border-radius: 5px;padding: 5px;}
</style>
<body>
 <div class="wk">
 	<!-- 左边导航栏 -->
    <div class="left-content">
    	<ul>
    		<c:if test="${not empty $return.hospital_imic.tnum }">
	        	<li class="nav-header dhtitle">医保控费审查</li> 
	        	<c:forEach items="${$return.hospital_imic.tnum }" var="tmum">
					<li class="nav-content" title="${tmum.sort_name}">
						<a href="#question_${tmum.sort_id}" data-item-id="${tmum.sort_id }">${fn:substring(tmum.sort_name, 0, 8)}${fn:length(tmum.sort_name) > 9 ?  '...': ''}(<b>${tmum.num })</b></a>
					</li>
				</c:forEach>
			</c:if>
			<c:if test="${not empty $return.ipc.tnum }">
				<li class="nav-header dhtitle" title="${item.sort_name }">合理用药审查</li> 
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
                	<span>体重${$return.patient.weight}kg</span>
                </c:if>
                 <%-- <span>过敏史：${$return.patient. alergy_drugs}</span> --%>
                <a href="javascript:void(0);" onclick="topatient();">患者详情</a>
            </p>
           <!-- 拼接入院诊断 -->
            <c:set var="dia_in" scope="session" value="" />
            <c:forEach items="${$return.diagnosis}" var="diag" >
            	<c:if test="${diag.diagnosis_type eq '1' or diag.diagnosis_type eq '21'}">
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
            <p class="top-p">诊　　断：<span  title="${dia_in}">${dia_in}</span></p>
            
            <!-- 拼接出院诊断 -->
            <c:set var="dia_out" scope="session" value="" />
            <c:forEach items="${$return.diagnosis}" var="diag" >
            	<c:if test="${diag.diagnosis_type eq '2'}">
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
            <c:forEach items="${$return.opers}" var="oper">${oper.operation} ;</c:forEach>
            </span></p>
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
			    <p class="top-p shenc-result comment_result_p">药师审方结果：<span style="font-size: 15px;">
		            <c:if test="${$return.auto.state eq 'Y'}">通过</c:if>
				    <c:if test="${$return.auto.state eq 'N'}">驳回</c:if>
				    <c:if test="${$return.auto.state eq 'D'}">医生决定</c:if>
				    <c:if test="${$return.auto.state eq 'DS'}">药师返回医生决定，医生【确认用药】</c:if>
				    <c:if test="${$return.auto.state eq 'DN'}">药师返回医生决定，医生【取消用药】</c:if>
			    </span></p>
			    <div style="float: right;margin-top: -30px;margin-right: 10px;">
				    <input type="checkbox" id="istodoc">
				    <label for="istodoc"style="color: blue;font-weight: 600;" title="">显示原问题医嘱</label>
			    </div>
			    <c:forEach items="${$return.ordersgroup}" var="og" >
			              <div class="commentcontent">
				              <p class="comment_result_p">医嘱信息：<span>
					          <c:set var="index_o"></c:set>
           			             <c:forEach items="${$return.comment_orders}" var="orders">
           				            <c:if test="${orders.order_no eq og.order_no}">
           				                <c:if test="${not empty index_o}">、</c:if>
						                【<span style="font-size: 14px;" class="ypsms" data-id="${orders.order_code }">
						                    <a href="javascript:void(0)" class="mylink">
        	    			                <c:if test="${ord.xiangm eq '高危药品'  and ord.xmz eq '是' }">
							                <!-- <i class="glyphicon glyphicon-warning-sign" style="color: red" title="高危药品"></i> -->
							                    <span class="gwyp">高危</span>
							                </c:if>
        	    			                ${orders.order_text }
        	    			                </a></span>
        	    			              <em>${orders.dosage}${orders.dosage_units} ${orders.administration} ${orders.frequency}</em>】
        	    		                     <c:set var="index_o">1</c:set>
        	    		                </c:if>
					                </c:forEach><c:set var="index_o"></c:set></span>
				              </p>
				                <div class="comment_result_p">
					                <c:forEach items="${$return.comments}" var="comment">
						                <c:if test="${comment.order_no eq og.order_no}">
							                <c:if test="${empty num}">
							                    <c:set var="num">1</c:set>
							                    药师意见：
							                    <c:if test="${not empty comment.comment_content}">
								    <span style="color:#cd2d2d;">【${comment.comment_content}】</span>
							                    </c:if><br>
							        <div id="comment_sys_qustion"></div>
							                </c:if>
							                <c:if test="${not empty comment.comment_name}">
							            <span>${comment.comment_name}<br></span>
							                </c:if>
						                </c:if>
					                </c:forEach>
					            </div>
					            <c:set var="num"></c:set>
				            </div>
				            <div id="primordial_info" style="display:none;">
					            <c:forEach items="${$return.item_info }" var="iteminfo" varStatus="st">
					                <c:forEach items="${$return.comment_orders}" var="orders">
					                    <c:if test="${orders.order_no eq og.order_no}">
					                    <hr>
					                        <c:if test="${fn:indexOf(orders.p_key, iteminfo.order_id) > -1  }">
			     		        <div class="box-list" id="question_${iteminfo.sort_id}">
		     			                         <c:if test="${iteminfo.check_result_state eq 'N' }">
							        <span class="intercept_c icon-ban" title="拦截级别问题"></span>
						                         </c:if> 
						                         <c:if test="${iteminfo.check_result_state eq 'T' }">
							 		<span class="hint_c fa fa-warning (alias)" title="提示级别问题"></span>
						                         </c:if>
			                        <p class="fstP">
			                            <span>
			             	                <font  class="level_${iteminfo.sys_check_level}"> 【${iteminfo.sort_name}】 ${iteminfo.sys_check_level_name} ${iteminfo.star_level }</font>
						                </span>
			                            <a href="javascript:void(0);"  onclick="seeopen('${iteminfo.id}',${iteminfo.sort_id})">查看详情</a>
			                        </p>
			                        <p>
		                                <span class="doctor_comment">强制使用理由:</span>
		                                    <c:if test="${not empty iteminfo.doc_advice && not empty iteminfo.doc_other_advice}">
		                                <span class="doctor_comment">【${iteminfo.doc_advice}${fn:substring(iteminfo.doc_other_advice,0,fn:length(iteminfo.doc_other_advice)-1)}】</span>
		                                    </c:if>
		                                    <c:if test="${not empty iteminfo.doc_advice && empty iteminfo.doc_other_advice}">
		                                <span class="doctor_comment">【${fn:substring(iteminfo.doc_advice,0,fn:length(iteminfo.doc_advice)-1)}】</span>
		                                    </c:if>
		                                    <c:if test="${empty iteminfo.doc_advice && not empty iteminfo.doc_other_advice}">
		                                <span class="doctor_comment">【${fn:substring(iteminfo.doc_other_advice,0,fn:length(iteminfo.doc_other_advice)-1)}】</span>
		                                    </c:if>
		                                    <c:if test="${empty iteminfo.doc_advice and empty iteminfo.doc_other_advice}">
		                                <span class="doctor_comment">无</span>
		                                    </c:if>
		                            </p>
			                        <p>医嘱信息：<b>
			             			    <c:forEach items="${$return.orders }" var="ord">
									        <c:if test="${fn:indexOf(iteminfo.order_id.concat(','), ord.p_key.concat(',')) > -1  }">
									    <span style="font-size: 14px;" class="ypsms" data-id="${ord.order_code }">
										【
										    <c:if test="${ord.xiangm eq '高危药品'  and ord.xmz eq '是' }">
										        <!-- <i class="glyphicon glyphicon-warning-sign" style="color: red" title="高危药品"></i> -->
										<span class="gwyp">高危</span>
										    </c:if>
										${ord.order_text }
										】
										</span>
										<em>${empty ord.dosage2?"":"".concat(ord.dosage2).concat("") }${empty ord.dosage_units?"":"".concat(ord.dosage_units).concat("") }　${ord.administration }　${ord.frequency}</em> 
									        </c:if>
								        </c:forEach></b>
						            </p>
			                        <p>审查结果：<b> ${iteminfo.description }</b>
			                        <c:if test="${iteminfo.sort_name eq 'TPN'}">
						            <a href="javascript:void(0);" style="font-size: 14px;"  onclick="setTPN('${iteminfo.id}',${iteminfo.sort_id},'${$return.ipc.ipc_auto_id}')">【TPN详情】</a>
						            </c:if>
			                        </p>
			                            <span class="meta"> ${iteminfo.reference }</span>
		         		       </div>
		         	                        </c:if>
		         	                    </c:if>
		         	                </c:forEach>
			     	            </c:forEach>
				            </div>
			            </c:forEach>
			</div>
			<div id="tab2" style="display: block;">
				<!-- 医保问题明细 -->
				<c:forEach items="${$return.hospital_imic.imic }" var="mr" varStatus="v">
					<div class="boc-list">
						<div class="box-list" id="question_${mr.sort_id}" >
							<p class="fstP">
								<span>
									<font  class="level_${mr.sys_level}">&#60;-${mr.sort_name}-&#62; ${mr.level_name} ${mr.level_star}</font>
								</span>
								<i class="falike fa fa-thumbs-o-up likeup" data-id="xxx"></i>
								<i class="falike fa fa-thumbs-o-down hatedown" data-id="xxx"></i>
								<a href="javascript:void(0);"  onclick="imicDetil('${mr.id}','${mr.imic_audit_id}')">查看详情</a>
				            </p>
				            <p id="dorctor_comment">
			                    
			                </p>
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
																<span style="font-size: 14px;" class="ypsms" data-id="${ors.item_code}">
																		【${ors.item_text}】
																</span>
																<em>
																	单价：<fmt:formatNumber value="${ors.price}"></fmt:formatNumber>元；
																	数量：<fmt:formatNumber value="${ors.amount}"></fmt:formatNumber>；
																	合计：<fmt:formatNumber value="${ors.costs}"></fmt:formatNumber>元；
																	${ors.dosage} ${ors.dosage_units} ${ors.administration} ${ors.frequency}
																</em> 
															</b>
													 	</p>
													 </c:if>
			                  					</c:if>
		                  			 	</c:forEach>	
	                  				</c:when>
	                  				<c:otherwise>
		                  				<c:forEach items="${$return.hospital_imic.bitems }" var="ors">
			                  				<c:if test="${fn:indexOf(mr.item_p_key.concat(','), ors.p_key.concat(',')) > -1 }">
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
															<em>
																<%-- 单价：${ors.price}元 ；数量：${ors.amount}； 合计：${ors.costs}元 --%>
																单价：<fmt:formatNumber value="${ors.price}"></fmt:formatNumber>元；
																数量：<fmt:formatNumber value="${ors.amount}"></fmt:formatNumber>；
																合计：<fmt:formatNumber value="${ors.costs}"></fmt:formatNumber>元；
																${ors.dosage} ${ors.dosage_units} ${ors.administration} ${ors.frequency}
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
				             <c:if test="${not empty mr.insur_item_message}">
				             	<p>医保提示：<b>${mr.insur_item_message}</b></p> 
				             </c:if>
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
							<span class="rejected_c fa fa-times (alias)" title="驳回问题：不可以用药">药</span>
						</c:if>
				            <p class="fstP">
					            <span>
					            	<font  class="level_${iteminfo.sys_check_level}"> &#60;-${iteminfo.sort_name}-&#62; ${iteminfo.sys_check_level_name} ${iteminfo.star_level }</font>
								</span>
					            <i class="falike fa fa-thumbs-o-up likeup" data-id="${iteminfo.id}"></i>
					            <i class="falike fa fa-thumbs-o-down hatedown" data-id="${iteminfo.id}"></i>
					            <a href="javascript:void(0);"  onclick="seeopen('${iteminfo.id}',${iteminfo.sort_id})">查看详情</a>
				            </p>
				            <p id="dorctor_comment">
			                    
			                </p>
				            <p>医嘱信息：<b>
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
										<em>${empty ord.dosage2?"":"".concat(ord.dosage2).concat("") }
											${empty ord.dosage_units?"":"".concat(ord.dosage_units).concat("") }
											　${ord.administration }　${ord.frequency}
										</em> 
									</c:if>
								</c:forEach></b>
							 </p>
			             	<p>审查结果：<b> ${iteminfo.description }</b>
			             	<c:if test="${iteminfo.sort_name eq 'TPN'}">
				            <a href="javascript:void(0);" style="font-size: 14px;"  onclick="setTPN('${iteminfo.id}',${iteminfo.sort_id},'${$return.ipc.ipc_auto_id}')">【TPN详情】</a>
				            </c:if>
			             	</p>
			             	<span class="meta"> ${iteminfo.reference }</span>
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
            <c:forEach items="${pa.tnum }" var="item" varStatus="status">
            	<c:if test="${item.check_result_state != 'N'}">
           		<div class="list">
                	<p class="title" id="titles" date_product="${pa.product_code}">${item.sort_name }</p>
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
            </c:forEach>
            </div>
            <button class="qzqd" onclick="qzqd();">提交</button>
        </div>
		<!-- 底部按钮区 -->
		<div class="btn-content">
			<div id="report" style="display : none"></div>
			<a class="caozuo2 qd-button" id="qd2">确定</a>
			<a class="sqmyy" id="sqmyy" onclick="sqmyy();">用药问题再次确认</a>
			<a class="qz" id="qsave" onclick="qsave();" title="将此医嘱提交给药师审查" style="cursor: no-drop; background-color: #8c9493 !important;">强制使用</a>
	        <a class="qx" id="qx" onclick="qx();" title="返回到开医嘱界面修改医嘱" style="cursor: no-drop; background-color: #8c9493 !important;">返回调整</a>
	        <!-- <a class="caozuo1" id="qd1" onclick="showAdvice();">查看药师审查结果</a> -->
   	    </div>
	</div>
 </div>
</body>
<script type="text/javascript">
 //验证强制使用的必填
 var boo = true;
 var commid = '${param.id}';
 $(function(){
	var fstate = '${param.state}';
	var ipc_state = '${$return.ipc.ipc_state}';
	var imic_state = '${$return.hospital_imic.imic_state}';
	$(".advicelabel").hide();
	//导航
 	$(".nav-content").click(function(){
 		$(this).addClass("li-active").siblings().removeClass("li-active");
 	});
	
 	var product_advices = eval('('+'${$return.product}'+')');//产品信息
    //是否必填
    $("#dianp").hide();
 })
	//等待时间
    var timeo = '${wait_timeout}';
    var timeo2 = '${wait_timeout2}';
    var notice_doctor = '${notice_doctor}';
	var state = "W";
	var advice;
	var yaoshi="";
	var index;
	var id = '${$return.ipc.ipc_auto_id}';
	var doctor_no = '${$return.patient.doctor_no}';
	var id1; 
	function showAdvice(){
		id1 = layer.open({
		  type: 1,
		  title: "药师审查结果",
		  area: ['400px', '300px'], //宽高
		  content: $("#rg_advice")
		});
	}
		
	//ipc查看详情
	 function seeopen(check_result_info_id,sort_id){
		 layer.open({
			  type: 2,
			  title: "医策智能辅助决策支持  -用药审查结果详情",
			  area: ['910px', '550px'], 
			  content: "/w/ipc/auditresult/particulars.html?auto_audit_id=${$return.ipc.ipc_auto_id}&&sort_id="+sort_id+"&&check_result_info_id="+check_result_info_id+"#topsta"
		}); 
	} 
	
	//imic查看详情
	 function imicDetil(imic_info_id, imic_audit_id){
		//alert(imic_info_id);
		 layer.open({
			  type: 2,
			  title: "医策智能辅助决策支持  -医保审查结果详情",
			  area: ['910px', '600px'], 
			  content: '/w/hospital_imic/auditresult/particulars.html?imic_audit_id='+imic_audit_id+'&patient_id=${$return.patient.patient_id}&visit_id=${$return.patient.visit_id}'+'&imic_info_id='+imic_info_id+"#topsta"
		});  
	} 

	 //患者详情
	 function topatient(){
		 layer.open({
			  type: 2,
			  title: "医策智能辅助决策支持  -患者详情",
			  area: ['847px', '550px'], 
			  content: "/w/hospital_common/patient/index.html?patient_id=${$return.patient.patient_id}&&visit_id=${$return.patient.visit_id}&&doctor_no=${$return.patient.doctor_no}"
		}); 
	 }
	
	//点赞方法
	goodlike();
	 function goodlike(){
		 $(".falike").on("click",function(){
			  var id=$(this).attr("data-id");
			  var good_reputation = 0;
			  //alert(id);
			  if($(this).index()==1){
					 if($(this).hasClass("fa-thumbs-up")){//赞取消     =不踩不赞
						 $(this).removeClass("fa-thumbs-up");
					 }else{//赞
						 $(this).addClass("fa-thumbs-up").siblings().removeClass("fa-thumbs-down");
						 good_reputation = 1;
					 }
				 }else{
					 if($(this).hasClass("fa-thumbs-down")){//踩取消
						 $(this).removeClass("fa-thumbs-down");
					 }else{//踩
						 $(this).addClass("fa-thumbs-down").siblings().removeClass("fa-thumbs-up");
						 good_reputation = 2;
					 }
				 }
			  $.call("ipc.auditresult.goodlike",{"id":id,"good_reputation": good_reputation},function(e){},
						function(e){}, {async: false, remark: false   })
		 })
	 }
	
	//打开药品说明书
	$(".ypsms").on("click", function(){
		if($(this).attr("data-id")){
		    var drugcode = $(this).attr("data-id");
			layer.open({
				type: 2,
				title: $(this).text()+'说明书',
				area: ['800px', '580px'], //宽高
				content: "/w/ipc/auditresult/instruction.html?his_drug_code="+drugcode	  
			});
		}
	});
	
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
	$('#istodoc').click(function(){
		var is_check = $(this).attr('checked');
		if(is_check){$('[id=primordial_info]').show();}
		else{$('[id=primordial_info]').hide();}
	});
	
	//TPN详情
	function setTPN(check_result_info_id,sort_id,ipc_auto_id){
		 layer.open({
			  type: 2,
			  maxmin:true,
			  title: "医策智能辅助决策支持  -审核结果详情",
			  area: ['90%', '90%'], 
			  content: "/w/hospital_common/showturns/indexinfo.html?auto_audit_id="+ipc_auto_id+"&&sort_id="+sort_id+"&&check_result_info_id="+check_result_info_id
		}); 
	}
</script>
