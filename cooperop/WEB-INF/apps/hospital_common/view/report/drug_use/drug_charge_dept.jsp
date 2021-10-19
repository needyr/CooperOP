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

.cb font{
	vertical-align: middle;
	font-size: 12px;
    color: #444;
    font-weight: 700;
    margin-left: 5px;
}

.cb input{
	vertical-align: middle;
    margin-left: 5px !important;
    margin-top: 0px !important;
}
</style>
<s:page title="科室病人药品费用构成统计表">
<div class="top">
<s:form id="form" collapsed="false" extendable="true">
	<s:row>
	<s:datefield label="时间" name="start_time" required="true" format="yyyy-MM-dd" autocomplete="off" value="" cols="1" ></s:datefield>
	<s:datefield label="至" name="end_time" required="true" format="yyyy-MM-dd" autocomplete="off" value="" cols="1" ></s:datefield>
	</s:row>
	
	<s:row>
	<div class="cols4">
	<label class="control-label">类型：</label>
	<div class="control-content">
	<label class="cb"><font>住(出)院</font><input type="checkbox" name="d_type" value="1" data-font="住(出)院"/></label><br>
	<label class="cb"><font>门诊</font><input type="checkbox" name="d_type" value="2" data-font="门诊"/>(
		<label><input type="radio" name="d_type2" value="" data-font="所有病人" /><font>所有病人</font></label>
		<label><input type="radio" name="d_type2" value="1" data-font="药品费用大于零的门诊病人" /><font>药品费用大于零的门诊病人</font></label>
		<label><input type="radio" name="d_type2" value="2" data-font="药品费用大于零且有处方(不包括中药饮片)的门诊病人" /><font>药品费用大于零且有处方(不包括中药饮片)的门诊病人</font></label>
	)</label><br>
	<label class="cb"><font>急诊</font><input type="checkbox" name="d_type" value="3" data-font="急诊"/>(
		<label><input type="radio" name="d_type3" value="" data-font="所有病人" /><font>所有病人</font></label>
		<label><input type="radio" name="d_type3" value="1" data-font="药品费用大于零的门诊病人" /><font>药品费用大于零的急诊病人</font></label>
		<label><input type="radio" name="d_type3" value="2" data-font="药品费用大于零且有处方(不包括中药饮片)的门诊病人" /><font>药品费用大于零且有处方(不包括中药饮片)的急诊病人</font></label>
	)</label>
	</label>
	</div>
	</div>
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
	<s:row>
	<input type="hidden" name="doctor_code" >
	<div class="cols2">
	<label class="control-label">开嘱医生</label>
	<div class="control-content">
	<textarea style="height: 40px;" readonly="readonly" class="form-control" placeholder="双击选择医生" name="doctor_name" cols="4"  data-autosize-on="true"></textarea>
	</div>
	</div>
	<a onclick="clean_doctor_name()">清空</a>
	</s:row>
	</s:row>
	
</s:form>
</div>
<div class="bottom">	
<s:table id="datatable1" limit="-1" sort="true" active="true" label="科室病人药品费用构成统计表" action="hospital_common.report.drug_use.query_dept" autoload="false" fitwidth="false">
<s:toolbar>
	<s:button label="导出" icon="fa fa-random" onclick="tableToExcel('datatable1', 'newexcel')"></s:button>
	<s:button label="打印" icon="" onclick="print_table('datatable1');"></s:button>
	<s:button label="查询" icon="" onclick="query('datatable1');"></s:button>
