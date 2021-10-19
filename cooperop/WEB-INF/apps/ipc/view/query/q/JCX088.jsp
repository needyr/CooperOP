<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="http://www.crtech.cn/jstl/cooperoptld"%>
<%@page import="java.util.Map" %>
<%@page import="java.util.Iterator" %>
<%@page import="cn.crtech.cooperop.application.action.BillAction" %>
<%@page import="cn.crtech.cooperop.bus.util.CommonFun" %>
<s:page ismodal="false" disloggedin="false" dispermission="false" schemeid="JCX088" init-action="" description="科室患者详情查看" system_product_code="ipc" title="科室患者详情查看" remark="科室患者详情查看" flag="q" type="query"><%
	Map<String, Object> a = null;
	if(!CommonFun.isNe(request.getParameter("djbh"))){
		a = BillAction.init("ipc.query.q.JCX088", (String)request.getParameter("djbh"));
	} else if(!CommonFun.isNe(request.getParameter("gzid"))){
		a = BillAction.initFromCache("ipc.query.q.JCX088", (String)request.getParameter("gzid"));
	} else if(!CommonFun.isNe(request.getParameter("p_dj_sn"))){
		a = BillAction.initFromCacheMX(request.getParameter("p_pageid"), "ipc.query.q.JCX088", (String)request.getParameter("ptableid"), (String)request.getParameter("p_dj_sn"), (String)request.getParameter("p_dj_sort"), (String)request.getParameter("p_gzid"));
	} else {
		a = BillAction.init("ipc.query.q.JCX088", null);
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
<s:row><s:form label="患者审查详情查询" icon="fa fa-list-alt" color="default" border="1" cols="4" istoorbar="Y" app_collapsed="Y"><s:row type="frow"><s:datefield label="审查开始日期" cols="1" name="start_date" value="${empty start_date ? rq : start_date}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
<s:datefield label="审查截止日期" cols="1" name="end_date" value="${empty end_date ? rq : end_date}" placeholder="" fdtype="字符" defaultValue="${rq}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="10" precision=""></s:datefield>
</s:row>
<s:row type="frow"><s:textfield color="grey" label="科室名称" name="dept_name" placeholder="" fdtype="字符" cols="1" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="true" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="zl_select_JCX088_02" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_name ? '' : dept_name}</s:textfield>
</s:row>
<s:row type="frow"><s:textfield label="住院号" cols="1" name="patient_no" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="32" precision="">${empty patient_no ? '' : patient_no}</s:textfield>
<s:textfield label="患者姓名" cols="1" name="patient_name" placeholder="输入患者姓名" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="1280" precision="">${empty patient_name ? '' : patient_name}</s:textfield>
</s:row>
<s:row type="frow" ishidden="ishidden"><s:textfield label="病人标识号" cols="1" name="patient_id" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="32" precision="">${empty patient_id ? '' : patient_id}</s:textfield>
<s:textfield label="职员内码" cols="1" name="zhiyid" placeholder="" fdtype="字符" defaultValue="${userid}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="11" precision="">${empty zhiyid ? userid : zhiyid}</s:textfield>
<s:textfield label="本次住院标识" cols="1" name="visit_id" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="32" precision="">${empty visit_id ? '' : visit_id}</s:textfield>
<s:textfield color="grey" label="职员名称" name="zhiyname" placeholder="" fdtype="字符" cols="1" defaultValue="${username}" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="20" precision="">${empty zhiyname ? username : zhiyname}</s:textfield>
<s:textfield label="科室代码" cols="1" name="dept_code" placeholder="" fdtype="字符" defaultValue="" format="" nextfocusfield="" islabel="否" readonly="false" required="否" encryption="否" create_action="" modify_action="" enter_action="" dbl_action="" out_action="" tableid="" expressions="" maxlength="128" precision="">${empty dept_code ? '' : dept_code}</s:textfield>
</s:row>
<s:toolbar><s:button label="运行" icon="glyphicon glyphicon-play-circle" nextfocusfield="" action="querymx" color=""></s:button>
</s:toolbar>
</s:form>
</s:row>
<s:row><s:tabpanel color="grey-silver" cols="4"><s:table label="患者审查详情" cols="4" color="grey-silver" active="true" isdesign_="Y" tableid="01" icon="glyphicon glyphicon-question-sign" djselect="" tablekey="" limit="25" toIndex="0" sort="true" autoload="false" istoorbar="Y" myIndex="0" fields="" app_table="false" height="" totals="" action="application.bill.queryTable"  select="multi" ><s:table.fields><s:table.field name="patient_name" value="$[patient_name]"  fdtype="字符" format="" dictionary="" label="患者姓名" size="1280" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="hospital_common.showturns.patientdetail" app_field="" datatype="template"><s:textfield readonly="true" name="patient_name" value="$[patient_name]"  fdtype="字符" format="" dictionary="" size="1280" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="textfield" align="left" width="120" fieldorder="" is_href="hospital_common.showturns.patientdetail" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="dept_name" fdtype="字符" format="" dictionary="" label="科室名称" size="128" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="check_level_name" fdtype="字符" format="" dictionary="" label="严重程度" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="60" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="sort_name" fdtype="字符" format="" dictionary="" label="审查类型" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="50" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="ordermessage" fdtype="字符" format="" dictionary="" label="医嘱信息" size="2000" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="400" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="is_shenc_pass" value="$[is_shenc_pass]"  fdtype="字符" format="" dictionary="" label="审查合理" size="1" maxlength_show="" digitsize="1" precision="" available="true" sort="true" controltype="textfield" align="left" width="80" fieldorder="" is_href="ipc.comment.pass" app_field="" datatype="template"><s:textfield readonly="true" name="is_shenc_pass" value="$[is_shenc_pass]"  fdtype="字符" format="" dictionary="" size="1" maxlength_show="" digitsize="1" precision="" available="true" sort="true" controltype="textfield" align="left" width="80" fieldorder="" is_href="ipc.comment.pass" app_field="">null</s:textfield>
</s:table.field>
<s:table.field name="description" datatype="script"  fdtype="文本" format="" dictionary="" label="描述" size="" maxlength_show="60" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="400" fieldorder="" is_href="" app_field=""> var html = [];  if(record.description && record.description.length>60){  	html.push('<a href="javascript:void(0);" title="'+record.description+'" style="cursor: pointer;"');  	html.push(' onclick="show_maxlength(this);" ');  	html.push('>');  	html.push(record.description.substring(0,60)+'……');  	html.push('</a>');  }else{  	html.push(record.description);  }  return html.join(''); </s:table.field>
<s:table.field name="reference" datatype="script"  fdtype="字符" format="" dictionary="" label="参考" size="500" maxlength_show="50" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="" is_href="" app_field=""> var html = [];  if(record.reference && record.reference.length>50){  	html.push('<a href="javascript:void(0);" title="'+record.reference+'" style="cursor: pointer;"');  	html.push(' onclick="show_maxlength(this);" ');  	html.push('>');  	html.push(record.reference.substring(0,50)+'……');  	html.push('</a>');  }else{  	html.push(record.reference);  }  return html.join(''); </s:table.field>
<s:table.field name="audit_source_type" fdtype="字符" format="" dictionary="" label="审查来源" size="50" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="100" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="check_datetime" fdtype="字符" format="" dictionary="" label="审查日期" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="yaoshi_advice" fdtype="字符" format="" dictionary="" label="药师意见" size="1000" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="150" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="doctor_advice" fdtype="字符" format="" dictionary="" label="医生意见" size="100" maxlength_show="" digitsize="0" precision="" available="true" sort="true" controltype="" align="left" width="150" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="auto_audit_id" fdtype="字符" format="" dictionary="" label="审查流水ID" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="200" fieldorder="" is_href="" app_field=""></s:table.field>
<s:table.field name="check_result_info_id" fdtype="字符" format="" dictionary="" label="审查问题ID" size="" maxlength_show="" digitsize="" precision="" available="true" sort="true" controltype="" align="left" width="120" fieldorder="" is_href="" app_field=""></s:table.field>
</s:table.fields>
<s:toolbar><s:button label="刷新" icon="icon-reload" nextfocusfield="" action="querymx" color=""></s:button>
<s:button label="导出" icon="glyphicon glyphicon-random" size="btn-sm" nextfocusfield="" action="exportexcel" color=""></s:button>
</s:toolbar>
</s:table>
</s:tabpanel>
</s:row>
</s:page>
