$.fn.extend({
	"ccdesigner": function() {
		var $this = this;
		
		$this.page = new cccontent("page");
		$this.page.element = $this;
		$this.page.attr("ismodal", "false");
		$this.page.attr("disloggedin", "false");
		$this.page.attr("dispermission", "false");
		$this.page.attr("schemeid", undefined);
		$this.page.attr("init-action", "");
		$this.page.attr_p = [];
		$this.page.attr_gj = [];
		$this.page.attr_gj_jg = [];
		//保存，保存时，在后台转为hashmap，然后序列化存入数据，起到加密的作用
		$this.ccsave = function() {
			var o = $this.page.getData();
			var r;
			$.call("crdc.designer.save", {jdata: $.toJSON(o)}, function(rtn) {
				r = rtn.result;
			},null,{async: false});
			return r;
		};
		
		//保存并发布,发布时目前只考虑本服务器发布，如果要发布还要考虑跨服务器的问题
		$this.ccdeploy = function() {
			var o = $this.page.getData();
			//console.log({deploy: true, jdata: $.toJSON(o)});
//			$.call("", {deploy: true, jdata: $.toJSON(o)}, function(rtn) {
//				
//			});
		};
		//载入
		$this.ccload = function(id,type,flag,system_product_code) {
			$.call("crdc.designer.get", {schemeid:id,type:type,flag:flag,system_product_code:system_product_code}, function(data) {
				//$this.page.setData(data.definition);
				$this.page.attrs = $.extend(true,{},data.definition.attrs);
				$this.page.attr_p = data.definition.attr_p;
				$this.page.attr_gj = data.definition.attr_gj;
				$this.page.attr_gj_jg = data.definition.attr_gj_jg;
				$this.page.loadchild(data.definition.contents);
				$(data.definition.contents).each(function(index,obj){
					$.call("crdc.designer.getControlHTML", {jdata: $.toJSON(obj)}, function(data) {
						var html = [];
						html.push(data.html);
						$this.page.element.append($(html.join("")));
					},null,{async: false});
				})
				$this.page.setChildElement($this.page.element);
			},null,{async: false});
		};
		//控件基础结构体 可能产生继承
		function cccontent(type) {
			var $this = this;
			$this.type = type;
			$this.parent = undefined;  //父控件
			$this.element = undefined;  //控件对应html元素（JQuery对象）
			$this.attrs = {"isdesign":"true"};  //控件属性集 键值对
			$this.contents = []; //子控件集（由于考虑顺序问题，固使用数组）
			
			//获取或设置控件属性
			$this.attr = function(key) {
				var rtn = undefined;
				var args = arguments;
				if (args.length == 1 && $.type(args[0]) == 'string') {
					return this.attrs[key];
				} else if (args.length == 1 && !$.isEmptyObject(key)) {
					$.extend(true, $this.attrs, key);
					$this.repaint();
				} else if (args.length == 2) {
					$this.attrs[key] = args[1];
					$this.repaint();
				}

				return $this.attrs[key];
			};
			
			//删除控件属性
			$this.removeAttr = function(key) {
				var rtn = $this.attrs[key];
				delete $this.attrs[key];
				$this.repaint();
				return rtn;
			};
			
			//在当前控件中添加控件
			$this.appendAndRepaint = function(type, attributes) {
				var ccc = new cccontent(type);
				ccc.parent = $this;
				if (attributes) {
					$.extend(true, ccc.attrs, attributes);
				}
				
				if(type == "table"){
					var fields = new cccontent("tablefields");
					fields.parent = ccc;
					var field = new cccontent("tablefield");
					var field1 = new cccontent("tablefield");
					var field2 = new cccontent("tablefield");
					field.parent = fields;
					field1.parent = fields;
					field2.parent = fields;
					$.extend(true, field.attrs, {label:"姓名",code:"name",type:"text",sortable:"true",sort:"asc"});
					$.extend(true, field1.attrs, {label:"性别",code:"sex",type:"text",sortable:"true",sort:"asc"});
					$.extend(true, field2.attrs, {label:"年龄",code:"age",type:"number",sortable:"true",format:"###岁"});
					
					fields.contents.push(field);
					fields.contents.push(field1);
					fields.contents.push(field2);
					
					ccc.contents.push(fields);
				}
				
				$this.contents.push(ccc);
				$this.repaint();
				return ccc;
			}
			$this.append = function(type, attributes,appendtype,dest) {
				var ccc = new cccontent(type);
				ccc.parent = $this;
				if (attributes) {
					$.extend(true, ccc.attrs, attributes);
				}
				if(type =='taskhistory'){
					$.extend(true, ccc.attrs, {djbh : '${djbh}'});
				}
				if(type == "select" || type=="checkbox" || type=="radio"){
					var op = new cccontent("option");
					op.parent = ccc;
					$.extend(true, op.attrs, {label:"选择项1",name:"option",value:"0"});
					ccc.contents.push(op);
				}
				if(type == "autocomplete"){
					var op = new cccontent("option");
					op.parent = ccc;
					$.extend(true, op.attrs, {label:"$[name]",value:"$[id]",optiontext:"$[name]"});
					ccc.contents.push(op);
				}
				if(type == "table"){
					var fields = new cccontent("tablefields");
					fields.parent = ccc;
					var field = new cccontent("tablefield");
					var field1 = new cccontent("tablefield");
					var field2 = new cccontent("tablefield");
					field.parent = fields;
					field1.parent = fields;
					field2.parent = fields;
					$.extend(true, field.attrs, {label:"姓名",code:"name",type:"text",sortable:"true",sort:"asc"});
					$.extend(true, field1.attrs, {label:"性别",code:"sex",type:"text",sortable:"true",sort:"asc"});
					$.extend(true, field2.attrs, {label:"年龄",code:"age",type:"number",sortable:"true",format:"###岁"});
					
					fields.contents.push(field);
					fields.contents.push(field1);
					fields.contents.push(field2);
					
					ccc.contents.push(fields);
				}
				ccc.paint(appendtype,dest);
				
				$this.contents.push(ccc);
				return ccc;
			};
			//使用JQuery选择器进行控件选择（选择器针对页面html元素）
			//如designer.page.find("div[ctype='toolbar']")[0]
			$this.find = function(str) {
				var ccc = [];
				var t = $this.element.find(str);
				for (var i = 0; i < t.length; i ++) {
					$($this.contents).each(function(index, obj) {
						if (obj.element && obj.element[0] == t[i]) {
							ccc.push(obj);
							return false;
						}
						$.merge(ccc, obj.find(str));
					});
				}
				return ccc;
			};
			
			//使用页面元素直接获得控件（HTML元素）
			//如designer.page.get($("div[ctype='button'][color='red']")[0])
			$this.get = function(element) {
				var ccc = undefined;
				$($this.contents).each(function(index, obj) {
					if (obj.element && (obj.attrs.crid == $(element).attr("crid") || obj.element[0] == element)) {
						ccc = obj;
						return false;
					}
					var c = obj.get(element);
					if (c != undefined) {
						ccc = c;
						return false;
					}
				});
				return ccc;
			};
			//如designer.page.get($("div[ctype='button'][color='red']")[0])
			$this.getparent = function(element) {
				var ccc = undefined;
				var obj = $this.parent;
				if (obj.element && (obj.attrs.crid == $(element).attr("crid") || obj.element[0] == element)) {
					ccc = obj;
				}else{
					var c = obj.getparent(element);
					if (c != undefined) {
						ccc = c;
					}
				}
				return ccc;
			};
			//全新绘制控件html
			$this.paint = function(appendtype,dest) {
				var html = [];
				$.call("crdc.designer.getControlHTML", {jdata: $.toJSON($this.getData())}, function(data) {
					html.push(data.html);
					$this.element = $(html.join(""));
					var p ="";
					if(appendtype=="row2form"){
						if($($this.parent.element).find(".form-horizontal").length > 0){
							p = $($this.parent.element).find(".form-horizontal");
						}else {
							p = $this.parent.element;
						}
					}else if(appendtype=="tool2page"){
						if($this.parent.contents.length>0){
							$this.parent.element.children(":first").before($this.element);
						}else{
							$this.parent.element.append($this.element);
						}
						
					}else if(appendtype=="button2pagetoolbar"){
						p = $($this.parent.element).find(".navbar-collapse ul");
					}else{
						if(dest!=undefined){
							$this.element.insertBefore(dest.element);
						}else{
							$this.parent.element.append($this.element);
						}
					}
					if(p!=""){
						if(dest!=undefined){
							$this.element.insertBefore(dest.element);
						}else{
							p.append($this.element);
						}
					}
				},null,{async: false});
				if($this.type=="richeditor" || $this.type=="switch" || $this.type=="radio" || $this.type=="checkbox"|| $this.type=="table"){
					$this.element.ccinit();
				}
				if($this.attrs.crid){
					if($this.type == "toolbar"){
						return ;
					}
					$this.element.contextmenu({
						target:'#context-menu',
						before: function(e) {
							return true;
						},
						onItem: function showSetting(context,e){
							var atype = $(e.toElement).attr("atype");
							var url = "setting.html";
							var width = "80%";
							var height = "80%";
							if($this.type=="table"){
								width = "95%";
								height = "85%";
								url = "setting_table.html";
							}else if($this.type=="tabs"){
								url = "setting_tabpanel.html";
							}else if($this.type=="checkbox" || $this.type=="radio" || $this.type=="select"){
								url = "setting_select.html";
							}else if($this.type=="form"){
								url = "setting_form.html";
							}else if($this.type=="switch"){
								url = "setting_switch.html";
							}else if($this.type=="button"){
								url = "setting_button.html";
							}
							if(atype=="setting"){
								if($this.type=="tabpanel"||$this.type=="row"||$this.type==""){
									return true;
								}
								$(context).attr("rightclick","rightclick");
								var d = $this.getData();
								var schemeid = $this.getparent("#_uicontent_").attrs.schemeid;
								var system_product_code = $this.getparent("#_uicontent_").attrs.system_product_code;
								if(!schemeid){
									return true;
								}
								d.attrs.schemeid = schemeid;
								d.attrs.system_product_code = system_product_code;
								$.modal(url, "设置", {
									width:width,
									height:height,
									type: $this.type,
									attdata: $.toJSON(d),
									callback: function(rtn) {
										if(rtn){
											$(context).removeAttr("rightclick");
												if($this.type == "table"){
													var fields = new cccontent("tablefields");
													fields.parent = $this;
													var child = rtn.children;
													for (var i = 0; i < child.length; i ++) {
														var field = new cccontent("tablefield")
														field.parent = fields;
														field.attrs = $.extend(true, {}, child[i]);
														if(field.attrs.available == 'false'){
															field.attrs.hidden = "true";
														}
														//console.log(field.attrs.controltype);
														if(field.attrs.controltype){
															field.attrs.datatype="template";
															var tc = new cccontent(field.attrs.controltype);
															tc.parent = field;
															//console.log(tc.type);
															delete child[i].label;
															if(field.attrs.editable=='true'){
																tc.attrs = $.extend(true, {}, child[i]);
															}else{
																tc.attrs = $.extend(true, {readonly: "true"}, child[i]);
															}
															
															field.contents.push(tc);
														}
														//}
														fields.contents.push(field);
													}
													for(var j=0;j<fields.contents.length;j++){
														if(fields.contents[j].attrs.fieldorder){
															var ind = fields.contents[j].attrs.fieldorder;
															var con = fields.contents[ind];
															fields.contents[ind] = fields.contents[j];
															fields.contents[j] = con;
														}
													}
													var toolbar;
													$($this.contents).each(function(index,o){
														if(o.type == "toolbar"){
															toolbar = o;
														}
													});
													$this.contents = [];
													$this.contents.push(fields);
													if(toolbar){
														$this.contents.push(toolbar);
													}
												}else if($this.type == "form"){
													
												}else if($this.type == 'checkbox' || $this.type == 'radio' || $this.type == 'select'){
													var child = rtn.children;
													$this.contents = [];
													for (var i = 0; i < child.length; i ++) {
														var option = new cccontent("option");
														option.parent = $this;
														option.attrs = $.extend(true, {}, child[i]);
														if(option.attrs.contentvalue){
															option.attrs.value = option.attrs.contentvalue;
														}else if(option.attrs.value){
															option.attrs.value = option.attrs.value;
														}else{
															option.attrs.value = option.attrs.contentIndex;
														}
														$this.contents.push(option);
													}
													for(var j=0;j<$this.contents.length;j++){
														if($this.contents[j].attrs.contentIndex){
															var ind = $this.contents[j].attrs.contentIndex;
															var con = $this.contents[ind];
															$this.contents[ind] = $this.contents[j];
															$this.contents[j] = con;
														}
													}
												}
												if(rtn.attrs.istoorbar=="Y"){
													if($this.attrs.istoorbar != "Y"){
														var toolbar = new cccontent("toolbar");
														$.extend(true, toolbar.attrs, {ondrop:"dropToolbar(this)",
															ondragover:"allowDrop(event)",crid:new Date().getTime()});
														toolbar.parent = $this;
														var button = new cccontent("button");
														$.extend(true, button.attrs,{crid:new Date().getTime()+1,label:"保存",icon:"fa fa-save"});
														button.parent = toolbar;
														toolbar.contents.push(button);
														$this.contents.push(toolbar);
													}
												}else{
													$($this.contents).each(function(index,o){
													    if(o.type==='toolbar'){
													    	$this.contents.splice(index,1);
													        return;
													    }
													})
												}
											$.extend(true, $this.attrs, rtn.attrs);
											if($this.type == 'autocomplete'){
												$.extend(true, $this.attrs, {text: "${"+rtn.attrs.name+"name}"});
											}
											if($this.parent.type == "tabpanel"){
												$this.parent.repaint();
											}else{
												$this.repaint();
											}
										}
									}
								})
							}else if(atype=="delete"){
								$($this.parent.contents).each(function(index, obj) {
									if(obj ==$this){
										$this.parent.contents.splice(index,1);
									}
								});
								$this.element.remove();
							}
							
						}
					});
				}
			};
			$this.setChildElement = function(element) {
				if($this.contents){
					$($this.contents).each(function (index ,obj){
						obj.element = undefined;
						if(obj.attrs.crid){
							obj.element = element.find("[crid='"+obj.attrs.crid+"']");
							if(obj.type != "toolbar"){
							obj.element.contextmenu({
								target:'#context-menu',
								before: function(e) {
									return true;
								},
								onItem: function showSetting(context,e){
									var atype = $(e.toElement).attr("atype");
									var url = "setting.html";
									var width = "80%";
									var height = "80%";
									if(obj.type=="table"){
										width = "95%";
										height = "85%";
										url = "setting_table.html";
									}else if(obj.type=="tabs"){
										url = "setting_tabpanel.html";
									}else if(obj.type=="checkbox" || obj.type=="radio" || obj.type=="select"){
										url = "setting_select.html";
									}else if(obj.type=="switch"){
										url = "setting_switch.html";
									}else if(obj.type=="form"){
										url = "setting_form.html";
									}else if(obj.type=="button"){
										url = "setting_button.html";
									}
									if(atype=="setting"){
										if(obj.type=="tabpanel"||obj.type=="row"||obj.type==""){
											return true;
										}
										$(context).attr("rightclick","rightclick");
										var d = obj.getData();
										var p = obj.getparent("#_uicontent_");
										var schemeid = p.attrs.schemeid;
										var flag = p.attrs.flag;
										var system_product_code = p.attrs.system_product_code;
										if(!schemeid){
											return true;
										}
										d.attrs.schemeid = schemeid;
										d.attrs.flag = flag;
										d.attrs.system_product_code = system_product_code;
										$.modal(url, "设置", {
											width:width,
											height:height,
											type: obj.type,
											attdata: $.toJSON(d),
											callback: function(rtn) {
												if(rtn){
													$(context).removeAttr("rightclick");
														if(obj.type == "table"){
															var fields = new cccontent("tablefields");
															fields.parent = obj;
															var child = rtn.children;
															for (var i = 0; i < child.length; i ++) {
																var field = new cccontent("tablefield")
																field.parent = fields;
																field.attrs = $.extend(true, {}, child[i]);
																if(field.attrs.available == 'false'){
																	field.attrs.hidden = "true";
																}
																//console.log(field.attrs.controltype);
																if(field.attrs.controltype){
																	field.attrs.datatype="template";
																	var tc = new cccontent(field.attrs.controltype);
																	tc.parent = field;
																	//console.log(tc.type);
																	delete child[i].label;
																	if(field.attrs.editable=='true'){
																		tc.attrs = $.extend(true, {}, child[i]);
																	}else{
																		tc.attrs = $.extend(true, {readonly: "true"}, child[i]);
																	}
																	
																	field.contents.push(tc);
																}
																fields.contents.push(field);
															}
															for(var j=0;j<fields.contents.length;j++){
																if(fields.contents[j].attrs.fieldorder){
																	var ind = fields.contents[j].attrs.fieldorder;
																	var con = fields.contents[ind];
																	fields.contents[ind] = fields.contents[j];
																	fields.contents[j] = con;
																}
															}
															var toolbar;
															$(obj.contents).each(function(index,o){
																if(o.type == "toolbar"){
																	toolbar = o;
																}
															});
															obj.contents = [];
															obj.contents.push(fields);
															if(toolbar){
																obj.contents.push(toolbar);
															}
														}else if(obj.type == "form"){
															
														}else if(obj.type == 'checkbox' || obj.type == 'radio' || obj.type == 'select'){
															var child = rtn.children;
															obj.contents = [];
															for (var i = 0; i < child.length; i ++) {
																var option = new cccontent("option");
																option.parent = obj;
																option.attrs = $.extend(true, {}, child[i]);
																if(option.attrs.contentvalue){
																	option.attrs.value = option.attrs.contentvalue;
																}else if(option.attrs.value){
																	option.attrs.value = option.attrs.value;
																}else{
																	option.attrs.value = option.attrs.contentIndex;
																}
																obj.contents.push(option);
															}
															for(var j=0;j<obj.contents.length;j++){
																if(obj.contents[j].attrs.contentIndex){
																	var ind = obj.contents[j].attrs.contentIndex;
																	var con = obj.contents[ind];
																	obj.contents[ind] = obj.contents[j];
																	obj.contents[j] = con;
																}
															}
														}
														if(rtn.attrs.istoorbar=="Y"){
															if(obj.attrs.istoorbar != "Y"){
																var toolbar = new cccontent("toolbar");
																$.extend(true, toolbar.attrs, {ondrop:"dropToolbar(this)",
																	ondragover:"allowDrop(event)",crid:new Date().getTime()});
																toolbar.parent = obj;
																var button = new cccontent("button");
																$.extend(true, button.attrs,{crid:new Date().getTime()+1,label:"保存",icon:"fa fa-save"});
																button.parent = toolbar;
																toolbar.contents.push(button);
																obj.contents.push(toolbar);
															}
														}else{
															$(obj.contents).each(function(index,o){
															    if(o.type==='toolbar'){
															    	obj.contents.splice(index,1);
															        return;
															    }
															})
														}
													$.extend(true, obj.attrs, rtn.attrs);
													if(obj.type == 'autocomplete'){
														$.extend(true, obj.attrs, {text: "${"+rtn.attrs.name+"name}"});
													}
													if(obj.parent.type == "tabpanel"){
														obj.parent.repaint();
													}else{
														obj.repaint();
													}
												}
											}
										})
									}else if(atype=="delete"){
										$(obj.parent.contents).each(function(index, o) {
											if(obj == o){
												obj.parent.contents.splice(index,1);
											}
										});
										obj.element.remove();
									}
									
								}
							});
							}
						}
						obj.setChildElement(element);
					});
					if($this.type == "tabpanel" || $this.type=="richeditor" || $this.type=="switch" 
						|| $this.type=="radio" || $this.type=="checkbox" 
							|| ($this.type == "table" && $this.parent.type == "row" )){
						$this.element.ccinit();
					}
				}
			}
			$this.loadchild = function(contents){
				if(contents){
					$(contents).each(function(index, obj){
						var ch = new cccontent(obj.type);
						ch.attrs = $.extend(true,{},obj.attrs);
						ch.parent = $this;
						$this.contents.push(ch);
						if(obj.contents){
							ch.loadchild(obj.contents);
						}
					});
				}
			}
			//修改控件属性后绘制控件html
			$this.repaint = function() {
				var html = [];
				$.call("crdc.designer.getControlHTML", {jdata: $.toJSON($this.getData())}, function(data) {
					html.push(data.html);
					if($this.type != "page"){
						var ele = $(html.join(""));
						$($this.element[0]).before(ele);
						$this.element.remove();
						$this.element=ele;
						$this.setChildElement(ele);
						if($this.type=="richeditor" || $this.type=="switch" || $this.type=="radio" || $this.type=="checkbox"|| $this.type=="table"){
							$this.element.ccinit();
						}else if($this.type == "tabs"){
							$this.parent.element.ccinit();
						}
						if($this.attrs.crid){
							if($this.type == "toolbar"){
								return ;
							}
							$this.element.contextmenu({
								target:'#context-menu',
								before: function(e) {
									return true;
								},
								onItem: function showSetting(context,e){
									var atype = $(e.toElement).attr("atype");
									var url = "setting.html";
									var width = "80%";
									var height = "80%";
									if($this.type=="table"){
										width = "95%";
										height = "85%";
										url = "setting_table.html";
									}else if($this.type=="tabs"){
										url = "setting_tabpanel.html";
									}else if($this.type=="checkbox" || $this.type=="radio" || $this.type=="select"){
										url = "setting_select.html";
									}else if($this.type=="switch"){
										url = "setting_switch.html";
									}else if($this.type=="form"){
										url = "setting_form.html";
									}else if($this.type=="button"){
										url = "setting_button.html";
									}
									if(atype=="setting"){
										if($this.type=="tabpanel"||$this.type=="row"||$this.type==""){
											return true;
										}
										$(context).attr("rightclick","rightclick");
										var d = $this.getData();
										var schemeid = $this.getparent("#_uicontent_").attrs.schemeid;
										var system_product_code = $this.getparent("#_uicontent_").attrs.system_product_code;
										if(!schemeid){
											return true;
										}
										d.attrs.schemeid = schemeid;
										d.attrs.system_product_code = system_product_code;
										$.modal(url, "设置", {
											width:width,
											height:height,
											type: $this.type,
											attdata: $.toJSON(d),
											callback: function(rtn) {
												if(rtn){
													$(context).removeAttr("rightclick");
														if($this.type == "table"){
															var fields = new cccontent("tablefields");
															fields.parent = $this;
															var child = rtn.children;
															for (var i = 0; i < child.length; i ++) {
																var field = new cccontent("tablefield")
																field.parent = fields;
																field.attrs = $.extend(true, {}, child[i]);
																if(field.attrs.available == 'false'){
																	field.attrs.hidden = "true";
																}
																//console.log(field.attrs.controltype);
																if(field.attrs.controltype){
																	field.attrs.datatype="template";
																	var tc = new cccontent(field.attrs.controltype);
																	tc.parent = field;
																	//console.log(tc.type);
																	delete child[i].label;
																	if(field.attrs.editable=='true'){
																		tc.attrs = $.extend(true, {}, child[i]);
																	}else{
																		tc.attrs = $.extend(true, {readonly: "true"}, child[i]);
																	}
																	
																	field.contents.push(tc);
																}
																fields.contents.push(field);
															}
															for(var j=0;j<fields.contents.length;j++){
																if(fields.contents[j].attrs.fieldorder){
																	var ind = fields.contents[j].attrs.fieldorder;
																	var con = fields.contents[ind];
																	fields.contents[ind] = fields.contents[j];
																	fields.contents[j] = con;
																}
															}
															var toolbar;
															$($this.contents).each(function(index,o){
																if(o.type == "toolbar"){
																	toolbar = o;
																}
															});
															$this.contents = [];
															$this.contents.push(fields);
															if(toolbar){
																$this.contents.push(toolbar);
															}
														}else if($this.type == "form"){
															
														}else if($this.type == 'checkbox' || $this.type == 'radio' || $this.type == 'select'){
															var child = rtn.children;
															$this.contents = [];
															for (var i = 0; i < child.length; i ++) {
																var option = new cccontent("option");
																option.parent = $this;
																option.attrs = $.extend(true, {}, child[i]);
																if(option.attrs.contentvalue){
																	option.attrs.value = option.attrs.contentvalue;
																}else if(option.attrs.value){
																	option.attrs.value = option.attrs.value;
																}else{
																	option.attrs.value = option.attrs.contentIndex;
																}
																$this.contents.push(option);
															}
															for(var j=0;j<$this.contents.length;j++){
																if($this.contents[j].attrs.contentIndex){
																	var ind = $this.contents[j].attrs.contentIndex;
																	var con = $this.contents[ind];
																	$this.contents[ind] = $this.contents[j];
																	$this.contents[j] = con;
																}
															}
														}
														if(rtn.attrs.istoorbar=="Y"){
															if($this.attrs.istoorbar != "Y"){
																var toolbar = new cccontent("toolbar");
																$.extend(true, toolbar.attrs, {ondrop:"dropToolbar(this)",
																	ondragover:"allowDrop(event)",crid:new Date().getTime()});
																toolbar.parent = $this;
																var button = new cccontent("button");
																$.extend(true, button.attrs,{crid:new Date().getTime()+1,label:"保存",icon:"fa fa-save"});
																button.parent = toolbar;
																toolbar.contents.push(button);
																$this.contents.push(toolbar);
															}
														}else{
															$($this.contents).each(function(index,o){
															    if(o.type==='toolbar'){
															    	$this.contents.splice(index,1);
															        return;
															    }
															})
														}
													$.extend(true, $this.attrs, rtn.attrs);
													if($this.type == 'autocomplete'){
														$.extend(true, $this.attrs, {text: "${"+rtn.attrs.name+"name}"});
													}
													if($this.parent.type == "tabpanel"){
														$this.parent.repaint();
													}else{
														$this.repaint();
													}
												}
											}
										})
									}else if(atype=="delete"){
										$($this.parent.contents).each(function(index, obj) {
											if(obj ==$this){
												$this.parent.contents.splice(index,1);
											}
										});
										$this.element.remove();
									}
									
								}
							});
						}
					}
				},null,{async: false});
			};
			//保存和发布时获取控件提交内容（去除其他的方法和属性，仅保留需要的内容）
			$this.getData = function() {
				var o = {};
				o.type = $this.type;
				o.attr_p = $this.attr_p;
				o.attr_gj = $this.attr_gj;
				o.attr_gj_jg = $this.attr_gj_jg;
				o.attrs = $.extend(true, {}, $this.attrs);
				o.contents = [];
				$($this.contents).each(function(index, content) {
					o.contents.push(content.getData());
				});
				return o;
			};
			$this.getHZData = function() {
				var o = [];
				$($this.contents).each(function(index, content) {
					if (content.type == "textfield" || 
							content.type == "textfield" || 
							content.type == "datefield" || 
							content.type == "timefield" || 
							content.type == "autocomplete" || 
							content.type == "select" || 
							content.type == "radio" || 
							content.type == "checkbox" || 
							content.type == "switch" || 
							content.type == "textarea" || 
							content.type == "richeditor" || 
							content.type == "image" || 
							content.type == "file") {
						o.push({name: content.attrs.name, label: content.attrs.label});
					} else {
						var t = content.getHZData();
						for (var i in t) {
							o.push(t[i]);
						}
					}
				});
				return o;
			};
		}
		return $this;
	}
});
