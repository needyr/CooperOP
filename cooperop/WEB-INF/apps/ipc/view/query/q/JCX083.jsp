<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="JCX083" init-action="" description="抗肿瘤药物医嘱点评结果汇总" system_product_code="ipc" title="抗肿瘤药物医嘱点评结果汇总" remark="抗肿瘤药物医嘱点评结果汇总" flag="q" type="query"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("ipc.query.q.JCX083", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("ipc.query.q.JCX083", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "ipc.query.q.JCX083", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("ipc.query.q.JCX083", null);
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
<s:row><s:form label="选择条件" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="Y" app_collapsed="N"><s:row type="frow"><s:datefield label="开始日期" cols="1" name="start_date" value="${empty start_date ? rq : start_date}" placeholder="" fdtype="整数" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="" precision=""></s:datefield>
<s:datefield label="结束日期" cols="1" name="end_date" value="${empty end_date ? rq : end_date}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
</s:row>
<s:toolbar><s:button label="查询" icon="glyphicon glyphicon-search" nextfocusfield="" action="querymx" color=""></s:button>
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
</s:form>
</s:row>
<s:row><s:tabpanel color="grey-silver" cols="4"><s:table label="抗肿瘤药物医嘱点评结果汇总" cols="4" color="grey-silver" active="true" isdesign_="Y" tableid="01" icon="" djselect="" tablekey="" limit="25" toIndex="0" app_table="false" sort="true" autoload="false" istoorbar="N" myIndex="0" fields="" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="spcomment_name" fdtype="字符" format="" dictionary="" label="点评类型" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="comment_datetime" fdtype="字符" format="" dictionary="" label="点评时间" size="19" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="patient_name" value="$[patient_name]"  fdtype="字符" format="" dictionary="" label="患者信息" size="64" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="hospital_common.showturns.patientdetail" app_field="" datatype="template"><s:textfield readonly="true" name="patient_name" value="$[patient_name]"  fdtype="字符" format="" dictionary="" size="64" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="hospital_common.showturns.patientdetail" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="doctor" fdtype="字符" format="" dictionary="" label="医生姓名" size="64" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="dept_name" fdtype="字符" format="" dictionary="" label="科室名称" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="ordermessage" fdtype="字符" format="" dictionary="" label="医嘱信息" size="2000" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="comment_result_message" fdtype="字符" format="" dictionary="" label="点评结果" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="dp1" fdtype="实数" format="" dictionary="" label="患者不适宜化疗" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="dp2" fdtype="实数" format="" dictionary="" label="方案不符合相关指南" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="dp3" fdtype="实数" format="" dictionary="" label="方案不是最优方案" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="dp4" fdtype="实数" format="" dictionary="" label="给药剂量不符合指南或说明书" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="150" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="dp5" fdtype="实数" format="" dictionary="" label="辅助用药不足够" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="dp6" fdtype="实数" format="" dictionary="" label="其他" size="14" maxlength_show="" digitsize="2" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
</s:table>
</s:tabpanel>
</s:row>
</s:page>
