<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="查看审查问题" disloggedin="true">
<style type="text/css">
	.descr_cla{
		overflow: hidden;
	    text-overflow: ellipsis;
	    white-space: nowrap;
	}
	.tioaz{
		margin-right: 40px;
    	float: right;
	}
</style>
	<s:row>
		<s:form id="form"  fclass="portlet light bordered" collapsed="true" extendable="true" >
			<s:row>
				<s:datefield label="开始时间" name="s_time" format="yyyy-MM-dd HH:mm:ss" ></s:datefield>
				<s:datefield label="截止时间" name="e_time" format="yyyy-MM-dd HH:mm:ss" ></s:datefield>
			</s:row>
			<s:row>
				<s:select label="科室类型" name="d_type" value="1">
					<s:option label="住院" value="1"></s:option>
					<s:option label="门诊" value="2"></s:option>
					<s:option label="急诊" value="3"></s:option>
				</s:select>
				<s:textfield label="科室名称" name="dept_name"  cols="1" dbl_action='zl_select_jcx057_2_01_common,jcx057_2_common' disabled="disabled"></s:textfield>
			</s:row>
			<s:row>
				<s:select label="处方/医嘱" name="p_type" value="0" >
					<s:option label="全部" value="0"></s:option>
					<s:option label="医嘱" value="1"></s:option>
					<s:option label="处方" value="2"></s:option>
				</s:select>
				<s:textfield label="问题类型" name="sort_name"  cols="1" dbl_action='zl_select_jcx057_2_02_common,jcx057_2_common' disabled="disabled"></s:textfield>
				<s:textfield label="患者" name="patient"  cols="1" ></s:textfield>
			</s:row>
			<s:row>
				<s:checkbox label="严重程度" name="level" cols="1" style="border:none">
					<s:option label="关注" value="1"></s:option>
					<s:option label="慎用" value="2"></s:option>
					<s:option label="不推荐" value="3"></s:option>
					<s:option label="禁忌" value="4"></s:option>
				</s:checkbox>
				<s:radio label="排除已经调整" name="is_tiaoz_audit" cols="1" value="2">
					<s:option label="全部" value="2"></s:option>
					<s:option label="是" value="1"></s:option>
					<s:option label="否" value="0"></s:option>
				</s:radio>
				<s:textfield label="医嘱信息" name="drug_name_w"  cols="1" ></s:textfield>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
	    <s:table select="single" id="datatable" label="审查问题" autoload="false"  action="hospital_common.changeqdetail.query"  sort="true">
	        <s:toolbar>
	        	<s:button label="筛选列 " icon="fa fa-random" onClick="shaixuan();"></s:button>
	        	<s:button label="导出" icon="fa fa-random" action="toexcel"></s:button>
				<s:button label="查询" onclick="query()" icon="fa fa-search"></s:button>
			</s:toolbar>
	        <s:table.fields>
	            <s:table.field name="patient_name" label="患者姓名" datatype="script" width="120px" sort="true">
	            	var html = [];
	            	html.push('<a href="javascript:void(0)" onclick="patientinfo');
	            	html.push('(\''+record.patient_id+'\', \''+record.visit_id+'\')">');
	            	html.push(record.patient_name + '</a>');
	            	return html.join('');
	            </s:table.field>
	            <s:table.field name="dept_name" label="科室"  width="130px" sort="true"></s:table.field>
	            <s:table.field name="check_level_name" label="严重程度"  width="80px" sort="true"></s:table.field>
	            <s:table.field name="shenc_change_level_name" label="调整后级别" sort="true" width="100px" datatype="script">
	            	var is_valid = record.is_valid;
	            	if(is_valid==null){
	            		is_valid = '';
	            	}else{
	            		is_valid = '<font style="color: orange;">(' +record.is_valid+')</font>';
	            	}
	            	if(record.shenc_change_level_name){
	            		return  record.shenc_change_level_name + is_valid ;
	            	}else{
	            		return '';
	            	}
	            </s:table.field>
	            <s:table.field name="sort_name" label="审查类型" sort="true"  width="80px" ></s:table.field>
	            <s:table.field name="ordermessage" label="医嘱信息"  width="380px" datatype="script" sort="true">
	            	var html = [], code = [] ,drug = [];
	            	if(record.ordermessage_code){
		            	code = record.ordermessage_code.split(',');
	            	}
	            	if(record.ordermessage){
		            	drug = record.ordermessage.replace(/<br>/g, ',') .split(',');
	            	}
	            	if(drug){
	            		var _code = '';
		            	for(var i = 0; i< drug.length; i++){
			            	if(code[i]){
			            		_code = code[i].replace('[', '');
			            		_code = code[i].replace(']', '');
			            		html.push('<a onclick="drugSms(\''+_code+'\');" data-id = "'+drug+'">'+drug[i]+ '</a><br>');
			            	}else{
			            		html.push(drug[i]+'<br>');
			            	}
		            		
		            	}
		            	return html.join('');
	            	}else{
	            		return '';
	            	}
	            </s:table.field>
	            <s:table.field name="is_shenc_pass" label="审查合理"  width="60px" datatype="script" sort="true">
	           		var is_pass_tz = record.is_shenc_pass ;
	           		var html = [] ;
	           		var ss = record.description.replace(/\	/g,"").replace(/[\r\n]/g,"").replace(/(^\s*)/g, "");
	           		var audit_source_type = record.audit_source_type ;
	           		if(audit_source_type != '自定义审查'){
		           		if(is_pass_tz == '结果调整'){
		           			html.push('<span data-content="'+ss+'"><a href="javascript:void(0)" onclick="changeLevel');
			           		html.push('(\''+record.auto_audit_id+'\', \''+record.check_result_info_id+'\',this)">');
			            	html.push(record.is_shenc_pass + '</a></span>');
		           		}else{
		           			html.push('<span data-content="'+ss+'">'+record.is_shenc_pass+'</span>');
		           		}
	           		}
	            	return html.join('');
	            </s:table.field>
	             <s:table.field name="is_add_advice" label="添加意见"  width="60px" datatype="script" sort="true">
	           		var html = [] ;
	           		var ss = record.description.replace(/\	/g,"").replace(/[\r\n]/g,"").replace(/(^\s*)/g, "");
	           		var audit_source_type = record.audit_source_type ;
	           		if(audit_source_type != '自定义审查'){
		           		if(record.is_add_advice == '添加意见'){
			            	html.push('<span data-content-ad="'+ss+'"><a href="javascript:void(0)" onclick="addAdvice');
			           		html.push('(\''+record.auto_audit_id+'\', \''+record.check_result_info_id+'\', \''+record.auto_audit_level+'\',this)">');
			            	html.push(record.is_add_advice+'</a></span>');
		           		}else{
		           			html.push('<span data-content-ad="'+ss+'"><a href="javascript:void(0)" style="color:#b88b07" onclick="addAdvice');
			           		html.push('(\''+record.auto_audit_id+'\', \''+record.check_result_info_id+'\', \''+record.auto_audit_level+'\',this)">');
			            	html.push(record.is_add_advice+'</a></span>');
		           		}
	           		}
	            	return html.join('');
	            </s:table.field>
	            <s:table.field name="description" label="描述"  width="400px" maxlength="10" datatype="script" sort="true">
	            	var description = record.description ;
	            	if(description){
	            		var simple_des = description.substring(0 , 80);
		            	if(description.length > 80){
		            		simple_des = simple_des + '...'
		            	}
		            	var str = description.replace(/[\r\n]/g,"");
		            	return '<span title="'+description+'">'+simple_des+'<a onclick="copy(\''+str+'\');">复制</a></span>';
	            	}else{
	            		return ' ';
	            	}
	            	
	            </s:table.field>
	            <s:table.field name="reference" label="参考"  width="300px" datatype="script" sort="true">
	            	var reference = record.reference ;
	            	if(reference){
	            		var simple_des = reference.substring(0 , 60);
		            	if(reference.length > 60){
		            		simple_des = simple_des + '...'
		            	}
		            	return '<span title="'+reference+'">'+simple_des+'</span>';
	            	}else{
	            		return ' ';
	            	}
	            	
	            </s:table.field>
	            <s:table.field name="audit_source_type" label="审查来源"  width="120px" sort="true"></s:table.field>
	            <s:table.field name="check_datetime" label="审查日期"  width="160px" sort="true"></s:table.field>
	            <s:table.field name=" " label="药师意见"  width="100px" sort="true"></s:table.field>
	            <s:table.field name="doctor_advice" label="医生意见"  width="130px" sort="true"></s:table.field>
	            <s:table.field name="shenc_pass_ren" label="调整人" width="100px" sort="true"></s:table.field>
	            <s:table.field name="shenc_pass_time" label="调整时间" width="160px" sort="true"></s:table.field>
	            <s:table.field name="shenc_pass_pharmacist_advice" label="调整人意见" sort="true" width="200px" datatype="script">
	            	var shenc_pass_pharmacist_advice = record.shenc_pass_pharmacist_advice ;
	            	if(shenc_pass_pharmacist_advice){
	            		var simple_des = shenc_pass_pharmacist_advice.substring(0 , 60);
		            	if(shenc_pass_pharmacist_advice.length > 60){
		            		simple_des = simple_des + '...'
		            	}
		            	return '<span title="'+shenc_pass_pharmacist_advice+'">'+simple_des+'</span>';
	            	}else{
	            		return ' ';
	            	}
	            	
	            </s:table.field>
	            <s:table.field name="auto_audit_id" label="审查流水id"  width="150px" sort="true"></s:table.field>
	            <s:table.field name="check_result_info_id" label="审查问题id"  width="150px" sort="true"></s:table.field>
	        </s:table.fields>
	    </s:table>
	</s:row>
	<s:row>
	<div class="tioaz">
		<input type="text" name="tiaoz_page" style="width: 50px;"/>
		<input type="button" name="tiaoz_button" value="跳转">
	</div>
	</s:row>
