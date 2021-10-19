/*$.fn.extend({
	"ccinit_form": function() {
		var $this = this;
		if ($.is_mobile()) {
		
		} else {
			
		}
		$this.attr("cinited", "cinited");
	}
});*/
$.fn.extend({
	"ccinit_form": function() {
		var $this = this;
		if ($.is_mobile()) {
		
		} else {
			
		}
		if ($this.parents(".portlet.box:eq(0)").find(".form-collapse-btn").length > 0) {
			$this.parents(".portlet.box:eq(0)").find(".form-collapse-btn").click(function() {
				if ($this.parents(".portlet.box:eq(0)").is(".portlet-collapsed")) {
					$this.parents(".portlet-body:eq(0)").animate({
						height : "show"
					}, 500, function() {
						$this.parents(".portlet.box:eq(0)").removeClass("portlet-collapsed");
						$this.parents(".portlet.box:eq(0)").find(".form-collapse-btn").html('[ 收起<i class="fa fa-caret-up"></i> ]');
					});
				} else {
					$this.parents(".portlet-body:eq(0)").animate({
						height : "hide"
					}, 500, function() {
						$this.parents(".portlet.box:eq(0)").addClass("portlet-collapsed");
						$this.parents(".portlet.box:eq(0)").find(".form-collapse-btn").html('[ 展开<i class="fa fa-caret-down"></i> ]');
					});
				}
			});
			if ($this.is("[collapsed='true']")) {
				$this.parents(".portlet.box:eq(0)").addClass("portlet-collapsed");
				$this.parents(".portlet.box:eq(0)").find(".form-collapse-btn").html('[ 展开<i class="fa fa-caret-down"></i> ]');
				$this.parents(".portlet-body:eq(0)").hide();
			} else {
				$this.parents(".portlet.box:eq(0)").find(".form-collapse-btn").html('[ 收起<i class="fa fa-caret-up"></i> ]');
			}
		}
		$this.attr("cinited", "cinited");
	}
});

