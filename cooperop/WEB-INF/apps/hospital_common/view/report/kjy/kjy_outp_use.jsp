<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style>
.top{
	height: 100px;
	border-bottom: 2px #c7c7c7 solid;
	overflow: auto;
	width: calc(100% - 40px);
    margin: 0px 0px 0px 20px;
}
.bottom{
	padding: 20px
}
</style>
<s:page title="">
<div class="top">
<s:form id="form">
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
<s:tabpanel>
<s:table id="datatable1" active="true" label="使用情况调查表" action="hospital_common.report.kjy.querykjymj" autoload="false" fitwidth="false">
<s:toolbar>
	<s:button label="导出" icon="fa fa-random" onclick="tableToExcel('datatable1', 'newexcel')"></s:button>
	<s:button label="打印" icon="" onclick="print_table('datatable1');"></s:button>
	<s:button label="使用情况调查表" icon="" onclick="query('datatable1');"></s:button>
</s:toolbar>
<s:table.fields>
	<s:table.field name="patient_no" datatype="string" label="住院号" width="80"></s:table.field>
	<s:table.field name="patient_id" datatype="string" label="患者ID" width="80"></s:table.field>
	<s:table.field name="patient_name" datatype="string" label="患者姓名" width="80"></s:table.field>
	<s:table.field name="age" datatype="string" label="年龄" width="80"></s:table.field>
	<s:table.field name="sex" datatype="string" label="性别" width="80"></s:table.field>
	<s:table.field name="admission_datetime" datatype="string" label="入院时间" width="120"></s:table.field>
	<s:table.field name="discharge_datetime" datatype="string" label="出院时间" width="120"></s:table.field>
	<s:table.field name="ts" datatype="string" label="住院天数" width="120"></s:table.field>
	<s:table.field name="attending_doctor" datatype="string" label="主管医生" width="100"></s:table.field>
	<s:table.field name="dept_name" datatype="string" label="科室" width="120"></s:table.field>
	<s:table.field name="out_diagnosis" datatype="script" label="出院诊断" width="200">
		var zd = record.out_diagnosis;
		return '<div style="width: 200px;word-wrap: break-word;word-break: break-all;overflow: hidden;">'+zd+'</div>';
	</s:table.field>
	<s:table.field name="oper_message" datatype="string" label="手术(切口等级)" width="150"></s:table.field>
	<s:table.field name="drug_kind_num" datatype="string" label="药品品种数" width="80"></s:table.field>
	<s:table.field name="kjy_kind_num" datatype="string" label="抗菌药药品种数" width="80"></s:table.field>
	<s:table.field name="all_money" datatype="string" label="治疗金额(元)" width="80"></s:table.field>
	<s:table.field name="kjy_money" datatype="string" label="抗菌药金额(元)" width="80"></s:table.field>
	<s:table.field name="is_lhyy" datatype="script" label="联合用药" width="80">
		if(record.one_drug_union_num > 0){
			return '是';
		}
		if(record.two_drug_union_num > 0){
			return '是';
		}
		if(record.three_drug_union_num > 0){
			return '是';
		}
		if(record.other_drug_union_num > 0){
			return '是';
		}
		return '否';
	</s:table.field>
	<s:table.field name="cf" datatype="script" label="处方（医嘱）信息" width="1100">
		var html = [];
		if(record.cf){
		var cf = record.cf.split(/\n/g);
		html.push('<table class="mytalbe" border="1" style="border-top: 1px solid;border-color: black !important" cellspacing="0" cellpadding="0" width="100%">');
		for(i in cf){
		var cc = cf[i].split('~');
        html.push('<tr style="border-bottom: 1px solid black;"><td width="250px">'+cc[0]+'</td><td width="100px">'+cc[1]+'</td><td width="100px">'+cc[2]+'</td><td width="100px">'+cc[3]+'</td><td width="100px">'+cc[4]+'</td><td width="120px">'+cc[5]+'</td><td width="120px">'+cc[6]+'</td><td width="100px">'+cc[7]+'</td><td width="80px">'+cc[8]+'</td></tr>');
		}
    	html.push('</tbody></table>');
		}
		return html.join('');
	</s:table.field>
</s:table.fields>
</s:table>

