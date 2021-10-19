<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="gzfh_3" init-action="" description="运营简讯（外系科室）-工作负荷 住院人次" system_product_code="iad" title="" remark="" flag="chart" type="chart"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("iad.chart.chart.gzfh_3", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("iad.chart.chart.gzfh_3", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "iad.chart.chart.gzfh_3", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("iad.chart.chart.gzfh_3", null);
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
<s:row><s:form label="查询条件" icon="fa fa-list-alt" color="default" border="1" cols="4" istoorbar="Y"><s:row type="frow"><s:select label="统计类型：" cols="1" name="leixing" value="${empty leixing ? '' : leixing}" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="季度" contentIndex="1" contentvalue="季度" maxlength="50" precision=""><s:option label="年月" contentIndex="0" value="年月"></s:option>
<s:option label="季度" contentIndex="1" value="季度"></s:option>
</s:select>
<s:datefield label="" cols="1" name="caozrq" value="${empty caozrq ? rq : caozrq}" placeholder="" fdtype="字符" defaultValue="${rq}" format="yyyy-MM" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
<s:textfield label="科室：" cols="1" name="visit_dept" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="zl_select_gzfh_3_01" out_action="" tableid="" expressions="" maxlength="32" precision="">${empty visit_dept ? '' : visit_dept}</s:textfield>
</s:row>
<s:toolbar><s:button label="查询" icon="glyphicon glyphicon-search" nextfocusfield="" action="querymx" color=""></s:button>
</s:toolbar>
</s:form>
</s:row>
<s:row><s:chart label="工作负荷 入院人次" cols="1" is_span="Y" color="grey-silver" chart_flag="fh" chart_height="" sub_label="更多" sub_label_href="iad.chart.chart.gzfh_4" span_head="入院人次" span_content=":bq" span_footer="本期同比增幅  :bfb" welcome_func="N" autoload="true" autorefresh="Y" refresh_time="" flag="fh"></s:chart>
<s:chart label="工作负荷 出院人次" cols="1" is_span="Y" color="grey-silver" chart_flag="fh2" chart_height="" sub_label="更多" sub_label_href="iad.chart.chart.gzfh_4" span_head="出院人次" span_content=":bq" span_footer="本期同比增幅   :bfb" welcome_func="N" autoload="true" autorefresh="Y" refresh_time="" flag="fh2"></s:chart>
</s:row>
<s:row><s:chart label="入院人次排名前 5名科室" cols="2" color="grey-silver" chart_flag="fh3" tableid="fh3" chart_height="650" drill_chart="" drill_chart_name="" autoload="true" welcome_func="N" group_field="type" g_name="visit_dept" sub_label="更多" sub_label_href="" flag="fh3" yaxis="[{name=je, label=金额, chart_type=bar}]"></s:chart>
<s:chart label="出院人次排名前 5科室" cols="2" color="grey-silver" chart_flag="fh4" tableid="fh4" chart_height="650" drill_chart="" drill_chart_name="" autoload="true" welcome_func="N" group_field="type" g_name="visit_dept" sub_label="" sub_label_href="" flag="fh4" yaxis="[{name=je, label=金额, chart_type=bar}]"></s:chart>
</s:row>
</s:page>
