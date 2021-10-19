var tabsparam = {};
var tabsding = {};
$.extend({
	"tabwindowinit": function() {
		var $this = $(".right-content .headLeft");
		//查询默认打开的页签
		setTimeout(function(){
			$.call("application.stapleTabs.query", {}, function(rtn){
				if(rtn){
					for(var i in rtn.resultset){
						var tab = rtn.resultset[i];
						if (tab.params_str != undefined) {
							tab.params_str = tab.params_str.replace(/\\/g, "");
							tab.params_str = tab.params_str.substring(1, tab.params_str.length - 1);
						}
						$.openTabPage(tab.pageid, tab.title, tab.url, tab.need_refresh, tab.params_str, "ding");
						tabsding[tab.pageid] = tab;
					}
				}
			}, null, {nomask: true});
		}, 1000);
		
	},
	"open_tabwindow": function(id, title, url, need_refresh, params, ding) {
		var $this = $(".right-content .headLeft");
		var height = $this.height() - $this.find('.tab').outerHeight();
		if ($this.find(".tab").find(".tab-title[wid='" + id + "']").length > 0) {
			$this.find(".tab").find(".tab-title").removeClass("active");
			$this.find(".tab").find(".tab-title[wid='" + id + "']").addClass("active");
			if(typeof crtechTogglePage == 'undefined'){
				$this.find(".tab-content").find(".tab-panel").removeClass("active");
				$this.find(".tab-content").find(".tab-panel[wid='" + id + "']").addClass("active");
				$this.find(".tab-content").find(".tab-panel[wid='" + id + "']").find("iframe").height(height);
				if (need_refresh == "1") {
					$this.find(".tab-content").find(".tab-panel[wid='" + id + "']").find("iframe")[0].contentWindow.location.reload(true);
				}
			}
		} else {
			$this.find(".tab .tab-title").removeClass("active");
			$this.find(".tab-content .tab-panel").removeClass("active");
			var html = [];
			var dingclass = "";
			if(ding == "ding" || tabsding[id]){
				dingclass = "ding";
			}
			html.push('<div class="tab-title active '+ dingclass +'"  wid="' + id + '" title="' + title + '">');
			html.push('<a href="javascript:void(0);" class="tabtitle">');
			html.push(title);
			html.push('</a>');
			html.push('<a href="javascript:void(0);" class="tab-close"><i class="cicon icon-close tabclose"></i></a>');
			html.push('<a href="javascript:void(0);" class="tab-refresh"><i class="cicon icon-ding3 refresh"></i><i class="cicon icon-ding refresh"></i></a>');
    		html.push('</div>');
			$this.find(".tab .tab_div .tab_width").append(html.join(''));
			if(typeof crtechOpenPage == 'undefined'){
				html = [];
				html.push('<div class="tab-panel active" wid="' + id + '">');
				html.push('<iframe src="' + url + '" frameborder="0"  style="width:100%;height:' + height + 'px;border:0px;" >');
				html.push('	</iframe>');
				html.push('</div>');
				$this.find(".tab-content").append(html.join(''));
			}
			$(".right-content .headLeft .tab .refresh_div .refresh-tab").attr("wid", id);
			var t = {pageid: id, title: title, url: url, need_refresh: need_refresh, params_str : params};
			tabsparam[id] = t;
			
			$this.find(".tab .tab-title[wid='" + id + "']").on("click", ".tabtitle", function() {
				var id = $(this).parents(".tab-title:first").attr("wid");
				$this.find(".tab").find(".tab-title").removeClass("active");
				$this.find(".tab").find(".tab-title[wid='" + id + "']").addClass("active");
				if(typeof crtechTogglePage == 'undefined'){
					$this.find(".tab-content").find(".tab-panel").removeClass("active");
					$this.find(".tab-content").find(".tab-panel[wid='" + id + "']").addClass("active");
					
					if(!$(this).hasClass("inited")){
						var $t = $(".right-content .headLeft").find(".tab-content").find(".tab-panel[wid='" + id + "']").find("iframe");
						$t[0].contentWindow.$($t.contents()).trigger("resize");
						$(this).addClass("inited")
					}
				}else{
					//调用陈杰的方法，激活页面
					crtechTogglePage(id);
				}
				$(".right-content .headLeft .tab .refresh_div .refresh-tab").attr("wid", id);
			});
			$this.find(".tab .tab-title[wid='" + id + "']").on("click", ".tabclose", function() {
				var closeid = "";
				var activeid = "";
				var id = $(this).parents(".tab-title:first").attr("wid");
				closeid = id;
				var active = $this.find(".tab").find(".tab-title[wid='" + id + "']").hasClass("active");
				var index = $this.find(".tab").find(".tab-title[wid='" + id + "']").index()-1;
				activeid = $this.find(".tab").find(".tab-title:eq(" + (index < $this.find(".tab").find(".tab-title").length-1 ? index : index - 1) +")").attr("wid");
				if(typeof crtechClosePage != 'undefined'){
					//调用陈杰的方法，
					if (!crtechClosePage(closeid, activeid)) {
						return;
					}
				}
				$this.find(".tab").find(".tab-title[wid='" + id + "']").remove();
				if(typeof crtechClosePage == 'undefined'){
					$this.find(".tab-content").find(".tab-panel[wid='" + id + "']").remove();
				}
				if (active) {
					$this.find(".tab").find(".tab-title[wid='" + activeid + "']").addClass("active");
					if(typeof crtechClosePage == 'undefined'){
						$this.find(".tab-content").find(".tab-panel[wid='" + activeid + "']").addClass("active");
					}
				}
				$(".right-content .headLeft .tab .refresh_div .refresh-tab").attr("wid", activeid);
				delete tabsparam[closeid];
				//计算位置
				var width = $this.find('.tab .tab_div').css("width").replace("px","")-0;
				var left = $this.find('.tab .tab_div .tab_width').css("left").replace("px","").replace("-","")-0;
				var tlength = $this.find(".tab").find(".tab-title").length;
				if(left + width >= tlength * 120){
					if(left >=120){
						left -= 120;
					}else{
						left = 0;
					}
					$this.find('.tab .tab_div .tab_width').css("left", "-"+ left + "px");
				}
			
				//TODO 计算产品产品并发数 ，放在缓存，记录ssid与product_code，一个产品所有页签关闭，则清除这条缓存记录
			});
			$this.find(".tab .tab-title[wid='" + id + "']").on("click", ".tab-refresh", function() {//钉住
				var $tabp = $(this).parents(".tab-title:first")
				var pid = $tabp.attr("wid");
				var daparam = tabsparam[pid];
				var ding = "1";
				if($tabp.hasClass("ding")){
					ding = "0";//取消钉
					delete tabsding[pid];
				}else{
					tabsding[pid] = daparam;
				}
				daparam.ding = ding;
				$.call("application.stapleTabs.staple", {data: $.toJSON(daparam)}, function(rtn){
					if(rtn){
						if(ding == "1"){
							$tabp.addClass("ding");
						}else{
							$tabp.removeClass("ding");
						}
					}
				}, null, {nomask: true});
			});
		}
		//计算位置
		var width = $this.find('.tab .tab_div').css("width").replace("px","")-0;
		var left = $this.find('.tab .tab_div .tab_width').css("left").replace("px","").replace("-","")-0;
		var tindex = $this.find(".tab").find(".tab-title[wid='" + id + "']").index()-0;
		if(left >= (tindex+1) * 120){
			var l = tindex * 120;
			$this.find('.tab .tab_div .tab_width').css("left", "-"+ l + "px");
		}else if(left + width <= (tindex+1) * 120){
			var l = (tindex+1) * 120 - width;
			$this.find('.tab .tab_div .tab_width').css("left", "-"+ l + "px");
		}
	},
	"close_tabwindow": function(id) {
		var $this = $(".right-content .headLeft");
		id = id || $this.find(".tab").find(".tab-title.active").attr("wid");
		$this.find(".tab").find(".tab-title[wid='" + id + "']").find(".tabclose").click();
		delete tabsparam[id];
	}
});

