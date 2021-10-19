(function(window) {
	var crrtc = {};
	crrtc.conns = undefined; // 所有正在连接的通讯{"u_123455": conn}
	crrtc.init = function(params) {
		var params = $.extend(true, {
			token : undefined,
			debug : true,
			defaultResolution : "default", // default/qvga/vga/hd/cif
			ready : function(e) {

			},
			error : function(errormsg) {

			},
			callin : function(conn) {

			},
			callout : function(conn) {

			},
			ring : function(conn) {

			},
			start : function(conn) {

			},
			end : function(conn) {

			},
			callerror : function(conn) {

			},
			status : function(conn, stats) {

			},
			messagein: function() {
				
			},
			record: function(conn, type) {
				return false;
			},
			recordurl: function(conn, type) {
				return false;
			},
			recordback: function(conn, type, rtn) {
				
			},
			recorderror: function(conn, type, error) {
				
			}
		}, params);

		// 初始化Ctrtc.Device这个全局单例
		// 参数1为必选为平台SDK获得的令牌
		// 参数2为可选，dbg:true会在浏览器的console台中打印用于调试的消息，在生产环境建议关闭
		// 参数3为可选，defaultResolution指定默认视频分辨率（主叫和被叫时均有效）(不指定则使用摄像头设备的默认分辨率)，
		// 参数3在被设置后仍然可以在通话前改变分辨率，参数3取值范围有qvga/vga/hd/cif四种
		Ctrtc.Device.init(params.token, {
			dbg : params.debug,
			defaultResolution : params.defaultResolution
		});

		// Device初始化成功后会回调该函数
		Ctrtc.Device.onReady(params.ready);

		// 注册当有SDK本身错误发生时JSSDK会回调的处理函数，如令牌无效、网络断开等事件
		Ctrtc.Device.onDevErr(function(e) {
			params.error(e.info);
		});

		// 注册当有新呼入或者新呼出事件时JSSDK会回调的处理函数（必选）
		// SDK会为每个收到的呼叫请求创建代表该请求的connection对象，应用通过该对象提供的accept/terminate方法可以控制接听和拒绝业务；
		// SDK也会为每个呼出请求创建一个代表该请求的connection对象，应用通过对象提供的cancel方法可以取消一个呼出的请求
		Ctrtc.Device.onConnNew(function(conn) {
			// 一般来说应用可以为每个connection创建一个独立的包含音视频播放和呼叫过程控制按钮的通话面板
			// connection.extraParas; //对应extraInfo
			// SDK本身支持在一个页面中同时与多个用户进行通话，所以可能会同时存在多个通话面板
			crrtc.conns = conn;
			if (conn.direction == 'incoming') {// 呼入请求
				params.callin(conn);
			} else {// 呼出请求
				params.callout(conn);
			}
		});
		// 注册当被叫振铃时JSSDK会回调的处理函数
		Ctrtc.Device.onRinging(function(conn) {
			params.ring(conn);
			// $("#" + conn.remoteAccount.username + " .log").text(
			// "对方" + conn.remoteAccount.username + "振铃中");
		});

		// 注册当远端媒体流已经到达本地时JSSDK会回调的处理函数（必选，否则媒体流无法播放）
		// 无论是作为主叫还是被叫，当两端媒体流成功建立起来时该函数都会被JSSDK回调
		// 开发者在本函数中可自主指定渲染媒体流的video元素
		Ctrtc.Device.onStarted(function(conn) {
			try {
				params.start(conn);
			} catch (e) {
				console.error(e);
			}
			record(conn, params);
			// 使html5 video元素播放远端媒体流
			// $("#remoteVideo").attr('src',
			// window.URL.createObjectURL(conn.remoteStream));
			// 播放本地媒体流
			// $("#localVideo").attr('src',
			// window.URL.createObjectURL(conn.localStream));
		});

		// 注册当正常通话结束时JSSDK会回调的处理函数
		Ctrtc.Device.onEnded(function(conn) {
			stopRecord(conn, params);
			try {
				params.end(conn);
			} catch (e) {
				console.error(e);
			}
			crrtc.conns = undefined;
		});

		// 注册当呼叫失败时JSSDK会回调的处理函数
		Ctrtc.Device.onConnFailed(function(conn) {
			stopRecord(conn, params);
			try {
				params.callerror(conn.info);
			} catch (e) {
				console.error(e);
			}
			crrtc.conns = undefined;
		});

		// 注册视频媒体流统计结果处理函数，回调时stats对象中包含rtt(往返总时延)、媒体路径、丢包数量等，该接口在一次通话中会周期回调
		Ctrtc.Device.onStats(function(conn, stats) {
			params.status(conn, stats);
			// var panelID = conn.remoteAccount.username;
			// $("#" + panelID + " .stats").text(
			// "媒体路径 本地:" + stats.connectionType.local.candidateType + " "
			// + stats.connectionType.local.ipAddress + " 对端:"
			// + stats.connectionType.remote.candidateType + " "
			// + stats.connectionType.remote.ipAddress + "视频统计:rtt"
			// + stats.video.rtt + "丢包" + stats.video.packetsLost + "发送"
			// + stats.video.packetsSent + "nack"
			// + stats.video.googNacksReceived);
		});
		
		//收到消息的回调
		Ctrtc.Device.onReceiveMessage(function(data){
			var rtn = undefined;
			try {
				rtn = $.parseJSON(decodeURIComponent(data.info)).rtcmsg;
			} catch (e) {
				rtn = data.info;
			}
			params.messagein(rtn);
		});

		// 只有当调用Device.run函数，JSSDK才会向能力平台建立网络连接注册本客户端并进入事件循环
		// 需要保证run函数的执行顺序一定在上面注册业务回调处理函数完成后才执行
		Ctrtc.Device.run();
		
	}

	crrtc.call = function(username, extraInfo, mediaType, resolution) {
		// Device.connect函数用于发出呼叫
		// 执行本函数后，onConnNew函数会立刻被JSSDK回调，SDK把为这个呼叫新创建的connnection对象返回给应用，应用通过该对象能控制呼叫后续过程
		// connect函数参数说明：
		// 第一参数（必填）指定被叫用户名;
		// 第二参数（可选）
		// mediaType 指定媒体类型：video(只视频)/audio(只音频)/both(音视频),不填写则SDK默认为both;
		// accType指定帐号体系：inner(开发者内部帐号)/esurf（天翼帐号）/weibo(新浪微博)/qq(QQ帐号),不填写则与主叫的帐号体系相同
		// videoResolution指定分辨率：有qvga/vga/hd/cif，不填写时如果在device初始化时指定了默认分辨率则使用这个分辨率，否则SDK选择摄像头支持的默认分辨率
		// setEC 是否使用回声消除 1：回声消除  0：不回声消除
		// setAGC 是否取消自动增益控制 0：自动增益控制 1：取消自动增益控制
		// audioMode 是否使用语音激励 0 : 不采用语音激励  1 : 采用语音激励

		Ctrtc.Device.connect(username, {
			mediaType : mediaType || "both",
			extraInfo : extraInfo,
			videoResolution : resolution || "default",
			setEC: 1,
			setAGC: 1,
			audioMode: 1
		});
	}

	crrtc.hangup = function(username) {
		Ctrtc.Device.disconnectAll();
	};

	crrtc.accept = function(username, mediaType, resolution) {
		if (crrtc.conns) {
			crrtc.conns.accept({
				mediaType : mediaType,
				videoResolution : resolution,
				setEC: 1,
				setAGC: 1,
				audioMode: 1
			});
		}
	}

	crrtc.reject = function(username, mediaType) {
		Ctrtc.Device.disconnectAll();
	}
	
	crrtc.message = function(username, message) {
		try {
			Ctrtc.Device.sendUserMessage(username, encodeURIComponent($.toJSON({rtcmsg: message})));
		} catch (e) {
		}finally {
		}
	}

	// 录像
	function record(conn, params) {
		if (params.record(conn, "local")) {
			try {
				Ctrtc.Device.recordLocal("av");
			} catch (e) {
				console.error(e);
			}
		}
		if (params.record(conn, "remote")) {
			try {
				Ctrtc.Device.recordRemote("av");
			} catch (e) {
				console.error(e);
			}
		}
	}
	;

	// 停止录像
	function stopRecord(conn, params) {
		if (params.record(conn, "local")) {
			try {
				Ctrtc.Device.stopRecordLocal(function(url) {
					console.log("local video: " + url);
					var rmurl = params.recordurl(conn, "local");
					if (rmurl) {
						uploadRecord(conn, params, "local", rmurl, url);
					}
					//saveRecord(conn.cfg_account.appacc, url);
				});
			} catch (e) {
				console.error(e);
			}
		}
		if (params.record(conn, "remote")) {
			try {
				Ctrtc.Device.stopRecordRemote(function(url) {
					console.log("remote video: " + url);
					var rmurl = params.recordurl(conn, "remote");
					if (rmurl) {
						uploadRecord(conn, params, "remote", rmurl, url);
					}
				});
			} catch (e) {
				console.error(e);
			}
		}
	}
	;

	function dataURLtoBlob(dataurl) {
		var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1], bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(
				n);
		while (n--) {
			u8arr[n] = bstr.charCodeAt(n);
		}
		return new Blob([ u8arr ], {
			type : mime
		});
	}

	function convertFileToDataURLviaFileReader(url, callback) {
		var xhr = new XMLHttpRequest();
		xhr.responseType = 'blob';
		xhr.onload = function() {
			var reader = new FileReader();
			reader.onloadend = function() {
				callback(dataURLtoBlob(reader.result));
			}
			reader.readAsDataURL(xhr.response);
		};
		xhr.open('GET', url);
		xhr.send();
	}

	function uploadRecord(conn, params, type, rmurl, url) {
		var ws;
		ws = $ws({
			url: rmurl,
			ready: function() {
				convertFileToDataURLviaFileReader(url, function(blob){
					ws.sendBlob(null, blob);
					ws.close();
					if (params.recordback) {
						params.recordback(conn, type, url);
					}
				});
			},
			receiveText: function(conn, text) {		
				$.console().log("upload " + type + " video over: " + text);			
			},
			receiveJSON: function(conn, json_obj) {
				$.console().log("upload " + type + " video over: " + json_obj);
			},
			receiveBLOB: function(conn, blob) {
				$.console().log("upload " + type + " video over: " + blob);			
			},
			error: function(conn, code, message) {
				if (params.recorderror) {
					params.recorderror(conn, type, '[' + code + ']' + message);
				}else{
					$.error("upload " + type + " video error: " + '[' + code + ']' + message);
				}
			}
		});
		/*convertFileToDataURLviaFileReader(url, function(blob) {
			var xhr = new XMLHttpRequest();
			xhr.responseType = 'json';
			xhr.onreadystatechange = function() {
				if(xhr.readyState == xhr.DONE && xhr.status != 200) {
					var message = $.trim(XMLHttpRequest.responseText) || xhr.statusText;
					if(this.status == 401){
						message = "尚未登录";
					}
					if (!message) {
						if (XMLHttpRequest.status != 200) {
							message = "访问服务端异常，HTTP错误代码:" + XMLHttpRequest.status;
						}
					}
					else if (message == "timeout")
						message = "连接服务器超时。";
					else if (message == "error"){
						message = "服务端处理异常。";
						return;
					}else if (message == "notmodified")
						message = "服务端未更新。";
					else if (message == "parsererror")
						message = "解析服务端返回信息异常。";
					if (params.recorderror) {
						params.recorderror(conn, type, message);
					}else{
						$.error("upload " + type + " video error: " + message);
					}
				}
			}
			xhr.onload = function() {
				if (params.recordback) {
					params.recordback(conn, type, xhr.response);
				}else{
					$.console().log("upload " + type + " video over: " + $.trim(xhr.responseText));
				}
			};
			xhr.open('POST', cooperopcontextpath + '/rm/ub/' + module + '/' + encodeURIComponent(name),
					true);
			xhr.setRequestHeader('Content-Type', 'video/webm');
			xhr.send(blob);
		});*/
	}

	function saveRecord(name, url) {
		var aLink = document.createElement('a');
		var evt = document.createEvent("HTMLEvents");
		evt.initEvent("click", false, false);// initEvent 不加后两个参数在FF下会报错
		aLink.download = name + ".webm";
		aLink.href = url;
		aLink.dispatchEvent(evt);
	}

	window.$crrtc = crrtc;
})(window);

