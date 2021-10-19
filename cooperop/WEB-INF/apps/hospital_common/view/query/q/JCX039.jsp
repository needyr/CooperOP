<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="JCX039" init-action="" description="PACS病人检查项目记录" system_product_code="hospital_common" title="" remark="" flag="q" type="query"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("hospital_common.query.q.JCX039", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("hospital_common.query.q.JCX039", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "hospital_common.query.q.JCX039", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("hospital_common.query.q.JCX039", null);
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
<s:row><s:form label="PACS病人检查项目记录" icon="icon-book-open" color="red" border="1" cols="4" istoorbar="N" app_collapsed="N"><s:row type="frow"><s:textfield label="申请序号" cols="1" name="exam_no" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="64" precision="">${empty exam_no ? '' : exam_no}</s:textfield>
</s:row>
<s:row type="frow"><s:button color="grey" label="查询" nextfocusfield="" icon="glyphicon glyphicon-play-circle" action="querymx"></s:button>
</s:row>
</s:form>
<s:table label="PACS病人检查项目记录" cols="4" color="grey-silver" isdesign_="Y" tableid="JCX039" icon="" djselect="" tablekey="" sort="false" autoload="false" fields="" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="fk" value="$[fk]"  fdtype="字符" format="" dictionary="" label="外键" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="fk" value="$[fk]"  fdtype="字符" format="" dictionary="" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="exam_no" value="$[exam_no]"  fdtype="字符" format="" dictionary="" label="申请序号" size="64" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="exam_no" value="$[exam_no]"  fdtype="字符" format="" dictionary="" size="64" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="exam_item_no" value="$[exam_item_no]"  fdtype="字符" format="" dictionary="" label="项目序号" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="exam_item_no" value="$[exam_item_no]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="exam_item" value="$[exam_item]"  fdtype="字符" format="" dictionary="" label="检查项目" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="exam_item" value="$[exam_item]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="exam_item_code" value="$[exam_item_code]"  fdtype="字符" format="" dictionary="" label="项目代码" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="" datatype="template"><s:textfield readonly="true" name="exam_item_code" value="$[exam_item_code]"  fdtype="字符" format="" dictionary="" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" app_field="">null</s:textfield>
</s:table.field>
</s:table.fields>
</s:table>
</s:row>
</s:page>