$(document).ready(function() {
	$(".right-content .headLeft .tab .refresh_div .refresh-tab").on("click", function (){
		var $tab = $(".right-content .headLeft");
		var id = $(this).attr("wid");
		$tab.find(".tab").find(".tab-title").removeClass("active");
		$tab.find(".tab").find(".tab-title[wid='" + id + "']").addClass("active");
		if(typeof crtechRefreshPage == 'undefined'){
			$tab.find(".tab-content").find(".tab-panel").removeClass("active");
			$tab.find(".tab-content").find(".tab-panel[wid='" + id + "']").addClass("active");
			$tab.find(".tab-content").find(".tab-panel[wid='" + id + "']").find("iframe")[0].contentWindow.location.reload(true);
		}else{
			//调用陈杰的方法，关闭页面
			crtechRefreshPage(id);
		}
	});
	$(".right-content .headLeft .tab .refresh_div .left-tab").on("click", function (){
		if(typeof crtechGoBack == 'undefined'){
			$tab.find(".tab-content").find(".tab-panel.active").find("iframe")[0].contentWindow.back();
		}else{
			//调用陈杰方法
			crtechGoBack();
		}
	});
	$(".right-content .headLeft .tab .refresh_div .right-tab").on("click", function (){
		if(typeof crtechGoForward == 'undefined'){
			$tab.find(".tab-content").find(".tab-panel.active").find("iframe")[0].contentWindow.back.forward();
		}else{
			//调用陈杰方法
			crtechGoForward();
		}
	});
	
	
	$(".right-content .headLeft .tab  .tab_tool > .left-tab").on("click", function (){
		var $tab = $(".right-content .headLeft");
		var left = $tab.find('.tab .tab_div .tab_width').css("left").replace("px","").replace("-","")-0;
		if(left <= 120){
			left = 0;
		}else{
			left -= 120;
		}
		$tab.find('.tab .tab_div .tab_width').css("left", "-"+ left + "px");
	});
	$(".right-content .headLeft .tab .tab_tool > .right-tab").on("click", function (){
		var $tab = $(".right-content .headLeft");
		var width = $tab.find('.tab .tab_div').css("width").replace("px","")-0;
		var left = $tab.find('.tab .tab_div .tab_width').css("left").replace("px","").replace("-","")-0;
		var tlength = $tab.find(".tab").find(".tab-title").length;
		if(width + left >= tlength * 120){
			var left = tlength * 120 - width;
		}else{
			left +=120;
		}
		$tab.find('.tab .tab_div .tab_width').css("left", "-"+ left + "px");
	});
	$(".right-content .headLeft .tab .tab_tool > .close-all").on("click", function (){
		$(".right-content .headLeft .tab").find(".tab-title").each(function(){
			$.close_tabwindow($(this).attr("wid"));
		});
	});
});