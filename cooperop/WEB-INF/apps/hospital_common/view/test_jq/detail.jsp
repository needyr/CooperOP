

<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	pageContext.setAttribute("wait_timeout", SystemConfig.getSystemConfigValue("hospital_common", "wait_time", "30"));
	pageContext.setAttribute("wait_timeout2", SystemConfig.getSystemConfigValue("hospital_common", "wait_time2", "30"));
	pageContext.setAttribute("notice_doctor", SystemConfig.getSystemConfigValue("hospital_common", "notice_doctor","后台药师正在审查，审查完成后，将会弹出结果提示"));
	pageContext.setAttribute("id", request.getParameter("id"));
	
%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/hospital_common/css/test_jq_demo.css" >
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
table
{
    border-collapse: collapse;
    margin: 0 auto;
    text-align: center;
    width: 710px
}
table td, table th
{
	text-align: center;
    border: 1px solid #cad9ea;
    color: #666;
    height: 30px;
}
table thead th
{
    background-color: #CCE8EB;
    width: 100px;
}
table tr:nth-child(odd)
{
    background: #fff;
}
table tr:nth-child(even)
{
    background: #F5FAFA;
}
</style>
<body>
 <div class="wk">
 	<!-- 左边导航栏 -->
    <div class="left-content">
        <ul>
        	 <li class="nav-header">
							合理用药审查分类：
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
                <span>${$return.patient.age}岁</span>
                 <span>身高${$return.patient.height}cm</span>
                <span>体重${$return.patient.weight}kg</span>
                 <%-- <span>过敏史：${$return.patient. alergy_drugs}</span> --%>
                <!-- <a href="javascript:void(0);" onclick="topatient();">患者详情</a> -->
            </p>
            <p class="top-p">诊断：<span>${$return.patient.diagnosis}</span></p>
            <p class="top-p">手术：<span>
            <c:forEach items="${$return.opers}" var="oper">
            	${oper.operation} ;
            </c:forEach>
            </span></p>
        </div>
    </div>
    
    <!-- 审查问题消息 -->
     <div class="box-content">
     	
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
		                <%--  <i class="falike fa fa-thumbs-o-up likeup" data-id="${iteminfo.id}"></i>
		                 <i class="falike fa fa-thumbs-o-down hatedown" data-id="${iteminfo.id}"></i>
		                <a href="javascript:void(0);"  onclick="seeopen(${iteminfo.id},${iteminfo.sort_id})">查看详情</a> --%>
		             </p>
		             
		             <p>医嘱信息：<b>
		             			<c:forEach items="${$return.orders }" var="ord">
								<c:if test="${fn:indexOf(iteminfo.order_id.concat(','), ord.p_key.concat(',')) > -1  }">
									<span style="font-size: 14px;" class="ypsms" data-id="${ord.order_code }">【${ord.order_text }】</span><em>${empty ord.dosage2?"":"".concat(ord.dosage2).concat("") }${empty ord.dosage_units?"":"".concat(ord.dosage_units).concat("") }　${ord.administration }　${ord.frequency}</em> 
								</c:if>
							</c:forEach></b>
					 </p>
		             <p>审查结果：<b> ${iteminfo.description }</b></p>
		             <span class="meta"> ${iteminfo.reference }</span>
	         	</div>
     	</c:forEach>
     </div>
 
 
     
     <div class="botom">
                <div class="content" id="qadivce" style="display:none;">
                        <div class="main">
                        <p class="titleFirst">${$return.advices[1].name}</p>
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
					 <%-- <c:forEach items="${$return.orders }" var="ord">
						<c:if test="${ord.sys_order_status eq '0'}">
							<span style="font-size: 14px;" class="ypsms" data-id="${ord.order_code }">【${ord.order_text }】</span><em>${empty ord.dosage2?"":"".concat(ord.dosage2).concat("") }${empty ord.dosage_units?"":"".concat(ord.dosage_units).concat("") }　${ord.administration }　${ord.frequency}</em><br> 
						</c:if>
					</c:forEach> --%>
					<table class="mytable">
						<thead>
							<tr>
								<th>医院药品编号</th>
								<th>医嘱</th>
								<th>剂量</th>
								<th>给药途径</th>
								<th>频率</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${$return.orders }" var="ord">
							<c:if test="${ord.sys_order_status eq '0'}">
								<tr>
								<td>${ord.order_code }</td>
								<td>【${ord.order_text }】</td>
								<td>${empty ord.dosage2?"":"".concat(ord.dosage2).concat("") }${empty ord.dosage_units?"":"".concat(ord.dosage_units).concat("") }</td>
								<td>${ord.administration }</td>
								<td>${ord.frequency}</td>
								</tr>
							</c:if>
							</c:forEach>
						</tbody>
					</table>
	    	    </div>
	  </div>
 </div>
