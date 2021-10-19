<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.json.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/metronic.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/layout.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/res/ipc/css/demo1.css" rel="stylesheet" type="text/css">
<link type="text/css" rel="stylesheet" href="/theme/plugins/font-awesome/css/font-awesome.min.css">
<link type="text/css" rel="stylesheet" href="/theme/plugins/simple-line-icons/simple-line-icons.min.css">
<style>
#shuoms:hover{cursor: pointer;color: #0732ab;}
#shuoms{color: #396dff;}
</style>
<body>
 <div class="wk">
    <div class="top-content">
        <p class="fstP">
                           【${$return.sort.sort_name}】
            <c:forEach items="${$return.drugs}" var="dr" varStatus="v">
            	<c:if test = "${fn:indexOf($return.sort.order_id.concat(','), dr.p_key.concat(',')) > -1  }">
            		${empty dti ? "":"和 "}
            		<a data-id = "${dr.order_code}" class="ypsms">${dr.order_text}</a>
            		<c:set var="dti">1</c:set>
            	</c:if>
            </c:forEach> 
            <a href="javascript:void(0)" onclick="topatient();">患者详情</a>
            <span class="dilek" style="margin-left:25px">
	             <i class="falike2 fa fa-thumbs-o-up" onclick="goodlike(this)" id="good" style="margin-right: 15px;"></i>
	             <i class="falike2 fa fa-thumbs-o-down" onclick="goodlike(this)" ></i>
            </span>
        </p>
        <p class="shsm">${$return.censorship.audit_explain}</p>
        <div class="list">
        	<c:if test = "${not empty $return.patient.diagnosis}">
        		<p><b>病生状态：</b>${$return.patient.diagnosis}</p>
        	</c:if>
        	<c:if test = "${not empty $return.patient.alergy_drugs}">
        		<p><b>过  敏 史 ：</b>${$return.patient.alergy_drugs}</p>
        	</c:if>
            <!-- <p><b>所属类别：</b>暂无</p> -->
            <p><b>不良反应：</b><a href="javascript:void(0);" class="ypsms" data-id="likeup">见说明书</a></p>
           <!--  <p><b>发生频率：</b>暂无</p> -->
            <p><b>严重程度：</b>
					<font  class="level_${$return.sort.sys_check_level }">${$return.sort.sys_check_level_name  }</font>
					<b>
	          		 <font  class="level_${$return.sort.sys_check_level}">${$return.sort.star_level }</font>
	            	</b>
            </p>
            <c:if test = "${not empty $return.sort.description}">
	            <p><b>
	            	摘要信息：</b>${$return.sort.description}
	            </p>
            </c:if>
            <c:if test = "${not empty $return.sort.reference}">
	             <p><b>参考文献：</b>${$return.sort.reference}</p>
            </c:if>
        </div>
    </div>
   <div class="btn-content head">
    <table>
    		<thead>
    			 <tr>
	                <th style="width: 60px;">严重程度</th>
	                <th style="width: 30px;">组</th>
	                <th style="width: 30px;">长/临</th>
	                <th style="width: 30px;">类别</th>
	                <c:if test="${$return.orders[0].p_type eq '1'}">
	                <th style="width: 45px;">医嘱下达时间</th>
	                <th style="width: 45px;">执行时间</th>
	                <th style="width: 45px;">结束时间</th>
	                </c:if>
	                <c:if test="${$return.orders[0].p_type eq '2'}">
	                <th style="width: 135px;">就诊日期</th>
	                </c:if>
	                <th style="width: 237px;">医嘱内容</th>
	                <th style="width: 50px;">剂量</th>
	                <th style="width: 50px;">途径</th>
	                <th style="width: 50px;">频次</th>
	                <th style="width: 60px;">开嘱医生</th>
	                <th style="width: 60px;">医嘱状态</th>
	            </tr>
    		</thead>
    	</table>
   	</div>
    <div class="btn-content btn-content1">
        <table border="">
            <c:forEach items="${$return.orders}" var="oo">
            <tr class="a1 ${empty oo.level?'':'active'}" id="${empty oo.level?'':'topsta'}">
				<%-- <c:if test="${oo.level==null}">
					<td style="width: 60px;" ></td>
				</c:if> --%>
				<td style="width: 60px;">${empty oo.level? "":$return.sort.sys_check_level_name}</td>
				<td style="width: 30px;">${oo.zu}</td>
				 <c:if test="${oo.repeat_indicator eq '0'}">
					<td style="width: 30px;">临</td>
				</c:if>
				<c:if test="${oo.repeat_indicator eq '1'}">
					<td style="width: 30px;">长</td>
				</c:if>
				<c:if test="${oo.repeat_indicator ne '1' && oo.repeat_indicator ne '0'}">
					<td  style="width: 30px;">${oo.repeat_indicator}</td>
				</c:if> 
                <%--  <td>${oo.repeat_indicator}</td>  --%>
				<%-- <td style="width: 30px;">${oo.order_class eq 'A' ? "药品":oo.order_class}</td> --%>
				<td style="width: 30px;">${oo.order_class}</td>
				<c:if test="${$return.orders[0].p_type eq '1'}">
                <td style="width: 45px;">${oo.enter_date_time}</td>
                <td style="width: 45px;">${oo.start_date_time}</td>
                <td style="width: 45px;">${oo.stop_date_time}</td>
                </c:if>
                <c:if test="${$return.orders[0].p_type eq '2'}">
                <td style="width: 135px;">${oo.enter_date_time}</td>
                </c:if>
                <td style="width: 237px;"><a id="shuoms" onclick="yaopin('${oo.order_code}')">${oo.order_text}</a></td>
                <td style="width: 50px;"><fmt:formatNumber type="number" value="${oo.dosage}"  />${oo.dosage_units}</td>
                <td style="width: 50px;">${oo.administration}</td>
                <td style="width: 50px;">${oo.frequency}</td>
                <td style="width: 60px;">${oo.doctor}</td>
                <td style="width: 60px;">${oo.sys_order_status_name}</td>
                <%-- <c:if test="${oo.sys_order_status eq '0'}">
					<td style="color: #00a093;">新开医嘱</td>
				</c:if>
				<c:if test="${oo.sys_order_status ne '0'}">
					<td style="width:">在用医嘱</td>
				</c:if> --%>
            </tr>
            </c:forEach>
        </table>
    </div>
 </div>
