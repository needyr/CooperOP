<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<style>
.pdf_box {
	width:100%;
}

.img_box {
	height:200px;
    border-top: 1px solid;
    overflow-x: hidden;
    overflow-y: scroll;
    white-space: nowrap;
    margin: 0 auto;
    border-left: 1px solid;
    border-bottom: 1px solid;
}
.img_box .img {
	width: 400px;
	height: 200px;
	margin-left: 20px;
}

#outerdiv {
	position:fixed;
	top:0;
	left:0;
	background:rgba(0,0,0,0.7);
	z-index:2;
	width:100%;
	height:100%;
	display:none;
}

.img {
	width: 100%;
	height: 25px;
	margin-top: 20px;
	box-shadow: 5px 5px 5px #888888;
}

.img:hover {
	/* border-top: 1px solid #7bfff9;
	border-bottom: 1px solid #7bfff9; */
	cursor: pointer;
}

.aaa {
	width:25px;
	float:left;
	border-top: 1px solid #939d99;
    overflow-x: hidden;
    overflow-y: auto;
    margin: 0 auto;
    border-left: 1px solid #939d99;
    border-bottom: 1px solid #939d99;
}

.tag {
    height: 10px;
    width: 10px;
    background-color: #c2c2c27a;
    border-radius: 5px;
    font-size: 10px;
    text-align: center;
    line-height: 10px;
    font-weight: 800;
    top: 20px;
    position: absolute;
}
</style>
<head>
<meta charset="UTF-8">
<script src="/theme/plugins/jquery.min.js" type="text/javascript"></script>
<title>说明书补充文件</title>
</head>
<body>
<div class="aaa">
	<%-- <c:if test="${fn:length($return.img.shuoms_file) > 0}">
	<c:set var="num" value="1"></c:set>
	<c:forEach var="p" items="${fn:split($return.img.shuoms_file, ',')}">
		<div style="position: relative;">
		<img alt="img" class="img" src="${pageContext.request.contextPath}/rm/s/hospital_common/${p}" 
		onerror="this.src='${pageContext.request.contextPath}/res/hospital_common/img/pdf.jpg'"
		data-src="${pageContext.request.contextPath}/rm/s/hospital_common/${p}">
		<div class="tag">${num}</div>
		</div>
		<c:set var="num" value="${num + 1}"></c:set>
	</c:forEach>
	</c:if> --%>
</div>
<div class="bbb" style="width:calc(100% - 35px);border: 1px solid #939d99;float:left;">
	<iframe id="pdfframe" src="" frameborder="0" width="100%" height="100%">
		
	</iframe>
</div>
</body>
</html>
<script type="text/javascript">
$(function(){
    var height = $(window).height() - 50;
	$('.aaa').css('height', height);
	$('.bbb').css('height', height);
	$(window).resize(function() {
		var height = $(window).height() - 50;
		$('.aaa').css('height', height);
		$('.bbb').css('height', height);
	})
	if('${$return.img.shuoms_file}'){
		var html = [];
		var num = 1;
		var file_adds = ('${$return.img.shuoms_file}').split(',');
		for(var i=0;i<file_adds.length;i++){
			var file_address = '${pageContext.request.contextPath}/rm/l/hospital_common/'+file_adds[i];
			$.ajax({
				url:file_address,
				type:'post',
				async: false,
				success:function(data){
					var file_content = eval("("+data+")")[0];
					var file_name = file_content.file_name;
					var rex_pdf = /(.pdf)$/;
					var rex_word = /(.docx)$/;
					var rex_excel = /(.xlsx|.xls|.csv)$/;
					html.push('<div style="position: relative;">');
					html.push('<img alt="img" title="'+file_name+'" class="img" src="${pageContext.request.contextPath}/rm/s/hospital_common/'+file_adds[i]+'"');
					if(rex_pdf.test(file_name)){
						html.push('onerror="this.src='+"'"+'${pageContext.request.contextPath}/res/hospital_common/img/pdf.jpg'+"'"+'"');
					}else if(rex_word.test(file_name)){
						html.push('onerror="this.src='+"'"+'${pageContext.request.contextPath}/res/hospital_common/img/word.jpg'+"'"+'"');
					}else if(rex_excel.test(file_name)){
						html.push('onerror="this.src='+"'"+'${pageContext.request.contextPath}/res/hospital_common/img/excel.jpg'+"'"+'"');
					}
					html.push('data-src="${pageContext.request.contextPath}/rm/s/hospital_common/'+file_adds[i]+'">');
					html.push('<div class="tag">'+num+'</div>');
					html.push('</div>');
					num += 1;
				},
				error:function(error){
				   console.log(error)
				}
			})
		}
		$(".aaa").append(html.join(''));
	}
	
	$(".img").click(function(){ 
      var _this = $(this);//将当前的pimg元素作为_this传入函数
      if(typeof crtech != "undefined"){
    	  crtech.print('${$return.local_ip}'+_this.attr('data-src'),'700px','700px');
      }else{
    	  $('#pdfframe').attr('src', _this.attr('data-src'));
          $("#pdfframe").load(function () {
              var frm = $("#pdfframe");
              frm.contents().find("body").css('text-align','center');
              frm.contents().find("img").click(function(){
            	  rotate(this);
              })
          });
      }
    });
	setTimeout(function(){
		$(".img:first").click();
	},500)
})

function getmatrix(a,b,c,d,e,f){  
    var aa=Math.round(180*Math.asin(a)/ Math.PI);  
    var bb=Math.round(180*Math.acos(b)/ Math.PI);  
    var cc=Math.round(180*Math.asin(c)/ Math.PI);  
    var dd=Math.round(180*Math.acos(d)/ Math.PI);  
    var deg=0;  
    if(aa==bb||-aa==bb){  
        deg=dd;  
    }else if(-aa+bb==180){  
        deg=180+cc;  
    }else if(aa+bb==180){  
        deg=360-cc||360-dd;  
    }  
    return deg>=360?0:deg;  
    //return (aa+','+bb+','+cc+','+dd);  
}  
function rotate(dom){
    var ele = $(dom);
    // console.log(ele.css('transform'))
    var css = ele.css('transform');
    var deg;
    var step=90;//每次旋转多少度 
    if(css === 'none'){
        deg = 0;
    } else {
        deg=eval('get'+css);
    }         
    ele.css({'transform':'rotate('+(deg+step)%360+'deg)'});  
}
</script>