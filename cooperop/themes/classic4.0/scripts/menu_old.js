function initMenu(rootPopedom, tilte){
	var $menu = $("#menus-ul");
	var pm = $menu.find("li[data-id='" + rootPopedom + "']");
	$menu.find("li").hide();
	if(pm && pm.hasClass("is-ready")){
		pm.show();
	}else{
		$.call("application.auth.getMenuTree", {rootPopedom: rootPopedom}, function(rtn){
			if(rtn){
				var html = [];
				html.push('<li data-id="'+ rootPopedom +'" class="is-ready"> ');
				html.push(' <p class="snav-title">'+ tilte +'</p>');
				html.push(' <div class="level1Div">');
				setMenu(html, rtn, rootPopedom);
				html.push('</div>');
				html.push('  </li> ');
				$menu.find("li[data-id='" + rootPopedom + "']").remove();
				$menu.append(html.join(''));
				$menu.find("li[data-id='" + rootPopedom + "']").find("a").on("click", function(){
					//$menu.find("a").removeClass("active");
					var $th_ = $(this);
					//$th_.addClass("active");
					var $pt = $th_.parent().parent();
					if($pt.hasClass("extended")){
						$pt.removeClass("extended");
						$pt.addClass("collapsed");
					}else{
						$pt.removeClass("collapsed");
						$pt.addClass("extended");
					}
					if($th_.attr("menu-code") && $th_.attr("menu-code") != '#'){
						var u = $th_.attr("menu-code").replace(/\./g, "/");
						var url = cooperopcontextpath + "/w/" + u+".html";
						var params = {};
						if($th_.attr("is-cs") == '1'){
							var ps = $th_.attr("menu-code").split(".");
							if($th_.attr("page-type") == 'bill'){
								params = {"djlx": $th_.attr("page-lx"),
										"djbs": $th_.attr("page-bs"),
										"djbh": "",
										"cpmc": ps[0]};
								url = "crtech://cs/bill";
							}else if($th_.attr("page-type") == 'query'){
								params = {"queryid": $th_.attr("page-lx")};
								url = "crtech://cs/query";
							}else if($th_.attr("page-type") == 'materials'){
								params = {};
								url = "crtech://cs/materials";
							} else if($th_.attr("page-type") == 'audit'){
								params = {"shenhid": $th_.attr("page-lx")};
								url = "crtech://cs/audit";
							}
							
							params = JSON.stringify(params);
						}
						$.openTabPage($th_.attr("menu-code"), $th_.text(), url, false, params);
					}
				});
				$menu.find("li[data-id='" + rootPopedom + "']").find(".icon-star").on("click", function(){
					//收藏 action
					var $pt = $(this).parent().parent();
					var $pa = $(this).parent().find("a");
					//var addItem = $pt.clone(true);
					$.call("application.storeMenus.store", {system_popedom_id: $pa.attr("data-id")}, function(rtn){
						if(rtn){
							$pt.addClass("store");
							var html1 = [];
							html1.push(' <div class="level1Div" data-id="'+$pa.attr("data-id")+'">');
							html1.push('   	<div class="menu-div">');
							html1.push('   		<div class="menu-bar">');
							html1.push('   		<a href="javascript:void(0);" ');
							html1.push('  menu-code="' + $pa.attr("menu-code") + '"  ');
							html1.push('  data-id="' + $pa.attr("data-id") + '"  ');
							html1.push('  page-lx="' + $pa.attr("page-lx") + '"  ');
							html1.push('  page-bs="' + $pa.attr("page-bs") + '"  ');
							html1.push('  page-type="' + $pa.attr("page-type") + '"  ');
							html1.push('  is-cs="' + $pa.attr("is-cs") + '"  ');
							html1.push('   	 		class="level1">' + $pa.attr("menu-name") + ' </a> ');
							html1.push('   		 <i class="cicon icon-del"></i> ');
							html1.push('   		</div> ');
							html1.push('   	</div> ');
							html1.push(' </div> ');
							var sccd = $(html1.join(''));
							sccd.find(".icon-del").on("click", function(){
								$menu.find("a[data-id='" + $(this).parents(".level1Div").attr("data-id") + "']").parent().find(".icon-star3").trigger("click");
								$(this).parents(".level1Div").remove();
							});
							sccd.find("a").on("click", function(){
								var $th_ = $(this);
								if($th_.attr("menu-code") && $th_.attr("menu-code") != '#'){
									var u = $th_.attr("menu-code").replace(/\./g, "/");
									var url = cooperopcontextpath + "/w/" + u+".html";
									var params = {};
									if($th_.attr("is-cs") == '1'){
										var ps = $th_.attr("menu-code").split(".");
										if($th_.attr("page-type") == 'bill'){
											params = {"djlx": $th_.attr("page-lx"),
													"djbs": $th_.attr("page-bs"),
													"djbh": "",
													"cpmc": ps[0]};
											url = "crtech://cs/bill";
										}else if($th_.attr("page-type") == 'query'){
											params = {"queryid": $th_.attr("page-lx")};
											url = "crtech://cs/query";
										}else if($th_.attr("page-type") == 'materials'){
											params = {};
											url = "crtech://cs/materials";
										}
										
										params = JSON.stringify(params);
									}
									$.openTabPage($th_.attr("menu-code"), $th_.text(), url, false, params);
								}
							});
							$("#menus-ul li[data-id='user-pro']").append(sccd);
						}
					}, null, {nomask: true});
				});
				$menu.find("li[data-id='" + rootPopedom + "']").find(".icon-star3").on("click", function(){
					//取消收藏 action
					var $pt = $(this).parent().parent();
					var $pa = $(this).parent().find("a");
					$.call("application.storeMenus.delStore", {system_popedom_id: $pa.attr("data-id")}, function(rtn){
						if(rtn){
							$pt.removeClass("store");
							$("#menus-ul li[data-id='user-pro']").find(".level1Div[data-id='"+$pa.attr("data-id")+"']").remove();
						}
					}, null, {nomask: true});
				});
			}
		}, null, {nomask: true});
	}
}
function setMenu(html, rtn, parent_id){
	for(var i in rtn){
		var menu = rtn[i];
		if(menu.system_popedom_id_parent == parent_id){
			var level = menu.level - 1;
			html.push('    <div class="menu-div collapsed '+((menu.is_store)?'store':'')+'">');
			html.push('    <div class="menu-bar">');//extended
	        html.push('      <a href="javascript:void(0);" ');
	        html.push('  child-num="' + menu.child_num + '"  ');
	        html.push('  menu-code="' + menu.code + '"  ');
	        html.push('  data-id="' + menu.id + '"  ');
	        html.push('  page-lx="' + menu.page_lx + '"  ');
	        html.push('  page-bs="' + menu.page_bs + '"  ');
	        html.push('  page-type="' + menu.page_type + '"  ');
	        html.push('  is-cs="' + menu.is_cs_page + '"  ');
	        html.push('  menu-name="' + menu.name + '"  ');
	        html.push(' class="level'+ level +'  ">');
	        
	        html.push(menu.name +'</a>');
	        if(menu.child_num > 0){
	        	html.push('<i class="crop-icon fa fa-angle-right"></i><i class="crop-icon fa fa-angle-down"></i> ');
	        }else{
	        	html.push('<i class="Collection cicon icon-star" title="添加为我的收藏菜单"></i> ');
	        	html.push('<i class="Collection cicon icon-star3" title="取消收藏"></i> ');
	        }
	        html.push('</div>');
	        
	        if(menu.child_num > 0){
	        	html.push('<div class="level'+ level +'-child child-div">');
	        	setMenu(html, rtn, menu.id);
	        	html.push('</div>');
	        }
	        html.push('</div>');
		}
	}
}
function initStoreMenus(){//初始化收藏菜单
	$.call("application.storeMenus.query", {}, function(rtn){
		if(rtn){
			var smenus = rtn.resultset;
			$.each(smenus, function(i, menu){
				var html = [];
				html.push(' <div class="level1Div" data-id="'+menu.id+'">');
				html.push('   	<div class="menu-div">');
				html.push('   		<div class="menu-bar">');
				html.push('   		<a href="javascript:void(0);" ');
				html.push('  child-num="' + menu.child_num + '"  ');
				html.push('  menu-code="' + menu.code + '"  ');
				html.push('  data-id="' + menu.id + '"  ');
				html.push('  page-lx="' + menu.page_lx + '"  ');
				html.push('  page-bs="' + menu.page_bs + '"  ');
				html.push('  page-type="' + menu.page_type + '"  ');
				html.push('  is-cs="' + menu.is_cs_page + '"  ');
				html.push('   	 		class="level1">' + menu.name + ' </a> ');
				html.push('   		 <i class="cicon icon-del"></i> ');
				html.push('   		</div> ');
				html.push('   	</div> ');
				html.push(' </div> ');
				var sccd = $(html.join(''));
				sccd.find(".icon-del").on("click", function(){
					var $tt = $(this);
					var delid = $(this).parents(".level1Div").attr("data-id");
					$.call("application.storeMenus.delStore", {system_popedom_id: delid}, function(rtn){
						if(rtn){
							$("#menus-ul").find("a[data-id='" + delid + "']").parent().parent().removeClass("store");
							$tt.parents(".level1Div").remove();
						}
					}, null, {nomask: true});
				});
				sccd.find("a").on("click", function(){
					var $th_ = $(this);
					if($th_.attr("menu-code") && $th_.attr("menu-code") != '#'){
						var u = $th_.attr("menu-code").replace(/\./g, "/");
						var url = cooperopcontextpath + "/w/" + u+".html";
						var params = {};
						if($th_.attr("is-cs") == '1'){
							var ps = $th_.attr("menu-code").split(".");
							if($th_.attr("page-type") == 'bill'){
								params = {"djlx": $th_.attr("page-lx"),
										"djbs": $th_.attr("page-bs"),
										"djbh": "",
										"cpmc": ps[0]};
								url = "crtech://cs/bill";
							}else if($th_.attr("page-type") == 'query'){
								params = {"queryid": $th_.attr("page-lx")};
								url = "crtech://cs/query";
							}else if($th_.attr("page-type") == 'materials'){
								params = {};
								url = "crtech://cs/materials";
							}
							
							params = JSON.stringify(params);
						}
						$.openTabPage($th_.attr("menu-code"), $th_.text(), url, false, params);
					}
				});
				$("#menus-ul li[data-id='user-pro']").append(sccd);
			})
		}
	});
}
function initOrganization(){
	$(".tcDiv .tcContent .retract").find(".icon-retract123").on("click", function(){
		if(typeof crtechCompany == 'undefined'){
			$(this).parents(".tcDiv").hide();
		}else{
			//调用陈杰显示机构页面
			crtechCompany('$(".tcDiv").toggle()');
		}
	});
	$.call("application.auth.queryOrganization", {}, function(rtn){
		if(rtn){
			var curr_jigid = rtn.curr_jigid;
			var jigs = rtn.jigs;
			for(var i in jigs){
				var jig = jigs[i];
				var html = [];
				if(curr_jigid){
					if(curr_jigid == jig.jigid){
						$(".qyName").html(jig.jigname+" <i class='fa fa-caret-down'></i>");
						html.push('<div class="tcDiv-s active" data-id="'+ jig.jigid +'">');
					}else{
						html.push('<div class="tcDiv-s" data-id="'+ jig.jigid +'">');
					}
				}else{
					if("1" == jig.is_default){
						$(".qyName").html(jig.jigname+" <i class='fa fa-caret-down'></i>");
						html.push('<div class="tcDiv-s active">');
					}else{
						html.push('<div class="tcDiv-s">');
					}
				}
				html.push('<p class="">'+ jig.jigname +'</p>');
				html.push('<i class="cicon icon-radio2"></i>');
				html.push('</div>');
				$(".tcDiv .tcContent").append(html.join(''));
			}
			$(".tcDiv .tcContent .tcDiv-s").on("click", function(){
				$.call("application.auth.changeJG", {jigid: $(this).attr("data-id")}, function(rt){
					if(rt){
						if(typeof crtechTogglePage == 'undefined'){
							location.reload();
						}else{
							//调用陈杰刷新页面，并发送新的登录信息给cs程序
							crtechCompany('$(".tcDiv").toggle()');
							crtechRefreshPage("ALL");
						}
					}
				});
			});
			$("#userinfo-name").find(".userinfo-name").html(userinfo.name);
			$("#userinfo-name").find(".icon-user").attr("src", cooperopcontextpath+ "/rm/s/application/" + userinfo.avatar + "S");
		}
	}, null, {nomask: true});
}
function searchMenu(searchContent){
	var html = [];
	$('#menus-ul li[data-id="search-pro"]').find(".search-span").html("");
    $.call("application.auth.searchMenu", {searchName: searchContent}, function(rtn){
		if(rtn){
			var smenus = rtn.resultset;
			$.each(smenus, function(i, menu){
				var html = [];
				html.push(' <div class="level1Div" data-id="'+menu.id+'">');
				html.push('   	<div class="menu-div">');
				html.push('   		<div class="menu-bar">');
				html.push('   		<a href="javascript:void(0);" ');
				html.push('  child-num="' + menu.child_num + '"  ');
				html.push('  menu-code="' + menu.code + '"  ');
				html.push('  data-id="' + menu.id + '"  ');
				html.push('  page-lx="' + menu.page_lx + '"  ');
				html.push('  page-bs="' + menu.page_bs + '"  ');
				html.push('  page-type="' + menu.page_type + '"  ');
				html.push('  is-cs="' + menu.is_cs_page + '"  ');
				html.push('   	 		class="level1">' + menu.name + ' </a> ');
				html.push('   		</div> ');
				html.push('   	</div> ');
				html.push(' </div> ');
				var sccd = $(html.join(''));
				sccd.find("a").on("click", function(){
					var $th_ = $(this);
					if($th_.attr("menu-code") && $th_.attr("menu-code") != '#'){
						var u = $th_.attr("menu-code").replace(/\./g, "/");
						var url = cooperopcontextpath + "/w/" + u+".html";
						var params = {};
						if($th_.attr("is-cs") == '1'){
							var ps = $th_.attr("menu-code").split(".");
							if($th_.attr("page-type") == 'bill'){
								params = {"djlx": $th_.attr("page-lx"),
										"djbs": $th_.attr("page-bs"),
										"djbh": "",
										"cpmc": ps[0]};
								url = "crtech://cs/bill";
							}else if($th_.attr("page-type") == 'query'){
								params = {"queryid": $th_.attr("page-lx")};
								url = "crtech://cs/query";
							}else if($th_.attr("page-type") == 'materials'){
								params = {};
								url = "crtech://cs/materials";
							}
							
							params = JSON.stringify(params);
						}
						$.openTabPage($th_.attr("menu-code"), $th_.text(), url, false, params);
					}
				});
				$('#menus-ul li[data-id="search-pro"]').find(".search-span").append(sccd);
			})
		}
	});
}

