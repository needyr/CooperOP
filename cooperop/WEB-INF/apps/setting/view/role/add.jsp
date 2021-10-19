<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="" ismodal="true">
	<s:row>
		<s:form border="0" id="myform" fclass="portlet light bordered">
			<s:row>
				<input type="hidden" name="id" value="${id }"/>
				<s:radio label="所属产品" name="system_product_code" action="application.common.listProducts" value="${system_product_code}" cols="3" required="required">
						<s:option value="$[code]" label="$[name]"></s:option>
					</s:radio>
			</s:row>
			<s:row>
				<s:textfield cols="2" label="角色名称" name="name" value="${name}"></s:textfield>
				<s:textfield cols="2" label="描述" name="description" value="${description}" ></s:textfield>
			</s:row>
			<s:row>
				<div class="cols3">
				</div>
				<div class="cols">
					<s:button onclick="save();" color="green" label="保存"></s:button>
				</div>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script>
	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		$.call("setting.role.save", $("#myform").getData(), function(rtn) {
			if (rtn) {
				$.closeModal(true);
			}
		},null,{async: false});
	}
</script>
