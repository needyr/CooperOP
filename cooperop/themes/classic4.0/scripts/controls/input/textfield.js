function showhref_(_this){
	var $this = $(_this);
	var paid = $this.attr("is_href");
	var url;
	if(paid){
		url = cooperopcontextpath + "/w/" + paid.replace(/\./g, "/") + ".html";
	}else{
		return;
	}
	var data = {};
	if ($this.is("[intable]")) {
		var trid = $this.parents("tr:eq(0)").attr("id");
		var ctid = $this.parents("tr:eq(0)").attr("ctid");
		var table = $("table[ctid='" + ctid + "']").DataTable();
		data = table.row("#" + trid).data()._CT_Data;
	} else {
		data = $("[name='" + k + "'][intable!='intable']").getData();
	}
	data = $.extend(true, $("[ctype='form']").getData() || {}, data);
	$.modal(url, " ", $.extend(true, data, {
		width: "100%",
		height: "100%",
		callback: function(rtn) {}
	}));
}
$.fn.extend({
	"ccinit_textfield": function() {
		var $this = this;
		if ($.is_mobile()) {
		
		} else {
			
		}
		if ($this.is("[intable]")) {
			$this.attr("trid", $this.parents("tr:eq(0)").attr("id"));
			$this.attr("ctid", $this.parents("tr:eq(0)").attr("ctid"));
		}
		
		if($this.is("[modify_action]") || $this.is("[enter_action]") || $this.is("[out_action]") || $this.is("[dbl_action]")){
			var exe = function(scheme) {
				var args = scheme.split(",");
				scheme = args[0];
				var schemeid = $("input[name='djlx']").val();
				if(!schemeid){
					if(args.length > 1){
						schemeid = args[1];
					}else{
						return;
					}
				}
				if(scheme.indexOf("dj_select_") > -1){
					var data = {};
					if ($this.is("[intable]")) {
						data = $("table[ctid='" + $this.attr("ctid") + "']").getData($this.attr("trid"));
					} else {
						data = $(document).getData();
					}
					$this.attr("hasdbl", "Y");
					scheme = scheme.replace("dj_select_","");
					var url = cooperopcontextpath + "/w/application/scheme/query.html";
					$.modal(url, $this.attr("label")|| "", $.extend(true, data, {
						width: "90%",
						height: "90%",
						system_product_code: module,
						fangalx: schemeid,
						fangabh: scheme,
						schemetype :"dj_select_",
						v_get: $.trim($this.val()) + '%', 
						callback: function(rtn) {
							$this.removeAttr("hasdbl");
							if (rtn) {
								for(var key in rtn){
									$(document).find("[ctype='form']").find("[name='" + key + "'][intable!='intable']").setData(rtn[key]);
									$(document).find("[ctype='table'][cinited]").refresh();
								}
							}
						}
					}));
				}else if(scheme.indexOf("zl_select_") > -1){
					scheme = scheme.replace("zl_select_","");
					var url = cooperopcontextpath + "/w/application/scheme/query.html";
					var data = {};
					if ($this.is("[intable]")) {
						data = $("table[ctid='" + $this.attr("ctid") + "']").getData($this.attr("trid"));
					} else {
						data = $(document).getData();
					}
					var hdd = $.extend(true, data, {
						system_product_code: module,
						fangalx: schemeid,
						fangabh: scheme,
						schemetype :"zl_select_",
						v_get: $.trim($this.val()) + '%'});
					$.call("application.scheme.check_isModal" ,hdd ,function(crtn){
						if(crtn.scheme.retu_one == "是" && crtn.count ==1){
							$.call("application.scheme.executeQueryScheme",hdd,function(ccrtn) {
								var rdata = ccrtn.resultset;
								for (var k in rdata[0]) {
									if ($this.is("[intable]")) {
										$("#" + $this.attr("trid")).find("[name='" + k + "']").setData(rdata[0][k]);
									} else {
										$("[name='" + k + "'][intable!='intable']").setData(rdata[0][k]);
									}
								}
							});
						}else{
							$this.attr("hasdbl", "Y");
							$.modal(url, crtn.scheme.dialog_cap || $this.attr("label")|| "", $.extend(true, data, {
								width: crtn.scheme.dialog_wid || "90%",
								height: crtn.scheme.dialog_hei || "90%",
								system_product_code: module,
								fangalx: schemeid,
								fangabh: scheme,
								v_get: $.trim($this.val()) + '%', 
								callback: function(rtn) {
									$this.removeAttr("hasdbl");
									if (rtn) {
										var da = {};
										if(crtn.scheme.multisel == '是'){
											for (var i = 0, ilen = rtn.length; i < ilen; i ++) {
												for (var k in rtn[i]["data"]) {
													if(da[k]){
														da[k] = da[k]+",'"+rtn[i]["data"][k]+"'";
													}else{
														da[k] = "'"+rtn[i]["data"][k]+"'";
													}
												}
											}
										}else{
											for (var i = 0, ilen = rtn.length; i < ilen; i ++) {
												for (var k in rtn[i]["data"]) {
													if(da[k]){
														da[k] = da[k]+","+rtn[i]["data"][k];
													}else{
														da[k] = rtn[i]["data"][k];
													}
												}
											}
										}
										
										for (var k in da) {
											if ($this.is("[intable]")) {
												$("#" + $this.attr("trid")).find("[name='" + k + "']").setData(da[k]);
											} else {
												$("[name='" + k + "'][intable!='intable']").setData(da[k]);
											}
										}
									}
								}
							}));
						}
					},null,{async: false});
				}else if(scheme.indexOf("ym_up_") > -1){
					scheme = scheme.replace("ym_up_","");
					var d = {
							"system_product_code": module,
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
						var dt = $(tobj).getData();
						$(tobj).find("tbody>tr").each(function(index){
							table.tr.push(dt[index]);
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
				}else if(scheme.indexOf("getpym") > -1){
					$.call("application.bill.getPYM", {v_get: $.trim($this.val())}, function(rtn){
						$(document).find("[ctype='form']").find("[name='pym'][intable!='intable']").setData(rtn.pym);
					}, null, {nomask: true});
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
				if($this.val()){
					setTimeout(function(){
						if(!$this.attr("hasdbl")){
							exe($this.attr("out_action"));
						}
					}, 500);
				}
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
		if ($this.is("[dblaction]")) {
			var open = function() {
				var arg = $this.attr("dblaction").split(",");
				var data = {};
				if ($this.is("[intable]")) {
					data =  $this.parents("tr:eq(0)").getData();
				} else {
					data = $(document).getData();
				}
				var schemeid = $("#schemeid").val();
				$.extend(true, data, {schemeid:schemeid,system_product_code:system_product_code, v_get: $.trim($this.val()) + '%'});
				if(!data.system_product_code){
					var system_product_code = $("#system_product_code").val();
					data.system_product_code = system_product_code;
				}
				data.queryvalue = $this.val();
				var url;
				var title = "";
				var width_ = "90%";
				var height_ = "90%";
				if(arg[0] == "queryschemes"){
					url = cooperopcontextpath + "/w/application/scheme/schemelist.html";
					$.extend(true, data, {djlx: arg[1], system_product_code: arg[2]});
					title = "方案";
				}else if(arg[0] == "queryfields"){
					url = cooperopcontextpath + "/w/crdc/bills/fieldslist.html";
					title = "字段";
				}else if(arg[0] == "fieldsmodify"){
					url = cooperopcontextpath + "/w/crdc/bills/modifyc.html";
					if(arg[1] == 'query'){
						url = cooperopcontextpath + "/w/crdc/query/modifyc.html";
					}else if(arg[1] == 'chart'){
						url = cooperopcontextpath + "/w/crdc/chart/modifyc.html";
					}
					
				}else if(arg[0] == "image_src"){
					url = cooperopcontextpath + "/w/crdc/image_src.html";
					title = "设置图片";
					width_ = "50%";
					height_ = "50%";
				}else if(arg[0] == "querycontract"){
					url = cooperopcontextpath + "/w/cr_reg/reg/contracts.html";
					title = "关联合同";
					width_ = "80%";
					height_ = "90%";
				}else if(arg[0]){
					url = cooperopcontextpath + "/w/" + arg[0].replace(/\./g, "/") + ".html";
					title = arg[1] || "";
				}
				if(url){
					$.modal(url, title || $this.data("title"), $.extend(true, data, {
						width: width_,
						height: height_,
						callback: function(rtn) {
							if(arg[0] == "fieldsmodify"){
								for (var k in rtn) {
									if ($this.is("[intable]")) {
										$("#" + $this.attr("trid")).find("[name='" + k + "']").setData(rtn[k]);
									} else {
										$("[name='" + k + "'][intable!='intable']").setData(rtn[k]);
									}
								}
								return;
							}
							
							if (rtn) {
								if(arg[0] == "queryschemes"){
									$this.val(rtn[0]["data"].fanganbh);
									return ;
								}
								var da = {};
								for (var i = 0, ilen = rtn.length; i < ilen; i ++) {
									for (var k in rtn[i]["data"]) {
										if(da[k]){
											da[k] = da[k]+","+rtn[i]["data"][k];
										}else{
											da[k] = rtn[i]["data"][k];
										}
									}
								}
								for (var k in da) {
									if ($this.is("[intable]")) {
										$("#" + $this.attr("trid")).find("[name='" + k + "']").setData(da[k]);
									} else {
										$("[name='" + k + "'][intable!='intable']").setData(da[k]);
									}
								}
							
							}
						}
					}));
				}
			}
			$this.siblings(".open-icon").on("click", function() {
				open();
			});
			$this.on("dblclick", function() {
				open();
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
		$this.attr("autocomplete","off");
		$this.attr("cinited", "cinited");
		$this.on("focus", function(){
			var p = $this.parent();
			if(p.attr("eidx")){
				$("#layui-layer"+p.attr("eidx")).remove();
			}
		});
	}
});
