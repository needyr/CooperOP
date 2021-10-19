<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="DP_TJ03" init-action="" description="住院抗菌药物统计报表" system_product_code="hospital_oc" title="住院抗菌药物统计报表" remark="住院抗菌药物统计报表" flag="chart" type="chart"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("hospital_oc.chart.chart.DP_TJ03", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("hospital_oc.chart.chart.DP_TJ03", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "hospital_oc.chart.chart.DP_TJ03", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("hospital_oc.chart.chart.DP_TJ03", null);
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
<s:row><s:form label="统计条件" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="Y"><s:row type="frow"><s:datefield label="起用日期" cols="1" name="start_date" value="${empty start_date ? '' : start_date}" placeholder="" fdtype="整数" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="" precision=""></s:datefield>
<s:datefield label="截止时间" cols="1" name="end_date" value="${empty end_date ? rq : end_date}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
</s:row>
<s:toolbar><s:button label="查询" icon="icon-cursor" nextfocusfield="" action="querymx" color=""></s:button>
<s:button label="导出EXCEL" icon="icon-shuffle" size="btn-sm" nextfocusfield="" action="exportexcel" color=""></s:button>
</s:toolbar>
<s:row type="frow"><s:textfield label="科室名称" cols="1" name="dept_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="0" readonly="false" required="0" encryption="0" create_action="" modify_action="" enter_action="" dbl_action="zl_select_DP_TJ03_01" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_name ? '' : dept_name}</s:textfield>
</s:row>
<s:row type="frow" ishidden="ishidden"><s:textfield label="科室代码" cols="1" name="dept_code" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_code ? '' : dept_code}</s:textfield>
</s:row>
</s:form>
</s:row>
<s:row><s:tabpanel color="grey-silver" cols="4"><s:table label="药品按使用数量情况排名表" cols="4" color="grey-silver" active="true" isdesign_="Y" tableid="CX01" icon="" djselect="" subpage_name="" autoload="false" limit="25" sort="false" app_table="N" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="item_code" value="${empty item_code ? '' : item_code}" fdtype="字符" format="" dictionary="" label="代码" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="320" fieldorder="0" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_name" value="$[drug_name]"  fdtype="字符" format="" dictionary="" label="药品名称" size="128" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="300" fieldorder="1" is_href="hospital_common.additional.instruction" app_field="" datatype="template"><s:textfield readonly="true" name="drug_name" value="$[drug_name]"  fdtype="字符" format="" dictionary="" size="128" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="300" fieldorder="1" is_href="hospital_common.additional.instruction" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="druggg" value="${empty druggg ? '' : druggg}" fdtype="字符" format="" dictionary="" label="药品规格" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="100" fieldorder="2" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_unit" value="${empty drug_unit ? '' : drug_unit}" fdtype="字符" format="" dictionary="" label="药品单位" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="3" is_href="" app_field=""></s:table.field>
<s:table.field name="shengccj" value="${empty shengccj ? '' : shengccj}" fdtype="字符" format="" dictionary="" label="生产厂家" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="4" is_href="" app_field=""></s:table.field>
<s:table.field name="charges" value="${empty charges ? '' : charges}" fdtype="实数" format="" dictionary="" label="金额" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="right" width="20" fieldorder="5" is_href="" app_field=""></s:table.field>
<s:table.field name="amount" value="${empty amount ? '' : amount}" datatype="script"  fdtype="字符" format="" dictionary="" label="数量" size="16" maxlength_show="18" digitsize="0" precision="" available="true" sort="true" controltype="" align="right" width="60" fieldorder="6" is_href="" app_field=""> var html = [];  if(record.amount && record.amount.length>18){  	html.push('<a href="javascript:void(0);" title="'+record.amount+'" style="cursor: pointer;"');  	html.push(' onclick="show_maxlength(this);" ');  	html.push('>');  	html.push(record.amount.substring(0,18)+'……');  	html.push('</a>');  }else{  	html.push(record.amount);  }  return html.join(''); </s:table.field>
<s:table.field name="amount_sn" value="${empty amount_sn ? '' : amount_sn}" fdtype="字符" format="" dictionary="" label="数量排名" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="right" width="30" fieldorder="7" is_href="" app_field=""></s:table.field>
<s:table.field name="操作" value="$[操作]"  fdtype="字符" format="" dictionary="" label="操作" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="250" fieldorder="8" is_href="hospital_oc.chart.chart.DP_TJ01_01" app_field="" datatype="template"><s:textfield readonly="true" name="操作" value="$[操作]"  fdtype="字符" format="" dictionary="" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="250" fieldorder="8" is_href="hospital_oc.chart.chart.DP_TJ01_01" app_field="">null</s:textfield>
</s:table.field>
</s:table.fields>
</s:table>
<s:table label="药品按金额情况排名表" cols="4" color="grey-silver" active="false" isdesign_="Y" tableid="CX02" icon="" djselect="" subpage_name="" autoload="false" limit="25" sort="false" app_table="N" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="item_code" value="${empty item_code ? '' : item_code}" fdtype="字符" format="" dictionary="" label="代码" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="320" fieldorder="0" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_name" value="$[drug_name]"  fdtype="字符" format="" dictionary="" label="药品名称" size="200" maxlength_show="200" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="350" fieldorder="1" is_href="hospital_common.additional.instruction" app_field="" datatype="template"><s:textfield readonly="true" name="drug_name" value="$[drug_name]"  fdtype="字符" format="" dictionary="" size="200" maxlength_show="200" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="350" fieldorder="1" is_href="hospital_common.additional.instruction" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="druggg" value="${empty druggg ? '' : druggg}" fdtype="字符" format="" dictionary="" label="药品规格" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="50" fieldorder="2" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_unit" value="${empty drug_unit ? '' : drug_unit}" fdtype="字符" format="" dictionary="" label="药品单位" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="50" fieldorder="3" is_href="" app_field=""></s:table.field>
<s:table.field name="shengccj" value="${empty shengccj ? '' : shengccj}" fdtype="字符" format="" dictionary="" label="生产厂家" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="4" is_href="" app_field=""></s:table.field>
<s:table.field name="amount" value="${empty amount ? '' : amount}" fdtype="字符" format="" dictionary="" label="数量" size="16" maxlength_show="" digitsize="4" precision="" available="true" sort="true" controltype="" align="right" width="20" fieldorder="5" is_href="" app_field=""></s:table.field>
<s:table.field name="charges" value="${empty charges ? '' : charges}" fdtype="实数" format="" dictionary="" label="金额" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="right" width="20" fieldorder="6" is_href="" app_field=""></s:table.field>
<s:table.field name="charges_sn" value="${empty charges_sn ? '' : charges_sn}" fdtype="实数" format="" dictionary="" label="金额排序" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="right" width="5" fieldorder="7" is_href="" app_field=""></s:table.field>
<s:table.field name="sn_message" value="${empty sn_message ? '' : sn_message}" fdtype="字符" format="" dictionary="" label="环比上期" size="20" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="right" width="20" fieldorder="8" is_href="" app_field=""></s:table.field>
<s:table.field name="操作" value="$[操作]"  fdtype="字符" format="" dictionary="" label="操作" size="40" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="200" fieldorder="9" is_href="hospital_oc.chart.chart.DP_TJ01_02" app_field="" datatype="template"><s:textfield readonly="true" name="操作" value="$[操作]"  fdtype="字符" format="" dictionary="" size="40" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="textfield" align="left" width="200" fieldorder="9" is_href="hospital_oc.chart.chart.DP_TJ01_02" app_field="">null</s:textfield>
</s:table.field>
</s:table.fields>
</s:table>
<s:table label="药品使用数量前5科室排名" cols="4" color="grey-silver" active="false" isdesign_="Y" tableid="CX03" icon="" djselect="" subpage_name="" autoload="false" limit="25" sort="false" app_table="N" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="item_code" value="${empty item_code ? '' : item_code}" fdtype="字符" format="" dictionary="" label="项目代码" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="320" fieldorder="0" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_name" value="${empty drug_name ? '' : drug_name}" fdtype="字符" format="" dictionary="" label="药品名称" size="360" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="350" fieldorder="1" is_href="" app_field=""></s:table.field>
<s:table.field name="druggg" value="${empty druggg ? '' : druggg}" fdtype="字符" format="" dictionary="" label="药品规格" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="right" width="60" fieldorder="2" is_href="" app_field=""></s:table.field>
<s:table.field name="jixing" value="${empty jixing ? '' : jixing}" fdtype="字符" format="" dictionary="" label="剂型" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="right" width="150" fieldorder="3" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_unit" value="${empty drug_unit ? '' : drug_unit}" fdtype="字符" format="" dictionary="" label="药品单位" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="right" width="50" fieldorder="4" is_href="" app_field=""></s:table.field>
<s:table.field name="dept_name" value="${empty dept_name ? '' : dept_name}" fdtype="字符" format="" dictionary="" label="科室名称" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="220" fieldorder="5" is_href="" app_field=""></s:table.field>
<s:table.field name="charges" value="${empty charges ? '' : charges}" fdtype="实数" format="" dictionary="" label="金额" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="right" width="20" fieldorder="6" is_href="" app_field=""></s:table.field>
<s:table.field name="amount" value="${empty amount ? '' : amount}" fdtype="字符" format="" dictionary="" label="数量" size="16" maxlength_show="" digitsize="4" precision="" available="true" sort="true" controltype="" align="right" width="20" fieldorder="7" is_href="" app_field=""></s:table.field>
<s:table.field name="sn" value="${empty sn ? '' : sn}" fdtype="整数" format="" dictionary="" label="数量排名" size="4" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="right" width="10" fieldorder="8" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
</s:tabpanel>
</s:row>
</s:page>
