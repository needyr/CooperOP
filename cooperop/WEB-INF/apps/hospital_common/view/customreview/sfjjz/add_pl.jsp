<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<link href="${pageContext.request.contextPath}/res/hospital_common/css/sfjjz.css" rel="stylesheet" type="text/css">
<s:page title="编辑配伍问题" ismodal="true">
<div class="left_top">
	<input type="text" name="search" class="search" autocomplete="off"/>
	<button type="button" onclick="search_item()" class="btn btn-default">查询</button>
	<button type="button" onclick="save_all();" class="btn btn-default">保存</button>
</div>
<div class="left-bottom">
       <div id="tree_own" class="">
       </div>
</div>
<div class="right-top">
	<input type="text" name="search2" class="search" autocomplete="off"/>
	<button type="button" onclick="search_child();" class="btn btn-default">查询</button>
	<button type="button" onclick="clean_all();" class="btn-clean btn btn-default">清空</button>
</div>
<div class="right-bottom">

</div>
<div class="bottom">

</div>
</s:page>
<script>
var click_code;//查看保存的全局诊断code
var _code = [];//所有的三级诊断code
function reflashTree(){
	$('#tree_2').jstree(true).settings.core.data = getTreeData();
    $('#tree_2').jstree(true).refresh();
}

//清空右边
function clean_all(){
	$('.bottom').empty();
	_code = [];
	$('input').prop('checked', false);
	$('ul').css('background-color', '');
}

//保存所有
function save_all(){
		var set = new Set();
		var duib_set = new Set();
		$('.com_span').each(function(){
			var code = $(this).attr('data-del');
			duib_set.add(code);
		});
		$('.com_span').each(function(){
			var _this = $(this);
			var code = _this.attr('data-del');
			var parent_code = _this.attr('data-parent');
			var count_old = duib_set.size;
			duib_set.add(parent_code);
			var count_new = duib_set.size;
			if(count_old != count_new){
				duib_set.delete(parent_code);
				set.add(code)
			}
		});
		$.call("hospital_common.customreview.sfjjz.saveAll",{codes: Array.from(set),parent_id: '${param.parent_id}'},function(rtn){
			$.closeModal();
		});
}

//清除所有勾选
function deleteQuestion(_this){
	var del_code = $(_this).attr('data_id');
	_code.splice($.inArray(del_code,_code),1);//删除数组元素
	$(_this).parent().remove();
	//删除选择中的诊断
	$('input[id="'+del_code+'"]').prop('checked', false);
	$('input[id="'+del_code+'"]').parent().parent().parent().css('background-color', '');
}

$(document).ready(function() {
	
});

function search_item(){
	getTreeData();
}

//查找第三级菜单的内容
function search_child(){
	var html = [];
	var code = click_code;
	var search = $('[name="search2"]').val();
	 $(".right-bottom").empty();
	 $.call("hospital_common.customreview.sfjjz.queryDiagnisis",{code:code,search:search},function(rtn){
		 for(i=0;i<rtn.mx.length;i++){
			html.push('<div class="first_box_main" >');
			html.push('<div class="first_box">');
			if(_code.contains(rtn.mx[i].diagnosis_code) == true){
				html.push('<input checked="checked" type="checkbox" name="mulu" data-name="'+rtn.mx[i].diagnosis_name+'" data-parent="'+click_code+'" id="'+rtn.mx[i].diagnosis_code+'" value="'+rtn.mx[i].diagnosis_code+'" class="first_mulu" >');
			}else{
				html.push('<input type="checkbox" name="mulu" data-name="'+rtn.mx[i].diagnosis_name+'" data-parent="'+click_code+'" id="'+rtn.mx[i].diagnosis_code+'" value="'+rtn.mx[i].diagnosis_code+'" class="first_mulu" >');
			}
			html.push('<label title="'+rtn.mx[i].diagnosis_name+'">'+rtn.mx[i].diagnosis_name+'</label>');
			html.push('</div>');
			html.push('</div>');
		 }
		 $(".right-bottom").append(html.join(''));
		 $('[name="mulu"]').change(function(){
			 if($(this).attr('checked') == "checked"){
				if(_code.contains($(this).val()) == false){
					var name = $(this).attr("data-name");
					var code = $(this).val();
					$('.bottom').append('<div title="'+name+'" data-parent="'+click_code+'" data-del="'+code+'"  class="com_span" ><span class="com_span_child">'+name+'</span><a data_id="'+code+'" onclick="deleteQuestion(this);" class="glyphicon glyphicon-remove glyphicon-remove-s"></a></div> ');
					_code.push(code);
				}
			 }else{
				var del_code = $(this).val();
				_code.splice($.inArray(del_code,_code),1);//删除数组元素
				$('div[data-del="'+del_code+'"]').remove();
			}
		 });
		 //div绑定checkbox点击
		 $(document).on('click', ':checkbox', function (e){
			 e.stopPropagation();
			 $(this).click();
		 });
		 $('.first_box_main').click(function () {
	            $(this).find(':checkbox').click();
	     })
	 })
}