<s:table id="datatable2" limit="-1" label="(按科室)" action="hospital_common.report.kjy.querykjymj_dept" autoload="false" fitwidth="false">
<s:toolbar>
	<s:button label="导出" icon="fa fa-random" onclick="tableToExcel('datatable2', 'newexcel')"></s:button>
	<s:button label="打印" icon="" onclick="print_table('datatable2');"></s:button>
	<s:button label="使用情况调查表" icon="" onclick="query('datatable2');"></s:button>
</s:toolbar>
<s:table.fields>
	<s:table.field name="dept_name" datatype="string" label="科室" width="120"></s:table.field>
	<s:table.field name="all_drug_pz_num" datatype="string" label="医嘱累计用药总品种数(种)" ></s:table.field>
	<s:table.field name="avg_all_drug_pz_num" datatype="script" label="平均用药品种数(种)" >
		return (record.all_drug_pz_num/(record.patient_num+0.00)).toFixed(2);
	</s:table.field>
	<s:table.field name="all_drug_kjy_pz_num" datatype="string" label="医嘱累计抗菌药总品种数(种)" ></s:table.field>
	<s:table.field name="bfb_all_drug_kjy_pz_num" datatype="script" label="累计抗菌药总品种数/累计用药总品种数" >
		return (record.all_drug_kjy_pz_num/(record.all_drug_pz_num+0.00)).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_yizhu_num" datatype="string" label="抗菌药物医嘱数" ></s:table.field>
	<s:table.field name="patient_num" datatype="string" label="医嘱总数" ></s:table.field>
	<s:table.field name="bfb_kjy_yizhu_num" datatype="script" label="医嘱抗菌药物使用率%" >
		return (record.kjy_yizhu_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="all_drug_money" datatype="string" label="药品总金额(元)" ></s:table.field>
	<s:table.field name="avg_all_drug_money" datatype="script" label="药品平均金额(元)" >
		return (record.all_drug_money/(record.patient_num+0.00)).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_all_drug_money" datatype="string" label="抗菌药总金额(元)" ></s:table.field>
	<s:table.field name="bfb_kjy_all_drug_money" datatype="script" label="抗菌药总额占药品总额比率%" >
		return (record.kjy_all_drug_money/(record.all_drug_money+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="patient_use_kjy_money" datatype="string" label="使用抗菌药物医嘱总金额(元)" ></s:table.field>
	<s:table.field name="avg_patient_use_kjy_money" datatype="script" label="抗菌药医嘱平均金额(元)" >
		return (record.patient_use_kjy_money/(record.patient_num+0.00)).toFixed(2);
	</s:table.field>
	<s:table.field name="one_kjy_num" datatype="string" label="单用抗菌药物医嘱数" ></s:table.field>
	<s:table.field name="bfb_one_kjy_num" datatype="script" label="单用抗菌药物医嘱使用率%" >
		return (record.one_kjy_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="two_drug_union_num" datatype="string" label="二联使用抗菌药医嘱数" ></s:table.field>
	<s:table.field name="bfb_two_drug_union_num" datatype="script" label="二联使用抗菌药医嘱使用率%" >
		return (record.two_drug_union_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="three_drug_union_num" datatype="string" label="三联使用抗菌药医嘱数" ></s:table.field>
	<s:table.field name="bfb_three_drug_union_num" datatype="script" label="三联使用抗菌药医嘱使用率%" >
		return (record.three_drug_union_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="other_drug_union_num" datatype="string" label="四联及以上使用抗菌药医嘱数" ></s:table.field>
	<s:table.field name="bfb_other_drug_union_num" datatype="script" label="四联及以上使用抗菌药医嘱使用率%" >
		return (record.other_drug_union_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="zhiliao_kjy_num" datatype="string" label="治疗使用抗菌药物医嘱数" ></s:table.field>
	<s:table.field name="bfb_zhiliao_kjy_num" datatype="script" label="治疗使用抗菌药物构成比%" >
		return (record.zhiliao_kjy_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="yufang_kjy_num" datatype="string" label="预防使用抗菌药物医嘱数" ></s:table.field>
	<s:table.field name="bfb_yufang_kjy_num" datatype="script" label="预防使用抗菌药物构成比%" >
		return (record.yufang_kjy_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="yufang_kjy_money" datatype="string" label="治疗使用抗菌药物总金额(元)" ></s:table.field>
	<s:table.field name="bfb_yufang_kjy_money" datatype="script" label="治疗使用抗菌药物总额占抗菌药总额%" >
		return (record.yufang_kjy_money/(record.kjy_all_drug_money+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="zhiliao_kjy_money" datatype="string" label="预防使用抗菌药物总金额(元)" ></s:table.field>
	<s:table.field name="bfb_zhiliao_kjy_money" datatype="script" label="预防使用抗菌药物总额占抗菌药总额%" >
		return (record.zhiliao_kjy_money/(record.kjy_all_drug_money+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="avg_all_drug_kjy_pz_num" datatype="script" label="医嘱平均使用抗菌药品种数(种)" >
		return (record.all_drug_kjy_pz_num/(record.patient_num+0.00)).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_ts_yizhu_num" datatype="string" label="使用特殊抗菌药医嘱数" ></s:table.field>
	<s:table.field name="bfb_kjy_ts_yizhu_num" datatype="script" label="特殊抗菌药使用率%" >
		return (record.kjy_ts_yizhu_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_xz_yizhu_num" datatype="string" label="使用限制抗菌药医嘱数" ></s:table.field>
	<s:table.field name="bfb_kjy_xz_yizhu_num" datatype="script" label="限制抗菌药使用率%" >
		return (record.kjy_xz_yizhu_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_fxz_yizhu_num" datatype="string" label="使用非限制抗菌药医嘱数" ></s:table.field>
	<s:table.field name="bfb_kjy_fxz_yizhu_num" datatype="script" label="非限制抗菌药使用率%" >
		return (record.kjy_fxz_yizhu_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_jmzs_yizhu_num" datatype="string" label="抗菌药静脉输液医嘱数" ></s:table.field>
	<s:table.field name="bfb_kjy_jmzs_yizhu_num" datatype="script" label="抗菌药静脉输液医嘱比率%" >
		return (record.kjy_jmzs_yizhu_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
</s:table.fields>
</s:table>

<s:table id="datatable3" limit="-1" label="(按医生)" action="hospital_common.report.kjy.querykjyomj_doctor" autoload="false" >
<s:toolbar>
	<s:button label="导出" icon="fa fa-random" onclick="tableToExcel('datatable3', 'newexcel')"></s:button>
	<s:button label="打印" icon="" onclick="print_table('datatable3');"></s:button>
	<s:button label="使用情况调查表" icon="" onclick="query('datatable3');"></s:button>
</s:toolbar>
<s:table.fields>
	<s:table.field name="doctor_name" datatype="string" label="医生名称" width="120"></s:table.field>
	<s:table.field name="doctor_dept_name" datatype="string" label="医生科室" width="120"></s:table.field>
	<s:table.field name="all_drug_pz_num" datatype="string" label="医嘱累计用药总品种数(种)" ></s:table.field>
	<s:table.field name="avg_all_drug_pz_num" datatype="script" label="平均用药品种数(种)" >
		return (record.all_drug_pz_num/(record.patient_num+0.00)).toFixed(2);
	</s:table.field>
	<s:table.field name="all_drug_kjy_pz_num" datatype="string" label="医嘱累计抗菌药总品种数(种)" ></s:table.field>
	<s:table.field name="bfb_all_drug_kjy_pz_num" datatype="script" label="累计抗菌药总品种数/累计用药总品种数" >
		return (record.all_drug_kjy_pz_num/(record.all_drug_pz_num+0.00)).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_yizhu_num" datatype="string" label="抗菌药物医嘱数" ></s:table.field>
	<s:table.field name="patient_num" datatype="string" label="医嘱总数" ></s:table.field>
	<s:table.field name="bfb_kjy_yizhu_num" datatype="script" label="医嘱抗菌药物使用率%" >
		return (record.kjy_yizhu_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="all_drug_money" datatype="string" label="药品总金额(元)" ></s:table.field>
	<s:table.field name="avg_all_drug_money" datatype="script" label="药品平均金额(元)" >
		return (record.all_drug_money/(record.patient_num+0.00)).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_all_drug_money" datatype="string" label="抗菌药总金额(元)" ></s:table.field>
	<s:table.field name="bfb_kjy_all_drug_money" datatype="script" label="抗菌药总额占药品总额比率%" >
		return (record.kjy_all_drug_money/(record.all_drug_money+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="patient_use_kjy_money" datatype="string" label="使用抗菌药物医嘱总金额(元)" ></s:table.field>
	<s:table.field name="avg_patient_use_kjy_money" datatype="script" label="抗菌药医嘱平均金额(元)" >
		return (record.patient_use_kjy_money/(record.patient_num+0.00)).toFixed(2);
	</s:table.field>
	<s:table.field name="one_kjy_num" datatype="string" label="单用抗菌药物医嘱数" ></s:table.field>
	<s:table.field name="bfb_one_kjy_num" datatype="script" label="单用抗菌药物医嘱使用率%" >
		return (record.one_kjy_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="two_drug_union_num" datatype="string" label="二联使用抗菌药医嘱数" ></s:table.field>
	<s:table.field name="bfb_two_drug_union_num" datatype="script" label="二联使用抗菌药医嘱使用率%" >
		return (record.two_drug_union_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="three_drug_union_num" datatype="string" label="三联使用抗菌药医嘱数" ></s:table.field>
	<s:table.field name="bfb_three_drug_union_num" datatype="script" label="三联使用抗菌药医嘱使用率%" >
		return (record.three_drug_union_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="other_drug_union_num" datatype="string" label="四联及以上使用抗菌药医嘱数" ></s:table.field>
	<s:table.field name="bfb_other_drug_union_num" datatype="script" label="四联及以上使用抗菌药医嘱使用率%" >
		return (record.other_drug_union_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="zhiliao_kjy_num" datatype="string" label="治疗使用抗菌药物医嘱数" ></s:table.field>
	<s:table.field name="bfb_zhiliao_kjy_num" datatype="script" label="治疗使用抗菌药物构成比%" >
		return (record.zhiliao_kjy_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="yufang_kjy_num" datatype="string" label="预防使用抗菌药物医嘱数" ></s:table.field>
	<s:table.field name="bfb_yufang_kjy_num" datatype="script" label="预防使用抗菌药物构成比%" >
		return (record.yufang_kjy_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="yufang_kjy_money" datatype="string" label="治疗使用抗菌药物总金额(元)" ></s:table.field>
	<s:table.field name="bfb_yufang_kjy_money" datatype="script" label="治疗使用抗菌药物总额占抗菌药总额%" >
		return (record.yufang_kjy_money/(record.kjy_all_drug_money+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="zhiliao_kjy_money" datatype="string" label="预防使用抗菌药物总金额(元)" ></s:table.field>
	<s:table.field name="bfb_zhiliao_kjy_money" datatype="script" label="预防使用抗菌药物总额占抗菌药总额%" >
		return (record.zhiliao_kjy_money/(record.kjy_all_drug_money+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="avg_all_drug_kjy_pz_num" datatype="script" label="医嘱平均使用抗菌药品种数(种)" >
		return (record.all_drug_kjy_pz_num/(record.patient_num+0.00)).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_ts_yizhu_num" datatype="string" label="使用特殊抗菌药医嘱数" ></s:table.field>
	<s:table.field name="bfb_kjy_ts_yizhu_num" datatype="script" label="特殊抗菌药使用率%" >
		return (record.kjy_ts_yizhu_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_xz_yizhu_num" datatype="string" label="使用限制抗菌药医嘱数" ></s:table.field>
	<s:table.field name="bfb_kjy_xz_yizhu_num" datatype="script" label="限制抗菌药使用率%" >
		return (record.kjy_xz_yizhu_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_fxz_yizhu_num" datatype="string" label="使用非限制抗菌药医嘱数" ></s:table.field>
	<s:table.field name="bfb_kjy_fxz_yizhu_num" datatype="script" label="非限制抗菌药使用率%" >
		return (record.kjy_fxz_yizhu_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
	<s:table.field name="kjy_jmzs_yizhu_num" datatype="string" label="抗菌药静脉输液医嘱数" ></s:table.field>
	<s:table.field name="bfb_kjy_jmzs_yizhu_num" datatype="script" label="抗菌药静脉输液医嘱比率%" >
		return (record.kjy_jmzs_yizhu_num/(record.patient_num+0.00)*100).toFixed(2);
	</s:table.field>
</s:table.fields>
</s:table>

</s:tabpanel>

</div>
</s:page>
<script src="${pageContext.request.contextPath}/res/hospital_common/js/table_print_excel.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$('[name=start_time]').val('${now_time}');
	$('[name=end_time]').val('${now_time}');
	$('#bottom').css('height',window.innerHeight - 110);
	$('.dataTables_scrollBody').css('height',window.innerHeight - 310);
	//$('.dataTables_scrollBody').css('width','100%');
	$('.dataTables_scrollBody').css('overflow','auto');
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