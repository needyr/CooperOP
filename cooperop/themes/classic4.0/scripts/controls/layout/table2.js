$.fn.extend({
	"ccinit_table" : function() {
		/*function fillExpr(jobj, data) {
			if (jobj.parents("foreach, if, when, otherwise, .autocomplete-content, [ctype='select'], [ctype='checkbox'], [ctype='radio'], [ctype='table'], [ctype='list']").length > 0) {
			} else {
				for (var i = 0; i < jobj[0].attributes.length; i ++) {
					if (jobj[0].attributes[i].nodeValue.indexOf("$[") >= 0 && jobj[0].attributes[i].nodeName != "select-option" && jobj[0].attributes[i].nodeName != "items") {
						jobj.attr(jobj[0].attributes[i].nodeName, $.dlreplace(jobj[0].attributes[i].nodeValue, data));
					}
				}
				for (var i = 0; i < jobj[0].childNodes.length; i ++) {
					if (jobj[0].childNodes[i].nodeType == 3) {
						var ah = jobj[0].childNodes[i].nodeValue;
						if (ah.indexOf("$[") >= 0) {
							jobj[0].childNodes[i].nodeValue = $.dlreplace(ah, data);
						}
					} else if (jobj[0].childNodes[i].nodeType == 1){
						fillExpr($(jobj[0].childNodes[i]), data);	
					} else {
						console.info(jobj[0].childNodes[i].nodeType);
						console.info(jobj[0].childNodes[i]);
					}
				}
			}
			return jobj[0].outerHTML;
		}*/
		
		var hps = [], hids = [];
		var t = this;
		while (t.is(":hidden")) {
			hps.push(t);
			t = t.parent();
		}
		for (var i = hps.length - 1; i >= 0; i--) {
			if (hps[i].is(":hidden")) {
				hps[i].addClass("hard_show");
				hids.push(hps[i]);
				if (this.is(":visible")) {
					break;
				}
			}
		}
		var SELECTED_DATA = [
				"multi", "single"
		];
		var $this = this;
		var tid = "_t" + ($(document).find("[ctype='table'][cinited]").length + 1) + "_";
		$this.data("params", $this.attr("params") ? $.parseJSON($this.attr("params")) : {});
		$this.data("loadable", $this.attr("autoload") == "true");
		$this.data("select", $.inArray($this.attr("select"), SELECTED_DATA) != -1 ? $this.attr("select") : "none");
		var _tc = {};
		var columns = [];
		var sorts = [];
		$this.data("_selected_init", []);
		$this.data("_selected", []);
		$this.data("_selected_data", {});
		if ($this.data("select") != "none") {
			_tc["DT_Select"] = {
				"index" : 0,
				"order" : 0,
				"name" : "DT_Select",
				"label" : $this.data("select") == SELECTED_DATA[0] ? '<input type="checkbox" id="' + tid
						+ 'selectall" class="datadataTables_select" ccinput="table_select"/>' : '',
				"sort" : false,
				"hidden": false,
				"width" : "10",
				"align" : "center",
				"datatype" : "select"
			};
			columns.push({
				"name" : "DT_Select",
				"data" : "DT_Select",
				"title" : $this.data("select") == SELECTED_DATA[0] ? '<input type="checkbox" id="' + tid
						+ 'selectall" class="datadataTables_select" ccinput="table_select"/>' : '',
				"orderable" : false,
				"searchable" : false,
				"visible" : true,
				"width" : "10",
				"className" : "center",
				"render" : function(data, type, row, meta) {
					var _s_ = false;
					if ($.inArray(row.DT_RowId, $this.data("_selected")) !== -1) {
						_s_ = true;
					}
					return $this.data("select") == SELECTED_DATA[0] ? '<input type="checkbox" class="datadataTables_select" ccinput="table_select" '+(_s_?'checked="checked"':'')+'/>'
							: '<input type="radio" class="datadataTables_select" ccinput="table_select" '+(_s_?'checked="checked"':'')+'/>';
				}
			});
		}
		$this.find("[scope='col']").each(function(index) {
			var _th = $(this);
			if (_th.attr("datatype") == "script") {
				var s = [];
				s.push('$this[0].field_ts_' + _th.attr("name") + ' = function(record, dictionary) {');
				s.push(_th.find("textarea").val());
				s.push('}');
				eval(s.join(""));
			}
			var _dictionary = _th.attr("dictionary");
			if (_th.attr("hidden") == "true") _th.attr("hidden", "hidden");
			_tc[_th.attr("name")] = {
				"index" : $this.data("select") != "none" ? index + 1 : index,
				"order" : $this.data("select") != "none" ? index + 1 : index,
				"name" : _th.attr("name"),
				"label" : _th.attr("label"),
				"sort" : _th.attr("sort"),
				"defaultsort" : _th.attr("defaultsort"),
				"hidden" : _th.attr("hidden") ? true : false,
				"width" : $this.attr("fitwidth") == "false" ? (_th.attr("width") || 100) : undefined,
				"align" : _th.attr("align"),
				"datatype" : _th.attr("datatype"),
				"format" : _th.attr("format"),
				"dictionary" : _dictionary,
				"script" : _th.attr("datatype") == "script" ? _th.find("textarea").val() : undefined,
				"template_script" : _th.attr("datatype") == "script" ? 'field_ts_' + _th.attr("name") : undefined,
				"template" : _th.attr("datatype") == "template" ? _th.html() : undefined
			};
			columns.push({
				"name" : _th.attr("name"),
				"data" : _th.attr("name"),
				"title" : _th.attr("label"),
				"orderable" : _th.attr("sort") == "true",
				"searchable" : false,
				"visible" : _th.attr("hidden") ? false : true,
				"width" : $this.attr("fitwidth") == "false" ? (_th.attr("width") || 100) : undefined,
				"className" : _th.attr("align"),
				"render" : function(data, type, row, meta) {
					var col = meta.settings.aoColumns[meta.col];
					var def = _tc[col.name];
					var value = (row["_CT_Data"][col.data] || "");
					if (def["template_script"]) {
						var record = $.extend(true, {}, row["_CT_Data"]);
						record = $.extend(true, {}, record);
						value = $this[0][def["template_script"]](record, $.parseJSON(def.dictionary));
					} else if (def["template"]) {
						var record = $.extend(true, {}, row["_CT_Data"]);
						record = $.extend(true, {}, record);
						//value = fillExpr($(def["template"]), record);
						value = $.dlexpr(def["template"], record);
					}
					if (def["dictionary"]) {
						value = $.parseJSON(def["dictionary"])[value] || value;
					}
					if (def["datatype"] == "date" && value) {
						value = $.formatdate(new Date(+value), def["format"] || "yyyy-MM-dd");
					} else if (def["datatype"] == "time" && value) {
						value = $.formatdate(new Date(+value), def["format"] || "HH:mm:ss");
					} else if (def["datatype"] == "datetime" && value) {
						value = $.formatdate(new Date(+value), def["format"] || "yyyy-MM-dd HH:mm:ss");
					} else if (def["datatype"] == "number") {
						value = $.formatnumber(+value, def["format"] || "#,###.#");
					} else if (def["datatype"] == "decimal") {
						value = $.formatnumber(+value, def["format"] || "#,##0.00");
					} else if (def["datatype"] == "currency") {
						value = $.formatnumber(+value / 100, def["format"] || "#,##0.00");
					}
					value = value == 0 ? value: (value || "");
					return value;
				}
			});
			if (_th.attr("defaultsort")) {
				sorts.push([
						$this.data("select") != "none" ? index + 1 : index, _th.attr("defaultsort")
				]);
			}
		});
		$this.data("_tc", _tc);
		$this.data("columns", columns);
		$this.find("[scope='col']").parent().remove();

		$this.data("isdemo", $this.find("tbody").find("tr").length > 0 || !$this.attr("action") || $this.attr("isdesign") == "true");
		var demodata = [];
		if ($this.data("isdemo")) {
			if ($.isNumeric($this.attr("defaultrow"))) {
				for (var dr = 0; dr < +$this.attr("defaultrow"); dr ++) {
					var _rr = {
						DT_RowId : tid + "row_" + demodata.length,
						_CT_Data : {}
					};
					for (var c in _tc) {
						_rr["_CT_Data"][c] = null;
					}
					demodata.push(_rr);
				}
			}
			$this.find("tbody").find("tr").each(function() {
				var _tr_ = $(this);
				var _rr = {
					DT_RowId : tid + "row_" + demodata.length
				};
				var _cd = {};
				_tr_.find("td, th").each(function(c1) {
					var c = $this.data("select") != "none" ? c1 + 1 : c1;
					var _td_ = $(this);
					_cd[columns[c].name] = _td_.text();
				});
				if (this.dataset) {
					_cd = $.extend(true, _cd, this.dataset);
				} else {
					var attrs = this.attributes;
					for ( var i in attrs) {
						if (attrs[i].name.substring(0, 5) == 'data-') {
							_cd[attrs[i].name.substring(5)] = attrs[i].value;
						}
					}
				}
				_rr["_CT_Data"] = _cd;
				for (var c in _tc) {
					_rr[c] = (_rr["_CT_Data"][c] || "");
				}
				demodata.push(_rr);
			});
			$this.find("tbody").html("");
		}
		
		$this.DataTable({
			"dom" : ($this.attr("filter") == "true" ?
					"<'dataTables_header'<'dataTables_filter_wrapper'f><'dataTables_total_wrapper'><'dataTables_length_wrapper'l>><'table-scrollable'rt><'dataTables_footer'<'dataTables_info_wrapper'i><'dataTables_page_wrapper'p>>" :
					"<'table-scrollable'rt><'dataTables_footer'<'dataTables_info_wrapper'li><'dataTables_page_wrapper'p>>"
					), // default layout with horizobtal scrollable datatable
			"language" : {
				"decimal" : "",
				"emptyTable" : "没有相关数据",
				"info" : "当前 _START_ ~ _END_ / 共 _TOTAL_ 条记录",
				"infoEmpty" : "当前 0 ~ 0 / 共 0 条记录",
				"infoFiltered" : "(从 _MAX_ 记录中过滤)",
				"infoPostFix" : "",
				"thousands" : ",",
				"lengthMenu" : "每页显示 _MENU_ 条记录",
				"loadingRecords" : "正在加载数据...",
				"processing" : "正在加载数据...",
				"search" : "",
				"searchPlaceholder": $this.attr("filterholder") || "输入关键字搜索...",
				"zeroRecords" : "没有找到相关数据",
				/*
				 * "paginate": { "first": "First", "last": "Last", "next": "Next", "previous": "Previous" },
				 */
				"aria" : {
					"sortAscending" : ": 点击升序排列（按住shift可以多列排序）",
					"sortDescending" : ": 点击降序排列（按住shift可以多列排序）"
				}
			},
			"stateSave" : true,
			"stateSaveCallback": function (settings, data) {
				var save_setting = $this.data("save_setting");
				if (save_setting) {
					$this.data("save_setting", undefined);
					_tc = $this.data("_tc");
					var t = {};
					for (var c in _tc) {
						t[c] = {
							hidden: _tc[c].hidden,
							index: _tc[c].index,
							order: _tc[c].order
						}
					}
					$.call("application.customTable.save", {
						pageid: pageid,
						tableid: $this.attr("id") || $this.attr("name") || tid,
						setting: t
					}, function(rtn) {
					});
				}
			},
			"stateLoadCallback": function(settings) {
				$.call("application.customTable.get", {
					pageid: pageid,
					tableid: $this.attr("id") || $this.attr("name") || tid
				}, function(setting) {
					if (setting) {
						var colsn = [];
						for (var c in _tc) {
							if (setting[c]) {
								_tc[c].hidden = setting[c].hidden;
								_tc[c].index = setting[c].index;
								_tc[c].order = setting[c].order;
								for (var i = 0 ; i < settings.aoColumns.length; i ++) {
									if (settings.aoColumns[i].data == c) {
										settings.aoColumns[i].visible = settings.aoColumns[i].bVisible = !_tc[c].hidden;
									}
								}
							}
						}
						$this.data("_tc", _tc);
					}
				}, undefined, {async: false});
				return settings;
			},
			"colReorder" : true,
			"deferRender" : true,
			"orderClasses": true,
			"processing" : ($this.attr("processing") || 'true') == 'true',
			"lengthMenu" : [
					[
							10, 20, 50, 100, 200, -1
					], [
							10, 20, 50, 100, 200, '全部'
					]
			],
			"autoWidth" : $this.attr("fitwidth") == "false",
			"scrollY" : $this.attr("theight") || 400,
			"scrollX" : true,
			"scrollCollapse": false,
			"paging" : +$this.attr("limit") > 0,
			"lengthChange" : +$this.attr("limit") > 0,
			"pageLength" : +$this.attr("limit"),
			"searching" : $this.attr("filter") == "true",
			"searchDelay" : 500,
			"search" : {
				"search" : $this.attr("filterstr") || ""
			},
			"ordering" : $this.attr("sort") == "true",
			"order" : sorts,
			"deferLoading" : true,
			"serverSide" : !$this.data("isdemo"),
			"columns" : columns,
			"rowCallback" : function(row, data, displayNum, displayIndex, dataIndex) {
				$(row).attr("ctid", tid);
				$(row).ccinit();
				if ($.inArray(data.DT_RowId, $this.data("_selected")) !== -1) {
					$(row).addClass('selected');
				}
			},
			"initComplete": function(settings, json) {
				var _tc = $this.data("_tc");
				var _cols = [];
				for (var c in _tc) {
					_cols[_tc[c].order] = _tc[c].index;
				}
				$this.DataTable().colReorder.disable();
				$this.data("save_setting", undefined);
				$this.DataTable().colReorder.order(_cols, true);
				if ($this.attr("fitwidth") == "true") {
					$this.css("width", "100%");
				}
				$this.DataTable().columns.adjust();
			},
			"fnDrawCallback" : function() { // 用于翻页条 加上跳转功能
				/*if ($this.is("[onrefreshed]")) {
					window[$this.attr("onrefreshed")]($this.data("resultset") || {});
				}*/
			},
			"data" : demodata,
			"fnServerData" : function(sSource, aoData, fnCallback) {
				var action = $this.attr("action");
				var data = {};
				if($this.attr("isdesign_") == 'Y'){
					data = $(document).getData() || {};
				}
				if (!$this.attr("action") && $this.attr("schemeid")) {
					action = 'application.scheme.executeQueryScheme';
					var scheme = $this.attr("schemeid").split(",");
					data["fangalx"] = scheme[0];
					data["fangabh"] = scheme[1];
				}
				var aoD = {};
				for ( var i in aoData) {
					var config = aoData[i];
					aoD[config["name"]] = config["value"];
				}

				data["t"] = Math.random();
				data["start"] = aoD["start"] + 1 + '';
				data["limit"] = aoD["length"] + '';
				data["filter"] = aoD["search"].value || "";

				var sort = [];
				for (var i = 0, iLen = aoD["order"].length; i < iLen; i++) {
					var col = _tc[aoD['columns'][aoD["order"][i]["column"]]["name"]];
					if (col["datatype"] != "nodata") {
						sort.push(col["name"] + " " + aoD["order"][i]["dir"]);
					}
				}
				data["sort"] = sort.join(",");

				var totals = [];
				$("[expressions][expressions!='']").each(function() {
					var $input = $(this);
					if ($this.attr("tableid") == $input.attr("tableid")) {
						totals.push([
								$input.attr("expressions"), $input.attr("name")
						].join(","));
					}
				});
				data["totals"] = totals.join(",");

				data = $.extend(true, data, $this.data("params"));
				data.gzid = $("[name='gzid']").val();
				data.pageid = $("[name='pageid']").val();
				data.tableid = $this.attr("tableid");
				if (data.async != false) {
					data.async = true;
				}
				$.call(action, data, function(rs) {
					if (!rs)
						rs = {};
					var j = {};
					j["iTotalRecords"] = rs.count || 0;
					j["iTotalDisplayRecords"] = rs.count || 0;
					var _rdata = [];
					if (rs.resultset) {
						for (var i = 0, iLen = rs.resultset.length; i < iLen; i++) {
							var _rr = {
								DT_RowId : tid + "row_" + (data["start"] + i),
								_CT_Data : $.extend(true, {}, rs.resultset[i])
							};
							for (var c in _tc) {
								_rr[c] = (_rr["_CT_Data"][c] || "");
							}
							_rdata.push(_rr);
							var d = {
									id: _rr["DT_RowId"],
									data: _rr["_CT_Data"]
								};
							if(rs.resultset[i]._r_checked == "checked"){
								var index = $.inArray(_rr["DT_RowId"], $this.data("_selected"));
								var index_init = $.inArray(_rr["DT_RowId"], $this.data("_selected_init"));
						        if ( index === -1 && index_init === -1) {
						        	$this.data("_selected_init").push(_rr["DT_RowId"]);
									$this.data("_selected").push(_rr["DT_RowId"]);
									$this.data("_selected_data")[_rr["DT_RowId"]] = d;
						        }
							}
						}
					}
					$this.data("resultset", rs);
					j["aaData"] = _rdata;
					fnCallback(j);
					$("[expressions][expressions!='']").each(function() {
						var $input = $(this);
						if ($this.attr("tableid") == $input.attr("tableid")) {
							$input.setData(rs.totals[$input.attr("name")]);
						}
					});
				}, undefined, {
					nomask : true,
					async : data.async
				});
			}
		});
		if ($this.data("select") != "none") {
			$this.parent().parent().find(".dataTables_scrollHead").find("#" + tid + 'selectall').on("click", function() {
				var _sa = $(this);
				if (_sa.attr("checked")) {
					for (var i = 0, ilen = $this.DataTable().rows()[0].length; i < ilen; i ++) {
						var index = $.inArray($($this.DataTable().row(i).nodes()[0]).attr("id"), $this.data("_selected"));
				        if ( index === -1 ) {
							var d = {
								id: $($this.DataTable().row(i).nodes()).attr("id"),
								data: $this.DataTable().row(i).data()._CT_Data,
								element: $this.DataTable().row(i).nodes()[0]
							};
							$($this.DataTable().row(i).nodes()).addClass("selected");
							$($this.DataTable().row(i).nodes()).find(".datadataTables_select").attr("checked", true);
							$this.data("_selected").push(d.id);
							$this.data("_selected_data")[d.id] = d;
				        }
					}
				} else {
					for (var i = 0, ilen = $this.DataTable().rows()[0].length; i < ilen; i ++) {
						$($this.DataTable().row(i).nodes()).removeClass("selected");
						$($this.DataTable().row(i).nodes()).find(".datadataTables_select").attr("checked", false);
					}
					$this.data("_selected", []);
					$this.data("_selected_data", {});
				}
				if ($this.is("[row_selected]")) {
					window[$this.attr("row_selected")]();
				}
			});
			$this.find("tbody").on("click", "tr[ctid]", function() {
				var _tr = $(this);
				var d = {
					id: _tr.attr("id"),
					data : $.extend(true, {}, $this.DataTable().row(this).data()._CT_Data, $($this.DataTable().row(this).node()).getData()),
					element: $this.DataTable().rows(this).nodes()[0]
				};
		        var index = $.inArray(_tr.attr("id"), $this.data("_selected"));
		        if ( index === -1 ) {
		        	if ($this.data("select") != SELECTED_DATA[0]) {
		        		for (var i = 0, ilen = $this.data("_selected").length; i < ilen; i ++) {
		        			$($this.data("_selected_data")[$this.data("_selected")[i]].element).removeClass("selected");
		        			$($this.data("_selected_data")[$this.data("_selected")[i]].element).find(".datadataTables_select").attr("checked", false);
		        		}
		        		$this.data("_selected", []);
		        		$this.data("_selected_data", {});
		        	}
		        	$this.data("_selected").push(d.id);
					$this.data("_selected_data")[d.id] = d;
		        	_tr.addClass('selected');
		        	_tr.find(".datadataTables_select").attr("checked", true);
		        } else {
		        	$this.data("_selected").splice( index, 1 );
	        		delete $this.data("_selected_data")[d.id];
		        	_tr.removeClass('selected');
		        	_tr.find(".datadataTables_select").attr("checked", false);
		        }
		        if ($this.is("[row_selected]")) {
					window[$this.attr("row_selected")](d);
				}
			});
			$this.find("tbody").on("dblclick", "tr[ctid]", function() {
				var _tr = $(this);
				var d = {
					id: _tr.attr("id"),
					data : $.extend(true, {}, $this.DataTable().row(this).data()._CT_Data, $($this.DataTable().row(this).node()).getData()),
					element: $this.DataTable().rows(this).nodes()[0]
				};
				if($this.is("[dblclick]")){
					window[$this.attr("dblclick")](d);
				}else{
					if($this.attr("tableid")){
						if($this.attr("djselect")){
							var u = $this.attr("djselect").replace(/\./g, "/");
							var url = cooperopcontextpath + "/w/" + u+".html";
							var uu = url+"?fromtable=Y&"+$this.attr("tablekey")+"="+d.data[$this.attr("tablekey")]+"&djbh="+d.data[$this.attr("tablekey")];
							if($this.attr("tablekey")){
								$.modal(uu, " ", $.extend(true, {
									callback : function(rtn) {
										if (rtn) {
											$this.refresh_table($this.DataTable().page());
										}
									}
								}, {}));
								//window.open(url+"?"+$this.attr("tablekey")+"="+d.data.$this.attr("tablekey"));
							}else{
								var pageinfo = $("[name='pageid']").val().split(".");
								if(pageinfo[1] == "query"){
									url = url + "?fromtable=Y";
								}else{
									url = url + "?ptableid="+$this.attr("tableid")+
									"&p_pageid="+$("[name='pageid']").val();
								}
								if(d.data.djbh){
									url = url + "&djbh="+d.data.djbh;
								}else if(d.data.dj_sn){
									url = url + "&p_dj_sn="+d.data.dj_sn + "&p_dj_sort="+d.data.dj_sort 
									+ "&p_gzid="+$("[name='gzid']").val();
								}
								$.modal(url, "修改", $.extend(true, {
									callback : function(rtn) {
										if (rtn) {
											$this.refresh_table($this.DataTable().page());
										}
									}
								}, {}));
								//window.open(url+"?djbh="+d.data.djbh);
							}
						}else{
							$this.editrecord(d);
						}
					}
				}
				
			});
		}
		if ($this.parent().is(".dataTables_scrollBody")) {
			$this.parent().parent().find(".dataTables_scrollHead").find("table").removeAttr("ctype");
		}
		$this.attr("ctid", tid);
		$this.attr("cinited", "cinited");
		if ($this.data("loadable")) {
			$this.DataTable().page(0).draw('page');
		}
		setTimeout(function(){
			for (var i = 0; i < hids.length; i ++) {
				hids[i].removeClass("hard_show");
			}
		} ,300);
		if ($this.parents(".portlet.box:eq(0)").find(".form-collapse-btn").length > 0) {
			$this.parents(".portlet.box:eq(0)").find(".form-collapse-btn").click(function() {
				if ($this.parents(".portlet.box:eq(0)").is(".portlet-collapsed")) {
					$this.parents(".portlet-body:eq(0)").animate({
						height : "show"
					}, 500, function() {
						$this.parents(".portlet.box:eq(0)").removeClass("portlet-collapsed");
						$this.parents(".portlet.box:eq(0)").find(".form-collapse-btn").html('[ 收起<i class="fa fa-caret-up"></i> ]');
					});
				} else {
					$this.parents(".portlet-body:eq(0)").animate({
						height : "hide"
					}, 500, function() {
						$this.parents(".portlet.box:eq(0)").addClass("portlet-collapsed");
						$this.parents(".portlet.box:eq(0)").find(".form-collapse-btn").html('[ 展开<i class="fa fa-caret-down"></i> ]');
					});
				}
			});
			if ($this.is("[collapsed='true']")) {
				$this.parents(".portlet.box:eq(0)").addClass("portlet-collapsed");
				$this.parents(".portlet.box:eq(0)").find(".form-collapse-btn").html('[ 展开<i class="fa fa-caret-down"></i> ]');
			} else {
				$this.parents(".portlet.box:eq(0)").find(".form-collapse-btn").html('[ 收起<i class="fa fa-caret-up"></i> ]');
			}
		}
		$(window).on("resize", function() {
			if ($this.attr("fitwidth") == "true") {
				$this.css("width", "100%");
			}
			$this.DataTable().columns.adjust();
		});
	},
	"params_table" : function(params) {
		var $this = this;
		var tps = $this.attr("params") ? $.parseJSON($this.attr("params")) : {};
		$this.data("params", $.extend(true, tps, params));
	},
	"refresh_table" : function(index) {// 页面数字，first,last,next,previous - Jump to previous page
		var $this = this;
		if (index == undefined) {
			$this.data("_selected", []);
			$this.data("_selected_data", {});
		}else if(index == "current"){
			index = $this.DataTable().page();
		}
		$this.data("loadable", true);
		return $this.DataTable().page(index || "first").draw(false);
	},
	"getSelected_table" : function() {
		var $this = this;
		var rtn = [];
		for (var i = 0, ilen = $this.data("_selected").length; i < ilen; i++) {
			rtn.push($.extend(true, {}, $this.data("_selected_data")[$this.data("_selected")[i]]));
		}
		return rtn;
	},
	"getData_table" : function(rid) {
		var $this = this;
		if ($.type(rid) === "string") {
			var rtn = {};
			for (var i = 0, ilen = $this.DataTable().rows()[0].length; i < ilen; i++) {
				if ($this.DataTable().row(i).data().DT_RowId == rid) {
					return $.extend(true, {}, $this.DataTable().row(i).data()._CT_Data, $($this.DataTable().row(i).node()).getData());
				}
			}
		} else {
			var k = $this.attr("name") || $this.attr("id") || "table_" + $.generateId();
			var rtn = {};
			rtn[k] = [];
			for (var i = 0, ilen = $this.DataTable().rows()[0].length; i < ilen; i++) {
				rtn[k].push($.extend(true, {}, $this.DataTable().row(i).data()._CT_Data, $($this.DataTable().row(i).node()).getData()));
			}
			return rtn;
		}
	},
	"setting_table" : function() {
		var $this = this;
		var _tc = $this.data("_tc");
		var _cols = [];
		for (var c in _tc) {
			_cols.push(_tc[c]);
		}
		_cols.sort(function(a, b) {
			return a.order - b.order;
		});
		var html = [];
		html.push('<div class="dataTables_setting">');
		html.push('	<div class="dataTables_columns">');
		html.push('		<h3>显示/隐藏列<em class="dataTables_columns_tips">(按住鼠标左键可拖动改变列显示顺序)</em></h3>');
		html.push('		<ul>');
		for (var i = 0; i < _cols.length; i ++) {
			var _t = _cols[i];
			if (_t.datatype == "select") continue;
			html.push('		<li draggable="true" cname="' + _t.name + '">');
			html.push('			<input type="checkbox" id="column_' + _t.name + '" data-name="' + _t.name + '" name="columns" value="' + _t.name + '"' + (_t.hidden ? '' : ' checked="checked"')  + '>');
			html.push('			<label for="column_' + _t.name + '" title="' + _t.label + '">' + _t.label + '</label>');
			html.push('		</li>');
		}
		html.push('		</ul>');
		html.push('		<div class="columns_all">');
		html.push('			<input type="checkbox" id="columns_all">');
		html.push('			<label for="columns_all">全部显示/全部隐藏</label>');
		html.push('		</div>');
		html.push('	</div>');
		html.push('</div>');
		layer.alert(html.join(""), {
			area: ["600px", "480px"],
			title: "表格设置",
			scrollbar: true,
			btn: ["确定并保存", "确定", "取消"], 
			success: function(layero, index) {
				var selectall = true;
				$(layero).find("input[name='columns']").each(function() {
					if (!this.checked) {
						selectall = false;
						return false;
					}
				});
				if (selectall) $(layero).find("#columns_all")[0].checked = true;
				$(layero).find("#columns_all").on("click", function(e) {
					if (this.checked) {
						$(layero).find("input[name='columns']").each(function() {
							this.checked = true;
						});
					} else {
						$(layero).find("input[name='columns']").each(function() {
							this.checked = false;
						});
					}
				});
				$(layero).find(".dataTables_columns").on("drop", "ul > li", function(e) {
					e.preventDefault();
					var cname=e.originalEvent.dataTransfer.getData("cname");
					var jobj = $(layero).find(".dataTables_columns > ul > li[cname='" + cname + "']");
					if (jobj.index() > $(this).index()) {
						jobj.insertBefore($(this));
					} else {
						jobj.insertAfter($(this));
					}
				});
//				$(layero).find(".dataTables_columns").on("drop", "ul", function(e) {
//					e.preventDefault();
//					var cname=e.originalEvent.dataTransfer.getData("cname");
//					var jobj = $(layero).find(".dataTables_columns > ul > li[cname='" + cname + "']");
//					jobj.appendTo($(this));
//				});
				$(layero).find(".dataTables_columns").on("dragover", "ul > li", function(e) {
					e.preventDefault();
					var cname=e.originalEvent.dataTransfer.getData("cname");
					var jobj = $(layero).find(".dataTables_columns > ul > li[cname='" + cname + "']");
//					jobj.after($(this));
				});
				$(layero).find(".dataTables_columns").on("dragstart", "ul > li", function(e) {
					var c = $(this);
					e.originalEvent.dataTransfer.setData("cname", c.attr("cname"));
				});
			},
			yes: function(index, layero) {
				var o = [];
				for (var i = 0; i < _cols.length; i ++) {
					o[i] = _cols[i].index;
				}
				$(layero).find("input[name='columns']").each(function(index, ele) {
					var c = $(ele).data("name");
					var i = index + ($this.data("select") != "none" ? 1 : 0);
					o[i] = _tc[c].index;
					_tc[c].hidden = !ele.checked;
					_tc[c].order = i;
				});
				$this.data("save_setting", true);
				$this.data("_tc", _tc);
				$this.DataTable().colReorder.order(o, true);
				for (var c in _tc) {
					$this.DataTable().column(_tc[c].order).visible(!_tc[c].hidden);
				}
				layer.close(index);
			},
			btn2: function(index, layero) {
				var o = [];
				for (var i = 0; i < _cols.length; i ++) {
					o[i] = _cols[i].index;
				}
				$(layero).find("input[name='columns']").each(function(index, ele) {
					var c = $(ele).data("name");
					var i = index + ($this.data("select") != "none" ? 1 : 0);
					o[i] = _tc[c].index;
					_tc[c].hidden = !ele.checked;
					_tc[c].order = i;
				});
				$this.data("save_setting", undefined);
				$this.data("_tc", _tc);
				$this.DataTable().colReorder.order(o, true);
				for (var c in _tc) {
					$this.DataTable().column(_tc[c].order).visible(!_tc[c].hidden);
				}
				layer.close(index);
			},
			btn3: function(index, layero) {
				layer.close(index);
			},
			cancel: function(index) {
			}
		});
	},
	"export_table" : function(data) {
		var $this = this;
		var data = data || {};
		var action = data["action"] || $this.attr("action");
		delete data["action"];
		var aoD = $this.DataTable().settings();
		data["start"] = 1;
		data["limit"] = -1;
		data["filter"] = aoD["search"].value || "";
		var _tc = $this.data("_tc");
		var sort = [];
		for (var i = 0, iLen = aoD["order"].length; i < iLen; i++) {
			var col = _tc[aoD['columns'][aoD["order"][i]["column"]]["name"]];
			if (col["datatype"] != "nodata") {
				sort.push(col["name"] + " " + aoD["order"][i]["dir"]);
			}
		}
		data["sort"] = sort.join(",");
		if (action == $this.attr("action")) {
			var _tc = $this.data("_tc");
			var _cols = [];
			for (var c in _tc) {
				if (_tc[c]["datatype"] == "nodata") continue;
				if (_tc[c]["name"] == "oper") continue;
				_cols.push(_tc[c]);
			}
			_cols.sort(function(a, b) {
				return a.order - b.order;
			});
			var html = [];
			html.push('<div class="dataTables_setting">');
			html.push('	<div class="dataTables_columns">');
			html.push('		<h3>选择导出列</h3>');
			html.push('		<ul>');
			for (var i = 0; i < _cols.length; i ++) {
				var _t = _cols[i];
				if (_t.datatype == "select") continue;
				html.push('		<li draggable="true" cname="' + _t.name + '">');
				html.push('			<input type="checkbox" id="column_' + _t.name + '" data-name="' + _t.name + '" name="columns" value="' + _t.name + '"' + (_t.hidden ? '' : ' checked="checked"')  + '>');
				html.push('			<label for="column_' + _t.name + '" title="' + _t.label + '">' + _t.label + '</label>');
				html.push('		</li>');
			}
			html.push('		</ul>');
			html.push('		<div class="columns_all">');
			html.push('			<input type="checkbox" id="columns_all">');
			html.push('			<label for="columns_all">全部选择/全部取消</label>');
			html.push('		</div>');
			html.push('	</div>');
			html.push('</div>');
			layer.alert(html.join(""), {
				area: ["600px", "480px"],
				title: "数据导出",
				scrollbar: true,
				btn: ["导出", "取消"], 
				success: function(layero, index) {
					var selectall = true;
					$(layero).find("input[name='columns']").each(function() {
						if (!this.checked) {
							selectall = false;
							return false;
						}
					});
					if (selectall) $(layero).find("#columns_all")[0].checked = true;
					$(layero).find("#columns_all").on("click", function(e) {
						if (this.checked) {
							$(layero).find("input[name='columns']").each(function() {
								this.checked = true;
							});
						} else {
							$(layero).find("input[name='columns']").each(function() {
								this.checked = false;
							});
						}
					});
				},
				yes: function(index, layero) {
					data["t"] = Math.random();
					data["$_type"] = "pdf";
					data["$exportname"] = data["$exportname"] || $this.parentsUntil(".portlet").last().siblings(".portlet-title").find(".caption").text().trim();
					data["fields"] = [];
					data["fielddefs"] = {};
					$(layero).find("input[name='columns']").each(function(index, ele) {
						var c = $(ele).data("name");
						var i = index + ($this.data("select") != "none" ? 1 : 0);
						if (ele.checked) {
							data["fields"].push(_tc[c]["name"]);
							data["fielddefs"][_tc[c]["name"]] = _tc[c];
						}
					});
					data['fields'] = data["fields"].join(",");
					data['fielddefs'] = $.toJSON(data["fielddefs"]);
					var totals = [];
					$("[expressions][expressions!='']").each(function() {
						var $input = $(this);
						if ($this.attr("tableid") == $input.attr("tableid")) {
							totals.push([
									$input.attr("expressions"), $input.attr("name")
							].join(","));
						}
					});
					data["totals"] = totals.join(",");
					data = $.extend(true, data, $this.data("params"));
					$.exportExcel(action, data);
					layer.close(index);
				},
				cancel: function(index) {
				}
			});
		} else {
			data = $.extend(true, data, $this.data("params"));
			$.exportExcel(action, data);
		}
	},
	"print_table" : function(data) {
		var $this = this;
		var data = data || {};
		var action = data["action"] || $this.attr("action");
		delete data["action"];
		var aoD = $this.DataTable().settings();
		data["start"] = 1;
		data["limit"] = -1;
		data["filter"] = aoD["search"].value || "";
		var _tc = $this.data("_tc");
		var sort = [];
		for (var i = 0, iLen = aoD["order"].length; i < iLen; i++) {
			var col = _tc[aoD['columns'][aoD["order"][i]["column"]]["name"]];
			if (col["datatype"] != "nodata") {
				sort.push(col["name"] + " " + aoD["order"][i]["dir"]);
			}
		}
		data["sort"] = sort.join(",");
		if (action == $this.attr("action")) {
			var _tc = $this.data("_tc");
			var _cols = [];
			for (var c in _tc) {
				if (_tc[c]["datatype"] == "nodata") continue;
				if (_tc[c]["name"] == "oper") continue;
				_cols.push(_tc[c]);
			}
			_cols.sort(function(a, b) {
				return a.order - b.order;
			});
			var html = [];
			html.push('<div class="dataTables_setting">');
			html.push('	<div class="dataTables_columns">');
			html.push('		<h3>选择打印列</h3>');
			html.push('		<ul>');
			for (var i = 0; i < _cols.length; i ++) {
				var _t = _cols[i];
				if (_t.datatype == "select") continue;
				html.push('		<li draggable="true" cname="' + _t.name + '">');
				html.push('			<input type="checkbox" id="column_' + _t.name + '" data-name="' + _t.name + '" name="columns" value="' + _t.name + '"' + (_t.hidden ? '' : ' checked="checked"')  + '>');
				html.push('			<label for="column_' + _t.name + '" title="' + _t.label + '">' + _t.label + '</label>');
				html.push('		</li>');
			}
			html.push('		</ul>');
			html.push('		<div class="columns_all">');
			html.push('			<input type="checkbox" id="columns_all">');
			html.push('			<label for="columns_all">全部选择/全部取消</label>');
			html.push('		</div>');
			html.push('	</div>');
			html.push('</div>');
			layer.alert(html.join(""), {
				area: ["600px", "480px"],
				title: "数据打印",
				scrollbar: true,
				btn: ["打印", "取消"], 
				success: function(layero, index) {
					var selectall = true;
					$(layero).find("input[name='columns']").each(function() {
						if (!this.checked) {
							selectall = false;
							return false;
						}
					});
					if (selectall) $(layero).find("#columns_all")[0].checked = true;
					$(layero).find("#columns_all").on("click", function(e) {
						if (this.checked) {
							$(layero).find("input[name='columns']").each(function() {
								this.checked = true;
							});
						} else {
							$(layero).find("input[name='columns']").each(function() {
								this.checked = false;
							});
						}
					});
				},
				yes: function(index, layero) {
					data["t"] = Math.random();
					data["$_type"] = "pdf";
					data["$exportname"] = data["$exportname"] || $this.parentsUntil(".portlet").last().siblings(".portlet-title").find(".caption").text();
					data["fields"] = [];
					data["fielddefs"] = {};
					$(layero).find("input[name='columns']").each(function(index, ele) {
						var c = $(ele).data("name");
						var i = index + ($this.data("select") != "none" ? 1 : 0);
						if (ele.checked) {
							data["fields"].push(_tc[c]["name"]);
							data["fielddefs"][_tc[c]["name"]] = _tc[c];
						}
					});
					data['fields'] = data["fields"].join(",");
					data['fielddefs'] = $.toJSON(data["fielddefs"]);
					var totals = [];
					$("[expressions][expressions!='']").each(function() {
						var $input = $(this);
						if ($this.attr("tableid") == $input.attr("tableid")) {
							totals.push([
									$input.attr("expressions"), $input.attr("name")
							].join(","));
						}
					});
					data["totals"] = totals.join(",");
					data = $.extend(true, data, $this.data("params"));
					$.print(action, data);
					layer.close(index);
				},
				cancel: function(index) {
				}
			});
		} else {
			data = $.extend(true, data, $this.data("params"));
			$.print(action, data);
		}
	},
	"addRecord": function(){
		var $this = this;
		var _rr = {DT_RowId: $this.attr("tid") + "row_" + ($this.DataTable().rows()[0].length), _CT_Data: (record || {})};
		if($this.attr("djselect")){
			var u = $this.attr("djselect").replace(/\./g, "/");
			var url = cooperopcontextpath + "/w/" + u+".html";
			var da = $("[ctype='form']").getData() || {};
			delete da.djbh;
			delete da.pageid;
			delete da.gzid;
			delete da.clientid;
			delete da.djbs;
			delete da.djlx;
			delete da.rq;
			delete da.kaiprq;
			delete da.riqi;
			delete da.ontime;
			delete da.dep_code;
			delete da.dep_name;
			if($this.attr("tablekey")){
				da.ptableid = $this.attr("tableid");
				$.modal(url, "新增", $.extend(true, {
					callback : function(rtn) {
						if (rtn) {
							$this.refresh_table("last");
						}
					}
				}, da));
			}else{
				var pageinfo = $("[name='pageid']").val().split(".");
				if(pageinfo[1] == "query"){
					url = url + "?fromtable=Y";
				}else{
					da.ptableid = $this.attr("tableid");
					da.p_pageid=$("[name='pageid']").val();
					da.p_gzid=$("[name='gzid']").val();
				}
				$.modal(url, "新增", $.extend(true, {
					callback : function(rtn) {
						if (rtn) {
							$this.refresh_table("last");
						}
					}
				}, da));
			}
		}else{
			var html = [];
			html.push("<div class='portlet box' >");
			html.push("<div class='portlet-body' >");
			html.push("<div class='form-horizontal' >");
			html.push("<div class='row-fluid'>");
			var _tc = $this.data("_tc");
			for (var c in _tc) {
				if(_tc[c]["name"] == 'DT_Select'){continue;}
				var value = (_rr["_CT_Data"][_tc[c]["name"]] || "");
				if (_tc[c]["template_script"]) {
					var record = {};
					value = $this[_tc[c]["template_script"]](record, $.parseJSON(_tc[c]["dictionary"]));
				} else if (_tc[c]["template"]) {
					var record = $.extend(true, {}, _rr["_CT_Data"]);
					value = $.dlexpr(_tc[c]["template"], record);
				}
				if (_tc[c]["dictionary"]) {
					value = $.parseJSON(_tc[c]["dictionary"])[value] || value;
				}
				if (_tc[c]["datatype"] == "date" && value) {
					value = $.formatdate(new Date(+value), _tc[c]["format"] || "yyyy-MM-dd");
				} else if (_tc[c]["datatype"] == "time" && value) {
					value = $.formatdate(new Date(+value), _tc[c]["format"] || "HH:mm:ss");
				} else if (_tc[c]["datatype"] == "datetime" && value) {
					value = $.formatdate(new Date(+value), _tc[c]["format"] || "yyyy-MM-dd HH:mm:ss");
				} else if (_tc[c]["datatype"] == "number") {
					value = $.formatnumber(new Date(+value), _tc[c]["format"] || "#,###.#");
				} else if (_tc[c]["datatype"] == "decimal") {
					value = $.formatnumber(new Date(+value), _tc[c]["format"] || "#,##0,00");
				} else if (_tc[c]["datatype"] == "currency") {
					value = "￥" + $.formatnumber(new Date(+value), _tc[c]["format"] || "#,##0,00");
				}
				if(_tc[c]["hidden"]=='hidden'){
					html.push("<div class='cols' style='display:none;'>");
				}else{
					html.push("<div class='cols'>");
				}
				html.push("<label class='control-label'>"+_tc[c]["label"]+"</label>");
				if(_tc[c]["template"]){
					value = value.replace("name=\""+_tc[c]["name"]+"\"","name=\"mx_"+_tc[c]["name"]+"\"");
					html.push(value.replace("intable=\"intable\""),"");
				} else if (_tc[c]["datatype"] == "image") {
					var record = $.extend(true, {}, _rr["_CT_Data"]);
					value = $.dlexpr('<s:image name="mx_'+_tc[c]["name"]+'" value="'+_tc[c]["name"]+'"></s:image>"', record);
					html.push(value.replace("intable=\"intable\""),"");
				} else if (_tc[c]["datatype"] == "file") {
					var record = $.extend(true, {}, _rr["_CT_Data"]);
					value = $.dlexpr('<s:file name="mx_'+_tc[c]["name"]+'" value="'+_tc[c]["name"]+'"></s:file>"', record);
					html.push(value.replace("intable=\"intable\""),"");
				}else{
					html.push("<p class='form-control-static'>"+value+"</p>");
				}
				html.push("</div>");
			}
			html.push("</div>");
			html.push("</div>");
			html.push("</div>");
			html.push("</div>");
			var _t = $(html.join(""));
			$this.after(_t);
			_t.ccinit();
			var o = {
					btn: ['保存', '取消']
					,btn1: function(index, layero){ //或者使用btn1
						var data = _t.getData();
						var ddd={};
						for(k in data){
							var v = data[k];
							if(k.indexOf("mx_") == 0){
								k = k.replace("mx_", "");
							}
							ddd[k]=v;
						}
						ddd.gzid = $("[name='gzid']").val();
						ddd.pageid = $("[name='pageid']").val();
						ddd.tableid = $this.attr("tableid");
						$.call("application.bill.addMX", ddd, function(rtn){
						  //TODO刷新页面
						  $this.refresh_table("last");
					  		if(rtn.result=='success'){
								  layer.close(index);
								  _t.remove();
							}
						});
					},btn2: function(index){ //或者使用btn2
						layer.close(index);
						_t.remove();
					} 
			};
			o.type = 1;
			o.content = _t;
			o.area = [o.width || "80%", o.height || "80%"];
			o.cancel = function(index) {
				layer.close(index);
				_t.remove();
		    }
			layer.open(o);
		}
	},
	"deleterow_table" : function() {
		var $this = this;
		var data = $this.getSelected_table();
		$(data).each(function(index, o) {
			$this.DataTable().row($(o.element)).remove().draw(false);
		})
		$this.data("_selected", []);
		$this.data("_selected_data", {});
	},
	"editrow_table" : function(rowd) {
		var $this = this;
		if (!rowd) {
			return;
		}
	},
	"addrow_table" : function(record) {
		var $this = this;
		var _rr = {
			DT_RowId : $this.attr("ctid") + "row_" + ($this.DataTable().rows()[0].length),
			_CT_Data : (record || {})
		};
		var _tc = $this.data("_tc");
		if ($.isEmptyObject(_rr["_CT_Data"])) {
			for (var c in _tc) {
				_rr["_CT_Data"][_tc[c]["name"]] = null;
			}
		}
		$this.DataTable().row.add(_rr).draw(false);
		return _rr;
	},
	"deleterecord":function(op){
		var $this = this;
		var data = $this.getSelected_table();
		var d ={
			gzid :$("[name='gzid']").val(),
			pageid : $("[name='pageid']").val(),
			tableid:$this.attr("tableid"),
			tablekey : $this.attr("tablekey")
		}
		$.extend(true,d, op || {});
		d.tr= [];
		$(data).each(function(index,o){
			o.element.remove();
			d.tr.push(o.data);
		})
		$.call("application.bill.deleteMX",{"data":$.toJSON(d)},function(rtn){
			  //TODO刷新页面
			$this.refresh_table($this.DataTable().page());
			if(rtn.result=='success'){
				$.message("操作成功！");
			}
		});
	},
	"editrecord":function(rowd){
		var $this = this;
		if(!rowd){return;}
		if($this.attr("djselect")){
			var u = $this.attr("djselect").replace(/\./g, "/");
			var url = cooperopcontextpath + "/w/" + u+".html";
			if($this.attr("tablekey")){
				$.modal(url+"?"+$this.attr("tablekey")+"="+rowd.data[$this.attr("tablekey")], "修改", $.extend(true, {
					callback : function(rtn) {
						if (rtn) {
							$this.refresh_table($this.DataTable().page());
						}
					}
				}, {}));
			}else{
				var pageinfo = $("[name='pageid']").val().split(".");
				if(pageinfo[1] == "query"){
					url = url + "?fromtable=Y";
				}else{
					url = url + "?ptableid="+$this.attr("tableid")+
					"&p_pageid="+$("[name='pageid']").val();
				}
				if(rowd.data.djbh){
					url = url + "&djbh="+rowd.data.djbh;
				}else if(rowd.data.dj_sn){
					url = url + "&p_dj_sn="+rowd.data.dj_sn + "&p_dj_sort="+rowd.data.dj_sort 
					+ "&p_gzid="+$("[name='gzid']").val();
				}
				$.modal(url, "修改", $.extend(true, {
					callback : function(rtn) {
						if (rtn) {
							$this.refresh_table($this.DataTable().page());
						}
					}
				}, {}));
			}
		}else{/*
			var html = [];
			html.push("<div class='portlet box' >");
			html.push("<div class='portlet-body' >");
			html.push("<div class='form-horizontal' >");
			html.push("<div class='row-fluid'>");
			html.push("<input type='hidden' name='dj_sn' value='"+rowd.data.dj_sn+"'/>");
			html.push("<input type='hidden' name='dj_sort' value='"+rowd.data.dj_sort+"'/>");
			
			var _tc = $this.data("_tc");
			for (var c in _tc) {
				if(_tc[c]["name"] == 'DT_Select'){continue;}
				var value = (rowd.data[_tc[c]["name"]] || "");
				if (_tc[c]["template_script"]) {
					var record = $.extend(true, {}, rowd.data);
					value = $this[_tc[c]["template_script"]](record, $.parseJSON(_tc[c]["dictionary"]));
				} else if (_tc[c]["template"]) {
					var record = $.extend(true, {}, rowd.data);
					value = $.dlexpr(_tc[c]["template"], record);
				}
				if (_tc[c]["dictionary"]) {
					value = $.parseJSON(_tc[c]["dictionary"])[value] || value;
				}
				if (_tc[c]["datatype"] == "date" && value) {
					value = $.formatdate(new Date(+value), _tc[c]["format"] || "yyyy-MM-dd");
				} else if (_tc[c]["datatype"] == "time" && value) {
					value = $.formatdate(new Date(+value), _tc[c]["format"] || "HH:mm:ss");
				} else if (_tc[c]["datatype"] == "datetime" && value) {
					value = $.formatdate(new Date(+value), _tc[c]["format"] || "yyyy-MM-dd HH:mm:ss");
				} else if (_tc[c]["datatype"] == "number") {
					value = $.formatnumber(new Date(+value), _tc[c]["format"] || "#,###.#");
				} else if (_tc[c]["datatype"] == "decimal") {
					value = $.formatnumber(new Date(+value), _tc[c]["format"] || "#,##0,00");
				} else if (_tc[c]["datatype"] == "currency") {
					value = "￥" + $.formatnumber(new Date(+value), _tc[c]["format"] || "#,##0,00");
				}
				html.push("<div class='cols'>");
				html.push("<label class='control-label'>"+_tc[c]["label"]+"</label>");
				if(_tc[c]["template"]){
					html.push(value);
				}else{
					html.push("<p class='form-control-static'>"+value+"</p>");
				}
				html.push("</div>");
			}
			html.push("</div>");
			html.push("</div>");
			html.push("</div>");
			html.push("</div>");
			var _t = $(html.join(""));
			$this.after(_t);
			_t.ccinit();
			var o = {
					btn: ['保存', '取消']
					,btn1: function(index, layero){ //或者使用btn1
						var data = _t.getData();
						data.gzid = $("[name='gzid']").val();
						data.pageid = $("[name='pageid']").val();
						data.tableid = $this.attr("tableid");
						$.call("application.bill.updateMX",{data :$.toJSON(data)},function(rtn){
							  //TODO刷新页面
							 $this.refresh_table($this.DataTable().page());
								if(rtn.result=='success'){
									  layer.close(index);
									  _t.remove();
								}
						});
					},btn2: function(index){ //或者使用btn2
						layer.close(index);
						_t.remove();
					} 
			};
			o.type = 1;
			o.content = _t;
			o.area = [o.width || "80%", o.height || "80%"];
			o.cancel = function(index) {
				layer.close(index);
				_t.remove();
		    }
			layer.open(o);
		*/}
	},
	"newBlankRow": function(record) {
		var $this = this;
		var _rr = {DT_RowId: $this.attr("ctid") + "row_" + ($this.DataTable().rows()[0].length), _CT_Data: (record || {})};
		if ($this.data("select") != "none") {
			_rr = $.extend(true, _rr, $this.data("_selectcol"));
		}
		var _tc = $this.data("_tc");
		for (var c = $this.data("select") != "none" ? 1 : 0, cLen = _tc.length; c < cLen; c ++) {
			var value = (_rr["_CT_Data"][_tc[c]["name"]] || "");
			if (_tc[c]["template_script"]) {
				var record = $.extend(true, {}, _rr["_CT_Data"]);
				value = $this[_tc[c]["template_script"]](record, $.parseJSON(_tc[c]["dictionary"]));
			} else if (_tc[c]["template"]) {
				var record = $.extend(true, {}, _rr["_CT_Data"]);
				value = $.dlexpr(_tc[c]["template"], record);
			}
			if (_tc[c]["dictionary"]) {
				value = $.parseJSON(_tc[c]["dictionary"])[value] || value;
			}
			if (_tc[c]["datatype"] == "date" && value) {
				value = $.formatdate(new Date(+value), _tc[c]["format"] || "yyyy-MM-dd");
			} else if (_tc[c]["datatype"] == "time" && value) {
				value = $.formatdate(new Date(+value), _tc[c]["format"] || "HH:mm:ss");
			} else if (_tc[c]["datatype"] == "datetime" && value) {
				value = $.formatdate(new Date(+value), _tc[c]["format"] || "yyyy-MM-dd HH:mm:ss");
			} else if (_tc[c]["datatype"] == "number") {
				value = $.formatnumber(new Date(+value), _tc[c]["format"] || "#,###.#");
			} else if (_tc[c]["datatype"] == "decimal") {
				value = $.formatnumber(new Date(+value), _tc[c]["format"] || "#,##0,00");
			} else if (_tc[c]["datatype"] == "currency") {
				value = "￥" + $.formatnumber(new Date(+value), _tc[c]["format"] || "#,##0,00");
			}
			_rr[_tc[c]["name"]] = value || "";
		}
		$this.DataTable().row.add(_rr).draw(false);
		return _rr;
	}
});
