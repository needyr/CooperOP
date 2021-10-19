<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="引擎属性" ismodal="true">
	<s:row>
		<s:form id="fform">
			<s:toolbar>
				<s:button icon="fa fa-edit" size="btn-sm btn-default" label="保存" onclick="save()" type="submit"></s:button>
				<s:button icon="fa fa-ban" size="btn-sm btn-default" label="取消" onclick="$.closeModal();"></s:button>
			</s:toolbar>
			<s:row>
				<s:radio cols="4" label="所属产品" name="system_product_code" required="true" value="${pageParam.system_product_code}" action="application.common.listProducts" >
					<s:option value="$[code]" label="$[name]"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<s:textfield cols="1" label="编号" name="id" placeholder="系统唯一的英文编号" required="true" value="${pageParam.id}"></s:textfield>
				<s:textfield cols="2" label="名称" name="name" placeholder="用于展现的引擎名称" required="true" value="${pageParam.name}"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		var data = $("form").getData();
		$.closeModal(data);
	}
</script>