</body>
<script type="text/javascript">
 //验证强制用药的必填
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
			//topass({id: id,doctor_no: doctor_no, state: 'Y'});
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
							  title: $(this).text(),
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
 })
 
 	//查看详情
	 function seeopen(check_result_info_id,sort_id){
	 		 // alert(check_result_info_id);  
	 		 layer.open({
				  type: 2,
				  title: "医策智能辅助决策支持  -审查结果详情",
				  //skin: 'layui-layer-rim', //加上边框
				  area: ['910px', '600px'], //宽高
				 // content: "particulars.html?auto_audit_id=${$return.patient.id}&&sort_id="+sort_id+"&&check_result_info_id="+check_result_info_id
				  content: "/w/ipc/auditresult/particulars.html?auto_audit_id=${$return.ipc_auto_id}&&sort_id="+sort_id+"&&check_result_info_id="+check_result_info_id
						 
			}); 
	} 
 
	 //病人详情
	 function topatient(){
		 layer.open({
			  type: 2,
			  title: "医策智能辅助决策支持  -患者详情",
			  //skin: 'layui-layer-rim', //加上边框
			  area: ['847px', '610px'], //宽高
			  content: "patientdetail.html?patient_id=${$return.patient.patient_id}&&visit_id=${$return.patient.visit_id}"
		}); 
	 } 
 	console.log($("#qadivce").html())
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
		  title: "强制用药需要填写意见说明",
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
	
	
	function qzqd() {
		var length= $('.list').length;
		var message='';
        boo = true;
        //是否必填
        for(i=0;i<length;i++){
            var isExist= $('div.checkbox'+i).length;
            var checked = $('div.checkbox'+i+' input[type=checkbox]:checked').length;
            if(isExist>0 && checked<=0){
                if($('input[name="adviceFruit"]').eq(i).val() == null || $('input[name="adviceFruit"]').eq(i).val() == ''){
                    boo = false;
                    message = $('input[name="adviceFruit"]').eq(i).attr('data-sort-name');
                    break;
                }
            }
        }
        if(boo==false){
            $.message("请填写必填项："+message);
        }else{
        var advices=[];
        $(".doc_advice1").each(function(){
        	advices.push({   
        		'sort_id':$(this).attr("data-id"),
        		'doc_advice_id': $(this).val(),
        		'doc_advice': $('.doc_advice2[data-id="'+$(this).attr('data-id')+'"]').val(),
        		'doc_other_advice': $('.adviceFruit[data-sort-id="'+$(this).attr('data-id')+'"]').val() ||''
        	});
        });
		layer.close(index);
		$("#qsave").remove();
		$("#qx").hide();
		
		$.ajax({
			"async": true,
			"dataType" : "json",
			"type" : "POST",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"cache" : false,
			"url" : "/prescripitonpass",
			"timeout" : 60000,
			"data" : {id: id,doctor_no: doctor_no,advices:$.toJSON(advices),patient_id:'${$return.patient.patient_id}',visit_id:'${$return.patient.visit_id}'},
			"success" : function(rtn) {
				state = rtn.state;
				yaoshi = rtn.yaoshi;
				advice= rtn.yxk_advice;
			},
			"error" : function(XMLHttpRequest, textStatus, errorThrown) {
				
			}
		});
		if(!timeo || timeo == '0'){
			$("#rg_advice").find(".advice-content").html(notice_doctor);
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
					$("#rg_advice").find(".advice-content").text("当前没有空闲药师，稍后药师审查完毕后将推送信息到手机APP，敬请留意！");
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
					"url" : "/prepassresult",
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
								$("#rg_advice").find(".advice-content").text("药师审查完毕后将推送信息到手机APP，敬请留意！");
								$.call("ipc.autoaudit.update", {id: id, is_overtime: "1"}, function(rtn6){});
								$(".qd-button").on("click", function(){
					        		closeModal({flag: "2", state: "1", "use_flag": "0"});//超时
								});
								clearInterval(timer2);
							}else{
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
									$("#rg_advice").find(".advice-content").text("药师审查完毕后将推送信息到手机APP，敬请留意！");
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
			}else {//药师在时间内审查返回，按药师结果返回
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
		}
	}
	function sqmyy(){
		topass({id: id,doctor_no: doctor_no,state: 'DS'});
		closeModal({flag: "5", state: "1", "use_flag": "1"});//双签名强制用药
	}
	function topass(data){
		$.ajax({
			"async": true,
			"dataType" : "json",
			"type" : "POST",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"cache" : false,
			"url" : "/topass",
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
			$.call("hospital_common.showturns.backAdjust",{"id":id,"state":"DB"},function(rtn){
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
		//STATE Y/N，
		//FLAG：0 标识自动审查通过，1自动审查不通过，取消医嘱;2自动审查不通过、强行用药人工审查超时；
		//     3自动审查不通过、强行用药人工审查通过；4自动审查不通过，强行用药人工审查不通过；5药师返回医生决定，医生双签名用药,
		//6药师返回医生决定，医生取消用药,7、审查结果有问题，但只做提示信息）
		//location.href = "message://data?"+JSON.stringify(param);
		if("undefined" != typeof crtech) {
			crtech.callback(xml.join(''), true);
		}
	}
</script>
