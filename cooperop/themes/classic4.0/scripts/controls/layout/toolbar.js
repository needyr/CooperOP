$.fn.extend({
	"ccinit_toolbar": function() {
		var $this = this;
		$this.attr("cinited", "cinited");
		$this.find("ul>li>a").on("click", function() {
			var $th = $(this);
			if($th.is("[action]")){
				var arg = $th.attr("action");
				/*var d = {
						"gzid":$("[name='gzid']").val(),
						"djbh":$("[name='djbh']").val(),
						"clientid":$("[name='clientid']").val(),
						"djbs":$("[name='djbs']").val(),
						"djlx":$("[name='djlx']").val(),
						"rq":$("[name='rq']").val(),
						"kaiprq":$("[name='kaiprq']").val(),
						"riqi":$("[name='riqi']").val(),
						"ontime":$("[name='ontime']").val(),
						//"gzid":$("[name='gzid']").val(),
						"pageid":$("[name='pageid']").val(),
						"jigid":$("[name='jigid']").val(),
						"dep_code":$("[name='dep_code']").val(),
						"dep_name":$("[name='dep_name']").val()
				};*/
				var dd = {
						"gzid":$("[name='gzid']").val(),
						"djbh":$("[name='djbh']").val(),
						"djbs":$("[name='djbs']").val(),
						"djlx":$("[name='djlx']").val(),
						"pageid":$("[name='pageid']").val()
				};
				if($("[name='tablekey_']").val()){
					var tablekey_ = $("[name='tablekey_']").val();
					dd[tablekey_] = $("[name='"+tablekey_+"']").val();
				}
				$(document).find("[ctype='form'][cinited]").each(function(){
					$.extend(true , dd , $(this).getData());
				});
				delete dd.chry;
				delete dd.hchry1;
				delete dd.qx;
				dd.tables = [];
				$(document).find("[ctype='table'][cinited]").each(function(index,tobj){
					var table = {"tableid":$(tobj).attr("tableid")};
					table.tr = [];
					var fields = $(tobj).data("_tc");
					$(tobj).find("tbody>tr").each(function(){
						var data = $.extend(true,{},$(tobj).getData($(this).attr("id")),$(this).getData());
						var tr = {};
						for(var i in fields){
							tr[fields[i].name]= data[fields[i].name];
							tr["dj_sn"] = data["dj_sn"];
							tr["dj_sort"] = data["dj_sort"];
						}
						table.tr.push(tr);
					})
					dd.tables.push(table);
				});
				if(arg == 'djsave'){
					if($("[name='task_id']").val()){
						dd.task_id = $("[name='task_id']").val();
					}
					$.inputbox("??????","????????????",function(r){
						if(r){
							dd.gzdesc = r;
							$.call("application.bill.save",{"data":$.toJSON(dd)},function(rtn){
								
							});
						}
					});
				}else if(arg == 'djsubmit'){
					if (!$("form").valid()) {
						return false;	
					}
					if($("[name='ptableid']").val()){
						var da = $(document).getData()
						delete da.djlx;
						delete da.djbs;
						delete da.gzid;
						da.tableid= $("[name='ptableid']").val();
						da.dj_sn = $("[name='p_dj_sn']").val();
						da.dj_sort = $("[name='p_dj_sort']").val();
						da.gzid = $("[name='p_gzid']").val();
						da.pageid = $("[name='p_pageid']").val();
						delete da.p_pageid;
						delete da.ptableid;
						delete da.p_dj_sn;
						delete da.p_dj_sort;
						delete da.p_gzid;
						delete da.company_id;
						$.confirm("?????????????????????", function(r) {
							if (r) {
								$.call("application.bill.addMX" ,da ,function(rtn){
									$.closeModal(true);
								});
							}
						});
					}else{
						$.confirm("?????????????????????", function(r) {
							if (r) {
								$.call("application.bill.submit",{"data":$.toJSON(dd)},function(rtn){
									//$("[name='" + k + "'][intable!='intable']").setData(rtn[i]["data"][k]);
									if(rtn=="-1"){
										$.message("????????????????????????????????????????????????");
									}else{
										if($("[name='fromtable']").val() == 'Y'){
											$.closeModal(true);
										}else{
											location.reload();
										}
									}
								});
							}
						});
					}
				}else if(arg == 'djsubmitWithOutTips'){
					if (!$("form").valid()) {
						return false;	
					}
					if($("[name='ptableid']").val()){
						var da = $(document).getData()
						delete da.djlx;
						delete da.djbs;
						delete da.gzid;
						da.tableid= $("[name='ptableid']").val();
						da.dj_sn = $("[name='p_dj_sn']").val();
						da.dj_sort = $("[name='p_dj_sort']").val();
						da.gzid = $("[name='p_gzid']").val();
						da.pageid = $("[name='p_pageid']").val();
						delete da.p_pageid;
						delete da.ptableid;
						delete da.p_dj_sn;
						delete da.p_dj_sort;
						delete da.p_gzid;
						delete da.company_id;
						$.call("application.bill.addMX" ,da ,function(rtn){
							$.closeModal(true);
						});
					}else{
						$.call("application.bill.submit",{"data":$.toJSON(dd)},function(rtn){
							//$("[name='" + k + "'][intable!='intable']").setData(rtn[i]["data"][k]);
							if(rtn=="-1"){
								$.message("????????????????????????????????????????????????");
							}else{
								if($("[name='fromtable']").val() == 'Y'){
									$.closeModal(true);
								}else{
									location.reload();
								}
							}
						});
					}
				}else if(arg == 'djsubAndClose'){
					if (!$("form").valid()) {
						return false;	
					}
					if($("[name='ptableid']").val()){
						var da = $(document).getData()
						delete da.djlx;
						delete da.djbs;
						delete da.gzid;
						da.tableid= $("[name='ptableid']").val();
						da.dj_sn = $("[name='p_dj_sn']").val();
						da.dj_sort = $("[name='p_dj_sort']").val();
						da.gzid = $("[name='p_gzid']").val();
						da.pageid = $("[name='p_pageid']").val();
						delete da.p_pageid;
						delete da.ptableid;
						delete da.p_dj_sn;
						delete da.p_dj_sort;
						delete da.p_gzid;
						delete da.company_id;
						$.confirm("?????????????????????", function(r) {
							if (r) {
								$.call("application.bill.addMX" ,da ,function(rtn){
									$.closeModal(true);
								});
							}
						});
					}else{
						$.confirm("?????????????????????", function(r) {
							if (r) {
								$.call("application.bill.submit",{"data":$.toJSON(dd)},function(rtn){
									//$("[name='" + k + "'][intable!='intable']").setData(rtn[i]["data"][k]);
									if(rtn=="-1"){
										$.message("????????????????????????????????????????????????");
									}else{
										$.message("???????????????",function(){
											if($("[name='fromtable']").val() == 'Y'){
												$.closeModal(true);
											}else{
												var $tabps = $('.page-content-tabs', parent.document);
												var id = $tabps.find(".nav").find(".active").attr("wid");
												var active = $tabps.find(".nav").find("li[wid='" + id + "']").hasClass("active");
												var index = $tabps.find(".nav").find("li[wid='" + id + "']").index();
												$tabps.find(".nav").find("li[wid='" + id + "']").remove();
												$tabps.find(".tab-content").find(".tab-pane[wid='" + id + "']").remove();
												if (active) {
													id = $tabps.find(".nav").find("li:eq(" + (index < $tabps.find(".nav").find("li").length ? index : index - 1) +")").attr("wid");
													$tabps.find(".nav").find("li[wid='" + id + "']").addClass("active");
													$tabps.find(".tab-content").find(".tab-pane[wid='" + id + "']").addClass("active");
												}
												$tabps.find(".nav").data('tabdrop').layout();
												if ($.browser.msie) {
													CollectGarbage();
												}
											}
										});
									}
								});
							}
						});
					}
				}else  if(arg == 'approval'){
					if (!$("form").valid()) {
						return false;	
					}
					$.confirm("?????????????????????", function(r) {
						if (r) {
							dd.task_id = $("[name='task_id']").val();
							dd.adivce = $("[name='adivce']").val();
							dd.audited = "Y";
							$.call("application.bill.approval",{"data":$.toJSON(dd)},function(rtn){
								//if(rtn){
								//	$.message("???????????????");
									$.closeModal(true);
								//}
							});
						}
					});
				}else if(arg == 'approval_checkfinger'){
					if (!$("form").valid()) {
						return false;	
					}
					$.confirm("?????????????????????", function(r) {
						if (r) {
							var cft = $.checkfingerFun()
							if(cft){
								dd.task_id = $("[name='task_id']").val();
								dd.adivce = $("[name='adivce']").val();
								dd.audited = "Y";
								$.call("application.bill.approval",{"data":$.toJSON(dd)},function(rtn){
									//if(rtn){
									//	$.message("???????????????");
										$.closeModal(true);
									//}
								});
							}
						}
					});
				}else if(arg == 'reject_checkfinger'){
					if (!$("form").valid()) {
						return false;	
					}
					$.confirm("?????????????????????", function(r) {
						if (r) {
							var cft = $.checkfingerFun()
							if(cft){
								dd.task_id = $("[name='task_id']").val();
								dd.adivce = $("[name='adivce']").val();
								dd.audited = "Y";
								$.call("application.bill.approval",{"data":$.toJSON(dd)},function(rtn){
									//if(rtn){
									//	$.message("???????????????");
										$.closeModal(true);
									//}
								});
							}
						}
					});
				}else if(arg == 'reject'){
					$.confirm("?????????????????????", function(r) {
						if (r) {
							dd.task_id = $("[name='task_id']").val();
							dd.adivce = $("[name='adivce']").val();
							dd.audited = "N";
							$.call("application.bill.approval",{"data":$.toJSON(dd)},function(rtn){
								if(rtn){
									$.message("???????????????");
									$.closeModal(true);
								}
							});
						}
					});
				}else if(arg == 'rejectback'){
					$.confirm("??????????????????????????????", function(r) {
						if (r) {
							dd.task_id = $("[name='task_id']").val();
							dd.adivce = $("[name='adivce']").val();
							dd.audited = "NL";
							$.call("application.bill.approval",{"data":$.toJSON(dd)},function(rtn){
								if(rtn){
									$.closeModal(true);
								}
							});
						}
					});
				}else if(arg == 'approvalnotsave'){
					$.confirm("?????????????????????", function(r) {
						if (r) {
							dd.task_id = $("[name='task_id']").val();
							dd.adivce = $("[name='adivce']").val();
							dd.audited = "Y";
							$.call("application.bill.approvalnotsave",{"data":$.toJSON(dd)},function(rtn){
								//if(rtn){
									//$.message("???????????????");
									$.closeModal(true);
								//}
							});
						}
					});
				}else if(arg == 'rejectnotsave'){
					$.confirm("?????????????????????", function(r) {
						if (r) {
							dd.task_id = $("[name='task_id']").val();
							dd.adivce = $("[name='adivce']").val();
							dd.audited = "N";
							$.call("application.bill.approvalnotsave",{"data":$.toJSON(dd)},function(rtn){
								if(rtn){
									$.message("???????????????");
									$.closeModal(true);
								}
							});
						}
					});
				}else if(arg == 'backprocess'){
					$.confirm("?????????????????????", function(r) {
						if (r) {
							$.call("application.bill.backprocess",{"data":$.toJSON(dd)},function(rtn){
								if(rtn=="1"){
									location.reload();
								}else{
									$.message("????????????????????????????????????????????????");
								}
							});
						}
					});
				}else if(arg == 'reaudit'){
					$.confirm("????????????", function(r) {
						if (r) {
							dd.task_id = $("[name='task_id']").val();
							dd.adivce = $("[name='adivce']").val();
							dd.audited = "R";
							$.call("application.bill.approvalnotsave",{"data":$.toJSON(dd)},function(rtn){
								if(rtn){
									$.closeModal(true);
								}
							});
						}
					});
				}else if(arg == 'querymx'){
					var da = $("form[ctype='form']").getData();
					da.xaxis = $("input[name='xaxis']").val();
					$("table[ctype='table']").each(function(){
						$(this).params(da);
						$(this).refresh();
					});
					
				}else if(arg == 'querygz'){
					var u = window.location.href;
					var url = cooperopcontextpath + "/w/application/bill/querygz.html";
					var djbhbs = $("input[name='djbs']").val()+""+$("input[name='djlx']").val();
					$.modal(url, "???????????????",{
						djbhbs: djbhbs,
						callback : function(rtn) {
							if (rtn) {
								window.location = u+"&gzid="+rtn.gzid;
							}
						}
					});
				}else if(arg == 'backprocess'){
					$.confirm("?????????????????????", function(r) {
						if (r) {
							$.call("application.bill.backprocess",{"data":$.toJSON(dd)},function(rtn){
								if(rtn=="1"){
									location.reload();
								}else{
									$.message("????????????????????????????????????????????????");
								}
							});
						}
					});
				}else if(arg == 'resubmit'){
					$.confirm("?????????????????????", function(r) {
						if (r) {
							$.call("application.bill.resubmit",{"data":$.toJSON(dd)},function(rtn){
								if(rtn=="-1"){
									$.message("????????????????????????????????????????????????");
								}else{
									if($("[name='fromtable']").val() == 'Y'){
										$.closeModal(true);
									}else{
										location.reload();
									}
								}
							});
						}
					});
				}else if(arg.indexOf("zl_select_") > -1){
					var schemeid = $("input[name='djlx']").val();
					var scheme = arg.replace("zl_select_","");
					var url = cooperopcontextpath + "/w/application/scheme/query.html";
					var data = {};
					if ($this.is("[intable]")) {
						data = $("table[ctid='" + $this.attr("ctid") + "']").getData($this.attr("trid"));
					} else {
						data = $(document).getData();
					}
					$.modal(url, "??????", $.extend(true, data, {
						width: "90%",
						height: "90%",
						system_product_code: module,
						fangalx: schemeid,
						fangabh: scheme,
						v_get: $.trim($this.val()) + '%', 
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
				}else if(arg.indexOf("dj_select_") > -1){
					var schemeid = $("input[name='djlx']").val();
					var flag = $("input[name='djbs']").val();
					var scheme = arg.replace("dj_select_","");
					var url = cooperopcontextpath + "/w/application/scheme/query.html";
					var data = {};
					if ($this.is("[intable]")) {
						data = $("table[ctid='" + $this.attr("ctid") + "']").getData($this.attr("trid"));
					} else {
						data = $(document).getData();
					}
					$.modal(url, "??????", $.extend(true, data, {
						width: "90%",
						height: "90%",
						system_product_code: module,
						flag: flag,
						fangalx: schemeid,
						fangabh: scheme,
						v_get: $.trim($this.val()) + '%',
						schemetype: "dj_select_",
						callback: function(rtn) {
							if (rtn) {
								for(var key in rtn){
									$(document).find("[ctype='form']").find("[name='" + key + "'][intable!='intable']").setData(rtn[key]);
									$(document).find("[ctype='table'][cinited]").refresh();
								}
							}
						}
					}));
				} else if(arg.indexOf("ym_up_") > -1){
					var scheme = arg.replace("ym_up_","");
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
						if($(tobj).getData()){
							table.tr = $(tobj).getData()[table["tableid"]];
							var selected_ = $(tobj).getSelected();
							$(tobj).find("tbody>tr").each(function(){
								var data = $.extend(true,{},$(tobj).getData($(this).attr("id")),$(this).getData());
								var tr = {};
								for(var i in fields){
									tr[fields[i].name]= data[fields[i].name];
									tr["dj_sn"] = data["dj_sn"];
									tr["dj_sort"] = data["dj_sort"];
								}
								for(var i in selected_){
									if(selected_[i].id == $(this).attr("id")){
										tr.xuanze = 1;
										continue;
									}
								}
								table.tr.push(tr);
							});
						}else{
							table.tr = [];
						}
						
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
			}else if(arg.indexOf("da_yin_") > -1){
				var scheme = arg.replace("da_yin_","");
				dd.selfprint = "Y";
				dd.no = scheme;
				if($("[name='tablekey_']").val()){
					var tablekey_ = $("[name='tablekey_']").val();
					dd["tablekey_"] = tablekey_;
					dd[tablekey_] = $("[name='"+tablekey_+"']").val();
				}
				$.modal(cooperopcontextpath + "/w/application/jasper.initJasper.pdf","??????", dd);
			}else if(arg.indexOf("da_save_yin_") > -1){
				if (!$("form").valid()) {
					return false;	
				}
				$.confirm("?????????????????????", function(r) {
					if (r) {
						$.call("application.bill.submit",{"data":$.toJSON(dd)},function(rtn){
							//$("[name='" + k + "'][intable!='intable']").setData(rtn[i]["data"][k]);
							if(rtn=="-1"){
								$.message("???????????????????????????????????????????????????");
							}else{
								var scheme = arg.replace("da_save_yin_","");
								dd.selfprint = "Y";
								dd.no = scheme;
								if($("[name='tablekey_']").val()){
									var tablekey_ = $("[name='tablekey_']").val();
									dd["tablekey_"] = tablekey_;
									dd[tablekey_] = $("[name='"+tablekey_+"']").val();
								}
								dd.djbh = rtn;
								$.modal(cooperopcontextpath + "/w/application/jasper.initJasper.pdf","??????", 
									$.extend(true, {
										callback : function(r) {
											location.reload();
										}
									}, dd));
								//$("[name='djbh']").val(rtn);
							}
						});
					}
				});
			
			}else if(arg == 'reload_usercache'){
				$.call("application.user.loadUserCache",{},function(rtn){
					if(rtn){
						$.message("?????????????????????")
					}
				});
			}
			}
		});
		$this.find("ul>li>a").each(function(i,o){
			
		});
	}
});
