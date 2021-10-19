var $crrtc = $crrtc || {};
(function(crrtc, window) {
	var MEDIA_TYPE = {
		DEFAULT : "both",
		VIDEO_ONLY : "video",
		AUDIO_ONLY : "audio"
	}

	var RESOLUTION = {
		AUTO : "default",
		HIGH : "qvga",
		QUALITY : "vga",
		NORMAL : "hd",
		LOW : "cif"
	}

	var log = {
		DEBUG : 4,
		LOG : 3,
		INFO : 2,
		WARN : 1,
		ERROR : 0,
		debug : function(event, msg) {
			if (window['console'] && +crrtc.params.debug >= log.DEBUG) {
				console.debug("CRRTC" + " | " + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.sss") + "| Debug | " + event + " | " + ($.isPlainObject(msg) ? JSON.stringify(msg) : msg));
			}
		},
		log : function(event, msg) {
			if (window['console'] && +crrtc.params.debug >= log.LOG) {
				console.log("CRRTC" + " | " + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.sss") + "| Log | " + event + " | " + ($.isPlainObject(msg) ? JSON.stringify(msg) : msg));
			}
		},
		info : function(event, msg) {
			if (window['console'] && +crrtc.params.debug >= log.INFO) {
				console.info("CRRTC" + " | " + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.sss") + "| Info | " + event + " | " + ($.isPlainObject(msg) ? JSON.stringify(msg) : msg));
			}
		},
		warn : function(event, msg) {
			if (window['console'] && +crrtc.params.debug >= log.WARN) {
				console.warn("CRRTC" + " | " + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.sss") + "| Warning | " + event + " | " + ($.isPlainObject(msg) ? JSON.stringify(msg) : msg));
			}
		},
		error : function(event, msg, error) {
			if (window['console'] && +crrtc.params.debug >= log.ERROR) {
				console.error("CRRTC" + " | " + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.sss") + "| Error | " + event + " | " + ($.isPlainObject(msg) ? JSON.stringify(msg) : msg) + " | "
						+ JSON.stringify(error));
				if (error) {
					console.error(error);
				}
			}
		}
	};

	var DEFAULT_PARAMS = {
		token : undefined,
		debug : log.DEBUG,
		defaultResolution : RESOLUTION.AUTO,
		defaultCamera : undefined,
		videodiv: undefined,
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
		messagein : function() {

		},
		needrecord : function(conn, type) {
			return false;
		},
		record : function(blobs) {
			
		}
	};

	crrtc.params = undefined;
	crrtc.inited = false;
	crrtc.current_conn = undefined;
	crrtc.init = function(params, reinit) {
		if (!crrtc.inited || reinit) {
			crrtc.params = $.extend(true, DEFAULT_PARAMS, params);

			function initCrrtc() {
				// 初始化Ctrtc.Device这个全局单例
				// 参数1为必选为平台SDK获得的令牌
				// 参数2为可选，dbg:true会在浏览器的console台中打印用于调试的消息，在生产环境建议关闭
				// 参数3为可选，defaultResolution指定默认视频分辨率（主叫和被叫时均有效）(不指定则使用摄像头设备的默认分辨率)，
				// 参数3在被设置后仍然可以在通话前改变分辨率，参数3取值范围有qvga/vga/hd/cif四种
				Ctrtc.Device.init(params.token, {
					dbg : +crrtc.params.debug >= log.WARN,
					defaultResolution : crrtc.params.defaultResolution
				});

				// Device初始化成功后会回调该函数
				Ctrtc.Device.onReady(function(e) {
					if (crrtc.inited) return;
					crrtc.inited = true;
					if (crrtc.params.ready) {
						crrtc.params.ready(e);
					} else {
						log.info("Ctrtc Device onReady", e);
					}
				});

				// 注册当有SDK本身错误发生时JSSDK会回调的处理函数，如令牌无效、网络断开等事件
				Ctrtc.Device.onDevErr(function(e) {
					if (crrtc.params.error) {
						crrtc.params.error(e.info);
					} else {
						log.error("Ctrtc Device onDevErr", e.info);
					}
				});

				// 注册当有新呼入或者新呼出事件时JSSDK会回调的处理函数（必选）
				// SDK会为每个收到的呼叫请求创建代表该请求的connection对象，应用通过该对象提供的accept/terminate方法可以控制接听和拒绝业务；
				// SDK也会为每个呼出请求创建一个代表该请求的connection对象，应用通过对象提供的cancel方法可以取消一个呼出的请求
				Ctrtc.Device.onConnNew(function(conn) {
					// 一般来说应用可以为每个connection创建一个独立的包含音视频播放和呼叫过程控制按钮的通话面板
					// connection.extraParas; //对应extraInfo
					// SDK本身支持在一个页面中同时与多个用户进行通话，所以可能会同时存在多个通话面板
					crrtc.current_conn = conn;
					if (conn.direction == 'incoming') {// 呼入请求
						if (crrtc.params.callin) {
							crrtc.params.callin(conn);
						} else {
							log.info("Ctrtc Device onConnNew callin", conn);
						}
					} else {// 呼出请求
						if (crrtc.params.callout) {
							crrtc.params.callout(conn);
						} else {
							log.info("Ctrtc Device onConnNew callout", conn);
						}
					}
				});

				// 注册当被叫振铃时JSSDK会回调的处理函数
				Ctrtc.Device.onRinging(function(conn) {
					if (crrtc.params.ring) {
						crrtc.params.ring(conn);
					} else {
						log.info("Ctrtc Device onRinging", conn);
					}
				});

				// 注册当远端媒体流已经到达本地时JSSDK会回调的处理函数（必选，否则媒体流无法播放）
				// 无论是作为主叫还是被叫，当两端媒体流成功建立起来时该函数都会被JSSDK回调
				// 开发者在本函数中可自主指定渲染媒体流的video元素
				Ctrtc.Device.onStarted(function(conn) {
					if (crrtc.params.videodiv) {
						$(crrtc.params.videodiv).html("");
						$(crrtc.params.videodiv).css("position", "relative");
						$(crrtc.params.videodiv).css("background", "#000!important;");
						var html = [];
						html.push('<video class="crrtc_remote_video" src="' + window.URL.createObjectURL(conn.remoteStream) +  '" autoplay style="width: 100%;position:relative;pointer-events: none;cursor:default;z-index:98;"></video>');
						html.push('<video class="crrtc_local_video" src="' + window.URL.createObjectURL(conn.localStream) +  '" autoplay muted style="width: 25%;position:absolute;right:0px;top:0px;pointer-events: auto;cursor:pointer;z-index: 99;"></video>');
						var videoobjs = $(html.join(""));
						$(crrtc.params.videodiv).find(".crrtc_remote_video").data("is_max", true);
						$(crrtc.params.videodiv).append(videoobjs);
						videoobjs.click(function() {
							var $r = $(crrtc.params.videodiv).find(".crrtc_remote_video");
							var $l = $(crrtc.params.videodiv).find(".crrtc_local_video");
							if ($r.data("is_max")) {
								$r.removeData("is_max");
								$r.css({
									width: "25%",
									position: "absolute",
									right: "0px",
									top: "0px",
									pointerEvents: "auto",
									cursor: "pointer",
									zIndex: "99"
								});
								$l.css({
									width: "100%",
									position: "relative",
									pointerEvents: "none",
									cursor: "default",
									zIndex: "98"
								});
							} else {
								$r.data("is_max", true);
								$r.css({
									width: "100%",
									position: "relative",
									pointerEvents: "none",
									cursor: "default",
									zIndex: "98"
								});
								$l.css({
									width: "25%",
									position: "absolute",
									right: "0px",
									top: "0px",
									pointerEvents: "auto",
									cursor: "pointer",
									zIndex: "99"
								});
							}
						});
					}
					crrtc.params.calling = true;
					if (crrtc.params.start) {
						crrtc.params.start(conn);
					} else {
						log.info("Ctrtc Device onStarted", conn);
					}
					record(conn);
				});

				// 注册当正常通话结束时JSSDK会回调的处理函数
				Ctrtc.Device.onEnded(function(conn) {
					if (crrtc.params.calling) {
						stopRecord(conn);
						if (crrtc.params.end) {
							try {
								crrtc.params.end(conn);
							} catch (e) {
								log.error("Ctrtc Device onEnded call end function error", e);
							}
						} else {
							log.info("Ctrtc Device onEnded", conn);
						}
					} else {
						if (crrtc.params.callerror) {
							try {
								crrtc.params.callerror(conn.info);
							} catch (e) {
								log.error("Ctrtc Device onConnFailed call callerror function error", e);
							}
						} else {
							log.info("Ctrtc Device onConnFailed", conn.info);
						}
					}
					
					crrtc.current_conn = undefined;
				});

				// 注册当呼叫失败时JSSDK会回调的处理函数
				Ctrtc.Device.onConnFailed(function(conn) {
					if (crrtc.params.calling) {
						stopRecord(conn);
					}
					if (crrtc.params.callerror) {
						try {
							crrtc.params.callerror(conn.info);
						} catch (e) {
							log.error("Ctrtc Device onConnFailed call callerror function error", e);
						}
					} else {
						log.info("Ctrtc Device onConnFailed", conn.info);
					}
					crrtc.current_conn = undefined;
				});

				// 注册视频媒体流统计结果处理函数，回调时stats对象中包含rtt(往返总时延)、媒体路径、丢包数量等，该接口在一次通话中会周期回调
				Ctrtc.Device.onStats(function(conn, stats) {
					if (crrtc.params.status) {
						crrtc.params.status(conn, stats);
					} else {
						log.log("Ctrtc Device onStats", stats);
					}
				});

				// 收到消息的回调
				Ctrtc.Device.onReceiveMessage(function(data) {
					log.log("Ctrtc Device onReceiveMessage", data);
				});

				// 只有当调用Device.run函数，JSSDK才会向能力平台建立网络连接注册本客户端并进入事件循环
				// 需要保证run函数的执行顺序一定在上面注册业务回调处理函数完成后才执行
				Ctrtc.Device.run();
			}

			if (crrtc.params.defaultCamera) {
				var mediaConfig = {
					video : {
						deviceId : crrtc.params.defaultCamera
					}
				};
				var errBack = function(e) {
					console.log('An error has occurred!', e)
				};

				// Put video listeners into place
				if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
					navigator.mediaDevices.getUserMedia(mediaConfig).then(function(stream) {
						window.stream = stream;
						if (window.stream) {
							window.stream.getTracks().forEach(function(track) {
								track.stop();
							});
						}
						initCrrtc();
					});
				}
			} else {
				initCrrtc();
			}

		} else {
			Ctrtc.Device.disconnectAll();
			if (crrtc.params.ready) {
				crrtc.params.ready();
			} else {
				log.info("Ctrtc Device onReady");
			}
		}
	}

	crrtc.call = function(system_user_id, bid, mediaType, resolution) {
		if (crrtc.inited && !crrtc.current_conn) {
			// Device.connect函数用于发出呼叫
			// 执行本函数后，onConnNew函数会立刻被JSSDK回调，SDK把为这个呼叫新创建的connnection对象返回给应用，应用通过该对象能控制呼叫后续过程
			// connect函数参数说明：
			// 第一参数（必填）指定被叫用户名;
			// 第二参数（可选）
			// mediaType 指定媒体类型：video(只视频)/audio(只音频)/both(音视频),不填写则SDK默认为both;
			// accType指定帐号体系：inner(开发者内部帐号)/esurf（天翼帐号）/weibo(新浪微博)/qq(QQ帐号),不填写则与主叫的帐号体系相同
			// videoResolution指定分辨率：有qvga/vga/hd/cif，不填写时如果在device初始化时指定了默认分辨率则使用这个分辨率，否则SDK选择摄像头支持的默认分辨率
			// setEC 是否使用回声消除 1：回声消除 0：不回声消除
			// setAGC 是否取消自动增益控制 0：自动增益控制 1：取消自动增益控制
			// audioMode 是否使用语音激励 0 : 不采用语音激励 1 : 采用语音激励
			Ctrtc.Device.connect(system_user_id, {
				mediaType : mediaType || MEDIA_TYPE.DEFAULT,
				extraInfo : bid,
				videoResolution : resolution || RESOLUTION.AUTO,
				setEC : 1,
				setAGC : 1,
				audioMode : 1
			});
		} else {
			log.error("Ctrtc Device connect", "Crrtc not inited or There is alive connect!");
			throw "Crrtc not inited or There is alive connect!";
		}
	}

	crrtc.hangup = function() {
		if (crrtc.inited) {
			Ctrtc.Device.disconnectAll();
		}
	};

	crrtc.accept = function(conn) {
		if (crrtc.inited && (crrtc.current_conn == conn)) {
			crrtc.current_conn.accept({
				mediaType : crrtc.current_conn.remoteMedia,
				videoResolution : crrtc.current_conn.resolution,
				setEC : 1,
				setAGC : 1,
				audioMode : 1
			});
		} else {
			log.error("Ctrtc Device accept", "Crrtc not inited or There is not alive connect!");
			throw "Crrtc not inited or There is not alive connect!";
		}
	};

	crrtc.reject = function() {
		if (crrtc.inited) {
			Ctrtc.Device.disconnectAll();
		} else {
			log.error("Ctrtc Device reject", "Crrtc not inited!");
			throw "Crrtc not inited!";
		}
	};
	
	crrtc.uploadRecord = function(r) {
		var yxk_params = [];
		yxk_params.push(userinfo.attendantMap.yxkclient_app_key + "=" + userinfo.attendantMap.yxkclient_app);
		yxk_params.push(userinfo.attendantMap.yxkclient_token_key + "=" + userinfo.attendantMap.yxkclient_token);
		yxk_params.push(userinfo.attendantMap.yxkclient_user_key + "=" + userinfo.attendantMap.yxkclient_user);
		yxk_params.push(userinfo.attendantMap.yxkclient_pwd_key + "=" + userinfo.attendantMap.yxkclient_pwd);
		var yxk_url = userinfo.attendantMap.yxkclient_url + "?" + yxk_params.join("&");
		$chohows.init({
			url : userinfo.attendantMap.yxkclient_url + "?" + yxk_params.join("&"),
			debug : 4,
			connect_times : 1,
			ready : function(connprop) {
				$chohows.send(r);
			},
			closed : function(msg, code) {
				console.debug("closed", msg);
				if (code == 1006) {
					$.warning(['请确认本地资源存储服务已安装并已经启动。',
					           '安装包: <a href="javascript:void(0);" download="本地资源存储服务.msi" style="font-size:inherit;">点击下载</a>'].join("<br/>"), function() {
						crrtc.uploadRecord(r);
					});
				}
			},
			receive : function(msg, from) {
				console.debug("receive", msg);
			},
			error : function(event) {
				$.error("upload video[" + JSON.stringify(r) + "] error: " + event);
			}
		});
	}
	
	// 录像
	var record = function(conn) {
		if (crrtc.params.needrecord) {
			if (crrtc.params.needrecord(conn, "local")) {
				try {
					Ctrtc.Device.recordLocal("av");
				} catch (e) {
					console.error(e);
				}
			}
			if (crrtc.params.needrecord(conn, "remote")) {
				try {
					Ctrtc.Device.recordRemote("av");
				} catch (e) {
					console.error(e);
				}
			}
		}
	};

	// 停止录像
	var stopRecord = function(conn) {
		if (crrtc.params.needrecord) {
			var res = {};
			function rcu() {
				for (var k in res) {
					if (!res[k].url) {
						return;
					}
				}
				function rb(r, k) {
					u2b(r.url, function(rtn) {
						res[k] = {
								type: 'blob',
								blob: rtn.blob
							};
						rcb();
					});
				}
				for (var k in res) {
					rb(res[k], k);
				}
			}
			
			function rcb() {
				for (var k in res) {
					if (!res[k].blob) {
						return;
					}
				}
				if (crrtc.params.videodiv) {
					$(crrtc.params.videodiv).find(".crrtc_remote_video").attr('src', "");
					$(crrtc.params.videodiv).find(".crrtc_local_video").attr('src', "");
					$(crrtc.params.videodiv).html("");
				}
				crrtc.params.record(res);
			}
			
			if (crrtc.params.needrecord(conn, "local")) {
				res.local = {type: 'blob'}
				Ctrtc.Device.stopRecordLocal(function(url) {
					console.log("local video: " + url);
					res.local.url = url;
					rcu();
				});
			}
			if (crrtc.params.needrecord(conn, "remote")) {
				res.remote = {type: 'blob'}
				Ctrtc.Device.stopRecordRemote(function(url) {
					console.log("remote video: " + url);
					res.remote.url = url;
					rcu();
				});
			}
		} else {
			if (crrtc.params.videodiv) {
				$(crrtc.params.videodiv).find(".crrtc_remote_video").attr('src', "");
				$(crrtc.params.videodiv).find(".crrtc_local_video").attr('src', "");
				$(crrtc.params.videodiv).html("");
			}
		}
	};
	
	var b2b = function(blob, filename, callback) {
		var reader = new FileReader();
		reader.onloadend = function() {
			callback({
				filename : filename,
				blob : blob
			});
		}
		reader.readAsBinaryString(blob);
	}

	var d2b = function(dataurl, filename, callback) {
		var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1], bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(
				n);
		while (n--) {
			u8arr[n] = bstr.charCodeAt(n);
		}
		b2b(new Blob([ u8arr ], {
			type : mime
		}), filename, callback);
	}

	function createXHR() {
		return window.ActiveXObject !== undefined ?
				createActiveXHR() :
		createStandardXHR();
	}
	function createStandardXHR() {
		try {
			return new window.XMLHttpRequest();
		} catch (e) {
		}
	}
	function createActiveXHR() {
		var xmlhttp_arr = [ "MSXML2.XMLHTTP", "Microsoft.XMLHTTP" ];
		var xmlhttp;
		for (i = 0; i < xmlhttp_arr.length; i++) {
			if (xmlhttp = new ActiveXObject(xmlhttp_arr[i]))
				break;
		}
		return xmlhttp;
	}

	var u2b = function(url, callback) {
		var xhr = createXHR();
		xhr.responseType = 'blob';
		xhr.onload = function() {
			var cd = xhr.getResponseHeader("Content-Disposition");
			var t = url.split("/");
			var filename = t[t.length - 1].split("?")[0];
			if (cd) {
				t = cd.split(";");
				for ( var i in t) {
					if (t[i].toLowerCase().indexOf("filename") > -1) {
						filename = t[i].split("=")[1];
						break;
					}
				}
			}
			var reader = new FileReader();
			reader.onloadend = function() {
				d2b(reader.result, filename, callback)
			}
			reader.readAsDataURL(xhr.response);
		};
		xhr.open('GET', url, true);
		xhr.send();
	}
})($crrtc, window);