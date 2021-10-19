<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<script src="/res/hospital_common/layui/layui.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="/res/hospital_common/layui/css/layui.css">
<link rel="stylesheet" type="text/css" href="/res/hospital_common/css/tabs.css">
<style>
.bottom{
	padding: 20px;
	background: white;
}
</style>
<s:page title="医生药品动态监测排名表">
<!-- <div class="top"> -->
<s:form id="form" collapsed="false" extendable="true">
	<s:row>
		<s:select label="统计每" name="time_unit" value="0" cols="1">
			<s:option value="0" label="月"></s:option>
			<s:option value="1" label="年"></s:option>
		</s:select>
		<s:select label="变化情况" name="bhqk" value="0" cols="1">
			<s:option value="0" label="金额"></s:option>
			<s:option value="1" label="数量"></s:option>
			<s:option value="2" label="强度"></s:option>
		</s:select>
	</s:row>

	<s:row>
	<s:datefield label="时间" name="start_time" required="true" format="yyyy-MM" autocomplete="off" value="" cols="1" ></s:datefield>
	<s:datefield label="至" name="end_time" required="true" format="yyyy-MM" autocomplete="off" value="" cols="1" ></s:datefield>
	</s:row>

	<s:row>
	<s:checkbox label="类型" cols="1" name="d_type">
		<s:option value ="1" label="住(出)院"></s:option>
		<s:option value ="2" label="门诊"></s:option>
		<s:option value ="3" label="急诊"></s:option>
	</s:checkbox>
	<s:textfield label="浮动率警示(%)" cols="1" number="true" name="sj"></s:textfield>
	</s:row>

	<s:row>
		<s:row>
		<input type="hidden" name="dept_code" >
		<div class="cols2">
		<label class="control-label">执行科室</label>
		<div class="control-content">
		<textarea style="height: 40px;" readonly="readonly" class="form-control" placeholder="双击选择科室" name="dept_name" cols="4"  data-autosize-on="true"></textarea>
		</div>
		</div>
		<a onclick="clean_dept_name()">清空</a>
		</s:row>
	</s:row>

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

	<s:row>
		<s:checkbox label="药品类型" cols="3" name="drug_type">
			<c:forEach var="ypfl" items="${ypfl}">
			  <s:option value ="${ypfl.drug_ypfl_code}" label="${ypfl.drug_ypfl_name}"></s:option>
		  	</c:forEach>
		</s:checkbox>
	</s:row>

	<s:row>
	<s:checkbox label="抗菌药类型" cols="1" name="kjy_drug_type">
		<s:option value ="0" label="非抗菌药"></s:option>
		<s:option value ="1" label="抗细菌药"></s:option>
		<s:option value ="2" label="抗真菌药"></s:option>
		<s:option value ="3" label="抗结核药"></s:option>
		<s:option value ="4" label="其他抗菌药"></s:option>
	</s:checkbox>
	<s:checkbox label="抗菌药级别" cols="1" name="kjy_drug_level">
		<s:option value ="1" label="非限制级"></s:option>
		<s:option value ="2" label="限制级"></s:option>
		<s:option value ="3" label="特殊级"></s:option>
	</s:checkbox>
	</s:row>

	<s:row>
		<s:checkbox label="剂型" cols="1" name="jixing">
			<s:option value ="1" label="口服剂型"></s:option>
			<s:option value ="2" label="注射剂型"></s:option>
			<s:option value ="3" label="其他剂型"></s:option>
		</s:checkbox>
		<s:checkbox label="基本药物" cols="1" name="is_jbyw">
			<s:option value ="1" label="是"></s:option>
			<s:option value ="0" label="否"></s:option>
		</s:checkbox>
	</s:row>

	<s:row>
		<input type="hidden" name="yllb_code" >
		<div class="cols2">
		<label class="control-label">药理类别</label>
		<div class="control-content">
		<textarea style="height: 40px;" readonly="readonly" class="form-control" placeholder="双击选择药理类别" name="yllb_name" cols="4"  data-autosize-on="true"></textarea>
		</div>
		</div>
		<a onclick="clean_yllb_name()">清空</a>
	</s:row>

	<s:row>
		<input type="hidden" name="drug_code" >
		<div class="cols2">
		<label class="control-label">药品</label>
		<div class="control-content">
		<textarea style="height: 50px;resize: none;" class="form-control" readonly="readonly" placeholder="双击选择药品" name="drug_name" cols="4"  data-autosize-on="true"></textarea>
		</div>
		</div>
		<a onclick="clean_drug_name()">清空</a>
	</s:row>
