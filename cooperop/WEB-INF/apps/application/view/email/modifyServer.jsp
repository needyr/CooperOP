<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="外部邮箱-资料维护" dispermission="true">
	<s:row>
		<s:form id="conditions" label="外部邮箱设置">
			<s:toolbar>
				<s:button label="保存" icon="fa fa-save" onclick="save();"></s:button>
				<s:button label="取消" icon="fa fa-ban" onclick="cancel();"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield cols="2" label="邮箱名称" name="name" value="${name}" placeholder="如：张三QQ邮箱"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield cols="1" label="账户" name="email" value="${email}" placeholder="通常为123@abc.com"></s:textfield>
				<s:textfield cols="1" label="密码" name="password" datatype="password" value="${password}" placeholder="账户密码"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield cols="1" label="POP服务器" name="pop_host" value="${pop_host}" placeholder="收件服务器"></s:textfield>
				<s:textfield cols="1" label="端口" name="pop_port" value="${pop_port}" placeholder="收件服务端口"></s:textfield>
				<s:switch cols="1" label="SSL加密" name="pop_ssl" value="${pop_ssl }" onvalue="1" offvalue="0"></s:switch>
				<s:textfield cols="1" label="收件间隔(分)" name="cycle_time" datatype="number" value="${cycle_time}" placeholder="每多少分钟自动收取一次邮件"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield cols="1" label="SMTP服务器" name="smtp_host" value="${smtp_host}" placeholder="发件服务器"></s:textfield>
				<s:textfield cols="1" label="端口" name="smtp_port" value="${smtp_port}" placeholder="发件服务端口"></s:textfield>
				<s:switch cols="1" label="SSL加密" name="smtp_ssl" value="${smtp_ssl }" onvalue="1" offvalue="0"></s:switch>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
function save() {
	var page = "";
	var data = $("#conditions").getData();
	if ("${id}") {
		data["id"] = "${id}";
		page = "application.email.updateServer";
	} else {
		page = "application.email.insertServer";
	}
	$.call(page, data, function(rtn) {
		$.closeModal(true);
	});
}
function cancel() {
	$.closeModal(false);
}
</script>
