var $crcockpit = $crcockpit || {};
var $crcockpitdesigner = $crcockpit;
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
	
	crcockpit.paintTab = function(tab) {
		var taele = crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-tabs > li > .cockpit-tabs-add").parent();
		var tpsele = crcockpit.ele.find(".cockpit > .cockpit-tabpanels");
		var i = tpsele.children(".cockpit-tabpanel").length;
		var tbele = $('					<li><a href="javascript:void(0);" data-cptabnum="' + i + '">' + tab.name + '</a></li>');
		var tpele = $('				<div class="cockpit-tabpanel" data-cptabnum="' + i + '"></div>');
		for (var code in tab.charts) {
			crcockpit.paintChart(tab.charts[code], tpele);
		}
		taele.before(tbele);
		tpsele.append(tpele);
		return tpele;
	}

	crcockpit.paintChart = function(chart, tele) {
		var html = [];
		html.push('			<div draggable="false" class="cockpit-box cockpit-left-' + chart.xaxis + ' cockpit-top-' + chart.yaxis + ' cockpit-width-' + chart.width + ' cockpit-height-' + chart.height + '" cockpit-chart-' + chart.code + '" data-cpid="' + chart.id + '">');
		html.push('				<div draggable="false" class="cockpit-chart cockpit-chart-' + chart.code + '" data-cpid="' + chart.id + '">');
		html.push('					<ul class="cockpit-chart-tips"></ul>');
		html.push('					<div class="cockpit-chart-title">');
		html.push('						<h3>' + crcockpit.chartdefs[chart.code].name + '</h3>');
		html.push('						<span class="cockpit-chart-time">' + crcockpit.time + '</span>');
		html.push('					</div>');
		html.push('					<div class="cockpit-chart-content">');
		html.push('						<div class="cockpit-chart-chart">');
		html.push('							<span class="cockpit-chart-chart-sample"></span>');
		html.push('						</div>');
		html.push('					</div>');
		html.push('				</div>');
		html.push('				<div draggable="false" class="cockpit-chart-move">');
		html.push('					<a href="javascript:void(0);" class="cockpit-chart-resizetop" draggable="false"><i></i></a>');
		html.push('					<a href="javascript:void(0);" class="cockpit-chart-resizeright" draggable="false"><i></i></a>');
		html.push('					<a href="javascript:void(0);" class="cockpit-chart-resizebottom" draggable="false"><i></i></a>');
		html.push('					<a href="javascript:void(0);" class="cockpit-chart-resizeleft" draggable="false"><i></i></a>');
		html.push('					<a href="javascript:void(0);" class="cockpit-chart-drag" draggable="false"><i class="vdiconfont vdicon-ic_normal_move"></i>点此移动</a>');
		html.push('					<a href="javascript:void(0);" class="cockpit-chart-delete" draggable="false"><i class="vdiconfont vdicon-trush"></i>删除</a>');
		html.push('				</div>');
		html.push('			</div>');
		var cele = $(html.join(""));
		tele.append(cele);
		if (crcockpit.charts[chart.code] && crcockpit.charts[chart.code].paint_designer) {
			crcockpit.charts[chart.code].paint_designer(chart, cele);
		}
		return cele;
	};

	crcockpit.paint = function() {
		var html = [];
		html.push('<div class="cockpitdesigner">');
		html.push('	<div class="cockpitdef">');
		html.push('		<div class="cockpitdef-title"><span>属性</span></div>');
		html.push('		<div class="cockpitdef-field required">');
		html.push('  		<label for="name">名称</label>');
		html.push('			<input type="text" name="name" value="' + crcockpit.data.name+ '"/>');
		html.push('		</div>');
		html.push('		<div class="cockpitdef-field required">');
		html.push('			<label for="template">模板</label>');
		html.push('			<select name="template">');
		for (var i = 0, template = crcockpit.templates[i]; i < crcockpit.templates.length; i ++, template = crcockpit.templates[i]) {
			html.push('				<option value="' + template.code + '"' + (template.code == crcockpit.data.template ? ' selected' : '') + '>' + template.name + '</option>');
		}
		html.push('			</select>');
		html.push('			<span class="select-tip"><i class="vdiconfont vdicon-dropdown"></i></span>');
		html.push('		</div>');
		html.push(' 	<div class="cockpitdef-field">');
		html.push('  		<button type="button" class="btnSave"><i class="vdiconfont vdicon-baocun"></i>保存</button>');
		html.push('  		<button type="button" class="btnPreview"><i class="vdiconfont vdicon-wodetubiao"></i>预览</button>');
		html.push('  	</div>');
		html.push('	</div>');
		html.push('	<div class="cockpitcharts">');
		html.push(' 	<div class="cockpitcharts-title"><span>可用图表</span></div>');
		html.push(' 	<div class="cockpitcharts-list">');
		for (var code in crcockpit.chartdefs) {
			html.push('			<a href="javascript:void(0);" data-cpcode="' + code + '" draggable="true"><i class="' + (crcockpit.charts[code].icon || "") + '"></i>' + crcockpit.chartdefs[code].name + '</a>');
		}
		html.push('		</div>');
		html.push('	</div>');
		html.push('	<div class="cockpit-bg">');
		html.push('		<i class="logo"></i>');
		
		html.push('		<div class="cockpit">');

		html.push('			<div class="cockpit-header">');
		html.push('				<h1 class="cockpit-title">' + crcockpit.data.name + '</h1>');
		html.push('				<div class="cockpit-info-date"></div>');
		html.push('				<div class="cockpit-info-time"></div>');
		html.push('				<ul class="cockpit-tabs">');
		html.push('					<li><a href="javascript:void(0);" class="cockpit-tabs-add" title="点击编辑驾驶舱页签"><i class="vdiconfont vdicon-addparam2"></i></a></li>');
		html.push('				</ul>');
		html.push('			</div>');
		
		html.push('			<div class="cockpit-rulers">');
		for (var i = 0; i < crcockpit.yaxis; i ++) {
			html.push('				<div class="cockpit-rulers-row">');
			for (var j = 0; j < crcockpit.xaxis; j ++) {
				html.push('					<div class="cockpit-rulers-cell"></div>');
			}
			html.push('				</div>');
		}
		html.push('			</div>');
		
		html.push('			<div class="cockpit-tabpanels"></div>');

		html.push('			<div class="cockpit-footer">');
		html.push('				<ul class="cockpit-info-other">');
		html.push('					<li class="cockpit-info-supporter">' + crcockpit.supports.supporter + '</li>');
		html.push('					<li class="cockpit-info-hotline">' + crcockpit.supports.hotline + '</li>');
		html.push('					<li class="cockpit-info-weixin">' + crcockpit.supports.weixin + '</li>');
		html.push('					<li class="cockpit-info-website">' + crcockpit.supports.website + '</li>');
		html.push('				</ul>');
		html.push('			</div>');
		html.push('		</div>');
		
		html.push('	</div>');
		html.push('</div>');
		
		crcockpit.ele.append(html.join(''));

		var active_tab = 0;
		for (var i = 0, tab = crcockpit.data.tabs[i]; i < crcockpit.data.tabs.length; i ++, tab = crcockpit.data.tabs[i]) {
			if (tab.isactive) active_tab = i;
			crcockpit.paintTab(tab);
		}
		crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-tabs").children("li:eq(" + active_tab + ")").children("a").addClass("active");
		crcockpit.ele.find(".cockpit > .cockpit-tabpanels").children(".cockpit-tabpanel:eq(" + active_tab + ")").addClass("active");
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
		crcockpit.ele.on("change", ".cockpitdef > .cockpitdef-field > input[name='name']", function() {
			var $t = $(this);
			crcockpit.data.name = $t.val();
			crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-title").text($t.val());
		});
		crcockpit.ele.on("change", ".cockpitdef > .cockpitdef-field > select[name='template']", function() {
			var $t = $(this);
			var ot = [crcockpit.base_url, 'template', crcockpit.data.template, 'style.css'].join("/");
			crcockpit.data.template = $t.val();
			var nt = [crcockpit.base_url, 'template', crcockpit.data.template, 'style.css'].join("/");
			var oHead = document.getElementsByTagName('HEAD').item(0);
			var cs = oHead.getElementsByTagName('link');
			for (var i = 0; i < cs.length; i++) {
				if (cs[i].href == ot) {
					cs[i].href = nt;
					break;
				}
			}
		});
		crcockpit.ele.on("click", ".cockpit > .cockpit-header > .cockpit-tabs > li > a[class!='cockpit-tabs-add']", function() {
			var $t = $(this);
			if ($t.is(".active")) return;
			crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-tabs > li > a").removeClass("active");
			crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel").removeClass("active");
			$t.addClass("active");
			crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel[data-cptabnum='" + $t.attr("data-cptabnum") + "']").addClass("active");
		});
		crcockpit.ele.on("click", ".cockpit > .cockpit-header > .cockpit-tabs > li > a.cockpit-tabs-add", function() {
			var $t = $(this);
			var html = ['<div class="cockpit-tab-editor">'];
			for (var i = 0, tab = crcockpit.data.tabs[i]; i < crcockpit.data.tabs.length; i ++, tab = crcockpit.data.tabs[i]) {
				html.push('<div>');
				html.push('	<input type="text" name="name" data-cptabindex="' + i + '" value="' + tab.name + '"></input>');
				html.push('	<a href="javascript:void(0);" class="cockpit-tab-editor-moveup"><i class="vdiconfont vdicon-shangyi1"></i>上移</a>');
				html.push('	<a href="javascript:void(0);" class="cockpit-tab-editor-movedown"><i class="vdiconfont vdicon-xiayi1"></i>下移</a>');
				html.push('	<a href="javascript:void(0);" class="cockpit-tab-editor-delete"><i class="vdiconfont vdicon-trush"></i>删除</a>');
				html.push('</div>');
			}
			html.push('<div>');
			html.push('	<a href="javascript:void(0);" class="cockpit-tab-editor-add"><i class="vdiconfont vdicon-add"></i>添加</a>');
			html.push('</div>');
			html.push('</div>');
			layer.open({
				type: 1,
				area:['400px', '400px'],
				move: false,
				maxmin: false,
				fixed: true,
				content: html.join(""),
				scrollbar: false,
				shade: 0.3,
				shadeClose: true,
				zIndex: layer.zIndex,
				closeBtn: 1,
				title: "TAB页签管理",
				btn: ["确定", "取消"], 
				success: function(layero, index) {
				},
				yes: function(index, layero) {
					var t = [];
					crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-tabs > li > a[data-cptabnum]").each(function() {
						var _$t = $(this);
						var _i = _$t.attr("data-cptabnum");
						$(this).attr("data-cptabnum_old", _i).removeAttr("data-cptabnum");
						crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel[data-cptabnum='" + _i + "']").attr("data-cptabnum_old", _i).removeAttr("data-cptabnum");
					});
					layero.find(".cockpit-tab-editor input[name='name']").each(function(oi) {
						var _$t = $(this);
						var _i = _$t.data("cptabindex");
						if (_i != undefined) {
							var tb = crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-tabs > li > a[data-cptabnum_old='" + _i + "']");
							var tp = crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel[data-cptabnum_old='" + _i + "']");
							tb.attr("data-cptabnum", oi);
							tb.text(_$t.val());
							tb.removeAttr("data-cptabnum_old");
							tp.attr("data-cptabnum", oi);
							tp.removeAttr("data-cptabnum_old");
							if (oi == 0) {
								crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-tabs").prepend(tb.parent());
								crcockpit.ele.find(".cockpit > .cockpit-tabpanels").prepend(tp);
							} else {
								crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-tabs > li:eq(" + (oi - 1) + ")").after(tb.parent());
								crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel:eq(" + (oi - 1) + ")").after(tp);
							}
							t.push($.extend(true, crcockpit.data.tabs[+_i], {name: _$t.val()}));
						} else {
							if (oi == 0) {
								crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-tabs").prepend('<li><a href="javascript:void(0);" data-cptabnum="' + oi + '">' + _$t.val() + '</a></li>');
								crcockpit.ele.find(".cockpit > .cockpit-tabpanels").prepend('<div class="cockpit-tabpanel" data-cptabnum="' + oi + '"></div>');
							} else {
								crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-tabs > li:eq(" + (oi - 1) + ")").after('<li><a href="javascript:void(0);" data-cptabnum="' + oi + '">' + _$t.val() + '</a></li>');
								crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel:eq(" + (oi - 1) + ")").after('<div class="cockpit-tabpanel" data-cptabnum="' + oi + '"></div>');
							}
							t.push({name: _$t.val(), charts: []});
						}
					});
					crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-tabs > li > a[data-cptabnum_old]").remove();
					crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-tabs > li:eq(0) > a[class!='cockpit-tabs-add']").click();
					crcockpit.data.tabs = t;
					layer.close(index);
				},
				cancel: function(index, layero) {
				}
			});
		});
		
		$(document).on("click", ".cockpit-tab-editor .cockpit-tab-editor-add", function() {
			var $t = $(this);
			var html = [];
			html.push('<div>');
			html.push('	<input type="text" name="name" value=""></input>');
			html.push('	<a href="javascript:void(0);" class="cockpit-tab-editor-moveup"><i class="vdiconfont vdicon-shangyi1"></i>上移</a>');
			html.push('	<a href="javascript:void(0);" class="cockpit-tab-editor-movedown"><i class="vdiconfont vdicon-xiayi1"></i>下移</a>');
			html.push('	<a href="javascript:void(0);" class="cockpit-tab-editor-delete"><i class="vdiconfont vdicon-trush"></i>删除</a>');
			html.push('</div>');
			$t.parent().before(html.join(''));
			$t.parent().parent().scrollTop($t.parent().parent().scrollTop() + $t.parent().parent().height());
		});
		$(document).on("click", ".cockpit-tab-editor .cockpit-tab-editor-moveup", function() {
			var $t = $(this);
			if ($t.parent().index() == 0) return;
			$t.parent().prev().before($t.parent());
		});
		$(document).on("click", ".cockpit-tab-editor .cockpit-tab-editor-movedown", function() {
			var $t = $(this);
			if ($t.parent().index() >= $t.parent().parent().find(".cockpit-tab-editor-add").parent().index() - 1) return;
			$t.parent().next().after($t.parent());
		});
		$(document).on("click", ".cockpit-tab-editor .cockpit-tab-editor-delete", function() {
			var $t = $(this);
			$.confirm("删除后将无法恢复，是否确定？", function(r) {
				if (r){
					$t.parent().remove();
				}
			});
		});
		var dragChart = undefined;
		// drop事件，拖放的目标元素，其他元素被拖放到本元素中
		crcockpit.ele.on("drop", ".cockpit > .cockpit-tabpanels > .cockpit-tabpanel", function(e) {
			if (!dragChart) return;
			var cd = crcockpit.chartdefs[dragChart.code];
			if (!cd) return;
			var c = $(this);
			var m = {x: e.originalEvent.x - dragChart.x, y: e.originalEvent.y - dragChart.y};
			var area = {
				x: c.offset().left, 
				y: c.offset().top,
				w: c.width(),
				h: c.height()
			}
			var p = {
				x: Math.round((m.x - area.x) * crcockpit.width / area.w) + 1,
				y: Math.round((m.y - area.y) * crcockpit.height / area.h) + 1
			};
			var chart = {
				id: generateId(),
				code: cd.code,
				xaxis: p.x > crcockpit.maxXaxis ? crcockpit.maxXaxis : p.x,
				yaxis: p.y > crcockpit.maxYaxis ? crcockpit.maxYaxis : p.y,
				width: crcockpit.minWidth,
				height: crcockpit.minHeight
			};
			crcockpit.data.tabs[c.index()].charts[chart.id] = chart;
			crcockpit.paintChart(chart, c);
			dragChart = undefined;
		});
		// drop事件，拖放的目标元素，其他元素被拖放到本元素中
		crcockpit.ele.on("dragover", ".cockpit > .cockpit-tabpanels > .cockpit-tabpanel", function(e) {
			var c = $(this);
			e.preventDefault();
		});
