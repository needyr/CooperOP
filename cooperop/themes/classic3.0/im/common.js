/**
 * Shine.Xia Choho Common JS SDK v1.0
 */
var $chohocommon = $chohocommon || {};
(function(chohocommon, window) {
	/**
	 * 替换表达式值 $[name] excuteExpressionD('$[name]',{name:'张三'})
	 *
	 * @param str
	 *            要处理的表达式
	 * @param map
	 *            值对象 ，一般是一个js对象
	 * @returns 返回处理后的值
	 */
	chohocommon["dlexpr"] = function(str, map) {
		if (str == undefined || str == null) {
			return "";
		}
		str = "" + str;
		for ( var t in map) {
			while (str.indexOf("$[" + t + "]") > -1) {
				str = str.replace("$[" + t + "]", map[t] == null ? "" : map[t]);
			}
		}
		while (str.indexOf("$[") > -1) {
			var t1 = str.substring(0, str.indexOf("$["));
			var t2 = str.substring(str.indexOf("$["));
			t2 = t2.substring(t2.indexOf("]") + 1);
			str = t1 + t2;
		}
		return str;
	};

	/**
	 * 格式化数字
	 *
	 * @param srcstr
	 *            需要格式的数字
	 * @param nafterdot
	 *            格式化格式 如："#,##0.00"
	 * @returns 格式后的值
	 */
	chohocommon["formatnumber"] = function(srcstr, nafterdot) {
		nafterdot = nafterdot || "#,##0.00";
		// TODO: 将format中的自定义字符换掉
		if (srcstr == "" || srcstr == undefined || srcstr == null
				|| srcstr == "0") {
			return srcstr;
		}
		var v = format(nafterdot, srcstr);
		return v;
	};

	chohocommon["formatfilesize"] = function(bytes) {
		if (typeof bytes !== 'number') {
			return '';
		}
		if (bytes >= 1000000000) {
			return (bytes / 1000000000).toFixed(2) + ' GB';
		}
		if (bytes >= 1000000) {
			return (bytes / 1000000).toFixed(2) + ' MB';
		}
		return (bytes / 1000).toFixed(2) + ' KB';
	};

	chohocommon["formatdate"] = function(date, format) {
		return chohocommon.formatDate(date, format);
	};

	chohocommon["computeage"] = function(birthday) {
		var bDay = new Date(birthday), nDay = new Date(), nbDay = new Date(nDay
				.getFullYear(), bDay.getMonth(), bDay.getDate()), age = nDay
				.getFullYear()
				- bDay.getFullYear();
		if (bDay.getTime() > nDay.getTime()) {
			return -1
		}
		return nbDay.getTime() <= nDay.getTime() ? age : --age;
	};

	chohocommon["parseidcard"] = function(ic) {
		if (!ic)
			return ic;
		var bd = [];
		var sex = '';
		if (ic.length == 15) {
			bd.push("19" + ic.substring(6, 8));
			bd.push(ic.substring(8, 10));
			bd.push(ic.substring(10, 12));
			sex = +ic.substring(14, 15) % 2 == 1 ? '男' : '女';
		} else if (ic.length == 18) {
			bd.push(ic.substring(6, 10));
			bd.push(ic.substring(10, 12));
			bd.push(ic.substring(12, 14));
			sex = +ic.substring(16, 17) % 2 == 1 ? '男' : '女';
		}

		return {
			area : ic.substring(0, 6),
			birthday : db.join("-"),
			sex : sex,
			age : $.computeage(bd.join("-"))
		}
	};

	chohocommon["removehtml"] = function(s) {
		return (s) ? $("<p>").append(s).text() : "";
	};

	chohocommon["is_mobile"] = function() {
		return navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i);
	};

	chohocommon["loadRES"] = function(js, success, error) {
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
				for (var i = 0; i < ls.length; i++) {
					if (ls[i].src == initjs[o].src) {
						initjs[o].ready = true;
						break;
					}
				}
			} else if (cssreg.test(initjs[o].src)) {
				var cs = oHead.getElementsByTagName('link');
				for (var i = 0; i < cs.length; i++) {
					if (cs[i].href == initjs[o].src) {
						initjs[o].ready = true;
						break;
					}
				}
			}
			if (initjs[o].ready)
				break;

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

	chohocommon["formatDate"] = function(date, format, options) {
		return chohocommon.formatDates(date, null, format, options);
	};

	chohocommon["formatDates"] = function(date1, date2, format, options) {
		var defaults = {
			dayNamesShort : [ "周日", "周一", "周二", "周三", "周四", "周五", "周六" ],
			dayNames : [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ],
			monthNamesShort : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月',
					'9月', '10月', '11月', '12月' ],
			monthNames : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月",
					"九月", "十月", "十一月", "十二月" ]
		};
		options = options || defaults;
		var date = date1, otherDate = date2, i, len = format.length, c, i2, formatter, res = '';
		for (i = 0; i < len; i++) {
			c = format.charAt(i);
			if (c == "'") {
				for (i2 = i + 1; i2 < len; i2++) {
					if (format.charAt(i2) == "'") {
						if (date) {
							if (i2 == i + 1) {
								res += "'";
							} else {
								res += format.substring(i + 1, i2);
							}
							i = i2;
						}
						break;
					}
				}
			} else if (c == '(') {
				for (i2 = i + 1; i2 < len; i2++) {
					if (format.charAt(i2) == ')') {
						var subres = chohocommon.formatDate(date, format
								.substring(i + 1, i2), options);
						if (parseInt(subres.replace(/\D/, ''), 10)) {
							res += subres;
						}
						i = i2;
						break;
					}
				}
			} else if (c == '[') {
				for (i2 = i + 1; i2 < len; i2++) {
					if (format.charAt(i2) == ']') {
						var subformat = format.substring(i + 1, i2);
						var subres = chohocommon.formatDate(date, subformat,
								options);
						if (subres != chohocommon.formatDate(otherDate,
								subformat, options)) {
							res += subres;
						}
						i = i2;
						break;
					}
				}
			} else if (c == '{') {
				date = date2;
				otherDate = date1;
			} else if (c == '}') {
				date = date1;
				otherDate = date2;
			} else {
				for (i2 = len; i2 > i; i2--) {
					if (formatter = dateFormatters[format.substring(i, i2)]) {
						if (date) {
							res += formatter(date, options);
						}
						i = i2 - 1;
						break;
					}
				}
				if (i2 == i) {
					if (date) {
						res += c;
					}
				}
			}
		}
		return res;
	};

	var winNotify_error = false;
	chohocommon["winNotify"] = function(opts) {
		opts = $.extend(true, {
			title: "众和IM",
			content: "您有一条新消息，请注意查看！",
			icon: $cimc.config.http_url + "/css/img/im-logo.png",
			clickback: function() {},
			id: undefined
		}, opts);

        if (window.webkitNotifications) {
            //chrome老版本
            if (window.webkitNotifications.checkPermission() == 0) {
                var notif = window.webkitNotifications.createNotification(opts.icon, opts.title, opts.content);
                notif.display = function() {}
                notif.onerror = function() {}
                notif.onclose = function() {}
                notif.onclick = function() {
                	this.cancel();
                	opts.clickback();
                }
                notif.replaceId = opts.id;
                notif.show();
            } else {
                window.webkitNotifications.requestPermission(function(permission) {
                    // Whatever the user answers, we make sure we store the
                    // information
                    if (!('permission' in Notification)) {
                        Notification.permission = permission;
                    }
                    //如果接受请求
                    if (permission === "granted") {
                    	$.winNotify(opts);
                    }
                });
            }
        }
        else if("Notification" in window){
            // 判断是否有权限
            if (Notification.permission === "granted") {
                var notification = new Notification(opts.title, {
                    "icon": opts.icon,
                    "body": opts.content,
                    "tag": opts.id
                });
                notification.onclick = function() {
                	notification.close();
                	opts.clickback();
                }
            }
            //如果没权限，则请求权限
            else if (Notification.permission !== 'denied') {
                Notification.requestPermission(function(permission) {
                    // Whatever the user answers, we make sure we store the
                    // information
                    if (!('permission' in Notification)) {
                        Notification.permission = permission;
                    }
                    //如果接受请求
                    if (permission === "granted") {
                    	$.winNotify(opts);
                    }
                });
            }
        } else {
        	if (!winNotify_error) {
        		winNotify_error = true;
        		//alert('你的浏览器不支持消息推送特性。');
        	}
	    }
	};

	chohocommon["computeFileSize"] = function(size, level) {
		if (level == undefined) level = 0;
		var d = ["B", "KB", "MB", "GB", "TB"];
		if (size >= 1024) {
			size = size / 1024;
			level ++;
			return chohocommon["computeFileSize"](size, level);
		}
		else {
			return chohocommon["formatnumber"](size, "#,###.#") + d[level];
		}
	};

	chohocommon["capitalize"] = function(str) {
		return str.replace(/\b\w+\b/g, function(word) {
			return word.substring(0,1).toUpperCase( ) +  word.substring(1);
		});
	};

	var dateFormatters = {
		s : function(d) {
			return d.getSeconds();
		},
		ss : function(d) {
			return zeroPad(d.getSeconds());
		},
		sss : function(d) {
			return zeroPad(d.getMilliseconds(), 3);
		},
		m : function(d) {
			return d.getMinutes();
		},
		mm : function(d) {
			return zeroPad(d.getMinutes());
		},
		h : function(d) {
			return d.getHours() % 12 || 12;
		},
		hh : function(d) {
			return zeroPad(d.getHours() % 12 || 12);
		},
		H : function(d) {
			return d.getHours();
		},
		HH : function(d) {
			return zeroPad(d.getHours());
		},
		d : function(d) {
			return d.getDate();
		},
		dd : function(d) {
			return zeroPad(d.getDate());
		},
		ddd : function(d, o) {
			return o.dayNamesShort[d.getDay()];
		},
		dddd : function(d, o) {
			return o.dayNames[d.getDay()];
		},
		M : function(d) {
			return d.getMonth() + 1;
		},
		MM : function(d) {
			return zeroPad(d.getMonth() + 1);
		},
		MMM : function(d, o) {
			return o.monthNamesShort[d.getMonth()];
		},
		MMMM : function(d, o) {
			return o.monthNames[d.getMonth()];
		},
		yy : function(d) {
			return (d.getFullYear() + '').substring(2);
		},
		yyyy : function(d) {
			return d.getFullYear();
		},
		t : function(d) {
			return d.getHours() < 12 ? 'a' : 'p';
		},
		tt : function(d) {
			return d.getHours() < 12 ? 'am' : 'pm';
		},
		T : function(d) {
			return d.getHours() < 12 ? 'A' : 'P';
		},
		TT : function(d) {
			return d.getHours() < 12 ? 'AM' : 'PM';
		},
		u : function(d) {
			return chohocommon.formatDate(d, "yyyy-MM-dd'T'HH:mm:ss'Z'");
		},
		S : function(d) {
			var date = d.getDate();
			if (date > 10 && date < 20) {
				return 'th';
			}
			return [ 'st', 'nd', 'rd' ][date % 10 - 1] || 'th';
		}
	};

	function zeroPad(n, p) {
		p = p || 2;
		return ((n < Math.pow(10, p)) ? ("" + (Math.pow(10, p) + n))
				.substring(1) : n);
	}

})($chohocommon, window);
