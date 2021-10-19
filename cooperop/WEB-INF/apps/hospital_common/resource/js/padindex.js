$.extend({
	"initmenus": function(){
		$.call("application.auth.initPadMenus", {} ,function(rtn){
			console.log(rtn.menus)
			if(rtn.menus){
				var html1 = [];
				var html2 = [];
				var html3 = [];
				var html4 = [];
				var html5 = [];
				html1.push('<div class="menuIcon">');
				html2.push('<div class="fltDiv menudiv1 menudiv0">');
				html2.push('<p class="menu-tile"></p>');
				html2.push('<ul>');
				html3.push('<div class="erDiv menudiv2">');
				html3.push('<p class="menu-tile"></p>');
				html3.push('<ul>');
				html4.push('<div class="sanDiv menudiv3">');
				html4.push('<p class="menu-tile"></p>');
				html4.push('<ul>');
				html5.push('<div class="siDiv menudiv4">');
				html5.push('<p class="menu-tile"></p>');
				html5.push('<ul>');
				
				html2.push('<li data-p-id="user0"> ');
				html2.push('<a href="javascript:void(0)" class="menu-lia" ');
				html2.push('data-id="user1" data-level="2" ');
				html2.push('data-child="0" ');
				html2.push('data-url="application.mine.profile" ');
				html2.push('data-name="个人设置"> ');
				html2.push('个人设置');
				html2.push('</a> ');
				html2.push('</li>');
				html2.push('<li data-p-id="user0"> ');
				html2.push('<a href="javascript:void(0)" class="menu-lia" ');
				html2.push('data-id="user1" data-level="2" ');
				html2.push('data-child="0" ');
				html2.push('data-url="application.mine.changepwd" ');
				html2.push('data-name="修改密码"> ');
				html2.push('修改密码');
				html2.push('</a> ');
				html2.push('</li>');
				html2.push('<li data-p-id="user0"> ');
				html2.push('<a href="javascript:void(0)" onclick="$.logout();" ');
				html2.push('data-id="user1" data-level="2" ');
				html2.push('data-child="0" ');
				html2.push('data-url="application.mine.changepwd" ');
				html2.push('data-name="注销用户"> ');
				html2.push('注销用户');
				html2.push('</a> ');
				html2.push('</li>');
				
				for(var i=0 ;i< rtn.menus.length; i++){
					var menu = rtn.menus[i];
					if(menu.level == "1"){
						html1.push('<i class="micon menu-lia ');
						html1.push(menu.icon||'icon-equalizer');
						html1.push('" data-id="'+menu.id+'" ');
						html1.push(' data-level="1" ');
						html1.push(' data-child="'+menu.child_num+'" ');
						html1.push(' data-url="'+menu.code+'" ');
						html1.push(' data-name="'+menu.name+'" ');
						html1.push(' title="'+menu.name+'"></i>');
						
					}else if(menu.level == "2"){
						html2.push('<li data-p-id="'+menu.system_popedom_id_parent+'"> ');
						html2.push('<a href="javascript:void(0)" class="menu-lia" ');
						html2.push('data-id="'+menu.id+'" data-level="2" ');
						html2.push('data-child="'+menu.child_num+'" ');
						html2.push('data-url="'+menu.code+'" ');
						html2.push('data-name="'+menu.name+'"> ');
						html2.push(menu.name);
						html2.push('</a> ');
						if(menu.child_num > 0 ){
							html2.push('<i class="fa fa-angle-right"></i>');
						}
						html2.push('</li>');
					}else if(menu.level == "3"){
						html3.push('<li data-p-id="'+menu.system_popedom_id_parent+'"> ');
						html3.push('<a href="javascript:void(0)" class="menu-lia" ');
						html3.push('data-id="'+menu.id+'" data-level="3" ');
						html3.push('data-child="'+menu.child_num+'" ');
						html3.push('data-url="'+menu.code+'" ');
						html3.push('data-name="'+menu.name+'"> ');
						html3.push(menu.name);
						html3.push('</a> ');
						if(menu.child_num > 0 ){
							html3.push('<i class="fa fa-angle-right"></i>');
						}
						html3.push('</li>');
					}else if(menu.level == "4"){
						html4.push('<li data-p-id="'+menu.system_popedom_id_parent+'"> ');
						html4.push('<a href="javascript:void(0)" class="menu-lia" ');
						html4.push('data-id="'+menu.id+'" data-level="4" ');
						html4.push('data-child="'+menu.child_num+'" ');
						html4.push('data-url="'+menu.code+'" ');
						html4.push('data-name="'+menu.name+'"> ');
						html4.push(menu.name);
						html4.push('</a> ');
						if(menu.child_num > 0 ){
							html4.push('<i class="fa fa-angle-right"></i>');
						}
						html4.push('</li>');
					}else if(menu.level == "5"){
						html5.push('<li data-p-id="'+menu.system_popedom_id_parent+'"> ');
						html5.push('<a href="javascript:void(0)" class="menu-lia" ');
						html5.push('data-id="'+menu.id+'" data-level="5" ');
						html5.push('data-child="'+menu.child_num+'" ');
						html5.push('data-url="'+menu.code+'" ');
						html5.push('data-name="'+menu.name+'"> ');
						html5.push(menu.name);
						html5.push('</a> ');
						if(menu.child_num > 0 ){
							html5.push('<i class="fa fa-angle-right"></i>');
						}
						html5.push('</li>');
					}
				}
				html1.push('</div>');
				html2.push('</ul>');
				html2.push('</div>');
				html3.push('</ul>');
				html3.push('</div>');
				html4.push('</ul>');
				html4.push('</div>');
				html5.push('</ul>');
				html5.push('</div>');
				$(".left-menu").append(html1.join(''));
				$(".menus-div").append(html2.join('')).append(html3.join('')).append(html4.join('')).append(html5.join(''));
			}
			
			
			$(".menu-lia").on("click", function(){
				var turl = $(this).data('url');
				if(turl != "#"){
					var beau_sp = $('.menu_name span');
					var is_had = 0;
					beau_sp.removeClass('choose_menu');
					beau_sp.each(function(){
						var surl = $(this).data('url');
						if(surl == turl){
							is_had = 1;
							$(this).addClass('choose_menu');
							return false;
						}
					});
					if(is_had == 0){
						if(beau_sp.length >= 5){
							beau_sp.eq(0).remove();
						}
						$('.menu_name').append('<span>'+ $(this).data('name') +'</span>');
						$('.menu_name span:last').addClass('choose_menu').data('url', turl);
						$('.menu_name span').bind("click",function(){
							$(this).addClass('choose_menu').siblings().removeClass('choose_menu');
							var xu = $(this).data("url").replace(/\./g, "/");
							var xurl = cooperopcontextpath + "/w/" + xu+".html";
							$("#indexIframe_oth").attr("src", xurl);
							$(".page-right-content.zzc").click();
						});
					}else{
						
					}
				}
				
				$(".siDiv").show();
				$(".sanDiv").show();
				$(".erDiv").show();
				$(".fltDiv").show();
				$(".page-right-content.zzc").show();
				var lev = $(this).attr("data-level");
				if(lev == '0'){
					$(".menuIcon").find("i").removeClass("color1");
					$(".fltDiv").removeClass("active");
					$(".erDiv").removeClass("active");
					$(".sanDiv").removeClass("active");
					$(".siDiv").removeClass("active");
					if($(this).hasClass("color1")){
						$(".siDiv").hide("active");
						$(".sanDiv").hide();
						$(".erDiv").hide();
						$(".fltDiv").hide();
						$(this).removeClass("color1");
						return;
					}else{
						$(this).addClass("color1");
					}
				}else if(lev == '1'){
					var f = $(".fltDiv").hasClass("active");
					$(".img-circle").removeClass("color1");
					$(".fltDiv").removeClass("active");
					$(".erDiv").removeClass("active");
					$(".sanDiv").removeClass("active");
					$(".siDiv").removeClass("active");
					if($(this).hasClass("color1")){
						if(f){
							$(".siDiv").hide();
							$(".sanDiv").hide();
							$(".erDiv").hide();
							$(".fltDiv").hide();
							$(this).removeClass("color1");
							return;
						}
					}else{
						$(this).parent().find("i").removeClass("color1");
						$(this).addClass("color1");
					}
				}else if(lev == "2"){
					$(this).parents("ul").find("li>a").removeClass("active");
					$(this).addClass("active");
					$(".erDiv").removeClass("active");
					$(".sanDiv").removeClass("active");
					$(".siDiv").removeClass("active");
				}else if(lev == "3"){
					$(this).parents("ul").find("li>a").removeClass("active");
					$(this).addClass("active");
					$(".sanDiv").removeClass("active");
					$(".siDiv").removeClass("active");
				}else if(lev == "4"){
					$(this).parents("ul").find("li>a").removeClass("active");
					$(this).addClass("active");
					$(".siDiv").removeClass("active");
				}
				
				var ch_n = $(this).attr("data-child");
				$(".menudiv"+lev).find("ul> li").hide();
				$(".menudiv"+lev).find("li[data-p-id='"+$(this).attr("data-id")+"']").show();
				if(ch_n && ch_n > 0){
					$(".menudiv"+lev).addClass("active");
					$(".menudiv"+lev).find("p").text($(this).attr("data-name"));
				}else{
					$("#indexIframe_im").hide();
					$("#indexIframe_oth").show();
					var u = $(this).attr("data-url").replace(/\./g, "/");
					var url = cooperopcontextpath + "/w/" + u+".html";
					$("#indexIframe_oth").attr("src", url);
					$(".page-right-content.zzc").click();
				}
			});
			$(".page-right-content.zzc").on("click", function(){
				$(".siDiv").hide();
				$(".sanDiv").hide();
				$(".erDiv").hide();
				$(".fltDiv").hide();
				$(".siDiv").removeClass("active");
				$(".sanDiv").removeClass("active");
				$(".erDiv").removeClass("active");
				$(".fltDiv").removeClass("active");
				$(this).hide();
			});
			
		});
	}
});

