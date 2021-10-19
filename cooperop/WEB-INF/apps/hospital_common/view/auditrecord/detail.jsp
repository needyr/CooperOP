<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="审方查询" disloggedin="true">
<style type="text/css">

	#main{
		height: 100%;
		width: 100%;
	}

	.titlefont{
		font-size: 16px;
	    line-height: 40px;
	    text-align: center;
	}
	ul{
		column-count: 2;
		margin:0px;
		padding:3px;
	}
	ul li{
		list-style: none;
		line-height: 25px;
	}
	#heliyongyao{
		height: 80px;
   		border-bottom: 2px solid #bec7d0;
	}
	#heliyongyao a{
		font-size: 15px;
		margin-left: 10px;
	}
	.elapsedtimeinfo{
		height: 210px;
		width: 100%;
		background-color: #f3f1f1;
		padding: 3px;
	}
	.li-1{
	
	}
	.li-2{
	
	}
	.li-3{
	
	}
	.li-4{
	
	}
	.time_home{
		height: 30px;
		background: #b1cbce;
		width: 90%;
		cursor: pointer;
		display: none;
	}
	.tdd{
		height: 30px;
		float: left;
		text-align: center;
	}
	.st{
	
	}
	.tname{
		width: 100px;
	    height: 154px;
	    border-left: 2px solid #f3da6a;
	    border-bottom: 5px solid darkgrey;
	    position: absolute;
	    margin: 0px auto;
	}
	.ed{
	}
	.dot{
	    width: 15px;
	    height: 15px;
	    background-color: #f3da6a;
	    position: absolute;
	    border-radius: 50%;
	    left: -8px;
	}
	.emm{
	    position: absolute;
	    bottom: 4px;
	    width: 100px;
	    left: 3px;
	    font-style: normal;
	}
	.dot-up{
		width: 15px;
	    height: 15px;
	    background-color: #f3da6a;
	    position: absolute;
	    border-radius: 50%;
	    left: -8px;
	    bottom: 0px;
	}
	.emmup{
		position: absolute;
	    top: 4px;
	    width: 100px;
	    left: 3px;
	    font-style: normal;
	}
	.tname-up{
	    width: 100px;
	    height: 154px;
	    border-left: 2px solid #f3da6a;
	    border-top: 5px solid darkgrey;
	    position: absolute;
	    margin: 0px auto;
	    bottom: 247px;
	}
	.interval{
		width: 2px;
		height: 100%;
		background-color: #596b79;
	}
	.tuval{
		display: block;
		margin-top: -23px;
	}
