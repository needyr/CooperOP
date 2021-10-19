<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
#shuoms:hover{
	cursor: pointer;
	color: #0732ab;
}
#shuoms{
	color: #396dff;
}

.is_hiddenmore{
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
</style>
<body>
 <div class="wk" style="width: calc(100% - 10px)">
   <div class="btn-content head">
    <table>
    		<thead>
    			 <tr>
	                <!-- <th style="width: 60px;">严重程度</th> -->
	                <th style="width: 20px;">组</th>
	                <th style="width: 30px;">长/临</th>
	                <th style="width: 135px;">下达时间</th>
	                <th style="width: 170px;">医嘱内容</th>
	                <th style="width: 40px;">剂量</th>
	                <th style="width: 40px;">途径</th>
	                <th style="width: 40px;">频次</th>
	                <th style="width: 200px;">药品信息</th>
	                <th style="width: 60px;">开嘱医生</th>
	                <th style="width: 60px;">医嘱状态</th>
	            </tr>
    		</thead>
    	</table>
   	</div>
    <div class="btn-content btn-content1" style="height: calc(100% - 50px);">
        <table border="">
            <c:forEach items="${$return.orders}" var="oo">
            <tr class="a1 ${empty oo.level?'':'active'}">
				<%-- <td style="width: 60px;">${empty oo.level? "":$return.sort.sys_check_level_name}</td> --%>
				<td style="width: 20px;">${oo.zu}</td>
				 <c:if test="${oo.repeat_indicator eq '0'}">
					<td style="width: 30px;">临</td>
				</c:if>
				<c:if test="${oo.repeat_indicator eq '1'}">
					<td style="width: 30px;">长</td>
				</c:if>
				<c:if test="${oo.repeat_indicator ne '1' && oo.repeat_indicator ne '0'}">
					<td  style="width: 50px;">${oo.repeat_indicator}</td>
				</c:if> 
				<%-- <td style="width: 30px;">${oo.order_class}</td> --%>
                <td style="width: 135px;">${oo.enter_date_time}</td>
                <td style="width: 170px;"><a id="shuoms" onclick="yaopin('${oo.order_code}')">${oo.order_text}</a></td>
                <td style="width: 40px;"><fmt:formatNumber type="number" value="${oo.dosage}"  />${oo.dosage_units}</td>
                <td style="width: 40px;">${oo.administration}</td>
                <td style="width: 40px;">${oo.frequency}</td>
                <td style="width: 200px;" class="is_hiddenmore" title="${oo.drug_message}">${oo.drug_message}</td>
                <td style="width: 60px;">${oo.doctor}</td>
                <c:if test="${oo.sys_order_status eq '0'}">
					<td style="color: #00a093;width: 60px;">新开医嘱</td>
				</c:if>
				<c:if test="${oo.sys_order_status ne '0'}">
					<td style="width: 60px;">在用医嘱</td>
				</c:if>
            </tr>
            </c:forEach>
        </table>
    </div>
 </div>
</body>
<script type="text/javascript">
	
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