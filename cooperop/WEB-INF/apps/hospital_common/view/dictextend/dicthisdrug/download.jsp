<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<script src="/theme/plugins/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script>
<style>
.img_box {
	height:120px;
    border-top: 1px solid;
    overflow-x: auto;
    overflow-y: hidden;
    white-space: nowrap;
    margin: 0 auto;
    border-left: 1px solid;
    border-bottom: 1px solid;
    border-right: 1px solid;
    padding: 0px !important;
}

.img_box .img {
	width: 110px;
	height: 110px;
	margin-right: 10px;
	box-shadow: 3px 3px 3px #888888;
}

#baoc:hover{
    color: #FFFFFF;
    background-color: #00C1DE;
}

#baoc {
	color: #00C1DE;
	border: 1px solid #00C1DE;
	margin-left: 13px;
	height: 25px;
}
</style>
<s:page title="">
<s:form>
	<s:row>
	<div class="img_box cols3">
	<%-- <c:if test="${fn:length(shuoms_file) > 0}">
	<c:forEach var="p" items="${fn:split(shuoms_file, ',')}">
		<img alt="img" class="img" src="${pageContext.request.contextPath}/rm/s/hospital_common/${p}" 
		onerror="this.src='${pageContext.request.contextPath}/res/hospital_common/img/pdf.jpg'"
		data-src="${pageContext.request.contextPath}/rm/s/hospital_common/${p}" data-value="${p}">
	</c:forEach>
	</c:if> --%>
	</div>
	</s:row>
	<s:row>
	<%-- <s:button label="保存数据"  id="baoc" onclick="save();" cols="1"></s:button> --%>
	<s:file id="upload_img" 
	filetypes="(.jpg|.png|.pdf|.jpeg|.gif|.JPG|.PNG|.PDF|.JPEG|.GIF|.docx|.xlsx|.xls|.csv)$" 
	maxlength="10" value="${shuoms_file}" name="ddd" cols="2" autoupload="false">
	</s:file>
	</s:row>
</s:form>
<s:row>
</s:row>
</s:page>
	
<script>
$(function(){
	$('.fileupload-buttonbar').append('<button  class="btn btn-sm blue" onclick="save();" cols="1">保存数据</button>')
	if('${shuoms_file}' && '${shuoms_file}'!=''){
		var html = [];
		var num = 1;
		var file_adds = ('${shuoms_file}').split(',');
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
					html.push('<img alt="'+file_name+'" title="'+file_name+'" class="img" src="${pageContext.request.contextPath}/rm/s/hospital_common/'+file_adds[i]+'"');
					if(rex_pdf.test(file_name)){
						html.push('onerror="this.src='+"'"+'${pageContext.request.contextPath}/res/hospital_common/img/pdf.jpg'+"'"+'"');
					}else if(rex_word.test(file_name)){
						html.push('onerror="this.src='+"'"+'${pageContext.request.contextPath}/res/hospital_common/img/word.jpg'+"'"+'"');
					}else if(rex_excel.test(file_name)){
						html.push('onerror="this.src='+"'"+'${pageContext.request.contextPath}/res/hospital_common/img/excel.jpg'+"'"+'"');
					}
					html.push(' data-fname="'+file_name+'" ');
					html.push('data-src="${pageContext.request.contextPath}/rm/s/hospital_common/'+file_adds[i]+'">');
					num += 1;
				},
				error:function(error){
				   console.log(error)
				}
			})
		}
		$(".img_box").append(html.join(''));
		$(".img").click(function(){ 
			   var _this = $(this);//将当前的pimg元素作为_this传入函数
			   var url = _this.attr('data-src');
			   var fname = _this.attr('data-fname');
				var rex_word = /(.docx)$/;
				var rex_pdf = /(.pdf)$/;
				var rex_excel = /(.xlsx|.xls|.csv)$/;
				if(rex_excel.test(fname) || rex_word.test(fname) || rex_pdf.test(fname)){
					window.open(url, "_blank");
				}else{
					var toplayer = null;
					if (top.layer && top != window) {
						toplayer = top;
					} else {
						var p = window;
						while (p.parent != p && p.parent.layer) {
							p = p.parent;
						}
						toplayer = p;
					}
					var o = {"data" : []};
					o.data.push({
						"description" : null,
						"src" : url, // 原图地址
						"thumb" :url + 'S'
						// 缩略图地址,
					});
					o.start = 0;
					toplayer.$.photos(o);
				}
			});
	}
})
function save(){
	var drug_code = '${param.drug_code}';
	var value = $('#upload_img').getData_file().ddd;
	if(drug_code){
		$.call('hospital_common.dictextend.dicthisdrug.updateSysByCodeShuoms',{'drug_code':drug_code,'shuoms_file':value},function(){
			$.closeModal();
		})
	}
}

</script>
