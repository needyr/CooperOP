$.fn.extend({
	"ccinit_datefield": function() {
		var $this = this;
		if ($.is_mobile()) {
			$this.attr("type", "date");
		} else {
			/*$this.datepicker({
				language: "zh-CN",
				rtl: Metronic.isRTL(),
				format: $this.attr("format") || "yyyy-mm-dd",
				orientation: "left top",
				autoclose: true,
				todayBtn: "linked",
				startDate: $this.attr("min"),
				endDate: $this.attr("max")
			});*/
			$this.click(function() {
				var o = {
					dateFmt : ($this.attr("format") || "yyyy-MM-dd"),
					// el:this,
					maxDate : "#F{$dp.el.getAttribute('maxdate')}",
					minDate : "#F{$dp.el.getAttribute('mindate')}",
					disabledDays: ($this.attr("disableddays") ? $this.attr("disableddays").split(",") : undefined),
					skin : "default",
					lang : "zh-cn",
					onpicked : function(dp) {
						if ($(dp.el).is("[onpicked]")) {
							if ($.isFunction($(dp.el).attr("onpicked"))) {
								$(dp.el).attr("onpicked").apply(dp);
							} else {
								eval($(dp.el).attr("onpicked") + "(dp)");
							}
						}
					}
				};
				WdatePicker(o);
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
		
		if ($this.attr("value")) {
			var value = formatDate(parseDate($this.attr("value")), $this.attr("format") || "yyyy-MM-dd");
			$this.attr("value", value);
		}
		$this.attr("cinited", "cinited");
	}
});
