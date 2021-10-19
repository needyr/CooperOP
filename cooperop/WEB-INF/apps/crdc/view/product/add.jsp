<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld" %>
<s:page title="新增产品定义" ismodal="true">
	<s:row>
		<s:form label="产品定义">
			<s:toolbar>
				<s:button icon="fa fa-save" color="green" label="保存" onclick="save();"></s:button>
				<s:button icon="fa fa-ban" label="取消" onclick="$.closeModal();"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield cols="4" label="产品编号" name="code" required="true"></s:textfield>
				<s:textfield cols="4" label="产品名称" name="name" required="true"></s:textfield>
				<s:textfield cols="4" label="根权限" name="popedom" dblaction=""></s:textfield>
				<s:textfield cols="4" label="默认角色" name="default_role" dblaction=""></s:textfield>
				<s:textfield cols="4" label="数据库实例" name="db_scheme" placeholder="用于数据库集基础数据同步时访问，配置为：'CR_YPPF10'或者'CR_ERP.CR_YPPF10'"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
function save() {
	if (!$("form").valid()) {
		return;	
	}
	$.call("crdc.product.insert", $("form").getData(), function(rtn) {
		$.success("保存成功！", function() {
			$.closeModal(true);
		});
	});
}
</script>