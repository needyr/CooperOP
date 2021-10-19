<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/ipc/css/spinner.css" >
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

#importentbu2{
	color: #0eabc3;
    background-color: #ffffff;
    border: 1px solid #0eabc3;
}
#importentbu2:hover{
	background: #FFFFFF;
    color: #1d808f;
    border: 1px solid #1d808f;
}

.tiaojianall{
	display: block;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    width: 100px;
}
.form-horizontal .row-fluid .control-label {
    width: 78px !important;
}

.zhaoz{
	position:absolute;
	left:0;
	top:0;
	width:100%;
	height:100%;
	z-index:10000;
	background-color:#80808073;
}
#fa_load{
	font-size: 50px;
    position: fixed;
    top: 50%;
    left: 50%;
}
</style>
<s:page title="事后点评抽样">
<div id="zhaoz" style="display: none">
<div class="spinner">
  <div class="spinner-container container1">
    <div class="circle1"></div>
    <div class="circle2"></div>
    <div class="circle3"></div>
    <div class="circle4"></div>
  </div>
  <div class="spinner-container container2">
    <div class="circle1"></div>
    <div class="circle2"></div>
    <div class="circle3"></div>
    <div class="circle4"></div>
  </div>
  <div class="spinner-container container3">
    <div class="circle1"></div>
    <div class="circle2"></div>
    <div class="circle3"></div>
    <div class="circle4"></div>
  </div>
