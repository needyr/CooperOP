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
	<link href="${pageContext.request.contextPath}/res/hospital_common/css/pat/ipc_result.css" rel="stylesheet" type="text/css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/font-awesome/css/font-awesome.min.css">
	<link type="text/css" rel="stylesheet" href="/theme/plugins/simple-line-icons/simple-line-icons.min.css">
</head>
<body>
	<div id="wrapper">
		<div class="tzdianp span-content tzdp" id="scroll-div_tzdianp">
            	<c:choose>
            		<c:when test="${not empty $return.checkandcommentdetail}">
            			<div class="left-tb">
	            			<table class="bhead mytable">
								<thead>
									<tr>
										<th style="width:35px">警</th>
										<th style="width:60px">严重程度</th>
										<th style="width:110px">审查类型</th>
										<th width=300px>药品名称</th>
										<!-- <th>问题来源</th> -->
										<th style="width:110px">患者姓名</th>
										<th style="width:150px">审查时间</th>
										<th width=200px>医生使用理由</th>
										<th style="width:100px">审查来源</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${$return.checkandcommentdetail}" var="cac">
									<tr data-doctor_advice="${cac.doctor_advice}" 
										data-related_drugs_show="${cac.related_drugs_show}" 
										data-sort_name="${cac.sort_name}" 
										data-sys_check_level_name="${cac.sys_check_level_name}" 
										data-description="${cac.description}"
										data-reference="${cac.reference}" data-type="${cac.type}"
										data-yaoshi_advice="${cac.yaoshi_advice}"
										data-yaoshi_name="${cac.yaoshi_name}"
										data-related_drugs_pkey="${cac.related_drugs_pkey}"
										data-patient_id="${cac.patient_id}"
										data-visit_id="${cac.visit_id}"
										data-order_no="${cac.order_no}"
										data-group_id="${cac.group_id}"
										class="data_tr">
										<c:choose>
											<c:when test="${cac.sys_check_level_name eq '不合理'}">
												<td style="color:red;width:35px;font-size: 18px;" title="不合理">×</td>
											</c:when>
											<c:when test="${cac.sys_check_level_name eq '合理'}">
											<td style="color:green;width:35px;font-size: 18px;" title="合理">√</td>
											</c:when>
											<c:when test="${cac.sys_check_level_name eq '争议'}">
											<td style="color:orange;width:35px;font-size: 18px;" title="争议">O</td>
											</c:when>
											<c:when test="${empty cac.check_result_state}">
											<td style="width:35px"><span class="rstate1 fa fa-check-circle-o" title="通过级别问题" ></span></td>
											</c:when>
											<c:when test="${cac.check_result_state eq 'N' }">
											<td style="width:35px"><span class="rstate2 icon-ban" title="拦截级别问题"></span></td>
											</c:when>
											<c:when test="${cac.check_result_state eq 'T' }">
											<td style="width:35px"><span class="rstate3 fa fa-warning (alias)" title="提示级别问题"></span></td>
											</c:when>
											<c:otherwise>
											<td style="width:35px"></td>
											</c:otherwise>
										</c:choose>
										<td style="width:60px">${cac.sys_check_level_name}</td>
										<td style="width:110px">${cac.sort_name}</td>
										<td style="width:300px" class="is_hiddenmore" title="${cac.related_drugs_show}">${cac.related_drugs_show}</td>
										<td style="width:110px">${cac.patient_name}</td>
										<td style="width:150px">${cac.check_datetime}</td>
										<td style="width:200px">${cac.yxk_advice}</td>
										<td style="width:100px">${cac.audit_source_type}</td>
									</tr>
									</c:forEach>
								</tbody>
							</table>
		            </div>
		            <div class="detail_result">
									
					</div>
		            <div class="right-content-demo">
						<div  style="width: 900px;">
							<table class="bhead mytable" id="comment_demo">
								<thead>
									<tr>
										<th width="60px">等级</th>
										<th width="20px">组</th>
										<th width="40px">长/临</th>
										<th width="60px">类别</th>
										<th width="150px">开嘱时间</th>
										<th width="150px">停嘱时间</th>
										<th width="300px">药品名称</th>
										<th width="70px">给药方式</th>
										<!-- <th width="60px">剂型</th> -->
										<th width="60px">剂量</th>
										<th width="60px">单位</th>
										<th width="60px">频次</th>
										<th width="350px">药品信息</th>
										<th width="120px">嘱托</th>
										<th width="100px">开嘱科室</th>
										<th width="70px">开嘱医生</th>
										<!-- <th width="60px">毒理分类</th> -->
									</tr>
								</thead>
								<tbody>
								
								</tbody>
							</table>
						</div>
						</div>
            		</c:when>
            		<c:otherwise>
						<h1 style="text-align: center;line-height: 360px;font-size: 20px;font-weight: 200;color: #a77d2d;">没有查询到数据</h1>
					</c:otherwise>
            	</c:choose>
        </div>
	</div>
