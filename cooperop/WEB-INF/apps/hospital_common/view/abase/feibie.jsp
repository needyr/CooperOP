<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/res/ipc/css/choose.css" >
<s:page title="选择费别" disloggedin="false">
<s:row>
<div class="top">
	<input type="text" name="search" class="search"/>
	<button type="button" onclick="search1();" class="btn btn-default">查询</button>
	<button type="button" onclick="addAll();" class="btn btn-default">保存</button>
</div>
<div class="left">
	<div class="thead_Div" id="main_thead">
		<table class="mytable">
			<thead>
				<tr>
					<th width=100px>费别代码</th>
					<th width=100px>费别名称</th>
					<th width=100px>简称</th>
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
					<th width=100px>费别代码</th>
					<th width=100px>费别名称</th>
					<th width=100px>简称</th>
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
	$.call("hospital_common.dicthisfeibie.searchCheck",{code: code.split(',')},function(rtn){
		if(rtn.feibie){
			var html = [];
			var v = rtn.feibie;
			for(i=0;i<v.length;i++){
			html.push('<tr class="is_c'+v[i].feibie_code+'" data_id="'+v[i].feibie_code+'">');
			html.push('<td width=100px>'+v[i].feibie_code+'</td>');
			html.push('<td width=100px>'+v[i].feibie_name+'</td>');
			html.push('<td width=120px>'+v[i].input_code+'</td>');
			html.push('</tr>');
			}
			$("#start_choose tbody").append(html.join(''));
			$('#start_choose tbody tr').click(function(){
				var data ='[data_id="'+$(this).attr('data_id')+'"]';
				$(this).remove();
				$(data).attr('class','is_c');
			});
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
	$("#showdrug_table tbody").empty();
	$.call("hospital_common.dicthisfeibie.search",{data: data},function(rtn){
		if(rtn.feibie){
			var html = [];
			var v = rtn.feibie;
			for(i=0;i<v.length;i++){
				html.push('<tr class="is_c'+v[i].feibie_code+'" data_id="'+v[i].feibie_code+'">');
				html.push('<td width=100px>'+v[i].feibie_code+'</td>');
				html.push('<td width=100px>'+v[i].feibie_name+'</td>');
				html.push('<td width=120px>'+v[i].input_code+'</td>');
				html.push('</tr>');
			}
			$("#showdrug_table tbody").append(html.join(''));
			var code = [];
			$('#start_choose tbody tr').each(function(e){
				var data ='[data_id='+'"'+$(this).find('td').eq(0).text()+'"'+']';
				$(data).attr('class','');
			})
			$('#showdrug_table tbody tr').click(function(){
				var is_c = $(this).attr('class');
				if(is_c){
					$('#start_choose tbody').append($(this).clone());
					$('#start_choose tbody tr').click(function(){
						var data ='[data_id="'+$(this).attr('data_id')+'"]';
						$(this).remove();
						$(data).attr('class','is_c');
					});
					$(this).attr('class','');
				}
			});
		}
	});
}

$('#showdrug_table tbody tr').click(function(){
	var is_c = $(this).attr('class');
	if(is_c){
		$('#start_choose tbody').append($(this).clone());
		$('#start_choose tbody tr').click(function(){
			var data ='[data_id="'+$(this).attr('data_id')+'"]';
			$(this).remove();
			$(data).attr('class','is_c');
		});
		$(this).attr('class','');
	}
});

function addAll(){
	var all_code = [];
	var all_name = [];
	$('#start_choose tbody tr').each(function(e){
		all_code.push($(this).find('td').eq(0).text());
		all_name.push($(this).find('td').eq(1).text());
	})
	$.closeModal({"code":all_code,"name":all_name});
}

</script>