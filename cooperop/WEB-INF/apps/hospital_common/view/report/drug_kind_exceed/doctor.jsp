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
<s:page title="医生门(急)诊处方药品品种超过N种清单">
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
		</s:checkbox>
		<s:radio label="统计对象" cols="2" name="obj" value="1">
			<s:option value ="1" label="药品费用大于零的病人"></s:option>
			<s:option value ="2" label="药品(排除中药饮片)费用大于零的病人"></s:option>
		</s:radio>
	</s:row>

	<s:row>
		<s:textfield cols="2" label="用药品种数≥" name="range" number="true" required="true"></s:textfield>
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
    <span style="font-size:14px">医生门(急)诊处方药品品种超过N种清单</span>
  	<button id="export" style="margin-left: 10px;" class="layui-btn layui-btn-primary layui-btn layui-btn-sm">导出结果清单</button>
  	<button class="layui-btn layui-btn-primary layui-btn layui-btn-sm" onclick="query1()">查询排名</button>
  </div>
</script>
<script type="text/javascript">
var cxtj = {};
var cols1;
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
	cols1 = [[
	    {field:'attending_doctor', title:'医生'}
	    ,{field:'doctor_dept', title:'医生科室'}
	    ,{field:'zong_count', title:'医生处方总数(张)',sort:true}
	    ,{field:'range_count', title:'医生药品金额超过'+(cxtj.range?cxtj.range:0)+'元的处方数量(张)',sort:true}
	    ,{field:'range_count_zong_count', title:'占医生处方总数比例(%)',sort:true,templet: function(record){
	    	  var val = (Number(record.range_count)/Number(record.zong_count)*100).toFixed(2);
	    	  return isNaN(val)?'':val;
	    }}
    ]];
	layui.use('table', function(){
	  var table = layui.table;
	  var ins1 = table.render({
	    elem: '#test'
	    //,url:'/w/hospital_common/report/common/query_dui.json'
	    ,toolbar: '#toolbarDemo'
	    ,title: '医生门(急)诊处方药品品种超过N种清单'
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
	cxtj = data;
	get_fifter_name("d_type");

	if(data.d_type){
		data.d_type = data.d_type.join(',');
	}
	$.call('hospital_common.report.drug_kind_exceed.query_doctor',data,function(rtn){
		var real_re = [];
		if(rtn.count>0){
			var range_zong = 0;
			var res = rtn.resultset;
			for(var i=0;i<res.length;i++){
				res[i].rowid = i + 1;
				range_zong = range_zong + Number(res[i].range_count);
				real_re.push(res[i]);
				if(res.length - 1 == i){
					real_re.push({'dept_name':'1.统计范围'});
					real_re.push({'dept_name':'时间：'+cxtj.start_time+'~'+cxtj.end_time});
					real_re.push({'dept_name':'类型：'+ (cxtj.d_type && cxtj.d_type.length >0?cxtj.d_type_name:'全部')});
					real_re.push({'dept_name':'统计对象：'+ (cxtj.obj == "1" ?'药品费用大于零的病人':cxtj.obj == '2'?'药品(排除中药饮片)费用大于零的病人':'全部' )});
					real_re.push({'dept_name':'用药品种数：≥'+ cxtj.range + '种的处方总数：'+range_zong+'张'});
				}
			}
		}
		create_table1(real_re);
	},null,{timeout:0})
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
