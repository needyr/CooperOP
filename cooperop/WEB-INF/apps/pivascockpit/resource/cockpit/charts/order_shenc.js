(function(crcockpit, window) {
	crcockpit.charts.order_shenc = {
		"icon": "vdiconfont vdicon-tubiao",
		"paint": function(chart, ele) {
			var $this = $(ele);

			var html = [];
			html.push('<div class="cockpit-chart-statistics">');
			html.push('	<ul>');
			html.push('		<li><em class="c-blue total_num"></em>医嘱总数</li>');
			html.push('		<li><em class="c-green audit_num"></em>已审核</li>');
			html.push('		<li><em class="c-white hold_num"></em>待审核</li>');
			html.push('	</ul>');
			html.push('</div>');
			$this.children(".cockpit-chart-content").append(html.join(''));
		},
		"statistics": function(chart, ele, success, error) {
			var $this = $(ele);

			$crcockpit.queryCockpitData(chart, {}, function(rs) {
				var data = rs.resultset;
//				{
//				id: 20200210232107102001
//				schedule_date: 20200210,
//				analysis_time: new Date().getTime(),
//				order_count: 0,
//				shenc_count: 0,
//				noshenc_count: 0,
//				}
				var time = new Date().getTime();
				if (data.length > 0) {
					time = data[0].analysis_time;
					$this.find(".cockpit-chart-statistics > ul > li > .total_num").text(data[0].order_count);
					$this.find(".cockpit-chart-statistics > ul > li > .audit_num").text(data[0].shenc_count);
					$this.find(".cockpit-chart-statistics > ul > li > .hold_num").text(data[0].noshenc_count);
					
					//chart
					var series = [];
					series.push(['已审', (data[0].shenc_count || 0)-0]);
					series.push(['待审', (data[0].noshenc_count || 0)-0]);
					$this.find(".cockpit-chart-content > .cockpit-chart-chart").html('');
					$this.find(".cockpit-chart-content > .cockpit-chart-chart").highcharts({
						title: {
							text: ''+data[0].order_count,
							align: 'center',
							verticalAlign: 'middle',
							y: $this.find(".cockpit-chart-content > .cockpit-chart-chart").height() / 5
						},
						tooltip: {
							headerFormat: '{series.name}<br>',
							pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
						},
						plotOptions: {
							pie: {
								dataLabels: {
									enabled: true,
									distance: -30
								},
								startAngle: -90, // 圆环的开始角度
								endAngle: 90,    // 圆环的结束角度
								center: ['50%', '75%']
							}
						},
						series: [{
							type: 'pie',
							name: '审查统计',
							innerSize: '50%',
							data: series
						}]
					}); 
				} else {
					$this.find(".cockpit-chart-content > .cockpit-chart-chart").html('<div class="cockpit-chart-nodata">无统计数据</div>');
				}
				
				$this.find(".cockpit-chart-title > .cockpit-chart-time").text(crcockpit.formatTime(new Date(time)));
				
				success();
			}, error);
		},
		"paint_full": function(chart, ele, params) {
			var $this = $(ele);

			var html = [];

			$this.children(".cockpit-chart-content").append(html.join(''));
		},
		"statistics_full": function(chart, ele, params, success, error) {
			var $this = $(ele);
			
			$crcockpit.queryFullData(chart, {}, function(rs) {
				var data = rs.resultset;
//				{
//				id: 20200210232107102001
//				schedule_date: 20200210,
//				analysis_time: new Date().getTime(),
//				dept_code: "6202778",
//				dept_name: "儿科护理站",
//				shenc_count: 0,
//				noshenc_count: 0,
//				}
				var html = [];
				var time = (data && data.length > 0) ? data[0].analysis_time || new Date().getTime() : new Date().getTime();

				var html = [];
				var series_ys = [];
				var series_ds = [];
				var categories =[];
				for (var j = 0, r = data[j]; j < data.length; j ++, r = data[j]) {
					html.push('		<div><span>' + r.dept_name + '(' + r.dept_code + ' )' + ': </span><em class="c-red">' + r.noshenc_count + '</em><em class="c-green">' + r.shenc_count + '</em></div>');
					series_ys.push(r.shenc_count-0);
					series_ds.push(r.noshenc_count-0);
					categories.push(r.dept_name);
				}
				//$this.children(".cockpit-chart-content").html(html.join(''));
				$this.find(".cockpit-chart-title > .cockpit-chart-time").text(crcockpit.formatTime(new Date(time)));
				
				//chart
				$this.find(".cockpit-chart-content > .cockpit-chart-chart").html('');
				$this.find(".cockpit-chart-content > .cockpit-chart-chart").highcharts({
					chart: {
						type: 'column'
					},
					xAxis: {
						categories: categories
					},
					yAxis: {
						min: 0,
						stackLabels: {  // 堆叠数据标签
							enabled: true
						}
					},
					tooltip: {
						pointFormat: '<span>{series.name}</span>: <b>{point.y}</b>' +
						'<br/>', 
						shared: true
					},
					plotOptions: {
						column: {
							stacking: 'normal',
							dataLabels: {
								enabled: true
							}
						}
					},
					series: [{
						name: '已审医嘱',
						data: series_ys
					},{
						name: '待审医嘱',
						data: series_ds
					}]
				}); 
				
				success();
			}, error);
		}
	};
})($crcockpit, window);
