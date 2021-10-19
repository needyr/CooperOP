function initMenu(rootPopedom, tilte){
	var $menu = $("#scrollbox2");
	var pm = $menu.find(".nav-list[data-id='" + rootPopedom + "']");
	$menu.find(".nav-list").hide();
	if(rootPopedom == "userinfo-pro"){
		$("#scrollbox3").parent().show();
		$("#scrollbox2").parent().hide();
		$("#scrollbox4").parent().hide();
		return;
	}else if(rootPopedom == "search-pro"){
		$("#scrollbox4").parent().show();
		$("#scrollbox2").parent().hide();
		$("#scrollbox3").parent().hide();
		return;
	}else{
		$("#scrollbox2").parent().show();
		$("#scrollbox3").parent().hide();
		$("#scrollbox4").parent().hide();
	}
	if(pm && pm.hasClass("is-ready")){
		pm.show();
		return;
	}else{
		$.call("application.auth.getMenuTree", {rootPopedom: rootPopedom}, function(rtn){
			if(rtn){
				var html = [];
				html.push('<div class="nav-list is-ready" data-id="'+ rootPopedom +'" > ');
				html.push(' <h5>'+ tilte +'</h5>');
				html.push('<ul class="navtree">');
				setMenu(html, rtn, rootPopedom);
				html.push('</ul>');
				html.push('</div>');
				$menu.find(".nav-list[data-id='" + rootPopedom + "']").remove();
				$menu.append(html.join(''));
				$menu.find(".nav-list[data-id='" + rootPopedom + "']").find("a.navbtn").on("click", function(){
					var $th_ = $(this);
					
					// -------------修改样式 start ygz 2020-08-26
					var last_li;
					$('.navElement').parent('li').removeClass('last-li');
					$('.navbtn').css({'color': '#333', 'background': ''});
					if($th_.attr('menu-code') == "#"){
						last_li = $th_.parent('.navElement').parent('li');
						//$th_.css({'font-weight': '600', 'background': 'radial-gradient(#b6d1f1, transparent)'});
					}else{
						last_li = $th_.parent('.navElement').parent('li').parent('ul').parent('.childNav').parent('li');
						$(last_li).addClass('last-li');
					}
					last_li.addClass('last-li');
					last_li.find('.navElement').first().find('a').css({'background': 'radial-gradient(#b6d1f1, transparent)'});
					$th_.css('color', '#1d99f0');
					// -------------修改样式 end
					
					var ext = $th_.find("span.tips-icon");
					if(ext){
						if(ext.hasClass("opened")){
							ext.removeClass("opened");
							$th_.parent().parent().find(".childNav:first").hide();
						}else{
							ext.addClass("opened");
							$th_.parent().parent().find(".childNav:first").show();
						}
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
										"product_code": ps[0]};
								url = "crtech://cs/bill";
							}else if($th_.attr("page-type") == 'query'){
								params = {"queryid": $th_.attr("page-lx"), "product_code": ps[0]};
								url = "crtech://cs/query";
							}else if($th_.attr("page-type") == 'materials'){
								params = {"product_code": ps[0],
										"fangabh": $th_.attr("page-lx"),	
									    "djbh": ""
									};
								url = "crtech://cs/materials";
							} else if($th_.attr("page-type") == 'audit'){
								params = {"shenhid": $th_.attr("page-lx"), "product_code": ps[0]};
								url = "crtech://cs/audit";
							}
							params.pageid = $th_.attr("menu-code");
							params = JSON.stringify(params);
						}
						var page_params_str = $th_.attr("menu-pageparams");
						if(page_params_str){
							params = $.extend(params, JSON.parse(page_params_str.replace(/'/g, '"')));
						}
						$.openTabPage($th_.attr("menu-code"), $th_.text(), url, false, params);
					}
				});
				$menu.find(".nav-list[data-id='" + rootPopedom + "']").find(".collection").on("click", function(){
					//收藏 action
					var $pt = $(this).parent();
					var $pa = $pt.find("a.navbtn");
					//var addItem = $pt.clone(true);
					var $th = $(this);
					if($th.hasClass("store")){
						$.call("application.storeMenus.delStore", {system_popedom_id: $pa.attr("data-id")}, function(rtn){
							if(rtn){
								$th.removeClass("store");
								$th.find(".cicon").removeClass("icon-star3");
								$th.find(".cicon").addClass("icon-star");
								$("#scrollbox3 .nav-list ul.sc.navtree").find("li[data-id='"+$pa.attr("data-id")+"']").remove();
							}
						}, null, {nomask: true});
					}else{
						$.call("application.storeMenus.store", {system_popedom_id: $pa.attr("data-id")}, function(rtn){
							if(rtn){
								$th.addClass("store");
								$th.find(".cicon").addClass("icon-star3");
								$th.find(".cicon").removeClass("icon-star");
								var html1 = [];
								html1.push('    <li data-id="' + $pa.attr("data-id") + '">');
								html1.push('    <div class="navElement">');//extended
								html1.push('      <a href="javascript:void(0);" ');
								html1.push('  menu-code="' + $pa.attr("menu-code") + '"  ');
								html1.push('  data-id="' + $pa.attr("data-id") + '"  ');
								html1.push('  page-lx="' + $pa.attr("page-lx") + '"  ');
								html1.push('  page-bs="' + $pa.attr("page-bs") + '"  ');
								html1.push('  page-type="' + $pa.attr("page-type") + '"  ');
								html1.push('  is-cs="' + $pa.attr("is-cs") + '"  ');
								html1.push(' class="navbtn">'+$pa.attr("menu-name"));
								html1.push('   		</a> ');
								html1.push('<a class="function close1" href="javascript:void(0);"><i class="cicon icon-close"></i></a>');
								html1.push('   	</div> ');
								html1.push(' </li> ');
								var sccd = $(html1.join(''));
								sccd.find(".function.close1").on("click", function(){
									$menu.find("a[data-id='" + $(this).parents(".navElement").find("a").attr("data-id") + "']").parent().find(".icon-star3").trigger("click");
									$(this).parents("li[data-id='"+$pa.attr("data-id")+"']").remove();
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
														"product_code": ps[0]};
												url = "crtech://cs/bill";
											}else if($th_.attr("page-type") == 'query'){
												params = {"queryid": $th_.attr("page-lx"), "product_code": ps[0]};
												url = "crtech://cs/query";
											}else if($th_.attr("page-type") == 'materials'){
												params = {"product_code": ps[0],
														"fangabh": $th_.attr("page-lx"),	
													    "djbh": ""
													};
												url = "crtech://cs/materials";
											} else if($th_.attr("page-type") == 'audit'){
												params = {"shenhid": $th_.attr("page-lx"), "product_code": ps[0]};
												url = "crtech://cs/audit";
											}
											params.pageid = $th_.attr("menu-code");
											params = JSON.stringify(params);
										}
										$.openTabPage($th_.attr("menu-code"), $th_.text(), url, false, params);
									}
								});
								$("#scrollbox3 .nav-list ul.sc.navtree").append(sccd);
							}
						}, null, {nomask: true});
					}
				});
				/*$menu.find(".nav-list[data-id='" + rootPopedom + "']").find(".icon-star3").on("click", function(){
					//取消收藏 action
					var $pt = $(this).parent().parent();
					var $pa = $pt.find("a");
					$.call("application.storeMenus.delStore", {system_popedom_id: $pa.attr("data-id")}, function(rtn){
						if(rtn){
							$th.parent().removeClass("store");
							$("#scrollbox3 .nav-list ul.sc.navtree").find(".li[data-id='"+$pa.attr("data-id")+"']").remove();
						}
					}, null, {nomask: true});
				});*/
			}
		}, null, {nomask: true});
	}
}
function setMenu(html, rtn, parent_id){
	for(var i in rtn){
		var menu = rtn[i];
		if(menu.system_popedom_id_parent == parent_id){
			var level = menu.level - 1;
			html.push('    <li>');
			html.push('    <div class="navElement">');//extended
	        html.push('      <a href="javascript:void(0);" ');
	        html.push('  child-num="' + menu.child_num + '"  ');
	        html.push('  menu-code="' + menu.code + '"  ');
			html.push('  menu-pageparams="' + menu.page_params + '"  ');
	        html.push('  data-id="' + menu.id + '"  ');
	        html.push('  page-lx="' + menu.page_lx + '"  ');
	        html.push('  page-bs="' + menu.page_bs + '"  ');
	        html.push('  page-type="' + menu.page_type + '"  ');
	        html.push('  is-cs="' + menu.is_cs_page + '"  ');
	        html.push('  menu-name="' + menu.name + '"  ');
	        html.push(' class="navbtn" title="' + menu.name + '"><span class="tips-icon">');
	        if(menu.child_num > 0){
	        	html.push('<i class="cicon icon-dropup"></i>');
	        }
	        html.push('</span> '+menu.name+'</a>');
	        if(menu.child_num > 0){
	        	
	        }else{
	        	html.push('<a class="function collection '+((menu.is_store)?'store':'')+'" href="#"><i class="cicon icon-star'+((menu.is_store)?'3':'')+'"></i></a> ');
	        }
	        html.push('</div>');
	        
	        if(menu.child_num > 0){
	        	html.push('<div class="childNav">');
	        	html.push('<ul>');
	        	setMenu(html, rtn, menu.id);
	        	html.push('</ul>');
	        	html.push('</div>');
	        }
	        html.push('</li>');
		}
	}
}
function initStoreMenus(){//初始化收藏菜单
	$.call("application.storeMenus.query", {}, function(rtn){
		if(rtn){
			var smenus = rtn.resultset;
			$.each(smenus, function(i, menu){
				var html = [];
				html.push('    <li data-id="' + menu.id + '">');
				html.push('    <div class="navElement">');//extended
		        html.push('      <a href="javascript:void(0);" ');
		        html.push('  child-num="' + menu.child_num + '"  ');
		        html.push('  menu-code="' + menu.code + '"  ');
				html.push('  menu-pageparams="' + menu.page_params + '"  ');
		        html.push('  data-id="' + menu.id + '"  ');
		        html.push('  page-lx="' + menu.page_lx + '"  ');
		        html.push('  page-bs="' + menu.page_bs + '"  ');
		        html.push('  page-type="' + menu.page_type + '"  ');
		        html.push('  is-cs="' + menu.is_cs_page + '"  ');
		        html.push('  menu-name="' + menu.name + '"  ');
		        html.push(' class="navbtn">'+menu.name);
				html.push('   		</a> ');
				html.push('<a class="function close1" href="javascript:void(0);"><i class="cicon icon-close"></i></a>');
				html.push('   	</div> ');
				html.push(' </li> ');
				var sccd = $(html.join(''));
				sccd.find(".function.close1").on("click", function(){
					var $tt = $(this);
					var delid = $(this).parents("li").attr("data-id");
					$.call("application.storeMenus.delStore", {system_popedom_id: delid}, function(rtn){
						if(rtn){
							$("#scrollbox2").find("a[data-id='" + delid + "']").parent().find(".function.close1").removeClass("store");
							$tt.parents("li").remove();
						}
					}, null, {nomask: true});
				});
				sccd.find(".navElement").on("click", "a", function(){
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
										"product_code": ps[0]};
								url = "crtech://cs/bill";
							}else if($th_.attr("page-type") == 'query'){
								params = {"queryid": $th_.attr("page-lx"), "product_code": ps[0]};
								url = "crtech://cs/query";
							}else if($th_.attr("page-type") == 'materials'){
								params = {"product_code": ps[0],
										"fangabh": $th_.attr("page-lx"),	
									    "djbh": ""
									};
								url = "crtech://cs/materials";
							} else if($th_.attr("page-type") == 'audit'){
								params = {"shenhid": $th_.attr("page-lx"), "product_code": ps[0]};
								url = "crtech://cs/audit";
							}
							params.pageid = $th_.attr("menu-code");
							params = JSON.stringify(params);
						}
						$.openTabPage($th_.attr("menu-code"), $th_.text(), url, false, params);
					}
				});
				$("#scrollbox3 .nav-list ul.sc.navtree").append(sccd);
			})
		}
	});
}
function toggleMenu(){

}
function toggleOrg(){
	if($(".layout-floatmenu").hasClass("invisible")){
		//$(".layout-siderbar").addClass("hide");
		$(".layout-floatmenu").removeClass("invisible");
	}else{
		//$(".layout-siderbar").removeClass("hide");
		$(".layout-floatmenu").addClass("invisible");
	}
}
function initOrganization(){
	$(".layout-floatmenu .left-menu").find("a.shut-off-btn").on("click", function(){
		if(typeof crtechCompany == 'undefined'){
			toggleOrg();
		}else{
			//调用陈杰显示机构页面
			crtechCompany('toggleOrg();');
		}
	});
	$.call("application.auth.queryOrganization", {}, function(rtn){
		if(rtn){
			// ygz.2021-03-18：授权时间即将过期提醒
			if(rtn.app_info && rtn.app_info.app){
				$("#header_notice_bar").append('<i class="fa fa-volume-up"></i> 产品授权即将到期（剩余' + rtn.app_info.time + "天）");
				$("#header_notice_bar").append("，售后电话：028-62378387，邮箱：123456@163.com");
				var msg = '告知：亲爱的用户您好，感谢您对我司的长期信任与支持！由于产品使用期限即将到期。到期时间：<font style="color: red; font-weight: 600;">'
					+ rtn.app_info.date + "（剩余" + rtn.app_info.time + "天）</font>"
					+ "，为避免对您的日常工作造成影响，请尽快与我司售后人员联系进行产品的二次授权，"
					+ "如因未及时联系我司而造成程序不能正常使用，本司不承担任何相关责任与后果！！！"
				$.tips("warning", msg + "售后电话：028-62378387，邮箱：123456@163.com");
			}

			var curr_jigid = rtn.curr_jigid;
			var jigs = rtn.jigs;
			for(var i in jigs){
				var jig = jigs[i];
				var html = [];
				if(curr_jigid){
					if(curr_jigid == jig.jigid){
						html.push('<li><a class="active" href="javascript:void(0);" data-id="'+ jig.jigid +'" title="'+jig.jigname+'">');
						html.push('<i class="cicon icon-radio2"></i>'+jig.jigname);
					}else{
						html.push('<li><a href="javascript:void(0);" data-id="'+ jig.jigid +'" title="'+jig.jigname+'" >');
						html.push('<i class="cicon icon-radio"></i>'+jig.jigname);
					}
				}else{
					if("1" == jig.is_default){
						html.push('<li><a class="active" href="javascript:void(0);" data-id="'+ jig.jigid +'" title="'+jig.jigname+'" >');
						html.push('<i class="cicon icon-radio2"></i>'+jig.jigname);
					}else{
						html.push('<li><a href="javascript:void(0);" data-id="'+ jig.jigid +'" title="'+jig.jigname+'" >');
						html.push('<i class="cicon icon-radio"></i>'+jig.jigname);
					}
				}
				html.push('</a></li>');
				$("#scrollbox3 .enterprise-list ul").append(html.join(''));
			}
			$("#scrollbox3 .enterprise-list ul li a").on("click", function(){
				$.call("application.auth.changeJG", {jigid: $(this).attr("data-id"), jigname: $(this).attr("title")}, function(rt){
					if(rt){
						if(typeof crtechTogglePage == 'undefined'){
							location.reload();
						}else{
							//调用陈杰刷新页面，并发送新的登录信息给cs程序
//							crtechCompany('$(".tcDiv").toggle()');
//							crtechRefreshPage("ALL");
							login_cs(window.location.protocol+"//"+window.location.host, rt);
						}
					}
				});
			});
		}
	}, null, {nomask: true});
}

