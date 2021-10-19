var ycfun = {
	version: "1.0.0",
	description: "德阳二院医保审核接口",
	his: "山东众阳",
	support: "四川医策科技",
	crtech_reg: 1,
	browser_version: 0
};
var IADRESS = 'http://localhost:8085';
var res_adress = 'http://localhost:8085';
var ycfunmap  = res_adress + '/res/hospital_common/bsintf/';
if(typeof($) == 'undefined'){document.write('<script type="text/javascript" src="_YCMAP_jquery.min.1.9.1.js"></script>'.replace('_YCMAP_', ycfunmap));};
if(typeof(layer) == 'undefined'){document.write('<script type="text/javascript" src="_YCMAP_layer.js"></script>'.replace('_YCMAP_', ycfunmap));}
//if(!$.isFunction($.xml2json2)){document.write('<script type="text/javascript" src="_YCMAP_xml2json.js"></script>'.replace('_YCMAP_', ycfunmap));console.log('加载$.xml2json');}
// document.write('<script type="text/javascript" src="_YCMAP_json2xml.js"></script>'.replace('_YCMAP_', ycfunmap)
var CRSHOW = IADRESS + '/w/hospital_common/showturns/detail.html';
var SIMICRSHOW = IADRESS + '/w/hospital_imic/simic/index.html';
var SIMPLEINFO = IADRESS + '/w/hospital_common/showturns/simpleinfo2.html';
var CFAILSHOW = IADRESS + '/w/hospital_common.showturns/ocresult.html';
var maxindex = 0;
var exenum = 0;
var lastcallback;
// his中转网页
//var ycbstransit = 'http://172.31.2.1/dthealth/web/csp/ycbstransit.html';
var ycbstransit = 'http://' + location.host + '/dthealth/web/csp/ycbstransit.html';
ycbstransit = 'http://' + location.host + '/ycfun2ie8/ycbstransit.html';

ycfun.browser_version = IEVersion();
if(ycfun.browser_version <= 8){
	alert('ie8以下');
	 window.attachEvent("onmessage", function(rtn){
		 
	 });
}else{
	// iframe跨域传值 (不支持ie8)
	try{
		window.addEventListener('message', function(rtn){
			// 取子页面数据
			var cdata = rtn.data;
			// 调用处理function
			var ffun = cdata.ffun;
			delete cdata.ffun;
			//eval(ffun + '(cdata)');
			if(cdata.close == 1){
				layer.close(maxindex); //关闭窗口
				exenum--;
			}
			if(lastcallback){
				if(cdata.crtn){
					lastcallback(cdata.crtn);
				}else{
					lastcallback('1');
				}
			}
		}, false);
	}catch(err){
		console.log(err);
	}
}

// 接口函数
function crInterfaceDoCall(command, inputData, callback){
	console.log(command);
	console.log(inputData);
	//inputData = inputData.toLowerCase();
	var inputData = '<root>' + inputData + '</root>';
	try{
		inputData = xml2json2(inputData);
	}catch(e){
		console.log(e.message);
		layer.alert('xml串格式不正确！');
		return -1;
	}
	if(exenum > 0){
		layer.msg('已有正在执行的命令');
		return;
	}
	var status_code = 1;
	if(command == ''){
		layer.msg('未选择命令！');
		return;
	}
	switch(command){
		case 'InterfaceInit':
			exenum ++;
			status_code = interfaceInit(inputData, callback);
			break;
		case 'PreCheck_Message':
			//status_code = preCheckMsg(inputData, callback);
			break;
		case 'PreCheck_Audit':
			exenum ++;
			status_code = preCheckAudit(inputData, callback);
			break;
		case 'PreCheck_SIMIC':
			exenum ++;
			status_code = preCheckSIMIC(inputData, callback);
			break;
		case 'InterfaceDestroy':
			status_code = interfaceDestroy(inputData, callback);
			break;
		case 'PreCheck_Search':
			status_code = preCheckSearch(inputData, callback);
			break;
		case 'test': break;
		default : status_code = -10 ;layer.msg('暂未收录该命令！');
	}
	return status_code;
}

