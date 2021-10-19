<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="JCX059" init-action="" description="医生统计分析" system_product_code="ipc" title="科室点评统计" remark="" flag="q" type="query"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("ipc.query.q.JCX059", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("ipc.query.q.JCX059", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "ipc.query.q.JCX059", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("ipc.query.q.JCX059", null);
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
<s:row><s:form label="医生统计分析" icon="fa fa-list-alt" color="default" border="1" cols="4" istoorbar="N" app_collapsed="N"><s:row type="frow"><s:textfield label="医生名称" cols="1" name="name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="zl_select_JCX059" out_action="zl_select_JCX059" tableid="" expressions="" maxlength="128" precision="">${empty name ? '' : name}</s:textfield>
<s:datefield label="开始时间" cols="1" name="start_date" value="${empty start_date ? '' : start_date}" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
<s:datefield label="截止时间" cols="1" name="end_date" value="${empty end_date ? '' : end_date}" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
</s:row>
</s:form>
<s:table label="医生处方统计" cols="4" color="grey-silver" isdesign_="Y" tableid="JCX059" icon="glyphicon glyphicon-list-alt" djselect="" tablekey="" sort="true" autoload="false" fields="" limit="25" istoorbar="Y" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="doctor_no" fdtype="字符" format="" dictionary="" label="医生编号" size="" maxlength="" digitsize="" precision="" available="true" sort="false" controltype="" align="left" width="120" fieldorder="0" is_href="" app_field=""></s:table.field>
<s:table.field name="name" fdtype="字符" format="" dictionary="" label="医生名称" size="128" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="1" is_href="" app_field=""></s:table.field>
<s:table.field name="buheli1" fdtype="字符" format="" dictionary="" label="不合理处方数" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="80" fieldorder="2" is_href="" app_field=""></s:table.field>
<s:table.field name="heli1" fdtype="字符" format="" dictionary="" label="合理处方数" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="80" fieldorder="3" is_href="" app_field=""></s:table.field>
<s:table.field name="zengyi1" fdtype="字符" format="" dictionary="" label="争议处方数" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="80" fieldorder="4" is_href="" app_field=""></s:table.field>
<s:table.field name="chouquyizhushu1" fdtype="字符" format="" dictionary="" label="抽取处方数" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="80" fieldorder="5" is_href="" app_field=""></s:table.field>
<s:table.field name="yizhuhegelu" fdtype="字符" format="" dictionary="" label="处方合格率" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="80" fieldorder="6" is_href="" app_field=""></s:table.field>
<s:table.field name="comment_finish_time" fdtype="字符" format="" dictionary="" label="完成点评时间" size="100" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="7" is_href="" app_field=""></s:table.field>
</s:table.fields>
<s:toolbar><s:button label="查询" icon=" icon-magnifier" nextfocusfield="" action="querymx" color=""></s:button>
</s:toolbar>
</s:table>
</s:row>
</s:page>
