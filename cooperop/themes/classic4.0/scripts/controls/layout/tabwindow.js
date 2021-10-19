var tabsparam = {};
var tabsding = {};
$.extend({
	"tabwindowinit": function() {
		$("#itempath ul").find("li[wid='home-page']").on("click", function(){
			$("#itempath").find("ul li").removeClass("active");
			$(this).addClass("active");
			$(".layout-toolbar .statusbar-cr .leftbar .refresh-tab a").attr("wid", "home-page");
			if(typeof crtechTogglePage == 'undefined'){
				$("#page-content").find(".page-tab").removeClass("active");
				$("#page-content").find(".page-tab[wid='home-page']").addClass("active");
			}else{
				crtechOpenPage("home-page", "首页", cooperopcontextpath + "/welcome.jsp", 0, {}, {}, "ding");
			}
		});
		//查询默认打开的页签
		setTimeout(function(){
			$.call("application.stapleTabs.query", {}, function(rtn){
				if(rtn){
					for(var i in rtn.resultset){
						var tab = rtn.resultset[i];
						/* ygz.2021.02.09 无需处理params_str, 是json格式，有？的连接截断
						if (tab.params_str != undefined) {
							tab.params_str = tab.params_str.replace(/\\/g, "");
							tab.params_str = tab.params_str.substring(1, tab.params_str.length - 1);
						}
						*/
						//$.openTabPage(tab.pageid, tab.title, tab.url, tab.need_refresh, tab.params_str, "ding");
						if(tab.url.indexOf('?') >= 0){
							tab.url = tab.url.substring(0, tab.url.indexOf('?'))
						}
						$.openTabPage(tab.pageid, tab.title, tab.url, tab.need_refresh,
							Object.keys(JSON.parse(tab.params_str)).length === 0? "":JSON.parse(tab.params_str), "ding");
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
		if(id == "home-page"){return;}
		if(id == ""){
			return;
		}
		var $this = $("#itempath ul.tab-ul");
		var $tab = $("#itempath ul");
		var pgc = $("#page-content");
		if ($this.find("li[wid='" + id + "']").length > 0) {
			$tab.find("li").removeClass("active");
			$this.find("li[wid='" + id + "']").addClass("active");
			if(typeof crtechTogglePage == 'undefined'){
				pgc.find(".page-tab").removeClass("active");
				pgc.find(".page-tab[wid='" + id + "']").addClass("active");
				if (need_refresh == "1") {
					pgc.find(".page-tab[wid='" + id + "']").find("iframe")[0].contentWindow.location.reload(true);
				}
			}
			$(".layout-toolbar .statusbar-cr .leftbar .refresh-tab a").attr("wid", id);
		} else {
			$tab.find("li").removeClass("active");
			pgc.find(".page-tab").removeClass("active");
			var html = [];
			var dingclass = "";
			if(ding == "ding" || tabsding[id]){
				dingclass = "ding";
			}
			html.push('<li class="active" wid="' + id + '">');
			html.push('<a class="item-tab" href="javascript:void(0);" title="'+title+'">'+title+'</a>');
			html.push('<a href="javascript:void(0);" class="operate roof ');
			if(ding == "ding" || tabsding[id]){
				html.push('active" ><i class="cicon icon-ding2"></i>');
			}else{
				html.push('" ><i class="cicon icon-ding"></i></a>');
			}
			html.push('</a>');
			html.push('<a class="operate del" href="javascript:void(0);"><i class="cicon icon-close"></i></a>');
			html.push('</li>');
			
			$this.append(html.join(''));
			if(typeof crtechOpenPage == 'undefined'){
				html = [];
				html.push('<div class="page-tab active" wid="' + id + '">');
				if(params && Object.keys(params).length >0){
//					var data = JSON.parse(params);
					url = url + "?" + excuteURLParams(params);
				}
				html.push('<iframe src="' + url + '" frameborder="0"  style="width:100%;border:0px;" ></iframe>');
				html.push('</div>');
				pgc.append(html.join(''));
			}
			$(".layout-toolbar .statusbar-cr .leftbar .refresh-tab a").attr("wid", id);
			var t = {pageid: id, title: title, url: url, need_refresh: need_refresh, params_str : params};
			tabsparam[id] = t;
			
			$this.find("li[wid='" + id + "']").on("click", ".item-tab", function() {
				var id = $(this).parents("li:first").attr("wid");
				$tab.find("li").removeClass("active");
				$this.find("li[wid='" + id + "']").addClass("active");
				if(typeof crtechTogglePage == 'undefined'){
					
				/*	if(!$(this).hasClass("inited")){
						var $t = $(".right-content .headLeft").find(".tab-content").find(".tab-panel[wid='" + id + "']").find("iframe");
						$t[0].contentWindow.$($t.contents()).trigger("resize");
						$(this).addClass("inited")
					}*/
					if(tabsding[id] && tabsding[id].inited == 0){
						tabsding[id].inited = 1;
						pgc.find(".page-tab[wid='" + id + "']").find("iframe")[0].contentWindow.location.reload(true);
					}
					pgc.find(".page-tab").removeClass("active");
					pgc.find(".page-tab[wid='" + id + "']").addClass("active");
					setTimeout(function(){
						$('.layout-content').css('width','99%');
						setTimeout(function(){
							$('.layout-content').css('width','100%');
						},100);	
					},500);
				}else{
					//调用陈杰的方法，激活页面
					crtechTogglePage(id);
				}
				$(".layout-toolbar .statusbar-cr .leftbar .refresh-tab a").attr("wid", id);
			});
			$this.find("li[wid='" + id + "']").on("click", ".del", function() {
				var closeid = "";
				var activeid = "";
				var id = $(this).parents("li:first").attr("wid");
				closeid = id;
				var active = $this.find("li[wid='" + id + "']").hasClass("active");
				var index = $this.find("li[wid='" + id + "']").index()-1;
				activeid = $this.find("li:eq(" + (index < $this.find("li").length-1 ? index : index - 1) +")").attr("wid");
				if(typeof crtechClosePage != 'undefined'){
					//调用陈杰的方法，
					if (!crtechClosePage(closeid, activeid)) {
						return;
					}
				}
				$this.find("li[wid='" + id + "']").remove();
				if(typeof crtechClosePage == 'undefined'){
					pgc.find(".page-tab[wid='" + id + "']").remove();
				}
				if (active) {
					$this.find("li[wid='" + activeid + "']").addClass("active");
					if(typeof crtechClosePage == 'undefined'){
						pgc.find(".page-tab[wid='" + activeid + "']").addClass("active");
					}
				}
				$(".layout-toolbar .statusbar-cr .leftbar .refresh-tab a").attr("wid", activeid);
				delete tabsparam[closeid];
				//计算位置
				var width = $("#itempath").css("width").replace("px","")-68;
				var left = $("#itempath").find('ul.tab-ul').css("left").replace("px","").replace("-","")-0;
				if(left == 68){
					left = 0;
				}else{
					left = left + 68;
				}
				var tlength = $this.find("li").length;
				if(left + width >= tlength * 132){
					if(left >=132){
						left -= 132;
					}else{
						left = 0;
					}
					left = left - 68;
					if(l <= 0){
						$("#itempath").find('ul.tab-ul').css("left", "68px");
					}else{
						$("#itempath").find('ul.tab-ul').css("left", "-"+ left + "px");
					}
				}
				if($("#itempath").find('ul.tab-ul li').length < 1){
					$(".layout-toolbar .statusbar-cr .rightbar .item a").addClass("disabled");
					$("#itempath").find('ul.home-ul li').click();
				}
				if(left > 0){
					$(".layout-toolbar .statusbar-cr .rightbar .left-tab a").removeClass("disabled");
				}
				if(left + 68 + width < tlength * 132){
					$(".layout-toolbar .statusbar-cr .rightbar .right-tab a").removeClass("disabled");
				}
				
				//TODO 计算产品产品并发数 ，放在缓存，记录ssid与product_code，一个产品所有页签关闭，则清除这条缓存记录
			});
			$this.find("li[wid='" + id + "']").on("click", ".roof", function() {//钉住
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
		var width = $("#itempath").css("width").replace("px","")-68;
		var left = $("#itempath").find('ul.tab-ul').css("left").replace("px","").replace("-","")-0;
		if(left == 68){
			left = 0;
		}else{
			left = left + 68;
		}
		var tindex = $this.find("li[wid='" + id + "']").index()-0;
		var l;
		if(left >= (tindex+1) * 132){
			l = tindex * 132 - 68;
			if(l <= 0){
				$("#itempath").find('ul.tab-ul').css("left", "68px");
			}else{
				$("#itempath").find('ul.tab-ul').css("left", "-"+ l + "px");
			}
		}else if(left + width <= (tindex+1) * 132){
			l = (tindex+1) * 132 - width - 68;
			$("#itempath").find('ul.tab-ul').css("left", "-"+ l + "px");
		}
		var tlength = $this.find("li").length;
		$(".layout-toolbar .statusbar-cr .rightbar .close-tabs a").removeClass("disabled");
		if(l > 0){
			$(".layout-toolbar .statusbar-cr .rightbar .left-tab a").removeClass("disabled");
		}
		if(l + 68 + width < tlength * 132){
			$(".layout-toolbar .statusbar-cr .rightbar .right-tab a").removeClass("disabled");
		}
	},
	"close_tabwindow": function(id) {
		var $this = $("#itempath");
		id = id || $this.find("ul").find("li.active").attr("wid");
		$this.find("ul").find("li[wid='" + id + "']").find(".del").click();
		delete tabsparam[id];
	}
});

$(document).ready(function() {
	$(".layout-toolbar .statusbar-cr .leftbar .refresh-tab a").on("click", function (){
		var $tab = $("#itempath");
		var $pgc = $("#page-content");
		var id = $(this).attr("wid");
		$tab.find("ul").find("li").removeClass("active");
		$tab.find("ul").find("li[wid='" + id + "']").addClass("active");
		if(typeof crtechRefreshPage == 'undefined'){
			$pgc.find(".page-tab").removeClass("active");
			$pgc.find(".page-tab[wid='" + id + "']").addClass("active");
			$pgc.find(".page-tab[wid='" + id + "']").find("iframe")[0].contentWindow.location.reload(true);
		}else{
			//调用陈杰的方法，关闭页面
			crtechRefreshPage(id);
		}
	});
	$(".layout-toolbar .statusbar-cr .leftbar .goback a").on("click", function (){
		if(typeof crtechGoBack == 'undefined'){
			$("#page-content").find(".page-tab.active").find("iframe")[0].contentWindow.back();
		}else{
			//调用陈杰方法
			crtechGoBack();
		}
	});
	$(".layout-toolbar .statusbar-cr .leftbar .forward a").on("click", function (){
		if(typeof crtechGoForward == 'undefined'){
			$("#page-content").find(".page-tab.active").find("iframe")[0].contentWindow.back.forward();
		}else{
			//调用陈杰方法
			crtechGoForward();
		}
	});
	
	
	$(".layout-toolbar .statusbar-cr .rightbar .left-tab a").on("click", function (){
		var $tab = $("#itempath");
		var width = $("#itempath").css("width").replace("px","")-68;
		var left = $("#itempath").find('ul.tab-ul').css("left").replace("px","").replace("-","")-0;
		if(left == 68){
			left = 0;
		}else{
			left = left + 68;
		}
		var tlength = $tab.find("ul.tab-ul li").length;
		if(left <= 132){
			left = 0;
		}else{
			left -= 132;
		}
		$(".layout-toolbar .statusbar-cr .rightbar .item a").addClass("disabled");
		if(left > 68){
			$(".layout-toolbar .statusbar-cr .rightbar .left-tab a").removeClass("disabled");
		}
		if(left + width < tlength * 132){
			$(".layout-toolbar .statusbar-cr .rightbar .right-tab a").removeClass("disabled");
		}
		left = left - 68;
		if(left <= 0){
			$("#itempath").find('ul.tab-ul').css("left", "68px");
		}else{
			$tab.find('ul.tab-ul').css("left", "-"+ left + "px");
		}
	});
	$(".layout-toolbar .statusbar-cr .rightbar .right-tab a").on("click", function (){
		var $tab = $("#itempath");
		var width = $("#itempath").css("width").replace("px","")-68;
		var left = $("#itempath").find('ul.tab-ul').css("left").replace("px","").replace("-","")-0;
		if(left == 68){
			left = 0;
		}else{
			left = left + 68;
		}
		var tlength = $tab.find("ul.tab-ul li").length;
		if(width + left >= tlength * 132){
			var left = tlength * 132 - width;
		}else{
			left +=132;
		}
		$(".layout-toolbar .statusbar-cr .rightbar .item a").addClass("disabled");
		if(left > 0){
			$(".layout-toolbar .statusbar-cr .rightbar .left-tab a").removeClass("disabled");
		}
		if(left + width < tlength * 132){
			$(".layout-toolbar .statusbar-cr .rightbar .right-tab a").removeClass("disabled");
		}
		left = left - 68;
		if(left <= 0){
			$("#itempath").find('ul.tab-ul').css("left", "68px");
		}else{
			$tab.find('ul.tab-ul').css("left", "-"+ left + "px");
		}
	});
	$(".layout-toolbar .statusbar-cr .rightbar .close-tabs a").on("click", function (){
		if(typeof crtechRefreshPage == 'undefined'){
			$.confirm("关闭全部页签？", function(r) {
				if (r) {
					$("#itempath > ul").find("li").each(function(){
						if($(this).attr("wid") != 'home-page'){
							$.close_tabwindow($(this).attr("wid"));
						}
					});
				}
			});
		}else{
			$("#itempath > ul").find("li").each(function(){
				if($(this).attr("wid") != 'home-page'){
					$.close_tabwindow($(this).attr("wid"));
				}
			});
		}
		
	});
});