(function(crcockpit, window) {
	crcockpit.charts.drug = {
		"icon": "vdiconfont vdicon-tubiao1",
		"paint": function(chart, ele) {
			var $this = $(ele);

			var html = [];

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
//				drug_classname: "抗生素药品",
//				drug_code: "60508931",
//				drug_name: "注射用阿奇霉素",
//				drug_spec: "0.25g",
//				drug_unit: "支",
//				shengccj: "东北制药集团沈阳第一制药有限公司",
//				pizhwh: "国药准字",
//				amount: 6899.00,
//				costs: 230771.55,
//				amount_used: 30.00,
//				costs_used: 1003.50,
//				amount_notused: 6869.00,
//				costs_notused: 229768.05
//				}
				var time = (data && data.length > 0) ? data[0].analysis_time || new Date().getTime() : new Date().getTime();

				var html = [];
				for (var j = 0, r = data[j]; j < data.length; j ++, r = data[j]) {
					html.push('		<div><span>' + r.drug_name + ' ' + r.drug_spec + '/' + r.drug_unit + '(' + r.shengccj + ',' + r.pizhwh + ' )' + ': </span><em class="c-red">' + r.amount + '</em></div>');
				}
				$this.children(".cockpit-chart-content").html(html.join(''));
				$this.find(".cockpit-chart-title > .cockpit-chart-time").text(crcockpit.formatTime(new Date(time)));
				
				success();
			}, error);
		},
		"paint_full": function(chart, ele, params) {
			var $this = $(ele);
			$this.find(".cockpit-chart-content > .cockpit-chart-chart").addClass("cockpit-chart-list");
			
			var html = [];
			html.push('	<div class="cockpit-chart-list-thead">');
			html.push('		<dl>');
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
			if (params) {
				$this.find(".cockpit-chart-title > h3").text(params.drug_classname + '统计');
			}
		},
		"statistics_full": function(chart, ele, params, success, error) {
			var $this = $(ele);
			var cpopid = $this.data("cpopid");  //这里要拿弹框的id
			clearInterval($crcockpit.poptimer[cpopid]);
			$crcockpit.queryFullData(chart, params, function(rs) {
				var data = rs.resultset;
//				{
//				id: 20200210232107102001
//				schedule_date: 20200210,
//				analysis_time: new Date().getTime(),
//				drug_classname: "抗生素药品",
//				drug_code: "60508931",
//				drug_name: "注射用阿奇霉素",
//				drug_spec: "0.25g",
//				drug_unit: "支",
//				shengccj: "东北制药集团沈阳第一制药有限公司",
//				pizhwh: "国药准字",
//				amount: 6899.00,
//				costs: 230771.55,
//				amount_used: 30.00,
//				costs_used: 1003.50,
//				amount_notused: 6869.00,
//				costs_notused: 229768.05
//				}
				var html = [];
				//var time = (data && data.length > 0) ? data[0].analysis_time || new Date().getTime() : new Date().getTime();

				var html = [];
				for (var j = 0, r = data[j]; j < data.length; j ++, r = data[j]) {
					html.push('		<div><span>' + r.drug_name + ' ' + r.drug_spec + '/' + r.drug_unit + '(' + r.shengccj + ',' + r.pizhwh + ' )' + ': </span><em class="c-red">' + r.amount + '</em></div>');
				}
				
				
				
				
				var time = new Date().getTime();
				if (data.length == 0) {
					$this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-thead > dl").html('');
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
					html.push('			<dd><div class="cockpit-chart-list-th">药品统计分类</div></dd>');
					html.push('			<dd><div class="cockpit-chart-list-th">药品编号</div></dd>');
					html.push('			<dd><div class="cockpit-chart-list-th">药品名称</div></dd>');
					html.push('			<dd><div class="cockpit-chart-list-th">规格</div></dd>');
					html.push('			<dd><div class="cockpit-chart-list-th">包装单位</div></dd>');
					html.push('			<dd><div class="cockpit-chart-list-th">生产厂家</div></dd>');
					html.push('			<dd><div class="cockpit-chart-list-th">批准文号</div></dd>');
					html.push('			<dd><div class="cockpit-chart-list-th">数量</div></dd>');
					html.push('			<dd><div class="cockpit-chart-list-th">金额</div></dd>');
					html.push('			<dd><div class="cockpit-chart-list-th">已使用数量</div></dd>');
					html.push('			<dd><div class="cockpit-chart-list-th">已使用金额</div></dd>');
					html.push('			<dd><div class="cockpit-chart-list-th">未使用数量</div></dd>');
					html.push('			<dd><div class="cockpit-chart-list-th">未使用金额</div></dd>');
					$this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-thead > dl").html(html.join(''));

					html = ['<div>'];
					for (var j = 0, r = data[j]; j < data.length; j ++, r = data[j]) {
						html.push('	<dl>');
						html.push('		<dd><span>' + r.drug_classname + '</span></dd>');
						html.push('		<dd><span>' + r.drug_code + '</span></dd>');
						html.push('		<dd><span title="' + r.drug_name + '">' + r.drug_name + '</span></dd>');
						html.push('		<dd><span>' + r.drug_spec + '</span></dd>');
						html.push('		<dd><span>' + r.drug_unit + '</span></dd>');
						html.push('		<dd><span title="' + r.shengccj + '">' + r.shengccj + '</span></dd>');
						html.push('		<dd><span>' + r.pizhwh + '</span></dd>');
						html.push('		<dd><span>' + r.amount + '</span></dd>');
						html.push('		<dd><span>' + r.costs + '</span></dd>');
						html.push('		<dd><span>' + r.amount_used + '</span></dd>');
						html.push('		<dd><span>' + r.costs_used + '</span></dd>');
						html.push('		<dd><span>' + r.amount_notused + '</span></dd>');
						html.push('		<dd><span>' + r.costs_notused + '</span></dd>');
						html.push('	</dl>');
					}
					html.push('</div>');
					
					var le = $this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-list");
					le.children("div").css("top", "0px");
					le.html(html.join(''));
					var de = $this.find(".cockpit-chart-content > .cockpit-chart-chart > .cockpit-chart-list-list > div > dl:eq(0)");
					
					var pn = Math.floor(le.innerHeight() / de.outerHeight());
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
					var timer = 0;
					var changePage = function(page) {
						if (timer) clearTimeout(timer);
						pe.find("li > a").removeClass("active");
						pe.find("li:eq(" + page + ") > a").addClass("active");
						le.children("div").css("top", "-" + ph * page + "px");
						if (page > ps - 1) page = 0;
						if (!notimer) {
							timer = setTimeout(function() {
								changePage(page + 1);
							}, (+chart.refresh_time || 10) * 1000);
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
		}
	};
})($crcockpit, window);
