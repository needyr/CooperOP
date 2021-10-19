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
				<s:textfield label="通道名称" name="name" value="${name }" required="true" maxlength="64"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="服务器" name="server" value="${server }" required="true" maxlength="128"></s:textfield>
				<s:textfield label="用户名" name="account" value="${account }" required="true" maxlength="32"></s:textfield>
				<s:textfield label="密码" name="password" value="${password }" required="true" maxlength="32"></s:textfield>
				<s:textfield label="端口" name="port" value="${port }" required="true" maxlength="8"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="每轮发送数量" name="limit_num" value="${limit_num }" required="true" max="3"></s:textfield>
				<s:textfield label="邮件署名" name="signature" value="${signature }" required="true" maxlength="16"></s:textfield>
				<s:switch label="安全模式" name="security_gateway" value="${security_gateway }" required="true" onvalue="1" offvalue="0"></s:switch>
			</s:row>
			<s:row>
				<s:textfield label="邮件发送者" name="from_" value="${from_ }" maxlength="32"></s:textfield>
				<s:switch label="调试模式" name="debugging" value="${debugging }" onvalue="1" offvalue="0"/>
				<s:switch label="默认通道" name="is_default" value="${is_default }" onvalue="1" offvalue="0"></s:switch>
				<s:switch label="发送外部邮件" name="to_external_mailbox" value="${to_external_mailbox }" onvalue="1" offvalue="0"></s:switch>
				<s:textfield label="收件人分隔符" name="addressee_separator" value="${addressee_separator }" placeholder="多收件人分隔符号" required="true"></s:textfield>
				<s:textfield label="发送类" name="fun_class" value="${fun_class }" cols="4" placeholder="发送邮件的时候使用"></s:textfield>
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
	$.call("application.tunnel.email.save", $("#setFrom").getData(), function(rtn) {
		if (rtn) {
			$.closeModal(true);
		}
	});
}
</script>
