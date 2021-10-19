<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="编辑问题严重程度匹配" disloggedin="true">

	<s:row>
		<s:form id="form" label="对接信息">

			<s:toolbar>
				<s:button label="确认" onclick="submitData()" icon=""></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>

			<s:row>
				<s:textfield name="thirdt_code" label="审方等级编号" cols="3"
					required="true" value="${thirdt_code}"></s:textfield>
			</s:row>

			<s:row>
				<s:textfield name="thirdt_name" label="审方等级名称" cols="3"
					required="true" value="${thirdt_name}"></s:textfield>
			</s:row>

			<s:row>
				<s:textfield name="check_state" label="状态" cols="3" required="true"
					value="${check_state}"></s:textfield>
			</s:row>

			<s:row>
				<s:autocomplete label="标准等级"
					params="{&#34;product_code&#34;: &#34;${param.product_code}&#34;}"
					name="sys_p_key" required="true" value="${s_p_key}"
					text="${level_name}" cols="3"
					action="hospital_common.auditset.mapchecklevel.queryAllLevel" limit="5">
					<s:option value="$[p_key]" label="$[level_name]">$[level_code] $[level_name]</s:option>
				</s:autocomplete>
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
			$('[name = "thirdt_code"]').attr('disabled', 'disabled');
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
			methodName = "hospital_common.auditset.mapchecklevel.insert";
		} else if (upOrAdd == 2) {
			methodName = "hospital_common.auditset.mapchecklevel.update";
			fdata.m_p_key = '${m_p_key}';
		}

		/* 必填验证 */
		if ($("form").valid()) {

			/* 提交数据 */
			ajax: $.call(methodName, fdata, function(s) {
				if (s > 0) {
					$.closeModal(false);
				} else {
					if (upOrAdd == 1) {
						$.message("审方等级编号重复");
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