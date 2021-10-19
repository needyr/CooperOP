var file_config = {
	src : document.getElementsByTagName('script')[document.getElementsByTagName('script').length - 1].src
};
file_config.redirect_url = file_config.src.substring(0, file_config.src.lastIndexOf('/') + 1) + "chohormcallback.htm";

$.fn.extend({
	"ccinit_file" : function() {
		var $this = this;
		function _initc() {
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
					maxFileSize : '文件不得大于' + $.formatfilesize(+$this.attr("maxsize") || 300000000),
					minFileSize : '文件过小'
				},
				formData : function(form) {
					var d = []; // 键值对
					// d.push({name: $chohorm.upload_files_redirectParamName, value: file_config.redirect_url});
					return d;
				},
				forceIframeTransport : true, // cross-site file
				redirectParamName : $chohorm.upload_files_redirectParamName,
				redirect : file_config.redirect_url,
				start : function(e) {
					uploading = true;
				},
				always : function(e, data) {
					uploading = false;
					if (data.result.files) {
						var v_ = $this.find(".file_file").val() ? $this.find(".file_file").val().split(/,/g) : [];
						for ( var i in data.result.files) {
							if ($.inArray(data.result.files[i].file_id, v_) == -1) {
								v_.push(data.result.files[i].file_id);
								$chohorm.addfile(data.result.files[i].file_id);
							}
						}
						$this.find(".file_file").val(v_.join(','));
					}
				},
				processalways : function(e, data) {
					if (data.files.error) {
						$.error(data.files[data.index].error);
						$this.find('.template-upload:last').remove();
					}
				},
				destroy : function(e, data) {
					var eli = data;
					$chohorm.removefile(eli.file_id, function(rtn) {
						var v_ = $this.find(".file_file").val() ? $this.find(".file_file").val().split(/,/g) : [];
						var i = $.inArray(eli.file_id, v_);
						if (i > -1) {
							v_.splice(i, 1);
						}
						$this.find(".file_file").val(v_.join(','));
						var button = $(e.currentTarget);
						button.closest('.template-download').remove();
					});
				},
				sequentialUploads : true,
				singleFileUploads : true,
				limitMultiFileUploads : 1,
				maxFileSize : +$this.attr("maxsize") || 300000000,
				minFileSize : 0,
				//maxNumberOfFiles : +$this.attr("maxlength") || 2,
				maxNumberOfFiles : 1,
				autoUpload : $this.attr("autoupload") == "true",
				disableImageResize : /Android(?!.*Chrome)|Opera/.test(window.navigator.userAgent),
				acceptFileTypes : $this.attr("filetypes") ? new RegExp($this.attr("filetypes"), "i") : undefined,
				previewMaxWidth : 40,
				previewMaxHeight : 40,
				disableImagePreview : true,
				disableAudioPreview : true,
				disableVideoPreview : true,
				uploadTemplateId : null,
				downloadTemplateId : null,
				uploadTemplate : function(o) {
					var row = [];
					for (var i = 0, file; file = o.files[i]; i++) {
						var html = [];
						html.push('<tr class="template-upload fade" file_name="' + file.name + '">');
						html
								.push('<td style="width:40px;background: url(' + cooperopcontextpath
										+ '/theme/img/filetypes/default_icon.gif) no-repeat center center;">');
						html.push('</td>');
						html.push('<td style="text-align:left;">');
						html.push('<span class="name">' + file.name + '</span>');
						html.push('<strong class="error text-danger"></strong>');
						html.push('</td>');
						html.push('<td style="position: relative;width:160px;">');
						html.push('<p class="size">Processing...</p>');
						html.push('<div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">');
						html.push('<div class="progress-bar progress-bar-success" style="width:0%;"></div>');
						html.push('</div>');
						html.push('</td>');
						html.push('<td style="width:50px;">');
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
						html.push('</td>');
						html.push('</tr>');
						row.push(html.join(""));
					}
					return row;
				},
				downloadTemplate : function(o) {
					var row = [];
					for (var i = 0, file; file = o.files[i]; i++) {
						if (file.error) {
							continue;
						}
						var html = [];
						html.push('<tr class="template-download fade"  file_id="' + file.file_id + '" file_name="' + file.file_name + '">');
						html
								.push('<td style="width:40px;background: url(' + cooperopcontextpath
										+ '/theme/img/filetypes/default_icon.gif) no-repeat center center;">');
						html.push('</td>');
						html.push('<td style="text-align:left;">');
						html.push('<span class="name">');
						if (file.file_id) {
							html.push('<a href="javascript:void(0);" file_url="' + $chohorm.fileurl(file.file_id) + '" title="' + decodeURI(file.file_name)
									+ '" onclick="ccfile_show_file($(this));">' + decodeURI(file.file_name) + '</a>');
							if ($this.attr("downloadable") != "false") {
								html.push('<a href="' + $chohorm.downloadurl(file.file_id) + '" title="点击下载" download="' + file.file_name
										+ '" style="margin-left: 10px"><i class="fa fa-download"></i></a>');
							}
						} else {
							html.push(file.name);
						}
						html.push('</span>');
						if (file.error) {
							html.push('<strong class="error text-danger">' + file.error + '</strong>');
						}
						html.push('</td>');
						html.push('<td style="position: relative;width:160px;">');
						if (file.file_id) {
							html.push('<p class="size">' + o.formatFileSize(+file.file_size) + '</p>');
						} else {
							html.push('<p class="size">' + o.formatFileSize(+file.size) + '</p>');
						}
						html.push('</td>');
						html.push('<td style="width:50px;">');
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
						html.push('</td>');
						html.push('</tr>');
						row.push(html.join(""));
					}
					return row;
				}
			});
			_ff.bind("fileuploadadd", function(e, data) {
				if (data.autoUpload || (data.autoUpload !== false && $(this).fileupload('option', 'autoUpload'))) {
					$this.fileupload('option', 'start').call($this.fileupload(), $.Event('start'));
				}
			});
	
			if ($this.find(".file_file").val()) {
				$chohorm.listfiles($this.find(".file_file").val(), function(rtn) {
					var _v = [];
					for ( var i in rtn) {
						_v.push(rtn[i].file_id);
					}
					$this.find(".file_file").val(_v.join(","));
					$this.fileupload('option', 'done').call($this[0], $.Event('done'), {
						result : {
							files : rtn
						}
					});
				});
			}
	
			/*
			 * // Load & display existing files: $this.addClass('fileupload-processing'); $.ajax({ url : $this.attr("action"), dataType : 'json', context : $this[0]
			 * }).always(function() { $(this).removeClass('fileupload-processing'); }).done(function(result) { $(this).fileupload('option', 'done').call(this,
			 * $.Event('done'), { result : result }); });
			 */
	
			$this.find(".start").click(function() {
				$this.fileupload('option', 'start').call($this.fileupload(), $.Event('start'));
			});
			$this.attr("cinited", "cinited");
		}
		$.initRM(_initc);
	},
	"setData_file" : function(data) {
		var $this = this;
		$this.find(".file_file").val(data)

		if ($this.find(".file_file").val()) {
			$chohorm.listfiles($this.find(".file_file").val(), function(rtn) {
				var _v = [];
				for ( var i in rtn) {
					_v.push(rtn[i].file_id);
				}
				$this.find(".file_file").val(_v.join(","));
				$this.fileupload('option', 'done').call($this[0], $.Event('done'), {
					result : {
						files : rtn
					}
				});
			});
		}
	},
	"getData_file" : function() {
		var $this = this;
		var d = {};
		d[$this.find(".file_file").attr("name")] = $this.find(".file_file").val();
		return d;
	}
});