function searchMenu(searchContent){
	var html = [];
	$('#scrollbox4').find("ul").html("");
    $.call("application.auth.searchMenu", {searchName: searchContent}, function(rtn){
		if(rtn){
			var smenus = rtn.resultset;
			$.each(smenus, function(i, menu){
				if(menu.code && menu.code != '#'){
					var html = [];
					html.push('    <li data-id="' + menu.id + '">');
					html.push('    <div class="navElement">');//extended
			        html.push('      <a href="javascript:void(0);" ');
			        html.push('  child-num="' + menu.child_num + '"  ');
			        html.push('  menu-code="' + menu.code + '"  ');
					html.push('  menu-pageparams="' + menu.page_params + '"  ');
			        html.push('  data-id="' + menu.id + '"  ');
			        html.push('  page-lx="' + menu.page_lx + '"  ');
			        html.push('  page-bs="' + menu.page_bs + '"  ');
			        html.push('  page-type="' + menu.page_type + '"  ');
			        html.push('  is-cs="' + menu.is_cs_page + '"  ');
			        html.push('  menu-name="' + menu.name + '"  ');
			        html.push(' class="navbtn"><span class="tips-icon"></span>'+menu.name);
					html.push('   		</a> ');
					html.push('   	</div> ');
					html.push(' </li> ');
					var sccd = $(html.join(''));
					sccd.find("a").on("click", function(){
						var $th_ = $(this);
						var u = $th_.attr("menu-code").replace(/\./g, "/");
						var url = cooperopcontextpath + "/w/" + u+".html";
						var params = {};
						if($th_.attr("is-cs") == '1'){
							var ps = $th_.attr("menu-code").split(".");
							if($th_.attr("page-type") == 'bill'){
								params = {"djlx": $th_.attr("page-lx"),
										"djbs": $th_.attr("page-bs"),
										"djbh": "",
										"product_code": ps[0]};
								url = "crtech://cs/bill";
							}else if($th_.attr("page-type") == 'query'){
								params = {"queryid": $th_.attr("page-lx"), "product_code": ps[0]};
								url = "crtech://cs/query";
							}else if($th_.attr("page-type") == 'materials'){
								params = {"product_code": ps[0],
										"fangabh": $th_.attr("page-lx"),	
									    "djbh": ""
									};
								url = "crtech://cs/materials";
							} else if($th_.attr("page-type") == 'audit'){
								params = {"shenhid": $th_.attr("page-lx"), "product_code": ps[0]};
								url = "crtech://cs/audit";
							}
							params.pageid = $th_.attr("menu-code");
							params = JSON.stringify(params);
						}
						$.openTabPage($th_.attr("menu-code"), $th_.text(), url, false, params);
						
					});
					$('#scrollbox4').find("ul").append(sccd);
				}
			});
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
function checkPwd(){
	var $pwd = $("#change_password").find("input");
	$.call("application.auth.queryZt", {pwd: $pwd.val()}, function(rtn){
		if(rtn){
			if(rtn.ztlist && rtn.ztlist.length>0){
				$("#scrollbox3 .nav-list ul.zt").remove();
				var html = [];
				html.push('<ul class="navtree zt">');
				html.push('	</ul>');
				$("#scrollbox3 .nav-list").append(html.join(''));
				for(var i= 0; i<rtn.ztlist.length; i++){
					var zt = rtn.ztlist[i];
					html = [];
					html.push('<li>');
					html.push('	<div class="navElement">');
					html.push('		<a class="navbtn">'+zt.name+'</a>');
					html.push('	</div>');
					html.push('</li>');
					var $html = $(html.join(''));
					$html.find("a").on("click", function(){
						if(typeof crtechTogglePage == 'undefined'){
							window.location.href = zt.url+"/sso.jsp?"+rtn._p;
						}else{
							$.getJSON(zt.url+"/w/application.auth.sso_v12.jsonp?callback=?", {vs: rtn.vs, ts: rtn.ts, uid: rtn.uid}, 
							    function(rr) {
									if(rr){
										login_cs(zt.url, rr);
									}
								});
						}
					});
					$("#scrollbox3 .nav-list ul.zt").append($html);
				}
				//$.openTabPage("application.login_zt", "登录账套", cooperopcontextpath + "/w/application/login_zt.html", false, {});
			}else{
				//退出
				$.logout();
			}
		}else{
			$pwd.val("");
			$("#change_password").find(".icon-advance").hide();
			$pwd.attr("placeholder", "密码输入错误！");
			$pwd.addClass("error");
		}
	});
}
function getLocalFullConfigs() {
	var obj = {};
	for (var i = 1; i < 4; i++) {
		var params = getLocalConfigs(i);
		for (var j = 0; j < params.length; j++) {
			obj[params[j].key] = params[j].value;
		}
	}
	return obj;
}

function getLocalConfigs(n) {
	var loginExJsonParams = localStorage.getItem("loginExJsonParams" + n);
	var params = [];
	if (!loginExJsonParams) {
		params = getLocalDefaultConfigs(n);
	} else {
		params = JSON.parse(loginExJsonParams);
	}
	return params;
}

function getLocalDefaultConfigs(n) {
	var h = [];
	if (n == 1) {
		h.push({"inx": "1", "key": "POS打印机端口", "value":"POS打印机端口", "description": "POS打印机端口"});
		h.push({"inx": "1", "key": "打开钱箱选择", "value":"", "description": "打开钱箱选择"});
		h.push({"inx": "1", "key": "打开钱箱指令", "value":"27,112,0,60,240", "description": "打开钱箱指令"});
		h.push({"inx": "1", "key": "默认打印机", "value":"", "description": "默认打印机"});
		h.push({"inx": "1", "key": "收款语音", "value":"否", "description": "收款语音"});
		h.push({"inx": "1", "key": "自动打开钱箱", "value":"否", "description": "自动打开钱箱"});
	} else if (n == 2) {
		h.push({"inx": "2", "key": "波特率", "value":"2400", "description": "波特率"});
		h.push({"inx": "2", "key": "金额灯显示指令", "value":"Chr(27)+Chr(115)+2", "description": "金额灯显示指令"});
		h.push({"inx": "2", "key": "客显端口", "value":"", "description": "客显端口"});
		h.push({"inx": "2", "key": "使用客显", "value":"否", "description": "使用客显"});
		h.push({"inx": "2", "key": "收款灯显示指令", "value":"Chr(27)+Chr(115)+3", "description": "收款灯显示指令"});
		h.push({"inx": "2", "key": "数字显示指令", "value":"Chr(27) + Chr(81) + Chr(65) + &number + Chr(13)", "description": "数字显示指令"});
		h.push({"inx": "2", "key": "找零灯显示指令", "value":"Chr(27)+Chr(115)+4", "description": "找零灯显示指令"});
	} else if (n == 3) {
		h.push({"inx": "3", "key": "LocalServer", "value":"", "description": "本地数据库 IP"});
		h.push({"inx": "3", "key": "LocalUserName", "value":"", "description": "本地数据库用户名"});
		h.push({"inx": "3", "key": "LocalPassword", "value":"", "description": "本地数据库密码"});
	}
	return h;
}
function login_cs (url_, rr){
	var params = {products:{}, BaseUserInfo: rr.BaseUserInfo};
	params["BaseUserInfo"]["clientid"] = rr._CRSID;
	for (var i = 0; i < rr.middbconfigs.length; i++) {
		var tmp = rr.middbconfigs[i];
		if (params["products"][tmp.system_product_code] == undefined) {
			params["products"][tmp.system_product_code] = {};
		}
		params["products"][tmp.system_product_code][tmp.key] = tmp.value;
	}
	var exParams = getLocalFullConfigs();
	Object.keys(params["products"]).forEach(function(key){
		$.extend(true, params["products"][key], exParams);
	});
	if (Object.getOwnPropertyNames(params["products"]).length < 1) {
		$.message("登陆失败，请配置中间件信息.");
		return;
	}
	crtechLogin(url_, rr._CRSID, $.toJSON(params), 1);
}
$(document).ready(function(){
	initFunc();
	initStoreMenus();
	initOrganization();
	if(userinfo.avatar){
		$("#scrollbox3 > .userinfo >.userhead > img").attr("src", cooperopcontextpath+ "/rm/s/application/" + userinfo.avatar + "S");
	}else{
		$("#scrollbox3 > .userinfo >.userhead > img").attr("src", cooperopcontextpath+ "/theme/img/avatar3_small.jpg");
	}
	$("#profile").on("click", function(){
		if(userinfo.supperUser == true){
			$.message("系统用户不能修改资料！");
		}else{
			$.openTabPage("application.mine.profile", "个人设置", 
					cooperopcontextpath + "/w/application/mine/profile.html", false, "");
		}
	});
	$("#changepwd").on("click", function(){
		$.openTabPage("application.mine.changepwd", "修改密码", 
				cooperopcontextpath + "/w/application/mine/changepwd.html", false, "");
	});
	$("#exitbtn").on("click", function(){
		$("#change_password").removeClass("invisible");
		$("#change_password").find("input").focus();
		/*if (typeof(crtechLogout) != 'undefined') {
			crtechLogout();
		} else {
			$.logout();
		}*/
	});
	$("#change_password").find("a").on("click", function(){
		$("#change_password").addClass("invisible");
		$("#scrollbox3 .nav-list ul.zt").remove();
	});
	$("#change_password").find(".icon-advance").on("click", function(){
		checkPwd();
	});
	$("#change_password").find("input").keyup(function(event) {
		if (event.keyCode == 13) {
			checkPwd();
		}else{
			var $pwd = $("#change_password").find("input");
			$pwd.removeClass("error");
			if($pwd.val()){
				$("#change_password").find(".icon-advance").show();
			}else{
				$("#change_password").find(".icon-advance").hide();
			}
			$pwd.attr("placeholder", "输入密码，按回车验证！");
		}
		var $pwd = $("#change_password").find("input");
		$pwd.removeClass("error");
		if($pwd.val()){
			$("#change_password").find(".icon-advance").show();
		}else{
			$("#change_password").find(".icon-advance").hide();
		}
		$pwd.attr("placeholder", "输入密码，按回车验证！");
	});
	$("#scrollbox3 > .userinfo >dl > dt").text(userinfo.name);
	$("#scrollbox3 > .userinfo >dl > dd").text(userinfo.baseDepName);
    $('#scrollbox4').find(".search-field input").on('keypress', function(event){ 
        if(event.keyCode == 13) {
        	searchMenu($(this).val());
        }
    });
    $('#scrollbox4').find(".search-field input").on('change', function(event){ 
        if(!$(this).val()) {
        	$('#scrollbox4').find("ul").html("");
        }
    });
	$('#scrollbox2').slimScroll({
	  	height: '100%',
		size: '4px',

	});
	$('#scrollbox3').slimScroll({
	  	height: '100%',
		size: '4px',

	});
	$('#scrollbox4').slimScroll({
	  	height: '100%',
		size: '4px',

	});
	$("#scrollbox2").parent().hide();
	$("#scrollbox4").parent().hide();
});