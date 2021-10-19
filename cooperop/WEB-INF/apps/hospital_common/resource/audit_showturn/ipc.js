function topass(data){
    $.ajax({
        "async": true,
        "dataType" : "json",
        "type" : "POST",
        "contentType" : "application/x-www-form-urlencoded; charset=UTF-8",
        "cache" : false,
        "url" : "/w/hospital_common/showturns/sureAgain.json",
        "timeout" : 60000,
        "data" : data,
        "success" : function(rtn) {
            
        },
        "error" : function(XMLHttpRequest, textStatus, errorThrown) {
            
        }
    });
}

//合理用药特殊类型审查操作
function ipc_sp_role(check_ipc_must_yes,imic_tnum){
	if(check_ipc_must_yes && check_ipc_must_yes == true){
		$.message('存在合理用药特殊规则问题，需要医生选择【是】【否】确认信息！');
		//开具特殊级抗菌药物需会诊，请医生选择是否会诊！
		//存在合理用药特殊规则问题，需要医生选择【是】【否】确认信息！
	}
	
	var ipc_onlyxz = true;
	if(fstate == 'N'){
		var ipc_tnums = imic_tnum;
		if(ipc_tnums && ipc_tnums.length > 0){
			for(var i=0; i< ipc_tnums.length; i++){
				if(ipc_tnums[i] != '05' && ipc_tnums[i].check_result_state == 'N'){
					ipc_onlyxz = false ;
				}
			}
		}
	}
	
	$('.ipc_sp_role').change(function(){
		//var ipc_sp_role_name = $(this).attr('name');
		if($(this).parent().attr('class').indexOf('ipc_radio_1')>-1){
			$(this).parent().removeClass('fa fa-square-o');
			$(this).parent().siblings('.ipc_radio').removeClass('fa fa-times');
			$(this).parent().siblings('.ipc_radio').removeClass('fa fa-check');
			$(this).parent().siblings('.ipc_radio').addClass('fa fa-square-o');
			$(this).parent().addClass('fa fa-check');
		}else{
			$(this).parent().siblings('.ipc_radio').removeClass('fa fa-times');
			$(this).parent().siblings('.ipc_radio').removeClass('fa fa-check');
			$(this).parent().siblings('.ipc_radio').addClass('fa fa-square-o');
			$(this).parent().removeClass('fa fa-square-o');
			$(this).parent().addClass('fa fa-times');
		}
		if($(this).parent().parent().attr('data-must') == '1' && ipc_onlyxz){
			if(check_must_yes_all()){
				if(fstate == 'T'){
					operation('op1');//变为已阅
				}else if(fstate == 'Q' || (fstate == 'N' && $('input[type="checkbox"]:checked').length != 0)){
					operation('op4');//强制使用 变为红色
				}
			}else{
				if(fstate == 'T'){
					operation('op5');//变为具有返回调整和已阅, 并且已阅为灰色
				}else if(fstate == 'Q' || fstate == 'N'){
					operation('op2');//强制使用 变为灰色
				}
			}
		}
		$.call('ipc.auditresult.updateCRIResult',{
			id:$(this).parent().parent().attr('data-id'),
			sp_result:$(this).val()
		},function(rtn){},function(e){}, {async: false, remark: false})
	});
}

//合理用药特殊类型审查操作, 判断选项是否必须选择【是】
function check_must_yes_all(){
    var check = true;
    $('.ipc_sp_box[data-must="1"]').each(function(){
        var choose_re = $(this).find('.ipc_sp_role:checked').val();
        if(choose_re=='' || choose_re==null || 
        choose_re == undefined  || choose_re == '0'){
            check = false;
        }
    })
    return check;
}

//ipc查看详情
function seeopen(check_result_info_id,sort_id,ipc_auto_id){
	 layer.open({
		  type: 2,
		  title: "医策智能辅助决策支持  -用药审查结果详情",
		  area: ['910px', '600px'], 
		  content: "/w/ipc/auditresult/particulars.html?auto_audit_id="+ipc_auto_id+"&&sort_id="+sort_id+"&&check_result_info_id="+check_result_info_id+"#topsta"
	}); 
}