function ccfile_show_file(jobj) {
	var fname = jobj.attr("title");
	var url = jobj.attr("file_url");
	var imagep = /(\.|\/)(gif|jpe?g|png|bmp)$/i;
	var videop = /(\.|\/)(ogg|mpeg4|webm|mp4)$/i;
	var flvp = /(\.|\/)(flv)$/i;
	if (imagep.test(fname.toLowerCase())) {
		var $this = jobj.parents("div[ctype='file']:eq(0)");
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
						var v_ = $this.find(".file_file").val() ? $this.find(".file_file").val().split(/,/g) : [];
						var i = $.inArray(data.file_id, v_);
						if (i > -1) {
							v_.splice(i, 1);
						}
						$this.find(".file_file").val(v_.join(','));
						$this.find(".template-download a[data-file_id='" + data.file_id + "']").parent().parent().remove();
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
		$this.find(".files tr").each(function(i) {
			if ($(this).attr("file_id") && imagep.test($(this).attr("file_name").toLowerCase())) {
				o.data.push({
					"file_id" : $(this).attr("file_id"),
					"title" : $(this).attr("file_name"),
					"description" : null,
					"src" : $chohorm.imageurl($(this).attr("file_id")), // 原图地址
					"thumb" : $chohorm.thumburl($(this).attr("file_id"))
				});
				if (this == jobj.parents("tr")[0]) {
					o.start = i;
				}
			}
		});
		$.photos(o);
	} else if (videop.test(fname.toLowerCase())) {
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
		toplayer.layer.open({
			type : 1,
			title : false,
			shade : 0.5,
			closeBtn : 0,
			shadeClose : true,
			area : [
					"650px", "490px"
			],
			zIndex : toplayer.layer.zIndex, // 重点1
			content : '<video src="' + url + '" preload="auto" autoplay controls style="width:100%"></video>'
		});
	} else if (flvp.test(fname.toLowerCase())) {
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
		toplayer.layer
				.open({
					type : 1,
					title : false,
					shade : 0.5,
					closeBtn : 0,
					shadeClose : true,
					area : [
							"650px", "490px"
					],
					zIndex : toplayer.layer.zIndex, // 重点1
					content : [
							'<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" width="640" height="480">',
							'<param name="movie" value="' + cooperopcontextpath + '/theme/scripts/controls/Flvplayer.swf" />',
							'<param name="quality" value="high" />',
							'<param name="allowFullScreen" value="true" />',
							'<param name="FlashVars" value="vcastr_file=' + url + '&LogoText=' + fname + '&IsAutoPlay=1" />',
							'<embed src="' + cooperopcontextpath + '/theme/scripts/controls/Flvplayer.swf" allowfullscreen="true" ',
							'flashvars="vcastr_file=' + url + '&LogoText=' + fname
									+ '&IsAutoPlay=1" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" ',
							'type="application/x-shockwave-flash" width="640" height="480"></embed>', '</object>'
					].join('')
				});
	} else {
		window.open("/convertUtil" + url, "_blank");
	}
}
