(function(crcockpit, window) {
	crcockpit.charts.drug_class = {
		"icon": "vdiconfont vdicon-tubiao08",
		"paint": function(chart, ele) {
			var $this = $(ele);

			var html = [];
			html.push('<div class="cockpit-chart-statistics">');
			html.push('	<dl>');
			html.push('		<dt>药品金额(万)</dt>');
			html.push('	</dl>');
			html.push('</div>');
			$this.children(".cockpit-chart-content").append(html.join(''));
		},
		"statistics": function(chart, ele, success, error) {
			var $this = $(ele);
			$crcockpit.queryCockpitData(chart, {}, function(rs) {
				var data = rs.resultset;
//				{
//					id: 20200210232107102001
//					schedule_date: 20200210,
//					analysis_time: new Date().getTime(),
//					drug_classname: "心血管药品",
//					amount: Math.floor(Math.random() * 10000) / 100
//				}
				$this.find(".cockpit-chart-content > .cockpit-chart-statistics > dl > dd").remove();
				var time = (data && data.length > 0) ? data[0].analysis_time || new Date().getTime() : new Date().getTime();

				var html = [];
				for (var j = 0, r = data[j]; j < data.length; j ++, r = data[j]) {
					html.push('		<dd><span>' + r.drug_classname + ': </span><em class="c-red">' + (Math.round(r.amount / 100) / 100) + '</em></dd>');
				}
				$this.find(".cockpit-chart-content > .cockpit-chart-statistics > dl").append(html.join(''));
				$this.find(".cockpit-chart-title > .cockpit-chart-time").text(crcockpit.formatTime(new Date(time)));
				
				//highchart
				var series = [];
				for(var j = 0, r = data[j]; j < data.length; j ++, r = data[j]){
					var da = {name: r.drug_classname, y: (Math.round(r.amount / 100) / 100)};
					series.push(da);
				}
				$this.find(".cockpit-chart-content > .cockpit-chart-chart").html('');
				$this.find(".cockpit-chart-content > .cockpit-chart-chart").highcharts({
					chart: {
						type: 'pie'
					},
					title: {
						text: null
					},
					tooltip: {
						pointFormat: '{series.name}<b>{point.percentage:.1f}%</b>'
					},
					plotOptions: {
						pie: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								format: '{point.name}:<b> {point.percentage:.1f} %</b>',
								distance: -40
							}
						}
					},
					series: [{
						name: "金额占比",
						data: series
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
			$crcockpit.queryFullData(chart, params, function(rs) {
				var data = rs.resultset;
//				{
//					id: 20200210232107102001
//					schedule_date: 20200210,
//					analysis_time: new Date().getTime(),
//					drug_classname: "心血管药品",
//					amount: Math.floor(Math.random() * 10000) / 100
//				}
				var time = (data && data.length > 0) ? data[0].analysis_time || new Date().getTime() : new Date().getTime();

				$this.find(".cockpit-chart-title > .cockpit-chart-time").text(crcockpit.formatTime(new Date(time)));

				//highchart
				var series = [];
				var categories =[];
				var dict_ = {};
				for(var j = 0, r = data[j]; j < data.length; j ++, r = data[j]){
					series.push({name: r.drug_classname, y: Math.round(r.amount / 100) / 100, drilldown: true});
					categories.push(r.drug_classname);
					dict_[r.drug_classname] = r.drug_class;
				}
				$this.find(".cockpit-chart-content > .cockpit-chart-chart").html('');
				$this.find(".cockpit-chart-content > .cockpit-chart-chart").highcharts({
					chart: {
						type: 'column',
						events: {
							drilldown: function (e) {
								if (chart.childcode) {
									crcockpit.popfull($.extend(true, {}, chart, {code: chart.childcode, name: chart.childname}), undefined,
											{drug_class: dict_[e.point.name], drug_classname: e.point.name});
								}
							}
						}
					},
					title: {
						text: null
					},
					tooltip: {
						pointFormat: '{series.name}:<b> {point.y}</b>(万)'
					},
					plotOptions: {
						column: {
							allowPointSelect: true,
							cursor: 'pointer',
							dataLabels: {
								enabled: true,
								format: '<b>{series.name}</b>: {point.y} '
							}
						}
					},
					xAxis: {
						categories: categories,
						crosshair: true
					},
					yAxis: {
						min: 0,
						title: {
							text: '药品金额(万)'
						}
					},
					series: [{
						name: '药品金额(万)',
						data: series
					}]
				}); 
				
				success();
				
			}, error);
		}
	};
})($crcockpit, window);
