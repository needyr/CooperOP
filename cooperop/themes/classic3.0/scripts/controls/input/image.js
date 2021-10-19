$.fn.extend({
	"ccinit_image" : function() {
		var $this = this;
		var uploading = false;
		var _ff = $this.fileupload({
			url : cooperopcontextpath + "/rm/u/" + module ,
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
					"data" : {},
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
			var images = [];
			var current = 0;
			$this.find(".files li .preview a").each(function(i) {
				images.push({
	            	"src": cooperopcontextpath + '/rm/s/' + module + '/' + $(this).attr("file_id") + 'S',
	            	"bigsrc": cooperopcontextpath + '/rm/s/' + module + '/' + $(this).attr("file_id")
	            });
				if (this == t) {
					current = i;
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
			toplayer.$.photos(images, current);
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
				"data" : {},
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
				"data" : {},
				"success" : function(rtn) {
					console.log(rtn);
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
