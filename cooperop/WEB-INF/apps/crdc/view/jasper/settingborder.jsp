<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="">
<s:row>
	<s:form id="setFrom">
		<s:toolbar>
			<s:button label="确定" onclick="save();"></s:button>
			<s:button label="取消" onclick="returnback();"></s:button>
		</s:toolbar>
		<s:row>
			<s:textfield label="高度" name="height_" value="${pageParam.height_ }"></s:textfield>
			<s:textfield label="宽度" name="width_" value="${pageParam.width_ }"></s:textfield>
		</s:row>
		<s:row>
			<s:textfield label="内上边距" name="padding_top" value="${pageParam.padding_top }"></s:textfield>
			<s:textfield label="内右边距" name="padding_right" value="${pageParam.padding_right }"></s:textfield>
			<s:textfield label="内下边距" name="padding_bottom" value="${pageParam.padding_bottom }"></s:textfield>
			<s:textfield label="内左边距" name="padding_left" value="${pageParam.padding_left }"></s:textfield>
		</s:row>
		<s:row>
			<s:textfield label="上边框" name="border_top" value="${pageParam.border_top }"></s:textfield>
			<s:textfield label="右边框" name="border_right" value="${pageParam.border_right }"></s:textfield>
			<s:textfield label="下边框" name="border_bottom" value="${pageParam.border_bottom }"></s:textfield>
			<s:textfield label="左边框" name="border_left" value="${pageParam.border_left }"></s:textfield>
		</s:row>
	</s:form>
</s:row>
</s:page>
<script>
	function save() {
		var data = $("#setFrom").getData();
		if(data.padding_top){
			data.padding_top = data.padding_top +"px solid";
		}
		if(data.padding_bottom){
			data.padding_bottom = data.padding_bottom +"px solid";
		}
		if(data.padding_left){
			data.padding_left = data.padding_left +"px solid";
		}
		if(data.padding_right){
			data.padding_right = data.padding_right +"px solid";
		}
		if(data.border_top){
			data.border_top = data.border_top +"px solid";
		}
		if(data.border_bottom){
			data.border_bottom = data.border_bottom +"px solid";
		}
		if(data.border_left){
			data.border_left = data.border_left +"px solid";
		}
		if(data.border_right){
			data.border_right = data.border_right +"px solid";
		}
		if(data.height_){
			data.height_ = data.height_ +"px";
		}
		if(data.width_){
			data.width_ = data.width_ +"px";
		}
		$.closeModal(data);
	}
	function returnback() {
		$.closeModal(false);
	}
</script>
