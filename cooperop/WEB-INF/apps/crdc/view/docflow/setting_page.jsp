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
			<s:textfield cols="2" label="运行窗口标题" name="title" value="${pageParam.runtitle }"></s:textfield>
		</s:row>
		<s:row>
			<s:textfield cols="2" label="备注" name="remark" value="${pageParam.remark }"></s:textfield>
			<s:button label="确定" color="green" onclick="save();"></s:button>
			<s:button label="取消" color="red" onclick="returnback();"></s:button>
		</s:row>
	</s:form>
</s:row>
<s:row>
	<s:tabpanel id="tabp">
		<s:form label="文档选择SQL" active="true" id="form1">
			<s:row>
				<s:textarea id="q1" name="othermxsql" placeholder="用于新建文档时获取可初始化的业务数据列表，返回记录必须包含所有主键字段" cols="4" rows="10" value="${pageParam.othermxsql}"></s:textarea>
			</s:row>
		</s:form>
		<s:form label="初始汇总SQL" id="form3">
			<s:row>
				<s:textarea id="q3" name="inithzsql" placeholder="用于新建文档时从业务数据中读取文档中非表格区域数据，条件是所有选择SQL中的字段，返回一条记录。" cols="4" rows="10" value="${pageParam.initdjsql}"></s:textarea>
			</s:row>
		</s:form>
	</s:tabpanel>
</s:row>
</s:page>
<script type="text/javascript">
function save(){
	var d = $("#setFrom").getData();
	d.initdjsql = "select * from dfl_"+d.flag+"_"+d.schemeid+" where djbh=:djbh";
	d.othermxsql = $("#q1").getData().othermxsql;
	d.inithzsql = $("#q1").getData().inithzsql;
	$.closeModal(d);
}
function returnback(){
	$.closeModal(false);
}
</script>
