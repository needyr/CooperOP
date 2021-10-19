<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style>

</style>
<s:page title="某病种不同医生使用抗菌药物治疗效果成本对比表">
<s:row>
<s:form id="form" collapsed="false" extendable="true">
	<s:row>
	<s:datefield label="出院时间" name="start_time" required="true" format="yyyy-MM-dd" autocomplete="off" value="" cols="1" ></s:datefield>
	<s:datefield label="至" name="end_time" required="true" format="yyyy-MM-dd" autocomplete="off" value="" cols="1" ></s:datefield>
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
	<input type="hidden" name="dept_code" >
	<div class="cols2">
	<label class="control-label">科室</label>
	<div class="control-content">
	<textarea style="height: 40px;" readonly="readonly" class="form-control" placeholder="双击选择科室" name="dept_name" cols="4"  data-autosize-on="true"></textarea>
	</div>
	</div>
	<a onclick="clean_dept_name()">清空</a>
	</s:row>

</s:form>
</s:row>
<s:row>
<s:table id="datatable1" active="true" label="某病种不同医生使用抗菌药物治疗效果成本对比表" action="hospital_common.report.diagnosis_kjy.cost_comparison_query" autoload="false" fitwidth="false">
<s:toolbar>
	<s:button label="导出" icon="fa fa-random" onclick="tableToExcel('datatable1', 'newexcel')"></s:button>
	<s:button label="打印" icon="" onclick="print_table('datatable1');"></s:button>
	<s:button label="查询" icon="" onclick="query();"></s:button>
</s:toolbar>
<s:table.fields>
	<%-- <s:table.field name="patient_no" datatype="string" label="住院号" width="80"></s:table.field> --%>
	<s:table.field name="attending_doctor" datatype="string" label="主管医生" width="80"></s:table.field>
	<s:table.field name="doctor_dept" datatype="string" label="医生所在科室" width="100"></s:table.field>
	<s:table.field name="zl_patient_num" datatype="string" label="治疗人数" width="80"></s:table.field>
	<s:table.field name="kjy_patient_num" datatype="string" label="使用抗菌药物的人数" width="100"></s:table.field>
	<s:table.field name="zy" datatype="script" label="治愈率(%)" width="80">
		return ((record.result_zy_num+0.00)/(record.zl_patient_num+0.00)).toFixed(2);
	</s:table.field>
	<s:table.field name="all_money" datatype="string" label="总金额(元)" width="80"></s:table.field>
	<s:table.field name="drug_money" datatype="string" label="药品总金额(元)" width="100"></s:table.field>
	<s:table.field name="pj_zlje" datatype="script" label="人均治疗金额(元)" width="100">
		return ((record.all_money+0.00)/(record.zl_patient_num+0.00)).toFixed(2);
	</s:table.field>
	<s:table.field name="pj_day_je" datatype="script" label="人均日金额(元)" width="100">
		return ((record.all_money+0.00)/(record.ts.replace('天','')+0.00)).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_money" datatype="string" label="抗菌药物总金额(元)" width="100"></s:table.field>
	<s:table.field name="kjy_kind_num" datatype="string" label="抗菌药物品种数" width="80"></s:table.field>
	<s:table.field name="result_zy_num" datatype="string" label="治愈人次" width="80"></s:table.field>
	<s:table.field name="result_hz_num" datatype="string" label="好转人次" width="80"></s:table.field>
	<s:table.field name="result_wy_num" datatype="string" label="未愈人次" width="80"></s:table.field>
	<s:table.field name="result_sw_num" datatype="string" label="死亡人次" width="80"></s:table.field>
	<s:table.field name="result_qt_num" datatype="string" label="其他人次" width="80"></s:table.field>

</s:table.fields>
</s:table>
</s:row>
</s:page>
<script src="${pageContext.request.contextPath}/res/hospital_common/js/table_print_excel.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	var date = new Date();
	var d = date.getFullYear() + '-' + (((date.getMonth()+1)<10)?'0'+(date.getMonth()+1):(date.getMonth()+1)) + '-' +date.getDate();
	$('[name=start_time]').val(d);
	$('[name=end_time]').val(d);
	$(window).resize(function(){
		$('.dataTables_scrollBody').css('height',$(window).height() -
				270 - $("div[ctype='form']").parent().parent().height());
		$('.dataTables_scrollBody').css('overflow','auto');
	});
	$(window).resize();
	$("i.showhide").on("click", function(){
		$('.dataTables_scrollBody').css('height',$(window).height() -
				270 - $("div[ctype='form']").parent().parent().height());
		$('.dataTables_scrollBody').css('overflow','auto');
	})
})

function query(){
	var diagno = $('[name=diagnosis_name]').val();
	if(diagno){
		var $this= $("#datatable1");
		var data = $("#form").getData();
		data.async = true;
		data.timeout= -1;
		$this.params(data);
		$this.refresh();
	}else{
		layer.alert('诊断不能为空!')
	}
}

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

function clean_diagnosis_name(){
	//$('[name=diagnosisfifter]').val('');
	$('[name=diagnosis_name]').val('');
}

function clean_dept_name(){
	$('[name=dept_code]').val('');
	$('[name=dept_name]').val('');
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
	$("#" + tableID).excel_new();
}
</script>
