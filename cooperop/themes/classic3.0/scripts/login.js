$(document).ready(function() {
	$(".lock-bottom").find(".account-div").on("click", function(){
		$(".page-body").find(".lock-body").hide();
		$(".page-body").find(".account-login").show();
		$(".lock-bottom").find(".qiehuan").show();
		$(this).hide();
		cancelfinger();
	});
	$(".lock-bottom").find(".weixin-div").on("click", function(){
		$(".page-body").find(".lock-body").hide();
		$(".page-body").find(".weixin-login").show();
		$(".lock-bottom").find(".qiehuan").show();
		$(this).hide();
		cancelfinger();
	});
	$(".lock-bottom").find(".zhiwen-div").on("click", function(){
		$(".page-body").find(".lock-body").hide();
		$(".page-body").find(".zhiwen-login").show();
		$(".lock-bottom").find(".qiehuan").show();
		$(this).hide();
		usefinger();
	});
});
function usefinger(){
	$.loginfinger({callback: function(rtn){
		if(rtn.redirect_url){
			location.href = cooperopcontextpath + rtn.redirect_url;
		}else{
			$.message("验证失败，请使用账号登陆！", function(){
				$(".lock-bottom").find(".account-div").trigger("click");
			});
		}
	}
	});
}
function cancelfinger(){
	$.cancelfinger();
}