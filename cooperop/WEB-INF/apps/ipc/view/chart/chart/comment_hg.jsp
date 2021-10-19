<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="comment_hg" init-action="" description="科室门（急）诊处方合格率统计" system_product_code="ipc" title="" remark="" flag="chart" type="chart"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("ipc.chart.chart.comment_hg", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("ipc.chart.chart.comment_hg", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "ipc.chart.chart.comment_hg", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("ipc.chart.chart.comment_hg", null);
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
<s:row><s:form label="表单" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="Y"><s:row type="frow"><s:datefield label="时间" cols="1" name="start_time" value="${empty start_time ? '' : start_time}" placeholder="" fdtype="时间" defaultValue="" format="yyyy-MM" nextfocusfield="" islabel="否" readonly="false" required="true" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="20" precision=""></s:datefield>
<s:autocomplete label="科室" cols="1" name="dept_code" value="${empty dept_code ? '' : dept_code}" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" autocomplete_initaction="setting.dep.querydep" tableid="" expressions="" maxlength="255" precision="" text="${dept_codename}"><s:option label="$[name]" value="$[id]" optiontext="$[name]">$[name]</s:option>
</s:autocomplete>
</s:row>
<s:toolbar><s:button label="查询" icon="fa fa-search" nextfocusfield="" action="querymx" color=""></s:button>
</s:toolbar>
</s:form>
</s:row>
<s:row><s:table label="科室门（急）诊处方合格率统计" cols="4" color="grey-silver" isdesign_="Y" tableid="table" icon="" autoload="false" sort="false" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="dept" value="${empty dept ? '' : dept}" fdtype="字符" format="" label="科室" size="" maxlength="128" digitsize="" precision="" align="left" fieldorder="" app_field=""></s:table.field>
<s:table.field name="samplecount" value="${empty samplecount ? '' : samplecount}" fdtype="字符" format="" label="抽取处方数" size="" maxlength="255" digitsize="" precision="" align="left" fieldorder="" app_field=""></s:table.field>
<s:table.field name="notcount" value="${empty notcount ? '' : notcount}" fdtype="字符" format="" label="不合格处方数" size="" maxlength="255" digitsize="" precision="" align="left" fieldorder="" app_field=""></s:table.field>
<s:table.field name="rate" value="${empty rate ? '' : rate}" fdtype="字符" format="##.##%" label="合格率" size="" maxlength="10" digitsize="" precision="" align="left" fieldorder="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
</s:row>
<s:row><s:chart label="表单" cols="2" color="grey-silver" chart_flag="333" tableid="222" chart_height="" drill_chart="" autoload="false" welcome_func="N" group_field="" g_name="qw" flag="333" yaxis="[]"></s:chart>
</s:row>
</s:page>
