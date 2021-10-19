$.fn.extend({
	"ccinit_image" : function() {
		var $this = this;
		var pa = decodeURLParams(window.location.href);
		var cid_ = "";
		//alert($.is_mobile());
		if($.is_mobile()){
			if(pa.module){
				module = pa.module;
				cid_ = pa._CRSID;
				$this.attr("sequ", pa.sequ);
			}
			$this.find(".image-add-span.camera").remove();
			$this.find(".image-add-span.mobile").remove();
			$this.find(".image-add-span.local").css("height", "100%");
			$this.find(".image-add-span.local .icon-desktop").css("line-height", "128px");
		}
		
		var uploading = false;
		var _ff = $this.fileupload({
			url : cooperopcontextpath + "/rm/u/" + module+"?sequ="+$this.attr("sequ")+"&_sssid="+cid_ ,
			type : "POST",
			dataType : "json",
			pasteZone : $this, // jQuery Object Chrome Only
			fileInput : $this.find(":file"), // jQuery Object Chrome Only
			disableImageResize : false,
			paramName : "_ccfile[]",
			formAcceptCharset : "utf-8",
			messages: {
                maxNumberOfFiles: '文件已超出限定数量',
                acceptFileTypes: '文件类型不允许在此处上传',
                maxFileSize: '文件不得大于' + $.formatfilesize(+($this.attr("maxsize") || "5000000")),
                minFileSize: '文件过小'
            },
			formData: function(form) {
				return {};
			},
			start: function(e) {
				uploading = true;
			},
			always: function(e, data) {
				uploading = false;
				if (data.result.files) {
					var v_ = $this.find(".imageupload_file").val() ? $this.find(".imageupload_file").val().split(/,/g) : [];
					for (var i in data.result.files) {
						if ($.inArray(data.result.files[i].file_id, v_) == -1) {
							v_.push(data.result.files[i].file_id);
						}
					}
					$this.find(".imageupload_file").val(v_.join(','));
				}
			},
			processalways: function(e, data) {
				if(data.files.error){
					$.error(data.files[data.index].error);
					$this.find('.template-upload:last').remove();
					if (data.files[data.index].error == data.messages.maxNumberOfFiles) {
						$this.find(".fileupload-buttonbar").hide();
					} else {
						$this.find(".fileupload-buttonbar").show();
					}
				} else {
					$this.find(".fileupload-buttonbar").show();
				}
			},
			destroy: function(e, data) {
				var eli = data;
				$.ajax( {
					"async": true,
					"dataType" : "json",
					"type" : "POST",
					"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
					"cache" : false,
					"url" : cooperopcontextpath + "/rm/r/" + module + "/" + eli.file_id,
					"timeout" : 60000,
					"data" : {sequ: $this.attr("sequ")},
					"success" : function(rtn) {
						var v_ = $this.find(".imageupload_file").val() ? $this.find(".imageupload_file").val().split(/,/g) : [];
						var i = $.inArray(eli.file_id, v_);
						if (i > -1) {
							v_.pop(i,1);
						}
						$this.find(".imageupload_file").val(v_.join(','));
						var button = $(e.currentTarget);
						button.closest('.template-download').remove();
						$this.find(".fileupload-buttonbar").show();
					},
					"error" : function(XMLHttpRequest, textStatus, errorThrown) {
						if(XMLHttpRequest.status == 401){
							if(top.logindiv.hasLogin == false){
								top.logindiv.hasLogin = true;
								try{
									//增加page参数用于notlogin.jsp判断产品化的notloginforlogin.jsp
									var loginUrl = 'notlogin.jsp?model=ajax&page=' + page;
									if('undefined' !=typeof(cooperopcontextpath)){
										loginUrl = cooperopcontextpath+'/'+loginUrl;
									}
									modal(loginUrl, "请先登录",{title:'请先登录',width:550,height:420,'callback':function(){
											top.logindiv.hasLogin = false;
											if(arguments[0] == true){
												callAction(page, data, success, error);
											}
									}});
								}catch(e){
									top.logindiv.hasLogin = false;
								}
							}
							return;
						}
						var message = $.trim(XMLHttpRequest.responseText) || textStatus;
						if (!message) {
							if (XMLHttpRequest.status != 200) {
								message = "访问服务端异常，HTTP错误代码:" + XMLHttpRequest.status;
							}
							else {
								message = "访问服务端异常，错误原因:" + errorThrown.message;
							}
						}
						else if (message == "timeout")
							message = "连接服务器超时。";
						else if (message == "error"){
							message = "服务端处理异常。";
							return;
						}else if (message == "notmodified")
							message = "服务端未更新。";
						else if (message == "parsererror")
							message = "解析服务端返回信息异常。";
						$.error(message);
					}
				});
			},
			sequentialUploads: true, 
			singleFileUploads : true,
			limitMultiFileUploads : 1,
			maxFileSize : +($this.attr("maxsize") || "5000000"),
			minFileSize : 0,
			maxNumberOfFiles: +($this.attr("maxlength") || "4"),
			forceIframeTransport : false, // cross-site file
			autoUpload : true,
			disableImageResize : /Android(?!.*Chrome)|Opera/
					.test(window.navigator.userAgent),
			acceptFileTypes : /(\.|\/)(gif|jpe?g|png|bmp)$/i,
			previewMaxWidth: 126,
			previewMaxHeight: 126,
			disableImagePreview: false,
			disableAudioPreview: true,
			disableVideoPreview: true,
			uploadTemplateId : null,
			downloadTemplateId : null,
			uploadTemplate : function(o) {
				if ($this.find(".files > li").length + o.files.length >= o.options.maxNumberOfFiles) {
					$this.find(".fileupload-buttonbar").hide();
				} else {
					$this.find(".fileupload-buttonbar").show();
				}
				var row = [];
				for (var i=0, file; file=o.files[i]; i++) {
					var html = [];
					html.push('<li class="template-upload fade">');
					html.push('<div class="preview"></div>');
					html.push('<div class="banner"><span class="name">' + file.name + '</span><span class="size">Processing...</span></div>');
					html.push('<div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">');
					html.push('<div class="progress-bar" style="top:0%;"></div>');
					html.push('</div>');
					html.push('<div class="error text-danger"></div>');
				    if (!i && !o.options.autoUpload) {
				    	html.push('<a class="font-blue start" href="javascript:void(0)" style="display:none">');
				    	html.push('<i class="fa fa-upload"></i>');
				    	html.push('</a>');
				    }
				    if (!i) {
				    	html.push('<a class="font-red cancel" href="javascript:void(0)" title="取消">');
				    	html.push('<i class="fa fa-ban"></i>');
				    	html.push('</a>');
				    }
				    html.push('</li>');
				    row.push(html.join(""));
				}
				return row;
			},
			downloadTemplate : function(o) {
				if ($this.find(".files > li").length + o.files.length >= o.options.maxNumberOfFiles && o.files[0]) {
					$this.find(".fileupload-buttonbar").hide();
				} else {
					$this.find(".fileupload-buttonbar").show();
				}
				var row = [];
				for (var i=0, file; file=o.files[i]; i++) {
		            if (file.error) {
		            	continue;
		            }
					var html = [];
					html.push('<li class="template-download fade">');
		            if (file.error) {
						html.push('<div class="error text-danger">' + file.error + '</div>');
		            }
					html.push('<div class="preview">');
		            if (file.file_id) {
		            	html.push('<a href="javascript:void(0);" file_id="' + file.file_id + '" file_name="' + file.file_name + '" data-gallery><img src="' + cooperopcontextpath + '/rm/s/' + module + '/' + file.file_id + 'S"></a>');
		            }
					html.push('</div>');
					
					html.push('<div class="tools">');
					if ($this.attr("islabel") != "true" && $this.attr("deleteable") != "false") {
						if (file.file_id) {
			            	html.push('<a class="font-red delete" data-file_id="' + file.file_id + '"');
			                html.push('>');
			                html.push('<i class="fa fa-trash-o"></i>');
			                html.push('</a>');
			            } else {
					    	html.push('<a class="font-red cancel" href="javascript:void(0)" title="取消">');
					    	html.push('<i class="fa fa-ban"></i>');
					    	html.push('</a>');
			            }
					}
					html.push('<a class="left" href="javascript:void(0)" title="左移">');
			    	html.push('<i class="glyphicon glyphicon-chevron-left"></i>');
			    	html.push('</a>');
			    	html.push('<a class="right" href="javascript:void(0)" title="右移">');
			    	html.push('<i class="glyphicon glyphicon-chevron-right"></i>');
			    	html.push('</a>');
			    	html.push('</div>');
			    	
				    html.push('</li>');
				    row.push(html.join(""));
		        }
				return row;
			}
		});
		_ff.bind("fileuploadadd", function(e, data) {
			if (data.autoUpload
					|| (data.autoUpload !== false && $(this).fileupload(
							'option', 'autoUpload'))) {
				$this.fileupload('option', 'start').call($this.fileupload(), $.Event('start'));
			}
		});
		$this.find(".files").on("click", "li .preview a", function() {
			var t = this;
			var o = {"data" : []};
			$this.find(".files li .preview a").each(function(i) {
				o.data.push({
					"file_id" : $(this).attr("file_id"),
					"title" : $(this).attr("file_name"),
					"description" : null,
					"src" : cooperopcontextpath + '/rm/s/' + module + '/' + $(this).attr("file_id"), // 原图地址
					"thumb" :cooperopcontextpath + '/rm/s/' + module + '/' + $(this).attr("file_id") + 'S'
				// 缩略图地址,
				});
				if (this == t) {
					o.start = i;
				}
			});
			var toplayer = null;
			if (top.layer && top != window) {
				toplayer = top;
			} else {
				var p = window;
				while (p.parent != p && p.parent.layer) {
					p = p.parent;
				}
				toplayer = p;
			}
			toplayer.$.photos(o);
		});
		$this.find(".files").on("click", "li .tools .left", function() {
			var pl = $(this).parents("li");
			if(pl.prev()){
				pl.prev().before(pl);
				var val = "";
				$this.find(".files li .preview a").each(function(i) {
					if(val==""){
						val = $(this).attr("file_id");
					}else{
						val += ","+$(this).attr("file_id");
					}
				});
				$this.find(".imageupload_file").val(val);
			}else{
				$.message("我已经在最前面了！！！");
			}
		});
		$this.find(".files").on("click", "li .tools .right", function() {
			var pl = $(this).parents("li");
			if(pl.next()){
				pl.next().after(pl);
				var val = "";
				$this.find(".files li .preview a").each(function(i) {
					if(val==""){
						val = $(this).attr("file_id");
					}else{
						val += ","+$(this).attr("file_id");
					}
				});
				$this.find(".imageupload_file").val(val);
			}else{
				$.message("我已经在最后面了！！！");
			}
		});
		if ($this.find(".imageupload_file").val()) {
			$.ajax( {
				"async": true,
				"dataType" : "json",
				"type" : "POST",
				"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
				"cache" : false,
				"url" : cooperopcontextpath + "/rm/l/" + module + "/" + $this.find(".imageupload_file").val(),
				"timeout" : 60000,
				"data" : {sequ: $this.attr("sequ")},
				"success" : function(rtn) {
					var _v = [];
					for (var i in rtn) {
						_v.push(rtn[i].file_id);
					}
					$this.find(".imageupload_file").val(_v.join(","));
					$this.fileupload('option', 'done')
	                .call($this[0], $.Event('done'), {result: {files: rtn}});
				},
				"error" : function(XMLHttpRequest, textStatus, errorThrown) {
					if(XMLHttpRequest.status == 401){
						if(top.logindiv.hasLogin == false){
							top.logindiv.hasLogin = true;
							try{
								//增加page参数用于notlogin.jsp判断产品化的notloginforlogin.jsp
								var loginUrl = 'notlogin.jsp?model=ajax&page=' + page;
								if('undefined' !=typeof(cooperopcontextpath)){
									loginUrl = cooperopcontextpath+'/'+loginUrl;
								}
								modal(loginUrl, "请先登录",{title:'请先登录',width:550,height:420,'callback':function(){
										top.logindiv.hasLogin = false;
										if(arguments[0] == true){
											callAction(page, data, success, error);
										}
								}});
							}catch(e){
								top.logindiv.hasLogin = false;
							}
						}
						return;
					}
					var message = $.trim(XMLHttpRequest.responseText) || textStatus;
					if (!message) {
						if (XMLHttpRequest.status != 200) {
							message = "访问服务端异常，HTTP错误代码:" + XMLHttpRequest.status;
						}
						else {
							message = "访问服务端异常，错误原因:" + errorThrown.message;
						}
					}
					else if (message == "timeout")
						message = "连接服务器超时。";
					else if (message == "error"){
						message = "服务端处理异常。";
						return;
					}else if (message == "notmodified")
						message = "服务端未更新。";
					else if (message == "parsererror")
						message = "解析服务端返回信息异常。";
					$.error(message);
				}
			});
		}

		/*
		 * // Load & display existing files:
		 * $this.addClass('fileupload-processing'); $.ajax({ url :
		 * $this.attr("action"), dataType : 'json', context : $this[0]
		 * }).always(function() { $(this).removeClass('fileupload-processing');
		 * }).done(function(result) { $(this).fileupload('option',
		 * 'done').call(this, $.Event('done'), { result : result }); });
		 */
		
		if($this.attr("qrcode_content")){
			$.ajax({
				"async": true,
				"dataType" : "json",
				"type" : "POST",
				"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
				"cache" : false,
				"url" : cooperopcontextpath + "/rm/qrcode/" + module,
				"timeout" : 60000,
				"data" : {sequ: $this.attr("sequ"), qrcontent : $this.attr("qrcode_content")},
				"success" : function(rtn) {
					if(rtn){
						var _v = [];
						var rtn1 = [];
						_v.push(rtn.file_id);
						rtn1.push(rtn);
						$this.find(".imageupload_file").val(_v.join(","));
						$this.fileupload('option', 'done')
						.call($this[0], $.Event('done'), {result: {files: rtn1}});
					}
				},
				"error" : function(XMLHttpRequest, textStatus, errorThrown) {
					if(XMLHttpRequest.status == 401){
						if(top.logindiv.hasLogin == false){
							top.logindiv.hasLogin = true;
							try{
								//增加page参数用于notlogin.jsp判断产品化的notloginforlogin.jsp
								var loginUrl = 'notlogin.jsp?model=ajax&page=' + page;
								if('undefined' !=typeof(cooperopcontextpath)){
									loginUrl = cooperopcontextpath+'/'+loginUrl;
								}
								modal(loginUrl, "请先登录",{title:'请先登录',width:550,height:420,'callback':function(){
										top.logindiv.hasLogin = false;
										if(arguments[0] == true){
											callAction(page, data, success, error);
										}
								}});
							}catch(e){
								top.logindiv.hasLogin = false;
							}
						}
						return;
					}
					var message = $.trim(XMLHttpRequest.responseText) || textStatus;
					if (!message) {
						if (XMLHttpRequest.status != 200) {
							message = "访问服务端异常，HTTP错误代码:" + XMLHttpRequest.status;
						}
						else {
							message = "访问服务端异常，错误原因:" + errorThrown.message;
						}
					}
					else if (message == "timeout")
						message = "连接服务器超时。";
					else if (message == "error"){
						message = "服务端处理异常。";
						return;
					}else if (message == "notmodified")
						message = "服务端未更新。";
					else if (message == "parsererror")
						message = "解析服务端返回信息异常。";
					$.error(message);
				}
			});
		}
		
		//初始化seq
		if(pa.sequ){
			$this.attr("sequ", pa.sequ);
		}else{
			$.ajax({
				"async": true,
				"dataType" : "json",
				"type" : "POST",
				"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
				"cache" : false,
				"url" : cooperopcontextpath + "/rm/i/" + module,
				"timeout" : 60000,
				"data" : {sequ: $this.attr("sequ")},
				"success" : function(rtn) {
					$this.attr("sequ", rtn.sequid);
					$this.find(".image-add-span.mobile").on("click",function(){
						var seq = $this.attr("sequ");
						//callAction("base.mobile.reUpload",{"seq": seq}, function(result){});
						var interval = setInterval(function(){
							$.ajax({
								"async": true,
								"dataType" : "json",
								"type" : "POST",
								"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
								"cache" : false,
								"url" : cooperopcontextpath + "/rm/l/" + module + "/" + $this.find(".imageupload_file").val(),
								"timeout" : 60000,
								"data" : {sequ: $this.attr("sequ"),is_check: "Y"},
								"success" : function(rtn) {
									var valueArray = $this.find(".imageupload_file").val().split(",");
									
									var _v = $this.find(".imageupload_file").val() == ""?[] : valueArray;
									var rtn1 = [];
									for (var i in rtn) {
										var equals = false;
										for (var j = 0; j < valueArray.length; j++) {
											if (valueArray[j] == rtn[i].file_id) {
												equals = true;
												break;
											}
										}
										
										if(rtn[i]){
											if(!equals){
												_v.push(rtn[i].file_id);
												rtn1.push(rtn[i]);
											}
										}
									}
									$this.find(".imageupload_file").val(_v.join(","));
									$this.fileupload('option', 'done')
					                .call($this[0], $.Event('done'), {result: {files: rtn1}});
								},
								"error" : function(XMLHttpRequest, textStatus, errorThrown) {
									if(XMLHttpRequest.status == 401){
										if(top.logindiv.hasLogin == false){
											top.logindiv.hasLogin = true;
											try{
												//增加page参数用于notlogin.jsp判断产品化的notloginforlogin.jsp
												var loginUrl = 'notlogin.jsp?model=ajax&page=' + page;
												if('undefined' !=typeof(cooperopcontextpath)){
													loginUrl = cooperopcontextpath+'/'+loginUrl;
												}
												modal(loginUrl, "请先登录",{title:'请先登录',width:550,height:420,'callback':function(){
														top.logindiv.hasLogin = false;
														if(arguments[0] == true){
															callAction(page, data, success, error);
														}
												}});
											}catch(e){
												top.logindiv.hasLogin = false;
											}
										}
										return;
									}
									var message = $.trim(XMLHttpRequest.responseText) || textStatus;
									if (!message) {
										if (XMLHttpRequest.status != 200) {
											message = "访问服务端异常，HTTP错误代码:" + XMLHttpRequest.status;
										}
										else {
											message = "访问服务端异常，错误原因:" + errorThrown.message;
										}
									}
									else if (message == "timeout")
										message = "连接服务器超时。";
									else if (message == "error"){
										message = "服务端处理异常。";
										return;
									}else if (message == "notmodified")
										message = "服务端未更新。";
									else if (message == "parsererror")
										message = "解析服务端返回信息异常。";
									$.error(message);
								}
							});
						}, 1000);
						
						var html = [];
						html.push("<div class='form-horizontal' style='display:none'>");
						html.push("<div class='row-fluid'>");
						html.push("<div style='display:block;text-align:center'>");
						html.push("<img src='"+cooperopcontextpath + "/rm/qrcode/" + module + "/" + seq+"' width='240px;;'>");
						html.push("</div>");
						html.push("</div>");
						html.push("<div class='row-fluid'>");
						html.push("<div style='display:block;text-align:center'>");
						html.push("<p style='color: red;font-size:16px;'>上传完毕之前请不要关闭此页面，否则将可能出现上传异常！</p>");
						html.push("</div>");
						html.push("</div>");
						html.push("</div>");
						var _t = $(html.join(""));
						$this.after(_t);
						_t.ccinit();
						var o = {
								/*btn: ['上传完成']
								,btn1: function(index){ //或者使用btn2
									layer.close(index);
									_t.remove();
									window.clearInterval(interval);
								} */
						};
						o.type = 1;
						o.content = _t;
						o.title = false;
						o.area = [o.width || "35%", o.height || "60%"];
						o.cancel = function(index) {
							layer.close(index);
							_t.remove();
							window.clearInterval(interval);
					    }
						layer.open(o);
					});
				},
				"error" : function(XMLHttpRequest, textStatus, errorThrown) {
					if(XMLHttpRequest.status == 401){
						if(top.logindiv.hasLogin == false){
							top.logindiv.hasLogin = true;
							try{
								//增加page参数用于notlogin.jsp判断产品化的notloginforlogin.jsp
								var loginUrl = 'notlogin.jsp?model=ajax&page=' + page;
								if('undefined' !=typeof(cooperopcontextpath)){
									loginUrl = cooperopcontextpath+'/'+loginUrl;
								}
								modal(loginUrl, "请先登录",{title:'请先登录',width:550,height:420,'callback':function(){
										top.logindiv.hasLogin = false;
										if(arguments[0] == true){
											callAction(page, data, success, error);
										}
								}});
							}catch(e){
								top.logindiv.hasLogin = false;
							}
						}
						return;
					}
					var message = $.trim(XMLHttpRequest.responseText) || textStatus;
					if (!message) {
						if (XMLHttpRequest.status != 200) {
							message = "访问服务端异常，HTTP错误代码:" + XMLHttpRequest.status;
						}
						else {
							message = "访问服务端异常，错误原因:" + errorThrown.message;
						}
					}
					else if (message == "timeout")
						message = "连接服务器超时。";
					else if (message == "error"){
						message = "服务端处理异常。";
						return;
					}else if (message == "notmodified")
						message = "服务端未更新。";
					else if (message == "parsererror")
						message = "解析服务端返回信息异常。";
					$.error(message);
				}
			});
		}
		
		$this.attr("cinited", "cinited");
	},
	"setData_image": function(data) {
		var $this = this;
		$this.find(".imageupload_file").val(data)
		
		if ($this.find(".imageupload_file").val()) {
			$.ajax( {
				"async": true,
				"dataType" : "json",
				"type" : "POST",
				"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
				"cache" : false,
				"url" : cooperopcontextpath + "/rm/l/" + module + "/" + $this.find(".imageupload_file").val(),
				"timeout" : 60000,
				"data" : {sequ: $this.attr("sequ")},
				"success" : function(rtn) {
					var _v = [];
					var rtn1 = [];
					for (var i in rtn) {
						if(rtn[i]){
							_v.push(rtn[i].file_id);
							rtn1.push(rtn[i]);
						}
					}
					$this.find(".imageupload_file").val(_v.join(","));
					$this.fileupload('option', 'done')
	                .call($this[0], $.Event('done'), {result: {files: rtn1}});
				},
				"error" : function(XMLHttpRequest, textStatus, errorThrown) {
					if(XMLHttpRequest.status == 401){
						if(top.logindiv.hasLogin == false){
							top.logindiv.hasLogin = true;
							try{
								//增加page参数用于notlogin.jsp判断产品化的notloginforlogin.jsp
								var loginUrl = 'notlogin.jsp?model=ajax&page=' + page;
								if('undefined' !=typeof(cooperopcontextpath)){
									loginUrl = cooperopcontextpath+'/'+loginUrl;
								}
								modal(loginUrl, "请先登录",{title:'请先登录',width:550,height:420,'callback':function(){
										top.logindiv.hasLogin = false;
										if(arguments[0] == true){
											callAction(page, data, success, error);
										}
								}});
							}catch(e){
								top.logindiv.hasLogin = false;
							}
						}
						return;
					}
					var message = $.trim(XMLHttpRequest.responseText) || textStatus;
					if (!message) {
						if (XMLHttpRequest.status != 200) {
							message = "访问服务端异常，HTTP错误代码:" + XMLHttpRequest.status;
						}
						else {
							message = "访问服务端异常，错误原因:" + errorThrown.message;
						}
					}
					else if (message == "timeout")
						message = "连接服务器超时。";
					else if (message == "error"){
						message = "服务端处理异常。";
						return;
					}else if (message == "notmodified")
						message = "服务端未更新。";
					else if (message == "parsererror")
						message = "解析服务端返回信息异常。";
					$.error(message);
				}
			});
		}
	},
	"getData_image": function() {
		var $this = this;
		var d = {};
		d[$this.find(".imageupload_file").attr("name")] = $this.find(".imageupload_file").val();
		return d;
	}
});
