/*var image_config = {
	src : rm_url+'/jssdk/chohorm.js'
};
image_config.redirect_url = image_config.src.substring(0, image_config.src.lastIndexOf('/') + 1) + "chohormcallback.htm";*/
var image_config = {
	src : document.getElementsByTagName('script')[document.getElementsByTagName('script').length - 1].src
};
image_config.redirect_url = image_config.src.substring(0, image_config.src.lastIndexOf('/') + 1) + "chohormcallback.htm";
$.fn.extend({
			"ccinit_image" : function() {
				var $this = this;
				function _initc() {
					$this.data("fileData", {});
					var uploading = false;
					var _ff = $this.fileupload({
								url : $chohorm.upload_files_url,
								type : "POST",
								dataType : "json",
								pasteZone : $this, // jQuery Object Chrome Only
								fileInput : $this.find(":file"), // jQuery Object Chrome Only
								disableImageResize : false,
								paramName : "_ccfile[]",
								formAcceptCharset : "utf-8",
								messages : {
									maxNumberOfFiles : '文件已超出限定数量',
									acceptFileTypes : '文件类型不允许在此处上传',
									maxFileSize : '文件不得大于' + $.formatfilesize(+$this.attr("maxsize") || 5000000),
									minFileSize : '文件过小'
								},
								formData : function(form) {
									var d = []; // 键值对
									// d.push({name: $chohorm.upload_files_redirectParamName, value: image_config.redirect_url});
									return d;
								},
								forceIframeTransport : true, // cross-site file
								redirectParamName : $chohorm.upload_files_redirectParamName,
								redirect : image_config.redirect_url,
								start : function(e) {
									uploading = true;
								},
								always : function(e, data) {
									uploading = false;
									if (data.result && data.result.files) {
										var v_ = $this.find(".imageupload_file").val() ? $this.find(".imageupload_file").val().split(/,/g) : [];
										for ( var i in data.result.files) {
											if ($.inArray(data.result.files[i].file_id, v_) == -1) {
												v_.push(data.result.files[i].file_id);
												$chohorm.addfile(data.result.files[i].file_id);
												$this.data("fileData")[data.result.files[i].file_id] = data.result.files[i];
											}
										}
										$this.find(".imageupload_file").val(v_.join(','));
										//$this.trigger("change");
									} else if ($("iframe").length > 0) {
										$.error("图片上传失败");
										$this.find('.template-upload:last').remove();
										if ($this.find(".files > li").length >= data.maxNumberOfFiles) {
											$this.find(".fileupload-buttonbar").hide();
										} else {
											$this.find(".fileupload-buttonbar").show();
										}
									}
								},
								processalways : function(e, data) {
									if (data.files.error) {
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
								destroy : function(e, data) {
									var eli = data;
									$chohorm.removefile(eli.file_id, function(rtn) {
										var v_ = $this.find(".imageupload_file").val() ? $this.find(".imageupload_file").val().split(/,/g) : [];
										var i = $.inArray(eli.file_id, v_);
										if (i > -1) {
											v_.splice(i, 1);
										}
										$this.find(".imageupload_file").val(v_.join(','));
										delete $this.data("fileData")[eli.file_id];
										var button = $(e.currentTarget);
										button.closest('.template-download').remove();
										$this.find(".fileupload-buttonbar").show();
									});
								},
								sequentialUploads : true,
								singleFileUploads : true,
								limitMultiFileUploads : 1,
								maxFileSize : +$this.attr("maxsize") || 5000000,
								minFileSize : 0,
								maxNumberOfFiles : +$this.attr("maxlength") || 4,
								autoUpload : true,
								disableImageResize : /Android(?!.*Chrome)|Opera/.test(window.navigator.userAgent),
								acceptFileTypes : $this.attr("filetypes") ? new RegExp($this.attr("filetypes"), "i") : /(\.|\/)(gif|jpe?g|png|bmp)$/i,
								previewMaxWidth : +$this.attr("previewMaxWidth") || 126,
								previewMaxHeight : +$this.attr("previewMaxHeight") || 126,
								disableImagePreview : false,
								disableAudioPreview : true,
								disableVideoPreview : true,
								uploadTemplateId : null,
								downloadTemplateId : null,
								uploadTemplate : function(o) {
									if ($this.find(".files > li").length + o.files.length >= o.options.maxNumberOfFiles) {
										$this.find(".fileupload-buttonbar").hide();
									} else {
										$this.find(".fileupload-buttonbar").show();
									}
									var row = [];
									for (var i = 0, file; file = o.files[i]; i++) {
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
									var df = 0;
									var row = [];
									for (var i = 0, file; file = o.files[i]; i++) {
										if (file.error) {
											continue;
										}
										df++;
										var html = [];
										html.push('<li class="template-download fade">');
										if (file.error) {
											html.push('<div class="error text-danger">' + file.error + '</div>');
										}
										html.push('<div class="preview">');
										if (file.file_id) {
											html.push('<a href="javascript:void(0);" file_id="' + file.file_id + '" file_name="' + file.file_name
													+ '" data-gallery style="background-image: url(' + $chohorm.thumburl(file.file_id) + ')"></a>');
										}
										html.push('</div>');
										if ($this.attr("islabel") != "true" && $this.attr("deleteable") != "false") {
											if (file.file_id) {
												html.push('<a class="font-red delete" data-file_id="' + file.file_id + '" title="删除"');
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
									if ($this.find(".files > li").length + df >= o.options.maxNumberOfFiles) {
										$this.find(".fileupload-buttonbar").hide();
									} else {
										$this.find(".fileupload-buttonbar").show();
									}
									return row;
								}
							});
					_ff.bind("fileuploadadd", function(e, data) {
						if (data.autoUpload || (data.autoUpload !== false && $(this).fileupload('option', 'autoUpload'))) {
							$this.fileupload('option', 'start').call($this.fileupload(), $.Event('start'));
						}
					});
					$this.find(".files").on(
							"click",
							"li .preview a",
							function() {
								var t = this;
								var o = {
									"title" : null, // 相册标题
									"start" : 0, // 初始显示的图片序号，默认0
									"editable" : $this.attr("islabel") != "true",
									"deleteable" : $this.attr("islabel") != "true" && $this.attr("deleteable") == "true",
									"save" : $this.attr("islabel") != "true" ? function(data, callback) {
										if ($this.attr("islabel") != "true") {
											var rotate = data.rotate % 360;
											if (rotate < 0)
												rotate = 360 + rotate;
											$chohorm.rotatefile(data.file_id, rotate, function(rtn) {
												var timg = new Image();
												timg.src = data.thumb;
												$(timg).on(
														"load",
														function() {
															$this.find(".template-download > .preview > a[file_id='" + data.file_id + "']").css("background-image",
																	"url(" + timg.src + "?t=" + new Date().getTime() + ")");
														});
												if (callback) {
													callback();
												}
											}, function(rtn) {
												if (callback) {
													callback(rtn);
												}
											});
										}
									} : undefined,
									"delete" : $this.attr("islabel") != "true" && $this.attr("deleteable") == "true" ? function(data, callback) {
										if ($this.attr("islabel") != "true" && $this.attr("deleteable") == "true") {
											$chohorm.removefile(data.file_id, function(rtn) {
												var v_ = $this.find(".imageupload_file").val() ? $this.find(".imageupload_file").val().split(/,/g) : [];
												var i = $.inArray(data.file_id, v_);
												if (i > -1) {
													v_.splice(i, 1);
												}
												$this.find(".imageupload_file").val(v_.join(','));
												delete $this.data("fileData")[data.file_id];
												$this.find(".template-download > .preview > a[file_id='" + data.file_id + "']").parent().parent().remove();
												$this.find(".fileupload-buttonbar").show();
												if (callback) {
													callback();
												}
											}, function(rtn) {
												if (callback) {
													callback(rtn);
												}
											});
										}
									} : undefined,
									"data" : []
								// 相册包含的图片，数组格式
								};
								$this.find(".files li .preview a").each(function(i) {
									o.data.push({
										"file_id" : $(this).attr("file_id"),
										"title" : $(this).attr("file_name"),
										"description" : null,
										"src" : $chohorm.imageurl($(this).attr("file_id")), // 原图地址
										"thumb" : $chohorm.thumburl($(this).attr("file_id"))
									// 缩略图地址,
									});
									if (this == t) {
										o.start = i;
									}
								});
								$.photos(o);
							});
	
					if ($this.find(".imageupload_file").val()) {
						$chohorm.listfiles($this.find(".imageupload_file").val(), function(rtn) {
							var _v = [];
							for ( var i in rtn) {
								_v.push(rtn[i].file_id);
								$this.data("fileData")[rtn[i].file_id] = rtn[i];
							}
							$this.find(".imageupload_file").val(_v.join(","));
							$this.fileupload('option', 'done').call($this[0], $.Event('done'), {
								result : {
									files : rtn
								}
							});
						});
					}
	
					/*
					 * // Load & display existing files: $this.addClass('fileupload-processing'); $.ajax({ url : $this.attr("action"), dataType : 'json', context :
					 * $this[0] }).always(function() { $(this).removeClass('fileupload-processing'); }).done(function(result) { $(this).fileupload('option',
					 * 'done').call(this, $.Event('done'), { result : result }); });
					 */
	
					/**
					 * 手机上传
					 */
					$this
							.find(".image-add-span.mobile")
							.click(
									function() {
										$this.attr("seqid", $md5.hex_md5(location.href + "?" + location.queryString + "~~" + $.generateId()));
										var html = [];
										html.push("<div class='form-horizontal' style='display:none;'>");
										html.push("<div class='row-fluid'>");
										html.push("<div style='display:block;text-align:center;margin-top:10px;'>");
										html.push("<img src='" + $chohorm.mobile_barcode_url + "&seqid=" + $this.attr("seqid") + "&maxlength="
												+ ((+$this.attr("maxlength") || 4) - $this.find(".files > li").length) + "' width='240px'>");
										html.push("</div>");
										html.push("</div>");
										html.push("<div class='row-fluid'>");
										html.push("<div style='display:block;text-align:center'>");
										html
												.push("<div style='font-size:16px;'>请使用手机扫码上传图片</div><div style='color:#666;margin: 3px 0px;'>建议使用<i class='fa fa-weixin' style='color: green;margin: 0px 2px 0px 4px;'></i><font style='color:green;margin: 0px 4px 0px 2px;'>微信</font>扫一扫</div><div style='color: red;'>手机未完成上传时请勿关闭此窗口</div>");
										html.push("</div>");
										html.push("</div>");
										html.push("</div>");
										var _t = $(html.join(""));
										$("body").append(_t);
										_t.ccinit();
	
										var _li = layer.open({
											id : $this.attr("seqid"),
											type : 1,
											content : _t,
											title : false,
											area : [
													"280px", "350px"
											],
											cancel : function(index) {
												$chohorm.callajax($chohorm.server_url + "/mr", {seqid: $this.attr("seqid")}, function(rtn) {
												});
												layer.close(index);
												_t.remove();
												window.clearInterval(interval);
											}
										});
	
										var interval = setInterval(function() {
											$chohorm.callajax($chohorm.server_url + "/ms", {seqid: $this.attr("seqid")}, function(rtn) {
												if (rtn.flag == "nodata") {
													window.clearInterval(interval);
													$.message("二维码已过期！", function() {
														layer.close(_li);
														_t.remove();
													});
												} else if (rtn.flag == "cancel") {
													$chohorm.callajax($chohorm.server_url + "/mr", {seqid: $this.attr("seqid")}, function(rtn) {
													});
													window.clearInterval(interval);
													$.warning("手机扫码上传图片已被手机端取消！", function() {
														layer.close(_li);
														_t.remove();
													});
												} else if (rtn.flag == "success") {
													window.clearInterval(interval);
													layer.close(_li);
													_t.remove();
													$chohorm.callajax($chohorm.server_url + "/ml", {seqid: $this.attr("seqid")}, function(rtn) {
														var _v = $this.find(".imageupload_file").val() ? $this.find(".imageupload_file").val().split(/,/g) : [];
														for ( var i in rtn) {
															_v.push(rtn[i].file_id);
															$this.data("fileData")[rtn[i].file_id] = rtn[i];
														}
														$this.find(".imageupload_file").val(_v.join(","));
														$this.fileupload('option', 'done').call($this[0], $.Event('done'), {
															result : {
																files : rtn
															}
														});
														if ($this.find(".files > li").length >= (+$this.attr("maxlength")) || 4) {
															$this.find(".fileupload-buttonbar").hide();
														} else {
															$this.find(".fileupload-buttonbar").show();
														}
													});
												}
											});
										}, 1000);
									});
	
					/**
					 * 拍照上传
					 */
					$this
							.find(".image-add-span.camera")
							.click(
									function() {
										var devices = [];
										var default_device = $.cookie("$app_camera_capture");
	
										var initpage = function() {
											if (devices.length == 0) {
												$.warning("读取摄像头信息失败！<br/>摄像头未找到。");
											}
											var html = [];
											html.push("<div class='form-horizontal' style='display:none;'>");
											html.push("<div class='row-fluid'>");
											html.push('<div class="cols4" crid="" style="padding-left: 100px;">');
											html.push('<label class="control-label" title="摄像头" style="width: 90px;">摄像头</label>');
											html.push('<div class="control-content">');
											html.push('<select ctype="select" class="form-control " value="' + (default_device || devices[0].deviceId) + '">');
											for ( var i in devices) {
												html.push('<option value="' + devices[i].deviceId + '"');
												if (devices[i].deviceId == default_device) {
													html.push(' selected');
												}
												html.push('>' + (devices[i].label || devices[i].deviceId) + '</option>');
											}
											html.push("</select>");
											html.push("</div>");
											html.push("</div>");
											html.push("</div>");
											html.push("<div class='row-fluid'>");
											html.push('<div style="width:640px;height:480px;margin:0 auto">');
											html.push('<video src="" style="width:2560px;height:1920px;zoom:0.25;background: #000;" autoplay></video>');
											html.push('<canvas width="2560" height="1920" style="display:none;"></canvas>');
											html.push("</div>");
											html.push("</div>");
											html.push("<div class='row-fluid'>");
											html
													.push("<a href='javascript:void(0);' style='display: block;width: 64px;height: 64px;margin: 20px auto;background: #3d9421;border-radius: 50%!important;line-height: 64px;color: #FFF;text-align:center;'><i class='icon-camera' style='line-height: 64px;font-size: 28px;' title='拍照'></i></a>");
											html.push("</div>");
											html.push("</div>");
											var _t = $(html.join(""));
											$("body").append(_t);
											_t.ccinit();
	
											var dataURLtoBlob = function(dataurl) {
												var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1], bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(
														n);
												while (n--) {
													u8arr[n] = bstr.charCodeAt(n);
												}
												return new Blob([
													u8arr
												], {
													type : mime
												});
											}
	
											_t.find("a").on("click", function() {
												$.mask("正在上传...");
												_t.find("canvas")[0].getContext('2d').drawImage(_t.find("video")[0], 0, 0, 2560, 1920);
												var blob = dataURLtoBlob(_t.find("canvas")[0].toDataURL("image/png", 1));// 1: type
												// 可选
												// 图片格式，默认为
												// image/png
												// 2:
												// encoderOptions
												// 可选
												// 在指定图片格式为
												// image/jpeg
												// 或
												// image/webp的情况下，可以从
												// 0 到 1
												// 的区间内选择图片的质量。如果超出取值范围，将会使用默认值
												// 0.92。其他参数会被忽略。
												$chohorm.uploadBlob(blob, function(rtn) {
													var _v = $this.find(".imageupload_file").val() ? $this.find(".imageupload_file").val().split(/,/g) : [];
													for ( var i in rtn.files) {
														_v.push(rtn.files[i].file_id);
														$this.data("fileData")[rtn.files[i].file_id] = rtn[i];
													}
													$this.find(".imageupload_file").val(_v.join(","));
													$this.fileupload('option', 'done').call($this[0], $.Event('done'), {
														result : {
															files : rtn.files
														}
													});
													if ($this.find(".files > li").length >= (+$this.attr("maxlength")) || 4) {
														$this.find(".fileupload-buttonbar").hide();
														stopevent();
														layer.close(_li);
														_t.remove();
													} else {
														$this.find(".fileupload-buttonbar").show();
													}
												});
											});
	
											var stopevent = function() {
												if (window.stream) {
													window.stream.getTracks().forEach(function(track) {
														track.stop();
													});
												}
												_t.find("video")[0].pause();
												_t.find("video").attr("src", "");
											};
	
											var start = function() {
												stopevent();
												if (_t.find("select").val()) {
													var mediaConfig = {
														video : {
															width : 640,
															height : 480,
															deviceId : _t.find("select").val()
														}
													};
													var errBack = function(e) {
														console.log('An error has occurred!', e)
													};
	
													// Put video listeners into place
													if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
														navigator.mediaDevices.getUserMedia(mediaConfig).then(function(stream) {
															try{
																_t.find("video").attr("src", window.URL.createObjectURL(stream));
															}catch (e) {
																_t.find("video")[0].srcObject = stream;
															}
															window.stream = stream;
														});
													}
												} else {
													_t.find("video")[0].pause();
													_t.find("video").attr("src", "");
												}
											}
	
											_t.find("select").change(function() {
												$.cookie("$app_camera_capture", $(this).val(), {
													expires : 365,
													path : "/"
												});
												start();
											});
	
											_t.find("select").change();
	
											var _li = layer.open({
												id : $this.attr("seqid"),
												type : 1,
												content : _t,
												title : false,
												area : [
														"680px", "660px"
												],
												cancel : function(index) {
													stopevent();
													layer.close(index);
													_t.remove();
												}
											});
										};
	
										if (navigator.mediaDevices && navigator.mediaDevices.enumerateDevices) {
											navigator.mediaDevices.enumerateDevices().then(function(MediaDeviceInfo) {
												$("#videoselect1 option").remove();
												$("#videoselect2 option").remove();
												for ( var i in MediaDeviceInfo) {
													if (MediaDeviceInfo[i].kind == "videoinput") {
														devices.push(MediaDeviceInfo[i]);
													}
												}
												initpage();
											});
										} else {
											$.warning("读取摄像头信息失败！<br/>请使用Chrome浏览器或使用Chrome浏览器核心的浏览器。");
										}
	
									});
	
					$this.attr("cinited", "cinited");
					$this.trigger("cinited");
				}
				$.initRM(_initc);
			},
			"setData_image" : function(data) {
				var $this = this;
				$this.find(".imageupload_file").val(data)

				if ($this.find(".imageupload_file").val()) {
					$chohorm.listfiles($this.find(".imageupload_file").val(), function(rtn) {
						var _v = [];
						for ( var i in rtn) {
							_v.push(rtn[i].file_id);
							$this.data("fileData")[rtn[i].file_id] = rtn[i];
						}
						$this.find(".imageupload_file").val(_v.join(","));
						$this.fileupload('option', 'done').call($this[0], $.Event('done'), {
							result : {
								files : rtn
							}
						});
					});
				}
			},
			"getData_image" : function() {
				var $this = this;
				var d = {};
				d[$this.find(".imageupload_file").attr("name")] = $this.find(".imageupload_file").val();
				return d;
			},
			"fileList_image": function() {
				var $this = this;
				var r = [];
				var a = $this.find(".imageupload_file").val().split(/,/g) || [];
				for ( var i in a) {
					r.push($this.data("fileData")[a[i]]);
				}
				return r;
			}
		});