// 初始化
function interfaceInit(inputData, callback){
	// 验参
	if(!paramsVerify(inputData)){
		exenum --;
		callback('<RESPONE><STATE>1</STATE><USE_FLAG>1</USE_FLAG><FLAG>-2</FLAG></RESPONE>');
		return -1;
	}
	// 启动/刷新im客户端
	setTimeout(function(){refreshIM(inputData);}, 3000)
	var reqdata = {json: JSON.stringify(inputData), isbs: 1};
	
	jQuery.support.cors = true
	$.ajax({
		"async": true,
		"dataType" : "json",
		"type" : "POST",
		"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
		// "cache" : false,
		"url" : IADRESS + "/prescripitoninit",
		"timeout" : 10000,
		"data" : reqdata ,
		"success" : function(rtn) {
			if("1" == rtn.isopen){
				maxindex = layer.open({
				  type: 2,
				  title: 'iadscp - 患者质控预警信息',
				  closeBtn: 0,
				  area: ['713px', '638px'],
				  fixed: false,
				  content: IADRESS + rtn.url
				});
			}else{
				exenum--;
			}
			lastcallback = callback;
		},
		"error" : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg('访问 iadscp 服务失败！');
			console.log(errorThrown);
			exenum--;
			lastcallback = null;
			callback('<RESPONE><STATE>1</STATE><USE_FLAG>1</USE_FLAG><FLAG>1</FLAG></RESPONE>');
		}
	});
	return 1;
}

// 简要信息查看
function preCheckMsg(inputData, callback){
	/*
	maxindex = layer.open({
	  type: 2,
	  title: 'iadscp - 简要信息查看',
	  area: ['800px', '600px'],
	  fixed: false,
	  maxmin: true,
	  content: SIMPLEINFO + '?drug_code=' + inputData.request.rows.row.order_code
	});
	*/
	console.log('ycfun: 暂不启用简要信息');
	lastcallback = null;
	return 1;
}

// 提交审查
function preCheckAudit(inputData, callback){
	var index = layer.alert('iadscp正在审查审查，请稍等...');
	var reqdata = {json: JSON.stringify(inputData), isbs: 1};
	console.log(reqdata);
	jQuery.support.cors = true; //允许跨域访问
	$.ajax({
		"async": false,
		"dataType" : "json",
		"type" : "POST",
		"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
		// "cache" : false,
		"url" : IADRESS + "/optionifs",
		"timeout" : 4000,
		"data" : reqdata ,
		"success" : function(rtn) {
			console.log(rtn);
			layer.close(index);
			if("Y" == rtn.state){
				layer.msg('审查通过！');
				exenum--;
				callback('<RESPONE><STATE>1</STATE><USE_FLAG>1</USE_FLAG><FLAG>1</FLAG></RESPONE>');
			}else{
				/*maxindex = layer.open({
				  type: 2,
				  title: 'iadscp - 提交审查 > 审查结果查看',
				  closeBtn: 0,
				  area: ['934px', '704px'],
				  fixed: false,
				  content: CRSHOW + '?id=' + rtn.id + '&state=' + rtn.state
				});*/
				var show = ycbstransit + '?id=' + rtn.id + '&state=' + rtn.state;
				var child_ret = window.showModalDialog(show, window, 'resizable:yes;scroll:yes;status:no;help:no;dialogWidth:945px;dialogHeight:672px;alwaysRaised:yes;location:no;');
				alert(child_ret);
				exenum--;
				callback(child_ret);
				//lastcallback = callback; // 监听接管回调
			}
		},
		"error" : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.close(index);
			layer.msg('访问 iadscp 服务失败！');
			exenum--;
			lastcallback = null;
			callback('<RESPONE><STATE>1</STATE><USE_FLAG>1</USE_FLAG><FLAG>-4</FLAG></RESPONE>');
		}
	});
	return 1;
}