</body>
<script type="text/javascript">
	$(function(){
		$('.data_tr').click(function(){
			$(this).css("background-color","#c8ecc9").siblings().css("background-color","");
			var p_keys = $(this).attr("data-related_drugs_pkey");
			var datas = {
				p_keys: p_keys,
				patient_id: $(this).attr("data-patient_id"),
				visit_id: $(this).attr("data-visit_id"),
				order_no: $(this).attr("data-order_no"),
				group_id: $(this).attr("data-group_id")
			}
			var html = [];
			var html2 = [];
			$(".detail_result").empty();
			$(".right-content-demo .bhead tbody").empty();
			if($(this).attr("data-type") == '审查'){
				html.push('<div><span>医生使用理由:</span>'+$(this).attr("data-doctor_advice")+'</div>');
				html.push('<div><span>问题医嘱:</span>'+$(this).attr("data-related_drugs_show")+'</div>');
				html.push('<div><span>审查问题:</span>'+$(this).attr("data-sort_name")+'</div>');
				html.push('<div><span>严重级别:</span>'+$(this).attr("data-sys_check_level_name")+'</div>');
				html.push('<div><span>审查结果:</span>'+$(this).attr("data-description")+'</div>');
				html.push('<div><span>参考文献:</span>'+$(this).attr("data-reference")+'</div>');
			}else{
				html.push('<div><span>点评药师:</span>'+$(this).attr("data-yaoshi_name")+'</div>');
				html.push('<div><span>药师点评意见:</span>'+$(this).attr("data-yaoshi_advice")+'</div>');
				html.push('<div><span>问题医嘱:</span>'+$(this).attr("data-related_drugs_show")+'</div>');
				html.push('<div><span>审查问题:</span>'+$(this).attr("data-sort_name")+'</div>');
				html.push('<div><span>严重级别:</span>'+$(this).attr("data-sys_check_level_name")+'</div>');
				html.push('<div><span>审查结果:</span>'+$(this).attr("data-description")+'</div>');
				html.push('<div><span>参考文献:</span>'+$(this).attr("data-reference")+'</div>');
			}
			$(".detail_result").append(html.join(''));
			$.call("hospital_common.additional.queryRealYizu", datas, function(rtn){
				if(rtn){
					if(rtn.realyizu){
						var v = rtn.realyizu;
						for(i=0;i<rtn.realyizu.length;i++){
							html2.push('<tr class="data_tr">');
							if(v[i].check_level_name){
								html2.push('<td width="60px">'+v[i].check_level_name+'</td>');
							}else{
								html2.push('<td width="60px"></td>');
							}
							html2.push('<td width="20px">'+v[i].tag+'</td>');
							if(v[i].repeat_indicator=='0'){
								html2.push('<td width="40px">临</td>');
							}else if(v[i].repeat_indicator=='1'){
								html2.push('<td width="40px">长</td>');
							}else{
								html2.push('<td width="40px"></td>');
							}
							html2.push('<td width="60px">'+v[i].order_class+'</td>');
							if(v[i].tag == '┍' || v[i].tag == '﹣'){
								if(v[i].enter_date_time){
									html2.push('<td width="150px">'+v[i].enter_date_time+'</td>');
								}else{
									html2.push('<td width="150px"></td>');
								}
								if(v[i].stop_date_time){
									html2.push('<td width="150px">'+v[i].stop_date_time+'</td>');
								}else{
									html2.push('<td width="150px"></td>');
								}
							}else{
								html2.push('<td width="150px"></td>');
								html2.push('<td width="150px"></td>');
							}
							html2.push('<td width="300px"><a onclick="yaopin(\''+v[i].order_code+'\')">'+v[i].order_text+'</a></td>');
							html2.push('<td width="70px">'+v[i].administration+'</td>');
							/* html2.push('<td width="60px">'+v[i].jixing+'</td>'); */
							html2.push('<td width="60px">'+parseFloat(v[i].dosage)+'</td>');
							html2.push('<td width="60px">'+v[i].dosage_units+'</td>');
							html2.push('<td width="60px">'+v[i].frequency+'</td>');
							html2.push('<td width="350px">'+v[i].drug_message+'</td>');
							if(v[i].beizhu){
								html2.push('<td width="120px">'+v[i].beizhu+'</td>');
							}else{
								html2.push('<td width="120px"></td>');
							}
							html2.push('<td width="100px">'+v[i].dept_name+'</td>');
							html2.push('<td width="70px">'+v[i].doctor+'</td>');
							/* html2.push('<td width="60px">'+v[i].property_toxi+'</td>'); */
							html2.push('</tr>');
						}
					}
				}
				$(".right-content-demo .bhead tbody").append(html2.join(''));
			});
		});
		
		$("#scroll-div_tzdianp").scroll(function() {
	         $("#thead_tzdp").css({"top":$(this).scrollTop()})
	   });
		
		$('.data_tr').eq(0).click();
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