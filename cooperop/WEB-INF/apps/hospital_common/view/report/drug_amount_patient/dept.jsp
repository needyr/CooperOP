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
<s:page title="科室药品使用例次汇总排名表">
<!-- <div class="top"> -->
<s:form id="form" collapsed="false" extendable="true">
	<s:row>
	<s:datefield label="时间" name="start_time" required="true" format="yyyy-MM-dd" autocomplete="off" value="" cols="1" ></s:datefield>
	<s:datefield label="至" name="end_time" required="true" format="yyyy-MM-dd" autocomplete="off" value="" cols="1" ></s:datefield>
	<s:checkbox label="抗菌药类型" cols="1" name="kjy_drug_type">
		<s:option value ="0" label="非抗菌药"></s:option>
		<s:option value ="1" label="抗细菌药"></s:option>
		<s:option value ="2" label="抗真菌药"></s:option>
		<s:option value ="3" label="抗结核药"></s:option>
		<s:option value ="4" label="其他抗菌药"></s:option>
	</s:checkbox>
	</s:row>

	<s:row>
	<s:textfield label="药品前几名" cols="1" number="true" name="drug_pm" required="true"></s:textfield>
	<s:textfield label="科室前几名" cols="1" number="true" name="dept_pm" required="true"></s:textfield>
	<s:checkbox label="抗菌药级别" cols="1" name="kjy_drug_level">
		<s:option value ="1" label="非限制级"></s:option>
		<s:option value ="2" label="限制级"></s:option>
		<s:option value ="3" label="特殊级"></s:option>
	</s:checkbox>
	</s:row>

	<s:row>
		<s:checkbox label="药品类型" cols="3" name="drug_type">
			<c:forEach var="ypfl" items="${ypfl}">
			  <s:option value ="${ypfl.drug_ypfl_code}" label="${ypfl.drug_ypfl_name}"></s:option>
		  	</c:forEach>
		</s:checkbox>
	</s:row>

	<s:row>
		<s:checkbox label="类型" cols="1" name="d_type">
			<s:option value ="1" label="住(出)院"></s:option>
			<s:option value ="2" label="门诊"></s:option>
			<s:option value ="3" label="急诊"></s:option>
		</s:checkbox>
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
	<s:row>
		<s:checkbox label="基本药物" cols="1" name="is_jbyw">
			<s:option value ="1" label="是"></s:option>
			<s:option value ="0" label="否"></s:option>
		</s:checkbox>
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
    <span style="font-size:14px">科室药品使用例次汇总排名表</span>
  	<button id="export" style="margin-left: 10px;" class="layui-btn layui-btn-primary layui-btn layui-btn-sm">导出结果清单</button>
  	<button class="layui-btn layui-btn-primary layui-btn layui-btn-sm" onclick="query1()">查询排名</button>
  </div>
</script>
<script type="text/javascript">
var cxtj;
var cols1 = [];
$(function(){
	$('[name=start_time]').val('${now_time}');
	$('[name=end_time]').val('${now_time}');
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
	if(real_re.length <= 0){
		cols1 = [[
		    {field:'rowid', title:'排名(例次)', width:100}
		    ,{field:'drug_name', title:'药品名称'}
		    ,{field:'jixing', title:'剂型'}
		    ,{field:'druggg', title:'规格'}
		    ,{field:'patient_count', title:'药品总例次'}
	    ]];
	}
	layui.use('table', function(){
	  var table = layui.table;
	  var ins1 = table.render({
	    elem: '#test'
	    //,url:'/w/hospital_common/report/common/query_dui.json'
	    ,toolbar: '#toolbarDemo'
	    ,title: '科室药品使用例次汇总排名表'
    	,defaultToolbar: ['filter']
	    //,totalRow: true
	    ,height: $(window).height() -
		150 - $("div[ctype='form']").parent().parent().height()
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

	$.call('hospital_common.report.drug_amount_patient.query_dept',data,function(rtn){
		var real_re = [];
		if(rtn.count>0){
			var res = rtn.resultset;
			for(var i=0;i<res.length;i++){
				var data_res = res[i];
				if(data_res.dept_rank){
					var dept_ranks = data_res.dept_rank.split(',');
					for(var j=1;j<=dept_ranks.length;j++){
						data_res['kz_'+j] = dept_ranks[j-1].split(':')[1];
					}
				}
				real_re.push(data_res);
				if(res.length - 1 == i){
					real_re.push({'rowid':'1.统计范围'});
					real_re.push({'rowid':'时间：'+cxtj.start_time+'~'+cxtj.end_time});
					real_re.push({'rowid':'类型：'+ (cxtj.d_type && cxtj.d_type.length >0?cxtj.d_type_name:'全部')});
					//real_re.push({'rowid':'排名方式：按' + (cxtj.pmfs == 'drug_money'?'金额':'数量')+'排名'});
					real_re.push({'rowid':'统计名次：药品前'+cxtj.drug_pm+'名,科室前'+cxtj.dept_pm+'名'});
					if(is_null("kjy_drug_type")){
						real_re.push({'rowid':'抗菌药类型：'+cxtj.kjy_drug_type_name});
					}
					if(is_null("kjy_drug_level")){
						real_re.push({'rowid':'抗菌药级别：'+cxtj.kjy_drug_level_name});
					}
					if(is_null("drug_type")){
						real_re.push({'rowid':'药品类型：'+cxtj.drug_type_name});
					}
					if(is_null("jixing")){
						real_re.push({'rowid':'剂型：'+cxtj.jixing_name});
					}
					if(cxtj.yllb_code){
						real_re.push({'rowid':'药理类别：'+cxtj.yllb_name});
					}
					if(is_null("is_jbyw")){
						real_re.push({'rowid':'基本药物：'+cxtj.is_jbyw_name});
					}
				}
			}
		}
		var data_cols1 = [];
		var data_cols2 = [];
		data_cols1.push({field:'rowid', title:'排名(例)', width:100, rowspan: 2});
		data_cols1.push({field:'drug_name', title:'药品名称', rowspan: 2});
		data_cols1.push({field:'jixing', title:'剂型', rowspan: 2});
		data_cols1.push({field:'druggg', title:'规格', rowspan: 2});
		data_cols1.push({field:'patient_count', title:'药品总例次', rowspan: 2});
		data_cols1.push({field:'', title:'科室排名', colspan: +cxtj.dept_pm, align:'center'});
		for(var i = 1;i <= +cxtj.dept_pm;i++){
			data_cols2.push({field:'kz_'+i, title:'科室'+i+'(例)'});
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

function clean_yllb_name(){
	$('[name=yllb_name]').val('');
	$('[name=yllb_code]').val('');
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
