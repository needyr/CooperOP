$.fn.extend({
	"ccinit_select2": function() {
		var $this = this;
		var _value = this[0].getAttribute("value");
		$this.data("params", $this.attr("params") ? $.parseJSON($this.attr("params")) : {});
		var template_option = $this.find(".option-template");
		$this.find(".option-template").remove();
		if($this.attr("action")){
			if (_value) {
				var templateClone = template_option.clone(true);;
				templateClone.removeAttr("class");
				templateClone.attr("selected", "selected");
				templateClone.text(templateClone.attr("label"));
				/*templateClone.removeAttr("label")
				templateClone.removeAttr("title")*/
				$.call($this.attr("action"), $.extend(true, $this.data("params"), {selected_: _value, limit: 999}), function(rtn) {
					var html = [];
					if (_value.indexOf(",") > -1) {
						var val_ = _value.split(/,/g);
						$this.val(val_);
						var vt = templateClone.attr("value");
						for(var k in val_){
							for (var i in rtn.resultset) {
								if(val_[k] == $.dlexpr(vt, rtn.resultset[i])){
									html.push($.dlexpr(templateClone[0].outerHTML, rtn.resultset[i]));
									break;
								}
							}
						}
					} else {
						$this.val(_value);
						var vt = templateClone.attr("value");
						for (var i in rtn.resultset) {
							if(_value == $.dlexpr(vt, rtn.resultset[i])){
								html.push($.dlexpr(templateClone[0].outerHTML, rtn.resultset[i]));
								break;
							}
						}
					}
					$this.append(html.join(""));
				},null, {async: false});
			}
		}
		$this.select2({
			width: '100%',
			placeholder: $this.attr("placeholder") ,
			tags: false ,
			multiple: true,
			maximumSelectionSize: $this.attr("maxlength") || 999,
			tokenSeparators: [';', ' '],
			language: "zh-CN",
			/*allowClear: false ,*/
			/*templateResult: function(data, container) {
				if (!data.data) {
					return undefined;
				}
				return data.data.name;
			},
			templateSelection: function(data) {
				if (!data.data && data.id) {
					return data.text;
				}
				return data.data.name;
			},*/
			escapeMarkup: function (m) {
	            return m;
	        },
			ajax: {
				delay: 250,
				cache: false ,
				url: function(params) {
					return "";
				},
				data: function (params) {
				    var queryParameters = {
				      filter: params.term,
				      except: this.val(),
				      start: 1,
				      limit: 20
				    }
				    return $.extend(true, queryParameters, $this.data("params"));
				},
				processResults: function (data) {
					var rtn = {results: []};
					for (var i in data.resultset) {
						rtn.results.push({
							id: $.dlexpr(template_option.attr("value"), data.resultset[i]),
							text: $.dlexpr(template_option.attr("label"), data.resultset[i]),
							title: $.dlexpr(template_option.attr("title"), data.resultset[i]),
							data: data.resultset[i]
						});
					}
				    return rtn;
				},
				transport: function (params, success, failure) {
				    return $.call($this.attr("action"), params.data, success, failure, {nomask: true});
				}
			}
		});
		$this.attr("cinited", "cinited");
	},
	"params_select2": function(params) {
		var $this = this;
		var tps = $this.attr("params") ? $.parseJSON($this.attr("params")) : {};
		$this.data("params", $.extend(true, tps, params));
	},
	"refresh_select2": function(params) {
		var $this = this;
		
	},
	"getData_select2": function() {
		var $this = this;
		var d = {};
		var ids = [];
		var ds = $this.select2("data")
		for(var i in ds){
			ids.push(ds[i].id);
		}
		d[$this.attr("name")] = ids.join(",");
		return d;
	}
});
