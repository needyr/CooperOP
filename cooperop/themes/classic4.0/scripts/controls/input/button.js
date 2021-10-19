$.fn.extend({
	"ccinit_button": function() {
		var $this = this;
		if ($.is_mobile()) {
		
		} else {
			
		}
		$this.click(function() {
			//$.console().log($(this).attr("action"));
		});
		$this.attr("cinited", "cinited");
		
		if($this.is("[action]")){
			var exe = function() {
				/*var dd = {
						"gzid":$("[name='gzid']").val(),
						"djbh":$("[name='djbh']").val(),
						"clientid":$("[name='clientid']").val(),
						"djbs":$("[name='djbs']").val(),
						"djlx":$("[name='djlx']").val(),
						"rq":$("[name='rq']").val(),
						"kaiprq":$("[name='kaiprq']").val(),
						"riqi":$("[name='riqi']").val(),
						"ontime":$("[name='ontime']").val(),
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
						"pageid":$("[name='pageid']").val(),
						"jigname":$("[name='jigname']").val()
				};
				var _CRSID = {
					"_CRSID":$("[name='_CRSID']").val()
				}
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
					
					dd.tables.push(table);
				});
				var arg = $this.attr("action");
				if(arg == 'djsave'){
					if($("[name='task_id']").val()){
						dd.task_id = $("[name='task_id']").val();
					}
					$.inputbox("挂账","临时挂账",function(r){
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
						$.confirm("是否确认提交？", function(r) {
							if (r) {
								$.call("application.bill.addMX" ,da ,function(rtn){
									$.closeModal(true);
								});
							}
						});
					}else{
						$.confirm("是否确认提交？", function(r) {
							if (r) {
								$.call("application.bill.submit",{"data":$.toJSON(dd)},function(rtn){
									//$("[name='" + k + "'][intable!='intable']").setData(rtn[i]["data"][k]);
									if(rtn=="-1"){
										$.message("数据已过期，请刷新页面重新填写！");
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
								$.message("数据已过期，请刷新页面重新填写！");
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
						$.confirm("是否确认提交？", function(r) {
							if (r) {
								$.call("application.bill.addMX" ,da ,function(rtn){
									$.closeModal(true);
								});
							}
						});
					}else{
						$.confirm("是否确认提交？", function(r) {
							if (r) {
								$.call("application.bill.submit",{"data":$.toJSON(dd)},function(rtn){
									//$("[name='" + k + "'][intable!='intable']").setData(rtn[i]["data"][k]);
									if(rtn=="-1"){
										$.message("数据已过期，请刷新页面重新填写！");
									}else{
										$.message("提交成功！",function(){
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
				}else if(arg == 'approval'){
					if (!$("form").valid()) {
						return false;	
					}
					$.confirm("是否确认通过？", function(r) {
						if (r) {
							dd.task_id = $("[name='task_id']").val();
							dd.adivce = $("[name='adivce']").val();
							dd.audited = "Y";
							$.call("application.bill.approval",{"data":$.toJSON(dd)},function(rtn){
								//if(rtn){
								//	$.message("操作成功！");
									$.closeModal(true);
								//}
							});
						}
					});
				}else if(arg == 'approval_checkfinger'){
					if (!$("form").valid()) {
						return false;	
					}
					$.confirm("是否确认通过？", function(r) {
						if (r) {
							var cft = $.checkfingerFun()
							if(cft){
								dd.task_id = $("[name='task_id']").val();
								dd.adivce = $("[name='adivce']").val();
								dd.audited = "Y";
								$.call("application.bill.approval",{"data":$.toJSON(dd)},function(rtn){
									//if(rtn){
									//	$.message("操作成功！");
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
					$.confirm("是否确认通过？", function(r) {
						if (r) {
							var cft = $.checkfingerFun()
							if(cft){
								dd.task_id = $("[name='task_id']").val();
								dd.adivce = $("[name='adivce']").val();
								dd.audited = "Y";
								$.call("application.bill.approval",{"data":$.toJSON(dd)},function(rtn){
									//if(rtn){
									//	$.message("操作成功！");
										$.closeModal(true);
									//}
								});
							}
						}
					});
				}else if(arg == 'reject'){
					$.confirm("是否确认驳回？", function(r) {
						if (r) {
							dd.task_id = $("[name='task_id']").val();
							dd.adivce = $("[name='adivce']").val();
							dd.audited = "N";
							$.call("application.bill.approval",{"data":$.toJSON(dd)},function(rtn){
								if(rtn){
									$.message("操作成功！");
									$.closeModal(true);
								}
							});
						}
					});
				}else if(arg == 'rejectback'){
					$.confirm("是否确认驳回上一步？", function(r) {
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
					$.confirm("是否确认通过？", function(r) {
						if (r) {
							dd.task_id = $("[name='task_id']").val();
							dd.adivce = $("[name='adivce']").val();
							dd.audited = "Y";
							$.call("application.bill.approvalnotsave",{"data":$.toJSON(dd)},function(rtn){
								//if(rtn){
									//$.message("操作成功！");
									$.closeModal(true);
								//}
							});
						}
					});
				}else if(arg == 'rejectnotsave'){
					$.confirm("是否确认驳回？", function(r) {
						if (r) {
							dd.task_id = $("[name='task_id']").val();
							dd.adivce = $("[name='adivce']").val();
							dd.audited = "N";
							$.call("application.bill.approvalnotsave",{"data":$.toJSON(dd)},function(rtn){
								if(rtn){
									$.message("操作成功！");
									$.closeModal(true);
								}
							});
						}
					});
				}else if(arg == 'backprocess'){
					$.confirm("是否确认撤回？", function(r) {
						if (r) {
							$.call("application.bill.backprocess",{"data":$.toJSON(dd)},function(rtn){
								if(rtn=="1"){
									location.reload();
								}else{
									$.message("单据没有在流程中，或流程已办结！");
								}
							});
						}
					});
				}else if(arg == 'newrecord'){
					if($this.parent().hasClass("active")){
						//多页签
						$this.parents(".portlet-title").parent().find(".tab-pane.active").find("[ctype='table']").addRecord();
					}else{
						$this.parents(".portlet-title").parent().find("[ctype='table']").addRecord();
					}
					
				}else if(arg == 'editrecord'){
					if($this.parent().hasClass("active")){
						//多页签
						var ta = $this.parents(".portlet-title").parent().find(".tab-pane.active").find("[ctype='table']");
						ta.editrecord(ta.getSelected());
					}else{
						var ta  = $this.parents(".portlet-title").parent().find("[ctype='table']");
						var rd = ta.getSelected();
						ta.editrecord(rd[0]);
					}
					
				}else if(arg == 'delectrecord'){
					$.confirm("是否确认删除记录？，删除后无法恢复！", function(r) {
						if (r) {
							if($this.parent().hasClass("active")){
								//多页签
								$this.parents(".portlet-title").parent().find(".tab-pane.active").find("[ctype='table']").deleterecord();
							}else{
								$this.parents(".portlet-title").parent().find("[ctype='table']").deleterecord();
							}
						}
					});
				}else if(arg == 'addrow'){
					if($this.parent().hasClass("active")){
						//多页签
						$this.parents(".portlet-title").parent().find(".tab-pane.active").find("[ctype='table']").newBlankRow();
					}else{
						$this.parents(".portlet-title").parent().find("[ctype='table']").newBlankRow();
					}
				}else if(arg == 'delectnotrecord'){
					$.confirm("是否确认删除记录？，删除后无法恢复！", function(r) {
						if (r) {
							if($this.parent().hasClass("active")){
								//多页签
								$this.parents(".portlet-title").parent().find(".tab-pane.active").find("[ctype='table']").deleterecord();
							}else{
								$this.parents(".portlet-title").parent().find("[ctype='table']").deleterecord({notdelete: "Y"});
							}
						}
					});
				}else if(arg == 'querymx'){
					var da = $("[ctype='form']").getData();
					da.xaxis = $("input[name='xaxis']").val();
					var tabp=[];
					$(".tab-pane").each(function(){
						var $this = $(this);
						if(!$this.hasClass('active')){
							tabp.push($this);
						}
					});
					for(var i = tabp.length - 1; i >= 0; i --){
						tabp[i].addClass("active");
					}
					if(da.querytabs){
						var qu = da.querytabs;
						$("table[ctype='table']").each(function(){
							for(var k = 0; k < qu.length; k++){
								if($(this).attr("tableid") == qu[k]){
									da.async = false;
									da.timeout = 0;
									$(this).params(da);
									$(this).refresh();
								}
							}
						});
					}else{
						$("table[ctype='table']").each(function(){
							da.async = false;
							da.timeout = 0;
							$(this).params(da);
							$(this).refresh();
						});
					}
					for(var i = 0 ;i<tabp.length ; i++){
						tabp[i].removeClass("active");
					}
					if(dd.djbs == 'chart'){
						//$.initChart();
						$("[ctype='chart']").each(function(){
							var $this = $(this);
							var ch_data = {};
							ch_data.timeout = 0;
							$.initChart($this.attr("flag"), dd.pageid,ch_data);
						});
					}
					
				}else if(arg == 'querygz'){
					var u = window.location.href;
					var url = cooperopcontextpath + "/w/application/bill/querygz.html";
					var djbhbs = $("input[name='djbs']").val()+""+$("input[name='djlx']").val();
					$.modal(url, "未完成单据",{
						djbhbs: djbhbs,
						callback : function(rtn) {
							if (rtn) {
								window.location = u+"&gzid="+rtn.gzid;
							}
						}
					});
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
					$.modal(url, "提取", $.extend(true, data, {
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
					$.modal(url, $this.text() || "", $.extend(true, data, {
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
				}else if(arg.indexOf("ym_up_") > -1){
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
							var selected_ = $(tobj).getSelected();
							for (var i = 0, ilen = $(tobj).DataTable().rows()[0].length; i < ilen; i++) {
								var tr = $.extend(true, {}, $(tobj).DataTable().row(i).data()._CT_Data);
								table.tr.push();
								
								for(var b in selected_){
									if(selected_[b].id == ("_"+$(tobj).attr("tableid") + "_row_" + (i+1))){
										tr.xuanze = 1;
										continue;
									}
								}
								table.tr.push(tr);
							}
							/*$(tobj).find("tbody>tr").each(function(){
								var data = $.extend(true,{},$(tobj).getData($(this).attr("id")),$(this).getData());
								var tr = {};
								for(var i in fields){
									tr[fields[i].name]= data[fields[i].name];
									tr["dj_sn"] = data["dj_sn"];
									tr["dj_sort"] = data["dj_sort"];
								}
							});*/
							
						}else{
							table.tr = [];
						}
						d.tables.push(table);
					});
					$.call("application.scheme.executeYmUpScheme",{"data": $.toJSON(d)},function(rtn){
						for(var key in rtn){
							$(document).find("[ctype='form']").find("[name='" + key + "'][intable!='intable']").setData(rtn[key]);
							$(document).find("[ctype='table'][cinited]").refresh();
						}
						if(rtn.schemeOut){
							$.message(rtn.schemeOut);
						}
					});
				}else if(arg.indexOf("da_yin_") > -1){
					if(typeof crtech != "undefined"){
						var scheme = arg.replace("da_yin_","");
						dd.selfprint = "Y";
						dd.no = scheme;
						if($("[name='tablekey_']").val()){
							var tablekey_ = $("[name='tablekey_']").val();
							dd["tablekey_"] = tablekey_;
							dd[tablekey_] = $("[name='"+tablekey_+"']").val();
						}
						//拼接checkbox打印的格式
						$("[ctype='checkbox']").each(function(indx, obj){
							var value = [];
							$(obj).find("input[type='checkbox']").each(function() {
								if (this.checked) {
									value.push( " ■ ");
								}else{
									value.push(" □ ");
								}
								value.push($(this).parents("label").text() + " ");
							});
							var checkbox_name = $(obj).attr("name");
							dd[checkbox_name] = value.join(" ");
						});
						delete dd.filename;
						var url = ws_config.http_url + "/w/application/jasper/initJasper.pdf?a=1";
						var hh = dd;
						hh._CRSID = _CRSID._CRSID;
						for(var key in hh){  
							var key_data = hh[key]
							if(typeof key_data != 'undefined'){
								key_data = key_data.toString().replace(/\#/g,' 井 ').replace(/\&/g,' 和 ');
							}
			            	url = url + "&" + key + "=" + key_data;
			    　　　　}
						setTimeout(function(){
							//$.closeModal(true);
							//location.reload();
						},1000);
						crtech.print(encodeURI(url), '830', '680');
					}else{
						var scheme = arg.replace("da_yin_","");
						dd.selfprint = "Y";
						dd.no = scheme;
						if($("[name='tablekey_']").val()){
							var tablekey_ = $("[name='tablekey_']").val();
							dd["tablekey_"] = tablekey_;
							dd[tablekey_] = $("[name='"+tablekey_+"']").val();
						}
						//拼接checkbox打印的格式
						$("[ctype='checkbox']").each(function(indx, obj){
							var value = [];
							$(obj).find("input[type='checkbox']").each(function() {
								if (this.checked) {
									value.push( " ■ ");
								}else{
									value.push(" □ ");
								}
								value.push($(this).parents("label").text() + " ");
							});
							var checkbox_name = $(obj).attr("name");
							dd[checkbox_name] = value.join(" ");
						});
						delete dd.filename;
						$.modal(cooperopcontextpath + "/w/application/jasper/initJasper.pdf","打印", dd);
						/*$.modal(cooperopcontextpath + "/w/application/jasper/initJasper.pdf","打印", 
						$.extend(true, {
							callback : function(r) {
								//location.reload();
							}
						}, dd));*/
					}
				}else if(arg.indexOf("da_save_yin_") > -1){
					if (!$("form").valid()) {
						return false;	
					}
					$.confirm("是否确认提交？", function(r) {
						if (r) {
							$.call("application.bill.submit",{"data":$.toJSON(dd)},function(rtn){
								//$("[name='" + k + "'][intable!='intable']").setData(rtn[i]["data"][k]);
								if(rtn=="-1"){
									$.message("单据已经进入流程，请先将流程撤回！");
								}else{
									
									if(typeof crtech != "undefined"){
										var scheme = arg.replace("da_save_yin_","");
										dd.selfprint = "Y";
										dd.no = scheme;
										dd.djbh = rtn;
										if($("[name='tablekey_']").val()){
											var tablekey_ = $("[name='tablekey_']").val();
											dd["tablekey_"] = tablekey_;
											dd[tablekey_] = $("[name='"+tablekey_+"']").val();
										}
										//拼接checkbox打印的格式
										$("[ctype='checkbox']").each(function(indx, obj){
											var value = [];
											$(obj).find("input[type='checkbox']").each(function() {
												if (this.checked) {
													value.push( " ■ ");
												}else{
													value.push(" □ ");
												}
												value.push($(this).parents("label").text() + " ");
											});
											var checkbox_name = $(obj).attr("name");
											dd[checkbox_name] = value.join(" ");
										});
										delete dd.filename;
										var url = ws_config.http_url + "/w/application/jasper/initJasper.pdf?a=1";
										var hh = dd;
										hh._CRSID = _CRSID._CRSID;
										for(var key in hh){
											var key_data = hh[key]
											if(typeof key_data != 'undefined'){
												key_data = key_data.toString().replace(/\#/g,' 井 ').replace(/\&/g,' 和 ');
											}
							            	url = url + "&" + key + "=" + key_data;
							    　　　　}
										setTimeout(function(){
											//location.reload();
											$.closeModal(true);
										},1000);
										crtech.print(encodeURI(url), '830', '680');
									}else{
										var scheme = arg.replace("da_save_yin_","");
										dd.selfprint = "Y";
										dd.no = scheme;
										dd.djbh = rtn;
										if($("[name='tablekey_']").val()){
											var tablekey_ = $("[name='tablekey_']").val();
											dd["tablekey_"] = tablekey_;
											dd[tablekey_] = $("[name='"+tablekey_+"']").val();
										}
										//拼接checkbox打印的格式
										$("[ctype='checkbox']").each(function(indx, obj){
											var value = [];
											$(obj).find("input[type='checkbox']").each(function() {
												if (this.checked) {
													value.push( " ■ ");
												}else{
													value.push(" □ ");
												}
												value.push($(this).parents("label").text() + " ");
											});
											var checkbox_name = $(obj).attr("name");
											dd[checkbox_name] = value.join(" ");
										});
										delete dd.filename;
										//$.modal(cooperopcontextpath + "/w/application/jasper/initJasper.pdf","打印", dd);
										$.modal(cooperopcontextpath + "/w/application/jasper/initJasper.pdf","打印", 
										$.extend(true, {
											callback : function(r) {
												//location.reload();
												try{
													$.closeModal(true);
												}catch(e){}
												try{
													$.closeTabpage();
												}catch(e){}
											}
										}, dd));
									}
									//$("[name='djbh']").val(rtn);
								}
							});
						}
					});
				}else if(arg == 'custom_show_table'){
					if($this.parent().hasClass("active")){
						//多页签
						$this.parents(".portlet-title").parent().find(".tab-pane.active").find("[ctype='table']").setting_table();
					}else{
						$this.parents(".portlet-title").parent().find("[ctype='table']").setting_table();
					}
				}else if(arg == 'custom_export_table'){
					if($this.parent().hasClass("active")){
						//多页签
						$this.parents(".portlet-title").parent().find(".tab-pane.active").find("[ctype='table']").export_table();
					}else{
						$this.parents(".portlet-title").parent().find("[ctype='table']").export_table();
					}
				}else if(arg == 'custom_print_table'){
					if($this.parent().hasClass("active")){
						//多页签
						$this.parents(".portlet-title").parent().find(".tab-pane.active").find("[ctype='table']").print_table();
					}else{
						$this.parents(".portlet-title").parent().find("[ctype='table']").print_table();
					}
				}else if(arg == 'exportexcel'){
					/*var $table = $($("table[ctype='table']")[0]);
					$("table[ctype='table']").each(function(index,obj){
						if($(obj).parents(".tab-pane") && $(obj).parents(".tab-pane").hasClass("active")){
							$table = $(obj);
						}
					})
					var fields = [];
					var tc = $table.data("_tc");
					var fielddefs = {"tc": tc};
					var f = {};
					for(var i in tc){
						if(tc[i].name !='_select_' && tc[i].name != 'DT_Select'){
							fields.push(tc[i].name);
							var ff = tc[i].name;
							f[ff] = tc[i];
						}
					}
					var da = $("[ctype='form']").getData();
					if(!da.jigid){
						da.jigid = dd.jigid;
					}
					if(!da.djbh){
						da.djbh = dd.djbh;
					}
					da.gzid = dd.gzid;
					da.pageid = $("input[name='pageid']").val();
					da.xaxis = $("input[name='xaxis']").val();
					da.tableid = $table.attr("tableid");
					da.fields = fields.join(",");
					da.fielddefs = $.toJSON(f);
					da.$exportname = $this.parents(".portlet-title").find(".caption").text().trim();
					$.exportExcel("application.excel.export", da);*/
					/*var uu = cooperopcontextpath + "/w/application/excel/export.xlsx";
					$.modal(uu, "导出", $.extend(true, {
						callback : function(rtn) {
							
						}
					}, da));*/
					function excuteExpressionD(expression,map){
						for(var key in map){
							expression = replaceOnlyStr(expression,"\\$\\[" + key + "\\]", map[key] ? map[key] + "" : "");
						}
						return expression;
					}
					
					function replaceOnlyStr(str,patten,replacement){
						if (str) {
							return str.replace(new RegExp(patten,"g"), replacement);
						}else if(str.indexOf(patten) < 0){
							return str;
						}else{
							return "";
						}
					}
					
					function getHtmlTagVal(input, key){
						if (input) {
							var rows = $(input).find("[name="+key+"]");
							if (rows.size() <= 0 && rows.prevObject && rows.prevObject.size() <= 0) {
								return "";
							}else {
								var str = [];
								if(rows.size() <= 0){
									rows = rows.prevObject
								}
								rows.each(function(){
									var row = $(this)[0];
									if(row.tagName == 'SELECT'){
										var value_sel = row.getAttribute('value');
										var opts = row.getElementsByTagName('option');
										for(var j=0;j<opts.length;j++){
											var opts_val = opts[j].getAttribute('value');
											if(value_sel == opts_val){
												if(opts[j].innerText) {
													str.push(opts[j].innerText);
												}else if(opts_val) {
													str.push(opts_val);
												}
											}
										}
									}else if(row.tagName == 'INPUT' || row.tagName == 'DIV'){
										var check = (row.getAttribute("type") == "checkbox" 
											|| row.getAttribute("type") == "radio"
											||row.getAttribute("ctype") == "checkbox" 
											|| row.getAttribute("ctype") == "radio");
										if(check && row.tagName == 'DIV'){
											var checkboxs = row.getElementsByClassName("checkbox-inline");
						    				var val = ","+(row.getAttribute('value')?row.getAttribute('value'):'') + ",";
						    				for(var j2=0;j2<checkboxs.length;j2++){
						    					var checkbox = $(checkboxs[j2]).find('[name='+key+']');
						    					if(val.indexOf(","+checkbox[0].getAttribute('value')+",")>=0) {
													if($(checkboxs[j2])[0].innerText) {
														str.push($(checkboxs[j2])[0].innerText);
													}else if(checkbox[0].getAttribute('value')) {
														str.push(checkbox[0].getAttribute('value'));
													}
												}
						    				}
						    			}else if(!check && row.getAttribute("type") != "hidden" && row.tagName == 'INPUT'){
						    				if(row.getAttribute('value')) {
						    					str.push(row.getAttribute('value'));
						        			}
						    			}else if(row.tagName == 'DIV' && row.getAttribute("ctype") != "hidden"){
						    				if(row.innerText) {
							    				str.push(row.innerText);
							    			}
						    			}
									}else if(row.getAttribute("ctype") != "checkbox" && row.getAttribute("ctype") != "radio"){
										if(row.innerText) {
						    				str.push(row.innerText);
						    			}
									}
								})
								return str.join(',');
							}
						}else{
							return "";
						}
					}
					var foot = '';
					var $table = $($("table[ctype='table']")[0]);
					$("table[ctype='table']").each(function(index,obj){
						if($(obj).parents(".tab-pane") && $(obj).parents(".tab-pane").hasClass("active")){
							$table = $(obj);
						}
					})
					var _tc_tmp = $table.data("_tc");
					var data_new = [];
					var _tc = [];
					for (var c in _tc_tmp) {
						_tc.push(_tc_tmp[c]);
					}
					_tc.sort(function(a, b) {
						return a.order - b.order;
					});
					data_new = _tc;
					var action = $table.attr("action");
					var data = $table.data("params") || {};
					$.extend( data , $("[ctype='form']").getData());
					data.start = 0;
					data.limit = -1;
					if(!data.jigid){
						data.jigid = dd.jigid;
					}
					if(!data.djbh){
						data.djbh = dd.djbh;
					}
					data.gzid = dd.gzid;
					data.pageid = $("input[name='pageid']").val();
					data.xaxis = $("input[name='xaxis']").val();
					data.tableid = $table.attr("tableid");
					data.$exportname = $this.parents(".portlet-title").find(".caption").text().trim();
					$("table[ctype='table']").each(function(index,obj){
						if($(obj).parents(".tab-pane") && $(obj).parents(".tab-pane").hasClass("active")){
							data.$exportname = $this.parents(".portlet-title").find(".nav-tabs li[class=active]").text().trim();
						}
					})
					$.call(action,data,function(rtn){
						var rt = rtn.resultset;
						var fileName = data.$exportname;
						fileName = fileName?fileName:'noname';
						var excelFile = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:excel' xmlns='http://www.w3.org/TR/REC-html40'>";
						excelFile += "<head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head>";
						excelFile += "<body><table width='10%'  border='1'>";
						excelFile += '<thead><tr>';
						for(s in data_new){
							if(!data_new[s].hidden){
								excelFile += '<th>'+data_new[s].label+'</th>';
							}
						}
						excelFile += '</tr></thead>';
						excelFile += '<tbody>';
						for(var i=0; i< rt.length;i++){
							var r = rt[i];
							excelFile += '<tr>';
							for(s in data_new){
								if(!data_new[s].hidden){
									if(data_new[s].datatype == 'script'){
										var funcStr = "function test(record){"+data_new[s].script+"}";
										var funcTest = new Function('return '+funcStr);
										excelFile += '<td style="vnd.ms-excel.numberformat:@">'+funcTest()(r)+'</td>';
									}
									else if(data_new[s].datatype == 'template'){
										var template = data_new[s].template;
										template = excuteExpressionD(template,r);
										template = getHtmlTagVal(template, data_new[s].name);
										excelFile += '<td style="vnd.ms-excel.numberformat:@">'+template+'</td>'
									}
									else{
										excelFile += '<td style="vnd.ms-excel.numberformat:@">'+(r[data_new[s].name]?r[data_new[s].name]:'')+'</td>';
									}
								}
							}
							excelFile += '</tr>';
						}
						
						excelFile += "</tbody></table></body>";
						excelFile += foot;
						excelFile += "</html>";
						
						//console.log(excelFile)
						//var link = "data:application/vnd.ms-excel;base64," + window.btoa(unescape(encodeURIComponent(excelFile)));
						var blob = new Blob([excelFile], {type: "data:application/vnd.ms-excel;base64"});  	
						//解决中文乱码问题
						blob =  new Blob([String.fromCharCode(0xFEFF), blob], {type: blob.type});  
						var a = document.createElement("a");
						a.download = fileName + ".xls";
						//a.href = link;
						a.href = window.URL.createObjectURL(blob);
						document.body.appendChild(a);
						a.click();
						document.body.removeChild(a);
					},function(e){},{async:true,timeout:0})
				}else if(arg == 'toexcel'){
					var $table = $($("table[ctype='table']")[0]);
					$("table[ctype='table']").each(function(index,obj){
						if($(obj).parents(".tab-pane") && $(obj).parents(".tab-pane").hasClass("active")){
							$table = $(obj);
						}
					})
					
					var fields = [];
					var tc = $table.data("_tc");
					var fielddefs = {"tc": tc};
					var f = {};
					for(var i in tc){
						if(tc[i].name !='_select_' && tc[i].name != 'DT_Select'){
							fields.push(tc[i].name)
							var ff = tc[i].name;
							f[ff] = tc[i];
						}
					}
					var da = $("[ctype='form']").getData();
					if(!da.djbh){
						da.djbh = dd.djbh;
					}
					
					da.pageid = $("input[name='pageid']").val();
					da.xaxis = $("input[name='xaxis']").val();
					da.tableid = $table.attr("tableid");
					da.action_url = $table.attr("action");
					da.fields = fields.join(",");
					da.fielddefs = $.toJSON(f);
					da.$exportname = $table.attr("exportname") || $this.parents(".portlet-title").find(".caption").text().trim();
					/*var uu = cooperopcontextpath + "/w/application/excel/export1.xlsx";
					$.modal(uu, "导出", $.extend(true, {
						callback : function(rtn) {
							
						}
					}, da));*/
					$.exportExcel("application.excel.export1", da);
				}else if(arg == 'reload_usercache'){
					$.call("application.user.loadUserCache",{},function(rtn){
						if(rtn){
							$.message("重新加载成功！")
						}
					});
				}else if(arg == 'tempexcel'){
					var param = {"pageid": $("input[name='pageid']").val()};
					var uu = cooperopcontextpath + "/w/application/bill/toexcel.html";
					$.modal(uu, "模版导入", $.extend(true, {
						callback : function(rtn) {
							
						}
					}, param));
				}else if(arg == 'mxtempexcel'){
					var tableid;
					if($this.parent().hasClass("active")){
						//多页签
						tableid = $this.parents(".portlet-title").parent().find(".tab-pane.active").find("[ctype='table']").attr("tableid");
					}else{
						tableid = $this.parents(".portlet-title").parent().find("[ctype='table']").attr("tableid");
					}
					if(tableid){
						var param = {"pageid": $("input[name='pageid']").val(), "ismx": "Y", "tableid": tableid,
								"gzid": $("input[name='gzid']").val()};
						var uu = cooperopcontextpath + "/w/application/bill/toexcel.html";
						$.modal(uu, "模版导入", $.extend(true, {
							callback : function(rtn) {
								$this.parents(".portlet-title").parent().find("[ctype='table']").refresh();
							}
						}, param));
					}
				}else if(arg == 'exportJS'){
					var da = $(document).getData();
					//var da = {type: "bill", system_product_code: "erp"};
					var pageids = da.pageid.split(".");
					var dd = {type : pageids[1], pageid: da.pageid, system_product_code: pageids[0]};
					//var dd = {type : da.type, pageid: "erp.bill.CWD.C09X"};
					if(dd.type == "bill"){
						dd.data = {fapiaoh: da.fapiaoh, fapdm: da.fapdm}
						//dd.data = {fapiaoh: "1-1", fapdm: "1"};
					}else if(dd.type == "query"){
						var $table = $this.parents(".portlet-title").parent().find("[ctype='table']") || $($("table[ctype='table']")[0]);
						var sd = $table.getSelected();
						var rr = [];
						for(var i=0; i< sd.length; i++){
							var rd = sd[i].data;
							rr.push({fapiaoh: rd.fapiaoh, fapdm: rd.fapdm});
						}
						dd.datas = rr;
					}
					$.call("application.bill.exportJS", {data: $.toJSON(dd)}, function(rtn){
						if(rtn.fileid){
							$("#export_js_a").remove();
							var html = [];
							html.push('<a href="javascript:void(0);" style="display:none" id="export_js_a"></a>');
							$("body").append(html.join(''));
							var indexa = 0
							var inta = setInterval(function(){
								if(indexa < rtn.fileid.length){
									if(typeof crtechTogglePage == 'undefined'){
										$("#export_js_a").attr("href", cooperopcontextpath+"/rm/d/"+dd.system_product_code+"/"+rtn.fileid[indexa]);
										$("#export_js_a")[0].click();
									}else{
										crtechdownload("", cooperopcontextpath+"/rm/d/"+dd.system_product_code+"/"+rtn.fileid[indexa], dd.fapiaoh +".txt", 0);
									}
									indexa++;
								}else{
									clearInterval(inta);
								}
							}, 1000);
						}
					});
				}else if(arg == 'exportchartall'){
					layer.msg("正在导出...", {
					     icon:16,
					     shade:[0.5, '#fff'],
					     time:false  //不自动关闭
				    })
				    var html = $('<div id="tmp_word_img_box"><div>');
				    $('.highcharts-container').each(function(index,_this){
				    	new html2canvas(_this,{
				 			scale: 2
				 		}).then(canvas => {
				            // canvas为转换后的Canvas对象
				            let oImg = new Image();
				            oImg.src = canvas.toDataURL("image/png");// 导出图片
				            html.append(oImg)
				            html.append("<br/>")
				            html.append("<br/>")
				        })
				    })
				    setTimeout(function (){
						var date = new Date();
						var d = date.getFullYear() + '-' + (((date.getMonth()+1)<10)?'0'+(date.getMonth()+1):(date.getMonth()+1)) + '-' +((date.getDate())<10?'0'+(date.getDate()):(date.getDate()));
				        html.wordExport('医策报表' + document.title + '(导出时间'+ d +')',true);
				        layer.msg("导出准备完成！", {time: 1000});
				    },4000)
				}
			}
			$this.on("click", function() {
				exe();
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
	}
});
