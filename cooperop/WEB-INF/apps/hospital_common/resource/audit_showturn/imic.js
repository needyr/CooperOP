function toItem(){
	layer.open({
		type: 2,
		title: "查看参与本次审查的计费项目",
		area: ['95%', '95%'],
		content: '/w/hospital_imic/auditresult/itemlist2.html?imic_audit_id='+ imic_audit_id
	});
}

// 进入单病种
function enterBz(patient_id,visit_id,doctor_no,patient_name,sex){
    var patient_id = patient_id;
    var visit_id = visit_id;
    var doctor_no = doctor_no;
    var title = '单病种管理信息确认【'+patient_name+'/'+sex+'】';

	$.modal('/w/hospital_imic/singledis/sureinfo.html', title, {
		width: '700px',
		height: '547px',
		patient_id: patient_id,
		visit_id: visit_id,
		doctor_no: doctor_no,
		callback : function(e){
			if(e){
				var show_str = '当前患者为单病种管理,<a href="javascript:void(0);" onclick="exitBz(\''+patient_id+'\',\''+visit_id+'\',\''+doctor_no+'\',\''+patient_name+'\',\''+sex+'\');">点击管理</a>;<font style="font-weight: 600; font-size:14px;">'+ e +'</font>'
				$('#mode_s').parent('p').attr('title', e);
				$('#mode_s').html(show_str);
			}
		}
	});

/*	layer.open({
		type: 2,
		title: title,
		area: ['700px', '547px'],
		content: '/w/hospital_imic/singledis/sureinfo.html?patient_id=' + patient_id + "&visit_id="
			+ visit_id + "&doctor_no=" + doctor_no,
		yes: function(index, layero){
			console.log(layero);
			if(layero){
				var show_str = '当前患者为单病种管理,<a href="javascript:void(0);" onclick="exitBz(\''+patient_id+'\',\''+visit_id+'\',\''+doctor_no+'\',\''+patient_name+'\',\''+sex+'\');">点击管理</a>;<font style="font-weight: 600; font-size:14px;">'+ e +'</font>'
				$('#mode_s').parent('p').attr('title', e);
				$('#mode_s').html(show_str);
			}
		}
	});*/







    /* ygz.2021.02.23 BS上不能采用$.modal

    */
}

// 退出单病种
function exitBz(patient_id,visit_id,doctor_no,patient_name,sex){
    var patient_id = patient_id;
    var visit_id = visit_id;
    var doctor_no = doctor_no;
    var title = '退出单病种管理【'+patient_name+'/'+sex+'】';
	var url = '/w/hospital_imic/singledis/exitbz.html';
	url += "?patient_id=" + patient_id + "&visit_id=" + visit_id
		+ "&doctor_no=" + doctor_no;


    $.modal('/w/hospital_imic/singledis/exitbz.html', title,{
        width: '700px',
        height: '547px',
        patient_id: patient_id,
        visit_id: visit_id,
        doctor_no: doctor_no,
        callback : function(e){
            if(e){
                $('#mode_s').html('当前患者是否加入单病种管理？<a href="javascript:void(0)" onclick="enterBz(\''+patient_id+'\',\''+visit_id+'\',\''+doctor_no+'\',\''+patient_name+'\',\''+sex+'\');">是</a> | <a href="javascript:void(0)" onclick="recordMode(0,\''+patient_id+'\',\''+visit_id+'\',\''+doctor_no+'\',\''+patient_name+'\',\''+sex+'\');">否</a>');
            }
        }
    });

}

function admin_sp(){
	var durl = '/w/hospital_imic/selfpaid/list.html?all=0';
	durl += '&patient_id=' + patient.patient_id;
	durl += '&visit_id=' + patient.visit_id;
	durl += '&doctor_no=' + patient.doctor_no;
	durl += '&d_type=' + audit_d_type;
	layer.open({
		type: 2,
		title: "患者本次住院转自费项目管理",
		area: ['95%', '95%'],
		content: durl
	});
    /*$.modal(durl, "患者本次住院转自费项目管理",{
        width: '95%',
        height: '95%',
        callback : function(e){}
    });*/
}

//限专药品转自费操作
/*
 * is_sp_click为转自费框是否被点击
 * 
 */
