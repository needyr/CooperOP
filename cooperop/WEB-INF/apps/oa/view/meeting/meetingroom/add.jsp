<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true" >
	<s:row>
		<s:form border="0" id="myform">
			<s:row>
				<input type="hidden" name="id" value="${id }"/>
				<s:textfield label="会议室名称" name="name" value="${name }" required="true" cols="4"></s:textfield>
				<s:autocomplete label="会议室地址" name="address" action="oa.meeting.meetingroom.queryAddressDistinct" 
					limit="10" editable="true" cols="2" value="${address }" required="true"> 
 					<s:option value="$[address]" label="$[address]"></s:option>
 				</s:autocomplete>
				<s:textfield label="可容纳人数" name="galleryful" number="true" value="${galleryful}" required="true" cols="2"></s:textfield>
				<s:image label="图片" name="img" value="${img}" maxlength="10" cols="4"></s:image>
			</s:row>
			<s:row style="text-align: center;margin-top: 10px;">
				<s:button label="保存" onclick="save();"></s:button>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		var d = $("#myform").getData();
		var action = "oa.meeting.meetingroom.insert";
		if(d.id){
			action = "oa.meeting.meetingroom.update";
		}
		$.call(action, d, function(rtn) {
			if (rtn.result == "success") {
				$.closeModal(true);
			}else{
				$.error(rtn.msg);
			}
		});
	}
</script>
