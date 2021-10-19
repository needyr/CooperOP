﻿
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<%@page import="java.util.Date" %>
<%
    pageContext.setAttribute("wx_appid", CommonFun.decryptString(SystemConfig.getSystemConfigValue("cooperop", "wx_appid")));
    pageContext.setAttribute("_t", System.currentTimeMillis());
    pageContext.setAttribute("copyright", SystemConfig.getSystemConfigValue("global", "copyright"));
    pageContext.setAttribute("now", new Date());
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <title>欢迎登录</title>
    <link href="${pageContext.request.contextPath}/theme/css/login_new.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/theme/plugins/google-fonts/opensans.css" rel="stylesheet"
          type="text/css"/>
    <link href="${pageContext.request.contextPath}/theme/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet"
          type="text/css"/>
</head>
<body>
<div class="login-container">
    <div class="sys-title"></div>
    <div class="login-wrap">
        <div class="panel">
            <div class="login-title"><span>登录</span></div>
            <div class="forms">
                <form class="login-Div" id="login-form" action="" method="post" onsubmit="submitMe();return false;">
                    <div class="">
                        <div class="form-element forms">
                            <input class="input-field" type="text" placeholder="用户名/手机号/邮箱" name="userid">
                            <i class="icons">
                                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="34" viewBox="0 0 30 34">
                                    <path class="icon-path"
                                          d="M972.231,561A1.227,1.227,0,0,1,971,559.809v-0.854a14.429,14.429,0,0,1,9.385-13.569l0.256-.1-0.231-.155A9.892,9.892,0,0,1,985.923,527h0.052a9.923,9.923,0,0,1,9.974,9.866,9.769,9.769,0,0,1-4.436,8.208l-0.231.156,0.257,0.1A14.97,14.97,0,0,1,1001,559.11v0.674a1.236,1.236,0,0,1-2.461,0,9.9,9.9,0,0,0,0-1.218,12.549,12.549,0,0,0-25.052,0,7.965,7.965,0,0,0,0,1.114v0.181a1.3,1.3,0,0,1-1.256,1.14h0Zm13.744-31.566a7.487,7.487,0,0,0-7.539,7.457,7.543,7.543,0,0,0,15.077,0,7.487,7.487,0,0,0-7.538-7.457h0Zm0,0"
                                          transform="translate(-971 -527)"/>
                                </svg>
                            </i>
                            <span class="tips">用户名不存在，请重新输入</span>
                        </div>
                        <div class="form-element">
                            <input class="input-field" type="password" placeholder="请输入密码" name="password">
                            <i class="icons">
                                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="34" viewBox="0 0 30 34">
                                    <path class="icon-path"
                                          d="M998.857,669H973.143A2.284,2.284,0,0,1,971,666.875V648.812a2.307,2.307,0,0,1,2.015-2.12h2.517v-1.384a10.469,10.469,0,0,1,20.936,0v1.384h2.512a2.37,2.37,0,0,1,2.02,2.12v18.063A2.35,2.35,0,0,1,998.857,669h0Zm-4.42-23.718a8.464,8.464,0,0,0-16.926,0v1.41h16.926v-1.41Zm4.42,3.53H973.143v18.063h25.714V648.812ZM985.193,652.6h1.9a0.207,0.207,0,0,1,.208.205v10.729a0.207,0.207,0,0,1-.208.206h-1.9a0.207,0.207,0,0,1-.208-0.206V652.808a0.206,0.206,0,0,1,.208-0.205h0Zm0,0"
                                          transform="translate(-971 -635)"/>
                                </svg>
                            </i>
                            <span class="tips">用户名不存在或密码错误，请重新输入</span>
                        </div>
                        <div class="loginItem">
                            <button class="submit-btn" type="submit">登 录</button>
                            <p>
                                <a id="client-setting-sid" style="display:none;" href="javascript:void(0)">
                                    <i class="fa icon-settings"></i>设置</a>
                            </p>
                        </div>
                    </div>
                </form>
            </div>
            <div class="logindiv finger finger-div">
                <div class="scan">
                    <i class="cicon icon-fingerprint"></i>
                    <span class="masker"></span>
                </div>
                <span>使用 <font color="red">中控Live20R指纹仪</font> 扫描识别指纹</span>
            </div>
            <div class="login-type">
                <a class="btn-account" href="javascript:void(0);" style="margin-left: 62px;"><i class="cicon icon-user"></i></a>
                <a class="btn-wechat weixin-btn" href="javascript:void(0);"></a>
                <a class="btn-finger zhiwen-btn" href="javascript:void(0);"></a>
            </div>
        </div>
        <div class="wei-div" style="text-align: center;display: none">
            <a class="btn-close" href="javascript:void(0);"><i class="cicon icon-close"></i></a>
            <br><br><br><br><br>
            <div class="wx-display">
                <img src="${pageContext.request.contextPath}/theme/img/wx.jpg" class="weixin-png">
                <p>请使用微信扫描二维码登录</p>
                <p>医策智能辅助决策支持云平台</p>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery-migrate.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.json.min.js" type="text/javascript"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/theme/plugins/jquery-validation/js/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/controls/input/imagevalidcode.js"
        type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/plugins/layer/layer.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/common.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/theme/scripts/controls/controls.js"
        type="text/javascript"></script>