</s:form>
<!-- </div> -->
<div class="bottom">
    <div class="cont active">
        <table class="layui-table" id="test" lay-filter="test"></table>
    </div>
</div>
</s:page>
<script src="${pageContext.request.contextPath}/res/hospital_common/js/table_print_excel.js" type="text/javascript"></script>
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container">
    <span style="font-size:14px">医生药品动态监测排名表</span>
  	<button id="export" style="margin-left: 10px;" class="layui-btn layui-btn-primary layui-btn layui-btn-sm">导出结果清单</button>
  	<button class="layui-btn layui-btn-primary layui-btn layui-btn-sm" onclick="query1()">查询排名</button>
  </div>
</script>
<script type="text/javascript">
var cxtj;
var cols1;
$(function(){
	var date = new Date();
	var d = date.getFullYear() + '-' + (((date.getMonth()+1)<10)?'0'+(date.getMonth()+1):(date.getMonth()+1));
	$('[name=start_time]').val(d);
	$('[name=end_time]').val(d);
	$(window).resize(function(){
		$('.bottom').css('height',$(window).height() -
				100 - $("div[ctype='form']").parent().parent().height());
	});
	$(window).resize();
	$("i.showhide").on("click", function(){
		setTimeout(function(){
			$('.bottom').css('height',$(window).height() -
					100 - $("div[ctype='form']").parent().parent().height());
		},500)
	})
	$(".form-collapse-btn").on("click", function(){
		setTimeout(function(){
			$('.bottom').css('height',$(window).height() -
					100 - $("div[ctype='form']").parent().parent().height());
		},500)
	})
    create_table1([]);

	$('[name=time_unit]').change(function(){
		if(this.value == '0'){
			//$('input[name=start_time]').attr('format','yyyy-MM');
			//$('input[name=end_time]').attr('format','yyyy-MM');

			$('input[name=start_time]').after('<input value="" format="yyyy-MM" name="start_time" ctype="datefield" class="form-control date-picker" type="text" required="required" autocomplete="off" label="时间" cols="1" cinited="cinited" aria-required="true" aria-invalid="false">');
			$('input[name=end_time]').after('<input value="" format="yyyy-MM" name="end_time" ctype="datefield" class="form-control date-picker" type="text" required="required" autocomplete="off" label="时间" cols="1" cinited="cinited" aria-required="true" aria-invalid="false">');

			$('input[name=start_time]').eq(0).remove();
			$('input[name=end_time]').eq(0).remove();

			$('input[name=start_time]').ccinit_datefield();
			$('input[name=end_time]').ccinit_datefield();
			var date = new Date();
			var d = date.getFullYear() + '-' + (((date.getMonth()+1)<10)?'0'+(date.getMonth()+1):(date.getMonth()+1));
			$('input[name=start_time]').val(d);
			$('input[name=end_time]').val(d);
		}else if(this.value == '1'){
			//$('input[name=start_time]').attr('format','yyyy');
			//$('input[name=end_time]').attr('format','yyyy');
			$('input[name=start_time]').after('<input value="" format="yyyy" name="start_time" ctype="datefield" class="form-control date-picker" type="text" required="required" autocomplete="off" label="时间" cols="1" cinited="cinited" aria-required="true" aria-invalid="false">');
			$('input[name=end_time]').after('<input value="" format="yyyy" name="end_time" ctype="datefield" class="form-control date-picker" type="text" required="required" autocomplete="off" label="时间" cols="1" cinited="cinited" aria-required="true" aria-invalid="false">');

			$('input[name=start_time]').eq(0).remove();
			$('input[name=end_time]').eq(0).remove();

			$('input[name=start_time]').ccinit_datefield();
			$('input[name=end_time]').ccinit_datefield();
			var date = new Date();
			var d = date.getFullYear();
			$('input[name=start_time]').val(d);
			$('input[name=end_time]').val(d);
		}else{
			this.value = '0'
			//$('input[name=start_time]').attr('format','yyyy-MM');
			//$('input[name=end_time]').attr('format','yyyy-MM');
			$('input[name=start_time]').after('<input value="" format="yyyy-MM" name="start_time" ctype="datefield" class="form-control date-picker" type="text" required="required" autocomplete="off" label="时间" cols="1" cinited="cinited" aria-required="true" aria-invalid="false">');
			$('input[name=end_time]').after('<input value="" format="yyyy-MM" name="end_time" ctype="datefield" class="form-control date-picker" type="text" required="required" autocomplete="off" label="时间" cols="1" cinited="cinited" aria-required="true" aria-invalid="false">');

			$('input[name=start_time]').eq(0).remove();
			$('input[name=end_time]').eq(0).remove();

			$('input[name=start_time]').ccinit_datefield();
			$('input[name=end_time]').ccinit_datefield();
			var date = new Date();
			var d = date.getFullYear() + '-' + (((date.getMonth()+1)<10)?'0'+(date.getMonth()+1):(date.getMonth()+1));
			$('input[name=start_time]').val(d);
			$('input[name=end_time]').val(d);
		}
	})
})


