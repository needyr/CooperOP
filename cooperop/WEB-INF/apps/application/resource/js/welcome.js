/**
 * 
 */
function initNotification() {
	var notify = $(".notification");
	if (notify.length > 0) {
		notify.on("click",  function() {
			var nid = $(this).attr("nid");
			if (nid) {
				var url = cooperopcontextpath + "/w/application/notification/detail.html";
				$.modal(url,$(this).attr("nsubject"),{
					id: nid,
					callback : function(rtn){
					}
				})
			}
		});
		$.call("application.notification.lastdetail", {}, function(rtn) {
			if (rtn) {
				if (rtn.header_image) {
					notify.css("background-image", "url('" + cooperopcontextpath + "/rm/s/application/" + rtn.header_image + "S')");
				} else {
					notify.css("background-image", "url('" + cooperopcontextpath + "/theme/layout/img/07.jpg')");
				}
				notify.find(".widget-blog-title").text(rtn.subject);
				notify.find(".widget-blog-subtitle").text($.formatdate(new Date(+rtn.pdate), "yyyy-MM-dd"));
				notify.attr("nid", rtn.id);
				notify.attr("nsubject", rtn.subject);
			} else {
				notify.css("background-image", cooperopcontextpath + "/theme/layout/img/07.jpg");
				notify.find(".widget-blog-title").text("暂无通知");
				notify.find(".widget-blog-subtitle").text("");
				notify.removeAttr("nid");
				notify.removeAttr("nsubject");
			}
		});
	}
}
function initNumtips() {
	var taskwarp = $(".tasknum");
	var emailwarp = $(".emailnum");
	var messagewarp = $(".messagenum");
	if (taskwarp.length > 0) {
		taskwarp.on("click", ".more", function() {
			top.$("#header_task_bar").find("a").click();
		});
		$.tasknum(function(num) {
			num = num || 0;
			if (num > 0) {
				taskwarp.find(".number").text(num);
			} else {
				taskwarp.find(".number").text("暂无");
			}
		});
	}
	if (emailwarp.length > 0) {
		emailwarp.on("click", ".more", function() {
			top.$("#header_inbox_bar").find("a").click();
		});
		$.emailnum(function(num) {
			num = num || 0;
			if (num > 0) {
				emailwarp.find(".number").text(num);
			} else {
				emailwarp.find(".number").text("暂无");
			}
		});
	}
	if (messagewarp.length > 0) {
		messagewarp.on("click", ".more", function() {
			top.$("#header_message_bar").find("a").click();
		});
		$.messagenum(function(num) {
			num = num || 0;
			if (num > 0) {
				messagewarp.find(".number").text(num);
			} else {
				messagewarp.find(".number").text("暂无");
			}
		});
	}
}
function initCategorys() {/*
	var categorys = $(".categorys");
	if (categorys.length > 0) {
		categorys.each(function() {
			var $c = $(this);
			var cid = $c.attr("cid");
			var limit = $c.attr("limit");
			if (cid) {
				$.call("oa.cms.category.navigate", {
					portal_category_id_parent: cid,
					is_navigation: 1
				}, function(rtn) {
					var html = [];
					for (var i in rtn) {
						html.push('<li class="' + (i == 0 ? 'active' : '') + '" cid="' + rtn[i].id + '" cname="' + rtn[i].name + '">');
						html.push('<a href="#category_tab_' + rtn[i].id + '" data-toggle="tab">');
						html.push(rtn[i].name);
						html.push('</a>');
						html.push('</li>');
					}
					$c.find(".nav-tabs").html(html.join(""));
					html = [];
					for (var i in rtn) {
						html.push('<div class="tab-pane fade' + (i == 0 ? ' active in' : '') + '" id="category_tab_' + rtn[i].id + '">');
						html.push('<ul class="feeds" cid="' + rtn[i].id + '"></ul>');
						html.push('</div>');
					}
					$c.find(".tab-content").html(html.join(""));
					$c.find(".tab-content").find(".feeds[cid]").each(function() {
						var $f = $(this);
						var ccid = $f.attr("cid");
						$.call("oa.cms.article.query", {portal_category_id: ccid, limit: limit, published: 1, sort: "is_top desc, is_hot desc, pdate desc"}, function(rtn2) {
							var html2 = [];
							for (var j in rtn2.resultset) {
								html2.push('<li>');
								html2.push('<a href="javascript:void(0)" aid="' + rtn2.resultset[j].id + '" asubject="' + rtn2.resultset[j].subject + '" title="' + rtn2.resultset[j].subject + '" class="article_link">');
								html2.push('	<div class="col1">');
								html2.push('		<div class="cont">');
								html2.push('			<div class="cont-col1">');
								if (rtn2.resultset[j].type == 'picture') {
									html2.push('				<div class="label label-sm label-warning">');
									html2.push('					<i class="fa fa-image"></i>');
								} else {
									html2.push('				<div class="label label-sm label-info">');
									html2.push('					<i class="fa fa-file-text-o"></i>');
								}
								html2.push('				</div>');
								html2.push('			</div>');
								html2.push('			<div class="cont-col2">');
								html2.push('				<div class="desc">');
								html2.push(rtn2.resultset[j].subject);
								if (+rtn2.resultset[j].is_top == 1) {
									html2.push(' <i class="fa fa-hand-o-up font-green" title="顶" style="margin-left: 3px"></i>');
								}
								if (+rtn2.resultset[j].is_hot == 1) {
									html2.push(' <i class="fa fa-fire font-red-flamingo" title="热" style="margin-left: 3px"></i>');
								}
								html2.push('				</div>');
								html2.push('			</div>');
								html2.push('		</div>');
								html2.push('	</div>');
								html2.push('	<div class="col2">');
								html2.push('		<div class="date">');
								html2.push($.formatdate(new Date(+rtn2.resultset[j].pdate), "yyyy-MM-dd HH:mm"));
								html2.push('		</div>');
								html2.push('	</div>');
								html2.push('</a>');
								html2.push('</li>');
							}
							$f.html(html2.join(""));
						});
					});
				});
				$c.find(".action").click(function() {
					var $la = $c.find(".nav-tabs").find(".active");
					var url = cooperopcontextpath + "/w/oa/cms/article/category.html?portal_category_id=" + $la.attr("cid") + "&name=" + $la.attr("cname");
					top.$(".page-content-tabs").open_tabwindow("category_" + $la.attr("cid"),"新闻：" + $la.attr("cname"), url);
				});
				$c.on("click", ".article_link", function() {
					var $al = $(this);
					var url = cooperopcontextpath + "/w/oa/cms/article/detail.html";
					$.modal(url,$al.attr("asubject"),{
						id: $al.attr("aid"),
						preview: true,
						callback : function(rtn){
						}
					})
				});
				$c.find(".scroller").slimScroll({
		            allowPageScroll: true, // allow page scroll when the element scroll is ended
		            size: '7px',
		            color: '#bbb',
		            wrapperClass: 'slimScrollDiv',
		            railColor: '#eaeaea',
		            position: 'right',
		            height: $c.find(".scroller").css("height"),
		            alwaysVisible: false,
		            railVisible: false,
		            disableFadeOut: true
		        });
			}
		});
	}
*/}
function initSalary(){
	/*$.call("hr.dep.post.salary.getSalaryDetail", {}, function(rtn) {
		
	})*/
}
$(document).ready(function() {
	initNotification();
	initNumtips();
	initCategorys();
	//initSalary();
});