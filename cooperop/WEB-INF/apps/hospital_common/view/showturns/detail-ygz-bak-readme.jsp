<!-- 
	readme
	本备份文件中包含医生等待药师接单，且能实时看到药师审查结果的代码
	@author yanguozhi 2019-05-10 不可删除
 -->

<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<script src="${pageContext.request.contextPath}/theme/scripts/layout.js" type="text/javascript"></script>
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
    width: 450px;
    height: 60px;
    /* border: 1px solid #9b9c9c; */
    text-align: center;
    padding: 3px;
    color: #bb7d0f;
    font-size: 16px;
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
                 <span>身高${$return.patient.height}cm</span>
                <span>体重${$return.patient.weight}kg</span>
                 <%-- <span>过敏史：${$return.patient. alergy_drugs}</span> --%>
                <a href="javascript:void(0);" onclick="topatient();">患者详情</a>
            </p>
            <%-- <p class="top-p">诊断：<span>${$return.patient.diagnosis_desc}</span></p> --%>
            <p class="top-p">入院诊断：<span>
	            <c:if test="${not empty $return.diagnosis}">
		            <c:set value="0" var="fristq"></c:set>
		            <c:forEach items="${$return.diagnosis}" var="diag" >
		            	<c:if test="${diag.diagnosis_type eq 1}">
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
		            	<c:if test="${diag.diagnosis_type eq 2}">
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
            
            <p class="top-p">手　　术：<span>
            <c:forEach items="${$return.opers}" var="oper">
            	${oper.operation} ;
            </c:forEach>
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
			</div>
			<div id="tab2" style="display: block;">
				<!-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^模板开始^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^  -->
				<%-- <div class="boc-list">
					<div class="box-list" id="question_xxx" style="background: #fbffd1;"> -->
					<div class="box-list" id="question_xxx" > 
								<p class="fstP">
									<span>
									<font  class="level_2">&#60;-xxx-&#62; 可疑   ★★</font>
									</span>
									<i class="falike fa fa-thumbs-o-up likeup" data-id="xxx"></i>
									<i class="falike fa fa-thumbs-o-down hatedown" data-id="xxx"></i>
									<a href="javascript:void(0);"  onclick="seeopen('xxx','xxx')">查看详情</a>
				             	</p>
				             <p id="dorctor_comment">
			                    
			                 </p>
			                  <p>医嘱信息：
			                  	<b>
									<span style="font-size: 14px;" class="ypsms" data-id="xxx">
											【<span class="gwyp" title="高危药品">高危</span>xxx】
									</span>
									<em>
										xxxx xxxx　xxx　xxxx
									</em> 
								</b>
							 </p>
				             <p>审查结果：<b>xxx</b></p>
				             <span class="meta"> 没有参考来源</span>
				             <span class="intercept_c icon-ban" title="拦截级别问题">保</span>
				             </div>
				             <p class="notsure"> 
				           		疑似违规，请自行判断违规性：
				             	<label for="is_use">
				             		<input type="radio" name = "isuse" id="is_use" data-choose="0" />未违规
				             	</label>
				             	<label for="no_use">
				             		<input type="radio" name = "isuse" id="no_use" data-choose="1" />违规
				             	</label>
				             </p>
						</div> --%>
				<!-- ^^^^^^^^^^^^^^^^^^^^^^^^^^^模板结束^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^  -->
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
			                  						<p>医嘱信息：${tmpo}
										                  	<b>
																<span style="font-size: 14px;" class="ypsms" data-id="xxx">
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
			                  						<p class="cfyy">医嘱信息：
									                  	<b>
															<span style="font-size: 14px;" class="ypsms" data-id="${ors.item_code}">
																	【${ors.item_text}】
															</span>
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
				             <p>审查结果：<b>${mr.description}</b></p>
				             <c:if test="${not empty mr.audit_explain}">
				             	<p>监控说明：<b>${mr.audit_explain}</b></p> 
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
						<!-- 限专药转自费  -->
						<c:if test="${(mr.check_result eq 'N' or mr.check_result eq 'Q') and mr.sort_id eq '05'}">
							<p class="notsure"> 
				             	<label for="is_use${v.index}">
				             		<input type="checkbox" name = "isuse${v.index}" id="is_use${v.index}" data-pkey="${mr.item_p_key}"  data-choose="0" data-id="${mr.id}" />
				             		患者自愿自费使用
				             	</label>
			             	</p>
							<%-- <p class="notsure"> 
				           		是否患者自愿自费使用：
				             	<label for="is_use${v.index}">
				             		<input type="radio" name = "isuse${v.index}" id="is_use${v.index}" data-choose="0" data-id="${mr.id}" />未违规
				             	</label>
				             	<label for="no_use${v.index}">
				             		<input type="radio" name = "isuse${v.index}" id="no_use${v.index}" data-choose="1" checked="true" data-id="${mr.id}" />违规
				             	</label>
			             	</p> --%>
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
			             	<p>审查结果：<b> ${iteminfo.description }</b></p>
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
        	<%-- <div class="main">
        		<c:if test="${not empty $return.hospital_imic.tnum}">
        			<c:forEach items="${$return.hospital_imic.tnum}" var="imc">
	        			<div class="list">
	                	<p class="title" id="titles2">
	                		${imc.sort_name}&nbsp;
	                	</p>
	                       	<!-- <div class="checkbox0">
	                           	<input type="checkbox" name="Fruit" data-id="106" value="临床治疗需要使用" data-sort-id="80" class="commentcheckbox1" id="bindcheckbox106">
	                           	<label for="bindcheckbox106" style="font-size: 12px;font-weight: 400;">临床治疗需要使用</label>
	                          </div>
	                       	<div class="checkbox0">
	                         	<input type="checkbox" name="Fruit" data-id="107" value="临床经验用法" data-sort-id="80" class="commentcheckbox1" id="bindcheckbox107">
	                         	<label for="bindcheckbox107" style="font-size: 12px;font-weight: 400;">临床经验用法</label>
	                         </div> -->
	                    <p class="optionText">
		                    <label style="font-size: 12px;font-weight: 400;">其他:</label>
		                    <input type="text" class="adviceFruit" name="adviceFruit" data-sort-id="${imc.sort_id}" data-sort-name="${imc.sort_name}">
	                    </p>
	                </div>
        		</c:forEach>
        		</c:if>
            </div> --%>
            
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
			<div id="report" style="display : none"></div>
			<a class="caozuo2 qd-button" id="qd2">确定</a>
			<a class="sqmyy" id="sqmyy" onclick="sqmyy();">用药问题再次确认</a>
			<a class="qz" id="qsave" onclick="qsave();" title="将此医嘱提交给药师审查">强制使用</a>
	        <a class="qx" id="qx" onclick="qx();" title="返回到开医嘱界面修改医嘱">返回调整</a>
	        <!-- <a class="caozuo1" id="qd1" onclick="showAdvice();">查看药师审查结果</a> -->
   	    </div>
	</div>
 </div>
