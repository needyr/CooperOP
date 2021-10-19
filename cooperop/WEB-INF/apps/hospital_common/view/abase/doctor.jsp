<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/hospital_common/css/choose.css" >
<style>
.checked {
	background-color: silver !important;
}
.mytable:hover {
    cursor: default;
}

i:hover {
	cursor: pointer;
	color: #22c2ff;
}
</style>
<s:page title="选择药品" disloggedin="false">
<s:row>
<div class="top">
	<input type="text" name="search" placeholder="请输入检索内容" class="search"/>
	<input type="text" name="dept_search" placeholder="所在科室名称或编码" class="search"/>
	<button type="button" onclick="search1();" class="btn btn-default">查询</button>
	<button type="button" onclick="addAll();" class="btn btn-default">保存</button>
</div>
<div class="left">
	<div class="thead_Div" id="main_thead">
		<table class="mytable">
			<thead>
				<tr>
					<th width=20px><i class="glyphicon glyphicon-plus add_all" data-table="showdrug_table"></i></th>
					<th width=100px>医生编号</th>
					<th width=100px>医生名称</th>
					<th width=100px>所在科室</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div class="tbody_Div_info" id="main_tbody">
		<table class="mytable" id="showdrug_table">
			<tbody>
				
			</tbody>
		</table>
	</div>
</div>
<div class="right">
	<div class="thead_Div" id="main_thead">
		<table class="mytable">
			<thead>
				<tr>
					<th width=20px><i class="glyphicon glyphicon-minus remove_all" data-table="start_choose"></i></th>
					<th width=100px>医生编号</th>
					<th width=100px>医生名称</th>
					<th width=100px>所在科室</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div class="tbody_Div" id="main_tbody">
		<table class="mytable" id="start_choose">
			<tbody>
				
			</tbody>
		</table>
	</div>
</div>
</s:row>
</s:page>
<script>
$(function(){
syncMove('main_thead', 'main_tbody');
if('${param.code}'){
	var code = '${param.code}';
	$.call("hospital_common.user.searchCheck",{code: code.split(','),is_permission:('${param.is_permission}'?'${param.is_permission}':'')},function(rtn){
		if(rtn.doctor){
			var html = [];
			var v = rtn.doctor;
			for(i=0;i<v.length;i++){
				html.push('<tr class="is_c'+v[i].no+'" data_id="'+v[i].no+'">');
				html.push('<td width=20px><i class="glyphicon glyphicon-minus remove_one" i_data_id="'+v[i].no+'"></i></td>');
				html.push('<td width=100px>'+v[i].no+'</td>');
				html.push('<td width=100px>'+v[i].name+'</td>');
				html.push('<td width=100px>'+v[i].department_name+'</td>');
				html.push('</tr>');
			}
			$("#start_choose tbody").append(html.join(''));
			$('#start_choose tbody tr .remove_one').click(function(){
				var data ='[data_id="'+$(this).attr('i_data_id').replace( /(:|\.|\[|\]|,)/g, "\\$1" )+'"]';
				$(this).parent().parent().remove();
				$(data).attr('class','is_c');
			});
			$('#start_choose tbody tr').dblclick(function(){
				$(this).find('i').click();
			})
		}
	});
}
});

function syncMove(theadId, tbodyId){
	try{
	//表格表头跟随内容滑动
	document.getElementById(tbodyId).onscroll=function(e) {
		document.getElementById(theadId).scrollLeft = document.getElementById(tbodyId).scrollLeft;
	}}catch(err){
		console.log("onscroll is miss ... ");
	}
}


function search1(){
	var data = $('.search').val();
	var dept = $('input[name=dept_search]').val();
	$("#showdrug_table tbody").empty();
	$.call("hospital_common.user.search",{data: data,dept:dept,is_permission:('${param.is_permission}'?'${param.is_permission}':'')},function(rtn){
		if(rtn.doctor){
			var html = [];
			var v = rtn.doctor;
			for(i=0;i<v.length;i++){
				html.push('<tr class="is_c'+v[i].no+'" data_id="'+v[i].no+'">');
				html.push('<td width=20px><i class="glyphicon glyphicon-plus add_one" i_data_id="'+v[i].no+'"></i></td>');
				html.push('<td width=100px>'+v[i].no+'</td>');
				html.push('<td width=100px>'+v[i].name+'</td>');
				html.push('<td width=100px>'+v[i].department_name+'</td>');
				html.push('</tr>');
			}
			$("#showdrug_table tbody").append(html.join(''));
			var code = [];
			$('#start_choose tbody tr').each(function(e){
				var data ='[data_id='+'"'+$(this).find('td').eq(0).text()+'"'+']';
				$(data).attr('class','');
			})
			$('#showdrug_table tbody tr .add_one').click(function(){
				var is_c = $(this).parent().parent().attr('class');
				if(is_c.indexOf('is_c') != -1){
					var hhh = $(this).parent().parent().clone();
					hhh.children().children('i').attr('class','glyphicon glyphicon-minus remove_one');
					$('#start_choose tbody').append(hhh);
					$('#start_choose tbody tr .remove_one').click(function(){
						var data ='[data_id="'+$(this).attr('i_data_id').replace( /(:|\.|\[|\]|,)/g, "\\$1" )+'"]';
						$(this).parent().parent().remove();
						$(data).attr('class','is_c');
					});
					$('#start_choose tbody tr').dblclick(function(){
						$(this).find('i').click();
					})
					$(this).parent().parent().attr('class','checked');
				}
			})
			$('#showdrug_table tbody tr').dblclick(function(){
				$(this).find('i').click();
			})
		}
	});
}

function addAll(){
	var all_code = [];
	var all_name = [];
	$('#start_choose tbody tr').each(function(e){
		all_code.push($(this).find('td').eq(1).text());
		all_name.push($(this).find('td').eq(2).text());
	})
	$.closeModal({"code":all_code,"name":all_name});
}

$('.add_all').click(function(){
	var table_name = $(this).attr("data-table");
	var _table = $('#'+table_name+" tbody tr[class!=checked]").clone();
	_table.find('i').attr('class','glyphicon glyphicon-minus remove_one');
	$('#start_choose tbody').append(_table);
	$('#'+table_name+" tbody tr").attr('class','checked');
	$('#start_choose tbody tr .remove_one').click(function(){
		var data ='[data_id="'+$(this).attr('i_data_id').replace( /(:|\.|\[|\]|,)/g, "\\$1" )+'"]';
		$(this).parent().parent().remove();
		$(data).attr('class','is_c');
	});
})

$('.remove_all').click(function(){
	var table_name = $(this).attr("data-table");
	$('#'+table_name+" tbody").empty();
	$("#showdrug_table tbody tr").attr('class','is_c');
})
</script>