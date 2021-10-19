
<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="人员类别字典-编辑">
	<s:row>
		<s:form id="dataform" label="病组分组资料">
			<s:toolbar>
				<s:button label="保存" onclick="submitData()"  icon="fa fa-check" style="background: #368bdb;text-align: center;color: #FFF;"></s:button>
				<s:button label="取消" onclick="cancel()" icon="fa fa-times"></s:button>
			</s:toolbar>
			<s:row>			
				<s:textfield  name="dg_grade_code" label="类别代码" value="${dg_grade_code}" required="true"></s:textfield >
			</s:row>
			<s:row>
				<s:textfield  name="dg_grade_name" label="类别名称" value="${dg_grade_name}" required="true"></s:textfield >
			</s:row>

			<s:row>
				<s:textarea name="beizhu" label="备注" value="${beizhu}"  autosize="false" height="80" ></s:textarea>
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
			methodName = "hospital_common.dictsys.dggrade.insert";
		} else if (upOrAdd == 2) {
			methodName = "hospital_common.dictsys.dggrade.update";
			fdata.id = '${id}';
		}
		/* 必填验证 */
		if ($("form").valid()) {

			/* 提交数据 */
			ajax: $.call(methodName, fdata, function(s) {
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

