<%@page import="java.util.Date"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="添加事后审查队列">
    <div class="note note-warning">
	    温馨提示：1、预览队列仅用于检测当前筛选的临时队列是否准确，可直接选择存入队列；
	    2、筛选条件和数据较多，可能会花费一定时间，请耐心等待；
	    3、如果提示连接服务器超时，可能是数据量过大，可以尝试规定队列上限试试；
    </div>
	<s:row>
		<div class="col-md-4">
			<s:form label="搜索条件" id="myform">
				<s:row>
					<s:select cols="3" label="抽样类型" id="unit" value="1">
					    <s:option label="按人为单位" value="1"/>
					    <s:option label="按处方为单位" value="2"/>
					    <s:option label="按医嘱为单位" value="3"/>
					</s:select>
					<s:select cols="3" label="数据来源" name="d_type" value="1">
					    <s:option label="住院" value="1"/>
					    <s:option label="门诊" value="2"/>
					    <s:option label="门急诊" value="-1"/>
					    <s:option label="急诊" value="3"/>
					</s:select>
					<s:select cols="3" label="样本类型" name="p_type" value="1">
						<s:option label="已收费处方" value="1"/>
						<s:option label="在院医嘱" value="2"/>
						<s:option label="出院医嘱" value="3"/>
					</s:select>
				    <s:datefield label="就诊时间" name="mintime" format="yyyy-MM-dd" cols="3" />
				    <s:datefield label="至" name="maxtime" format="yyyy-MM-dd" cols="3" />
					<s:textfield cols="3" id="dept_name" label="开嘱科室" readonly="true" placeholder="双击选取科室" dbl_action="noaction()"/>
					<input type="hidden" name="deptfifter">
					<s:textfield cols="3" id="feibie_name" label="费别" readonly="true" placeholder="双击选取费别" dbl_action="noaction()"/>	
					<input type="hidden" name="feibiefifter">
					<s:textfield cols="3" id="doctor_name" label="医生" readonly="true" placeholder="双击选取医生" dbl_action="noaction()"/>	
					<input type="hidden" name="doctorfifter">	
					<s:textfield cols="3"  id="drug_name" label="药品" readonly="true" placeholder="双击选取药品" dbl_action="noaction()"/>
					<input type="hidden" name="drugfifter">	
					<s:textfield cols="3"  label="患者ID/住院号" name="patient" />
					<s:checkbox label="患者性别" name="p_sex" cols="3">
						<s:option value="男" label="男"/>
						<s:option value="女" label="女"/>
						<s:option value="0" label="未知"/>
				    </s:checkbox>
				    <s:textfield cols="3" name="p_min_age" label="患者年龄(就诊时)" placeholder="患者就诊时年龄 如:0"/>		
					<s:textfield cols="3" name="p_max_age" label="至" placeholder="患者就诊时年龄 如:0"/>		
					<s:textfield cols="3" name="remark" label="队列描述" placeholder="默认当前时间"/>	
					<s:textfield cols="3" name="qMaxCount" label="队列上限" placeholder="默认为全部"/>
				</s:row>
				<s:row>
					<div style="margin-left: 100px;">
						<input type="checkbox" value="1" name="isInclude" checked="checked" id="contain_sample"/>
						<label for="contain_sample">包含已有队列数据</label>
					</div>
				</s:row>
				<s:row>
					<s:button label="预览队列" icon="fa fa-search" onclick="preview();" id="importentbu2"/> 
					<s:button label="立即存入" icon="fa fa-save" onclick="queueAdd();" id="importentbu" /> 
					<s:button label="重置" icon="glyphicon glyphicon-minus-sign" onclick="resetOWn();"/> 
				</s:row>
			</s:form>
		</div>
		<div class="col-md-8">
			<s:table id="datatable" label="队列患者临时列表" icon="fa fa-list" autoload="false"
				sort="true" limit="10" fitwidth="true" action="hospital_common.afaudit.queryTmpQueue" 
				 height="400" >
				<s:table.fields>
					<s:table.field name="patient_no" datatype="string" label="就诊号"/>
					<s:table.field name="patient_id" datatype="string" label="患者ID" sort="true"/>
					<s:table.field name="patient_name" datatype="template" label="患者名称">
						<a href="javascript:void(0);" onclick="topatient('$[patient_id]','$[visit_id]');">$[patient_name]</a>
					</s:table.field>
					<s:table.field name="sex" datatype="string" label="患者性别"/>
					<s:table.field name="age" datatype="string" label="就诊年龄"/>
					<s:table.field name="admission_datetime" datatype="string" label="就诊时间" width="130px" sort="true"/>
					<s:table.field name="visit_id" datatype="string" label="入院次数" sort="true"/>
					<s:table.field name="doctor_name" datatype="string" label="主治医生"/>
					<s:table.field name="dept_name" datatype="string" label="诊断科室" width="100px"/>
					<s:table.field name="feibie_name" datatype="string" label="费别" width="100px"/>
					<s:table.field name="p_type" label="开单类型" datatype="script">
	                	var p_type = record.p_type;
	                	var d_type = record.d_type;
	                	if(p_type == 1){p_type= '医嘱';}
	                	else if (p_type == 2){p_type= '处方';}
	                	if(d_type == 1 ){d_type = '住院';}
	                	else if(d_type == 2){d_type = '急诊';}
	                    else if (d_type == 3){d_type = '门诊';}
	                	return '<a onclick="druglist(\''+record.patient_id+'\', \''+record.visit_id+'\');">'+d_type + p_type+ '</a>';
	                </s:table.field>
			    </s:table.fields>
		    </s:table>
		</div>
	</s:row>
