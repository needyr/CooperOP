<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="JCX053" init-action="" description="HIS开单记录" system_product_code="hospital_common" title="" remark="" flag="q" type="query"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("hospital_common.query.q.JCX053", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("hospital_common.query.q.JCX053", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "hospital_common.query.q.JCX053", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("hospital_common.query.q.JCX053", null);
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
<s:row><s:form label="HIS开单记录" icon="icon-book-open" color="red" border="1" cols="4" istoorbar="N" app_collapsed="N"><s:row type="frow"><s:button label="查询" cols="" nextfocusfield="" icon="glyphicon glyphicon-play-circle" action="querymx" color=""></s:button>
</s:row>
</s:form>
<s:table label="HIS开单记录" cols="4" color="grey-silver" isdesign_="Y" tableid="JCX053" icon="" djselect="" tablekey="" sort="false" autoload="false" fields="" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="p_key" value="$[p_key]"  fdtype="字符" format="" dictionary="" label="主键" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="p_key" value="$[p_key]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="fk" value="$[fk]"  fdtype="字符" format="" dictionary="" label="外键" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="fk" value="$[fk]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="visit_date" value="$[visit_date]"  fdtype="字符" format="" dictionary="" label="就诊日期" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="visit_date" value="$[visit_date]"  fdtype="字符" format="" dictionary="" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="visit_no" value="$[visit_no]"  fdtype="字符" format="" dictionary="" label="就诊序号" size="64" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="visit_no" value="$[visit_no]"  fdtype="字符" format="" dictionary="" size="64" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="patient_id" value="$[patient_id]"  fdtype="字符" format="" dictionary="" label="病人标识号" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="patient_id" value="$[patient_id]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="presc_indicator" value="$[presc_indicator]"  fdtype="整数" format="" dictionary="" label="药品处方标志" size="4" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="presc_indicator" value="$[presc_indicator]"  fdtype="整数" format="" dictionary="" size="4" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="ordered_by_dept" value="$[ordered_by_dept]"  fdtype="字符" format="" dictionary="" label="开单科室" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="ordered_by_dept" value="$[ordered_by_dept]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="ordered_by_doctor" value="$[ordered_by_doctor]"  fdtype="字符" format="" dictionary="" label="开单医生" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="ordered_by_doctor" value="$[ordered_by_doctor]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="rcpt_no" value="$[rcpt_no]"  fdtype="字符" format="" dictionary="" label="收据号" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="rcpt_no" value="$[rcpt_no]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
</s:table.fields>
</s:table>
</s:row>
</s:page>
