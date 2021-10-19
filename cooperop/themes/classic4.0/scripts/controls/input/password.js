$.fn.extend({
	"ccinit_password": function() {
		var $this = this;
		if ($.is_mobile()) {
		
		} else {
			
		}
		if ($this.is("[dbl_action]")) {
			$this.siblings(".open-icon").on("click", function() {
				$.console().log($this.attr("dbl_action"));
				$.modal(location.href);
			});
			$this.on("dblclick", function() {
				$.console().log($this.attr("dbl_action"));
				$.modal(location.href);
			});
		}
		$this.attr("cinited", "cinited");
	}
});
