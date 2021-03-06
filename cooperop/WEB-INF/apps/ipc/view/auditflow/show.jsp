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
        <!-- ??????????????? -->
        <div class="left-content">
            <ul>
                <c:if test="${not empty $return.hospital_imic.tnum }">
	            <li class="nav-header dhtitle" >??????????????????</li> 
	            <c:forEach items="${$return.hospital_imic.tnum }" var="tnum">
			    <li class="nav-content" title="${tnum.sort_name}">
			        <a href="#question_${tnum.sort_id}" data-item-id="${tnum.sort_id }">${fn:substring(tnum.sort_name, 0, 8)}${fn:length(tnum.sort_name) > 9 ?  '...': ''}(<b>${tnum.num }</b>)</a>
			    </li>
			    </c:forEach>
			    </c:if>
			    <c:if test="${not empty $return.ipc.tnum }">
				<li class="nav-header dhtitle" >??????????????????</li> 
		        <c:forEach items="${$return.ipc.tnum }" var="item">
				<li class="nav-content" title="${item.sort_name }">
				    <a href="#question_${item.sort_id}" data-item-id="${item.sort_id }"> ${item.sort_name } (<b>${item.num }</b>)</a>
				</li>
				</c:forEach>
				</c:if>
            </ul>
    </div>
        <!-- ???????????? -->
        <div class="top-content">
            <div class="top-div">
                <p class="top-p">
                    <span>${$return.patient.patient_name}</span>
	                <span>${$return.patient.sex}</span>
	                <span>${$return.patient.age}</span>
					<c:if test="${not empty $return.patient.height}">
						<span>??????<fmt:formatNumber value="${$return.patient.height}" pattern="#.0" minFractionDigits='0' />cm</span>
					</c:if>
					<c:if test="${not empty $return.patient.weight}">
						<span>??????<fmt:formatNumber value="${$return.patient.weight}" pattern="#.0" minFractionDigits='0' />kg</span>
					</c:if>
	                <%-- <span>????????????${patient. alergy_drugs}</span> --%>
	                <a href="javascript:void(0);" onclick="topatient();">????????????</a>
                </p>
            <!-- ?????????????????? -->
            <c:set var="dia_in" scope="session" value="" />
            <c:forEach items="${$return.diagnosis}" var="diag" >
            	<c:if test="${diag.diagnosis_type eq '1' or diag.diagnosis_type eq '21'}">
            		<c:choose>
            			<c:when test="${empty dia_in}">
	            			<c:set var="dia_in" value="${dia_in.concat(diag.diagnosis_desc)}" />
            			</c:when>
            			<c:otherwise>
            				<c:set var="dia_in" value="${dia_in.concat('???').concat(diag.diagnosis_desc)}" />
            			</c:otherwise>
            		</c:choose>
            	</c:if>
            </c:forEach>
            <p class="top-p">???????????????<span  title="${dia_in}">${dia_in}</span></p>
            
            <!-- ?????????????????? -->
            <c:set var="dia_out" scope="session" value="" />
            <c:forEach items="${$return.diagnosis}" var="diag" >
            	<c:if test="${diag.diagnosis_type eq '2'}">
            		<c:choose>
            			<c:when test="${empty dia_out}">
	            			<c:set var="dia_out" value="${dia_out.concat(diag.diagnosis_desc)}" />
            			</c:when>
            			<c:otherwise>
            				<c:set var="dia_out" value="${dia_out.concat('???').concat(diag.diagnosis_desc)}" />
            			</c:otherwise>
            		</c:choose>
            	</c:if>
            </c:forEach>
            <p class="top-p" title="${dia_out}">???????????????<span >${dia_out}</span></p>
                <p class="top-p">?????????<span>
                <c:forEach items="${$return.opers}" var="oper">${oper.operation} ;</c:forEach>
                </span></p>
            </div>
        </div>
        <div class="h5" id="rg_advice">
		    <span class="advice-title" style="font-size: 16px;"></span>
			<span class="advice-content" style="font-size: 14px;"></span>
			<button class="qd qd-button" id="qd">??????</button>
	    </div>
	    <div class="box-content">
			<div class="card-tabs-bar graygreen">
			    <a href="javascript:void();" class="" id="shenc" onclick="shenc();">??????????????????</a>
			    <a href="javascript:void();" class="active" id="dianp" onclick="dianp();">??????????????????</a>
			</div>
	        <div class="card-tabs-stack graygreen">
	            <div id="tab1" style="display: none;">
		            <!-- ???????????????????????? -->
		            <c:forEach items="${$return.hospital_imic.imic }" var="mr" varStatus="v">
			            <div class="boc-list">
			                <div class="box-list" id="question_${mr.sort_id}" >
							    <p class="fstP">
								    <span>
								        <font  class="level_${mr.sys_level}">&#60;-${mr.sort_name}-&#62; ${mr.level_name} ${mr.level_star}</font>
									</span>
									<i class="falike fa fa-thumbs-o-up likeup" data-id="xxx"></i>
									<i class="falike fa fa-thumbs-o-down hatedown" data-id="xxx"></i>
									<a href="javascript:void(0);"  onclick="imicDetil('${mr.id}','${mr.imic_audit_id}')">????????????</a>
			                    </p>
                            <c:choose>
	                            <c:when test="${mr.item_p_key eq '??????????????????'}">
	                                <!-- <p>???????????????????????????????????????</p> -->
	                            </c:when>
	                            <c:otherwise>
		                            <c:forEach items="${$return.hospital_imic.bitems }" var = "ors">
		                                <c:if test="${mr.item_p_key eq ors.p_key}">
		                        <p>???????????????
					                <b>
						                <span style="font-size: 14px;" class="ypsms" data-id="xxx">???${ors.item_text}???</span>
							            <em>
								            ?????????<fmt:formatNumber value="${ors.price}"></fmt:formatNumber>??????
											?????????<fmt:formatNumber value="${ors.amount}"></fmt:formatNumber>???
											?????????<fmt:formatNumber value="${ors.price}"></fmt:formatNumber>??????
											${ors.dosage} ${ors.dosage_units} ${ors.administration} ${ors.frequency}
							            </em> 
						            </b>
					            </p>
		                                </c:if>
		                            </c:forEach>
	                            </c:otherwise>
	                        </c:choose>
				                <p>???????????????<b>${mr.description}</b></p>
				                <c:if test="${not empty mr.audit_explain}">
				                <p>???????????????<b>${mr.audit_explain}</b></p> 
				                </c:if>
				                <c:choose>
				                    <c:when test="${mr.check_result eq 'N'}">
				                <span class="intercept_c icon-ban" title="?????????????????????????????????????????????????????????">???</span>
				                    </c:when>
				                    <c:when test="${mr.check_result eq 'T'}">
			                    <span class="hint_c fa fa-warning (alias)" title="????????????">???</span>
				                    </c:when>
				                    <c:when test="${mr.check_result eq 'Q'}">
				                <span class="ss_c icon-earphones-alt" title="???????????????">???</span>
				                    </c:when>
				                </c:choose>
			                </div>
			                <!-- ????????????  -->
			                <%-- <c:if test="${mr.check_result eq 'Q'}">
				                <p class="notsure"> ?????????????????????????????????
				                    <label for="is_use${v.index}">
				                        <input type="radio" name = "isuse${v.index}" id="is_use${v.index}" data-choose="0" data-id="${mr.id}" />?????????
				                    </label>
				                    <label for="no_use${v.index}">
				                        <input type="radio" name = "isuse${v.index}" id="no_use${v.index}" data-choose="1" checked="true" data-id="${mr.id}" />??????
				                    </label>
			                    </p>
					        </c:if> --%>
                       </div>
                        </c:forEach>
		            <!-- ???????????????????????? -->
			        <c:forEach items="${$return.ipc.item_info }" var="iteminfo" varStatus="st">
			            <div class="boc-list">
		                    <div class="box-list" id="question_${iteminfo.sort_id}">
	     		                <c:if test="${iteminfo.check_result_state eq 'N' }">
				                <span class="intercept_c icon-ban" title="????????????:??????????????????????????????????????????????????????????????????">???</span>
				                </c:if> 
				                <c:if test="${iteminfo.check_result_state eq 'T' }">
				                <span class="hint_c fa fa-warning (alias)" title="????????????">???</span>
				                </c:if>
		                        <p class="fstP">
		                             <span>
		                                  <font  class="level_${iteminfo.sys_check_level}"> ???${iteminfo.sort_name}??? ${iteminfo.sys_check_level_name} ${iteminfo.star_level }</font>
					                 </span>
		                             <i class="falike fa fa-thumbs-o-up likeup" data-id="${iteminfo.id}"></i>
		                             <i class="falike fa fa-thumbs-o-down hatedown" data-id="${iteminfo.id}"></i>
		                             <a href="javascript:void(0);"  onclick="seeopen('${iteminfo.id}',${iteminfo.sort_id})">????????????</a>
		                         </p>
		                    <p>
		                <span class="doctor_comment">????????????:</span>
		                <c:if test="${not empty iteminfo.doc_advice && not empty iteminfo.doc_other_advice}">
		                <span class="doctor_comment">???${iteminfo.doc_advice}${fn:substring(iteminfo.doc_other_advice,0,fn:length(iteminfo.doc_other_advice))}???</span>
		                </c:if>
		                <c:if test="${not empty iteminfo.doc_advice && empty iteminfo.doc_other_advice}">
		                <span class="doctor_comment">???${fn:substring(iteminfo.doc_advice,0,fn:length(iteminfo.doc_advice)-1)}???</span>
		                </c:if>
		                <c:if test="${empty iteminfo.doc_advice && not empty iteminfo.doc_other_advice}">
		                <span class="doctor_comment">???${fn:substring(iteminfo.doc_other_advice,0,fn:length(iteminfo.doc_other_advice))}???</span>
		                </c:if>
	                    <c:if test="${empty iteminfo.doc_advice and empty iteminfo.doc_other_advice}">
	                    <span class="doctor_comment">???</span>
	                    </c:if>
	                </p>
		            <p>???????????????<b>
		            <c:forEach items="${$return.orders }" var="ord">
				        <c:if test="${fn:indexOf(iteminfo.order_id.concat(','), ord.p_key.concat(',')) > -1  }">
				        <span style="font-size: 14px;" class="ypsms" data-id="${ord.order_code }">
				        ???
				           <c:if test="${ord.xiangm eq '????????????'  and ord.xmz eq '???' }">
			                <!-- <i class="glyphicon glyphicon-warning-sign" style="color: red" title="????????????"></i> -->
				            <span class="gwyp">??????</span>
					    	</c:if>
				            ${ord.order_text }
				        ???
				        </span>
				        <em>${empty ord.dosage2?"":"".concat(ord.dosage2).concat("") }${empty ord.dosage_units?"":"".concat(ord.dosage_units).concat("") }???${ord.administration }???${ord.frequency}</em> 
					    </c:if>
				    </c:forEach></b>
				    </p>
		            <p>???????????????<b> ${iteminfo.description }</b>
		            <c:if test="${iteminfo.sort_name eq 'TPN'}">
		            <a href="javascript:void(0);" style="font-size: 14px;"  onclick="setTPN('${iteminfo.id}',${iteminfo.sort_id},'${$return.ipc.ipc_auto_id}')">???TPN?????????</a>
		            </c:if>
		            </p>
		            <span class="meta"> ${iteminfo.reference }</span>
                </div>
            </div>
     	    </c:forEach>
		        </div>
		        <div id="tab2" style="display: block;">
			       <p class="top-p shenc-result comment_result_p">?????????????????????<span style="font-size: 15px;">
				       <c:if test="${$return.auto.state eq 'Y'}">??????</c:if>
				       <c:if test="${$return.auto.state eq 'N'}">??????</c:if>
				       <c:if test="${$return.auto.state eq 'D'}">????????????</c:if>
				       <c:if test="${$return.auto.state eq 'DS'}">???????????????????????????????????????????????????</c:if>
				       <c:if test="${$return.auto.state eq 'DN'}">???????????????????????????????????????????????????</c:if>
			           </span></p>
			           <div style="float: right;margin-top: -30px;margin-right: 10px;">
				            <input type="checkbox" id="istodoc">
				            <label for="istodoc"style="color: blue;font-weight: 600;" title="">?????????????????????</label>
			            </div>
			           <%-- <p class="top-p" style="color: #ae0202;width:100%;margin-top: 8px;">???????????????<span>${$return.auto.yxk_advice}</span></p> --%>
		               <div id="commentright">
		               <c:forEach items="${$return.ordersgroup}" var="og" >
			              <div class="commentcontent">
				              <p class="comment_result_p">???????????????<span>
					          <c:set var="index_o"></c:set>
           			             <c:forEach items="${$return.comment_orders}" var="orders">
           				            <c:if test="${orders.order_no eq og.order_no}">
           				                <c:if test="${not empty index_o}">???</c:if>
						                ???<span style="font-size: 14px;" class="ypsms" data-id="${orders.order_code }">
						                    <a href="javascript:void(0)" class="mylink">
        	    			                <c:if test="${ord.xiangm eq '????????????'  and ord.xmz eq '???' }">
							                <!-- <i class="glyphicon glyphicon-warning-sign" style="color: red" title="????????????"></i> -->
							                    <span class="gwyp">??????</span>
							                </c:if>
        	    			                ${orders.order_text }
        	    			                </a></span>
        	    			              <em>${orders.dosage}${orders.dosage_units} ${orders.administration} ${orders.frequency}</em>???
        	    		                     <c:set var="index_o">1</c:set>
        	    		                </c:if>
					                </c:forEach><c:set var="index_o"></c:set></span>
				              </p>
				                <div class="comment_result_p">
					                <c:forEach items="${$return.comments}" var="comment">
						                <c:if test="${comment.order_no eq og.order_no}">
							                <c:if test="${empty num}">
							                    <c:set var="num">1</c:set>
							                    ???????????????
							                    <c:if test="${not empty comment.comment_content}">
								    <span style="color:#cd2d2d;">???${comment.comment_content}???</span>
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
							        <span class="intercept_c icon-ban" title="??????????????????"></span>
						                         </c:if> 
						                         <c:if test="${iteminfo.check_result_state eq 'T' }">
							 		<span class="hint_c fa fa-warning (alias)" title="??????????????????"></span>
						                         </c:if>
			                        <p class="fstP">
			                            <span>
			             	                <font  class="level_${iteminfo.sys_check_level}"> ???${iteminfo.sort_name}??? ${iteminfo.sys_check_level_name} ${iteminfo.star_level }</font>
						                </span>
			                            <a href="javascript:void(0);"  onclick="seeopen('${iteminfo.id}',${iteminfo.sort_id})">????????????</a>
			                        </p>
			                        <p>
		                                <span class="doctor_comment">??????????????????:</span>
		                                    <c:if test="${not empty iteminfo.doc_advice && not empty iteminfo.doc_other_advice}">
		                                <span class="doctor_comment">???${iteminfo.doc_advice}${fn:substring(iteminfo.doc_other_advice,0,fn:length(iteminfo.doc_other_advice)-1)}???</span>
		                                    </c:if>
		                                    <c:if test="${not empty iteminfo.doc_advice && empty iteminfo.doc_other_advice}">
		                                <span class="doctor_comment">???${fn:substring(iteminfo.doc_advice,0,fn:length(iteminfo.doc_advice)-1)}???</span>
		                                    </c:if>
		                                    <c:if test="${empty iteminfo.doc_advice && not empty iteminfo.doc_other_advice}">
		                                <span class="doctor_comment">???${fn:substring(iteminfo.doc_other_advice,0,fn:length(iteminfo.doc_other_advice)-1)}???</span>
		                                    </c:if>
		                                    <c:if test="${empty iteminfo.doc_advice and empty iteminfo.doc_other_advice}">
		                                <span class="doctor_comment">???</span>
		                                    </c:if>
		                            </p>
			                        <p>???????????????<b>
			             			    <c:forEach items="${$return.orders }" var="ord">
									        <c:if test="${fn:indexOf(iteminfo.order_id.concat(','), ord.p_key.concat(',')) > -1  }">
									    <span style="font-size: 14px;" class="ypsms" data-id="${ord.order_code }">
										???
										    <c:if test="${ord.xiangm eq '????????????'  and ord.xmz eq '???' }">
										        <!-- <i class="glyphicon glyphicon-warning-sign" style="color: red" title="????????????"></i> -->
										<span class="gwyp">??????</span>
										    </c:if>
										${ord.order_text }
										???
										</span>
										<em>${empty ord.dosage2?"":"".concat(ord.dosage2).concat("") }${empty ord.dosage_units?"":"".concat(ord.dosage_units).concat("") }???${ord.administration }???${ord.frequency}</em> 
									        </c:if>
								        </c:forEach></b>
						            </p>
			                        <p>???????????????<b> ${iteminfo.description }</b>
			                        <c:if test="${iteminfo.sort_name eq 'TPN'}">
						            <a href="javascript:void(0);" style="font-size: 14px;"  onclick="setTPN('${iteminfo.id}',${iteminfo.sort_id},'${$return.ipc.ipc_auto_id}')">???TPN?????????</a>
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

	//ipc????????????
	 function seeopen(check_result_info_id,sort_id){
		 var id = '${$return.ipc_auto_id}';
		 $.modal("/w/ipc/auditresult/particulars.html","??????????????????????????????  -????????????????????????",{
		     width: '910px',
			 height: '600px',
			 auto_audit_id: id,
			 sort_id: sort_id,
			 check_result_info_id: check_result_info_id
		 });
	} 
	
	//imic????????????
	 function imicDetil(imic_info_id, imic_audit_id){
		 var id = '${$return.ipc_auto_id}';
		 $.modal("/w/hospital_imic/auditresult/particulars.html","??????????????????????????????  -????????????????????????",{
		     width: '910px',
			 height: '600px',
			 imic_audit_id: imic_audit_id,
			 imic_info_id: imic_info_id
		 });
	}
	
	//????????????
	function topatient(){
	    var patient_id = '${$return.patient.patient_id}';
		var visit_id = '${$return.patient.visit_id}';
		$.modal( "/w/hospital_common/patient/index.html","??????????????????????????????  -????????????",{
			width: '847px',
			height: '610px',
			patient_id: patient_id,
			visit_id: visit_id
		});
	}
	
	//????????????
	goodlike();
	 function goodlike(){
		 $(".falike").on("click",function(){
			  var id=$(this).attr("data-id");
			  var good_reputation = 0;
			  //alert(id);
			  if($(this).index()==1){
					 if($(this).hasClass("fa-thumbs-up")){//?????????     =????????????
						 $(this).removeClass("fa-thumbs-up");
					 }else{//???
						 $(this).addClass("fa-thumbs-up").siblings().removeClass("fa-thumbs-down");
						 good_reputation = 1;
					 }
				 }else{
					 if($(this).hasClass("fa-thumbs-down")){//?????????
						 $(this).removeClass("fa-thumbs-down");
					 }else{//???
						 $(this).addClass("fa-thumbs-down").siblings().removeClass("fa-thumbs-up");
						 good_reputation = 2;
					 }
				 }
			  $.call("ipc.auditresult.goodlike",{"id":id,"good_reputation": good_reputation},function(e){},
						function(e){}, {async: false, remark: false })
		 })
	 }
	
	function drugsms(his_drug_code){
		$.modal("/w/ipc/auditresult/instruction.html","???????????????",{
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
	
	//TPN??????
	function setTPN(check_result_info_id,sort_id,ipc_auto_id){
		 layer.open({
			  type: 2,
			  maxmin:true,
			  title: "??????????????????????????????  -??????????????????",
			  area: ['90%', '90%'], 
			  content: "/w/hospital_common/showturns/indexinfo.html?auto_audit_id="+ipc_auto_id+"&&sort_id="+sort_id+"&&check_result_info_id="+check_result_info_id
		}); 
	}
</script>
