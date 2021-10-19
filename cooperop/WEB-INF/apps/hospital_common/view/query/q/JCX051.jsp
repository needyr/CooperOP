<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="JCX051" init-action="" description="门诊医嘱处方明细记录" system_product_code="hospital_common" title="" remark="" flag="q" type="query"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("hospital_common.query.q.JCX051", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("hospital_common.query.q.JCX051", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "hospital_common.query.q.JCX051", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("hospital_common.query.q.JCX051", null);
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
<s:row><s:form label="门诊医嘱处方明细记录" icon="icon-book-open" color="red" border="1" cols="4" istoorbar="N" app_collapsed="N"><s:row type="frow"><s:textfield label="就诊序号" cols="1" name="visit_no" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="64" precision="">${empty visit_no ? '' : visit_no}</s:textfield>
<s:textfield label="就诊日期" cols="1" name="visit_date" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="" precision="">${empty visit_date ? '' : visit_date}</s:textfield>
</s:row>
<s:row type="frow"><s:button label="查询" cols="" nextfocusfield="" icon="glyphicon glyphicon-play-circle" action="querymx" color=""></s:button>
</s:row>
</s:form>
<s:table label="HIS门诊医嘱处方明细记录" cols="4" color="grey-silver" isdesign_="Y" tableid="JCX051" icon="" djselect="" tablekey="" sort="false" autoload="false" fields="" limit="25" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="p_key" value="$[p_key]"  fdtype="字符" format="" dictionary="" label="主键" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="p_key" value="$[p_key]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="fk_1" value="$[fk_1]"  fdtype="字符" format="" dictionary="" label="关联外键1" size="64" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="fk_1" value="$[fk_1]"  fdtype="字符" format="" dictionary="" size="64" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="fk_2" value="$[fk_2]"  fdtype="字符" format="" dictionary="" label="关联外键2" size="64" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="fk_2" value="$[fk_2]"  fdtype="字符" format="" dictionary="" size="64" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="visit_date" value="$[visit_date]"  fdtype="字符" format="" dictionary="" label="就诊日期" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="visit_date" value="$[visit_date]"  fdtype="字符" format="" dictionary="" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="visit_no" value="$[visit_no]"  fdtype="字符" format="" dictionary="" label="就诊序号" size="64" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="visit_no" value="$[visit_no]"  fdtype="字符" format="" dictionary="" size="64" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="serial_no" value="$[serial_no]"  fdtype="字符" format="" dictionary="" label="序号" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="serial_no" value="$[serial_no]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="presc_no" value="$[presc_no]"  fdtype="字符" format="" dictionary="" label="处方序号" size="64" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="presc_no" value="$[presc_no]"  fdtype="字符" format="" dictionary="" size="64" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="item_no" value="$[item_no]"  fdtype="字符" format="" dictionary="" label="项目序号" size="32" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="item_no" value="$[item_no]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="item_class" value="$[item_class]"  fdtype="字符" format="" dictionary="" label="项目类别" size="12" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="item_class" value="$[item_class]"  fdtype="字符" format="" dictionary="" size="12" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="drug_code" value="$[drug_code]"  fdtype="字符" format="" dictionary="" label="药品代码" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="drug_code" value="$[drug_code]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="drug_name" value="$[drug_name]"  fdtype="字符" format="" dictionary="" label="药品名称" size="128" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="drug_name" value="$[drug_name]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="drug_spec" value="$[drug_spec]"  fdtype="字符" format="" dictionary="" label="药品规格" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="drug_spec" value="$[drug_spec]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="firm_id" value="$[firm_id]"  fdtype="字符" format="" dictionary="" label="厂家标识" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="firm_id" value="$[firm_id]"  fdtype="字符" format="" dictionary="" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="units" value="$[units]"  fdtype="字符" format="" dictionary="" label="项目单位" size="" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="units" value="$[units]"  fdtype="字符" format="" dictionary="" size="" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="amount" value="$[amount]"  fdtype="字符" format="" dictionary="" label="数量" size="16" maxlength="" digitsize="4" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="amount" value="$[amount]"  fdtype="字符" format="" dictionary="" size="16" maxlength="" digitsize="4" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="dosage" value="$[dosage]"  fdtype="字符" format="" dictionary="" label="一次用量" size="16" maxlength="" digitsize="4" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="dosage" value="$[dosage]"  fdtype="字符" format="" dictionary="" size="16" maxlength="" digitsize="4" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="dosage_units" value="$[dosage_units]"  fdtype="字符" format="" dictionary="" label="用量单位" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="dosage_units" value="$[dosage_units]"  fdtype="字符" format="" dictionary="" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="administration" value="$[administration]"  fdtype="字符" format="" dictionary="" label="用药途径" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="administration" value="$[administration]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="frequency" value="$[frequency]"  fdtype="字符" format="" dictionary="" label="频次" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="frequency" value="$[frequency]"  fdtype="字符" format="" dictionary="" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="provided_indicator" value="$[provided_indicator]"  fdtype="字符" format="" dictionary="" label="自备标记" size="8" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="provided_indicator" value="$[provided_indicator]"  fdtype="字符" format="" dictionary="" size="8" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="costs" value="$[costs]"  fdtype="实数" format="" dictionary="" label="计价金额" size="8" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="costs" value="$[costs]"  fdtype="实数" format="" dictionary="" size="8" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="charges" value="$[charges]"  fdtype="实数" format="" dictionary="" label="应付费用" size="14" maxlength="" digitsize="2" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="charges" value="$[charges]"  fdtype="实数" format="" dictionary="" size="14" maxlength="" digitsize="2" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="charge_indicator" value="$[charge_indicator]"  fdtype="整数" format="" dictionary="" label="交费标志" size="4" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="charge_indicator" value="$[charge_indicator]"  fdtype="整数" format="" dictionary="" size="4" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="dispensary" value="$[dispensary]"  fdtype="字符" format="" dictionary="" label="摆药药局" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="dispensary" value="$[dispensary]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
</s:table.fields>
</s:table>
</s:row>
</s:page>