function initFunc() {
	$(".Econtent .cls-ding").on("click", function(){
		$('div.right-content.right-content1').css('left', $('div.right-content.right-content1').css('left') == '250px' ? '50px': '250px');
		$('div.Econtent').toggle();
		if(typeof crtechMenuToggle != 'undefined'){
			crtechMenuToggle();
		}
	});
}

$(document).ready(function(){
	initFunc();
	initStoreMenus();
	initOrganization();
	var $udiv = $("#menus-ul").find("li[data-id='user-pro']").find(".box");
	if(userinfo.avatar){
		$udiv.find(".boxImg img").attr("src", cooperopcontextpath+ "/rm/s/application/" + userinfo.avatar + "S");
	}
	$udiv.find(".boxBtn .profile").on("click", function(){
		$.openTabPage("application.mine.profile", "个人设置", 
				cooperopcontextpath + "/w/application/mine/profile.html", false, "");
	});
	$udiv.find(".boxBtn .changepwd").on("click", function(){
		$.openTabPage("application.mine.changepwd", "修改密码", 
				cooperopcontextpath + "/w/application/mine/changepwd.html", false, "");
	});
	console.log(userinfo);
	$udiv.find(".boxName .textName").text(userinfo.name);
	$udiv.find(".boxName .textZw").text(userinfo.baseDepName);
    $('#menus-ul li[data-id="search-pro"]').find(".search-ipt input").on('keypress',function(event){ 
        if(event.keyCode == 13) {
        	searchMenu($(this).val());
        }
    });
    $('#menus-ul li[data-id="search-pro"]').find(".search-ipt input").on('change',function(event){ 
        if(!$(this).val()) {
        	$('#menus-ul li[data-id="search-pro"]').find(".search-span").html("");
        }
    });
	$('#scrollbox').slimScroll({
		  height: '100%',
		  size: "4px"
		});
});