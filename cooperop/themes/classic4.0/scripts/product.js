$.extend({
	"initProduct": function(){
		var flag = 0;
		$.call("application.auth.initProduct", {}, function(rtn){
			if(rtn){
				var products = rtn.resultset || [];
				var proDiv = $("#scrollbox1 ul");
				for(var i in products){
					var product = products[i];
					var html = [];
					html.push('  <li> ');
					html.push('    <a href="javascript:void(0);" ');
					 html.push('  child-num="' + product.child_num + '"  ');
				        html.push('  menu-code="' + product.code + '"  ');
				        html.push('  page-lx="' + product.page_lx + '"  ');
				        html.push('  page-bs="' + product.page_bs + '"  ');
				        html.push('  page-type="' + product.page_type + '"  ');
				        html.push('  is-cs="' + product.is_cs_page + '"  ');
				        html.push('  menu-name="' + product.name + '"  ');
					html.push(' data-id="'+product.id+'" title="'+ product.name +'"');	
					html.push('		><i class="');
					html.push(product.icon || 'cicon icon-data-setting');
					html.push('"></i></a>');
					html.push('  </li>');
					proDiv.append(html.join(''));
				}
				proDiv.find("li a").on("click", function(){
					var $th_ = $(this)
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
								params = {"queryid": $th_.attr("page-lx"),
										"product_code": ps[0]};
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
							
							params = JSON.stringify(params);
						}
						$.openTabPage($th_.attr("menu-code"), $th_.attr("menu-name"), url, false, params);
					}
					initMenu($(this).attr("data-id"), $(this).attr("title"));//根据产品初始化菜单，判断crtech对象存在调用crtech打开菜单，否则直接调用initmenu方法
					if(typeof crtechCompany == 'undefined'){
						if($(".container").hasClass("siderbar-mini")){
							$(".container").removeClass("siderbar-mini");
						}
						proDiv.find("li a").removeClass("active");
						$th_.addClass("active");
					}else{
						crtechMenuToggle(1);
					}
					
				});
			}
		}, null, {nomask: true});
	}
});
$(document).ready(function(){
	$.initProduct();
	if (userinfo.id) {
		var _t = $("#scrollbox1 ul > li.user-li > .userhead");
		_t.attr("title", userinfo.name);
		if (userinfo.avatar) {
			_t.find("img").attr("src", cooperopcontextpath+ "/rm/s/application/" + userinfo.avatar + "S");
		}else{
			_t.find("img").attr("src", cooperopcontextpath+ "/theme/img/avatar3_small.jpg");
		}
	}
});