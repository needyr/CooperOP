var IADRESS = 'http://' + window.location.host;
var ycfunmap  = IADRESS + '/res/hospital_common/yctest/';
if(typeof($) == 'undefined'){document.write('<script type="text/javascript" src="_YCMAP_jquery-3.5.1.min.js"></script>'.replace('_YCMAP_', ycfunmap));};
document.write('<script type="text/javascript" src="_YCMAP_json2xml.js"></script>'.replace('_YCMAP_', ycfunmap));
document.write('<script type="text/javascript" src="_YCMAP_xml2json.js"></script>'.replace('_YCMAP_', ycfunmap));
var CRSHOW = IADRESS + '/w/hospital_common/showturns/detail.html';
var SIMICRSHOW = IADRESS + '/w/hospital_imic/simic/index.html';
var SIMPLEINFO = IADRESS + '/w/hospital_common/showturns/simpleinfo2.html';
var CFAILSHOW = IADRESS + '/w/hospital_common.showturns/ocresult.html';
var maxindex = 0;
var lastcallback;

// 接口函数
function crInterfaceDoCall(command, inputData, callback){
	//console.log(command);
	//console.log(inputData);
	inputData = inputData.toLowerCase();
	var inputData = '<root>' + inputData + '</root>';
	try{
		inputData = $.xml2json2(inputData);
	}catch(e){
		return -1;
	}
	var status_code = 1;
	if(command == ''){
		console.log('未选择命令！');
		return;
	}
	switch(command){
		case 'InterfaceInit':
			status_code = interfaceInit(inputData, callback);
			break;
		case 'PreCheck_Message':
			status_code = preCheckMsg(inputData, callback);
			break;
		case 'PreCheck_Audit':
			status_code = preCheckAudit(inputData, callback);
			break;
		case 'PreCheck_SIMIC':
			status_code = preCheckSIMIC(inputData, callback);
			break;
		case 'InterfaceDestroy':
			status_code = interfaceDestroy(inputData, callback);
			break;
		case 'PreCheck_Search':
			status_code = preCheckSearch(inputData, callback);
			break;
		case 'test': break;
		default : status_code = -10 ;console.log('暂未收录该命令！');
	}
	return status_code;
}

// 初始化
function interfaceInit(inputData, callback){
	// 验参
	if(!paramsVerify(inputData)){
		callback('<RESPONE><STATE>1</STATE><USE_FLAG>1</USE_FLAG><FLAG>-2</FLAG></RESPONE>');
		return -1;
	}
	var reqdata = {json: JSON.stringify(inputData), isbs: 1};
	// todo 登录IM
	$.ajax({
		"async": true,
		"dataType" : "json",
		"type" : "POST",
		"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
		// "cache" : false,
		"url" : IADRESS + "/prescripitoninit",
		//"timeout" : 60000,
		"data" : reqdata ,
		"success" : function(rtn) {
			callback(rtn);
		},
		"error" : function(XMLHttpRequest, textStatus, errorThrown) {
			callback('<RESPONE><STATE>1</STATE><USE_FLAG>1</USE_FLAG><FLAG>1</FLAG></RESPONE>');
		}
	});
	return 1;
}

// 提交审查
function preCheckAudit(inputData, callback){
	var reqdata = {json: JSON.stringify(inputData), isbs: 1};
	$.ajax({
		"async": true,
		"dataType" : "json",
		"type" : "POST",
		"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
		"url" : IADRESS + "/optionifs",
		"data" : reqdata ,
		"success" : function(rtn) {
			callback(rtn);
		},
		"error" : function(XMLHttpRequest, textStatus, errorThrown) {
			callback('<RESPONE><STATE>1</STATE><USE_FLAG>1</USE_FLAG><FLAG>-4</FLAG></RESPONE>');
		}
	});
	return 1;
}

// 结算审查
function preCheckSIMIC(inputData, callback){
	lastcallback = null; // 自己回调
	var reqdata = {json: JSON.stringify(inputData), isbs: 1};
	$.ajax({
		"async": true,
		"dataType" : "json",
		"type" : "POST",
		"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
		// "cache" : false,
		"url" : IADRESS + "/optionifs",
		//"timeout" : 60000,
		"data" : reqdata ,
		"success" : function(rtn) {
			callback(rtn);
		},
		"error" : function(XMLHttpRequest, textStatus, errorThrown) {
			callback('<RESPONE><STATE>1</STATE><USE_FLAG>1</USE_FLAG><FLAG>-4</FLAG></RESPONE>');
		}
	});
	return 1;
}

//获取数据 
function execSQL(){
	var sql = $('#rsql').val();
	var  list = [];
	$.ajax({
		"async": false,
		"dataType" : "json",
		"type" : "POST",
		"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
		"url" : IADRESS + "/w/hospital_common/test_jq/jsexesql.json",
		"data" : {sql: sql, is_bs: 1} ,
		"success" : function(rtn) {
			if(rtn.data != null){
				list = rtn.data;
			}
		},
		"error" : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('请求失败！');
		}
	});
	return list;
}

// 更新控制值
function upctrl(val){
	$.ajax({
		"async": false,
		"dataType" : "json",
		"type" : "POST",
		"contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
		"url" : IADRESS + "/w/hospital_common/test_jq/upctrl.json",
		"data" : {val: val, is_bs: 1} ,
		"success" : function(rtn) {
			if(rtn == "1"){
				alert('final_control 已更新为' + val);
			}else{
				alert(rtn);
			}
		},
		"error" : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('请求失败！');
		}
	});
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
			console.log('参数错误 inputData：' + error_msg.substring(0, error_msg.length - 2));
		}
	} catch(err) {
		
	}
	console.log(vifparams);
	return vifpass;
}