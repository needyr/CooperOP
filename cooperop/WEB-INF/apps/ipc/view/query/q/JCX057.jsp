<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="JCX057" init-action="" description="医嘱查询" system_product_code="ipc" title="医嘱" remark="" flag="q" type="query"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("ipc.query.q.JCX057", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("ipc.query.q.JCX057", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "ipc.query.q.JCX057", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("ipc.query.q.JCX057", null);
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
<s:row><s:form label="医嘱查询" icon="fa fa-list-alt" color="default" border="1" cols="4" istoorbar="N" app_collapsed="Y"><s:row type="frow"><s:datefield label="开始日期" cols="1" name="start_date" value="${empty start_date ? rq : start_date}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
<s:datefield label="截止日期" cols="1" name="end_date" value="${empty end_date ? rq : end_date}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
<s:textfield label="操作员" cols="1" name="zhiyname" placeholder="" fdtype="字符" defaultValue="${username}" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="20" precision="">${empty zhiyname ? username : zhiyname}</s:textfield>
</s:row>
<s:row type="frow"><s:textfield color="grey" label="科室名称" name="dept_name" placeholder="" fdtype="字符" cols="1" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="zl_select_JCX057_01" out_action="zl_select_JCX057_01" tableid="" expressions="" maxlength="128" precision="">${empty dept_name ? '' : dept_name}</s:textfield>
<s:checkbox label="选择顺序" cols="2" name="is_zx" value="${empty is_zx ? '' : is_zx}" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" clabel="按问题类型优先" contentIndex="0" contentvalue="按问题类型优先" maxlength="2" precision=""><s:option label="按问题类型优先" contentIndex="0" value="按问题类型优先"></s:option>
<s:option label="按入院时间排序" contentIndex="1" value="按入院时间排序"></s:option>
</s:checkbox>
</s:row>
<s:row type="frow"><s:textfield label="问题类型" cols="1" name="sort_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="zl_select_JCX057_02" out_action="" tableid="" expressions="" maxlength="50" precision="">${empty sort_name ? '' : sort_name}</s:textfield>
</s:row>
<s:row type="frow" ishidden="ishidden"><s:textfield label="病人标识号" cols="1" name="patient_id" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="32" precision="">${empty patient_id ? '' : patient_id}</s:textfield>
<s:textfield label="职员内码" cols="1" name="zhiyid" placeholder="" fdtype="字符" defaultValue="${userid}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="11" precision="">${empty zhiyid ? userid : zhiyid}</s:textfield>
<s:textfield label="本次住院标识" cols="1" name="visit_id" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="32" precision="">${empty visit_id ? '' : visit_id}</s:textfield>
</s:row>
</s:form>
<s:table label="医嘱" cols="4" color="default" isdesign_="Y" tableid="JCX057" icon="glyphicon glyphicon-list-alt" djselect="" tablekey="" limit="25" sort="false" autoload="false" fields="" istoorbar="Y" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="sort_name" fdtype="字符" format="" dictionary="" label="问题类型" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="0" app_field=""></s:table.field>
<s:table.field name="sys_check_level_name" fdtype="字符" format="" dictionary="" label="预警级别" size="50" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="40" fieldorder="1" app_field=""></s:table.field>
<s:table.field name="dept_name" fdtype="字符" format="" dictionary="" label="科室名称" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="100" fieldorder="2" app_field=""></s:table.field>
<s:table.field name="patient_name" fdtype="字符" format="" dictionary="" label="病人姓名" size="1280" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="3" app_field=""></s:table.field>
<s:table.field name="bed_no" fdtype="字符" format="" dictionary="" label="床位号" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="40" fieldorder="4" app_field=""></s:table.field>
<s:table.field name="tag" fdtype="字符" format="" dictionary="" label="组" size="2" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="right" width="20" fieldorder="5" app_field=""></s:table.field>
<s:table.field name="repeat_indicator" fdtype="字符" format="" dictionary="" label="长/临" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="6" app_field=""></s:table.field>
<s:table.field name="order_class" fdtype="字符" format="" dictionary="" label="类别" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="50" fieldorder="7" app_field=""></s:table.field>
<s:table.field name="administration" fdtype="字符" format="" dictionary="" label="用药途径" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="8" app_field=""></s:table.field>
<s:table.field name="start_date_time" fdtype="字符" format="" dictionary="" label="开始时间" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="140" fieldorder="9" app_field=""></s:table.field>
<s:table.field name="stop_date_time" fdtype="字符" format="" dictionary="" label="停止时间" size="19" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="140" fieldorder="10" app_field=""></s:table.field>
<s:table.field name="order_text" fdtype="字符" format="" dictionary="" label="医嘱正文" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="11" app_field=""></s:table.field>
<s:table.field name="dosage" fdtype="字符" format="" dictionary="" label="一次用量" size="16" maxlength="" digitsize="4" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="12" app_field=""></s:table.field>
<s:table.field name="frequency" fdtype="字符" format="" dictionary="" label="频次" size="16" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="100" fieldorder="13" app_field=""></s:table.field>
<s:table.field name="description" fdtype="文本" format="" dictionary="" label="审查问题描述" size="" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="300" fieldorder="14" app_field=""></s:table.field>
<s:table.field name="reference" fdtype="文本" format="" dictionary="" label="参考文献" size="300" maxlength="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="250" fieldorder="15" app_field=""></s:table.field>
<s:table.field name="order_no" fdtype="字符" format="" dictionary="" label="医嘱号" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="16" app_field=""></s:table.field>
<s:table.field name="order_status" fdtype="字符" format="" dictionary="" label="医嘱状态" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="17" app_field=""></s:table.field>
<s:table.field name="freq_detail" fdtype="字符" format="" dictionary="" label="执行时间" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="18" app_field=""></s:table.field>
<s:table.field name="doctor" fdtype="字符" format="" dictionary="" label="医生" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="19" app_field=""></s:table.field>
<s:table.field name="stop_doctor" fdtype="字符" format="" dictionary="" label="停嘱医生" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="80" fieldorder="20" app_field=""></s:table.field>
<s:table.field name="nurse" fdtype="字符" format="" dictionary="" label="护士" size="128" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="21" app_field=""></s:table.field>
<s:table.field name="patient_id" fdtype="字符" format="" dictionary="" label="病人ID" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="22" app_field=""></s:table.field>
<s:table.field name="visit_id" fdtype="字符" format="" dictionary="" label="本次住院标识" size="32" maxlength="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="23" app_field=""></s:table.field>
</s:table.fields>
<s:toolbar><s:button label="导出" icon="glyphicon glyphicon-random" nextfocusfield="" action="exportexcel" color=""></s:button>
<s:button label="查询" icon=" icon-magnifier" size="btn-sm" nextfocusfield="" action="querymx" color=""></s:button>
</s:toolbar>
</s:table>
</s:row>
</s:page>
