<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<style>
.box{
	margin-top: 2px;
    margin-left: 6px;
    border-top: 1px solid #cccccc;
    float: left;
    width: 30%;
}
.check_box{
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	margin-left: 5px;
}
.page-content {
    padding: 0px 0px 0px 0px !important;
}

.tree_checkbox{
	height: 15px;
    width: 15px;
    vertical-align: middle;
    margin-bottom: 3px !important;
}

ul li{
list-style-type:none;
}

ol, ul {
    margin-bottom: 2px !important;
}

.se_item{
	display:none;
}

.se_item_block{
	display: block;
}

.first_ul{
	padding-inline-start: 10px;
}

.content_span{
	margin-left: 5px;
	color: #3b3838;
}
.content_a:hover{
	cursor:pointer;
	background-color: #f1f1f1;
}
</style>
<s:page title="添加">
<div style="height: 130px;">
<div class="cols3" style="margin-left: 6px;">
<label class="control-label">问题描述及处理意见</label>
<input type="button" value="保存" onclick="save();" class="btn btn-sm btn-sm btn-default " style="float: right;margin-left: 5px;margin-right: 5px;">
<input type="button" value="取消" onclick="cenel();" class="btn btn-sm btn-sm btn-default " style="float: right;margin-left: 5px;margin-right: 5px;">
<div class="control-content">
<textarea style="height: 100px;" class="form-control" readonly="readonly" id="comment_content"  data-autosize-on="true"></textarea>
</div>
</div>
</div>

<div id="bot" style="overflow: auto;border-bottom: 1px #c9c9c9 solid;">
<c:forEach items="${comment}" var="co">
<c:if test="${co.level eq 2}">
<ul class="first_ul">
	<li>
	<div class="content_a first_div">
	<i style="font-size: 20px;position: relative;top: 3px;color: #2553d8;" class="fa fa-plus-square-o"></i>
    <span class="content_span" style="font-size: 14px;">${co.comment_name}</span>
	</div>
	<c:forEach items="${comment}" var="a">
		<c:if test="${fn:indexOf(a.system_code.concat('-'), co.system_code) > -1 and a.level eq 3 }">
		<ul class="se_item">
		<li>
		<div class="content_a">
        <i style="font-size: 20px;position: relative;top: 3px;color: #2553d8;" class="fa fa-plus-square-o"></i>
        <span class="content_span" style="font-size: 14px;">${a.comment_name}</span>
        </div>
        <c:forEach items="${comment}" var="b">
        <c:if test="${fn:indexOf(b.system_code.concat('-'), a.system_code) > -1 and b.level eq 4 }">
        	<ul class="se_item">
                 <li>
                 <div class="content_a s_div" title="${b.comment_name}">
                 <input type="checkbox" name="tree_checkbox" class="tree_checkbox"
                 	value="${b.system_code}" data-id="${b.system_code}" data-name="${b.comment_name}">
                 <span class="content_span">${b.comment_name}</span>
                 </div>
                 </li>
             </ul>
        </c:if>
        </c:forEach>
		</li>
		</ul>
		</c:if>
	</c:forEach>
	</li>
</ul>
</c:if>
</c:forEach>

</div>

</s:page>
<script type="text/javascript">
$(function(){
	var h = $(window).height();
	$('#bot').css('height', h - 200);
	var params = eval('('+'${param.data}'+')');
	$('#comment_content').val(params.comment_content);
	$(params.check_gx).each(function(i){
		$("[data-id='"+params.check_gx[i]+"']").prop("checked",true);
	})
	
	$('input[type=checkbox]').change(function(){
	if($(this).is(':checked')){
		var value = $(this).val();
		var name = $(this).attr('data-name');
		$('#comment_content').val($('#comment_content').val()+name+'；');
		var fir = $(this).parent().parent().parent().parent().parent().parent().children('div');
		var se = $(this).parent().parent().parent().parent().children('div');
		if(fir.find("i").attr('class') == 'fa fa-plus-square-o'){
			fir.click();
		}
		if(se.find("i").attr('class') == 'fa fa-plus-square-o'){
			se.click();
		}
	}else{
		var name = $(this).attr('data-name');
		$('#comment_content').val($('#comment_content').val().replace(name+'；', ''));
	}
	})
	
	$('ul div').click(function(){
        if($(this).find("i").attr('class') == "fa fa-plus-square-o"){
            $(this).find("i").attr('class','fa fa-minus-square-o')
            $(this).parent().children(".se_item").addClass('se_item_block');
            $(this).parent().children(".se_item").removeClass('se_item');
        }else{
            $(this).find("i").attr('class','fa fa-plus-square-o')
            $(this).parent().children(".se_item_block").addClass('se_item');
            $(this).parent().children(".se_item_block").removeClass('se_item_block');
        }
    })
    
    //div绑定checkbox点击
    $(document).on('click', '.s_div', function (e){
        $(this).children('.tree_checkbox').click();
    });
    
    $('input').click(function(e){
    	e.stopPropagation();
    })
    
    var comment_code = '${param.comment_code}';
    if(comment_code){
    	var comment_codes = comment_code.split(',');
    	for(var i =0;i<comment_codes.length;i++){
    		$('[data-id="'+comment_codes[i]+'"]').click();
    	}
    }
})

/* $(window).resize(function() {
	var h = $(".page-content").height();
	$('#bot').css('height', h - 200);
}); */

function save(){
	var data = {};
	var arr = [];
	data.content = $('#comment_content').val();
	$('input[type=checkbox]:checked').each(function(i){
		var map = {};
		map['name'] = $(this).attr('data-name');
		map['code'] = $(this).val();
      	arr[i] = map;
    });
	data.code = arr;
	$.closeModal(data);
}

function cenel(){
	$.closeModal(false);
}
</script>