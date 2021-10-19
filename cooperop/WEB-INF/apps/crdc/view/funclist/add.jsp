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
				<input name="functionname_old" value="${functionname}" type="hidden"/>
				<input name="lx" value="N" type="hidden"/>
				<s:textfield label="方法代码" name="functionname" required="required"
					value="${functionname}"></s:textfield>
				<s:textfield label="方法名称" name="functitle" required="required" cols="2"
					value="${functitle}"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea label="备注" name="beizhu" required="required" cols="3"
					value="${beizhu}"></s:textarea>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	function save() {
		$.call("crdc.funclist.save", $("#myform").getData(), function(rtn) {
			if (rtn.result == 'success') {
				$.closeModal(true);
			}
		});
	}
</script>