// 结算审查
function preCheckSIMIC(inputData, callback){
	interfaceInit(inputData, null);
	// 放大窗口以免窗口大小不够
	if (window.screen) {//判断浏览器是否支持window.screen判断浏览器是否支持screen
	  //var myw = screen.availWidth;   //定义一个myw，接受到当前全屏的宽
	  //var myh = screen.availHeight;  //定义一个myw，接受到当前全屏的高
	  //window.moveTo(0, 0);           //把window放在左上脚
	  window.resizeTo(1000, 800);     //把当前窗体的长宽跳转为myw和myh
	}
	//-- 开独立窗口
	//window.open('http://www.baidu.com', '_blank', 'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no') 
	lastcallback = null; // 自己回调
	var index = layer.alert('iadscp正在审查审查，请稍等...');
	var reqdata = {json: JSON.stringify(inputData), isbs: 1};
	jQuery.support.cors = true; //允许跨域访问
	$.ajax({
		"async": true,
		"dataType" : "json",
		"type" : "POST",
		"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
		// "cache" : false,
		"url" : IADRESS + "/optionifs",
		"timeout" : 30000,
		"data" : reqdata ,
		"success" : function(rtn) {
			layer.close(index);
			if(rtn.state == "Y"){
				exenum--;
				layer.msg('审核通过！');
			}else{
				maxindex = layer.open({
				  type: 2,
				  title: 'iadscp - 医保结算审查 > 审查结果查看',
				  closeBtn: 0,
				  area: ['934px', '704px'],
				  fixed: false,
				  content: SIMICRSHOW + '?id=' + rtn.id + '&patient_id='
						  + inputData.request.patient.id + '&visit_id='
						  + inputData.request.patient.visitid 
						  + '&doctor_no=' + inputData.request.doctor.code
				});
			}
			lastcallback = callback;
		},
		"error" : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.close(index);
			layer.msg('访问 iadscp 服务失败！');
			exenum--;
			lastcallback = null;
			callback('<RESPONE><STATE>1</STATE><USE_FLAG>1</USE_FLAG><FLAG>-4</FLAG></RESPONE>');
		}
	});
	return 1;
}

//清空缓存
function interfaceDestroy(inputData ){
	closeIM();
	layer.msg('清空缓存, 退出客户端！');
}

// 查询审查结果
function preCheckSearch(inputData, callback){
	var reqdata = {json: JSON.stringify(inputData), isbs: 1};
	jQuery.support.cors = true; //允许跨域访问
	$.ajax({
		"async": true,
		"dataType" : "json",
		"type" : "POST",
		"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
		// "cache" : false,
		"url" : IADRESS + "/presearch",
		"timeout" : 3000,
		"data" : reqdata ,
		"success" : function(rtn) {
			if("Y" == rtn.state){
				layer.msg('审查通过！');
			}else{
				/* 异步弹窗
				maxindex = layer.open({
				  type: 2,
				  title: 'iadscp - 审查结果读取',
				  area: ['1150px', '593px'],
				  fixed: false,
				  maxmin: true,
				  content: CFAILSHOW + '?id=' + rtn.response.id.replace(/\"/g,"'")
				});
				*/
				// ie阻塞式弹窗
				
				lastcallback = callback;
			}
		},
		"error" : function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg('访问 iadscp 服务失败！');
			lastcallback = null;
		}
	});
	return 1;
}

// 关闭窗口
function cclose(cdata){
	if(cdata.close == 1){
		layer.close(maxindex);
		exenum--;
	}
}

// 回调
function cback(cdata){
	if(cdata.close == 1){
		layer.close(maxindex);
		exenum--;
	}
}

// 参数校验
function paramsVerify(vifparams){
	var vifpass = true;
	var params = vifparams.request;
	var error_msg = '';
	try {
		if(!params){
			vifpass = false;
			error_msg += '参数有误, ';
		}
		if(!params.d_type){
			vifpass = false;
			error_msg += 'd_type 不存在或值为null, ';
		}
		if(!params.p_type){
			vifpass = false;
			error_msg += 'p_type 不存在或值为null, ';
		}
		if(!params.doctor){
			vifpass = false;
			error_msg += 'doctor 不存在或值为null, ';
		}
		if(!params.patient){
			vifpass = false;
			error_msg += 'patient 不存在或值为null, ';
		}
		if(error_msg != ''){
			layer.alert('参数错误 inputData：' + error_msg.substring(0, error_msg.length - 2));
		}
	} catch(err) {
		
	}
	console.log(vifparams);
	return vifpass;
}

