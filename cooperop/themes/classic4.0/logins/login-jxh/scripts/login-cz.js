//指纹登录按钮点击
$(".login-type .zhiwen-btn").on("click", function () {
    $(".login-wrap .finger-div").removeClass("display-hidden");
    $(".login-wrap .panel").removeClass("display-show");
    $(".login-wrap .wei-div").removeClass("display-show");

    $(".btn-finger").removeClass("display-show");
    $(".btn-wechat").removeClass("display-hidden");
    $(".btn-account").removeClass("display-hidden");

    $(".login-wrap .wei-div").addClass("display-hidden");
    $(".login-wrap .finger-div").addClass("display-show");
    $(".login-wrap .panel").addClass("display-hidden");

    $(".btn-finger").addClass("display-hidden");
    $(".btn-wechat").addClass("display-show");
    $(".btn-account").addClass("display-show");

    $(".btn-wechat").css("margin-left","100px");
    usefinger();
});

//点击登录
$(".login-type .btn-account").on("click", function () {

    $(".login-wrap .panel").removeClass("display-hidden");
    $(".login-wrap .finger-div").removeClass("display-show");
    $(".login-wrap .wei-div").removeClass("display-show");

    $(".btn-finger").removeClass("display-hidden");
    $(".btn-account").removeClass("display-show");
    $(".btn-wechat").removeClass("display-hidden");


    $(".btn-account").addClass("display-hidden");
    $(".btn-wechat").addClass("display-show");
    $(".btn-finger").addClass("display-show");
    $(".login-wrap .panel").addClass("display-show");
    $(".finger-div").addClass("display-hidden");
    $(".wei-div").addClass("display-hidden");

    $(".btn-wechat").css("margin-left","0");
    cancelfinger();
});

//微信登陆按钮点击
$(".login-type .weixin-btn").on("click", function () {

    $(".login-wrap .wei-div").removeClass("display-hidden");
    $(".login-wrap .finger-div").removeClass("display-show");
    $(".login-wrap .panel").removeClass("display-show");

    $(".login-wrap .btn-finger").removeClass("display-hidden");
    $(".login-wrap .btn-account").removeClass("display-hidden");
    $(".login-wrap .btn-wechat").removeClass("display-show");


    $(".btn-account").addClass("display-show");
    $(".btn-wechat").addClass("display-hidden");
    $(".btn-finger").addClass("display-show");

    $(".login-wrap .panel").addClass("display-hidden");
    $(".finger-div").addClass("display-hidden");
    $(".wei-div").addClass("display-show");

    var obj = new WxLogin({  //https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=eed48c49b75ad25e653a6a36bc4a0df4582cdf1c&lang=zh_CN
        id: "login_container",   //第三方页面显示二维码的容器id
        appid: appid,   //应用唯一标识，在微信开放平台提交应用审核通过后获得
        scope: "snsapi_login",   //应用授权作用域，拥有多个作用域用逗号（,）分隔，网页应用目前仅填写snsapi_login即可
        redirect_uri: encodeURIComponent("http://cooperop.crtech.cn/w/application/auth/wxlogin.json"),  //重定向地址，需要进行UrlEncode
        state: "wx_login",  //用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
        style: "black",   //提供"black"、"white"可选，默认为黑色文字描述
        href: ""
    });
    // cancelfinger();
});

//关闭按钮
$(".wei-div .btn-close").on("click", function () {
    $(".login-wrap .panel").removeClass("display-hidden");
    $(".login-wrap .finger-div").removeClass("display-show");
    $(".login-wrap .wei-div").removeClass("display-show");

    $(".btn-finger").removeClass("display-hidden");
    $(".btn-account").removeClass("display-show");
    $(".btn-wechat").removeClass("display-hidden");


    $(".btn-account").addClass("display-hidden");
    $(".btn-wechat").addClass("display-show");
    $(".btn-finger").addClass("display-show");

    $(".login-wrap .panel").addClass("display-show");
    $(".finger-div").addClass("display-hidden");
    $(".wei-div").addClass("display-hidden");

    $(".btn-wechat").css("margin-left","0");
});

