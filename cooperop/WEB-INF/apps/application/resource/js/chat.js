var getLastPostPos = function() {
    var height = 0;
    while ($(".message-send-content").find(".post").length > 50) {
    	$(".message-send-content").find(".post:first").remove();
    }
    $(".message-send-content").find(".post").each(function() {
        height = height + $(this).outerHeight();
    });

    return height;
};   
var contacter_users = function() {
	$(".list-users").html("");
	var data = {};
	data["type"] = $(".message-send-form").attr("data-taget");
	data["id"] = $(".message-send-form").attr("data-send_to");
	$.call("application.contacter.contacter_users", data, function(rtn) {
		rtn = rtn.resultset || [];
		var html = [];
		for (var i in rtn) {
    		html.push('<li class="media">');
    		html.push('<a herf="javascript:void(0);" mtype="U" mid="' + rtn[i].id + '" mname="' + rtn[i].name + '">')
    		html.push('	<span class="media-object">');
			if (rtn[i].avatar) {
				html.push('	<i class="ava" style="background-image:url(' + cooperopcontextpath + '/rm/s/application/' + rtn[i].avatar + 'S);"></i> ');
			} else {
				html.push('	<i class="fa fa-user"></i> ');
			}
			html.push('	</span> ');
    		html.push('<span class="media-body">');
			html.push('  <h4 class="media-heading">' + rtn[i].name + '</h4>');
			html.push('</span></a></li>');
		}
		$(".list-users").html(html.join(""));
	}, undefined, {nomask: true});
}
var message_cycle = 10000;
var message_new = function(noread) {
	var data = {};
	data["target"] = $(".message-send-form").attr("data-taget");
	data["send_to"] = $(".message-send-form").attr("data-send_to");
	if (noread) {
		data["noread"] = "true";
	} else {
		data["start"] = 1;
		data["limit"] = 20;
	}
	$.call("application.message.listnew", data, function(rtn) {
		rtn = rtn.resultset || [];
		if (rtn.length > 0) {
			var html = [];
			for (var i = rtn.length - 1; i >= 0; i --) {
	    		html.push('<div class="post ' + rtn[i].send_type + '">');
				if (rtn[i].avatar) {
					html.push('	<img class="avatar" src="' + cooperopcontextpath + '/rm/s/application/' + rtn[i].avatar + 'S" alt="..." /> ');
				} else {
					html.push('	<span class="avatar"><i class="fa fa-user"></i></span> ');
				}
	    		html.push('<div class="message ' + (rtn[i].type == 'F' ? 'filemessage' : (rtn[i].type == 'I' ? 'imagemessage' : '')) + '">');
				html.push('  <span class="arrow"></span>');
				html.push('  <span class="name">' + rtn[i].system_user_name + '</span>');
				html.push('  <span class="datetime">' + rtn[i].send_time_label_sort + '</span>');
				html.push('  <span class="body">');
				if (rtn[i].type == 'F') {
					html.push('  <div class="filecontent">');
	    			html.push('  	<i class="fa fa-file-o"></i>');
	    			html.push('  	<div class="filename">');
	            	html.push('<a href="' + cooperopcontextpath + '/rm/d/' + module + '/' + rtn[i].content + '" title="' + rtn[i].file_name + '" download="' + rtn[i].file_name + '" >' + rtn[i].file_name + '</a>');
	    			html.push('  	</div>');
	    			html.push('  	<div class="filesize">');
					html.push($.formatfilesize(+rtn[i].file_size));
	    			html.push('  	</div>');
	    			html.push('  </div>');
				} else if (rtn[i].type == 'I'){
					html.push('		<div class="preview">');
	            	html.push('		<img src="' + cooperopcontextpath + '/rm/s/' + module + '/' + rtn[i].content + 'S" bigsrc="' + cooperopcontextpath + '/rm/s/' + module + '/' + rtn[i].content + '">');
					html.push('		</div>');
				} else {
					html.push(rtn[i].content);
				}
				html.push('</span></div></div>');
			}
			$(".message-send-content").append(html.join(""));
			$(".message-send-content").slimScroll({
	            scrollTo: getLastPostPos()
	        });
		}
	}, undefined, {nomask: true});
}
var isIE8 = !!navigator.userAgent.match(/MSIE 8.0/);
var initScrollBar = function() {
	if ($(".message-send-content").is("[initialized]")) {
		$(".message-send-content").removeAttr("initialized");
        $(".message-send-content").removeAttr("style");
		$(".message-send-content").slimScroll({
            wrapperClass: 'slimScrollDiv',
            destroy: true
        });
	}
	if ($(".message-send-users").is("[initialized]")) {
		$(".message-send-users").removeAttr("initialized");
        $(".message-send-users").removeAttr("style");
		$(".message-send-users").slimScroll({
            wrapperClass: 'slimScrollDiv',
            destroy: true
        });
	}
    $(".message-send-content").slimScroll({
        allowPageScroll: true, // allow page scroll when the element scroll is ended
        size: '7px',
        color: '#bbb',
        wrapperClass: 'slimScrollDiv',
        railColor: '#eaeaea',
        position: 'right',
        height: $(document).height() - $(".message-send-content").offset().top * 2 - $(".message-send-toolbar").outerHeight() - $(".message-send-form").outerHeight(),
        alwaysVisible: false,
        railVisible: false,
        disableFadeOut: true
    });
    $(".message-send-content").attr("initialized", "initialized");
    $(".message-send-users").slimScroll({
        allowPageScroll: true, // allow page scroll when the element scroll is ended
        size: '7px',
        color: '#bbb',
        wrapperClass: 'slimScrollDiv',
        railColor: '#eaeaea',
        position: 'right',
        height: $(".col-md-9").height() - $(".message-send-users-toolbar").outerHeight(),
        alwaysVisible: false,
        railVisible: false,
        disableFadeOut: true
    });
    $(".message-send-users").attr("initialized", "initialized");
}
var resize;
if (isIE8) {
       var currheight;
       $(window).resize(function() {
           if (currheight == document.documentElement.clientHeight) {
               return; //quite event since only body resized not window.
           }
           if (resize) {
               clearTimeout(resize);
           }
           resize = setTimeout(function() {
           	initScrollBar();
           }, 50); // wait 50ms until window resize finishes.                
           currheight = document.documentElement.clientHeight; // store last body client height
       });
   } else {
       $(window).resize(function() {
           if (resize) {
               clearTimeout(resize);
           }
           resize = setTimeout(function() {
           	initScrollBar();
           }, 50); // wait 50ms until window resize finishes.
       });
   }
