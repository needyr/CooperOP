$.fn.extend({
	"ccinit_chart": function() {
		var $this = this;
		if ($.is_mobile()) {
		
		} else {
			
		}
		$this.attr("cinited", "cinited");
		
		if (!$this.attr("isdesign")) {
			$this.find(".demo_chart").hide();
			$this.find(".demo-chart-label").hide();
		}
		$this.height($this.attr("chart_height")|| 200)
		if ($this.attr("autoload") == 'true' || $this.attr("autoload") == true) {
			if($this.attr("pageid")){
				$.initChart($this.attr("flag"), $this.attr("pageid"));
			}else{
				$.initChart($this.attr("flag"));
			}
		}
		$this.parent().find(".xuanzhuan").on("click", function(){
			$.xuanzhuan($this.attr("flag"));
		});
	}
});
