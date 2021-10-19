<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="属性" ismodal="true">
	<s:row>
		<s:form>
			<s:toolbar>
				<s:button icon="fa fa-edit" size="btn-sm btn-default" label="保存" onclick="save()"></s:button>
				<s:button icon="fa fa-ban" size="btn-sm btn-default" label="取消" onclick="$.closeModal();"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield cols="1" label="节点编号" name="id" value="${pageParam.id}" placeholder="唯一的节点英文编号" required="true"></s:textfield>
				<s:textfield cols="2" label="节点名称" name="name" value="${pageParam.name}" placeholder="用于展现的节点名称" required="true"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea cols="3" autosize="true" rows="6" label="规则SQL" name="execute_scheme" required="true" >${pageParam.execute_scheme}</s:textarea>
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