<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page ismodal="true" title="" dispermission="true">
	<s:row>
		<s:form id="setFrom" >
			<s:toolbar>
				<s:button label="确定" onclick="save();"></s:button>
				<s:button label="取消" onclick="returnback()"></s:button>
			</s:toolbar>
			<s:row>
				<s:image name="image_src" value="${image_src }" autoupload="false" islabel="" cols="4"></s:image>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	function save() {
		var rtn = [];
		var data = $("#setFrom").getData();
		data.image_name = $("[file_id='"+data.image_src+"']").attr("file_name");
		rtn.push({"data": data});
		$.closeModal(rtn);
	}
	function returnback() {
		$.closeModal(false);
	}
</script>
