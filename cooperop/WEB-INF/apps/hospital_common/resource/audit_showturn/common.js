//患者详情
function topatient(patient_id,visit_id,doctor_no){
    layer.open({
        type: 2,
        title: "医策智能辅助决策支持  -患者详情",
        area: ['847px', '610px'], 
        content: "/w/hospital_common/patient/index.html?patient_id="+patient_id+"&&visit_id="+visit_id+"&&doctor_no="+doctor_no
    }); 
}

//点赞方法
function goodlike(){
    $(".falike").on("click",function(){
        var id=$(this).attr("data-id");
        var good_reputation = 0;
        if($(this).index()==1){
                if($(this).hasClass("fa-thumbs-up")){//赞取消     =不踩不赞
                    $(this).removeClass("fa-thumbs-up");
                }else{//赞
                    $(this).addClass("fa-thumbs-up").siblings().removeClass("fa-thumbs-down");
                    good_reputation = 1;
                }
            }else{
                if($(this).hasClass("fa-thumbs-down")){//踩取消
                    $(this).removeClass("fa-thumbs-down");
                }else{//踩
                    $(this).addClass("fa-thumbs-down").siblings().removeClass("fa-thumbs-up");
                    good_reputation = 2;
                }
            }
        var pro = $(this).data('pro');
        if(pro == 'ipc'){
            $.call("ipc.auditresult.goodlike",{"id":id,"good_reputation": good_reputation},function(e){},
                    function(e){}, {async: false, remark: false   })
        }else if(pro == 'hospital_imic'){
            $.call("hospital_imic.auditresult.goodlike",{"id":id,"good_reputation": good_reputation},function(e){},
                    function(e){}, {async: false, remark: false   })
        }
        
    })
}
//打开药品说明书
function openIns(){
    $(".ypsms").on("click", function(){
        if($(this).attr("data-id")){
            var drugcode = $(this).attr("data-id");
            layer.open({
                    type: 2,
                    title: $(this).text()+'说明书',
                    area: ['800px', '580px'], //宽高
                    content: "/w/ipc/auditresult/instruction.html?his_drug_code="+drugcode	  
            });
        }
    });
}
//帮助手册
function helps(){
    layer.open({
            type: 2,
            title: $(this).text()+'帮助手册',
            area: ['600px', '500px'],
            content: "help.html"	  
    });
}
//生成医生操作按钮
function operation(operway){
    //提示
    if(operway == 'op1'){
        $('#qd2').unbind('click');
        $('#qd2').prop('onclick', '');
        $('#qd2').removeClass('notclick');
        $("#qd2").show();
        $("#qd2").text("已阅读");
        $("#sqmyy").hide();
        $("#qx").hide();
        $("#qd1").hide();
        $("#qsave").hide();
        $("#qd2").on("click", function(){
            var frg = calflag();
            closeModal({'flag': frg.flag, 'state': "1", 'use_flag': "1"});
        });
    }else if (operway == 'op2'){
        //灰色强制用药
        $('#qsave').unbind('click');
        $('#qsave').prop('onclick', '');
        $('#qsave').addClass('notclick');
        $('#qsave').click(function (){
            layer.msg('包含违规项，不能执行该操作', {
                    icon: 2,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                }, function(){
                    //do something
                });
        });
    }else if (operway == 'op3'){
        //仅能返回调整
        $('#qsave').hide();
    }else if (operway == 'op4'){
        //从灰色强制用药变为普通情况
        $('#qsave').unbind('click');
        $('#qsave').prop('onclick', '');
        $('#qsave').removeClass('notclick');
        $('#qsave').click(function (){
            qsave();
        });
    }else if (operway == 'op5') {
        //已阅读, 为灰色, 只能返回调整,显示已阅读、返回调整两个按钮
        $("#qd2").show();
        $("#qx").show();
        $("#qd2").text("已阅读");
        $('#qd2').unbind('click');
        $('#qd2').prop('onclick', '');
        $('#qd2').addClass('notclick');
        $("#sqmyy").hide();
        $("#qd1").hide();
        $("#qsave").hide();
        $('#qd2').click(function (){
            layer.msg('包含违规项，不能执行该操作', {
                    icon: 2,
                    time: 1000 //2秒关闭（如果不配置，默认是3秒）
                }, function(){
                    //do something
                });
        });
    }
}

function shenc(){
    $("#shenc").attr("class","active");
    $("#dianp").attr("class","");
    $("#commentright").css("display","none");
    $("#tab2").css("display","block");
}

function dianp(){
    $("#dianp").attr("class","active");
    $("#shenc").attr("class","");
    $("#commentright").css("display","block");
    $("#tab2").css("display","none");
}

//report提示
function report_text(){
	//report提示
	var result_report = '';
	if(fstate == 'T'){
		result_report = '审查结果:提示(通过,点击已阅读关闭提示页面)';
	}else if(fstate == 'Q'){
		result_report = '审查结果:拦截(返回调整医嘱，或者强制使用提交人工审查)';
	}else if(fstate == 'N'){
		result_report = '审查结果:驳回(转为自费后可强制使用)';
	}
	$('#report').addClass('hasreport').text(result_report);
}

