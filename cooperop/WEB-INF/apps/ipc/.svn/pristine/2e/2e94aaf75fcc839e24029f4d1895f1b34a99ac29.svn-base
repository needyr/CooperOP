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
	<input type="text" name="search" class="search" autocomplete="off"/>
	<button type="button" onclick="search1();" class="btn btn-default">查询</button>
	<button type="button" onclick="addAll();" class="btn btn-default">保存</button>
	<label ><em>一共：${param.num} 例 </em><em> 剩余：</em><em id="syu">${param.num}</em></label>
	<div class="assign_type_div">
	<label><input name="assign_type" type="radio" value="1" checked="checked"/>数量 </label>
	<!-- <label><input name="assign_type" type="radio" value="2" />百分比 </label> -->
	<label><input name="assign_type" type="radio" value="3"/>平均</label>
	</div>
</div>
<div class="left">
	<div class="thead_Div" id="main_thead">
		<table class="mytable">
			<thead>
				<tr>
					<th width="45%">编号</th>
					<th width="45%">姓名</th>
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
<!-- <div class="center">
<div class="fa fa-backward" id="center_1"></div>
<div class="fa fa-forward" id="center_2"></div>
</div> -->
<div class="right">
	<div class="thead_Div" id="main_thead">
		<table class="mytable" id="choose">
			<thead>
				<tr>
					<th width="45%">编号</th>
					<th width="45%">姓名</th>
					<th width="10%">数量</th>
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
if('${assign_users}'){
	var v = eval('('+'${assign_users}'+')');
	var type = '${type}';
	if(type){
		$('[name=assign_type][value='+type+']').attr('checked','checked')
	}
	var html = [];
	for(i=0;i<v.length;i++){
		var data ='[data_id='+'"'+v[i].no+'"'+']';
		if($(data).html()){
			html.push('<tr style="background:#b9b7b7" data_id="'+v[i].no+'" data_user="'+v[i].comment_user+'">');
		}else{
			html.push('<tr class="is_c'+v[i].no+'" data_id="'+v[i].no+'" data_user="'+v[i].comment_user+'">');
		}
		html.push('<td width="45%">'+v[i].no+'</td>');
		html.push('<td width="45%">'+v[i].name+'</td>');
		if($('[name=assign_type]:checked').val() != '3'){
			html.push('<td width="10%"><input autocomplete="off" name="count" type="text" style="width:100%" value="'+v[i].num+'"/></td>')
		}else{
			html.push('<td width="10%"><input autocomplete="off" name="count" type="text" style="width:100%" value="'+v[i].num+'" disabled="disabled"/></td>')
		}
		html.push('</tr>');
	}
	$("#start_choose tbody").append(html.join(''));
	$('input[name="count"]').click(function(){event.stopPropagation();})
	$('#start_choose tbody tr').click(function(){
		var data ='[data_id='+$(this).attr('data_id')+']';
		$(this).remove();
		$(data).attr('class','is_c');
		$(data).css('background','');
		if($('[name=assign_type]:checked').val() == '3'){
			var pp = average();
		}
		residue();
	});
	$("input[name=count]").bind("input propertychange",function(){
		residue();
	})
	residue()
}
search1();
$('[name=assign_type]').change(function(){
	var value = this.value;
	if(value == '1'){
		$('#choose tr th:eq(2)').text('数量');
		$('input[name="count"]').removeAttr('disabled');
		residue()
	}else if(value == '2'){
		$('#choose tr th:eq(2)').text('%');
		$('input[name="count"]').removeAttr('disabled');
		residue()
	}else if(value == '3'){
		$('input[name="count"]').attr('disabled','disabled');
		$('#syu').text(0);
		$('#choose tr th:eq(2)').text('数量');
		 average_demo()
	}
})
});

function average_demo(){
	var num = '${param.num}';
	var pr = 0;
	$('#start_choose tbody tr').each(function(e){
		pr = pr+1;
	})
	if(num >= pr){
		var y = num%pr;
		var pj = (num - y) / pr;
		if(y == 0){
			$('#start_choose tbody tr td [name="count"]').val(pj);
		}else{
			$('#start_choose tbody tr td [name="count"]').val(pj);
			$('#start_choose tbody tr td [name="count"]').each(function(index){
				if(index < y){
					$(this).val(parseInt($(this).val())+1);
				}
			})
		}
	}else{
		$('#start_choose tbody tr:last').remove();
		$('#start_choose tbody').empty();
		$('#showdrug_table tbody').find("tr").css('background','');
		$('#showdrug_table tbody').find("tr").attr('class','is_c');
		$('#syu').text('${param.num}');
		layer.msg('人数过多抽样不够分配,请减少分配人数!')
	}
}

function syncMove(theadId, tbodyId){
	try{
	//表格表头跟随内容滑动
	document.getElementById(tbodyId).onscroll=function(e) {
		document.getElementById(theadId).scrollLeft = document.getElementById(tbodyId).scrollLeft;
	}}catch(err){
		console.log("onscroll is miss ... ");
	}
}