// 刷新IM客户端登录信息
function refreshIM(inputData){
	// 可用但绵竹会报错 不建议如此
	//window.location.href = 'crtech://refreshim?doctor_code=' + inputData.request.doctor.code;
	if(ycfun.crtech_reg == 1){
		if($('#ycim_zdy1').length > 0){
			$('#ycim_zdy1').prop('src', 'crtech://refreshim?doctor_code=' + inputData.request.doctor.code);
		}else{
			var ychtml = '<iframe id="yc_zdy1" src="crtech://refreshim?doctor_code=:doctor_no" style="display: none;"></iframe>';
			$('body').append(ychtml.replace(':doctor_no', inputData.request.doctor.code));
		}
	}	
}

// 回收IM客户端资源
function closeIM(){
	if(ycfun.crtech_reg == 1){
		window.location.href = 'crtech://closeim';
	}
	/* if($('#ycim_zdy2').length > 0){
		$('#ycim_zdy2')[0].click();
	}else{
		var ychtml = '<a id="ycim_zdy2" href="crtech://closeim" style="display: none;">crtech.closeIM</a>';
		$('body').append(ychtml);
		$('#ycim_zdy2')[0].click();
	} */
}

function check_def_crtech(){
	try{
		var fso = new ActiveXObject('Scripting.FileSystemObject');
		var filePath = 'C:\\yice\\IMRegister.exe';
		//alert(fso.FileExists(filePath));
		if(!fso.FileExists(filePath)){
			ycfun.crtech_reg = 0;
		}
	}catch(err){
		console.log('don\'t support ActiveXObject !');
	}
	
	
}

check_def_crtech();



// -------------------------------------------------------------------------------------------------------------------------
// 外部函数 ===================
// -------------------------------------------------------------------------------------------------------------------------


