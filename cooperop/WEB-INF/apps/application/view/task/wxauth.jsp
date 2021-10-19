<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="cn.crtech.cooperop.bus.session.Session"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
	pageContext.setAttribute("wx_appid", CommonFun.decryptString(SystemConfig.getSystemConfigValue("cooperop", "wx_appid")));
	pageContext.setAttribute("state", Session.getSession(request, response).getId());
	pageContext.setAttribute("now", new Date());
%>
 <script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
 <s:page title="微信授权审核" dispermission="true">
 	<s:row>
 		<div id="login_container" style="background-color: #fff;text-align:center;">
		</div>
 	</s:row>
 </s:page>
 <script type="text/javascript">
window.onload = function() {
var obj = new WxLogin({  
     id:"login_container",   
     appid: '${wx_appid}',   
     scope: "snsapi_login",   
     redirect_uri: encodeURIComponent("http://cooperop.crtech.cn/w/application/bill/wxauth.json"),  
     state: '${state}_'+'${param.tourl}_'+'${param.djbh}_'+'${param.order_id}_'+'${param.task_id}',  
     style: "black",
     href: ""
   });

}
</script>
