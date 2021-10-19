<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="TJFX_01" init-action="" description="普通药品排名前三十" system_product_code="hospital_oc" title="普通药品排名前三十" remark="普通药品排名前三十" flag="chart" type="chart"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("hospital_oc.chart.chart.TJFX_01", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("hospital_oc.chart.chart.TJFX_01", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "hospital_oc.chart.chart.TJFX_01", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("hospital_oc.chart.chart.TJFX_01", null);
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
<s:row><s:form label="普通药品排名前三十_统计条件" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="Y"><s:row type="frow"><s:datefield label="开始日期" cols="1" name="start_date" value="${empty start_date ? '' : start_date}" placeholder="" fdtype="整数" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="true" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="" precision=""></s:datefield>
<s:datefield label="截止时间" cols="1" name="end_date" value="${empty end_date ? rq : end_date}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="true" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
</s:row>
<s:toolbar><s:button label="查询" icon="icon-cursor" nextfocusfield="" action="querymx" color=""></s:button>
<s:button label="导出EXCEL" icon="icon-shuffle" size="btn-sm" nextfocusfield="" action="exportexcel" color=""></s:button>
</s:toolbar>
<s:row type="frow"><s:textfield label="科室名称" cols="2" name="dept_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="zl_select_TJFX_01_01" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_name ? '' : dept_name}</s:textfield>
</s:row>
<s:row type="frow" ishidden="ishidden"><s:textfield label="科室代码" cols="1" name="dept_code" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_code ? '' : dept_code}</s:textfield>
</s:row>
</s:form>
</s:row>
<s:row><s:tabpanel color="grey-silver" cols="4"><s:table label="住院普通药品金额排名前三十（除溶媒和抗菌药物）" cols="4" color="grey-silver" active="true" isdesign_="Y" tableid="TJBB01" icon="" djselect="" subpage_name="" autoload="false" limit="50" sort="false" app_table="N" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="item_code" value="${empty item_code ? '' : item_code}" fdtype="字符" format="" dictionary="" label="项目代码" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_name" value="${empty drug_name ? '' : drug_name}" fdtype="字符" format="" dictionary="" label="药品名称" size="128" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="220" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="druggg" value="${empty druggg ? '' : druggg}" fdtype="字符" format="###岁" dictionary="" label="药品规格" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="shengccj" value="${empty shengccj ? '' : shengccj}" fdtype="字符" format="" dictionary="" label="生产厂家" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_unit" value="${empty drug_unit ? '' : drug_unit}" fdtype="字符" format="" dictionary="" label="药品单位" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="jixing" value="${empty jixing ? '' : jixing}" fdtype="字符" format="" dictionary="" label="剂型" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="amount" value="${empty amount ? '' : amount}" fdtype="字符" format="" dictionary="" label="数量" size="16" maxlength_show="" digitsize="4" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="charges" value="${empty charges ? '' : charges}" fdtype="实数" format="" dictionary="" label="应付费用" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="right" width="10" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="charges_sn" value="${empty charges_sn ? '' : charges_sn}" fdtype="实数" format="" dictionary="" label="金额排序" size="10" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="right" width="10" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="sn_message" value="${empty sn_message ? '' : sn_message}" fdtype="字符" format="" dictionary="" label="上期环比" size="20" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="right" width="10" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
<s:table label="门诊普通药品金额排名前三十（除溶媒和抗菌药物）" cols="4" color="grey-silver" active="false" isdesign_="Y" tableid="TJBB02" icon="" djselect="" subpage_name="" autoload="false" limit="50" sort="false" app_table="N" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="item_code" value="${empty item_code ? '' : item_code}" fdtype="字符" format="" dictionary="" label="项目代码" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_name" value="${empty drug_name ? '' : drug_name}" fdtype="字符" format="" dictionary="" label="药品名称" size="128" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="220" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="druggg" value="${empty druggg ? '' : druggg}" fdtype="字符" format="" dictionary="" label="药品规格" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="shengccj" value="${empty shengccj ? '' : shengccj}" fdtype="字符" format="" dictionary="" label="生产厂家" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_unit" value="${empty drug_unit ? '' : drug_unit}" fdtype="字符" format="" dictionary="" label="药品单位" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="jixing" value="${empty jixing ? '' : jixing}" fdtype="字符" format="" dictionary="" label="剂型" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="amount" value="${empty amount ? '' : amount}" fdtype="字符" format="" dictionary="" label="数量" size="16" maxlength_show="" digitsize="4" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="charges" value="${empty charges ? '' : charges}" fdtype="实数" format="" dictionary="" label="应付费用" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="charges_sn" value="${empty charges_sn ? '' : charges_sn}" fdtype="实数" format="" dictionary="" label="金额排序" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="right" width="30" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="sn_message" value="${empty sn_message ? '' : sn_message}" fdtype="字符" format="" dictionary="" label="上期环比" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="right" width="10" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
<s:table label="全院药品使用排名前三十" cols="4" color="grey-silver" active="false" isdesign_="Y" tableid="TJBB03" icon="" djselect="" subpage_name="" autoload="false" limit="50" sort="false" app_table="N" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="item_code" value="${empty item_code ? '' : item_code}" fdtype="字符" format="" dictionary="" label="项目代码" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_name" value="${empty drug_name ? '' : drug_name}" fdtype="字符" format="" dictionary="" label="药品名称" size="128" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="220" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="druggg" value="${empty druggg ? '' : druggg}" fdtype="字符" format="" dictionary="" label="药品规格" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="shengccj" value="${empty shengccj ? '' : shengccj}" fdtype="字符" format="" dictionary="" label="生产厂家" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_unit" value="${empty drug_unit ? '' : drug_unit}" fdtype="字符" format="" dictionary="" label="药品单位" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="40" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="jixing" value="${empty jixing ? '' : jixing}" fdtype="字符" format="" dictionary="" label="剂型" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="amount" value="${empty amount ? '' : amount}" fdtype="字符" format="" dictionary="" label="数量" size="16" maxlength_show="" digitsize="4" precision="" available="true" sort="true" controltype="" align="right" width="60" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="charges" value="${empty charges ? '' : charges}" fdtype="实数" format="" dictionary="" label="应付费用" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="right" width="80" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="charges_sn" value="${empty charges_sn ? '' : charges_sn}" fdtype="字符" format="" dictionary="" label="金额排名" size="10" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="right" width="10" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="costs_sq" value="${empty costs_sq ? '' : costs_sq}" fdtype="字符" format="" dictionary="" label="上期金额" size="10" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="right" width="40" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="sn_message" value="${empty sn_message ? '' : sn_message}" fdtype="字符" format="" dictionary="" label="环比上期" size="10" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="right" width="40" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
</s:tabpanel>
</s:row>
</s:page>
