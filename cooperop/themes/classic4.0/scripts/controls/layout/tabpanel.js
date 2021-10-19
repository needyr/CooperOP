$.fn.extend({
	"ccinit_tabpanel": function() {
		var $this = this;
		$(window).on("resize", function(e) {
			$this.find(".tab-content > .tab-pane.resizing").removeClass("resizing");
		});
		$this.find(".nav-tabs > li > a").click(function() {
			var _i = $(this).parent().index();
			$this.find(".nav-tabs > li").removeClass("active");
			$this.find(".nav-tabs > li:eq(" + _i + ")").addClass("active");
			$this.find(".tab-tools > .tools").removeClass("active");
			$this.find(".tab-tools > .tools:eq(" + _i + ")").addClass("active");
			$this.find(".tab-content > .tab-pane").removeClass("active");
			$this.find(".tab-content > .tab-pane:eq(" + _i + ")").addClass("active");
			if (!$this.find(".tab-content > .tab-pane:eq(" + _i + ")").is(".resizing")) {
				$this.find(".tab-content > .tab-pane:eq(" + _i + ")").addClass("resizing");
				$this.find(".tab-content > .tab-pane:eq(" + _i + ")").trigger("resize");
			}
		});
		$this.attr("cinited", "cinited");
	}
});