//

$(document).ready(function() {
	$("#header_im_bar").on("click", function() {
		$("#indexIframe_im").show();
		$("#indexIframe_oth").hide();
//		var url = cooperopcontextpath + "/w/application/pad/welcome.html";
//		$("#indexIframe_oth").attr("src", url);
		$(".page-right-content.zzc").click();
	});
	$("#header_notification_bar").on("click", function() {
		$("#indexIframe_im").hide();
		$("#indexIframe_oth").show();
		var url = cooperopcontextpath + "/w/application/notification/notification.html";
		$("#indexIframe_oth").attr("src", url);
		$(".page-right-content.zzc").click();
	});
	$("#header_suggestions_bar").on("click", function() {
		$("#indexIframe_im").hide();
		$("#indexIframe_oth").show();
		var url = cooperopcontextpath + "/w/application/suggestions/list.html";
		$("#indexIframe_oth").attr("src", url);
		$(".page-right-content.zzc").click();
	});
	$("#header_task_bar").on("click", function() {
		$("#indexIframe_im").hide();
		$("#indexIframe_oth").show();
		var url = cooperopcontextpath + "/w/application/task/mine.html";
		$("#indexIframe_oth").attr("src", url);
		$(".page-right-content.zzc").click();
	});
	$("#header_inbox_bar").on("click", function() {
		$("#indexIframe_im").hide();
		$("#indexIframe_oth").show();
		var url = cooperopcontextpath + "/w/application/email/mine.html";
		$("#indexIframe_oth").attr("src", url);
		$(".page-right-content.zzc").click();
	});
	refreshnum();
	//QuickSidebar.init();
	setInterval(refreshnum, 6000);
});