/**
 * Shine.Xia 
 * WebSocket encapsulation and inheritance v1.0
 */
;(function(window) {
	window.$ws = function(params) {
		var ws = {websocket: undefined, token : undefined};
		
		params = $.extend(true, {
			url: undefined,
			ready: function() {
				
			},
			receiveText: function(conn, text) {
				
			},
			receiveJSON: function(conn, json_obj) {
				
			},
			receiveBLOB: function(conn, json_obj) {
				
			},
			error: function(conn, code, error) {
				
			}
		}, params);
		
		ws.retry_max = 3 - 1;
		ws.retry = 0;
		ws.init = function() {
			if ('WebSocket' in window)  
	            ws.websocket = new WebSocket(params.url);  
	        else if ('MozWebSocket' in window)  
	            ws.websocket = new MozWebSocket(params.url);  
	        else  
	            throw new Error("The Browers not support WebSocket.");
			
			console.log("connect to: " + params.url);
			
			ws.websocket.onmessage = function(evt) {
				console.log("onmessage: " + evt);
				if (evt.type == "message") {
					var rtn = $.parseJSON(evt.data);
					if (rtn.rtn == "login") {
						ws.token = rtn.content.token;
						params.ready();
					} else if (rtn.rtn == "text") {
						console.log(rtn.content);
						params.receiveText(evt, rtn.content);
					} else if (rtn.rtn == "json") {
						console.log(rtn.content);
						params.receiveJSON(evt, rtn.content);
					} else if (rtn.rtn == "error") {
						console.log(rtn.content);
						params.error(evt, new Exception(rtn.content.message));
					}
				} else if (evt.type == "binary") {
					console.log(evt.data);
					params.receiveBLOB(evt, evt.data);
				}
			};

			ws.websocket.onclose = function(evt) {
				console.log("onclose: " + evt);
				var reason;
		        if (evt.code == 1000)
		            reason = "Normal closure, meaning that the purpose for which the connection was established has been fulfilled.";
		        else if(evt.code == 1001)
		            reason = "An endpoint is \"going away\", such as a server going down or a browser having navigated away from a page.";
		        else if(evt.code == 1002)
		            reason = "An endpoint is terminating the connection due to a protocol error";
		        else if(evt.code == 1003)
		            reason = "An endpoint is terminating the connection because it has received a type of data it cannot accept (e.g., an endpoint that understands only text data MAY send this if it receives a binary message).";
		        else if(evt.code == 1004)
		            reason = "Reserved. The specific meaning might be defined in the future.";
		        else if(evt.code == 1005)
		            reason = "No status code was actually present.";
		        else if(evt.code == 1006)
		           reason = "The connection was closed abnormally, e.g., without sending or receiving a Close control frame";
		        else if(evt.code == 1007)
		            reason = "An endpoint is terminating the connection because it has received data within a message that was not consistent with the type of the message (e.g., non-UTF-8 [http://tools.ietf.org/html/rfc3629] data within a text message).";
		        else if(evt.code == 1008)
		            reason = "An endpoint is terminating the connection because it has received a message that \"violates its policy\". This reason is given either if there is no other sutible reason, or if there is a need to hide specific details about the policy.";
		        else if(evt.code == 1009)
		           reason = "An endpoint is terminating the connection because it has received a message that is too big for it to process.";
		        else if(evt.code == 1010) // Note that this status code is not used by the server, because it can fail the WebSocket handshake instead.
		            reason = "An endpoint (client) is terminating the connection because it has expected the server to negotiate one or more extension, but the server didn't return them in the response message of the WebSocket handshake. <br /> Specifically, the extensions that are needed are: " + evt.reason;
		        else if(evt.code == 1011)
		            reason = "A server is terminating the connection because it encountered an unexpected condition that prevented it from fulfilling the request.";
		        else if(evt.code == 1015)
		            reason = "The connection was closed due to a failure to perform a TLS handshake (e.g., the server certificate can't be verified).";
		        else
		            reason = "Unknown reason";

				console.error("code: " + evt.code + ", reason: " + reason);
				console.error(evt);
				if (evt.code != 1000) {
					if (!ws.closing && ws.retry < ws.retry_max) {
						ws.retry ++;
						setTimeout(function(){
							ws.init(params);
						},1000);
					} else {
						params.error(evt.target, evt.code, reason);
					}
				}
			};
			
			ws.websocket.onerror = function(evt) {
				console.log("onerror: " + evt);
				console.error(evt);
			}

			ws.websocket.onopen = function(evt) {
				console.log("onopen: " + evt);
				if (ws.websocket.readyState == ws.websocket.OPEN) {
					ws.websocket.send($.toJSON({
						type: "login",
						appid: "yunclinic",
						userid: userinfo.id
					}));
					ws.retry = 0;
				}
			};
		}
		
		ws.closing = false;
		ws.close = function() {
			ws.closing = true;
			ws.websocket.close();
		}
		
		ws.sendText = function(userid, text) {
			console.log("send text to: " + params.url + " | " + text);
			ws.websocket.send($.toJSON({
				type: "text",
				token: ws.token,
				touser: userid,
				content: text
			}));
		};
		
		ws.sendObject = function(userid, jsonobj) {
			console.log("send object to: " + params.url + " | " + $.toJSON(jsonobj));
			ws.websocket.send($.toJSON({
				type: "json",
				token: ws.token,
				touser: userid,
				content: jsonobj
			}));
		};
		
		ws.sendBlob = function(userid, blob) {
			console.log("send blob to: " + params.url + " | " + blob);
			ws.websocket.send($.toJSON({
				type: "blob",
				token: ws.token,
				touser: userid,
				content: {
					size: blob.size, 
					state: "start", 
					md5: hex_md5(blob)
				}
			}));
			ws.websocket.send(blob);
			ws.websocket.send($.toJSON({
				type: "blob",
				token: ws.token,
				touser: userid,
				content: {
					size: blob.size, 
					state: "end", 
					md5: hex_md5(blob)
				}
			}));
		};
		
		ws.init();
		
		return ws;
	}
})(window);