$(document).ready(function() {
	initScrollBar();
	$(".message-send-content").on("click", ".message .body img", function() {
		var t = this;
		var images = [];
		var current = 0;
		$(".message-send-content").find(".message .body img").each(function(i) {
			images.push({
		            	"src": $(this).attr("src"),
		            	"bigsrc": $(this).attr("bigsrc") || $(this).attr("src")
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
	message_new(false);
	contacter_users();
	setInterval(function() {
		message_new(true);
	}, message_cycle);
	var uploading = false;
	var _ff = $(".sendfile").fileupload({
		url : cooperopcontextpath + "/rm/u/" + module ,
		type : "POST",
		dataType : "json",
		pasteZone : $(".message-send-content"), // jQuery Object Chrome Only
		fileInput : $(".sendfile").find(":file"), // jQuery Object Chrome Only
		disableImageResize : false,
		paramName : "_ccfile[]",
		formAcceptCharset : "utf-8",
		formData: function(form) {
			return {};
		},
		start: function(e) {
			uploading = true;
		},
		always: function(e, data) {
			if (data.result.files) {
				for (var i in data.result.files) {
					var data = {content: data.result.files[i].file_id};
			    	data["target"] = $(".message-send-form").attr("data-taget");
			    	data["send_to"] = $(".message-send-form").attr("data-send_to");
			    	data["type"] = "F";
			    	if(data.content){
			    		$.call("application.message.send", data, function(rtn) {
			    			uploading = false;
			        	}, undefined, {nomask: true});	
			    	}
				}
			}
		},
		sequentialUploads: true, 
		singleFileUploads : true,
		limitMultiFileUploads : 1,
		maxFileSize : $(".sendfile").attr("maxsize") || "50000000",
		minFileSize : 0,
		maxNumberOfFiles: 9999999,
		forceIframeTransport : false, // cross-site file
		autoUpload : true,
		disableImageResize : /Android(?!.*Chrome)|Opera/
				.test(window.navigator.userAgent),
		// acceptFileTypes : /(\.|\/)(gif|jpe?g|png)$/i,
		previewMaxWidth: 40,
		previewMaxHeight: 40,
		disableImagePreview: true,
		disableAudioPreview: true,
		disableVideoPreview: true,
		uploadTemplateId : null,
		downloadTemplateId : null,
		filesContainer: $(".message-send-content"),
		uploadTemplate : function(o) {
			var row = [];
			for (var i=0, file; file=o.files[i]; i++) {
				var html = [];
        		html.push('<div class="post from">');
				if ($(".message-send-form").attr("data-avatar")) {
					html.push('	<img class="avatar" src="' + cooperopcontextpath + '/rm/s/application/' + $(".message-send-form").attr("data-avatar") + 'S" alt="..." /> ');
				} else {
					html.push('	<span class="avatar"><i class="fa fa-user"></i></span> ');
				}
        		html.push('	<div class="message filemessage">');
    			html.push('  <span class="arrow"></span>');
    			html.push('  <span class="name">' + $(".message-send-form").attr("data-system_user_name") + '</span>');
    			html.push('  <span class="datetime">' + $.formatdate(new Date(), 'HH:mm') + '</span>');
    			html.push('  <span class="body">');
    			html.push('  		<div class="filecontent">');
    			html.push('  			<i class="fa fa-file-o"></i>');
    			html.push('  			<div class="filename">');
    			html.push(file.name);
    			html.push('  			</div>');
    			html.push('  			<div class="filesize">');
    			html.push(o.formatFileSize(+file.size));
    			html.push('  			</div>');
    			html.push('  		</div>');
				html.push('  	<div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">');
				html.push('			<div class="progress-bar progress-bar-success" style="width:0%;"></div>');
				html.push('  	</div>');
				html.push('  	<div class="progress-percent">发送中...</div>');
    			html.push('	 </span>');
    			html.push('	</div>');
    			html.push('</div>');
    			setTimeout(function() {
    				$(".message-send-content").slimScroll({
    					scrollTo: getLastPostPos()
    				});
    			}, 100);
			    row.push(html.join(""));
			}
			return row;
		},
		downloadTemplate : function(o) {
			var row = [];
			for (var i=0, file; file=o.files[i]; i++) {
				var html = [];
        		html.push('<div class="post from">');
				if ($(".message-send-form").attr("data-avatar")) {
					html.push('	<img class="avatar" src="' + cooperopcontextpath + '/rm/s/application/' + $(".message-send-form").attr("data-avatar") + 'S" alt="..." /> ');
				} else {
					html.push('	<span class="avatar"><i class="fa fa-user"></i></span> ');
				}
        		html.push('<div class="message filemessage">');
    			html.push('  <span class="arrow"></span>');
    			html.push('  <span class="name">' + $(".message-send-form").attr("data-system_user_name") + '</span>');
    			html.push('  <span class="datetime">' + $.formatdate(new Date(), 'HH:mm') + '</span>');
    			html.push('  <span class="body">');
    			html.push('  <div class="filecontent">');
    			html.push('  	<i class="fa fa-file-o"></i>');
    			html.push('  	<div class="filename">');
    			if(file.file_id) {
	            	html.push('<a href="' + cooperopcontextpath + '/rm/d/' + module + '/' + file.file_id + '" title="' + file.file_name + '" download="' + file.file_name + '" >' + file.file_name + '</a>');
	            } else {
	            	html.push(file.name);
	            }
    			html.push('  	</div>');
    			html.push('  	<div class="filesize">');
    			if(file.file_id) {
    				html.push(o.formatFileSize(+file.file_size));
    			} else {
    				html.push(o.formatFileSize(+file.size));
    			}
    			html.push('  	</div>');
    			html.push('  </div>');
				html.push('  	<div class="progress progress-striped active" style="height:1px;" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">');
				html.push('  	</div>');
				if (file.error) {
					html.push('  <div class="progress-percent error text-danger">发送失败：' + file.error + '</div>');
	            } else {
	            	html.push('  <div class="progress-percent">已发送</div>');
	            }
    			html.push('	 </span>');
    			html.push('	</div>');
    			html.push('</div>');
    			setTimeout(function() {
    				$(".message-send-content").slimScroll({
    					scrollTo: getLastPostPos()
    				});
    			}, 100);
			    row.push(html.join(""));
			}
			return row;
		}
	});
	_ff.bind("fileuploadadd", function(e, data) {
		if (data.autoUpload
				|| (data.autoUpload !== false && $(this).fileupload(
						'option', 'autoUpload'))) {
			$(this).fileupload('option', 'start').call($(this).fileupload(), $.Event('start'));
		}
	});
	_ff = $(".sendimage").fileupload({
		url : cooperopcontextpath + "/rm/u/" + module ,
		type : "POST",
		dataType : "json",
		pasteZone : $(".message-send-content"), // jQuery Object Chrome Only
		fileInput : $(".sendimage").find(":file"), // jQuery Object Chrome Only
		disableImageResize : false,
		paramName : "_ccfile[]",
		formAcceptCharset : "utf-8",
		formData: function(form) {
			return {};
		},
		start: function(e) {
			uploading = true;
		},
		always: function(e, data) {
			if (data.result.files) {
				for (var i in data.result.files) {
					var data = {content: data.result.files[i].file_id};
			    	data["target"] = $(".message-send-form").attr("data-taget");
			    	data["send_to"] = $(".message-send-form").attr("data-send_to");
			    	data["type"] = "I";
			    	if(data.content){
			    		$.call("application.message.send", data, function(rtn) {
			    			uploading = false;
			        	}, undefined, {nomask: true});	
			    	}
				}
			}
		},
		sequentialUploads: true, 
		singleFileUploads : true,
		limitMultiFileUploads : 1,
		maxFileSize : $(".sendimage").attr("maxsize") || "5000000",
		minFileSize : 0,
		maxNumberOfFiles: 9999999,
		forceIframeTransport : false, // cross-site file
		autoUpload : true,
		disableImageResize : /Android(?!.*Chrome)|Opera/
			.test(window.navigator.userAgent),
		acceptFileTypes : /(\.|\/)(gif|jpe?g|png|bmp)$/i,
		previewMaxWidth: 500,
		previewMaxHeight: 500,
		disableImagePreview: false,
		disableAudioPreview: true,
		disableVideoPreview: true,
		uploadTemplateId : null,
		downloadTemplateId : null,
		filesContainer: $(".message-send-content"),
		uploadTemplate : function(o) {
			var row = [];
			for (var i=0, file; file=o.files[i]; i++) {
				var html = [];
        		html.push('<div class="post from">');
				if ($(".message-send-form").attr("data-avatar")) {
					html.push('	<img class="avatar" src="' + cooperopcontextpath + '/rm/s/application/' + $(".message-send-form").attr("data-avatar") + 'S" alt="..." /> ');
				} else {
					html.push('	<span class="avatar"><i class="fa fa-user"></i></span> ');
				}
        		html.push('	<div class="message imagemessage">');
    			html.push('  <span class="arrow"></span>');
    			html.push('  <span class="name">' + $(".message-send-form").attr("data-system_user_name") + '</span>');
    			html.push('  <span class="datetime">' + $.formatdate(new Date(), 'HH:mm') + '</span>');
    			html.push('  <span class="body">');
    			html.push('  	<div class="preview"></div>');
				html.push('		<div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0">');
				html.push('			<div class="progress-bar" style="top:0%;"></div>');
				html.push('		</div>');
    			html.push('	 </span>');
    			html.push('	</div>');
    			html.push('</div>');
    			setTimeout(function() {
    				$(".message-send-content").slimScroll({
    					scrollTo: getLastPostPos()
    				});
    			}, 100);
			    row.push(html.join(""));
			}
			return row;
		},
		downloadTemplate : function(o) {
			var row = [];
			for (var i=0, file; file=o.files[i]; i++) {
				var html = [];
        		html.push('<div class="post from">');
				if ($(".message-send-form").attr("data-avatar")) {
					html.push('	<img class="avatar" src="' + cooperopcontextpath + '/rm/s/application/' + $(".message-send-form").attr("data-avatar") + 'S" alt="..." /> ');
				} else {
					html.push('	<span class="avatar"><i class="fa fa-user"></i></span> ');
				}
        		html.push('<div class="message imagemessage">');
    			html.push('  <span class="arrow"></span>');
    			html.push('  <span class="name">' + $(".message-send-form").attr("data-system_user_name") + '</span>');
    			html.push('  <span class="datetime">' + $.formatdate(new Date(), 'HH:mm') + '</span>');
    			html.push('  <span class="body">');
				html.push('		<div class="preview">');
	            if (file.file_id) {
	            	html.push('		<img src="' + cooperopcontextpath + '/rm/s/' + module + '/' + file.file_id + 'S" bigsrc="' + cooperopcontextpath + '/rm/s/' + module + '/' + file.file_id + '">');
	            }
				html.push('		</div>');
				if (file.error) {
					html.push('	<div class="progress-percent error text-danger">发送失败：' + file.error + '</div>');
	            }
    			html.push('	 </span>');
    			html.push('	</div>');
    			html.push('</div>');
    			setTimeout(function() {
    				$(".message-send-content").slimScroll({
    					scrollTo: getLastPostPos()
    				});
    			}, 100);
			    row.push(html.join(""));
			}
			return row;
		}
	});
	_ff.bind("fileuploadadd", function(e, data) {
		if (data.autoUpload
				|| (data.autoUpload !== false && $(this).fileupload(
						'option', 'autoUpload'))) {
			$(this).fileupload('option', 'start').call($(this).fileupload(), $.Event('start'));
		}
	});
    $(".message-send-users").on("click", "a[mtype]", function() {
		$.modal(cooperopcontextpath + "/w/application/message/chat.html", "消息：" + $(this).attr("mname"), {
			target: $(this).attr("mtype"),
			send_to: $(this).attr("mid"),
			width: "700px",
			height: "480px",
			noshade: true,
			modalid: $(this).attr("mtype") + "_" + $(this).attr("mid")
		});
	});
    $(".message-send-btn").on("click", function() {
    	var data = $(".message-send-form").getData();
    	data["target"] = $(".message-send-form").attr("data-taget");
    	data["send_to"] = $(".message-send-form").attr("data-send_to");
    	data["type"] = "T";
    	if(data.content){
    		$.call("application.message.send", data, function(rtn) {
        		$(".message-send-form").find("[name='content']").setData("");
        		var html = [];
        		if (rtn) {
	        		html.push('<div class="post ' + rtn.send_type + '">');
					if (rtn.avatar) {
						html.push('	<img class="avatar" src="' + cooperopcontextpath + '/rm/s/application/' + rtn.avatar + 'S" alt="..." /> ');
					} else {
						html.push('	<span class="avatar"><i class="fa fa-user"></i></span> ');
					}
	        		html.push('<div class="message">');
	    			html.push('  <span class="arrow"></span>');
	    			html.push('  <span class="name">' + rtn.system_user_name + '</span>');
	    			html.push('  <span class="datetime">' + rtn.send_time_label_sort + '</span>');
	    			html.push('  <span class="body">');
	    			html.push(rtn.content);
	    			html.push('</span></div></div>');
        		} else {
	        		html.push('<div class="post warning">');
	        		html.push('<div class="message">');
	    			html.push('  <span class="body"><i class="fa fa-warning"></i>');
	    			html.push('您无权向该用户发送消息！');
	    			html.push('</span></div></div>');

        		}
        		$(".message-send-content").append(html.join(""));
        		$(".message-send-content").slimScroll({
                    scrollTo: getLastPostPos()
                });
        	}, undefined, {nomask: true});	
    	}else{
    		var html = [];
    		html.push('<div class="post warning">');
    		html.push('<div class="message">');
			html.push('  <span class="body"><i class="fa fa-warning"></i>');
			html.push('发送消息不能为空，请重新输入！');
			html.push('</span></div></div>');
			$(".message-send-content").append(html.join(""));
    		$(".message-send-content").slimScroll({
                scrollTo: getLastPostPos()
            });
    		return;
    	}
    	
    });
    $(".message-send-form").find(".note-editor").find(".note-toolbar").find(".btn-group").addClass("dropup");
    $(".message-send-form").find(".note-editor").find(".note-toolbar").find(".btn-sm").addClass("btn-xs").removeClass("btn-sm");
    var __th = $(".message-send-form").find(".note-editor").find(".note-toolbar").outerHeight();
    $(".message-send-form").find(".note-editor").find(".note-toolbar").hide();
    $(".fontstyle").on("click", "a", function(e) {
    	$(".message-send-form").find(".note-editor").find(".note-toolbar").toggle();
    	if ($(".message-send-form").find(".note-editor").find(".note-toolbar").is(":hidden")) {
    		$(".message-send-form").find(".note-editable").height($(".message-send-form").find(".note-editable").height() + __th);
    	} else {
    		$(".message-send-form").find(".note-editable").height($(".message-send-form").find(".note-editable").height() - __th);
    	}
    });
    $(".message-send-form").find("[name='content']").setData("");
    $(".message-send-form").find(".note-editable").on("keydown", function(e) {
    	var event = event || e;
    	if (event.keyCode == 13 && event.ctrlKey) {
    		$(".message-send-btn").click();
    	}
    });
});