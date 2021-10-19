(function(crcockpit, window) {
	crcockpit.charts.pici_class = {
		"icon": "vdiconfont vdicon-tubiaoduoweizhuzhuangtu",
		"paint": function(chart, ele) {
			var $this = $(ele);
		},
		"statistics": function(chart, ele, success, error) {
			var $this = $(ele);
			
			$crcockpit.queryCockpitData(chart, {}, function(rs) {
				var data = rs.resultset;
//				{
//				id: 20200210232107102001
//				schedule_date: 20200210,
//				analysis_time: new Date().getTime(),
//				pzfyjch: "01",
//				pzfyjch_count: 0,
//				peizhi_count: 0,
//				nopeizhi_count: 0,
//				tuiy_count: 0
//				}
				var time = (data && data.length > 0) ? data[0].analysis_time || new Date().getTime() : new Date().getTime();
				var categories = [];
				var peizhi_count_ = [];
				var nopeizhi_count_ = [];
				var tuiy_count_ = [];
				var html = [];
				for (var j = 0, r = data[j]; j < data.length; j ++, r = data[j]) {
					categories.push(r.pzfyjch);
					peizhi_count_.push(r.peizhi_count - 0);
					nopeizhi_count_.push(r.nopeizhi_count - 0);
					tuiy_count_.push(r.tuiy_count - 0);
					html.push('		<div><span>' + r.pzfyjch + ': </span><em class="c-white">' + r.pzfyjch_count + '</em>/<em class="c-green">' + r.peizhi_count + '</em>/<em class="c-blue">' + r.nopeizhi_count + '</em>/<em class="c-red">' + r.tuiy_count + '</em></div>');
				}
				//$this.children(".cockpit-chart-content").html(html.join(''));
				$this.find(".cockpit-chart-title > .cockpit-chart-time").text(crcockpit.formatTime(new Date(time)));
				//chart
				$this.find(".cockpit-chart-content > .cockpit-chart-chart").html('');
				$this.find(".cockpit-chart-content > .cockpit-chart-chart").highcharts({
					chart: {
						type: 'bar'
					},
					title: {
						text: null
					},
					xAxis: {
						categories: categories
					},
					yAxis: {
						min: 0,
						title: {
							text: null
						},
						stackLabels: {  // 堆叠数据标签
							enabled: true
						}
					},
					legend: {
						reversed: true 
					},
					plotOptions: {
						series: {
							stacking: 'normal',
							dataLabels: {
								enabled: false
							}
						}
					},
					tooltip: {
						formatter: function () {
							return '<b>' + this.x + '</b><br/>' +
								this.series.name + ': ' + this.y + '<br/>';
						}
					},
					series: [{
						name: '退药数',
						data: tuiy_count_
					}, {
						name: '未配置',
						data: nopeizhi_count_
					}, {
						name: '已配置',
						data: peizhi_count_
					}]
				});
				success();
			}, error);
		},
		"paint_full": function(chart, ele, params) {
			var $this = $(ele);
		},
		"statistics_full": function(chart, ele, params, success, error) {
			var $this = $(ele);

			$crcockpit.queryFullData(chart, {}, function(rs) {
				var data = rs.resultset;
//				{
//				id: 20200210232107102001
//				schedule_date: 20200210,
//				analysis_time: new Date().getTime(),
//				pzfyjch: "01",
//				pzfyjch_count: 0,
//				peizhi_count: 0,
//				nopeizhi_count: 0,
//				tuiy_count: 0
//				}
				var time = (data && data.length > 0) ? data[0].analysis_time || new Date().getTime() : new Date().getTime();
				
				var categories = [];
				var peizhi_count_ = [];
				var nopeizhi_count_ = [];
				var tuiy_count_ = [];
				var html = [];
				for (var j = 0, r = data[j]; j < data.length; j ++, r = data[j]) {
					categories.push(r.pzfyjch);
					peizhi_count_.push(r.peizhi_count - 0);
					nopeizhi_count_.push(r.nopeizhi_count - 0);
					tuiy_count_.push(r.tuiy_count - 0);
					html.push('		<div><span>' + r.pzfyjch + ': </span><em class="c-white">' + r.pzfyjch_count + '</em>/<em class="c-green">' + r.peizhi_count + '</em>/<em class="c-blue">' + r.nopeizhi_count + '</em>/<em class="c-red">' + r.tuiy_count + '</em></div>');
				}
				//$this.children(".cockpit-chart-content").html(html.join(''));
				$this.find(".cockpit-chart-title > .cockpit-chart-time").text(crcockpit.formatTime(new Date(time)));
				
				//chart
				$this.find(".cockpit-chart-content > .cockpit-chart-chart").html('');
				$this.find(".cockpit-chart-content > .cockpit-chart-chart").highcharts({
					chart: {
						type: 'bar'
					},
					title: {
						text: null
					},
					xAxis: {
						categories: categories
					},
					yAxis: {
						min: 0,
						title: {
							text: null
						},
						stackLabels: {  // 堆叠数据标签
							enabled: true
						}
					},
					legend: {
						reversed: true 
					},
					plotOptions: {
						series: {
							stacking: 'normal',
							dataLabels: {
								enabled: true
							}
						}
					},
					tooltip: {
						formatter: function () {
							return '<b>' + this.x + '</b><br/>' +
								this.series.name + ': ' + this.y + '<br/>';
						}
					},
					series: [{
						name: '退药数',
						data: tuiy_count_
					}, {
						name: '未配置',
						data: nopeizhi_count_
					}, {
						name: '已配置',
						data: peizhi_count_
					}]
				}); 
				success();
			}, error);
		}
	};
})($crcockpit, window);
