$.extend({
	"initHead": function(){
		var flag = 0;
		$(".layout-header .clearfix .switch a").on("click", function(){
			if(typeof crtechCompany == 'undefined'){
				if($(".container").hasClass("siderbar-mini")){
					$(".container").removeClass("siderbar-mini");
				}else{
					$(".container").addClass("siderbar-mini");
				}
			}else{
				if(flag == 0){
					crtechMenuToggle(flag)
					flag = 1;
				}else{
					crtechMenuToggle(flag)
					flag = 0;
				}
			}
		});
		$("#enterprise a").html(userinfo.jigname+'<i class="cicon icon-switch"></i>');
		$("#enterprise a").on("click", function(){
			if(typeof crtechCompany == 'undefined'){
				if($(".layout-floatmenu").hasClass("invisible")){
				//	$(".layout-siderbar").addClass("hide");
					$(".layout-floatmenu").removeClass("invisible");
				}else{
				//	$(".layout-siderbar").removeClass("hide");
					$(".layout-floatmenu").addClass("invisible");
				}
			}else{
				//调用陈杰显示机构页面
				crtechCompany('toggleOrg();');
			}
		});
	}
});
function initOrganization(){
	$.call("application.auth.queryOrganization", {}, function(rtn){
		if(rtn){
			var curr_jigid = rtn.curr_jigid;
			var jigs = rtn.jigs;
			for(var i in jigs){
				var jig = jigs[i];
				if(curr_jigid){
					if(curr_jigid == jig.jigid){
						$("#enterprise a").html(jig.jigname+' <i class="cicon icon-switch"></i>');
					}
				}else{
					if("1" == jig.is_default){
						$("#enterprise a").html(jig.jigname+' <i class="cicon icon-switch"></i>');
					}
				}
			}
		}
	}, null, {nomask: true});
}
function refreshnum(){
	$.tasknum(function(num) {
		num = num || 1;
		if (num > 0) {
			var old = +$("#header_task_bar").attr("data-id");
			$("#header_task_bar").find("span").text(num);
			$("#header_task_bar").find("span").show();
			$("#header_task_bar").attr("class", "remind")
			$("#header_task_bar").attr("data-id", num);
			if (old < num) {
				if(typeof crtechNotify == 'undefined'){
					$.winNotify({
						id: "new_task",
						title: "待办事项提醒",
						content: "您有" + num + "项待办事项，请及时处理。",
						clickback: function() {
							$("#header_task_bar").click();
							window.focus();
						}
					});
				}else{
					crtechNotify(url, 1);
				}
			}
		} else {
			$("#header_task_bar").find("span").text('');
			$("#header_task_bar").find("span").hide();
		}
	});
	/*$.notificationnum(function(rtn) {
		var num = rtn.num || 0;
		if (num > 0) {
			var old = +$("#header_notification_bar").attr("data-id");
			$("#header_notification_bar").find("span").text(num);
			$("#header_notification_bar").find("span").show();
			$("#header_notification_bar").attr("class", "remind")
			$("#header_notification_bar").attr("data-id", num);
			if (old < num) {
				if(typeof crtechNotify == 'undefined'){
					$.winNotify({
						id: "new_notification",
						title: "通知公告提醒",
						content: "您有" + num + "条未读通知公告，请及时查看。",
						clickback: function() {
							$("#header_notification_bar").click();
							window.focus();
						}
					});
				}else{
					crtechNotify(url, 1);
				}
			}
		} else {
			$("#header_notification_bar").find("span").text('');
			$("#header_notification_bar").find("span").hide();
		}
	});*/
	$.messagenum(function(num) {
		num = num || 0;
		if (num > 0) {
			$("#header_suggestions_bar").find("span").text(num);
			$("#header_suggestions_bar").find("span").show();
			var old = +$("#header_suggestions_bar").attr("data-id");
			$("#header_suggestions_bar").attr("class", "remind")
			$("#header_suggestions_bar").attr("data-id", num);
			if (old < num) {
				if(typeof crtechNotify == 'undefined'){
					$.winNotify({
						id: "new_suggestions",
						title: "消息提醒",
						content: "您有" + num + "条消息，请及时查看。",
						clickback: function() {
							$("#header_suggestions_bar").click();
							window.focus();
						}
					});
				}else{
					crtechNotify(url, 1);
				}
			}
		} else {
			$("#header_suggestions_bar").find("span").text('');
			$("#header_suggestions_bar").find("span").hide();
		}
	});
}
$(document).ready(function() {
	$("#header_notification_bar").on("click", function() {
		$.openTabPage("system_notification", "通知公告", cooperopcontextpath + "/w/oa/notice/mine.html", true);
	});
	$("#header_suggestions_bar").on("click", function() {
		$.openTabPage("system_suggestions", "消息提醒", cooperopcontextpath + "/w/application/pad/welcome.html", true);
	});
	$("#header_task_bar").on("click",function() {
		$.openTabPage("task_mine", "待办事项", cooperopcontextpath + "/w/application/task/mine.html", true);
	});
	$("#header_logout_bar").on("click", function() {
		if (typeof(crtechLogout) != 'undefined') {
			crtechLogout();
		}else if(typeof(crtech) != 'undefined'){
			crtech.logout();
		}else {
			$.logout();
		}
	});
	$("#min_").on("click",function() {
		crtechMinimize();
	});
	$("#max_").on("click",function() {
		crtechMaximize();
		var $max_ = $(this).find("i");
		if($max_.hasClass("icon-imize-default")){
			$max_.removeClass("icon-imize-default");
			$max_.addClass("icon-imize-max")
		}else{
			$max_.addClass("icon-imize-default");
			$max_.removeClass("icon-imize-max")
		}
	});
	$("#close_").on("click",function() {
		crtechQuit();
	});
	$("#header_calendar_bar").on("click",function() {
		$.openTabPage("oa.calendar.calendar.list", "日历", cooperopcontextpath + "/w/oa/calendar/calendar/list.html", true);
	});
	refreshnum();
	setInterval(refreshnum, 180000);
	if(typeof crtechCompany != 'undefined') { 
		initOrganization();
	}
});