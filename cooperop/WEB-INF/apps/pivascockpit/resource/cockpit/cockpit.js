var $crcockpit = $crcockpit || {};
(function(crcockpit, window) {
	if (crcockpit.inited) {
		return;
	}
	crcockpit.src = document.getElementsByTagName('script')[document.getElementsByTagName('script').length - 1].src;
	crcockpit.base_url = crcockpit.src.substring(0, crcockpit.src.lastIndexOf('/'));
	crcockpit.ele = undefined;
	crcockpit.clock = undefined;

	var idseq = 0;
	var generateId = function() {
		idseq ++;
		if (idseq > 999) idseq = 1;
		var id = ('000' + idseq);
		id = id.substring(id.length - 3);
		var id = 'CP' + new Date().getTime() + id + 'C';
		return id;
	};

	var loadRES	= function(js, success, error) {
		var jsreg = new RegExp("\.js$");
		var jsreg2 = new RegExp("\.js[\?w|#w]");
		var cssreg = new RegExp(".css$");
		var cssreg2 = new RegExp("\.css[\?w|#w]");
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
			if (jsreg.test(initjs[o].src) || jsreg2.test(initjs[o].src)) {
				var ls = oHead.getElementsByTagName('script');
				for (var i = 0; i < ls.length; i++) {
					if (ls[i].src == initjs[o].src) {
						initjs[o].ready = true;
						break;
					}
				}
			} else if (cssreg.test(initjs[o].src) || cssreg2.test(initjs[o].src)) {
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
			if (jsreg.test(initjs[o].src) || jsreg2.test(initjs[o].src)) {
				oScript = document.createElement("script");
				oScript.type = "text/javascript";
				oScript.src = initjs[o].src;
			} else if (cssreg.test(initjs[o].src) || cssreg2.test(initjs[o].src)) {
				oScript = document.createElement("link");
				oScript.rel = "stylesheet";
				oScript.type = "text/css";
				oScript.href = initjs[o].src;
			}
			oScript.jsurl = initjs[o].src;
			oScript.onload = oScript.onreadystatechange = function() {
				if (!this.readyState // 这是FF的判断语句，因为ff下没有readyState这人值，IE的readyState肯定有值
						|| this.readyState == 'loaded' || this.readyState == 'complete') { // 这是IE的判断语句
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


	crcockpit.charts = {};
	
	crcockpit.queryCockpitData = function(chart, params, callback, error) {
		crcockpit.queryData(chart, 'cockpit', params, callback, error);
	}
	
	crcockpit.queryFullData = function(chart, params, callback, error) {
		crcockpit.queryData(chart, 'full', params, callback, error);
	}
	
	crcockpit.queryData = function(chart, type, params, callback, error) {
		$.call("pivascockpit.statistics", {
			code: chart.code,
			type: type,
			params: JSON.stringify(params)
		}, callback, function(errmsg) {
			if (error) error(errmsg);
			else $.warning(errmsg);
		}, {nomask: true})
	}

	var tcharts = {};
	crcockpit.paint = function() {
		var html = [];
		html.push('<div class="cockpit">');

		html.push('	<div class="cockpit-header">');
		html.push('		<h1 class="cockpit-title">' + crcockpit.options.name + '</h1>');
		html.push('		<div class="cockpit-info-date"></div>');
		html.push('		<div class="cockpit-info-time"></div>');
		html.push('		<ul class="cockpit-tabs">');
		var active_tab = 0;
		for (var i = 0, tab = crcockpit.options.tabs[i]; i < crcockpit.options.tabs.length; i ++, tab = crcockpit.options.tabs[i]) {
			if (tab.isactive) active_tab = i;
			html.push('			<li><a href="javascript:void(0);" data-cptabnum="' + i + '">' + tab.name + '</a></li>');
		}

		html.push('		</ul>');
		html.push('	</div>');

		html.push('	<div class="cockpit-tabpanels">');
		for (var i = 0, tab = crcockpit.options.tabs[i]; i < crcockpit.options.tabs.length; i ++, tab = crcockpit.options.tabs[i]) {
			html.push('		<div class="cockpit-tabpanel" data-cptabnum="' + i + '">');

			for (var j = 0, chart = tab.charts[j]; j < tab.charts.length; j ++, chart = tab.charts[j]) {
				chart.id = generateId();
				tcharts[chart.id] = chart;
				html.push('			<div class="cockpit-box cockpit-left-' + chart.xaxis + ' cockpit-top-' + chart.yaxis + ' cockpit-width-' + chart.width + ' cockpit-height-' + chart.height + '">');
				html.push('				<div class="cockpit-chart cockpit-chart-' + chart.code + '" data-cpid="' + chart.id + '">');
				html.push('					<ul class="cockpit-chart-tips"></ul>');
				html.push('					<div class="cockpit-chart-title">');
				html.push('						<h3>' + chart.name + '</h3>');
				html.push('						<span class="cockpit-chart-time"></span>');
				html.push('					</div>');
				html.push('					<div class="cockpit-chart-content">');
				html.push('						<div class="cockpit-chart-chart">');
				html.push('							<span class="cockpit-chart-chart-loading"></span>');
				html.push('						</div>');
				html.push('					</div>');
				html.push('				</div>');
				html.push('			</div>');
			}

			html.push('		</div>');
		}
		html.push('	</div>');

		html.push('	<div class="cockpit-footer">');
		html.push('		<ul class="cockpit-info-other">');
		html.push('			<li class="cockpit-info-supporter">' + crcockpit.options.supporter + '</li>');
		html.push('			<li class="cockpit-info-hotline">' + crcockpit.options.hotline + '</li>');
		html.push('			<li class="cockpit-info-weixin">' + crcockpit.options.weixin + '</li>');
		html.push('			<li class="cockpit-info-website">' + crcockpit.options.website + '</li>');
		html.push('		</ul>');
		html.push('	</div>');
		html.push('</div>');

		crcockpit.ele.append(html.join(''));

		crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-tabs").children("li:eq(" + active_tab + ")").children("a").addClass("active");
		crcockpit.ele.find(".cockpit > .cockpit-tabpanels").children(".cockpit-tabpanel:eq(" + active_tab + ")").addClass("active");

		crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel > .cockpit-box > .cockpit-chart").each(function () {
			var $chart = $(this);
			var c = tcharts[$chart.data("cpid")];
			$chart.data("cpchart", c);
			if (crcockpit.charts[c.code] && crcockpit.charts[c.code].paint) {
				crcockpit.charts[c.code].paint(c, $chart);
			}
		});
		
		if (crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-tabs").children("li").length == 1) {
			crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-tabs").children("li").hide();
		}

	};

	crcockpit.popindex = 1000;
	crcockpit.poptimer = {};
	crcockpit.statisticsfull = function(chart, ele, data) {
		if (crcockpit.charts[chart.code] && crcockpit.charts[chart.code].statistics_full) {
			ele.find(".cockpit-chart-title > .cockpit-chart-time").removeClass("cockpit-chart-error");
			ele.find(".cockpit-chart-title > .cockpit-chart-time").attr("title", "");
			ele.find(".cockpit-chart-title > .cockpit-chart-time").addClass("cockpit-chart-loading");
			crcockpit.charts[chart.code].statistics_full(chart, ele, data, function() {
				ele.find(".cockpit-chart-title > .cockpit-chart-time").removeClass("cockpit-chart-loading");
			}, function(emsg) {
				ele.find(".cockpit-chart-title > .cockpit-chart-time").text(crcockpit.formatTime(new Date()));
				ele.find(".cockpit-chart-title > .cockpit-chart-time").addClass("cockpit-chart-error");
				ele.find(".cockpit-chart-title > .cockpit-chart-time").attr("title", emsg);
			});
		}
	}
	crcockpit.popfull = function(chart, ele, data) {
		var popid = generateId();
		if (data) {
			data = $.extend(true, {start: 1, limit: -1}, data);
		}
		var html = [];
		html.push('<div class="cockpit-pop" data-cpopid="' + popid + '">');
		html.push('	<div class="cockpit-pop-box">');
		html.push('		<div class="cockpit-pop-chart cockpit-chart-' + chart.code + '" data-cpid="' + chart.id + '" data-cpopid="' + popid + '">');
		html.push('			<div class="cockpit-chart-title">');
		html.push('				<h3>' + chart.name + '</h3>');
		html.push('				<ul class="cockpit-chart-tips"></ul>');
		html.push('				<span class="cockpit-chart-time"></span>');
		html.push('				<a class="cockpit-chart-closepop" href="javascript:void(0);">关闭</a>');
		html.push('			</div>');
		html.push('			<div class="cockpit-chart-content">');
		html.push('				<div class="cockpit-chart-chart">');
		html.push('					<span class="cockpit-chart-chart-loading"></span>');
		html.push('				</div>');
		html.push('			</div>');
		html.push('		</div>');
		html.push('	</div>');
		html.push('</div>');
		var $pop = $(html.join(''));
		$pop.data("cpchart", chart);
		$pop.data("cpchartele", ele);

		var p = {};
		if (ele) {
			p = {
				top: ele.parent().offset().top,
				left: ele.parent().offset().left,
				width: ele.parent().width(),
				height: ele.parent().height()
			};
		} else if (event){
			p = {
				top: event.y - 30,
				left: event.x - 150,
				width: 300,
				height: 60
			};
		} else {
			p = {
				top: crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").offset().top + (crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").height()) / 2,
				left: crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").offset().left + (crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").width()) / 2,
				width: 0,
				height: 0
			};
		}
		if (p.top < 0) p.top = 0;
		if (p.top > $("body").height()) p.top = $("body").height() - p.height;
		if (p.left < 0) p.left = 0;
		if (p.left > $("body").width()) p.left = $("body").width() - p.width;

		$pop.data("cppopposition", p);
		$pop.css("z-index", crcockpit.popindex+=100);
		$pop.children(".cockpit-pop-box").css($pop.data("cppopposition"));
		crcockpit.ele.find(".cockpit").append($pop);

		var a = function() {
			if (crcockpit.charts[chart.code] && crcockpit.charts[chart.code].paint_full) {
				crcockpit.charts[chart.code].paint_full(chart, $pop.find(".cockpit-pop-box > .cockpit-pop-chart"), data);
			}
			$pop.children(".cockpit-pop-box").animate({
//				top: crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").offset().top,
//				left: crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").offset().left,
//				width: crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").width(),
//				height: crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").height()
				top: crcockpit.ele.offset().top + crcockpit.ele.height() * 0.04,
				left: crcockpit.ele.offset().left + crcockpit.ele.width() * 0.02,
				width: crcockpit.ele.width() * 0.96,
				height: crcockpit.ele.height() * 0.94
			}, (ele ? 500 : 750), "swing", function() {
				if (+chart.refresh_time > 0) {
					crcockpit.poptimer[popid] = setInterval(function() {
						crcockpit.statisticsfull(chart, $pop.find(".cockpit-pop-box > .cockpit-pop-chart"), data);
					}, (+chart.refresh_time || 60) * 1000);
				}
				crcockpit.statisticsfull(chart, $pop.find(".cockpit-pop-box > .cockpit-pop-chart"), data);
				
				var hammerBox = new Hammer($pop.find(".cockpit-pop-box > .cockpit-pop-chart > .cockpit-chart-content")[0]);
				hammerBox.get('pinch').set({
					enable: true
				});
				hammerBox.on('pinch', function(ev) {
					crcockpit.closepop(chart, $pop);
				});
				hammerBox.get('swipe').set({
					direction: Hammer.DIRECTION_VERTICAL
				});
				hammerBox.on('swipe', function(ev) {
					if (ev.distance > 150) {
						crcockpit.closepop(chart, $pop);
					}
				});
			});
		}

		if (ele) {
			clearInterval(crcockpit.timers[chart.id]);
			delete crcockpit.timers[chart.id];
			ele.css("opacity", 0);
			$pop.children(".cockpit-pop-box").animate({
				top: crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").offset().top + (crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").height() - p.height) / 2,
				left: crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").offset().left + (crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").width() - p.width) / 2
			}, 500, "swing", a);
		} else {
			a();
		}
	};
	crcockpit.closepop = function(chart, ele) {
		var cpopid = ele.data("cpopid");
		if (crcockpit.poptimer[cpopid]) {
			clearInterval(crcockpit.poptimer[cpopid]);
			delete crcockpit.poptimer[cpopid];
		}
		var p = ele.data("cppopposition");
		var a = function() {
			ele.children(".cockpit-pop-box").animate(ele.data("cppopposition"), (ele.data("cpchartele") ? 500 : 750), "swing", function() {
				if (ele.data("cpchartele")) {
					ele.data("cpchartele").css("opacity", 1);
					crcockpit.statisticsChart(chart, ele.data("cpchartele"));
				}
				ele.remove();
			});
		}

		if (ele.data("cpchartele")) {
			ele.children(".cockpit-pop-box").animate({
				top: crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").offset().top + (crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").height() - p.height) / 2,
				left: crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").offset().left + (crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").width() - p.width) / 2,
				width: p.width,
				height: p.height
			}, 500, "swing", a);
		} else {
			a();
		}
		
	};

	crcockpit.bindEvent = function() {
		crcockpit.clock = setInterval(function() {
			var now = new Date();
			var y = "" + now.getFullYear(), y1 = y.substring(0, 1), y2 = y.substring(1,2), y3 = y.substring(2,3), y4=y.substring(3,4), y = ['<b>', [y1, y2, y3, y4].join('</b><b>'), '</b>'].join(''), 
			    m = now.getMonth() > 8 ? "" + now.getMonth() + 1 : "0" + (now.getMonth() + 1), m1 = m.substring(0, 1), m2 = m.substring(1,2), m = ['<b>', [m1, m2].join('</b><b>'), '</b>'].join(''),
			    d = now.getDate() > 9 ? "" + now.getDate() : "0" + now.getDate(), d1 = d.substring(0, 1), d2 = d.substring(1,2), d = ['<b>', [d1, d2].join('</b><b>'), '</b>'].join(''),
			    h = now.getHours() > 9 ? "" + now.getHours() : "0" + now.getHours(), h1 = h.substring(0, 1), h2 = h.substring(1,2), h = ['<b>', [h1, h2].join('</b><b>'), '</b>'].join(''),
			    mi = now.getMinutes() > 9 ? "" + now.getMinutes() : "0" + now.getMinutes(), mi1 = mi.substring(0, 1), mi2 = mi.substring(1,2), mi = ['<b>', [mi1, mi2].join('</b><b>'), '</b>'].join(''),
			    s = now.getSeconds() > 9 ? "" + now.getSeconds() : "0" + now.getSeconds(), s1 = s.substring(0, 1), s2 = s.substring(1,2), s = ['<b>', [s1, s2].join('</b><b>'), '</b>'].join(''),
			    w = ["日", "一", "二", "三", "四", "五", "六"][now.getDay()];
			crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-info-date").html(['<em>', y, '</em><em>', m, '</em><em>', d, '</em><i>星期', w,'</i>'].join(''));
			crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-info-time").html(['<em>', h, '</em><em>', mi, '</em><em>', s, '</em>'].join(''));
		}, 100);
		crcockpit.ele.on("click", ".cockpit > .cockpit-header > .cockpit-tabs > li > a", function() {
			var $t = $(this);
			if ($t.is(".active")) return;
			crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-tabs > li > a").removeClass("active");
			crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").removeClass("active");
			crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-tabs > li > a[data-cptabnum='" + $t.data("cptabnum") + "']").addClass("active");
			crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel[data-cptabnum='" + $t.data("cptabnum") + "']").addClass("active");
			crcockpit.statisticsTimer();
		});
		crcockpit.ele.on("click", ".cockpit-chart-title > .cockpit-chart-time.cockpit-chart-error", function() {
			$.warning($(this).attr("title"));
			return false;
		});
		crcockpit.ele.on("click", ".cockpit > .cockpit-tabpanels > .cockpit-tabpanel.active > .cockpit-box > .cockpit-chart", function() {
			var $chart = $(this);
			var chart = $chart.data("cpchart");
			crcockpit.popfull(chart, $chart);
		});
		crcockpit.ele.on("click", ".cockpit > .cockpit-pop > .cockpit-pop-box > .cockpit-pop-chart > .cockpit-chart-title > .cockpit-chart-closepop", function() {
			var $chart = $(this).closest(".cockpit-pop");
			var chart = $chart.data("cpchart");
			crcockpit.closepop(chart, $chart);
		});
		crcockpit.ele.on("click", ".cockpit > .cockpit-pop", function() {
			var $chart = $(this);
			var chart = $chart.data("cpchart");
			crcockpit.closepop(chart, $chart);
		});
		crcockpit.ele.on("click", ".cockpit > .cockpit-pop > .cockpit-pop-box", function() {
			return false;
		});
	};

	crcockpit.formatTime = function(now) {
		var y = "" + now.getFullYear(), 
		    m = now.getMonth() > 8 ? "" + now.getMonth() + 1 : "0" + (now.getMonth() + 1),
		    d = now.getDate() > 9 ? "" + now.getDate() : "0" + now.getDate(),
		    h = now.getHours() > 9 ? "" + now.getHours() : "0" + now.getHours(),
		    mi = now.getMinutes() > 9 ? "" + now.getMinutes() : "0" + now.getMinutes(),
		    s = now.getSeconds() > 9 ? "" + now.getSeconds() : "0" + now.getSeconds(),
		    w = ["日", "一", "二", "三", "四", "五", "六"][now.getDay()];
		return [m, '-', d, ' ', h, ':', mi, ':', s].join('');
	}

	crcockpit.timers = {};
	crcockpit.statistics = function(chart, ele) {
		if (crcockpit.charts[chart.code] && crcockpit.charts[chart.code].statistics) {
			ele.find(".cockpit-chart-title > .cockpit-chart-time").removeClass("cockpit-chart-error");
			ele.find(".cockpit-chart-title > .cockpit-chart-time").attr("title", "");
			ele.find(".cockpit-chart-title > .cockpit-chart-time").addClass("cockpit-chart-loading");
			crcockpit.charts[chart.code].statistics(chart, ele, function() {
				ele.find(".cockpit-chart-title > .cockpit-chart-time").removeClass("cockpit-chart-loading");
			}, function(emsg) {
				ele.find(".cockpit-chart-title > .cockpit-chart-time").text(crcockpit.formatTime(new Date()));
				ele.find(".cockpit-chart-title > .cockpit-chart-time").addClass("cockpit-chart-error");
				ele.find(".cockpit-chart-title > .cockpit-chart-time").attr("title", emsg);
			});
		}
	}
	crcockpit.statisticsTimer = function() {
		for (var cpid in crcockpit.timers){
			clearInterval(crcockpit.timers[cpid]);
			delete crcockpit.timers[cpid];
		}
		crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel.active > .cockpit-box > .cockpit-chart").each(function () {
			var $chart = $(this);
			var chart = $chart.data("cpchart");
			crcockpit.statisticsChart(chart, $chart);
		});
	};
	crcockpit.statisticsChart = function(chart, ele) {
		if (+chart.refresh_time > 0) {
			crcockpit.timers[chart.id] = setInterval(function() {
				crcockpit.statistics(chart, ele);
			}, (+chart.refresh_time || 60) * 1000);
		}
		crcockpit.statistics(chart, ele);
	}

	crcockpit.create = function(ele, options) {
		crcockpit.ele = $(ele);
		crcockpit.options = $.extend(true, {
			id: undefined, //驾驶舱ID
			name: "驾驶舱标题", //驾驶舱名字（标题）
			supporter: "成都超然祥润科技有限公司", //技术支持（用于显示）
			hotline: "400-000-7932 028-85534408", //服务热线（用于显示）
			weixin: "chaoran_jingpei", //微信（用于显示）
			website: "http://www.crtech.cn", //官网（用于显示）
			template: "space", //驾驶舱使用模板
			tabs: [  //驾驶舱TAB页签数组
				// {
				// 	name: “”, //TAB页签名称
				// 	charts: [  //TAB页签中的图表数组
				// 		{
				// 			code: “”,  //图表编号
				// 			name: “”,  //图表名称
				// 			refresh_time: 30, //图表数据刷新时间
				// 			childcode: “”,  //大屏图表数据项下钻打开图表编号
				// 			childname: “”,  //大屏图表数据项下钻打开图表名称
				// 			xaxis: 1,  //图表LEFT位置，1～12
				// 			yaxis: 1,  //图表TOP位置，1～12
				// 			width: 2,  //图表宽度，1～12
				// 			height: 8  //图表高度，1～12
				// 		}
				// 	]
				// }
			]
		}, options);

		var ccs = ["drug_class", "drug", "order_shenc", "pici_peizhi", "pici_class", "paiyao", "cpfuh"];
		
		var res = [];

		res.push([crcockpit.base_url, 'css', 'vdiconfont', 'iconfont.css'].join("/"));
		res.push([crcockpit.base_url, 'hammer', 'hammer.min.js'].join("/"));
		res.push([crcockpit.base_url, 'template', crcockpit.options.template, 'style.css'].join("/"));
		for (var i = 0; i < ccs.length; i ++) {
			res.push([crcockpit.base_url, 'charts', ccs[i] + '.js'].join("/"));
			res.push([crcockpit.base_url, 'template', crcockpit.options.template, 'charts', ccs[i] + '.css'].join("/"));
		}

		loadRES(res, function() {
			crcockpit.paint();
			crcockpit.bindEvent();
			crcockpit.statisticsTimer();
		}, function(jsurl, evt) {
			if (window.console) {
				window.console.error("load cockpit template [" + jsurl + "] failed.");
			}
		});
	};

	crcockpit.inited = true;
})($crcockpit, window);