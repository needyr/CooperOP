<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<style type="text/css">
#importentbu{
	color: #0eabc3;
    background-color: #ffffff;
    border: 1px solid #0eabc3;
}
#importentbu:hover{
	background: #FFFFFF;
    color: #1d808f;
    border: 1px solid #1d808f;
}

</style>
<s:page title="事后点评抽样">
	<s:row>
	<div class="col-md-3">
	<s:form label="" id="myform">
		<s:row>
		<%-- <s:autocomplete action="ipc.sample.queryDrug" cols="3" label="药品" name="drugFifter" limit="10"> 
			<s:option value="$[drug_code]" label="$[drug_name]">
				<span style="width:100px;display:block;float:left">
					$[drug_code]
				</span>
				<span style="width:100px;display:block;float:left">
					$[drug_name]
				</span>
			</s:option>
		</s:autocomplete> --%>
		<s:datefield label="就诊时间" name="mintime" format="yyyy-MM-dd" placeholder="请选择日期" value="${defalutmixtime}" cols="3"></s:datefield>
		<s:datefield label="至" name="maxtime" format="yyyy-MM-dd" placeholder="请选择日期" value="${defalutmaxtime}" cols="3"></s:datefield>
		<s:select cols="3" label="数据来源" name="datasouce" value="1">
		<s:option label="住院" value="1"></s:option>
		<s:option label="门诊" value="2"></s:option>
		<s:option label="急诊" value="3"></s:option>
		</s:select>
		<s:select cols="3" label="病人住院状态" name="patient_state" value="1">
		<s:option label="在院" value="1"></s:option>
		<s:option label="出院" value="2"></s:option>
		</s:select>
		<s:select cols="3" label="医嘱/处方" name="p_typeFifter" value="1">
		<s:option label="医嘱" value="1"></s:option>
		<s:option label="处方" value="2"></s:option>
		</s:select>
		<%-- <s:autocomplete action="ipc.sample.queryDept" cols="3" label="开嘱科室" name="deptFifter" limit="10"> 
			<s:option value="$[dept_code]" label="$[dept_name]">
				<span style="width:100px;display:block;float:left">
					$[dept_code]
				</span>
				<span style="width:100px;display:block;float:left">
					$[dept_name]
				</span>
			</s:option>
		</s:autocomplete>
		<s:autocomplete action="ipc.sample.queryFeibie" cols="3" label="费别" name="feibieFifter" limit="10"> 
			<s:option value="$[feibie_name]" label="$[feibie_name]">
				<span style="width:100px;display:block;float:left">
					$[p_key]
				</span>
				<span style="width:100px;display:block;float:left">
					$[feibie_name]
				</span>
			</s:option>
		</s:autocomplete>
		<s:autocomplete action="ipc.sample.queryDoctor" cols="3" label="医生" name="doctorFifter" limit="10"> 
			<s:option value="$[user_name]" label="$[user_name]">
				<span style="width:100px;display:block;float:left">
					$[p_key]
				</span>
				<span style="width:100px;display:block;float:left">
					$[user_name]
				</span>
			</s:option>
		</s:autocomplete> --%>
		<s:textfield cols="3" label="姓名/住院号" name="patient" ></s:textfield>
		<%-- <s:textfield cols="2" label="数量抽取" name="patient_num_sample" value="100"></s:textfield>
		<s:select cols="1" name="num_type" value="1">
			<s:option label="%" value="1"></s:option>
			<s:option label="数值" value="2"></s:option>
		</s:select> --%>
		<input type="hidden" name="deptfifter" >
		<s:textfield cols="3" name="dept_name" readonly="true" dbl_action='zl_select_deptment_many_00,dict_his_deptment' label="开嘱科室"></s:textfield>
		<input type="hidden" name="feibiefifter" >
		<s:textfield cols="3" name="feibie_name" readonly="true" dbl_action='zl_select_feibie_many_00,dict_his_feibie' label="费别"></s:textfield>
		<input type="hidden" name="doctorfifter" >
		<s:textfield cols="3" name="doctor_name" readonly="true" dbl_action='zl_select_doctor_many_00,doctor_many' label="医生"></s:textfield>
		<input type="hidden" name="drugfifter" >
		<s:textfield cols="3" name="drug_name" readonly="true" dbl_action='zl_select_dict_his_drug_many,dict_his_drug' label="药品" ></s:textfield>
		</s:row>
		<s:row>
			<div style="text-align: right;">
			<s:button label="重置" icon="" onclick="reset();"></s:button> 
			<s:button label="查询" icon="fa fa-search" onclick="query();"></s:button>
			<s:button label="前往抽样" icon="glyphicon glyphicon-gift" onclick="search();" id="importentbu"></s:button>
			</div>
		</s:row>
	</s:form>
	</div>
	<div class="col-md-9">
		<s:table id="datatable" label="事后点评患者列表" icon="fa fa-list" autoload="false"
			sort="true" limit="10" fitwidth="true" action="ipc.sample.query">
			<s:toolbar>
				<s:button label="前往分配" icon="fa fa-send" onclick="tzresult();" id="importentbu"></s:button>
			</s:toolbar>
			<s:table.fields>
			<s:table.field name="patient_no" datatype="string" label="住院号"></s:table.field>
				<s:table.field name="patient_id" datatype="string" label="患者ID" sort="true"></s:table.field>
				<s:table.field name="patient_name" datatype="template" label="患者名称">
					<a href="javascript:void(0);" onclick="topatient('$[patient_id]','$[visit_id]');">$[patient_name]</a>
				</s:table.field>
				<s:table.field name="visit_id" datatype="string" label="入院次数"></s:table.field>
				<s:table.field name="d_type" datatype="script" label="患者类型" sort="true">
					if(record.d_type == '1'){
						return '住院';
					}else if(record.d_type == '2'){
						return '门诊';
					}else if(record.d_type == '3'){
						return '急诊';
					}else{
						return '测试';
					}
				</s:table.field>
				<%-- <s:table.field name="identity" datatype="string" label="身份"></s:table.field> --%>
				<s:table.field name="charge_type" datatype="string" label="费别"></s:table.field>
				<s:table.field name="p_type" datatype="script" label="医嘱/处方">
					if(record.p_type == '1'){
						return '医嘱';
					}else if(record.p_type != '1'){
						return '处方';
					}
				</s:table.field>
				<s:table.field name="discharge_datetime" datatype="script" label="病人住院状态">
					if(record.p_type != '1'){
						return '出院';
					}
					return '在院';
				</s:table.field>
				<s:table.field name="dept_name" datatype="string" label="科室"></s:table.field>
				<s:table.field name="doctor" datatype="string" label="医生"></s:table.field>
			</s:table.fields>
		</s:table>
	</div>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		var d = $("[name=deptFifter]").getData();
		var t = $("[name=datasouce]").val();
		d.outp_or_inp = t;
		$("[name=deptFifter]").params(d);
		//query();
	});
		
	$("[name=datasouce]").change(function(){
		var d = $("[name=deptFifter]").getData();
		var t = $("[name=datasouce]").val();
		d.outp_or_inp = t;
		$("[name=deptFifter]").params(d);
	});
	

	function query(){
		var feibieFifter_name=$("input[name='feibieFifter']").val();
		var data = $("#myform").getData();
		data.feibieFifter_name = feibieFifter_name;
		if(data.datasouce != 1){
			$("[name='patient_state']").val("2");
		}
		$("#datatable").params(data);
		$("#datatable").refresh();
	}
	
	function cancel(){
		$.closeModal(false);
	}
	function search(){
		/* query();
		var deptFifter_name=$("input[name='deptFifter']").val();
		var feibieFifter_name=$("input[name='feibieFifter']").val();
		var doctorFifter_name=$("input[name='doctorFifter']").val();
		if($("[name='datasouce']").val() != 1){
			$("[name='patient_state']").val("2");
		}
		var data = $("#myform").getData();
		data.create_user = userinfo.no;
		$.modal("sampledetail.html","抽样结果",{
			width:'90%',
			height:'90%',
			patient: data.patient,
			datasouce: data.datasouce,
			mintime: data.mintime,
			maxtime: data.maxtime,
			p_typeFifter: data.p_typeFifter,
			patient_state: data.patient_state,
			dept_code: data.deptFifter,
			feibie: data.feibieFifter,
			doctor: data.doctorFifter,
			deptFifter_name: deptFifter_name,
			deptFifter: data.deptFifter,
			feibieFifter_name: feibieFifter_name,
			feibieFifter: data.feibieFifter,
			doctorFifter_name: doctorFifter_name,
			doctorFifter: data.doctorFifter,
			drugfifter: data.drugfifter,
			drug_name: data.drug_name,
			callback : function(e){
			}
		}); */
		window.location.href="/w/ipc/sample/sampledetail.html";
	}
	
	//病人详情
	function topatient(patient_id,visit_id){
		 layer.open({
			  type: 2,
			  title: "患者详情",
			  //skin: 'layui-layer-rim', //加上边框
			  area: ['840px', '570px'], //宽高
			  content: "/w/hospital_common/showturns/patientdetail.html?patient_id="+patient_id+"&&visit_id="+visit_id
		}); 
	 } 
	 
	function tzresult(){
		window.location.href="/w/ipc/sample/sampleset.html";
	}
	
	function getDruy(){
		$.modal("getdrug.html","抽样结果",{
			width:'90%',
			height:'90%',
			callback : function(e){
			}
		});
	}
	
	function reset(){
		$('[name=deptfifter]').val('');
		$('[name=dept_name]').val('');
		$('[name=feibiefifter]').val('');
		$('[name=feibie_name]').val('');
		$('[name=doctorfifter]').val('');
		$('[name=doctor_name]').val('');
		$('[name=drugfifter]').val('');
		$('[name=drug_name]').val('');
		$('[name=patient]').val('');
		$('[name=mintime]').val('${defalutmixtime}');
		$('[name=maxtime]').val('${defalutmaxtime}');
		$('[name=datasouce]').val('1');
		$('[name=patient_state]').val('1');
		$('[name=p_typeFifter]').val('1');
	}
	
</script>