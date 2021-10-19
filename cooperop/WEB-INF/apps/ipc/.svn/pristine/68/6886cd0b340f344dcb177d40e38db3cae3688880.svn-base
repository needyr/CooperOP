var refreshflag = false;
$(document).ready(function() {
	if (userinfo.id) {
		var _t = $(".page-header.navbar .top-menu .navbar-nav > li.dropdown-user > .dropdown-toggle");
		_t.find(".username").text(userinfo.name);
		if (userinfo.avatar) {
			_t.find(".img-circle").attr(
					"src",
					cooperopcontextpath + "/rm/s/application/"
							+ userinfo.avatar + "S");
			_t.find(".icon-circle").hide();
			_t.find(".img-circle").show();
		} else {
			_t.find(".img-circle").hide();
			if (userinfo.gender == '0') {
				_t.find(".icon-circle").find(".fa").addClass(
						"female");
			}
			_t.find(".icon-circle").show();
		}
	}
	QuickSidebar.init();
});

function openFrame(aobj, url) {
	$(".page-sidebar-menu").find("li").removeClass("active");
	aobj.parent().addClass("active");
	window.open(url, "mainframe");
}


function logout() {
	$.logout();
}

function changepwd() {
	$.modal(cooperopcontextpath + "/w/precheck/changepwd.html", "修改密码", {
	});
}
