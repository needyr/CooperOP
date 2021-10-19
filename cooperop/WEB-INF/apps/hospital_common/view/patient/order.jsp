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
	<link href="${pageContext.request.contextPath}/res/hospital_common/css/pat/order.css" rel="stylesheet" type="text/css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/font-awesome/css/font-awesome.min.css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/simple-line-icons/simple-line-icons.min.css">
</head>
<body>
	<div id="wrapper">
		<div class="yizhu span-content yz scroll-div" id="scroll-div_yz">
			<table>
			<thead>
				<tr class="position-th-fixed" id="thead_yz">
	                	<th style="width: 60px;">组</th>
	                    <th style="width: 60px;">长/临</th>
	                    <th style="width: 60px;">类别</th>
	                    <th style="width: 140px;">下达时间</th>
	                    <th style="width: 140px;">结束时间</th>
	                    <th style="width: 200px;">医嘱内容</th>
	                    <th style="width: 80px;">剂量</th>
	                    <th style="width: 80px;">单位</th>
	                    <th style="width: 80px;">途径</th>
	                    <th style="width: 80px;">频次</th>
	                    <th style="width: 350px;">药品信息</th>
	                    <th style="width: 80px;">嘱托</th>
	                    <th style="width: 140px;">执行时间</th>
	                    <th style="width: 80px;">医生说明</th>
	                    <th style="width: 80px;">医生</th>
	                    <th style="width: 80px;">停止医生</th>
	                    <th style="width: 80px;">护士</th>
                </tr>
                </thead>
                <tbody class="yizhu_tbody">
                	<c:forEach items="${$return.order}" var="ord">
                		<tr>
                			<td style="width: 60px;">${ord.tag}</td>
                			<td style="width: 60px;">${ord.repeat_indicator}</td>
                			<td style="width: 60px;">${ord.order_class_name}</td>
                			<td style="width: 140px;">${ord.enter_date_time}</td>
                			<td style="width: 140px;">${ord.stop_date_time}</td>
                			<td style="width: 200px;">${ord.order_text}</td>
                			
                			<td style="width: 80px;">${ord.dosage}</td>
                			<td style="width: 80px;">${ord.dosage_units}</td>
                			<td style="width: 80px;">${ord.administration}</td>
                			<td style="width: 80px;">${ord.frequence}</td>
                			<td style="width: 350px;">${ord.drug_message}</td>
                			
                			<td style="width: 80px;">${ord.beizhu}</td>
                			<td style="width: 140px;">${ord.start_date_time}</td>
                			<td style="width: 80px;">${ord.freq_detail}</td>
                			<td style="width: 80px;">${ord.doctor}</td>
                			<td style="width: 80px;">${ord.stop_doctor}</td>
                			
                			<td style="width: 80px;">${ord.nurse}</td>
                		</tr>
                	</c:forEach>
                </tbody>
         	</table>
        </div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		$("#scroll-div_yz").scroll(function() {
	         $("#thead_yz").css({"top":$(this).scrollTop()})
	   });
	})
</script>