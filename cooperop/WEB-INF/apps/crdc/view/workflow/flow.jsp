<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page title="流程属性" ismodal="true">
	<s:row>
		<s:form id="fform">
			<s:toolbar>
				<s:button icon="fa fa-edit" size="btn-sm btn-default" label="保存" onclick="save()" type="submit"></s:button>
				<s:button icon="fa fa-ban" size="btn-sm btn-default" label="取消" onclick="$.closeModal();"></s:button>
			</s:toolbar>
			<s:row>
				<s:radio cols="4" onchange="setProduct()" label="所属产品" name="system_product_code" required="true" value="${pageParam.system_product_code}" action="application.common.listProducts" >
					<s:option value="$[code]" label="$[name]"></s:option>
				</s:radio>
			</s:row>
			<s:row>
				<s:textfield cols="1" label="流程编号" name="id" placeholder="系统唯一的流程英文编号" required="true" value="${pageParam.id}"></s:textfield>
				<s:textfield cols="2" label="流程名称" name="name" placeholder="用于展现的流程名称" required="true" value="${pageParam.name}"></s:textfield>
				<s:textfield cols="1" datatype="number" label="期望完成时间" name="expireTime" placeholder="在流程超过多少分钟后将自动提醒" value="${pageParam.expireTime}"></s:textfield>
			</s:row>
			<s:row>
				<%-- <s:textfield cols="2" label="流程创建单据" name="instance_bill" placeholder="此单据在保存成功后将自动发起流程" required="true" dblaction="crdc.workflow.selectbill" value="${pageParam.instance_bill}"></s:textfield>
				<s:textfield cols="2" label="信息展现单据" name="info_bill" placeholder="用于查看流程及业务信息的再现单据" required="true" dblaction="crdc.workflow.selectbill" value="${pageParam.info_bill}"></s:textfield> --%>
				<s:autocomplete cols="2" label="触发流程单据" name="instance_bill" placeholder="此单据在保存成功后将自动发起流程" required="true" action="crdc.workflowdesigner.selectbill" value="${pageParam.instance_bill}" editable="true">
					<s:option value="$[system_product_code].$[type].$[flag].$[id]" label="$[system_product_code].$[type].$[flag].$[id]">$[system_product_code].$[type].$[flag].$[id]($[description])</s:option>
				</s:autocomplete>
				<s:autocomplete cols="2" label="信息展现单据" name="info_bill" placeholder="用于查看流程及业务信息的再现单据" required="true" action="crdc.workflowdesigner.selectbill" value="${pageParam.info_bill}" editable="true">
					<s:option value="$[system_product_code].$[type].$[flag].$[id]" label="$[system_product_code].$[type].$[flag].$[id]">$[system_product_code].$[type].$[flag].$[id]($[description])</s:option>
				</s:autocomplete>
			</s:row>
			<s:row>
				<s:textarea cols="4" rows="6" label="主题规则SQL" name="subject_scheme" placeholder="流程主题拼接SQL语句，最终结果必须是一条只包含一个字符串字段（主题）的记录，传入变量只有djbh。如：select a from b where djbh = :djbh" required="true">${pageParam.subject_scheme}</s:textarea>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	function setProduct(){
		var system_product_code = $("#fform").getData().system_product_code;
		$("[name='instance_bill']").params({"system_product_code":system_product_code});
		$("[name='info_bill']").params({"system_product_code":system_product_code});
	}
	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		var data = $("form").getData();
		$.closeModal(data);
	}
</script>