//计算平均分配的例数
function average(){
	$('#choose tr th:eq(2)').text('数量');
	var num = parseInt('${param.num}');
	var pr = 0;
	//$('#start_choose tbody tr td [name="count"]').val('');
	$('#start_choose tbody tr').each(function(e){
		pr = pr+1;
	})
	if(num >= pr){
		var y = num%pr;
		var pj = (num - y) / pr;
		if(y == 0){
			$('#start_choose tbody tr td [name="count"]').val(pj);
		}else{
			$('#start_choose tbody tr td [name="count"]').val(pj);
			$('#start_choose tbody tr td [name="count"]').each(function(index){
				if(index < y){
					$(this).val(parseInt($(this).val())+1);
				}
			})
		}
		return 1;
	}else{
		$('#start_choose tbody tr:last').remove();
		layer.msg('人数过多抽样不够分配,请减少分配人数!')
		return 0;
	}
}

function search1(){
	var data = $('.search').val();
	$("#showdrug_table tbody").empty();
	$.call("ipc.sample.querysystem",{filter: data},function(rtn){
		if(rtn.resultset){
			var html = [];
			var v = rtn.resultset;
			for(i=0;i<v.length;i++){
				var data ='[data_id='+'"'+v[i].no+'"'+']';
				if($(data).html()){
					html.push('<tr style="background:#b9b7b7" data_id="'+v[i].no+'" data_user="'+v[i].comment_user+'">');
				}else{
					html.push('<tr class="is_c'+v[i].no+'" data_id="'+v[i].no+'" data_user="'+v[i].comment_user+'">');
				}
				html.push('<td width="45%">'+v[i].no+'</td>');
				html.push('<td width="45%">'+v[i].name+'</td>');
				html.push('</tr>');
			}
			$("#showdrug_table tbody").append(html.join(''));
			var code = [];
			code = ('${param.code}').split(',');
			if(code != ''){
				for(j=0;j<code.length;j++){
					var data ='[data_id='+'"'+code[j]+'"'+']';
					$(data).attr('class','');
				}
			}
			showdrug_table();
		}
	});
}

showdrug_table();

function showdrug_table(){
	$('#showdrug_table tbody tr').click(function(){
		var is_c = $(this).attr('class');
		var check = 1;
		if(is_c){
			if($('[name=assign_type]:checked').val() != '3'){
				$('#start_choose tbody').append($(this).clone().append('<td width="10%"><input autocomplete="off" name="count" type="text" style="width:100%"/></td>'));
			}else{
				$('#start_choose tbody').append($(this).clone().append('<td width="10%"><input autocomplete="off" name="count" type="text" style="width:100%" disabled="disabled"/></td>'));
				check = average();
			}
			if(check == 1){
				$('input[name="count"]').click(function(){event.stopPropagation();})
				$('#start_choose tbody tr').click(function(){
					var data ='[data_id='+$(this).attr('data_id')+']';
					$(this).remove();
					$(data).attr('class','is_c');
					$(data).css('background','');
					if($('[name=assign_type]:checked').val() == '3'){
						var pp = average();
					}
					residue();
				});
				$("input[name=count]").bind("input propertychange",function(){
					var reg=/^\d+$/;
					if(reg.test($(this).val())==false){
						$(this).val('');
					}
					residue();
				})
				$(this).attr('class','');
				$(this).css('background','#b9b7b7');
			}
			}
	});
}

function residue(){
	var all_num = 0;
	$('#start_choose tbody tr').each(function(e){
		var num = $(this).find('td').eq(2).children().val();
		if(num){
			all_num = all_num + parseInt(num);
		}
	})
	var result = parseInt('${param.num}') - parseInt(all_num);
	if(0<=result){
		$('#syu').text(result);
	}else{
		$('#syu').text('错误');
	}
}

function addAll(){
	var type = $('[name=assign_type]:checked').val();
	var all = {};
	var all_users_id = '';
	var list = [];
	var all_num = 0;
	all.type = type;
	$('#start_choose tbody tr').each(function(e){
		var code = $(this).find('td').eq(0).text();
		var comment_user= $(this).attr('data_user');
		var name = $(this).find('td').eq(1).text();
		var num = $(this).find('td').eq(2).children().val();
		if(num != 0 && num){
			all_num = parseInt(all_num) + parseInt(num);
			if(e == 0){
				all_users_id = all_users_id + comment_user 
			}else{
				all_users_id = all_users_id + "," + comment_user
			}
			list.push({no:code,comment_user:comment_user,name:name,num:num});
		}
	})
	all.person = list;
	if(type=='1' && all_num == '${param.num}'){
		//console.log(eval("("+$.toJSON(all)+")"))
		$.call("ipc.sample.updateSample",{id:'${param.id}',comment_user_mx:JSON.stringify(all),comment_user:all_users_id,assign_type:type},function(e){
			$.closeModal(true);
		})
	}else if(type=='2' && all_num == 100){
		$.call("ipc.sample.updateSample",{id:'${param.id}',comment_user_mx:JSON.stringify(all),comment_user:all_users_id,assign_type:type},function(e){
			$.closeModal(true);
		})
	}else if(type=='3'){
		$.call("ipc.sample.updateSample",{id:'${param.id}',comment_user_mx:JSON.stringify(all),comment_user:all_users_id,assign_type:type},function(e){
			$.closeModal(true);
		})
	}else{
		layer.msg('请填写正确分配数量/百分比!');
	}
	
}
</script>