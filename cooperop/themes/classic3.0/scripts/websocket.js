/**
 * Shine.Xia WebSocket encapsulation and inheritance v1.0
 */
var $chohows = $chohows || {};
(function(chohows, window) {
	var TYPE_CONNECTBACK = "connprop";
	var TYPE_HEARTBEAT = "heartbeat";
	var TYPE_TEXT = "message";
	var TYPE_BLOB = "blob";
	var TYPE_ERROR = "error";

	var STATE_BEGIN = "begin";
	var STATE_END = "end";
	
	var BLOB_TYPE = {
		type: TYPE_BLOB,
		blob: undefined,
		data: undefined,
		file: undefined,
		url: undefined
	}

	var HEART_BEAT_TEMPLATE = {
		id : undefined, // '_msg_' + generateId()
		type : TYPE_HEARTBEAT,
		state : STATE_END,
		multi : false
	};

	var TEXT_MESSAGE_TEMPLATE = {
		id : undefined, // '_msg_' + generateId()
		type : TYPE_TEXT,
		state : STATE_BEGIN,
		multi : false,
		from : {
			token : undefined,
			appid : undefined,
			request : {},
			userinfo : {}
		},
		to : {
			token : undefined,
			appid : undefined,
			request : {},
			userinfo : {}
		},
		params : {}
	};

	var BLOB_MESSAGE_TEMPLATE = {
		id : undefined, // '_msg_' + generateId()
		msgid : undefined,
		path : [],
		type : TYPE_BLOB,
		state : STATE_BEGIN,
		blob : {
			fileName : undefined,
			contentType : undefined,
			size : 0,
			md5 : undefined
		}
	};
	
	var ERROR_MESSAGE_TEMPLATE = {
		type : TYPE_ERROR,
		id : undefined,
		msgid : undefined,
		state: undefined //error message
	};

	var ERROR_POOL = {
		"10000" : "The Browers not support WebSocket.",
	}

	chohows.websocket = undefined;
	chohows.retry_times = 0;
	chohows.ready = function(connprop) {
		if (chohows.params["ready"]) {
			chohows.params.ready(connprop);
		} else {
			log.info("ready", "receive connect properties: " + JSON.stringify(connprop));
		}
	};
	chohows.closed = function(msg, code) {
		if (chohows.params["closed"]) {
			chohows.params.closed(msg, code);
		} else {
			log.info("closed", "disconnect [" + chohows.params.url + "] case by " + JSON.stringify(msg));
		}
	};
	chohows.receive = function(msg) {
		if (!chohows.callback(msg)) {
			if (chohows.params["receive"]) {
				chohows.params.receive(msg.params, msg.from);
			} else {
				log.info("receive", JSON.stringify(msg));
			}
		}
	};
	chohows.error = function(code, error) {
		if (chohows.params["error"]) {
			chohows.params.error(code, ERROR_POOL[code] || error);
		} else {
			log.error("error", "code = " + code + ", reason = "
					+ ERROR_POOL[code] || error || code);
		}
	};
	var log = {
		DEBUG : 4,
		LOG : 3,
		INFO : 2,
		WARN : 1,
		ERROR : 0,
		debug : function(event, msg) {
			if (window['console'] && +chohows.params.debug >= log.DEBUG) {
				console.debug("ChohoWS" + " | " + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.sss") + "| Debug | " + event + " | "
						+ ($.isPlainObject(msg) ? JSON.stringify(msg) : msg));
			}
		},
		log : function(event, msg) {
			if (window['console'] && +chohows.params.debug >= log.LOG) {
				console.log("ChohoWS" + " | " + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.sss") + "| Log | " + event + " | "
						+ ($.isPlainObject(msg) ? JSON.stringify(msg) : msg));
			}
		},
		info : function(event, msg) {
			if (window['console'] && +chohows.params.debug >= log.INFO) {
				console.info("ChohoWS" + " | " + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.sss") + "| Info | " + event + " | "
						+ ($.isPlainObject(msg) ? JSON.stringify(msg) : msg));
			}
		},
		warn : function(event, msg) {
			if (window['console'] && +chohows.params.debug >= log.WARN) {
				console.warn("ChohoWS" + " | " + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.sss") + "| Warning | " + event + " | "
						+ ($.isPlainObject(msg) ? JSON.stringify(msg) : msg));
			}
		},
		error : function(event, msg, error) {
			if (window['console'] && +chohows.params.debug >= log.ERROR) {
				console.error("ChohoWS" + " | " + formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.sss") + "| Error | " + event + " | "
						+ ($.isPlainObject(msg) ? JSON.stringify(msg) : msg) + " | "
						+ JSON.stringify(error));
				if (error) {
					console.error(error);
				}
			}
		}
	};
	chohows.params = {
		url: undefined,
		debug : log.INFO,
		connect_times : 0,
		heart_time: 60000,
		ready: function(connprop) {
			
		},
		closed: function(msg) {
			
		},
		receive: function(msg) {
			
		},
		error: function(code, error) {
			
		}
	};
	chohows.params[TYPE_CONNECTBACK] = {};
	chohows.init = function(params) {
		chohows.params = $.extend(true, chohows.params, params);
		if ('WebSocket' in window)
			chohows.websocket = new WebSocket(chohows.params.url);
		else if ('MozWebSocket' in window)
			chohows.websocket = new MozWebSocket(chohows.params.url);
		else {
			chohows.error("10000");
			return;
		}

		chohows.websocket.onmessage = function(evt) {
			chohows.onmessage(evt);
		};

		chohows.websocket.onclose = function(evt) {
			chohows.onclose(evt);
		};

		chohows.websocket.onerror = function(evt) {
			chohows.onerror(evt);
		};

		chohows.websocket.onopen = function(evt) {
			chohows.onopen(evt);
		};
	};
	chohows.onclose = function(evt) {
		var reason = evt.reason;
		if (!reason) {
			if (evt.code == 1000)
				reason = "Normal closure, meaning that the purpose for which the connection was established has been fulfilled.";
			else if (evt.code == 1001)
				reason = "An endpoint is \"going away\", such as a server going down or a browser having navigated away from a page.";
			else if (evt.code == 1002)
				reason = "An endpoint is terminating the connection due to a protocol error";
			else if (evt.code == 1003)
				reason = "An endpoint is terminating the connection because it has received a type of data it cannot accept (e.g., an endpoint that understands only text data MAY send this if it receives a binary message).";
			else if (evt.code == 1004)
				reason = "Reserved. The specific meaning might be defined in the future.";
			else if (evt.code == 1005)
				reason = "No status code was actually present.";
			else if (evt.code == 1006)
				reason = "The connection was closed abnormally, e.g., without sending or receiving a Close control frame";
			else if (evt.code == 1007)
				reason = "An endpoint is terminating the connection because it has received data within a message that was not consistent with the type of the message (e.g., non-UTF-8 [http://tools.ietf.org/html/rfc3629] data within a text message).";
			else if (evt.code == 1008)
				reason = "An endpoint is terminating the connection because it has received a message that \"violates its policy\". This reason is given either if there is no other sutible reason, or if there is a need to hide specific details about the policy.";
			else if (evt.code == 1009)
				reason = "An endpoint is terminating the connection because it has received a message that is too big for it to process.";
			else if (evt.code == 1010) // Note that this status code is not
				// used by the server, because it
				// can fail the WebSocket handshake
				// instead.
				reason = "An endpoint (client) is terminating the connection because it has expected the server to negotiate one or more extension, but the server didn't return them in the response message of the WebSocket handshake. <br /> Specifically, the extensions that are needed are: "
						+ evt.reason;
			else if (evt.code == 1011)
				reason = "A server is terminating the connection because it encountered an unexpected condition that prevented it from fulfilling the request.";
			else if (evt.code == 1015) {
				reason = "The connection was closed due to a failure to perform a TLS handshake (e.g., the server certificate can't be verified).";
				log.log("onclose", reason);
				chohows.closed(reason);
				return;
			} else
				reason = "Unknown reason";
		}
		if (evt.code != 1000) {
			if (chohows.params.connect_times == 0
					|| chohows.retry_times < chohows.params.connect_times) {
				chohows.retry_times++;
				setTimeout(function() {
					chohows.init();
				}, 1000);
			} else {
				log.log("onclose", reason);
				chohows.closed(reason, evt.code);
			}
		}
	};
	chohows.onmessage = function(evt) {
		log.debug("onmessage", evt.data);
		if ($.type(evt.data) == 'string') {
			var tmsg = JSON.parse(evt.data);
			if (tmsg.type == TYPE_CONNECTBACK) {
				chohows.retry_times = 0;
				chohows.params[TYPE_CONNECTBACK] = tmsg.params;
				chohows.ready(tmsg.params);
			} else if (tmsg.type == TYPE_HEARTBEAT) {
				log.debug("heartbeat back", JSON.stringify(tmsg));
				chohows.heart_thread();
			} else if (tmsg.type == TYPE_ERROR) {
				if (!messagequenes[tmsg.id]) {
					messagequenes[tmsg.id] = tmsg;
				}
				parseMessage(tmsg.id);
			} else if (tmsg.type == TYPE_TEXT) {
				if (!messagequenes[tmsg.id]) {
					messagequenes[tmsg.id] = tmsg;
				}
				if (!tmsg.multi) {
					parseMessage(tmsg.id);
				} else if (tmsg.state == STATE_BEGIN) {
					tmsg["blobs"] = [];
				} else if (tmsg.state == STATE_END) {
					parseMessage(tmsg.id);
				}
			} else if (tmsg.type == TYPE_BLOB) {
				if (tmsg.state == STATE_BEGIN) {
					tblob = $.extend(true, {
						u8arr : new Uint8Array(tmsg.blob.size),
						blobs : [],
						cursor : 0
					}, tmsg);
				} else if (tmsg.state == STATE_END) {
					if (tblob.id == tmsg.id) {
						if (messagequenes[tblob.msgid]) {
							messagequenes[tblob.msgid].blobs.push($
									.extend(true, {}, tblob));
							tblob = undefined;
						}
					}
				}
			}
		} else if (evt.data instanceof Blob) {
			if (tblob) {
				tblob.blobs.push(evt.data);
			}
		}
	};
	chohows.onerror = function(evt) {
		log.error("onerror", undefined, evt);
		chohows.error(evt);
	};
	chohows.last_heartbeat_thread;
	chohows.heart_thread = function() {
		setTimeout(function() {
			var send = $.extend(true, {}, HEART_BEAT_TEMPLATE, {
				id : '_msg_' + generateId()
			});
			log.debug("heartbeat", JSON.stringify(send));
			chohows.websocket.send(JSON.stringify(send));
		}, chohows.params.heart_time);
		clearTimeout(chohows.last_heartbeat_thread);
		chohows.last_heartbeat_thread = setTimeout(function() {
			clearTimeout(chohows.last_heartbeat_thread);
			chohows.init();
		}, chohows.params.heart_time * 2);
	}; 
	chohows.onopen = function(evt) {
		chohows.retry_times = 0;
		log.log("onopen", "connect [" + chohows.params.url + "] success");
		chohows.heart_thread();
	};
	chohows.cbbuffer = {};
	chohows.buffercallback = function(message, callback, error) {
		chohows.cbbuffer[message.id] = {
			message: message,
			callback: callback,
			error: error
		}
	};
	chohows.callback = function(message) {
		if (chohows.cbbuffer[message.id]) {
			if (chohows.cbbuffer[message.id].callback && message.type == TYPE_TEXT) {
				chohows.cbbuffer[message.id].callback(message.params);
			}
			if (chohows.cbbuffer[message.id].error && message.type == TYPE_ERROR) {
				chohows.cbbuffer[message.id].error(message.state);
			}
			delete chohows.cbbuffer[message.id];
			return true;
		}
		return false;
	};
	chohows.send = function(params) {
		log.log("send", JSON.stringify(params.data));
		var send = $.extend(true, {}, TEXT_MESSAGE_TEMPLATE, {
			id : '_msg_' + generateId(),
			params : $.extend(true, {}, params.data),
		});

		var blobs = [];
		
		var sendcontent = function() {
			var t = $.extend(true, {}, send);
			for (var i in blobs) {
				var p = t;
				var path = blobs[i].path;
				for ( var k in blobs[i].path) {
					var key = blobs[i].path[k];
					if (k < blobs[i].path.length - 1) {
						p = p[key];
					} else {
						var b = $.extend(true, {}, blobs[i]);
						delete b["path"];
						delete b["state"];
						p[key] = {
							dataurl : URL.createObjectURL(blobs[i].blob),
							fileName : blobs[i].filename,
							id : blobs[i].id,
							md5 : blobs[i].md5,
							contentType : blobs[i].blob.type,
							msgid : blobs[i].msgid,
							size : blobs[i].blob.size,
							type : blobs[i].blob.type
						};
					}
				}
			}
			if (params.beforesend) {
				params.beforesend(t.params);
			}
			
			function sendblob(blob, cb) {
				var b = $.extend(true, {}, BLOB_MESSAGE_TEMPLATE, {
					id : blob.id,
					msgid : send.id,
					path : blob.path,
					blob : {
						fileName : blob.filename,
						contentType : blob.blob.type,
						size : blob.blob.size,
						md5 : blob.md5
					}
				});
				log.debug("send", JSON.stringify(b));
				chohows.websocket.send(JSON.stringify(b));
				log.debug("send", "blob=" + b.id);
				chohows.websocket.send(blob.blob);
				b.state = 'end';
				log.debug("send", JSON.stringify(b));
				chohows.websocket.send(JSON.stringify(b));
				cb();
			}
			
			function loopsend(i) {
				sendblob(blobs[i], function() {
					i++;
					if (i < blobs.length) {
						loopsend(i);
					} else {
						var e = {
							id : send.id,
							type : send.type,
							multi : send.multi,
							state : 'end'
						};
						log.debug("send", JSON.stringify(e));
						chohows.buffercallback(t, params.callback, params.error);
						chohows.websocket.send(JSON.stringify(e));
					}
				});
			}
			if (blobs.length == 0) {
				send.state = 'end';
				log.debug("send", JSON.stringify(send));
				chohows.buffercallback(t, params.callback, params.error);
				chohows.websocket.send(JSON.stringify(send));
			} else {
				log.debug("send", JSON.stringify(send));
				chohows.websocket.send(JSON.stringify(send));
				loopsend(0);
			}
		}
		
		var readcontent = function() {
			function readblob(blob, cb) {
				if (blob['blob']) {
					b2b(blob.blob, blob.id, function(rtn) {
						blob.md5 = rtn.md5;
						blob.filename = blob.filename || rtn.filename;
						cb();
					});
				} else if (blob['url']) {
					u2b(blob.url, function(rtn) {
						blob.md5 = rtn.md5;
						blob.filename = blob.filename || rtn.filename;
						blob.blob = rtn.blob;
						cb();
					})
				} else if (blob['file']) {
					f2b(blob.file, function(rtn) {
						blob.md5 = rtn.md5;
						blob.filename = blob.filename || rtn.filename;
						blob.blob = rtn.blob;
						cb();
					})
				} else if (blob['data']) {
					d2b(blob.data, blob.id, function(rtn) {
						blob.md5 = rtn.md5;
						blob.filename = blob.filename || rtn.filename;
						cb();
					});
				}
			}
			
			function loopread(i) {
				if (i < blobs.length) {
					readblob(blobs[i], function() {
						i++;
						loopread(i);
					});
				} else {
					sendcontent();
				}
			}
			
			loopread(0);
		}

		var compute = function() {
			function computeblob(key, parent, patharray) {
				var pa = $.extend(true, [], patharray);
				pa.push(key);
				if (parent[key]['type'] == BLOB_TYPE.type) {
					var newvalue = '_blob_' + generateId();
					send.multi = true;
					if (parent[key]['url']) {
						blobs.push({
							id : newvalue,
							path : pa,
							filename : parent[key]['filename'],
							url : parent[key]['url']
						});
					} else if (parent[key]['file']) {
						blobs.push({
							id : newvalue,
							path : pa,
							filename : parent[key]['filename'],
							file : parent[key]['file']
						});
					} else if (parent[key]['data']) {
						blobs.push({
							id : newvalue,
							path : pa,
							filename : parent[key]['filename'],
							data : parent[key]['data']
						});
					} else if (parent[key]['blob']) {
						blobs.push({
							id : newvalue,
							path : pa,
							filename : parent[key]['filename'],
							blob : parent[key]['blob']
						});
					}
					parent[key] = newvalue;
				} else if ($.type(parent[key]) == 'blob') {
					var newvalue = '_blob_' + generateId();
					send.multi = true;
					blobs.push({
						id : newvalue,
						path : pa,
						blob : parent[key]
					});
					parent[key] = newvalue;
				} else if ($.isPlainObject(parent[key])) {
					for ( var o in parent[key]) {
						computeblob(o, parent[key], pa);
					}
				}
			}
			computeblob('params', send, []);
			readcontent();
		}
		
		compute();
	};

	var generateId = function() {
		var now = new Date()
		var id = formatDate(now, 'yyyyMMddHHmmss') + formatDate(now, 'sss')
				+ Math.round(Math.random() * 1000);
		return id;
	}

	var b2b = function(blob, filename, callback) {
		var reader = new FileReader();
		reader.onloadend = function() {
			callback({
				md5 : hex_md5(reader.result),
				filename : blob.name || filename,
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

	var f2b = function(file, callback) {
		var reader = new FileReader();
		reader.onloadend = function() {
			d2b(reader.result, file.name, callback)
		}
		reader.readAsDataURL(file);
	}

	var messagequenes = {};
	var tblob = undefined;

	var parseMessage = function(id) {
		if (messagequenes[id]) {
			if (messagequenes[id].multi) {
				function fillBlob(i) {
					function readBlob(j) {
						var reader = new FileReader();
						reader.onloadend = function() {
							var rr = new Uint8Array(reader.result);
							for ( var r in rr) {
								messagequenes[id].blobs[i].u8arr[messagequenes[id].blobs[i].cursor] = rr[r];
								messagequenes[id].blobs[i].cursor++;
							}
							if (j < messagequenes[id].blobs[i].blobs.length - 1) {
								readBlob(j + 1);
							} else {
								messagequenes[id].blobs[i].blob.dataurl = URL
										.createObjectURL(new Blob(
												[ messagequenes[id].blobs[i].u8arr ],
												{
													type : messagequenes[id].blobs[i].blob.contentType
												}));
								var p = messagequenes[id];
								var path = messagequenes[id].blobs[i].path;
								for ( var k in messagequenes[id].blobs[i].path) {
									var key = messagequenes[id].blobs[i].path[k];
									if (k < messagequenes[id].blobs[i].path.length - 1) {
										p = p[key];
									} else {
										p[key] = messagequenes[id].blobs[i].blob;
									}
								}
								if (i < messagequenes[id].blobs.length - 1) {
									fillBlob(i + 1);
								} else {
									delete messagequenes[id]["blobs"];
									delete messagequenes[id]["state"];
									chohows.receive(messagequenes[id]);
									delete messagequenes[id];
								}
							}
						}
						reader.readAsArrayBuffer(messagequenes[id].blobs[i].blobs[j]);
					}
					readBlob(0);
				}
				fillBlob(0);
			} else {
				chohows.receive(messagequenes[id]);
				delete messagequenes[id];
			}
		}
	}
	

	var hexcase = 0;  /* hex output format. 0 - lowercase; 1 - uppercase        */
	var b64pad  = ""; /* base-64 pad character. "=" for strict RFC compliance   */
	var chrsz   = 8;  /* bits per input character. 8 - ASCII; 16 - Unicode      */

	/*
	 * These are the functions you'll usually want to call
	 * They take string arguments and return either hex or base-64 encoded strings
	 */
	var hex_md5 = function(s){ return binl2hex(core_md5(str2binl(s), s.length * chrsz));}
	var b64_md5 = function(s){ return binl2b64(core_md5(str2binl(s), s.length * chrsz));}
	var str_md5 = function(s){ return binl2str(core_md5(str2binl(s), s.length * chrsz));}
	var hex_hmac_md5 = function(key, data) { return binl2hex(core_hmac_md5(key, data)); }
	var b64_hmac_md5 = function(key, data) { return binl2b64(core_hmac_md5(key, data)); }
	var str_hmac_md5 = function(key, data) { return binl2str(core_hmac_md5(key, data)); }

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

})($chohows, window);