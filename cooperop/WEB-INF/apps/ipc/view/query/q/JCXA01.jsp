<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="JCXA01" init-action="" description="超说明书用药申请用药查询" system_product_code="ipc" title="超说明书用药申请用药查询" remark="超说明书用药申请用药查询" flag="q" type="query"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("ipc.query.q.JCXA01", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("ipc.query.q.JCXA01", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "ipc.query.q.JCXA01", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("ipc.query.q.JCXA01", null);
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
<s:row><s:form label="超说明书用药申请" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="Y" app_collapsed="N"><s:row type="frow"><s:datefield label="开始日期" cols="1" name="start_date" value="${empty start_date ? rq : start_date}" placeholder="" fdtype="整数" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="" precision=""></s:datefield>
<s:datefield label="截止日期" cols="1" name="end_date" value="${empty end_date ? rq : end_date}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
</s:row>
<s:row type="frow"><s:textfield label="科室名称" cols="1" name="dept_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_name ? '' : dept_name}</s:textfield>
</s:row>
<s:toolbar><s:button label="查询" icon="glyphicon glyphicon-search" nextfocusfield="" action="querymx" color=""></s:button>
<s:button label="导出" icon="glyphicon glyphicon-random" size="btn-sm" nextfocusfield="" action="exportexcel" color=""></s:button>
</s:toolbar>
</s:form>
</s:row>
<s:row><s:tabpanel color="grey-silver" cols="4"><s:table label="超说明书用药查询结果" cols="4" color="grey-silver" active="true" isdesign_="Y" tableid="01" icon="" djselect="ipc.bill.IPC.A013" tablekey="" limit="25" height="" totals="" toIndex="0" app_table="false" sort="false" autoload="true" istoorbar="N" myIndex="0" fields="" fitwidth="false" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="state" fdtype="字符" format="" dictionary="" label="状态" size="16" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="djbh" fdtype="字符" format="" dictionary="" label="单据编号" size="15" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="rq" fdtype="字符" format="" dictionary="" label="日期" size="10" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="100" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="username" fdtype="字符" format="" dictionary="" label="操作人" size="255" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="100" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="dept_code" fdtype="字符" format="" dictionary="" label="科室代码" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="100" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="dept_name" fdtype="字符" format="" dictionary="" label="科室名称" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_code" fdtype="字符" format="" dictionary="" label="药品代码" size="32" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_name" fdtype="字符" format="" dictionary="" label="药品名称" size="128" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="150" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_spec" fdtype="字符" format="" dictionary="" label="药品规格" size="32" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="drug_units" fdtype="字符" format="" dictionary="" label="单位" size="32" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="shengccj" fdtype="字符" format="" dictionary="" label="生产厂家" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="pizhwh" fdtype="字符" format="" dictionary="" label="批准文号" size="40" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="offlabel_mudi" fdtype="字符" format="" dictionary="" label="超说明书用药目的" size="2000" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="offlabel_neirong" fdtype="字符" format="" dictionary="" label="超说明书内容" size="2000" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="offlabel_yiju" fdtype="字符" format="" dictionary="" label="超说明书依据" size="2000" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="offlabel_jiansbl" fdtype="字符" format="" dictionary="" label="超说明书简述病例" size="2000" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
</s:tabpanel>
</s:row>
</s:page>
