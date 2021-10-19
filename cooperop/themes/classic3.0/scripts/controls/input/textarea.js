$.fn.extend({
	"ccinit_textarea": function() {
		var $this = this;
		if ($.is_mobile()) {
		
		} else {
			
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
		if($this.attr("autosize")=="true"){
			autosize(this[0]);
		}
		$this.attr("cinited", "cinited");
	},
	"getData_textarea": function() {
		var $this = this;
		var d = {};
		var val = $this.val();
		//因为textarea每次初始化值后，会自动在最后面加一个换行符，导致不改变值的情况下每保存一次后面会多个换行符
		if(val.substring(val.lastIndexOf("\n")) == "\n"){
			val = val.substring(0, val.lastIndexOf("\n"));
		}
		if(val){
			d[$this.attr("name")] = val;
		}else{
			d[$this.attr("name")] = $this.val();
		}
		return d;
	}
});
