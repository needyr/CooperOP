(function(crcockpit, window) {
	crcockpit.charts.paiyao = {
		"icon": "vdiconfont vdicon-shuzi",
		"paint": function(chart, ele) {
			var $this = $(ele);
			$this.find(".cockpit-chart-content > .cockpit-chart-chart").addClass("cockpit-chart-list");
			var html =[];
			html.push('	<li><span class="tip-blue"></span>未排药</li>');
			html.push('	<li><span class="tip-green"></span>已排药</li>');
			html.push('	<li><span class="tip-red"></span>退药量</li>');
			$this.find(".cockpit-chart-tips").addClass('cockpit-chart-tips-circle');
			$this.find(".cockpit-chart-tips").html(html.join(''));
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
//				paiyao_count: 0,
//				nopaiyao_count: 0,
//				tuiy_count: 0
//				}
				var time = (data && data.length > 0) ? data[0].analysis_time || new Date().getTime() : new Date().getTime();

				var html = ['<div>'];
				for (var j = 0, r = data[j]; j < data.length; j ++, r = data[j]) {
					if (j % 2 == 0 && j > 0) html.push('</div><div>');
					html.push('<dl>');
					html.push('  <dt>' + r.pzfyjch + '</dt>');
					html.push('  <dd><span class="bg-blue c-white">' + r.nopaiyao_count + '</span></dd>');
					html.push('  <dd><span class="bg-green c-white">' + r.paiyao_count + '</span></dd>');
					html.push('  <dd><span class="bg-red c-white">' + r.tuiy_count + '</span></dd>');
					html.push('</dl>');
				}
				if (j % 2 == 1) {
					html.push('	<dl>');
					html.push('	</dl>');
				}
				html.push('</div>');
				$this.find(".cockpit-chart-content > .cockpit-chart-chart").html(html.join(''));
				$this.find(".cockpit-chart-title > .cockpit-chart-time").text(crcockpit.formatTime(new Date(time)));
				
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
//				paiyao_count: 0,
//				nopaiyao_count: 0,
//				tuiy_count: 0
//				}
				var time = (data && data.length > 0) ? data[0].analysis_time || new Date().getTime() : new Date().getTime();

				var categories = [];
				var nopaiyao_count_ = [];
				var paiyao_count_ = [];
				var tuiy_count_ = [];
				for (var j = 0, r = data[j]; j < data.length; j ++, r = data[j]) {
					categories.push(r.pzfyjch);
					nopaiyao_count_.push(r.nopaiyao_count - 0);
					paiyao_count_.push(r.paiyao_count - 0);
					tuiy_count_.push(r.tuiy_count - 0);
				}
				$this.find(".cockpit-chart-title > .cockpit-chart-time").text(crcockpit.formatTime(new Date(time)));
				
				//chart
				$this.find(".cockpit-chart-content > .cockpit-chart-chart").html('');
				$this.find(".cockpit-chart-content > .cockpit-chart-chart").highcharts({
					chart: {
						type: 'column'
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
						name: '退药量',
						data: tuiy_count_
					},{
						name: '未排药',
						data: nopaiyao_count_
					},{
						name: '已排药',
						data: paiyao_count_
					}]
				}); 
				
				
				success();
			}, error);
		}
	};
})($crcockpit, window);
