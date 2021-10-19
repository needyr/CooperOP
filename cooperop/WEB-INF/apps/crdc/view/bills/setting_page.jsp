<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<s:page ismodal="true" title="">
<s:row>
	<s:form fclass="portlet light bordered" id="setFrom">
		<s:row>
			<s:textfield label="单据类型" name="schemeid" value="${pageParam.schemeid }" maxlength="4" required="true"></s:textfield>
			<s:textfield label="单据类型标识" name="flag" value="${pageParam.flag }" required="true" maxlength="3"></s:textfield>
			<s:textfield label="单据中文描述" name="description" value="${pageParam.description }" required="true"></s:textfield>
			<s:select label="所属产品" name="system_product_code" action="application.common.listProducts" value="${pageParam.system_product_code }" required="true">
				<s:option value="$[code]" label="$[name]"></s:option>
			</s:select>
		</s:row>
		<s:row>
			<s:textfield label="单据摘要" name="djzy" value="${pageParam.djzy }"></s:textfield>
			<s:textfield label="帮助索引" name="helpno" value="${pageParam.helpno }"></s:textfield>
			<s:textfield cols="2" label="运行窗口标题" name="title" value="${pageParam.runtitle }"></s:textfield>
		</s:row>
		<s:row>
			<s:textfield cols="2" label="备注" name="remark" value="${pageParam.remark }"></s:textfield>
			<s:textfield label="打印附加明显" name="printmx" value="${pageParam.printmx }"></s:textfield>
			<s:textfield label="单据编号后缀" name="sub" value="${pageParam.sub }" maxlength="1"></s:textfield>
		</s:row>
		<s:row>
			<s:textfield label="宽度" name="modalwidth" value="${pageParam.modalwidth }"></s:textfield>
			<s:textfield label="高度" name="modalheight" value="${pageParam.modalheight }"></s:textfield>
		</s:row>
		<s:row>
			<%-- <s:button label="单据再现设置" color="blue" onclick="show();"></s:button>
			<s:button label="引用其他单据方案" color="blue" onclick="includedj();"></s:button> --%>
			<s:button label="确定" color="green" onclick="save();"></s:button>
			<s:button label="取消" color="red" onclick="returnback();"></s:button>
		</s:row>
	</s:form>
</s:row>
<s:row>
	<s:tabpanel id="tabp">
		<s:form label="单据再现抬头提取sql" active="true" id="form1">
			<s:row>
				<s:textarea id="q1" name="initdjsql" cols="4" rows="5" >${pageParam.initdjsql}</s:textarea>
			</s:row>
		</s:form>
	<%-- 	<s:form label="单据再现明显提取sql" id="form2">
			<s:row>
				<s:textarea id="q2"  name="querydjmxsql" cols="4" rows="5" value="${pageParam.querydjmxsql}"></s:textarea>
			</s:row>
		</s:form> --%>
		<s:form label="单据打印后执行sql" id="form3">
			<s:row>
				<s:textarea id="q3" name="djprintsql" cols="4" rows="5">${pageParam.djprintsql}</s:textarea>
			</s:row>
		</s:form>
		<s:form label="附加明细提取sql" id="form4">
			<s:row>
				<s:textarea id="q4" name="othermxsql" cols="4" rows="5">${pageParam.othermxsql}</s:textarea>
			</s:row>
		</s:form>
	</s:tabpanel>
</s:row>
</s:page>
<script type="text/javascript">
function save(){
	var d = $("#setFrom").getData();
	/* var d1 = $("#form1").getData();
	var d2 = $("#form2").getData();
	var d3 = $("#form3").getData();
	var d4 = $("#form4").getData(); */
	var d1 = $("#q1").getData();
	var d3 = $("#q3").getData();
	var d4 = $("#q4").getData();
	d.initdjsql = d1.initdjsql;
	d.djprintsql = d3.djprintsql;
	d.othermxsql = d4.othermxsql;
	$.closeModal(d);
}
function returnback(){
	$.closeModal(false);
}
</script>
