<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="BB04" init-action="" description="处方点评分析" system_product_code="ipc" title="处方点评分析" remark="处方点评分析" flag="chart" type="chart"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("ipc.chart.chart.BB04", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("ipc.chart.chart.BB04", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "ipc.chart.chart.BB04", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("ipc.chart.chart.BB04", null);
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
<s:row><s:form label="统计条件" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="Y"><s:row type="frow"><s:datefield label="起用日期" cols="1" name="start_date" value="${empty start_date ? rq : start_date}" placeholder="" fdtype="整数" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="" precision=""></s:datefield>
<s:datefield label="截止时间" cols="1" name="end_date" value="${empty end_date ? rq : end_date}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
</s:row>
<s:toolbar><s:button label="查询" icon="icon-cursor" nextfocusfield="" action="querymx" color=""></s:button>
</s:toolbar>
<s:row type="frow"><s:select label="科室类型" cols="1" name="d_type" value="${empty d_type ? '0' : d_type}" placeholder="" fdtype="字符" defaultValue="0" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="全部" contentIndex="0" contentvalue="0" maxlength="50" precision=""><s:option label="全部" contentIndex="0" value="0"></s:option>
<s:option label="住院" contentIndex="1" value="1"></s:option>
<s:option label="门诊" contentIndex="2" value="2"></s:option>
<s:option label="急诊" contentIndex="3" value="3"></s:option>
</s:select>
<s:select label="医嘱/处方" cols="1" name="p_type" value="${empty p_type ? '0' : p_type}" placeholder="" fdtype="字符" defaultValue="0" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="全部" contentIndex="0" contentvalue="0" maxlength="8" precision=""><s:option label="全部" contentIndex="0" value="0"></s:option>
<s:option label="医嘱" contentIndex="1" value="1"></s:option>
<s:option label="处方" contentIndex="2" value="2"></s:option>
</s:select>
</s:row>
<s:row type="frow"><s:textfield label="科室名称" cols="1" name="dept_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="0" readonly="false" required="0" encryption="0" create_action="" modify_action="" enter_action="" dbl_action="zl_select_BB04_01" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_name ? '' : dept_name}</s:textfield>
<s:textfield label="科室代码" cols="1" name="dept_code" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_code ? '' : dept_code}</s:textfield>
</s:row>
</s:form>
</s:row>
<s:row ishidden="ishidden"><s:table label="01点评不合格比例" cols="4" color="grey-silver" isdesign_="Y" tableid="01" icon="" djselect="" subpage_name="" autoload="true" sort="false" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="tishi" value="${empty tishi ? '' : tishi}" fdtype="字符" format="" dictionary="" label="提示" size="20" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="shl" value="${empty shl ? '' : shl}" fdtype="实数" format="" dictionary="" label="数量" size="14" maxlength_show="" digitsize="3" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
<s:table label="02点评不合格药品分类" cols="4" color="grey-silver" isdesign_="Y" tableid="02" icon="" djselect="" subpage_name="" autoload="true" sort="false" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="property_toxi" value="${empty property_toxi ? '' : property_toxi}" fdtype="字符" format="" dictionary="" label="分类" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="shl" value="${empty shl ? '' : shl}" fdtype="实数" format="" dictionary="" label="数量" size="14" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
<s:table label="03常规点评问题分类" cols="4" color="grey-silver" isdesign_="Y" tableid="03" icon="" djselect="" subpage_name="" autoload="true" sort="false" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="message_txt" value="${empty message_txt ? '' : message_txt}" fdtype="字符" format="" dictionary="" label="严重程度" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="shl" value="${empty shl ? '' : shl}" fdtype="实数" format="" dictionary="" label="数量" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
<s:table label="04点评不合格药品排名（前十）" cols="4" color="grey-silver" isdesign_="Y" tableid="04" icon="" djselect="" subpage_name="" autoload="true" sort="true" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="order_text" value="${empty order_text ? '' : order_text}" fdtype="字符" format="" dictionary="" label="药品名称" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="shl" value="${empty shl ? '' : shl}" fdtype="实数" format="" dictionary="" label="不合格数量" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
<s:table label="05点评不合格科室排名" cols="4" color="grey-silver" isdesign_="Y" tableid="05" icon="" djselect="" subpage_name="" autoload="true" sort="false" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="dept_name" value="${empty dept_name ? '' : dept_name}" fdtype="字符" format="" dictionary="" label="科室名称" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="shl" value="${empty shl ? '' : shl}" fdtype="实数" format="" dictionary="" label="不合格数量" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
<s:table label="06点评不合格医生排名（前十）" cols="4" color="grey-silver" isdesign_="Y" tableid="06" icon="" djselect="" subpage_name="" autoload="true" sort="false" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="doctor" value="${empty doctor ? '' : doctor}" fdtype="字符" format="" dictionary="" label="医生" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="shl" value="${empty shl ? '' : shl}" fdtype="实数" format="" dictionary="" label="不合格数量" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
</s:row>
<s:row><s:chart label="点评不合格比例" cols="1" color="grey-silver" chart_flag="DP01" tableid="01" chart_height="300" drill_chart="ipc.chart.chart.BB04_11" drill_chart_name="点评不合格比例[下钻]->药品分类数量" autoload="true" welcome_func="N" group_field="" g_name="tishi" sub_label="" sub_label_href="" flag="DP01" yaxis="[{name=shl, label=数量, chart_type=pie}]"></s:chart>
<s:chart label="点评不合格药品分类" cols="2" color="grey-silver" chart_flag="DP02" tableid="02" chart_height="300" drill_chart="ipc.chart.chart.BB04_21" drill_chart_name="	点评不合格药品分类[下钻]->科室名称" autoload="true" welcome_func="N" group_field="" g_name="property_toxi" sub_label="[更多]" sub_label_href="ipc.chart.chart.BB04_2" flag="DP02" yaxis="[{name=shl, label=问题数量, chart_type=pie}]"></s:chart>
<s:chart label="常规点评问题分类" cols="1" color="grey-silver" chart_flag="DP03" tableid="03" chart_height="300" drill_chart="" drill_chart_name="" autoload="true" welcome_func="N" group_field="" g_name="message_txt" sub_label="[更多]" sub_label_href="ipc.chart.chart.BB04_3" flag="DP03" yaxis="[{name=shl, label=数量, chart_type=bar}]"></s:chart>
</s:row>
<s:row></s:row>
<s:row><s:chart label="点评不合格药品（前10）" cols="1" color="grey-silver" chart_flag="DP04" tableid="04" chart_height="400" drill_chart="ipc.chart.chart.BB04_41" drill_chart_name="不合格药品排名[下钻]->科室名称" autoload="true" welcome_func="N" group_field="" g_name="order_text" sub_label="[更多]" sub_label_href="ipc.chart.chart.BB04_4" flag="DP04" yaxis="[{name=shl, label=不合格数量, chart_type=column}]"></s:chart>
<s:chart label="点评不合格科室（前10）" cols="2" color="grey-silver" chart_flag="DP05" tableid="05" chart_height="400" drill_chart="ipc.chart.chart.BB04_51" drill_chart_name="点评不合格科室[下钻]->医生姓名" autoload="true" welcome_func="N" group_field="" g_name="dept_name" sub_label="[更多]" sub_label_href="ipc.chart.chart.BB04_5" flag="DP05" yaxis="[{name=shl, label=不合格数量, chart_type=column}]"></s:chart>
<s:chart label="点评不合格医生（前10）" cols="1" color="grey-silver" chart_flag="DP06" tableid="06" chart_height="400" drill_chart="ipc.chart.chart.BB04_61" drill_chart_name="不合格医生[下钻]->患者信息" autoload="true" welcome_func="N" group_field="" g_name="doctor" sub_label="[更多]" sub_label_href="ipc.chart.chart.BB04_6" flag="DP06" yaxis="[{name=shl, label=不合格数量, chart_type=column}]"></s:chart>
</s:row>
</s:page>
