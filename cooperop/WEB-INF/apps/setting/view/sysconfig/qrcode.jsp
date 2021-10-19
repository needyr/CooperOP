<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
	<s:row>
		<s:form id="myform" label="系统设置">
			<s:toolbar>
				<s:button label="保存并重新加载" icon="fa fa-save" onclick="save();"></s:button>
			</s:toolbar>
			<s:row>
				<s:image cols="4" label="安卓二维码" name="android_qrcode" value="${android_qrcode }" maxlength="1"></s:image>
				<s:image cols="4" label="苹果二维码" name="ios_qrcode" value="${ios_qrcode }" maxlength="1"></s:image>
			</s:row>
			<s:row>
				<s:switch label="开启app校验" name="check_deviceId" value="${check_deviceId }" onvalue="Y" offvalue="N"></s:switch>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		$.call("setting.sysconfig.saveAndRe", $("#myform").getData(), function(rtn) {
			if (rtn) {
				//$.closeModal(true);
			}
		});
	}
</script>
