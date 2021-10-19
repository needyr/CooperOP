<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="流程属性" ismodal="true">
	<s:row>
		<s:form>
			<s:toolbar>
				<s:button icon="fa fa-edit" size="btn-sm btn-default" label="保存" onclick="save()"></s:button>
				<s:button icon="fa fa-ban" size="btn-sm btn-default" label="取消" onclick="$.closeModal();"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield cols="2" label="流向编号" name="id" value="${pageParam.id}" placeholder="源节点是判断节点时作为流向判断条件"></s:textfield>
				<s:textfield cols="2" label="流向名称" name="name" value="${pageParam.name}" placeholder="用于展现的流程名称"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea cols="3" autosize="true" rows="6" label="判断SQL" name="expr_scheme" placeholder="流向判断条件SQL语句，源节点不是判断节点时有效，最终结果必须是一条只包含一个字符串字段的记录，当返回Y时表示此流向有效，其他表示无效，无记录也是无效，传入变量只有djbh。如：select a from b where djbh = :djbh">${pageParam.expr_scheme}</s:textarea>
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