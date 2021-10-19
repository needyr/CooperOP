$.fn.extend({
	"ccinit_timefield": function() {
		var $this = this;
		if ($.is_mobile()) {
			$this.attr("type", "time");
		} else {
			$this.timepicker({
				defaultTime: $this.val(),
				autoclose: true,
				showSeconds: false,
				minuteStep: 1,
				showMeridian: false
			});
		}
		if ($this.attr("nextfocusfield")) {
			if ($this.is("[intable]")) {
				$this.on("blur", function() {
					$("#" + $this.attr("trid")).find("[name='"+$this.attr("nextfocusfield")+"']").focus();
				});
			}else{
				$this.on("blur", function() {
					$("[name='"+$this.attr("nextfocusfield")+"']").focus();
				});
			}
		}
		$this.attr("cinited", "cinited");
	}
});
