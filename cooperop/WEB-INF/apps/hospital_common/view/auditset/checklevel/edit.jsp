<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>

<s:page title="" disloggedin="true">

	<s:row>
		<s:form id="form" label="编辑">

			<s:toolbar>
				<s:button label="确认" onclick="submitData()" icon=""></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>

			<s:row>
				<s:textfield name="level_code" label="等级编号" cols="3"
					required="true" value="${level_code}"></s:textfield>
			</s:row>

			<s:row>
				<s:textfield name="level_name" label="等级名称" cols="3"
					required="true" value="${level_name}"></s:textfield>
			</s:row>
			
			<s:row>
				<s:textfield name="level_star" label="星级" cols="3"
					required="true" value="${level_star}"></s:textfield>
			</s:row>
			
			<s:row>
				<s:textarea name="description" 
					value="${description}" label="说明" cols="3"></s:textarea>
			</s:row>

		</s:form>
	</s:row>

</s:page>


<script>
	//初始化
	$(function() {

		/* 1添加,2更新 */
		var upOrAdd = '${param.upOrAdd}';
		if (upOrAdd == 2) {
			$('[name = "level_code"]').attr('disabled', 'disabled');
		}
	});

	function submitData() {
		/* 1添加,2更新 */
		var upOrAdd = '${param.upOrAdd}';
		var methodName;
		var fdata = $("#form").getData();
		console.log(fdata);
		fdata.product_code = '${param.product_code}';
		/* 判断所需要调用方法 */
		if (upOrAdd == 1) {
			methodName = "hospital_common.auditset.checklevel.insert";
		} else if (upOrAdd == 2) {
			methodName = "hospital_common.auditset.checklevel.update";
			fdata.p_key = '${p_key}';
		}

		/* 必填验证 */
		if ($("form").valid()) {

			/* 提交数据 */
			ajax: $.call(methodName, fdata, function(s) {
				if (s > 0) {
					$.closeModal(false);
				} else {
					if (upOrAdd == 1) {
						$.message("等级编号重复");
					} else if (upOrAdd == 2) {
						$.message("编辑失败");
					}
				}

			});
		}
	}

	function cancel() {
		$.closeModal(false);
	}
</script>