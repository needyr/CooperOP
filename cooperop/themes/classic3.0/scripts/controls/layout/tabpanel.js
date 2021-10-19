$.fn.extend({
	"ccinit_tabpanel": function() {
		var $this = this;
		$this.find(".nav-tabs > li > a").click(function() {
			var _i = $(this).parent().index();
			$this.find(".nav-tabs > li").removeClass("active");
			$this.find(".nav-tabs > li:eq(" + _i + ")").addClass("active");
			$this.find(".tab-tools > .tools").removeClass("active");
			$this.find(".tab-tools > .tools:eq(" + _i + ")").addClass("active");
			$this.find(".tab-content > .tab-pane").removeClass("active");
			$this.find(".tab-content > .tab-pane:eq(" + _i + ")").addClass("active");
			$this.find(".tab-content > .tab-pane.active").find("[ctype='textarea']").each(function(index){
				autosize.update(this);
			});
			//$(document).trigger("resize");
			$this.find(".tab-content > .tab-pane:eq(" + _i + ")").trigger("resize");
			if(_i != 0 && !$this.find(".tab-content > .tab-pane:eq(" + _i + ")").attr("has_resize")){
				setTimeout(function(){
					$this.find(".tab-content > .tab-pane:eq(" + _i + ")").trigger("resize");
					$this.find(".tab-content > .tab-pane:eq(" + _i + ")").attr("has_resize", "Y");
				}, 300);
			}
			//$this.find(".tab-content > .tab-pane:eq(" + _i + ")").trigger("resize");
		});
		//$(document).trigger("resize");
		$this.attr("cinited", "cinited");
	}
});