//		crcockpit.ele.on("drop", ".cockpit > .cockpit-tabpanels > .cockpit-tabpanel > .cockpit-box", function(e) {
//			var c = $(this);
//			return false;
		// dragstart事件，被拖动的元素，开始拖放触发
		crcockpit.ele.on("dragstart", ".cockpitcharts > .cockpitcharts-list > a", function(e) {
			if (crcockpit.data.tabs.length == 0) {
				$.warning("请先建立驾驶舱页签", function() {
					crcockpit.ele.find(".cockpit > .cockpit-header > .cockpit-tabs > li > a.cockpit-tabs-add").trigger("click");
				});
				return false;
			}
			var c = $(this);
			dragChart = {
				code: c.data("cpcode"),
				x:	e.originalEvent.x - c.offset().left,
				y: e.originalEvent.y - c.offset().top
			}
			e.stopPropagation(); //阻止冒泡
		});
		// dragend事件，拖放的对象元素，拖放操作结束
		crcockpit.ele.on("dragend", ".cockpitcharts > .cockpitcharts-list > a", function(e) {
			var c = $(this);
			dragChart = undefined;
		});
		crcockpit.ele.on("click", ".cockpit > .cockpit-tabpanels > .cockpit-tabpanel > .cockpit-box .cockpit-chart-delete", function(e) {
			var c = $(this);
			var cele = c.parent().parent();
			var tele = cele.parent();
			delete crcockpit.data.tabs[tele.index()].charts[cele.data("cpid")];
			cele.remove();
		});
		var moving = undefined;
		var setMoving = function(ele, e) {
			moving = {
				chart: ele.parent().parent(),
				tab: ele.parent().parent().parent(),
				x: e.originalEvent.x, 
				y: e.originalEvent.y
			}
			moving.chart.addClass("moving");
		};
		var moveMoving = function(e) {
			if (moving && e.originalEvent.buttons == 1) {
				var x = moving.chart.position().left + e.originalEvent.x - moving.x;
				var y = moving.chart.position().top + e.originalEvent.y - moving.y;
				var x2 = moving.tab.width() - moving.chart.outerWidth();
				var y2 = moving.tab.height() - moving.chart.outerHeight();
				if (x < 0) x = 0; if (y < 0) y = 0; if (x > x2) x = x2; if (y > y2) y = y2;
				moving.chart.css({
					"left": x,
					"top": y
				});
				moving.x = e.originalEvent.x; 
				moving.y = e.originalEvent.y;
			}
		}
		var finishMoving = function(e, tele) {
			if (moving) {
				var c = $(this);
				moving.chart.removeClass("moving");
				var x = moving.chart.position().left + e.originalEvent.x - moving.x;
				var y = moving.chart.position().top + e.originalEvent.y - moving.y;
				var x2 = moving.tab.width() - moving.chart.outerWidth();
				var y2 = moving.tab.height() - moving.chart.outerHeight();
				if (x < 0) x = 0; if (y < 0) y = 0; if (x > x2) x = x2; if (y > y2) y = y2;
				moving.chart.removeAttr("style");
				var chart = crcockpit.data.tabs[tele.index()].charts[moving.chart.data("cpid")];
				moving.chart.removeClass("cockpit-left-" + chart.xaxis);
				moving.chart.removeClass("cockpit-top-" + chart.yaxis);
				chart.xaxis = Math.round(x * crcockpit.width / moving.tab.width()) + 1;
				chart.yaxis = Math.round(y * crcockpit.height / moving.tab.height()) + 1;
				moving.chart.addClass("cockpit-left-" + chart.xaxis ).addClass("cockpit-top-" + chart.yaxis);
				moving = undefined;
			}
		}
		crcockpit.ele.on("mousedown", ".cockpit > .cockpit-tabpanels > .cockpit-tabpanel > .cockpit-box .cockpit-chart-drag", function(e) {
			var c = $(this);
			setMoving(c, e);
		});
		var resizing = undefined;
		var setResizing = function(ele, e, direction) {
			resizing = {
				chart: ele.parent().parent(),
				tab: ele.parent().parent().parent(),
				cx: ele.parent().parent().position().left,
				cy: ele.parent().parent().position().top,
				cw: ele.parent().parent().outerWidth(),
				ch: ele.parent().parent().outerHeight(),
				x: e.originalEvent.x, 
				y: e.originalEvent.y,
				direction: direction
			}
			resizing.chart.addClass("moving");
		};
		var resizeResizing = function(e) {
			if (resizing && e.originalEvent.buttons == 1) {
				var x = e.originalEvent.x - resizing.tab.offset().left, 
					y = e.originalEvent.y - resizing.tab.offset().top;
				if (resizing.direction == "n") {
					var miny = 0, maxy = resizing.cy + resizing.ch - (resizing.tab.height() * crcockpit.minHeight / crcockpit.height);
					if (y < miny) y = miny; if (y> maxy) y = maxy;
					resizing.chart.css({"top": y, "height": resizing.ch + resizing.cy - y});
				} else if (resizing.direction == "s") {
					var miny = resizing.cy + (resizing.tab.height() * crcockpit.minHeight / crcockpit.height), maxy = resizing.tab.height();
					if (y < miny) y = miny; if (y> maxy) y = maxy;
					resizing.chart.css({"height": y - resizing.cy});
				} else if (resizing.direction == "w") {
					var minx = 0, maxx = resizing.cx + resizing.cw - (resizing.tab.width() * crcockpit.minWidth / crcockpit.width);
					if (x < minx) x = minx; if (x> maxx) x = maxx;
					resizing.chart.css({"left": x, "width": resizing.cw + resizing.cx - x});
				} else if (resizing.direction == "e") {
					var minx = resizing.cx + (resizing.tab.width() * crcockpit.minWidth / crcockpit.width), maxx = resizing.tab.width();
					if (x < minx) x = minx; if (x> maxx) x = maxx;
					resizing.chart.css({"width": x - resizing.cx});
				}
			}
		};
		var finishResizing = function(e, tele) {
			if (resizing) {
				resizing.chart.removeClass("moving");
				var c = {
					x: resizing.chart.position().left,
					y: resizing.chart.position().top,
					w: resizing.chart.outerWidth(),
					h: resizing.chart.outerHeight()
				}
				resizing.chart.removeAttr("style");
				var chart = crcockpit.data.tabs[tele.index()].charts[resizing.chart.data("cpid")];
				resizing.chart.removeClass("cockpit-left-" + chart.xaxis);
				resizing.chart.removeClass("cockpit-top-" + chart.yaxis);
				resizing.chart.removeClass("cockpit-width-" + chart.width);
				resizing.chart.removeClass("cockpit-height-" + chart.height);
				chart.xaxis = Math.round(c.x * crcockpit.width / resizing.tab.width()) + 1;
				chart.yaxis = Math.round(c.y * crcockpit.height / resizing.tab.height()) + 1;
				chart.width = Math.round(c.w * crcockpit.width / resizing.tab.width());
				chart.height = Math.round(c.h * crcockpit.height / resizing.tab.height());
				resizing.chart.addClass("cockpit-left-" + chart.xaxis).addClass("cockpit-top-" + chart.yaxis).addClass("cockpit-width-" + chart.width).addClass("cockpit-height-" + chart.height);
				resizing = undefined;
			}
		}
		crcockpit.ele.on("mousedown", ".cockpit > .cockpit-tabpanels > .cockpit-tabpanel > .cockpit-box .cockpit-chart-resizetop", function(e) {
			var c = $(this);
			setResizing(c, e, "n");
		});
		crcockpit.ele.on("mousedown", ".cockpit > .cockpit-tabpanels > .cockpit-tabpanel > .cockpit-box .cockpit-chart-resizeright", function(e) {
			var c = $(this);
			setResizing(c, e, "e");
		});
		crcockpit.ele.on("mousedown", ".cockpit > .cockpit-tabpanels > .cockpit-tabpanel > .cockpit-box .cockpit-chart-resizebottom", function(e) {
			var c = $(this);
			setResizing(c, e, "s");
		});
		crcockpit.ele.on("mousedown", ".cockpit > .cockpit-tabpanels > .cockpit-tabpanel > .cockpit-box .cockpit-chart-resizeleft", function(e) {
			var c = $(this);
			setResizing(c, e, "w");
		});
		crcockpit.ele.on("mousemove", ".cockpit > .cockpit-tabpanels > .cockpit-tabpanel", function(e) {
			if (moving && e.originalEvent.buttons == 1) {
				moveMoving(e);
			} else if (resizing && e.originalEvent.buttons == 1) {
				resizeResizing(e);
			}
		});
		crcockpit.ele.on("mouseup", ".cockpit > .cockpit-tabpanels > .cockpit-tabpanel", function(e) {
			if (moving) {
				finishMoving(e, $(this));
			} else if (resizing) {
				finishResizing(e, $(this));
			}
		});
		$(window).on("mouseup", function(e) {
			if (moving) {
				finishMoving(e, crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel.active"));
			} else if (resizing) {
				finishResizing(e, crcockpit.ele.find(".cockpit > .cockpit-tabpanels > .cockpit-tabpanel.active"));
			}
		});
		crcockpit.ele.on("click", ".cockpitdef > .cockpitdef-field > button.btnSave", function() {
			crcockpit.ele.trigger("save", crcockpit.getData(false));
		});
		crcockpit.ele.on("click", ".cockpitdef > .cockpitdef-field > button.btnPreview", function() {
			crcockpit.ele.trigger("preview", crcockpit.getData(true));
		});
	};
	
	crcockpit.getData = function(preview) {
		var data = $.extend(true, {}, crcockpit.data);
		if (preview) {
			data.supporter = crcockpit.supports.supporter;
			data.hotline = crcockpit.supports.hotline;
			data.weixin = crcockpit.supports.weixin;
			data.website = crcockpit.supports.website;
		}
		for (var i = 0, tab = data.tabs[i]; i < data.tabs.length; i ++, tab = data.tabs[i]) {
			var charts = [];
			for (var id in tab.charts) {
				var chart = tab.charts[id];
				delete chart["id"];
				if (preview) {
					var cd = crcockpit.chartdefs[chart.code];
					chart.name = cd.name;
					chart.refresh_time = cd.refresh_time;
					chart.childcode = cd.childcode;
					chart.childname = cd.childname;
				}
				charts.push(chart);
			}
			data.tabs[i].charts = charts;
		}
		return data;
	}
	
	crcockpit.getImage = function(callback) {
		var c = crcockpit.ele.find(".cockpit");
		if (crcockpit.data.tabs.length <= 1) {
			c.find(".cockpit-header > .cockpit-tabs").hide();
		}
		html2canvas(c[0], {
			scale: crcockpit.previewWidth / c.width()
		}).then(function(canvas) {
			c.find(".cockpit-header > .cockpit-tabs").show();
			callback(canvas);
		});
	}

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

	crcockpit.create = function(ele, options) {
		options = options || {};
		
		crcockpit.ele = $(ele);
		var res = [];
		res.push([crcockpit.base_url, 'css', 'vdiconfont', 'iconfont.css'].join("/"));
		res.push([crcockpit.base_url, 'css', 'cockpitdesigner.css'].join("/"));
		res.push([crcockpit.base_url, 'html2canvas', 'html2canvas.min.js'].join("/"));
		
		crcockpit.xaxis = 12;
		crcockpit.yaxis = 12;
		crcockpit.width = 12;
		crcockpit.height = 12;
		crcockpit.minWidth = 2;
		crcockpit.minHeight = 3;
		crcockpit.maxXaxis = crcockpit.xaxis - crcockpit.minWidth + 1;
		crcockpit.maxYaxis = crcockpit.yaxis - crcockpit.minHeight + 1;
		crcockpit.previewWidth = options.previewWidth || 640;
		crcockpit.time = crcockpit.formatTime(new Date());
		crcockpit.supports = {
			supporter: options.supporter || "成都超然祥润科技有限公司·医疗事业部",
			hotline: options.hotline || "400-000-7932 028-85534408",
			weixin: options.weixin || "chaoran_jingpei",
			website: options.website || "http://www.crtech.cn"
		}
		crcockpit.charts = {}; //JS SDK
		crcockpit.chartdefs = {};
		if (options.chartdefs) {
			for (var i = 0, chart = options.chartdefs[i]; i < options.chartdefs.length; i ++, chart = options.chartdefs[i]) {
				res.push([crcockpit.base_url, 'charts', chart.code + '.js'].join("/"));
				crcockpit.chartdefs[chart.code] = $.extend(true, {}, chart);
			}
		}
		crcockpit.templates = [];
		if (options.templates) {
			for (var i = 0, t = options.templates[i]; i < options.templates.length; i ++, t = options.templates[i]) {
				crcockpit.templates.push({
					code: t.bianh,
					name: t.dictlist
				});
			}
		}
		if (crcockpit.templates.length == 0) {
			crcockpit.templates.push({
				code: "space",
				name: "太空"
			});
		}
		crcockpit.data = {
			id: options.id || "",
			name: options.name || "新建驾驶舱",
			template: options.template || "space",
			tabs: []
		}
		if (options.tabs) {
			for (var i = 0, t = options.tabs[i]; i < options.tabs.length; i ++, t = options.tabs[i]) {
				var tab = {
					name: t.name,
					charts: {}
				};
				for (var j = 0, c = t.charts[j]; j < t.charts.length; j ++, c = t.charts[j]) {
					var chart = {
							id: generateId(),
							code: c.code,
							xaxis: c.xaxis,
							yaxis: c.yaxis,
							width: c.width,
							height: c.height
					}
					tab.charts[chart.id] = chart;
				}
				crcockpit.data.tabs.push(tab);
			}
		}
		if (crcockpit.data.tabs.length == 0) {
			crcockpit.data.tabs.push({
				name: '默认页签',
				charts: {}
			});
		}
		res.push([crcockpit.base_url, 'template', crcockpit.data.template, 'style.css'].join("/"));

		loadRES(res, function() {
			crcockpit.paint();
			crcockpit.bindEvent();
		}, function(jsurl, evt) {
			if (window.console) {
				window.console.error("load cockpit template [" + jsurl + "] failed.");
			}
		});
};

	crcockpit.inited = true;
})($crcockpit, window);