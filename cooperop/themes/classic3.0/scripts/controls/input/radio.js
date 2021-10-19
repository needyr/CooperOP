$.fn.extend({
	"ccinit_radio": function() {
		var $this = this;
		if ($.is_mobile()) {
		
		} else {
			
		}
		var create = function() {
			$this.find("input[type='radio']").each(function() {
				if($this.attr("value")==$(this).val()){
					$(this).attr("checked","checked");
				}
				$(this).show();
				$(this).uniform();
			});
		}
		
		if($this.is("[modify_action]") || $this.is("[enter_action]") || $this.is("[out_action]") || $this.is("[dbl_action]")){
			var exe = function(scheme) {
				var schemeid = $("input[name='djlx']").val();
				if(!schemeid){
					return;
				}
				if(scheme.indexOf("dj_select_") > -1){
					scheme = scheme.replace("dj_select_","");
				}else if(scheme.indexOf("zl_select_") > -1){
					scheme = scheme.replace("zl_select_","");
					var url = cooperopcontextpath + "/w/application/scheme/query.html";
					var data = {};
					if ($this.is("[intable]")) {
						data = $("table[ctid='" + $this.attr("ctid") + "']").getData($this.attr("trid"));
					} else {
						data = $(document).getData();
					}
					$.modal(url, "选择" + $this.attr("label"), $.extend(true, data, {
						width: "90%",
						height: "90%",
						fangalx: schemeid,
						fangabh: scheme,
						callback: function(rtn) {
							if (rtn) {
								for (var i = 0, ilen = rtn.length; i < ilen; i ++) {
									for (var k in rtn[i]["data"]) {
										if ($this.is("[intable]")) {
											$("#" + $this.attr("trid")).find("[name='" + k + "']").setData(rtn[i]["data"][k]);
										} else {
											$("[name='" + k + "'][intable!='intable']").setData(rtn[i]["data"][k]);
										}
									}
								}
							}
						}
					}));
				}else if(scheme.indexOf("ym_up_") > -1){
					scheme = scheme.replace("ym_up_","");
					var d = {
							"scheme":scheme,
							"gzid":$("[name='gzid']").val(),
							"djbh":$("[name='djbh']").val(),
							"clientid":$("[name='clientid']").val(),
							"djbs":$("[name='djbs']").val(),
							"djlx":$("[name='djlx']").val(),
							"rq":$("[name='rq']").val(),
							"kaiprq":$("[name='kaiprq']").val(),
							"riqi":$("[name='riqi']").val(),
							"ontime":$("[name='ontime']").val(),
							"gzid":$("[name='gzid']").val(),
							"pageid":$("[name='pageid']").val()
					};
					$(document).find("[ctype='form'][cinited]").each(function(){
						$.extend(true , d , $(this).getData());
					});
					d.tables = [];
					$(document).find("[ctype='table'][cinited]").each(function(index,tobj){
						var table = {"tableid":$(tobj).attr("tableid")};
						table.tr = [];
						$(tobj).find("tbody>tr").each(function(){
							table.tr.push($(this).getData());
						})
						d.tables.push(table);
					});
					$.call("application.scheme.executeYmUpScheme",{"data":$.toJSON(d)},function(rtn){
						for(var key in rtn){
							$(document).find("[ctype='form']").find("[name='" + key + "'][intable!='intable']").setData(rtn[key]);
							$(document).find("[ctype='table'][cinited]").refresh();
						}
						if(rtn.schemeOut){
							$.message(rtn.schemeOut);
						}
					});
				}
			}
			$this.attr("autocomplete","off");
		}
		if ($this.is("[modify_action]")) {
			$this.on("change", function() {
				exe($this.attr("modify_action"));
			});
		}
		if ($this.is("[enter_action]")) {
			$this.on("focus", function() {
				exe($this.attr("enter_action"));
			});
		}
		if ($this.is("[out_action]")) {
			$this.on("blur", function() {
				exe($this.attr("out_action"));
			});
		}
		if ($this.is("[dbl_action]")) {
			$this.siblings(".open-icon").on("click", function() {
				exe($this.attr("dbl_action"));
			});
			$this.on("dblclick", function() {
				exe($this.attr("dbl_action"));
			});
		}
		
		if ($this.attr("action")) {
			$this.data("params", $this.attr("params") ? $.parseJSON($this.attr("params")) : {});
			var objtemplate = null;
			var htmltemplate = null;
			var index = 0;
			$this.find("label.radio-inline").each(function(i) {
				if ($(this).find(":radio").val().indexOf("$[") > -1) {
					objtemplate = $(this);
					htmltemplate = this.outerHTML;
					index = i;
					return false;
				} 
			});
			
			if (objtemplate) {
				$.call($this.attr("action"), $this.data("params"), function(rtn) {
					var html = [];
					for (var i in rtn.resultset) {
						html.push($.dlexpr(htmltemplate, rtn.resultset[i]));
					}
					objtemplate.replaceWith(html.join(""));
					create();
				});
			}
		} else {
			create();
		}
		$this.attr("cinited", "cinited");
	},
	"params_radio": function(params) {
		var $this = this;
		var tps = $this.attr("params") ? $.parseJSON($this.attr("params")) : {};
		$this.data("params", $.extend(true, tps, params));
	},
	"refresh_radio": function(params) {
		var $this = this;
		
	},
	"getData_radio": function() {
		var $this = this;
		var value = "";
		$this.find("input[type='radio']").each(function() {
			if (this.checked) {
				value = $(this).val();
				return false;
			}
		});
		var d = {};
		d[$this.find("input[type='radio']").attr("name")] = value
		return d;
	},
	"setData_radio" : function(value) {
		var $this = this;
		$this.find("input[type='radio']").each(function() {
			this.checked=false;
			$(this).removeAttr("checked");
			$(this).parent().removeClass("checked");
			if (value == $(this).val()) {
				this.checked=true;
				$(this).attr("checked", "checked");
				$(this).parent().addClass("checked");
				return false;
			}
		});
	}
});