function create_table1(real_re){
	layui.use('table', function(){
	  var table = layui.table;
	  var ins1 = table.render({
	    elem: '#test'
	    //,url:'/w/hospital_common/report/common/query_dui.json'
	    ,toolbar: '#toolbarDemo'
	    ,title: '医生药品动态监测排名表'
    	,defaultToolbar: ['filter']
	    //,totalRow: true
	    ,height: $(window).height() -
		150 - $("div[ctype='form']").parent().parent().height()
	    ,cols: cols1
	    ,page: true
	    ,limit: 50
	    /* ,response: {
	    	statusName: 'status' //规定数据状态的字段名称，默认：code
            ,statusCode: null //规定成功的状态码，默认：0
            ,msgName: 'msg' //规定状态信息的字段名称，默认：msg
            ,countName: 'count' //规定数据总数的字段名称，默认：count
            ,dataName: 'resultset' //规定数据列表的字段名称，默认：data
	    } */
	    ,data: real_re
	    ,done: function(res, curr, count){
	    	$("#export").off('click').click(function(){
	    		table.exportFile(ins1.config.id,real_re, 'xls');
	    	})
	    }
	    })
    	$("i.showhide").on("click", function(){
			$('[lay-id="test"]').height($(window).height() -
					150 - $("div[ctype='form']").parent().parent().height());
			$('[lay-id="test"] .layui-table-box .layui-table-body.layui-table-main').height($(window).height() -
					320 - $("div[ctype='form']").parent().parent().height());
		})
    	$(".form-collapse-btn").on("click", function(){
			$('[lay-id="test"]').height($(window).height() -
					150 - $("div[ctype='form']").parent().parent().height());
			$('[lay-id="test"] .layui-table-box .layui-table-body.layui-table-main').height($(window).height() -
					320 - $("div[ctype='form']").parent().parent().height());
		})
	});
}