/*
 * A JavaScript implementation of the RSA Data Security, Inc. MD5 Message
 * Digest Algorithm, as defined in RFC 1321.
 * Version 2.1 Copyright (C) Paul Johnston 1999 - 2002.
 * Other contributors: Greg Holt, Andrew Kepert, Ydnar, Lostinet
 * Distributed under the BSD License
 * See http://pajhome.org.uk/crypt/md5 for more info.
 */

/*
 * Configurable variables. You may need to tweak these to be compatible with
 * the server-side, but the defaults work in most cases.
 */
var hexcase = 0;  /* hex output format. 0 - lowercase; 1 - uppercase        */
var b64pad  = ""; /* base-64 pad character. "=" for strict RFC compliance   */
var chrsz   = 8;  /* bits per input character. 8 - ASCII; 16 - Unicode      */

/*
 * These are the functions you'll usually want to call
 * They take string arguments and return either hex or base-64 encoded strings
 */
function hex_md5(s){ return binl2hex(core_md5(str2binl(s), s.length * chrsz));}
function b64_md5(s){ return binl2b64(core_md5(str2binl(s), s.length * chrsz));}
function str_md5(s){ return binl2str(core_md5(str2binl(s), s.length * chrsz));}
function hex_hmac_md5(key, data) { return binl2hex(core_hmac_md5(key, data)); }
function b64_hmac_md5(key, data) { return binl2b64(core_hmac_md5(key, data)); }
function str_hmac_md5(key, data) { return binl2str(core_hmac_md5(key, data)); }

