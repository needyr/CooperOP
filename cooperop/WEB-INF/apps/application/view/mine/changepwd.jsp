<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="个人资料" dispermission="true">
<%-- <script type="text/javascript" src="${request.contextPath }/theme/scripts/controls/input/password.js"></script> --%>
	<s:row>
		<s:form border="0" id="myform" fclass="portlet light bordered">
			<s:row>
				<input type="hidden" name="id" value="${user.id }"/>
				<s:textfield label="原密码" datatype="password" name="oldpwd" required="true" minlength="6" value=""></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="新密码" datatype="password" name="newpwd" required="true" minlength="6" value=""></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="确认新密码" datatype="password"  name="newpwd2" required="true" minlength="6" value=""></s:textfield>
			</s:row>
			<s:row>
				<div class="cols">
					<s:button onclick="save();" color="green" label="保存"></s:button>
				</div>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	 function save() {
		   var data  = $("#myform").getData();
		   if(data.newpwd != data.newpwd2){
				$.error("两次输入的密码不一致！", function() {
				});
				return false;
		   }
			$.call("application.mine.updatePassword", data, function() {
				$.message("更新成功！", function() {
					location.reload(true);
				});
			}, function (emsg) {
				$.error(emsg, function() {
				});
			});
			return false;
		}
</script>

