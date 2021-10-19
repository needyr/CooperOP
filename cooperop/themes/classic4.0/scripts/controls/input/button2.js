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
				var dd = {
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
						"pageid":$("[name='pageid']").val(),
						"jigid":$("[name='jigid']").val(),
						"dep_code":$("[name='dep_code']").val(),
						"dep_name":$("[name='dep_name']").val()
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
					if(dd.djbs == 'chart'){
						//$.initChart();
						$("[ctype='chart']").each(function(){
							var $this = $(this);
							$.initChart($this.attr("flag"), dd.pageid);
						});
					}
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
									$(this).params(da);
									$(this).refresh();
								}
							}
						});
					}else{
						$("table[ctype='table']").each(function(){
							da.async = false;
							$(this).params(da);
							$(this).refresh();
						});
					}
					for(var i = 0 ;i<tabp.length ; i++){
						tabp[i].removeClass("active");
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
					$.modal(url, "选择" + $this.attr("label"), $.extend(true, data, {
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
						table.tr = $(tobj).getData();
						/*$(tobj).find("tbody>tr").each(function(){
							table.tr.push($(this).getData());
						})*/
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
						d[$(obj).attr("name")] = value.join(" ");
					});
					$.modal(cooperopcontextpath + "/w/application/jasper.initJasper.pdf","打印", dd);
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
									var scheme = arg.replace("da_save_yin_","");
									dd.selfprint = "Y";
									dd.save_befroe_print = "Y";
									dd.no = scheme;
									if($("[name='tablekey_']").val()){
										var tablekey_ = $("[name='tablekey_']").val();
										dd["tablekey_"] = tablekey_;
										dd[tablekey_] = $("[name='"+tablekey_+"']").val();
									}
									dd.djbh = rtn;
									$.modal(cooperopcontextpath + "/w/application/jasper.initJasper.pdf","打印", 
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
					for(var i=0;i<tc.length;i++){
						if(tc[i].name !='_select_'){
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
					/*var uu = cooperopcontextpath + "/w/application/excel/export.xlsx";
					$.modal(uu, "导出", $.extend(true, {
						callback : function(rtn) {
							
						}
					}, da));*/
					$.exportExcel("application.excel.export", da);
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
					for(var i=0;i<tc.length;i++){
						if(tc[i].name !='_select_'){
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
					da.$exportname = $table.attr("exportname");
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
