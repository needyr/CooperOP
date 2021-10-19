<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="新增信息">
	<s:row>
		<s:form label="新增TPN指标" id="dataForm">
			<s:toolbar>
				<s:row>
					<s:button label="保存" onclick="save()"></s:button>
					<s:button label="取消" onclick="$.closeModal(false);"></s:button>
				</s:row>
			</s:toolbar>
			<s:row>
				<s:textfield label="指标ID" name="shengfang_tpnzbid" 
					required="true" cols="2" ></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="指标编号" name="shengfang_tpnzbbh" 
					required="true" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="指标名称" name="name_tpnzb"  cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="等级" name="level"  cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:switch label="是否活动" name="beactive" offvalue="否" onvalue="是" ></s:switch>
			</s:row>
			<s:row>
				<s:textfield label="拼音码" name="pym" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="开始年龄_指标" name="nianl_start_tnpzb"  datatype="number"
					required="true"  cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="截止年龄_指标" name="nianl_end_tpnzb"  datatype="number"
					required="true"  cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="备注" name="beizhu" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="操作员" name="username"  cols="2"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	//新增
	function save() {
	   if (!$("form").valid()) {
			return false;
		}
		var data = $("#dataForm").getData();
		$.call("hospital_common.tpn.tpnzb.insert", data, function(rtn) {
			if (rtn) {
				$.closeModal(true);
			}else{
				$.message("该指标ID已存在！");
			}
		});
	}
</script>
