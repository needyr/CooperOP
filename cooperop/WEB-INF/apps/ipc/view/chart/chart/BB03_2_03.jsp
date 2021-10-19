<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="BB03_2_03" init-action="" description="审查药师统计[下钻]->科室->医生->审查记录" system_product_code="ipc" title="" remark="" flag="chart" type="chart"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("ipc.chart.chart.BB03_2_03", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("ipc.chart.chart.BB03_2_03", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "ipc.chart.chart.BB03_2_03", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("ipc.chart.chart.BB03_2_03", null);
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
<s:row><s:form label="选择条件" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="Y"><s:row type="frow"><s:textfield label="医生姓名" cols="1" name="doctor_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="50" precision="">${empty doctor_name ? '' : doctor_name}</s:textfield>
<s:textfield color="grey" label="医生编号" name="doctor_no" placeholder="" fdtype="字符" cols="1" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="20" precision="">${empty doctor_no ? '' : doctor_no}</s:textfield>
<s:radio label="" cols="1" name="d_type" value="${empty d_type ? '' : d_type}" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="全部" contentIndex="0" contentvalue="0" maxlength="20" precision=""><s:option label="全部" contentIndex="0" value="0"></s:option>
<s:option label="住院" contentIndex="1" value="1"></s:option>
<s:option label="门诊" contentIndex="2" value="2"></s:option>
<s:option label="急诊" contentIndex="3" value="3"></s:option>
</s:radio>
</s:row>
<s:toolbar><s:button label="查询" icon="icon-cursor" nextfocusfield="" action="querymx" color=""></s:button>
</s:toolbar>
</s:form>
</s:row>
<s:row ishidden="ishidden"><s:form label="表单" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4"><s:row type="frow"><s:datefield label="开始日期" cols="1" name="start_date" value="${empty start_date ? '' : start_date}" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="" precision=""></s:datefield>
<s:datefield label="结束日期" cols="1" name="end_date" value="${empty end_date ? '' : end_date}" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="" precision=""></s:datefield>
<s:textfield label="科室代码" cols="1" name="dept_code" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="20" precision="">${empty dept_code ? '' : dept_code}</s:textfield>
<s:textfield label="药师ID" cols="1" name="yaoshi_id" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="20" precision="">${empty yaoshi_id ? '' : yaoshi_id}</s:textfield>
<s:textfield label="药师姓名" cols="1" name="yaoshi_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="50" precision="">${empty yaoshi_name ? '' : yaoshi_name}</s:textfield>
</s:row>
</s:form>
</s:row>
<s:row><s:table label="审查药师统计[下钻]->科室->医生->审查记录" cols="4" color="grey-silver" isdesign_="Y" tableid="04" icon="glyphicon glyphicon-sort-by-attributes" djselect="" subpage_name="" autoload="true" sort="false" limit="25" app_table="N" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="create_time" value="${empty create_time ? '' : create_time}" fdtype="字符" format="" dictionary="" label="提交审方时间" size="50" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="d_type" value="${empty d_type ? '' : d_type}" fdtype="字符" format="" dictionary="" label="开单类型" size="50" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="150" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="patient_name" value="$[patient_name]"  fdtype="字符" format="" dictionary="" label="患者" size="128" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="150" fieldorder="" is_href="hospital_common.patient.index" app_field="" datatype="template"><s:textfield readonly="true" name="patient_name" value="$[patient_name]"  fdtype="字符" format="" dictionary="" size="128" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="150" fieldorder="" is_href="hospital_common.patient.index" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="dept_name" value="${empty dept_name ? '' : dept_name}" fdtype="字符" format="" dictionary="" label="开嘱科室" size="50" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="150" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="doctorname" value="${empty doctorname ? '' : doctorname}" fdtype="字符" format="" dictionary="" label="开嘱医生" size="50" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="150" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="p_type" value="${empty p_type ? '' : p_type}" fdtype="字符" format="" dictionary="" label="审查类型" size="20" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="150" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="check_results_message" value="${empty check_results_message ? '' : check_results_message}" fdtype="字符" format="" dictionary="" label="审方结果" size="100" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="300" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="yaoshi_name" value="${empty yaoshi_name ? '' : yaoshi_name}" fdtype="字符" format="" dictionary="" label="审方药师" size="50" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="pharmacist_exam_time" value="${empty pharmacist_exam_time ? '' : pharmacist_exam_time}" fdtype="字符" format="" dictionary="" label="药师审查时间" size="23" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
</s:row>
</s:page>
