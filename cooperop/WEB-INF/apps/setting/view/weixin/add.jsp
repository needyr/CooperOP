<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
	<s:row>
		<s:form id="myform" label="微信基础管理">
			<s:toolbar>
				<s:button label="保存" onclick="save();"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="AppID" name="wx_appid" value="${wx_appid}" required="true" cols="2"></s:textfield>
			</s:row>
			<s:row>			
				<s:textfield label="AppSecret" name="wx_appsecret"  value="${wx_appsecret}" required="true" cols="2"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		$.call("setting.weixin.save", $("#myform").getData(), function(rtn) {
			if (rtn) {
				$.message("已加密保存！");
			}
		},null,{async: false});
	}
</script>
