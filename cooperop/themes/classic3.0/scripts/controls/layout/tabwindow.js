var tabsparam = {};
var tabsding = {};
$.extend({
	"tabwindowinit": function() {
		$("#itempath ul").find("li[wid='home-page']").on("click", function(){
			$("#itempath ul").find("li").removeClass("active");
			$("#itempath ul").find("li .tab-items").removeClass("active");
			$(this).addClass("active");
			$(this).find(".tab-items").addClass("active");
			$(".topbar-global .page-tabs .tools-box a.refresh-tab").attr("wid", "home-page");
			$("#page-content").find(".page-tab").removeClass("active");
			$("#page-content").find(".page-tab[wid='home-page']").addClass("active");
		});
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
						tab.inited = 0;
						tabsding[tab.pageid] = tab;
					}
				}
			}, null, {nomask: true});
		}, 10);
		setTimeout(function(){
			$("#itempath").find("ul > li[wid='home-page'] > a").click();
		}, 500);
	},
	"open_tabwindow": function(id, title, url, need_refresh, params, ding) {
		if(id == ""){
			return;
		}
		var $this = $("#itempath ul");
		var pgc = $("#page-content");
		$this.find("li").removeClass("active");
		$this.find("li .tab-items").removeClass("active");
		pgc.find(".page-tab").removeClass("active");
		if ($this.find("li[wid='" + id + "']").length > 0) {
			$this.find("li[wid='" + id + "']").addClass("active");
			$this.find("li[wid='" + id + "'] .tab-items").addClass("active");
			pgc.find(".page-tab[wid='" + id + "']").addClass("active");
			if (need_refresh == "1") {
				pgc.find(".page-tab[wid='" + id + "']").find("iframe")[0].contentWindow.location.reload(true);
			}
			$(".topbar-global .page-tabs .tools-box a.refresh-tab").attr("wid", id);
		} else {
			var html = [];
			var dingclass = "";
			if(ding == "ding" || tabsding[id]){
				dingclass = "ding";
			}
			html.push('<li class="active" wid="' + id + '">');
			html.push('<a class="tab-items active" href="javascript:void(0);" title="'+title+'"><span>'+title+'</span></a>');
			html.push('<a href="javascript:void(0);" class="set ding');
			if(ding == "ding" || tabsding[id]){
				html.push(' active" ><i class="cicon icon-ding2"></i>');
			}else{
				html.push('" ><i class="cicon icon-ding"></i></a>');
			}
			html.push('</a>');
			html.push('<a class="set release" href="javascript:void(0);"><i class="cicon icon-imize-exit"></i></a>');
			html.push('</li>');
			
			$this.append(html.join(''));
			html = [];
			html.push('<div class="page-tab page-right-content active" wid="' + id + '">');
			html.push('<iframe src="' + url + '" frameborder="0"  style="width:100%;border:0px;" ></iframe>');
			html.push('</div>');
			pgc.append(html.join(''));
			$(".topbar-global .page-tabs .tools-box a.refresh-tab").attr("wid", id);
			var t = {pageid: id, title: title, url: url, need_refresh: need_refresh, params_str : params};
			tabsparam[id] = t;
			
			$this.find("li[wid='" + id + "']").on("click", ".tab-items", function() {
				var id = $(this).parents("li:first").attr("wid");
				$this.find("li").removeClass("active");
				$this.find("li .tab-items").removeClass("active");
				$this.find("li[wid='" + id + "']").addClass("active");
				$(this).addClass("active");
				if(tabsding[id] && tabsding[id].inited == 0){
					tabsding[id].inited = 1;
					pgc.find(".page-tab[wid='" + id + "']").find("iframe")[0].contentWindow.location.reload(true);
				}
				pgc.find(".page-tab").removeClass("active");
				pgc.find(".page-tab[wid='" + id + "']").addClass("active");
				$(".topbar-global .page-tabs .tools-box a.refresh-tab").attr("wid", id);
			});
			$this.find("li[wid='" + id + "']").on("click", ".release", function() {
				var closeid = "";
				var activeid = "";
				var id = $(this).parents("li:first").attr("wid");
				closeid = id;
				var active = $this.find("li[wid='" + id + "']").hasClass("active");
				var index = $this.find("li[wid='" + id + "']").index()-1;
				activeid = $this.find("li:eq(" + (index < $this.find("li").length-1 ? index : index - 1) +")").attr("wid");
				$this.find("li[wid='" + id + "']").remove();
				pgc.find(".page-tab[wid='" + id + "']").remove();
				if (active) {
					$this.find("li[wid='" + activeid + "']").addClass("active");
					pgc.find(".page-tab[wid='" + activeid + "']").addClass("active");
				}
				$(".topbar-global .page-tabs .tools-box a.refresh-tab").attr("wid", activeid);
				delete tabsparam[closeid];
				//计算位置
				var width = $("#itempath").css("width").replace("px","")-0;
				var left = $("#itempath").find('ul').css("left").replace("px","").replace("-","")-0;
				var tlength = $this.find("li").length;
				if(left + width >= tlength * 132 - 32){
					if(left >=80){
						left -= 80;
					}else{
						left = 0;
					}
					$("#itempath").find('ul').css("left", "-"+ left + "px");
				}
				$(".layout-toolbar .statusbar-cr .rightbar .item a").addClass("disabled");
				if(left > 0){
					$(".layout-toolbar .statusbar-cr .rightbar .left-tab a").removeClass("disabled");
				}
				if(left + width < tlength * 80- 32){
					$(".layout-toolbar .statusbar-cr .rightbar .right-tab a").removeClass("disabled");
				}
				//TODO 计算产品产品并发数 ，放在缓存，记录ssid与product_code，一个产品所有页签关闭，则清除这条缓存记录
			});
			$this.find("li[wid='" + id + "']").on("click", ".ding", function() {//钉住
				var $th = $(this);
				var pid = $th.parent().attr("wid");
				var daparam = tabsparam[pid];
				var ding = "1";
				if($th.hasClass("active")){
					ding = "0";//取消钉
					delete tabsding[pid];
				}else{
					tabsding[pid] = daparam;
				}
				daparam.ding = ding;
				$.call("application.stapleTabs.staple", {data: $.toJSON(daparam)}, function(rtn){
					if(rtn){
						if(ding == "1"){
							$th.addClass("active");
							$th.find(".cicon").removeClass("icon-ding");
							$th.find(".cicon").addClass("icon-ding2");
						}else{
							$th.removeClass("active");
							$th.find(".cicon").addClass("icon-ding");
							$th.find(".cicon").removeClass("icon-ding2");
						}
					}
				}, null, {nomask: true});
			});
		}
		//计算位置
		var width = $("#itempath").css("width").replace("px","")-0;
		var left = $("#itempath").find('ul').css("left").replace("px","").replace("-","")-0;
		var tindex = $this.find("li[wid='" + id + "']").index()-0;
		var l;
		if(left >= (tindex+1) * 80-32){
			l = tindex * 80- 32;
			$("#itempath").find('ul').css("left", "-"+ l + "px");
		}else if(left + width <= (tindex+1) * 80 -32){
			l = (tindex+1) * 80 - width;
			$("#itempath").find('ul').css("left", "-"+ l + "px");
		}
		var tlength = $this.find("li").length;
		$(".layout-toolbar .statusbar-cr .rightbar .close-tabs a").removeClass("disabled");
		if(l > 0){
			$(".layout-toolbar .statusbar-cr .rightbar .left-tab a").removeClass("disabled");
		}
		if(l + width < tlength * 80- 32){
			$(".layout-toolbar .statusbar-cr .rightbar .right-tab a").removeClass("disabled");
		}
	},
	"close_tabwindow": function(id) {
		var $this = $("#itempath");
		id = id || $this.find("ul").find("li.active").attr("wid");
		$this.find("ul").find("li[wid='" + id + "']").find(".release").click();
		delete tabsparam[id];
	}
});

