<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<style>
.portlet {
    margin-bottom: 0px !important;
    padding: 0px !important;
}
</style>
<s:page title="药品说明书查询" disloggedin="true">
 <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/hospital_common/css/instruction.css">
 <body>
  <div class="main" >
   <div class="quick">
    	<s:form id ="dataForm">
    		<s:row>
    			<s:autocomplete id="" name="drug_code" action="hospital_common.additional.queryDrug"  value="${drug_code}" text="${drug_name}" limit="10" cols="1" editable="true" placeholder="请输入药品编号，名称，拼音码">
					<s:option value="$[drug_code]" label="$[drug_name]">
					<span style="width:280px;display:block;float:left">
						$[drug_code]
					</span>
					<span style="width:200px;display:block;float:left">
						$[drug_name]
					</span>
					<span style="width:100px;display:block;float:left">
						●$[druggg]
					</span>
					<span style="width:200px;display:block;float:left">
						●$[shengccj]
					</span>
					</s:option>
				</s:autocomplete>
				<s:button label="搜索" onclick="getInstruction();" icon="fa fa-search" style="height: 30px;"></s:button>
				<%-- <input name="cg_query" autocomplete="off" style="width: calc(30% - 200px);">
				 <s:button label="查询临床文献" onclick="getCG();" icon="fa fa-search" style="height: 30px;"></s:button> --%>
				 <c:if test="${not empty drug.shuoms_file}">
				 <s:button id="ckpdf" label="说明书附件" ></s:button>
				 </c:if>
    			 <button style="height: 30px;margin-right: 5px; float: right; font-size: 16px;width: 40px;border: 1px solid #a9b1bd;color: #333;background: #FFF;" label="算" onclick="callCalculator();" icon="fa icon-calculator"><i class="fa icon-calculator"></i>算</button>
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
					    	<p style="word-break: break-all;">${dr.value}</p>
					    </div>
					    </c:if>
		   		 	</c:if>
		   		</c:forEach>
		     </c:forEach>
    	</c:if>
    </div>
    <div class="bottom"></div>
    </div>
  </div>
</body>
</s:page>
<script type="text/javascript">
var his_drug_code = '${drug_code}';
$(function(){
	aByClick();
	$('.rightContent').css('height' , window.innerHeight*0.99 - 60 - 28);
	$(window).resize(function() {
		$('.rightContent').css('height' , window.innerHeight*0.99 - 60 - 28);
	})
	//if(typeof crtech == "undefined"){
		$('#ckpdf').click(function(){
			$.modal('/w/hospital_common/additional/shuoms_img.html?drug_code='+his_drug_code,"药品说明书附件",{
		        width:"95%",
		        height:"95%",
		        maxmin: true,
				shadeClose: false,//点击遮罩不关闭层
				shade: 0,
		        callback : function(e){
		        }
			});
		})
	//}
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
	//重新拼接导航
	$(".leftList").html('');
	$(".rightContent").html('');
	$.call("hospital_common.additional.instruction", data, function(rtn){
		if(rtn != null && rtn.drug != null && rtn.drug != 0){
			his_drug_code = rtn.drug.drug_code;
			$('#ckpdf').remove();
			if(rtn.drug.shuoms_file){
				$("#dataForm").children("div").append('<button crid="" action="" class="btn btn-sm btn-sm btn-default " ctype="button" t="1" type="button" id="ckpdf" cinited="cinited">说明书附件</button>');
				//if(typeof crtech == "undefined"){
					$('#ckpdf').click(function(){
						$.modal('/w/hospital_common/additional/shuoms_img.html?drug_code='+his_drug_code,"药品说明书附件",{
					        width:"95%",
					        height:"95%",
					        maxmin: true,
							shadeClose: false,//点击遮罩不关闭层
							shade: 0,
					        callback : function(e){
					        }
						});
					})
				//}
			}
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
}

function getCG(){
	var data = $('[name=cg_query]').val();
	$(".bottom").html('');
	$.call('hospital_common.additional.queryCG',{cg_query:data},function(rtn){
		var html = [];
		html.push('<font style="font-size: 13px;">相关临床指南：</font>');
		for(var i=0;i<rtn.length;i++){
			var _path= '${pageContext.request.contextPath}/res/hospital_common/cg/' + rtn[i].id + '.html';
			html.push('<a onclick="getCGPath('+"'"+_path+"'"+",'"+data+"'"+')">'+rtn[i].cg_name+'</a>');
			html.push('||');
		}
		$(".bottom").append(html.join(''));
	})
}

function getCGPath(path,data){
	layer.open({
		type: 2,//类型
		title: '临床指南',//窗口名字
		maxmin: true,
		shadeClose: false,//点击遮罩不关闭层
		shade: 0,
		area: ['870px', '90%'],//定义宽和高
		content: '/w/hospital_common/additional/cg.html?path='+path+'&&data='+data,//打开的内容
		success: function(layero, index){
		}
	})
}

function ckpdf(){
	/* layer.open({
		type: 2,//类型
		title: '药品说明书附件',//窗口名字
		maxmin: true,
		shadeClose: false,//点击遮罩不关闭层
		shade: 0,
		area: ['95%', '95%'],//定义宽和高
		content: '/w/hospital_common/additional/shuoms_img.html?drug_code='+'${drug_code}',//打开的内容
		success: function(layero, index){
		}
	}) */
	$.modal('/w/hospital_common/additional/shuoms_img.html?drug_code='+'${drug_code}',"药品说明书附件",{
        width:"95%",
        height:"95%",
        maxmin: true,
		shadeClose: false,//点击遮罩不关闭层
		shade: 0,
        callback : function(e){
        }
	});
}

var calculator;
function callCalculator(){
	if(calculator){
		layer.close(calculator);
		calculator = null;
	}else{
		calculator = layer.open({
			type: 2,//类型
			offset: [60, $('.main').width() - 245],
			title: '辅助计算器',//窗口名字
			maxmin: true,
			shadeClose: false,//点击遮罩不关闭层
			shade: 0,
			area: ['250px', '320px'],//定义宽和高
			content: '/w/hospital_common/additional/calculator.html',
			success: function(layero, index){
			}
		})
	}
}
</script>