/*
 * Perform a simple self-test to see if the VM is working
 */
function md5_vm_test()
{
  return hex_md5("abc") == "900150983cd24fb0d6963f7d28e17f72";
}

/*
 * Calculate the MD5 of an array of little-endian words, and a bit length
 */
function core_md5(x, len)
{
  /* append padding */
  x[len >> 5] |= 0x80 << ((len) % 32);
  x[(((len + 64) >>> 9) << 4) + 14] = len;

  var a =  1732584193;
  var b = -271733879;
  var c = -1732584194;
  var d =  271733878;

  for(var i = 0; i < x.length; i += 16)
  {
    var olda = a;
    var oldb = b;
    var oldc = c;
    var oldd = d;

    a = md5_ff(a, b, c, d, x[i+ 0], 7 , -680876936);
    d = md5_ff(d, a, b, c, x[i+ 1], 12, -389564586);
    c = md5_ff(c, d, a, b, x[i+ 2], 17,  606105819);
    b = md5_ff(b, c, d, a, x[i+ 3], 22, -1044525330);
    a = md5_ff(a, b, c, d, x[i+ 4], 7 , -176418897);
    d = md5_ff(d, a, b, c, x[i+ 5], 12,  1200080426);
    c = md5_ff(c, d, a, b, x[i+ 6], 17, -1473231341);
    b = md5_ff(b, c, d, a, x[i+ 7], 22, -45705983);
    a = md5_ff(a, b, c, d, x[i+ 8], 7 ,  1770035416);
    d = md5_ff(d, a, b, c, x[i+ 9], 12, -1958414417);
    c = md5_ff(c, d, a, b, x[i+10], 17, -42063);
    b = md5_ff(b, c, d, a, x[i+11], 22, -1990404162);
    a = md5_ff(a, b, c, d, x[i+12], 7 ,  1804603682);
    d = md5_ff(d, a, b, c, x[i+13], 12, -40341101);
    c = md5_ff(c, d, a, b, x[i+14], 17, -1502002290);
    b = md5_ff(b, c, d, a, x[i+15], 22,  1236535329);

    a = md5_gg(a, b, c, d, x[i+ 1], 5 , -165796510);
    d = md5_gg(d, a, b, c, x[i+ 6], 9 , -1069501632);
    c = md5_gg(c, d, a, b, x[i+11], 14,  643717713);
    b = md5_gg(b, c, d, a, x[i+ 0], 20, -373897302);
    a = md5_gg(a, b, c, d, x[i+ 5], 5 , -701558691);
    d = md5_gg(d, a, b, c, x[i+10], 9 ,  38016083);
    c = md5_gg(c, d, a, b, x[i+15], 14, -660478335);
    b = md5_gg(b, c, d, a, x[i+ 4], 20, -405537848);
    a = md5_gg(a, b, c, d, x[i+ 9], 5 ,  568446438);
    d = md5_gg(d, a, b, c, x[i+14], 9 , -1019803690);
    c = md5_gg(c, d, a, b, x[i+ 3], 14, -187363961);
    b = md5_gg(b, c, d, a, x[i+ 8], 20,  1163531501);
    a = md5_gg(a, b, c, d, x[i+13], 5 , -1444681467);
    d = md5_gg(d, a, b, c, x[i+ 2], 9 , -51403784);
    c = md5_gg(c, d, a, b, x[i+ 7], 14,  1735328473);
    b = md5_gg(b, c, d, a, x[i+12], 20, -1926607734);

    a = md5_hh(a, b, c, d, x[i+ 5], 4 , -378558);
    d = md5_hh(d, a, b, c, x[i+ 8], 11, -2022574463);
    c = md5_hh(c, d, a, b, x[i+11], 16,  1839030562);
    b = md5_hh(b, c, d, a, x[i+14], 23, -35309556);
    a = md5_hh(a, b, c, d, x[i+ 1], 4 , -1530992060);
    d = md5_hh(d, a, b, c, x[i+ 4], 11,  1272893353);
    c = md5_hh(c, d, a, b, x[i+ 7], 16, -155497632);
    b = md5_hh(b, c, d, a, x[i+10], 23, -1094730640);
    a = md5_hh(a, b, c, d, x[i+13], 4 ,  681279174);
    d = md5_hh(d, a, b, c, x[i+ 0], 11, -358537222);
    c = md5_hh(c, d, a, b, x[i+ 3], 16, -722521979);
    b = md5_hh(b, c, d, a, x[i+ 6], 23,  76029189);
    a = md5_hh(a, b, c, d, x[i+ 9], 4 , -640364487);
    d = md5_hh(d, a, b, c, x[i+12], 11, -421815835);
    c = md5_hh(c, d, a, b, x[i+15], 16,  530742520);
    b = md5_hh(b, c, d, a, x[i+ 2], 23, -995338651);

    a = md5_ii(a, b, c, d, x[i+ 0], 6 , -198630844);
    d = md5_ii(d, a, b, c, x[i+ 7], 10,  1126891415);
    c = md5_ii(c, d, a, b, x[i+14], 15, -1416354905);
    b = md5_ii(b, c, d, a, x[i+ 5], 21, -57434055);
    a = md5_ii(a, b, c, d, x[i+12], 6 ,  1700485571);
    d = md5_ii(d, a, b, c, x[i+ 3], 10, -1894986606);
    c = md5_ii(c, d, a, b, x[i+10], 15, -1051523);
    b = md5_ii(b, c, d, a, x[i+ 1], 21, -2054922799);
    a = md5_ii(a, b, c, d, x[i+ 8], 6 ,  1873313359);
    d = md5_ii(d, a, b, c, x[i+15], 10, -30611744);
    c = md5_ii(c, d, a, b, x[i+ 6], 15, -1560198380);
    b = md5_ii(b, c, d, a, x[i+13], 21,  1309151649);
    a = md5_ii(a, b, c, d, x[i+ 4], 6 , -145523070);
    d = md5_ii(d, a, b, c, x[i+11], 10, -1120210379);
    c = md5_ii(c, d, a, b, x[i+ 2], 15,  718787259);
    b = md5_ii(b, c, d, a, x[i+ 9], 21, -343485551);

    a = safe_add(a, olda);
    b = safe_add(b, oldb);
    c = safe_add(c, oldc);
    d = safe_add(d, oldd);
  }
  return Array(a, b, c, d);

}

