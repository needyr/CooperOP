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
				<input type="hidden" name="id" value="${id }"/>
				<s:select label="所属产品" name="system_product_code" action="application.common.listProducts" value="${system_product_code }" required="true">
					<s:option value="$[code]" label="$[name]"></s:option>
				</s:select>
				<s:textfield label="方案编号" name="no" required="required"
					value="${no}" islabel="${not empty id}"></s:textfield>
				<s:textfield label="方案类型" name="view_id" required="required"
					value="${view_id}" islabel="${not empty id}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="方案名称" name="description" cols="3"
					value="${description}"></s:textfield>
			</s:row>
			<s:row>
				<s:switch label="保存后打印" name="after_save" value="${after_save }" onvalue="Y" offvalue="N"></s:switch>
			</s:row>
			<s:row>
				<s:file cols="4" label="打印模版" name="attach" autoupload="true" value="${attach }"></s:file>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	function save() {
		var d = $("#myform").getData();
		if(!d.id){
			delete d.id;
		}
		$.call("crdc.djasper.save", d, function(rtn) {
			if (rtn) {
				$.closeModal(true);
			}
		});
	}
</script>
