<%@page import="java.util.Date"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="cn.crtech.cooperop.bus.session.Session"%>
<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
	pageContext.setAttribute("wx_appid", CommonFun.decryptString(SystemConfig.getSystemConfigValue("cooperop", "wx_appid")));
	pageContext.setAttribute("wx_state", Session.getSession(request, response).getId());
%>
 <script src="https://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
<s:page title="个人资料" dispermission="true">
<script src="${request.contextPath }/theme/scripts/controls/input/select.js" type="text/javascript"></script>
	<s:row>
	<input type="hidden" name=wx_appid value="${wx_appid }"/>
	<input type="hidden" name=wx_state value="${wx_state }"/>
		<s:form border="1" id="myform">
			<s:toolbar>
				<s:button label="录入指纹" icon="fa fa-save" onclick="addfinger()" ></s:button>
				<s:button label="关联微信" icon="fa fa-save" onclick="wx2()" ></s:button>
				<s:button onclick="save();" label="保存" icon="fa fa-save"></s:button>
			</s:toolbar>
			<s:row>
			<s:row>
				<s:image label="头像" name="avatar" value="${uinfo.avatar }" maxlength="10"></s:image>
			</s:row>
			<s:row>
				<input type="hidden" name="id" value="${uinfo.id }"/>
				<s:textfield label="姓名" cols="2" name="name" required="true"
					value="${uinfo.name}" readonly="true"></s:textfield>
				<s:textfield label="昵称" name="nick_name" cols="2" value="${uinfo.nick_name}"></s:textfield>
			</s:row>
			<s:row>
				<s:datefield label="生日" cols="2" name="birthday" value="${uinfo.birthday}" format="yyyy-MM-dd"></s:datefield>
				<s:radio label="性别" name="gender" cols="2" required="true" value="${uinfo.gender}">
					<s:option label="女" value="0"></s:option>
					<s:option label="男" value="1"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<s:textfield label="办公电话" cols="2" name="telephone" value="${uinfo.telephone}"></s:textfield>
				<s:textfield label="移动电话" cols="2" name="mobile" required="true" value="${uinfo.mobile}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="邮箱" cols="2" name="email" required="true"
					value="${uinfo.email}"></s:textfield>
				<s:textfield label="qq" cols="2" name="qq" value="${uinfo.qq}"></s:textfield>
			</s:row>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	function save() {
		if (!$("form").valid()) {
			return;
		}
		$.call("application.mine.update", $("#myform").getData(), function(rtn) {
			if (rtn.result == 'success') {
				$.message("更新成功");
			}
		},null,{async: false});
	}
	function wx2(){
		$.wx2();
	}
	function addfinger(){
		$.modal("/w/application/finger/addfinger.html", "指纹录入", {
			hrzhiyid: userinfo.id,
			callback: function(rtn) {
			}
		});
	}
</script>

