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
		$this.on("focus", function(){
			var p = $this.parent();
			if(p.attr("eidx")){
				$("#layui-layer"+p.attr("eidx")).remove();
			}
		});
	},
	"getData_textarea": function() {
		var $this = this;
		var d = {};
		var val = $this.val();
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
