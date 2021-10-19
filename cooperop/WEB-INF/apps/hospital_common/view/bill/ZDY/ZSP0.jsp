<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="ZSP0" init-action="" flag="ZDY" description="自定义审批项目" system_product_code="hospital_common" djzy="自定义审批项目" helpno="" title="自定义审批项目" remark="自定义审批项目" printmx="" sub="" modalwidth="600" modalheight="600" djprintsql="" othermxsql="" type="bill"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("hospital_common.bill.ZDY.ZSP0", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("hospital_common.bill.ZDY.ZSP0", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "hospital_common.bill.ZDY.ZSP0", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("hospital_common.bill.ZDY.ZSP0", null);
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
<s:row><s:form label="审批项目" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="N"><s:row type="frow"><s:datefield label="日期" cols="1" name="rq" value="${empty rq ? rq : rq}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
<s:textfield color="grey" label="审核人" name="shenhr_name" placeholder="" fdtype="字符" cols="1" defaultValue="${username}" format="" nextfocusfield="" islabel="true" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="50" precision="">${empty shenhr_name ? username : shenhr_name}</s:textfield>
<s:textfield label="审批人ID" cols="1" name="shenhr_id" placeholder="" fdtype="字符" defaultValue="${userid}" format="" nextfocusfield="" islabel="true" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="50" precision="">${empty shenhr_id ? userid : shenhr_id}</s:textfield>
</s:row>
<s:row type="frow"><s:textfield label="发起人" cols="1" name="username" placeholder="" fdtype="字符" defaultValue="${username}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="12" precision="">${empty username ? username : username}</s:textfield>
<s:textfield label="科室名称" cols="1" name="dept_name" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_name ? '' : dept_name}</s:textfield>
</s:row>
<s:row type="frow"><s:textarea label="内容" cols="4" name="content" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="true" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="200" precision="">${empty content ? '' : content}</s:textarea>
</s:row>
<s:row type="frow"><s:textarea label="审核意见" cols="4" name="shenhyj" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="30" precision="">${empty shenhyj ? '' : shenhyj}</s:textarea>
</s:row>
<s:row type="frow"><s:button label="同意" cols="" nextfocusfield="" icon="" action="approval" color=""></s:button>
<s:button label="驳回" cols="" nextfocusfield="" icon="" action="reject" color=""></s:button>
</s:row>
</s:form>
</s:row>
<s:row ishidden="ishidden"><s:form label="表单" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4"><s:row type="frow"><s:textfield label="企业ID" cols="1" name="company_id" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="50" precision="">${empty company_id ? '' : company_id}</s:textfield>
<s:textfield label="机构内码" cols="1" name="jigid" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="11" precision="">${empty jigid ? '' : jigid}</s:textfield>
<s:textfield label="科室代码" cols="1" name="dept_code" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_code ? '' : dept_code}</s:textfield>
<s:textfield color="grey" label="患者标识号" name="patient_id" placeholder="" fdtype="字符" cols="1" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="32" precision="">${empty patient_id ? '' : patient_id}</s:textfield>
<s:textfield label="本次住院标识" cols="1" name="visit_id" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="32" precision="">${empty visit_id ? '' : visit_id}</s:textfield>
<s:textfield label="单据编号" cols="1" name="djbh" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="15" precision="">${empty djbh ? '' : djbh}</s:textfield>
</s:row>
</s:form>
</s:row>
<s:row><s:form label="流程展现" icon="fa fa-list-alt" color="grey-silver" border="1" cols="4" istoorbar="N"><s:row type="frow"><s:taskhistory label="taskhistory" cols="" djbh="${djbh}"></s:taskhistory>
</s:row>
</s:form>
</s:row>
</s:page>