//查找树状菜单
function getTreeData() {
	var r = [];
	var search = $('[name="search"]').val();
	//菜单
	$.call("hospital_common.customreview.sfjjz.queryItem", {search: search}, function(rtn) {
		var parent = rtn.parent;
        var child = rtn.child;
        $("#tree_own").empty();
        var html = [];
        var html_orther = [];
        
        for (var i = 0; i < parent.length; i++) {
        	if(_code.contains(parent[i].diagnosis_code) == true){
	       	    html.push('<ul class="first_ul" style="background-color: rgb(209, 209, 209);">');
        	}else{
        		html.push('<ul class="first_ul">');
        	}
       	    html.push('<li ><div data-code="'+parent[i].diagnosis_code+'" class="content_a first_div" title="'+parent[i].diagnosis_name+'">');
       	    html.push('<i style="font-size: 20px;position: relative;top: 3px;color: #2553d8;"></i>');
       	    if(_code.contains(parent[i].diagnosis_code) == true){
       	    	html.push('<input type="checkbox" checked="checked" name="tree_checkbox" id="'+parent[i].diagnosis_code+'" data-name="'+parent[i].diagnosis_name+'" value="'+parent[i].diagnosis_code+'" class="tree_checkbox">');
       	    }else{
	       	    html.push('<input type="checkbox" name="tree_checkbox" data-name="'+parent[i].diagnosis_name+'" id="'+parent[i].diagnosis_code+'" value="'+parent[i].diagnosis_code+'" class="tree_checkbox">');
       	    }
       	    html.push('<span class="content_span">'+parent[i].diagnosis_name+'</span></div>');
	       	 for (var j = 0; j < child.length; j++) {
	     		if (child[j].parent_code.substring(0, 5) == parent[i].diagnosis_code.substring(0, 5)) {
	     			if(_code.contains(child[j].diagnosis_code) == true){
		     			html.push('<ul class="se_item_block" style="background-color: rgb(209, 209, 209);">');
	     			}else{
	     				html.push('<ul class="se_item">');
	     			}
	        	    html.push('<li ><div data-parent="'+parent[i].diagnosis_code+'" data-code="'+child[j].diagnosis_code+'" class="content_a" title="'+parent[i].diagnosis_name+'">');
	        	    if(_code.contains(child[j].diagnosis_code) == true){
		        	    html.push('<input type="checkbox" data-parent="'+parent[i].diagnosis_code+'" checked="checked" name="tree_checkbox" id="'+child[j].diagnosis_code+'" data-name="'+child[j].diagnosis_name+'" value="'+child[j].diagnosis_code+'" class="tree_checkbox">');
	        	    }else{
	        	    	html.push('<input type="checkbox" data-parent="'+parent[i].diagnosis_code+'" name="tree_checkbox" id="'+child[j].diagnosis_code+'" data-name="'+child[j].diagnosis_name+'" value="'+child[j].diagnosis_code+'" class="tree_checkbox">');
	        	    }
	        	    html.push('<span class="content_span">'+child[j].diagnosis_name+'</span></div>');
	        	    html.push('</li>');
	        	    html.push('</ul>');
	     		}
	     	}
       	    html.push('</li>');
       	    html.push('</ul>');
        }
       
        
      $.call("hospital_common.customreview.sfjjz.queryOrther", {search: search}, function(rtn) {
    	  if(rtn.length > 0){
    		html_orther.push('<ul class="first_ul">');
		  	html_orther.push('<li ><div class="content_a first_div">');
		  	html_orther.push('<i style="font-size: 20px;position: relative;top: 3px;color: #2553d8;"></i>');
		  	html_orther.push('<span class="content_span" style="font-size: 14px;">其他诊断</span></div>');
		  	for (var i = 0; i < rtn.length; i++) {
		  		if(_code.contains(rtn[i].diagnosis_code) == true){
		  			html_orther.push('<ul class="se_item_block" style="background-color: rgb(209, 209, 209);">');
				}else{
					html_orther.push('<ul class="se_item">');
				}
		  		html_orther.push('<li ><div data-code="'+rtn[i].diagnosis_code+'" class="content_a" title="'+rtn[i].diagnosis_name+'">');
		  		if(_code.contains(rtn[i].diagnosis_code) == true){
		  			html_orther.push('<input type="checkbox" checked="checked" name="tree_checkbox" id="'+rtn[i].diagnosis_code+'" data-name="'+rtn[i].diagnosis_name+'" value="'+rtn[i].diagnosis_code+'" class="tree_checkbox">');
		  	    }else{
		  	    	html_orther.push('<input type="checkbox" name="tree_checkbox" id="'+rtn[i].diagnosis_code+'" data-name="'+rtn[i].diagnosis_name+'" value="'+rtn[i].diagnosis_code+'" class="tree_checkbox">');
		  	    }
		  		html_orther.push('<span class="content_span">'+rtn[i].diagnosis_name+'</span></div>');
		  		html_orther.push('</li>');
		  		html_orther.push('</ul>');
		  	}
		  	html_orther.push('</li>');
		  	html_orther.push('</ul>');  
	       	$("#tree_own").append(html_orther.join(''));
    	  }
        }, null, {
			async : false
		})
    	$("#tree_own").append(html.join(''));
        
        $('.se_item_block').parent().children('div').children('i').toggleClass('fa fa-minus-square-o')
        $('.se_item').parent().children('div').children('i').toggleClass('fa fa-plus-square-o')
        //+-展开关闭
       $("i").click(function(){
        	event.stopPropagation();
        	//$(this).parent().parent().children("ul:first").slideToggle(200);
        	if($(this).attr('class') == "fa fa-plus-square-o"){
        		$(this).attr('class','fa fa-minus-square-o')
        		$(this).parent().parent().children(".se_item").addClass('se_item_block');
        		$(this).parent().parent().children(".se_item").removeClass('se_item');
        	}else{
        		$(this).attr('class','fa fa-plus-square-o')
        		$(this).parent().parent().children(".se_item_block").addClass('se_item');
        		$(this).parent().parent().children(".se_item_block").removeClass('se_item_block');
        	}
        });
        $(".first_div").click(function(){
        	//$(this).parent().children("ul:first").slideToggle(200);
        	if($(this).find("i").attr('class') == "fa fa-plus-square-o"){
        		$(this).find("i").attr('class','fa fa-minus-square-o')
        		$(this).parent().children(".se_item").addClass('se_item_block');
        		$(this).parent().children(".se_item").removeClass('se_item');
        	}else{
        		$(this).find("i").attr('class','fa fa-plus-square-o')
        		$(this).parent().children(".se_item_block").addClass('se_item');
        		$(this).parent().children(".se_item_block").removeClass('se_item_block');
        	}
        });
        $('input').click(function(){
        	event.stopPropagation();
        })
        $('.content_a').click(function(){
        	var is_first = $(this).attr('data-parent');
			 if(is_first){
				 var code = $(this).attr('data-code');
				 var html = [];
				 click_code = code;
				 $(".right-bottom").empty();
				 $.call("hospital_common.customreview.sfjjz.queryDiagnisis",{code:code},function(rtn){
					 for(i=0;i<rtn.mx.length;i++){
						html.push('<div class="first_box_main">');
						html.push('<div class="first_box">');
						if(_code.contains(rtn.mx[i].diagnosis_code) == true){
							html.push('<input checked="checked" type="checkbox" name="mulu" data-name="'+rtn.mx[i].diagnosis_name+'" data-parent="'+click_code+'" id="'+rtn.mx[i].diagnosis_code+'" value="'+rtn.mx[i].diagnosis_code+'" class="first_mulu" >');
						}else{
							html.push('<input type="checkbox" name="mulu" data-name="'+rtn.mx[i].diagnosis_name+'" data-parent="'+click_code+'" id="'+rtn.mx[i].diagnosis_code+'" value="'+rtn.mx[i].diagnosis_code+'" class="first_mulu" >');
						}
						html.push('<label title="'+rtn.mx[i].diagnosis_name+'">'+rtn.mx[i].diagnosis_name+'</label>');
						html.push('</div>');
						html.push('</div>');
					 }
					 $(".right-bottom").append(html.join(''));
					 $('[name="mulu"]').change(function(){
						 if($(this).attr('checked') == "checked"){
							if(_code.contains($(this).val()) == false){
								var name = $(this).attr("data-name");
								var code = $(this).val();
								$('.bottom').append('<div title="'+name+'" data-parent="'+click_code+'" data-del="'+code+'"  class="com_span" ><span class="com_span_child">'+name+'</span><a data_id="'+code+'" onclick="deleteQuestion(this);" class="glyphicon glyphicon-remove glyphicon-remove-s"></a></div> ');
								_code.push(code);
							}
						 }else{
							var del_code = $(this).val();
							_code.splice($.inArray(del_code,_code),1);//删除数组元素
							$('div[data-del="'+del_code+'"]').remove();
						}
					 });
					 $('.first_box_main').click(function () {
						 event.stopPropagation();
				         $(this).find(':checkbox').click();
				     })
					 $('[name="mulu"]').click(function () {
						 $(this).click();
				     })
				 }, null, {
						async : false
				});
				 
			 }
        });
        $('.tree_checkbox').change(function(){
        	if($(this).attr('checked') == "checked"){
				if(_code.contains($(this).val()) == false){
					var name = $(this).attr("data-name");
					var code = $(this).val();
					var parent = $(this).attr('data-parent');
					$('.bottom').append('<div title="'+name+'" data-parent="'+parent+'" data-del="'+code+'"  class="com_span" ><span class="com_span_child">'+name+'</span><a data_id="'+code+'" onclick="deleteQuestion(this);" class="glyphicon glyphicon-remove glyphicon-remove-s"></a></div> ');
					_code.push(code);
					$(this).parent().parent().parent().css('background-color','#d1d1d1');
				}
			 }else{
				var del_code = $(this).val();
				_code.splice($.inArray(del_code,_code),1);//删除数组元素
				$('div[data-del="'+del_code+'"]').remove();
				$(this).parent().parent().parent().css('background-color','');
			}
        });
	}, null, {
		async : false
	});
}

//判断数组中是否存在值em
Array.prototype.contains = 
function(em){
	for(var i=0;i<this.length;i++){
		if(this[i] == em){
			return true;
		}
	}
	return false;
}

//回车事件
$(document).keydown( function(e) {
    var key = window.event?e.keyCode:e.which;
    if(key.toString() == "13"){
    	getTreeData();
    	return false;
    }
});
</script>