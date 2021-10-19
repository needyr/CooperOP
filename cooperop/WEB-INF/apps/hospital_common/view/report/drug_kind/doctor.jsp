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
<s:page title="医生药品品种数统计表">
<!-- <div class="top"> -->
<s:form id="form" collapsed="false" extendable="true">
	<s:row>
	<s:datefield label="时间" name="start_time" required="true" format="yyyy-MM-dd" autocomplete="off" value="" cols="1" ></s:datefield>
	<s:datefield label="至" name="end_time" required="true" format="yyyy-MM-dd" autocomplete="off" value="" cols="1" ></s:datefield>
	</s:row>

	<s:row>
	<s:checkbox label="类型" cols="1" name="d_type">
		<s:option value ="2" label="门诊"></s:option>
		<s:option value ="3" label="急诊"></s:option>
		<s:option value ="1" label="住院"></s:option>
		<s:option value ="4" label="出院"></s:option>
	</s:checkbox>
	</s:row>

	<s:row>
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
	</s:row>

	<s:row>
	<input type="hidden" name="doctor_code" >
	<div class="cols2">
	<label class="control-label">医生</label>
	<div class="control-content">
	<textarea style="height: 40px;" readonly="readonly" class="form-control" placeholder="双击选择医生" name="doctor_name" cols="4"  data-autosize-on="true"></textarea>
	</div>
	</div>
	<a onclick="clean_doctor_name()">清空</a>
	</s:row>

	<s:row>
		<s:checkbox label="剂型" cols="1" name="jixing">
			<s:option value ="1" label="口服剂型"></s:option>
			<s:option value ="2" label="注射剂型"></s:option>
			<s:option value ="3" label="其他剂型"></s:option>
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
    <span style="font-size:14px">医生药品品种数统计表</span>
  	<button id="export" style="margin-left: 10px;" class="layui-btn layui-btn-primary layui-btn layui-btn-sm">导出结果清单</button>
  	<button class="layui-btn layui-btn-primary layui-btn layui-btn-sm" onclick="query1()">查询排名</button>
  </div>
</script>
<script type="text/javascript">
var cxtj;
var cols1 = [];
$(function(){
	var date = new Date();
	var d = date.getFullYear() + '-' + (((date.getMonth()+1)<10)?'0'+(date.getMonth()+1):(date.getMonth()+1)) + '-' +((date.getDate())<10?'0'+(date.getDate()):(date.getDate()));
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
})


function create_table1(real_re){
	layui.use('table', function(){
	  var table = layui.table;
	  var ins1 = table.render({
	    elem: '#test'
	    //,url:'/w/hospital_common/report/common/query_dui.json'
	    ,toolbar: '#toolbarDemo'
	    ,title: '医生药品品种数统计表'
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

	$.call('hospital_common.report.drug_kind.query_doctor',data,function(rtn){
		var real_re = [];
		if(rtn.count>0){
			var res = rtn.resultset;
			for(var i=0;i<res.length;i++){
				real_re.push(res[i]);
				if(res.length - 1 == i){
					real_re.push({'doctor_name':'1.统计范围'});
					real_re.push({'doctor_name':'时间：'+cxtj.start_time+'~'+cxtj.end_time});
					real_re.push({'doctor_name':'类型：'+ (cxtj.d_type && cxtj.d_type.length >0?cxtj.d_type_name:'全部')});
					if(is_null("jixing")){
						real_re.push({'doctor_name':'剂型：'+cxtj.jixing_name});
					}
					if(is_null("dept_name")){
						real_re.push({'doctor_name':'科室：'+cxtj.dept_name});
					}
					if(is_null("doctor_name")){
						real_re.push({'doctor_name':'医生：'+cxtj.doctor_name});
					}
					if(cxtj.yllb_code){
						real_re.push({'doctor_name':'药理类别：'+cxtj.yllb_name});
					}
				}
			}
		}
		var data_cols1 = [];
		data_cols1.push({field:'doctor_name', title:'医生姓名'});
		data_cols1.push({field:'zong', title:'药品品种数(种)',sort:true});
		data_cols1.push({field:'jbyw', title:'基本药物品种数(种)',sort:true});
		data_cols1.push({field:'kjy', title:'抗菌药物品种数(种)',sort:true});
		data_cols1.push({field:'kjy1', title:'非限制抗菌药物品种数(种)',sort:true});
		data_cols1.push({field:'kjy2', title:'限制抗菌药物品种数(种))',sort:true});
		data_cols1.push({field:'kjy3', title:'特殊抗菌药物品种数(种)',sort:true});
		cols1 = [data_cols1];
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
</script>