</body>
<script type="text/javascript">

//病人详情
function topatient(){
	//去掉前后双引号
	var patient_id='\"${$return.patient.patient_id}\"'.replace(/^\"|\"$/g,'');
    layer.open({
	    type: 2,
	    title: "医策智能辅助决策支持  -病人详细信息",
	    //skin: 'layui-layer-rim', //加上边框
	    area: ['95%', '95%'], //宽高
	    content: "/w/hospital_common/patient/index.html?patient_id="+patient_id+"&visit_id="+'${$return.patient.visit_id}'
    }); 
}

//点赞方法
function goodlike(_this){
	  var id='${param.check_result_info_id}';
	  //alert(id);
	  console.log();
	 if($(_this).attr("id") == 'good'){
		 if($(_this).hasClass("fa-thumbs-up")){
			 //赞取消     =不踩不赞
			 $(_this).removeClass("fa-thumbs-up");
			 $.call("ipc.auditresult.goodlike",{"id":id,"good_reputation":"0"},function(e){},
					function(e){}, {async: false, remark: false   })
		 }else{
			 //赞
			 $(_this).addClass("fa-thumbs-up").siblings().removeClass("fa-thumbs-down");
			 $.call("ipc.auditresult.goodlike",{"id":id,"good_reputation":"1"},function(e){},
						function(e){}, {async: false, remark: false   })
		 }
	 }else{
		 if($(_this).hasClass("fa-thumbs-down")){
			 //踩取消
			 $(_this).removeClass("fa-thumbs-down");
			 $.call("ipc.auditresult.goodlike",{"id":id,"good_reputation":"0"},function(e){},
						function(e){}, {async: false, remark: false   })
		 }else{
			 //踩
			 $(_this).addClass("fa-thumbs-down").siblings().removeClass("fa-thumbs-up");
			 $.call("ipc.auditresult.goodlike",{"id":id,"good_reputation":"2"},function(e){},
						function(e){}, {async: false, remark: false   })
		 }
	 }
 }
	
$(function(){
	 $(".ypsms").on("click", function(){
		if($(this).attr("data-id")){
			var drugcode = $(this).attr("data-id");
			if(drugcode == 'likeup'){
				drugcode = $(".ypsms").eq(0).attr("data-id")
			}
			console.log(drugcode);
			layer.open({
				  type: 2,
				  title:'【'+ $(this).text()+'】说明书',
				  area: ['800px', '580px'], //宽高
				  content: "instruction.html?his_drug_code="+drugcode+"#adverse_reaction"
			});
		}
	});
	
});	

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
</script>