</body>
<script type="text/javascript">
 var prlv = {};
 prlv.prts = eval('(${$return.prts})');
 prlv.mangerlv = '${$return.mangerlv}';
 var fstate = '${param.state}';
 var rtnflag = eval('(${$return.initst})');

/*  var ipc_state = '${$return.ipc.state}';
 var imic_state = '${$return.hospital_imic.state}'; */

 //验证强制使用的必填
 var boo = true;
 var commid = '${param.id}';
 var auto_audit_id = '${$return.ipc.ipc_auto_id}';
 var imic_audit_id = '${$return.hospital_imic.imic_audit_id}';
 $(function(){
	  $('input[type="checkbox"]').click(function(){
			//@description 申诉问题时 不需要选择 直接填用药理由
			 var choose = $(this).prop('checked');
			 var istozf = 0 ;
			 if(choose){
				 istozf = 1 ;
			 }
			 var pkey = $(this).data('pkey');
			 //$.call('hospital_imic.auditresult.toSelfPaid', {'imic_audit_id': imic_audit_id, 'p_keys': pkey, 'toselfpaid': istozf}, function(rtn){},function(e){},{async: false, remark: false});
			 $.call('hospital_imic.auditresult.toSelfPaid', {'imic_audit_id': imic_audit_id, 'p_keys': pkey, 'toselfpaid': istozf}, function(rtn){},function(e){}, {async: false, remark: false});
	  }); 
	/*  $('input[type="radio"]').click(function(){
		//@description 申诉问题时 不需要选择 直接填用药理由
		 var wgnum = $(":radio[data-choose='1']:checked").length;
		 //检查是否有选择违规的
		 //alert('当前有 '+ wgnum + ' 个选择违规');
		 var imic_info_id = $(this).data('id');
		 var choose = $(this).data('choose');
		 if(wgnum == 0){
			 operation('op4');
		 }else{
			 operation('op2');
		 }
		 $.call('hospital_common.showturns.iMicChoose', {'imic_info_id': imic_info_id, 'doctor_choose': choose}, function(e){},
				function(e){}, {async: false, remark: false   }); 
	 	}); */
	/* 公告说明
	if(is_work == false){
		$('#report').addClass('hasreport').text('${notice_pharmacist_notwork}');
	} */
	//showResultInfo();
	$(".advicelabel").hide();
	//根据结果判断按钮状态
	if(fstate == 'N'){
		//驳回时不能进行强制使用（除非仅包含限专类别问题）
		var onlyxz = true ;
		<c:forEach items="${$return.hospital_imic.tnum }" var="tmum">
				<c:if test = "${tmum.sort_id ne '05' and tmum.check_result_state eq 'N'}">
					onlyxz = false ;
				</c:if>
		</c:forEach>
		if(!onlyxz || ipc_state == 'HL_B'){
			operation('op3');
		}
	}else if(fstate == 'T'){
		operation('op1');
	}
	
	//判断医嘱是否重复提交
    var dataAudit;
	$.call('hospital_common.showturns.hadSubmit', {auto_audit_id: '${$return.ipc_auto_id}'} , function(rtnhas){
		dataAudit = rtnhas.rtnmap;
	},function(e){}, {async: false, remark: false}); 
	if(dataAudit.flag == 1){
		$("#qd2").show();
		$("#qd2").text("已阅读");
		$("#sqmyy").hide();
		$("#qx").hide();
		$("#qd1").hide();
		$("#qsave").hide();
		//$("#rg_advice").find(".advice-content").html('<font class="ntcinfo">'+dataAudit.message+'</font>');
		$("#qd2").on("click", function(){
			layer.open({
			  title: "系统提示",
			  area: ['400px', '200px'], 
			  btn: ['处理','返回'],
			  closeBtn: 0,
			  content: '<font class="ntcinfo">'+dataAudit.message+'</font>',
			  yes: function(index, layero){
				//重复保存，存储为返回调整
	    		$.call("hospital_common.showturns.backAdjust",{'id': id, 'state': 'DB', 'order_flag': '9'},function(rtn){
					closeModal({flag: "1", state: "1", "use_flag": "0"});
				});
			  },
			  btn2: function(index, layero){
				  layer.close(index);
			  }
			});
		});
		/* layer.open({
		  title: "系统提示",
		  area: ['400px', '300px'], 
		  btn: ['确认'],
		  closeBtn: 0,
		  content: '<div style="background-color: #fff;padding: 19px 12px;min-height: 120px;"><font class="ntcinfo">'+dataAudit.message+'</font></div>',
		  yes: function(index, layero){
			  layer.close(index);
		  }
		}); */
	}
	
	 goodlike();
	 openIns();
	//导航
 	$(".nav-content").click(function(){
 		$(this).addClass("li-active").siblings().removeClass("li-active");
 	});
	
 	var product_advices = eval('('+'${$return.product}'+')');//产品信息
	 	
    //加载完成后去执行逻辑
    var length= $('.list').length;
    //是否必填
 	for(j=0;j<product_advices.length;j++){
 		var product_code = product_advices[j].product_code;
 		append_advice(product_code);
 		//判断是否必填
	    for(i=0;i<length;i++){
	        var isExist= $('div.checkbox'+i+product_code).length;
	        var checked = $('div.checkbox'+i+product_code+' input[type=checkbox]:checked').length;
	        if(isExist>0 && checked<=0){
	            $('p.title[data-product="'+product_code+'"]:eq('+i+')').append("&nbsp<span><font color=\"red\" style=\"font-size: 20px;top: 6px; position: relative;\">*</font></span>");
	            if($('input[name="adviceFruit'+product_code+'"]').eq(i).val() == null || $('input[name="adviceFruit'+product_code+'"]').eq(i).val() == ''){
	                boo = false;
	            }
	        }
	    }
    }
    $("#dianp").hide();
 })
 
 function append_advice(product_code){
	//拼接医生意见
	$("#qadivce").find("input[name='Fruit"+product_code+"']").on("click", function(){
		var $this = $(this);
		var addr=$(".doc_advice1"+product_code+"[data-id='"+$this.attr('data-sort-id')+"']");
		var addr2=$(".doc_advice2"+product_code+"[data-id='"+$this.attr('data-sort-id')+"']");
		var key=$this.attr("data-id");
		if($this[0].checked){
			addr2.val(addr2.val()+$this.val()+",");
			addr.val(addr.val()+key+",");
		}else{
			addr2.val(addr2.val().replace($this.val()+",", ""));
			addr.val(addr.val().replace(key+",", ""));
		}
	});
 }
 
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
	var to_pharmacist = '1' ;
		<c:if test="${empty $return.ipc.tnum }">
			notice_doctor = '您已选择用药，请发送该患者医嘱！';
			to_pharmacist = '0';
		</c:if>
	function qsave(){
		//------------------判断是否已经提交审查start
		/* var dataAudit;
		$.call('hospital_common.showturns.hadSubmit', {auto_audit_id: id} , function(rtnhas){
			dataAudit = rtnhas.rtnmap;
		},function(e){}, {async: false, remark: false}); 
		if(dataAudit.flag == 0){ */
			$.call('hospital_common.showturns.isWork', {}, function(rtn){
				if(rtn.iswork == false && to_pharmacist == '1'){
					//非药师工作时间：医生强制使用自动通过
					$.call("hospital_common.showturns.backAdjust",{"id":'${$return.ipc.ipc_auto_id}',"common_id":commid,"state": "DQ1"},function(rtn){
						//closeModal({flag: "11", state: "1", "use_flag": "1"});
						$("#rg_advice").find(".advice-content").html('<font class="ntcinfo">当前非药师工作时间，系统自动【通过】了您的医嘱/处方，请发送该患者的医嘱/处方</font>');
						id1 = layer.open({
							  type: 1,
							  title: "系统提示",
							  area: ['400px', '300px'], //宽高
							  closeBtn: 0,
							  content: $("#rg_advice")
							});
						$(".qd-button").on("click", function(){
				    		closeModal({flag: "11", state: "1", "use_flag": "1"});//非药师工作时间：医生强制使用自动通过
						});
					});	
				}else{
					index = layer.open({
						  type: 1,
						  title: "强制使用需要填写意见说明",
						  area: ['500px', '438px'], 
						  content: $("#qadivce")
					});
				}
			}); 
		/* }else{
			$("#rg_advice").find(".advice-content").html('<font class="ntcinfo">'+dataAudit.message+'</font>');
			id1 = layer.open({
				  type: 1,
				  title: "系统提示",
				  area: ['400px', '300px'], 
				  closeBtn: 0,
				  content: $("#rg_advice")
				});
			$(".qd-button").on("click", function(){
	    		//重复保存，存储为返回调整
	    		$.call("hospital_common.showturns.backAdjust",{'id': id,"common_id":commid,'state': 'DB', 'order_flag': '9'},function(rtn){
					closeModal({flag: "1", state: "0", "use_flag": "0"});
				});
			});
			return ;
		} */
	}
	var id1; 
	function showAdvice(){
		id1 = layer.open({
		  type: 1,
		  title: "药师审查结果",
		  area: ['400px', '300px'], //宽高
		  content: $("#rg_advice")
		});
	}
	
	//提交到药师审查
	function qzqd() { 
		var length= $('.list').length;
		var message='';
        boo = true;
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
        
        if(boo==false){
            $.message("请填写必填项："+message);
        }else{
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
		layer.close(index);
		$("#qsave").remove();
		$("#qx").hide();
		/* var to_pharmacist = '1' ;
		<c:if test="${empty $return.ipc.tnum }">
			notice_doctor = '您已选择用药，请发送该患者医嘱！';
			to_pharmacist = '0';
		</c:if> */
		$.ajax({
			"async": true,
			"dataType" : "json",
			"type" : "POST",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"cache" : false,
			"url" : "/w/hospital_common/showturns/mustDo.json",
			"timeout" : 60000,
			"data" : {"to_pharmacist": to_pharmacist, id: id,doctor_no: doctor_no,advices:$.toJSON(advices),patient_id:'${$return.patient.patient_id}',visit_id:'${$return.patient.visit_id}', 'common_id': commid},
			"success" : function(rtn) {
				state = rtn.ipc.state;
				yaoshi = rtn.ipc.yaoshi;
				advice= rtn.ipc.yxk_advice;
			},
			"error" : function(XMLHttpRequest, textStatus, errorThrown) {
				
			}
		});
		if(!timeo || timeo == '0'){
			$("#rg_advice").find(".advice-content").html('<font class="ntcinfo">'+notice_doctor+'</font>');
			id1 = layer.open({
				  type: 1,
				  title: "温馨提示",
				  //skin: 'layui-layer-rim', //加上边框
				  area: ['400px', '240px'], //宽高
				  content: $("#rg_advice")
				});
			$(".qd-button").on("click", function(){
        		closeModal({flag: "2", state: "1", "use_flag": "0"});//超时
			});
			return;
		}
		
		var tt = timeo;
		var tt2 = timeo2;
		$("#qd").hide();
		$("#rg_advice").find(".advice-content").html("等待药师审查用药合理性：<span style='font-size: 18px;color:#919090;'>"+tt+"</span> 秒");
		id1 = layer.open({
			  type: 1,
			  title: "温馨提示",
			  //skin: 'layui-layer-rim', //加上边框
			  area: ['400px', '240px'], //宽高
			  content: $("#rg_advice")
			});
		var timer = setInterval(function(){
			--tt;
			if(state == "W"){
				if(tt <=0){
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
					$("#rg_advice").find(".advice-content").html("等待药师审查用药合理性：<span style='font-size: 18px;color:#919090;'>"+tt+"</span> 秒");
				}
			}else if(state == "J"){
				state = "S";
				$("#rg_advice").find(".advice-title").text("药师审查中...");
				$("#rg_advice").find(".advice-content").html("药师 <span style='font-size: 18px;'>【"+yaoshi+"】</span> 正在审查，请稍后！");
				$.ajax({
					"async": true,
					"dataType" : "json",
					"type" : "POST",
					"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
					"cache" : false,
					"url" : "/w/hospital_common/showturns/getPCResult.json",
					"timeout" : 1800000,
					"data" : {id: id,doctor_no: doctor_no},
					"success" : function(rtn) {
						layer.close(id1);
						advice= rtn.yxk_advice;
						state = rtn.state;
						var timer2 = setInterval(function(){
							--tt2;
							if(tt2 <=0 && (state == 'J' || state =='Z')){
								$(".qd-button").show();
								$(".caozuo1").show();
								$("#rg_advice").find(".advice-title").text("药师审查超时");
								$("#rg_advice").find(".advice-content").text("稍后药师审查完毕后，将推送消息，敬请留意！");
								$.call("ipc.autoaudit.update", {id: id, is_overtime: "1"}, function(rtn6){});
								$(".qd-button").on("click", function(){
					        		closeModal({flag: "2", state: "1", "use_flag": "0"});//超时
								});
								clearInterval(timer2);
							}else{
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
									clearInterval(timer2);
								}else if(state == 'N'){
									$("#rg_advice").find(".advice-title").text("药师审查：不通过");
									clearInterval(timer2);
								}else if(state == 'D'){
									$("#rg_advice").find(".advice-title").html("药师审查：返回医生决定");
									advice = advice+"<br /><font color='red' "+
									"style='margin-top: 10px;display:  block;'>您可以点击右下角按钮【执行双签名用药】/【返回调整】，如果强行用药造成的后果由您负责！</font>"
									clearInterval(timer2);
								}else if(state == 'Z'){
									$(".qd-button").show();
									$(".caozuo1").show();
									$("#rg_advice").find(".advice-title").text("药师审查超时");
									$("#rg_advice").find(".advice-content").text("稍后药师审查完毕后，将推送消息，敬请留意！");
									$.call("ipc.autoaudit.update", {id: id, is_overtime: "1"}, function(rtn6){});
									$(".qd-button").on("click", function(){
						        		closeModal({flag: "2", state: "1", "use_flag": "0"});//超时
									});
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
									$(".qd-button").on("click", function(){
										//topass({id: id,doctor_no: doctor_no, state: 'Y'});
										closeModal({flag: "3", state: "1", "use_flag": "1"});//人工通过
									});
								}else if(state == 'N'){
									$(".qd-button").show();
									$(".caozuo1").show();
									$(".qd-button").on("click", function(){
										//topass({id: id,doctor_no: doctor_no, state: 'N'});
										closeModal({flag: "4", state: "0", "use_flag": "0"});//人工不通过
									});
								}else{//设置允许医生双签名用药
									$("#qd").show();
									$(".caozuo1").show();
									$("#qd").on("click", function(){
										layer.close(id1);
									});
									$("#sqmyy").show();
									//取消用药
									$("#qd2").show();
									$("#qd2").text("取消用药");
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
					$("#qd").show();
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
				}else if(state == 'N'){
					$("#rg_advice").find(".advice-title").text("药师审查：不通过");
				}else if(state == 'D'){
					$("#rg_advice").find(".advice-title").html("药师审查：返回医生决定");
					advice = advice+"<br /><font color='red' "+
					"style='margin-top: 10px;display:  block;'>您可以点击右下角按钮【执行双签名用药】/【返回调整】，如果强行用药造成的后果由您负责！</font>"
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
					$("#qd").show();
					$("#qd").on("click", function(){
						closeModal({flag: "3", state: "1", "use_flag": "1"});//人工通过
					});
				}else if(state == 'N'){
					$("#qd").on("click", function(){
						closeModal({flag: "4", state: "0", "use_flag": "0"});//人工不通过
					});
				}else{//设置允许医生双签名用药
					$("#qd").on("click", function(){
						layer.close(i1);
					});
					$("#sqmyy").show();
					$("#qx").show();
				}
				clearInterval(timer);
			}
	    },1000);
		dianp();
        }
	}
	
	//获取审查结果展示数据
	function showResultInfo (){
		var common_id = '${param.id}';
		$.call("hospital_common.", {}, function(rtn){
			
		});
	}
	
	function sqmyy(){
		topass({id: id,doctor_no: doctor_no,state: 'DS'});
		closeModal({flag: "5", state: "1", "use_flag": "1"});//双签名强制使用
	}
	
	function topass(data){
		$.ajax({
			"async": true,
			"dataType" : "json",
			"type" : "POST",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"cache" : false,
			"url" : "/w/hospital_common/showturns/sureAgain.json",
			"timeout" : 60000,
			"data" : data,
			"success" : function(rtn) {
				
			},
			"error" : function(XMLHttpRequest, textStatus, errorThrown) {
				
			}
		});
	}
	
	function qx() {
		if(state == 'D'){
			topass({id: id,doctor_no: doctor_no,state: 'DN'});
			closeModal({flag: "6", state: "0", "use_flag": "0"});//取消
		}else{
			$.call("hospital_common.showturns.backAdjust",{"common_id":commid,"state":"DB"},function(rtn){
				closeModal({flag: "1", state: "0", "use_flag": "0"});//取消
			});
			
		}
	}
	
	function closeModal(param){
		var xml = [];
		xml.push('<RESPONE>');
		xml.push('<STATE>'+param.state+'</STATE>');
		xml.push('<USE_FLAG>'+param.use_flag+'</USE_FLAG>');
		xml.push('<FLAG>'+param.flag+'</FLAG>');
		xml.push('</RESPONE>');
		//STATE 1/0，
		//FLAG：0 标识自动审查通过，1自动审查不通过，取消医嘱;2自动审查不通过、强行用药人工审查超时；
		//     3自动审查不通过、强行用药人工审查通过；4自动审查不通过，强行用药人工审查不通过；5药师返回医生决定，医生双签名用药,
		//6药师返回医生决定，医生取消用药,7、审查结果有问题，但只做提示信息, 11、非要药师工作时间，医生确认用药）
		//location.href = "message://data?"+JSON.stringify(param);
		if("undefined" != typeof crtech) {
			crtech.callback(xml.join(''), true);
		}
	}
	
	//ipc查看详情
	 function seeopen(check_result_info_id,sort_id){
		 layer.open({
			  type: 2,
			  title: "医策智能辅助决策支持  -用药审查结果详情",
			  area: ['910px', '600px'], 
			  content: "/w/ipc/auditresult/particulars.html?auto_audit_id=${$return.ipc.ipc_auto_id}&&sort_id="+sort_id+"&&check_result_info_id="+check_result_info_id
		}); 
	} 
	
	//imic查看详情
	 function imicDetil(imic_info_id, imic_audit_id){
		//alert(imic_info_id);
		layer.open({
			  type: 2,
			  title: "医策智能辅助决策支持  -医保审查结果详情",
			  area: ['910px', '600px'], 
			  content: '/w/hospital_imic/auditresult/particulars.html?imic_audit_id='+imic_audit_id+'&imic_info_id='+imic_info_id
		});  
	} 

	 //患者详情
	 function topatient(){
		 layer.open({
			  type: 2,
			  title: "医策智能辅助决策支持  -患者详情",
			  area: ['847px', '610px'], 
			  content: "patientdetail.html?patient_id=${$return.patient.patient_id}&&visit_id=${$return.patient.visit_id}&&doctor_no=${$return.patient.doctor_no}"
		}); 
	 }
	
	//点赞方法
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
	function openIns(){
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
	
	function operation(operway){
		//提示
		if(operway == 'op1'){
			$("#qd2").show();
			$("#qd2").text("已阅读");
			$("#sqmyy").hide();
			$("#qx").hide();
			$("#qd1").hide();
			$("#qsave").hide();
			$("#qd2").on("click", function(){
				closeModal({flag: "7", state: "1", "use_flag": "1"});
			});
		}else if (operway == 'op2'){
			//灰色强制用药
			$('#qsave').unbind('click');
			$('#qsave').prop('onclick', '');
			$('#qsave').addClass('notclick');
			$('#qsave').click(function (){
				layer.msg('包含违规项，不能执行该操作', {
			 		  icon: 2,
			 		  time: 1000 //2秒关闭（如果不配置，默认是3秒）
			 		}, function(){
			 		  //do something
			 		});
			});
		}else if (operway == 'op3'){
			//仅能返回调整
			$('#qsave').hide();
		}else if (operway == 'op4'){
			//从灰色强制用药变为普通情况
			$('#qsave').unbind('click');
			$('#qsave').prop('onclick', '');
			$('#qsave').removeClass('notclick');
			$('#qsave').click(function (){
				qsave();
			});
		}
	}
</script>
