$.fn.extend({
	"ccinit_autocomplete": function() {
		var $this = this;
		if ($.is_mobile()) {
		
		} else {
			
		}
		$this.data("params", $this.attr("params") ? $.parseJSON($this.attr("params")) : {});
		$this.data("value", $this.val());
		$this.data("acvalue", $this.attr("text") || $this.val());
		$this.val($this.attr("text") || $this.val());
		$this.attr("autocomplete", "off");
		var cc = $this.next(".autocomplete-content").find("div:eq(0)");
		var content = {
			label: cc.attr("data-label") || cc.text(),
			html: cc.html(),
			value: cc.attr("data-value")
		};
		$this.data("isdemo", $this.next(".autocomplete-content").find("div:gt(0)").length > 0);
		var data = [];
		if ($this.data("isdemo")) {
			$this.next(".autocomplete-content").find("div:gt(0)").each(function() {
				var da = {};
				for (var an in this.dataset) {
					da[an] = this.dataset[an];
				}
				data.push(da);
			});
		}
		$this.next(".autocomplete-content").remove();
		var selected = false;
		$this.autocomplete({
			minLength : 0,
			autoFocus : true,
			delay : 1000,
			autoFill : true,
			selectFirst : true,
			pagination : +$this.attr("limit") > 0,
			source : function(req, resp) {
				if ($this.is("[readonly]")) {
					return;
				}
				var action = $this.attr("action");
				if (!$this.attr("action") && $this.attr("schemeid")) {
					action = 'application.scheme.executeQueryScheme';
					var scheme = $this.attr("schemeid").split(",");
					$this.data("params")["fangalx"] = scheme[0];
					$this.data("params")["fangabh"] = scheme[1];
				}
				
				var start = 1, limit = $this.attr("limit");
				if (+$this.attr("limit") > 0) {
					limit = +limit;
				} else {
					limit = 50;
				}
				if (req.pageid == undefined || req.pageid == 0) {
					req.pageid = 1;
				}
				start = (req.pageid - 1) * limit + 1;
				selected = false;
				if ($this.data("isdemo")) {
					var tdata = [];
					for (var i = start - 1; i < start - 1 + limit; i ++) {
						tdata.push(data[i]);
					}
					resp($.map(tdata, function(item) {
						var t = {
							data : item,
							acvalue : $.dlexpr(content.value, item),
							label : $.dlexpr(content.html, item),
							value : $.dlexpr(content.label, item)
						};
						return t;
					}), {
						totalPage : Math.ceil(data.length / limit)
					});
				} else {
					var tdata = $.extend(true, {
						filter : $this.val(),
						start : start,
						limit : limit
					}, $this.data("params"));
					$.call(action, tdata, function(rtn) {
						resp($.map(rtn.resultset, function(item) {
							var t = {
								data : item,
								acvalue : $.dlexpr(content.value, item),
								label : $.dlexpr(content.html, item),
								value : $.dlexpr(content.label, item)
							};
							return t;
						}), {
							totalPage : Math.ceil(rtn.count / limit)
						});
					}, function(errormsg) {
						$.error(errormsg);
					}, {nomask: true});
				}
			},
			search : function() {
				//					
				if ($this.is("[readonly]")) {
					return;
				}
			},
			focus : function(c, item) {
				if ($this.is("[readonly]")) {
					return;
				}
				$(".ui-autocomplete").css("z-index", 99999);
				if (c.keyCode == 38 || c.keyCode == 40) {
	               clearTimeout(changetimer);
	               changetimer = setTimeout(function(){
	                  changeselect(c, item);
	               }, 500);
	            }
			},
			select : function(c, item) {
				clearTimeout(changetimer);
	            changetimer = setTimeout(function(){
	               changeselect(c, item);
	            }, 10);
			}
		});
		var changetimer = undefined;
	      var changeselect = function(e, item) {
	         if ($this.is("[readonly]")) {
	            return;
	         }
	         // 此算法必须和config中maxlength限制一致计算最大录入长度
	         if ($this.attr("maxlength")) {
	            var value = item.item.acvalue;
	            var length = value.length;
	            var param = $this.attr("maxlength");
	            var hanzLen = 0;
	            for (var i = 0; i < value.length; i++) {
	               if (value.charCodeAt(i) > 127) {
	                  if (i < (param - hanzLen))
	                     hanzLen++;
	                  length++;
	               }
	            }
	            if (!length <= param) {
	               value = value.substring(0, param - hanzLen);
	               item.item.acvalue = value;
	            }
	         }
	         if($this.data("value") != item.item.acvalue){
	            $this.val(item.item.value);
	            $this.data("data", item.item.data);
	            $this.data("label", item.item.label);
	            $this.data("value", item.item.acvalue);
	            $this.data("acvalue", item.item.value);
	            $this.trigger("change");
	            selected = true;
	         }
	      }
		$this.click(function() {
			if ($this.attr("editable") != 'true')
				if ($this.val() != "" && ($this.val() == $this.data("value"))) {
					$this.select();
				}
			if ($this.is("[readonly]")) {
				return;
			}
			$this.autocomplete("search", $this.val());
		});
		$this.on("blur", function() {
			if ($this.val() != $this.data("acvalue") && $this.attr("editable") == 'true') {
				$this.data("data", {});
				$this.data("label", $this.val());
				$this.data("value", $this.val());
				$this.data("acvalue", $this.val());
			}else if($this.val() != $this.data("acvalue")){
				$this.val("");
				$this.data("data", {});
				$this.removeData("label");
				$this.removeData("value");
				$this.removeData("acvalue");
			}
		});
		
		$this.attr("cinited", "cinited");
	},
	"params_autocomplete": function(params) {
		var $this = this;
		var tps = $this.attr("params") ? $.parseJSON($this.attr("params")) : {};
		$this.data("params", $.extend(true, tps, params));
	},
	"getData_autocomplete": function() {
		var $this = this;
		var d = {};
		d[$this.attr("name")] = $this.data("value");
		return d;
	},
	"setData_autocomplete": function(item) {
		var $this = this;
		$this.val(item.value);
		$this.data("data", item.data);
		$this.data("label", item.label);
		$this.data("value", item.acvalue);
		$this.data("acvalue", item.value);
	},
	"clearData_autocomplete": function() {
		var $this = this;
		$this.val("");
		$this.data("data", {});
		$this.data("label", "");
		$this.data("value", "");
		$this.data("acvalue", "");
	}
});
