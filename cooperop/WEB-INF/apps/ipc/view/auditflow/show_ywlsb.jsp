<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
	pageContext.setAttribute("wait_timeout", SystemConfig.getSystemConfigValue("hospital_common", "wait_time", "30"));
	pageContext.setAttribute("notice_doctor", SystemConfig.getSystemConfigValue("hospital_common", "notice_doctor","后台药师正在审查，审查完成后，将会弹出结果提示"));
	pageContext.setAttribute("id", request.getParameter("id"));
	
%>

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
        	 <li class="nav-header">
							问题分类
						</li> 
        	<c:forEach items="${$return.items }" var="item">
						<li class="nav-content">
							<a class="#question_${item.sort}" href="#" data-item-id="${item.sort }" id="_question"> ${item.sort_name } (<b>${item.num }</b>)</a>
						</li>
			</c:forEach>
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
                 <%-- <span>过敏史：${$return.patient. alergy_drugs}</span> --%>
                <a href="javascript:void(0);" onclick="topatient();">患者详情</a>
            </p>
            <p class="top-p">入院诊断：<span>
	            <c:if test="${not empty $return.diagnosis}">
		            <c:set value="0" var="fristq"></c:set>
		            <c:forEach items="${$return.diagnosis}" var="diag" >
		            	<c:if test="${diag.diagnosis_type eq '1' or diag.diagnosis_type eq '21'}">
		            		<c:choose>
		            			<c:when test="${fristq eq 0}">
			            				${diag.diagnosis_desc}
			            			<c:set value="1" var="fristq"></c:set>
		            			</c:when>
		            			<c:otherwise>
		            				、${diag.diagnosis_desc}
		            			</c:otherwise>
		            		</c:choose>
		            	</c:if>
		            </c:forEach>
	            </c:if>
            </span></p>
            <p class="top-p">出院诊断：<span>
	            <c:if test="${not empty $return.diagnosis}">
		            <c:set value="0" var="fristc"></c:set>
		            <c:forEach items="${$return.diagnosis}" var="diag" >
		            	<c:if test="${diag.diagnosis_type eq '2'}">
		            		<c:choose>
		            			<c:when test="${fristq eq 0}">
			            				${diag.diagnosis_desc}
			            			<c:set value="1" var="fristc"></c:set>
		            			</c:when>
		            			<c:otherwise>
		            				、${diag.diagnosis_desc}
		            			</c:otherwise>
		            		</c:choose>
		            	</c:if>
		            </c:forEach>
	            </c:if>
            </span></p>
            <p class="top-p">手术：<span>
            <c:forEach items="${$return.opers}" var="oper">
            	${oper.operation} ;
            </c:forEach>
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
			<c:forEach items="${$return.item_info }" var="iteminfo" varStatus="st">
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
		                 <%-- <i class="falike fa fa-thumbs-o-up likeup" data-id="${iteminfo.id}"></i>
		                 <i class="falike fa fa-thumbs-o-down hatedown" data-id="${iteminfo.id}"></i>
		                <a href="javascript:void(0);"  onclick="seeopen('${iteminfo.check_result_info_id}',${iteminfo.sort_id})">查看详情</a> --%>
		             </p>
		             <p>
	                    <span class="doctor_comment">医生意见:</span>
	                    <c:if test="${not empty iteminfo.doc_advice && 
	                    			 not empty iteminfo.doc_other_advice}">
	                    <span class="doctor_comment">【${iteminfo.doc_advice}${fn:substring(iteminfo.doc_other_advice,0,fn:length(iteminfo.doc_other_advice))}】</span>
	                    </c:if>
	                    <c:if test="${not empty iteminfo.doc_advice && 
	                    			 empty iteminfo.doc_other_advice}">
	                    <span class="doctor_comment">【${fn:substring(iteminfo.doc_advice,0,fn:length(iteminfo.doc_advice)-1)}】</span>
	                    </c:if>
	                    <c:if test="${empty iteminfo.doc_advice && 
	                    			 not empty iteminfo.doc_other_advice}">
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
									</span><em>${empty ord.dosage2?"":"".concat(ord.dosage2).concat("") }${empty ord.dosage_units?"":"".concat(ord.dosage_units).concat("") }　${ord.administration }　${ord.frequency}</em> 
								</c:if>
							</c:forEach></b>
					 </p>
		             <p>审查结果：<b> ${iteminfo.description }</b>
		             <c:if test="${iteminfo.sort_name eq 'TPN'}">
		            <a href="javascript:void(0);" style="font-size: 14px;"  onclick="setTPN('${iteminfo.id}',${iteminfo.sort_id},'${$return.ipc_auto_id}')">【TPN详情】</a>
		            </c:if>
		             </p>
		             <span class="meta"> ${iteminfo.reference }</span>
	         	</div>
     	</c:forEach>
		</div>
		<div id="tab2" style="display: block;">
			<p class="top-p shenc-result comment_result_p">药师审方结果：<span style="font-size: 15px;">
				<c:choose>
					<c:when test="${$return.auto.state eq 'Y'}">通过</c:when>
					<c:when test="${$return.auto.state eq 'N'}">驳回</c:when>
					<c:when test="${$return.auto.state eq 'D'}">医生决定</c:when>
					<c:when test="${$return.auto.state eq 'DS'}">医生决定(医生已再次确认用药)</c:when>
					<c:when test="${$return.auto.state eq 'DN'}">医生决定(医生取消用药)</c:when>
					<c:otherwise>
						未提交给药师审查
					</c:otherwise>
				</c:choose>
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
           			<c:forEach items="${$return.comment_orders}" var="orders" >
           				<c:if test="${orders.order_no eq og.order_no}">
           				<c:if test="${not empty index_o}">、</c:if>
						【<span style="font-size: 14px;" class="ypsms" data-id="${orders.order_code }"><a  href="javascript:void(0)"  
        	    			 class="mylink">
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
				<div>
					<c:forEach items="${$return.comments}" var="comment">
						<c:if test="${comment.order_no eq og.order_no}">
							<c:if test="${empty num}">
							<c:set var="num">1</c:set>
							药师意见：
							<c:if test="${not empty comment.comment_content}">
								<span style="color:#cd2d2d;">【${comment.comment_content}】</span>
							</c:if><br>
							<div id="comment_sys_qustion">
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
			                <%-- <a href="javascript:void(0);"  onclick="seeopen('${iteminfo.check_result_info_id}',${iteminfo.sort_id})">查看详情</a> --%>
			             </p>
			             <p>
		                    <span class="doctor_comment">强制使用理由:</span>
		                    <c:if test="${not empty iteminfo.doc_advice && 
		                    			 not empty iteminfo.doc_other_advice}">
		                    <span class="doctor_comment">【${iteminfo.doc_advice}${fn:substring(iteminfo.doc_other_advice,0,fn:length(iteminfo.doc_other_advice)-1)}】</span>
		                    </c:if>
		                    <c:if test="${not empty iteminfo.doc_advice && 
		                    			 empty iteminfo.doc_other_advice}">
		                    <span class="doctor_comment">【${fn:substring(iteminfo.doc_advice,0,fn:length(iteminfo.doc_advice)-1)}】</span>
		                    </c:if>
		                    <c:if test="${empty iteminfo.doc_xadvice && 
		                    			 not empty iteminfo.doc_other_advice}">
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
										</span><em>${empty ord.dosage2?"":"".concat(ord.dosage2).concat("") }${empty ord.dosage_units?"":"".concat(ord.dosage_units).concat("") }　${ord.administration }　${ord.frequency}</em> 
									</c:if>
								</c:forEach></b>
						 </p>
			             <p>审查结果：<b> ${iteminfo.description }</b>
			             <c:if test="${iteminfo.sort_name eq 'TPN'}">
			            <a href="javascript:void(0);" style="font-size: 14px;"  onclick="setTPN('${iteminfo.id}',${iteminfo.sort_id},'${$return.ipc_auto_id}')">【TPN详情】</a>
			            </c:if>
			             </p>
			             <span class="meta"> ${iteminfo.reference }</span>
		         		 </div>
		         	</c:if>
		         	</c:if>
		         	</c:forEach>
			     	</c:forEach>
				</div>
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

	function seeopen(check_result_info_id,sort_id){
		var id = '${$return.ipc_auto_id}';
		$.modal("/w/ipc/auditresult/particulars_ywlsb.html","详情",{
			width: '910px',
			height: '600px',
			auto_audit_id: id,
			sort_id: sort_id,
			check_result_info_id: check_result_info_id
		});
	}
	
	function topatient(check_result_info_id,sort_id){
	    var patient_id = '${$return.patient.patient_id}';
		var visit_id = '${$return.patient.visit_id}';
		$.modal("/w/hospital_common/patient/index.html","患者详情",{
			width: '847px',
			height: '610px',
			patient_id: patient_id,
			visit_id: visit_id
		});
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
			  content: "/w/hospital_common/showturns/indexinfo.html?auto_audit_id="+ipc_auto_id+"&&sort_id="+sort_id+"&&check_result_info_id="+check_result_info_id+"&&history=1"
		}); 
	}
</script>
