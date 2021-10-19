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
<s:table id="datatable2" limit="-1" label="门(急)诊处方抗菌药物使用情况(按科室)" action="hospital_common.report.kjy.querykjymj_dept" autoload="false" fitwidth="false">
<s:toolbar>
	<s:button label="导出" icon="fa fa-random" onclick="tableToExcel('datatable2', 'newexcel')"></s:button>
	<s:button label="打印" icon="" onclick="print_table('datatable2');"></s:button>
	<s:button label="查询" icon="" onclick="query('datatable2');"></s:button>
</s:toolbar>
<s:table.fields>
	<s:table.field name="dept_name" datatype="string" label="科室" width="120"></s:table.field>
	<s:table.field name="all_drug_pz_num" datatype="string" label="处方累计用药总品种数(种)" ></s:table.field>
	<s:table.field name="avg_all_drug_pz_num" datatype="script" label="平均用药品种数(种)" >
		return (+record.all_drug_pz_num/+record.patient_num).toFixed(2);
	</s:table.field>
	<s:table.field name="all_drug_kjy_pz_num" datatype="string" label="处方累计抗菌药总品种数(种)" ></s:table.field>
	<s:table.field name="bfb_all_drug_kjy_pz_num" datatype="script" label="累计抗菌药总品种数/累计用药总品种数" >
		return (+record.all_drug_kjy_pz_num/+record.all_drug_pz_num).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_yizhu_num" datatype="string" label="抗菌药物处方数" ></s:table.field>
	<s:table.field name="patient_num" datatype="string" label="处方总数" ></s:table.field>
	<s:table.field name="bfb_kjy_yizhu_num" datatype="script" label="处方抗菌药物使用率%" >
		return (+record.kjy_yizhu_num/+record.patient_num*100).toFixed(2);
	</s:table.field>
	<s:table.field name="all_drug_money" datatype="string" label="药品总金额(元)" ></s:table.field>
	<s:table.field name="avg_all_drug_money" datatype="script" label="药品平均金额(元)" >
		return (+record.all_drug_money/+record.patient_num).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_all_drug_money" datatype="string" label="抗菌药总金额(元)" ></s:table.field>
	<s:table.field name="bfb_kjy_all_drug_money" datatype="script" label="抗菌药总额占药品总额比率%" >
		return ((record.kjy_all_drug_money+0.00)/(record.all_drug_money+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="patient_use_kjy_money" datatype="string" label="使用抗菌药物医嘱总金额(元)" ></s:table.field>
	<s:table.field name="avg_patient_use_kjy_money" datatype="script" label="抗菌药医嘱平均金额(元)" >
		return (+record.patient_use_kjy_money/+record.patient_num).toFixed(2);
	</s:table.field>
	<s:table.field name="one_kjy_num" datatype="string" label="单用抗菌药物处方数" ></s:table.field>
	<s:table.field name="bfb_one_kjy_num" datatype="script" label="单用抗菌药物处方使用率%" >
		return ((record.one_kjy_num+0.00)/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="two_drug_union_num" datatype="string" label="二联使用抗菌药处方数" ></s:table.field>
	<s:table.field name="bfb_two_drug_union_num" datatype="script" label="二联使用抗菌药处方使用率%" >
		return ((record.two_drug_union_num+0.00)/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="three_drug_union_num" datatype="string" label="三联使用抗菌药处方数" ></s:table.field>
	<s:table.field name="bfb_three_drug_union_num" datatype="script" label="三联使用抗菌药处方使用率%" >
		return ((record.three_drug_union_num+0.00)/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="other_drug_union_num" datatype="string" label="四联及以上使用抗菌药医嘱数" ></s:table.field>
	<s:table.field name="bfb_other_drug_union_num" datatype="script" label="四联及以上使用抗菌药医嘱使用率%" >
		return ((record.other_drug_union_num+0.00)/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="zhiliao_kjy_num" datatype="string" label="治疗使用抗菌药物处方数" ></s:table.field>
	<s:table.field name="bfb_zhiliao_kjy_num" datatype="script" label="治疗使用抗菌药物构成比%" >
		return ((record.zhiliao_kjy_num+0.00)/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="yufang_kjy_num" datatype="string" label="预防使用抗菌药物处方数" ></s:table.field>
	<s:table.field name="bfb_yufang_kjy_num" datatype="script" label="预防使用抗菌药物构成比%" >
		return ((record.yufang_kjy_num+0.00)/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="yufang_kjy_money" datatype="string" label="治疗使用抗菌药物总金额(元)" ></s:table.field>
	<s:table.field name="bfb_yufang_kjy_money" datatype="script" label="治疗使用抗菌药物总额占抗菌药总额%" >
		return ((record.yufang_kjy_money+0.00)/(record.kjy_all_drug_money+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="zhiliao_kjy_money" datatype="string" label="预防使用抗菌药物总金额(元)" ></s:table.field>
	<s:table.field name="bfb_zhiliao_kjy_money" datatype="script" label="预防使用抗菌药物总额占抗菌药总额%" >
		return ((record.zhiliao_kjy_money+0.00)/(record.kjy_all_drug_money+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="avg_all_drug_kjy_pz_num" datatype="script" label="处方平均使用抗菌药品种数(种)" >
		return ((record.all_drug_kjy_pz_num+0.00)/(record.patient_num+0.00)).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_ts_yizhu_num" datatype="string" label="使用特殊抗菌药处方数" ></s:table.field>
	<s:table.field name="bfb_kjy_ts_yizhu_num" datatype="script" label="特殊抗菌药使用率%" >
		return ((record.kjy_ts_yizhu_num+0.00)/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_xz_yizhu_num" datatype="string" label="使用限制抗菌药处方数" ></s:table.field>
	<s:table.field name="bfb_kjy_xz_yizhu_num" datatype="script" label="限制抗菌药使用率%" >
		return ((record.kjy_xz_yizhu_num+0.00)/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_fxz_yizhu_num" datatype="string" label="使用非限制抗菌药处方数" ></s:table.field>
	<s:table.field name="bfb_kjy_fxz_yizhu_num" datatype="script" label="非限制抗菌药使用率%" >
		return ((record.kjy_fxz_yizhu_num+0.00)/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_jmzs_yizhu_num" datatype="string" label="抗菌药静脉输液处方数" ></s:table.field>
	<s:table.field name="bfb_kjy_jmzs_yizhu_num" datatype="script" label="抗菌药静脉输液医嘱比率%" >
		return ((record.kjy_jmzs_yizhu_num+0.00)/(record.patient_num+0.00)*100).toFixed(2);
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
	$this.params(data);
	$this.refresh();
}

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
	$("#" + tableID).excel_new();
}
</script>
