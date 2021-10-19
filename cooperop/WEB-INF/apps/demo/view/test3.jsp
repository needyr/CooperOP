<%@page import="cn.crtech.cooperop.application.action.SchemeAction"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>

<s:page title="测试">
	<s:row>
		<s:button label="测试1" onclick="test1()"></s:button>
		<s:button label="测试2" onclick="test2()"></s:button>
	</s:row>
</s:page>

<script type="text/javascript">
	function test1(){
		$.success("成功");
	}
	
	function test2(){
		$.error("失败");
	}
</script>