</div>
</div>
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
		<input type="hidden" name="drugfifter" value="${param.drugfifter}">
		<s:row>
			<s:select cols="3" label="抽样类型" name="unit" value="1">
			<s:option label="按人为单位" value="1"></s:option>
			<s:option label="按处方为单位" value="2"></s:option>
			<s:option label="按医嘱为单位" value="3"></s:option>
			</s:select>
			<s:select cols="3" label="数据来源" name="datasouce" value="${param.datasouce}">
			<s:option label="门急诊" value="-1"></s:option>
			<s:option label="住院" value="1"></s:option>
			<s:option label="急诊" value="3"></s:option>
			<s:option label="门诊" value="2"></s:option>
			</s:select>
			<%-- <s:select cols="3" label="病人住院状态" name="patient_state" value="${param.patient_state}">
			<s:option label="在院" value="1"></s:option>
			<s:option label="出院" value="2"></s:option>
			</s:select> --%>
			<s:select cols="3" label="样本类型" name="p_typeFifter"  value="${param.p_typeFifter}">
			<s:option label="已收费处方" value="1"></s:option>
			<s:option label="在院医嘱" value="2"></s:option>
			<s:option label="出院医嘱" value="3"></s:option>
			</s:select>
		</s:row>
		<s:row>
			<s:datefield label="就诊时间" name="mintime" format="yyyy-MM-dd" value="${defalutmixtime}" cols="3" ></s:datefield>
			<s:datefield label="至" name="maxtime" format="yyyy-MM-dd" value="${defalutmaxtime}" cols="3" ></s:datefield>
			<s:select label="抽样药品" name="drug_type" value="" cols="3" readonly="true">
				<s:option label="全部"  value="0"></s:option>
				<s:option label="西药"  value="1"></s:option>
				<s:option label="中药"  value="2"></s:option>
				<s:option label="草药"  value="3"></s:option>
				<s:option label="民族药"  value="4"></s:option>
			</s:select>
		</s:row>
		<s:row>
		<s:select cols="3" label="抽样方式" name="is_allsample" value="1">
			<s:option label="随机抽样" value="1"></s:option>
			<s:option label="全例抽样" value="2"></s:option>
		</s:select>
		</s:row>
		<s:row>
		<s:select cols="2" name="num_type" value="2" label="数量抽取" style="width: 90px;">
			<s:option label="例数" value="2"></s:option>
			<s:option label="百分比(%)" value="1"></s:option>
		</s:select>
		<s:textfield cols="1"  name="patient_num_sample" style="margin-left: 0px;"></s:textfield>
		</s:row>
		<s:row>
			<input type="hidden" name="comment_way" value="1"/>
			<%-- <s:autocomplete label="抽样目的" action="ipc.sample.queryCommentWay" cols="3" name="comment_way_text" value="常规点评细则" data-code="$[system_code]" readonly="true"> 
				<s:option label="$[comment_name]" value="$[system_code]">$[comment_name]</s:option>
			</s:autocomplete> --%>
		</s:row>
		<input type="hidden" name="deptfifter" >
		<s:textfield cols="3"  name="dept_name" label="开嘱科室" readonly="true" dbl_action="ss();"></s:textfield>		
		<%-- <s:textfield cols="3" name="dept_name" readonly="true" dbl_action='zl_select_deptment_many_00,dict_his_deptment' label="开嘱科室"></s:textfield> --%>
		<input type="hidden" name="feibiefifter" >
		<s:textfield cols="3"  name="feibie_name" label="费别" readonly="true" dbl_action="ss();"></s:textfield>		
		<%-- <s:textfield cols="3" name="feibie_name" readonly="true" dbl_action='zl_select_feibie_many_00,dict_his_feibie' label="费别"></s:textfield> --%>
		<input type="hidden" name="doctorfifter" >
		<s:textfield cols="3"  name="doctor_name" label="医生" readonly="true" dbl_action="ss();"></s:textfield>		
		<%-- <s:textfield cols="3" name="doctor_name" readonly="true" dbl_action='zl_select_doctor_many_00,doctor_many' label="医生"></s:textfield> --%>
		<input type="hidden" name="drugfifter" >
		<s:textfield cols="3"  name="drug_name" label="药品" readonly="true" dbl_action="ss();"></s:textfield>
		<%-- <s:textfield cols="3" name="drug_name" readonly="true" dbl_action='zl_select_dict_his_drug_many,dict_his_drug' label="药品" ></s:textfield> --%>
		<s:textfield cols="3" name="diag" label="诊断"></s:textfield>
		<s:textfield cols="3" label="患者ID/住院号" name="patient" value="${param.patient}" datatype="script"></s:textfield>
		</s:row>
		<s:row>
		</s:row>
		<s:row>
			<div style="margin-left: 100px;">
			<input type="checkbox" value="1" name="contain_sample" checked="checked" id="contain_sample"/>
			<label for="contain_sample">包含已抽样</label>			
			</div>
		</s:row>
		<s:row>
			<s:button label="抽样" icon="glyphicon glyphicon-save" onclick="save();" id="importentbu2"></s:button>
			<s:button label="保存" icon="fa fa-save" onclick="update();" id="importentbu" ></s:button> 
			<s:button label="分配" icon="glyphicon glyphicon-random" onclick="tzresult();"></s:button>
			<s:button label="重置" icon="glyphicon glyphicon-minus-sign" onclick="resetOWn();"></s:button> 
		</s:row>
	</s:form>
	</div>
	<div class="col-md-9">
		<s:table id="datatable" label="抽样点评患者列表" icon="fa fa-list" autoload="false"
			sort="true" limit="25" fitwidth="true" action="ipc.sample.queryStartSample" height="520">
		<s:table.fields>
		<s:table.field name="patient_no" datatype="string" label="住院号"></s:table.field>
		<s:table.field name="patient_id" datatype="string" label="患者ID" sort="true"></s:table.field>
		<s:table.field name="patient_name" datatype="template" label="患者名称">
			<a href="javascript:void(0);" onclick="topatient('$[patient_id]','$[visit_id]');">$[patient_name]</a>
		</s:table.field>
		<%-- <s:table.field name="visit_id" datatype="string" label="入院次数" width="50"></s:table.field> --%>
		<s:table.field name="charge_type" datatype="string" label="费别" width="120"></s:table.field>
		<%-- <s:table.field name="shenfen" datatype="string" label="身份"></s:table.field>
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
		</s:table.field> --%>
		<s:table.field name="dept_in" datatype="string" label="科室"></s:table.field>
		<s:table.field name="director" datatype="string" label="医生"></s:table.field>
		<s:table.field name="diagnosis_desc" datatype="script" label="诊断" > 
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
	var gzid = "";//开始抽样的挂账ID
	var is_save = 0;//标记是否保存抽样内容
	var is_finish = 0;//标记是否按时完成抽样
	$(function(){
		/* var d = $("[name=deptFifter]").getData();
		var t = $("[name=datasouce]").val();
		d.outp_or_inp = t;
		$("[name=deptFifter]").params(d); */
		//query();
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
	
	$("[name=datasouce]").change(function(){
		var t = $("[name=datasouce]").val();
		if(t == '1'){
			$("[name=p_typeFifter]").val('2');
		}else{
			$("[name=p_typeFifter]").val('1');
		}
		changetime();
	});
	
	$("[name=p_typeFifter]").change(function(){
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
	
	function query(id){
		$("#datatable").params({"GZID": id,"async": false});
		$("#datatable").refresh();
		$('#zhaoz').css('display','none');
		$('#zhaoz').removeClass('zhaoz');
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
	
	function save(){
		var data = $("#myform").getData();
		var arr = data.mintime.split('-');
		var min = new Date(arr[0],arr[1],arr[2]);
		var mintime = min.getTime();
		var arrB = data.maxtime.split('-');
		var max = new Date(arrB[0],arrB[1],arrB[2]);
		var maxtime = max.getTime();
		if(mintime <= maxtime){
			if($('#contain_sample').is(':checked')){
				data.contain_sample = '1';
			}else{
				data.contain_sample = '0';
			}
			autosave(data);
		}else{
			$.message("时间填写有误！");
		}
	}
	
	function autosave(data){
		data.create_user = userinfo.no;
		var reg = /^[1-9]\d*|0$/;//是否大于0的整数
		var reg2 = /^(0?\d?\d|100)$/;//是否为0-100的数字
		var text = $("input[name='patient_num_sample']").val();
		var num_type = $("[name='num_type']").val();
		if(num_type == '1' && !reg2.test(text)){
			$.message("随机抽样请输入正确的整数！");
		}else if(num_type == '2' && !reg.test(text)){
			$.message("随机抽样请输入正确的整数！");
		}else{
			$('#zhaoz').css('display','');
			$('#zhaoz').addClass('zhaoz');
			if(is_save == 0 && gzid){
				$.call("ipc.sample.sampleAllTMPByGZID", {"GZID":gzid}, function(rtn) {
					
				});
			}
			$.call("ipc.sample.insertSample", data, function(rtn) {
				if(rtn.gzid){
					query(rtn.gzid);
					gzid = rtn.gzid;
					is_save = 0;
					is_finish = 1;
				}
			},function(e){},{async:true,timeout:0});
			/* setTimeout(function(){
				if(is_finish == 0){
				layer.alert("抽样时间过长!您可以选择[继续等待]或[取消]", {
					icon:0,
					title: "抽样提醒！",
					scrollbar: false,
					offset: '38%',
					closeBtn: 0,
					zIndex: 99999,
					btn: ["继续等待", "取消"], 
					yes: function(index) {
						layer.close(index);
					},
					cancel: function(index) {
						window.location.href="/w/ipc/sample/samplerandom.html";
					}
				}); 
				}
			},8000); */
		}
	}
	
	function update(){
		if(is_save == 0 && gzid){
			$.confirm("是否确认保存抽样？",function callback(e){
				if(e==true){
					$.call("ipc.sample.realSaveSample", {GZID:gzid}, function(rtn) {
						is_save = 1;
						gzid = "";
						query(null);
					},function(e){},{async:true,remark:false,timeout:0});
				}
			});
		}else{
			$.message("请先抽样!");
		}
	}
	
	function fanhui(){
		$.closeModal();
	}
	
	function tzresult(){
		if(is_save == 0 && gzid){
			$.call("ipc.sample.sampleAllTMPByGZID", {"GZID":gzid}, function(rtn) {
				
			});
		}
		window.location.href="/w/ipc/sample/sampleset.html";
	}
	
	$("[name=is_allsample]").change(function(){
		var value = $("[name=is_allsample]").val();
		if(value == '1'){
			$("[name=patient_num_sample]").val("");
			$("[name=patient_num_sample]").removeAttr("readonly");
			$("[name=num_type]").removeAttr("readonly");
		}else{
			$("[name=patient_num_sample]").val("100");
			$("[name=num_type]").val("1");
			$("[name=patient_num_sample]").attr("readonly","readonly");
			$("[name=num_type]").attr("readonly","readonly");
		}
	});
	
	function resetOWn(){
		deleteHTML();
		addHTML();
		$('[name=spcomment_unit]').val('1');
		$('[name=deptfifter]').val('');
		$('[name=dept_name]').val('');
		$('[name=feibiefifter]').val('');
		$('[name=feibie_name]').val('');
		$('[name=doctorfifter]').val('');
		$('[name=doctor_name]').val('');
		$('[name=drugfifter]').val('');
		$('[name=drug_name]').val('');
		$('[name=patient]').val('');
		$('[name=patient_num_sample]').val('');
		$('[name=mintime]').val('${defalutmixtime}');
		$('[name=maxtime]').val('${defalutmaxtime}');
		$('[name=datasouce]').val('-1');
		$('[name=patient_state]').val('1');
		$('[name=p_typeFifter]').val('1');
		$('[name=num_type]').val('2');
		$('[name=comment_way]').val('1');
		$('[name=comment_way_text]').val('常规点评细则');
	}
	
	window.onbeforeunload=function(){
		if(is_save == 0 && gzid){
			$.call("ipc.sample.sampleAllTMPByGZID", {"GZID":gzid}, function(rtn) {
				
			});
		}
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