function xml2json2 (xml, extended) {
	if (!xml) return {}; // quick fail

	//### PARSER LIBRARY
	// Core function
	function parseXML(node, simple) {
		if (!node) return null;
		var txt = '', obj = null, att = null;
		var nt = node.nodeType, nn = jsVar(node.localName || node.nodeName);
		var nv = node.text || node.nodeValue || '';
		/*DBG*/ //if(window.console) console.log(['x2j',nn,nt,nv.length+' bytes']);
		if (node.childNodes) {
			if (node.childNodes.length > 0) {
				/*DBG*/ //if(window.console) console.log(['x2j',nn,'CHILDREN',node.childNodes]);
				$.each(node.childNodes, function (n, cn) {
					var cnt = cn.nodeType, cnn = jsVar(cn.localName || cn.nodeName).toLowerCase();
					var cnv = cn.text || cn.nodeValue || '';
					/*DBG*/ //if(window.console) console.log(['x2j',nn,'node>a',cnn,cnt,cnv]);
					if (cnt == 8) {
						/*DBG*/ //if(window.console) console.log(['x2j',nn,'node>b',cnn,'COMMENT (ignore)']);
						return; // ignore comment node
					}
					else if (cnt == 3 || cnt == 4 || !cnn) {
						// ignore white-space in between tags
						if (cnv.match(/^\s+$/)) {
							/*DBG*/ //if(window.console) console.log(['x2j',nn,'node>c',cnn,'WHITE-SPACE (ignore)']);
							return;
						};
						/*DBG*/ //if(window.console) console.log(['x2j',nn,'node>d',cnn,'TEXT']);
						txt += cnv.replace(/^\s+/, '').replace(/\s+$/, '');
						// make sure we ditch trailing spaces from markup
					}
					else {
						/*DBG*/ //if(window.console) console.log(['x2j',nn,'node>e',cnn,'OBJECT']);
						obj = obj || {};
						if (obj[cnn]) {
							/*DBG*/ //if(window.console) console.log(['x2j',nn,'node>f',cnn,'ARRAY']);

							// http://forum.jquery.com/topic/jquery-jquery-xml2json-problems-when-siblings-of-the-same-tagname-only-have-a-textnode-as-a-child
							if (!obj[cnn].length) obj[cnn] = myArr(obj[cnn]);
							obj[cnn] = myArr(obj[cnn]);

							obj[cnn][obj[cnn].length] = parseXML(cn, true/* simple */);
							obj[cnn].length = obj[cnn].length;
						}
						else {
							/*DBG*/ //if(window.console) console.log(['x2j',nn,'node>g',cnn,'dig deeper...']);
							obj[cnn] = parseXML(cn);
						};
					};
				});
			}; //node.childNodes.length>0
		}; //node.childNodes
		if (node.attributes) {
			if (node.attributes.length > 0) {
				/*DBG*/ //if(window.console) console.log(['x2j',nn,'ATTRIBUTES',node.attributes])
				att = {}; obj = obj || {};
				$.each(node.attributes, function (a, at) {
					var atn = jsVar('@' + at.name).toLowerCase(), atv = at.value;
					att[atn] = atv;
					if (obj[atn]) {
						/*DBG*/ //if(window.console) console.log(['x2j',nn,'attr>',atn,'ARRAY']);

						// http://forum.jquery.com/topic/jquery-jquery-xml2json-problems-when-siblings-of-the-same-tagname-only-have-a-textnode-as-a-child
						//if(!obj[atn].length) obj[atn] = myArr(obj[atn]);//[ obj[ atn ] ];
						obj[cnn] = myArr(obj[cnn]);

						obj[atn][obj[atn].length] = atv;
						obj[atn].length = obj[atn].length;
					}
					else {
						/*DBG*/ //if(window.console) console.log(['x2j',nn,'attr>',atn,'TEXT']);
						obj[atn] = atv;
					};
				});
				//obj['attributes'] = att;
			}; //node.attributes.length>0
		}; //node.attributes
		if (obj) {
			obj = $.extend((txt != '' ? new String(txt) : {}), /* {text:txt},*/obj || {}/*, att || {}*/);
			//txt = (obj.text) ? (typeof(obj.text)=='object' ? obj.text : [obj.text || '']).concat([txt]) : txt;
			txt = (obj.text) ? ([obj.text || '']).concat([txt]) : txt;
			if (txt) obj.text = txt;
			txt = '';
		};
		var out = obj || txt;
		//console.log([extended, simple, out]);
		if (extended) {
			if (txt) out = {}; //new String(out);
			txt = out.text || txt || '';
			if (txt) out.text = txt;
			if (!simple) out = myArr(out);
		};
		return out;
	}; // parseXML
	// Core Function End
	// Utility functions
	var jsVar = function (s) { return String(s || '').replace(/-/g, "_"); };

	// NEW isNum function: 01/09/2010
	// Thanks to Emile Grau, GigaTecnologies S.L., www.gigatransfer.com, www.mygigamail.com
	function isNum(s) {
		// based on utility function isNum from xml2json plugin (http://www.fyneworks.com/ - diego@fyneworks.com)
		// few bugs corrected from original function :
		// - syntax error : regexp.test(string) instead of string.test(reg)
		// - regexp modified to accept  comma as decimal mark (latin syntax : 25,24 )
		// - regexp modified to reject if no number before decimal mark  : ".7" is not accepted
		// - string is "trimmed", allowing to accept space at the beginning and end of string
		var regexp = /^((-)?([0-9]+)(([\.\,]{0,1})([0-9]+))?$)/
		return (typeof s == "number") || regexp.test(String((s && typeof s == "string") ? jQuery.trim(s) : ''));
	};
	// OLD isNum function: (for reference only)
	//var isNum = function(s){ return (typeof s == "number") || String((s && typeof s == "string") ? s : '').test(/^((-)?([0-9]*)((\.{0,1})([0-9]+))?$)/); };

	var myArr = function (o) {

		// http://forum.jquery.com/topic/jquery-jquery-xml2json-problems-when-siblings-of-the-same-tagname-only-have-a-textnode-as-a-child
		//if(!o.length) o = [ o ]; o.length=o.length;
		if (!$.isArray(o)) o = [o]; o.length = o.length;

		// here is where you can attach additional functionality, such as searching and sorting...
		return o;
	};
	// Utility functions End
	//### PARSER LIBRARY END

	// Convert plain text to xml
	if (typeof xml == 'string') xml = $.parseXML(xml);

	// Quick fail if not xml (or if this is a node)
	if (!xml.nodeType) return;
	if (xml.nodeType == 3 || xml.nodeType == 4) return xml.nodeValue;

	// Find xml root node
	var root = (xml.nodeType == 9) ? xml.documentElement : xml;

	// Convert xml to json
	var out = parseXML(root, true /* simple */);

	// Clean-up memory
	xml = null; root = null;

	// Send output
	return out;
}


//===========
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
