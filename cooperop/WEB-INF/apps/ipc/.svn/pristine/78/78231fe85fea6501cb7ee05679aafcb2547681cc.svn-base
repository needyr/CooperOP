<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%pageContext.setAttribute("id", request.getParameter("id"));%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/ipc/css/timeoutdetail.css" >
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
<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js" type="text/javascript"></script>
<script type="text/javascript" src="../jquery-1.8.3.min.js"></script>

<html>
<body>
    <div class="wk">
        <!-- 左边导航栏 -->
        <div class="left-content">
            <ul>
                <c:if test="${not empty $return.hospital_imic.tnum }">
	            <li class="nav-header dhtitle" >医保控费审查</li> 
	            <c:forEach items="${$return.hospital_imic.tnum }" var="tnum">
			    <li class="nav-content" title="${tnum.sort_name}">
			        <a href="#question_${tnum.sort_id}" data-item-id="${tnum.sort_id }">${fn:substring(tnum.sort_name, 0, 8)}${fn:length(tnum.sort_name) > 9 ?  '...': ''}(<b>${tnum.num }</b>)</a>
			    </li>
			    </c:forEach>
			    </c:if>
			    <c:if test="${not empty $return.ipc.tnum }">
				<li class="nav-header dhtitle" >合理用药审查</li> 
		        <c:forEach items="${$return.ipc.tnum }" var="item">
				<li class="nav-content" title="${item.sort_name }">
				    <a href="#question_${item.sort_id}" data-item-id="${item.sort_id }"> ${item.sort_name } (<b>${item.num }</b>)</a>
				</li>
				</c:forEach>
				</c:if>
            </ul>
    </div>
        <!-- 病人信息 -->
        <div class="top-content">
            <div class="top-div">
                <p class="top-p">
                    <span>${$return.patient.patient_name}</span>
	                <span>${$return.patient.sex}</span>
	                <span>${$return.patient.age}</span>
					<c:if test="${not empty $return.patient.height}">
						<span>身高<fmt:formatNumber value="${$return.patient.height}" pattern="#.0" minFractionDigits='0' />cm</span>
					</c:if>
					<c:if test="${not empty $return.patient.weight}">
						<span>体重<fmt:formatNumber value="${$return.patient.weight}" pattern="#.0" minFractionDigits='0' />kg</span>
					</c:if>
	                <%-- <span>过敏史：${patient. alergy_drugs}</span> --%>
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
            <p class="top-p">入院诊断：<span  title="${dia_in}">${dia_in}</span></p>
            
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
                <p class="top-p">手术：<span>
                <c:forEach items="${$return.opers}" var="oper">${oper.operation} ;</c:forEach>
                </span></p>
            </div>
        </div>
        <div class="h5" id="rg_advice">
		    <span class="advice-title" style="font-size: 16px;"></span>
			<span class="advice-content" style="font-size: 14px;"></span>
			<button class="qd qd-button" id="qd">确定</button>
	    </div>
	    <div class="box-content">
			<div class="card-tabs-bar graygreen">
			    <a href="javascript:void();" class="" id="shenc" onclick="shenc();">智能审查结果</a>
			    <a href="javascript:void();" class="active" id="dianp" onclick="dianp();">药师审查结果</a>
			</div>
	        <div class="card-tabs-stack graygreen">
	            <div id="tab1" style="display: none;">
		            <!-- 医保控费问题明细 -->
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
                            <c:choose>
	                            <c:when test="${mr.item_p_key eq '违反主单规则'}">
	                                <!-- <p>违规项目：当次开单所有医嘱</p> -->
	                            </c:when>
	                            <c:otherwise>
		                            <c:forEach items="${$return.hospital_imic.bitems }" var = "ors">
		                                <c:if test="${mr.item_p_key eq ors.p_key}">
		                        <p>医嘱信息：
					                <b>
						                <span style="font-size: 14px;" class="ypsms" data-id="xxx">【${ors.item_text}】</span>
							            <em>
								            单价：<fmt:formatNumber value="${ors.price}"></fmt:formatNumber>元；
											数量：<fmt:formatNumber value="${ors.amount}"></fmt:formatNumber>；
											合计：<fmt:formatNumber value="${ors.price}"></fmt:formatNumber>元；
											${ors.dosage} ${ors.dosage_units} ${ors.administration} ${ors.frequency}
							            </em> 
						            </b>
					            </p>
		                                </c:if>
		                            </c:forEach>
	                            </c:otherwise>
	                        </c:choose>
				                <p>审查结果：<b>${mr.description}</b></p>
				                <c:if test="${not empty mr.audit_explain}">
				                <p>监控说明：<b>${mr.audit_explain}</b></p> 
				                </c:if>
				                <c:choose>
				                    <c:when test="${mr.check_result eq 'N'}">
				                <span class="intercept_c icon-ban" title="驳回问题：导致不能用药，且不能强制使用">保</span>
				                    </c:when>
				                    <c:when test="${mr.check_result eq 'T'}">
			                    <span class="hint_c fa fa-warning (alias)" title="警示问题">保</span>
				                    </c:when>
				                    <c:when test="${mr.check_result eq 'Q'}">
				                <span class="ss_c icon-earphones-alt" title="可申诉问题">保</span>
				                    </c:when>
				                </c:choose>
			                </div>
			                <!-- 疑似违规  -->
			                <%-- <c:if test="${mr.check_result eq 'Q'}">
				                <p class="notsure"> 可疑，请确认是否违规：
				                    <label for="is_use${v.index}">
				                        <input type="radio" name = "isuse${v.index}" id="is_use${v.index}" data-choose="0" data-id="${mr.id}" />未违规
				                    </label>
				                    <label for="no_use${v.index}">
				                        <input type="radio" name = "isuse${v.index}" id="no_use${v.index}" data-choose="1" checked="true" data-id="${mr.id}" />违规
				                    </label>
			                    </p>
					        </c:if> --%>
                       </div>
                        </c:forEach>
		            <!-- 合理用药问题明细 -->
			        <c:forEach items="${$return.ipc.item_info }" var="iteminfo" varStatus="st">
			            <div class="boc-list">
		                    <div class="box-list" id="question_${iteminfo.sort_id}">
	     		                <c:if test="${iteminfo.check_result_state eq 'N' }">
				                <span class="intercept_c icon-ban" title="拦截问题:导致不能用药，可根据实际情况选择是否强制使用">药</span>
				                </c:if> 
				                <c:if test="${iteminfo.check_result_state eq 'T' }">
				                <span class="hint_c fa fa-warning (alias)" title="警示问题">药</span>
				                </c:if>
		                        <p class="fstP">
		                             <span>
		                                  <font  class="level_${iteminfo.sys_check_level}"> 【${iteminfo.sort_name}】 ${iteminfo.sys_check_level_name} ${iteminfo.star_level }</font>
					                 </span>
		                             <i class="falike fa fa-thumbs-o-up likeup" data-id="${iteminfo.id}"></i>
		                             <i class="falike fa fa-thumbs-o-down hatedown" data-id="${iteminfo.id}"></i>
		                             <a href="javascript:void(0);"  onclick="seeopen('${iteminfo.id}',${iteminfo.sort_id})">查看详情</a>
		                         </p>
		                    <p>
		                <span class="doctor_comment">医生意见:</span>
		                <c:if test="${not empty iteminfo.doc_advice && not empty iteminfo.doc_other_advice}">
		                <span class="doctor_comment">【${iteminfo.doc_advice}${fn:substring(iteminfo.doc_other_advice,0,fn:length(iteminfo.doc_other_advice))}】</span>
		                </c:if>
		                <c:if test="${not empty iteminfo.doc_advice && empty iteminfo.doc_other_advice}">
		                <span class="doctor_comment">【${fn:substring(iteminfo.doc_advice,0,fn:length(iteminfo.doc_advice)-1)}】</span>
		                </c:if>
		                <c:if test="${empty iteminfo.doc_advice && not empty iteminfo.doc_other_advice}">
		                <span class="doctor_comment">【${fn:substring(iteminfo.doc_other_advice,0,fn:length(iteminfo.doc_other_advice))}】</span>
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
            </div>
     	    </c:forEach>
		        </div>
		        <div id="tab2" style="display: block;">
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
			           <%-- <p class="top-p" style="color: #ae0202;width:100%;margin-top: 8px;">药师意见：<span>${$return.auto.yxk_advice}</span></p> --%>
		               <div id="commentright">
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
				            <div id="primordial_info">
					            <c:forEach items="${$return.item_info }" var="iteminfo" varStatus="st">
					                <c:forEach items="${$return.comment_orders}" var="orders">
					                    <c:if test="${orders.order_no eq og.order_no}">
					                        <c:if test="${fn:indexOf(iteminfo.order_id.concat(','), orders.p_key.concat(',')) > -1  }">
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
		            </div>
		    </div>
	   </div>
	</div>
