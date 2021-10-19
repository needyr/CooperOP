<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
	pageContext.setAttribute("wait_timeout", SystemConfig.getSystemConfigValue("hospital_common", "wait_time", "30"));
	pageContext.setAttribute("wait_timeout2", SystemConfig.getSystemConfigValue("hospital_common", "wait_time2", "30"));
	pageContext.setAttribute("notice_pharmacist_notwork", SystemConfig.getSystemConfigValue("hospital_common", "notice_pharmacist_notwork", "当前无药师在线，由医生决定是否用药"));
	pageContext.setAttribute("notice_doctor", SystemConfig.getSystemConfigValue("hospital_common", "notice_doctor","公告：后台药师正在审查，审查完成后，将会弹出结果提示"));
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
        	 <li class="nav-header">
							问题分类
						</li> 
        	<c:forEach items="${$return.items }" var="item">
						<li class="nav-content">
							<a href="#question_${item.sort}" data-item-id="${item.sort }"> ${item.sort_name } (<b>${item.num }</b>)</a>
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
	<a href="javascript:void();" class="active" id="shenc" onclick="shenc();">智能审查结果</a>
	</div>
	<div class="card-tabs-stack graygreen">
		<div id="tab2" style="display: block;">
		<c:forEach items="${$return.item_info }" var="iteminfo" varStatus="st">
	     			<c:forEach items="${$return.orders }" var="ord">
						<c:if test="${ord.sys_order_status eq '0' and fn:indexOf(iteminfo.order_id.concat(','), ord.p_key.concat(',')) > -1 and iteminfo.level >= iteminfo.interceptor_level or iteminfo.level >= iteminfo.info_level}">
						</c:if>
					</c:forEach>
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
		                 <i class="falike fa fa-thumbs-o-up likeup" data-id="${iteminfo.id}"></i>
		                 <i class="falike fa fa-thumbs-o-down hatedown" data-id="${iteminfo.id}"></i>
		                <a href="javascript:void(0);"  onclick="seeopen('${iteminfo.id}','${iteminfo.sort_id}')">查看详情</a>
		             </p>
		             <p id="dorctor_comment">
	                    
	                 </p>
		             <p>医嘱：<b>
		             			<c:forEach items="${$return.orders }" var="ord">
								<c:if test="${fn:indexOf(iteminfo.order_id.concat(','), ord.p_key.concat(',')) > -1  }">
									<span style="font-size: 14px;" class="ypsms" data-id="${ord.order_code }">
									【
									<c:if test="${ord.xmz eq '是' }">
									<!-- <i class="glyphicon glyphicon-warning-sign" style="color: red" title="高危药品"></i> -->
									<span class="gwyp" title="高危药品">高危</span>
									</c:if>
									${ord.order_text }
									】
									</span><em>${empty ord.dosage2?"":"".concat(ord.dosage2).concat("") }
									${empty ord.dosage_units?"":"".concat(ord.dosage_units).concat("") }　${ord.administration }　${ord.frequency}</em> 
								</c:if>
							</c:forEach></b>
					 </p>
		             <p>结果：<b style="color: #a50606; font-size: 16px;"> ${iteminfo.description }</b></p>
		             <span class="meta"> ${iteminfo.reference }</span>
	         	</div>
     	</c:forEach>
		</div>
	</div>
	</div>
    
     <div class="botom">
                <div class="content" id="qadivce" style="display:none;">
                        <div class="main">
                        <%-- <p class="titleFirst">${$return.advices[1].name}</p> --%>
                        <c:forEach items="${$return.items }" var="item" varStatus="status">
                           <c:if test="${item.check_result_state != 'N'}">
                           <div class="list">
                                <p class="title" id="titles">${item.sort_name }</p>
                                <c:forEach items="${$return.advices}" var="av" >
                                <c:if test="${av.apa_check_sorts_name == item.sort_name}">
                                <c:choose>
                                   <c:when test="${av.is_must == 1}">
                                       <div class="checkbox${status.index}">
                                      <input type="checkbox" name="Fruit" 
                                       				data-id="${av.detail_id}" value="${av.usereasondetail_name}" data-sort-id="${item.sort}" class="commentcheckbox1" id="bindcheckbox${av.detail_id}"/>
                                      <label for="bindcheckbox${av.detail_id}" style="font-size: 12px;font-weight: 400;">${av.usereasondetail_name}</label>
                                       </div>
                                   </c:when>
                                   <c:otherwise>
                                       <div>
                                       <input type="checkbox" name="Fruit" value="${av.usereasondetail_name}" 
                                            		data-id="${av.detail_id}" data-sort-id="${item.sort}" id="bindcheckbox${av.detail_id}" class="commentcheckbox1"/>
                                       <label for="bindcheckbox${av.detail_id}" style="font-size: 12px;font-weight: 400;">${av.usereasondetail_name}</label>
                                       </div>
                                   </c:otherwise>
                                </c:choose>
                                </c:if>
                                </c:forEach>
                                <p class="optionText"><label style="font-size: 12px;font-weight: 400;">其他:</label><input type="text" class="adviceFruit" name="adviceFruit" data-sort-id="${item.sort}" data-sort-name="${item.sort_name}"></p>
                            </div>
                            </c:if>
                            <label class="advicelabel"><textarea class="doc_advice1" data-id="${item.sort}"></textarea></label>
                            <label class="advicelabel"><textarea class="doc_advice2" data-id="${item.sort}"></textarea></label>
                        </c:forEach>
                        </div>
                        <button class="qzqd" onclick="qzqd();">提交</button>
                    </div>
				<!-- </div> -->
				<div class="btn-content">
	    	    </div>
	  </div>
 </div>