</style>
<div id="main">
	<div class="titlefont">
		智能审查综合结果[
		<c:choose>
			<c:when test="${common.state eq 'Y'}">
				通过
			</c:when>
			<c:when test="${common.state eq 'T'}">
				提示
			</c:when>
			<c:when test="${common.state eq 'Q'}">
				拦截
			</c:when>
			<c:when test="${common.state eq 'N'}">
				驳回
			</c:when>
			<c:otherwise>
				错误
			</c:otherwise>
		</c:choose>
		]
		,耗时[${common.cost_time/1000}s]
		<a href="javascript:void(0);" onclick="auditDetail('${common.id}')" >查看详情</a>
		<a href="javascript:void(0);" onclick="auditbunch('0')">HIS请求串</a>
		<textarea hidden="true" id="his_req">${common.his_req}</textarea>
	</div>
	<c:if test="${not empty ipcdata }">
		<div class="" id="heliyongyao">
			合理用药, 智能审查结果[xx],当前状态
			<span>
			<c:if test="${ipcdata.audit.audit.state eq 'HL_B'}">智能审查驳回</c:if>
			<c:if test="${ipcdata.audit.audit.state eq 'HL_N'}">智能审查拦截</c:if>
			<c:if test="${ipcdata.audit.audit.state eq 'HL_T'}">智能审查提示</c:if>
			<c:if test="${ipcdata.audit.audit.state eq 'HL_Y'}">智能审查通过	</c:if>
			<c:if test="${ipcdata.audit.audit.state eq 'Y'}">药师审查通过</c:if>
			<c:if test="${ipcdata.audit.audit.state eq 'N'}">药师审查驳回</c:if>
			<c:if test="${ipcdata.audit.audit.state eq 'NOCHECK'}">合理用药未审</c:if>
			<c:if test="${ipcdata.audit.audit.state eq 'D'}">药师审查医生决定</c:if>
			<c:if test="${ipcdata.audit.audit.state eq 'DQ'}">医生强制用药</c:if>
			<c:if test="${ipcdata.audit.audit.state eq 'HL_F'}">智能审查超时或失败</c:if>
			<c:if test="${ipcdata.audit.audit.state eq 'DS'}">医生决定，确认用药</c:if>
			<c:if test="${ipcdata.audit.audit.state eq 'DN'}">医生决定，取消用药</c:if>
			<c:if test="${ipcdata.audit.audit.state eq 'DQ1'}">药师不在线，系统通过</c:if>
			<c:if test="${ipcdata.audit.audit.state eq 'is_overtime'}">强制用药超时</c:if>
			<c:if test="${ipcdata.audit.audit.state eq 'DB'}">返回调整</c:if>&nbsp;
			</span>
			<a href="javascript:void(0);" onclick="druglist('${ipcdata.audit.audit.id}')">审查药品</a>
			<a href="javascript:void(0);" onclick="auditIssue('${ipcdata.audit.audit.id}')">结果内容</a>
			<a href="javascript:void(0);" onclick="auditbunch('1')">审查请求串</a>
			<textarea hidden="true" id="thirdt_request">${ipcdata.audit.bunchTime.thirdt_request}</textarea>
			<a href="javascript:void(0);" onclick="auditbunch('2')">审查返回串</a>
			<textarea hidden="true" id="thirdt_response">${ipcdata.audit.bunchTime.thirdt_response}</textarea>
			<a href="javascript:void(0);">TMP套表数据</a>
			<div class="time_home">
	<c:forEach items="${ipcdata.audit.procedureTime}" var="time" varStatus="k">
		<div class="tdd" style="width: ${0-time.zb > 0 ? -time.zb:time.zb}%" title="${time.tname}[${time.zb}%]">
			<div class="interval"></div>
			<span  class="tuval">${time.tuval}</span>
			<span class="st"></span>
				<c:choose >
					<c:when test="${k.index%2 eq 1}">
						<span class="tname-up">
							<em class="dot-up"></em>
							<em class="emmup">${time.tname}</em>
						</span>
					</c:when>
					<c:otherwise>
						<span class="tname">
							<em class="dot"></em>
							<em class="emm">${time.tname}</em>
						</span>
					</c:otherwise>
				</c:choose>
				
			<span class="ed"></span>
		</div>
	</c:forEach>
		<!-- <div class="tdd" style="width: 20%" title=""></div>
		<div class="tdd" style="width: 20%" title=""></div>
		<div class="tdd" style="width: 20%" title=""></div>
		<div class="tdd" style="width: 20%" title=""></div>
		<div class="tdd" style="width: 20%" title=""></div> -->
	</div>
			</div>
			<%-- <div class="elapsedtimeinfo">
				<ul>
					<c:forEach items="${ipcdata.audit.procedureTime}" var="time">
						<li class="li-${time.level}">　${time.sort_id}、${time.tname}：${time.tuval}</li>
					</c:forEach>
				</ul>
			</div> --%>
	</c:if>
	
	<%-- <c:if test="${not empty imicipcdata.audit.audit }">
	<div style=""  class="titlefont" id="yibaoshencha">医保审查：&nbsp;
		<a href="javascript:void(0);">审查结果</a>
		<a href="javascript:void(0);">参与审查药品</a>
		<a href="javascript:void(0);">审查串</a>
		<a href="javascript:void(0);">TMP套表数据</a>
		<div class="elapsedtimeinfo">
			<ul>
			</ul>
		</div>
	</div>
	</c:if> --%>
</div>
</s:page>
<script type="text/javascript">

function auditDetail(common_id){
   $.modal("/w/hospital_common/showturns/show.html?id="+ common_id,"查看审查详情",{
	   width: '935px',
	   height: '645px',
       callback : function(e){}
   })
}
function auditIssue(auto_audit_id){
   $.modal("auditissue.html?auto_audit_id="+ auto_audit_id,"查看审查问题",{
	   width: '900px',
	   height: '590px',
	   maxmin:false,
       callback : function(e){}
   })
}

function druglist(auto_audit_id){
	$.modal('auditdrug.html?auto_audit_id='+ auto_audit_id,"参与审查药品",{
	  	width: '1000px',
	  	height: '610px',
	    callback : function(e){}
	});
}

function auditbunch(identify){
	var data;
	if(identify == '0'){
		data=getFormatCode($("#his_req").val());
	}
	if(identify == '1'){
		data=getFormatCode($("#thirdt_request").val());
	}
	if(identify == '2'){
		data=getFormatCode($("#thirdt_response").val());
	}
	$.modal('auditbunch.html',"审查串",{
	  	width: '550px',
	  	height: '580px',
	  	maxmin:false,
	  	data:data,
	  	identify:identify,
	    callback : function(e){}
	});
}
var getFormatCode=function(strValue){
	return strValue.replace(/\r\n/g, "<br>").replace(/\n/g,"<br>").replace(/\'/g, "\\'").replace(/\"/g, "\\\"");
}

</script>