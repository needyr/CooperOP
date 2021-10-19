<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="shenf_jg_mz" init-action="" description="门诊审方结果导出" system_product_code="ipc" title="审方结果导出" remark="审方结果导出" flag="chart" type="chart"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("ipc.chart.chart.shenf_jg_mz", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("ipc.chart.chart.shenf_jg_mz", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "ipc.chart.chart.shenf_jg_mz", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("ipc.chart.chart.shenf_jg_mz", null);
	}
	Map<String, Object> pageParam = (Map<String, Object>)pageContext.getAttribute("pageParam"); 
	Iterator<String> it1 = pageParam.keySet().iterator(); 
	while (it1.hasNext()) {   
		String key1 = it1.next();  
		pageContext.setAttribute(key1, pageParam.get(key1));   
	}  
	Iterator<String> it = a.keySet().iterator(); 
	while (it.hasNext()) {   
		String key = it.next();  
		pageContext.setAttribute(key, a.get(key));   
	}  
%>
	 <c:if test="${not empty param.fromtable }">  
<input type="hidden" name="fromtable" value="${param.fromtable}">
	 </c:if> 
	 <c:if test="${not empty param.ptableid }">  
<input type="hidden" name="ptableid" value="${param.ptableid}">
	 </c:if> 
	 <c:if test="${not empty param.p_pageid }">  
<input type="hidden" name="p_pageid" value="${param.p_pageid}">
	 </c:if> 
	 <c:if test="${not empty param.p_dj_sn }">  
<input type="hidden" name="p_dj_sn" value="${param.p_dj_sn}">
	 </c:if> 
	 <c:if test="${not empty param.p_dj_sort }">  
<input type="hidden" name="p_dj_sort" value="${param.p_dj_sort}">
	 </c:if> 
	 <c:if test="${not empty param.p_gzid }">  
<input type="hidden" name="p_gzid" value="${param.p_gzid}">
	 </c:if> 
<input type="hidden" name="djbs" value="${djbs}">
<input type="hidden" name="_CRSID" value="${_CRSID}">
<input type="hidden" name="djlx" value="${djlx}">
<input type="hidden" name="gzid" value="${gzid}">
<input type="hidden" name="pageid" value="${pageid}">
<input type="hidden" name="company_id" value="${company_id}">
	 <c:if test="${empty param.ptableid }">  
<input type="hidden" name="task_id" value="${param.task_id}">
<input type="hidden" name="djbh" value="${djbh}">
<input type="hidden" name="clientid" value="${clientid}">
<input type="hidden" name="zhiyid" value="${zhiyid}">
<input type="hidden" name="username" value="${username}">
<input type="hidden" name="userid" value="${userid}">
<input type="hidden" name="uname" value="${uname}">
<input type="hidden" name="lgnname" value="${lgnname}">
<input type="hidden" name="rq" value="${rq}">
<input type="hidden" name="kaiprq" value="${kaiprq}">
<input type="hidden" name="riqi" value="${riqi}">
<input type="hidden" name="ontime" value="${ontime}">
<input type="hidden" name="jigid" value="${jigid}">
<input type="hidden" name="jigname" value="${jigname}">
<input type="hidden" name="dep_code" value="${dep_code}">
<input type="hidden" name="dep_name" value="${dep_name}">
<input type="hidden" name="xaxis" value="${pageParam.xaxis}">
	 </c:if> 
<s:row><s:form label="统计条件" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="Y"><s:row type="frow"><s:datefield label="起用日期" cols="1" name="start_date" value="${empty start_date ? '' : start_date}" placeholder="" fdtype="整数" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="true" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="" precision=""></s:datefield>
<s:datefield label="截止时间" cols="1" name="end_date" value="${empty end_date ? rq : end_date}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="true" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
</s:row>
<s:toolbar><s:button label="查询" icon="icon-cursor" nextfocusfield="" action="querymx" color=""></s:button>
<s:button label="导出EXCEL" icon="icon-shuffle" size="btn-sm" nextfocusfield="" action="exportexcel" color=""></s:button>
</s:toolbar>
</s:form>
</s:row>
<s:row><s:table label="门诊审方结果查询导出" cols="4" color="grey-silver" isdesign_="Y" tableid="shengfang_jg2" icon="" djselect="" subpage_name="" autoload="false" limit="50" sort="false" app_table="N" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="level" value="${empty level ? '' : level}" fdtype="" format="" dictionary="" label="拦截等级" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="80" fieldorder="0" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_name" value="${empty drug_name ? '' : drug_name}" fdtype="" format="" dictionary="" label="医嘱名称" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="1" is_href="" app_field=""></s:table.field>
<s:table.field name="orderd_by" value="${empty orderd_by ? '' : orderd_by}" fdtype="" format="" dictionary="" label="部门" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="2" is_href="" app_field=""></s:table.field>
<s:table.field name="doctor" value="${empty doctor ? '' : doctor}" fdtype="" format="" dictionary="" label="医师姓名" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="3" is_href="" app_field=""></s:table.field>
<s:table.field name="patient_id" value="$[patient_id]"  fdtype="" format="" dictionary="" label="患者id" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="4" is_href="hospital_common.patient.index" app_field="" datatype="template"><s:textfield readonly="true" name="patient_id" value="$[patient_id]"  fdtype="" format="" dictionary="" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="4" is_href="hospital_common.patient.index" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="description" value="${empty description ? '' : description}" fdtype="" format="" dictionary="" label="违规说明" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="320" fieldorder="5" is_href="" app_field=""></s:table.field>
<s:table.field name="reference" value="${empty reference ? '' : reference}" fdtype="" format="" dictionary="" label="来源" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="220" fieldorder="6" is_href="" app_field=""></s:table.field>
<s:table.field name="shl" value="${empty shl ? '' : shl}" fdtype="" format="" dictionary="" label="数量合计" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="10" fieldorder="7" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
</s:row>
</s:page>