//检查function函数是否存在
function isExistsFunction(functionName){
    var isFunction =false;
    try{
        isFunction = typeof(eval(functionName)) =="function";
    }catch(e){}
    return isFunction;
}

//强制使用填写理由
function qsave(){
	if(patient.imic_sort_05 != undefined && patient.imic_sort_05 == 1){
		//toPrint_opwin();
	}
	//window.open('http://www.baidu.com', '', 'directories=0, width=800, height=400, location=0, menubar=0, resizable=0,status=0,toolbar=0,top=200,left=200,scrollbars=0');
	index = layer.open({
		type: 1,
		title: "强制使用需要填写意见说明",
		area: ['500px', '438px'],
		content: $("#qadivce")
	});
}

//返回调整
function qx(mystate) {
	if(mystate){
		try{
			var frg = calflag();
			$.call("hospital_common.showturns.backAdjust",{"common_id":commid,"state":mystate},function(rtn){
				closeModal({'flag': frg.flag, 'state': "0", 'use_flag': "0"});//取消
			});
		}catch(err){
			closeModal({'flag': frg.flag, 'state': "0", 'use_flag': "0"});//取消
		}
	}else{
		if(rtnflag.ipc && rtnflag.ipc == 'HL_N'){
			rtnflag.ipc = 'DB';
		}
		if(rtnflag.hospital_imic && rtnflag.hospital_imic == 'MQ'){
			rtnflag.hospital_imic = 'DB';
		}
		
		try{
			var frg = calflag();
			$.call("hospital_common.showturns.backAdjust",{"common_id":commid,"state":"DB"},function(rtn){
				closeModal({'flag': frg.flag, 'state': "0", 'use_flag': "0"});//取消
			});
		}catch(err){
			closeModal({'flag': frg.flag, 'state': "0", 'use_flag': "0"});//取消
		}
	}
}

//处理flag值(审方说明)
function calflag(){
	var rtns = {
		'flag' : '',
		'remark' : ''
	};
	for(var item in rtnflag){
		try{
			var key = item + rtnflag[item];
			rtns.flag = rtns.flag + eval('prlv.mangerlv.' + key + '.flag');
			if(rtns.remark == ''){
				rtns.remark = rtns.remark + eval('prlv.mangerlv.' + key + '.remark');
			}else{
				rtns.remark = rtns.remark +',' + eval('prlv.mangerlv.' + key + '.remark');
			}
		}catch(err){
			continue;
		}
	}
	return rtns;
}

//关闭窗口判断
/*
 * 注意call,为imic特有操作
 */
function closeModal(param){
	imic_recordSF(imic_audit_id);
	var xml = [];
	xml.push('<RESPONE>');
	xml.push('<STATE>'+param.state+'</STATE>');
	xml.push('<USE_FLAG>'+param.use_flag+'</USE_FLAG>');
	xml.push('<FLAG>'+param.flag+'</FLAG>');
	xml.push('</RESPONE>');
	//location.href = "message://data?"+JSON.stringify(param);
	if("undefined" != typeof crtech) {
		//im上传会导致页面无法关闭
		crtech.callback(xml.join(''), true);
	}else{
		var furl = '';
		if (parent !== window) { 
	        try {
	        	furl = parent.location.href; 
	        }catch (e) { 
	        	furl = document.referrer; 
	        } 
	     }
		//window.parent.postMessage({crtn: xml.join(''), ffun: 'cback', close: 1}, furl);
		var ret2par = {crtn: xml.join(''), ffun: 'cback', close: 1};
		window.parent.postMessage(JSON.stringify(ret2par), '*');
	}
}

//转自费提交
function imic_recordSF(imic_audit_id){
	if(is_sp_click == 1){
		$.call('hospital_imic.auditresult.recordSF', {'imic_audit_id': imic_audit_id}, function(rtn){},function(e){}, {async: false, remark: false});
	}
}

//根据结果判断按钮状态
function check_first_but_state(hospital_imic_tnum,check_ipc_must_yes){
	//根据结果判断按钮状态
	if(fstate == 'N'){
		//驳回时不能进行强制使用（除非仅包含限专类别问题）
		var tnums = hospital_imic_tnum;
		if(tnums && tnums.length > 0){
			for(var i=0; i< tnums.length; i++){
				if(tnums[i] != '05' && tnums[i].check_result_state == 'N'){
					onlyxz = false ;
				}
			}
		}
		/* if(!onlyxz || ipc_state == 'HL_B'){
			operation('op3');// 不能强制用药
			// 灰色强制用药 op2
			// 灰色 =>普通强制用药 op4
		} */
		//医保转自费, 增加合理用药特殊规则判断, 保证处理按钮不会错乱
		if(onlyxz || (check_ipc_must_yes && check_ipc_must_yes == true)){
			operation('op2');
		}else{
			operation('op3');//普通情况 不能强制用药
		}
	}else if(fstate == 'T'){
		if(check_ipc_must_yes && check_ipc_must_yes == true){
			operation('op5');
		}else{
			operation('op1');
		}
	}else if(fstate == 'Q'){
		if(check_ipc_must_yes && check_ipc_must_yes == true){
			//operation('op4');
			operation('op2');//不能强制用药
		}
	}
}