<!--  <script src="https://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script> -->
<script src="${pageContext.request.contextPath}/theme/scripts/login_v12.js" type="text/javascript"></script>
<script>
    var cooperopcontextpath = "${pageContext.request.contextPath}";
    jQuery(document).ready(
        function () {
            $("input[name='userid']").focus();
            if (typeof crtechTogglePage != 'undefined') {
                $('#clientsettingsid').show();
                $('#clientsettingsid').click(function () {
                    location.href = cooperopcontextpath + '/w/xdesigner/product/localmiddbconfig.html'
                });
            }
            //$("#loginForm").getData();
            $.call("application.auth.isneedvalid", {}, function (rtn) {
                if (rtn) {
                    $(".form-validcode").show();
                    $(".form-validcode").find("[name='validcode']").attr("required", "required");
                }
            });
            $("input[name='userid']").val($.cookie('login_name'));
        });


    var submitMe = function () {
        $.call("application.auth.login_v12", $("#login-form").getData(), function (rtn) {
            //登录出现问题
            if (rtn.emsg) {
                if (rtn.error_flag == 'A') {
                    $("#login-form .form-element:nth-child(1)").addClass("error-tips");
                } else {
                    $("#login-form .form-element:nth-child(2)").addClass("error-tips");
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
                //验证成功
            } else {
                $.cookie('login_name', $("input[name='userid']").val(), {expires: 7, path: '/'});
                if (typeof crtechTogglePage == 'undefined') {
                    location.href = cooperopcontextpath + rtn.redirect_url;
                } else {
                    $("input[name='password']").val("");
                    //$("input[name='userid']").val("");
                    var params = {products: {}, BaseUserInfo: rtn.v12_userinfo.BaseUserInfo};
                    params["BaseUserInfo"]["clientid"] = rtn._CRSID;
                    for (var i = 0; i < rtn.v12_userinfo.middbconfigs.length; i++) {
                        var tmp = rtn.v12_userinfo.middbconfigs[i];
                        if (params["products"][tmp.system_product_code] == undefined) {
                            params["products"][tmp.system_product_code] = {};
                        }
                        params["products"][tmp.system_product_code][tmp.key] = tmp.value;
                    }
                    var exParams = getLocalFullConfigs();
                    Object.keys(params["products"]).forEach(function (key) {
                        $.extend(true, params["products"][key], exParams);
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
        }, function (ems) {
            $.error(ems);
        });
    }
</script>
</html>
