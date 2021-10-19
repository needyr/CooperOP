﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@page import="java.util.Date"%>
<%
	pageContext.setAttribute("wx_appid", CommonFun.decryptString(SystemConfig.getSystemConfigValue("cooperop", "wx_appid")));
	pageContext.setAttribute("_t", System.currentTimeMillis());
	pageContext.setAttribute("copyright", SystemConfig.getSystemConfigValue("global", "copyright"));
	pageContext.setAttribute("now", new Date());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<title>欢迎登录</title>
<link href="${pageContext.request.contextPath}/theme/plugins/google-fonts/opensans.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/theme/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/theme/css/login.css" rel="stylesheet" type="text/css" />
<body>
	<div class="container-login">
		<div class="login-wrap">
			<div class="window-control">
				<a id="close_" class="closebtn" href="javascript: void(0);"><i class="cicon icon-imize-exit"></i></a>
			</div>
			<div class="title"><h2><img src="${pageContext.request.contextPath}/theme/img/xin.png"></h2></div>
			<div class="loginbox">
				<form id="loginForm" action="" method="post" onsubmit="submitMe();return false;">
					<div class="logindiv forms">
						<div class="element ">
							<label><i class="cicon icon-user"></i></label>
							<input name="userid" type="text" placeholder="用户名/手机号/邮箱">
							<span class="msg">用户名不存在，请检查后重试！</span>
						</div>
						<div class="element">
							<label><i class="cicon icon-lock"></i></label>
							<input name="password" type="password" placeholder="请输入密码">
						</div>
						<div class="element yzm" cooperoptype="imagevalidcode">
							<label><i class="cicon icon-approval"></i></label>
							<input type="text" cooperoptype="imagevalidcode_input"
								autocomplete="off" placeholder="验证码" name="validcode" >
							<div class="imgbox">
								<img cooperoptype="imagevalidcode_img" alt="验证码">
							</div>
						</div>
						<div class="btnbox"><button class="submit">登录</button></div>
					</div>
					<div class="logindiv finger"  style="display: none;">
						<div class="scan">
							<i class="cicon icon-fingerprint"></i>
							<span class="masker"></span>
						</div>
						<span>使用 <font color="red">中控Live20R指纹仪</font> 扫描识别指纹</span>
					</div>
					<div class="logindiv qr-code">
						<a class="btn-close" href="javascript:void(0);"><i class="cicon icon-close"></i></a>
						<div id="login_container">
						</div>
					</div>
				</form>
				<div class="others">
					<ul>
						<li class="account-div" style="display: none"><a href="javascript:void(0);"><i class="cicon icon-user"></i></a></li>
						<li class="weixin-div"><a href="javascript:void(0);" title="微信扫码登录"><i class="cicon icon-wchat"></i></a></li>
						<li class="zhiwen-div"><a class="zhiwen-div" href="javascript:void(0);" title="指纹认证登录"><i class="cicon icon-finger"></i></a></li>
					</ul>
				</div>
				<a class="setting" id="clientsettingsid" style="display:none;" href="javascript:void(0)">高级设置<i class="cicon icon-go"></i></a>
			</div>
		</div>
		<div class="copyfooter"><fmt:formatDate value="${now}" pattern="yyyy"/> &copy; ${copyright }</div>
	</div>
</body>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.json.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/theme/plugins/jquery-validation/js/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/controls/input/imagevalidcode.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/controls/controls.js"
	type="text/javascript"></script>
 <script src="https://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/login.js" type="text/javascript"></script>
</html>
<script>
var appid = '${wx_appid }';
var cooperopcontextpath = "${pageContext.request.contextPath}";
jQuery(document).ready(
	function() {
		$("input[name='userid']").focus();
		if(typeof crtechTogglePage != 'undefined'){
			$('#clientsettingsid').show();
			$('#clientsettingsid').click(function(){
				location.href = cooperopcontextpath + '/w/xdesigner/product/localmiddbconfig.html'
			});
		}
		//$("#loginForm").getData();
		$.call("application.auth.isneedvalid", {}, function(rtn) {
			if (rtn) {
				$(".form-validcode").show();
				$(".form-validcode").find("[name='validcode']").attr("required", "required");
			}
		});
		$("input[name='userid']").val($.cookie('login_name'));
	});

	var submitMe = function() {
		$.call("application.auth.login_v12", $("#loginForm").getData(), function(rtn) {
			if (rtn.emsg) {
				if(rtn.error_flag == 'A'){
					$(".forms .element:first").addClass("error-tips");
					$(".forms .element:first").find(".msg").text(rtn.emsg);
				}else{
					$(".forms .element:first").addClass("error-tips");
					$(".forms .element:first").find(".msg").text(rtn.emsg);
					/* $.error(rtn.emsg, function(){
						//$("input[name='password']").focus();
					}); */
					$("input[name='password']").val("");
					$("body").focus();
					if (rtn.isneedvalid) {
						$(".yzm").show();
						$(".yzm").find("[name='validcode']").attr("required", "required");
						$(".yzm").find("img[cooperoptype='imagevalidcode_img']").click();
					} else {
						$(".yzm").hide();
						$(".yzm").find("[name='validcode']").removeAttr("required");
					}
				}
			} else {
				$.cookie('login_name', $("input[name='userid']").val(), {expires: 7, path:'/'});
				if(typeof crtechTogglePage == 'undefined'){
					location.href = cooperopcontextpath + rtn.redirect_url;
				}else{
					$("input[name='password']").val("");
					//$("input[name='userid']").val("");
					var params = {products:{}, BaseUserInfo: rtn.v12_userinfo.BaseUserInfo};
					params["BaseUserInfo"]["clientid"] = rtn._CRSID;
					for (var i = 0; i < rtn.v12_userinfo.middbconfigs.length; i++) {
						var tmp = rtn.v12_userinfo.middbconfigs[i];
						if (params["products"][tmp.system_product_code] == undefined) {
							params["products"][tmp.system_product_code] = {};
						}
						params["products"][tmp.system_product_code][tmp.key] = tmp.value;
					}
					var exParams = getLocalFullConfigs();
					Object.keys(params["products"]).forEach(function(key){
						$.extend(true,params["products"][key], exParams);
					});
					//var params = '{"Compression":"否","DESKey":"","Database":"","Database2":"","Encrypt":"否","IP":"crtech.imwork.net","IP2":"","LocalPassword":"","LocalServer":"","LocalUserName":"","POS打印机端口":"POS打印机端口","Port":"9012","Port2":"","bmid":"","bmname":"","jigid":"","jigname":"","username":"admin","userpass":"123.","zhiyid":"","下载资料":"否","临时销售启动路径":"","使用客显":"否","客显端口":"","打开钱箱指令":"27,112,0,60,240","打开钱箱选择":"","找零灯显示指令":"Chr(27)+Chr(115)+4","收款灯显示指令":"Chr(27)+Chr(115)+3","收款语音":"否","数字显示指令":"Chr(27) + Chr(81) + Chr(65) + &number + Chr(13)","波特率":"2400","自动打开钱箱":"否","金额灯显示指令":"Chr(27)+Chr(115)+2","默认打印机":""}';
					if (Object.getOwnPropertyNames(params["products"]).length < 1) {
						$.message("登陆失败，请配置中间件信息.");
						return;
					}
					//params = $.extend(params.BaseUserInfo, params[tmpKey]);
					// alert(JSON.stringify(params));
					crtechLogin("", rtn._CRSID, $.toJSON(params), 0);
				}
			}
		}, function(ems) {
			$.error(ems);
		});
	}
</script>
