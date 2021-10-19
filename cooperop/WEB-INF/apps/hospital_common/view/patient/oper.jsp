<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<head>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery.cokie.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/jquery.json.min.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js" type="text/javascript"></script>
	<script src="${pageContext.request.contextPath}/theme/scripts/common.js" type="text/javascript"></script>
	<link href="${pageContext.request.contextPath}/res/hospital_common/css/pat/oper.css" rel="stylesheet" type="text/css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/font-awesome/css/font-awesome.min.css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/simple-line-icons/simple-line-icons.min.css">
</head>
<body>
	<div id="wrapper">
		<!-- 收费项目 -->
        <div class="shoushu span-content ss">
        	<div class="left-tb" style="width: 50%; height: 455px; border:0px solid; float: left; overflow: auto;">
                <table class="tizheng_table mytable">
                	<c:if test = "${not empty $return.operation}">
                	<thead class="bhead">
                		<tr>
                        <th style="width: 60px">编号</th>
                        <!-- <th style="width: 60px">所在科室代码</th> -->
                        <th style="width: 60px">术前诊断</th>
                        <th style="width: 60px">手术等级</th>
                        <th style="width: 60px">Ⅰ助</th>
                        <th style="width: 60px">所在科室</th>
                        <th style="width: 60px">手术科室</th>
                        <th style="width: 100px">手术操作人</th>
                        <th style="width: 170px">开始时间</th>
                        <th style="width: 170px">截止时间</th>
                    </tr>
                    </thead>
                	</c:if>
                    <c:forEach items = "${$return.operation}" var = "ope" varStatus = "vs" >
                    <tr class="data_tr" data-id="${ope.oper_id}">
                        <td style="width: 60px" class="oper_item">${ope.oper_id}</td>
                        <%-- <td style="width: 60px">${ope.dept_stayed}</td> --%>
                        <td style="width: 60px" class="oper_item">${ope.diag_after_operation}</td>
                        <td style="width: 60px" class="oper_item">${ope.operation_scale}</td>
                        <td style="width: 60px" class="oper_item">${ope.first_assistant}</td>
                        <td style="width: 60px" class="oper_item">${ope.dept_name}</td>
                        <td style="width: 60px" class="oper_item">${ope.oper_dept_name}</td>
                        <td style="width: 100px" class="oper_item">${ope.operator_name}</td>
                        <td style="width: 170px" class="oper_item">${ope.start_date_time}</td>
                        <td style="width: 170px" class="oper_item">${ope.end_date_time}</td>
                    </tr>
                    </c:forEach>
                </table>
            </div>
            
      		<div class="right-content"  style="width: 50%; height: 455px; border:0px solid; float: left; overflow: auto;">
      			<table id="ssdetail" class="mytable">
	      			
      			</table>
      		</div>
        </div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		$(".shoushu .left-tb table .data_tr").click(function(){
			$(this).css("background-color","#a5d8af").siblings().css("background-color","");
			var oper_id = $(this).attr("data-id");
			$.call("hospital_common.showturns.queryOperDetil",{"oper_id":oper_id,"visit_id":'${param.visit_id}',"patient_id":'${param.patient_id}'},function(rtn){
				//console.log(rtn);
				if(rtn!=null&&typeof(rtn)!= "undefined"){
					 $("#ssdetail").empty();
	                 $("#ssdetail").append("<tr><th>编号</th><th>手术编码</th><th style='width: 200px;'>手术</th><th>手术名称</th><th>手术规模</th><th>创伤等级</th></tr>");
					 $.each(rtn.operdetail,function(i,v){
					     $("#ssdetail").append("<tr><td>"+v.oper_id+"</td><td>"+v.operation_no+"</td><td>"+v.operation+"</td><td>"+(v.operation_name==null?"":v.operation_name)+"</td><td>"+(v.operation_scale==null?"":v.operation_scale)+"</td><td>"+(v.woundgrade_name==null?"":v.woundgrade_name)+"</td></tr>");
					});
				}else{
					 $("#ssdetail").append("未查询到信息！");
				} 
				
			},function(e){}, {async: false, remark: false   })
		});
		$('.oper_item').eq(0).click();
	})
</script>