</body>
<script type="text/javascript">
//查看详情
	 function seeopen(check_result_info_id,sort_id){
	 layer.open({
		  type: 2,
		  title: "医策智能辅助决策支持  -审查结果详情",
		  area: ['910px', '600px'], //宽高
		  content: "/w/ipc/auditresult/particulars.html?auto_audit_id=${$return.ipc_auto_id}&&sort_id="+sort_id+"&&check_result_info_id="+check_result_info_id
	}); 
	} 
 //验证强制使用的必填
 var boo = true;
 //点赞方法
 function goodlike(){
	 $(".falike").on("click",function(){
		  var id=$(this).attr("data-id");
		  //alert(id);
		  if($(this).index()==1){
				 if($(this).hasClass("fa-thumbs-up")){
					 //赞取消     =不踩不赞
					 $(this).removeClass("fa-thumbs-up");
					 $.call("ipc.auditresult.goodlike",{"id":id,"good_reputation":"0"},function(e){},
							function(e){}, {async: false, remark: false   })
				 }else{
					 //赞
					 $(this).addClass("fa-thumbs-up").siblings().removeClass("fa-thumbs-down");
					 $.call("ipc.auditresult.goodlike",{"id":id,"good_reputation":"1"},function(e){},
								function(e){}, {async: false, remark: false   })
				 }
			 }else{
				 if($(this).hasClass("fa-thumbs-down")){
					 //踩取消
					 $(this).removeClass("fa-thumbs-down");
					 $.call("ipc.auditresult.goodlike",{"id":id,"good_reputation":"0"},function(e){},
								function(e){}, {async: false, remark: false   })
				 }else{
					 //踩
					 $(this).addClass("fa-thumbs-down").siblings().removeClass("fa-thumbs-up");
					 $.call("ipc.auditresult.goodlike",{"id":id,"good_reputation":"2"},function(e){},
								function(e){}, {async: false, remark: false   })
				 }
			 }
	 })
 }
 


 $(function(){
	var pstate = '${param.state}';
	$(".advicelabel").hide();
	if(pstate == 'T'){
		$("#qd2").show();
		$("#qd2").text("已阅读");
		$("#sqmyy").hide();
		$("#qx").hide();
		$("#qd1").hide();
		$("#qsave").hide();
		$("#qd2").on("click", function(){
			closeModal({flag: "7", state: "1", "use_flag": "1"});
		});
	}
	
	
	 goodlike();
	 	//打开药品说明书
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
		

	 	$(".nav-content").click(function(){
	 		$(this).addClass("li-active").siblings().removeClass("li-active");
	 	});
	 	
		$("#qadivce").find("input[name='Fruit']").on("click", function(){
			var $this = $(this);
			var addr=$(".doc_advice1[data-id='"+$this.attr('data-sort-id')+"']");
			var addr2=$(".doc_advice2[data-id='"+$this.attr('data-sort-id')+"']");
			var key=$this.attr("data-id");
			if($this[0].checked){
				addr2.val(addr2.val()+$this.val()+"，");
				addr.val(addr.val()+key+",");
			}else{
				addr2.val(addr2.val().replace($this.val()+"，", ""));
				addr.val(addr.val().replace(key+"，", ""));
			}
		});
        
		
        //加载完成后去执行逻辑
        var length= $('.list').length;
        //是否必填
        for(i=0;i<length;i++){
            var isExist= $('div.checkbox'+i).length;
            var checked = $('div.checkbox'+i+' input[type=checkbox]:checked').length;
            if(isExist>0 && checked<=0){
                $('p.title:eq('+i+')').append("&nbsp<span><font color=\"red\" style=\"font-size: 20px;top: 6px; position: relative;\">*</font></span>");
                if($('input[name="adviceFruit"]').eq(i).val() == null || $('input[name="adviceFruit"]').eq(i).val() == ''){
                    boo = false;
                }
            }
        }
        $("#dianp").hide();
        
 })
 
 	
 
	 //病人详情
	 function topatient(){
		 layer.open({
			  type: 2,
			  title: "医策智能辅助决策支持  -患者详情",
			  //skin: 'layui-layer-rim', //加上边框
			  area: ['847px', '610px'], //宽高
			  content: "patientdetail.html?patient_id=${$return.patient.patient_id}&&visit_id=${$return.patient.visit_id}&&doctor_no=${$return.patient.doctor_no}"
		}); 
	 } 
 	//console.log($("#qadivce").html())
	 //等待时间
    var timeo = '${wait_timeout}';
    var timeo2 = '${wait_timeout2}';
    var notice_doctor = '${notice_doctor}';
	var state = "W";
	var advice;
	var yaoshi="";
	var index;
	var id = '${$return.ipc_auto_id}';
	var doctor_no = '${$return.patient.doctor_no}';
	function qsave(){
		 index = layer.open({
		  type: 1,
		  title: "强制使用需要填写意见说明",
		  //skin: 'layui-layer-rim', //加上边框
		  area: ['500px', '438px'], //宽高
		  content: $("#qadivce")
		});
	}
	var id1; 
	function showAdvice(){
		id1 = layer.open({
		  type: 1,
		  title: "药师审查结果",
		  //skin: 'layui-layer-rim', //加上边框
		  area: ['400px', '300px'], //宽高
		  content: $("#rg_advice")
		});
	}

</script>
