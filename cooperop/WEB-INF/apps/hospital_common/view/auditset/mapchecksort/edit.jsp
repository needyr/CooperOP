<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>

<s:page title="" disloggedin="true">

	<s:row>
		<s:form id="form" label="审核规则信息">
			<s:toolbar>
				<s:button label="确认" onclick="submitData()" icon=""></s:button>
				<s:button label="取消" onclick="cancel()" icon=""></s:button>
			</s:toolbar>

			<s:row>
				<s:select label="审核引擎" name="check_type" value="${check_type}" cols="3">
					<s:option value="1" label="智能审查"></s:option>
					<s:option value="2" label="自定义审查"></s:option>
				</s:select>
			</s:row>
			<s:row>
				<s:textfield name="thirdt_code" label="规则编码" cols="3" required="true" value="${thirdt_code}"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield name="thirdt_name" label="规则名称" cols="3" required="true" value="${thirdt_name}"></s:textfield>
			</s:row>
			<s:row>
				<s:autocomplete label="对应的系统规则"
					params="{&#34;product_code&#34;: &#34;${param.product_code}&#34;}"
					name="sys_p_key" required="true" value="${s_p_key}"
					text="${sort_name}" cols="3"
					action="hospital_common.auditset.checksort.query" limit="10">
					<s:option value="$[p_key]" label="$[sort_name]">$[sort_id] $[sort_name]</s:option>
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
			methodName = "hospital_common.auditset.mapchecksort.insert";
		} else if (upOrAdd == 2) {
			methodName = "hospital_common.auditset.mapchecksort.update";
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
						$.message("审方类别编号或审查分类重复");
					} else if (upOrAdd == 2) {
						$.message("审查类别重复");
					} else {
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