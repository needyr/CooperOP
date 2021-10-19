(function(crcockpit, window) {
	crcockpit.charts.cpfuh = {
		"icon": "vdiconfont vdicon-fsux_tubiao_biaoge",
		"loadDeptGroup": function($this, callback, error) {
			var group = $this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-thead > dl > dt > select").val();
			$crcockpit.queryCockpitData({code: "dept_group"}, {}, function(rs) {
				html = ['<option value="">全部科室分组</option>'];
				for (var i = 0, o = rs.resultset[i]; i < rs.resultset.length; i ++, o = rs.resultset[i]) {
					html.push('<option value="' + o.id + '">' + o.name + '</option>')
				}
				$this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-thead > dl > dt > select").html(html.join("")).val(group);
				callback();
			}, error);
		},
		"loadPiciGroup": function($this, callback, error) {
			var picizu = +$this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a.active").data("id");
			$crcockpit.queryCockpitData({code: "pici_group"}, {}, function(ps) {
				html = [];
				for (var i = 0, o = ps.resultset[i]; i < ps.resultset.length; i ++, o = ps.resultset[i]) {
					html.push('<li><a href="javascript:void(0)" data-id="' + o.id + '" data-start="' + (o.start_time || "") + '" data-end="' + (o.end_time || "") + '" class="' + (i == picizu ? "active" : "") + '">' + o.name + '</a></li>')
				}
				
				$this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup").html(html.join(""));
				callback();
			}, error);
		},
		"computePiciGroup": function($this, data, picinum) {
			var width = +$this.find(".cockpit-chart-content > .cockpit-chart-list > .cockpit-chart-list-thead").width();
			var tw = +$this.find(".cockpit-chart-content > .cockpit-chart-list > .cockpit-chart-list-thead > dl > dt").outerWidth();
			width -= tw;
			var limit = Math.floor(width / tw / 1.8);
			var picizu = $this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a.active").data("id");
			var userselected = $this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup").is(".userselected");
			$this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a").removeClass("active");
			//计算当天时间是那个批次组默认
			if (!userselected) {
				var now = new Date();
				var date = now.getFullYear() + '-' + (now.getMonth() + 1) + '-' + now.getDate();
				$this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a").each(function() {
					$t = $(this);
					if ($t.data("start") && $t.data("end")) {
						var start_time = new Date(date + " " + $t.data("start")), end_time = new Date(date + " " + $t.data("end"));
						if (end_time <= start_time) {
							if (now < end_time) {
								start_time.setDate(start_time.getDate() - 1);
							} else {
								end_time.setDate(end_time.getDate() + 1);
							}
						}
						if (now >= start_time && now < end_time) {
							picizu = $t.data("id");
							return false;
						}
					}
				});
			}
			var picis = [];
			for (var i = 1; i <= picinum; i ++) {
				if (((+picizu > 0 && +data[0]["picizu_" + i] == +picizu) || (+picizu == 0)) && picis.length <= limit) {
					picis.push({
						picino: i,
						picizu_id: +data[0]["picizu_" + i],
						picizu_name: $this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a[data-id='" + data[0]["picizu_" + i] + "']").text()
					});
					if (picis.length >= limit) break;
				} 
			}
			if (userselected || picis.length > 0) {
				$this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a[data-id='" + picizu + "']").addClass("active");
			}
			if (!userselected && picis.length < limit && +picizu > 0) {
				for (var i = 1; i <= picinum; i ++) {
					if (+data[0]["picizu_" + i] > +picizu && picis.length <= limit) {
						if (!$this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a[data-id='" + data[0]["picizu_" + i] + "']").is(".active")) {
							$this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a[data-id='" + data[0]["picizu_" + i] + "']").addClass("active");
						}
						picis.push({
							picino: i,
							picizu_id: +data[0]["picizu_" + i],
							picizu_name: $this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a[data-id='" + data[0]["picizu_" + i] + "']").text()
						});
						if (picis.length >= limit) break;
					}
				}
				if (picis.length < limit) {
					for (var i = picinum; i >= 1; i --) {
						if (+data[0]["picizu_" + i] < +picizu && picis.length <= limit) {
							if (!$this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a[data-id='" + data[0]["picizu_" + i] + "']").is(".active")) {
								$this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a[data-id='" + data[0]["picizu_" + i] + "']").addClass("active");
							}
							picis.unshift({
								picino: i,
								picizu_id: +data[0]["picizu_" + i],
								picizu_name: $this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a[data-id='" + data[0]["picizu_" + i] + "']").text()
							});
							if (picis.length >= limit) break;
						}
					}
				}
			}
			return picis;
		},
		"paint": function(chart, ele) {
			var $this = $(ele);
			$this.find(".cockpit-chart-content > .cockpit-chart-chart").addClass("cockpit-chart-list");
			
			var html = [];
			html.push('	<div class="cockpit-chart-tabs">');
			html.push('		<ul class="cpfh-day">');
			html.push('			<li><a href="javascript:void(0)" data-mark="D" class="active">当日</a></li>')
			html.push('			<li><a href="javascript:void(0)" data-mark="C">次日</a></li>')
			html.push('		</ul>');
			html.push('		<ul class="cpfh-picigroup">');
			html.push('		</ul>');
			html.push('	</div>');
			
			$this.find(".cockpit-chart-title").append(html.join(""));

			html = [];
			html.push('	<div class="cockpit-chart-list-thead">');
			html.push('		<dl>');
			html.push('			<dt>');
			html.push('				<select name="dept_group_id"><option value="">全部科室分组</option></select>');
			html.push('				<span class="select-tip"><i class="vdiconfont vdicon-dropdown"></i></span>');
			html.push('			</dt>');
			html.push('		</dl>');
			html.push('	</div>');
			html.push('	<div class="cockpit-chart-list-list">');
			html.push('		<span class="cockpit-chart-chart-loading"></span>');
			html.push('	</div>');
			html.push('<div class="cockpit-chart-list-pagination">');
			html.push('	<ul>');
			html.push('	</ul>');
			html.push('</div>');
			
			$this.find(".cockpit-chart-content > .cockpit-chart-chart").html(html.join(""));

			$this.on("click", ".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-day > li > a", function(e) {
				var $t = $(this);
				if ($t.is(".active") && $this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-day > li > a.active").length == 1) return false;
				$this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-day > li > a").removeClass("active");
				$t.addClass("active");
				$this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-day").addClass("userselected");
				$crcockpit.statistics(chart, ele);
				return false;
			});
			$this.on("click", ".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a", function(e) {
				var $t = $(this);
				if ($t.is(".active") && $this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a.active").length == 1) return false;
				$this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a").removeClass("active");
				$t.addClass("active");
				$this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup").addClass("userselected");
				$crcockpit.statistics(chart, ele);
				return false;
			});
			$this.on("click", ".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-thead > dl > dt > select", function(e) {
				return false;
			});
			$this.on("change", ".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-thead > dl > dt > select", function(e) {
				$crcockpit.statistics(chart, ele);
			});
		},
		"statistics": function(chart, ele, success, error) {
			var $this = $(ele);
			var cpid = $this.data("cpid");
			var timer = $this.data("timer");
			if (timer) {
				clearTimeout(timer);
				timer = 0;
			}
			clearInterval($crcockpit.timers[cpid]);
			
			$crcockpit.charts.cpfuh.loadDeptGroup($this, function() {
				$crcockpit.charts.cpfuh.loadPiciGroup($this, function() {
					$crcockpit.queryCockpitData(chart, {dept_group_id: $this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-thead > dl > dt > select").val(),
						date_mark: $this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-day > li > a.active").data("mark")}, function(rs) {
						var data = rs.resultset;
//						{
//						id: 20200210232107102001
//						schedule_date: 20200210,
//						analysis_time: new Date().getTime(),
//						dept_group_id: "1",
//						dept_group_name: "早一",
//						dept_code: "6202778",
//						dept_name: "儿科护理站",
//						dept_name_short: "儿科",
//						pici_num: 4,
//						picizu: 1,
//						jiaojie_flag: 1,
//						pici_no_1: "00",
//						pici_count_1: 0,
//						peizhi_count_1: 0,
//						dabao_count_1: 0,
//						peizhi_fuh_count_1: 0,
//						peizhi_notfuh_count_1: 0,
//						dabao_fuh_count_1: 0,
//						dabao_notfuh_count_1: 0,
//						pici_no_2: "08",
//						......
//						dabao_notfuh_count_20: 0,
//						dept_group_order_no: 0,
//						dept_order_no: 0
//						}

						var time = new Date().getTime();
						if (data.length == 0) {
							$this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-thead > dl > dd").remove();
							$this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-list").html('<div class="cockpit-chart-nodata">无统计数据</div>');
							$this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-pagination > ul").html('');
							if (+chart.refresh_time > 0) {
								crcockpit.timers[cpid] = setInterval(function() {
									crcockpit.statistics(chart, $this);
								}, (+chart.refresh_time || 60) * 1000);
							}
						} else {
							time = data[0].analysis_time;
							var picinum = data[0].pici_num;

							var html = [];

							$this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-thead > dl > dd").remove();
							
							var picis = $crcockpit.charts.cpfuh.computePiciGroup($this, data, picinum);
							
							for (var i = 0, pici = picis[i]; i < picis.length; i ++, pici = picis[i]) {
								html.push('			<dd><div class="cockpit-chart-list-th">' + data[0]["pici_no_" + pici.picino] + '批次(' + pici.picizu_name.substring(0, 1) + ')</div><span>打包</span><span>配置</span><span>交接</span></dd>');
							}
							
							$this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-thead > dl").append(html.join(''));

							html = ['<div>'];
							for (var j = 0, r = data[j]; j < data.length; j ++, r = data[j]) {
								html.push('	<dl>');
								html.push('		<dt title="' + r.dept_name + '">' + (r.dept_name_short || r.dept_name) + '</dt>');
								for (var i = 0, pici = picis[i]; i < picis.length; i ++, pici = picis[i]) {
									html.push('			<dd><span><span class="' + (+r["dabao_fuh_count_" + pici.picino] < +r["dabao_count_" + pici.picino] ? 'c-green' : '') + '">' + (r["dabao_fuh_count_" + pici.picino] || 0) + 
											'</span>/' + (r["dabao_count_" + pici.picino] || 0) + '</span><span><span class="' + (+r["peizhi_fuh_count_" + pici.picino] < +r["peizhi_count_" + pici.picino] ? 'c-green' : '') + '">' + 
											(r["peizhi_fuh_count_" + pici.picino] || 0) + '</span>/' + (r["peizhi_count_" + pici.picino] || 0) + '</span><i class="' +
											(+r["jiaojie_flag_" + pici.picino] == 1 ? 'cpfuh-jiaojie' : '') + '"></i></dd>');
								}
								html.push('		</dl>');
							}
							html.push('</div>');
							var le = $this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-list");
							le.children("div").css("top", "0px");
							le.html(html.join(''));
							var de = $this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-list > div > dl:eq(0)");
							
							var pn = Math.floor((le.innerHeight() + de.outerHeight() / 4 ) / de.outerHeight()); //最后一行显示超过3/4则当全部显示了
							var ph = de.outerHeight() * pn;
							var ps = Math.ceil(rs.count / pn);
							
							html = [];
							for (var j = 0; j < ps; j ++) {
								html.push('<li><a ');
								html.push(j == 0 ? 'class="active" ' : '');
								html.push('href="javascript:void(0);"></a>');
							}
							var pe = $this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-pagination > ul");

							pe.html(html.join(''));
							
							
							var notimer = false;
							var changePage = function(page) {
								if (timer) clearTimeout(timer);
								pe.find("li > a").removeClass("active");
								if (+chart.refresh_time == 0) {
									if (page > ps - 1) page = 0;
								} else {
									if (page > ps - 1) page = ps - 1;
								}
								pe.find("li:eq(" + page + ") > a").addClass("active");
								le.children("div").css("top", "-" + ph * page + "px");
								if (!notimer) {
									timer = setTimeout(function() {
										changePage(page + 1);
									}, (+chart.refresh_time || 10) * 1000);
									$this.data("timer", timer);
								}
							}
							
							pe.find("li > a").on("click", function() {
								var index = $(this).parent().index();
								notimer = true;
								changePage(index);
								return false;
							});
							
							pe.on("click", function() {
								return false;
							});
							
							var ttm;
							le.on("mousewheel", function(e) {
								if (Math.abs(e.originalEvent.deltaY) > 5 || Math.abs(e.originalEvent.deltaX) > 5) {
									if (ttm) clearTimeout(ttm);
									e = e || event;
									var index = pe.find("li > a.active").parent().index();
									if (+e.originalEvent.deltaY > 0 || +e.originalEvent.deltaX > 0) {
										index ++;
										if (index > ps - 1 ) index = ps - 1;
									} else if (+e.originalEvent.deltaY < 0 || +e.originalEvent.deltaX < 0) {
										index --;
										if (index < 0) index = 0;
									}
									notimer = true;
									setTimeout(function() {
										changePage(index);
									}, 100);
								}
							});

							
							var hammerBox = new Hammer(le[0]);
							hammerBox.get('swipe').set({
								direction: Hammer.DIRECTION_VERTICAL
							});
							hammerBox.on('swipe', function(ev) {
								var index = pe.find("li > a.active").parent().index();
								if (ev.offsetDirection == Hammer.DIRECTION_DOWN) {
									index --;
									if (index < 0) index = 0;
								} else {
									index ++;
									if (index > ps - 1 ) index = ps - 1;
								}
								notimer = true;
								changePage(index);
							});
							
							changePage(0);
							if (+chart.refresh_time > 0) {
								crcockpit.timers[cpid] = setInterval(function() {
									clearTimeout(timer);
									crcockpit.statistics(chart, $this);
								}, (+chart.refresh_time || 60) * ps * 1000);
							}
						}
						
						$this.find(".cockpit-chart-title > .cockpit-chart-time").text(crcockpit.formatTime(new Date(time)));
						
						success();
					}, error);
				}, error);
			}, error);
			
		},
		"paint_full": function(chart, ele, params) {
			var $this = $(ele);
			$this.find(".cockpit-chart-content > .cockpit-chart-chart").addClass("cockpit-chart-list");
			
			var html = [];
			html.push('	<div class="cockpit-chart-tabs">');
			html.push('		<ul class="cpfh-day">');
			html.push('			<li><a href="javascript:void(0)" data-mark="D" class="active">当日</a></li>')
			html.push('			<li><a href="javascript:void(0)" data-mark="C">次日</a></li>')
			html.push('		</ul>');
			html.push('		<ul class="cpfh-picigroup">');
			html.push('		</ul>');
			html.push('	</div>');
			
			$this.find(".cockpit-chart-title").append(html.join(""));

			html = [];
			html.push('	<div class="cockpit-chart-list-thead">');
			html.push('		<dl>');
			html.push('			<dt>');
			html.push('				<select name="dept_group_id"><option value="">全部科室分组</option></select>');
			html.push('				<span class="select-tip"><i class="vdiconfont vdicon-dropdown"></i></span>');
			html.push('			</dt>');
			html.push('		</dl>');
			html.push('	</div>');
			html.push('	<div class="cockpit-chart-list-list">');
			html.push('		<span class="cockpit-chart-chart-loading"></span>');
			html.push('	</div>');
			html.push('<div class="cockpit-chart-list-pagination">');
			html.push('	<ul>');
			html.push('	</ul>');
			html.push('</div>');
			
			$this.find(".cockpit-chart-content > .cockpit-chart-chart").html(html.join(""));

			$this.on("click", ".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-day > li > a", function(e) {
				var $t = $(this);
				if ($t.is(".active") && $this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-day > li > a.active").length == 1) return false;
				$this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-day > li > a").removeClass("active");
				$t.addClass("active");
				$this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-day").addClass("userselected");
				$crcockpit.statisticsfull(chart, ele, params);
				return false;
			});
			$this.on("click", ".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a", function(e) {
				var $t = $(this);
				if ($t.is(".active") && $this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a.active").length == 1) return false;
				$this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup > li > a").removeClass("active");
				$t.addClass("active");
				$this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-picigroup").addClass("userselected");
				$crcockpit.statisticsfull(chart, ele, params);
				return false;
			});
			$this.on("click", ".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-thead > dl > dt > select", function(e) {
				return false;
			});
			$this.on("change", ".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-thead > dl > dt > select", function(e) {
				$crcockpit.statisticsfull(chart, ele, params);
			});
		},
		"statistics_full": function(chart, ele, params, success, error) {
			var $this = $(ele);
			var cpopid = $this.data("cpopid");  //这里要拿弹框的id
			var timer = $this.data("timer");
			if (timer) {
				clearTimeout(timer);
				timer = 0;
			}
			clearInterval($crcockpit.poptimer[cpopid]);
			
			$crcockpit.charts.cpfuh.loadDeptGroup($this, function() {
				$crcockpit.charts.cpfuh.loadPiciGroup($this, function() {
					
					$crcockpit.queryFullData(chart, {dept_group_id: $this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-thead > dl > dt > select").val(),
						date_mark: $this.find(".cockpit-chart-title > .cockpit-chart-tabs > ul.cpfh-day > li > a.active").data("mark")}, function(rs) {
						var data = rs.resultset;
		//				{
		//				id: 20200210232107102001
		//				schedule_date: 20200210,
		//				analysis_time: new Date().getTime(),
		//				dept_group_id: "1",
		//				dept_group_name: "早一",
		//				dept_code: "6202778",
		//				dept_name: "儿科护理站",
		//				dept_name_short: "儿科",
		//				pici_num: 4,
		//				picizu: 1,
		//				jiaojie_flag: 1,
		//				pici_no_1: "00",
		//				pici_count_1: 0,
		//				peizhi_count_1: 0,
		//				dabao_count_1: 0,
		//				peizhi_fuh_count_1: 0,
		//				peizhi_notfuh_count_1: 0,
		//				dabao_fuh_count_1: 0,
		//				dabao_notfuh_count_1: 0,
		//				pici_no_2: "08",
		//				......
		//				dabao_notfuh_count_20: 0,
		//				dept_group_order_no: 0,
		//				dept_order_no: 0
		//				}
		
						var time = new Date().getTime();
						if (data.length == 0) {
							$this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-thead > dl > dd").remove();
							$this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-list").html('<div class="cockpit-chart-nodata">无统计数据</div>');
							$this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-pagination > ul").html('');
							if (+chart.refresh_time > 0) {
								$crcockpit.poptimer[cpopid] = setInterval(function() {
									$crcockpit.statisticsfull(chart, $this, params);
								}, (+chart.refresh_time || 60) * 1000);
							}
						} else {
							time = data[0].analysis_time;
							var picinum = data[0].pici_num;
		
							var html = [];
		
							$this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-thead > dl > dd").remove();
							
							var picis = $crcockpit.charts.cpfuh.computePiciGroup($this, data, picinum);
							
							for (var i = 0, pici = picis[i]; i < picis.length; i ++, pici = picis[i]) {
								html.push('			<dd><div class="cockpit-chart-list-th">' + data[0]["pici_no_" + pici.picino] + '批次(' + pici.picizu_name.substring(0, 1) + ')</div><span>打包</span><span>配置</span><span>交接</span></dd>');
							}
							
							$this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-thead > dl").append(html.join(''));

							html = ['<div>'];
							for (var j = 0, r = data[j]; j < data.length; j ++, r = data[j]) {
								html.push('	<dl>');
								html.push('		<dt title="' + r.dept_name + '">' + (r.dept_name_short || r.dept_name) + '</dt>');
								for (var i = 0, pici = picis[i]; i < picis.length; i ++, pici = picis[i]) {
									html.push('			<dd><span><span class="' + (+r["dabao_fuh_count_" + pici.picino] < +r["dabao_count_" + pici.picino] ? 'c-green' : '') + '">' + (r["dabao_fuh_count_" + pici.picino] || 0) + 
											'</span>/' + (r["dabao_count_" + pici.picino] || 0) + '</span><span><span class="' + (+r["peizhi_fuh_count_" + pici.picino] < +r["peizhi_count_" + pici.picino] ? 'c-green' : '') + '">' + 
											(r["peizhi_fuh_count_" + pici.picino] || 0) + '</span>/' + (r["peizhi_count_" + pici.picino] || 0) + '</span><i class="' +
											(+r["jiaojie_flag_" + pici.picino] == 1 ? 'cpfuh-jiaojie' : '') + '"></i></dd>');
								}
								html.push('		</dl>');
							}
							html.push('</div>');
							var le = $this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-list");
							le.children("div").css("top", "0px");
							le.html(html.join(''));
							var de = $this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-list > div > dl:eq(0)");
							
							var pn = Math.floor((le.innerHeight() + de.outerHeight() / 4 ) / de.outerHeight()); //最后一行显示超过3/4则当全部显示了
							var ph = de.outerHeight() * pn;
							var ps = Math.ceil(rs.count / pn);
							
							html = [];
							for (var j = 0; j < ps; j ++) {
								html.push('<li><a ');
								html.push(j == 0 ? 'class="active" ' : '');
								html.push('href="javascript:void(0);"></a>');
							}
							var pe = $this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-pagination > ul");
		
							pe.html(html.join(''));
							
							
							var notimer = false;
							var changePage = function(page) {
								if (timer) clearTimeout(timer);
								pe.find("li > a").removeClass("active");
								if (page > ps - 1) page = 0;
								pe.find("li:eq(" + page + ") > a").addClass("active");
								le.children("div").css("top", "-" + ph * page + "px");
								if (!notimer) {
									timer = setTimeout(function() {
										changePage(page + 1);
									}, (+chart.refresh_time || 10) * 1000);
									$this.data("timer", timer);
								}
							}
							
							pe.find("li > a").on("click", function() {
								var index = $(this).parent().index();
								notimer = true;
								changePage(index);
								return false;
							});
							
							pe.on("click", function() {
								return false;
							});
							
							var ttm;
							le.on("mousewheel", function(e) {
								if (Math.abs(e.originalEvent.deltaY) > 5 || Math.abs(e.originalEvent.deltaX) > 5) {
									if (ttm) clearTimeout(ttm);
									e = e || event;
									var index = pe.find("li > a.active").parent().index();
									if (+e.originalEvent.deltaY > 0 || +e.originalEvent.deltaX > 0) {
										index ++;
										if (index > ps - 1 ) index = ps - 1;
									} else if (+e.originalEvent.deltaY < 0 || +e.originalEvent.deltaX < 0) {
										index --;
										if (index < 0) index = 0;
									}
									notimer = true;
									setTimeout(function() {
										changePage(index);
									}, 100);
								}
							});
							
							var hammerBox = new Hammer(le[0]);
							hammerBox.get('swipe').set({
								direction: Hammer.DIRECTION_VERTICAL
							});
							hammerBox.on('swipe', function(ev) {
								var index = pe.find("li > a.active").parent().index();
								if (ev.offsetDirection == Hammer.DIRECTION_DOWN) {
									index --;
									if (index < 0) index = 0;
								} else {
									index ++;
									if (index > ps - 1 ) index = ps - 1;
								}
								notimer = true;
								setTimeout(function() {
									changePage(index);
								}, 100);
								ev.preventDefault();
							});
							
							changePage(0);
		
							if (+chart.refresh_time > 0) {
								$crcockpit.poptimer[cpopid] = setInterval(function() {
									clearTimeout(timer);
									$crcockpit.statisticsfull(chart, $this, params);
								}, (+chart.refresh_time || 60) * ps * 1000);
							}
						}
						
						$this.find(".cockpit-chart-title > .cockpit-chart-time").text(crcockpit.formatTime(new Date(time)));
						
						success();
					}, error);
				});
			});
		}
	};
})($crcockpit, window);
