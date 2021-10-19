<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="JCX046" init-action="" description="病人手术记录" system_product_code="hospital_common" title="" remark="" flag="q" type="query"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("hospital_common.query.q.JCX046", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("hospital_common.query.q.JCX046", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "hospital_common.query.q.JCX046", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("hospital_common.query.q.JCX046", null);
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
<s:row><s:form label="病人手术记录" icon="icon-book-open" color="red" border="1" cols="4" istoorbar="N" app_collapsed="N"><s:row type="frow"><s:textfield label="病人标识号" cols="1" name="patient_id" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="32" precision="">${empty patient_id ? '' : patient_id}</s:textfield>
</s:row>
<s:row type="frow"><s:button label="查询" cols="" nextfocusfield="" icon="glyphicon glyphicon-play-circle" action="querymx" color=""></s:button>
</s:row>
</s:form>
<s:table label="病人手术记录" cols="4" color="grey-silver" isdesign_="Y" tableid="JCX046" icon="" djselect="" tablekey="" sort="false" autoload="false" fields="" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="p_key" value="$[p_key]"  fdtype="字符" format="" dictionary="" label="主键" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="p_key" value="$[p_key]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="fk" value="$[fk]"  fdtype="字符" format="" dictionary="" label="外键" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="fk" value="$[fk]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="patient_id" value="$[patient_id]"  fdtype="字符" format="" dictionary="" label="病人标识号" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="patient_id" value="$[patient_id]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="visit_id" value="$[visit_id]"  fdtype="字符" format="" dictionary="" label="本次住院标识" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="visit_id" value="$[visit_id]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="oper_id" value="$[oper_id]"  fdtype="字符" format="" dictionary="" label="本次手术标识" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="oper_id" value="$[oper_id]"  fdtype="字符" format="" dictionary="" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="dept_stayed" value="$[dept_stayed]"  fdtype="字符" format="" dictionary="" label="所在科室" size="128" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="dept_stayed" value="$[dept_stayed]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="operating_dept" value="$[operating_dept]"  fdtype="字符" format="" dictionary="" label="手术科室" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="operating_dept" value="$[operating_dept]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="operator" value="$[operator]"  fdtype="字符" format="" dictionary="" label="挂号员" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="operator" value="$[operator]"  fdtype="字符" format="" dictionary="" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="start_date_time" value="$[start_date_time]"  fdtype="字符" format="" dictionary="" label="手术开始日期及时间" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="start_date_time" value="$[start_date_time]"  fdtype="字符" format="" dictionary="" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="end_date_time" value="$[end_date_time]"  fdtype="字符" format="" dictionary="" label="手术结束日期及时间" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="end_date_time" value="$[end_date_time]"  fdtype="字符" format="" dictionary="" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="smooth_indicator" value="$[smooth_indicator]"  fdtype="字符" format="" dictionary="" label="手术过程顺利标志" size="8" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="smooth_indicator" value="$[smooth_indicator]"  fdtype="字符" format="" dictionary="" size="8" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="operating_room" value="$[operating_room]"  fdtype="字符" format="" dictionary="" label="手术室" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="operating_room" value="$[operating_room]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="operating_room_no" value="$[operating_room_no]"  fdtype="字符" format="" dictionary="" label="手术间" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="operating_room_no" value="$[operating_room_no]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="diag_before_operation" value="$[diag_before_operation]"  fdtype="字符" format="" dictionary="" label="术前诊断" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="diag_before_operation" value="$[diag_before_operation]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="patient_condition" value="$[patient_condition]"  fdtype="字符" format="" dictionary="" label="病情说明" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="patient_condition" value="$[patient_condition]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="operation_scale" value="$[operation_scale]"  fdtype="字符" format="" dictionary="" label="手术等级" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="operation_scale" value="$[operation_scale]"  fdtype="字符" format="" dictionary="" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="diag_after_operation" value="$[diag_after_operation]"  fdtype="字符" format="" dictionary="" label="术后诊断" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="diag_after_operation" value="$[diag_after_operation]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="emergency_indicator" value="$[emergency_indicator]"  fdtype="字符" format="" dictionary="" label="急诊标志" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="emergency_indicator" value="$[emergency_indicator]"  fdtype="字符" format="" dictionary="" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="operation_class" value="$[operation_class]"  fdtype="字符" format="" dictionary="" label="手术类型" size="15" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="operation_class" value="$[operation_class]"  fdtype="字符" format="" dictionary="" size="15" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="first_assistant" value="$[first_assistant]"  fdtype="字符" format="" dictionary="" label="Ⅰ助" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="first_assistant" value="$[first_assistant]"  fdtype="字符" format="" dictionary="" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="second_assistant" value="$[second_assistant]"  fdtype="字符" format="" dictionary="" label="Ⅱ助" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="second_assistant" value="$[second_assistant]"  fdtype="字符" format="" dictionary="" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="anesthesia_method" value="$[anesthesia_method]"  fdtype="字符" format="" dictionary="" label="麻醉方法" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="anesthesia_method" value="$[anesthesia_method]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="anesthesia_doctor" value="$[anesthesia_doctor]"  fdtype="字符" format="" dictionary="" label="麻醉医师" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="anesthesia_doctor" value="$[anesthesia_doctor]"  fdtype="字符" format="" dictionary="" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
</s:table.fields>
</s:table>
</s:row>
</s:page>
