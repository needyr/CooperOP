var cloud_app = {
	is_app: false,
	app_name: undefined
};
function decodeURLParams(url) {
	var p = {};
	if (url.indexOf("?") < 0) {
		return p;
	}
	url = url.substring(url.indexOf("?") + 1);
	var params = url.split("&");
	for (var i = 0; i < params.length; i ++) {
		var t = params[i].split("=");
		p[t[0]] = t[1];
	}
	return p;
}

function excuteURLParams(data) {
	var temp = [];
	for ( var key in data) {
		if ($.isArray(data[key])) {
			for ( var i = 0; i < data[key].length; i++) {
				var t = data[key][i];
				temp.push(key + "=" + (t ==='' || typeof t == undefined || t==null ? "":encodeURIComponent(t)));
			}
		} else {
			var t = data[key];
			temp.push(key + "=" + (t ==='' || typeof t == undefined || t==null ?"": encodeURIComponent(t)));
		}
	}
	return temp.join("&");
}

// 窗口关闭 window.close(); 
function windowClose() {
	var topInners = top.windows;// 关闭inner
	var topComs = top.windowComs;
	if (topInners != null && topInners.length > 0) {
		var win = topInners[topInners.length - 1];
		var parentWin = topComs[topComs.length - 1];
		if (parentWin != null) {
			parentWin.commitPage();
		}
		win.destroy();
		topInners.pop();
		topComs.pop();
	} else {
		window.open('','_top');
		window.top.close();
	}
}

/* Date Parsing
-----------------------------------------------------------------------------*/

/**
 * 将日期格式的字符串转换称 js的Date
 */
function parseDate(s, ignoreTimezone) { // ignoreTimezone defaults to true
	if (typeof s == 'object') { // already a Date object
		return s;
	}
	if (typeof s == 'number') { // a UNIX timestamp
		return new Date(s * 1000);
	}
	if (typeof s == 'string') {
		if (s.match(/^\d+$/)) { // a UNIX timestamp
			return new Date(parseInt(s, 10) * 1000);
		}
		if (ignoreTimezone === undefined) {
			ignoreTimezone = true;
		}
		return parseISO8601(s, ignoreTimezone) || (s ? new Date(s) : null);
	}
	
	return null;
}


function parseISO8601(s, ignoreTimezone) { // ignoreTimezone defaults to false
	// derived from http://delete.me.uk/2005/03/iso8601.html
	var m = s.match(/^([0-9]{4})(-([0-9]{2})(-([0-9]{2})([T ]([0-9]{2}):([0-9]{2})(:([0-9]{2})(\.([0-9]+))?)?(Z|(([-+])([0-9]{2}):([0-9]{2})))?)?)?)?$/);
	if (!m) {
		return null;
	}
	var date = new Date(m[1], 0, 1);
	if (ignoreTimezone || !m[14]) {
		var check = new Date(m[1], 0, 1, 9, 0);
		if (m[3]) {
			date.setMonth(m[3] - 1);
			check.setMonth(m[3] - 1);
		}
		if (m[5]) {
			date.setDate(m[5]);
			check.setDate(m[5]);
		}
		fixDate(date, check);
		if (m[7]) {
			date.setHours(m[7]);
		}
		if (m[8]) {
			date.setMinutes(m[8]);
		}
		if (m[10]) {
			date.setSeconds(m[10]);
		}
		if (m[12]) {
			date.setMilliseconds(Number("0." + m[12]) * 1000);
		}
		fixDate(date, check);
	}else{
		date.setUTCFullYear(
			m[1],
			m[3] ? m[3] - 1 : 0,
			m[5] || 1
		);
		date.setUTCHours(
			m[7] || 0,
			m[8] || 0,
			m[10] || 0,
			m[12] ? Number("0." + m[12]) * 1000 : 0
		);
		var offset = Number(m[16]) * 60 + Number(m[17]);
		offset *= m[15] == '-' ? 1 : -1;
		date = new Date(+date + (offset * 60 * 1000));
	}
	return date;
}

function fixDate(d, check) { // force d to be on check's YMD, for daylight savings purposes
	if (+d) { // prevent infinite looping on invalid dates
		while (d.getDate() != check.getDate()) {
			d.setTime(+d + (d < check ? 1 : -1) * HOUR_MS);
		}
	}
}

function parseTime(s) { // returns minutes since start of day
	if (typeof s == 'number') { // an hour
		return s * 60;
	}
	if (typeof s == 'object') { // a Date object
		return s.getHours() * 60 + s.getMinutes();
	}
	var m = s.match(/(\d+)(?::(\d+))?\s*(\w+)?/);
	if (m) {
		var h = parseInt(m[1], 10);
		if (m[3]) {
			h %= 12;
			if (m[3].toLowerCase().charAt(0) == 'p') {
				h += 12;
			}
		}
		return h * 60 + (m[2] ? parseInt(m[2], 10) : 0);
	}
}

/**
 * 格式化数字
 * 
 * @param srcstr
 *            需要格式的数字
 * @param nafterdot
 *            格式化格式 如："#,##0.00"
 * @returns 格式后的值
 */
function formatnumber(srcstr, nafterdot) {
	nafterdot = nafterdot || "#,##0.00";
	// TODO: 将format中的自定义字符换掉
	if (srcstr == "" || srcstr == undefined || srcstr == null || srcstr == "0") {
		return srcstr;
	}
	var v = format(nafterdot, srcstr);
	return v;
}

window.format=function(b,a){if(!b||isNaN(+a))return a;var a=b.charAt(0)=="-"?-a:+a,j=a<0?a=-a:0,e=b.match(/[^\d\-\+#]/g),h=e&&e[e.length-1]||".",e=e&&e[1]&&e[0]||",",b=b.split(h),a=a.toFixed(b[1]&&b[1].length),a=+a+"",d=b[1]&&b[1].lastIndexOf("0"),c=a.split(".");if(!c[1]||c[1]&&c[1].length<=d)a=(+a).toFixed(d+1);d=b[0].split(e);b[0]=d.join("");var f=b[0]&&b[0].indexOf("0");if(f>-1)for(;c[0].length<b[0].length-f;)c[0]="0"+c[0];else+c[0]==0&&(c[0]="");a=a.split(".");a[0]=c[0];if(c=d[1]&&d[d.length-
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            1].length){for(var d=a[0],f="",k=d.length%c,g=0,i=d.length;g<i;g++)f+=d.charAt(g),!((g-k+1)%c)&&g<i-c&&(f+=e);a[0]=f}a[1]=b[1]&&a[1]?h+a[1]:"";return(j?"-":"")+a[0]+a[1]};



/*
 * Date Formatting
 * -----------------------------------------------------------------------------
 */


function formatDate(date, format, options) {
	return formatDates(date, null, format, options);
}


