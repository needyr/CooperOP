<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%
	pageContext.setAttribute("fields", CommonFun.json2Object(request.getParameter("fields"), List.class));
	pageContext.setAttribute("pageattr", CommonFun.json2Object(request.getParameter("pageattr"), Map.class));
%>
<s:page title="流程属性" ismodal="true">
	<s:row>
		<s:form id="fform">
			<s:toolbar>
				<s:button icon="fa fa-edit" size="btn-sm btn-default" label="保存" onclick="save()" type="submit"></s:button>
				<s:button icon="fa fa-ban" size="btn-sm btn-default" label="取消" onclick="$.closeModal();"></s:button>
			</s:toolbar>
			<s:row>
				<s:textfield cols="1" label="流程编号" name="id" placeholder="系统唯一的流程英文编号" required="true" value="${pageParam.id}" readonly="true"></s:textfield>
				<s:textfield cols="2" label="流程名称" name="name" placeholder="用于展现的流程名称" required="true" value="${pageParam.name}" readonly="true"></s:textfield>
				<s:textfield cols="1" datatype="number" label="期望完成时间" name="expireTime" placeholder="在流程超过多少分钟后将自动提醒" value="${pageParam.expireTime}"></s:textfield>
			</s:row>
			<s:row>
				<s:textarea cols="4" rows="6" label="主题" name="subject" value="${pageParam.subject }" placeholder="直接输入，可在下方列表中双击加入字段变量" required="true"></s:textarea>
			</s:row>
			<s:row>
				<s:select id="fieldsselect" label="流程变量" multiple="multiple" cols="2" style="height:auto;" ondblclick="addParam();">
					<s:option label="流程名称{process_name}" value="process_name"></s:option>
					<s:option label="发起人{creator_name}" value="creator_name"></s:option>
					<s:option label="发起人编号{creator_no}" value="creator_no"></s:option>
					<s:option label="发起部门{department_name}" value="department_name"></s:option>
					<s:option label="发起部门编号{department_no}" value="department_no"></s:option>
					<s:option label="发起日期{create_date}" value="create_date"></s:option>
					<s:option label="发起时间{create_time}" value="create_time"></s:option>
				</s:select>
				<s:select id="fieldsselect1" label="文档变量" multiple="multiple" cols="2" style="height:auto;" ondblclick="addParam1();">
					<c:forEach items="${fields}" var="f">
						<s:option value="${f.name }" label="${f.label }{${f.name }}"></s:option>
					</c:forEach>
				</s:select>
			</s:row>
		</s:form>
	</s:row>
</s:page>
<script type="text/javascript">
	function addParam() {
		if ($("#fieldsselect").val()) {
			$("[name='subject']").val($("[name='subject']").val() + "" + $("#fieldsselect").val() + "")
		}
	}
	function addParam1() {
		if ($("#fieldsselect1").val()) {
			$("[name='subject']").val($("[name='subject']").val() + "" + $("#fieldsselect1").val() + "")
		}
	}
	function save() {
		if (!$("form").valid()) {
			return false;	
		}
		var data = $("form").getData();
		data.instance_bill = '${pageattr.system_product_code}'+".document."+'${pageattr.flag}'+"."+'${pageattr.schemeid}';
		data.info_bill = '${pageattr.system_product_code}'+".document."+'${pageattr.flag}'+"."+'${pageattr.schemeid}'+"suggestions";
		data.subject_scheme = "select "+data.subject+ " from dfl_"+'${pageattr.flag}'+"_"+'${pageattr.schemeid}'+"(nolock)  where djbh=:djbh";
		$.closeModal(data);
	}
</script>