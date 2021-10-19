<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="SYC_YJCX01" init-action="" description="药品配对预警查询" system_product_code="hospital_common" title="药品配对预警查询" remark="药品配对预警查询" flag="q" type="query"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("hospital_common.query.q.SYC_YJCX01", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("hospital_common.query.q.SYC_YJCX01", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "hospital_common.query.q.SYC_YJCX01", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("hospital_common.query.q.SYC_YJCX01", null);
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
<s:row><s:form label="未配对药品预警查询" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="Y" app_collapsed="N"><s:row type="frow"><s:radio label="" cols="1" name="xuanze" value="${empty xuanze ? '' : xuanze}" placeholder="选择" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="0" readonly="false" required="0" encryption="0" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="未配对" contentIndex="1" contentvalue="1" maxlength="10" precision=""><s:option label="全部" contentIndex="0" value="0"></s:option>
<s:option label="未配对" contentIndex="1" value="1"></s:option>
<s:option label="已经配对" contentIndex="2" value="2"></s:option>
</s:radio>
</s:row>
<s:toolbar><s:button label="查询" icon="glyphicon glyphicon-search" nextfocusfield="" action="querymx" color=""></s:button>
<s:button label="导出" icon="icon-shuffle" size="btn-sm" nextfocusfield="" action="exportexcel" color=""></s:button>
</s:toolbar>
</s:form>
</s:row>
<s:row><s:table label="预警数据结果" cols="4" color="grey-silver" isdesign_="Y" tableid="01" icon="" djselect="" tablekey="" limit="25" height="" totals="" app_table="false" sort="false" autoload="true" istoorbar="N" fields="" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="p_key" fdtype="字符" format="" dictionary="" label="主键" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_code" fdtype="字符" format="" dictionary="" label="药品代码" size="32" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_name" fdtype="字符" format="###岁" dictionary="" label="药品名称" size="128" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="150" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="druggg" fdtype="字符" format="" dictionary="" label="药品规格" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_unit" fdtype="字符" format="" dictionary="" label="药品单位" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="包装规格" fdtype="字符" format="" dictionary="" label="包装规格" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="包装单位" fdtype="字符" format="" dictionary="" label="包装单位" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="shengccj" fdtype="字符" format="" dictionary="" label="生产厂家" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="jixing" fdtype="字符" format="" dictionary="" label="剂型" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="use_30day " fdtype="" format="" dictionary="" label="30天内是否使用" size="20" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
</s:row>
</s:page>