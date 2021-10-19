var cloud_app = {
	is_app: false,
	app_name: undefined
};
$.extend({
	"is_mobile": function() {
		return navigator.userAgent.match(/(iPhone|iPod|Android|ios)/i);
	},
	/**
	 * 打开一个窗口
	 * @param url 要打开的窗口的url
	 * @param data 要传入新窗口的参数
	 * @param target 要在哪个现有窗口中打开
	 */
	"open": function (url, data, target) {
		data = $.extend(true, {}, data);
		$("#__windowOpenForm").remove();
		var fmhtml =$('<form id="__windowOpenForm" enctype="application/x-www-form-urlencoded;" accept-charset="UTF-8" method="post" action=""></form>');
		fmhtml.attr("action", url);
		fmhtml.attr("target", target || "_blank");
		var input,inputStr = "<input type='hidden'>";
		for (var o in data) {
			if($.isArray(data[o])){
				for ( var i = 0; i < data[o].length; i++) {
					input = $(inputStr);
					input.attr("name",o);
					input.val(data[o][i]);
					fmhtml.append(input);
				}
			}else{
				input = $(inputStr);
				input.attr("name",o);
				input.val(data[o]);
				fmhtml.append(input);
			}
		}
		$(document).find("body").append(fmhtml);
		fmhtml = $("#__windowOpenForm");
		fmhtml.submit();
		fmhtml.remove();
	},
	"openTabPage": function (pid, title, pageid, params, need_refresh, ding) {
		var url = '/w';
		if(pageid.startsWith('/w') || pageid.startsWith('http')){
			url = pageid;
		}else if('undefined' !=typeof(cooperopcontextpath)){
			url = cooperopcontextpath+'/w';
			url = url + "/" + pageid.replace(/\./g, "/") + ".html?" + excuteURLParams(params);
		}
		top.$.open_tabwindow(pid, title, 
				url, need_refresh, params, ding);
	},
	"closeTabpage" : function (pageid) {
		top.$.close_tabwindow(pageid);
	},
	"modal" : function (url, title, options) {
		options = $.extend(true, {}, options);
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
		if (top != window && url != undefined && url != null && url.indexOf("http") != 0 && url.indexOf("/") != 0) {
			url = location.href.substring(0, location.href.lastIndexOf("/") + 1) + url;
		}

		if (options["modalid"]) {
			for (var mi in toplayer.modals) {
				if (toplayer.modals[mi].id == options["modalid"]) {
					toplayer.layer.zIndex ++;
					toplayer.modals[mi].layero.css("z-index",toplayer.layer.zIndex + 1);
					return;
				}
			}
		}
		
		var o = toplayer.$.extend(true, {}, options);
		delete options["title"];
		delete options["callback"];
		delete options["width"];
		delete options["height"];
		delete options["noshade"];
		delete options["maxmin"];
		o.id = options["modalid"];
		o.type = 2;
		o.content = "about:blank;";
		var full = false;
		if (cloud_app.is_app || $.is_mobile()) {
			full = true;
			o.width = "100%";
			o.height = "100%";
		} else if ($(toplayer).width() <= 768) {
			full = true;
			o.width = "100%";
			o.height = "100%";
		} else if (o.width) {
			if (+o.width > 0) {
				if ($(toplayer).width() <= o.width) {
					full = true;
					o.width = "100%";
					o.height = "100%";
				}
			} else if (o.width.toUpperCase().indexOf("PX") > 0) {
				if (+(o.width.substring(0, o.width.length - 2)) > $(toplayer).width()) {
					full = true;
					o.width = "100%";
					o.height = "100%";
				} 
			} else if (o.width.indexOf("%") > 0) {
				if ($(toplayer).width() * +(o.width.substring(0, o.width.length - 1)) / 100 > $(toplayer).width()) {
					full = true;
					o.width = "100%";
					o.height = "100%";
				} 
			}
		}
		o.area = [o.width || "80%", o.height || "80%"];
		o.maxmin  = o.maxmin == undefined ? true : o.maxmin;
		if (full) {
			o.move = false;
			o.offset = "lt";
			o.maxmin  = false;
		}
		o.fix = true;
		o.scrollbar = false;
		o.shade = o.noshade ? 0 : 0.3;
		o.shadeClose = false;
		o.closeBtn = 1;
		o.zIndex = toplayer.layer.zIndex, //重点1
		o.title = '<span style="font-size:14px" title="' + (title || o.title) + '">' + (title || o.title) + '</span>';
		o.success = function(layero, index) {
		   toplayer.layer.setTop(layero);
	       var body = layer.getChildFrame('body', index);
	       var iframeWin = window[layero.find('iframe')[0]['name']];
	       body.attr("layer_index", index);
	       toplayer.modals["m_" + index] = {index: index, callback: o.callback, layero: layero, id: options["modalid"]};
	       delete options["modalid"];
    	   if('undefined' !=typeof(cooperopcontextpath))
    		   if(top.contextPath != undefined && top.contextPath.indexOf("/")!=0){
    			   url = cooperopcontextpath+"/"+url;
    		   }
    	   $.open(url, $.extend(true, options, {ismodal: true}), layero.find('iframe')[0]['name']);
	    };
	    o.cancel = function(index) {
	    	if (o.callback) {
	    		o.callback(toplayer.modals["m_" + index].value);
	    	}
	    	delete toplayer.modals["m_" + index];
	    }
	    var index = toplayer.layer.open(o);
	    if (full) {
	    	toplayer.layer.full(index);
	    }
	},

	/**
	 * 关闭内部窗口，与上面的打开对应，一般在打开了的内部窗口中调用，以达到关闭这个窗口的作用 parent.closeModal()
	 * @param value 父页面要接受的参数，没有则不传
	 */
	"closeModal" : function (value) {
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
		var modal_index = toplayer.layer.getFrameIndex(window.name);
		if (toplayer.modals["m_" + modal_index].callback) {
			toplayer.modals["m_" + modal_index].callback(value);
		}
		delete toplayer.modals["m_" + modal_index];
		toplayer.layer.close(modal_index);
	}
})