<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<style>
.tree_checkbox{
	height: 20px;
    width: 20px;
    vertical-align: middle;
    margin-bottom: 7px !important;
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

<div class='lists'>
        <ul class="first_ul">
            <li>
                <div class="content_a first_div">
                <i style="font-size: 20px;position: relative;top: 3px;color: #2553d8;" class="fa fa-plus-square-o"></i>
                <span class="content_span" style="font-size: 14px;">其他诊断</span>
                </div>
                <ul class="se_item">
                <li>
                <div class="content_a">
                <i style="font-size: 20px;position: relative;top: 3px;color: #2553d8;" class="fa fa-plus-square-o"></i>
                <span class="content_span" style="font-size: 14px;">22诊断</span>
                </div>
                    <ul class="se_item">
                        <li>
                        <div data-code="BNW010" class="content_a s_div" title="感冒病">
                        <input type="checkbox" name="tree_checkbox" id="BNW010" data-name="感冒病" value="BNW010" class="tree_checkbox">
                        <span class="content_span">感冒病</span>
                        </div>
                        </li>
                    </ul>
                </li>
                </ul>
            </li>
        </ul>
    </div>
</s:page>
<script type="text/javascript">
$(function(){

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
})
</script>