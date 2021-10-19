<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="JCX038" init-action="" description="PACS病人检查主记录" system_product_code="hospital_common" title="" remark="" flag="q" type="query"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("hospital_common.query.q.JCX038", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("hospital_common.query.q.JCX038", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "hospital_common.query.q.JCX038", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("hospital_common.query.q.JCX038", null);
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
<s:row><s:form label="PACS病人检查主记录" icon="icon-book-open" color="red" border="1" cols="4" istoorbar="N" app_collapsed="N"><s:row type="frow"><s:textfield label="病人标识号" cols="1" name="patient_id" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="32" precision="">${empty patient_id ? '' : patient_id}</s:textfield>
<s:textfield label="姓名" cols="1" name="name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty name ? '' : name}</s:textfield>
</s:row>
<s:row type="frow"><s:button color="grey" label="查询" nextfocusfield="" icon="glyphicon glyphicon-play-circle" action="querymx"></s:button>
</s:row>
</s:form>
<s:table label="PACS病人检查主记录" cols="4" color="grey-silver" isdesign_="Y" tableid="JCX038" icon="" djselect="" tablekey="" sort="false" autoload="false" fields="" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="p_key" value="$[p_key]"  fdtype="字符" format="" dictionary="" label="主键" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="p_key" value="$[p_key]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="fk" value="$[fk]"  fdtype="字符" format="" dictionary="" label="外键" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="fk" value="$[fk]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="exam_no" value="$[exam_no]"  fdtype="字符" format="" dictionary="" label="申请序号" size="64" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="exam_no" value="$[exam_no]"  fdtype="字符" format="" dictionary="" size="64" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="patient_id" value="$[patient_id]"  fdtype="字符" format="" dictionary="" label="病人标识号" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="patient_id" value="$[patient_id]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="visit_id" value="$[visit_id]"  fdtype="字符" format="" dictionary="" label="本次住院标识" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="visit_id" value="$[visit_id]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="name" value="$[name]"  fdtype="字符" format="" dictionary="" label="姓名" size="128" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="name" value="$[name]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="sex" value="$[sex]"  fdtype="字符" format="" dictionary="" label="性别" size="4" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="sex" value="$[sex]"  fdtype="字符" format="" dictionary="" size="4" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="date_of_birth" value="$[date_of_birth]"  fdtype="字符" format="" dictionary="" label="出生日期" size="10" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="date_of_birth" value="$[date_of_birth]"  fdtype="字符" format="" dictionary="" size="10" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="req_date_time" value="$[req_date_time]"  fdtype="字符" format="" dictionary="" label="申请日期及时间" size="" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="req_date_time" value="$[req_date_time]"  fdtype="字符" format="" dictionary="" size="" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="req_dept" value="$[req_dept]"  fdtype="字符" format="" dictionary="" label="申请科室" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="req_dept" value="$[req_dept]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="req_physician" value="$[req_physician]"  fdtype="字符" format="" dictionary="" label="申请医生" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="req_physician" value="$[req_physician]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="req_memo" value="$[req_memo]"  fdtype="字符" format="" dictionary="" label="申请备注" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="req_memo" value="$[req_memo]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="exam_class" value="$[exam_class]"  fdtype="字符" format="" dictionary="" label="检查类别" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="exam_class" value="$[exam_class]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="exam_sub_class" value="$[exam_sub_class]"  fdtype="字符" format="" dictionary="" label="检查子类" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="exam_sub_class" value="$[exam_sub_class]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="clin_symp" value="$[clin_symp]"  fdtype="文本" format="" dictionary="" label="临床症状" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="clin_symp" value="$[clin_symp]"  fdtype="文本" format="" dictionary="" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="clin_diag" value="$[clin_diag]"  fdtype="字符" format="" dictionary="" label="临床诊断" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="clin_diag" value="$[clin_diag]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="phys_sign" value="$[phys_sign]"  fdtype="文本" format="" dictionary="" label="体征" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="phys_sign" value="$[phys_sign]"  fdtype="文本" format="" dictionary="" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="relevant_lab_test" value="$[relevant_lab_test]"  fdtype="字符" format="" dictionary="" label="相关化验结果" size="256" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="relevant_lab_test" value="$[relevant_lab_test]"  fdtype="字符" format="" dictionary="" size="256" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="relevant_diag" value="$[relevant_diag]"  fdtype="文本" format="" dictionary="" label="其他诊断" size="" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="relevant_diag" value="$[relevant_diag]"  fdtype="文本" format="" dictionary="" size="" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="performed_by" value="$[performed_by]"  fdtype="字符" format="" dictionary="" label="执行科室" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="performed_by" value="$[performed_by]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="patient_source" value="$[patient_source]"  fdtype="字符" format="" dictionary="" label="病人来源" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="patient_source" value="$[patient_source]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="notice" value="$[notice]"  fdtype="文本" format="" dictionary="" label="注意事项" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="notice" value="$[notice]"  fdtype="文本" format="" dictionary="" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="exam_date_time" value="$[exam_date_time]"  fdtype="字符" format="" dictionary="" label="检查日期及时间" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="exam_date_time" value="$[exam_date_time]"  fdtype="字符" format="" dictionary="" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="report_date_time" value="$[report_date_time]"  fdtype="字符" format="" dictionary="" label="报告日期及时间" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="report_date_time" value="$[report_date_time]"  fdtype="字符" format="" dictionary="" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="reporter" value="$[reporter]"  fdtype="字符" format="" dictionary="" label="报告者" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="reporter" value="$[reporter]"  fdtype="字符" format="" dictionary="" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="result_status" value="$[result_status]"  fdtype="字符" format="" dictionary="" label="结果状态" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="result_status" value="$[result_status]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
</s:table.fields>
</s:table>
</s:row>
</s:page>