</s:toolbar>
<s:table.fields>
	<s:table.field name="dept_name" label="科室" datatype="script" >
		return record.dept_name?record.dept_name:'未知科室';
	</s:table.field>
	<s:table.field name="total" label="总费用(元)" datatype="decimal" format="##0.00" sort="true"></s:table.field>
	<s:table.field name="total_drug" label="药品收入(元)" datatype="decimal" format="##0.00" sort="true"></s:table.field>
	<s:table.field name="total_drug_total" label="药品收入所占比例(%)" datatype="script">
		return (Number(record.total_drug)/Number(record.total)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="xi_drug" label="西药收入(元)" datatype="decimal" format="##0.00" sort="true"></s:table.field>
	<s:table.field name="xi_drug_total_drug" label="西药收入占药品收入比例(%)" datatype="script">
		return (Number(record.xi_drug)/Number(record.total_drug)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="zongc_drug" label="中成药(元)" datatype="decimal" format="##0.00" sort="true"></s:table.field>
	<s:table.field name="zongc_drug_total_drug" label="中成药收入占药品收入比例(%)" datatype="script">
		return (Number(record.zongc_drug)/Number(record.total_drug)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="zongy_drug" label="中药饮片(元)" datatype="decimal" format="##0.00" sort="true"></s:table.field>
	<s:table.field name="zongy_drug_total_drug" label="中药饮片收入占药品收入比例(%)" datatype="script">
		return (Number(record.zongy_drug)/Number(record.total_drug)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="swzj_drug" label="生物制剂收入(元)" datatype="decimal" format="##0.00" sort="true"></s:table.field>
	<s:table.field name="swzj_drug_total_drug" label="生物制剂收入占药品收入比例(%)	" datatype="script">
		return (Number(record.swzj_drug)/Number(record.total_drug)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="kj_drug" label="抗菌药物收入(元)" datatype="decimal" format="##0.00" sort="true"></s:table.field>
	<s:table.field name="kj_drug_total_drug" label="抗菌药物收入占药品收入比例(%)" datatype="script">
		return (Number(record.kj_drug)/Number(record.total_drug)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="patient_count" label="总病人数" datatype="String"></s:table.field>
	<s:table.field name="avg_total" label="人均费用(元)" datatype="script">
		return (Number(record.total)/Number(record.patient_count)).toFixed(2);
	</s:table.field>
	<s:table.field name="avg_total_drug" label="人均药品费用(元)" datatype="script">
		return (Number(record.total_drug)/Number(record.patient_count)).toFixed(2);
	</s:table.field>
	<s:table.field name="avg_xi_drug" label="人均使用西药费用(元)" datatype="script">
		return (Number(record.xi_drug)/Number(record.patient_count)).toFixed(2);
	</s:table.field>
	<s:table.field name="avg_zongc_drug" label="人均使用中成药费用(元)" datatype="script">
		return (Number(record.zongc_drug)/Number(record.patient_count)).toFixed(2);
	</s:table.field>
	<s:table.field name="avg_zongy_drug" label="人均使用中药饮片费用(元)" datatype="script">
		return (Number(record.zongy_drug)/Number(record.patient_count)).toFixed(2);
	</s:table.field>
	<s:table.field name="avg_swzj_drug" label="人均使用生物制剂费用(元)" datatype="script">
		return (Number(record.zongy_drug)/Number(record.patient_count)).toFixed(2);
	</s:table.field>
</s:table.fields>
</s:table>

</div>
</s:page>
<script src="${pageContext.request.contextPath}/res/hospital_common/js/table_print_excel.js" type="text/javascript"></script>
<script type="text/javascript">
var cxtj;
$(function(){
	var date = new Date();
	var d = date.getFullYear() + '-' + (((date.getMonth()+1)<10)?'0'+(date.getMonth()+1):(date.getMonth()+1)) + '-' +((date.getDate())<10?'0'+(date.getDate()):(date.getDate()));
	$('[name=start_time]').val(d);
	$('[name=end_time]').val(d);
	//$('#bottom').css('height',window.innerHeight - 110);
	//$('.dataTables_scrollBody').css('height',window.innerHeight - 350);
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
	
	var chk_value =[]; 
    $('input[name="d_type"]:checked').each(function(){ 
        chk_value.push($(this).val()); 
    });
    data.d_type = chk_value.join(',')
    
	cxtj = data;
	data.async = true;
	data.timeout= -1;
	data.oper_name = '';
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

function clean_dept_name(){
	$('[name=dept_code]').val('');
	$('[name=dept_name]').val('');
}

function clean_doctor_name(){
	$('[name=doctor_code]').val('');
	$('[name=doctor_name]').val('');
}

function print_table(tableID){
	var data = $("#form").getData();
	var chk_value =[]; 
	var type = "";
    $('input[name="d_type"]:checked').each(function(){ 
        chk_value.push($(this).val());
        var msg = $('input[name="d_type'+$(this).val()+'"]').attr("data-font");
        type = type + $(this).attr("data-font") + ( msg?'('+msg+')': '') + '、';
    });
    data.d_type = chk_value.join(',')
    cxtj = cxtj?cxtj:data;
	$("#" + tableID).params_table(cxtj);
	var foot = '1.统计范围<br>出院时间：'+cxtj.start_time+'~'+cxtj.end_time;
	foot = foot + '<br>类型：' + (type?type:'全部');
	$("#"+tableID).print_new(foot);
}

function tableToExcel(tableID, fileName) {
	var data = $("#form").getData();
	var chk_value =[]; 
	var type = "";
    $('input[name="d_type"]:checked').each(function(){ 
        chk_value.push($(this).val());
        var msg = $('input[name="d_type'+$(this).val()+'"]').attr("data-font");
        type = type + $(this).attr("data-font") + ( msg?'('+msg+')': '') + '、';
    });
    data.d_type = chk_value.join(',')
    cxtj = cxtj?cxtj:data;
	$("#" + tableID).params_table(cxtj);
	var foot = '1.统计范围<br>出院时间：'+cxtj.start_time+'~'+cxtj.end_time;
	foot = foot + '<br>类型：' + (type?type:'全部');
	$("#" + tableID).excel_new(foot);
}
</script>