function formatDates(date1, date2, format, options) {
	var defaults = {
		dayNamesShort : [ "周日", "周一", "周二", "周三", "周四", "周五", "周六" ],
		dayNames : [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ],
		monthNamesShort : [ '1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月',
				'9月', '10月', '11月', '12月' ],
		monthNames : [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月",
				"十月", "十一月", "十二月" ]
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
					var subres = formatDate(date, format.substring(i + 1, i2),
							options);
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
					var subres = formatDate(date, subformat, options);
					if (subres != formatDate(otherDate, subformat, options)) {
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
		return formatDate(d, "yyyy-MM-dd'T'HH:mm:ss'Z'");
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
	return ((n < Math.pow(10,p)) ? ("" + (Math.pow(10,p) + n)).substring(1) : n);
}

var _mask_index = [];
var modals = {};
var winNotify_error = false;
//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外  
function windowkey(e) {  
    var ev = e || window.event; //获取event对象
    //判断退格键
    if (ev.keyCode == 8) {
        var obj = ev.target || ev.srcElement; //获取事件源   
        var t = obj.type || obj.getAttribute('type'); //获取事件源类型   
        //获取作为判断条件的事件类型   
        var vReadOnly = obj.readOnly;  
        var vDisabled = obj.disabled;  
        var vEditable = obj.getAttribute('contenteditable');
        //处理undefined值情况   
        vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;  
        vDisabled = (vDisabled == undefined) ? true : vDisabled;  
        vEditable = (vEditable == undefined) ? false : vEditable == 'true';  
        //当敲Backspace键时，事件源类型为密码或单行、多行文本的，   
        //并且readOnly属性为true或disabled属性为true的，则退格键失效   
        var flag1 = ev.keyCode == 8 && (t == "password" || t == "number" || t == "text" || t == "textarea" || t == "search") && (vReadOnly == true || vDisabled == true);  
        //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效   
        var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "number" && t != "textarea" && t != "search" && vEditable == false;  
	    if (flag2 || flag1) {
	    	if (ismodal) {
	    		e.returnValue = false;
	    		$.closeModal();
	    		return false;
	    	} else {
	    		var toplayer = null;
	    		if (top.layer && top != window) {
	    			toplayer = top;
	    		} else {
	    			var p = window;
	    			while (p.parent != p && p.parent.layer) {
	    				p = p.parent;
	    			}
	    			toplayer = p;
	    		}
	        	var modal_index;
	        	for (var o in toplayer.modals) {
	        		modal_index = toplayer.modals[o].index;
	        	}
	        	if (modal_index) {
		    		if (toplayer.modals["m_" + modal_index].callback) {
		    			toplayer.modals["m_" + modal_index].callback();
		    		}
		    		delete toplayer.modals["m_" + modal_index];
		    		toplayer.layer.close(modal_index);
		    		e.returnValue = false;
		    		return false;
		    	} else {
		    		e.returnValue = false;
		    		return false;
		    	}
	    	}
		}
    } else if (ev.keyCode == 27){  //ESC
		if (ismodal) {
    		e.returnValue = false;
    		$.closeModal();
    		return false;
    	} else {
    		var toplayer = null;
    		if (top.layer && top != window) {
    			toplayer = top;
    		} else {
    			var p = window;
    			while (p.parent != p && p.parent.layer) {
    				p = p.parent;
    			}
    			toplayer = p;
    		}
        	var modal_index;
        	for (var o in toplayer.modals) {
        		modal_index = toplayer.modals[o].index;
        	}
        	if (modal_index) {
	    		if (toplayer.modals["m_" + modal_index].callback) {
	    			toplayer.modals["m_" + modal_index].callback();
	    		}
	    		delete toplayer.modals["m_" + modal_index];
	    		toplayer.layer.close(modal_index);
	    		e.returnValue = false;
	    		return false;
	    	}
    	}
	}else if (ev.keyCode == 13){
		if ($(".layui-layer-btn0").html()) {
			$(".layui-layer-btn0").click();
			e.returnValue = false;
    		return false;
		}
	}
}  
//禁止后退键 作用于Firefox、Opera  
document.onkeypress = windowkey;  
//禁止后退键  作用于IE、Chrome  
document.onkeydown = windowkey;  
/*window.onbeforeunload = function(e) {
	if (ismodal) {
		var ev = e || window.event;
		ev.returnValue = false;
		$.closeModal();
		return false;
	}
}*/
$.extend( {

	"ypsms": function(name) {
		$.call("application.common.ypsms", {
			name: name
		}, function(rtn) {
			if (rtn) {
				window.open(rtn, name);
			}
		}, undefined, {
			async: true,
			nomask: "正在获取用药说明书地址..."
		});
	},
	/**
	 * 替换表达式值 $[name] excuteExpressionD('$[name]',{name:'张三'})
	 * @param str 要处理的表达式
	 * @param map 值对象 ，一般是一个js对象
	 * @returns 返回处理后的值
	 */
	"dlexpr": function(str, map) {
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
		return str.replace(/\$template\[/g,'$[');
	},
	/**
	 * 格式化数字
	 * @param srcstr 需要格式的数字
	 * @param nafterdot 格式化格式 如："#,##0.00"
	 * @returns 格式后的值
	 */
	"formatnumber": function(srcstr, nafterdot) {
		nafterdot = nafterdot || "#,##0.00";
		// TODO: 将format中的自定义字符换掉
		if (srcstr == "" || srcstr == undefined || srcstr == null || srcstr == "0") {
			return srcstr;
		}
		var v = format(nafterdot, srcstr);
		return v;
	},
	"formatfilesize": function (bytes) {
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
    },
	"formatdate": function(date, format) {
		return formatDate(date, format);
	},
	"computeage": function (birthday) {
		var bDay = new Date(birthday),
		nDay = new Date(),
		nbDay = new Date(nDay.getFullYear(),bDay.getMonth(),bDay.getDate()),
		age = nDay.getFullYear() - bDay.getFullYear();
		if (bDay.getTime()>nDay.getTime()) {return -1}
		return nbDay.getTime()<=nDay.getTime()?age:--age;
	},
	"parseidcard": function(ic) {
		if (!ic) return ic;
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
			area: ic.substring(0, 6),
			birthday: bd.join("-"),
			sex: sex,
			age: $.computeage(bd.join("-"))
		}
	},
	"removehtml": function(s) {
		return (s)? $("<p>").append(s).text(): "";
	},
	"is_mobile": function() {
		return navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i);
	},
	"openTabPage": function (pageid, title, url, need_refresh, params, ding) {
		var url2 = '/w';
		if(url.startsWith('/w') || url.startsWith('http')){
			url2 = url;
		}else if('undefined' !=typeof(cooperopcontextpath)){
			url2 = cooperopcontextpath+'/w';
			url2 = url2 + "/" + url.replace(/\./g, "/") + ".html?" + excuteURLParams(need_refresh);
		}
		if(typeof crtechTogglePage == 'undefined'){
			top.$.open_tabwindow(pageid, title, url2, need_refresh, params, ding);
		}else{
			crtechOpenPage(pageid, title, url2, 0, params, {pageid : params.pageid}, ding);
		}
	},
	/**
	/**
	 * 打开一个窗口
	 * @param url 要打开的窗口的url
	 * @param data 要传入新窗口的参数
	 * @param target 要在哪个现有窗口中打开
	 */
	"open": function (url, data, target) {
		data = $.extend(true, {}, data);
		$("#__windowOpenForm").remove();
		var fmhtml =$('<form id="__windowOpenForm" enctype="application/x-www-form-urlencoded;" accept-charset="UTF-8" method="post" action=""></form>');
		fmhtml.attr("action", url);
		fmhtml.attr("target", target || "_blank");
		var input,inputStr = "<input type='hidden'>";
		for (var o in data) {
			if($.isArray(data[o])){
				for ( var i = 0; i < data[o].length; i++) {
					input = $(inputStr);
					input.attr("name",o);
					input.val(data[o][i]);
					fmhtml.append(input);
				}
			}else{
				input = $(inputStr);
				input.attr("name",o);
				input.val(data[o]);
				fmhtml.append(input);
			}
		}
		$(document).find("body").append(fmhtml);
		fmhtml = $("#__windowOpenForm");
		fmhtml.submit();
		fmhtml.remove();
	},
	/**
	 * 打开一个内部窗口.
	 * @param url 打开的窗口的url地址
	 * @param title 窗口标题
	 * @param options 窗口选项，包含height,width，callback等等
	 */
	/*"photos" : function (images, current_index) {
		if (!images) return;
		var toplayer = null;
		if (top.layer && top != window) {
			toplayer = top;
		} else {
			var p = window;
			while (p.parent != p && p.parent.layer) {
				p = p.parent;
			}
			toplayer = p;
		}
		var url = "";

		var o = {
				photos: {
					"title": "", //相册标题
					"id": new Date().getTime(), //相册id
					"start": current_index || 0, //初始显示的图片序号，默认0
					"data": [   //相册包含的图片，数组格式
		            ]
				},
				shift: 5,
				zIndex: toplayer.layer.zIndex
			};

		for (var i in images) {
			o.photos.data.push({
				"alt": "",
            	"pid": i, //图片id
            	"src": images[i].bigsrc || images[i].src, //原图地址
            	"thumb": images[i].src //缩略图地址
			});
		}
		toplayer.layer.photos(o);
	},*/
	/**
	 * 打开一个内部窗口.
	 * @param url 打开的窗口的url地址
	 * @param title 窗口标题
	 * @param options 窗口选项，包含height,width，callback等等
	 */
	"modal" : function (url, title, options, is_cs) {
		options = $.extend(true, {}, options);
		if(is_cs == 1){
			var o = $.extend(true, {}, options);
			o._CRSID = $.cookie('_CRSID');
			delete o["callback"];
			//调用陈杰的modal方法
			if (options.callback) {
				options.callback(true);
	    	}
			return ;
		}
		var toplayer = null;
		if (top.layer && top != window) {
			toplayer = top;
		} else {
			var p = window;
			while (p.parent != p && p.parent.layer) {
				p = p.parent;
			}
			toplayer = p;
		}
		if (top != window && url != undefined && url != null && url.indexOf("http") != 0 && url.indexOf("/") != 0) {
			url = location.href.substring(0, location.href.lastIndexOf("/") + 1) + url;
		}

		if (options["modalid"]) {
			for (var mi in toplayer.modals) {
				if (toplayer.modals[mi].id == options["modalid"]) {
					toplayer.layer.zIndex ++;
					toplayer.modals[mi].layero.css("z-index",toplayer.layer.zIndex + 1);
					return;
				}
			}
		}

		var o = toplayer.$.extend(true, {}, options);
		delete options["title"];
		delete options["callback"];
		delete options["width"];
		delete options["height"];
		delete options["noshade"];
		delete options["maxmin"];
		o.id = options["modalid"];
		o.type = 2;
		o.content = "blank.jsp";
		var full = false;
		if (cloud_app.is_app || $.is_mobile()) {
			full = true;
			o.width = "100%";
			o.height = "100%";
		} else if ($(toplayer).width() <= 768) {
			full = true;
			o.width = "100%";
			o.height = "100%";
		} else if (o.width) {
			if (+o.width > 0) {
				if ($(toplayer).width() <= o.width) {
					full = true;
					o.width = "100%";
					o.height = "100%";
				}
			} else if (o.width.toUpperCase().indexOf("PX") > 0) {
				if (+(o.width.substring(0, o.width.length - 2)) > $(toplayer).width()) {
					full = true;
					o.width = "100%";
					o.height = "100%";
				}
			} else if (o.width.indexOf("%") > 0) {
				if ($(toplayer).width() * +(o.width.substring(0, o.width.length - 1)) / 100 > $(toplayer).width()) {
					full = true;
					o.width = "100%";
					o.height = "100%";
				}
			}
		}
		o.area = [o.width || "80%", o.height || "80%"];
		o.maxmin  = o.maxmin == undefined ? true : o.maxmin;
		if (full) {
			o.move = false;
			o.offset = "lt";
			o.maxmin  = false;
		}
		o.fix = true;
		o.scrollbar = false;
		o.shade = o.noshade ? 0 : 0.3;
		o.shadeClose = false;
		o.zIndex = toplayer.layer.zIndex; //重点1
		if (title || o.title) {
			o.closeBtn = 1;
			o.title = '<span style="font-size:14px" title="' + (title || o.title) + '">' + (title || o.title) + '</span>';
		} else {
			o.closeBtn = 2;
			o.title = false;
			o.maxmin  = false;
		}
		o.success = function(layero, index) {
			toplayer.layer.setTop(layero);
			var body = layer.getChildFrame('body', index);
			var iframeWin = window[layero.find('iframe')[0]['name']];
			body.attr("layer_index", index);
			toplayer.modals["m_" + index] = {index: index, callback: o.callback, layero: layero, id: options["modalid"]};
			delete options["modalid"];
			if('undefined' !=typeof(cooperopcontextpath))
				if(top.contextPath != undefined && top.contextPath.indexOf("/")!=0){
					url = cooperopcontextpath+"/"+url;
				}
			$.open(url, $.extend(true, options, {ismodal: true}), layero.find('iframe')[0]['name']);
		};
		o.cancel = function(index) {
			if (o.callback) {
				o.callback(toplayer.modals["m_" + index].value);
			}
			delete toplayer.modals["m_" + index];
		}
		var index = toplayer.layer.open(o);
		if (full) {
			toplayer.layer.full(index);
		}
	},

	/**
	 * 关闭内部窗口，与上面的打开对应，一般在打开了的内部窗口中调用，以达到关闭这个窗口的作用 parent.closeModal()
	 * @param value 父页面要接受的参数，没有则不传
	 */
	"closeTabpage" : function (pageid) {
		if(typeof crtechTogglePage == 'undefined'){
			top.$.close_tabwindow(pageid);
		}else{
			crtechCloseTabPage("");
		}
	},
	"closeModal" : function (value) {
		var toplayer = null;
		if (top.layer && top != window) {
			toplayer = top;
		} else {
			var p = window;
			while (p.parent != p && p.parent.layer) {
				p = p.parent;
			}
			toplayer = p;
		}
		var modal_index = toplayer.layer.getFrameIndex(window.name);
		if (toplayer.modals["m_" + modal_index] && toplayer.modals["m_" + modal_index].callback) {
			toplayer.modals["m_" + modal_index].callback(value);
		}
		delete toplayer.modals["m_" + modal_index];
		toplayer.layer.close(modal_index);
	},
	/**
	 * 通过ajax的方式调用后台action
	 * @param page 要调用的action地址 ，如:base.teacher.query
	 * @param data 要传递到后台Action的参数
	 * @param success 调用成功返回后的回调方法
	 * @param error 调用失败返回的回调方法
	 */
	"jscall": function (page, data, success, error, opts) {
		$.call(module + "." + page, data, success, error, opts);
	},
	"call": function (page, data, success, error, opts) {
		var option = $.extend(true, {
				timeout: 60000,
				nomask: false,
				async: true}, opts);
		data["t"] = Math.random();
		data["request_method"] = "ajax";
		data["browser.version"] = $.browser.version;
		if ($.browser.msie) {
			data["browser"] = "msie";
		}
		else {
			data["browser"] = "notmsie";
		}
		var url = '/w/' + page.replace(/\./g, "/") + ".json";
		if('undefined' !=typeof(cooperopcontextpath)){
			url = cooperopcontextpath + url;
		}
		var $req = $.ajax({
			"async": option.async,
			"dataType" : "json",
			"type" : "POST",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"cache" : false,
			"url" : url,
			"timeout" : option.timeout,
			"data" : excuteURLParams(data),
			"beforeSend" : function() {
				if (option.nomask != true)
					$.mask(option.nomask != false ? option.nomask : "正在提交数据，请稍等...");
			},
			"complete" : function() {
				if (option.nomask != true)
					$.unmask();
			},
			"success" : success || function(rtn) {
			},
			"error" : function(XMLHttpRequest, textStatus, errorThrown) {
				if(XMLHttpRequest.status == 401){
					if(top.logindiv.hasLogin == false){
						top.logindiv.hasLogin = true;
						try{
							//增加page参数用于notlogin.jsp判断产品化的notloginforlogin.jsp
							var loginUrl = 'notlogin.jsp?model=ajax&page=' + page;
							if('undefined' !=typeof(cooperopcontextpath)){
								loginUrl = cooperopcontextpath+'/'+loginUrl;
							}
							modal(loginUrl, "请先登录",{title:'请先登录',width:550,height:420,'callback':function(){
									top.logindiv.hasLogin = false;
									if(arguments[0] == true){
										callAction(page, data, success, error);
									}
							}});
						}catch(e){
							top.logindiv.hasLogin = false;
						}
					}
					return;
				}
				var message = $.trim(XMLHttpRequest.responseText) || textStatus;
				if (!message) {
					if (XMLHttpRequest.status != 200) {
						message = "访问服务端异常，HTTP错误代码:" + XMLHttpRequest.status;
					}
					else {
						message = "访问服务端异常，错误原因:" + errorThrown.message;
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
				if (error) {
					error(message);
				}else{
					if(message == "无效产品，请联系管理员购买或续约！"){
						$.confirm("授权过期提醒", function(ret){
							// location.href = "/xinadmin/login.jsp";
							if(ret) window.open("/xinadmin/login.jsp","_blank");
						}, "您的产品授权已过期，是否重新授权？");
					}else {$.error(message);}
				}
			}
		});
		return $req;
	},
	/**
	 * 导出excel数据
	 * @param action 要导出excel数据的Action
	 * @param data 参数
	 */
	"export" : function(action, data) {
		var data = $.extend(true, {}, data);
		action = action || data["action"];
		delete data["action"];
		var $lc = getQueryStringRegExp("$lc");
		var data = $.extend(true, {}, data);
		if ($lc) {
			data["$lc"] = $lc;
		}
		
		data["window"] = "excel";
		var url = '/w';
		if('undefined' !=typeof(cooperopcontextpath)){
			url = cooperopcontextpath+'/w';
		}
		window.open(url + "/" + action.replace(/\./g, "/") + ".html?" + excuteURLParams(data), "_blank");
	},
	/**
	 * 导出excel数据
	 * 
	 * @param action
	 *            要导出excel数据的Action
	 * @param data
	 *            参数
	 */
	"exportExcel" : function(action, data) {
		var toplayer = null;
		if (top.layer && top != window) {
			toplayer = top;
		} else {
			var p = window;
			while (p.parent != p && p.parent.layer) {
				p = p.parent;
			}
			toplayer = p;
		}
		var url = "/" + action.replace(/\./g, "/") + ".xlsx";
		if ('undefined' != typeof (cooperopcontextpath)) {
			url = cooperopcontextpath+ "/w" + url;
		}
		function showExport(d) {
			var html = [];
			html.push("<div class='progerss-warp info' style='display:none;'>");
			html.push('<div class="progerss-bar">');
			html.push('</div>');
			html.push('<div class="progress-label">正在生成文件...</div>');
			html.push("</div>");
			html.push("</div>");
			var _t = $(html.join(""));
			toplayer.$("body").append(_t);
			_t.ccinit();
			_t.find(".progerss-bar").progressbar({
				value : 0,
				max : 100
			});

			_t.data("exporting", true);
			var _li = toplayer.layer.open({
				id : d._eid,
				type : 1,
				content : _t,
				title : false,
				zIndex : toplayer.layer.zIndex,
				skin : "exporting-layer",
				area : [
						"400px", "60px"
				],
				cancel : function(index) {
					if (_t.data("exporting")) {
						return false;
					}
					toplayer.layer.close(index);
					_t.remove();
				}
			});
			
			url = "/excelprogress";
			if ('undefined' != typeof (cooperopcontextpath)) {
				url = cooperopcontextpath +"/w"+ url;
			}
			var interval = setInterval(function() {
				$.ajax({
					"async" : true,
					"dataType" : "json",
					"type" : "POST",
					"contentType" : "application/json; charset=UTF-8",
					"cache" : false,
					"url" : url,
					"timeout" : 0,
					"data" : $.toJSON(d),
					"beforeSend" : function() {
					},
					"complete" : function() {
					},
					"success" : function(rtn) {
						var processBean = rtn;
						if (processBean.message != null) {
							_t.find(".progerss-bar").addClass("error");
							$("#msg").html(processBean.message);
						}
						;
						if (processBean.downloadUrl != null) {
							_t.data("exporting", false);
							_t.find(".progress-label")
									.html(
											"完成 ! 文件已自动下载，如果未自动下载，<a href='" + cooperopcontextpath + "/"+processBean.downloadUrl+"'>点击下载</a>");
							_t.addClass("success");
							_t.find(".progerss-bar").progressbar({
								"value" : 100
							});
							window.open(cooperopcontextpath + "/" + processBean.downloadUrl);
							window.clearInterval(interval);
						} else if (processBean.total > -1) {
							var val = parseInt(+(processBean.currNumBegin
									/ processBean.total * 100));
							if (processBean.total <= processBean.currNumEnd) {
								_t.find(".progress-label").text(
										"当前处理" + processBean.currNumBegin
												+ " - " + processBean.total
												+ " 条数据，共：" + processBean.total
												+ "条数据。" + val + "%");
							} else {
								_t.find(".progress-label").text(
										"当前处理" + processBean.currNumBegin
												+ " - "
												+ processBean.currNumEnd
												+ " 条数据，共：" + processBean.total
												+ "条数据。" + val + "%");
							}
							_t.find(".progerss-bar").progressbar({
								"value" : val
							});
						}
					},
					"error" : function(XMLHttpRequest, textStatus, errorThrown) {
						if (XMLHttpRequest.status == 404) {
							if (error) {
								error(404);
								return;
							}
						}
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
							// return;
						} else if (message == "notmodified")
							message = "服务端未更新。";
						else if (message == "parsererror")
							message = "解析服务端返回信息异常。";
						_t.data("exporting", false);
						_t.addClass("error");
						_t.find(".progress-label").html(message);
					}
				});
			}, 1000);
		}
		var $req = $.ajax({
			"async" : true,
			"dataType" : "json",
			"type" : "POST",
			"contentType" : "application/json; charset=UTF-8",
			"cache" : false,
			"url" : url,
			"timeout" : 3000,
			"data" : $.toJSON(data),
			"beforeSend" : function() {
			},
			"complete" : function() {
			},
			"success" : function(rtn) {
				showExport(rtn);
			},
			"error" : function(XMLHttpRequest, textStatus, errorThrown) {
				if (XMLHttpRequest.status == 404) {
					if (error) {
						error("404 NOT FOUND");
						return;
					}
				}
				var message = $.trim(XMLHttpRequest.responseText) || textStatus;
				if (!message) {
					if (XMLHttpRequest.status != 200) {
						message = "访问服务端异常，HTTP错误代码:" + XMLHttpRequest.status;
					} else {
						message = "访问服务端异常，错误原因:" + errorThrown.message;
					}
				} else if (message == "timeout")
					message = "连接服务器超时。";
				else if (message == "error") {
					message = "服务端处理异常。";
					// return;
				} else if (message == "notmodified")
					message = "服务端未更新。";
				else if (message == "parsererror")
					message = "解析服务端返回信息异常。";
				$.error(message);
			}
		});
	},
	"mask" : function(message) {
		_mask_index.push(layer.load(1, {
			shade: [0.3, "#000"],
			scrollbar: false,
			content: "<span class='layui-layer-loading-message'>" + (message || "正在加载...") + "</span>",
			}));
	},
	"unmask" : function() {
		layer.close(_mask_index.pop());
	},
	"message" : function(message, callback, note) {
		var html = [];
		html.push("<h3>" + message + "</h3>");
		if (note) {
			html.push("<div class='layui-layer-content-content'>" + note + "</div>");
		}
	    layer.alert(html.join(""), {
	    	icon: 0,
			scrollbar: false,
			zIndex : layer.zIndex,
			end: function() {
				if (callback) {
					callback();
				}
			}
		});
	},
	"warning" : function(message, callback, note) {
		var html = [];
		html.push("<h3>" + message + "</h3>");
		if (note) {
			html.push("<div class='layui-layer-content-content'>" + note + "</div>");
		}
	    layer.alert(html.join(""), {
			icon:0,
			title: "请注意",
			scrollbar: false,
			zIndex : layer.zIndex,
			end: function() {
				if (callback) {
					callback();
				}
			}
		});
	},
	"success" : function(message, callback, note) {
		var html = [];
		html.push("<h3>" + message + "</h3>");
		if (note) {
			html.push("<div class='layui-layer-content-content'>" + note + "</div>");
		}
	    layer.alert(html.join(""), {
			icon:1,
			title: "提示",
			scrollbar: false,
			zIndex : layer.zIndex,
			end: function() {
				if (callback) {
					callback();
				}
			}
		});
	},
	"error" : function(message, callback, note) {
		var html = [];
		html.push("<h3>" + message + "</h3>");
		if (note) {
			html.push("<div class='layui-layer-content-content'>" + note + "</div>");
		}
		$("body").blur();
		console.log(jQuery( ":focus" ));
	    layer.alert(html.join(""), {
			icon:2,
			title: "出错啦",
			scrollbar: false,
			zIndex : layer.zIndex,
			end: function() {
				if (callback) {
					callback();
				}
			}
		});
	},
	"confirm" : function(message, callback, note) {
		var html = [];
		html.push("<h3>" + message + "</h3>");
		if (note) {
			html.push("<div class='layui-layer-content-content'>" + note + "</div>");
		}
		layer.alert(html.join(""), {
			icon:3,
			title: "请选择...",
			scrollbar: false,
			zIndex : layer.zIndex,
			btn: ["确定", "取消"], 
			yes: function(index) {
				layer.close(index);
				if (callback) {
					callback(true);
				}
			},
			cancel: function(index) {
				if (callback) {
					callback();
				}
			}
		});
	},
	"inputbox" : function(message, inittext, callback, note) {
		var html = [];
		if (note) {
			html.push("<div class='layui-layer-input-tips'>" + note + "</div>");
		}
		html.push('<input type="text" class="layui-layer-input" value="' + inittext + '">');
		layer.alert(html.join(""), {
			title: message || "请输入...",
			scrollbar: false,
			zIndex : layer.zIndex,
			btn: ["确定", "取消"], 
			yes: function(index, layero) {
				var text = $(layero).find(".layui-layer-input").val();
				if (text == "") return;
				layer.close(index);
				if (callback) {
					callback(text);
				}
			},
			cancel: function(index) {
				if (callback) {
					callback();
				}
			}
		});
	},
	"tips": function(type, message, title) {
		$.bootstrapGrowl(message, {
            ele: 'body', // which element to append to
            type: type, // (null, 'info', 'danger', 'success', 'warning')
            offset: {
                from: 'top',
                amount: 10
            }, // 'top', or 'bottom'
            align: 'center', // ('left', 'right', or 'center')
            width: 300, // (integer, or 'auto')
            delay: 10000, // Time while the message will be displayed. It's not equivalent to the *demo* timeOut!
            allow_dismiss: true, // If true then will display a cross to close the popup.
            stackup_spacing: 10 // spacing between consecutively stacked growls.
        });
	},
	"winNotify": function(opts) {
		opts = $.extend(true, {
			title: "CooperOP",
			content: "您有一条新消息，请注意查看！",
			icon: cooperopcontextpath + "/theme/layout/img/logo-small.png",
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
        	if (!winNotify_error && !cloud_app.is_app && !$.is_mobile()) {
        		winNotify_error = true;
        		/*alert('你的浏览器不支持消息推送特性，请使用谷歌浏览器。');*/
        	}
	    }
	},
	"computeFileSize": function(size, level) {
		if (level == undefined) level = 0;
		var d = ["B", "KB", "MB", "GB", "TB"];
		if (size >= 1024) {
			size = size / 1024;
			level ++;
			return $.computeFileSize(size, level);
		}
		else {
			return $.formatnumber(size, "#,###.#") + d[level];
		}
	},
	"capitalize": function(str) {
		return str.replace(/\b\w+\b/g, function(word) {   
			return word.substring(0,1).toUpperCase( ) +  word.substring(1);   
		});   
	},
	"console": function() {
		if (window.console) {
			return window.console;
		} else {
			return {
				error: function() {
				},
				warning: function() {
				},
				info: function() {
				},
				log: function() {
				},
				debug: function() {
				},
				handle: function() {
				}
			}
		}
	},
	"logout": function() {
		$.call("application.auth.logout", {}, function(rtn) {
			if (rtn.emsg) {
				$.error(rtn.emsg);
			} else {
				location.href = cooperopcontextpath + rtn.redirect_url;
			}
		}, function(ems) {
			$.error(ems);
		});
	},
	"tasknum": function(callback) {
		$.call("application.task.tasknum", {}, function(rtn) {
			if (callback) {
				callback(rtn);
			}
		}, function(ems) {
			$.console().error(ems);
		}, {nomask: true});
	},
	"notificationnum": function(callback) {
		$.call("oa.notice.getMyNoticenum", {}, function(rtn) {
			if (callback) {
				callback(rtn);
			}
		}, function(ems) {
			$.console().error(ems);
		}, {nomask: true});
	},
	"emailnum": function(callback) {
		$.call("application.email.emailnum", {}, function(rtn) {
			if (callback) {
				callback(rtn);
			}
		}, function(ems) {
			$.console().error(ems);
		}, {nomask: true});
	},
	"messagenum": function(callback) {
		$.call("application.systemMessage.messagenum", {type : 5}, function(rtn) {
			if (callback && rtn) {
				callback(rtn.resultset[0].num);
			}
		}, function(ems) {
			$.console().error(ems);
		}, {nomask: true});
	},
	"messagesession": function(callback) {
		$.call("application.message.session", {start: 1, limit: 50}, function(rtn) {
			if (callback) {
				callback(rtn);
			}
		}, function(ems) {
			$.console().error(ems);
		}, {nomask: true});
	},
	"suggestionsnum": function(callback) {
		$.call("application.suggestions.suggestionsnum", {}, function(rtn) {
			if (callback) {
				callback(rtn);
			}
		}, function(ems) {
			$.console().error(ems);
		}, {nomask: true});
	},
	"contacters": function(opts) {
		opts = $.extend(true, {}, opts);
		var callback = opts.callback;
		delete opts.callback;
		$.call("application.contacter.contacters", opts, function(rtn) {
			if (callback) {
				callback(rtn);
			}
		}, function(ems) {
			$.console().error(ems);
		}, {nomask: true});
	}, 
	"print": function(action, data, target) {
		data = data || {};
		data["_page"] = action
		window.open(cooperopcontextpath + "/tools/print.jsp?" + excuteURLParams(data), target || "_blank");
	},
	"photos" : function (options) {  // images, current_index,
		var o = $.extend(false, {
			"title": null, // 相册标题
			"start": 0, // 初始显示的图片序号，默认0
        	"editable": false,
        	"deleteable": false,
			"save": undefined,
			"delete": undefined,
			"data": [{
				"file_id": null,
				"title": null,
				"description": null,
            	"src": null, // 原图地址
            	"srcObj": null, // 原图对象
            	"srcWidth": 0, // 原图宽度
            	"srcHeight": 0, // 原图高度
            	"rotate": 0, // 旋转角度
            	"thumb": null // 缩略图地址,
			}]	// 相册包含的图片，数组格式
		}, options);
		
		var btns = [{
			"id": "prev",
			"title": "上一张",
			"icon": "fa fa-arrow-left"
		},{
			"id": "zoomin",
			"title": "放大",
			"icon": "fa fa-search-plus"
		},{
			"id": "original",
			"title": "原始比例",
			"text": "1:1"
		},{
			"id": "optimum",
			"title": "最佳大小",
			"icon": "fa fa-tachometer"
		},{
			"id": "zoomout",
			"title": "缩小",
			"icon": "fa fa-search-minus"
		},{
			"id": "rotateleft90",
			"title": "向左旋转90度",
			"icon": "fa fa-rotate-left"
		},{
			"id": "rotateright90",
			"title": "向右旋转90度",
			"icon": "fa fa-rotate-right"
		},{
			"id": "save",
			"title": "保存旋转",
			"icon": "fa fa-save"
		},{
			"id": "trash",
			"title": "删除",
			"icon": "fa fa-trash-o"
		},{
			"id": "next",
			"title": "下一张",
			"icon": "fa fa-arrow-right"
		}];
		
		var events = {
			"active": function(ele, event) {
				var $$t = $(ele);
				_t.find(".thumbs .thumb").removeClass("active");
				$$t.addClass("active");
				_t.find(".viewer>.title").html(o.data[$$t.index()].title);
				if (o.data[$$t.index()].srcobj) {
					events["showimg"](ele, event);
				} else {
					var timg = new Image();
					timg.src = o.data[$$t.index()].thumb + "?t=" + new Date().getTime();
					$(timg).on("load", function() {
						var tw = timg.width, th = timg.height, ts = tw / th;
						var vw = _t.find(".viewer").width(), vh = _t.find(".viewer").height(), vs = vw / vh;
						var width = vw > tw ? tw : vw, height = vh > th ? th : vh;
						if (tw / vw > th / vh) {
							_t.find(".viewer>img").css({
								"width": width,
								"height": width /ts,
								"left": (vw - width) / 2,
								"top": (vh - width /ts) / 2
							});
						} else {
							_t.find(".viewer>img").css({
								"width": height * ts,
								"height": height,
								"top": (vh - height) / 2,
								"left": (vw - height * ts) / 2
							});
						}
						_t.find(".viewer>img").attr("src", timg.src);
						var img = new Image();
						img.src = o.data[$$t.index()].src + "?t=" + new Date().getTime();
						$(img).on("load", function() {
							o.data[$$t.index()].srcWidth = img.width;
							o.data[$$t.index()].srcHeight = img.height;
							o.data[$$t.index()].srcObj = img.src;
							events["showimg"](ele, event);
						});
					});
				}
			},
			"showimg": function(ele, event) {
				var $$t = _t.find(".thumbs .thumb.active");
				_t.find(".viewer>img").attr("src", o.data[$$t.index()].srcObj);
				events["optimum"](ele, event);
			},
			"zoomin": function(ele, event) {
				var $$t = _t.find(".thumbs .thumb.active");
				events["resizeimg"](_t.find(".viewer>img").width() * 1.1, _t.find(".viewer>img").height() * 1.1);
			},
			"original": function(ele, event) {
				var $$t = _t.find(".thumbs .thumb.active");
				events["resizeimg"](o.data[$$t.index()].srcWidth, o.data[$$t.index()].srcHeight);
			},
			"optimum": function(ele, event) {
				var $$t = _t.find(".thumbs .thumb.active");
				var tw = o.data[_t.find(".thumbs .thumb.active").index()].srcWidth, th = o.data[_t.find(".thumbs .thumb.active").index()].srcHeight, ts = tw / th;
				var vw = _t.find(".viewer").width(), vh = _t.find(".viewer").height(), vs = vw / vh;
				var rotate = (o.data[_t.find(".thumbs .thumb.active").index()].rotate || 0);
				events["resizeimg"]((rotate % 180 == 0) ? (vw > tw ? tw : vw) : (vh > tw ? tw : vh), (rotate % 180 == 0) ? (vh > th ? th : vh) : (vw > th ? th : vw));
			},
			"zoomout": function(ele, event) {
				var $$t = _t.find(".thumbs .thumb.active");
				events["resizeimg"](_t.find(".viewer>img").width() * 0.9, _t.find(".viewer>img").height() * 0.9);
			},
			"rotateleft90": function(ele, event) {
				var $$t = _t.find(".thumbs .thumb.active");
				events["resizeimg"](_t.find(".viewer>img").width(), _t.find(".viewer>img").height(), -90);
			},
			"rotateright90": function(ele, event) {
				var $$t = _t.find(".thumbs .thumb.active");
				events["resizeimg"](_t.find(".viewer>img").width(), _t.find(".viewer>img").height(), 90);
			},
			"save": function(ele, event) {
				var $$t = _t.find(".thumbs .thumb.active");
				var _index = _t.find(".thumbs .thumb.active").index();
				if (o.editable && o.save) {
					$.mask("正在保存图片...");
					o.save(o.data[_index], function(rtn) {
						$.unmask();
						if (rtn) {
							$.error(rtn);
							return;
						}
						var timg = new Image();
						timg.src = o.data[_index].thumb + "?t=" + new Date().getTime();
						$(timg).on("load", function() {
							_t.find(".thumbs .thumb:eq(" + _index + ")").css("background-image", "url(" + timg.src + ")")
						});
						var img = new Image();
						img.src = o.data[_index].src + "?t=" + new Date().getTime();
						$(img).on("load", function() {
							o.data[_index].srcWidth = img.width;
							o.data[_index].srcHeight = img.height;
							o.data[_index].srcObj = img.src;
							o.data[_index].rotate = 0;
							events["showimg"](ele, event);
						});
					});
				}
			},
			"trash": function(ele, event) {
				var $$t = _t.find(".thumbs .thumb.active");
				var _index = _t.find(".thumbs .thumb.active").index();
				if (o.editable && o.deleteable && o["delete"]) {
					o["delete"](o.data[_index], function(rtn) {
						if (rtn) {
							$.error(rtn);
							return;
						}
						o.data.splice(_index, 1);
						_t.find(".thumbs .thumb:eq(" + _index + ")").remove();
						if (_t.find(".thumbs .thumb").length > 0) {
							if (_t.find(".thumbs .thumb").length > _index) {
								events["active"](_t.find(".thumbs .thumb").get(_index), event);
							} else {
								events["active"](_t.find(".thumbs .thumb").get(_t.find(".thumbs .thumb").length - 1), event);
							}
						} else {
							events["close"](ele, event);
						}
					});
				}
			},
			"resizeimg": function(width, height, rotate) {
				var $$t = _t.find(".thumbs .thumb.active");
				rotate = (o.data[_t.find(".thumbs .thumb.active").index()].rotate || 0) + (+rotate || 0);
				o.data[_t.find(".thumbs .thumb.active").index()].rotate = rotate;
				
				var tw = o.data[_t.find(".thumbs .thumb.active").index()].srcWidth, th = o.data[_t.find(".thumbs .thumb.active").index()].srcHeight, ts = tw / th;
				var vw = _t.find(".viewer").width(), vh = _t.find(".viewer").height(), vs = vw / vh;
				var sw = 0, sh = 0, sl = 0, st = 0;
				
				if ((rotate % 180 == 0)) {
					if (tw / vw > th / vh) {
						sw = width;
						sh = width / ts;
					} else {
						sw = height * ts;
						sh = height;
					}
					st = vh > sh ? (vh - sh) / 2 : 0;
					sl = vw > sw ? (vw - sw) / 2 : 0;
				} else {
					if (tw / vh > th / vw) {
						sw = width;
						sh = width / ts;
					} else {
						sw = height * ts;
						sh = height;
					}
					if (sh >= sw) {
						if (vh >= sh) {
							st = (vh - sh) / 2;
						} else if (vh >= sw) {
							st = (vh - sh) / 2;
						} else {
							st = 0;
						}
					} else {
						if (vh >= sw) {
							st = (vh - sh) / 2;
						} else if (vh >= sh) {
							st = (sw - sh) / 2;
						} else {
							st = (sw - sh) / 2;
						}
					}
					if (sw >= sh) {
						if (vw >= sw) {
							sl = (vw - sw) / 2;
						} else if (vw >= sh) {
							sl = (vw - sw) / 2;
						} else {
							sl = 0;
						}
					} else {
						if (vw >= sh) {
							sl = (vw - sw) / 2;
						} else if (vw >= sw) {
							sl = (sh - sw) / 2;
						} else {
							sl = (sh - sw) / 2;
						}
					}
				}
				_t.find(".viewer>img").css({
					"width": sw,
					"height": sh,
					"left": sl,
					"top": st,
					"transform": "rotate(" + rotate + "deg)",
					"-ms-transform": "rotate(" + rotate + "deg)",//  IE 9 
					"-moz-transform": "rotate(" + rotate + "deg)",//  Firefox  
					"-webkit-transform": "rotate(" + rotate + "deg)",//  Safari 和 Chrome  
					"-webkit-transform": "rotate(" + rotate + "deg)"
				});
				if ((rotate % 180 == 0)) {
					if (vw < sw) {
						_t.find(".viewer").scrollLeft((sw - vw) / 2);
					}
					if (vh < sh) {
						_t.find(".viewer").scrollTop((sh - vh) / 2);
					}
				} else {
					if (vw < sh) {
						_t.find(".viewer").scrollLeft((sh - vw) / 2);
					}
					if (vh < sw) {
						_t.find(".viewer").scrollTop((sw - vh) / 2);
					}
				}
			},
			"prev": function(ele, event) {
				var $$t = $(ele);
				_t.find(".thumbs .thumb.active").prev().click();
			},
			"next": function(ele, event) {
				var $$t = $(ele);
				_t.find(".thumbs .thumb.active").next().click();
			},
			"close": function(ele, event) {
				_t.remove();
			}
		};
		
		var html = [];
		html.push('<div class="cphotoviewer" style="" oncontextmenu="return false;" onselectstart="return false">');
		html.push('<style>');
		html.push('.cphotoviewer{display: ; position: fixed; background: rgba(0, 0, 0, 0.8); top: 0px; left: 0px; bottom: 0px; right: 0px; z-index: 9999999999;}');
		html.push('.cphotoviewer>.viewer{position: absolute; top: 0px; left: 0px; bottom: 138px; right: 0px; overflow: hidden;margin:0 auto;}');
		html.push('.cphotoviewer>.viewer>.title{    position: fixed;top: 0px;left: 0px;z-index: 3;padding: 10px;text-align: left;font-size: 18px;font-weight: 600;color: #eeeeee;background: rgba(0, 0, 0, 0.3);}');
		html.push('.cphotoviewer>.viewer>img{display: block; position:absolute;cursor:default;}');
		html.push('.cphotoviewer>.viewer>.toolbar-wrap{pointer-events: none;position: fixed; bottom: 136px; left: 0px; right: 0px; height: 40px; text-align: center;}');
		html.push('.cphotoviewer>.viewer>.toolbar-wrap>.toolbar{pointer-events: auto;display: inline-block; height: 40px; margin: 0 auto; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2); border-radius: 5px 5px 0px 0px !important; background: rgba(0, 0, 0, 0.4);}');
		html.push('.cphotoviewer>.viewer>.toolbar-wrap>.toolbar>.toolbar-btn{display: block; width: 38px; height: 38px; line-height: 36px; text-align: center; color: #AAA; float: left;font-size: 16px; font-weight: bold;}');
		html.push('.cphotoviewer>.viewer>.toolbar-wrap>.toolbar>.toolbar-btn:hover{color: #FFF;}');
		html.push('.cphotoviewer>.viewer>.toolbar-wrap>.toolbar>.toolbar-btn>i{font-size: 20px; line-height: 36px;font-weight:normal;}');
		html.push('.cphotoviewer>.thumbs{position: absolute; height: 136px; left: 0px; right: 0px; bottom: 0px; background: rgba(0, 0, 0, 0.3); box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2); overflow: hidden;}');
		html.push('.cphotoviewer>.thumbs>.thumb{display: block; transition: all 0.3s;float: left; height: 128px; width: 128px; border: 2px solid #3c3a3a; margin: 4px 0px 0px 5px; background-color: rgba(0, 0, 0, 0.4); background-size: contain; background-position: center center; background-repeat: no-repeat;}');
		html.push('.cphotoviewer>.thumbs>.thumb.active{border-color: #ce691d;transition: all 0.3s;}');
		html.push('.cphotoviewer>.close-btn{font-size: 24px; color: #AAA; position: absolute; right: 0px; top: 0px; width: 32px; height: 32px; text-align: center; line-height: 30px;}');
		html.push('.cphotoviewer>.close-btn:hover{color: #FFF;}');
		html.push('.cphotoviewer>.close-btn>i{font-size: 24px; text-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);}');
		html.push('</style>');
		html.push('	<div class="viewer">');
		html.push('		<div class="title"></div>');
		html.push('		<img src="" ondragstart="return false;">');
		html.push('		<div class="toolbar-wrap">');
		html.push('			<div class="toolbar">');
		for (var i in btns) {
			if (!o.editable) {
				if (btns[i].id == "save" || btns[i].id == "trash") continue;
			}
			if (!o.deleteable) {
				if (btns[i].id == "trash") continue;
			}
			html.push('				<a href="javascript:void(0)" class="toolbar-btn" pid="' + btns[i].id + '" title="' + btns[i].title + '">');
			if (btns[i]["icon"])
				html.push('					<i class="' + btns[i]["icon"] + '"></i>');
			if (btns[i]["text"])
				html.push('					' + btns[i]["text"]);
			html.push('				</a>');
		}
		html.push('			</div>');
		html.push('		</div>');
		html.push('	</div>');
		html.push('	<div class="thumbs">');
		for (var i in o.data) {
			html.push('		<a href="javascript:void(0)" class="thumb" style=" background-image: url(' + o.data[i].thumb + "?t=" + new Date().getTime() + ');"></a>');
		}
		html.push('	</div>');
		html.push('	<a href="javascript:void(0)" class="close-btn"><i class="fa fa-times"></i></a>');
		html.push('</div>');
		
		var topwindow = null;
		var topwindow = window;
		while (topwindow.parent != topwindow) {
			topwindow = topwindow.parent;
		}
		
		var _t = $(html.join(""));
		
		_t.find(".thumbs").on("click", '.thumb', function(event) {
			events["active"](this, event);
		});

		_t.on("click", '.close-btn', function(event) {
			events["close"](this, event);
		});
		
		_t.find(".thumbs .thumb").get(o.start).click();
		
		_t.find(".toolbar").on("click", '.toolbar-btn', function(event) {
			var $this = $(this);
			if ($this.is("[pid]") && $this.attr("pid") && events[$this.attr("pid")]) {
				events[$this.attr("pid")](this, event);
			}
		});
		
		var _mv = false, _mt=0, _ml=0, _mx=0, _my=0;
		_t.find(".viewer>img").on("mousedown", function(event) {
			_mv = true;
			_t.find(".viewer>img").css("cursor", "move");
			_mt = _t.find(".viewer>img").position().top;
			_ml =_t.find(".viewer>img").position().left;
			_mx = event.clientX;
			_my = event.clientY;
		});
		
		_t.find(".viewer>img").on("mousemove", function(event) {
			if (_mv) {
				if (_my && _mx) {
					_mt += event.clientY - _my;
					_ml += event.clientX - _mx;
					_t.find(".viewer>img").css("top", _mt);
					_t.find(".viewer>img").css("left", _ml);
				}
				_mx = event.clientX;
				_my = event.clientY;
			}
		});
		
		_t.find(".viewer>img").on("mouseup", function(event) {
			if (_mv) {
				if (_my && _mx) {
					_mt += event.clientY - _my;
					_ml += event.clientX - _mx;
					_t.find(".viewer>img").css("top", _mt);
					_t.find(".viewer>img").css("left", _ml);
				}
				_mx = event.clientX;
				_my = event.clientY;
			}
			_mt = 0;
			_ml = 0;
			_mx = 0;
			_my = 0;
			_mv = false;
			_t.find(".viewer>img").css("cursor", "default");
		});
		
		
		//滚轮缩放
		var _stop = $(topwindow).scrollTop();
		var _ssb = false;
		_t.on("mousewheel", function(event) {
			if (_ssb) {
				_ssb = false;
				return false;
			}
			if (event.originalEvent.deltaY > 0) {  //下，缩小
				events["zoomout"](this, event);
			} else if (event.originalEvent.deltaY < 0) { //上，放大
				events["zoomin"](this, event);
			}
			$(this).scrollTop(_stop);
			_ssb = true;
			return false;
		});
		
		topwindow.$("body").append(_t);
	},
	"initChart": function(ch, pageid, data){
		var qdata = $(document).getData();
		if(ch){
			qdata.chart_flag = ch;
		}
		if(pageid){
			qdata.pageid = pageid;
		}

		$.call("application.chart.query", qdata, function(rtn) {
			if(rtn.charts){
				var colors = ['#7cb5ec', '#434348', '#90ed7d', '#f7a35c', '#8085e9',
					'#f15c80', '#e4d354', '#2b908f', '#f45b5b', '#91e8e1'];
				for(var k=0; k<rtn.charts.length; k++){
					var chart = rtn.charts[k];
					if(chart.color){
						if(chart.color == '2'){
							colors = ['#f45b5b', '#e4d354', '#90ed7d', '#f7a35c', '#8085e9',
								'#f15c80', '#7cb5ec', '#2b908f', '#91e8e1', '#434348']
						}else if(chart.color == '3'){
							colors = ['#f45b5b', '#f7a35c', '#e4d354', '#90ed7d', '#2b908f',
								'#7cb5ec', '#8085e9', '#f15c80', '#91e8e1', '#434348'];
						}
					}
					if(chart.chart_type=='2'){
						if(chart.countMap){
							var title = chart.title;
							var span_head = chart.span_head;
							var span_content = chart.span_content;
							var span_footer = chart.span_footer;
							var countMap = chart.countMap;
							for (var k in countMap) {
								countMap[k];
								title = title.replace(':'+k, countMap[k]);
								span_head = span_head.replace(':'+k, countMap[k]);
								span_content = span_content.replace(':'+k, countMap[k]);
								span_footer = span_footer.replace(':'+k, countMap[k]);
							}
							if(chart.subtitle_href){
								title = title + ' <a sytle="color: #4497de;" href="javascript: chartModal(\''+chart.subtitle_href+'\');">'+chart.subtitle+'</a>';
							}
							$(".chart-span[flag='"+ chart.flag +"']").find(".chart-div .chart-title").html(title);
							$(".chart-span[flag='"+ chart.flag +"']").find(".chart-div .cspan .cspan-head").html(span_head);
							$(".chart-span[flag='"+ chart.flag +"']").find(".chart-div .cspan .cspan-content").html(span_content);
							$(".chart-span[flag='"+ chart.flag +"']").find(".chart-div .cspan .cspan-footer").html(span_footer);
						}
						if(chart.autorefresh == 'Y'){

							setInterval(function () {
								var point = hchart.series[0].points[0];
								var url = "/w/application/chart/queryPlotNum.json";
								if('undefined' !=typeof(cooperopcontextpath)){
									url = cooperopcontextpath + url;
								}
								$.ajax( {
									"async": true,
									"dataType" : "json",
									"type" : "POST",
									"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
									"cache" : false,
									"url" : url,
									"timeout" : 60000,
									"data" : excuteURLParams(qdata),
									"beforeSend" : function() {
									},
									"complete" : function() {
									},
									"success" : function(result){
										for (var k in result) {
											result[k];
											title = title.replace(':'+k, result[k]);
											span_head = span_head.replace(':'+k, result[k]);
											span_content = span_content.replace(':'+k, result[k]);
											span_footer = span_footer.replace(':'+k, result[k]);
										}
										$(".chart-span[flag='"+ chart.flag +"']").find(".chart-div .chart-title").html(title);
										$(".chart-span[flag='"+ chart.flag +"']").find(".chart-div .cspan .cspan-head").html(span_head);
										$(".chart-span[flag='"+ chart.flag +"']").find(".chart-div .cspan .cspan-content").html(span_content);
										$(".chart-span[flag='"+ chart.flag +"']").find(".chart-div .cspan .cspan-footer").html(span_footer);
									},
									"error" : function(XMLHttpRequest, textStatus, errorThrown) {}
								});
							}, chart.refresh_time*1000 || 60000);

						}
						continue;
					}
					var xAxis = {};
					var yAxis = [];
					var series = [];
					var chaa = {};
					var pae = {};
					var title = chart.title;
					if(title.indexOf(":xaxis") > -1){
						title = title.replace(":xaxis", $("input[name='xaxis']").val());
					}
					var subtitle;
					var plotOptions={};
					if(chart.chart_type == '3'){
						chaa.type = 'gauge';
						pae = {
							size: '100%',
							startAngle: -150,
							endAngle: 150,
							background: [ {
								// default background
								backgroundColor: '#f9f9f9'
							}, {
								backgroundColor: '#DDD',
								borderWidth: 0,
								outerRadius: '105%',
								innerRadius: '103%'
							}]
						};
						var yd = {
							min: +chart.min_num,
							max: +chart.max_num,
							minorTickInterval: 'auto',
							minorTickWidth: 1,
							minorTickLength: 10,
							minorTickPosition: 'inside',
							minorTickColor: '#666',
							tickPixelInterval: 30,
							tickWidth: 2,
							tickPosition: 'inside',
							tickLength: 15,
							tickColor: '#666',
							labels: {
								step: 2,
								rotation: 'auto'
							},
							title: {
								text: chart.num_unit,
								y: 10
							}
						};

						yd.plotBands = [];
						var ps = chart.plots;
						for(var a=0; a < ps.length; a++){
							var plot = ps[a];
							yd.plotBands.push({
								innerRadius: '88%',
								from: +plot.from_num*chart.max_num/100,
								to: +plot.to_num*chart.max_num/100,
								color: plot.color // red
							});
						}
						yAxis.push(yd);
						series = [{
							name: chart.title,
							data: [chart.default_num-0],
							tooltip: {
								valueSuffix: chart.num_unit
							}
						}];
					}else if(chart.chart_type=='4'){
						chaa =  {
							type: 'scatter',
							zoomType: 'xy'
						};
						xAxis = {
							title: {
								enabled: true,
								text: chart.x_name
							},
							plotLines: [{
								color: 'black',
								dashStyle: 'dot',
								width: 2,
								value: chart.plotline_xvalue,
								label: {
									rotation: 90,
									style: {
										fontStyle: 'italic'
									},
									text: chart.plotline_xname
								},
								zIndex: 3
							}]
						};
						yAxis.push({
							title: {
								text: chart.y_name
							},
							gridLineWidth:0,
							plotLines: [{
								color: 'black',
								dashStyle: 'dot',
								width: 2,
								value: chart.plotline_yvalue,
								label: {
									align: 'right',
									style: {
										fontStyle: 'italic'
									},
									text: chart.plotline_yname,
									x: -10
								},
								zIndex: 3
							}]
						});
						plotOptions = {
							scatter: {
								tooltip: {
									headerFormat: '<b>{series.name}</b><br>',
									pointFormat: chart.x_name+': {point.x} <br> '+chart.y_name+': {point.y}'
								}
							}
						}
						var ys = chart.yaxis;
						var xs = chart.xaxis;
						var temp = {};
						for(var a=0; a < ys.length; a++){
							var y = ys[a];
							if(temp[y[chart.g_name]]){
								temp[y[chart.g_name]].data.push([+y['x'],+y['y']]);
							}else{
								temp[y[chart.g_name]] = {
									name: y[chart.g_name],
									data: [[+y['x'],+y['y']]]
								};
							}
						}
						for(var i in temp){
							series.push(temp[i]);
						}
						/*for(var i=0; i < xs.length; i++){
							var xx = xs[i][chart.g_name];
							var dd = {name: xx};
							dd.data = [];
							for(var a=0; a < ys.length; a++){
								var y = ys[a];
								if(y[chart.g_name] == xx){
									data.push([y['x'],y['y']])
								}
							}
							series.push(dd);
						}*/
					}else{
						if(chart.subtitle_href){
							title = title + ' <a sytle="color: #4497de;" href="javascript: chartModal(\''+chart.subtitle_href+'\');">'+chart.subtitle+'</a>';
						}else{
							subtitle =  ' <a sytle="color: #4497de;" href="javascript: chartModal(\''+chart.subtitle_href+'\');">'+chart.subtitle+'</a>';
						}
						yAxis = [];
						xAxis.categories = [];
						var xs = chart.xaxis;
						var ys = chart.yaxis;
						plotOptions = {
							series: {
								dataLabels: {
									enabled: true
								}
							}
						};
						for(var i=0; i < xs.length; i++){
							var x = xs[i][chart.g_name];
							xAxis.categories.push(x);
						}
						for(var a=0; a < ys.length; a++){
							var y = ys[a];
							if(y.chart_type == 'spider'){
								chaa = {polar: true, type : "line"};
								pae = {
									size: '80%'
								};
							}else{
								chaa = {type : y.chart_type};
							}
							if(y.noyaxis == '0'){
								var d = {};
								d.labels = {
									style: {
										color: Highcharts.getOptions().colors[a]
									}
								};
								if(y.yaxis_suffix){
									d.labels.format = '{value}'+ y.yaxis_suffix;
								}else{
									d.labels.format = '{value}';
								}
								d.title = {
									text: false,
									style: {
										color: Highcharts.getOptions().colors[a]
									}
								};
								if(y.yaxis_align == 'right'){
									d.opposite = true
								}
								yAxis.push(d);
							}
							if(chart.group_field){
								for(var bb=0; bb<y.groupf.length; bb++){
									var g = y.groupf[bb];
									var s = {type: y.chart_type, name: g.g_field};
									if(y.chart_type == 'spider'){
										delete s.type;
										yAxis.push({
											gridLineInterpolation: 'polygon',
											lineWidth: 0
										});
										xAxis.tickmarkPlacement = 'on';
										xAxis.lineWidth = 0;
									}
									s.data = [];
									for(var i=0; i < xs.length; i++){
										var x = xs[i][chart.g_name];
										var poi = {name: x};
										if(chart.drill_chart){
											poi.drilldown = true;
										}
										var va = 0;
										for(var b=0; b < g.series.length; b++){
											var ss = g.series[b];
											if(ss[chart.g_name] == x){
												/*if(s.type == 'pie'){
													s.data.push({name: x ,y: ss[y.fieldname]-0});
												}else{
													s.data.push(ss[y.fieldname]-0);
												}*/
												va = ss[y.fieldname]-0;
											}
										}
										poi.y = va;
										s.data.push(poi);
									}
									series.push(s);
								}
							}else{
								var s = {type: y.chart_type, name: y.fieldname_ch};

								if(y.chart_type == 'spider'){
									delete s.type;
									yAxis.push({
										gridLineInterpolation: 'polygon',
										lineWidth: 0
									});
									xAxis.tickmarkPlacement = 'on';
									xAxis.lineWidth = 0;
								}
								s.data = [];
								if(y.chart_type == 'pie1'){
									s.innerSize='60%';
									s.type='pie';
								}
								for(var i=0; i < xs.length; i++){
									var x = xs[i][chart.g_name];
									var va = 0;
									var poi = {name: x };
									if(chart.drill_chart){
										poi.drilldown = true;
									}
									var sdata = [];
									for(var b=0; b < y.series.length; b++){
										var ss = y.series[b];
										if(ss[chart.g_name] == x){
											/*var poi;
											if(s.type == 'pie'){
												poi = {name: x ,y:ss[y.fieldname]-0};
											}else{
												poi = {y:ss[y.fieldname]-0};
											}
											s.data.push(poi);*/
											//xAxis.categories.push(x);
											if(y.chart_type == 'columnrange'){
												sdata.push(ss[y.fieldname+'_min']-0);
												sdata.push(ss[y.fieldname+'_max']-0);
											}else{
												va = ss[y.fieldname]-0;
											}
										}
									}
									poi.y = va;
									if(y.stack){
										poi.stack = y.stack;
									}
									if(y.chart_type == 'columnrange'){
										s.data.push(sdata);
									}else{
										s.data.push(poi);
									}
								}
								series.push(s);
							}
						}
						if(chart.drill_chart){
							var u = chart.drill_chart.replace(/\./g, "/");
							var url = cooperopcontextpath + "/w/" + u+".html";
							var drill_title = title;
							if(chart.drill_chart_name){
								drill_title = chart.drill_chart_name;
							}
							chaa.events = {
								drilldown: function (e) {
									if(!e.originalEvent.defaultPrevented){
										var tit = drill_title;
										if(tit.indexOf(":xaxis") > -1){
											tit = tit.replace(":xaxis", e.point.name);
										}
										if(chart.drill_tabopen == '1'){
											$.openTabPage(chart.drill_chart, tit, chart.drill_chart, true, {xaxis : e.point.name, product_code: "xdesigner"});
										}else{
											$.modal(url, tit, $.extend(true, {
												width: '100%',
												height: '100%',
												xaxis : e.point.name,
												callback : function(rtn) {
													if (rtn) {

													}
												}
											},$("[ctype='form']").getData()));
										}
										e.originalEvent.preventDefault();
									}
								}
							}
						}
					}

					if(chaa.type == 'pie' || chaa.type == 'pie1'){
						if(chaa.type == 'pie1'){
							chaa.type = 'pie';
						}
						plotOptions = {
							series: {
								dataLabels: {
									enabled: true,
									format: '{point.name}: {point.percentage:.1f}%'
								}
							}
						};
					}
					if(chart.stacking == 'Y'){
						plotOptions.column = {
							stacking: 'normal'
						}
					}
					var initdata = {
						chart: chaa,
						colors: colors,
						pane: pae,
						title: {
							useHTML : true,
							text: title
						},
						/* subtitle: {
                             text: subtitle,
                             align: 'right'
                         },*/
						plotOptions: plotOptions,
						tooltip: {
							shared: true
						},
						xAxis: xAxis,
						// yAxis: yAxis,
						series: series
					};
					if(yAxis.length > 0){
						initdata.yAxis=yAxis;
					}
					initdata.exporting = {
						enabled: false
					};
					$(".chart_container[flag='"+ chart.flag +"']").highcharts(initdata,
						// Add some life
						function (hchart) {
							if (!hchart.renderer.forExport && chart.autorefresh == 'Y') {
								setInterval(function () {
									var point = hchart.series[0].points[0];
									/*inc = Math.round((Math.random() - 0.5) * 20);
                                    newVal = point.y + inc;
                                    if (newVal < 0 || newVal > 200) {
                                        newVal = point.y - inc;
                                    }*/
									/*$.call("application.chart.queryPlotNum", qdata, function(result){
                                        if(result){
                                            point.update(result.plot-0);
                                        }
                                    });*/
									var url = "/w/application/chart/queryPlotNum.json";
									if('undefined' !=typeof(cooperopcontextpath)){
										url = cooperopcontextpath + url;
									}
									$.ajax( {
										"async": true,
										"dataType" : "json",
										"type" : "POST",
										"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
										"cache" : false,
										"url" : url,
										"timeout" : 60000,
										"data" : excuteURLParams(qdata),
										"beforeSend" : function() {
										},
										"complete" : function() {
										},
										"success" : function(result){
											if(result){
												point.update(result.plot-0);
											}
										},
										"error" : function(XMLHttpRequest, textStatus, errorThrown) {}
									});
								}, chart.refresh_time*1000 || 60000);
							}
						});
				}
			}
		});
	},
	"updatechart": function(flag){
		var chart = $(".chart_container[flag='"+ flag +"']").parent();
		chart.get("");
	},
	"xuanzhuan": function(flag){
		var ch = $(".chart_container[flag='"+ flag +"']").parent();
		var clientHeight = $(window).height();
		var clientWidth = $(window).width();
		if(ch.attr("h_xuanz") == 'Y'){
			ch.css({
				"transform": "rotate(0deg)",
				"-ms-transform": "rotate(0deg)",
				"-moz-transform": "rotate(0deg)",
				"-webkit-transform": "rotate(0deg)",
				"-o-transform": "rotate(0deg)"
					});
			ch.attr("h_xuanz", 'N');
			clientWidth = ch.attr("old_w");
			clientHeight = ch.attr("old_h");
			alert(ch.width());
		}else{
			ch.css({
				"transform": "rotate(90deg)",
				"-ms-transform": "rotate(90deg)",
				"-moz-transform": "rotate(90deg)",
				"-webkit-transform": "rotate(90deg)",
				"-o-transform": "rotate(90deg)"
			});
			ch.attr("h_xuanz", 'Y');
			ch.attr("old_h", ch.height());
			ch.attr("old_w", ch.width());
		}
		ch.width(clientWidth);
		ch.height(clientHeight);
	},
	"loginfinger": function(option){
		var callback;
		var url;
		if(option){
			callback = option.callback;
			url = option.url;
		}
		$.ajax({
			"async": false,
			"dataType" : "json",
			"type" : "GET",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"cache" : false,
			"url" : "http://127.0.0.1:22001/zkbioonline/fingerprint/beginCapture?type=2",
			"timeout" : "60000",
			"data" : {},
			"success": function(r){
				if(r.ret == 0){//系统正常
					//系统正常
					$.ajax({
						"async": false,
						"dataType" : "json",
						"type" : "GET",
						"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
						"cache" : false,
						"url" : "http://127.0.0.1:22001/zkbioonline/info",
						"timeout" : "60000",
						"data" : {},
						"success" : function(dd) {
							if(dd.ret == 0){
								//var enroll_count = 3;
								var tim = setInterval(function(){
									var ddd = $.ajax({
										"async": false,
										"dataType" : "json",
										"type" : "GET",
										"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
										"cache" : false,
										"url" : "http://127.0.0.1:22001/zkbioonline/fingerprint/getImage",
										"timeout" : "60000",
										"data" : {}
									});
									if(ddd.responseJSON.ret == 0){
										clearInterval(tim);
										var ddd = $.ajax({
											"async": false,
											"dataType" : "json",
											"type" : "GET",
											"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
											"cache" : false,
											"url" : "http://127.0.0.1:22001/zkbioonline/fingerprint/getTemplate",
											"timeout" : "60000",
											"data" : {},
											"success": function(rimage){
												if(rimage.ret == 0){
													$.call(url || "application.user.loginFinger", {
														finger_image: rimage.data.template
													}, callback || function(lrtn) {
														if(lrtn.redirect_url){
															location.href = cooperopcontextpath + lrtn.redirect_url;
														}
													});
												}
											}
										});
									}
								}, 1000);
								
							}
						}
					});
					
				}
			}
		});
	},
	"lookfinger": function(option){
		var callback;
		var errback;
		var url;
		if(option){
			callback = option.callback;
			errback = option.errback;
			url = option.url;
		}
		$.ajax({
			"async": false,
			"dataType" : "json",
			"type" : "GET",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"cache" : false,
			"url" : "http://127.0.0.1:22001/zkbioonline/fingerprint/beginCapture?type=2",
			"timeout" : "60000",
			"data" : {},
			"success": function(r){
				if(r.ret == 0){//系统正常
					//系统正常
					$.ajax({
						"async": false,
						"dataType" : "json",
						"type" : "GET",
						"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
						"cache" : false,
						"url" : "http://127.0.0.1:22001/zkbioonline/info",
						"timeout" : "60000",
						"data" : {},
						"success" : function(dd) {
							if(dd.ret == 0){
								//var enroll_count = 3;
								var tim = setInterval(function(){
									var ddd = $.ajax({
										"async": false,
										"dataType" : "json",
										"type" : "GET",
										"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
										"cache" : false,
										"url" : "http://127.0.0.1:22001/zkbioonline/fingerprint/getImage",
										"timeout" : "60000",
										"data" : {}
									});
									if(ddd.responseJSON.ret == 0){
										clearInterval(tim);
										var ddd = $.ajax({
											"async": false,
											"dataType" : "json",
											"type" : "GET",
											"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
											"cache" : false,
											"url" : "http://127.0.0.1:22001/zkbioonline/fingerprint/getTemplate",
											"timeout" : "60000",
											"data" : {},
											"success": function(rimage){
												if(rimage.ret == 0){
													$.call(url || "application.user.getInfoByFinger", {
														sn: r.data['sn'],
														finger_image: rimage.data.template
													}, callback || function(lrtn) {
														if(lrtn.system_user_id){
															$.message("验证成功！");
														}else{
															$.message("验证失败！");
														}
													},errback, option);
												}
											}
										});
									}
								}, 1000);
								
							}
						}
					});
					
				}
			}
		});
	},
	"checkfingerFun" : function(system_user_id, option){
		var html = [];
		html.push("<div class='form-horizontal' style='display:none'>");
		html.push("<div class='row-fluid finger-div' >");
				html.push("<div style='display:block;text-align:center'>");
				html.push("<img class='finger-img' src='"+cooperopcontextpath+"/theme/layout/img/finger-4.png' width='100px;'/>");
				html.push("</div>");
		html.push("</div>");
		html.push("<div class='row-fluid'>");
		html.push("<div style='display:block;text-align:center'>");
		html.push("<p style='color: red;font-size:16px;'>请按下您已经注册指纹的手指！</p>");
		html.push("</div>");
		html.push("</div>");
		html.push("</div>");
		var _t = $(html.join(""));
		$("body").append(_t);
		_t.ccinit();
		var o = {
		};
		o.type = 1;
		o.content = _t;
		o.title = false;
		o.area = [o.width || "22%", o.height || "30%"];
		o.zIndex = layer.zIndex;
		o.cancel = function(index) {
			layer.close(index);
			_t.remove();
	    }
		var laindex = layer.open(o);
		return $.checkfinger(system_user_id, $.extend(true, option, {
			initimage: function(rrr){
				if(rrr.ret == 0){
					layer.close(laindex);
				}
			}
		}));
		
	},
	"getfingernums": function(system_user_id ,callback){
		$.call("application.user.getFinger", {
			system_user_id: system_user_id
		} ,callback);
	},
	"deletefingers": function(system_user_id ,callback){
		$.call("application.user.deleteFingers", {
			system_user_id: system_user_id
		}, callback);
	},
	"checkfinger": function(system_user_id, option){
		var fla = false;
		var callback;
		var url;
		var sn;
		if(option){
			callback = option.callback;
			url = option.url;
			initimage = option.initimage;
		}
		$.ajax({
			"async": false,
			"dataType" : "json",
			"type" : "GET",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"cache" : false,
			"url" : "http://127.0.0.1:22001/zkbioonline/fingerprint/beginCapture?type=2&FakeFunOn=0",
			"timeout" : "60000",
			"data" : {},
			"success": function(r){
				if(r.ret == 0){//系统正常
					//系统正常
					$.ajax({
						"async": false,
						"dataType" : "json",
						"type" : "GET",
						"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
						"cache" : false,
						"url" : "http://127.0.0.1:22001/zkbioonline/info",
						"timeout" : "60000",
						"data" : {},
						"success" : function(dd) {
							if(dd.ret == 0){
								//var enroll_count = 3;
								var tim = setInterval(function(){
									var ddd = $.ajax({
										"async": false,
										"dataType" : "json",
										"type" : "GET",
										"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
										"cache" : false,
										"url" : "http://127.0.0.1:22001/zkbioonline/fingerprint/getImage",
										"timeout" : "60000",
										"data" : {},
										"success": initimage|| function(rinit){
											
										}
									});
									if(ddd.responseJSON.ret == 0){
										clearInterval(tim);
										var ddd = $.ajax({
											"async": false,
											"dataType" : "json",
											"type" : "GET",
											"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
											"cache" : false,
											"url" : "http://127.0.0.1:22001/zkbioonline/fingerprint/getTemplate",
											"timeout" : "60000",
											"data" : {},
											"success": function(rimage){
												if(rimage.ret == 0){
													$.call(url || "application.user.checkFinger", {
														system_user_id: system_user_id,
														sn: r.data['sn'],
														finger_image: rimage.data.template
													}, callback || function(rtn) {
														if (rtn.flag == true) {
															fla = true;
															$.message("验证成功！");
															return r.data['sn'];
														}else{
															$.message("验证失败！");
														}
													});
												}
											}
										});
									}
								}, 1000);
								
							}
						}
					});
					
				}
			}
		});
		return fla
	},
/**
 * @param system_user_id 登记指纹的人员id
 */
	"regfingerFun" : function (system_user_id){
		$.call("application.user.getFinger", {system_user_id: system_user_id}, function(rtn){
			if(rtn){
				var html = [];
				html.push("<div class='form-horizontal' style='display:none'>");
				html.push("<div class='row-fluid'>");
				if(rtn.fingers){
					for(var fi=0; fi<rtn.fingers.length; fi++){
						html.push("<div class='cols1'>");
						html.push("<img class='finger-img' src='"+cooperopcontextpath+"/theme/layout/img/finger-4.png' data-id='"+rtn.fingers[fi].finger_image+"' width='100px;'/>");
						html.push("</div>");
					}
				}
				html.push("<div class='cols1 add-f-div'>");
				html.push("<div class='finger-plus'>");
				html.push("<i class='fa fa-plus add-btn' title='添加'></i>");
				html.push("</div>");
				html.push("</div>");
				html.push("</div>");
				html.push("<div class='row-fluid'>");
				html.push("<div class='cols1'>");
				html.push("</div>");
				html.push("<div class='cols2 nolabel'>");
				html.push("<p style='color: red;font-size:16px;'>每注册一个指纹，都需要登记三次该手指的纹路！</p>");
				html.push("</div>");
				html.push("</div>");
				html.push("</div>");
				var _t = $(html.join(""));
				_t.find(".finger-plus").on("click",function(){
					var fhtml=[];
					
					fhtml.push("<div class='cols1'>");
					fhtml.push("<img class='finger-img' src='"+cooperopcontextpath+"/theme/layout/img/finger-1.png' width='100px;'/>");
					fhtml.push("</div>");
					_t.find(".add-f-div").before(fhtml.join(""));
					$.regfinger(system_user_id, {
						initimage: function(rrr){
							console.log(rrr);
							if(rrr.ret == 0){
								var ind = rrr.data.enroll_index-0+1;
								_t.find(".finger-img:last").attr("src", cooperopcontextpath+"/theme/layout/img/finger-"+ind+".png");
							}
						},
						callback: function(rf){
							if (rf) {
								_t.find(".finger-img:last").attr("data-id", rf.finger_image);
							}
					}});
				});
				$("body").append(_t);
				_t.ccinit();
				var o = {
						/*btn: ['上传完成']
						,btn1: function(index){ //或者使用btn2
							layer.close(index);
							_t.remove();
						} */
				};
				o.type = 1;
				o.content = _t;
				o.title = false;
				o.area = [o.width || "60%", o.height || "60%"];
				o.zIndex = layer.zIndex;
				o.cancel = function(index) {
					layer.close(index);
					_t.remove();
			    }
				layer.open(o);
			}
		});
	},
	"regfinger": function(system_user_id, option){
		var callback;
		var url;
		var sn;
		if(option){
			callback = option.callback;
			url = option.url;
			initimage = option.initimage;
		}
		
		$.ajax({
			"async": false,
			"dataType" : "json",
			"type" : "GET",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"cache" : false,
			"url" : "http://127.0.0.1:22001/zkbioonline/fingerprint/beginCapture?type=1&FakeFunOn=0",
			"timeout" : "60000",
			"data" : {},
			"success": function(r){
				if(r.ret == 0){//系统正常
					//系统正常
					$.ajax({
						"async": false,
						"dataType" : "json",
						"type" : "GET",
						"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
						"cache" : false,
						"url" : "http://127.0.0.1:22001/zkbioonline/info",
						"timeout" : "60000",
						"data" : {},
						"success" : function(dd) {
							if(dd.ret == 0){
								var enroll_count = 3;
								var tim = setInterval(function(){
									var ddd = $.ajax({
										"async": false,
										"dataType" : "json",
										"type" : "GET",
										"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
										"cache" : false,
										"url" : "http://127.0.0.1:22001/zkbioonline/fingerprint/getImage",
										"timeout" : "60000",
										"data" : {},
										"success": initimage|| function(rinit){
											
										}
									});
									if(ddd.responseJSON.ret == 0 && ddd.responseJSON.data.enroll_index == enroll_count){
										clearInterval(tim);
										var ddd = $.ajax({
											"async": false,
											"dataType" : "json",
											"type" : "GET",
											"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
											"cache" : false,
											"url" : "http://127.0.0.1:22001/zkbioonline/fingerprint/getTemplate",
											"timeout" : "60000",
											"data" : {},
											"success": function(rimage){
												if(rimage.ret == 0){
													console.log(rimage);
													$.call(url || "application.user.regFinger", {
														system_user_id: system_user_id,
														sn: r.data['sn'],
														finger_image: rimage.data.template
													}, callback || function(rtn) {
														if (rtn) {
															$.message("指纹注册成功，已注册指纹 "+rtn.fcount+" 枚！");
															return r.data['sn'];
														}
													});
												}
											}
										});
									}
								}, 1000);
								
							}
						}
					});
					
				}
			}
		});
	},
	"cancelfinger": function(callback){
		$.ajax({
			"async": false,
			"dataType" : "json",
			"type" : "GET",
			"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
			"cache" : false,
			"url" : "http://127.0.0.1:22001/zkbioonline/fingerprint/cancelCapture",
			"timeout" : "60000",
			"data" : {},
			"success": callback || function(r){
				if(r.ret == 0){
					
				}
			}
		});
	},
	"wx2": function(option){
		var html = [];
		html.push("<div class='form-horizontal' style='display:none'>");
		html.push("<div class='row-fluid' >");
				html.push("<div style='display:block;text-align:center' id='login_container'>");
				html.push("</div>");
		html.push("</div>");
		html.push("</div>");
		var _t = $(html.join(""));
		$("body").append(_t);
		_t.ccinit();
		var appid = $("input[name='wx_appid']").val();
		var state = $("input[name='wx_state']").val();
		var obj = new WxLogin({  //https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419316505&token=eed48c49b75ad25e653a6a36bc4a0df4582cdf1c&lang=zh_CN
            id:"login_container",   //第三方页面显示二维码的容器id
            appid: appid,   //应用唯一标识，在微信开放平台提交应用审核通过后获得
            scope: "snsapi_login",   //应用授权作用域，拥有多个作用域用逗号（,）分隔，网页应用目前仅填写snsapi_login即可
            redirect_uri: encodeURIComponent(window.location.protocol+"//"+window.location.host+"/w/application/auth/authorization.json"),  //重定向地址，需要进行UrlEncode，需要时域名
            state: state,  //用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
            style: "black",   //提供"black"、"white"可选，默认为黑色文字描述
            href: ""
          });
		var o = {
		};
		o.type = 1;
		o.content = _t;
		o.title = false;
		o.area = [o.width || "40%", o.height || "80%"];
		o.zIndex = layer.zIndex,
		o.cancel = function(index) {
			layer.close(index);
			_t.remove();
	    }
		var laindex = layer.open(o);
	},
	/*"v12Common": function(command, jsonStr){
		if(command == 'submitProcess'){
			var jsonParam = JSON.parse(jsonStr);
			$.call("application.bill.submit_v12", jsonParam, function(rtn){
				
			});
		}else if(command == 'approvalProcess'){
			var jsonParam = JSON.parse(jsonStr);
			jsonParam.audited = 'Y';
			$.call("application.bill.approval_v12", jsonParam || {}, function(rtn){
				
			});
		}else if(command == 'rejectProcess'){
			var jsonParam = JSON.parse(jsonStr);
			jsonParam.audited = 'N';
			$.call("application.bill.approval_v12", jsonParam || {}, function(rtn){
				
			});
		}else if(command == 'rejestLastProcess'){
			jsonParam.audited = 'NL';
			var jsonParam = JSON.parse(jsonStr);
			$.call("application.bill.approval_v12", jsonParam || {}, function(rtn){
				
			});
		}else if(command == 'backProcess'){
			var jsonParam = JSON.parse(jsonStr);
			$.call("application.bill.backProcess_v12", jsonParam || {}, function(rtn){
				
			});
		}
	},*/
	"initRM": function(_initc){
		$.call("application.auth.rmcert", {module: module}, function(rtn){
			if($chohorm){
				$chohorm.init(rtn.cert, _initc);
			}
		});
	},
	"generateId": function() {
		var now = new Date();
		var _t = 1;
		if ($(document).data("generateId_times")) {
			_t = $(document).data("generateId_times") + 1;
			if (_t >= 1000) _t = 1; 
		}
		$(document).data("generateId_times", _t);
		_t = (1000 + _t) + "";
		var id = formatDate(now, 'yyyyMMddHHmmss') + formatDate(now, 'sss')
				+ _t.substring(1);
		return id;
	},
	"py": function(str) {
		return pinyinUtil.getFirstLetter(str);
	},
	"speak": function(txt) {
		if (window["SpeechSynthesisUtterance"]) {
			/* text – 要合成的文字内容，字符串。
			lang – 使用的语言，字符串， 例如："zh-cn"
			voiceURI – 指定希望使用的声音和服务，字符串。
			volume – 声音的音量，区间范围是0到1，默认是1。
			rate – 语速，数值，默认值是1，范围是0.1到10，表示语速的倍数，例如2表示正常语速的两倍。
			pitch – 表示说话的音高，数值，范围从0（最小）到2（最大）。默认值为1。 */
			var utter = new SpeechSynthesisUtterance(txt);
			utter.lang = 'zh-cn';
			utter.pitch = 0;
			utter.volume = 1;
			utter.rate = 0.8;
			speechSynthesis.speak(utter);
		} else {
			$.error("您的浏览器不支持语音播报");
		}
	},
	"speech": function(callback) {
		if (window["webkitSpeechRecognition"]) {
			var rec = new webkitSpeechRecognition();
			rec.continuous = true;
            rec.interimResults = false;
			rec.maxAlternatives = 1;
            rec.lang = 'zh-cn';
			rec.lang = 'en-US';
			var text = ''
			rec.onresult = function (rst) {
				console.log(rst);
				if (rst.results && rst.results[0][0]) {	
					if (rst.results[0].isFinal) {
						recognition.stop();	
					} else {
						console.log('听ing');
					}
					if (callback) {
						callback(rst.results[0][0].transcript);
					} else {
						console.log(rst.results[0][0].transcript);
					}
				}
			}
			rec.onsoundend = function () {
				console.log('soundend')
			};
			rec.onerror = function (e) {
				console.error(e)
			};
			rec.onstart = function () {
				console.log('start')
			};
			rec.onsoundstart = function () {
				console.log('onsoundstart')
			};
			rec.onspeechend = function () {
				console.log('stop')
			};
			rec.start();
		} else {
			console.error("您的浏览器不支持语音识别");
		}
	}
});
function chartModal(moreurl){
	var u = moreurl.replace(/\./g, "/");
	var url = cooperopcontextpath + "/w/" + u+".html";
	$.modal(url, ' ', $.extend(true, {
		width: '100%',
		height: '100%',
    	callback : function(rtn) {
			if (rtn) {
				
			}
		}
    },$("[ctype='form']").getData()));
}
/**
 * 获取浏览器类型及 版本
 * @returns 返回对象，两属性，type:表示浏览器类型，version：表示版本 {___anonymous25556_25557} 
 */
function getUserAgent(){
	var Sys={};
	var ua=navigator.userAgent.toLowerCase();
	var s;
	(s=ua.match(/msie ([\d.]+)/))?(Sys['type']='ie',Sys['version']=s[1]):
	(s=ua.match(/firefox\/([\d.]+)/))?(Sys['type']='firefox',Sys['version']=s[1]):
	(s=ua.match(/chrome\/([\d.]+)/))?(Sys['type']='chrome',Sys['version']=s[1]):
	(s=ua.match(/opera.([\d.]+)/))?(Sys['type']='opera',Sys['version']=s[1]):
	(s=ua.match(/version\/([\d.]+).*safari/))?(Sys['type']='safari',Sys['version']=s[1]):0;
	return Sys;
}

//获取页面url参数
function getQueryStringRegExp(name,href)
{
	if(href ==undefined || href == ""){
		href = location.href;
	}
	var qop = decodeURLParams(href);
	return qop[name] || "";
}