//双签名用药
function sqmyy(){
	topass({id: id,doctor_no: doctor_no,state: 'DS'});
	closeModal({flag: "5", state: "1", "use_flag": "1"});//双签名强制使用
}

//查看强制用药审查结果
function showAdvice(){
	id1 = layer.open({
	  type: 1,
	  title: "药师审查结果",
	  area: ['400px', '300px'], //宽高
	  content: $("#rg_advice")
	});
}

// 超说明书申请
function csmsWf(qhtmlId){
	var sms = $('#'+qhtmlId).find('.ypsms');
	if(sms){
		if(sms.length == 1){
			csmsWfst(sms.eq(0).data('id'));
		}else if(sms.length > 1){
			var html = [];
			for(var i=0; i<sms.length; i++){
				html.push('<a class="ypm2css" onclick="csmsWfst(\''+ sms.eq(i).data('id') +'\');">◈ ' + sms.eq(i).html() + '</a><br>');
			}
			//▷<a style="font-size: 14px;" class="ypsms">药品1<a> <br> -<a style="font-size: 14px;" class="ypsms">药品2</a><br><span>点击药品名称发起申请</span>
			html.push('<font style="color: #ad640a; margin-left: 10px;">提示：点击药品名称发起超说明书申请</font>');
			layer.open({
				  type: 1,
				  shadeClose: true, //开启遮罩关闭
				  title: "选择申请药品",
				  area: ['300px', '200px'], //宽高
				  content: html.join('')
				});
		}else{
			$.message('没有检测到药品，不能发起超说明书申请！');
		}
	}
}

function csmsWfst(id){
	// 查询药品数据
	$.call("hospital_common.showturns.getDrug",{"drug_code": id},function(d){
		var url = "/w/ipc/bill/IPC/A01.html?drug_code= "+ id;
		if(doctor){
			//alert(doctor._CRSID);
			url += ('&dept_code=' + doctor.dept_code);
			url += ('&_CRSID=' + doctor._CRSID);
			url += ('&dept_name=' + doctor.dept_name);
			d._CRSID = doctor._CRSID;
		}
		url += ('&drug_name=' + d.drug_name);
		url += ('&drug_spec=' + d.druggg);
		url += ('&drug_unit=' + d.drug_unit);
		url += ('&shengccj=' + d.shengccj);
		url += ('&pizhwh=' + d.pizhwh);
		
		/*layer.open({
			  type: 2,
			  d,
			  title: "超说明书申请",
			  area: ['910px', '600px'], 
			  content: url
		});*/
		d.type = 2;
		d.title = "超说明书申请";
		d.area = ['910px', '600px'];
		d.content = url;
		layer.open(d)
	})
	
}

//TPN详情
function setTPN(check_result_info_id,sort_id,ipc_auto_id){
	 layer.open({
		  type: 2,
		  maxmin:true,
		  title: "医策智能辅助决策支持  -审核结果详情",
		  area: ['90%', '90%'], 
		  content: "/w/hospital_common/showturns/indexinfo.html?auto_audit_id="+ipc_auto_id+"&&sort_id="+sort_id+"&&check_result_info_id="+check_result_info_id
	}); 
}

function is_cftj(auto_audit_id,patient_id,visit_id){
	$.call('hospital_common.check_repeat.hadSubmit', {auto_audit_id: auto_audit_id,patient_id:patient_id,visit_id:visit_id} , function(rtnhas){
		var dataAudit = rtnhas.rtnmap;
		if(dataAudit.flag == '1'){
			rep_check = 1
			layer.open({
			  title: "系统提示",
			  area: ['400px', '250px'], 
			  btn: ['确认'],
			  closeBtn: 0,
			  content: '<font class="ntcinfo">重复提交药师审查！药师审查结果为：<b style="font-size:16px;">'+dataAudit.message+'</b></font>',
			  yes: function(index, layero){
				//重复保存，存储为返回调整
				$.call("hospital_common.showturns.backAdjust",{'common_id': commid, 'state': 'DB', 'order_flag': '9'},function(rtn){
					var frg = calflag();
					closeModal({'flag': frg.flag, 'state': "1", 'use_flag': "0"});//超时
				});
			  }
			});
		}else{
			rep_check = 2
		}
	},function(e){}, {async: false, remark: false}); 
}