$(document).ready(function() {
	$(".topbar-global .page-tabs .tools-box a.refresh-tab").on("click", function (){
		var $tab = $("#itempath");
		var $pgc = $("#page-content");
		var id = $(this).attr("wid");
		$tab.find("ul").find("li").removeClass("active");
		$tab.find("ul").find("li[wid='" + id + "']").addClass("active");
		$pgc.find(".page-tab").removeClass("active");
		$pgc.find(".page-tab[wid='" + id + "']").addClass("active");
		$pgc.find(".page-tab[wid='" + id + "']").find("iframe")[0].contentWindow.location.reload(true);
	});
	
	
	$(".topbar-global .page-tabs .tools-box a.left-tab").on("click", function (){
		var $tab = $("#itempath");
		var width = $tab.css("width").replace("px","")-0;
		var left = $tab.find('ul').css("left").replace("px","").replace("-","")-0;
		var tlength = $tab.find("ul li").length;
		if(left <= 80){
			left = 0;
		}else{
			left -= 80;
		}
		$tab.find('ul').css("left", "-"+ left + "px");
	});
	$(".topbar-global .page-tabs .tools-box a.right-tab ").on("click", function (){
		var $tab = $("#itempath");
		var width = $tab.css("width").replace("px","")-0;
		var left = $tab.find('ul').css("left").replace("px","").replace("-","")-0;
		var tlength = $tab.find("ul li").length;
		if(width + left >= tlength * 80 - 32){
			var left = tlength * 80 - width;
		}else{
			left +=80;
		}
		$tab.find('ul').css("left", "-"+ left + "px");
	});
	$(".topbar-global .page-tabs .tools-box a.close-all").on("click", function (){
		$.confirm("关闭全部页签？", function(r) {
			if (r) {
				$("#itempath > ul").find("li").each(function(){
					if($(this).attr("wid") != 'home-page'){
						$.close_tabwindow($(this).attr("wid"));
					}
				});
			}
		});
		
	});
});