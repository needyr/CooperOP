<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="cn.crtech.cooperop.bus.util.GlobalVar"%>
<%@page import="java.util.Date"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<!DOCTYPE html>
<s:page dispermission="true" disloggedin="true" title="登陆" ismodal="false" ismobile="true">
<%
	String welcome_page = GlobalVar.getSystemProperty("mobileLogin.jsp");
	pageContext.setAttribute("system_title", SystemConfig.getSystemConfigValue("global", "system_title"));
	pageContext.setAttribute("copyright", SystemConfig.getSystemConfigValue("global", "copyright"));
	pageContext.setAttribute("now", new Date());
%>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,initial-scale=1.0,width=device-width" />
<meta name="format-detection"
	content="telephone=no,email=no,date=no,address=no">
<title>${system_title }</title>
<style type="text/css">
	.fa {
	    display: inline-block;
	    font: normal normal normal 14px/1 FontAwesome;
	    font-size: 1.5em;
	    /* float: right; */
	    margin-top: 1px;
	    position: absolute;
	    right: 11px;
	    top: 9px;
	    /* line-height: 3px; */
	    text-rendering: auto;
	    -webkit-font-smoothing: antialiased;
	    -moz-osx-font-smoothing: grayscale;
	    transform: translate(0, 0);
	}
	.ui-menu-item{
		font-size: 1.2em;
		padding: 3px 0 3px 5px!important;
	}
	.form-control{
		height: 2em;
		font-size: 1.5em;
	}
	
</style>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/plugins/font-awesome/css/font-awesome.min.css?iml=Y">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/theme/mobile/mobileLogin.css?iml=Y">
	
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.cokie.min.js?iml=Y"
	type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/plugins/jquery-validation/js/jquery.validate.min.js?iml=Y"></script>
<!-- END PAGE LEVEL PLUGINS -->


<script src="${pageContext.request.contextPath}/theme/scripts/controls/input/imagevalidcode.js?iml=Y"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/clipboard/clipboard.min.js?iml=Y"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.sha1.js?iml=Y"></script>
</head>
<body>
	<div id="wrap">
		<div class="header">
			<img src="${pageContext.request.contextPath}/theme/img/logo.png?iml=Y" alt="">
		</div>
		<div id="main" >
			<s:row>
				<s:select nolabel="true" name="khserver" value="" action="application.khzl.queryKHZL" placeholder="选择登陆企业">
					<s:option value="" label="请选择登陆企业"></s:option>
					<s:option value="crtech001,http://120.24.14.166:8123" label="超然内部管理系统"></s:option>
					<s:option value="$[kehuid],$[app_ip_port]" label="$[kehumingcheng]"></s:option>
				</s:select>
			</s:row>
			<input name="userid" required="required" type="text" class="main-ipt" placeholder="账号/手机号">
			<input name="password" required="required" type="password" class="main-ipt" placeholder="密码">
			<input name="deviceId" class="main-ipt" style="display:none;" readonly="readonly"/>
			<!-- <input name="deviIdmess" id="deviid" /> -->
			<button type="button" class="btn main-btn" style="display:none;" data-clipboard-action="copy">
			<span>复制</span></button>
			<a href="javascript: re();" class="wangji"><p class="main-p">忘记密码</p></a><%--  <a href="javascript: submitMe();"
				class="main-btn"><span>登录</span></a> --%>
			<button type="submit" class="main-btn" onclick="submitMe();return false;"><span>登录</span></button>
			<p class="tish" style="display:none;text-align: center;margin: 2em auto;line-height: 1.8em;color:red;">请将此页面截图发送给系统管理人员进行app绑定，绑定后此手机app将只能登陆该账号</p>
		</div>
		<div id="footer">
			<p class="footer-p"><fmt:formatDate value="${now}" pattern="yyyy"/> &copy; ${copyright }</p>
		</div>
	</div>
</body>
</html>
</s:page>
<script type="text/javascript">
if(clipboard){
	clipboard.destroy();
}
var clipboard = new Clipboard('.btn');
clipboard.on('success', function(e) {
   $.message("复制成功！");
});

	var deviceId;
	apiready = function() {
		var u = store.getJson("user");
		if(u){
			$("#main").find('input[name="userid"]').val(u.userid);
			$("#main").find('input[name="password"]').val(u.password);
			if(u.khserver){
				//submitMe(u);
				setTimeout(function(){
					$("#main").find('select[name="khserver"]').val(u.khserver);
				},1000); 
			}
		}
		api.parseTapmode();
		deviceId = api.deviceId;
	};
	jQuery(document).ready(
			function() {
				$.call("application.auth.isneedvalid", {}, function(rtn) {
					if (rtn) {
						$(".form-validcode").show();
						$(".form-validcode").find("[name='validcode']").attr("required", "required");
					}
				});
				
			});
	
	var submitMe = function(usto) {
		var da = $("#main").getData();
		da.ism = "Y";
		da.deviceid = deviceId;
		if(usto){
			da.khserver = usto.khserver;
			da.isvla = usto.isvla;
			var ukhbh = usto.khserver.substring(0, usto.khserver.indexOf(","));
			var push = api.require('push');
			push.unbind({
			    userName: usto.uname,
			    userId: usto.uid+"_"+ukhbh
			},function(ret,err){
			    if(ret.status){
			       // api.alert({msg:'解除绑定成功'});
			    }else{
			        //api.alert({msg:err.msg});
			    }
			});
		}
		
		var ser = da.khserver.substring(da.khserver.indexOf(",")+1);
		var khbh = da.khserver.substring(0, da.khserver.indexOf(","));
		$.getJSON(ser+"/w/application.auth.login.jsonp?callback=?", da, 
		    function(rtn) {
				if (rtn.emsg) {
					$.error(rtn.emsg, function(){
						$("input[name='password']").val("");
						$("body").focus();
						//$("input[name='password']").focus(); 
					});
					if (rtn.isneedvalid) {
						$(".form-validcode").show();
						$(".form-validcode").find("[name='validcode']").attr("required", "required");
						$(".form-validcode").find("img[cooperoptype='imagevalidcode_img']").click();
					} else {
						$(".form-validcode").hide();
						$(".form-validcode").find("[name='validcode']").removeAttr("required");
					}
				} else {
					if(rtn.bang_deviceId == "Y"){
						$("#main").find('input[name="password"]').hide();
						$(".main-btn").hide();
						$(".wangji").hide();
						$("#main").find('input[name="deviceId"]').val(deviceId);
						$("#main").find('input[name="deviceId"]').show();
						$("#main").find('.btn.main-btn').show();
						$("#main").find('.btn.main-btn').attr("data-clipboard-text", "账号："+$("#main").find('input[name="userid"]').val()+"申请绑定设备号："+deviceId);
						$(".tish").show();
					}else{
						da.uid = rtn.userinfo.id;
						da.uname = rtn.userinfo.id;
						store.set('user',da);
						var u = (rtn.redirect_url).split("?");
						var url = ser+"/"+rtn.redirect_url;
						
						var push = api.require('push');
						push.bind({
						    userName:da.uname,
						    userId: da.uid+"_"+khbh
						},function(ret,err){
						    if(ret.status){
						       // api.alert({msg:'绑定成功'});
						    }else{
						       // api.alert({msg:err.msg});
						    }
						});
						
						api.openWin({name: "main", 
							url: url,
							params: {_CRSID: $.cookie("_CRSID")},
							bounces: false,
							reload : true});
					}
				}
	    });  
	}	
	function re(){
		location.reload();
	}
</script>
