$.fn.extend({
	"ccinit_switch": function() {
		var $this = this;
		if ($.is_mobile()) {
		
		} else {
			
		}
		if ($this.attr("value") == ($this.attr("onvalue") || "是")) {
			$this[0].checked = true;
		}
		$this.bootstrapSwitch({
			size: "small",
			onText: ($this.attr("ontext") || "是"),
			offText: ($this.attr("offtext") || "否"),
			onColor: $this.attr("oncolor") || "primary",
			offColor: $this.attr("offcolor") || "default"
		});
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
	},
	"setData_switch": function(data) {
		var $this = this;
		if ($this.is("[ctype]") && $this.is("[cinited]")) {
			//if(data == ($this.attr("onvalue") || "是")){
				//$this.bootstrapSwitch('toggleState');
				//$this.bootstrapSwitch('state', true);
			//}
			//$this.bootstrapSwitch('setState', "是");  //data == ($this.attr("onvalue") || "是") ? true : false
			$this.bootstrapSwitch('state', data == ($this.attr("onvalue") || "是") ? true : false);  //
		}
	},
	"getData_switch": function() {
		var $this = this;
		var d = {};
		d[$this.attr("name")] = $this[0].checked ? ($this.attr("onvalue") || "是") : ($this.attr("offvalue") || "否")
		return d;
	}
});
