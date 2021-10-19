$.extend({
	"initProduct": function(){
		$.call("application.auth.initProduct", {}, function(rtn){
			if(rtn){
				var products = rtn.resultset || [];
				var proDiv = $(".left-content ul");
				for(var i in products){
					var product = products[i];
					var html = [];
					html.push('  <li class="li-menu1"> ');
					html.push('    <a href="javascript:void(0);" ');
					html.push(' data-id="'+product.id+'" title="'+ product.name +'"');	
					html.push('		class="level1"><i class="');
					html.push(product.icon || 'icon-equalizer');
					html.push('"></i></a>');
					html.push('  </li>');
					proDiv.append(html.join(''));
				}
				proDiv.find(".li-menu1 a").on("click", function(){
					if (!$('div.Econtent').is(':visible')) {
						$(".Econtent .cls-ding").click();
					}
					proDiv.find(".li-menu1 a").removeClass("active");
					$(this).addClass("active");
					initMenu($(this).attr("data-id"), $(this).attr("title"));//根据产品初始化菜单，判断crtech对象存在调用crtech打开菜单，否则直接调用initmenu方法
				});
			}
		}, null, {nomask: true});
	}
});
$(document).ready(function(){
	$.initProduct();
	if (userinfo.id) {
		var _t = $(".left-content ul > li.li-menu1 > .user-pro");
		$("#menus-ul li[data-id='user-pro'] .snav-title.uname").text(userinfo.name);
		_t.attr("title", userinfo.name);
		if (userinfo.avatar) {
			_t.find(".img-circle").attr("src", cooperopcontextpath+ "/rm/s/application/" + userinfo.avatar + "S");
		}
	}
/*	$(".Econtent .Econtent-div ul .search_div[data-id='user-pro'] .profile").on("click", function(){
		$.openTabPage("application.mine.profile", "个人设置", 
				cooperopcontextpath + "/w/application/mine/profile.html", false, "");
	});
	$(".Econtent .Econtent-div ul li[data-id='user-pro'] .changepwd").on("click", function(){
		$.openTabPage("application.mine.changepwd", "修改密码", 
				cooperopcontextpath + "/w/application/mine/changepwd.html", false, "");
	});
	$(".Econtent .Econtent-div ul li[data-id='user-pro'] .logout").on("click", function(){
		if(typeof(crtechLogout) == 'undefined' || crtechLogout()) {
				$.logout();
		}
	});*/
	$('#scrollbox-product').slimScroll({
		  height: '100%',
		  size: "4px"
		});
});