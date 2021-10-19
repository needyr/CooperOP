<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="BB03" init-action="" description="前置审方统计报表" system_product_code="ipc" title="前置审方统计报表" remark="前置审方统计报表" flag="chart" type="chart"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("ipc.chart.chart.BB03", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("ipc.chart.chart.BB03", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "ipc.chart.chart.BB03", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("ipc.chart.chart.BB03", null);
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
<s:row><s:form label="前置审查统计分析" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="Y"><s:row type="frow"><s:datefield label="开始日期" cols="1" name="start_date" value="${empty start_date ? rq : start_date}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="0" readonly="false" required="0" encryption="0" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="" precision=""></s:datefield>
<s:datefield label="截止日期" cols="1" name="end_date" value="${empty end_date ? rq : end_date}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="0" readonly="false" required="0" encryption="0" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
<s:radio label="" cols="1" name="d_type" value="${empty d_type ? '0' : d_type}" placeholder="科室类型" fdtype="字符" defaultValue="0" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="全部" contentIndex="0" contentvalue="0" maxlength="50" precision=""><s:option label="全部" contentIndex="0" value="0"></s:option>
<s:option label="住院" contentIndex="1" value="1"></s:option>
<s:option label="门诊" contentIndex="2" value="2"></s:option>
<s:option label="急诊" contentIndex="3" value="3"></s:option>
</s:radio>
</s:row>
<s:row type="frow"><s:textfield label="科室代码" cols="1" name="dept_code" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="0" readonly="false" required="0" encryption="0" create_action="" modify_action="" enter_action="" dbl_action="zl_select_BB03_01" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_code ? '' : dept_code}</s:textfield>
<s:textfield label="科室名称" cols="1" name="dept_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_name ? '' : dept_name}</s:textfield>
</s:row>
<s:toolbar><s:button label="查询" icon="icon-cursor" nextfocusfield="" action="querymx" color=""></s:button>
</s:toolbar>
</s:form>
</s:row>
<s:row></s:row>
<s:row ishidden="ishidden"><s:tabpanel color="grey-silver" cols="4"><s:table label="01审查结果统计" cols="4" color="grey-silver" active="true" isdesign_="Y" tableid="01" icon="" djselect="" subpage_name="" autoload="true" toIndex="0" sort="false" myIndex="0" limit="25" totals="" fitwidth="false" app_table="N" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="check_results_message" value="$[check_results_message]"  fdtype="字符" format="" dictionary="" label="审查信息" size="128" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="check_results_message" value="$[check_results_message]"  fdtype="字符" format="" dictionary="" size="128" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="shl" value="$[shl]"  fdtype="实数" format="" dictionary="" label="数量" size="14" maxlength_show="" digitsize="3" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="shl" value="$[shl]"  fdtype="实数" format="" dictionary="" size="14" maxlength_show="" digitsize="3" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
</s:table.fields>
</s:table>
<s:table label="02审查药师统计" cols="4" color="grey-silver" active="false" isdesign_="Y" tableid="02" icon="" djselect="" subpage_name="" autoload="true" toIndex="1" sort="false" myIndex="1" limit="25" totals="" fitwidth="false" app_table="N" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="user_name" value="$[user_name]"  fdtype="字符" format="" dictionary="" label="用户名称" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="user_name" value="$[user_name]"  fdtype="字符" format="" dictionary="" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="shl" value="$[shl]"  fdtype="实数" format="" dictionary="" label="数量" size="14" maxlength_show="" digitsize="3" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="shl" value="$[shl]"  fdtype="实数" format="" dictionary="" size="14" maxlength_show="" digitsize="3" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
</s:table.fields>
</s:table>
<s:table label="03时间段审查报表" cols="4" color="grey-silver" active="false" isdesign_="Y" tableid="03" icon="" djselect="" subpage_name="" autoload="true" toIndex="2" sort="false" myIndex="2" limit="25" totals="" fitwidth="false" app_table="N" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="hour" value="$[hour]"  fdtype="字符" format="" dictionary="" label="小时" size="10" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="hour" value="$[hour]"  fdtype="字符" format="" dictionary="" size="10" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="shl" value="$[shl]"  fdtype="实数" format="" dictionary="" label="数量" size="14" maxlength_show="" digitsize="3" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="shl" value="$[shl]"  fdtype="实数" format="" dictionary="" size="14" maxlength_show="" digitsize="3" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="yaoshisc_shl" value="$[yaoshisc_shl]"  fdtype="整数" format="" dictionary="" label="药师审查数量" size="4" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="" datatype="template"><s:textfield readonly="true" name="yaoshisc_shl" value="$[yaoshisc_shl]"  fdtype="整数" format="" dictionary="" size="4" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="" app_field="">null</s:textfield>
</s:table.field>
</s:table.fields>
</s:table>
</s:tabpanel>
</s:row>
<s:row><s:chart label="审查统计" cols="2" color="grey-silver" chart_flag="BB03_TB01" tableid="01" chart_height="300" drill_chart="" drill_chart_name="" autoload="true" welcome_func="N" group_field="" g_name="check_results_message" sub_label="[更多]" sub_label_href="" flag="BB03_TB01" drill_tabopen="0" dync_sql="" yaxis="[{name=shl, label=数量, chart_type=pie}]"></s:chart>
<s:chart label="审查药师统计" cols="2" color="grey-silver" chart_flag="BB03_TB2" tableid="02" chart_height="300" drill_chart="" drill_chart_name="" autoload="true" welcome_func="N" group_field="" g_name="user_name" sub_label="" sub_label_href="" flag="BB03_TB2" drill_tabopen="0" dync_sql="" yaxis="[{name=shl, label=数量, chart_type=column}]"></s:chart>
</s:row>
<s:row><s:chart label="审查时间统计" cols="4" color="grey-silver" chart_flag="BB03_TB03" tableid="03" chart_height="300" drill_chart="" drill_chart_name="" autoload="true" welcome_func="N" group_field="" g_name="hour" sub_label="" sub_label_href="" flag="BB03_TB03" drill_tabopen="0" dync_sql="" yaxis="[{name=shl, label=智能审查数, chart_type=column}, {name=yaoshisc_shl, label=药师审查数, chart_type=spline}]"></s:chart>
</s:row>
</s:page>
