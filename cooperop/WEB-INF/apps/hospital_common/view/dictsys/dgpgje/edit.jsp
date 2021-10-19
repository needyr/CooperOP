<%@page import="cn.crtech.cooperop.bus.cache.SystemConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
				<s:autocomplete name="interface_type" label="医保接口类型"
					value="${interface_type}" text="${interface_type_name}"
					required="true" action="hospital_common.dictsys.dgpgje.queryInterfaceType"
					limit="5">
					<s:option value="$[interface_type]" label="$[interface_type_name]">$[interface_type] $[interface_type_name]</s:option>
				</s:autocomplete>
			</s:row>

			<s:row>
				<s:autocomplete name="dg_grade" label="人员类别" value="${dg_grade}"
					text="${dg_grade_name}" required="true"
					action="hospital_common.dictsys.dggrade.query" limit="5">
					<s:option value="$[dg_grade_code]" label="$[dg_grade_name]">$[dg_grade_code] $[dg_grade_name]</s:option>
				</s:autocomplete>
			</s:row>

			<s:row>
				<s:autocomplete name="dg_icd" label="病组分组" value="${dg_icd}"
					text="${dg_name}" required="true"
					action="hospital_common.dictsys.dg.query" limit="5"
					onchange="getremark(this)">
					<s:option value="$[dg_icd],$[dg_remark]" label="$[dg_name]">$[id] $[dg_icd] $[dg_name] $[dg_remark]</s:option>
				</s:autocomplete>
			</s:row>

			<s:row>
				<s:textfield name="dg_remark" label="分组详情" value="${dg_remark}"
					required="true"></s:textfield>
			</s:row>

			<s:row>
				<s:textfield name="dg_pgje" label="系统评估金额" value="${dg_pgje}" readonly="true" 
				min="0"
					></s:textfield>
			</s:row>

			<s:row>
				<s:textfield name="dg_pgje_hospital" label="医院评估金额" placeholder="请输入大于0的数字"
					value="${dg_pgje_hospital}" min="0"></s:textfield>
			</s:row>
		</s:form>
	</s:row>

</s:page>
<script type="text/javascript">
	function getremark(_this) {
		var arr=$("#dataform").getData().dg_icd.split(',');
		var icd=arr[0];
		var remark=arr[1];
		$("[name='dg_remark']").val(remark);		
	}

	//判断操作是添加还是更新
	function submitData() {
		/* 1添加,2更新 */
		var upOrAdd = '${param.upOrAdd}';
		var methodName;
		var fdata = $("#dataform").getData();
		 var icds= fdata.dg_icd.split(',');
		fdata.dg_icd=icds[0]; 
		/* 判断所需要调用方法 */
		if (upOrAdd == 1) {
			methodName = "hospital_common.dictsys.dgpgje.insert";
		} else if (upOrAdd == 2) {
			methodName = "hospital_common.dictsys.dgpgje.update";
			fdata.id = '${id}';
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

