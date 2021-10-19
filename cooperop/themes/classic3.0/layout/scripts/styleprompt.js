//新界面风格提示
$(function() {
	if($("#interface_style").val() == "metronic") {
		loadNewStyle();
		return;
	}
	
	if($("#styleprompt").val() == "false") {
		return;
	}
	modal("styleprompt.jsp","新界面风格提示", {
		width : 950,
		height : 550
	});
	
	function getcookie(objname){//获取指定名称的cookie的值
		var arrstr = document.cookie.split("; ");
		 for(var i = 0;i < arrstr.length;i ++){
		 var temp = arrstr[i].split("=");
		 if(temp[0] == objname) return unescape(temp[1]);
		 }
	}
});

//启用新界面风格
function useNewStyle() {
	var params = {};
	params["code"] = "interface_style";
	params["value"] = "metronic";
	params["system_product_code"] = "base";
	callAction("base.user.saveMineConfig", params, function(rtn) {
		if(rtn == "success") {
			loadNewStyle();
		}
	});
}

function loadNewStyle() {
	if(location.href.indexOf("e?page") > 0) {
		location.href = "index.jsp";
	}else {
		location.href = "home.jsp";
	}
}

function notprompt() {
	var Days = 30;
	var exp = new Date(); 
	exp.setTime(exp.getTime() + Days*24*60*60*1000);
	document.cookie = "styleprompt=false";
	closeModal();
}