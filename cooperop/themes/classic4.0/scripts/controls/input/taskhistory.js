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
			html.push('<li class="expand">');
			html.push('<a href="javascript:void(0);" ><i class="fa fa-chevron-down">展开</i>');
			html.push('</a></li>');
			html.push('<li class="collapse">');
			html.push('<a href="javascript:void(0);" ><i class="fa fa-chevron-up">收起</i>');
			html.push('</a></li>');
//			html.push('			<button type="button" class="btn btn-sm btn-sm btn-default expand"><i class="fa fa-chevron-down"></i>展开</button>');
//			html.push('			<button type="button" class="btn btn-sm btn-sm btn-default collapse" style="display:none;"><i class="fa fa-chevron-up"></i>收起</button>');
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
					if(rtn.resultset[0].process_img){
						html.push('<div class="vs-box"><img src="'+cooperopcontextpath +'/rm/s/xdesigner/'+rtn.resultset[0].process_img+'"></div>');
					}
					html.push('<div class="timeline-cr">');
					html.push('	<ul>');
					for (var i in rtn.resultset) {
						var record = rtn.resultset[i];
						
						if (!record.audited && !record.task_time_end) {
							html.push('<li class="doing" title="待办">');
						} else if (record.audited == "Y") {
							if(record.process_state == '0'){
								html.push('<li class="back" title="不通过">');
							}else{
								html.push('<li class="passed" title="通过">');
							}
						} else if (record.audited == "N") {
							html.push('<li class="error" title="驳回">');
						} else if (record.audited == "R") {
							html.push('<li class="error">')
						} else if (record.audited == "B") {
							html.push('<li class="error">')
						} else if (record.audited == "F") {
							html.push('<li class="passed">');
						}
						html.push('<i class="status"></i>');
						if (record.system_product_process_id != 'free') {
							html.push('<em class="name">' + record.node_name + '</em>');
						}else{
							//html.push('<em class="name">' + record.node_name + '</em>');
						}
						html.push('<div class="detail">');
						html.push('	<span class="ms">' + record.operator_name + '</span>');
						if(record.advice && record.advice!='null'){
							html.push('	<span class="ms">审批意见：'+record.advice+'</span>');
						}
						if(record.task_time_end){
							html.push('	<span class="time">' + $.formatdate(new Date(+record.task_time_end), 'yyyy-MM-dd HH:mm:ss')+ '</span>');
						}
						html.push('</div>');
						html.push('</li>');
					}
					if (rtn.resultset.length > 0) {
						var record = rtn.resultset[0];
						html.push('<li class="passed">');
						html.push('	<i class="status"></i>');
						html.push('	<em class="name">发起</em>');
						html.push('	<div class="detail">');
						html.push('		<span class="ms">' + record.creator_name + '</span>');
						html.push('		<span class="time">' + $.formatdate(new Date(+record.process_time_start), 'yyyy-MM-dd HH:mm:ss')+ '</span>');
						html.push('	</div>');
						html.push('</li>');
					}
					html.push('</ul>');
					html.push('</div>');
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
