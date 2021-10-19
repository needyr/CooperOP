<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="${pageContext.request.contextPath}/theme/plugins/jquery.min.js?ipl=Y"
	type="text/javascript"></script>
<!DOCTYPE html>
<html>
<style type="text/css">
.res{
    background: #ff9632 !important;
}
.result{
	background: yellow;
	font-size: 15px;
	color: black;
}
</style>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
<!-- <div style=" float: right;margin-top: 6px;">
<span>检索</span>
<input type="text" name="gjz_search" autocomplete="off" style="width: 100px;"/>
<input type="button" value="下" onclick="next()" />
<input type="button" value="上" onclick="previous()" />
<span id="now_c">0</span>/<span id="max_c">0</span>
</div> -->
<div id="addPage_div" style="font-weight: 600"></div>
</body>
<script type="text/javascript">
var oldKey = "";
var index = 0;
var pos = new Array();//用于记录每个关键词的位置，以方便跳转
var oldCount = 0;//记录搜索到的所有关键词总数
var top;
$(function(){
	var path = getUrlParam('path');
	var data = getUrlParam('data');
	//$.('#my_iframe')
	//$("input[name=gjz_search]").bind("input propertychange",function(){
		//search();
	//})
	$('#addPage_div').load(path, function () {
		//$("input[name=gjz_search]").val(data);
		//search();
		var oIframe = $("#addPage_div")[0];
		var regExp = new RegExp(data+'(?!([^<]+)?>)', 'ig');//正则表达式匹配
		$(oIframe).html($(oIframe).html().replace(regExp, "<span class='result'>" + data + "</span>")); // 高亮操作
	});
	
})

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return decodeURI(r[2]); return null; //返回参数值
}

function search() {
	var oIframe = $("#addPage_div")[0];
    $(".result").removeClass("res");//去除原本的res样式
    var key = $("input[name=gjz_search]").val(); //取key值
    if (!key) {
		//console.log("key为空则退出");
		$(".result").each(function () {//恢复原始数据
            $(this).replaceWith($(this).html());
        });
        oldKey = "";
		$('#now_c').text(0);
    	$('#max_c').text(0);
        return; //key为空则退出
    }
    if (oldKey != key) {
		//console.log("进入重置方法");
        //重置
        index = 0;
        $(".result").each(function () {
            $(this).replaceWith($(this).html());
        });
        pos = new Array();
		var regExp = new RegExp(key+'(?!([^<]+)?>)', 'ig');//正则表达式匹配
		$(oIframe).html($(oIframe).html().replace(regExp, "<span id='result" + index + "' class='result'>" + key + "</span>")); // 高亮操作
        oldKey = key;
        $(".result").each(function () {
            pos.push(($(this).offset().top));
        });
        oldCount = $(".result").length;
		//console.log("oldCount值：",oldCount);
    }

    $(".result:eq(" + index + ")").addClass("res");//当前位置关键词改为红色字体
    $(layer).scrollTop(pos[index]);//跳转到指定位置
	$('#max_c').text(oldCount);
    if(oldCount > 0){
    	$('#now_c').text(index+1);
    }
    $("input[name=gjz_search]").val(key);
    $("input[name=gjz_search]").focus();
}

function previous(){
	index--;
    index = index < 0 ? oldCount - 1 : index;
	search();
}
function next(){
	index++;
    //index = index == oldCount ? 0 : index;
	if(index==oldCount){
		index = 0 ;
	}
	search();
}
</script>
</html>
