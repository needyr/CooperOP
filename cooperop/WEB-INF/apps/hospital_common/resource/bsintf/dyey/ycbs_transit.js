var yc = {
	show_page: "http://192.168.1.183:8080/w/hospital_common/showturns/detail.html",
	simic_page: "http://192.168.1.183:8080/w/hospital_imic/simic/index.html",
	rtn_xml: ''
}
	init();
function init(){
	if(document.URL.indexOf('simic') >= 0){
		// 结算审核
		var real_page = yc.simic_page +  document.URL.substring(document.URL.indexOf('?'));
		var iframe = '<iframe src="'+ real_page +'" style="width:940px; height:667px;border: none;"></iframe>';
	}else{
		// 提交审核
		var real_page = yc.show_page +  document.URL.substring(document.URL.indexOf('?'));
		var iframe = '<iframe src="'+ real_page +'" style="width:940px; height:667px;border: none;"></iframe>';
	}
	$('#main').append(iframe);
}

//iframe传值
if(IEVersion() >= 0 && IEVersion() <=8){
	window.attachEvent("onmessage", function(rtn){
		var cdata = JSON.parse(rtn.data);
		var ffun = cdata.ffun;
		delete cdata.ffun;
		//eval(ffun + '(cdata)');
		if('cback' == ffun){
			yc.rtn_xml = cdata.crtn;
			window.returnValue = cdata.crtn;
			//alert('中转：' + yc.rtn_xml);
			//console.log('中转页监听：' + cdata.crtn);
			window.close();
		}
	 });
}else{
	window.addEventListener('message', function(rtn){
		// 取子页面数据
		var cdata = JSON.parse(rtn.data);
		var ffun = cdata.ffun;
		delete cdata.ffun;
		//eval(ffun + '(cdata)');
		if('cback' == ffun){
			//alert('中转：' + cdata.crtn);
			window.returnValue = cdata.crtn;
			yc.rtn_xml = cdata.crtn;
			//console.log('中转页监听：' + cdata.crtn);
			window.close();
		}
	}, false);
}

if(document.URL.indexOf('simic') < 0){
	window.onbeforeunload = function(){
		if(yc.rtn_xml == ''){
			window.returnValue = '<RESPONE><STATE>0</STATE><USE_FLAG>0</USE_FLAG><FLAG>1</FLAG></RESPONE>'; // 直接改为返回调整参数
			return "您还未做出选择，若离开则默认为您选择【返回调整】";
		}
	}
}


window.onunload = function(){
	 if(yc.rtn_xml == ''){
		//console.log('用户点击x关闭页面，需要回传数据到服务器');
	}
}

// 判断ie版本
function IEVersion() {
	var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
	var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器  
	var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器  
	var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
	if(isIE) {
		var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
		reIE.test(userAgent);
		var fIEVersion = parseFloat(RegExp["$1"]);
		if(fIEVersion == 7) {
			return 7;
		} else if(fIEVersion == 8) {
			return 8;
		} else if(fIEVersion == 9) {
			return 9;
		} else if(fIEVersion == 10) {
			return 10;
		} else {
			return 6;//IE版本<=7
		}   
	} else if(isEdge) {
		return 'edge';//edge
	} else if(isIE11) {
		return 11; //IE11  
	}else{
		return -1;//不是ie浏览器
	}
}