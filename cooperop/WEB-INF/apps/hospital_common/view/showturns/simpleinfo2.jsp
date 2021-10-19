<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="药品说明书查询" disloggedin="true">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/hospital_common/css/simple.css">
	<body>
		<div class="main">
			<div id="top" >
				<%-- <select id="select_drug">
					<!-- <option value="">请选择相应药品</option> -->
					<c:forEach items="${drug_content}" var="_drug">
					<option value="${_drug.drug_code}">${_drug.drug_name}|【${_drug.druggg}】|【${_drug.shengccj}】</option>
					</c:forEach>
				</select> --%>
				<s:form id ="dataForm" style="padding: 0px 0px !important;">
				<s:row>
				<s:autocomplete style="margin-right:20px;width: 385px !important; height: 30px !important;font-size: 13px !important;" value="${drug_content[0].drug_code}" text="${drug_content[0].drug_name}|【${drug_content[0].druggg}】|【${drug_content[0].shengccj}】" name="select_drug" action="hospital_common.showturns.queryAllShuoms" limit="10" params="{&#34;drug_code&#34;: &#34;${drug_code}&#34;}">
						<s:option value="$[drug_code]" label="$[drug_name]|【$[druggg]】|【$[shengccj]】">
								$[drug_name]|【$[druggg]】|【$[shengccj]】
						</s:option>
				</s:autocomplete>
				<c:if test="${not empty drug_content[0].shuoms_file}">
  				<s:button id="ckpdf" style="margin-right:20px;width: 100px !important; height: 30px !important;font-size: 13px !important;float:right !important;" label="说明书附件" ></s:button>
				</c:if>
				<button style="height: 30px;margin-right: 5px; float: right; font-size: 16px;width: 40px;border: 1px solid #a9b1bd;color: #333;background: #FFF;" label="算" onclick="callCalculator();" icon="fa icon-calculator"><i class="fa icon-calculator"></i>算</button>
				</s:row>
				</s:form>
				<!-- <font style="color: #e8bf46">根据规格厂家选择</font> -->
			</div>
			<div id="middle">
				<nav class="leftList" id="myScrollspy">
					<ul>
					<%-- <li><a id="ybf" href="#abc">医保信息</a></li>
						<c:if test="${not empty drug && drug ne '0'}">
							<c:forEach items="${smsdrug}" var="sms">
								<c:forEach items="${drug}" var="dr">
									<c:if
										test="${dr.key eq sms.column_name and not empty dr.value}">
										<li><a href="#${dr.key }">${sms.column_name_cn }</a></li>
									</c:if>
								</c:forEach>
							</c:forEach>
						</c:if> --%>
					</ul>
				</nav>
				<div class="rightContent">
					<%--<div id="abc" class="mdz">
						<h5>【医保信息】</h5>
						<p>
							 <c:forEach items="${$return.ybinfo}" var="bfo">
								${bfo.insur_showmessage}<br>
								<c:if test="${not empty bfo.item_spec}">
									规格：${bfo.item_spec};
								</c:if>
								<c:if test="${not empty bfo.units}">
									单位：${bfo.units};
								</c:if>
								<c:if test="${not empty bfo.price}">
									 单价：<fmt:formatNumber value="${bfo.price}" pattern="#.0" minFractionDigits='0' />元;
								</c:if>
								<c:if test="${not empty bfo.insur_charge}">
									 限价：<fmt:formatNumber value="${bfo.insur_charge}" pattern="#.0" minFractionDigits='0' />元
								</c:if>
								<br>
								----------------------------------------------------------<br>
							</c:forEach> 
						</p>
					</div>--%>
					<%-- <c:if test="${not empty drug && drug ne '0'}">
						<c:choose>
							<c:when test="${is_zdy eq '0'}">
								<i style="color: #d6cece;">该说明书内容来自【标准说明书】</i>
							</c:when>
							<c:when test="${is_zdy eq '1'}">
								<i style="color: #d6cece;">该说明书内容来自【自定义说明书】</i>
							</c:when>
						</c:choose>
						<c:forEach items="${smsdrug}" var="sms">
							<c:forEach items="${drug}" var="dr">
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
					</c:if> --%>
				</div>
			</div>
			<div id="foot">
				<c:forEach items="${assist}" var="assist">
					<a class="foot_a" href="javascript:void(0);"
						data-url="${assist.url}">${assist.queryname}</a>|
  				</c:forEach>
  				<%-- <c:if test="${not empty drug_content[0].shuoms_file}">
  				<a class="foot_a" href="javascript:void(0);"
						data-url="http://${local_ip}/w/hospital_common/additional/shuoms_img.html?drug_code=${drug_code}">查看药品说明书附件</a>
				</c:if> --%>
			</div>
		</div>
	</body>
