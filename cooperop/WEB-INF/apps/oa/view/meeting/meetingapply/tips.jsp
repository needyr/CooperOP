<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="">
	<div id="tips" style="text-align:center;font-size:14px;color:red;"></div>
</s:page>
<script type="text/javascript">
var start = '${pageParam.start}';
var end = '${pageParam.end}';
var creator = '${pageParam.creator}';
var tips="该会议室在 "+formatDate(new Date(start), 'yyyy-MM-dd HH:mm')+" 到 "+formatDate(new Date(end), 'yyyy-MM-dd HH:mm')+"</br>"+"已被\""+creator+"\"占用";

$(document).ready(function(){
	$("#tips").html(tips);
})
</script>