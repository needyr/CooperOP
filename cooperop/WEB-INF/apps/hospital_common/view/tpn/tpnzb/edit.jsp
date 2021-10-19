<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="修改信息">
	<s:row>
		<s:form label="修改TPN指标" id="dataForm">
			<s:toolbar>
				<s:row>
					<s:button label="保存" onclick="save()"></s:button>
					<s:button label="取消" onclick="$.closeModal(false);"></s:button>
				</s:row>
			</s:toolbar>
			<s:row>
				<s:textfield label="指标ID" name="shengfang_tpnzbid"  readonly="true"
					value="${shengfang_tpnzbid }" required="true" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="指标编号" name="shengfang_tpnzbbh"
					value="${shengfang_tpnzbbh }" required="true" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="指标名称" name="name_tpnzb"
					value="${name_tpnzb }" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="等级" name="level" value="${level }"
					cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:switch label="是否活动" name="beactive" onvalue="是" offvalue="否" cols="2" value="${beactive }"></s:switch>
			</s:row>
			<s:row>
				<s:textfield label="拼音码" name="pym" value="${pym }" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="开始年龄_指标" name="nianl_start_tnpzb" datatype="number"
					value="${nianl_start_tnpzb }" required="true" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="截止年龄_指标" name="nianl_end_tpnzb" datatype="number"
					value="${nianl_end_tpnzb }" required="true" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="备注" name="beizhu" value="${beizhu }"
					cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="操作员" name="username" value="${username }"
					cols="2"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	//修改保存
	function save() {
		if (!$("form").valid()) {
			return false;
		}
		var data = $("#dataForm").getData();
		$.call("hospital_common.tpn.tpnzb.update", data, function(rtn) {
			if (rtn) {
				$.closeModal(true);
			}
		});
	}
</script>