//判断医嘱是否重复提交
function check_repeat_submit(rtn_ipc_auto_id){
	var dataAudit = {};
	if(typeof(rtnflag.ipc) != "undefined"){
		$.call('hospital_common.showturns.hadSubmit', {auto_audit_id: rtn_ipc_auto_id} , function(rtnhas){
			dataAudit = rtnhas.rtnmap;
		},function(e){}, {async: false, remark: false}); 
	}else{
		dataAudit.flag = 0;
	}
	if(dataAudit.flag == 1){
		$("#qd2").show();
		$("#qd2").text("已阅读");
		$("#sqmyy").hide();
		$("#qx").hide();
		$("#qd1").hide();
		$("#qsave").hide();
		//$("#rg_advice").find(".advice-content").html('<font class="ntcinfo">'+dataAudit.message+'</font>');
		$("#qd2").on("click", function(){
			layer.open({
			  title: "系统提示",
			  area: ['400px', '200px'], 
			  btn: ['处理','返回'],
			  closeBtn: 0,
			  content: '<font class="ntcinfo">'+dataAudit.message+'</font>',
			  yes: function(index, layero){
				//重复保存，存储为返回调整
				rtnflag.ipc = 'DB' ;
	    		$.call("hospital_common.showturns.backAdjust",{'common_id': commid, 'state': 'DB', 'order_flag': '9'},function(rtn){
	    			var frg = calflag();
					closeModal({'flag': frg.flag, 'state': "1", 'use_flag': "0"});
				});
			  },
			  btn2: function(index, layero){
				  layer.close(index);
			  }
			});
		});
	}
}

//加载完成后去执行逻辑
//强制使用填写理由，必填逻辑判断
function qzsave_doctor_reason_required(rtn_product){
	var product_advices = eval('('+rtn_product+')');//产品信息
    //加载完成后去执行逻辑
    //强制使用填写理由，必填逻辑判断
    var length= $('.list').length;
    //是否必填
 	for(j=0;j<product_advices.length;j++){
 		var product_code = product_advices[j].product_code;
 		append_advice(product_code);
 		//判断是否必填
	    for(i=0;i<length;i++){
	        var isExist= $('div.checkbox'+(i)+product_code).length;
	        var checked = $('div.checkbox'+(i)+product_code+' input[type=checkbox]:checked').length;
	        if((isExist>0 && checked<=0) || $('.advice_is_must'+(i)+product_code).text() == '1'){
	            $('p.title[data-product="'+product_code+'"]:eq('+i+')').append("&nbsp<span><font color=\"red\" style=\"font-size: 20px;top: 6px; position: relative;\">*</font></span>");
	            if($('input[name="adviceFruit'+product_code+'"]').eq(i).val() == null || $('input[name="adviceFruit'+product_code+'"]').eq(i).val() == ''){
	                boo = false;
	            }
	        }
	    }
    }
}

//拼接意见
function append_advice(product_code){
	//拼接医生意见
	$("#qadivce").find("input[name='Fruit"+product_code+"']").on("click", function(){
		var $this = $(this);
		var addr=$(".doc_advice1"+product_code+"[data-id='"+$this.attr('data-sort-id')+"']");
		var addr2=$(".doc_advice2"+product_code+"[data-id='"+$this.attr('data-sort-id')+"']");
		var key=$this.attr("data-id");
		if($this[0].checked){
			addr2.val(addr2.val()+$this.val()+",");
			addr.val(addr.val()+key+",");
		}else{
			addr2.val(addr2.val().replace($this.val()+",", ""));
			addr.val(addr.val().replace(key+",", ""));
		}
	});
}

//记录模式
function recordMode(mode_code,patient_id,visit_id,doctor_no,patient_name,sex){
	var data = {};
	data.patient_id = patient_id;
	data.visit_id = visit_id;
	data.mode_code = mode_code;
	data.doctor_no = doctor_no;
	$.call('hospital_mqc.earlywarn.recordMode', data, function(rtn){
		if(rtn == "1"){
			$('#mode_s').html('当前患者为普通管理,<a style="color: blue;cursor: pointer;" onclick="enterBz(\''+patient_id+'\',\''+visit_id+'\',\''+doctor_no+'\',\''+patient_name+'\',\''+sex+'\');">点击加入单病种管理</a>');
		}else{
			$.message('操作失败，请重试或联系管理员！');
		}
	});
}