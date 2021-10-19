<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<script src="/res/hospital_common/layui/layui.js" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="/res/hospital_common/layui/css/layui.css">
<s:page title="门急诊处方抗菌药物使用情况调查表">
<s:form id="form" collapsed="false" extendable="true">
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
<s:row>
	<!-- 等待添加数据datatables -->
    <table class="layui-table" id="test" lay-filter="test"></table>
</s:row>

</s:page>
<script src="${pageContext.request.contextPath}/res/hospital_common/js/lay_tabletoexcel.js" type="text/javascript"></script>
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container">
    <span style="font-size:14px"></span>
  	<button id="export" style="margin-left: 10px;" class="layui-btn layui-btn-primary layui-btn layui-btn-sm">导出结果清单</button>
  	<button class="layui-btn layui-btn-primary layui-btn layui-btn-sm" onclick="query()">查询</button>
  </div>
</script>
<script type="text/javascript">
var cols1;
$(function(){
	var date = new Date();
	var d = date.getFullYear() + '-' + (((date.getMonth()+1)<10)?'0'+(date.getMonth()+1):(date.getMonth()+1)) + '-' +((date.getDate())<10?'0'+(date.getDate()):(date.getDate()));
	$('[name=start_time]').val(d);
	$('[name=end_time]').val(d);
	$('#bottom').css('height',window.innerHeight - 110);
	$('.dataTables_scrollBody').css('height',window.innerHeight - 310);
	//$('.dataTables_scrollBody').css('width','100%');
	$('.dataTables_scrollBody').css('overflow','auto');
	query();
})

function query(){
	var data = $("#form").getData();
	if (!$("form").valid()){
	       return false;
	}
	create_table1(data);
}