/*
 * These functions implement the four basic operations the algorithm uses.
 */
function md5_cmn(q, a, b, x, s, t)
{
  return safe_add(bit_rol(safe_add(safe_add(a, q), safe_add(x, t)), s),b);
}
function md5_ff(a, b, c, d, x, s, t)
{
  return md5_cmn((b & c) | ((~b) & d), a, b, x, s, t);
}
function md5_gg(a, b, c, d, x, s, t)
{
  return md5_cmn((b & d) | (c & (~d)), a, b, x, s, t);
}
function md5_hh(a, b, c, d, x, s, t)
{
  return md5_cmn(b ^ c ^ d, a, b, x, s, t);
}
function md5_ii(a, b, c, d, x, s, t)
{
  return md5_cmn(c ^ (b | (~d)), a, b, x, s, t);
}

/*
 * Calculate the HMAC-MD5, of a key and some data
 */
function core_hmac_md5(key, data)
{
  var bkey = str2binl(key);
  if(bkey.length > 16) bkey = core_md5(bkey, key.length * chrsz);

  var ipad = Array(16), opad = Array(16);
  for(var i = 0; i < 16; i++)
  {
    ipad[i] = bkey[i] ^ 0x36363636;
    opad[i] = bkey[i] ^ 0x5C5C5C5C;
  }

  var hash = core_md5(ipad.concat(str2binl(data)), 512 + data.length * chrsz);
  return core_md5(opad.concat(hash), 512 + 128);
}