function imic_zzf(dtype_can_toselfpaid){
	$('.imic_zzf').click(function(){
		if (dtype_can_toselfpaid == 0){
			$.message('当前工作站的转自费权限已关闭！');
			var thisBox = $(this);
			thisBox.removeAttr('checked');
			return;
		}
		is_sp_click = 1;
		var thisBox = $(this);
		var choose = thisBox.prop('checked');
		var istozf = 0 ;
		var pkey = thisBox.data('pkey');
		if(choose){
			$.confirm('是否进行医保转自费，请确认!',function(rtn){
				if(rtn == true){
					istozf = 1 ;
					 $.call('hospital_imic.auditresult.toSelfPaid', {'imic_audit_id': imic_audit_id, 'p_keys': pkey, 'toselfpaid': istozf}, function(rtn){},function(e){}, {async: false, remark: false});
				}else{
					thisBox.removeAttr('checked');
					return;
				}
			});
		}else{
			 $.call('hospital_imic.auditresult.toSelfPaid', {'imic_audit_id': imic_audit_id, 'p_keys': pkey, 'toselfpaid': istozf}, function(rtn){},function(e){}, {async: false, remark: false});
		}
				
		// 智能审查结果为N
		if(fstate == 'N'){
			 if($('input[type="checkbox"]:checked').length == 0){
				operation('op2');//强制使用 变为灰色
			 }else{
				//operation('op4');//强制使用 变为红色
				if(isExistsFunction('check_must_yes_all') && check_must_yes_all()){
					operation('op4');//强制使用 变为红色
				}else{
					operation('op2');//强制使用 变为灰色
				}
			 } 
		}
	});
}

//imic查看详情
function imicDetil(imic_info_id, imic_audit_id, patient_id,visit_id){
	layer.open({
		  type: 2,
		  title: "医策智能辅助决策支持  -医保审查结果详情",
		  area: ['910px', '600px'], 
		  content: '/w/hospital_imic/auditresult/particulars.html?imic_audit_id='+imic_audit_id+'&patient_id='+patient_id+'&visit_id='+visit_id+'&imic_info_id='+imic_info_id+"#topsta"
	});  
} 

function printSF(){
	if(patient.print){
		$.confirm('该患者已经打印过自费知情告知书，是否再次打印？',function(choose){
			if(choose == true){
				toPrint();
			}
		});
	}else{
		toPrint();
	}
}

function toPrint(){
	var url = "/w/hospital_imic/bill/ZFTZS_PRINT/printsf.html";
	var vda = {
		patient_id: patient.patient_id,
		visit_id: patient.visit_id,
		print_id: 'ZFTZS_PRINT',
		doctor_no: patient.doctor_no,
		remark: '自费知情告知书打印',
		product: 'hospital_imic'
	};
	$.call('hospital_common.showturns.print', vda, function(){}, function(e){}, {async: true, remark: false});
	if(doctor._CRSID){
		url += "?_CRSID=1";
			// ygz 2020-09-10 ： 无需登录
			//+ doctor._CRSID;
	}
	
	// dept_name patient_name sex age bedno
	url += '&patient_name=' + patient.patient_name;
	url += '&sex=' + patient.sex;
	url += '&age=' + patient.age;
	url += '&bed_no=' + patient.bed_no;
	url += '&patient_no=' + patient.patient_no;
	url += '&dept_name=' + patient.dept_in_name;
	layer.open({
		type: 2,
	  	title: $(this).text()+'自费知情告知书打印',
	  //skin: 'layui-layer-rim', //加上边框
	  	area: ['300px', '200px'], //宽高
	  //content: "instruction.html?his_drug_code="+drugcode
	  	content: url
	});
}

function toPrint_opwin(){
	var url = "/w/hospital_imic/bill/ZFTZS_PRINT/printsf.html";
	var vda = {
		patient_id: patient.patient_id,
		visit_id: patient.visit_id,
		print_id: 'ZFTZS_PRINT',
		doctor_no: patient.doctor_no,
		remark: '自费知情告知书打印',
		product: 'hospital_imic'
	};
	$.call('hospital_common.showturns.print', vda, function(){}, function(e){}, {async: true, remark: false});
	if(doctor._CRSID){
		url += "?_CRSID=1";
			// ygz 2020-09-10 ： 无需登录
			//+ doctor._CRSID;
	}
	
	// dept_name patient_name sex age bedno
	url += '&patient_name=' + patient.patient_name;
	url += '&sex=' + patient.sex;
	url += '&age=' + patient.age;
	url += '&bed_no=' + patient.bed_no;
	url += '&patient_no=' + patient.patient_no;
	url += '&dept_name=' + patient.dept_in_name;
	window.open(url, $(this).text()+'自费知情告知书打印', 'directories=0, width=800, height=600, location=0, menubar=0, resizable=0,status=0,toolbar=0,top=200,left=200,scrollbars=0');
/*	layer.open({
		type: 2,
	  	title: $(this).text()+'自费知情告知书打印',
	  //skin: 'layui-layer-rim', //加上边框
	  	area: ['300px', '200px'], //宽高
	  //content: "instruction.html?his_drug_code="+drugcode
	  	content: url
	});*/
}

// 是否打印
function check_print(){
	var vda = {
		patient_id: patient.patient_id,
		visit_id: patient.visit_id,
		print_id: 'ZFTZS_PRINT'
	};
	$.call('hospital_common.showturns.checkPrint', vda, function(ret){
		//console.log(ret);
		if(ret.prs && ret.prs.length > 0){
			patient.print = true;
			$('imic_zzf_desc').text('该患者已经打印过自费知情告知书');
		}else{
			patient.print = false;
			$('imic_zzf_desc').text('该患者还未打印自费知情告知书，请点击顶部【打印自费告知书】打印');
		}
	}, function(e){}, {async: true, remark: false});
}
