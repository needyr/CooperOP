<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="JCX033" init-action="" description="HIS病人住院主记录" system_product_code="hospital_common" title="" remark="" flag="q" type="query"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("hospital_common.query.q.JCX033", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("hospital_common.query.q.JCX033", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "hospital_common.query.q.JCX033", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("hospital_common.query.q.JCX033", null);
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
<s:row><s:form label="HIS病人住院主记录" icon="icon-book-open" color="red" border="1" cols="4" istoorbar="Y" app_collapsed="N"><s:row type="frow"><s:textfield label="病人标识号" cols="1" name="patient_id" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="32" precision="">${empty patient_id ? '' : patient_id}</s:textfield>
<s:textfield color="grey" label="病人姓名" name="patient_name" placeholder="" fdtype="字符" cols="1" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="1280" precision="">${empty patient_name ? '' : patient_name}</s:textfield>
<s:textfield label="本次住院标识" cols="1" name="visit_id" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="32" precision="">${empty visit_id ? '' : visit_id}</s:textfield>
</s:row>
<s:row type="frow"><s:textfield label="科室名称" cols="1" name="dept_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_name ? '' : dept_name}</s:textfield>
<s:textfield label="科室代码" cols="1" name="dept_code" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_code ? '' : dept_code}</s:textfield>
</s:row>
<s:toolbar><s:button label="查询" icon="icon-control-play" nextfocusfield="" action="querymx" color=""></s:button>
</s:toolbar>
<s:row type="frow"><s:select label="科室类型" cols="1" name="d_type" value="${empty d_type ? '' : d_type}" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="全部" contentIndex="0" contentvalue="0" maxlength="50" precision=""><s:option label="全部" contentIndex="0" value="0"></s:option>
<s:option label="住院" contentIndex="1" value="1"></s:option>
<s:option label="门诊" contentIndex="2" value="2"></s:option>
<s:option label="急诊" contentIndex="3" value="3"></s:option>
</s:select>
<s:select label="医嘱/处方" cols="1" name="p_type" value="${empty p_type ? '' : p_type}" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="全部" contentIndex="0" contentvalue="0" maxlength="8" precision=""><s:option label="全部" contentIndex="0" value="0"></s:option>
<s:option label="医嘱" contentIndex="1" value="1"></s:option>
<s:option label="处方" contentIndex="2" value="2"></s:option>
</s:select>
</s:row>
</s:form>
<s:table label="HIS病人住院主记录" cols="4" color="grey-silver" isdesign_="Y" tableid="JCX033" icon="" djselect="" tablekey="" sort="false" autoload="false" fields="" limit="25" istoorbar="N" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="p_key" value="$[p_key]"  fdtype="字符" format="" dictionary="" label="主键" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="p_key" value="$[p_key]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="patient_id" value="$[patient_id]"  fdtype="字符" format="" dictionary="" label="病人标识号" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="patient_id" value="$[patient_id]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="visit_id" value="$[visit_id]"  fdtype="字符" format="" dictionary="" label="本次住院标识" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="visit_id" value="$[visit_id]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="dept_in" value="$[dept_in]"  fdtype="字符" format="" dictionary="" label="入院科室编码" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="dept_in" value="$[dept_in]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="admission_datetime" value="$[admission_datetime]"  fdtype="字符" format="" dictionary="" label="入院日期及时间" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="admission_datetime" value="$[admission_datetime]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="charge_type" value="$[charge_type]"  fdtype="字符" format="" dictionary="" label="费别" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="charge_type" value="$[charge_type]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="discharge_disposition" value="$[discharge_disposition]"  fdtype="字符" format="" dictionary="" label="出院方式" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="discharge_disposition" value="$[discharge_disposition]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="dept_discharge" value="$[dept_discharge]"  fdtype="字符" format="" dictionary="" label="出院科室编码" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="dept_discharge" value="$[dept_discharge]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="discharge_datetime" value="$[discharge_datetime]"  fdtype="字符" format="" dictionary="" label="出院日期时间" size="19" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="discharge_datetime" value="$[discharge_datetime]"  fdtype="字符" format="" dictionary="" size="19" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="occupation" value="$[occupation]"  fdtype="字符" format="" dictionary="" label="职业" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="occupation" value="$[occupation]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="duty" value="$[duty]"  fdtype="字符" format="" dictionary="" label="勤务" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="duty" value="$[duty]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="weight" value="$[weight]"  fdtype="实数" format="" dictionary="" label="体重" size="14" maxlength="" digitsize="2" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="weight" value="$[weight]"  fdtype="实数" format="" dictionary="" size="14" maxlength="" digitsize="2" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="height" value="$[height]"  fdtype="实数" format="" dictionary="" label="身高" size="14" maxlength="" digitsize="2" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="height" value="$[height]"  fdtype="实数" format="" dictionary="" size="14" maxlength="" digitsize="2" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="insurance_type" value="$[insurance_type]"  fdtype="字符" format="" dictionary="" label="医保类别" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="insurance_type" value="$[insurance_type]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="insurance_no" value="$[insurance_no]"  fdtype="字符" format="" dictionary="" label="医疗保险号" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="insurance_no" value="$[insurance_no]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="work_unit" value="$[work_unit]"  fdtype="字符" format="" dictionary="" label="工作单位" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="work_unit" value="$[work_unit]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="top_unit" value="$[top_unit]"  fdtype="字符" format="" dictionary="" label="隶属大单位" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="top_unit" value="$[top_unit]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="mailing_address" value="$[mailing_address]"  fdtype="字符" format="" dictionary="" label="通信地址" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="mailing_address" value="$[mailing_address]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="patient_class" value="$[patient_class]"  fdtype="字符" format="" dictionary="" label="入院方式" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="patient_class" value="$[patient_class]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="admission_cause" value="$[admission_cause]"  fdtype="字符" format="" dictionary="" label="住院原因" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="admission_cause" value="$[admission_cause]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="pat_adm_condition" value="$[pat_adm_condition]"  fdtype="字符" format="" dictionary="" label="入院病情" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="pat_adm_condition" value="$[pat_adm_condition]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="blood_type" value="$[blood_type]"  fdtype="字符" format="" dictionary="" label="血型" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="blood_type" value="$[blood_type]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="alergy_drugs" value="$[alergy_drugs]"  fdtype="字符" format="" dictionary="" label="过敏药物" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="alergy_drugs" value="$[alergy_drugs]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="adverse_reaction_drugs" value="$[adverse_reaction_drugs]"  fdtype="字符" format="" dictionary="" label="不良反应药物" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="adverse_reaction_drugs" value="$[adverse_reaction_drugs]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="infusion_react_times   infusion_react_times" value="$[infusion_react_times   infusion_react_times]"  fdtype="整数" format="" dictionary="" label="输液反应次数" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="infusion_react_times   infusion_react_times" value="$[infusion_react_times   infusion_react_times]"  fdtype="整数" format="" dictionary="" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
</s:table.fields>
</s:table>
</s:row>
</s:page>
