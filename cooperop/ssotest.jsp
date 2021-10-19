<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@page import="cn.crtech.cooperop.bus.util.MD5"%>
<%@page import="java.util.Map"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="cn.crtech.cooperop.application.action.AuthAction"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%

	/**
	 * 使用方法：
	 *    访问地址：	http://协同办公服务器:端口/[发布应用名称]/sso.jsp?uid=xxx&ts=xxx&vs=xxx&rd=xxx&其它参数
	 *    参数说明：	uid	用户在系统中的唯一ID,v_system_user中的id
	 *				ts	登录时的Unix时间戳，精确到秒级，如：2017-09-01 12:01:01.001 = 1504238461001
	 *				vs	登录校验串，值等于MD5(uid + 协同办公配置的连接密钥 + timestamp)
	 *				rd	所需跳转的页面地址，格式为：/w/产品/网页.html 如：/w/application/welcome.html
	 *				其它参数	传向目标网页的其他参数
	 */

	String uid = "XTY00000231";
	String SSO_KEY = GlobalVar.getSystemProperty("sso.key", "!~CROP@CRTECH~!");
	long ts = System.currentTimeMillis();
	String vs = MD5.md5(uid + SSO_KEY + ts);
	String rd = "/w/bmc/index1.html";

	String url = "http://cooperop.crtech.cn/sso.jsp?uid=" + uid + "&ts=" + ts + "&vs=" + vs + "&rd=" + rd + "&_pid_=7800000000";
%>
<s:page title="单点登录测试" disloggedin="true">
	3秒后跳转： <%= url %>
	<script>
		setTimeout(function() {
			location.href = "<%=url%>";
		}, 3000);
	</script>
</s:page>
