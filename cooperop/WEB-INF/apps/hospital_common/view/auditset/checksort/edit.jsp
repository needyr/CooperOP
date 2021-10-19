<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>

<s:page title="" disloggedin="true">
	<s:row>
		<s:form id="form" label="分类信息">
			<s:toolbar>
				<s:button label="确认" onclick="checkZzf()" icon="fa fa-check"></s:button>
				<s:button label="取消" onclick="cancel()" icon="fa fa-times"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield name="sort_id" id="sort_id" label="分类编号" cols="1" required="true" value="${sort_id}"></s:textfield>
			</s:row>

			<s:row>
				<s:radio name="is_zzf" label="转自费设置" value="${empty is_zzf ? 1:is_zzf}" required="true" cols="1">
					<s:option value="1" label="全部允许转自费"></s:option>
					<s:option value="0" label="全部不允许转自费"></s:option>
					<s:option value="2" label="自定义控制转自费"></s:option>
				</s:radio>
			</s:row>

			<s:row>
				<s:switch name="state" label="是否启用" value="${state}" onvalue="1" offvalue="0" ontext="是" offtext="否"></s:switch>
			</s:row>

			<s:row>
				<s:textfield name="sort_name" id="sort_name" label="分类名称" cols="3" required="true" value="${sort_name}"></s:textfield>
			</s:row>

			<s:row>
				<s:textfield name="sort_num" label="排序值" cols="3" required="true" value="${sort_num}"></s:textfield>
			</s:row>

			<s:row>
				<s:textarea name="audit_explain" required="true" value="${audit_explain}" label="审查说明" cols="3" height="4" rows="4"></s:textarea>
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
			$('[name = "sort_id"]').attr('disabled', 'disabled');
		}
	});

	function submitData() {
		/* 1添加,2更新 */
		var upOrAdd = '${param.upOrAdd}';
		var methodName;
		var fdata = $("#form").getData();
		fdata.product_code = '${param.product_code}';

		/* 判断所需要调用方法 */
		if (upOrAdd == 1) {
			methodName = "hospital_common.auditset.checksort.insert";
		} else if (upOrAdd == 2) {
			methodName = "hospital_common.auditset.checksort.update";
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
						$.message("分类编号重复，请重新输入");
					} else {
						$.message("编辑失败");
					}

				}

			});
		}
	}

	//判断转自费设置是否改变
	function checkZzf() {
		var upOrAdd = '${param.upOrAdd}';
		var fdata = $("#form").getData();

		/* if (upOrAdd == 2 && '${is_zzf}' == '2' && fdata.is_zzf != "2") {
			$.confirm('转自费设置不再是【科室控制转自费】，其所有设置将会被删除！', function(choose) {
				if (choose == true) {
					delBySortId();
					submitData();
				} else {
					return;
				}
			});
		} else {
			submitData();
		}
		 */
		submitData();
	}

	//删除科室控制转自费设置
	/* function delBySortId() {
		$.call("hospital_common.auditset.checksort.delBySortId", {
			"sort_id" : "${sort_id}"
		}, function(s) {

		});
	}
 */
	function cancel() {
		$.closeModal(false);
	}

	
</script>