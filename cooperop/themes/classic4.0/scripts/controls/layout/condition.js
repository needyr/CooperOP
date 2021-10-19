$.fn.extend({
	"ccinit_condition": function() {
		var $this = this;
		if ($.is_mobile()) {
		
		} else {
			
		}
		$this.on("click", ".portlet-condition-main > a", function() {
			if ($this.children(".portlet-condition-extend").is(":hidden")) {
				$(this).children("i").attr("class", "fa fa-caret-up");
				$this.children(".portlet-condition-extend").show();
			} else {
				$(this).children("i").attr("class", "fa fa-caret-down");
				$this.children(".portlet-condition-extend").hide();
			}
		});
		$this.on("click", ".portlet-condition-main > .portlet-condition-filter > a", function() {
			$this.trigger("query");
		});
		$this.on("keyup", ".portlet-condition-main > .portlet-condition-filter > input", function(e) {
			if (e.keyCode == 13) {
				$this.trigger("query");
			}
		});
		$this.attr("cinited", "cinited");
	}
});
