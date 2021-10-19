<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="设置序号" dispermission="true">
	<s:row>
		<s:form border="0" id="myform" fclass="portlet light bordered">
			<s:row>
				<s:switch cols="4" label="开放到首页" name="func_checked" value="${pageParam.func_checked }" onvalue="Y" offvalue="N"/>
			</s:row>
			<s:row>
				<s:textfield cols="3" label="显示顺序" name="func_order" value="${pageParam.func_order }" min="0"></s:textfield>
			</s:row>
			<s:row>
				<div class="cols">
					<s:button onclick="save();" color="green" label="保存"></s:button>
				</div>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	function save() {
		$.closeModal($("#myform").getData());
	}
</script>