</s:page>
<script type="text/javascript">
	$(function(){
	});
	
	function changetime(){
		var unit = $('#unit').val();
		var dtype = $('[name=d_type]').val();
		var t = $("[name=p_type]").val();
		if(unit == '1' && dtype == '1'){$('input[name=mintime]').parent().prev().text('入院日期');}
		else if(unit == '1' && dtype != '1'){$('input[name=mintime]').parent().prev().text('就诊时间')}
		else if(unit == '2' && dtype != '1'){$('input[name=mintime]').parent().prev().text('开具时间');}
		else if(unit == '3' && dtype == '1'){$('input[name=mintime]').parent().prev().text('开嘱时间');}
		if(t =='3'){$('input[name=mintime]').parent().prev().text('出院日期');}
	}
	
	$("[name=p_type]").change(function(){changetime();});
	
	$("[name=d_type]").change(function(){
		var t = $("[name=d_type]").val();
		if(t == '1'){$("[name=p_type]").val('2');}
		else{$("[name=p_type]").val('1');}
		changetime();
	});
	
	$("#unit").change(function(){
		var unit = $('#unit').val();
		tiaoz(unit);
		changetime();
	});
	
	function tiaoz(unit){
		if(unit == '2'){
			deleteHTML();
			var html = [];
			html.push('<option value="2">门诊</option>');
			html.push('<option value="-1">门急诊</option>');
			html.push('<option value="3">急诊</option>');
			$('select[name=d_type]').append(html.join(''));
			var html2 = [];
			html2.push('<option value="1">已收费处方</option>');
			$('select[name=p_type]').append(html2.join(''));
		}else if(unit == '3'){
			deleteHTML();
			var html = [];
			html.push('<option value="1">住院</option>');
			$('select[name=d_type]').append(html.join(''));
			var html2 = [];
			html2.push('<option value="2">在院医嘱</option>');
			html2.push('<option value="3">出院医嘱</option>');
			$('select[name=p_type]').append(html2.join(''));
		}else if(unit == '1'){
			deleteHTML();
			addHTML();
		}
	}
	
	//初始化页面数据
	function addHTML(){
		var html = [];
		html.push('<option value="1">住院</option>');
		html.push('<option value="2">门诊</option>');
		html.push('<option value="-1">门急诊</option>');
		html.push('<option value="3">急诊</option>');
		$('select[name=d_type]').append(html.join(''));
		var html2 = [];
		html2.push('<option value="1">已收费处方</option>');
		html2.push('<option value="2">在院医嘱</option>');
		html2.push('<option value="3">出院医嘱</option>');
		$('select[name=p_type]').append(html2.join(''));
	}
	
	//重置页面方法
	function deleteHTML(){
		$('select[name=d_type] option[value="-1"]').remove();
		$('select[name=d_type] option[value="1"]').remove();
		$('select[name=d_type] option[value="2"]').remove();
		$('select[name=d_type] option[value="3"]').remove();
		$('select[name=p_type] option[value="1"]').remove();
		$('select[name=p_type] option[value="2"]').remove();
		$('select[name=p_type] option[value="3"]').remove();
	}
	
	function query(){
		var data = $("#myform").getData();
		$("#datatable").params(data);
		$("#datatable").refresh();
	}
	//执行前对数据规范的检查
	function standardCheck(){
		//var re = /^[0-9]+.?[0-9]*$/; 
	    //判断正整数 /^[1-9]+[0-9]*]*$/ (非空项用正则表达式，允许空用isNaN())
	    //if(re.test(nubmer))
	    //if(isNaN(nubmer))
		if($("form").valid()){//必填检查
			//就诊日期检查
			var qTimeLock=0;
			var mintimeval=$("[name='mintime']").val();
		    var maxtimeval=$("[name='maxtime']").val();
		    if(mintimeval != "" && maxtimeval != ""){qTimeLock=1;}//就诊日期前后都填了
			if((mintimeval != "" && maxtimeval != "") || (mintimeval == "" && maxtimeval == "")){//就诊日期前后都填了或者前后都没填
				if(qTimeLock == 1){//就诊日期前后都填了
					var arr = mintimeval.split('-');
					var min = new Date(arr[0],arr[1],arr[2]);
					var mintime = min.getTime();
					var arrB = maxtimeval.split('-');
					var max = new Date(arrB[0],arrB[1],arrB[2]);
					var maxtime = max.getTime();
					if(mintime > maxtime){$.message("就诊时间填写有误！");}
				}
				//就诊年龄检查
				var qAgeLock=0;
				var p_min_age=$("[name='p_min_age']").val();
	    		var p_max_age=$("[name='p_max_age']").val();
	    		if(p_min_age != "" && p_max_age != ""){qAgeLock==1;}//就诊年龄前后都填了
	    		if((p_min_age!=""&&p_max_age!="")||(p_min_age==""&&p_max_age=="")){//就诊年龄前后都填了或者前后都没填
	    			if(qAgeLock==1){//就诊年龄前后都填了
	    				if(isNaN(p_min_age)||isNaN(p_max_age)||(p_min_age>p_max_age)){$.message("就诊年龄填写有误！");}//就诊年龄不符合规则，允许空
	    			}
	    			//队列上限检查
	    			var nubmer = $("[name='qMaxCount']").val();
	    			if(!isNaN(nubmer)){//队列上限是纯数字,允许空
	    				return true;
	    			}else{$.message("队列上限填写有误！");}
	    		}else{$.message("就诊年龄填写有误！");}
			}else{$.message("就诊时间填写有误！");}
		}
		return false;
	}
	//预览队列
	function preview(){
		if(standardCheck()){query();}//规范检查
	}
	//病人详情
	function topatient(patient_id,visit_id){
	    $.modal('/w/hospital_common/showturns/patientdetail.html?patient_id='+patient_id+'&visit_id='+visit_id+'',"查看患者详情",{
			width: '900px',
			height: '550px',
			callback : function(e){}
		});
	 } 
	//查看医嘱
	function druglist(patient_id, visit_id){
		$.modal('/w/hospital_common/afaudit/druglist.html?patient_id='+ patient_id+"&visit_id="+visit_id,"查看医嘱",{
		  	width: '950px',
		  	height: '550px',
		    callback : function(e){}
		});
	}
	//存入队列
	function queueAdd(data){
		if(standardCheck()){//规范检查
			$.confirm("确定添加当前队列吗？", function(btn) {
				if (btn) {
					var data = $("#myform").getData();
					$.call("hospital_common.afaudit.insert",data,
					    function(rtn){
						    if(rtn){$.closeModal(rtn);}
						    else{$.message("未找到匹配患者就诊记录！");}
						}
					);
				}
			});
		}
	}
	
	//重置
	function resetOWn(){
		deleteHTML();
		addHTML();
		$('#unit').val('1');
		$('[name=deptfifter]').val('');
		$('#dept_name').val('');
		$('[name=feibiefifter]').val('');
	    $('#feibie_name').val('');
		$('[name=doctorfifter]').val('');
		$('#doctor_name').val('');
		$('[name=drugfifter]').val('');
		$('#drug_name').val('');
		$('[name=patient]').val('');
		$('[name=mintime]').val('${defalutmixtime}');
		$('[name=maxtime]').val('${defalutmaxtime}');
		$('[name=p_type]').val('1');
		$('[name=p_sex]').removeAttr("checked");
		$('[name=p_sex]').parent().find("span").attr("class","");
		$('[name=p_min_age]').val("");
		$('[name=p_max_age]').val("");
		$('[name=remark]').val('');
		$('[name=qMaxCount]').val('');
	}
	//选择药品
	$('#drug_name').dblclick(function(){
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
					$('#drug_name').val(name);
					$('[name=drugfifter]').val(code);
				}
		    }
		});
	});
	//选择医生
	$('#doctor_name').dblclick(function(){
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
					$('#doctor_name').val(name);
					$('[name=doctorfifter]').val(code);
				}
		    }
		});
	});
	//选择科室
	$('#dept_name').dblclick(function(){
		var code = $('[name=deptfifter]').val();
		var d_type = $('[name=d_type]').val();
		$.modal("/w/ipc/sample/choose/department.html", "添加科室", {
			height: "550px",
			width: "40%",
			code: code,
			maxmin: false,
			datasouce: d_type,
			callback : function(rtn) {
				if(rtn){
					var name = rtn.name.toString();
					var code = rtn.code.toString();
					$('#dept_name').val(name);
					$('[name=deptfifter]').val(code);
				}
		    }
		});
	});
	//选择费别
	$('#feibie_name').dblclick(function(){
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
					$('#feibie_name').val(name);
					$('[name=feibiefifter]').val(code);
				}
		    }
		});
	});
</script>