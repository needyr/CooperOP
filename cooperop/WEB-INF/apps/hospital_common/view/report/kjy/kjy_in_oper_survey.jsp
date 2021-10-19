<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style>
/* .top{
	height: 100px;
	border-bottom: 2px #c7c7c7 solid;
	overflow: auto;
	width: calc(100% - 40px);
    margin: 0px 0px 0px 20px;
}
.bottom{
	padding: 20px
} */
</style>
<s:page title="">
<div class="top">
<s:form id="form" collapsed="false" extendable="true">
	<s:row>
	<s:datefield label="出院时间" name="start_time" required="true" format="yyyy-MM-dd" autocomplete="off" value="" cols="1" ></s:datefield>
	<s:datefield label="至" name="end_time" required="true" format="yyyy-MM-dd" autocomplete="off" value="" cols="1" ></s:datefield>
	</s:row>

	<s:row>
		<input type="hidden" name="oper_code" >
		<div class="cols2">
		<label class="control-label">手术名称</label>
		<div class="control-content">
		<textarea style="height: 50px;" class="form-control" readonly="readonly" placeholder="双击选择" name="oper_name" cols="4"  data-autosize-on="true"></textarea>
		</div>
		</div>
		<a onclick="clean_oper_name()">清空</a>
	</s:row>

	<s:row>
	<input type="hidden" name="dept_code" >
	<div class="cols2">
	<label class="control-label">科室</label>
	<div class="control-content">
	<textarea style="height: 40px;" readonly="readonly" class="form-control" placeholder="双击选择科室" name="dept_name" cols="4"  data-autosize-on="true"></textarea>
	</div>
	</div>
	<a onclick="clean_dept_name()">清空</a>
	</s:row>

	<s:row>
	<input type="hidden" name="doctor_code" >
	<div class="cols2">
	<label class="control-label">主管医生</label>
	<div class="control-content">
	<textarea style="height: 40px;" readonly="readonly" class="form-control" placeholder="双击选择医生" name="doctor_name" cols="4"  data-autosize-on="true"></textarea>
	</div>
	</div>
	<a onclick="clean_doctor_name()">清空</a>
	</s:row>

	<s:row>
	<!-- <input type="hidden" name="diagnosisfifter" > -->
	<div class="cols2">
	<label class="control-label">诊断</label>
	<div class="control-content">
	<textarea style="height: 40px;" class="form-control" placeholder="双击选择诊断" name="diagnosis_name" cols="4"  data-autosize-on="true"></textarea>
	</div>
	</div>
	<a onclick="clean_diagnosis_name()">清空</a>
	</s:row>

	<s:row>
		<s:checkbox label="切口类型" cols="2" name="incision_type">
			<s:option value ="0" label="非手术"></s:option>
			<s:option value ="Ⅰ" label="I类"></s:option>
			<s:option value ="Ⅱ" label="II类"></s:option>
			<s:option value ="Ⅲ" label="III类"></s:option>
		</s:checkbox>
	</s:row>

</s:form>
</div>
<div class="bottom">
<s:table id="datatable1" active="true" label="使用情况调查表" action="hospital_common.report.kjy.querykjyoper" autoload="false" fitwidth="false">
<s:toolbar>
	<s:button label="导出" icon="fa fa-random" onclick="tableToExcel('datatable1', 'newexcel')"></s:button>
	<s:button label="打印" icon="" onclick="print_table('datatable1');"></s:button>
	<s:button label="查询" icon="" onclick="query('datatable1');"></s:button>
</s:toolbar>
<s:table.fields>
	<s:table.field name="patient_no" datatype="string" label="住院号" width="80"></s:table.field>
	<s:table.field name="patient_id" datatype="string" label="患者ID" width="80"></s:table.field>
	<s:table.field name="patient_name" datatype="script" label="患者姓名" width="80" >
	 	return '<a onclick="query_orders(\''+record.patient_id+'\',\''+record.visit_id+'\')">'
			+ record.patient_name+'</a>'
	</s:table.field>
	<s:table.field name="age" datatype="string" label="年龄" width="80"></s:table.field>
	<s:table.field name="sex" datatype="string" label="性别" width="80"></s:table.field>
	<s:table.field name="dept_name" datatype="string" label="科室" width="120"></s:table.field>
	<s:table.field name="attending_doctor" datatype="string" label="主管医生" width="100"></s:table.field>
	<s:table.field name="admission_datetime" datatype="string" label="入院时间" width="120"></s:table.field>
	<s:table.field name="discharge_datetime" datatype="string" label="出院时间" width="120"></s:table.field>
	<s:table.field name="out_diagnosis" datatype="script" label="出院诊断" width="200">
		var zd = record.out_diagnosis;
		return '<div style="width: 200px;word-wrap: break-word;word-break: break-all;overflow: hidden;">'+zd+'('+record.sys_discharge_disposition+')'+'</div>';
	</s:table.field>
	<s:table.field name="oper_message" datatype="string" label="手术(切口等级)" width="150"></s:table.field>
	<s:table.field name="oper_before_drug" datatype="string" label="术前给药时间" width="80"></s:table.field>
	<s:table.field name="kjy_kind_num" datatype="string" label="抗菌药药品种数" width="80"></s:table.field>
	<s:table.field name="yf_use_drug_day" datatype="string" label="预防用药天数" width="80"></s:table.field>
	<s:table.field name="cf" datatype="script" label="处方（医嘱）信息" width="1100">
		var html = [];
		if(record.cf){
		var cf = record.cf.split(/\n/g);
		html.push('<table class="mytalbe" border="1" style="border-top: 1px solid;border-color: black !important" cellspacing="0" cellpadding="0" width="100%">');
		for(i in cf){
		var cc = cf[i].split('~');
        html.push('<tr style="border-bottom: 1px solid black;"><td width="250px">'+cc[0]+'</td><td width="100px">'+cc[1]+'</td><td width="100px">'+cc[2]+'</td><td width="100px">'+cc[3]+'</td><td width="100px">'+cc[4]+'</td><td width="120px">'+cc[5]+'</td><td width="120px">'+cc[6]+'</td><td width="50px">'+cc[7]+'</td><td width="80px">'+cc[8]+'</td></tr>');
		}
    	html.push('</tbody></table>');
		}
		return html.join('');
	</s:table.field>
