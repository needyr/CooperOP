<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="JCX067" init-action="" description="患者基本药物处方占比" system_product_code="ipc" title="患者基本药物处方占比" remark="患者基本药物处方占比" flag="q" type="query"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("ipc.query.q.JCX067", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("ipc.query.q.JCX067", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "ipc.query.q.JCX067", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("ipc.query.q.JCX067", null);
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
<s:row><s:form label="筛选条件" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="Y" app_collapsed="N"><s:row type="frow"><s:datefield label="开始日期" cols="1" name="start_date" value="${empty start_date ? rq : start_date}" placeholder="" fdtype="整数" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="" precision=""></s:datefield>
<s:datefield label="结束日期" cols="1" name="end_date" value="${empty end_date ? rq : end_date}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
<s:radio label="" cols="1" name="d_type" value="${empty d_type ? '0' : d_type}" placeholder="科室类型" fdtype="字符" defaultValue="0" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="全部" contentIndex="0" contentvalue="0" maxlength="50" precision=""><s:option label="全部" contentIndex="0" value="0"></s:option>
<s:option label="住院" contentIndex="1" value="1"></s:option>
<s:option label="门诊" contentIndex="2" value="2"></s:option>
<s:option label="急诊" contentIndex="3" value="3"></s:option>
</s:radio>
</s:row>
<s:toolbar><s:button label="刷新" icon="icon-reload" nextfocusfield="" action="querymx" color=""></s:button>
</s:toolbar>
<s:row type="frow"><s:textarea label="" cols="4" name="shuoming" placeholder="说明" fdtype="字符" defaultValue="医嘱标签为 国家基本药物 或者 地方基本药物 的 为基本药物处方 占比=基本药物处方量/统计总处方量 按照科室进行统计" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="300" precision="">${empty shuoming ? '医嘱标签为 国家基本药物 或者 地方基本药物 的 为基本药物处方 占比=基本药物处方量/统计总处方量 按照科室进行统计' : shuoming}</s:textarea>
</s:row>
</s:form>
</s:row>
<s:row><s:tabpanel color="grey-silver" cols="4"><s:table label="患者基本药物处方占比" cols="4" color="grey-silver" active="true" isdesign_="Y" tableid="01" icon="" djselect="" tablekey="" limit="25" toIndex="0" sort="true" autoload="false" istoorbar="N" myIndex="0" fields="" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="dept_name" fdtype="字符" format="" dictionary="" label="科室名称" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="250" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="jb_shl" fdtype="实数" format="" dictionary="" label="基本数量" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="shl" fdtype="实数" format="" dictionary="" label="总数" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="jbl" fdtype="实数" format="" dictionary="" label="基本药物使用率" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
</s:tabpanel>
</s:row>
</s:page>