/*
 * Add integers, wrapping at 2^32. This uses 16-bit operations internally
 * to work around bugs in some JS interpreters.
 */
function safe_add(x, y)
{
  var lsw = (x & 0xFFFF) + (y & 0xFFFF);
  var msw = (x >> 16) + (y >> 16) + (lsw >> 16);
  return (msw << 16) | (lsw & 0xFFFF);
}

/*
 * Bitwise rotate a 32-bit number to the left.
 */
function bit_rol(num, cnt)
{
  return (num << cnt) | (num >>> (32 - cnt));
}

/*
 * Convert a string to an array of little-endian words
 * If chrsz is ASCII, characters >255 have their hi-byte silently ignored.
 */
function str2binl(str)
{
  var bin = Array();
  var mask = (1 << chrsz) - 1;
  for(var i = 0; i < str.length * chrsz; i += chrsz)
    bin[i>>5] |= (str.charCodeAt(i / chrsz) & mask) << (i%32);
  return bin;
}

/*
 * Convert an array of little-endian words to a string
 */
function binl2str(bin)
{
  var str = "";
  var mask = (1 << chrsz) - 1;
  for(var i = 0; i < bin.length * 32; i += chrsz)
    str += String.fromCharCode((bin[i>>5] >>> (i % 32)) & mask);
  return str;
}