</s:table.fields>
</s:table>

</div>
</s:page>
<script src="${pageContext.request.contextPath}/res/hospital_common/js/table_print_excel.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	var date = new Date();
	var d = date.getFullYear() + '-' + (((date.getMonth()+1)<10)?'0'+(date.getMonth()+1):(date.getMonth()+1)) + '-' +((date.getDate())<10?'0'+(date.getDate()):(date.getDate()));
	$('[name=start_time]').val(d);
	$('[name=end_time]').val(d);
	//$('#bottom').css('height',window.innerHeight - 110);
	//$('.dataTables_scrollBody').css('height',window.innerHeight - 310);
	//$('.dataTables_scrollBody').css('width','100%');
	//$('.dataTables_scrollBody').css('overflow','auto');

	  $('.dataTables_scrollBody').css('min-height',"100px");
	 $(window).resize(function(){
		    $('.dataTables_scrollBody').css('height',$(window).height() -280-$("div[ctype='form']").parent().parent().height());
		  }).resize();

	 $("i.showhide").on("click", function(){
    	 $('.dataTables_scrollBody').css('height',$(window).height() -
					280 - $("div[ctype='form']").parent().parent().height());
		})
})

function query(table){
	var $this= $("#"+table);
	//var data = {};
	//data.sample_id = '${param.sample_id}'
	var data = $("#form").getData();
	data.async = true;
	data.timeout= -1;
	data.oper_name = '';
	$this.params(data);
	$this.refresh();
}

$('[name=oper_name]').dblclick(function(){
	var code = $('[name=oper_code]').val();
	$.modal("/w/hospital_common/abase/oper.html", "添加手术名称", {
		height: "550px",
		width: "50%",
		code: code,
		maxmin: false,
		callback : function(rtn) {
			if(rtn){
				var name = rtn.name.toString();
				var code = rtn.code.toString();
				$('[name=oper_name]').val(name);
				$('[name=oper_code]').val(code);
			}
	    }
	});
});

$('[name=dept_name]').dblclick(function(){
	var code = $('[name=dept_code]').val();
	$.modal("/w/hospital_common/abase/department.html", "添加科室", {
		height: "550px",
		width: "40%",
		code: code,
		is_permission: 1,
		maxmin: false,
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
	var code = $('[name=doctor_code]').val();
	$.modal("/w/hospital_common/abase/doctor.html", "添加医生", {
		height: "550px",
		width: "50%",
		code: code,
		is_permission: 1,
		maxmin: false,
		callback : function(rtn) {
			if(rtn){
				var name = rtn.name.toString();
				var code = rtn.code.toString();
				$('[name=doctor_name]').val(name);
				$('[name=doctor_code]').val(code);
			}
	    }
	});
});

$('[name=diagnosis_name]').dblclick(function(){
	var code = $('[name=diagnosis_name]').val();
	$.modal("/w/hospital_common/abase/diagnosis.html", "添加诊断", {
		height: "550px",
		width: "50%",
		code: code,
		maxmin: false,
		callback : function(rtn) {
			if(rtn){
				var name = rtn.name.toString();
				//var code = rtn.code.toString();
				$('[name=diagnosis_name]').val(name);
				//$('[name=diagnosisfifter]').val(code);
			}
	    }
	});
});

function clean_dept_name(){
	$('[name=dept_code]').val('');
	$('[name=dept_name]').val('');
}

function clean_doctor_name(){
	$('[name=doctor_code]').val('');
	$('[name=doctor_name]').val('');
}

function clean_oper_name(){
	$('[name=oper_code]').val('');
	$('[name=oper_name]').val('');
}

function clean_diagnosis_name(){
	//$('[name=diagnosisfifter]').val('');
	$('[name=diagnosis_name]').val('');
}


function print_table(table){
	//$("#datatable").print_table();
	var data = $("#form").getData();
	$("#"+table).params_table(data);
	$("#"+table).print_new('');
}

function tableToExcel(tableID, fileName) {
	var data = $("#form").getData();
	$("#" + tableID).params_table(data);
	$("#" + tableID).excel_new(null,{limit : $("#" + tableID).dataTable().fnSettings()._iDisplayLength,
		start : $("#" + tableID).dataTable().fnSettings()._iDisplayStart + 1});
}

function query_orders(patient_id,visit_id){
	$.modal('/w/hospital_common/report/queryhis/his_in_orders_list.html?patient_id=' + patient_id + 
			'&&visit_id=' + visit_id, "查看医嘱", {
		width : "100%",
		height : "100%",
		callback : function(e) {
		}
	});
}
</script>
