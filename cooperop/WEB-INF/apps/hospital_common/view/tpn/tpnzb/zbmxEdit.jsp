<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="修改信息">
	<s:row>
		<s:form label="修改信息" id="dataForm">
			<s:toolbar>
				<s:row>
					<s:button label="保存" onclick="save()"></s:button>
					<s:button label="取消" onclick="$.closeModal(false);"></s:button>
				</s:row>
			</s:toolbar>

			<s:row>
				<s:textfield label="指标ID" name="shengfang_tpnzbid"
					value="${shengfang_tpnzbid }" readonly="true" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:autocomplete label="TPN指标名称" name="tpnzb_name" required="true"
					value="${tpnzb_name }" cols="2" limit="5">
					<s:option value="$[name]" label="$[name]"></s:option>
					<s:option data-name="热氮比   "></s:option>
					<s:option data-name="热量    "></s:option>
					<s:option data-name="糖脂比   "></s:option>
					<s:option data-name="液体总量  "></s:option>
					<s:option data-name="糖浓度   "></s:option>
					<s:option data-name="渗透压   "></s:option>
					<s:option data-name="钠浓度   "></s:option>
					<s:option data-name="钾浓度   "></s:option>
					<s:option data-name="镁浓度   "></s:option>
					<s:option data-name="钙浓度   "></s:option>
					<s:option data-name="磷浓度   "></s:option>
					<s:option data-name="一价浓度  "></s:option>
					<s:option data-name="二价浓度  "></s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:textfield label="指标最小值" name="tpnzb_min" datatype="number"
					value="${tpnzb_min }" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="指标最大值" name="tpnzb_max" datatype="number"
					value="${tpnzb_max }" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="TPN指标单位" name="tpnzb_dw"
					value="${tpnzb_dw }"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="备注" name="beizhu" value="${param.beizhu }"
					cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="公式" name="formul_tpnzb" id="formul_tpnzb"
					value="${formul_tpnzb }" onclick="cal()" required="true"
					cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="显示顺序" name="dj_sn" datatype="number"
					value="${dj_sn }" required="true" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="显示图片比例" name="show_bl" datatype="number"
					value="${show_bl }" cols="2"></s:textfield>
			</s:row>
			<s:row>
				<s:textfield label="显示单位" name="show_dw" value="${show_dw }"
					cols="2"></s:textfield>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	//单机计算公式
	var sends = '${formul_tpnzbs }';
	function cal() {
		$.modal("calc.html", "TPN计算器", {
			width : "455px",
			height : "600px",
			callback : function(e){	
				if(typeof(e)=='object'){
					var views = "";
					sends="";
					$.each(e, function(i, val) {
						views += val.view;
						sends += val.send;
					})
					$("#formul_tpnzb").val(views);
				}						
			}
		})
	}
	//修改指标明细信息
	function save() {
		if (!$("form").valid()) {
			return false;
		}
		var data = $("#dataForm").getData();
		data.id = '${id }';
		data.formul_tpnzbs=sends;
		$.call("hospital_common.tpn.tpnzb.updateZbmx", data, function(rtn) {
			if (rtn) {
				$.closeModal(true);
			}
		});
	}
</script>
