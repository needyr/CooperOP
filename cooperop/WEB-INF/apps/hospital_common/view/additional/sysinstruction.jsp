<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="药品说明书查询" disloggedin="true">
 <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/hospital_common/css/instruction.css">
 <body>
  <div class="main" >
   <div class="quick">
    	<s:form id ="dataForm">
    		<s:row>
    			<s:autocomplete id="sys_drug_code" name="drug_code" action="hospital_common.additional.querySysDrug"  value="${drug_code}" text="${drug_name}" limit="10" cols="1" editable="true" placeholder="请输入药品编号，名称，拼音码">
					<s:option value="$[drug_code]" label="$[drug_name]">
					<span style="width:60px;display:block;float:left">
						$[drug_code]
					</span>
					<span style="width:130px;display:block;float:left">
						$[drug_name]
					</span>
					<span style="width:150px;display:block;float:left">
						●$[druggg]
					</span>
					<span style="width:200px;display:block;float:left">
						●$[shengccj]
					</span>
					</s:option>
				</s:autocomplete>
				<s:button label="搜索" onclick="getInstruction();" icon="fa fa-search" style="height: 30px;"></s:button>
    		</s:row>
    	</s:form>
  	</div>
  	<div>
  	<nav class="leftList" id="myScrollspy" >
	  	<c:if test="${not empty drug && drug ne '0'}">
		    <ul >
		    <c:forEach items="${smsdrug}" var = "sms">
		   		<c:forEach items="${drug}" var = "dr">
		   			<c:if test="${dr.key eq sms.column_name and not empty dr.value}">
		   		 		<li><a href="#${dr.key }">${sms.column_name_cn }</a></li> 
		   			</c:if>
		   		</c:forEach>
		    </c:forEach>
		    </ul>
		 </c:if>
	 </nav>
	 <div class="rightContent">
	    <c:if test="${not empty drug && drug ne '0'}">
    		<c:choose>
    			<c:when test="${is_zdy eq '0'}">
    				<i style="color:  #d6cece;">该说明书内容来自【标准说明书】</i>
    			</c:when>
    			<c:when test="${is_zdy eq '1'}">
    				<i style="color:  #d6cece;">该说明书内容来自【自定义说明书】</i>
    			</c:when>
    		</c:choose> 
		    <c:forEach items="${smsdrug}" var = "sms">
		   		<c:forEach items="${drug}" var = "dr">
		   			<c:if test="${dr.key eq sms.column_name }">
		   				<c:if test="${not empty dr.value}">
		   		 		<div id="${dr.key }" class="mdz">
					    	<h5>【${sms.column_name_cn}】</h5>
					    	<p>${dr.value}</p>
					    </div>
					    </c:if>
		   		 	</c:if>
		   		</c:forEach>
		     </c:forEach>
    	</c:if>
    </div>
    </div>
  </div>
</body>
</s:page>
<script type="text/javascript">
$(function(){
	aByClick();
	$('.rightContent').css('height' , window.innerHeight*0.99 - 60);
	var sys_drug_code=$("#sys_drug_code").val();
	if(sys_drug_code == ""){
		$.message("系统内【暂无】该药品说明书信息");
	}
})

function aByClick(){
	//修改图片路径
	$('.mdz img').each(function(){
		var _path= '${pageContext.request.contextPath}/res/hospital_common/hytimages/' + $(this).attr('src');
		$(this).attr('src', _path);
	});
	
	$("a").on("click", function(){
		$('.mdz').css('background-color', '#ffffff !important');
		$("p").css({"color":"#333","font-size":"14px","font-weight":""});
		$("a").css("background-color","");
		$(this).css("background-color","#9dc7c7");
		var nid = $(this).attr("href");
		//f5f5f5
		$(nid).css("background-color", "#f5f5f5 !important");
		$(nid+" p").css("color","#45867e").css({"font-weight":"400","font-size":"15px"});
		$(nid+" span").attr("display","block");
	})
}

function getInstruction(){
	var data = $("#dataForm").getData();
	if(data.drug_code != ""){
		//重新拼接导航
		$(".leftList").html('');
		$(".rightContent").html('');
		$.call("hospital_common.additional.sysinstruction", data, function(rtn){
			if(rtn != null && rtn.drug != null && rtn.drug != 0){
				var sms = rtn.smsdrug;
				var dr = rtn.drug;
				var smshtml = [];
				var drhtml = [];
				var is_zdy = rtn.is_zdy;
				console.log(rtn);
				smshtml.push('<ul>');
				for(x in sms){
					if(eval("dr."+sms[x].column_name) != null){
						smshtml.push('<li>');
						smshtml.push('<a href="#'+sms[x].column_name+'">');
						smshtml.push(sms[x].column_name_cn);
						smshtml.push('</a>');
						smshtml.push('</li>');
					}
				} 
				smshtml.push('</ul>');
				$(".leftList").append(smshtml.join(''));
				if(is_zdy == '1'){
					drhtml.push('<i style="color:  #d6cece;">该说明书内容来自【自定义说明书】</i>');
				}else{
					drhtml.push('<i style="color:  #d6cece;">该说明书内容来自【标准说明书】</i>');
				}
				for(y in sms){
					if(eval("dr."+sms[y].column_name) != null){
						drhtml.push('<div id="'+ sms[y].column_name +'" class="mdz">');
						drhtml.push('<h5>【'+ sms[y].column_name_cn +'】</h5>');
						drhtml.push('<p>'+ eval("dr."+sms[y].column_name) +'</p>');
						drhtml.push('</div>'); 
					}
				}
				$(".rightContent").append(drhtml.join(''));  
				aByClick();
			}else{
				$.message("系统内【暂无】该药品说明书信息");
			}
		},function(e){}, {async: false, remark: false   });
	}else{
		return false;
	}
}
</script>