/*
 * Convert an array of little-endian words to a hex string.
 */
function binl2hex(binarray)
{
  var hex_tab = hexcase ? "0123456789ABCDEF" : "0123456789abcdef";
  var str = "";
  for(var i = 0; i < binarray.length * 4; i++)
  {
    str += hex_tab.charAt((binarray[i>>2] >> ((i%4)*8+4)) & 0xF) +
           hex_tab.charAt((binarray[i>>2] >> ((i%4)*8  )) & 0xF);
  }
  return str;
}

/*
 * Convert an array of little-endian words to a base-64 string
 */
function binl2b64(binarray)
{
  var tab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
  var str = "";
  for(var i = 0; i < binarray.length * 4; i += 3)
  {
    var triplet = (((binarray[i   >> 2] >> 8 * ( i   %4)) & 0xFF) << 16)
                | (((binarray[i+1 >> 2] >> 8 * ((i+1)%4)) & 0xFF) << 8 )
                |  ((binarray[i+2 >> 2] >> 8 * ((i+2)%4)) & 0xFF);
    for(var j = 0; j < 4; j++)
    {
      if(i * 8 + j * 6 > binarray.length * 32) str += b64pad;
      else str += tab.charAt((triplet >> 6*(3-j)) & 0x3F);
    }
  }
  return str;
}

