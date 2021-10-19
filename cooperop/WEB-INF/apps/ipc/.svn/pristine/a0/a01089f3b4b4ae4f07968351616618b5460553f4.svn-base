<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="事后点评抽样">
<style>
.tiaojian{
	font-size: 14px;
    width: 100%;
    line-height: 27px;
}
</style>
	<s:row>
	<div class="col-md-3">
		<div style="background-color: gainsboro;">
			<span style="font-size: 14px;">搜索条件</span>
		</div>
		<div style="border: 1px solid gainsboro;overflow: auto;max-height: 400px;">
		<div style="margin-left: 10px;">
			<div class="tiaojian">抽样方式：手动抽样</div>
			<div class="tiaojian">抽样类型：${$return.spcomment_unit eq 1?"按人为单位":$return.spcomment_unit eq 2?"按处方为单位":"按医嘱为单位"}</div>
			<div class="tiaojian">专项类型：${empty $return.special_types?"":$return.special_types}</div>
			<div class="tiaojian">就诊时间：</div>
			<div class="tiaojian">${$return.sample_start_time}~${$return.sample_end_time}</div>
			<div class="tiaojian">抽样药品：${$return.drug_type eq '0'?"全部":$return.drug_type eq '1'?"西药":$return.drug_type eq '2'?"中药":$return.drug_type eq '3'?"草药":"民族药"}</div>
			<div class="tiaojian">抽样目的：常规点评细则</div>
			<div class="tiaojian">数据来源：${$return.d_type eq 1?"住院":$return.d_type eq 2?"门诊":"急诊"}</div>
			<c:if test="${$return.d_type eq 1}">
			<div class="tiaojian">病人住院状态：${$return.patient_state eq 1?"在院医嘱":"出院医嘱"}</div>
			<!-- <div class="tiaojian">医嘱/处方：医嘱</div> -->
			</c:if>
			<c:if test="${$return.d_type ne 1}">
			<div class="tiaojian">医嘱/处方：已交费处方</div>
			</c:if>
			<div class="tiaojian">药品：${empty $return.diagnosis_desc?"无":$return.diagnosis_desc}</div>
			<div class="tiaojian">开嘱科室：${empty $return.dept_name?"无":$return.dept_name}</div>
			<div class="tiaojian">费别：${empty $return.feibie?"无":$return.feibie}</div>
			<div class="tiaojian">医生：${empty $return.doctor_name?"无":$return.doctor_name}</div>
			<div class="tiaojian">药品：${empty $return.drug_name?"无":$return.drug_name}</div>
			<div class="tiaojian">姓名/住院号：${empty $return.user_name?"无":$return.user_name}</div>
		</div>
		</div>
	</div>
	<div class="col-md-9">
		<s:table id="datatable" label="抽样点评患者列表"  icon="fa fa-list" autoload="false"
			sort="true" limit="10" fitwidth="true" action="ipc.sample.queryComment" >
			<s:toolbar>
				<s:button label="返回" icon="fa fa-reply" onclick="fanhui();"></s:button> 
				<s:button label="添加" icon="icon-plus" onclick="addPatient();"></s:button> 
			</s:toolbar>
			<s:table.fields>
				<s:table.field name="del" datatype="script" label="操作">
					var is_active = record.is_active;
					var html = [];
					if(is_active == '1'){
						html.push('<a href="javascript:void(0)" onclick="Delete(');
						html.push('\''+record.patient_id+'\')">');
						html.push('删除</a>');
					}else if(is_active == '0'){
						html.push('<a href="javascript:void(0)" onclick="add(');
						html.push('\''+record.patient_id+'\')">');
						html.push('添加</a>');
					}
					return html.join('');
				</s:table.field>
				<s:table.field name="patient_id" datatype="string" label="患者ID" sort="true"></s:table.field>
				<s:table.field name="visit_id" datatype="string" label="住院次数" sort="true"></s:table.field>
				<s:table.field name="patient_name" datatype="template" label="患者名称">
					<a href="javascript:void(0);" onclick="topatient('$[patient_id]','$[visit_id]');">$[patient_name]</a>
				</s:table.field>
				<s:table.field name="sex" datatype="string" label="性别"></s:table.field>
				<s:table.field name="age" datatype="string" label="年龄"></s:table.field>
				<s:table.field name="charge_type" datatype="string" label="费别"></s:table.field>
				<s:table.field name="d_type" datatype="script" label="患者类型">
					if(record.d_type == '1'){
						return '住院';
					}else if(record.d_type == '3'){
						return '急诊';
					}else if(record.d_type == '2'){
						return '门诊';
					}else{
						return '测试';
					}
				</s:table.field>
				<s:table.field name="p_type" datatype="script" label="医嘱/处方">
					if(record.p_type == '1'){
						return '医嘱';
					}else if(record.p_type != '1'){
						return '处方';
					}
				</s:table.field>
				<s:table.field name="discharge_datetime" datatype="script" label="样本来源">
					if(record.p_type != '1'){
							return '出院';
					}
					return '在院';
				</s:table.field>
				<%-- <s:table.field name="dept_name" datatype="string" label="科室"></s:table.field>
				<s:table.field name="doctor" datatype="string" label="医生"></s:table.field> --%>
			</s:table.fields>
		</s:table>
	</div>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		if('${param.sample_type}' == "1"){
			$("#suijicheck").hide();
		}else{
			$("#suijicheck").show();
		}
		query();
	});
	function query(){
		var data = $("#myform").getData();
		data.sample_id="${param.sample_id}";
		$("#datatable").params(data);
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
	}
	function cancel(){
		$.closeModal(2);
	}
	
	//病人详情
	 function topatient(patient_id,visit_id){
		 layer.open({
			  type: 2,
			  title: "患者详情",
			  //skin: 'layui-layer-rim', //加上边框
			  area: ['874px', '610px'], //宽高
			  content: "/w/hospital_common/additional/patientInfo.html?patient_id="+patient_id+"&&visit_id="+visit_id
		}); 
		 event.stopPropagation();
	 } 
	function Delete(patient_id){
		$.call("ipc.sample.updateSampleDel",{"patient_id":patient_id,"sample_id":'${param.sample_id}'},function(rtn){
			query();
		})
	}
	
	function add(patient_id){
		$.call("ipc.sample.updateSampleAdd",{"patient_id":patient_id,"sample_id":'${param.sample_id}'},function(rtn){
			query();
		})
	}
	
	function addPatient(){
		$.modal("sampledetail.html","添加抽样",{
			width:'100%',
			height:'100%',
			sample_id: '${param.sample_id}',
			callback : function(e){
				query();
			}
		});
	}
	
	function fanhui(){
		$.closeModal();
	}
</script>