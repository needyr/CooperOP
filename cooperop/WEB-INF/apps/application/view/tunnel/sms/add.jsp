<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="">
	<s:row >
		<s:form label="消息设置" id="setFrom">
			<s:toolbar>
				<s:button label="保存" onclick="save();"></s:button>
			</s:toolbar>
			<s:row>
				<input type="hidden" name="id" value="${id }"/>
				<s:radio label="网关" name="gateway" value="${gateway }">
					<s:option label="凌凯短信平台" value="1"></s:option>
					<s:option label="阿里短信平台" value="2"></s:option>
				</s:radio>
				<s:textfield label="通道名称" name="name" value="${name }" required="true" maxlength="64"></s:textfield>
				<s:textfield label="传输协议" name="protocol" value="${protocol }" required="true" maxlength="64" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="网关账户" name="username" value="${username }" required="true" maxlength="64"></s:textfield>
				<s:textfield label="网关密匙" name="password" value="${password }" required="true" maxlength="64"></s:textfield>
				<s:textfield label="每轮发送数量" name="limit_num" value="${limit_num }" required="true" max="500"></s:textfield>
				<s:textfield label="短信署名" name="signature" value="${signature }" required="true" maxlength="16"></s:textfield>
			</s:row>
			<s:row>
				<s:switch label="默认通道" name="is_default" value="${is_default }" onvalue="1" offvalue="0"></s:switch>
				<s:textfield label="收件人分隔符" name="addressee_separator" value="${addressee_separator }" placeholder="多收件人分隔符号" required="true"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
$(document).ready(function(){
})
function save(){
	if (!$("form").valid()) {
		return false;	
	}
	$.call("application.tunnel.sms.save", $("#setFrom").getData(), function(rtn) {
		if (rtn) {
			$.closeModal(true);
		}
	});
}
</script>
