<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="编辑">
	<s:row>
		<s:form id="dataform" label="病组评估金额">
			<s:toolbar>
				<s:button label="保存" onclick="submitData()" icon="fa fa-check"
					style="background: #368bdb;text-align: center;color: #FFF;"></s:button>
				<s:button label="取消" onclick="cancel()" icon="fa fa-times"></s:button>
			</s:toolbar>
			
			<s:row>
				<s:autocomplete name="item_code" label="检验项目" value="${item_code}" text="${item_name}"
					required="true" action="hospital_common.dict.labtestitems.queryLabReportItem" limit="5">
					<s:option value="$[item_code]" label="$[item_name]">$[item_code] $[item_name]</s:option>
				</s:autocomplete>
			</s:row>

			<s:row>
				<s:radio name="is_byxjc" label="是否为病原学检测" value="${is_byxjc}" required="true">
					<s:option value="1" label="是"></s:option>
					<s:option value="0" label="否"></s:option>
				</s:radio>
			</s:row>
		</s:form>
	</s:row>

</s:page>
<script type="text/javascript">

	//判断操作是添加还是更新
	function submitData() {
		/* 1添加,2更新 */
		var upOrAdd = '${param.upOrAdd}';
		var methodName;
		var fdata = $("#dataform").getData();
		/* 判断所需要调用方法 */
		if (upOrAdd == 1) {
			methodName = "hospital_common.dict.labtestitems.insert";
		} else if (upOrAdd == 2) {
			methodName = "hospital_common.dict.labtestitems.update";
			fdata.pkey_id = '${pkey_id}';
		}
		/* 必填验证 */
		if ($("form").valid()) {
			/* 提交数据 */
			$.call(methodName, fdata, function(s) {
				if (s > 0) {
					$.closeModal(true);
				} else {
					if (upOrAdd == 1) {
						$.message("添加失败");
					} else if (upOrAdd == 2) {
						$.message("更新失败");
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

