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
/* .layui-table-cell{
  height:auto;
  overflow:visible;
  text-overflow:inherit;
} */
</style>
<s:page title="">
<div class="top">
<s:form id="form">
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
	
	<%-- <s:row>
		<s:checkbox label="抗菌药类型" cols="2" name="kjy_drug_type">
			<s:option value ="" label=""></s:option>
		</s:checkbox>
	</s:row> --%>
	
	<s:row>
		<s:checkbox label="抗菌药级别" cols="2" name="kjy_drug_level">
			<s:option value ="1" label="非限制级"></s:option>
			<s:option value ="2" label="限制级"></s:option>
			<s:option value ="3" label="特殊级"></s:option>
		</s:checkbox>
	</s:row>
	
	<%-- <s:row>
		<s:checkbox label="剂型" cols="2" name="jixing">
			<s:option value ="" label=""></s:option>
		</s:checkbox>
	</s:row> --%>
	
	
	
</s:form>
</div>
<div class="bottom">
	<div class="tabs">
	    <div class="category">
	        <ul style="margin-bottom: 0px;">
	            <li class="active">药品使用强度统计</li>
	            <li>药物类使用强度统计</li>
	        </ul>
	    </div>
	    <div class="cont active">
	        <table class="layui-table" id="test" lay-filter="test"></table>
	    </div>
	    <div class="cont">
	        <table class="layui-hide" id="test2" lay-filter="test"></table>
	    </div>
	</div>
</div>
</s:page>
<script src="${pageContext.request.contextPath}/res/hospital_common/js/table_print_excel.js" type="text/javascript"></script>
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container">
    <span style="font-size:14px">药品使用强度统计表</span>
  	<button id="export" style="margin-left: 10px;" class="layui-btn layui-btn-primary layui-btn layui-btn-sm">导出结果清单</button>
  	<button class="layui-btn layui-btn-primary layui-btn layui-btn-sm" onclick="query1()">查询药品使用强度统计</button>
  </div>
</script>
<script type="text/html" id="toolbarDemo2">
  <div class="layui-btn-container">
    <span style="font-size:14px">药物类使用强度统计</span>
  	<button id="export2" style="margin-left: 10px;" class="layui-btn layui-btn-primary layui-btn layui-btn-sm">导出结果清单</button>
	<button class="layui-btn layui-btn-primary layui-btn layui-btn-sm" onclick="query2()">查询药物类使用强度统计</button>
  </div>
</script>
<script type="text/javascript">
$(function(){
	$('[name=start_time]').val('${now_time}');
	$('[name=end_time]').val('${now_time}');
	$('#bottom').css('height',window.innerHeight - 110);
	//$('.dataTables_scrollBody').css('height',window.innerHeight - 310);
	//$('.dataTables_scrollBody').css('width','100%');
	//$('.dataTables_scrollBody').css('overflow','auto');
	//选项卡切换
    $('.category ul li').click(function(){
        indexC = $(this).index();
        $(this).addClass('active').siblings().removeClass('active');
        $('.cont').eq(indexC).addClass('active').siblings().removeClass('active');
    })
    create_table1([]);
    create_table2([]);
})

var cols1 = [[
	{field:'property_toxi', title:'类别', width:80, totalRowText: '合计'}
    ,{field:'drug_name', title:'药品名称', width:120}
    ,{field:'jixing', title:'剂型', width:80}
    ,{field:'druggg', title:'规格', width:80}
    ,{field:'anti_level', title:'级别', width:60}
    ,{field:'num', title:'数量', width:80}
    ,{field:'all_money', title:'总金额(元)', width:120, sort: true,totalRow: true}
    ,{field:'sylc', title:'使用例次', width:120, sort: true,totalRow: true}
    ,{field:'avg_sylc', title:'每例平均金额(元)', width:120,templet: function(record){
        return (Number(record.all_money)/Number(record.sylc)).toFixed(2);
    }}
    ,{field:'ddds', title:'DDDs', width:80,templet: function(record){
  	  return (+record.ddds).toFixed(2);
    },totalRow: true}
    ,{field:'intension', title:'使用强度', width:80,totalRow: true,templet: function(record){
  	  return (Number(record.intension)).toFixed(2);
    }}
    ,{field:'_data1', title:'占药物类总额比例(%)', width:120,templet: function(record){
        return (Number(record.num)/Number(record.kind_drug_num)*100).toFixed(2);
    }}
    ,{field:'_data2', title:'占药药品总金额比例(%)', width:120,templet: function(record){
        return (Number(record.all_money)/Number(record.all_drug_money)*100).toFixed(2);
    }}
]]

