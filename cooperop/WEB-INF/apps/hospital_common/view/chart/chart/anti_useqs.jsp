<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="anti_useqs" init-action="" description="住院抗菌药物使用趋势" system_product_code="hospital_common" title="住院抗菌药物使用趋势" remark="住院抗菌药物使用趋势" flag="chart" type="chart"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("hospital_common.chart.chart.anti_useqs", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("hospital_common.chart.chart.anti_useqs", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "hospital_common.chart.chart.anti_useqs", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("hospital_common.chart.chart.anti_useqs", null);
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
<s:row><s:form label="统计条件" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="Y"><s:row type="frow"><s:datefield label="开始日期" cols="1" name="start_date" value="${empty start_date ? '' : start_date}" placeholder="" fdtype="整数" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="true" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="" precision=""></s:datefield>
<s:datefield label="结束日期" cols="1" name="end_date" value="${empty end_date ? rq : end_date}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="true" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
</s:row>
<s:toolbar><s:button label="查询" icon="icon-cursor" nextfocusfield="" action="querymx" color=""></s:button>
<s:button label="导出EXCEL" icon="icon-shuffle" size="btn-sm" nextfocusfield="" action="exportexcel" color=""></s:button>
</s:toolbar>
</s:form>
</s:row>
<s:row><s:chart label="全年抗菌药物累计金额趋势情况" cols="4" color="grey-silver" chart_flag="auti_usebb01" tableid="" chart_height="600" drill_chart="hospital_common.chart.chart.anti_in_ks" drill_chart_name="抗菌药物科室分析[下钻]->[:xaxis]" drill_tabopen="0" autoload="false" welcome_func="N" group_field="" g_name="billdate" sub_label="" sub_label_href="" flag="auti_usebb01" dync_sql="" yaxis="[{name=charges, label=抗菌药物金额, chart_type=spline}]"></s:chart>
</s:row>
<s:row><s:table label="抗菌药物使用情况（金额）" cols="4" color="grey-silver" isdesign_="Y" tableid="anti_usebd_02" icon="" djselect="" subpage_name="" autoload="false" limit="25" sort="false" app_table="N" totals="" fitwidth="true" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="billdate" value="${empty billdate ? '' : billdate}" datatype="script"  fdtype="字符" format="" dictionary="" label="年月" size="120" maxlength_show="120" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="0" is_href="" app_field=""> var html = [];  if(record.billdate && record.billdate.length>120){  	html.push('<a href="javascript:void(0);" title="'+record.billdate+'" style="cursor: pointer;"');  	html.push(' onclick="show_maxlength(this);" ');  	html.push('>');  	html.push(record.billdate.substring(0,120)+'……');  	html.push('</a>');  }else{  	html.push(record.billdate);  }  return html.join(''); </s:table.field>
<s:table.field name="charges" value="$[charges]"  fdtype="实数" format="" dictionary="" label="抗菌药物金额" size="14" maxlength_show="120" digitsize="2" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="1" is_href="hospital_common.chart.chart.anti_in_ks" app_field="" datatype="template"><s:textfield readonly="true" name="charges" value="$[charges]"  fdtype="实数" format="" dictionary="" size="14" maxlength_show="120" digitsize="2" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="1" is_href="hospital_common.chart.chart.anti_in_ks" app_field="">null</s:textfield>
</s:table.field>
</s:table.fields>
</s:table>
</s:row>
</s:page>
