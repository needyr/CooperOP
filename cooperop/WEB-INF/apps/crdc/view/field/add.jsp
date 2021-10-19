<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
	<s:row>
		<s:form border="0" id="myform">
			<s:toolbar>
				<s:button label="保存" onclick="save();" icon=""></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield label="西文字段名" name="fdname" required="required"
					value="${fdname}"></s:textfield>
				<s:textfield label="中文名称" name="chnname" required="required"
					value="${chnname}"></s:textfield>
				<s:select label="字段类型" name="fdtype" required="required"
					value="${fdtype}">
					<s:option label="字符" value="字符"></s:option>	
					<s:option label="文本" value="文本"></s:option>	
					<s:option label="整数" value="整数"></s:option>	
					<s:option label="实数" value="实数"></s:option>	
					<s:option label="位图" value="位图"></s:option>	
					<s:option label="二进制" value="二进制"></s:option>	
				</s:select>
			</s:row>
			<s:row>
				<s:textfield label="字段宽度" name="fdsize" required="required"
					value="${fdsize}"></s:textfield>
				<s:textfield label="字段精度" name="fddec" required="required"
					value="${fddec}"></s:textfield>
				<s:textfield label="检索字段显示宽度控制" name="nouse" required="required"
					value="${nouse}"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	function save() {
		$.call("crdc.field.save", $("#myform").getData(), function(rtn) {
			if (rtn.result == 'success') {
				$.closeModal(true);
			}
		});
	}
</script>
