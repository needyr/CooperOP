$.fn.extend({
	"ccinit_taskhistory": function() {
		var $this = this;
		if ($this.attr("djbh")) {
			var height = 0;
			$this.attr("class", "portlet box grey-steel");
			var html = [];
			html.push('<div class="portlet-title">');
			html.push('		<div class="caption"><i class="fa fa-history"></i>审批历史</div>');
			html.push('		<div class="tools">');
			html.push('			<button type="button" class="btn btn-sm btn-sm btn-default expand"><i class="fa fa-chevron-down"></i>展开</button>');
			html.push('			<button type="button" class="btn btn-sm btn-sm btn-default collapse" style="display:none;"><i class="fa fa-chevron-up"></i>收起</button>');
			html.push('		</div>');
			html.push('</div>');
			html.push('<div class="form-horizontal" style="display:none;">');
			html.push('</div>');
			$this.html(html.join(""));
			$this.on("click", ".expand", function() {
				$this.find(".portlet-title > .tools > .collapse").show();
				$this.find(".portlet-title > .tools > .expand").hide();
				$this.find(".form-horizontal").slideDown(height);
			});
			$this.on("click", ".collapse", function() {
				$this.find(".portlet-title > .tools > .collapse").hide();
				$this.find(".portlet-title > .tools > .expand").show();
				$this.find(".form-horizontal").slideUp(height);
			});
			if ($this.attr("expand") == "true") {
				$this.find(".expand").click();
			}
			$.call("application.task.orderhistory", {djbh: $this.attr("djbh")}, function(rtn) {
				if (rtn.count > 0) {
					html = [];
					html.push('<div class="timeline">');
					for (var i in rtn.resultset) {
						var record = rtn.resultset[i];
						html.push('<div class="timeline-item">');
						html.push('		<div class="timeline-badge">');
						html.push('			<div class="timeline-icon">');
						if (!record.audited && !record.task_time_end) {
							html.push('				<i class="icon-hourglass font-red-flamingo"></i>');
							html.push('				<p class="font-red-flamingo">处理中</p>');
						} else if (record.audited == "Y") {
							html.push('				<i class="icon-user-following font-green-haze"></i>');
							html.push('				<p class="font-green-haze">通过</p>');
						} else if (record.audited == "N") {
							html.push('				<i class="icon-user-unfollow font-red-intense"></i>');
							html.push('				<p class="font-red-intense">驳回</p>');
						} else if (record.audited == "R") {
							html.push('				<i class="icon-refresh font-yellow-casablanca"></i>');
							html.push('				<p class="font-yellow-casablanca">重审</p>');
						} else if (record.audited == "B") {
							html.push('				<i class="icon-reload font-grey-cascade"></i>');
							html.push('				<p class="font-grey-cascade">撤回</p>');
						} else if (record.audited == "F") {
							html.push('				<i class="icon-power font-blue"></i>');
							html.push('				<p class="font-blue">结束</p>');
						}
						html.push('			</div>');
						html.push('		</div>');
						html.push('		<div class="timeline-body">');
						html.push('			<div class="timeline-body-arrow">');
						html.push('			</div>');
						html.push('			<div class="timeline-body-head">');
						html.push('				<div class="timeline-body-head-caption">');
						if (record.system_product_process_id != 'free') {
							html.push('					<span class="timeline-body-alerttitle font-red-intense">' + record.node_name + '</span>');
						}
						html.push('					<span class="timeline-body-title font-red-pink">' + ((!record.audited && !record.task_time_end) ? record.operator_name.substring(0, record.operator_name.length -1) : record.operator_name) + '</span>');
						if (record.audited && record.task_time_end) {
							html.push('					<span class="timeline-body-time font-grey-cascade">' + $.formatdate(new Date(+record.task_time_end), 'yyyy-MM-dd HH:mm:ss') + '</span>');
						}
						html.push('				</div>');
						html.push('			</div>');
						html.push('			<div class="timeline-body-content">');
						if(record.advice && record.advice!='null'){
							html.push(record.advice);
						}
						html.push('			</div>');
						html.push('		</div>');
						html.push('</div>');
					}
					if (rtn.resultset.length > 0) {
						var record = rtn.resultset[0];
						html.push('<div class="timeline-item">');
						html.push('		<div class="timeline-badge">');
						html.push('			<div class="timeline-icon">');
						html.push('				<i class="icon-note font-blue"></i>');
						html.push('				<p class="font-blue">发起</p>');
						html.push('			</div>');
						html.push('		</div>');
						html.push('		<div class="timeline-body">');
						html.push('			<div class="timeline-body-arrow">');
						html.push('			</div>');
						html.push('			<div class="timeline-body-head">');
						html.push('				<div class="timeline-body-head-caption">');
						html.push('					<span class="timeline-body-title font-red-pink">' + record.creator_name + '</span>');
						html.push('					<span class="timeline-body-time font-grey-cascade">' + $.formatdate(new Date(+record.process_time_start), 'yyyy-MM-dd HH:mm:ss')+ '</span>');
						html.push('				</div>');
						html.push('			</div>');
						html.push('			<div class="timeline-body-content">');
						html.push('发起申请');
						html.push('			</div>');
						html.push('		</div>');
						html.push('</div>');
					}
					html.push('</div>');
				} else {
					html.push('<div class="timeline">');
					html.push('</div>');
				}
				$this.find(".form-horizontal").html(html.join(""));
				height = $this.find(".form-horizontal").height;
			}, {no_marsk: true});
		} else {
			$this.remove();
		}
	}
});