function create_table1(params){
	var data_cols1 = [];
	var data_cols2 = [];
	data_cols1.push({field:'patient_no', title:'门诊号', width:80, rowspan: 2});
	data_cols1.push({field:'group_id', title:'处方号', width:80, rowspan: 2});
	data_cols1.push({field:'patient_id', title:'患者ID', width:100, rowspan: 2});
	
	data_cols1.push({field:'patient_name', title:'患者姓名', width:100, rowspan: 2,templet:function(record){
		return '<a onclick="query_orders(\''+record.patient_id+'\',\''+record.visit_id+'\',\''+record.group_id+'\')">'
		+ record.patient_name+'</a>'
	}});
	
	data_cols1.push({field:'sex', title:'性别', width:80, rowspan: 2});
	data_cols1.push({field:'age', title:'年龄', width:80, rowspan: 2});
	data_cols1.push({field:'admission_datetime', title:'入院时间', width:120, rowspan: 2});
	data_cols1.push({field:'discharge_datetime', title:'出院时间', width:120, rowspan: 2});
	//data_cols1.push({field:'ts', title:'住院天数', width:120, rowspan: 2});
	data_cols1.push({field:'attending_doctor', title:'主管医生', width:100, rowspan: 2});
	data_cols1.push({field:'dept_name', title:'科室', width:120, rowspan: 2});
	data_cols1.push({field:'in_diagnosis', title:'诊断', width:200, rowspan: 2});

	data_cols1.push({field:'oper_message', title:'手术(切口等级)', width:150, rowspan: 2});
	data_cols1.push({field:'drug_kind_num', title:'药品品种数', width:120, rowspan: 2});
	data_cols1.push({field:'kjy_kind_num', title:'抗菌药药品种数', width:120, rowspan: 2});
	data_cols1.push({field:'all_money', title:'治疗金额(元)', width:120, rowspan: 2});
	data_cols1.push({field:'kjy_money', title:'抗菌药金额(元)', width:120, rowspan: 2});
	data_cols1.push({field:'is_lhyy', title:'联合用药', width:80, rowspan: 2,templet:function(record){
		var str = "";
		if(record.one_drug_union_num > 0){
			str += '单联,';
		}
		if(record.two_drug_union_num > 0){
			return '二联,';
		}
		if(record.three_drug_union_num > 0){
			return '三联,';
		}
		if(record.other_drug_union_num > 0){
			return '四联及以上,';
		}
		return str;
	}});


	data_cols1.push({field:'cfxx', title:'处方信息', colspan: 8, align:'center'});

	data_cols2.push({field: 'drug_name', title:'药品名称', width:120});
	data_cols2.push({field: 'jixing', title:'剂型', width:80});
	data_cols2.push({field: 'druggg', title:'规格', width:120});
	data_cols2.push({field: 'shl', title:'数量', width:80});
	//data_cols2.push({field: 'order_money', title:'金额(元)', width:100});
	data_cols2.push({field: 'administration', title:'用法', width:120});
	data_cols2.push({field: 'dosages', title:'用量', width:100});
	data_cols2.push({field: 'frequency', title:'频率', width:100});
	data_cols2.push({field: 'purpose', title:'用药目的', width:100});
	cols1 = [data_cols1, data_cols2];

	layui.use('table', function(){
	  var table = layui.table;
	  var ins1 = table.render({
	    elem: '#test'
	    ,url:'/w/hospital_common/report/kjy/querykjymj.json'
	    ,where: {'json':JSON.stringify(params)}
	    ,toolbar: '#toolbarDemo'
	    ,title: '门急诊处方抗菌药物使用情况调查表'
    	,defaultToolbar: ['filter']
	    //,totalRow: true
	    ,loading:true
	    ,height: $(window).height() -
		150 - $("div[ctype='form']").parent().parent().height()
	    ,cols: cols1
	    ,page: {
	    	limit: 50
		    ,limits: [10,50,100,300,500]
	    }
	    //,autoSort: false //禁用前端自动排序。
	    ,response: {
	    	statusName: 'status' //规定数据状态的字段名称，默认：code
            ,statusCode: null //规定成功的状态码，默认：0
            ,msgName: 'msg' //规定状态信息的字段名称，默认：msg
            ,countName: 'count' //规定数据总数的字段名称，默认：count
            ,dataName: 'resultset' //规定数据列表的字段名称，默认：data
	    }
	    ,request: {
		   pageName: 'layui_page' //页码的参数名称，默认：page
		   ,limitName: 'limit' //每页数据量的参数名，默认：limit
		}
	    //,data: real_re
	    ,done: function(res, curr, count){
	    	merge(res.resultset, curr, count,['patient_no','group_id','patient_id','patient_name',
	    		'sex','age','admission_datetime','discharge_datetime','ts',
	    		'attending_doctor','dept_name','out_diagnosis','oper_message',
	    		'drug_kind_num','kjy_kind_num','all_money','kjy_money','is_lhyy'],
	    		[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17],true);
	    	$("#export").off('click').click(function(){
	    		
	    		var limit_val = $('[lay-id=test] .layui-laypage-limits select').val();
	    		if(limit_val == undefined || limit_val == null){
	    			$.message("未检测到数据！请先查询出相关数据再导出")
	    			return;
	    		}
	    		
	    		$.call('hospital_common.report.kjy.querykjymj',{'json':JSON.stringify(params),limit:limit_val,layui_page:$('[lay-id=test] .layui-laypage-em').next().text()},function(myresult){
	    			tabletoexcel(ins1.config.cols, myresult.resultset, ''
	    					, ins1.config.title
	    					,['patient_no','group_id','patient_id','patient_name',
	    			    		'sex','age','admission_datetime','discharge_datetime','ts',
	    			    		'attending_doctor','dept_name','out_diagnosis','oper_message',
	    			    		'drug_kind_num','kjy_kind_num','all_money','kjy_money','is_lhyy'],
	    			    		[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17],true)
	    		},null,{timeout:0,async:true})
	    		/*$.ajax({
	    	        url:"/w/hospital_common/report/kjy/querykjymj.json",
	    	        type:"post",
	    	        dataType:"json",
	    	        async:true,
	    	        timeout:0,
	    	        data:{'json':JSON.stringify(params),limit:-1},
	    	        success:function(myresult){
	    	        	tabletoexcel(ins1.config.cols, myresult.resultset, ''
    					, ins1.config.title
    					,['patient_no','group_id','patient_id','patient_name',
    			    		'sex','age','admission_datetime','discharge_datetime','ts',
    			    		'attending_doctor','dept_name','out_diagnosis','oper_message',
    			    		'drug_kind_num','kjy_kind_num','all_money','kjy_money','is_lhyy'],
    			    		[0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17],true)
	    	        }
	    	    });*/
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

function query_orders(patient_id,visit_id,group_id){
	$.modal('/w/hospital_common/report/queryhis/his_in_orders_list.html?patient_id=' + patient_id + 
			'&&visit_id=' + visit_id + '&&group_id='+group_id, "查看医嘱", {
		width : "100%",
		height : "100%",
		callback : function(e) {
		}
	});
}


</script>