function query1(){
	var data = $("#form").getData();
	if (!$("form").valid()){
	       return false;
	}
	cxtj = {};
	cxtj = data;
	get_fifter_name("kjy_drug_type");
	get_fifter_name("d_type");
	get_fifter_name("kjy_drug_level");
	get_fifter_name("drug_type");
	get_fifter_name("jixing");
	get_fifter_name("is_jbyw");

	$.call('hospital_common.report.dynamic_monitor_drug.query_doctor',data,function(rtn){
		var real_re = [];
		var mons = cxtj.time_unit == '0'?monDiff(cxtj.start_time, cxtj.end_time):yearDiff(cxtj.start_time, cxtj.end_time);
		if(rtn.count>0){
			var res = rtn.resultset;
			for(var i=0;i<res.length;i++){
				var _re = res[i];
				if(_re.msg){
					var msg = _re.msg.split('^');
					for(var j = 0;j<msg.length;j++){
						var msg_json = $.parseJSON(msg[j]);
						_re['rowid_money_'+msg_json.date] = msg_json.money;
						if(j != 0){
							_re['rowid_fdl_'+msg_json.date] = msg_json.fdl;
						}
					}
				}
				if(_re.is_warn){
					if(_re.is_warn == '1'){
						_re.warn_mark = '×'
					}
				}
				real_re.push(_re);
				if(res.length - 1 == i){
					real_re.push({'warn_mark':'1.统计范围'});
					real_re.push({'warn_mark':'监测内容：每'+(cxtj.time_unit=='0'?'月':'年')+(cxtj.bhqk == '0'?'金额':cxtj.bhqk == '1'?'数量':'强度')});
					real_re.push({'warn_mark':'时间：'+cxtj.start_time+'~'+cxtj.end_time});
					real_re.push({'warn_mark':'类型：'+ (cxtj.d_type && cxtj.d_type.length >0?cxtj.d_type_name:'全部')});
					if(is_null("dept_code")){
						real_re.push({'warn_mark':'执行科室：'+cxtj.dept_name});
					}
					if(is_null("doctor_code")){
						real_re.push({'warn_mark':'开嘱医生：'+cxtj.doctor_name});
					}
					if(is_null("drug_code")){
						real_re.push({'warn_mark':'药品：'+cxtj.drug_name});
					}
					if(is_null("kjy_drug_type")){
						real_re.push({'warn_mark':'抗菌药类型：'+cxtj.kjy_drug_type_name});
					}
					if(is_null("kjy_drug_level")){
						real_re.push({'warn_mark':'抗菌药级别：'+cxtj.kjy_drug_level_name});
					}
					if(is_null("drug_type")){
						real_re.push({'warn_mark':'药品类型：'+cxtj.drug_type_name});
					}
					if(is_null("jixing")){
						real_re.push({'warn_mark':'剂型：'+cxtj.jixing_name});
					}
					if(cxtj.yllb_code){
						real_re.push({'warn_mark':'药理类别：'+cxtj.yllb_name});
					}
					if(is_null("is_jbyw")){
						real_re.push({'warn_mark':'基本药物：'+cxtj.is_jbyw_name});
					}
				}
			}
		}
	    var data_cols1 = [];
		var data_cols2 = [];
		data_cols1.push({field:'warn_mark', title:'标志', width:80, rowspan: 2});
		data_cols1.push({field:'doctor_name', title:'医生姓名', rowspan: 2});
		data_cols1.push({field:'doctor_code', title:'医生编码', rowspan: 2});
		data_cols1.push({field:'doctor_dept_name', title:'医生所属科室名称', rowspan: 2});
		if(cxtj.time_unit == '0'){
			for(var i = 0;i<mons.length;i++){
				data_cols1.push({field:'', title: mons[i], colspan: 2, align:'center'});
				data_cols2.push({field: 'rowid_money_'+mons[i], title:'金额',sort:true});
				data_cols2.push({field: 'rowid_fdl_'+mons[i], title:'浮动率',sort:true});
			}
		}else{
			for(var i = 0;i<mons.length;i++){
				data_cols1.push({field:'', title: mons[i], colspan: 2, align:'center'});
				data_cols2.push({field: 'rowid_money_'+mons[i], title:'金额',sort:true});
				data_cols2.push({field: 'rowid_fdl_'+mons[i], title:'浮动率',sort:true});
			}
		}
		cols1 = [data_cols1, data_cols2];
		create_table1(real_re);
	},null,{timeout:0})
}

