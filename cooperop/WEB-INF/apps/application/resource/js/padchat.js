$(document).ready(function(){
	$(".footer .session").on("click", function(){
		$(".footer .footerdiv").removeClass("active");
		$(".content .leftMain .left-content").removeClass("active");
		$(this).addClass("active");
		$(".content .leftMain .listDiv").addClass("active");
		
	});
	$(".footer .contactor").on("click", function(){
		$(".footer .footerdiv").removeClass("active");
		$(".content .leftMain .left-content").removeClass("active");
		$(this).addClass("active");
		$(".content .leftMain .jg-listDiv").addClass("active");
	});
	$(".footer .group").on("click", function(){
		$(".footer .footerdiv").removeClass("active");
		$(".content .leftMain .left-content").removeClass("active");
		$(this).addClass("active");
		$(".content .leftMain .qz-listDiv").addClass("active");
	});
	
	$(".rightMain-mask").on("click", function(){
		$(this).removeClass("active");
		$(".right-content .rightMain1").removeClass("active");
	});
});

function openPage(url_){
	var suburl_ = '';
	if(url_.indexof("?") > -1){
		suburl_ = url_.substring(url_.indexof("?"));
		url_ = url_.substring(0, url_.indexof("?"));
	}
	var u = url_.replace(/\./g, "/");
	var url = "/w/" + u+".html";
	url = url+suburl_;
	$("#indexIframe_oth", window.parent.document).attr("src", url);
	$("#indexIframe_im", window.parent.document).hide();
	$("#indexIframe_oth", window.parent.document).show();
}