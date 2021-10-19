var yc = {
	show_page: "http://192.168.0.31:8085/w/hospital_common/showturns/detail.html",
	rtn_xml: ''
}
	init();
function init(){
	var real_page = yc.show_page +  document.URL.substring(document.URL.indexOf('?'));
	var iframe = '<iframe src="'+ real_page +'" style="width:934px; height:661px;border: none;"></iframe>';
	$('#main').append(iframe)
}

// iframe传值
window.addEventListener('message', function(rtn){
	// 取子页面数据
	var cdata = rtn.data;
	var ffun = cdata.ffun;
	delete cdata.ffun;
	//eval(ffun + '(cdata)');
	if('cback' == ffun){
		window.returnValue = cdata.crtn;
		yc.rtn_xml = cdata.crtn;
		console.log('中转页监听：' + cdata.crtn);
		window.close();
	}
}, false);

window.onbeforeunload = function(){
	if(yc.rtn_xml == ''){
		window.returnValue = '<RESPONE><STATE>0</STATE><USE_FLAG>0</USE_FLAG><FLAG>1</FLAG></RESPONE>'; // 直接改为返回调整参数
		return "您还未做出选择，若离开则默认为您选择【返回调整】";
	}
}

window.onunload = function(){
	 if(yc.rtn_xml == ''){
		console.log('用户点击x关闭页面，需要回传数据到服务器');
	}
}