$('[name=yllb_name]').dblclick(function(){
	var code = $('[name=yllb_code]').val();
	$.modal("/w/hospital_common/abase/ylfl_multiple.html", "添加药理类型", {
		height: "550px",
		width: "40%",
		code: code,
		maxmin: false,
		callback : function(rtn) {
			if(rtn){
				var name = rtn.name.join(',');
				var code = rtn.code.join(',');
				$('[name=yllb_name]').val(name);
				$('[name=yllb_code]').val(code);
			}
	    }
	});
});

$('[name=drug_name]').dblclick(function(){
	var code = $('[name=drug_code]').val();
	$.modal("/w/hospital_common/abase/drug.html", "添加药品", {
		height: "550px",
		width: "80%",
		code: code,
		maxmin: false,
		callback : function(rtn) {
			if(rtn){
				var name = rtn.name.toString();
				var code = rtn.code.toString();
				$('[name=drug_name]').val(name);
				$('[name=drug_code]').val(code);
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

function clean_yllb_name(){
	$('[name=yllb_name]').val('');
	$('[name=yllb_code]').val('');
}

function clean_drug_name(_this){
	$('[name=drug_code]').val('');
	$('[name=drug_name]').val('');
}

function clean_dept_name(){
	$('[name=dept_code]').val('');
	$('[name=dept_name]').val('');
}

function clean_doctor_name(){
	$('[name=doctor_code]').val('');
	$('[name=doctor_name]').val('');
}

/**
 * 获取多选显示值
 */
function get_fifter_name(fif_name){
	var value = [];
	$("input[name='"+fif_name+"']").each(function() {
		if (this.checked) {
			value.push($(this).parents("label").text() + " ");
		}
	});
	if(value.length > 0){
		cxtj[fif_name+"_name"] = value.join(',');
	}
}

function is_null(name){
	return cxtj[name] && cxtj[name].length > 0
}

function monDiff(startTime, endTime) {
	var times = [];
    startTime = new Date(startTime);
    endTime = new Date(endTime);
    if((endTime.getYear() == startTime.getYear()) && (endTime.getMonth() == startTime.getMonth()) && endTime>=startTime) {
    	times.push(startTime.getYear()+1900 + '-' + PrefixZero(startTime.getMonth()+1,2));
    } else if(endTime>=startTime && (endTime.getYear() == startTime.getYear())) {
    	for(var i=startTime.getMonth();i<=endTime.getMonth();i++){
    		times.push(startTime.getYear()+1900 + '-' + PrefixZero(i+1,2));
    	}
    }else if(endTime>=startTime && (endTime.getYear() > startTime.getYear())){
    	var num_year = endTime.getYear() - startTime.getYear() + 1;
    	for(var i=0;i<num_year;i++){
    		var ss = startTime.getYear() + i;
    		if(ss == endTime.getYear()){
    			for(var j=0;j<=endTime.getMonth();j++){
    	    		times.push(ss+1900 + '-' + PrefixZero(j+1,2));
    	    	}
    		}else if(ss<endTime.getYear() && ss>startTime.getYear()){
    			for(var j=0;j<12;j++){
    	    		times.push(ss+1900 + '-' + PrefixZero(j+1,2));
    	    	}
    		}else{
    			for(var j=startTime.getMonth();j<12;j++){
    	    		times.push(ss+1900 + '-' + PrefixZero(j+1,2));
    	    	}
    		}
    	}
    }
    return times;
}

function yearDiff(startTime, endTime) {
	var times = [];
    startTime = new Date(startTime);
    endTime = new Date(endTime);
    if(endTime.getYear() == startTime.getYear()) {
    	times.push(startTime.getYear()+1900);
    } else if(endTime.getYear() > startTime.getYear()) {
    	var num_year = endTime.getYear() - startTime.getYear() + 1;
    	for(var i=0;i<num_year;i++){
    		times.push(startTime.getYear() + i + 1900);
    	}
    }
    return times;
}

function PrefixZero(num, n) {
    return (Array(n).join(0) + num).slice(-n);
}
</script>
