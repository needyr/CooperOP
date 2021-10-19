<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("_t", System.currentTimeMillis());
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
<link href="${pageContext.request.contextPath}/theme/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/theme/plugins/iconfont/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/theme/css/login.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/theme//plugins/layer/skin/layer.css" rel="stylesheet" type="text/css" />
<link href="http://at.alicdn.com/t/font_1122195_o43sfpxr5sj.css" rel="stylesheet" type="text/css" />
<body>
	<div class="bgDiv"></div>
	<div class="">
	<img src="${pageContext.request.contextPath}/theme/img/logo-big.png" alt="" class="logo">
		<div class="content">
			<div class="box">
				<form class="loginDiv" id="loginForm" action="" method="post" onsubmit="submitMe();return false;">
					<p class="qiehuan"><span class="active">账号登陆</span></p>
					<p class="ts-title">用户名或密码有误，请重新输入或<a href="">找回密码</a></p>
					<div class="loginItem">
	                	<i class="loginI icon-user"></i><input type="text" name="userid" value="" class="ipt">
	                	<span class="login_close">×</span>
	              	</div>
	              	<div class="loginItem">
	                	<i class="loginI cicon icon-lock"></i><input type="password" name="password" value="" class="ipt">
	                	<span class="login_close">×</span>
	              	</div>
					<div class="form-group form-validcode loginItem" cooperoptype="imagevalidcode">
						<input class="form-control placeholder-no-fix" type="text" cooperoptype="imagevalidcode_input"
								autocomplete="off" placeholder="验证码" name="validcode" />
						<img cooperoptype="imagevalidcode_img" alt="验证码">
					</div>
					<div class="loginItem">
		                <button type="submit" name="button" class="btn">登陆</button>
		                <p><a id="clientsettingsid" style="display:none;" href="javascript:void(0)"><i class="fa icon-settings"></i> 设置</a><a href=""><i class="fa fa-question"></i> 忘记密码</a></p>
					</div>
				</form>
				<div class="saoma">
					<div class="app-login lock-body">
						<p class="stitle">超然app扫码登录</p>
						<img src="${pageContext.request.contextPath}/theme/img/chaoran_winxin.png" alt="">
						<p class="sEnd">还未安装app？<a href="javascrpit:void(0);">点击下载安卓app</a></p>
						<p class="sEnd">ios系统请在应用商店下载安装</p>
					</div>
					<div class="wx-login lock-body">
						<p class="stitle">微信扫码登录</p>
						<img src="${pageContext.request.contextPath}/theme/img/chaoran_winxin.png" alt="">
						<p class="sEnd">微信扫描登录，需系统账号已关联绑定微信!</p>
					</div>
					<div class="finger-login lock-body">
						<p class="stitle">指纹登录</p>
						<div class="zhiwen-img">
							<img alt="" src="${pageContext.request.contextPath}/theme/layout/img/finger.png"/>
							<span class="finger-line"></span>
						</div>
						<p class="sEnd">使用<font color="red">中控live20r</font>登录，需系统账号已录入绑定指纹!</p>
					</div>
					<div class="lock-bottom">
						<div class="app-div qhdiv">
							<a href="javascript:void(0);" class="app-btn"><i
								class="cicon icon-lock"></i>超然app登录</a>
						</div>
						<div class="weixin-div qhdiv">
							<a href="javascript:void(0);" class="weixin-btn"><i class="cicon icon-wchat"></i>微信登录</a>
						</div>
						<div class="zhiwen-div qhdiv">
							<a href="javascript:void(0);" class="zhiwen-btn"><i class="cicon icon-finger"></i>指纹登录</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
 
  <p class="btP">© 2009-2017 123456789.com 版权所有 ICP证：浙B2-20080101</p>
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
<script src="${pageContext.request.contextPath}/theme/scripts/login.js" type="text/javascript"></script>
</html>
<script>
var cooperopcontextpath = "${pageContext.request.contextPath}";
jQuery(document).ready(
	function() {
		if(typeof crtechTogglePage != 'undefined'){
			$('#clientsettingsid').show();
			$('#clientsettingsid').click(function(){
				location.href=cooperopcontextpath + '/w/crdc/product/localmiddbconfig.html'
			});
		}
		$("#loginForm").getData();
		$.call("application.auth.isneedvalid", {}, function(rtn) {
			if (rtn) {
				$(".form-validcode").show();
				$(".form-validcode").find("[name='validcode']").attr("required", "required");
			}
		});
		
	});
	
	var submitMe = function() {
		$.call("application.auth.login_v12", $("#loginForm").getData(), function(rtn) {
			if (rtn.emsg) {
				if(rtn.error_flag == 'A'){
					$.message(rtn.emsg);
				}else{
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
				}
			} else {
				if(typeof crtechTogglePage == 'undefined'){
					location.href = cooperopcontextpath + rtn.redirect_url;
				}else{
					$("input[name='password']").val("");
					$("input[name='userid']").val("");
					var params = {products:{}, BaseUserInfo: rtn.v12_userinfo.BaseUserInfo};
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
					crtechLogin(rtn._CRSID, $.toJSON(params));
				}
			}
		}, function(ems) {
			$.error(ems);
		});
	}
</script>
