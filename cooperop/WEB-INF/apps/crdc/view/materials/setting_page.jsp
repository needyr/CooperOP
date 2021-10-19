<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page ismodal="true" title="">
<s:row>
	<s:form fclass="portlet light bordered" id="setFrom">
		<s:row>
			<input type="hidden" name="flag" value="m"/>
			<s:textfield label="方案标识" name="schemeid" value="${pageParam.schemeid }" required="true" maxlength="16"></s:textfield>
			<s:select label="所属产品" name="system_product_code" action="application.common.listProducts" value="${pageParam.system_product_code }" required="true">
				<s:option value="$[code]" label="$[name]"></s:option>
			</s:select>
			<s:textfield label="方案中文描述" name="description" value="${pageParam.description }" required="true"></s:textfield>
		</s:row>
		<s:row>
			<s:textfield label="资料表名" name="tablename" value="${pageParam.tablename }" required="true"></s:textfield>
			<s:textfield label="资料表主键" name="tablekey" value="${pageParam.tablekey }" required="true"></s:textfield>
		</s:row>
		<s:row>
			<s:textfield cols="2" label="运行窗口标题" name="title" value="${pageParam.runtitle }"></s:textfield>
			<s:textfield cols="2" label="备注" name="remark" value="${pageParam.remark }"></s:textfield>
		</s:row>
		<s:row>
			<s:button label="确定" color="green" onclick="save();"></s:button>
			<s:button label="取消" color="red" onclick="returnback();"></s:button>
		</s:row>
	</s:form>
</s:row>
</s:page>
<script type="text/javascript">
function save(){
	var d = $("#setFrom").getData();
	$.closeModal(d);
}
function returnback(){
	$.closeModal(false);
}
</script>
