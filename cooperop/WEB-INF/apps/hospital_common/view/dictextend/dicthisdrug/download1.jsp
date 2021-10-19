<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<%-- <script src="/theme/plugins/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script> --%>
<link href="{$contextpath}/theme/plugins/jquery-file-upload/css/jquery.fileupload.css?m={$module}" rel="stylesheet"/>
<link href="{$contextpath}/theme/plugins/jquery-file-upload/css/jquery.fileupload-ui.css?m={$module}" rel="stylesheet"/>
<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/vendor/tmpl.min.js"></script>
<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/vendor/load-image.min.js"></script>
<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/vendor/canvas-to-blob.min.js"></script>
<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/blueimp-gallery/jquery.blueimp-gallery.min.js"></script>
<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-process.js"></script>
<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-image.js"></script>
<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-audio.js"></script>
<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-video.js"></script>
<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-validate.js"></script>
<script type="text/javascript" src="{$contextpath}/theme/plugins/jquery-file-upload/js/jquery.fileupload-ui.js"></script>	
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
	<c:if test="${fn:length(shuoms_file) > 0}">
	<div class="img_box cols3">
	<c:forEach var="p" items="${fn:split(shuoms_file, ',')}">
		<img alt="img" class="img" src="${pageContext.request.contextPath}/rm/s/hospital_common/${p}" 
		onerror="this.src='${pageContext.request.contextPath}/res/hospital_common/img/pdf.jpg'"
		data-src="${pageContext.request.contextPath}/rm/s/hospital_common/${p}" data-value="${p}">
	</c:forEach>
	</div>
	</c:if>
	</s:row>
	<s:row>
	<%-- <s:button label="保存数据"  id="baoc" onclick="save();" cols="1"></s:button> --%>
	<%-- <s:file id="upload_img" 
	filetypes="(.jpg|.png|.pdf|.jpeg|.gif|.JPG|.PNG|.PDF|.JPEG|.GIF)$" 
	maxlength="10" value="${shuoms_file}" name="ddd" cols="2" autoupload="false">
	</s:file> --%>
	<span class="btn btn-success fileinput-button">
      <i class="glyphicon glyphicon-plus"></i>
      <span>添加文件</span>
      <input type="file" name="files[]" multiple="">
    </span>
	</s:row>
</s:form>
<s:row>
</s:row>
</s:page>
<script>
$(function(){
	//$('.fileupload-buttonbar').append('<button  class="btn btn-sm blue" onclick="save();" cols="1">保存数据</button>')
})
function save(){
	var drug_code = '${param.drug_code}';
	/* var value = $('#upload_img').getData_file().ddd;
	if(drug_code){
		$.call('hospital_common.dictextend.dicthisdrug.updateSysByCodeShuoms',{'drug_code':drug_code,'shuoms_file':value},function(){
			$.closeModal();
		})
	} */
}
</script>
