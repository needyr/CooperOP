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
	padding: 20px
}
</style>
<s:page title="科室药品使用强度汇总统计表">
<s:form id="form" collapsed="false" extendable="true">
	<s:row>
	<s:datefield label="时间" name="start_time" required="true" format="yyyy-MM-dd" autocomplete="off" value="" cols="1" ></s:datefield>
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
		<s:checkbox label="药品类型" cols="2" name="drug_type">
			<c:forEach var="dt" items="${drug_type}">
			<s:option value ="${dt.property_toxi}" label="${dt.property_toxi}"></s:option>
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
	</s:row>

	<s:row>
		<s:checkbox label="抗菌药级别" cols="2" name="kjy_drug_level">
			<s:option value ="1" label="非限制级"></s:option>
			<s:option value ="2" label="限制级"></s:option>
			<s:option value ="3" label="特殊级"></s:option>
		</s:checkbox>
	</s:row>

	<s:row>
		<s:checkbox label="剂型" cols="2" name="jixing">
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

	<%-- <s:row>
		<s:radio label="排名方式" cols="2" name="pm" value="1">
			<s:option value ="1" label="按药物类排序"></s:option>
			<s:option value ="2" label="按使用强度排序"></s:option>
		</s:radio>
	</s:row> --%>

</s:form>
<div class="bottom">
	<table class="layui-table" id="test" lay-filter="test"></table>
</div>
</s:page>
<script src="${pageContext.request.contextPath}/res/hospital_common/js/table_print_excel.js" type="text/javascript"></script>
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container">
    <span style="font-size:14px">科室药品使用强度汇总统计表</span>
  	<button id="export" style="margin-left: 10px;" class="layui-btn layui-btn-primary layui-btn layui-btn-sm">导出结果清单</button>
  	<button class="layui-btn layui-btn-primary layui-btn layui-btn-sm" onclick="query1()">查询</button>
  </div>
</script>
<script type="text/javascript">
$(function(){
	var date = new Date();
	var d = date.getFullYear() + '-' + (((date.getMonth()+1)<10)?'0'+(date.getMonth()+1):(date.getMonth()+1)) + '-' +((date.getDate())<10?'0'+(date.getDate()):(date.getDate()));
	$('[name=start_time]').val(d);
	$('[name=end_time]').val(d);
	$(window).resize(function(){
		$('.bottom').css('height',$(window).height() -
				150 - $("div[ctype='form']").parent().parent().height());
	});
	$(window).resize();
	$("i.showhide").on("click", function(){
		setTimeout(function(){
			$('.bottom').css('height',$(window).height() -
					150 - $("div[ctype='form']").parent().parent().height());
		},500)
	})
	$(".form-collapse-btn").on("click", function(){
		setTimeout(function(){
			$('.bottom').css('height',$(window).height() -
					150 - $("div[ctype='form']").parent().parent().height());
		},500)
	})
    create_table1([]);
})

var cols1 = [[
	{field:'dept_name', title:'科室名称'}
	,{field:'all_patient', title:'同期收治患者人数(人)'}
	,{field:'all_patient_ts', title:'同期收治患者人天数(天)'}
	,{field:'drug_all_patient', title:'药品使用人数(人)'}
	,{field:'drug_all_patient_all_patient', title:'药品使用率(%)',templet: function(record){
        return (Number(record.drug_all_patient)/Number(record.all_patient)*100 || 0).toFixed(2);
    }}
	,{field:'kind_drug_num', title:'药品品规数'}
    ,{field:'sylc', title:'总例次'}
    ,{field:'all_money', title:'总金额(元)'}
    ,{field:'ddds', title:'累计DDDs'}
    ,{field:'intension', title:'使用强度',templet: function(record){
  	  return (Number(record.intension) || 0).toFixed(2);
    }}
]]

function create_table1(real_re){
	layui.use('table', function(){
	  var table = layui.table;
	  var ins1 = table.render({
	    elem: '#test'
	    //,url:'/w/hospital_common/report/common/query_dui.json'
	    ,toolbar: '#toolbarDemo'
	    ,title: '科室药品使用强度汇总统计表'
    	,defaultToolbar: ['filter']
	    //,totalRow: true
	    ,height: (window.innerHeight - 310)
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
	    ,done : function(res, curr, count) {
	        $("#export").off('click').click(function(){
	    		table.exportFile(ins1.config.id,real_re, 'xls');
	    	})
	 	}
	    })
		$('[lay-id="test"]').height($(window).height() -
				150 - $("div[ctype='form']").parent().parent().height());
		$('[lay-id="test"] .layui-table-box .layui-table-body.layui-table-main').height($(window).height() -
				320 - $("div[ctype='form']").parent().parent().height());
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
	$.call('hospital_common.report.drug_use_intension.query_dept_zj',data,function(rtn){
		var real_re = []
		var all_patient = 0;
		var all_patient_ts = 0;
		var drug_all_patient = 0;
		var kind_drug_num = 0;
		var sylc = 0;
		var all_money = 0;
		var ddds = 0;
		var intension = 0;
		if(rtn.count>0){
			var res = rtn.resultset;
			for(var i=0;i<res.length;i++){
				 if(res[i].ddds){
				res[i].intension = Number(res[i].all_patient_ts)?Number(res[i].ddds)/Number(res[i].all_patient_ts)*100 || 0:0;
				} 
				all_patient += (+res[i].all_patient || 0);
				all_patient_ts += (+res[i].all_patient_ts || 0);
				drug_all_patient += (+res[i].drug_all_patient || 0);
				kind_drug_num += (+res[i].kind_drug_num || 0);
				sylc += (+res[i].sylc || 0);
				all_money += (+res[i].all_money || 0);
				ddds += (+res[i].ddds || 0);
				intension += (+res[i].intension || 0);
				real_re.push(res[i]);
			}
			var total = {};
			total.dept_name = '合计'
			total.all_patient = Number(all_patient).toFixed(2);
			total.all_patient_ts = Number(all_patient_ts).toFixed(2);
			total.drug_all_patient = Number(drug_all_patient).toFixed(2);
			total.kind_drug_num = Number(kind_drug_num).toFixed(2);
			total.sylc = Number(sylc).toFixed(2);
			total.all_money = Number(all_money).toFixed(2);
			total.ddds = Number(ddds).toFixed(2);
			
			//total.intension = Number(intension).toFixed(2);
			total.intension = Number(total.all_patient_ts)?Number(total.ddds)/Number(total.all_patient_ts)*100 || 0:0;
			real_re.push(total);
		}
		create_table1(real_re);
	},null,{timeout:0})
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

function clean_dept_name(){
	$('[name=dept_code]').val('');
	$('[name=dept_name]').val('');
}

function clean_doctor_name(){
	$('[name=doctor_code]').val('');
	$('[name=doctor_name]').val('');
}

function clean_yllb_name(){
	$('[name=yllb_name]').val('');
	$('[name=yllb_code]').val('');
}

function clean_drug_name(_this){
	$('[name=drug_code]').val('');
	$('[name=drug_name]').val('');
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
