<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="审方查询" disloggedin="true">
<style type="text/css">
	.choose{background-color: #d3d3d3 !important;}
</style>
	<s:row>
		<s:form id="form" >
			<s:row>
				<s:datefield label="开始时间" name="start_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"></s:datefield>
				<s:datefield label="截止时间" name="end_time" format="yyyy-MM-dd HH:mm:ss" autocomplete="off"></s:datefield>
				<%--<s:textfield name="doctor" label="医生"></s:textfield>
				<s:autocomplete id="dp" label="部门" name="dept_code" cols="1" action="hospital_common.auditset.checkdept.queryToDeptType" value="${dept_code}" text="${dept_name}" limit="10" params="{&#34;sort&#34;:&#34;cast(dept_code as decimal(18,2))&#34;,&#34;is_all&#34;:&#34;1&#34;}" editable="false">
					<s:option value="$[dept_code]" label="$[dept_name]($[dept_type])">
							$[dept_code]
							$[dept_name]
							($[dept_type])
					</s:option>
				</s:autocomplete> --%>
				<input type="hidden" name="doctor" >
				<s:textfield cols="1"  name="doctor_name" label="医生" readonly="true" placeholder="双击选择医生" dbl_action="ss();"></s:textfield>		
				<input type="hidden" name="dept_code" >
				<s:textfield cols="1"  name="dept_name" label="开嘱科室" placeholder="双击选择科室" readonly="true" dbl_action="ss();"></s:textfield>
				<s:checkbox name="d_type_query" cols="1">
					<s:option value="1" label="住院"></s:option>
					<s:option value="2" label="门诊"></s:option>
					<s:option value="3" label="急诊"></s:option>
					<s:option value="4" label="护士"></s:option>
					<s:option value="5" label="医保结算"></s:option>
				</s:checkbox>
				<s:checkbox name="is_deal" cols="1">
					<s:option value="1" label="查看医生未处理"></s:option>
				</s:checkbox>
				<s:checkbox name="state_query" cols="3" label="审查结果">
					<s:option value="HL_Y" label="通过"></s:option>
					<s:option value="DB" label="返回调整"></s:option>
					<s:option value="HL_T" label="提示医生"></s:option>
					<s:option value="Y" label="药师通过"></s:option>
					<s:option value="N" label="药师不通过"></s:option>
					<s:option value="DS" label="医生确认用药"></s:option>
					<s:option value="DN" label="医生取消用药"></s:option>
					<s:option value="DQ1" label="非工作时间强制通过"></s:option>
				</s:checkbox>
				<s:textfield cols="1"  name="patient" label="患者"></s:textfield>
				<s:textfield cols="1"  name="yz_cf_no" label="医嘱/处方号"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
	<s:row>
		<s:table id="datatable" label="审方列表"  fitwidth="true" autoload="false" action="hospital_common.showturns.queryAudit" sort="true">
            <s:toolbar>
            	<s:button label="刷新" icon="fa fa-search" onclick="query();"></s:button>
            </s:toolbar>
            <s:table.fields>
                <s:table.field name="common_create_time" sort="true" label="提交审方时间" width="110px" datatype="script">
                	var ct = record.common_create_time;
                	return ct.substring(5);
                </s:table.field>
                <s:table.field name="p_type" label="开单类型" datatype="script" width="60px">
                	var p_type = record.p_type;
                	var d_type = record.d_type;
                	if(p_type == 1){
                		p_type= '医嘱';
                	}else if (p_type == 2){
                		p_type= '处方';
                	}else if(p_type == 3){
                		p_type= '诊疗';
                	}else if(p_type == 4){
                		p_type= '收费';
                	}
                	if(d_type == 1 ){
                		d_type = '住院';
                	}else if(d_type == 2){
                		d_type = '门诊';
                	}else if (d_type == 3){
                		d_type = '急诊';
                	}else if(d_type == 4){
                		d_type = '护士';
                	}else if(d_type == 5){
                		d_type = '结算审核';
                	}
                	return '<a onclick="druglist(\''+record.id+'\', this);">'+d_type + p_type+ '</a>';
                </s:table.field>
                <s:table.field name="patient_name" label="患者" datatype="template" width="100px">
                	<a href="javascript:void(0);" onclick="toPatient('$[patient_id]','$[visit_id]', this)">$[patient_name]</a>
                </s:table.field>
                <%-- <s:table.field name="visit_id" label="住院次数"></s:table.field> --%>
                <s:table.field name="patient_dept_name" label="开嘱科室" width="120px" sort="true"></s:table.field>
                <s:table.field name="doctor_no" label="开嘱医生" datatype="script" width="80px">
                	return record.doctor_name + '(' +record.doctor_no+')';
                </s:table.field>
                <s:table.field name="is_after" label="审查类型" datatype="script">
                	var is_after = record.is_after; 
                	if(is_after == '1'){
                		return '事后审查';
                	}else{
                		return '即时审查';
                	}
                </s:table.field>
                <s:table.field name="state" label="审方结果" datatype="script" sort="true">
                	var state = record.state;
                	var state_name ;
                	if(state == 'HL_Y'){
                		state_name = '智能审查通过';
                	}else if(state == 'HL_N'){
                		state_name = '<font style="color: #747f00">智能审查拦截，等待医生决策</font>';
                	}else if(state == 'HL_F'){
                		state_name = '<font style="color: red">智能审查超时(通过)</font>';
                	}else if(state == 'DB'){
                		var flag = record.order_flag;
                		if(flag == '9'){
                			state_name = '医生重复提交审查';
                		}else{
                			state_name = '智能审查拦截，医生返回调整';
                		}
                	}else if(state == 'Y'){
                		state_name = '智能审查拦截，药师审查通过';
                		if(record.is_sure == 1){
                			state_name = state_name + '，【医生已查看】';
                		}
                	}else if(state == 'N'){
                		state_name = '智能审查拦截，药师审查不通过';
                		if(record.is_sure == 1){
                			state_name = state_name + '，【医生已查看】';
                		}
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
                		state_name = '<font style="color: #bd850a">智能审查拦截，医生强制使用，等待药师审查</font>';
                	}else if(state == 'DQ1'){
                		state_name = '智能审查拦截，医生强制使用，药师非工作时间系统自动通过';
                	}else if(state == 'is_overtime'){
                		state_name = '<font style="color: #bd850a">智能审查拦截，药师审查超时</font>';
                	}else{
                		state_name = '<font style="color: red">正在审查...</font>';
                	}
                	return state_name;
                </s:table.field>
                <s:table.field name="yaoshi_name" label="审方药师" sort="true" datatype="script">
	                if(record.yaoshi_name){
	                	return record.yaoshi_name + (record.pharmacist_id == null?'':('(' + (record.pharmacist_id) +')'));
	                }else{
	                	return "";
	                }
                </s:table.field>
                <s:table.field name="pharmacist_exam_time" label="药师审查时间"></s:table.field>
                <s:table.field name="z_cost_time" sort="true" label="审查耗时" datatype="script">
                	var time = record.z_cost_time;
                	if(time > 0){
                		return time/1000 + 's';
                	}else{
                		return '0';
                	}
                </s:table.field>
                <s:table.field name="cost_time" label="已等待药师" datatype="script">
                	var state = record.state;
                	var cost_time = record.cost_time;
                	if(cost_time >= 300 && cost_time <= 600){
                		cost_time = '<span  style="color: orange" class="up_time">'+cost_time+'</span>' 
                	}else if ( cost_time > 600){
                		cost_time = '<span  style="color: red" class="up_time">'+cost_time+'</span>' 
                	}else{
                		cost_time = '<span class="up_time">'+cost_time+'</span>' 
                	}
                	if(state == 'DQ'){
                		return cost_time;
                	}else if(state == 'is_overtime'){
                		return cost_time;
                	}else{
                		return "";
                	}
                </s:table.field> 
                <s:table.field name="caozuo" label="操作" datatype="script">
                	var fhtml = [] ;
                	var state = record.sys_audit_result;
                	if(state == 'HL_Y'){
                		return '√' + ' | <a href="javascript:void(0)" onclick="seeCInfo(\''+ record.common_id +'\')">日志</a>';
                	}else{
                		if(state != 'HL_F'){
	                		fhtml.push('<a href="javascript:void(0)" onclick="auditDetail(\''+ record.common_id +'\')">详情</a>');
                		}
	               		fhtml.push(' | <a href="javascript:void(0)" onclick="seeCInfo(\''+ record.common_id +'\')">日志</a>');
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
		},function(e){}, {async: false, remark: false  });
		query();
		$('#datatable').on('click', 'tbody tr', function(e) { 
			$(this).addClass('choose').siblings().removeClass('choose');
		});
		setInterval('sand1()', 1000);
	});
	
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
		$("#datatable").params(data);
		$("#datatable").refresh();
	}
	
	function auditDetail(common_id){
		  $.modal("/w/hospital_common/showturns/show.html?id="+ common_id,"查看审查详情",{
	    	width: '936px',
	    	height: '604px',
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
	
	$('[name=dept_name]').dblclick(function(){
		var code = $('[name=dept_code]').val();
		var data = $('#form').getData();
		var datasouce = data.d_type_query;
		$.modal("/w/ipc/sample/choose/department.html", "添加科室", {
			height: "550px",
			width: "40%",
			code: code,
			maxmin: false,
			datasouce: JSON.stringify(datasouce),
			callback : function(rtn) {
				if(rtn){
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('[name=dept_name]').val(name);
					$('[name=dept_code]').val(code);
				}
		    }
		});
	});
	
	$('[name=doctor_name]').dblclick(function(){
		var code = $('[name=doctor]').val();
		$.modal("/w/ipc/sample/choose/doctor.html", "添加医生", {
			height: "550px",
			width: "50%",
			code: code,
			maxmin: false,
			callback : function(rtn) {
				if(rtn){
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('[name=doctor_name]').val(name);
					$('[name=doctor]').val(code);
				}
		    }
		});
	});
	
	function seeCInfo(common_id){
		$.modal('/w/hospital_common/auditrecord/info.html',"查看审查日志",{
		  	width: '950px',
		  	height: '600px',
		  	id: common_id,
		    callback : function(e){}
		});
	}
</script>