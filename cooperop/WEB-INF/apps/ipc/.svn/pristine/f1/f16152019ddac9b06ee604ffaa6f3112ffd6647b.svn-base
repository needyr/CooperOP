<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/ipc/css/sample.css" >
<style type="text/css">
.tiaojianall{
	display: block;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    width: 150px;
}
.hisicon {
    /* 方形样式
    width: 19px;
    height: 19px;
    display: inline-table;
    border-radius: 5px !important;
    text-align: center;
    margin-left: 2px;
    color: #f6f6f6;
    background-color: #989898;
    cursor: pointer; */
    
    /* 圆形样式*/
    border: 1px solid #f47000;
    width: 19px;
    height: 19px;
    display: inline-table;
    border-radius: 10px !important;
    text-align: center;
    margin-left: 2px;
    color: #f47000;
    cursor: pointer;
}

</style>
<s:page title="事后点评抽样">
	<s:row>
	<div class="col-md-3">
	<s:form label="搜索条件" id="myform">
		<s:row>
		<%-- <s:autocomplete action="ipc.sample.queryDrug" cols="3" label="药品" name="drugFifter" limit="10" value="${param.drugFifter}" text="${param.drugFifter_name}" > 
			<s:option value="$[p_key]" label="$[drug_name]">
				<span style="width:100px;display:block;float:left">
					$[p_key]
				</span>
				<span style="width:100px;display:block;float:left">
					$[drug_name]
				</span>
			</s:option>
		</s:autocomplete> --%>
		<%-- <s:textfield cols="3" label="抽样方式" name="" value="手动抽样" readonly="true"></s:textfield> --%>
		<s:select cols="3" label="抽样类型" name="spcomment_unit" value="1">
		<s:option label="按人为单位" value="1"></s:option>
		<s:option label="按处方为单位" value="2"></s:option>
		<s:option label="按医嘱为单位" value="3"></s:option>
		</s:select>
		<s:select cols="3" label="数据来源" name="datasouce" value="${$return.d_type}">
		<s:option label="门急诊" value="-1"></s:option>
		<s:option label="住院" value="1"></s:option>
		<s:option label="急诊" value="3"></s:option>
		<s:option label="门诊" value="2"></s:option>
		</s:select>
		<s:select cols="3" label="样本类型" name="p_typeFifter"  value="">
			<s:option label="已收费处方" value="1"></s:option>
			<s:option label="在院医嘱" value="2"></s:option>
			<s:option label="出院医嘱" value="3"></s:option>
		</s:select>
		<c:if test="${empty param.sample_id}">
		<s:datefield label="就诊时间" name="mintime" format="yyyy-MM-dd" value="${defalutmixtime}" cols="3" ></s:datefield>
		<s:datefield label="至" name="maxtime" format="yyyy-MM-dd" value="${defalutmaxtime}" cols="3" ></s:datefield>
		</c:if>
		<c:if test="${not empty param.sample_id}">
		<s:datefield label="就诊时间" name="mintime" format="yyyy-MM-dd" value="${$return.sample_start_time}" cols="3" ></s:datefield>
		<s:datefield label="至" name="maxtime" format="yyyy-MM-dd" value="${$return.sample_end_time}" cols="3" ></s:datefield>
		</c:if>
		<s:select label="抽样药品" name="drug_type" value="" cols="3" readonly="true">
			<s:option label="全部"  value="0"></s:option>
			<s:option label="西药"  value="1"></s:option>
			<s:option label="中药"  value="2"></s:option>
			<s:option label="草药"  value="3"></s:option>
			<s:option label="民族药"  value="4"></s:option>
		</s:select>
		<input type="hidden" name="comment_way" value="1"/>
		<%-- <s:autocomplete label="抽样目的" action="ipc.sample.queryCommentWay" cols="3" name="comment_way_text" value="常规点评细则" data-code="$[system_code]" readonly="true"> 
			<s:option label="$[comment_name]" value="$[system_code]">$[comment_name]</s:option>
		</s:autocomplete> --%>
		<input type="hidden"  name="deptfifter" value="${$return.dept_code}">
		<s:textfield cols="3"  name="dept_name" label="开嘱科室" readonly="true" value="${$return.dept_name}" dbl_action="ss();"></s:textfield>		
		<input type="hidden"  name="feibiefifter" value="${$return.feibie}">
		<s:textfield cols="3"  name="feibie_name" label="费别" readonly="true" value="${$return.feibie}" dbl_action="ss();"></s:textfield>		
		<input type="hidden"  name="doctorfifter" value="${$return.doctor}">
		<s:textfield cols="3"  name="doctor_name" label="医生" readonly="true" value="${$return.doctor_name}" dbl_action="ss();"></s:textfield>		
		<input type="hidden" name="drugfifter" value="${$return.drug_code}">
		<s:textfield cols="3"  name="drug_name" label="药品" readonly="true" value="${$return.drug_name}" dbl_action="ss();"></s:textfield>
		<s:textfield cols="3" name="diag" label="诊断"></s:textfield>
		<s:textfield cols="3"  label="患者ID/住院号" name="patient" value=""></s:textfield>
		</s:row>
		<s:row>
			<div style="margin-left: 100px;">
			<input type="checkbox" value="1" name="contain_sample" checked="checked" id="contain_sample"/>
			<label for="contain_sample">包含已抽样</label>			
			</div>
		</s:row>
		<s:row>
			<s:button label="筛选" icon="fa fa-search" onclick="sx();" id="importentbu2"></s:button> 
			<s:button label="保存" icon="fa fa-save" onclick="save();" id="importentbu" ></s:button> 
			<s:button label="分配" icon="glyphicon glyphicon-random" onclick="tzresult();"></s:button>
			<s:button label="重置" icon="glyphicon glyphicon-minus-sign" onclick="resetOWn();"></s:button> 
			<%-- <s:button label="返回" icon="fa fa-reply" onclick="fanhui();"></s:button>  --%>
		</s:row>
		<s:row>
		</s:row>
	</s:form>
	</div>
	<div class="col-md-9">
		<s:table id="datatable" label="抽样点评患者列表" icon="fa fa-list" autoload="false"
			sort="true" limit="10" fitwidth="true" action="ipc.sample.query" 
			select="multi" height="450" >
		<s:table.fields>
		<s:table.field name="history" datatype="script" label="提示" width='30px'>
			if(record.is_yichou){
				return '<div title="已经抽取" style="color:red">已抽</div>';
			}else if(record.is_check){
				return '<div class="hisicon" title="已前置审方">查 </div>';
			}else if(record.is_comment){
				return '<div class="hisicon" title="已点评">评</div>';
			}else if(record.is_pharmacist_check){
				return '<div class="hisicon" title="药师已审查">审</div>';
			}else{
				return ' ';
			}
		</s:table.field>
		<s:table.field name="patient_no" datatype="string" label="住院号"></s:table.field>
		<s:table.field name="patient_id" datatype="string" label="患者ID" sort="true"></s:table.field>
		<s:table.field name="patient_name" datatype="template" label="患者名称">
			<a href="javascript:void(0);" onclick="topatient('$[patient_id]','$[visit_id]');">$[patient_name]</a>
		</s:table.field>
		<s:table.field name="visit_id" datatype="string" label="入院次数" width="50"></s:table.field>
		<%-- <s:table.field name="identity" datatype="string" label="身份"></s:table.field> --%>
		<s:table.field name="charge_type" datatype="string" label="费别" width="120"></s:table.field>
		<%--<s:table.field name="shenfen" datatype="string" label="身份"></s:table.field>
		<s:table.field name="d_type" datatype="script" label="患者类型" width="50">
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
		<s:table.field name="p_type" datatype="script" label="医嘱/处方" width="55">
			if(record.p_type == '1'){
				return '医嘱';
			}else if(record.p_type != '1'){
				return '处方';
			}
		</s:table.field> 
		<s:table.field name="discharge_datetime" datatype="script" label="病人住院状态" width="74">
			if(record.p_type != '1'){
					return '出院';
			}
			return '在院';
		</s:table.field>--%>
		<s:table.field name="dept_name" datatype="string" label="科室"></s:table.field>
		<s:table.field name="doctor" datatype="string" label="医生"></s:table.field>
		<s:table.field name="diagnosis_desc" datatype="script" label="诊断" width="150"> 
			if(record.diagnosis_desc){
				return '<div title="'+record.diagnosis_desc+'" class="tiaojianall">'+record.diagnosis_desc+'</div>';
			}
			return " ";
		</s:table.field>
	</s:table.fields>
	</s:table>
	</div>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
		$("#suijicheck").hide();
		var d = $("[name=deptFifter]").getData();
		var t = $("[name=datasouce]").val();
		var p_type = '${$return.p_type}';
		var p_state = '${$return.patient_state}';
		if(p_type){
			if(p_type=='2'){
				$('select[name=p_typeFifter]').val('1');
			}else if(p_type=='1' && p_state == '1'){
				$('select[name=p_typeFifter]').val('2');
			}else if(p_type=='1' && p_state == '2'){
				$('select[name=p_typeFifter]').val('3');
			}
		}
		d.outp_or_inp = t;
		$("[name=deptFifter]").params(d);
		if('${param.sample_id}'){
			query();
		}
	});
	
	function changetime(){
		var unit = $('[name=spcomment_unit]').val();
		var dtype = $('[name=datasouce]').val();
		var t = $("[name=p_typeFifter]").val();
		if(unit == '1' && dtype == '1'){
			$('input[name=mintime]').parent().prev().text('入院日期');
		}else if(unit == '1' && dtype != '1'){
			$('input[name=mintime]').parent().prev().text('就诊时间');
		}else if(unit == '2' && dtype != '1'){
			$('input[name=mintime]').parent().prev().text('开具时间');
		}else if(unit == '3' && dtype == '1'){
			$('input[name=mintime]').parent().prev().text('开嘱时间');
		}
		
		if(t =='3'){
			$('input[name=mintime]').parent().prev().text('出院日期');
		}
	}
	
	$("[name=p_typeFifter]").change(function(){
		changetime();
	});
	
	$("[name=datasouce]").change(function(){
		var t = $("[name=datasouce]").val();
		if(t == '1'){
			$("[name=p_typeFifter]").val('2');
		}else{
			$("[name=p_typeFifter]").val('1');
		}
		changetime();
	});
	
	$("[name=spcomment_unit]").change(function(){
		var unit = $('[name=spcomment_unit]').val();
		tiaoz(unit);
		changetime();
	});
	
	function tiaoz(unit){
		if(unit == '2'){
			deleteHTML();
			var html = [];
			html.push('<option value="-1">门急诊</option>');
			html.push('<option value="2">门诊</option>');
			html.push('<option value="3">急诊</option>');
			$('select[name=datasouce]').append(html.join(''));
			var html2 = [];
			html2.push('<option value="1">已收费处方</option>');
			$('select[name=p_typeFifter]').append(html2.join(''));
		}else if(unit == '3'){
			deleteHTML();
			var html = [];
			html.push('<option value="1">住院</option>');
			$('select[name=datasouce]').append(html.join(''));
			var html2 = [];
			html2.push('<option value="2">在院医嘱</option>');
			html2.push('<option value="3">出院医嘱</option>');
			$('select[name=p_typeFifter]').append(html2.join(''));
		}else if(unit == '1'){
			deleteHTML();
			addHTML();
		}
	}
	
	function addHTML(){
		var html = [];
		html.push('<option value="-1">门急诊</option>');
		html.push('<option value="1">住院</option>');
		html.push('<option value="2">门诊</option>');
		html.push('<option value="3">急诊</option>');
		$('select[name=datasouce]').append(html.join(''));
		var html2 = [];
		html2.push('<option value="1">已收费处方</option>');
		html2.push('<option value="2">在院医嘱</option>');
		html2.push('<option value="3">出院医嘱</option>');
		$('select[name=p_typeFifter]').append(html2.join(''));
	}
	
	function deleteHTML(){
		$('select[name=datasouce] option[value="-1"]').remove();
		$('select[name=datasouce] option[value="1"]').remove();
		$('select[name=datasouce] option[value="2"]').remove();
		$('select[name=datasouce] option[value="3"]').remove();
		$('select[name=p_typeFifter] option[value="1"]').remove();
		$('select[name=p_typeFifter] option[value="2"]').remove();
		$('select[name=p_typeFifter] option[value="3"]').remove();
	}
	
	function query(){
		if($("[name='datasouce']").val() != 1){
			$("[name='patient_state']").val("2");
		}
		var data = $("#myform").getData();
		if('${param.sample_id}'){
			data.sample_id = '${param.sample_id}';
		}
		$("#datatable").params(data);
		$("#datatable").refresh();
	}
	
	function sx(){
		var data = $("#myform").getData();
		var arr = data.mintime.split('-');
		var min = new Date(arr[0],arr[1],arr[2]);
		var mintime = min.getTime();
		var arrB = data.maxtime.split('-');
		var max = new Date(arrB[0],arrB[1],arrB[2]);
		var maxtime = max.getTime();
		if(mintime <= maxtime){
			query();
		}else{
			$.message("时间填写有误！");
		}
	}
	//病人详情
	 function topatient(patient_id,visit_id){
		 layer.open({
			  type: 2,
			  title: "患者详情",
			  //skin: 'layui-layer-rim', //加上边框
			  area: ['100%', '100%'], //宽高
			  offset: '0px',
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
		$.modal("addsample.html","添加抽样",{
			width:'80%',
			height:'80%',
			sample_id: '${param.sample_id}',
			mintime: '${param.mintime}',
			maxtime: '${param.maxtime}',
			callback : function(e){
				query();
			}
		});
	}
	
	function save(){
		var data = $("#myform").getData();
		var arr = data.mintime.split('-');
		var min = new Date(arr[0],arr[1],arr[2]);
		var mintime = min.getTime();
		var arrB = data.maxtime.split('-');
		var max = new Date(arrB[0],arrB[1],arrB[2]);
		var maxtime = max.getTime();
		if(mintime <= maxtime){
			if('${param.sample_id}'){
				patientadd(data);
			}else{
				patientsave(data);
			}
		}else{
			$.message("时间填写有误！");
		}
	}
	
	function patientsave(data){
		var select = $('#datatable').getSelected();
		if(select.length != 0){
			data.create_user = userinfo.no;
			data.select= $.toJSON(select);
			$.call("ipc.sample.saveSample",data,function(rtn){
				window.location.href="/w/ipc/sample/sampledetail.html";
			},function(e){},{async:true,remark:false,timeout:null});
		}else{
			$.message("请抽取患者！");
		}
	}
	
	function patientadd(data){
		var select = $('#datatable').getSelected();
		data.create_user = userinfo.no;
		data.select= $.toJSON(select);
		data.sample_id = '${param.sample_id}'
		$.call("ipc.sample.saveSamplebyedit",data,function(rtn){
			$.closeModal();
		},function(e){},{async:true,remark:false,timeout:null});
		
	}
	
	function fanhui(){
		$.closeModal();
	}
	
	$("[name=sampletype]").change(function(){
		var r = $("[name=sampletype]").val();
		if(r == '2'){
			$("#suijicheck").show();
		}else{
			$("#suijicheck").hide();
		}
	});
	
	function tzresult(){
		window.location.href="/w/ipc/sample/sampleset.html";
	}
	
	function resetOWn(){
		deleteHTML();
		addHTML();
		$('[name=spcomment_unit]').val('1');
		$('[name=diag]').val('');
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
		$('[name=patient_state]').val('1');
		$('[name=p_typeFifter]').val('1');
		$('[name=datasouce]').val('-1');
		$('[name=patient_state]').val('1');
		$('[name=num_type]').val('2');
		$('[name=comment_way]').val('1');
		$('[name=comment_way_text]').val('常规点评细则');
	}
	
	$('[name=drug_name]').dblclick(function(){
		var code = $('[name=drugfifter]').val();
		$.modal("/w/ipc/sample/choose/drug.html", "添加药品", {
			height: "550px",
			width: "80%",
			code: code,
			maxmin: false,
			callback : function(rtn) {
				if(rtn){
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('[name=drug_name]').val(name);
					$('[name=drugfifter]').val(code);
				}
		    }
		});
	});
	
	$('[name=doctor_name]').dblclick(function(){
		var code = $('[name=doctorfifter]').val();
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
					$('[name=doctorfifter]').val(code);
				}
		    }
		});
	});
	
	$('[name=dept_name]').dblclick(function(){
		var code = $('[name=deptfifter]').val();
		var datasouce = $('[name=datasouce]').val();
		$.modal("/w/ipc/sample/choose/department.html", "添加科室", {
			height: "550px",
			width: "40%",
			code: code,
			maxmin: false,
			datasouce: datasouce,
			callback : function(rtn) {
				if(rtn){
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('[name=dept_name]').val(name);
					$('[name=deptfifter]').val(code);
				}
		    }
		});
	});
	
	$('[name=feibie_name]').dblclick(function(){
		var code = $('[name=feibiefifter]').val();
		$.modal("/w/ipc/sample/choose/feibie.html", "添加费别", {
			height: "550px",
			width: "40%",
			code: code,
			maxmin: false,
			callback : function(rtn) {
				if(rtn){
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('[name=feibie_name]').val(name);
					$('[name=feibiefifter]').val(code);
				}
		    }
		});
	});
</script>