
<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="编辑">
	<s:row>
		<s:form id="dataform">
			<s:toolbar>
				<s:button label="保存" onclick="submitData()" icon="fa fa-check"
					style="background: #368bdb;text-align: center;color: #FFF;"></s:button>
				<s:button label="取消" onclick="cancel()" icon="fa fa-times"></s:button>
			</s:toolbar>
			<s:row>
				<s:autocomplete name="dg_icd" label="诊断编码" value="${dg_icd}"
					action="hospital_common.dictsys.dg.queryDictHisDiagnosis"
					required="true" limit="5" cols="3" onchange="updateAuto()">
					<s:option value="$[diagnosis_code],$[diagnosis_name]"
						label="$[diagnosis_code]">$[diagnosis_code] $[diagnosis_name]</s:option>
				</s:autocomplete>
			</s:row>

			<s:row>
				<s:textfield name="dg_name" label="病组名称" value="${dg_name}"
					required="true" cols="3"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield name="dg_remark" label="分组详情" value="${dg_remark}"
					required="true" cols="3"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield name="dg_tablepar" label="病组批次号" value="${dg_tablepar}"
					cols="3"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield name="dg_zl_id" label="项目编码" value="${dg_zl_id}"
					cols="3"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield name="dg_zl_name" label="项目名称" value="${dg_zl_name}"
					cols="3"></s:textfield>
			</s:row>
			<s:row>
				<s:autocomplete name="dg_type" label="分组大类" value="${dg_type}"
					text="${dg_type_name}" required="true"
					action="hospital_common.dictsys.dgtype.query" limit="5" cols="3">
					<s:option value="$[dg_type_code]" label="$[dg_type_name]">$[dg_type_code] $[dg_type_name]</s:option>
				</s:autocomplete>
			</s:row>

			<s:row>
				<s:textarea name="beizhu" label="备注" value="${beizhu}"
					autosize="false" height="50" cols="3"></s:textarea>
			</s:row>
		</s:form>
	</s:row>

</s:page>
<script type="text/javascript">
   function updateAuto(){
	   var fdata = $("#dataform").getData();
	   var arr=fdata.dg_icd.split(',');
	   $("[name='dg_name']").val(arr[1]);
   }
	//判断操作是添加还是更新
	function submitData() {
		/* 1添加,2更新 */
		var upOrAdd = '${param.upOrAdd}';
		var methodName;
		var fdata = $("#dataform").getData();
		var arr=fdata.dg_icd.split(',');
		fdata.dg_icd=arr[0];
		/* 判断所需要调用方法 */
		if (upOrAdd == 1) {
			methodName = "hospital_common.dictsys.dg.insert";
		} else if (upOrAdd == 2) {
			methodName = "hospital_common.dictsys.dg.update";
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