function getLocalFullConfigs() {
    var obj = {};
    for (var i = 1; i < 4; i++) {
        var params = getLocalConfigs(i);
        for (var j = 0; j < params.length; j++) {
            obj[params[j].key] = params[j].value;
        }
    }
    return obj;
}

function getLocalConfigs(n) {
    var loginExJsonParams = localStorage.getItem("loginExJsonParams" + n);
    var params = [];
    if (!loginExJsonParams) {
        params = getLocalDefaultConfigs(n);
    } else {
        params = JSON.parse(loginExJsonParams);
    }
    return params;
}

function getLocalDefaultConfigs(n) {
    var h = [];
    if (n == 1) {
        h.push({"inx": "1", "key": "POS打印机端口", "value": "POS打印机端口", "description": "POS打印机端口"});
        h.push({"inx": "1", "key": "打开钱箱选择", "value": "", "description": "打开钱箱选择"});
        h.push({"inx": "1", "key": "打开钱箱指令", "value": "27,112,0,60,240", "description": "打开钱箱指令"});
        h.push({"inx": "1", "key": "默认打印机", "value": "", "description": "默认打印机"});
        h.push({"inx": "1", "key": "收款语音", "value": "否", "description": "收款语音"});
        h.push({"inx": "1", "key": "自动打开钱箱", "value": "否", "description": "自动打开钱箱"});
    } else if (n == 2) {
        h.push({"inx": "2", "key": "波特率", "value": "2400", "description": "波特率"});
        h.push({"inx": "2", "key": "金额灯显示指令", "value": "Chr(27)+Chr(115)+2", "description": "金额灯显示指令"});
        h.push({"inx": "2", "key": "客显端口", "value": "", "description": "客显端口"});
        h.push({"inx": "2", "key": "使用客显", "value": "否", "description": "使用客显"});
        h.push({"inx": "2", "key": "收款灯显示指令", "value": "Chr(27)+Chr(115)+3", "description": "收款灯显示指令"});
        h.push({
            "inx": "2",
            "key": "数字显示指令",
            "value": "Chr(27) + Chr(81) + Chr(65) + &number + Chr(13)",
            "description": "数字显示指令"
        });
        h.push({"inx": "2", "key": "找零灯显示指令", "value": "Chr(27)+Chr(115)+4", "description": "找零灯显示指令"});
    } else if (n == 3) {
        h.push({"inx": "3", "key": "LocalServer", "value": "", "description": "本地数据库 IP"});
        h.push({"inx": "3", "key": "LocalUserName", "value": "", "description": "本地数据库用户名"});
        h.push({"inx": "3", "key": "LocalPassword", "value": "", "description": "本地数据库密码"});
    }
    return h;
}


function usefinger() {
    $.loginfinger({
        callback: function (rtn) {
            if (rtn.redirect_url) {
                location.href = cooperopcontextpath + rtn.redirect_url;
            } else {
                $.message("验证失败，请使用账号登陆！", function () {
                    //切换至密码登录
                    $(".login-wrap .panel").removeClass("display-hidden");
                    $(".login-wrap .finger-div").removeClass("display-show");
                    $(".login-wrap .wei-div").removeClass("display-show");

                    $(".btn-finger").removeClass("display-hidden");
                    $(".btn-account").removeClass("display-show");
                    $(".btn-wechat").removeClass("display-hidden");


                    $(".btn-account").addClass("display-hidden");
                    $(".btn-wechat").addClass("display-show");
                    $(".btn-finger").addClass("display-show");

                    $(".login-wrap .panel").addClass("display-show");
                    $(".finger-div").addClass("display-hidden");
                    $(".wei-div").addClass("display-hidden");
                });
            }
        }
    });
}

function cancelfinger() {
    $.cancelfinger();
}