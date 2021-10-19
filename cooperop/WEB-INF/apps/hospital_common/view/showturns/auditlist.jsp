<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="审方查询" disloggedin="false">
<style type="text/css">
	.choose{
		background-color: #d3d3d3 !important;
	}
</style>
	<s:row>
		<s:form id="form" >
			<s:row>
				<s:datefield label="开始时间" name="start_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"></s:datefield>
				<s:datefield label="截止时间" name="end_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"></s:datefield>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="审方列表" autoload="false" action="hospital_common.showturns.queryAuditAll" sort="true">
            <s:toolbar>
            	<s:button label="刷选" icon="fa fa-search" onclick="query();"></s:button>
            </s:toolbar>
            <s:table.fields>
                <s:table.field name="create_time" label="提交审方时间" width="120px"></s:table.field>
                <s:table.field name="p_type" label="开单类型" datatype="script">
                	var p_type = record.p_type;
                	var d_type = record.d_type;
                	if(p_type == 1){
                		p_type= '医嘱';
                	}else if (p_type == 2){
                		p_type= '处方';
                	}
                	if(d_type == 1 ){
                		d_type = '住院';
                	}else if(d_type == 2){
                		d_type = '急诊';
                	}else if (d_type == 3){
                		d_type = '门诊';
                	}
                	return '<a onclick="druglist(\''+record.id+'\', this);">'+d_type + p_type+ '</a>';
                </s:table.field>
                <s:table.field name="audit_state" label="异常医嘱" datatype="script">
                	var audit_state = record.audit_state;
                	var state = record.state;
                	var flag = record.order_flag;
                	if(state == 'N'){
	                	return '<a onclick="errorlist(\''+record.id+'\', this);">不通过医嘱</a>';
                	}else if((state == 'D' && flag != '-1')){
                		return '<a style="color: red" onclick="tz();" title="小弹窗或者在任务栏中确认，点此链接跳转">请您再次确认医嘱使用</a>';
                	}
                	return '';
                </s:table.field>
                <s:table.field name="patient_name" label="病人" datatype="template">
                	<a href="javascript:void(0);" onclick="toPatient('$[patient_id]','$[visit_id]', this)">$[patient_name]</a>
                </s:table.field>
               <%--  <s:table.field name="visit_id" label="住院次数"></s:table.field> --%>
                <s:table.field name="patient_dept_name" label="开嘱科室"  width="120px"></s:table.field>
                <%-- <s:table.field name="doctor_no" label="开嘱医生"></s:table.field> --%>
               <%--  <s:table.field name="is_after" label="审查类型" datatype="script">
                	var is_after = record.is_after; 
                	if(is_after == '1'){
                		return '事后审查';
                	}else{
                		return '即时审查';
                	}
                </s:table.field> --%>
                <s:table.field name="state" label="审方结果" datatype="script">
                	var state = record.state;
                	var state_name ;
                	if(state == 'HL_Y'){
                		state_name = '智能审查通过';
                	}else if(state == 'HL_N'){
                		state_name = '<font style="color: #747f00">智能审不通过,等待医生决策</font>';
                	}else if(state == 'HL_F'){
                		state_name = '<font style="color: red">审方超时(通过)</font>';
                	}else if(state == 'DB'){
                		state_name = '智能审查拦截，医生返回调整';
                	}else if(state == 'Y'){
                		state_name = '智能审查拦截，药师审查通过';
                	}else if(state == 'N'){
                		state_name = '智能审查拦截，药师审查不通过';
                	}else if(state == 'DS'){
                		var flag = record.order_flag;
	                	if(flag == '-1'){
	                		state_name = '智能审查拦截，医生强制使用，药师审查超时，系统返回医生决定，医生确认用药';
	                	}else{
                			state_name = '智能审查拦截，药师审查：医生决定，医生确认用药';
	                	}
                	}else if(state == 'DN'){
	                	var flag = record.order_flag;
	                	if(flag == '-1'){
	                		state_name = '智能审查拦截，医生强制使用，药师审查超时，系统返回医生决定，医生取消用药';
	                	}else{
                			state_name = '智能审查拦截，药师审查：医生决定，医生取消用药';
	                	}
                	}else if(state == 'HL_T'){
                		state_name = '智能审查提示(通过)';
                	}else if(state == 'D'){
                		var flag = record.order_flag;
	                	if(flag == '-1'){
	                		state_name = '<font style="color: #747f00">智能审查拦截，医生强制使用，药师审查超时，系统自动返回医生决定</font>';
	                	}else{
                			state_name = '<font style="color: #747f00">智能审查拦截，药师审查：医生决定，等待医生决策</font>';
	                	}
                	}else if(state == 'DQ'){
                		state_name = '<font style="color: orange">智能审查拦截，医生强制使用，等待药师审查</font>';
                	}else if(state == 'DQ1'){
                		state_name = '智能审查拦截，医生强制使用，药师非工作时间系统自动通过';
                	}else if(state == 'is_overtime'){
                		state_name = '<font style="color: orange">智能审查拦截，药师审查超时</font>';
                	}else{
                		state_name = '<font style="">正在审查...</font>';
                	}
                	return state_name;
                </s:table.field>
                <s:table.field name="yaoshi_name" label="审方药师" datatype="script"  width="80px">
	                if(record.yaoshi_name){
	                	return record.yaoshi_name + (record.pharmacist_id == null?'':('(' + (record.pharmacist_id) +')'));
	                }else{
	                	return "";
	                }
                </s:table.field>
                <s:table.field name="pharmacist_exam_time" label="药师审查时间"  width="120px"></s:table.field>
                <s:table.field name="cost_time" label="已等待" datatype="script"  width="40px">
                	var state = record.state;
                	var cost_time = record.cost_time;
                	if(cost_time >= 300 && cost_time <= 600){
                		cost_time = '<span  style="color: orange" class="up_time">'+cost_time+'</span>' 
                	}else if ( cost_time > 600){
                		cost_time = '<span  style="color: red" class="up_time">'+cost_time+'</span>' 
                	}
                	if(state == 'DQ'){
                		return cost_time;
                	}else if(state == 'is_overtime'){
                		return cost_time;
                	}else{
                		return "";
                	}
                </s:table.field> 
                <s:table.field name="caozuo" label="操作" datatype="script"  width="20px">
                	var fhtml = [] ;
                	var state = record.state;
                	if(state == 'HL_Y'){
                		return '√';
                	}else{
                		fhtml.push('<a href="javascript:void(0)" onclick="auditDetail(\''+ record.id +'\')">');
                		fhtml.push('详情</a>');
                	}
                	return fhtml.join('');
                </s:table.field>
            </s:table.fields>
        </s:table>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		var today ;
		$.call('hospital_common.showturns.onlyGetTime', {}, function(rtn){
			today = rtn.today;
			$('[name = start_time]').val(today + ' 00:00:00');
			$('[name = end_time]').val(today + ' 23:59:59');
		},function(e){}, {async: false, remark: false   });
		query();
		setInterval('sand1()', 1000);
	});
	
	function tz(){
		$("#indexIframe_im",parent.document).hide();
		$("#indexIframe_oth",parent.document).show();
		var url = cooperopcontextpath + "/w/application/task/mine.html";
		$("#indexIframe_oth",parent.document).attr("src", url);
		$(".page-right-content.zzc",parent.document).click();
	}
	
	function sand1(){
		$('.up_time').each(function(){
			var _this = $(this);
			var new_time = parseInt(_this.text()) + 1;
			if(new_time >= 300 && new_time <= 600){
				_this.css('color', 'orange');
			}else if (new_time > 600){
				_this.css('color', 'red');
			}
			_this.text(new_time);
		});
	}
	
	function query(){
		var data = $('#form').getData();
		data.doctor_no = userinfo.no;
		//console.log(data);
		$("#datatable").params(data);
		$("#datatable").refresh();
	}
	
	function auditDetail(ipc_id){
	    //$.modal("detailshow.html?id="+ ipc_id,"查看审查详情",{
	    $.modal("/w/ipc/auditflow/show.html?auto_audit_id="+ ipc_id,"查看审查详情",{
	    	width: '935px',
	    	height: '645px',
	        callback : function(e){}
	    });
	}
	    
	function toPatient(patient_id, visit_id, _this){
		$(_this).css('color', '#900987');
		$.modal('patientdetail.html?patient_id='+patient_id+'&&visit_id='+visit_id+'',"查看审查详情",{
		  	width: '900px',
		  	height: '550px',
		      callback : function(e){}
		  });
	}
	
	function druglist(id, _this){
		$(_this).css('color', '#900987');
		$.modal('/w/ipc/auditresult/druglist.html?auto_audit_id='+ id,"查看医嘱",{
		  	width: '900px',
		  	height: '550px',
		    callback : function(e){}
		});
	}
	
	function errorlist(id, _this){
		var start_time = $('input[name="start_time"]').val();		
		var end_time = $('input[name="end_time"]').val();	
		$(_this).css('color', '#900987');
		$.modal('/w/ipc/auditresult/druglist.html?auto_audit_id='+ id + '&is_ywlsb=1&start_time='+ start_time+ '&end_time='+end_time,"查看不通过医嘱",{
		  	width: '900px',
		  	height: '550px',
		    callback : function(e){}
		});
	}
	
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "H+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
</script>