</s:page>
<script type="text/javascript">
	var myscroll = 0;
	var widthscroll = 0;
	var drug_name;
	$(function(){
		defaultChoose();
		drug_name='${param.drug_name}';
		if(drug_name!=null && drug_name!=""){
			query();
		}
		$('.dataTables_scrollBody').css('max-height', window.innerHeight - 245);
		//query();
		$('input[name=tiaoz_button]').click(function(){
			var page = $('input[name=tiaoz_page]').val();
			if(page){
				var formData = $('#form').getData();
				formData.timeout = 0;
				$("#datatable").params(formData);
				$("#datatable").DataTable().page(page - 1).draw(false);
				$('.dataTables_scrollBody').scroll(function(){
					myscroll = $(this).scrollTop();
					widthscroll = $(this).scrollLeft();
				})
			}
		})
	});
	
	function p(s) {
	    return s < 10 ? '0' + s: s;
	}
	
	function defaultChoose(){
		var myDate = new Date();
		//获取当前年
		var year=myDate.getFullYear();
		//获取当前月
		var month=myDate.getMonth()+1;
		//获取当前日
		var date=myDate.getDate(); 
		//var h=myDate.getHours();       //获取当前小时数(0-23)
		//var m=myDate.getMinutes();     //获取当前分钟数(0-59)
		//var s=myDate.getSeconds();  
		var startTime=year+'-'+p(month)+"-"+p(date)+" 00:00:00";
		var endTime=year+'-'+p(month)+"-"+p(date)+" 23:59:59";
		$('[name="s_time"]').val(startTime);
		$('[name="e_time"]').val(endTime);
		$('[name="dept_name"]').val('全部');
		$('[name="sort_name"]').val('全部');
	}
	
	function query_myscroll(){
		var real_myscroll = myscroll;
		var real_widthscroll = widthscroll;
		var formData = $('#form').getData();
		formData.async = false;
		formData.timeout = 0;
		$("#datatable").params(formData);
		start = $("#datatable").dataTable().fnSettings()._iDisplayStart; 
		total = $("#datatable").dataTable().fnSettings().fnRecordsDisplay();
		var p = $("#datatable").DataTable().page();
		if((total-start)==1){
			if (start > 0) {
				$("#datatable").refresh_table(p-1);
			}else{
				$("#datatable").refresh();
			}
		}else{
			$("#datatable").refresh_table(p);
		}
		$('.dataTables_scrollBody').scrollTop(real_myscroll);
		$('.dataTables_scrollBody').scroll(function(){
			myscroll = $(this).scrollTop();
			widthscroll = $(this).scrollLeft();
		})
	}
	
	
	function query(){
		//debugger
		var formData = $('#form').getData();
		formData.drug_name_w=drug_name;
		formData.timeout = 0;
		$("#datatable").params(formData);
		start = $("#datatable").dataTable().fnSettings()._iDisplayStart; 
		total = $("#datatable").dataTable().fnSettings().fnRecordsDisplay();
		var p = $("#datatable").DataTable().page();
		if((total-start)==1){
			if (start > 0) {
				$("#datatable").refresh_table(p-1);
			}else{
				$("#datatable").refresh();
			}
		}else{
			$("#datatable").refresh_table(p);
		}
		$('.dataTables_scrollBody').scroll(function(){
			myscroll = $(this).scrollTop();
			widthscroll = $(this).scrollLeft();
		})
		drug_name=null;
	}
	
	function patientinfo(patient_id, visit_id){
		$.modal('/w/hospital_common/patient/index.html?patient_id='+patient_id+'&visit_id='+visit_id+'',
				'患者详情',
				{
			       width:"96%",
			       height:"99%",
			       callback : function(e){}
				}
		);
	}
	
	function changeLevel(auto_audit_id, check_result_info_id, t_this){
		$.modal('/w/ipc/comment/pass.html?auto_audit_id='+auto_audit_id+'&check_result_info_id='+check_result_info_id+'',
				'调整问题等级',
				{
			       width:"500px",
			       height:"460px",
			       callback : function(rtn){
			    	   if(rtn){
			    		   //changeTZ(rtn);
			    		   query_myscroll();
			    	   }
			       }
				}
		);
		event.stopPropagation();
	}
	
	function addAdvice(auto_audit_id, check_result_info_id, level,t_this){
		if($(t_this).text() == "已经调整"){
			$.modal('/w/ipc/comment/pass.html?auto_audit_id='+auto_audit_id+'&check_result_info_id='+check_result_info_id+'&level='+level+'&is_add=1',
					'添加药师意见',
					{
				       width:"500px",
				       height:"460px",
				       callback : function(rtn){
				    	   if(rtn){
				    		   if(rtn.pharmacist_todoctor_advice){
				    			   //changeTZ(rtn);
				    			   query_myscroll();
				    			}
				    	   }
				       }
					}
			);
		}else{
			$.modal('/w/ipc/comment/pass.html?auto_audit_id='+auto_audit_id+'&check_result_info_id='+check_result_info_id+'&level='+level+'',
					'添加药师意见',
					{
				       width:"500px",
				       height:"460px",
				       callback : function(rtn){
				    	   if(rtn){
				    		   if(rtn.pharmacist_todoctor_advice){
				    			   //changeTZ(rtn);
				    			   query_myscroll();
				    			}
				    	   }
				       }
					}
			);
		}
		event.stopPropagation();
	}
	
	function changeTZ(rtn){
		var d = rtn.description.replace(/(^\s*)/g, "").replace(/\ /g,"\\ ").replace(/\	/g,"\\	").replace(/\./g,"\\.").replace(/\(/g,"\\(").replace(/\]/g,"\\]").replace(/[\r\n]/g,"");
		var elelist = document.querySelectorAll('[data-content="'+d+'"]');
		var ad = document.querySelectorAll('[data-content-ad="'+d+'"] a');
		for(var i=0;i<elelist.length;i++){
		 	elelist[i].innerHTML = "已经调整";
		}
		if(rtn.pharmacist_todoctor_advice){
			 for(var j=0;j<ad.length;j++){
				  ad[j].setAttribute('style', 'color: #b88b07 !important');
				  ad[j].innerText = '已经调整';
			 }
		}
	}
	
	function drugSms(drug_code){
		 if(drug_code.indexOf('[') > 0){
			 return ;
		 }
		 $.modal("/w/ipc/auditresult/instruction.html?his_drug_code="+drug_code,"查看药品说明书",{
	        width:"70%",
	        height:"90%",
	        callback : function(e){
	        }
		}); 
		event.stopPropagation();
	}
	
	function copy(becopy){
		becopy = becopy.replace('\t','');
		const input = document.createElement('input');
	    document.body.appendChild(input);
	    input.setAttribute('value', becopy);
	    input.select();
	    if (document.execCommand('copy')) {
	        document.execCommand('copy');
	        console.log('复制成功');
	    }
	 	document.body.removeChild(input);
	 	layer.msg('复制成功', {
	 		  icon: 1,
	 		  time: 800 //2秒关闭（如果不配置，默认是3秒）
	 		}, function(){
	 		  //do something
	 		}); 
	 	event.stopPropagation(); 
	}
	
	function shaixuan(){
		$('#datatable').setting_table();
	}
</script>