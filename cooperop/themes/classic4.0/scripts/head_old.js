$.extend({
	"initHead": function(){
		$(".head .qyName").on("click", function(){
			if(typeof crtechCompany == 'undefined'){
				$(".tcDiv").toggle();
			}else{
				//调用陈杰显示机构页面
				crtechCompany('$(".tcDiv").toggle()');
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
						$(".qyName").html(jig.jigname+" <i class='fa fa-caret-down'></i>");
					}
				}else{
					if("1" == jig.is_default){
						$(".qyName").html(jig.jigname+" <i class='fa fa-caret-down'></i>");
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
			var old = +$("#header_task_bar").find("sapn").text();
			$("#header_task_bar").find("span").text(num);
			$("#header_task_bar").find("span").show();
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
			$("#header_notification_bar").find("span").hide();
		}
	});
	$.systemMessagenum(function(num) {
		num = num || 0;
		if (num > 0) {
			var old = +$("#header_suggestions_bar").find("span").text();
			$("#header_suggestions_bar").find("span").text(num);
			$("#header_suggestions_bar").find("span").show();
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
			$("#header_suggestions_bar").find("span").hide();
		}
	});
}
$(document).ready(function() {
	$("#header_notification_bar").on("click", function() {
		$.openTabPage("system_notification", "通知公告", cooperopcontextpath + "/w/application/notification/notification.html", true);
	});
	$("#header_suggestions_bar").on("click", function() {
		$.openTabPage("system_suggestions", "消息提醒", cooperopcontextpath + "/w/application/suggestions/list.html", true);
	});
	$("#header_task_bar").on("click",function() {
		$.openTabPage("task_mine", "待办事项", cooperopcontextpath + "/w/application/task/mine.html", true);
	});
	$("#header_logout_bar").on("click", function() {
		if (typeof(crtechLogout) != 'undefined') {
			crtechLogout();
		} else {
			$.logout();
		}
	});
	//refreshnum();
	//setInterval(refreshnum, 6000);
	if(typeof crtechCompany != 'undefined') { 
		initOrganization();
	}
});