var $cimc = $cimc || {};
(function(cimc, window) {
	if (cimc.inited) {
		return;
	}
	// do not delete
	cimc.config = ws_config;
	cimc.CLIENT_TYPES = {
		WEBBROWSER : "Browser",
		MOBILE : "Phone",
		PAD : "Pad",
		TV : "TV",
	};
	cimc.LOG_LEVEL = {
		DEBUG: 4,
		LOG: 3,
		INFO: 2,
		WARN: 1,
		ERROR: 0
	};
	cimc.params = {
		url : undefined,
		no_ui: false,
		debug : cimc.LOG_LEVEL.LOG,
		client_type : cimc.CLIENT_TYPES.WEBBROWSER,
		connect_times : 3
	};
	cimc.send = function(userid, message, callback, beforesend) {
		$chohows.send({
			data : {
				userid : userid,
				action : "message",
				message : message
			},
			beforesend : beforesend,
			callback : callback
		});
	}
	cimc.jsloaderror = function(message) {
		if (cimc.params["error"]) {
			cimc.params.error(message);
		} else if (window.console) {
			console.error("ChohoIM | " + $chohocommon.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS") + " | Error | " + message);
		}
	};
	cimc.init = function(params) {
		loadRES([ cimc.config.http_url + "/themes/classic3.0/im/jquery.js",
				cimc.config.http_url + "/themes/classic3.0/im/md5.js",
				cimc.config.http_url + "/themes/classic3.0/im/common.js" ], function() {
			cimc.params = chohojQuery.extend(true, cimc.params, params);
			login(function(rtn) {
				cimc.config.token = rtn;
				cimc.params.url = cimc.config.ws_url + "?"
						+ cimc.config.app_key + "=im&"
						+ cimc.config.token_key + "=" + cimc.config.token;
				
				var __res = [];
				__res = [  //cimc.config.http_url + "/themes/classic3.0/script/ctrtc.min-2.5.7.js",
							cimc.config.http_url + "/themes/classic3.0/im/websocket.js",
							cimc.config.http_url + "/themes/classic3.0/im/instantmessager.js" ];
				
				
				loadRES(__res,
						function() {
							$chohoim.init(cimc.params);
						}, function(jsurl, evt) {
							cimc.jsloaderror("load choho im js sdk [" + jsurl
									+ "] failed.");
						});
			});
		}, function(jsurl, evt) {
			cimc.jsloaderror("load choho im js sdk [" + jsurl + "] failed.");
		});
	};
	var login = function(callback) {
		chohojQuery
				.ajax({
					"async" : false,
					"dataType" : "text",
					url : cimc.config.http_url + "/cimcjs/login",
					data : {
						uid : cimc.params.userid,
						ct : cimc.params.client_type
					},
					success : function(rtn) {
						callback(rtn);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						var message = $.trim(XMLHttpRequest.responseText)
								|| textStatus;
						if (!message) {
							if (XMLHttpRequest.status != 200) {
								message = "访问服务端异常，HTTP错误代码:"
										+ XMLHttpRequest.status;
							} else {
								message = "访问服务端异常，错误原因:" + errorThrown.message;
							}
						} else if (message == "timeout")
							message = "连接服务器超时。";
						else if (message == "error") {
							message = "服务端处理异常。";
							return;
						} else if (message == "notmodified")
							message = "服务端未更新。";
						else if (message == "parsererror")
							message = "解析服务端返回信息异常。";
						throw (message);
					}
				});
	};

	var loadRES = function(js, success, error) {
		var jsreg = new RegExp(".js$");
		var cssreg = new RegExp(".css$");
		var initjs = {};
		for ( var i in js) {
			initjs[js[i]] = {
				ready : false,
				src : js[i]
			}
		}

		var js_ready = function() {
			var f = true;
			for ( var o in initjs) {
				if (!initjs[o].ready) {
					f = false;
					break;
				}
			}
			if (f) {
				success();
			}
		};

		var oHead = document.getElementsByTagName('HEAD').item(0);
		for ( var o in initjs) {
			if (jsreg.test(initjs[o].src)) {
				var ls = oHead.getElementsByTagName('script');
				for (var i = 0; i < ls.length; i ++) {
					if (ls[i].src == initjs[o].src) {
						initjs[o].ready = true;
						break;
					}
				}
			} else if (cssreg.test(initjs[o].src)) {
				var cs = oHead.getElementsByTagName('link');
				for (var i = 0; i < cs.length; i ++) {
					if (cs[i].href == initjs[o].src) {
						initjs[o].ready = true;
						break;
					}
				}
			}
			if (initjs[o].ready) break;
			
			var oScript = null;
			if (jsreg.test(initjs[o].src)) {
				oScript = document.createElement("script");
				oScript.type = "text/javascript";
				oScript.src = initjs[o].src;
			} else if (cssreg.test(initjs[o].src)) {
				oScript = document.createElement("link");
				oScript.rel = "stylesheet";
				oScript.type = "text/css";
				oScript.href = initjs[o].src;
			}
			oScript.jsurl = initjs[o].src;
			oScript.onload = oScript.onreadystatechange = function() {
				if (!this.readyState // 这是FF的判断语句，因为ff下没有readyState这人值，IE的readyState肯定有值
						|| this.readyState == 'loaded'
						|| this.readyState == 'complete') { // 这是IE的判断语句
					initjs[this.jsurl].ready = true;
					js_ready();
				}
			};
			oScript.onerror = function(evt) {
				error(this.src, evt);
			}
			oHead.appendChild(oScript);
		}
		var loaded = true;
		for ( var o in initjs) {
			if (!initjs[o].ready) {
				loaded = false;
			}
		}
		if (loaded) {
			js_ready();
		}
	};
})($cimc, window);