</body>
</html>
<script type="text/javascript">
	$(function(){
		$(".ypsms").on("click", function(){
			if($(this).attr("data-id")){
				var drugcode = $(this).attr("data-id");
				drugsms(drugcode);
			}
		});
		$("[id='_question']").bind("click", function(){
			var id = $(this).attr("class");
			$('#tab1').animate({scrollTop: $(id).offset().top-135}, 500);
		});
		$('[id=primordial_info]').hide();
	});

	//ipc查看详情
	 function seeopen(check_result_info_id,sort_id){
		 var id = '${$return.ipc_auto_id}';
		 $.modal("/w/ipc/auditresult/particulars.html","医策智能辅助决策支持  -用药审查结果详情",{
		     width: '910px',
			 height: '600px',
			 auto_audit_id: id,
			 sort_id: sort_id,
			 check_result_info_id: check_result_info_id
		 });
	} 
	
	//imic查看详情
	 function imicDetil(imic_info_id, imic_audit_id){
		 var id = '${$return.ipc_auto_id}';
		 $.modal("/w/hospital_imic/auditresult/particulars.html","医策智能辅助决策支持  -医保审查结果详情",{
		     width: '910px',
			 height: '600px',
			 imic_audit_id: imic_audit_id,
			 imic_info_id: imic_info_id
		 });
	}
	
	//患者详情
	function topatient(){
	    var patient_id = '${$return.patient.patient_id}';
		var visit_id = '${$return.patient.visit_id}';
		$.modal( "/w/hospital_common/patient/index.html","医策智能辅助决策支持  -患者详情",{
			width: '847px',
			height: '610px',
			patient_id: patient_id,
			visit_id: visit_id
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
						function(e){}, {async: false, remark: false })
		 })
	 }
	
	function drugsms(his_drug_code){
		$.modal("/w/ipc/auditresult/instruction.html","药品说明书",{
			width: '800px',
			height: '660px',
			his_drug_code: his_drug_code
		});
	}

	function shenc(){
		$("#shenc").attr("class","active");
		$("#dianp").attr("class","");
		$("#tab1").css("display","block");
		$("#tab2").css("display","none");
	}
	
	function dianp(){
		$("#dianp").attr("class","active");
		$("#shenc").attr("class","");
		$("#tab1").css("display","none");
		$("#tab2").css("display","block");
	}
	
	$('#istodoc').click(function(){
		var is_check = $(this).attr('checked');
		if(is_check){
			$('[id=primordial_info]').show();
		}else{
			$('[id=primordial_info]').hide();
		}
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