</s:page>
<script type="text/javascript">
	$(function() {
		//aByClick();
		/* <c:if test="${empty drug && drug ne '0'}">
			$.message('暂无该药品说明书！', function(){
				window.close();
			});
		</c:if> */
		//$('#ybf').click();
		//add($("#select_drug").find("option:selected").val())
		add($("#dataForm").getData().select_drug)
		$('[name=select_drug]').change(function(){
			var data = $("#dataForm").getData();
			add(data.select_drug)
		})
		/* var mh = window.innerHeight - 60 - 40 + 20;
		$('#middle').css('height' , mh);
		$('.leftList').css('height' , mh - 4); */
		$(window).resize(function() {
			var mh = window.innerHeight - 60 - 40 + 20;
			$('#middle').css('height' , mh);
			$('.leftList').css('height' , mh - 4);
		})
		$(window).resize();
		//$("#select_drug option:eq(1)").attr("selected","selected");
		/* $('.main').css('height' , window.innerHeight - 20);
		$('#middle').css('height' , window.innerHeight*0.99 - 60 - 40);
		$('.rightContent').css('height' , window.innerHeight*0.99 - 60 - 40);
		$('.leftList').css('height' , window.innerHeight*0.99 - 60 - 40);
		$(window).resize(function() {
			$('.main').css('height' , window.innerHeight - 20);
			$('#middle').css('height' , window.innerHeight*0.99 - 60 - 40);
			$('.rightContent').css('height' , window.innerHeight*0.99 - 60 - 40);
			$('.leftList').css('height' , window.innerHeight*0.99 - 60 - 40);
		}) */
		
		$('#ckpdf').click(function(){
			if (typeof crtech != "undefined") {
				crtech.modal('http://'+'${local_ip}'+'/w/hospital_common/additional/shuoms_img.html?drug_code='+encodeURIComponent('${drug_content[0].drug_code}'), "60%", "60%");//宽高可以传百分比
			}
		})
	})
	
	
	
	function aByClick() {
		//修改图片路径
		$('.mdz img')
				.each(
						function() {
							var _path = '${pageContext.request.contextPath}/res/hospital_common/hytimages/'
									+ $(this).attr('src');
							$(this).attr('src', _path);
						});

		$("a").on("click", function() {
			$('.mdz').css('background-color', '#ffffff !important');
			$("p").css({
				"color" : "#333",
				"font-size" : "14px",
				"font-weight" : ""
			});
			$("a").css("background-color", "");
			$(this).css("background-color", "#9dc7c7");
			var nid = $(this).attr("href");
			//f5f5f5
			$(nid).css("background-color", "#f5f5f5 !important");
			$(nid + " p").css("color", "#45867e").css({
				"font-weight" : "400",
				"font-size" : "15px"
			});
			$(nid + " span").attr("display", "block");
		})
	}
	$("#foot").find(".foot_a").on("click", function() {
		var u = $(this).attr("data-url");
		if (crtech) {
			crtech.modal(u, "1000", "600");//宽高可以传百分比
		}
	});

	function add(drug_code){
		$.call("hospital_common.showturns.getSimpleinfo",{patient_id: '${patient_id}',visit_id:'${visit_id}','drug_code':drug_code},function(rtn){
			//console.log(rtn)
			$('#myScrollspy ul').empty();
			var html_myScrollspy = [];
			html_myScrollspy.push('<li><a id="ybf" href="#abc">医保信息</a></li>');
			if(rtn.drug && rtn.drug != 0 && rtn.smsdrug){
				for(sms in rtn.smsdrug){
					for(dr in rtn.drug){
						//alert(dr)
						if(dr == rtn.smsdrug[sms].column_name && eval('rtn.drug.' + dr) ){
							html_myScrollspy.push('<li><a href="#'+dr+'">'+rtn.smsdrug[sms].column_name_cn+'</a></li>');
						}
					}
				}
			}
			$('#myScrollspy ul').append(html_myScrollspy.join(''));
			
			$('.rightContent').empty();
			if(rtn && rtn.ybinfo !=null){
				var html = [];
				html.push('<div id="abc" class="mdz">');
				html.push('<h5>【医保信息】</h5>');
				html.push('<p>');
				for(bfo in rtn.ybinfo){
					html.push(rtn.ybinfo[bfo].insur_showmessage + '<br>');
					if(rtn.ybinfo[bfo].item_spec){
						html.push('规格：'+rtn.ybinfo[bfo].item_spec+';');
					}
					if(rtn.ybinfo[bfo].units){
						html.push('单位：'+rtn.ybinfo[bfo].units+';');
					}
					if(rtn.ybinfo[bfo].price){
						html.push('单价：'+parseFloat(rtn.ybinfo[bfo].price)+'元;');
					}
					if(rtn.ybinfo[bfo].insur_charge){
						html.push('限价：'+parseFloat(rtn.ybinfo[bfo].insur_charge)+';');
					}
					html.push('<br>');
					html.push('----------------------------------------------------------<br>');
				}
				html.push('</div>');
				html.push('</p>');
				$('.rightContent').append(html.join(''));
			}
			
			if(rtn.drug && rtn.drug != '0'){
				var html = [];
				if(rtn.is_zdy == '0'){
					html.push('<i style="color: #d6cece;">该说明书内容来自【标准说明书】</i>');
				}else if (rtn.is_zdy == '1'){
					html.push('<i style="color: #d6cece;">该说明书内容来自【自定义说明书】</i>');
				}
				$('#ckpdf').remove();
				if(rtn.drug.shuoms_file){
					$("#dataForm").children("div").append('<button crid="" action="" style="margin-right:20px;width: 100px !important; height: 30px !important;font-size: 13px !important;float:right !important;" class="btn btn-sm btn-sm btn-default " ctype="button" t="1" type="button" id="ckpdf" cinited="cinited">说明书附件</button>');
					$('#ckpdf').click(function(){
						if (typeof crtech != "undefined") {
							crtech.modal('http://'+'${local_ip}'+'/w/hospital_common/additional/shuoms_img.html?drug_code='+encodeURIComponent(rtn.drug.drug_code), "60%", "60%");//宽高可以传百分比
						}
					})
				}
				for(sms in rtn.smsdrug){
					for(dr in rtn.drug){
						if(dr == rtn.smsdrug[sms].column_name && eval('rtn.drug.' + dr)){
							html.push('<div id="'+dr+'" class="mdz">');
							html.push('<h5>【'+rtn.smsdrug[sms].column_name_cn+'】</h5>');
							html.push('<p style="word-break: break-all;">'+eval('rtn.drug.' + dr)+'</p>');
							html.push('</div>');
						}
					}
				}
				//console.log(html.join(''))
				$('.rightContent #abc').after(html.join(''));
			}
			aByClick();
			$('#ybf').click();
		});
		//$("a").on("click", function() {alert(1)})
	}
	
	function ckpdf(){
		/* layer.open({
			type: 2,//类型
			title: '药品说明书附件',//窗口名字
			maxmin: true,
			shadeClose: false,//点击遮罩不关闭层
			shade: 0,
			area: ['95%', '95%'],//定义宽和高
			content: 'http://127.0.0.1:9001/w/hospital_common/additional/shuoms_img.html?drug_code='+'${drug_code}',//打开的内容
			success: function(layero, index){
			}
		}) */
		if (typeof crtech != "undefined") {
			crtech.modal('http://'+'${local_ip}'+'/w/hospital_common/additional/shuoms_img.html?drug_code='+encodeURIComponent('${drug_content[0].drug_code}'), "60%", "60%");//宽高可以传百分比
		}
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