var cols2 = [[
	{field:'property_toxi', title:'类别', width:80, totalRowText: '合计'}
    ,{field:'all_money', title:'总金额(元)', width:120, sort: true,totalRow: true}
    ,{field:'sylc', title:'使用例次', width:120, sort: true,totalRow: true}
    ,{field:'avg_sylc', title:'每例平均金额(元)', width:120,templet: function(record){
        return (Number(record.all_money)/Number(record.sylc)).toFixed(2);
    }}
    ,{field:'ddds', title:'DDDs', width:80,templet: function(record){
  	  return (+record.ddds).toFixed(2);
    },totalRow: true}
    ,{field:'intension', title:'使用强度', width:80,totalRow: true,templet: function(record){
  	  return (Number(record.intension)).toFixed(2);
    }}
    ,{field:'_data2', title:'占药药品总金额比例(%)', width:120,templet: function(record){
        return (Number(record.all_money)/Number(record.all_drug_money)*100).toFixed(2);
    }}
]]

function create_table1(real_re){
	layui.use('table', function(){
	  var table = layui.table;
	  var ins1 = table.render({
	    elem: '#test'
	    //,url:'/w/hospital_common/report/common/query_dui.json'
	    ,toolbar: '#toolbarDemo'
	    ,title: '药品使用强度统计表'
    	,defaultToolbar: ['filter']
	    ,totalRow: true
	    ,height: (window.innerHeight - 310)
	    ,cols: cols1
	    ,page: true
	    //,limit: real_re.length
	    /* ,response: {
	    	statusName: 'status' //规定数据状态的字段名称，默认：code
            ,statusCode: null //规定成功的状态码，默认：0
            ,msgName: 'msg' //规定状态信息的字段名称，默认：msg
            ,countName: 'count' //规定数据总数的字段名称，默认：count
            ,dataName: 'resultset' //规定数据列表的字段名称，默认：data
	    } */
	    ,data: real_re
	    })
	    $("#export").off('click').click(function(){
    		table.exportFile(ins1.config.id,real_re, 'xls');
    	})
	});
}

function query1(){
	var data = $("#form").getData();
	$.call('hospital_common.report.kjy.query_dui',data,function(rtn){
		var real_re = [];
		if(rtn.count>0){
			var res = rtn.resultset;
			for(var i=0;i<res.length;i++){
				if(res[i].ddds){
				res[i].intension = Number(res[i].ddds)/Number(res[i].all_patient_ts);
				}
				real_re.push(res[i]);
			}
		}
		create_table1(real_re);
	},null,{timeout:0})
}

function create_table2(real_re){
	layui.use('table', function(){
	  var table = layui.table;
	  var ins1 = table.render({
	    elem: '#test2'
	    //,url:'/w/hospital_common/report/common/query_dui.json'
	    ,toolbar: '#toolbarDemo2'
	    ,title: '药物类使用强度统计'
    	,defaultToolbar: ['filter']
	    ,totalRow: true
	    ,height: (window.innerHeight - 310)
	    ,cols: cols2
	    ,page: true
	    //,limit: real_re.length
	    /* ,response: {
	    	statusName: 'status' //规定数据状态的字段名称，默认：code
            ,statusCode: null //规定成功的状态码，默认：0
            ,msgName: 'msg' //规定状态信息的字段名称，默认：msg
            ,countName: 'count' //规定数据总数的字段名称，默认：count
            ,dataName: 'resultset' //规定数据列表的字段名称，默认：data
	    } */
	    ,data: real_re
	    })
	    $("#export2").off('click').click(function(){
    		table.exportFile(ins1.config.id,real_re, 'xls');
    	})
	});
}

function query2(){
	var data = $("#form").getData();
	$.call('hospital_common.report.kjy.query_dti',data,function(rtn){
		var real_re = [];
		if(rtn.count>0){
			var res = rtn.resultset;
			for(var i=0;i<res.length;i++){
				if(res[i].ddds){
				res[i].intension = Number(res[i].ddds)/Number(res[i].all_patient_ts);
				}
				real_re.push(res[i]);
			}
		}
		create_table2(real_re)
	})
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