function refreshnum() {
	$.tasknum(function(num) {
		num = num || 1;
		if (num > 0) {
			var old = +$("#header_task_bar").find("sapn").text();
			$("#header_task_bar").find("span").text(num);
			$("#header_task_bar").find("span").show();
			if (old < num) {
				/*$.winNotify({
					id: "new_task",
					title: "待办事项提醒",
					content: "您有" + num + "项待办事项，请及时处理。",
					clickback: function() {
						$("#header_task_bar").find("a").click();
						window.focus();
					}
				});*/
			}
		} else {
			$("#header_task_bar").find("span").hide();
		}
	});
	$.notificationnum(function(num) {
		num = num || 0;
		if (num > 0) {
			var old = +$("#header_notification_bar").find("spann").text();
			$("#header_notification_bar").find("span").text(num);
			$("#header_notification_bar").find("span").show();
			if (old < num) {
				/*$.winNotify({
					id: "new_notification",
					title: "通知公告提醒",
					content: "您有" + num + "条未读通知公告，请及时查看。",
					clickback: function() {
						$("#header_notification_bar").find("a").click();
						window.focus();
					}
				});*/
			}
		} else {
			$("#header_notification_bar").find("span").hide();
		}
	});
	$.emailnum(function(num) {
		num = num || 0;
		if (num > 0) {
			var old = +$("#header_inbox_bar").find("span").text();
			$("#header_inbox_bar").find("span").text(num);
			$("#header_inbox_bar").find("span").show();
			if (old < num) {
				/*$.winNotify({
					id: "new_email",
					title: "邮件提醒",
					content: "您有" + num + "封未读邮件，请及时查看。",
					clickback: function() {
						$("#header_inbox_bar").find("a").click();
						window.focus();
					}
				});*/
			}
		} else {
			$("#header_inbox_bar").find("span").hide();
		}
	});
	$.suggestionsnum(function(num) {
		num = num || 0;
		if (num > 0) {
			var old = +$("#header_suggestions_bar").find("span").text();
			$("#header_suggestions_bar").find("span").text(num);
			$("#header_suggestions_bar").find("span").show();
			if (old < num) {
				/*$.winNotify({
					id: "new_suggestions",
					title: "消息提醒",
					content: "您有" + num + "条消息，请及时查看。",
					clickback: function() {
						$("#header_suggestions_bar").find("a").click();
						window.focus();
					}
				});*/
			}
		} else {
			$("#header_suggestions_bar").find("span").hide();
		}
	});
};
